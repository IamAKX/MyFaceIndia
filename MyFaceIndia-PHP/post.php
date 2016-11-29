<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$uid = $_POST['uid'];
		$username = $_POST['username'];
		$userpost = $_POST['userpost'];
		$pdatetime=$_POST['date'];
		$type = $_POST['type'];
		$access = $_POST['access'];

		require_once('dbConnect.php');
		
		$name = $uid.time().".jpeg";
		$path = "postimage/".$name;
		
		
		if($type == '1'){
			if(file_put_contents($path, base64_decode($userpost))!=false)
			{
	
				$sql = "INSERT INTO post (uid,imgname,uname,ptime,type,access,likes) VALUES ($uid , '$name','$username','$pdatetime',$type,$access,0)";
	
	
			
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
				echo "failure";
			}
		}
		else
		{
			$sql = "INSERT INTO post (uid,imgname,uname,ptime,type,access) VALUES ($uid , '$userpost','$username','$pdatetime',$type,$access)";
	
	
			
				if(mysqli_query($conn,$sql))
				{
					
	
			echo "success";
	
				}
				else
				{
					echo "failure";
	
	
				}
	
		}
	}
	else
	{
		echo 'error';
	}
	mysqli_close($conn);
?>