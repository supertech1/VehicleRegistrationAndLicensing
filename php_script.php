<?php
$db_host = "localhost";
$db_user = "";
$db_pw = "";
$db = "";

$con = mysqli_connect($db_host, $db_user, $db_pw, $db);

if(mysqli_connect_error($con)){
    echo " failed to connect to Database";
} else{
    echo "success"
    // $username = $_POST['username'];
    // $password = $_POST['password']
}


?>