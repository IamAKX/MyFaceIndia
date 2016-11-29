<?php

	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		
		$uid = $_POST['uid'];
		
		$likenotification = $_POST['likenotification'];
		$commentnotification = $_POST['commentnotification'];
		$messagenotification = $_POST['messagenotification'];
		$chatnotification = $_POST['chatnotification'];
		$friendnotification = $_POST['friendnotification'];
		$groupnotification = $_POST['groupnotification'];
		$notificationsound = $_POST['notificationsound'];
		$emailcomment = $_POST['emailcomment'];
		$emaillike = $_POST['emaillike'];
		$emailfriendship = $_POST['emailfriendship'];
		$emailgroupinvite = $_POST['emailgroupinvite'];

		require_once('dbConnect.php');
		
		$sql = "UPDATE users SET notificationl=$likenotification, notificationc=$commentnotification, notifications=$messagenotification, notificationd=$chatnotification, notificationf=$friendnotification, notificationg=$groupnotification, email_comment=$emailcomment, email_like=$emaillike, email_new_friend=$emailfriendship, email_group_invite=$emailgroupinvite, sound_new_notification=$notificationsound WHERE idu= $uid";
		
		
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