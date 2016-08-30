<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-xs-12">
		<div class="page-header">
			<h3>Your Events</h3>
		</div>
		<section id="eventlist">
			<c:choose>
				<c:when test="${empty events}">
					<div class="row">
						<div class="alert alert-warning">
							<p>We are sorry, you didn't create any event yet!</p>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="events-list">
						<c:forEach items="${events}" var="entry">
							<div class="row">
								<div class="col-xs-8 col-xs-offset-2 col-sm-2 col-sm-offset-0">
									<img src="${entry.value.image}" alt="${entry.value.name} image"
										class="img-responsive" />
								</div>
								<div class="col-xs-12 col-sm-10">
									<p>
										<strong>Name :</strong> ${entry.value.name}
									</p>
									<p>
										<strong>Date :</strong> ${entry.value.date}
									</p>
									<p>
										<strong>Location :</strong> ${entry.value.location}
									</p>
									<p>
										<strong>Suspended :</strong> ${entry.value.suspended}
									</p>
									<p>
										<button data-target="${entry.key}" type="button"
											class="btn btn-default">
											<span class="glyphicon glyphicon-cog"></span> Modify
										</button>
									</p>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-sm-offset-3">
					<a href="home?action=create" class="btn btn-success btn-block"> <span
						class="glyphicon glyphicon-plus"></span> New Event
					</a>
				</div>
			</div>
		</section>
	</div>
</div>
