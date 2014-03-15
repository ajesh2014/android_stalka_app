<?php 
 $con=mysql_connect("localhost","root","ajesh");

      mysql_select_db("android");

$to_user=30;
$phone ="9774d56d682e549c";
$from=29;
$message="hello";
echo ("select * from messages where from_id="."'".$to_user."'" ." or from_id = "."'".$from."'"." and to_id="."'".$from."'"." or to_id ="."'".$to_user."'"."order by stamp asc");


$from = 30;
$to= 29;

echo "delete from messages where from_id ="."'".$from."'"." or  from_id ="."'".$to."'"." and to_id="."'".$to."'"." or to_id ="."'".$to."'";

//select * from messages where from_id='29' or from_id = '30' and to_id='30' or to_id ='29'order by stamp asc;

?>

