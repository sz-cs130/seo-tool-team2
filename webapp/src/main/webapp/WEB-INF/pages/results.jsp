<html>
<head>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
	<script>
		$(document).ready(function() {
			$('body').css("display", "none");
			$('body').fadeIn();
		});
	</script>
	<link href='http://fonts.googleapis.com/css?family=Armata' rel='stylesheet' type='text/css'>
	<link href="resources/css/search.css" rel="stylesheet" type="text/css" />
	<link rel="shortcut icon" href="resources/images/shopzilla.ico">
	<title>Shopzilla - SEO Tool Results</title>
</head>

<body>
<div id="body-container">
 	<div id="title">
        	<img src="resources/images/sz_logo.jpeg" width="200" height="50" 
			style="float:left; margin-left:50px; margin-right:15px; margin-top:12px;"/>
        	<h1>Search Engine Optimization Tool</h1> 
        </div>

	<div class="body-content">
		<h1 style="margin-top:0px;">Results for '${query}'</h1>
		<a href="/">Search Again</a>
		<a href="/recommend">Recommendations</a>
		${output}
	</div>
</div>
</body>

</html>
