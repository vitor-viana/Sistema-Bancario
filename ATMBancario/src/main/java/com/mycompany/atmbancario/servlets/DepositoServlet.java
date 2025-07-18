package com.mycompany.atmbancario.servlets;

import com.mycompany.atmbancario.db.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "DepositoServlet", urlPatterns = {"/deposito"})
public class DepositoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("id_usuario");
        if (idUsuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        double valor = Double.parseDouble(request.getParameter("valor"));

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Atualizar saldo
                String sqlUpdate = "UPDATE contas SET saldo = saldo + ? WHERE id_usuario = ?";
                try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                    stmtUpdate.setDouble(1, valor);
                    stmtUpdate.setInt(2, idUsuario);
                    stmtUpdate.executeUpdate();
                }

                // Registrar transação
                String sqlInsert = "INSERT INTO transacoes (id_conta_origem, valor, tipo_transacao) " +
                                  "SELECT id_conta, ?, 'DEPOSITO' FROM contas WHERE id_usuario = ?";
                try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                    stmtInsert.setDouble(1, valor);
                    stmtInsert.setInt(2, idUsuario);
                    stmtInsert.executeUpdate();
                }

                conn.commit();
                response.sendRedirect("atm.jsp?success=Depósito realizado");
            } catch (SQLException e) {
                conn.rollback();
                throw new ServletException("Erro ao realizar depósito", e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao conectar ao banco", e);
        }
    }
}