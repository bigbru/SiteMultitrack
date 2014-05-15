<%-- 
    Document   : header
    Created on : 13 mars 2014, 18:09:40
    Author     : Dam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Ne pas oublier cette ligne sinon tous les tags de la JSTL seront ignorés ! -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Gestionnaire d'utilisateurs</title>
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico">
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="navbar navbar-default" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="index.jsp">MultiTrackSongs</a>
                </div>
                <div class="collapse navbar-collapse">
                    <div class="navbar-form navbar-right">
                        <div id="loginNavForm">   
                            <div class="form-group">
                                <input type="text" class="form-control" id="loginSession" placeholder="login">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="passwordSession" placeholder="password">
                            </div>
                            <button type="button" class="btn btn-default" id="submitLoginFormBtn">login</button>    
                        </div>
                        <div id="linkForDisconnect" hidden>
                            <p class="navbar-text"><a href="" id="logOutBtn">Déconnexion</a></p>
                        </div>
                    </div>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <div class="container">
