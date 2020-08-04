<?php
include('connection.php');

$vehicle_id = $_POST['vehicle_id'];
$payment_ref = $_POST['payment_ref'];
$transaction_status =  $_POST['transaction_status'];


$response = array();

$date = date("Y-m-d");
$today = strtotime($date);
$expires = strtotime("+1 year",$today);

// echo (date("Y m d","0000-00-00"));
// echo ($date);
// echo ("tosin".date("Y-m-d",$expires));

$query = "INSERT INTO `payment_record`(`vehicle_id`,`date`,`payment_ref`, `transaction_status`) 
            VALUES ('{$vehicle_id}','{$date}','{$payment_ref}', '{$transaction_status}')";
if($row = mysqli_query($con, $query)){
        $response['valid'] = "yes";
        $select_date_query = "SELECT `vehicle_expiry_date` from `vehicle_detail` WHERE `vehicle_id` = '{$vehicle_id}'";
        if($select_date_query_row = mysqli_query($con, $select_date_query)){
            if(mysqli_num_rows($select_date_query_row) >0){
                $select_result = mysqli_fetch_assoc($select_date_query_row);
                $current_date = $select_result['vehicle_expiry_date'];
                $current_date_year = date("Y", $current_date);
                $new_date = "";
                if($current_date_year < "2000") {
                    $new_date = $date;
                } else {
                    $current_date_str = strtotime($current_date);
                    $new_date = strtotime("+1 year", $current_date_str);
                }
                $update_date_query = "UPDATE `vehicle_detail` SET `vehicle_expiry_date` = '{$new_date}' WHERE `vehicle_id`='{$vehicle_id}'";
                $update_row = mysqli_query($con, $update_date_query);

            }
        }





} else {
    $response['valid'] = "Server Error";
}
echo json_encode($response);




?>