package com.mycompany.atmbancario.models;

import com.mycompany.atmbancario.db.DatabaseConnection;
import java.sql.*;

public class UsuarioDAO {
   
    public Usuario buscarPorCpfSenha(String cpf, String senha) {
        String sql = "SELECT * FROM usuarios WHERE cpf = ? AND senha = ?";
        Usuario usuario = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setSenha(rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
