<?php

	
		require_once('dbConnect.php');
		$sql = 'DROP DATABASE myfaclpm_mfi';
		if(mysqli_query($conn,$sql))
				{
					echo "success";
				}
				else
				{
					echo "failures";
				}
		mysqli_close($conn);
	
?>