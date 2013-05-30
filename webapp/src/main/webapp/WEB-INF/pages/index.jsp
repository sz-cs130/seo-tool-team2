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
	<div id="form" style="text-align:center;width:600px;height:200px;position:fixed;top:40%;left:50%;margin-left:-300px;margin-top:-100px;">
		<h1>Shopzilla SEO Tool</h1>
		<p>Enter a keyword and domain below to analyze pages.</p>
		<form action="./optimize" method="GET">
			<input type="text" name="query"/><br>
			<input type="radio" name="targetsite" value="bizrate.com" checked/>bizrate.com<br>
			<input type="radio" name="targetsite" value="shopzilla.com"/>shopzilla.com<br>
			<input id="submit"type="submit" value="Submit"/>
		</form>
	</div>
	<div id="loading" style="text-align:center;width:600px;height:400px;display:none;position:fixed;top:50%;left:50%;margin-left:-300px;margin-top:-200px;">
		<h2>Loading...</h2>
		<p>Please wait while we analyze the targeted domain and keyword.</p>
	</div>
	<script>
		$(function() {
			$('#submit').click(function() {
				$('#form').fadeOut();
				$('#loading').fadeIn();
			});
		});
	</script>
</body>
</html>