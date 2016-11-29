<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$uid = $_POST['uid'];
		$pid = $_POST['pid'];
		
		require_once('dbConnect.php');
		
			$sql = "INSERT INTO postlike (uid,pid,likes) VALUES ($uid , $pid,1)";
	
			if(mysqli_query($conn,$sql))
			{
				$sql = "UPDATE post SET likes = likes + 1 WHERE id = $pid";
				if(mysqli_query($conn,$sql))
				{
					echo "success";
				}
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