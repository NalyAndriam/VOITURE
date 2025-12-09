package com.itu.voiture.dao;

import java.sql.Date;
import java.util.List;

import com.itu.voiture.model.Reservation;
import com.itu.voiture.util.DatabaseConnection;

public interface ReservationDao {
    List<Reservation> getReservationsByDate(Date date, DatabaseConnection dbConn);
}