<%-- 
    Document   : index.jsp
    Created on : Jan 4, 2021, 12:32:07 PM
    Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id" content="937118531722-jes7468psj0s162odcoqkn22866l9efp.apps.googleusercontent.com">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://apis.google.com/js/platform.js" async defer></script>
    </head>
    <body>
        <c:import url='./navbar.jsp'/>
        <c:if test="${sessionScope.USER ==null}">
            <div class="container login-container">
                <div class="row">
                    <div class="col-md-6 login-form-1">
                        <h3>Welcome to HanaShop</h3>
                        <form action='MainController' method="POST">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="UserName" name='txtUserName' value="${param.txtUserName}" />
                            </div>
                            <p>${requestScope.LOGIN_ERROR}</p>
                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="Password*"  name='txtPassword' value="${param.txtPassword}" />
                            </div>
                            <div  class="g-signin2" data-onsuccess="onSignIn"></div>
                            <div class="form-group">
                                <input type="submit" class="btnSubmit"  name='btnAction'value="Login" />
                            </div>

                        </form>

                    </div>
                </div>
            </div>  
        </c:if>
        <script>
            function onSignIn(googleUser) {
                var auth2 = gapi.auth2.getAuthInstance();
                auth2.signOut().then(function () {
                });
                var profile = googleUser.getBasicProfile();
                window.location.href = "MainController?btnAction=LoginGoogle&userName=" + profile.getName() + "&email=" + profile.getEmail() + "&userID=" + profile.getId();
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
