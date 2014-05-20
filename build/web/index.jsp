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
                    <input type="text" class="form-control" id="inputSongsSearch" placeholder="Artiste / Titre">
                </div>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default" id="btnSearchSongs">Rechercher</button>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" class="btn btn-primary" id="btnRefreshList"><span class="glyphicon glyphicon-usd"></span> Boutique</button>
                </div>
            </div>
        </div>
    </div>

    <div id="contentLogScreen">
        <div class="jumbotron">
            <h1>Bienvenue</h1>
            <p>Blablabla Blablabla Blablabla</p>
            <p>BlablablaBlablabla BlablablaBlablabla BlablablaBlablabla BlablablaBlablabla Blablabla</p>
        </div> 
    </div>

    <div id="divTableListSongs">
        <table class="table table-striped" id="tableListMain">
            <thead>
            <th>Artiste</th>
            <th>Titre</th>
            <th>Prix</th>
            <th>Utils</th>
            </thead>
            <tbody id="tableListSongs"></tbody>
        </table>

        <ul class="pager">
            <li id="liPrevBtn"><a href="#" id="btnPrevPage">Previous</a></li>
            <li id="liNextBtn"><a href="#" id="btnNextPage">Next</a></li>
        </ul>
    </div>

</div>
<div id="contentWithoutLog">
    <div class="jumbotron">
        <h1>SiteMultiTrack</h1>
        <p>Blablabla Blablabla Blablabla</p>
        <p>BlablablaBlablabla BlablablaBlablabla BlablablaBlablabla BlablablaBlablabla Blablabla</p>
        <p>Si vous n'êtes pas inscrit, vous pouvez vous inscrire en cliquant sur le bouton ci-dessous.</p>
        <p><a class="btn btn-primary btn-lg" id="signUpUserBtn" role="button">S'inscrire</a></p>

        <hr/>
        <div id="signUpUserDiv" hidden>
            <div class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="signUpLogin" class="col-sm-2 control-label">Login</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control inputsForSignUp" id="signUpLogin" placeholder="Login">
                    </div>
                </div>

                <div class="form-group">
                    <label for="signUpNom" class="col-sm-2 control-label">Nom</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control inputsForSignUp" id="signUpNom" placeholder="Nom">
                    </div>
                </div>

                <div class="form-group">
                    <label for="signUpPrenom" class="col-sm-2 control-label">Prenom</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control inputsForSignUp" id="signUpPrenom" placeholder="Prenom">
                    </div>
                </div>

                <div class="form-group">
                    <label for="signUpPass" class="col-sm-2 control-label">Mot de passe</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control inputsForSignUp" id="signUpPass" placeholder="Prenom">
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
