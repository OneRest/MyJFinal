<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>mongodb数据测试</title>
</head>
<body>
<a href="${basePath}/student/AddToStudent">添加教师基本信息</a>

<form action="${basePath}/student/QueryStudent" method="post">
请输入关键字：<input type="text" name="keyword" />
<input type="submit" value="查询">
</form>

<div >
	<table >
		<tbody>
			<tr>
				<th width="4%">id</th>
				<th width="10%">标题</th>
				<th width="10%">描述</th>
				<th width="10%">作者</th>
				<th width="10%">URL</th>
				<th width="8%">标签</th>
				<th width="12%">价格</th>
				<th width="10%">操作</th>
			</tr>
			<!--注意：var="student"可以改变，但其随后跟的属性必须是数据库的字段  -->
			<c:forEach items="${reslut.list}" var="reslut">
			<tr>
				<td style="text-align:center;"><c:out value="${reslut._id}" default=""/></td>
				<td style="text-align:center;">${reslut.title}</td>
				<td style="text-align:center;">${reslut.description}</td>
				<td style="text-align:center;">${reslut.by}</td>
				<td style="text-align:center;">${reslut.url}</td>
				<td style="text-align:center;">${reslut.tags}</td>
				<td style="text-align:center;">${reslut.likes}</td>	
				
				<!-- 操作 -->
				<td class="td" style="text-align:center;">
					&nbsp;&nbsp;<a href="${basePath}/">修改</a>
					&nbsp;&nbsp;<a href="${basePath}/">删除</a>
				</td>		
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<p>当前第 ${reslut.pageNumber}页 ；  总共 : ${reslut.totalRow}条； 总共 : ${reslut.totalPage}页</p>
</div>
</body>
</html>