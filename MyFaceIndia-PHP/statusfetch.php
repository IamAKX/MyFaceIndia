<?php

 if ($_SERVER["REQUEST_METHOD"] == "POST") 
 {

		$uid = $_REQUEST['uid'];
	
	require_once('dbConnect.php');
	// Check connection
	
	$sql = "SELECT * FROM status WHERE uid=$uid. ORDER BY sdatetime DESC";
   	

	$result = mysqli_query($conn,$sql);
	$statusarray = array();
	while($row =mysqli_fetch_assoc($result))
	    {
	       
	    	 array_push($statusarray,array(
										 
										 "status"=>$row['status'],
										 "stime"=>$row['sdatetime']
								    )
						 );
	    }

	echo json_encode(array("statusarray"=>$statusarray));	

	$conn->close();
	
}
else 
{
     echo "failure";
}
?>