<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- Hero Section Begin -->
    <section class="hero hero-normal">
        <div class="container">
            <div class="row">
                <div class="col-lg-3">
                    <div class="hero__categories">
                        <div class="hero__categories__all">
                            <i class="fa fa-bars"></i>
                            <span>상품목록</span>
                        </div>
                        <ul>
                            <li><a href="<%=request.getContextPath() %>/shop/grid.do">원두</a></li>
                            <li><a href="<%=request.getContextPath() %>/shop/grid.do">생두</a></li>
                            <li><a href="<%=request.getContextPath() %>/shop/grid.do">드립백</a></li>
                            <li><a href="<%=request.getContextPath() %>/shop/grid.do">캡슐</a></li>
                            <li><a href="<%=request.getContextPath() %>/shop/grid.do">도매</a></li>
                            <li><a href="<%=request.getContextPath() %>/shop/grid.do">커피용품</a></li>
                            <li><a href="<%=request.getContextPath() %>/shop/grid.do">정기배송</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-9">
                    <div class="hero__search">
                        <div class="hero__search__form">
                            <form action="#">
                                <div class="hero__search__categories">
                                    모든 항목
                                    <span class="arrow_carrot-down"></span>
                                </div>
                                <input type="text" placeholder="무엇이 필요하신가요?">
                                <button type="submit" class="site-btn">검색</button>
                            </form>
                        </div>
                        <div class="hero__search__phone">
                            <div class="hero__search__phone__icon">
                                <i class="fa fa-phone"></i>
                            </div>
                            <div class="hero__search__phone__text">
                                <h5>063) 123 - 4567</h5>
                                <span>운영시간 09:00 ~ 18:00</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Hero Section End -->
    