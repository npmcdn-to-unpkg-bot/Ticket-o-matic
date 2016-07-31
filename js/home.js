/*
 * Innerbar animations and handlers
 */
function innerToggle(target, parent, selector) {
    $(parent + " " + selector).each(function() {
        $(this).hide();
    });
    $("#inner-" + target + "-form").show();
    $("#innerbar-popup").hide().removeClass("hidden").slideToggle();
    $("#inner-" + target + "-form input:first").focus();
}
var innerbar_timeout;
$(".navbar-action-group").on('click', "button,a", function() {
    var target = $(this).attr("aria-label");
    if (target !== "cart") {
        innerToggle(target, "#innerbar-popup", "form");
    }
});
$("#innerbar-popup").on('focus', "input", function() {
    clearTimeout(innerbar_timeout);
});
$("#innerbar-popup").on('focusout', "form", function() {
    if (!$("#inner-search-form ul li a").is(":focus"))
        innerbar_timeout = setTimeout(function() {
            $("#innerbar-popup").slideToggle();
        }, 2000);

});
/*
 * Back-to-top button animations and handler
 */
function backToTopOffset() {
  var offset = $(document).height() - $("footer").offset().top;
  console.log(offset);
    if ($(this).scrollTop() > offset) {
        $("#back-to-top").fadeIn(duration);
    } else {
        $("#back-to-top").fadeOut(duration);
    }

    if ($("#back-to-top").offset().top + $("#back-to-top").height() >= $("footer").offset().top - 10) {
        $("#back-to-top").css('position', 'absolute');
    }
    if ($(document).scrollTop() + window.innerHeight < $("footer").offset().top)
        $("#back-to-top").css('position', 'fixed');
}
var offset = 300;
var duration = 300;
$(window).scroll(function() {
    backToTopOffset();

});
$("#back-to-top").on('click', function(e) {
  e.preventDefault();
    $("html,body").animate({
        scrollTop: 0
    }, duration);
});
