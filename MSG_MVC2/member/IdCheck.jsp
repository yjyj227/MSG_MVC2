<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="beans.MemberDAO"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Movie so good</title>
<!-- 부트스트랩 -->

<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon2.ico" />
<!-- Font Awesome icons (free version)-->
<script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
<!-- Google fonts-->
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
<link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="css/styles.css" rel="stylesheet" />
<script LANGUAGE="JavaScript" src="./script.js?ver=1"></script>
<style>
@import url(css/styles2.css);

table, .table, thead {/**/
	border-color: rgb(212, 99, 61);
	border-top: 2px solid rgb(212, 99, 61);
	border-bottom: 2px solid rgb(212, 99, 61);
}
.table-hover>tbody>tr:hover {/**/
	background-color: rgba(212, 99, 61, 0.2);
}
.pagination li a {/**/
	border: 1.5px solid rgb(212, 99, 61);
}
.nav>li>a:hover, .nav>li>a:focus, .nav-tabs>li.active>a,
.nav-tabs>li.active>a:hover, .nav-tabs>li.active>a:focus,
.btn {/**/
	background-color: rgb(212, 99, 61);
}
.form-control:focus {/**/
	border-color: rgb(212, 99, 61);
	box-shadow: 0 0 0 0.25rem rgba(212, 99, 61, 0.25);
}
     </style>
</head>
<body id="page-top" class="bg-dark">
       
		
		
			<br>
			<center>
			<br>
			<c:set var="check" value="${requestScope.check1}" />
			${param.mem_id}
			<c:choose>
			<c:when test="${check1==true}">
			는 이미 존재하는 아이디입니다.<p>
			</c:when>
			<c:otherwise>
			는 사용 가능한 아이디입니다.<p>
			</c:otherwise>
			</c:choose>
			<br>
			<button type="button" class="btn btn-default btn-sm" onclick="self.close()">닫기</button>
			</center>
			

		

<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="js/scripts.js"></script>
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
<!-- * *                               SB Forms JS                               * *-->
<!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
<script src="../js/bootstrap.min.js"></script>
</body>
</html>