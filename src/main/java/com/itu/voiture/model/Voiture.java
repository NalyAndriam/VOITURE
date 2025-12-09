package com.itu.voiture.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Voiture {
    private int id;
    private String numero;
    private int nbplaces;
    
    private List<Reservation> reservationsAttribuees = new ArrayList<>();
    
    private Time heureDisponible = Time.valueOf("00:00:00");

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public int getNbplaces() { return nbplaces; }
    public void setNbplaces(int nbplaces) { this.nbplaces = nbplaces; }
    
    public List<Reservation> getReservationsAttribuees() { return reservationsAttribuees; }
    public void setReservationsAttribuees(List<Reservation> reservationsAttribuees) { this.reservationsAttribuees = reservationsAttribuees; }
    
    public Time getHeureDisponible() { return heureDisponible; }
    public void setHeureDisponible(Time heureDisponible) { this.heureDisponible = heureDisponible; }
}