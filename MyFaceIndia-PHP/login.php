<?php 
	
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Getting values 
		$username = $_POST['email'];
		$password = $_POST['password'];
		
		//Creating sql query
		$sql = "SELECT * FROM users WHERE (email='$username' AND password='$password') OR (username='$username' AND password='$password')";
		
		//importing dbConnect.php script 
		require_once('dbConnect.php');
		
		 $r = mysqli_query($conn,$sql);
 
		 $res = mysqli_fetch_array($r);
		 
		 $result = array();
		 
		 array_push($result,array(
		 "username"=>$res['username'],
		 "email"=>$res['email'],
		 "img"=>$res['image'],
		 "id"=>$res['idu'],
		 "fname"=>$res['first_name'],
		 "lname"=>$res['last_name'],
		 "dob"=>$res['born'],
		 "gender"=>$res['gender'],
		 "interests"=>$res['interests'],
		 "country"=>$res['country'],
		 "city"=>$res['location'],
		 "address"=>$res['address'],
		 "work"=>$res['work'],
		 "school"=>$res['school'],
		 "website"=>$res['website'],
		 "google"=>$res['gplus'],
		 "facebook"=>$res['facebook'],
		 "twitter"=>$res['twitter'],
		 "bio"=>$res['bio']
		 )
		 );
		 
		 
		//if we got some result 
		if(isset($res)){
			//displaying success 
			echo "success";
			echo json_encode(array("result"=>$result));	
		}else{
			//displaying failure
			echo "failure";
		}
		mysqli_close($conn);
	}
?>	