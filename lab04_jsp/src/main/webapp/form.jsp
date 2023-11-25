<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>JSP 1</title>
		
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
			rel="stylesheet" 
			integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" 
			crossorigin="anonymous" />
	</head>
	<body>
		<h1>Form</h1>
		<!-- action: 전송되는 데이터를 받을 페이지를 지정하는 속성 -->
		<!-- method: 데이터 전송 방식을 결정 -->
		<!-- get/post 방식:
		get: 
			URL에 데이터를 포함하여 서버에 요청을 보냄.
			주로 데이터를 검색하는 데 사용되며, 브라우저 주소 표시줄에 데이터가 노출됨.
			예: http://example.com/page?name=value (보안이 취약함)
		post: 
			HTTP 요청 본문에 데이터를 넣어 서버에 요청을 보냄.
			주로 데이터를 서버로 제출하거나 업로드할 때 사용되며, URL에 데이터가 노출되지 않음.
			보안 면에서 GET보다 안전함.
		-->
		<form action="form_result.jsp" method="get">
			<input type="text" name="username" placeholder="사용자 이름" />
			<input type="submit" value="제출" />
		</form>
		
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" 
			integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" 
			crossorigin="anonymous"></script>
	</body>
</html>