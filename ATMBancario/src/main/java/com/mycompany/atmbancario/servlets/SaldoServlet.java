package com.mycompany.atmbancario.servlets;

import com.mycompany.atmbancario.db.DatabaseConnection;
import com.mycompany.atmbancario.models.Conta;
import com.mycompany.atmbancario.models.ContaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SaldoServlet", urlPatterns = {"/saldo"})
public class SaldoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer id_usuario = (Integer) session.getAttribute("id_usuario");
        
        if (id_usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        ContaDAO contaDAO = new ContaDAO();
        Conta conta = contaDAO.buscarPorIdUsuario(id_usuario);
   
        if (conta != null) {
            request.setAttribute("saldo", conta.getSaldo());
            request.getRequestDispatcher("saldo.jsp").forward(request, response);
        } else {
            response.sendRedirect("atm.jsp?error=Conta não encontrada");
        }
        
    }
}