<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>List all photos</title>
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
            <a class="nav-link" href="/view_page">View photo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="#">List all photos</a>
        </li>
    </ul>
</div>
<div class="container text-center">
    <h1 class="display-4">List all photos</h1>
    <h4><small>You can delete photos by selecting them and then clicking the delete button</small></h4><br/>
</div>
<div class="container text-center">

    <div class="jumbotron text-center">
        <c:if test="${photos_key_set ne null}">
            <form action="/delete" method="POST">

                <div class="row">
                    <div class="col-sm-3">
                    </div>
                    <div class="col">
                        <table align="center" class="table">
                            <tr>
                                <td></td>
                                <td>Photo ID</td>
                                <td>Preview</td>
                            </tr>
                            <c:forEach items="${photos_key_set}" var="p">
                                <tr>
                                    <td><input type="checkbox" id="${p}" name="photo_id" value="${p}"></td>
                                    <td><c:out value="${p}"/></td>
                                    <td><a href="/photo/${p}"><img src="/photo/${p}" class="img-thumbnail" style="width:60px;height:60px;"></a></td>
                                </tr>
                            </c:forEach>
                        </table>

                    </div>
                    <div class="col-sm-3">

                    </div>
                </div>
                <br/>
                <p align="center">
                    <button type="submit" class="btn btn-info" onclick="window.location='/list'">Delete</button>
                </p>
            </form>
        </c:if>
        <c:if test="${photos_key_set eq null}">
        <h1 class="display-4" align="center">
            No images uploaded
            </h1>
            </c:if>
    </div>
</div>

</body>
</html>
