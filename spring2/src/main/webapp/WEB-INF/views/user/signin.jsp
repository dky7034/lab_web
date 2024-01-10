<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Spring 2</title>
	
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
        rel="stylesheet" 
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" 
        crossorigin="anonymous">
</head>
<body>
    <div class="container-fluid">
        <c:set var="title" value="로그인" />
        <%@ include file="../fragments/title.jspf" %>
        
        <main class="my-2">
            <div class="my-2 card card-body">
            	<!-- form action 속성이 설정되어 있지 않으면 현재 페이지(URL)로 submit. -->
                <form method="post">
                <!-- action 속성: 폼 데이터가 제출될 URL 지정. 
               		현재 페이지로 POST 메서드를 사용하여 폼 데이터가 제출됨 -->
                	<c:if test="${not empty param.result && param.result eq 'f'}"> 
                	<!-- -> result는 UserController.java에서의 요청 파라미터(89번째 줄 확인) -->
                	<!-- 요청 파라미터 값을 가져와야 하므로 JSP 내장 객체인 param을 사용하여 
                		result라는 이름의 파라미터의 값을 가져온다. -->
                	<!-- 로그인 실패 후 다시 로그인 페이지로 이동했을 때 경고 메시지 -->
                	<div class="text-danger">아이디와 패스워드를 확인하세요...</div>
                	</c:if>
                    <div class="my-2">
                        <input type="text" class="form-control" 
                            name="userid" placeholder="아이디" required autofocus />
                    </div>
                    <div class="my-2">
                        <input type="password" class="form-control"
                            name="password" placeholder="비밀번호" required />
                    </div>
                    <!-- 위에서 입력한 아이디와 비밀번호는 name 속성을 통해 서버로 전송됨! -->
                    <div class="my-2">
                        <input type="submit" class="form-control btn btn-success"
                            value="로그인" />
                    </div>
                    <!-- submit은 해당 입력 요소가 폼을 제출하는 버튼임을 나타냄.
                    	 HTML 폼에서 사용자가 입력한 데이터를 서버로 제출하는 버튼! -->
                </form>
            </div>
        </main>
    
    </div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" 
	    integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" 
	    crossorigin="anonymous"></script>
</body>
</html>