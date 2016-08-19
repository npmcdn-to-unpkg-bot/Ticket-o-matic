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
	<header>
		<nav class="navbar navbar-inverse navbar-static-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar-collapse"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="home"> <img alt="Ticket-o-Matic"
						src="./assets/all.png"></a>
				</div>
				<div class="collapse navbar-collapse " id="navbar-collapse">
					<ul class="nav navbar-nav navbar-left ">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">Category<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#">Concert</a></li>
								<li><a href="#">Cinema</a></li>
								<li><a href="#">Festival</a></li>
							</ul></li>
						<li class="navbar-action-group visible-xs"><a href="#"
							aria-label="search"><span class="glyphicon glyphicon-search"></span>
								Search</a></li>
						<c:choose>
							<c:when test="${empty user}">
								<li class="navbar-action-group visible-xs"><a href="#"
									aria-label="login"><span class="glyphicon glyphicon-log-in"></span>
										Login</a></li>
							</c:when>
							<c:otherwise>
								<li class="navbar-action-group visible-xs"><a href="home?action=account_details"
									aria-label="account"><span class="glyphicon glyphicon-user"></span>
										Account</a></li>
							</c:otherwise>
						</c:choose>

						<li class="navbar-action-group visible-xs"><a href="#"
							aria-label="cart"><span
								class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
					</ul>
					<div class="navbar-action-group navbar-right hidden-xs">
						<button type="button" class="btn btn-default navbar-btn"
							aria-label="search">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						</button>
						<c:choose>
							<c:when test="${empty user}">
								<button type="button" class="btn btn-default navbar-btn"
									aria-label="login">
									<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
								</button>
							</c:when>
							<c:otherwise>
								<a href="home?action=account_details" class="btn btn-default"
									aria-label="account"><span class="glyphicon glyphicon-user"></span></a>
							</c:otherwise>
						</c:choose>
						<button type="button" class="btn btn-default navbar-btn"
							aria-label="cart">
							<span class="glyphicon glyphicon-shopping-cart"
								aria-hidden="true"></span>
						</button>
					</div>
				</div>
			</div>
		</nav>
		<div class="innerbar light-primary-color">
			<div id="innerbar-popup" class="hidden">
				<div class="container">
					<form id="inner-search-form" class="" method="post">
						<!--<input type="date" class=" form-control" aria-label="search" id="searchbydate" name="search">-->
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group" id="search-group">
									<div class="input-group">
										<input type="search" class="form-control" aria-label="search"
											id="search" name="search"
											placeholder="Search by Events Title"> <span
											class="input-group-btn">
											<button type="submit" class="btn btn-default">Search</button>
										</span>
									</div>
								</div>
								<div class="form-group hidden" id="searchbydate-group">
									<div class="input-group">
										<input type="date" class="form-control" aria-label="search"
											id="search" name="search" placeholder="Search"> <span
											class="input-group-btn">
											<button type="submit" class="btn btn-default">Search</button>
										</span>
									</div>
								</div>
								<div class="form-group search-inline hidden"
									id="searchbyprice-group">
									<div class="row">
										<div class="col-xs-4 col-sm-5">
											<input type="number" class="form-control"
												aria-label="lower bound" name="pricelower"
												placeholder="Upper Bound">
										</div>
										<div class="col-xs-4 col-sm-5">
											<input type="number" class="form-control"
												aria-label="upper bound" name="priceupper"
												placeholder="Lower Bound">
										</div>
										<div class="col-xs-4 col-sm-2">
											<button type="submit" class="btn btn-default">Search</button>
										</div>
									</div>
								</div>

							</div>
							<div class="col-xs-12">
								<label class="control-label"><span
									class="glyphicon glyphicon-filter"></span>Filter</label>
								<div>
									<label class="radio-inline"> <input type="radio"
										name="filters" id="filterbytitle" value="bytitle" checked />Title
									</label> <label class="radio-inline"> <input type="radio"
										name="filters" id="filterbyloc" value="byloc" />Location
									</label> <label class="radio-inline"> <input type="radio"
										name="filters" id="filterbydate" value="bydate" />Date
									</label> <label class="radio-inline"> <input type="radio"
										name="filters" id="filterbyprice" value="byprice" />Price
									</label>
								</div>
							</div>
						</div>
					</form>
					<form id="inner-login-form" class="form-inline text-center"
						method="post">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group">
									<input type="text" class="form-control" aria-label="Username"
										id="username" name="username" placeholder="Username">
								</div>
								<div class="form-group">
									<input type="password" class="form-control"
										aria-label="Password" id="password" name="password"
										placeholder="Password">
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-default btn-block">
										<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
										Login
									</button>
								</div>
								<div class="form-group">
									<a href="home?action=registration"
										class="btn btn-default btn-block">Sign up</a>
								</div>
							</div>
						</div>
					</form>
					<div id="inner-cart-div" class="inner-cart row text-center">
						<div class="col-xs-12">
							<a href="home?action=cart"><button type="button"
									class="btn btn-success">
									<span class="glyphicon glyphicon-shopping-cart"></span> Your
									cart
								</button></a> <span class="coins"> Coins: <strong>0.00</strong>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>

	<!-- Include Content -->
	<jsp:include page="${page}" />

	<div class="back-to-top-parent">
		<button type="button" name="back-to-top" id="back-to-top"
			class="btn btn-primary back-to-top">
			<span class="glyphicon glyphicon-chevron-up"></span> TOP
		</button>
	</div>
	<footer>
		<div class="container">
			<div class="row">
				<div class="col-xs-6">
					<a href="https://github.com/thoniorf/Ticket-o-matic"
						target="_blank">Developed with <span
						class="glyphicon glyphicon-heart"></span> by Antonio Fortino
					</a>
				</div>
				<div class="col-xs-6">
					<a class="pull-right" rel="license"
						href="http://creativecommons.org/licenses/by/4.0/"><img
						alt="Creative Commons License" style="border-width: 0"
						src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a>
				</div>
			</div>
		</div>
	</footer>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="./js/script.js"></script>
</body>

</html>
