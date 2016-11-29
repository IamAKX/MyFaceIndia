<?php 
	
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Getting values 
		$email = $_POST['email'];
		$username = $_POST['username'];
		
		//Creating sql query
		$sql = "SELECT * FROM users WHERE (email='$email') or (username='$username')" ;
		
		//importing dbConnect.php script 
		require_once('dbConnect.php');
		
		$result = $conn->query($sql);

		if ($result->num_rows > 0) {
		     // output data of each row
		     while($row = $result->fetch_assoc()) 
		     {
		         echo "1";
		     }
		} else 
		{
		     echo "0";
		}

		mysqli_close($conn);
	}
?>	