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

	<title>Shopzilla - SEO Tool</title>
</head>

<body>

<div id="body-container" style="height:365px;">

        <div id="title">
        	<img src="resources/images/sz_logo.jpeg" width="200" height="50" 
			style="float:left; margin-left:50px; margin-right:15px; margin-top:12px;"/>
        	<h1>Search Engine Optimization Tool</h1> 
        </div>
        <div id="form" class="body-content">
            Enter a keywoard and domain below to analyze pages.<br /><br />

            <form action="./optimize" method="get">
               	<p><input type="text" name="query"
			style="width:450px; height:40px; font-size:24px;"></p>
            	<p>
                  <input type="radio" name="targetsite" value="bizrate.com" 
			checked="checked"/><em>bizrate.com</em><br />
                  <input type="radio" name="targetsite" value="shopzilla.com" />
			<em>shopzilla.com</em><br />
			         OR<br />
			         Specific url: <input type="text" name="targeturl" value="optional" /><br /><br />
                  <input id="submit" type="submit"  value="Submit"/><br />
              	</p>
            </form>
    	</div>
</div>
	<script>
		$(function() {
			$('#submit').click(function() {
				$('#form').fadeOut();
				$('#form').promise().done(function() {
					var newText = '<center><h2>Loading...</h2>' +
							'<p>Please wait while we analyze <br&#x2F>' +
							'the targeted domain and keyword.</p></center>';
					$('#body-container').animate({
						height: '265px'
					} , 1000);
					$('#form').html(newText);
					$('#form').fadeIn();
				});
				
			});
		});
	</script>
</body>
</html>
