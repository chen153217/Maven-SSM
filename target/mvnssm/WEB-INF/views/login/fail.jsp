<%--
  Created by IntelliJ IDEA.
  User: chenhansen
  Date: 2017/8/10
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>创建存在问题</title>
    <style type="text/css">
        body{
            margin: 0;
            padding: 0;
        }
        div{
            position: absolute;
            top: 50%;
            left: 50%;
            margin-top: -200px;
            margin-left: -200px;
            width: 400px;
            height: 400px;
           text-align: center;
        }
    </style>
</head>
<body>
<div>
    <%=(request.getAttribute("errorMsg")!=null)?request.getAttribute("errorMsg"):""%>
    <%=(request.getAttribute("errorUserLength")!=null)?request.getAttribute("errorUserLength"):""%>
    <%=(request.getAttribute("userExist")!=null)?request.getAttribute("userExist"):""%><br>
     <a href="<%=request.getContextPath()%>/site/resite">回到主页</a>
</div>
<%--<jsp:forward page="site.jsp"></jsp:forward>--%>
</body>
<script>

</script>
</html>
