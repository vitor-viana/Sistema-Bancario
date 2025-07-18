<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <title>ATM - Extrato</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Caixa Eletrônico - Extrato</h1>
        <ul>
            <% List<String> transacoes = (List<String>) request.getAttribute("transacoes"); %>
            <% if (transacoes != null && !transacoes.isEmpty()) { %>
                <% for (String transacao : transacoes) { %>
                    <li><%= transacao %></li>
                <% } %>
            <% } else { %>
                <li>Nenhuma transação encontrada</li>
            <% } %>
        </ul>
        <a href="atm.jsp">Voltar</a>
    </div>
</body>
</html>