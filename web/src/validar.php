<?php
$usuario=$_POST['usuario'];
$clave=$_POST['clave'];

$conexion = new mysqli("localhost","root","","neb");
$consulta = "SELECT * FROM usuarios WHERE id='usuario' and clave='$clave'";
$resultado=mysqli_query($conexion, $consulta);

$filas=mysqli_num_rows($resultado)
if($filas > 0) {
    header("location:forlinkneb.php");
}
else {
    echo "Error en la autenticación";
}
mysqli_free_result($resultado);
mysqli_close($conexion);