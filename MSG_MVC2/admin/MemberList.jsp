<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="beans.*, java.util.*, java.text.SimpleDateFormat"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="mem_id" value="${sessionScope.idKey}" />
<c:set var="mem_nickname" value="${sessionScope.mem_nickname}" />
<c:set var="mem_point" value="${sessionScope.mem_point}" />
<c:set var="mem_grade" value="${sessionScope.mem_grade}" />
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

<style>
@import url(css/styles2.css);
@import url(css/styles3.css);

thead {
	background-color:rgba(212, 99, 61, 0.25);
}
</style>
</head>
<c:if test="${mem_id!='admin'}">
	<script>
		history.back();
	</script>
</c:if>

<c:if test="${mem_id=='admin'}">
<body id="page-top" class="bg-dark">
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
            <div class="container">
                <a class="navbar-brand" href="Main.do"><img src="assets/img/msg-logo.png" alt="..." /></a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars ms-1"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
		            	<c:if test="${!empty mem_id}">
						<li class="nav-item"><a class="nav-link disabled" href="MyPage.do">
							<c:if test="${mem_id!='admin'}">
								<c:choose>
								<c:when test="${mem_grade==1}"><img src="assets/img/lv/1.png" width="15px" height="15px"></c:when>
								<c:when test="${mem_grade==2}"><img src="assets/img/lv/2.png" width="15px" height="15px"></c:when>
								<c:when test="${mem_grade==3}"><img src="assets/img/lv/3.png" width="15px" height="15px"></c:when>
								<c:when test="${mem_grade==4}"><img src="assets/img/lv/4.png" width="15px" height="15px"></c:when>
								<c:when test="${mem_grade==5}"><img src="assets/img/lv/5.png" width="15px" height="15px"></c:when>
								</c:choose>
							<c:out value="${mem_nickname}" />님 환영합니다.</a>
						</li>
						</c:if>
						<li class="nav-item"><a class="nav-link" href="Logout.do">Logout</a></li>
						<c:choose>
						<c:when test="${mem_id=='admin'}">
						<li class="nav-item"><a class="nav-link" href="MemberList.do">관리</a></li>
						</c:when>
						<c:when test="${mem_id!='admin'}">
						<li class="nav-item"><a class="nav-link" href="MyPage.do">My Page</a></li>
						</c:when>
						</c:choose>
						</c:if>
						<c:if test="${empty mem_id}">
						<li class="nav-item"><a class="nav-link" href="Login.do">Login</a></li>
						</c:if>
		                <li class="nav-item"><a class="nav-link" href="Main.do#portfolio">게시판</a></li>
		                <c:if test="${empty mem_id}">
						<li class="nav-item"><a class="nav-link" href="Agreement.do">Sign up</a></li>
						</c:if>
		            </ul>
                </div>
            </div>
        </nav>
		
		<header class="Masthead bg-dark"></header>
	
		<section class="page-section bg-dark" id="portfolio">
			<div class="container">
            <a class="topbutton" href="#"><img src="assets/img/topbutton.png"></a>
            
            <div class="row">
			<div class="col-md-offset-1 col-md-10 col-md-offset-1">
			<div class="col-md-12 pagetitle" align="center">회원 관리 ( ${pgList.count} )</div>
			
			
			<div class="col-md-12">
			
			<div class="col-md-12"><!-- 검색기능 -->
				<div class="col-md-offset-3 col-md-6 col-md-offset-3">
					<form action="MemberList.do" name="bus">
						<div class="col-md-3">
						<select class="form-control" name="search">
							<option value="mem_id">아이디</option>
							<option value="log_nickname">닉네임</option>
							<option value="log_grade">등급</option>
						</select>
						</div>
						<div class="col-md-7">
						<input type="text" class="form-control" name="searchtext">
						</div>
						<div class="col-md-2">
						<button type="submit" class="btn btn-default btn-md">검색</button>
						</div>
					</form>
				</div>
			</div><!-- 검색기능 -->
			
			<c:if test="${pgList.count==0}">
				<table class="table table-hover">
					<tr align="center">
						<td>회원이 없습니다.</td>
					</tr>
				</table>
			</c:if>
			<c:if test="${pgList.count > 0}">
				<table class="table table-hover">
				
					<thead>
						<tr align="center">
							<td width="5%">번호</td><td width="17%">아이디</td><td width="17%">비밀번호</td><td width="17%">닉네임</td><td width="17%">포인트</td><td width="17%">등급</td><td width="15%">관리</td>
						</tr>
					</thead>
					<c:set var="number" value="${pgList.number}" />
					<c:forEach var="member" items="${memberList}">
						<tr align="center">
							<td width="5%">
								<c:out value="${number}" />
    							<c:set var="number" value="${number-1}" />
    						</td>
							<td width="17%">${member.mem_id}</td>
							<td width="17%">${member.log_passwd}</td>
    						<td width="17%">${member.log_nickname}</td>
    						<td width="17%">${member.log_point}</td>
    						<td width="17%">
    							<c:choose>
								<c:when test="${member.log_grade==1}"><img src="assets/img/lv/1.png" width="15px" height="15px">&nbsp;준회원</c:when>
								<c:when test="${member.log_grade==2}"><img src="assets/img/lv/2.png" width="15px" height="15px">&nbsp;정회원</c:when>
								<c:when test="${member.log_grade==3}"><img src="assets/img/lv/3.png" width="15px" height="15px">&nbsp;우수회원</c:when>
								<c:when test="${member.log_grade==4}"><img src="assets/img/lv/4.png" width="15px" height="15px">&nbsp;특별회원</c:when>
								<c:when test="${member.log_grade==5}"><img src="assets/img/lv/5.png" width="15px" height="15px">&nbsp;VIP회원</c:when>
								</c:choose>
    						</td>
    						
    						<td width="15%">
    							<button type="button" class="btn btn-warning btn-xs" onclick="location.href='MemberModify.do?member_id=${member.mem_id}'">수정</button>
    							<button type="button" class="btn btn-danger btn-xs" onclick="location.href='MemberDrop.do?member_id=${member.mem_id}'">삭제</button>
    						</td>
						</tr>
				
				</c:forEach>
				</table>
			</c:if>
			
			</div>
			
			<div class="col-md-12">
			<div class="col-md-1"></div>
			<div class="col-md-10">
			<div class="col-md-12" align="center">
			<ul class="pagination">
			
				<c:if test="${pgList.startPage > pgList.blockSize}">
				<li><a href="Scrap_List.do?pageNum=${pgList.startPage-pgList.blockSize}"> < </a></li>
				</c:if>
			
				<c:forEach var="i" begin="${pgList.startPage}"	end="${pgList.endPage}">
				<li>
				<a href="MemberList.do?pageNum=${i}">
				<c:if test="${pgList.currentPage==i}">
					<font color="red"><b>${i}</b></font>
				</c:if>
				<c:if test="${pgList.currentPage!=i}">
					${i}
				</c:if>
				</a>
				</li>
				</c:forEach>
				<!-- 다음 페이지 -->
				<c:if test="${pgList.endPage < pgList.pageCount}">
				<li><a href="Scrap_List.do?pageNum=${pgList.startPage+pgList.blockSize}"> > </a></li>
				</c:if>
			
			</ul>
			</div>
			
			
			
					
					
					
				</div><!-- 10 -->
					<div class="col-md-1"></div>
			</div>
			
			
			
			</div><!-- class="col-md-offset-1 col-md-10 col-md-offset-1" -->
		</div><!-- row -->
     </div><!-- container -->
		</section>




		<!-- Footer-->
        <footer class="footer py-4 bg-dark">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-4 text-lg-start">Copyright &copy; Your Website 2021</div>
                    <div class="col-lg-4 my-3 my-lg-0">
                        <a class="btn btn-dark btn-social mx-2" href="#!"><i class="fab fa-twitter"></i></a>
                        <a class="btn btn-dark btn-social mx-2" href="#!"><i class="fab fa-facebook-f"></i></a>
                        <a class="btn btn-dark btn-social mx-2" href="#!"><i class="fab fa-linkedin-in"></i></a>
                    </div>
                    <div class="col-lg-4 text-lg-end">
                        <a class="link-dark text-decoration-none me-3" href="#!">Privacy Policy</a>
                        <a class="link-dark text-decoration-none" href="#!">Terms of Use</a>
                    </div>
                </div>
            </div>
        </footer>

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
</c:if>
</html>