<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content container">
    <section id="event" class="event">
        <div class="row">
            <div class="col-xs-12">
                <div class="page-header">
                    <h3 data-name="name">${event.name }</h3>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-sm-3">
                <a href="#" class="thumbnail">
                    <img src="assets/Image_placeholder.png" alt="event image" class="img-responsive">
                </a>
            </div>
            <div class="col-xs-12 col-sm-9">
                <div class="row">
                    <div id="event-date" class="col-xs-12">
                        <h5><span class="glyphicon glyphicon-calendar"></span> Date:</h5>
                        <p data-name="date">
                            ${event.date }
                        </p>
                    </div>
                    <div id="event-organizer" class="col-xs-12">
                        <h5><span class="glyphicon glyphicon-user"></span> Organizer:</h5>
                        <p>
                            thoniorf
                        </p>
                    </div>
                    <div id="event-category" class="col-xs-12">
                        <h5><span class="glyphicon glyphicon-list"></span> Category:</h5>
                        <p>
                            Category
                        </p>
                    </div>
                    <div id="event-description" class="col-xs-12">
                        <h5><span class="glyphicon glyphicon-bold"></span> Description:</h5>
                        <p>
                        ${event.description }

                        </p>
                    </div>
                    <div id="event-location" class="col-xs-12">
                        <h5><span class="glyphicon glyphicon-map-marker"></span> Location:</h5>
                        <p data-name="location">
                            ${event.location }
                        </p>
                    </div>
                    <div class="col-xs-12">
                        <h5><span class="glyphicon glyphicon-star"></span> Add to Wishlist:</h5>
                        <p>
                            <form id="add-eventwishlist-form" class="form-inline">
                                <div class="input-group">
                                <select class="form-control" name="id">
                                    <option value="1">Lista dei Desideri</option>
                                    <option value="8">Nuovi Eventi</option>
                                </select>
                                  <span class="input-group-btn">
                                  <button type="submit" class="btn btn-default">
                                    Add
                                  </button></span>

                                </div>

                            </form>
                        </p>
                    </div>
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
                                            <th>
                                                Section
                                            </th>
                                            <th colspan="2">
                                                Price
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${sells}" var="entry">
                                        <tr>
                                            <td data-name="section">
                                                ${entry.value.ticket.category.name}
                                            </td>
                                            <td data-name="price">
                                                <strong>${entry.value.price} €</strong>
                                            </td>
                                            <td>
                                            <button data-target="${entry.value.id}"class="btn btn-primary btn-block"><span class="glyphicon glyphicon-shopping-cart"></span><span class="hidden-xs">Buy Now</span></button>
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
