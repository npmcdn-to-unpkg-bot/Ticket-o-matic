<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content container">
    <section id="top">
        <h1>Top Events</h1>
        <div class="row">
        <c:forEach items="${tops}" var="entry">
            <div class="col-xs-6 col-md-3">
                <div class="thumbnail">
                    <img src="${entry.value.image }" alt="${entry.value.name } image">
                    <div class="caption text-center">
                        <h3><a href="home?action=event&e=${entry.key }">${entry.value.name }</a></h3>
                    </div>
                </div>
            </div>
		</c:forEach>
        </div>
    </section>
    <section id="recent">
        <h1>Recents Events</h1>
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
