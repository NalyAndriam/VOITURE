package com.itu.voiture.dao;

import java.util.List;

import com.itu.voiture.model.Voiture;
import com.itu.voiture.util.DatabaseConnection;

public interface VoitureDao {
    List<Voiture> getAll();
}