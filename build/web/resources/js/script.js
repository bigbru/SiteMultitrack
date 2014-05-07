$(function() {

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

    function refreshListUSers(json) {
        $("#tableListUsers").html("");
        for (var i = 0; i < json.length; i++) {
            var obj = json[i];
            $("#tableListUsers").append('\
                    <tr id="edit' + obj.login + 'Main">\
                        <td class="tdLogin">' + obj.login + '</td>\
                        <td class="tdLName">' + obj.lastname + '</td>\
                        <td class="tdFName">' + obj.firstname + '</td>\\n\
                        <td class="tdFName">' + obj.cp + '</td>\\n\
                        <td class="tdFName">' + obj.ville + '</td>\
                        <td class="tdUtils">\
                            <button type="button" class="btn btn-info adrBtn" id="' + obj.idville + '"><span class="glyphicon glyphicon-home"></span></button>\
                            <button type="button" class="btn btn-success editBtn" id="edit' + obj.login + '"><span class="glyphicon glyphicon-edit"></span></button>\
                            <button type="button" class="deleteBtn btn btn-danger" id="' + obj.login + '"><span class="glyphicon glyphicon-remove"></span></button>\
                        </td>\
                    </tr>\
                    <tr id="edit' + obj.login + 'Tr" hidden>\
                        <form class "form-horizontal" role="form">\
                            <td>' + obj.login + '</td>\
                            <td><input type="nom" id="editNom' + obj.login + '" placeholder="' + obj.lastname + '" name="nom"></td>\
                            <td><input type="prenom" id="editPrenom' + obj.login + '" placeholder="' + obj.firstname + '" name="prenom"></td>\
                            <td><button type="button" id="' + obj.login + '" class="editBtnForm btn btn-info"><span class="glyphicon glyphicon-check"></span> Modifier</button></td>\
                        </form>\
                    </tr>');
        }

        $.ajax({
            type: "POST",
            url: "ServletUsers",
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
    }

    $.ajax({
        type: "POST",
        url: "ServletUsers",
        data: "action=testForLogin",
        success: function(msg) {
            if (msg !== "false") {
                logOutDisplay(false);
                refreshListUSers(JSON.parse(msg));
            }
        }
    });

    $("#submitLoginFormBtn").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=loginUtilisateur&loginSession=" + $("#loginSession").val() + "&passwordSession=" + $("#passwordSession").val(),
            success: function(msg) {
                if (msg !== "false") {
                    alert("hey");
                    logOutDisplay(false);
                    refreshListUSers(JSON.parse(msg));
                }
                else
                    alert("login invalide");
            }
        });
    });
    $("#logOutBtn").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=deconnexion",
            success: function(msg) {
                logOutDisplay(true);
            }
        });
    });
    $("#btnSearchLogin").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=chercherParLogin&login=" + $("#inputLoginSearch").val(),
            success: function(msg) {
                if (msg !== "false")
                    refreshListUSers(JSON.parse(msg));
                else
                    alert("login invalide");
            }
        });
    });
    $("#btnGenerateUser").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=testUsers&nUsers=" + $("#inputNbUsers").val(),
            success: function(msg) {
                if (msg !== "false") {
                    refreshListUSers(JSON.parse(msg));
                    $("#inputNbUsers").val("");
                } else
                    alert("nombre invalide");
            }
        });
    });
    $("#btnAddUser").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=creerUnUtilisateur&nom=" + $("#inputNom").val() + "&prenom=" + $("#inputPrenom").val() + "&login=" + $("#inputLogin").val() + "&cp=" + $("#inputCp").val() + "&ville=" + $("#inputVille").val(),
            success: function(msg) {
                if (msg !== "false") {
                    refreshListUSers(JSON.parse(msg));
                }
                else
                    alert("Erreur dans l'inscription, ce login existe.");
            }
        });
    });
    $("#btnPrevPage").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=prevPage",
            success: function(msg) {
                refreshListUSers(JSON.parse(msg));
            }
        });
    });
    $("#btnNextPage").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=nextPage",
            success: function(msg) {
                if (msg !== "false")
                    refreshListUSers(JSON.parse(msg));
            }
        });
    });
    $("#btnRefreshList").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=refreshList",
            success: function(msg) {
                refreshListUSers(JSON.parse(msg));
            }
        });
    });
    $(document).on('click', '.deleteBtn', function() {
        var id = $(this).attr('id');
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=deleteUser&login=" + id,
            success: function(msg) {
                refreshListUSers(JSON.parse(msg));
            }
        });
    });
    $(document).on('click', '.editBtnForm', function() {
        var id = $(this).attr('id');
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=editUser&login=" + id + "&nom=" + $("#editNom" + id).val() + "&prenom=" + $("#editPrenom" + id).val(),
            success: function(msg) {
                refreshListUSers(JSON.parse(msg));
            }
        });
    });
    $("#btnCreateNewUser").click(function() {
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=signUpUser&nom=" + $("#signUpNom").val() + "&prenom=" + $("#signUpPrenom").val() + "&login=" + $("#signUpLogin").val() + "&pass=" + $("#signUpPass").val() + "&cp=" + $("#signUpCp").val() + "&ville=" + $("#signUpVille").val(),
            success: function(msg) {
                if (msg !== "false") {
                    logOutDisplay(false);
                    refreshListUSers(JSON.parse(msg));
                }
                else
                    alert("Erreur dans l'inscription, ce login existe.");
            }
        });
    });

    $(document).on('click', '.linkPage', function() {
        var id = $(this).attr('id');
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=goPage&id=" + id,
            success: function(msg) {
                refreshListUSers(JSON.parse(msg));
            }
        });
    });

    $(document).on('click', '.adrBtn', function() {
        var id = $(this).attr('id');
        $.ajax({
            type: "POST",
            url: "ServletUsers",
            data: "action=listVille&ville=" + id,
            success: function(msg) {
                refreshListUSers(JSON.parse(msg));
            }
        });
    });

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

    $(document).on('click', '.editBtn', function() {
        var id = $(this).attr('id');
        $("#" + id + "Main").hide();
        $("#" + id + "Tr").show();
    });
});