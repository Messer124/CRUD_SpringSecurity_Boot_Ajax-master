function getAllRoles() {
    $.ajax({
        url: "/rest/getAllRoles",
        type: "GET",
        dataType: "json"
    }).done((msg) => {
        allRoles = JSON.parse(JSON.stringify(msg));
    })
}

function getAllUsers() {
    $.ajax({
        url: "/rest/getAllUsers",
        type: "GET",
        dataType: "json"
    }).done((msg) => {
        allUsers = JSON.parse(JSON.stringify(msg));
        $("#all-users-table tbody").empty();
        $.each(allUsers, (i, user) => {

            $("#all-users-table tbody").append(
                $("<tr>").append(
                    $("<td>").text(user.id),
                    $("<td>").text(user.username),
                    $("<td>").text(user.email),
                    $("<td>").text(user.roles.length === 1 ? user.roles[0]['name'] : user.roles[0]['name'] + ', ' + user.roles[1]['name']),
                    $("<td>").append(`<button type='button' data-toggle='modal' class='btn-info btn' 
                                          data-target='#editUserModal' data-user-id=${user.id}>Edit</button> 
                                          <button type='button' data-toggle='modal' class='btn btn-danger' 
                                          data-target='#deleteUserModal' data-user-id=${user.id}>Delete</button>`)
                ));
        });
    });
}