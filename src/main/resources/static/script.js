$(document).ready(function() {
    $(".drop").mouseover(function() {
        $(this).find(".dropdown").stop(true, true).slideDown(300);
    });

    $(".drop").mouseleave(function() {
        $(this).find(".dropdown").stop(true, true).slideUp(300);
    });
});
