<?php
$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$tutor_id = $_POST["tutor_id"];
$subject_id = $_POST["subject_id"];



//execute the SQL query and return records
$mysql_qry = "DELETE FROM TUTOR_SUBJECT_TBL WHERE tutor_id = '$tutor_id' AND subject_id='$subject_id'";


if($conn->query($mysql_qry) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry."<br>".$conn->error;
}

//close the connection
$conn->close();

?>