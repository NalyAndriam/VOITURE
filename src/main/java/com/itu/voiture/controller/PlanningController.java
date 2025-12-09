package com.itu.voiture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itu.voiture.dao.ReservationDao;
import com.itu.voiture.dao.VoitureDao;
import com.itu.voiture.dao.impl.ReservationDaoImpl;
import com.itu.voiture.dao.impl.VoitureDaoImpl;
import com.itu.voiture.model.Reservation;
import com.itu.voiture.model.Voiture;
import com.itu.voiture.util.DatabaseConnection;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Controller
public class PlanningController {

    private final ReservationDao reservationDao = new ReservationDaoImpl();
    private final VoitureDao voitureDao = new VoitureDaoImpl();
    private final DatabaseConnection dbConn = new DatabaseConnection(); // une seule instance

    private static final double VITESSE_KMH = 20.0; 

    @GetMapping("/")
    public String home(@RequestParam(name = "date", required = false) String dateStr, Model model) {

        if (dateStr == null || dateStr.isEmpty()) {
            return "index";
        }

        Date date = Date.valueOf(dateStr);

        List<Reservation> reservations = reservationDao.getReservationsByDate(date, dbConn);
        List<Voiture> voitures = voitureDao.getAll(dbConn);

        attribuerVoitures(reservations, voitures);

        model.addAttribute("voitures", voitures);
        model.addAttribute("selectedDate", dateStr);

        return "index";
    }

    private void attribuerVoitures(List<Reservation> reservations, List<Voiture> voitures) {
        for (Reservation r : reservations) {
            if (r.getDistanceKm() <= 0) {
                continue; 
            }

            double distanceAllerRetour = r.getDistanceKm() * 2;
            double tempsTrajetHeures = distanceAllerRetour / VITESSE_KMH;
            r.setTempsTrajetHeures(tempsTrajetHeures);

            long minutesTrajet = Math.round(tempsTrajetHeures * 60);
            long millisArrivee = r.getHeureArrivee().getTime();
            Time heureRetour = new Time(millisArrivee + minutesTrajet * 60_000L);
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