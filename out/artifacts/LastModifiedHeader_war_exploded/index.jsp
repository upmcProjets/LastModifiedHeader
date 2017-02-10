<%--
  Created by IntelliJ IDEA.
  User: Zahir
  Date: 08/02/2017
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <a href="/home" >homeServlet</a>
  <script>
    x = document.cookie;
    var urll = 'http://tracker.com/track?tid=' + encodeURI(document.cookie);

    document.write('<div>'+urll+'</div>');
  </script>
  </body>
</html>
