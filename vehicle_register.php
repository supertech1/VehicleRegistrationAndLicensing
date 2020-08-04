<?php
include('connection.php');

$vehicle_type = $_POST['vehicle_type'];
$vehicle_make = $_POST['vehicle_make'];
$vehicle_capacity = $_POST['vehicle_capacity'];
$engine_number =$_POST['engine_number'];
$chassis_number =$_POST['chassis_number'];
$vehicle_color =$_POST['vehicle_color'];
$plate_number =$_POST['plate_number'];
$vehicle_year =$_POST['vehicle_year'];
$owner_id =$_POST['owner_id'];

$vehicle_id = generateVehicleId($con);

$response = array();

$query = "INSERT INTO `vehicle_detail`(`vehicle_id`,`owner_id`,`vehicle_type`,`vehicle_make`,`vehicle_capacity`,`engine_number`, `chassis_number`, `vehicle_color`, `plate_number`, `vehicle_year`) 
            VALUES ('{$vehicle_id}','{$owner_id}','{$vehicle_type}','{$vehicle_make}','{$vehicle_capacity}','{$engine_number}','{$chassis_number}','{$vehicle_color}','{$plate_number}','{$vehicle_year}')";
if($row = mysqli_query($con, $query)){
        $response['valid'] = "yes";
        $response['vehicle_id'] = $vehicle_id;
} else {
    $response['valid'] = "Server Error";
}
echo json_encode($response);


function generateVehicleId($con){
    $initial = "VISBN";
    $number_not_valid = true;
    $id = "";
	while($number_not_valid){
        $digits = mt_rand(100000, 999999);
        $id = $initial.$digits;
		$select_query = "SELECT * FROM `user_profile` WHERE `owner_id` = '{$id}'";
		$row = mysqli_query($con , $select_query);
		if(mysqli_num_rows($row) > 0) {
			$number_not_valid = true;
		} else {
			$number_not_valid = false;
		}
    }
    
    return $id;
}


?>