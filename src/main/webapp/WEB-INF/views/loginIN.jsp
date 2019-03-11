<%--
  Created by IntelliJ IDEA.
  User: Lis
  Date: 29 янв 19
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script>
        function isnotNull() {
            console.log($("#mail").val());
            console.log($("#pass").val());
            if ($("#mail").val() != "" && $("#pass").val() != "") {
                return true;
            } else {
                alert("Введите логин или пароль");
                return false;
            }
        }
    </script>
</head>
<body>
<c:if test="${err!=null && err.error != null}">
    <div class="alert alert-danger  alert-dismissible">
        <strong>Ошибка!</strong> <c:out value="${err.error}"/>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">&times;</button>
    </div>
           </c:if>
<div class="container">
    <h2>Введите логин и пароль</h2>
    <form action="${pageContext.servletContext.contextPath}/signin" method="post">
        <div class="form-group">
            <label for="mail">Login:</label>
            <input type="login" class="form-control" id="mail" placeholder="Enter name" name="mail">
        </div>
        <div class="form-group">
            <label for="pass">Password:</label>
            <input type="password" class="form-control" id="pass" placeholder="Enter password" name="pass">
        </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input" type="checkbox" name="remember"> Remember me
            </label>
        </div>
        <button type="submit" class="btn btn-primary" onclick= "return isnotNull();">ВХОД</button>
    </form>
</div>

</body>

</body>
</html>
