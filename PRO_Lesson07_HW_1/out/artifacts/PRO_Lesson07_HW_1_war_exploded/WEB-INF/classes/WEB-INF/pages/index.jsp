<!DOCTYPE html>
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
            <a class="nav-link active" href="#">Upload photo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/view_page">View photo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/list">List all photos</a>
        </li>
    </ul>
</div>
<div class="container text-center">
    <h1 class="display-4">Upload a photo</h1>
    <h4><small>Upload your photo using the button below</small></h4><br/>
</div>
<div class="container  text-center">

    <div class="jumbotron text-center">
        <form action="/add_photo" enctype="multipart/form-data" method="POST">
            <input type="file" name="photo" class="btn btn-info">
            <input type="submit" class="btn btn-outline-info "/>

        </form>

    </div>

</div>
</body>
</html>
