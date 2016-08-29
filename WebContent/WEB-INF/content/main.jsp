<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
    <section id="top">
        <div class="page-header">
            <h1>Top Events <small><a href="#">Show more ...</a></small></h1>
        </div>
        <div class="row">
        <c:forEach items="${tops}" var="entry">
            <div class="col-sm-6 col-md-3">
                <div class="thumbnail">
                    <img src="./assets/Image_placeholder.png" alt="${entry.value.name } image">
                    <div class="caption">
                        <h3>${entry.value.name }</h3>
                        <p><a href="home?action=event&e=${entry.key }" class="btn btn-primary" role="button">View</a></p>
                    </div>
                </div>
            </div>
		</c:forEach>
        </div>
    </section>
    <section id="recent">
        <div class="page-header">
            <h1>Recents Events <small><a href="#">Show more ...</a></small></h1>
        </div>
        <div class="row">
            <div class="col-sm-6 col-md-3">
                <div class="thumbnail">
                    <img src="./assets/Image_placeholder.png" alt="Event Title image">
                    <div class="caption">
                        <h3>Event name</h3>
                        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-3">
                <div class="thumbnail">
                    <img src="./assets/Image_placeholder.png" alt="Event Title image">
                    <div class="caption">
                        <h3>Event name</h3>
                        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-3">
                <div class="thumbnail">
                    <img src="./assets/Image_placeholder.png" alt="Event Title image">
                    <div class="caption">
                        <h3>Event name</h3>
                        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-3">
                <div class="thumbnail">
                    <img src="./assets/Image_placeholder.png" alt="Event Title image">
                    <div class="caption">
                        <h3>Event name</h3>
                        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
