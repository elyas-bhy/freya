<html>
<head>
	<title>Museum Manager 2014</title>
	<link type="text/css" rel="stylesheet" href="../../css/smoothness/jquery-ui.min.css"/>
	<link type="text/css" rel="stylesheet" href="../../css/smoothness/jquery.ui.theme.css"/>
	<link type="text/css" rel="stylesheet" href="../../css/demo_table.css"/>
	<link type="text/css" rel="stylesheet" href="../../css/demo_table_jui.css"/>
	<link type="text/css" rel="stylesheet" href="../../js/chosen_v1.0.0/chosen.css"/>
	<link type="text/css" rel="stylesheet" href="../../css/design.css"/>
	<script type="text/javascript" src="../../js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="../../js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="../../js/chosen_v1.0.0/chosen.jquery.js"></script>
	<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<script type="text/javascript">
		var pwd = "http://localhost:8080/";
		$(document).ready(function() {
			$('#tabs').tabs();
			$('.chosen').chosen();
		});
	</script>
</head>
<body>

	<div id="header">
		<h1>Museum Manager</h1>
	</div>
	<div id="wrapper">
		<jsp:include page="nav.jsp"></jsp:include>
