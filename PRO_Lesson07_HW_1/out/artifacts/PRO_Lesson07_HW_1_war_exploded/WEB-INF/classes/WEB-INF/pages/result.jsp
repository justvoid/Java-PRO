<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>Upload photos</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs nav-justified">
        <li class="nav-item">
            <a class="nav-link" href="/">Upload photo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="#">View photo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/list">List all photos</a>
        </li>
    </ul>
</div>
<div class="container text-center">
    <h1 class="display-4">View photo</h1>
    <h4><small>Enter the photo id into textbox below to view it</small></h4><br/>
</div>
<div class="container text-center">

    <div class="jumbotron text-center">
        <form action="/view_page" method="POST">
            <div class="row">
                <div class="col">
                </div>
                <div class="col">
                    <input type="text" name="photo_id" class="form-control" placeholder="Photo id" >
                </div>
                <div class="col" align="left">
                    <input type="submit" class="btn btn-outline-info " />
                </div>
            </div>
        </form>
    </div>
</div>
<c:if test="${photo_id ne null}">
    <div class="container text-center">

        <h4><small>Your photo id is: ${photo_id}</small></h4>
        <input type="submit" value="Delete Photo" class="btn btn-info" onclick="window.location='/delete/${photo_id}';" />
        <br/><br/><a href="/photo/${photo_id}"><img class="img-fluid" src="/photo/${photo_id}"/></a>

    </div>
</c:if>
</body>
</html>
