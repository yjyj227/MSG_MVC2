<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="beans.MemberDAO"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8" />
<c:set var="loginCheck" value="${requestScope.loginCheck}" />

<c:choose>
	<c:when test="${loginCheck==true}">
		<c:set var="idKey" value="${requestScope.mem_id}" scope="session" />
		<c:set var="pwKey" value="${requestScope.mem_passwd}" scope="session" />
		<c:set var="mem_nickname" value="${requestScope.mem_nickname}" scope="session" />
		<c:set var="mem_point" value="${requestScope.mem_point}" scope="session" />
		<c:set var="mem_grade" value="${requestScope.mem_grade}" scope="session" />
		<meta http-equiv="Refresh" content="0;url=Main.do">
	</c:when>
	<c:otherwise>
		<script>
	    	alert("아이디 또는 비밀번호가 일치하지 않습니다.");
	    </script>
	    <meta http-equiv="Refresh" content="0;url=Login.do">
    </c:otherwise>
</c:choose>