<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content container">
	<section id="cart">
		<div class="row">
			<div class="col-xs-12">
				<div class="page-header">
					<h1>Your Cart</h1>
				</div>
			</div>
		</div>
		<c:choose>
			<c:when test="${empty cart.tickets }">
			<p>
			<strong>Ops !</strong> Your cart is empty. <button id="empty-cart-btn" class="btn btn-link">Check new events' tickets</button>
			</p>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="col-xs-12">
						<c:forEach items="${cart.tickets}" var="entry">
							<div id="item-cart" class="row">
								<div class="col-xs-12">
									<div class="ticket row">
										<div class="col-sm-3">
											<a href="#" class="thumbnail"> <img src="" alt="">
											</a>
										</div>
										<div class="col-sm-5">
											<p>
												<strong>${entry.value.ticket.event.name }</strong>
											</p>
											<p>${entry.value.ticket.event.date }</p>
											<p>${entry.value.ticket.event.location }</p>

										</div>
										<div class="col-sm-4">
											<div class="row">
												<div class="col-xs-12">
													<p class="lead">${entry.value.price }€</p>
												</div>
											</div>

											<div class="row">
												<div class="col-xs-12">
													<p>
														<button data-target="${entry.key }" type="button"
															class="btn btn-danger">Remove</button>
													</p>

												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						<div class="row">
							<div class="col-sm-4 col-sm-offset-8">
								<p class="lead">
									<strong>Total ${cart.total} €</strong>
								</p>
							</div>
						</div>
					</div>
				</div>
				<div class="cart-action row">
					<div class="col-xs-12 col-sm-6">
						<button data-action="checkout" type="button" class="btn btn-success btn-block">Check
							out</button>
					</div>
					<div class="col-xs-12 col-sm-4">
						<button data-action="clear" type="button" class="btn btn-warning btn-block">Clear
							Cart</button>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</section>
</div>