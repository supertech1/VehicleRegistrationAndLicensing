<?php
$db_host = "localhost:3306";
$db_user = "tosin";
$db_pw = "Supertechnology1@";
$db = "admin_vehicle";

$con = mysqli_connect($db_host, $db_user, $db_pw, $db);
if(mysqli_connect_error($con)){
    echo " Server error";
} 
?>