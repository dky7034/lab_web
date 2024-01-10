<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Spring 2</title>
		
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
			rel="stylesheet" 
			integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" 
			crossorigin="anonymous" />
	</head>
	<body>
		<header>
			<h1>Ex2 페이지</h1>
		</header>
		
		<nav>
			<ul>
				<li>
					<c:url var="homePage" value="/" /> <!-- context root 로 이동하기 위한 주소 생성 -->
					<a href="${homePage}">메인 페이지</a>
				</li>
				<li>
					<c:url var="ex1Page" value="/ex1" />
					<a href="${ex1Page}">ex1</a>
				</li>
			</ul>
		</nav>
		
		<main>
			<h2>요청 처리 결과</h2>
			<div>dto: ${dto}</div>
		</main>
		
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" 
			integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" 
			crossorigin="anonymous"></script>
	</body>
</html>