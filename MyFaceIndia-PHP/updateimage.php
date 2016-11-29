<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$uid = $_POST['uid'];
		$userpost = $_POST['userpost'];
		

		require_once('dbConnect.php');
		
		$name = time().'_'.$uid.".jpg";
		$path = "profileimage/".$name;
		
		
		if(file_put_contents($path, base64_decode($userpost))!=false)
		{
			$sql = "UPDATE users SET image='$name' WHERE idu=$uid";
		
			if(mysqli_query($conn,$sql))
			{
				echo "success".$name;

			}
			else
			{
				echo "failure";

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