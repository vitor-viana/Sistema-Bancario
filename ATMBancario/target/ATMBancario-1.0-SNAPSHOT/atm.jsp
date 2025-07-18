<%@page import="com.mycompany.atmbancario.models.ContaDAO"%>
<%@page import="com.mycompany.atmbancario.models.Conta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Integer id_usuario = (Integer) session.getAttribute("id_usuario");
    ContaDAO contaDAO = new ContaDAO();
    Conta conta = contaDAO.buscarPorIdUsuario(id_usuario);
    
%>

<!DOCTYPE html>
<html>
<head>
    <title>ATM - Menu</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Caixa Eletrônico - Menu</h1>
        <p>Número da Conta: <%= conta.getNumeroConta() %></p>
        <form action="deposito" method="post">
            <label>Valor do Depósito:</label>
            <input type="number" step="0.01" name="valor" required>
            <input type="submit" value="Depositar">
        </form>
        <form action="saque" method="post">
            <label>Valor do Saque:</label>
            <input type="number" step="0.01" name="valor" required>
            <input type="submit" value="Sacar">
        </form>
        <form action="transferencia" method="post">
            <label>Número da Conta Destino:</label>
            <input type="text" name="numero_conta_destino" required>
            <label>Valor:</label>
            <input type="number" step="0.01" name="valor" required>
            <input type="submit" value="Transferir">
        </form>
        <form action="investimento" method="post">
            <label>Valor do Investimento:</label>
            <input type="number" step="0.01" name="valor" required>
            <label>Taxa (% ao mês):</label>
            <input type="number" step="0.01" name="taxa" required>
            <label>Meses:</label>
            <input type="number" name="meses" required>
            <input type="submit" value="Simular Investimento">
        </form>
        <a href="saldo">Consultar Saldo</a><br>
        <a href="extrato">Ver Extrato</a>
        <% if (request.getParameter("success") != null) { %>
            <p class="success"><%= request.getParameter("success") %></p>
        <% } %>
        <% if (request.getParameter("error") != null) { %>
            <p class="error"><%= request.getParameter("error") %></p>
        <% } %>
    </div>
</body>
</html>