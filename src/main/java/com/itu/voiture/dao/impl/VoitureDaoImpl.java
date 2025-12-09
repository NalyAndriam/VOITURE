package com.itu.voiture.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.itu.voiture.dao.VoitureDao;
import com.itu.voiture.model.Voiture;
import com.itu.voiture.util.DatabaseConnection;

public class VoitureDaoImpl implements VoitureDao {

    @Override
    public List<Voiture> getAll(DatabaseConnection dbConn) {
        List<Voiture> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dbConn.getConnection();
            String sql = "SELECT id, numero, nbplaces FROM Voiture ORDER BY numero";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Voiture v = new Voiture();
                v.setId(rs.getInt("id"));
                v.setNumero(rs.getString("numero"));
                v.setNbplaces(rs.getInt("nbplaces"));
                list.add(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lecture des voitures", e);
        } finally {
            DatabaseConnection.close(rs);
            DatabaseConnection.close(ps);
            DatabaseConnection.close(conn);
        }
        return list;
    }
}