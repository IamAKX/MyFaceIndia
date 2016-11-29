<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		

		require_once('dbConnect.php');
		
		$sql = "SELECT u.idu,p.id,u.image,p.uname,p.imgname,p.ptime,p.type,p.likes FROM post p, users u WHERE p.uid = u.idu ORDER BY p.ptime DESC";	
		$result = mysqli_query($conn,$sql);
		
		$statusarray = array();
		while($row = mysqli_fetch_assoc($result))
		    {
		       
		    	 array_push($statusarray,array(
		    	 								"uid"=>$row['idu'],
											 "pid"=>$row['id'],
											 "uprofilepic"=>$row['image'],
											 "uname"=>$row['uname'],
											 "imgname"=>$row['imgname'],
											 "time"=>$row['ptime'],
											 "type"=>$row['type'],
											 "likes"=>$row['likes']
									    )
							 );
		    }

		echo json_encode(array("postarray"=>$statusarray));	

		//$conn->close();
	}
	else
	{
		echo 'error';
	}
	mysqli_close($conn);
?>