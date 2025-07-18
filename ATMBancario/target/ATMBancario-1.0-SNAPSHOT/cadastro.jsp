<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ATM - Cadastro</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Caixa Eletrônico - Cadastro</h1>
        <form action="cadastro" method="post">
            <label>Nome:</label>
            <input type="text" name="nome" required><br>
            <label>CPF:</label>
            <input type="text" name="cpf" required><br>
            <label>Senha:</label>
            <input type="password" name="senha" required><br>
            <input type="submit" value="Cadastrar">
        </form>
        <a href="index.jsp">Já tenho conta</a>
        <% if (request.getParameter("error") != null) { %>
            <p class="error">Erro ao cadastrar</p>
        <% } %>
        <% if (request.getParameter("success") != null) { %>
            <p class="success">Cadastro realizado</p>
        <% } %>
    </div>
</body>
</html>