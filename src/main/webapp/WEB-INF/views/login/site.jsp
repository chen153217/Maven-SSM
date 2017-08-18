<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>imeweb</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/my.css" type="text/css">
</head>
<body>
<div id="wrapper">
    <div id="header">
        <div id="title">
            <h1>上海美嘉林公司新建站点</h1>
        </div>
        <div id="nav">
            <ul>
                <li  class="site">
                   <label>创建站点ID</label>
                </li>
                <li  class="group">
                    <label>创建分组</label>
                </li>
                <li  class="usability">
                    <label>适用性设置</label>
                </li>
                <li  class="users">
                    <label>人员列表导入</label>
                </li>
                <li  class="container">
                    <label>内容库创建</label>
                </li>
                <li  class="workflow">
                    <label>工作流设置</label>
                </li>
            </ul>
        </div>
    </div>
    <div id="maincontent">
        <form action="<%=request.getContextPath()%>/site/site" method="POST" enctype="multipart/form-data" onsubmit="return isAvailable()">
            <div class="description">
                <h3>新建站点</h3>
            </div>
            <div class="upload">
                <span id="fileName"></span>
                <label>浏览文件...<input type="file" name="file" id="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="getFile()"></label>
            </div>
            <div class="submit">
                <input type="submit" value="提交">
            </div>
        </form>
    </div>
    <div id="footer">
        <p>说明:使用本界面用户将通过读取一系列相关excel表来实现自动配置站点信息
    </div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/my.js"></script>
</body>
</html>
