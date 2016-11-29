<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$uid = $_POST['uid'];
		$status = $_POST['status'];
		$sdatetime=$_POST['date'];
		$access = $_POST['access'];

		require_once('dbConnect.php');
		
		$sql = "INSERT INTO status(uid,status,sdatetime,access) VALUES ($uid , '$status', '$sdatetime',$access)";
		
		
		if(mysqli_query($conn,$sql))
		{
			
			echo "success";

		}
		else
		{
			echo "fail";

		}
	}
	else
	{
		echo 'error';
	}
	mysqli_close($conn);
?>