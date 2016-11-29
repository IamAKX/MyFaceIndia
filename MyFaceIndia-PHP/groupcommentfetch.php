<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$gid = $_POST['gid'];
		$pid = $_POST['pid'];
		require_once('dbConnect.php');
		
		$sql = "SELECT u.username,c.message,c.time from users u, groupcomments c WHERE c.gid = $gid AND c.mid = $pid and c.uid = u.idu ORDER BY c.time DESC";	
		$result = mysqli_query($conn,$sql);
		
		$statusarray = array();
		while($row = mysqli_fetch_assoc($result))
		    {
		       
		    	 array_push($statusarray,array(
											 "uname"=>$row['username'],
											 "msg"=>$row['message'],
											 "time"=>$row['time']
									    )
							 );
		    }

		echo json_encode(array("comments"=>$statusarray));	

		//$conn->close();
	}
	else
	{
		echo 'error';
	}
	mysqli_close($conn);
?>