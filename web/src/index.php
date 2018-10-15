<html>
<head>
	<title>Nebulon</title>
    <link rel="stylesheet" type="text/css" href="estilo.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body bgcolor="#DAE5E4">
    
    <?php
    $conexion = new mysqli("localhost","","neb");
    if($conexion) {
        echo "exito";
    }
    else{
        echo "paila";
    }
	<div align="center">
    <form action="linkneb.php">
    	<p>LOG-IN</p><br>
    
    	<label for="nombre">Usrio</label>
        <input type="text"><br>
        echo"algp";
    //vagina    
        <label for="">Password</label>
    	<input type="password"><br>
        
        
        <input type="submit">
	</form>
    </div>
    ?>
</body>
</html>