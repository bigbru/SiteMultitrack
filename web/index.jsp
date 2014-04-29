<%-- 
    Document   : index
    Created on : 13 mars 2014, 16:51:59
    Author     : Dam
--%>

<!-- Ne pas oublier cette ligne sinon tous les tags de la JSTL seront ignorés ! -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="header.jsp"/>
<div id="contentWithLog" hidden>
    <div class="well">
        <div class="form-inline" role="form">
            <div class="form-group">
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="inputLoginSearch" placeholder="Login">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" class="btn btn-default" id="btnSearchLogin">Rechercher</button>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-info" id="createUserBtn"><span class="glyphicon glyphicon-plus"></span> Add User</button>
    <div class="col-lg-3">
        <div>
            <div class="input-group">
                <input type="number" class="form-control" id="inputNbUsers" placeholder="Nb utilisateurs">
                <span class="input-group-btn">
                    <button class="btn btn-warning" type="button" id="btnGenerateUser">Créer users</button>
                </span>
            </div>
        </div>
    </div>

    <hr/>
    <div id="createUserDiv" class="" hidden>
        <div class="form-horizontal" role="form">
            <div class="form-group">
                <label for="inputNom" class="col-sm-2 control-label">Nom</label>
                <div class="col-sm-5">
                    <input type="nom" class="form-control" id="inputNom" placeholder="Nom" name="nom">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPrenom" class="col-sm-2 control-label">Prenom</label>
                <div class="col-sm-5">
                    <input type="prenom" class="form-control" id="inputPrenom" placeholder="Prenom" name="prenom">
                </div>
            </div>
            <div class="form-group">
                <label for="inputLogin" class="col-sm-2 control-label">Login</label>
                <div class="col-sm-5">
                    <input type="login" class="form-control" id="inputLogin" placeholder="Login" name="login">
                </div>
            </div>
            <div class="form-group">
                <label for="inputCp" class="col-sm-2 control-label">Code Postal</label>
                <div class="col-sm-5">
                    <input type="login" class="form-control" id="inputCp" placeholder="Code Postal" name="cp">
                </div>
            </div>
            <div class="form-group">
                <label for="inputVille" class="col-sm-2 control-label">Ville</label>
                <div class="col-sm-5">
                    <input type="login" class="form-control" id="inputVille" placeholder="Ville" name="ville">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" id="btnAddUser" class="btn btn-default">Ajouter</button>
                </div>
            </div>
        </div>
        <hr/>
    </div>

    <h2>Liste des utilisateurs <button type="button" class="btn btn-primary btn-xs" id="btnRefreshList" href=""><span class="glyphicon glyphicon-refresh"></span></button></h2>

    <table class="table table-striped" id="tableListMain">
        <thead>
        <th>Login</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>CP</th>
        <th>Ville</th>
        <th>Utils</th>
        </thead>
        <tbody id="tableListUsers"></tbody>
    </table>
    
    <ul class="pager">
        <li id="liPrevBtn"><a href="#" id="btnPrevPage">Previous</a></li>
        <li id="liNextBtn"><a href="#" id="btnNextPage">Next</a></li>
    </ul>
    
</div>
<div id="contentWithoutLog">
    <div class="jumbotron">
        <h1>Gestionnaire d'utilisateurs</h1>
        <p>Bienvenue dans le système de gestion des utilisateurs</p>
        <p>Sur le site vous pourrez donc gérer des utilisateurs, en ajouter, en supprimer, etc. Afin d'accéder à ces fonctionnalités, connectez vous.</p>
        <p>Si vous n'êtes pas inscrit, vous pouvez vous inscrire en cliquant sur le bouton ci-dessous.</p>
        <p><a class="btn btn-primary btn-lg" id="signUpUserBtn" role="button">S'inscrire</a></p>

        <hr/>
        <div id="signUpUserDiv" hidden>
            <div class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="signUpLogin" class="col-sm-2 control-label">Login</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="signUpLogin" placeholder="Login">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="signUpNom" class="col-sm-2 control-label">Nom</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="signUpNom" placeholder="Nom">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="signUpPrenom" class="col-sm-2 control-label">Prenom</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="signUpPrenom" placeholder="Prenom">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="signUpCp" class="col-sm-2 control-label">Code Postal</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="signUpCp" placeholder="Code postal">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="signUpVille" class="col-sm-2 control-label">Ville</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="signUpVille" placeholder="Ville">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="signUpNumR" class="col-sm-2 control-label">Numéro</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="signUpNumR" placeholder="Numéro de rue">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="signUpRue" class="col-sm-2 control-label">Rue</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="signUpRue" placeholder="Nom de rue">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="signUpPass" class="col-sm-2 control-label">Mot de Passe</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="signUpPass" placeholder="Mot de passe">
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" id="btnCreateNewUser" class="btn btn-default">S'inscrire</button>
                    </div>
                </div>
            </div>
        </div>
    </div> 
</div>            

<jsp:include page="footer.jsp"/>
