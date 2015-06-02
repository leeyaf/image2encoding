<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Image2Encoding</title>
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<script src="bootstrap-3.3.4-dist/js/jquery.min.js"></script>
<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="page-header">
			<h1>Image2Encoding</h1>
			<h3>
				<small>Convert a image to char encoding image</small>
			</h3>
		</div>
		<!-- upload message -->
		<c:if test="${!empty message.address}">
			<div class="panel panel-default">
				<div class="panel-body">
					<img src="images/image/${message.address}" class="img-responsive center-block"
						alt="Responsive image">
				</div>
			</div>
		</c:if>
		<c:if test="${!empty message.encoding}">
			<div class="panel panel-default">
				<div class="panel-body">
					<img src="images/encoding/${message.encoding}" class="img-responsive center-block"
						alt="Responsive image">
				</div>
			</div>
		</c:if>
		<!-- upload image -->
		<div class="panel panel-default">
			<div class="panel-heading">upload a image here..</div>
			<div class="panel-body">
				<form action="upload_file" method="post"
					enctype="multipart/form-data">
					<div class="form-group">
						<input type="file" name="image">
						<p class="help-block">less than 4MB</p>
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
		<!-- image url 
		<div class="panel panel-default">
			<div class="panel-heading">input a image url here..</div>
			<div class="panel-body">
				<form action="upload_file" method="post"
					enctype="multipart/form-data">
					<div class="form-group"><input
							type="text" class="form-control" 
							placeholder="URl">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
		-->
	</div>
</body>
</html>