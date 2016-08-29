/*
 * Innerbar animations and handlers
 */
var search_type = 'search';
function innerToggle(target, parent, selector, tohide) {
	$(parent + " " + tohide).each(function() {
		$(this).hide();
	});
	$("#inner-" + target + "-" + selector).show();
	$("#innerbar-popup").hide().removeClass("hidden").slideDown("slow");
	$("#inner-" + target + "-" + selector + " input:visible:first").focus();
}
var innerbar_timeout;
$(".navbar-action-group").on('click', "button,a", function() {
	var target = $(this).attr("aria-label");
	if (target !== "account" && target !== "cart") {
		innerToggle(target, "#innerbar-popup", "form", "form");
		$("#innerbar-popup #inner-cart-div").hide();
	} else if (target == "cart") {
		innerToggle(target, "#innerbar-popup", "div", "form");
	}
});
$("#innerbar-popup").on('focus', "input", function() {
	clearTimeout(innerbar_timeout);
});
$("#innerbar-popup").on('focusout', "form", function() {
		innerbar_timeout = setTimeout(function() {
			$("#innerbar-popup").slideUp();
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

	if ($("#back-to-top").offset().top + $("#back-to-top").height() >= $(
			"footer").offset().top - 10) {
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
		scrollTop : 0
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
/*
 * SERIALIZE FORM FUNCTION
 */
(function($) {
	$.fn.serializeFormJSON = function() {
		var disabled = this.find(':input:disabled').removeAttr('disabled');
		var o = {};
		var a = this.serializeArray();
		disabled.attr('disabled', 'disabled');
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
})(jQuery);

/*
 * SIGN UP REQUEST
 */
$("#registration-form").submit(function(e) {
	e.preventDefault();
	var frm = $(this).serializeFormJSON();
	$.ajax({
		url : "account?action=signup",
		type : "POST",
		dataType : "JSON",
		data : JSON.stringify(frm)
	}).done(function(data) {
		operation_alert(data, function() {
			window.location.href = "home";
		});
	}).fail(function(data, status, err) {
		alert("error: " + data + " status: " + status + " err: " + err);
	});
});
/*
 * SIGN IN REQUEST
 */
$("#inner-login-form").submit(function(e) {
	e.preventDefault();
	var frm = $(this).serializeFormJSON();
	$.ajax({
		url : "account?action=signin",
		type : "POST",
		dataType : "JSON",
		data : JSON.stringify(frm)
	}).done(function(data) {
		operation_alert(data, function() {
			window.location.reload();
		});
		
	}).fail(function(data, status, err) {
		alert("error: " + data + " status: " + status + " err: " + err);
	});
});

/*
 *  ADD TICKET
 */
$("#tickets").on('click',"button",function(e){
	var id = parseInt($(this).attr("data-target"),10);
	var section = $(this).parent().parent().find("[data-name='section']").text().trim();
	var price = $(this).parent().parent().find("[data-name='price'] strong").text();
	price = price.substring(0, price.length - 1).trim();
	var name= $("[data-name='name']").text().trim();
	var date= $("[data-name='date']").text().trim();
	var location=$("[data-name='location']").text().trim();
	var jsonObj = {};
	var eventjson = {};
	eventjson.name = name;
	eventjson.location = location;
	var datejson = {};
	datejson.year = date.substring(0,4);
	datejson.month = date.substring(5,7);
	datejson.day = date.substring(8,10);
	eventjson.date = datejson;
	var categoryjson = {};
	categoryjson.name = section;
	var ticketjson = {};
	ticketjson.event = eventjson;
	ticketjson.category = categoryjson
	jsonObj.id = id;
	jsonObj.price = price;
	jsonObj.ticket = ticketjson;
	$.ajax({
		url : "cart?action=add",
		type : "POST",
		dataType : "JSON",
		data : JSON.stringify(jsonObj)
	}).done(function(data) {
		operation_alert(data, function() {
			window.location.reload();
		});
	}).fail(function(data, status, err) {
		alert("error: " + data + " status: " + status + " err: " + err);
	});
});
/*
 *  REMOVE TICKET
 */
$("#cart .ticket").on('click',"button",function(){
	var id = parseInt($(this).attr("data-target"),10);
	var name= $("[data-name='name'] strong").text().trim();
	var jsonObj = {};
	jsonObj.id = id;
	$.ajax({
		url : "cart?action=remove",
		type : "POST",
		dataType : "JSON",
		data : JSON.stringify(jsonObj)
	}).done(function(data) {
		operation_alert(data, function() {
			window.location.reload();
		});
	}).fail(function(data, status, err) {
		alert("error: " + data + " status: " + status + " err: " + err);
	});
});
$("#cart .cart-action").on('click',"button",function(){
	var action = $(this).attr("data-action");
	$.ajax({
		url : "cart?action="+action,
		type : "GET",
		dataType : "JSON"
	}).done(function(data) {
		if(data.callback === "SIGNIN") {
			operation_alert(data, function() {
				innerToggle("login", "#innerbar-popup", "form", "form");
				$("#innerbar-popup #inner-cart-div").hide();
			});
		} else {
			operation_alert(data, function() {
				window.location.reload();
			});
		}
		
	}).fail(function(data, status, err) {
		alert("error: " + data + " status: " + status + " err: " + err);
	});
});
$("#empty-cart-btn").on('click',function(){
	innerToggle("search", "#innerbar-popup", "form", "form");
	$("#innerbar-popup #inner-cart-div").hide();
});
