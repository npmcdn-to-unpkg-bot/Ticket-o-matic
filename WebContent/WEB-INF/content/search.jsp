<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content container">
	<section id="search">
		<div class="row">
			<div class="col-xs-12">
				<div class="page-header">
					<h1>Results</h1>
				</div>
			</div>
		</div>
		<c:choose>
			<c:when test="${empty results}">
				<p>
					<strong>Ops !</strong>
					There aren't results for your search terms.
					<button id="empty-result-btn" class="btn btn-link">Try again !</button>
				</p>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="events-list col-xs-12">
						<c:forEach items="${results}" var="entry">
							<div class="row">
								<div class="col-xs-6 col-xs-offset-3 col-sm-2 col-sm-offset-0">
									<a href="home?action=event&e=${entry.value.id}" class="img-responsive">
										<img src="${entry.value.image }" alt="${entry.value.name } image" class="img-responsive"></a>
									</div>
									<div class="col-xs-12 col-sm-10">
										<p>
											<strong>${entry.value.name }</strong>
										</p>
										<p><strong>Date:</strong> ${entry.value.date }</p>
										<p><strong>Location:</strong> ${entry.value.location }</p>
										<p><strong>Category:</strong> ${entry.value.category.name }</p>
										<p><a href="home?action=event&e=${entry.value.id}" class="btn btn-default">View</a></p>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="row">
					<div class="col-xs-12 col-sm-6 col-sm-offset-3">
						<button id="more-result-btn" data-limit="10" data-offset="${results.size()}"class="btn btn-primary btn-block">Get More Results</button>
					</div>
					</div>
				</c:otherwise>
			</c:choose>
		</section>
	</div>
