/**
 * HANDLE ALERTs JS
 */
function operation_alert(result,callback){
$("#operation-alert").focus();
$("html,body").animate({
	scrollTop : 0
},"fast");
if(result.result === "SUCCESS"){
	$("#operation-alert-text").html("<strong>"+result.message+"</strong>");
	$("#operation-alert").removeClass("hidden").addClass("alert-success fade in");
    $("#operation-alert").fadeTo(1500, 1).slideUp(500, function() {
        $(this).removeClass("alert-success fade in");
        callback();
    });
} else {
	$("#operation-alert-text").html("<strong>"+result.reason+"</strong>");
	$("#operation-alert").removeClass("hidden").addClass("alert-danger fade in");
    $("#operation-alert").fadeTo(1500, 1).slideUp(500, function() {
        $(this).removeClass("alert-danger fade in");
        callback();
    });
}
}