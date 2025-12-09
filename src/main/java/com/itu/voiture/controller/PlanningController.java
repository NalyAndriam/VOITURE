package com.itu.voiture.controller;

import com.itu.voiture.dao.ReservationDao;
import com.itu.voiture.dao.VoitureDao;
import com.itu.voiture.model.Reservation;
import com.itu.voiture.model.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PlanningController {

    @Autowired private ReservationDao reservationDao;
    @Autowired private VoitureDao voitureDao;

    @Value("${planning.vitesse.kmh:20.0}")
    private double vitesseKmh;

    @GetMapping("/")
    public String home(
            @RequestParam(name = "date", required = false) String dateStr,
            @RequestParam(name = "voiture", required = false) String numeroVoiture,
            Model model) {

        List<Reservation> reservations;
        List<Voiture> voitures = voitureDao.getAll();

        // Initialisation des listes pour éviter NullPointerException
        for (Voiture v : voitures) {
            if (v.getReservationsAttribuees() == null) {
                v.setReservationsAttribuees(new ArrayList<>());
            }
            if (v.getHeureDisponible() == null) {
                v.setHeureDisponible(Time.valueOf("00:00:00"));
            }
        }

        // SI ON A UNE DATE → on filtre par date
        if (dateStr != null && !dateStr.trim().isEmpty()) {
            try {
                Date sqlDate = Date.valueOf(dateStr);
                reservations = reservationDao.getReservationsByDate(sqlDate);
                model.addAttribute("selectedDate", dateStr);
                model.addAttribute("selectedDateAsDate", sqlDate);
            } catch (Exception e) {
                model.addAttribute("error", "Date invalide");
                reservations = reservationDao.getAll(); // fallback
            }
        } else {
            // SINON → on affiche TOUTES les réservations (toutes dates)
            reservations = reservationDao.getAll();
            model.addAttribute("selectedDate", ""); // pas de filtre
        }

        // Attribution des voitures
        attribuerVoitures(reservations, voitures);

        // Filtre optionnel par voiture
        if (numeroVoiture != null && !numeroVoiture.trim().isEmpty()) {
            voitures = voitures.stream()
                    .filter(v -> v.getNumero().equals(numeroVoiture))
                    .toList();
        }

        model.addAttribute("voitures", voitures);
        return "index";
    }

    private void attribuerVoitures(List<Reservation> reservations, List<Voiture> voitures) {
        reservations.sort((r1, r2) -> r1.getHeureArrivee().compareTo(r2.getHeureArrivee()));

        for (Reservation r : reservations) {
            if (r.getDistanceKm() <= 0) continue;

            double distanceAR = r.getDistanceKm() * 2;
            double tempsHeures = distanceAR / vitesseKmh;
            r.setTempsTrajetHeures(tempsHeures);

            long minutes = Math.round(tempsHeures * 60);
            Time heureRetour = new Time(r.getHeureArrivee().getTime() + minutes * 60_000L);
            r.setHeureRetour(heureRetour);

            for (Voiture v : voitures) {
                if (v.getHeureDisponible().getTime() <= r.getHeureArrivee().getTime()) {
                    v.getReservationsAttribuees().add(r);
                    v.setHeureDisponible(heureRetour);
                    break;
                }
            }
        }
    }
}