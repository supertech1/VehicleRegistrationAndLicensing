<?php
include('connection.php');

$value = $_POST['value'];

$response = array();

$query = "SELECT * FROM `vehicle_detail` WHERE `plate_number`='{$value}' OR`chassis_number`='{$value}'";
if($row = mysqli_query($con, $query)){
    if(mysqli_num_rows($row) >0){
        $response['valid'] = "yes";
        $response['vehicle_detail'] = mysqli_fetch_assoc($row);
        $user_id = $response['vehicle_detail']['owner_id'];

        $count_query = "SELECT * FROM `user_profile` WHERE `owner_id`='{$user_id}'";
        $row_count_query = mysqli_query($con, $count_query);
		$response['vehicle_owner']  = mysqli_fetch_assoc($row_count_query);
        
    }
    else{
        $response['valid'] = "No record found";
    } 
} else {
    $response['valid'] = "Server Error";
}
echo json_encode($response);



?>