$(function() {

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
                                console.log(msg);
                                refreshListSongs(JSON.parse(msg));
                            }
                        }
                    });
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
        }
    }

    function refreshListSongs(json) {
        $("#tableListSongs").html("");
        for (var i = 0; i < json.length; i++) {
            var obj = json[i];
            $("#tableListSongs").append('\
                    <tr id="edit' + obj.id + 'Main">\
                        <td class="tdAuteur">' + obj.auteur + '</td>\
                        <td class="tdTitre">' + obj.titre + '</td>\
                        <td class="tdPrix">' + obj.prix + '</td>\
                        <td class="tdUtils">\
                            <button type="button" class="btn btn-success suscribeBtn" id="' + obj.id + '"><span class="glyphicon glyphicon-star"></span></button>\
                        </td>\
                    </tr>');
        }
    }

    var createUserDisplay = false;
    $("#createUserBtn").click(function() {
        if (createUserDisplay) {
            $("#createUserDiv").hide();
            createUserDisplay = false;
        }
        else {
            $("#createUserDiv").show();
            createUserDisplay = true;
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

    $("#btnCreateNewUser").click(function() {
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
    });

    $("#submitLoginFormBtn").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=loginUtilisateur&loginSession=" + $("#loginSession").val() + "&passwordSession=" + $("#passwordSession").val(),
            success: function(msg) {
                if (msg !== "false") {
                    getSongsIfLog();
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
    getSongsIfLog();

    $("#btnSearchSongsByArtist").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletSongs",
            data: "action=chercherParAuteur&auteur=" + $("#inputSongsSearch").val(),
            success: function(msg) {
                if (msg !== "false")
                    refreshListSongs(JSON.parse(msg));
                else
                    alert("Auteur introuvable");
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

    $.ajax({
        type: "POST",
        url: "ServletSongs",
        data: "action=getPages",
        success: function(msg) {
            if (msg !== "false") {
                var jsonPages = JSON.parse(msg);
                for (var i = 0; i < jsonPages.length; i++) {
                    var obj = jsonPages[i];
                    var nb = parseInt(obj.nb);
                    var current = parseInt(obj.current);
                    //if (current === 0) $("#liPrevBtn").addClass("disabled")
                    //if (current === nb) $("#liNextBtn").addClass("disabled")
                }
            }
        }
    });
});