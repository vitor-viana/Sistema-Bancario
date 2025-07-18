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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "TransferenciaServlet", urlPatterns = {"/transferencia"})
public class TransferenciaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("id_usuario");
        if (idUsuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String numeroContaDestino = request.getParameter("numero_conta_destino");
        double valor = Double.parseDouble(request.getParameter("valor"));

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Verificar saldo da conta origem
                String sqlCheckOrigem = "SELECT id_conta, saldo FROM contas WHERE id_usuario = ?";
                int idContaOrigem = -1;
                boolean saldoSuficiente = false;
                try (PreparedStatement stmtCheckOrigem = conn.prepareStatement(sqlCheckOrigem)) {
                    stmtCheckOrigem.setInt(1, idUsuario);
                    ResultSet rsOrigem = stmtCheckOrigem.executeQuery();
                    if (rsOrigem.next()) {
                        idContaOrigem = rsOrigem.getInt("id_conta");
                        saldoSuficiente = rsOrigem.getDouble("saldo") >= valor;
                    }
                }

                // Verificar conta destino
                String sqlCheckDestino = "SELECT id_conta FROM contas WHERE numero_conta = ?";
                int idContaDestino = -1;
                try (PreparedStatement stmtCheckDestino = conn.prepareStatement(sqlCheckDestino)) {
                    stmtCheckDestino.setString(1, numeroContaDestino);
                    ResultSet rsDestino = stmtCheckDestino.executeQuery();
                    if (rsDestino.next()) {
                        idContaDestino = rsDestino.getInt("id_conta");
                    }
                }

                if (saldoSuficiente && idContaDestino != -1) {
                    // Atualizar saldo origem
                    String sqlUpdateOrigem = "UPDATE contas SET saldo = saldo - ? WHERE id_conta = ?";
                    try (PreparedStatement stmtUpdateOrigem = conn.prepareStatement(sqlUpdateOrigem)) {
                        stmtUpdateOrigem.setDouble(1, valor);
                        stmtUpdateOrigem.setInt(2, idContaOrigem);
                        stmtUpdateOrigem.executeUpdate();
                    }

                    // Atualizar saldo destino
                    String sqlUpdateDestino = "UPDATE contas SET saldo = saldo + ? WHERE id_conta = ?";
                    try (PreparedStatement stmtUpdateDestino = conn.prepareStatement(sqlUpdateDestino)) {
                        stmtUpdateDestino.setDouble(1, valor);
                        stmtUpdateDestino.setInt(2, idContaDestino);
                        stmtUpdateDestino.executeUpdate();
                    }

                    // Registrar transação
                    String sqlInsert = "INSERT INTO transacoes (id_conta_origem, id_conta_destino, valor, tipo_transacao) VALUES (?, ?, ?, 'TRANSFERENCIA')";
                    try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                        stmtInsert.setInt(1, idContaOrigem);
                        stmtInsert.setInt(2, idContaDestino);
                        stmtInsert.setDouble(3, valor);
                        stmtInsert.executeUpdate();
                    }

                    conn.commit();
                    response.sendRedirect("atm.jsp?success=Transferência realizada");
                } else {
                    conn.rollback();
                    response.sendRedirect("atm.jsp?error=Saldo insuficiente ou conta destino inválida");
                }
            } catch (SQLException e) {
                conn.rollback();
                throw new ServletException("Erro ao realizar transferência", e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao conectar ao banco", e);
        }
    }
}