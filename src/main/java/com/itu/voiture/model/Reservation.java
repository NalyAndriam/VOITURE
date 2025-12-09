package com.itu.voiture.model;

import java.sql.Date;
import java.sql.Time;

public class Reservation {
    private int id;
    private String nom;
    private String prenom;
    private Date dateArrivee;
    private Time heureArrivee;
    private int nbPersonne;
    private int idHotel;
    private String nomHotel;        
    private double distanceKm;      
    private double tempsTrajetHeures; 
    private Time heureRetour;       

   
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public Date getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(Date dateArrivee) { this.dateArrivee = dateArrivee; }
    public Time getHeureArrivee() { return heureArrivee; }
    public void setHeureArrivee(Time heureArrivee) { this.heureArrivee = heureArrivee; }
    public int getNbPersonne() { return nbPersonne; }
    public void setNbPersonne(int nbPersonne) { this.nbPersonne = nbPersonne; }
    public int getIdHotel() { return idHotel; }
    public void setIdHotel(int idHotel) { this.idHotel = idHotel; }
    public String getNomHotel() { return nomHotel; }
    public void setNomHotel(String nomHotel) { this.nomHotel = nomHotel; }
    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
    public double getTempsTrajetHeures() { return tempsTrajetHeures; }
    public void setTempsTrajetHeures(double tempsTrajetHeures) { this.tempsTrajetHeures = tempsTrajetHeures; }
    public Time getHeureRetour() { return heureRetour; }
    public void setHeureRetour(Time heureRetour) { this.heureRetour = heureRetour; }
}