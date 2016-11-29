<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		
		$uid = $_POST['uid'];
		$fname = $_POST['fname'];
		$lname = $_POST['lname'];
		$dateOB = $_POST['date'];
		$gender = $_POST['gender'];
		$intrest = $_POST['intrest'];
		$country = $_POST['country'];
		$city = $_POST['city'];
		$address = $_POST['address'];
		$workplace = $_POST['workplace'];
		$education = $_POST['education'];
		$website = $_POST['website'];
		$glink = $_POST['glink'];
		$fblink = $_POST['fblink'];
		$tlink = $_POST['tlink'];
		$bio = $_POST['bio'];

		require_once('dbConnect.php');
		
		$sql = "UPDATE users SET first_name='$fname', last_name='$lname', country='$country', location='$city', address='$address', work='$workplace', school='$education', website='$website', bio='$bio', facebook='$fblink', twitter='$tlink', gplus='$glink', gender=$gender, interests=$intrest, born='$dateOB' WHERE idu= $uid";
		
		
		if(mysqli_query($conn,$sql))
		{
			echo "success";	
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