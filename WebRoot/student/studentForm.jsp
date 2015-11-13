<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<p> <input type="hidden" name="st._id" value="${result._id}"></p>
<%-- <p>id <input type="text" name="st._id" value="${result._id}"></p> --%>
<p>姓名<input type="text" name="st.name" value="${result.name}">${studentNameMgs}</p>
<p>性别 <input type="text" name="st.sxe" value="${result.sxe}">${sexMgs}</p>
<p>年龄<input type="text" name="st.age" value="${result.age}">${ageMgsMgs}</p>
<p>联系电话 <input type="text" name="st.tel" value="${result.tel}">${phoneMgs}</p>
<p>居住地址 <input type="text" name="st.address" value="${result.address}">${addressMgs}</p>

 <input type="submit" name="" value="保存">
