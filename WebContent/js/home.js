/*
 * Innerbar animations and handlers
 */
var search_type = 'search';

function innerToggle(target, parent, selector,tohide) {
    $(parent + " " + tohide).each(function() {
        $(this).hide();
    });
    $("#inner-" + target + "-"+selector).show();
    $("#innerbar-popup").hide().removeClass("hidden").slideToggle();
    $("#inner-" + target + "-"+selector+" input:visible:first").focus();
}
var innerbar_timeout;
$(".navbar-action-group").on('click', "button,a", function() {
    var target = $(this).attr("aria-label");
    if (target !== "cart") {
        innerToggle(target, "#innerbar-popup", "form","form");
        $("#innerbar-popup #inner-cart-div").hide();
    } else {
      innerToggle(target,"#innerbar-popup","div","form");
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

function searchPlaceholder(text) {
    $("#search-group input").filter(":first").attr("placeholder", text);
    $("#search-group").hide().removeClass("hidden").fadeIn();
    $("#searchbydate-group").hide();
    $("#searchbyprice-group").hide();
}
$("#inner-search-form").on('click', "input[name=filters]", function() {
    var type = $(this).val();
    if (type === 'bydate') {
        $("#searchbydate-group").hide().removeClass("hidden").fadeIn();
        $("#searchbyprice-group").hide();
        $("#search-group").hide();
    } else if (type === 'byprice') {
        $("#searchbyprice-group").hide().removeClass("hidden").fadeIn();
        $("#searchbydate-group").hide();
        $("#search-group").hide();
    } else if (type === 'bytitle') {
        searchPlaceholder("Search by Events Title");
    } else if (type === 'byloc') {
        searchPlaceholder("Search by Events Location");
    }
});
/*
 * Back-to-top button animations and handler
 */
function backToTopOffset() {
    var offset = $(document).height() - $("footer").offset().top;
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
/*
 * ADD TICKET TO EVENT
 */
$("#add-ticket").on('click', function() {
    var ticket = $(".ticket").filter(":first").clone(true);
    $(ticket).find("button").removeAttr("disabled");
    $(ticket).appendTo($("#ticket-group")).hide().slideDown("fast");
});
/*
 * REMOVE TICKET FORM EVENT
 */
$("#create-event .ticket").on('click', "button", function() {
    var ticket = $(this).closest(".ticket");
    $(ticket).slideUp("fast", function() {
        $(ticket).remove();
    })
});
