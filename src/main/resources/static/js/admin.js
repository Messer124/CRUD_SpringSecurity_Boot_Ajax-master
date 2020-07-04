let allRoles;
let allUsers;
$(() => {
    getAllRoles();
    getAllUsers();


    $('[href="#admin-profile"]').on('show.bs.tab', (e) => {
        getAllUsers();
    })

    $("#editUserModal").on('show.bs.modal', (e) => {
        let userId = $(e.relatedTarget).data("user-id");

        $.ajax({
            url: "/rest/getUserById/" + userId,
            type: "GET",
            dataType: "json"
        }).done((msg) => {

            let user = JSON.parse(JSON.stringify(msg));

            $("#idInputEdit").empty().val(user.id);
            $("#usernameInputEdit").empty().val(user.username);
            $("#emailInputEdit").empty().val(user.email);
            $("#passwordInputEdit").empty();
            $("#rolesInputEdit").empty();
            $.each(allRoles, (i, role) => {
                $("#rolesInputEdit").append(
                    $("<option>").text(role.name)
                );
            });

            $("#buttonEditSubmit").on('click', (e) => {
                e.preventDefault();

                let editUser = {
                    id: $("#idInputEdit").val(),
                    username: $("#usernameInputEdit").val(),
                    email: $("#emailInputEdit").val(),
                    password: $("#passwordInputEdit").val(),
                    roles: $("#rolesInputEdit").val()
                }

                $.ajax({
                    url: "/rest/updateUser",
                    type: "PUT",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(editUser)
                }).done((msgUpdate) => {
                    $.ajax({
                        url: "/rest/getCurrentUser",
                        type: "GET",
                        dataType: "json"
                    }).done((currentUser) => {
                        if (currentUser.username === user.username) {
                            if (currentUser.roles.length === 1) {
                                if (currentUser.roles[0]['name'] !== 'ROLE_ADMIN') {
                                    $("#v-pills-admin-tab").css('display', 'none');
                                }
                                if (currentUser.roles[0]['name'] !== 'ROLE_USER') {
                                    $("#v-pills-user-tab").css('display', 'none');
                                }
                            } else if (currentUser.roles.length === 2) {
                                $("#v-pills-admin-tab").css('display', 'block');
                                $("#v-pills-user-tab").css('display', 'block');
                            }
                            $("#editUserModal").modal('hide');
                            getAllUsers();
                        } else {
                            $("#editUserModal").modal('hide');
                            getAllUsers();
                        }
                    })
                })
            })
        });
    });


    $("#deleteUserModal").on('show.bs.modal', (e) => {
        let userId = $(e.relatedTarget).data("user-id");

        $.ajax({
            url: "/rest/getUserById/" + userId,
            type: "GET",
            dataType: "json"
        }).done((msg) => {
            let user = JSON.parse(JSON.stringify(msg));

            $("#idInputDelete").empty().val(user.id);
            $("#usernameInputDelete").empty().val(user.username);
            $("#emailInputDelete").empty().val(user.email);
            $("#rolesInputDelete").empty();
            $.each(user.roles, (i, role) => {
                $("#rolesInputDelete").append(
                    $("<option>").text(role.name)
                )
            });

            $("#buttonDeleteSubmit").on('click', (e) => {
                e.preventDefault();

                $.ajax({
                    url: "/rest/deleteUserById/" + userId,
                    type: "DELETE",
                    dataType: "text"
                }).done((deleteMsg) => {
                    $("#deleteUserModal").modal('hide');
                    getAllUsers();
                })
            })
        });
    })

    $('[href="#newUser"]').on('show.bs.tab', (e) => {
        $(() => {
            $("#usernameInputNew").empty();
            $("#emailInputNew").empty();
            $("#passwordInputNew").empty();
            $("#rolesInputNew").empty();
            $.each(allRoles, (i, role) => {
                $("#rolesInputNew").append(
                    $("<option>").text(role.name)
                )
            });
        })
    })

    $("#buttonInputNewSubmit").on('click', (e) => {
        e.preventDefault();

        let newUser = {
            username: $("#usernameInputNew").val(),
            email: $("#emailInputNew").val(),
            password: $("#passwordInputNew").val(),
            roles: $("#rolesInputNew").val()
        }

        $.ajax({
            url: "/rest/createUser",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(newUser)
        }).done((msgSave) => {
            let fields = document.querySelectorAll("#new-form div input, #new-form div select");
            for (let i = 0; i < fields.length; i++) {
                if (fields[i] instanceof HTMLInputElement) {
                    fields[i].value = "";
                } else if (fields[i] instanceof HTMLSelectElement) {
                    fields[i].value = "ROLE_USER";
                }
            }
            getAllUsers();
        })
    });

})
