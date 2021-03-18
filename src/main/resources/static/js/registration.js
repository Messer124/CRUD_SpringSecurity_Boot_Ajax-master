$(() => {

    $("#buttonRegistrationSubmit").on('click', (e) => {
        e.preventDefault();

        let newUser = {
            username: $("#usernameInputNew").val(),
            email: $("#emailInputNew").val(),
            password: $("#passwordInputNew").val(),
            roles: null
        }

        $.ajax({
            url: "/rest/registerUser",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(newUser)
        }).done(() => {
            $("#regUserModal").modal('show');
            $("#regUserModal").on('hidden.bs.modal', (e) => {
                window.location = 'http://localhost:8080/login';
            });
        })
    });

});
