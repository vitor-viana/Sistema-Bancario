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

@WebServlet(name = "InvestimentoServlet", urlPatterns = {"/investimento"})
public class InvestimentoServlet extends HttpServlet {

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
        double taxa = Double.parseDouble(request.getParameter("taxa")) / 100; 
        int meses = Integer.parseInt(request.getParameter("meses"));

        // Simulação de investimento (juros compostos)
        double montante = valor * Math.pow(1 + taxa, meses);
        double rendimento = montante - valor;

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Registrar transação de investimento
            String sqlInsert = "INSERT INTO transacoes (id_conta_origem, valor, tipo_transacao) " +
                              "SELECT id_conta, ?, 'INVESTIMENTO' FROM contas WHERE id_usuario = ?";
            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setDouble(1, valor);
                stmtInsert.setInt(2, idUsuario);
                stmtInsert.executeUpdate();
            }
            request.setAttribute("montante", String.format("%.2f", montante));
            request.setAttribute("rendimento", String.format("%.2f", rendimento));
            request.getRequestDispatcher("investimento.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erro ao registrar investimento", e);
        }
    }
}