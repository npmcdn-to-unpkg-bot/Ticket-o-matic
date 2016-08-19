<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="row">
    <div class="col-xs-12">
        <div class="page-header">
            <h3>Account Details</h3>
        </div>
        <div class="row">
            <label for="account-details-form" class="col-xs-12 col-sm-3 col-sm-offset-1">
                <h4>Your Details</h4></label>
        </div>

        <form id="account-details-form" class="form-horizontal" action="" method="post">
            <div class="form-group">
                <label for="username" class="col-xs-12 col-sm-2 control-label">Username:</label>
                <div class="col-xs-12 col-sm-6">
                    <p id="username" class="form-control-static">
                        Your Username
                    </p>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-xs-12 col-sm-2 control-label">Name:</label>
                <div class="col-xs-12 col-sm-6">
                    <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                </div>
            </div>
            <div class="form-group">
                <label for="surname" class="col-xs-12 col-sm-2 control-label">Surname:</label>
                <div class="col-xs-12 col-sm-6">
                    <input type="text" class="form-control" id="surname" name="surname" placeholder="Surname">
                </div>
            </div>

            <div class="form-group">
                <label for="email" class="col-xs-12 col-sm-2  control-label">Email:</label>
                <div class="col-xs-12 col-sm-6">
                    <input type="text" class="form-control" id="email" name="email" placeholder="Email">
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-xs-12 col-sm-2  control-label">Password:</label>
                <div class="col-xs-12 col-sm-6">
                    <input type="text" class="form-control" id="password" name="password" placeholder="Password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-8 col-sm-4 col-sm-offset-2">
                    <button type="submit" class="btn btn-success btn-block">Update</button>
                </div>
                <div class="col-xs-4 col-sm-2">
                    <button type="reset" class="btn btn-default"> Cancel </button>
                </div>
            </div>
        </form>
    </div>
</div>