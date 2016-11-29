<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$uid = $_POST['uid'];
		$mid = $_POST['pid'];
		$msg = $_POST['msg'];
		$cdatetime=$_POST['date'];
		

		require_once('dbConnect.php');
		
			$sql = "INSERT INTO comments (uid,mid,message,time,likes) VALUES ($uid , $mid,'$msg','$cdatetime',0)";
	
	
			
				if(mysqli_query($conn,$sql))
				{
					
	
			echo "success";
	
				}
				else
				{
					echo "failure";
	
	
				}
	
	}
	else
	{
		echo 'error';
	}
	mysqli_close($conn);
?>