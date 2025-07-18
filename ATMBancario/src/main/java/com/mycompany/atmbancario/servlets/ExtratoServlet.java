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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ExtratoServlet", urlPatterns = {"/extrato"})
public class ExtratoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("id_usuario");
        if (idUsuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT t.id_transacao, t.valor, t.tipo_transacao, t.data " +
                        "FROM transacoes t JOIN contas c ON t.id_conta_origem = c.id_conta " +
                        "WHERE c.id_usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idUsuario);
                ResultSet rs = stmt.executeQuery();
                List<String> transacoes = new ArrayList<>();
                while (rs.next()) {
                    String transacao = String.format("ID: %d, Tipo: %s, Valor: R$ %.2f, Data: %s",
                            rs.getInt("id_transacao"), rs.getString("tipo_transacao"),
                            rs.getDouble("valor"), rs.getTimestamp("data"));
                    transacoes.add(transacao);
                }
                request.setAttribute("transacoes", transacoes);
                request.getRequestDispatcher("extrato.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao consultar extrato", e);
        }
    }
}