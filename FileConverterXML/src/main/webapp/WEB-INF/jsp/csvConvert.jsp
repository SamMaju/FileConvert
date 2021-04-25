<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload File</title>
</head>
<body>

<h2>Upload File</h2>

<p>
Upload File To Convert in CSV
</p>

<form method="POST" action="/csvConvert" enctype="multipart/form-data">
		<input type="file" name="file" /><br />
		<br /> <input type="submit" value="Submit" />
	</form>
</body>
</html>