<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息添加</title>
</head>
<body>
<h1>学生信息添加</h1>
<form action="${basePath}/student/AddStudent" method="post">

<%@ include file="/student/studentForm.jsp"%>

</form>
</body>
</html>