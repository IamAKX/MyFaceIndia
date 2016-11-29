<?php 
//importing dbConnect.php script 
		include('dbConnect.php');	
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Getting values 
		$email = $_POST['email'];
	
		
		//Creating sql query
		$sql = "SELECT * FROM users WHERE (email='$email') or (username='$email')" ;
		
		
		
		$result = $conn->query($sql);

		if ($result->num_rows > 0) {
		     // output data of each row

		     while($row = $result->fetch_assoc()) 
		     {

		     	$name='';
		     	if($row['first_name']=='')
		     		$name=$row['username'];
		     	else
		     		
		     		{
		     		
		     		$name=$row['first_name'];

		     		mail($row['email'],"MyFaceIndia Password recovery","Dear ".$name.",\n\t\t Your password has been successfully recovered. Login in with following credential.\nUser name : ".$row[username]."\nEmail : ".$row[email]."\nPassword : ".$row[password]."\nTHANK YOU!!!","From: admin@myfaceindia.com");	
		         echo "success";
		         }
		     }
		} else 
		{
		     echo "failure";
		}

		mysqli_close($conn);
	}
?>	