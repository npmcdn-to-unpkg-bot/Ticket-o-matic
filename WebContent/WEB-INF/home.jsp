<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Ticket-o-matic</title>

<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">
<link rel="stylesheet" href="./css/theme.css">
<link rel="stylesheet" href="./css/palette.css">
<link rel="stylesheet" href="./css/custom.css">

</head>
<body>
	
	<!-- Include HEADER -->
	<jsp:include page="./content/header.jsp" />
	<!-- Include CONTENT -->
	<jsp:include page="${page}" />
	<div class="back-to-top-parent">
		<button type="button" name="back-to-top" id="back-to-top"
			class="btn btn-primary back-to-top">
			<span class="glyphicon glyphicon-chevron-up"></span> TOP
		</button>
	</div>
	<!-- Include FOOTER -->
	<jsp:include page="./content/footer.jsp" />
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="./js/script.js"></script>
</body>

</html>
