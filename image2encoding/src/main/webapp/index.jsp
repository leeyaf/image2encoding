<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<script src="bootstrap-3.3.4-dist/js/jquery.min.js"></script>
<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- upload image -->
	<!-- image url -->
	<h2>Image2Encoding</h2>
	<form action="/image2encoding/upload_file" method="post"
		enctype="multipart/form-data">
		<input type="file" /> <input type="submit" />
	</form>
	<div class="btn-group" role="group" aria-label="...">
		<button type="button" class="btn btn-default">Left</button>
		<button type="button" class="btn btn-default">Middle</button>
		<button type="button" class="btn btn-default">Right</button>
	</div>
</body>
</html>