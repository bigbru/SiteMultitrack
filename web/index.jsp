<%-- 
    Document   : index
    Created on : 13 mars 2014, 16:51:59
    Author     : Dam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-music"></span> Boutique <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#" id="btnRefreshList">Musiques</a></li>
                        <li><a href="#" id="btnAboForm">Abonnements</a></li>
                    </ul>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-home"></span> Mon compte <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#" id="btnMyMusic">Mes musiques</a></li>
                        <li class="divider"></li>
                        <li><a href="#" id="btnGetInfos">Mes infos</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div id="divAbo">
        <div class="page-header">
            <h1>Page d'abonnement</h1>
        </div>
        <p class="lead" id="leadAboP" style="color: #008000;font-size: 24px;"></p>
        <p class="lead" id="leadAboPNo" style="color: red;font-size: 24px;"></p>
        <p id=" ">Il existe trois possibilités pour acheter de la musique. La possibilité d'acheter chanson par chanson au tarif de 3,99€, acheter un lot de chansons ou bien de s'abonner au service.</p>
        <div class="page-header"></div>
        <div class="row" id="rowSuscribeDiv">
            <div class="col-lg-4">
                <img class="img-circle" data-src="holder.js/140x140" alt="140x140" src="resources/img/search-icon.svg" style="width: 140px; height: 140px;">
                <h2>Chanson unique</h2>
                <p>Vous avez la possibilité d'acheter une seule chanson uniquement au tarif de 3,99€. Cette chanson sera ensuite disponible à vie sur le service et associé à votre compte utilisateur.</p>
                <p><a class="btn btn-default" href="#" id="buyOneSong" role="button">Acheter une chanson »</a></p>
            </div>
            <div class="col-lg-4">
                <img class="img-circle" data-src="holder.js/140x140" alt="140x140" src="resources/img/song-icon.svg" style="width: 140px; height: 140px;">
                <h2>Lot de chansons</h2>
                <p>Vous avez ici la possibilité d'acheter les chansons par lot (de 2 à 50 chansons) avec un prix dégressif. Les prix commencent à partir de 0,80€ la chanson. Les chansons achetés en lot seront disponible à vie sur le service, associé à votre compte utilisateur.</p>
                <p><a class="btn btn-default" href="#" id="buyMultipleSong" role="button">Acheter un lot »</a></p>

                <div id="selectDivForBuySongs" hidden>
                    <hr/>
                    <form class="form" role="form">
                        <div class="form-group">
                            <select class="form-control" id="selectorForBuySongs">
                                <option value="1">2 chansons -> 5€</option>
                                <option value="2">5 chansons -> 10€</option>
                                <option value="3">10 chansons -> 15€</option>
                                <option value="4">30 chansons -> 30€</option>
                                <option value="5">50 chansons -> 40€</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <a href="#" id="selectBtnForBuySongs"><img src="https://www.paypal.com/fr_FR/FR/i/btn/btn_xpressCheckout.gif" align="left" style="margin-right:7px;"></a>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-lg-4">
                <img class="img-circle" data-src="holder.js/140x140" alt="140x140" src="resources/img/suscribe-icon.svg" style="width: 140px; height: 140px;">
                <h2>Abonnement</h2>
                <p>Vous avez également la possibilité de vous abonner au service pour différentes périodes de temps. Les formules sont disponible à partir de 5€ par mois pour un accès à la totalité du catalogue.</p>
                <p><a class="btn btn-default" href="#" id="buySuscribeSong" role="button">S'abonner »</a></p>

                <div id="selectDivForSuscribeSongs" hidden>
                    <hr/>
                    <form class="form" role="form">
                        <div class="form-group">
                            <select class="form-control" id="selectorForSuscribeSongs">
                                <option value="1">1 jour -> 1€</option>
                                <option value="2">15 jours -> 10€</option>
                                <option value="3">1 mois -> 15€</option>
                                <option value="4">3 mois -> 30€</option>
                                <option value="5">6 mois -> 50€</option>
                                <option value="6">1 an -> 60€</option>
                                <option value="7">à vie -> 100€</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <a href="#" id="selectBtnForSuscribeSongs"><img src="https://www.paypal.com/fr_FR/FR/i/btn/btn_xpressCheckout.gif" align="left" style="margin-right:7px;"></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div id="divUserInfo"></div>
    <div id="divUserPaiements"></div>
    <div id="contentLogScreen">
        <div class="jumbotron">
            <h1>Bienvenue sur BHUMP !</h1>
            <p>BHUMP est un player HTML5 multi-pistes destiné aux musiciens, celui-ci vous offre la possibilité de jouer uniquement les pistes de votre choix de chaque chansons</p>
            <b style="font-size:26px;">Inscrivez-vous et découvrez notre service grâce à ces 3 morceaux offerts :</b>
            <div style="height:90px;width:100%;margin-top: 15px;">
            <div class="imgMain">
                <img class="img-circle"  src="resources/img/alice.jpg" style="width: 100px; height: 100px;box-shadow: 5px 2px 11px #777777;float:left;margin-right:10px;">
                <b>Alice Copper</b> <br/><br/>
                <i> - I'm Eighteen</i>
            </div>
            <div class="imgMain">
                <img class="img-circle"  src="resources/img/beatles.jpg" style="width: 100px; height: 100px;box-shadow: 5px 2px 11px #777777;float:left;margin-right:10px;">
                <b>Beatles</b> <br/><br/>
                <i> - When Im 64</i>
            </div>
            <div class="imgMain">
                <img class="img-circle"  src="resources/img/dead-kennedy.jpg" style="width: 100px; height: 100px;box-shadow: 5px 2px 11px #777777;float:left;margin-right:10px;">
                <b>Dead Kennedys</b>  <br/><br/>
                <i> - Holiday In Cambodia</i>
            </div>
            </div>
        </div> 
    </div>

    <div id="divTableListSongs">
        <table class="table table-striped tablesorter" id="tableListMain">
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
        <h1>Bienvenue sur BHUMP !</h1>
        <p>BHUMP est un player HTML5 multi-pistes destiné aux musiciens, celui-ci vous offre la possibilité de jouer uniquement les pistes de votre choix de chaque chansons</p>
        <p>Si vous désirez essayer notre service, n'hésitez pas c'est gratuit !</p>
        <p>Si vous n'êtes pas inscrit, vous pouvez vous inscrire en cliquant sur le bouton ci-dessous.</p>
        <p><a class="btn btn-primary btn-lg" id="signUpUserBtn" role="button" style="margin-left: 360px;margin-top: 25px;width:300px;height:100px;border-radius: 30px;font-size:55px;padding-top: 13px;">S'inscrire</a></p>
        <hr/>
        <div id="signUpUserDiv" hidden>
            <div class="form-horizontal" role="form" style="margin-left:215px;">
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
                        <button type="button" id="btnCreateNewUser" class="btn btn-default" style="background-color: #2EA023;border-radius: 20px;color: #FFFFFF;font-size: 22px;height: 50px;margin-left: 76px;width: 140px;">Envoyer</button>
                    </div>
                </div>
            </div>
        </div>
        
        <b style="font-size:26px;">Inscrivez-vous et découvrez notre service grâce à ces 3 morceaux offerts :</b>
        <div style="height:90px;width:100%;margin-top: 15px;">
            <div class="imgMain">
                <img class="img-circle"  src="resources/img/alice.jpg" style="width: 100px; height: 100px;box-shadow: 5px 2px 11px #777777;float:left;margin-right:10px;">
                <b>Alice Copper</b> <br/><br/>
                <i> - I'm Eighteen</i>
            </div>
            <div class="imgMain">
                <img class="img-circle"  src="resources/img/beatles.jpg" style="width: 100px; height: 100px;box-shadow: 5px 2px 11px #777777;float:left;margin-right:10px;">
                <b>Beatles</b> <br/><br/>
                <i> - When Im 64</i>
            </div>
            <div class="imgMain">
                <img class="img-circle"  src="resources/img/dead-kennedy.jpg" style="width: 100px; height: 100px;box-shadow: 5px 2px 11px #777777;float:left;margin-right:10px;">
                <b>Dead Kennedys</b>  <br/><br/>
                <i> - Holiday In Cambodia</i>
            </div>
        </div>
    </div> 
</div>

<!-- Modal -->
<div class="modal fade" id="modalInfoSong" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalInfoSongTitle"></h4>
            </div>
            <div class="modal-body" id="modalInfoSongBody">

            </div>
            <div class="modal-footer" id="modalInfoSongFooter"></div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
