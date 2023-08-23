<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ogani Template">
    <meta name="keywords" content="Ogani, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>JavaChip | Template</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap" rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/table.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/font-awesome.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/elegant-icons.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/nice-select.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery-ui.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/owl.carousel.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/slicknav.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/style.css" type="text/css"/>
<title>JavaChip|배송지 관리</title>
</head>
<body>
<section class="checkout spad" style="padding-top: 20px;">
    <div class="container">
        <div class="checkout__form">
		<h4>배송지 관리</h4>
		<div class="col-lg-10 col-md-6" id="address_list" style=margin-bottom:20px;>
	    	<span>배송지 목록</span>
	    		<select name="sort" id="sort" onchange="sortFn()">
	    			<option value="add_main" id="add_main">기본 배송지</option>
	    			<option value="add_sub1" id="add_sub1">추가 배송지1</option>
	    			<option value="add_sub2" id="add_sub2">추가 배송지2</option>
	    		</select>
				<button type="button" class="btn btn-outline-primary" onclick="">배송지 저장</button>
	       	</div>
	       	<div class="col-lg-10 col-md-6">
	                <div class="row">
	                    <div class="col-lg-6">
	                        <div class="checkout__input">
	                            <p>성명<span>*</span></p>
	                            <input type="text" name="oName" id="oName" required>
	                        </div>
	                    </div>
	                </div>
	                <div class="checkout__input">
	                    <p>주소<span>*</span></p>
	                    <input type="text" id="oAdd1" name="oAdd1" placeholder="우편번호" class="checkout__input__add" style="width:150px;" required>
	                    <button type="button" class="btn btn-outline-primary" onclick="sample7_execDaumPostcode()">우편번호</button>
	                    <input type="text" id="oAdd2" name="oAdd2" placeholder="주소" class="checkout__input__add" required>
	                    <input type="text" id="oAdd3" name="oAdd3" placeholder="나머지 주소" class="checkout__input__add" required>
	                </div>
	                <div class="row">
	                    <div class="col-lg-6">
	                        <div class="checkout__input">
	                            <p>전화번호<span>*</span></p>
	                            <input type="text" name="oPhone" id="oPhone" required>
	                        </div>
	                    </div>
	                    <div class="col-lg-6">
	                        <div class="checkout__input">
	                            <p>이메일<span>*</span></p>
	                            <input type="text" name="oMail" id="oMail" required>
	                        </div>
	                    </div>
	                </div>
	                <div class="checkout__input">
	                    <p>배송 시 전달사항</p>
	                    <input type="text"
	                        placeholder="전달사항을 입력해주세요. (선택사항)">
	                </div>
	            </div>
            </div>
        </div>
    </section>
</body>
    <script src="<%=request.getContextPath() %>/resources/js/bootstrap.bundle.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.nice-select.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery-ui.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.slicknav.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/mixitup.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/owl.carousel.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/main.js"></script>
    <script src="http://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/addressapi.js"></script>
</html>