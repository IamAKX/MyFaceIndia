<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		
		$username = $_POST['username'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		

		require_once('dbConnect.php');
		
		$sql = "INSERT INTO users(username,email, password,image) VALUES ('$username','$email','$password','default.jpg')";
		
		
		if(mysqli_query($conn,$sql))
		{
			mail($email,"MyFaceIndia Registration Acknowledgement!!","Dear ".$username.",\n\t\t You are successfully registered to our server and now you will receive updates and information frequently.\n\nTHANK YOU!!!","From: admin@myfaceindia.com");
			
					 $sql = "SELECT * FROM users WHERE (email='$email' AND password='$password')";
					 $r = mysqli_query($conn,$sql);
		 
					 $res = mysqli_fetch_array($r);
					 
					 $result = array();
					 
					 array_push($result,array(
					 "username"=>$res['username'],
					 "email"=>$res['email'],
					 "img"=>$res['image'],
					 "id"=>$res['idu']
					 )
					 );
					 if(isset($res)){
					//displaying success 

					echo "success";
					echo json_encode(array("result"=>$result));	
				}else{
				//displaying failure
				echo "failure";
				}

		}else
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