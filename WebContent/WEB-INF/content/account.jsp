<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content container-fluid">
    <div class="sidebar-float sidebar-color hidden-xs"></div>
    <div class="row">
        <section id="account-nav" class="col-xs-12 col-sm-4 sidebar sidebar-color">
            <div class="row">
                <div class="col-xs-12 col-sm-offset-4 col-sm-8 ">
                    <div class="page-header">
                        <h3>${user.name} ${user.surname} ${user.type }</h3>
                    </div>
                    <ul class="nav account-nav-list">
                        <li>
                            <a href="home?action=details"><span class="glyphicon glyphicon-user"></span> Account Details</a>
                        </li>
                        
						<c:if test="${user.type eq Organizer} ">
                        <li>
                            <a href="home?action=eventlist"><span class="glyphicon glyphicon-calendar"></span> Organizer Events</a>
                        </li>
                        </c:if>
                        <li>
                            <a href="home?action=order"><span class="glyphicon glyphicon-list-alt"></span> Orders</a>
                        </li>
                        <li>
                            <a href="home?action=wishlist"><span class="glyphicon glyphicon-star"></span> Wishlist</a>
                        </li>
                        <li>
                            <a href="account?action=logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </section>
        <section id="account-content" class="col-xs-12 col-sm-8">
       <jsp:include page="${account_content}"/>
        </section>
    </div>
</div>