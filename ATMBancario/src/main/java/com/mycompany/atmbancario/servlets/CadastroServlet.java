package com.mycompany.atmbancario.servlets;

import com.mycompany.atmbancario.db.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "CadastroServlet", urlPatterns = {"/cadastro"})
public class CadastroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String numeroConta = "000" + (int)(Math.random() * 10000) + "-1";
        String tipoConta = "Corrente";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Inserir usuário
                String sqlUsuario = "INSERT INTO usuarios (nome, cpf, senha) VALUES (?, ?, ?)";
                try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmtUsuario.setString(1, nome);
                    stmtUsuario.setString(2, cpf);
                    stmtUsuario.setString(3, senha);
                    stmtUsuario.executeUpdate();
                    ResultSet rs = stmtUsuario.getGeneratedKeys();
                    int idUsuario = rs.next() ? rs.getInt(1) : -1;

                    // Inserir conta
                    String sqlConta = "INSERT INTO contas (id_usuario, numero_conta, saldo, tipo_conta) VALUES (?, ?, 0.00, ?)";
                    try (PreparedStatement stmtConta = conn.prepareStatement(sqlConta)) {
                        stmtConta.setInt(1, idUsuario);
                        stmtConta.setString(2, numeroConta);
                        stmtConta.setString(3, tipoConta);
                        stmtConta.executeUpdate();
                    }
                }
                conn.commit();
                response.sendRedirect("index.jsp?success=1");
            } catch (SQLException e) {
                conn.rollback();
                response.sendRedirect("cadastro.jsp?error=1");
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao cadastrar usuário", e);
        }
    }
}