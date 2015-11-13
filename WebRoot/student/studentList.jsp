<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息管理</title>
<script src="/js/jquery-1.4.4.min.js" type="text/javascript" ></script>

<style type="text/css">
.td{border:1px solid #e4e4e4;}
</style>

</head>
<body>
<h1>学生信息管理</h1>
<a href="${basePath}/index.jsp">返回主页面</a><br>
<a href="${basePath}/student/AddToStudent">添加学生基本信息</a>

<form action="${basePath}/student/QueryStudent" method="post">
请输入关键字：<input type="text" name="Keyword" />
<input type="submit" value="查询">
</form>

<div >
	<table style="border:1px solid #e4e4e4;border-collapse:collapse;width:100%;margin-bottom:4px;background:#fff" >
		<tbody>
			<tr style="border:1px solid #e4e4e4">
				<th width="2%" class="td" >id</th>
				<th width="8%"class="td">姓名</th>
				<th width="5%"class="td">性别</th>
				<th width="5%"class="td">年龄</th>
				<th width="10%"class="td">联系电话</th>
				<th width="20%"class="td">居住地址</th>
				<th width="10%"class="td">操作</th>
			</tr>
			<!--注意：如果有分页，返回的对象必须要有list集合；var="student"可以改变，但其随后跟的属性必须是数据库的字段  -->
			<c:forEach items="${reslut.list}" var="reslut">
			<tr style="border:1px solid #e4e4e4">
				<td class="td" style="text-align:center;"><c:out value="${reslut._id}" default=""/></td>
				<td class="td" style="text-align:center;">${reslut.name}</td>
				<td class="td" style="text-align:center;">${reslut.sxe}</td>
				<td class="td" style="text-align:center;">${reslut.age}</td>
				<td class="td" style="text-align:center;">${reslut.tel}</td>
				<td class="td" style="text-align:center;">${reslut.address}</td>
				
				<!-- 操作 -->
				<td class="td" style="text-align:center;">
					&nbsp;&nbsp;<a href="${basePath}/student/QueryStudentById/${reslut._id}">修改</a>
					&nbsp;&nbsp;<a href="${basePath}/student/DeleteStudent/${reslut._id}">删除</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<a>当前页：${reslut.pageNumber}</a>
	<a>总页数：${reslut.totalPage}</a>
	<a>总记录条数：${reslut.totalRow}</a>

</div>

</body>
</html>