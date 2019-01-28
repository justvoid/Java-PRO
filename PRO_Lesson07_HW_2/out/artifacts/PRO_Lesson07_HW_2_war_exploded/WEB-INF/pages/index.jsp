<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
  
<div class="container">
  <h1 class="display-4">Online Archiver</h1>
  <h4><small>Select files to archive and then click submit to archive them</small></h4><br/>       
</div>
<div class="container">
<div class="row">
<div class="col-8 ">
  <div class="jumbotron">
    <form action="/upload_files" enctype="multipart/form-data" method="POST">
        <p class="font-weight-light">File#1: <input type="file" name="file" class="btn btn-info"></p>
        <p class="font-weight-light">File#2: <input type="file" name="file" class="btn btn-info"></p>
        <p class="font-weight-light">File#3: <input type="file" name="file" class="btn btn-info"></p>
        <p class="font-weight-light">File#4: <input type="file" name="file" class="btn btn-info"></p>
        <p class="font-weight-light">File#5: <input type="file" name="file" class="btn btn-info"></p>
        <input type="submit" class="btn btn-outline-primary btn-block"/>
    </form>        
  </div>
  </div>
  </div>
</div>
</body>
</html>
