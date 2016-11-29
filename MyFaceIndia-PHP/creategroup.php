<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$uid = $_POST['uid'];
		$name = $_POST['name'];
		$title = $_POST['title'];
		$image = $_POST['image'];
		$privacy = $_POST['privacy'];
		$control = $_POST['control'];
		$desc = $_POST['desc'];
		$gdate = $_POST['gdate'];
		

		require_once('dbConnect.php');

		$imgname = time().'_'.$uid.".jpg";
		$path = "groupicon/".$imgname;
		
		if(file_put_contents($path, base64_decode($image))!=false)
		{
			$sql = "INSERT INTO `group`(`uid`, `name`, `title`, `image`, `privacy`, `control`, `description`, `gdate`) VALUES ($uid , '$name','$title','$imgname',$privacy,$control,'$desc','$gdate')";
	
	
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
		echo 'error';
	}
	mysqli_close($conn);
?>