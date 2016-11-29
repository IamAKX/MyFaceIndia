<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		
		$uid = $_POST['uid'];
		$username = $_POST['username'];
		$name = $_POST['name'];
		$url=$_POST['url'];
		$type = $_POST['type'];
		$edatetime = $_POST['edatetime'];
		$uimage = $_POST['uimage'];


		require_once('dbConnect.php');
		
		$sql = "INSERT INTO events (uid,uname,name,url,type,edatetime,uimage) VALUES ($uid,'$username','$name','$url',$type,'$edatetime','$uimage')";
		
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