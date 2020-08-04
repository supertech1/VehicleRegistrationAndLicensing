hgjhgjhgj
<?php
include('connection.php');

$fullname = $_POST['fullname'];
$email = $_POST['emailaddress'];
$phonenumber = $_POST['phonenumber'];
$password =$_POST['password'];
print "about to generate";
$id = generateUserId();
echo $id;



function generateUserId(){
    $initial = "UID";
    $number_not_valid = true;
    $id = "";
    while($number_not_valid){
        $digits = mt_rand(100000, 999999);
        $id = $initial.$digits;
        $select_query = "SELECT * FROM `user_profile` WHERE `owner_id` = '{$id}'";
        if(mysqli_num_rows(mysqli_query($select_query))){
            $number_not_valid = true;
        } else {
            $number_not_valid = false
        }
    }
    return $id;
}

?>