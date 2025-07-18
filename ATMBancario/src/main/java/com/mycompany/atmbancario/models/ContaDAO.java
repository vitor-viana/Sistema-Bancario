package com.mycompany.atmbancario.models;

import com.mycompany.atmbancario.db.DatabaseConnection;
import java.sql.*;
import java.math.BigDecimal;

public class ContaDAO {
    
    public Conta buscarPorIdUsuario(int id) {
        String sql = "SELECT * FROM contas WHERE id_usuario = ?";
        Conta conta = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    conta = new Conta();
                    conta.setIdConta(rs.getInt("id_conta"));
                    conta.setIdUsuario(rs.getInt("id_usuario"));
                    conta.setNumeroConta(rs.getString("numero_conta"));
                    conta.setSaldo(rs.getBigDecimal("saldo"));
                    conta.setTipoConta(rs.getString("tipo_conta"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conta;
    }
}
