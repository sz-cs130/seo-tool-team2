<html>
<head>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
	<script>
		$(document).ready(function() {
			$('body').css("display", "none");
			$('body').fadeIn();
			$('#back').button();
			$('#back').click(function() {
        		parent.history.back();
        		return false;
    		});
		});
	</script>
	<link href='http://fonts.googleapis.com/css?family=Armata' rel='stylesheet' type='text/css'>
	<link href="resources/css/search.css" rel="stylesheet" type="text/css" />
	<title>Shopzilla - SEO Tool Recommendations</title>
</head>

<body>
<div id="body-container">
 	<div id="title">
        	<img src="resources/images/sz_logo.jpeg" width="200" height="50" 
			style="float:left; margin-left:50px; margin-right:15px; margin-top:12px;"/>
        	<h1>Search Engine Optimization Tool</h1> 
        </div>

	<div class="body-content">
		<h1 style="margin-top:0px;">Recommendations for '${query}'</h1>
		<a href="/">Search Again</a>
		<a id="back" href="#">Back</a>
		${output}
	</div>
</div>
</body>

</html>
