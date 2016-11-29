<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$gid = $_POST['gid'];
		$uid = $_POST['uid'];
		$pid = $_POST['pid'];
		
				

		require_once('dbConnect.php');
		
			$sql = "INSERT INTO grouppostlike (uid,pid,likes,gid) VALUES ($uid , $pid,1,$gid)";
			
			if(mysqli_query($conn,$sql))
				{
					
					$sql1="UPDATE grouppost SET `likes` = `likes`+1 WHERE `id` = $pid AND `gid` = $gid" ;	
					
					if(mysqli_query($conn,$sql1))
					{
						echo "success";
					}
					else
						echo "failure";					

	
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