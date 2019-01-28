<!DOCTYPE html>
<html lang="en">
<head>
  <title>Your .zip archive</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
  
<div class="container">
  <h1 class="display-4">Your .zip archive is ready!</h1>
  <h4><small>Click the button below to download</small></h4><br/>       
</div>
<div class="container">
<div class="row">
<div class="col-6 ">
  <div class="jumbotron">
    <p><input type="submit" class="btn btn-info" value="Download Archive" onclick="window.location='/get-archive/${archive_hash}';" /></p><br/>
    <p><input type="submit" class="btn btn-outline-primary btn-block" value="Back to Main" onclick="window.location='/';" /></p>
               
  </div>
  </div>
  </div>
</div>
</body>
</html>
