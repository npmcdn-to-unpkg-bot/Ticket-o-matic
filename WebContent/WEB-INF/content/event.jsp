<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content container">
	<section id="event" class="event">
		<div class="row">
			<div class="col-xs-12">
				<div class="page-header">
					<h3 data-name="name">${event.name }</h3>
					<div class="alert alert-warning alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<strong>Warning!</strong> Every item in the cart is reserved for <strong>30 minutes</strong>. Hurry Up!
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-3">
				<a href="${event.image }"> <img data-name="image"
					src="${event.image }" alt="${event.name } image"
					class="img-responsive">
				</a>
			</div>
			<div class="col-xs-12 col-sm-9">
				<div class="row">
					<div id="event-date" class="col-xs-12">
						<h5>
							<span class="glyphicon glyphicon-calendar"></span> Date:
						</h5>
						<p data-name="date">${event.date }</p>
					</div>
					<div id="event-organizer" class="col-xs-12">
						<h5>
							<span class="glyphicon glyphicon-user"></span> Organizer:
						</h5>
						<p>${event.organizer.username }</p>
					</div>
					<div id="event-category" class="col-xs-12">
						<h5>
							<span class="glyphicon glyphicon-list"></span> Category:
						</h5>
						<p>${event.category.name }</p>
					</div>
					<div id="event-description" class="col-xs-12">
						<h5>
							<span class="glyphicon glyphicon-bold"></span> Description:
						</h5>
						<p>${event.description }</p>
					</div>
					<div id="event-location" class="col-xs-12">
						<h5>
							<span class="glyphicon glyphicon-map-marker"></span> Location:
						</h5>
						<p data-name="location">${event.location }</p>
					</div>
					<c:if test="${!empty user }">
						<div class="col-xs-12">
							<h5>
								<span class="glyphicon glyphicon-star"></span> Add to Wishlist:
							</h5>
							<p>
							<form id="add-eventwishlist-form" class="form-inline">
								<div class="input-group">
									<select class="form-control" name="id">
										<c:forEach items="${wishlists}" var="wishlist">
											<option value="${wishlist.key }">${wishlist.value.title}</option>
										</c:forEach>
									</select> <span class="input-group-btn">
										<button type="submit" class="btn btn-default">Add</button>
									</span>

								</div>

							</form>
							</p>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<p class="lead">Guests:</p>
				<hr>
			</div>
			<div class="col-xs-12">
				<div class="row">
					<c:forEach items="${event.guests}" var="entry">
						<div class="col-xs-6 col-sm-3">
							<div class="thumbnail">
								<img src="${entry.value.image }"
									alt="${entry.value.name } image">
								<div class="caption text-center">
									<h3>
										<a href="home?action=event&e=${entry.key }">${entry.value.name }</a>
									</h3>
								</div>
							</div>
						</div>

					</c:forEach>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<p class="lead">Tickets Available:</p>
				<hr>
			</div>
			<div class="col-xs-12">
				<div id="ticket-group" class="row">
					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-12">
								<table id="tickets" class="table ticket-table">
									<thead>
										<tr>
											<th>Section</th>
											<th>Quantity</th>
											<th colspan="2">Price</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${sells}" var="entry">
											<tr>
												<td data-name="section"
													data-target="${entry.value.ticket.category.id}">
													${entry.value.ticket.category.name}</td>
												<td>${entry.value.ticket.quantity} left</td>
												<td data-name="price"><strong>${entry.value.price}
														€</strong></td>
												<td>
													<button data-target="${event.id}"
														class="btn btn-primary btn-block">
														<span class="glyphicon glyphicon-shopping-cart"></span><span
															class="hidden-xs">Buy Now</span>
													</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
