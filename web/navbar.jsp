<%-- 
    Document   : navbar
    Created on : Jan 16, 2021, 8:16:28 AM
    Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Navbar Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <style>
        .navbar{
            background: #ffff00 !important;
        }
    </style>
    <body>

        <nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
            <div class="container-fluid">
                <a class="navbar-branch" href="#">
                    <img src="data:image/png;base64,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" height="50">
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" 
                        data-target="#navbarResponsive">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link active" href="MainController?btnAction=ShowHomePage">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="MainController?btnAction=search">Shopping</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="MainController?btnAction=ShowCartPage">Cart</a>
                        </li>
                        <c:if test="${sessionScope.USER ==null}">
                            <li class="nav-item">
                                <a class="nav-link" href="MainController?btnAction=ShowLoginPage">Login</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.USER !=null}">
                            <c:if test="${sessionScope.USER.roleId eq'AD'}">
                                <li class="nav-item">
                                    <a class="nav-link" href="MainController?btnAction=ShowManageFoodPage">Manage Food</a>
                                </li>
                            </c:if>
                            <li class="nav-item">
                                <p class="nav-link">Welcome ${sessionScope.USER.userName}</p>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="MainController?btnAction=SearchOrder">History Order</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="MainController?btnAction=Logout">Log Out</a>
                            </li>

                        </c:if>

                    </ul>
                </div>
            </div>
        </nav>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
