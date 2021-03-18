$(document).ready(function(){

    $("#form").on('submit', function(event){
        event.preventDefault();
        let username = $('#username').val();
        let password = $('#password').val();
        $.post("http://localhost:8080/authenticate", {
            username:username, password: password
        }).done(function(msg, status, xhr){
            console.log(xhr);
            localStorage.setItem('Authorization', xhr.getResponseHeader('authorization'));
            window.location.href = "/main?Authorization=" + localStorage.getItem('Authorization');
        })
    })
});
