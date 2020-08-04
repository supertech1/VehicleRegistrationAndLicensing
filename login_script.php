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
        $user_id = $response['record']['owner_id'];

        $count_query = "SELECT COUNT(*) as c FROM `vehicle_detail` WHERE `owner_id`='{$user_id}'";
        $row_count_query = mysqli_query($con, $count_query);
		$row_result = mysqli_fetch_assoc($row_count_query);
        $response['count'] = $row_result['c'];
		$response['vehicles'] = [];
		$response['vehicles_id'] = [];

        if($response['count'] > 0){
            $vehicles_query = "SELECT * FROM `vehicle_detail` WHERE `owner_id`='{$user_id}'";
            $row_vehicles_query = mysqli_query($con, $vehicles_query);
            $vehicles = array();
            $vehicles_id = array();
			
            while($vehicles_row = mysqli_fetch_assoc($row_vehicles_query)){
                array_push($vehicles, $vehicles_row);
                array_push($vehicles_id, $vehicles_row['vehicle_id']);
            }
            $response['vehicles'] = $vehicles;
            $response['vehicles_id'] = $vehicles_id;
        } 
        
    }
    else{
        $response['valid'] = "Invalid username/password";
    } 
} else {
    $response['valid'] = "Server Error";
}
echo json_encode($response);



?>