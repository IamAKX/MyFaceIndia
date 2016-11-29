<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		
		$uname = $_POST['uname'];
		require_once('dbConnect.php');
		
		$sql = "SELECT idu,username,image FROM users WHERE username LIKE '%$uname%'";	
		$result = mysqli_query($conn,$sql);
		
		$uarray = array();
		while($row = mysqli_fetch_assoc($result))
		    {
		       
		    	 array_push($uarray,array(
		    	 							"uid"=>$row['idu'],
											 "pname"=>$row['username'],
											 "uimg"=>$row['image'],
											
									    )
							 );
		    }

		echo json_encode(array("userarray"=>$uarray));	

		//$conn->close();
	}
	else
	{
		echo 'error';
	}
	mysqli_close($conn);
?>