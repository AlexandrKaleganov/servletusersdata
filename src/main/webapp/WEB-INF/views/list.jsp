<%@ page import="ru.job4j.architecture.DispatchDiapason" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.job4j.architecture.Users" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Kaleganov Alxandr
  Date: 07 дек 18
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <script>
        function exit() {
            $.ajax({
                type: "POST",
                url: "./",
                data: {exit: "exit"}
            })
        };
        $(document).ready(function () {
            <c:if test="${message!=null}">
            alert("${message}");
            </c:if>
        });
    </script>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Трекер: список пользователей</a>
        </div>
        <ul id="do" class="nav navbar-nav">
            <li class="active"><a href="${pageContext.servletContext.contextPath}/">Home</a></li>
            <li class="active"><a href="${pageContext.servletContext.contextPath}/create">Добавить пользователя</a></li>
            <li class="active"><a href="${pageContext.servletContext.contextPath}/list">Список пользователей</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="${pageContext.servletContext.contextPath}/" onclick="exit()"><span
                    class="glyphicon glyphicon-user"></span> Выход</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <h2>Список пользователей</h2>
    <p>Список пользователей форму бутстрап</p>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>id</th>
            <th>Name</th>
            <th>Email</th>
            <th>Страна</th>
            <th>Город</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        </tr>
        <c:forEach items="${list}" var="u">
            <tr>
                <td><c:out value="${u.id}"/></td>
                <td><c:out value="${u.name}"/></td>
                <td><c:out value="${u.mail}"/></td>
                <td><c:out value="${u.country}"/></td>
                <td><c:out value="${u.city}"/></td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/edit" method="post">
                        <input type="hidden" name="id" value="${u.id}">
                        <input type="hidden" name="action" value="findbyid">
                        <input type="submit" value="EDIT">
                    </form>
                </td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${u.id}">
                        <input type="submit" value="DELETE">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
