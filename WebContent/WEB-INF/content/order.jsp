<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-xs-12">
		<div class="page-header">
			<h3>Your Orders</h3>
		</div>
		<section id="orders">
			<c:choose>
				<c:when test="${empty orders}">
					<div class="alert alert-warning">
						<p>We are sorry, you didn't complete any order yet,buy something!</p>
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach items="${orders}" var="entry">
						<div class="panel panel-primary panel-table">
							<div class="panel-heading">
								<div class="row">
									<div class="col col-xs-6">
										<h2 class="panel-title">
											<span class="glyphicon glyphicon-star"></span>
											<strong>Order N°:</strong>
											${entry.value.id}
											<strong>Date:</strong>
											${entry.value.date }
											<strong>Cost:</strong>${entry.value.total }€
										</h2>
									</div>
									<div class="col col-xs-6 text-right">
										<button type="button" class="btn btn-info collapsed" data-toggle="collapse" data-target="#${entry.value.id}">
											<i class="glyphicon glyphicon-chevron-down"></i>
											Show Items
										</button>
									</div>
								</div>
							</div>
							<div class="panel-collapse collapse" id="${entry.value.id}">
								<div class="panel-body">
									<div class="table-container">
										<table class="table table-responsive">
											<tbody></tbody>

										</table>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</section>
	</div>
</div>
