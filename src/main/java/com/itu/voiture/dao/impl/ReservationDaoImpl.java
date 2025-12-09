package com.itu.voiture.dao.impl;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.itu.voiture.dao.ReservationDao;
import com.itu.voiture.model.Reservation;
import com.itu.voiture.util.DatabaseConnection;

public class ReservationDaoImpl implements ReservationDao {

    @Override
    public List<Reservation> getReservationsByDate(Date date, DatabaseConnection dbConn) {
        List<Reservation> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();  
            String sql = "SELECT r.id, r.nom, r.prenom, r.heure_arrivee, r.nb_personne, " +
                         "r.id_hotel, h.nom AS nom_hotel, COALESCE(d.km, 0) AS distance_km " +
                         "FROM Reservation r " +
                         "JOIN Hotel h ON r.id_hotel = h.id " +
                         "LEFT JOIN Distance d ON d.vers = r.id_hotel AND d.depart = 'TNR' " +
                         "WHERE r.date_arrivee = ? " +
                         "ORDER BY r.heure_arrivee ASC";

            ps = conn.prepareStatement(sql);
            ps.setDate(1, date);
            rs = ps.executeQuery();

            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setNom(rs.getString("nom"));
                r.setPrenom(rs.getString("prenom"));
                r.setHeureArrivee(rs.getTime("heure_arrivee"));
                r.setNbPersonne(rs.getInt("nb_personne"));
                r.setIdHotel(rs.getInt("id_hotel"));
                r.setNomHotel(rs.getString("nom_hotel"));
                r.setDistanceKm(rs.getDouble("distance_km"));
                list.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lecture réservations", e);
        } finally {
            DatabaseConnection.close(rs);
            DatabaseConnection.close(ps);
            DatabaseConnection.close(conn);
        }
        return list;
    }
}