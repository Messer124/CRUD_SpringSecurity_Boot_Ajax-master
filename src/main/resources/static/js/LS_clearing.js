$(() => {
    $("#logout_btn").on('click', (e) => {
        localStorage.clear();
    });
})