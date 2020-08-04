<?php
include('connection.php');

$fullname = $_POST['fullname'];
$email = $_POST['emailaddress'];
$phonenumber = $_POST['phonenumber'];
$drivers_license = $_POST['drivers_license'];
$password =$_POST['password'];

$id = generateUserId($con);
if($id != null) {
    $query = "INSERT INTO `user_profile`(`owner_id`,`fullname`,`emailaddress`,`drivers_license`,`phonenumber`,`password`) VALUES ('{$id}','{$fullname}','{$email}','{$drivers_license}','{$phonenumber}','{$password}')";
    if($row = mysqli_query($con, $query)){
            echo "success";
        } else {
        echo "Server Error";
    }
}



function generateUserId($con){
    $initial = "UID";
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