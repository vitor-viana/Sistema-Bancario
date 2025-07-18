<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ATM - Saldo</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Caixa Eletrônico - Saldo</h1>
        <p>Saldo atual: R$ <%= request.getAttribute("saldo") %></p>
        <a href="atm.jsp">Voltar</a>
    </div>
</body>
</html>