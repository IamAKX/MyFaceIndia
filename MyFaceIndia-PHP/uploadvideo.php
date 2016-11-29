<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 	 require_once('dbConnect.php');
	 $uid = $_POST['uid'];
	 $username = $_POST['username'];
	 $pdatetime=$_POST['date'];
	 $type = $_POST['type'];
	 $access = $_POST['access'];
	 $name = time().".mp4";

	 $file_size = $_FILES['myFile']['size'];
	 $file_type = $_FILES['myFile']['type'];
	 $temp_name = $_FILES['myFile']['tmp_name'];
	 $location = "postvid/";
 
	 move_uploaded_file($temp_name, $location.$name);
	 echo $name;
	 /*$sql = "INSERT INTO post (uid,imgname,uname,ptime,type,access) VALUES ($uid , '$name','$username','$pdatetime',$type,$access)";
	 echo $sql;
	 if(mysqli_query($conn,$sql))
	 {
	 	echo "success";
	 	}
	 else{
	 echo "failure";
	 }
	 */
 }else{
 	echo "Error";
 }
 mysqli_close($conn);
 ?>