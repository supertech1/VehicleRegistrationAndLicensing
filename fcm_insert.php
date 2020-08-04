<?php
include('connection.php');

$fcm_token = $_POST['fcm_token'];
$sql = "insert into `fcm` values('".$fcm_token."');";
mysqli_query($con, $sql);
mysqli_close($con);

?>