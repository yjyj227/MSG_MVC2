<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="beans.MemberDAO"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8" />
<!DOCTYPE html>
<html>
<head>

<title>Movie so good</title>

</head>
<body>
<c:set var="updatecheck" value="${requestScope.updatecheck}" />
<c:choose>
	<c:when test="${updatecheck==true}">
	<script>
		alert("회원정보 수정이 완료되었습니다.");
		location.href="MemberUpdate.do";
	</script>
	</c:when>
	<c:otherwise>
	<script>
		alert("다시 시도해주십시오.");
		history.back();
	</script>
	</c:otherwise>
</c:choose>
</body>
</html>