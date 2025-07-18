<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ATM - Investimento</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Caixa Eletrônico - Simulação de Investimento</h1>
        <p>Montante final: R$ <%= request.getAttribute("montante") %></p>
        <p>Rendimento: R$ <%= request.getAttribute("rendimento") %></p>
        <a href="atm.jsp">Voltar</a>
    </div>
</body>
</html>