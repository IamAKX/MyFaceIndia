<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		
		$uid = $_POST['uid'];
		require_once('dbConnect.php');
		
		$sql = "SELECT * FROM `group` WHERE `uid` = $uid";	
		$result = mysqli_query($conn,$sql);
		
		$grouparray = array();
		
		while($row = mysqli_fetch_assoc($result))
		    {
		       
		    	 array_push($grouparray,array(
											 "id"=>$row['id'],
											 "name"=>$row['name'],
											 "title"=>$row['title'],
											 "image"=>$row['image'],
											 "privacy"=>$row['privacy'],
											 "control"=>$row['control'],
											 "description"=>$row['description'],
											 "gdate"=>$row['gdate']
									    )
							 );
		    }

		echo json_encode(array("groups"=>$grouparray));	

		//$conn->close();
	}
	else
	{
		echo 'error';
	}
	mysqli_close($conn);
?>