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

$(function(){
	$(".dropdown-menu > li > a.trigger").on("click",function(e){
		var current=$(this).next();
		var grandparent=$(this).parent().parent();
		if($(this).hasClass('left-caret')||$(this).hasClass('right-caret'))
			$(this).toggleClass('right-caret left-caret');
		grandparent.find('.left-caret').not(this).toggleClass('right-caret left-caret');
		grandparent.find(".sub-menu:visible").not(current).hide();
		current.toggle();
		e.stopPropagation();
	});
	$(".dropdown-menu > li > a:not(.trigger)").on("click",function(){
		var root=$(this).closest('.dropdown');
		root.find('.left-caret').toggleClass('right-caret left-caret');
		root.find('.sub-menu:visible').hide();
	});
});

/*
 * Innerbar animations and handlers
 */
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
		$.ajax({
			url : "user?action=coins",
			type : "POST",
			dataType : "JSON",
		}).done(function(data){
			$(".coins strong").html(data.coins)
		});
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
$("#inner-search-form").on('submit',function(event){
	var value;
	//event.preventDefault();
	var filter = $(this).find("input[name=filters]:checked").val();
	if (filter === 'bydate') {
		value = "date="+$(this).find("input[type=date]").val();
	} else if (filter === 'byprice') {
		value = "lb="+$(this).find("input[name=pricelower]").val();
		value +="&up="+$(this).find("input[name=priceupper]").val();
	} else {
		value = "s="+$(this).find("input[type=search]").val();
	}
	$.ajax({
		url : "search?action="+filter+"&"+value,
		type : "POST",
		dataType : "JSON",
	}).done(function(data) {
		console.log(data);
	}).fail(function(data, status, err) {
		
	});
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
$("#add-guest").on('click', function() {
	var ticket = $(".guest").filter(":first").clone(true);
	$(ticket).find("button").removeAttr("disabled");
	$(ticket).appendTo($("#guest-group")).hide().slideDown("fast");
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
$("#create-event .guest").on('click', "button", function() {
	var ticket = $(this).closest(".guest");
	$(ticket).slideUp("fast", function() {
		$(ticket).remove();
	})
});


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
 * SAVE EVENT
 */
$("#event-details-form").on('submit',function(e){
	e.preventDefault();
	var frm = {};
	frm.name = $(this).find("input[name='title']").val();
	frm.location = $(this).find("input[name='location']").val();
	frm.description = $(this).find("textarea[name='description']").val();
	frm.image = $(this).find("input[name='image']").val();
	// date
	frm.date = {};
	frm.date.year= $(this).find("input[name='date']").val().substring(0,4);
	frm.date.month = $(this).find("input[name='date']").val().substring(5,7);
	frm.date.day = $(this).find("input[name='date']").val().substring(8,10);
	// category
	frm.category = {};
	frm.category.id = $(this).find("select[name='category']").val();
	var tickets = {};
	var id = 0;
	$(".ticket").each(function(index,val){
		var number = parseInt($(this).find("input[name='ticket-number']").val(),10);
		var price = $(this).find("input[name='ticket-price']").val();
		var category = {};
		category.id = $(this).find("select[name='ticket-category']").val();
		for(var i = 0; i < number ; i++){
			var ticket = {};
			ticket.price = price;
			ticket.category = category;
			tickets[id]=ticket;
			id++;
		}		
	});
	frm.ticket = tickets;
	var guests = {};
	$(".guest").each(function(index,val){
		var name = $(this).find("input[name='guest-name']").val().trim();
		var image = $(this).find("input[name='guest-image']").val().trim();
		var guest = {};
		guest.name = name;
		guest.image = image;
		guests[index] = guest;
	});
	frm.guests = guests;
	$.ajax({
		url : "event?action=create",
		type : "POST",
		dataType : "JSON",
		data : JSON.stringify(frm)
	}).done(function(data) {
		operation_alert(data, function() {});
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
	var image = $("[data-name='image']").attr("src").trim();
	var jsonObj = {};
	var eventjson = {};
	eventjson.id = id;
	eventjson.name = name;
	eventjson.location = location;
	eventjson.image = image;
	var datejson = {};
	datejson.year = date.substring(0,4);
	datejson.month = date.substring(5,7);
	datejson.day = date.substring(8,10);
	eventjson.date = datejson;
	var categoryjson = {};
	categoryjson.id = parseInt($(this).parent().parent().find("[data-name='section']").attr("data-target"),10);
	categoryjson.name = section;
	var ticketjson = {};
	ticketjson.event = eventjson;
	ticketjson.category = categoryjson
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
/*
 * 
 * 
 * 
 * 
 */
$("#account-details-form").submit(function(e){
	e.preventDefault();
	var frm = $(this).serializeFormJSON();
	$.ajax({
		url:"user?action=update",
		type:"POST",
		dataType:"JSON",
		data: JSON.stringify(frm)
	}).done(function(data){
		operation_alert(data);
    }).fail(function(data,status,err){
    	alert("error: " + data + " status: "+status+ " err: " +err);
    });
});
function appendWishlist(data,target){
	if(data.result === "FAIL"){
		$(target + " tbody").after("<p>"+data.reason+"</p>");
	} else {
	$.each(data,function(key,value){
		$(target + " tbody").after('<tr>'+
									'<div class="row">'+
										'<td class="col-md-10">'+
											'<div class="media">'+
												'<div class="media-left">'+
													'<a href="#" class="thumbnail"><img class="media-object" src="'+value.image+'" alt="" /></a>'+
												'</div>'+
												'<div class="media-body">'+
													'<h4 class="media-heading">'+value.name+'</h4>'+
													'<dl>'+
														'<dt>Date</dt>'+
														'<dd>'+value.date+'</dd>'+
														'<dt>Location</dt>'+
														'<dd>'+value.location+'</dd>'+
													'</dl>'+
												'</div>'+
											'</div>'+
										'</td>'+
									'</div>'+
								'</tr>');
	});
	}
}
function appendOrder(data,target){
	console.log(data);
	if(data.result === "FAIL"){
		$(target + " tbody").after("<p>"+data.reason+"</p>");
	} else {
	$.each(data,function(key,value){
		console.log(value);
		$(target + " tbody").append('<tr>'+
									'<div class="row">'+
										'<td class="col col-md-4 col-xs-12">'+
											'<div class="media">'+
												'<div class="media-left">'+
													'<a href="#" class="thumbnail"><img class="media-object" src="'+value.ticket.event.image+'" alt="" /></a>'+
												'</div>'+
												'<div class="media-body">'+
													'<h4 class="media-heading">'+value.ticket.event.name+'</h4>'+
													'<dl>'+
														'<dt>Date</dt>'+
														'<dd>'+value.ticket.event.date+'</dd>'+
														'<dt>Location</dt>'+
														'<dd>'+value.ticket.event.location+'</dd>'+
													'</dl>'+
												'</div>'+
											'</div>'+
										'</td>'+
										'<td class=" col col-md-4 col-xs-12">'+
										'<dl>'+
										'<dt>Ticket</dt>'+
										'<dd>'+value.ticket.id+'</dd>'+
										'<dt>Category</dt>'+
										'<dd>'+value.ticket.category.name+'</dd>'+
									'</dl>'+
									'</td>'+
									'<td class="col col-md-4 col-xs-12">'+
									'<dl>'+
									'<dt>Seller</dt>'+
									'<dd>'+value.seller.username+'</dd>'+
									'<dt>Price</dt>'+
									'<dd>'+value.price+'€</dd>'+
								'</dl>'+
								'</td>'+
									'</div>'+
								'</tr>');
	});
	}
}
/*
 * offset and limit for wishlist
 */
var offset = 0;
var limit = 5;

/*
 *  Show wishlists events
 */
$("#wishlists").on("click","button",function(){
	var target = $(this).attr("data-target");
	var expanded = $(this).attr("aria-expanded");
	if(typeof expanded == 'undefined'){
	$.ajax({
		url:"user?action=showevent",
		type:"POST",
		data:"{"+"id:"+target.substr(1,1)+"}",
		dataType:"JSON",
	}).done(function(data){
		appendWishlist(data,target);
    }).fail(function(data,status,err){
    	alert("error: " + data + " status: "+status+ " err: " +err);
    });
	}
	 /*&offset="+offset+"&limit="+limit*/
});
/*
 *  Show order items
 */
$("#orders").on("click","button",function(){
	var target = $(this).attr("data-target");
	var expanded = $(this).attr("aria-expanded");
	var item = {};
	item.id=parseInt(/\d+/.exec(target),10);
	if(typeof expanded == 'undefined'){
	$.ajax({
		url:"user?action=showitems",
		type:"POST",
		dataType:"JSON",
		data:JSON.stringify(item)
	}).done(function(data){
		appendOrder(data,target);
    }).fail(function(data,status,err){
    	alert("error: " + data + " status: "+status+ " err: " +err);
    });
	}
	 /*&offset="+offset+"&limit="+limit*/
});
$("#addwishlist").on('click',function(){
	$("#addwishlist-body").hide().removeClass("hidden").slideDown("slow",function(){});
	window.location.hash = "#addwishlist-form";
	$("#addwishlist-form #title").focus();
});
$("#addwishlist-form").submit(function(e){
	e.preventDefault();
	var frm = $(this).serializeFormJSON();
	$.ajax({
		url:"user?action=addwishlist",
		type:"POST",
		dataType:"JSON",
		data: JSON.stringify(frm)
	}).done(function(data){
		operation_alert(data,function(){window.location.reload()});
    }).fail(function(data,status,err){
    	alert("error: " + data + " status: "+status+ " err: " +err);
    });
});
$("#add-eventwishlist-form").on('submit',function(event){
	event.preventDefault();
	var url = window.location.href;
	var jsonObj = {};
	jsonObj.wishlist = parseInt($("#add-eventwishlist-form select option:selected").val(),10);
	jsonObj.event = parseInt(/e=(\d+)/.exec(url)[1],10);
	$.ajax({
		url:"user?action=addevent",
		type:"POST",
		dataType:"JSON",
		data: JSON.stringify(jsonObj)
	}).done(function(data){
		operation_alert(data,function(){});
    }).fail(function(data,status,err){
    	alert("error: " + data + " status: "+status+ " err: " +err);
    });
});
