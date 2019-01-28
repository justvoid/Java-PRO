<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Uploaded files</title>
    </head>
    <body>
        <div align="center">
            <h1>Your photo id is: ${photo_id}</h1>

            <input type="submit" value="Delete Photo" onclick="window.location='/delete/${photo_id}';" />
            <input type="submit" value="Upload New" onclick="window.location='../../../resources/WEB-INF';" />

            <br/><br/><img src="/photo/${photo_id}" />
        </div>
    </body>
</html>
