<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
<!--通过Post方式调用http://localhost:9000/MyJFinal/sayHello地址。最终，调用的是TestController.sayHello()方法。  -->
<form action="${basePath}/sayHello" method="post">

请输入您的名字：

<input type="text" name="userName" />
<!-- <input type="text" name="Keyword" /> -->

<input type="submit" value="确定"/>

</form> 

<a href="${basePath}/get">mongo测试</a><br>

<a href="${basePath}/student/GetStudent">学生管理</a>
</body>
</html>