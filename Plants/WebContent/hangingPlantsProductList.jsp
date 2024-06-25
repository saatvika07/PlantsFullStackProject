<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="plantsPackage.HangingPlantProducts"%>
<%@ page import="plantsPackage.HangingPlantProductsDAO"%>
<%@ page import="plantsPackage.HangingPlantsDbCon"%>
<%@ page import="java.sql.SQLException"%>


<%
	HangingPlantProductsDAO pd = new HangingPlantProductsDAO(HangingPlantsDbCon.getConnection());
	List<HangingPlantProducts> hangProd = pd.getAllHangingPlantProducts();
	//ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
	//<%@ page import="yourpackage.Cart"
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/includes/head.jsp"%>
<title>Hanging Plants</title>
</head>
<body>
	<%@ include file="/includes/header.jsp"%>
	<ul class="breadcrumb">
		<li><a href="index.jsp">Home > </a></li>
		<li>Hanging Plants</li>
	</ul>
	<div class="section-image">
		<img src="HangingPlantsImages/hanging-plants.jpg">
	</div>
	<!-- need to change style -->
	<div class="container">
		<div class="row">
			<%
				if (!hangProd.isEmpty()) {
					for (HangingPlantProducts p : hangProd) {
			%>
			<div class="product-card">
				<div class="product-image">
					<img src="HangingPlantsImages/<%=p.getImage()%>" alt="PlantImage">
				</div>
				<div class="product-info">
					<h5><%=p.getPname()%></h5>
					<div class="rating">
						<i class="fa fa-star" aria-hidden="true"></i> <i
							class="fa fa-star" aria-hidden="true"></i> <i class="fa fa-star"
							aria-hidden="true"></i> <i class="fa fa-star-half"
							aria-hidden="true"></i> <i class="fa fa-star-o"
							aria-hidden="true"></i> <span>(4.5/5)</span>
					</div>
					<div class="prices-div">
						<p class="prices">
							<span><del>
									&#8377;<%=p.getOriginalprice()%></del></span> <span class="discount-price">&#8377;<%=p.getDiscountprice()%></span>
						</p>
					</div>
					<div class="buttons">
						<a href="#">View Product</a>
					</div>
				</div>
			</div>
			<%
				}
				} else {
					out.println("There are no products");
				}
			%>

		</div>
	</div>
</body>
</html>