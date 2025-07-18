<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ATM - Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Caixa Eletrônico - Login</h1>
        <form action="login" method="post">
            <label>CPF:</label>
            <input type="text" name="cpf" required><br>
            <label>Senha:</label>
            <input type="password" name="senha" required><br>
            <input type="submit" value="Entrar">
        </form>
        <a href="cadastro.jsp">Cadastrar novo usuário</a>
        <% if (request.getParameter("error") != null) { %>
            <p class="error">CPF ou senha inválidos</p>
        <% } %>
    </div>
</body>
</html>