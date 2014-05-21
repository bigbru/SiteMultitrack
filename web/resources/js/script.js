$(document).ready(function() {

    function getSongsIfLog() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=testForLogin",
            success: function(msg) {
                if (msg !== "false") {
                    $.ajax({
                        type: "POST",
                        url: "ServletSongs",
                        data: "action=getSongs",
                        success: function(msg) {
                            if (msg !== "false") {
                                logOutDisplay(false);
                                refreshListSongs(JSON.parse(msg));
                            }
                        }
                    });
                }
            }
        });
    }

    function hideForLog() {
        $("#divTableListSongs").hide();
        $("#divAbo").hide();
        $("#contentLogScreen").hide();
        $("#divUserInfo").hide();
        $("#divUserPaiements").hide();
    }

    function checkIfLog() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=testForLogin",
            success: function(msg) {
                if (msg !== "false") {
                    logOutDisplay(false);
                    hideForLog();
                    $("#contentLogScreen").show();
                }
            }
        });
    }

    function logOutDisplay(forLogOut) {
        if (forLogOut) {
            $("#linkForDisconnect").hide();
            $("#loginNavForm").show();
            $("#contentWithLog").hide();
            $("#contentWithoutLog").show();
        } else {
            $("#linkForDisconnect").show();
            $("#loginNavForm").hide();
            $("#contentWithLog").show();
            $("#contentWithoutLog").hide();
            hideForLog();
        }
    }

    function refreshListSongs(json) {
        hideForLog();
        $("#divTableListSongs").show();
        $("#tableListSongs").html("");
        var btn;
        var getIt = false;
        var abo = false;
        var obj;
        var objUser;


        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=getAbo",
            success: function(aboJ) {
                var jsonAbo = JSON.parse(aboJ)[0];

                if (jsonAbo.abo !== "false") {
                    for (var i = 0; i < json.length; i++) {
                        btn = '';
                        obj = json[i];
                        btn = '<button type="button" class="btn btn-info infoModalBtn" id="' + obj.id + '"><span class="glyphicon glyphicon-list-alt"></span></button> ';
                        btn += '<button type="button" class="btn btn-success playSongBtn" id="' + obj.id + '"><span class="glyphicon glyphicon-ok"></span></button>';
                        $("#tableListSongs").append('\
                            <tr id="edit' + obj.id + 'Main">\
                                <td class="tdArtiste">' + obj.artiste + '</td>\
                                <td class="tdTitre">' + obj.titre + '</td>\
                                <td class="tdPrix">' + obj.prix + '€</td>\
                                <td class="tdUtils">' + btn + '</td>\
                            </tr>');
                    }
                } else {
                    $.ajax({
                        type: "POST",
                        url: "ServletUsers",
                        data: "action=getSongs",
                        success: function(userSongs) {
                            var userJson = JSON.parse(userSongs);
                            for (var i = 0; i < json.length; i++) {
                                btn = '';
                                getIt = false;
                                obj = json[i];
                                for (var j = 0; j < userJson.length; j++) {
                                    objUser = userJson[j];
                                    if (objUser.id === obj.id) {
                                        getIt = true;
                                    }
                                }
                                btn = '<button type="button" class="btn btn-info infoModalBtn" id="' + obj.id + '"><span class="glyphicon glyphicon-list-alt"></span></button> ';
                                if (getIt) {
                                    btn += '<button type="button" class="btn btn-success playSongBtn" id="' + obj.id + '"><span class="glyphicon glyphicon-ok"></span></button>';
                                } else {
                                    btn += '<button type="button" class="btn btn-warning suscribeBtn" id="' + obj.id + '"><span class="glyphicon glyphicon-usd"></span></button>';
                                }
                                $("#tableListSongs").append('\
                                    <tr id="edit' + obj.id + 'Main">\
                                        <td class="tdArtiste">' + obj.artiste + '</td>\
                                        <td class="tdTitre">' + obj.titre + '</td>\
                                        <td class="tdPrix">' + obj.prix + '€</td>\
                                        <td class="tdUtils">' + btn + '</td>\
                                    </tr>');
                            }
                        }
                    });
                }
            }
        });
    }

    $(document).on('click', '.infoModalBtn', function() {
        var id = $(this).attr('id');
        $.ajax({
            type: "POST",
            url: "ServletSongs",
            data: "action=getASong&id=" + id,
            success: function(msg) {
                var json = JSON.parse(msg)[0];
                $('#modalInfoSongTitle').html(json.artiste + " - " + json.titre);

                $.ajax({
                    type: "POST",
                    url: "ServletSongs",
                    data: "action=getPistes&id=" + id,
                    success: function(msgPistes) {
                        var jsonPistes = JSON.parse(msgPistes);
                        $('#modalInfoSongBody').html("<ul>");
                        for (var i = 0; i < jsonPistes.length; i++) {
                            obj = jsonPistes[i];
                            $('#modalInfoSongBody').append("<li>" + obj.nom + "</li>");
                        }
                        $('#modalInfoSongBody').append("</ul>");
                        $('#modalInfoSong').modal('show');
                    }
                });
            }
        });
    });

    $("#btnMyMusic").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=getSongs",
            success: function(msg) {
                if (msg !== "false") {
                    refreshListSongs(JSON.parse(msg));
                } else {
                    alert("Aucune musique");
                }
            }
        });
    });



    var selectForBuyDisplay = false;
    $("#buyMultipleSong").click(function() {
        if (selectForBuyDisplay) {
            $("#selectDivForBuySongs").hide();
            selectForBuyDisplay = false;
        }
        else {
            $("#selectDivForBuySongs").show();
            selectForBuyDisplay = true;
        }
    });

    var selectForSuscribeDisplay = false;
    $("#buySuscribeSong").click(function() {
        if (selectForSuscribeDisplay) {
            $("#selectDivForSuscribeSongs").hide();
            selectForSuscribeDisplay = false;
        }
        else {
            $("#selectDivForSuscribeSongs").show();
            selectForSuscribeDisplay = true;
        }
    });

    var signUpUserDisplay = false;
    $("#signUpUserBtn").click(function() {
        if (signUpUserDisplay) {
            $("#signUpUserDiv").hide();
            signUpUserDisplay = false;
        }
        else {
            $("#signUpUserDiv").show();
            signUpUserDisplay = true;
        }
    });

    $(".inputsForSignUp").keyup(function(e) {
        if (e.keyCode === 13) {
            $("#btnCreateNewUser").click();
        }
    });

    $("#btnCreateNewUser").click(function() {
        if ($("#signUpNom").val() === "" || $("#signUpPrenom").val() === "" || $("#signUpLogin").val() === "" || $("#signUpPass").val() === "") {
            alert("Certains champs sont vides");
        } else {
            $.ajax({
                type: "POST",
                url: "ServletUsers",
                data: "action=signUpUser&nom=" + $("#signUpNom").val() + "&prenom=" + $("#signUpPrenom").val() + "&login=" + $("#signUpLogin").val() + "&pass=" + $("#signUpPass").val(),
                success: function(msg) {
                    if (msg !== "false")
                        getSongsIfLog();
                    else
                        alert("Erreur dans l'inscription, ce login existe.");
                }
            });
        }
    });

    $(".inputsloginSession").keyup(function(e) {
        if (e.keyCode === 13) {
            $("#submitLoginFormBtn").click();
        }
    });

    $("#submitLoginFormBtn").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=loginUtilisateur&loginSession=" + $("#loginSession").val() + "&passwordSession=" + $("#passwordSession").val(),
            success: function(msg) {
                if (msg !== "false") {
                    logOutDisplay(false);
                } else
                    alert("login invalide");
            }
        });
    });

    $("#logOutBtn").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=deconnexion",
            success: function() {
                logOutDisplay(true);
            }
        });
    });

    // test si log puis affiche songs
    checkIfLog();

    $("#inputSongsSearch").keyup(function(e) {
        if (e.keyCode === 13) {
            $("#btnSearchSongs").click();
        }
    });

    $("#btnSearchSongs").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletSongs",
            data: "action=chercherChanson&str=" + $("#inputSongsSearch").val(),
            success: function(msg) {
                if (msg !== "false")
                    refreshListSongs(JSON.parse(msg));
                else
                    alert("Introuvable");
            }
        });
    });

    $("#btnPrevPage").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletSongs",
            data: "action=prevPage",
            success: function(msg) {
                refreshListSongs(JSON.parse(msg));
            }
        });
    });

    $("#btnNextPage").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletSongs",
            data: "action=nextPage",
            success: function(msg) {
                if (msg !== "false")
                    refreshListSongs(JSON.parse(msg));
            }
        });
    });


    $("#buyOneSong").click(function() {
        $("#btnRefreshList").click();
    });

    $("#btnRefreshList").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletSongs",
            data: "action=refreshList",
            success: function(msg) {
                refreshListSongs(JSON.parse(msg));
            }
        });
    });

    $("#btnAboForm").click(function() {
        hideForLog();
        $("#divAbo").show();

        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=getAbo",
            success: function(msg) {
                var jsonAbo = JSON.parse(msg)[0];

                if (jsonAbo.abo !== 'false') {
                    if (jsonAbo.abo !== 'life') {
                        $("#leadAboP").html("Vous êtes abonné jusqu'au " + jsonAbo.abo);
                    } else {
                        $("#leadAboP").html('Vous êtes abonné à vie.');
                        $("#rowSuscribeP").hide();
                        $("#rowSuscribeDiv").hide();
                    }
                } else {
                    $("#leadAboP").html("Vous n'êtes actuellement pas abonné et il vous reste " + jsonAbo.chansons + " chansons disponible.");
                }
            }
        });
    });

    $(document).on('click', '.suscribeBtn', function() {
        var id = $(this).attr('id');
        $.ajax({
            type: "POST",
            url: "ServletSongs",
            data: "action=suscribe&id=" + id,
            success: function(msg) {
                alert(msg);
            }
        });
    });

    $(document).on('click', '.playSongBtn', function() {
        var id = $(this).attr('id');
        $.ajax({
            type: "POST",
            url: "ServletSongs",
            data: "action=playSong&id=" + id,
            success: function(msg) {
                alert(msg);
            }
        });
    });


    $("#btnGetInfos").click(function() {
        hideForLog();
        $("#divUserInfo").show();
        $("#divUserInfo").html("");
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=getInfos",
            success: function(msg) {
                $("#divUserInfo").html(msg);
            }
        });
    });

    $("#selectBtnForBuySongs").click(function() {
        if (confirm("Voulez vous vraiment acheter " + $("#selectorForBuySongs option:selected").text() + " ?")) {
            var nbChansons = 0;
            switch ($("#selectorForBuySongs").val()) {
                case "1":
                    nbChansons = 2;
                    break;
                case "2":
                    nbChansons = 5;
                    break;
                case "3":
                    nbChansons = 10;
                    break;
                case "4":
                    nbChansons = 30;
                    break;
                case "5":
                    nbChansons = 50;
                    break;
            }
            $.ajax({
                type: "POST",
                url: "ServletUsers",
                data: "action=putLotSongs&nb="+nbChansons,
                success: function() {
                    alert("Merci de votre achat !");
                    $("#btnAboForm").click();
                }
            });
        }

    });

    $("#selectBtnForSuscribeSongs").click(function() {
        if (confirm("Voulez vous vraiment vous abonner " + $("#selectorForSuscribeSongs option:selected").text() + " ?")) {
            var jours = 0;
            switch ($("#selectorForSuscribeSongs").val()) {
                case "1":
                    jours = 1;
                    break;
                case "2":
                    jours = 15;
                    break;
                case "3":
                    jours = 30;
                    break;
                case "4":
                    jours = 90;
                    break;
                case "5":
                    jours = 180;
                    break;
                case "6":
                    jours = 365;
                    break;
                case "7":
                    jours = "life";
                    break;
            }
            $.ajax({
                type: "POST",
                url: "ServletUsers",
                data: "action=putSuscribeDay&nb="+jours,
                success: function() {
                    alert("Merci de votre achat !");
                    $("#btnAboForm").click();
                }
            });
        }
    });



//    $.ajax({
//        type: "POST",
//        url: "ServletSongs",
//        data: "action=getPages",
//        success: function(msg) {
//            if (msg !== "false") {
//                var jsonPages = JSON.parse(msg);
//                for (var i = 0; i < jsonPages.length; i++) {
//                    var obj = jsonPages[i];
//                    var nb = parseInt(obj.nb);
//                    var current = parseInt(obj.current);
//                    //if (current === 0) $("#liPrevBtn").addClass("disabled")
//                    //if (current === nb) $("#liNextBtn").addClass("disabled")
//                }
//            }
//        }
//    });


//    $("#btnSearchSongs").autocomplete({
//        minLength: 2,
//        scrollHeight: 220,
//        source: function(req, add) {
//            $.ajax({
//                url: 'ServletSongs',
//                type: "get",
//                dataType: 'json',
//                data: 'action=chercherChanson&str=' + req.term,
//                async: true,
//                cache: true,
//                success: function(data) {
//                    var suggestions = [];
//                    //process response  
//                    $.each(data, function(i, val) {
//                        suggestions.push({"id": val.id, "value": val.artiste});
//                    });
//                    //pass array to callback  
//                    add(suggestions);
//                }
//            });
//        }
//    });

});