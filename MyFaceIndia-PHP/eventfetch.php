<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$uid = $_POST['uid'];
		$typ = $_POST['type'];
		require_once('dbConnect.php');
		
		if($typ==0)
			$sql = "SELECT * from events ORDER BY edatetime DESC";	
		else
			$sql = "SELECT * from events where type = $typ ORDER BY edatetime DESC";	
		$result = mysqli_query($conn,$sql);
		
		$eventarray = array();
		
		while($row = mysqli_fetch_assoc($result))
		    {
		       
		    	 array_push($eventarray,array(
											 "id"=>$row['id'],
											 "uname"=>$row['uname'],
											 "name"=>$row['name'],
											 "url"=>$row['url'],
											 "type"=>$row['type'],
											 "edatetime"=>$row['edatetime'],
											 "uimage"=>$row['uimage']
									    )
							 );
		    }

		echo json_encode(array("events"=>$eventarray));	

		//$conn->close();
	}
	else
	{
		echo 'error';
	}
	mysqli_close($conn);
?>