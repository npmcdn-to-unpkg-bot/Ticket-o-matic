function innerToggle(target, parent, selector) {
    $(parent + " " + selector).each(function() {
        $(this).hide();
    });
    $("#inner-" + target + "-form").show();
    $("#innerbar-popup").hide().removeClass("hidden").slideToggle();
    $("#inner-" + target + "-form input:first").focus();
}
var innerbar_timeout;
var dropdown_timeout;
$(".navbar-action-group").on('click', "button,a", function() {
    var target = $(this).attr("aria-label");
    if (target !== "cart") {
        innerToggle(target, "#innerbar-popup", "form");
    }
});
$("#inner-search-form button[aria-label='filters']").on('click', function() {
console.log("innerbar stop");
    clearTimeout(innerbar_timeout);
    /*  dropdown_timeout = setTimeout(function() {
          $("#inner-search-form ul[aria-label='filterslist']").dropdown();
      }, 2500);*/
});
$("#inner-search-form ul[aria-label='filterslist']").on('click', "a", function(e) {
    e.preventDefault();
    $("#inner-search-form input:first").focus();
});
$("#innerbar-popup").on('focus', "input", function() {
    console.log("innerbar stop");
    clearTimeout(innerbar_timeout);
});
$("#innerbar-popup").on('focusout', "form", function() {
    console.log("innerbar start");
    if(!$("#inner-search-form ul li a").is(":focus"))
    innerbar_timeout = setTimeout(function() {
        $("#innerbar-popup").slideToggle();
    }, 3000);

});
