<?php
include('connection.php');

$username = $_POST['username'];
$password = $_POST['password'];

$response = array();

$query = "SELECT * FROM `user_profile` WHERE `emailaddress`='{$username}' AND `password`='{$password}'";
if($row = mysqli_query($con, $query)){
    if(mysqli_num_rows($row) >0){
        $response['valid'] = "yes";
        $response['record'] = mysqli_fetch_assoc($row);
        
    }
    else{
        $response['valid'] = "Invalid username/password";
    } 
} else {
    $response['valid'] = "Server Error";
}
echo json_encode($response);



?>