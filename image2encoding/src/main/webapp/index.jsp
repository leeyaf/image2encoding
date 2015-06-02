<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="../bootstrap-3.3.4-dist/css/bootstrap.min.css">
<script src="../bootstrap-3.3.4-dist/js/jquery.min.js"></script>
<script src="../bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<h2>Image2Encoding</h2>
	<form action="/image2encoding/upload_file" method="post"
		enctype="multipart/form-data">
		<input type="file" /> <input type="submit" />
	</form>
</body>
</html>