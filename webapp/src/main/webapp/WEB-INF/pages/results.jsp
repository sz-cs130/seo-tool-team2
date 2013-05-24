<html>
<head>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
	<script>
		$(document).ready(function() {
			$('body').css("display", "none");
			$('body').fadeIn();
		});
	</script>
</head>
<body>
	<h1>Results for '${query}'</h1>
	<a href="/">Search Again</a>
	${output}
</body>
</html>