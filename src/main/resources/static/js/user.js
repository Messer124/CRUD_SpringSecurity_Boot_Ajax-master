$(() => {

    getCurrent();

    function getCurrent() {
        $.ajax({
            url: "/rest/getCurrentUser",
            type: "GET",
            dataType: "json"
        }).done((msg) => {
            let user = JSON.parse(JSON.stringify(msg));
            $("#current-user-table tbody").empty().append(
                $("<tr>").append(
                    $("<td>").text(user.id),
                    $("<td>").text(user.username),
                    $("<td>").text(user.email),
                    $("<td>").text( user.roles.length === 1 ? user.roles[0]['name'] : user.roles[0]['name']  + ', ' + user.roles[1]['name']),
                ));
        })
    }

    $('[href="#user-profile"]').on('show.bs.tab', (e) => {
        getCurrent();
    })
})