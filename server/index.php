<?php 

  $con = mysql_connect("localhost","root","ajesh");

      mysql_select_db("android");

$method =$_REQUEST['method'];

$method_no = (int) ($method);

switch ($method_no)
{
case 1:

      getAllUsers();

break;



case 2:

      updateLoc($_REQUEST['phone'],$_REQUEST['lat'],$_REQUEST['lng']);

break;


case 3:

	createuser($_REQUEST['phone'],$_REQUEST['username'],$_REQUEST['email'],$_REQUEST['firstname'],$_REQUEST['lastname'],$_REQUEST['password'],$_REQUEST['type'],$_REQUEST['msg']);


break;


case 4:

	getUserLoc($_REQUEST['phone']);


break;


case 5:

	friend_req($_REQUEST['req'],$_REQUEST['user']);


break;

case 6:

	getFriendReq($_REQUEST['phone_id']);



break;

case 7:

	getFriends($_REQUEST['phoneID']);



break;

case 8:

	AcceptFriend($_REQUEST['acc'],$_REQUEST['req']);

break;

case 9:

	DeclineFriend($_REQUEST['acc'],$_REQUEST['req']);

break;

case 10:

	readMessages($_REQUEST['phoneID']);

break;

case 11:

	readmessage($_REQUEST['phoneID'],$_REQUEST['senderID']);

break;


case 12:

	sendMessage($_REQUEST['phoneid'],$_REQUEST['to'],$_REQUEST['message']);

break;


case 13:

	deleteMessage($_REQUEST['from'],$_REQUEST['to']);

break;


case 14:


	getTypesuser();

break;


case 15:

	getTypeList($_REQUEST['type']);


break;

case 16:


	searchUser($_REQUEST['user']);




break;

case 17:

	readSentMessages($_REQUEST['phoneID']);

break;

case 18:

	deleteFriend($_REQUEST['phone'],$_REQUEST['friendID']);

break;


}






///////////////////////////////////////////////////////////////////////////////////////////////////


function getAllUsers(){


$q=mysql_query("select user.ID_USER,user.PHONE_ID, F_NAME,L_NAME,LAT,LNG,user.MESS_CHK from user inner join location on user.ID_USER = location.ID_USER");

      while($e=mysql_fetch_assoc($q)){

              $output[]=$e;
}
      
     print(json_encode($output));




}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
function updateLoc($phone,$lat,$lng){



 $userid;


$result =mysql_query("select ID_USER from user where PHONE_ID ="."'".$phone."'");





while($row = mysql_fetch_array($result))
  {
  $userid= $row['ID_USER'] ;
 
  }




if(mysql_num_rows($result)==0){

echo(2);

}





else if(mysql_num_rows($result)==1){

//echo("select ID_USER from location where ID_USER ="."'".$userid."'");



$query =mysql_query("select * from location where ID_USER ="."'".$userid."'");

$locid;

while($row = mysql_fetch_array($query))
  {
$locid=$row['ID_USER'] ;
 
  }



if( empty($locid)==false){


mysql_query("UPDATE location SET LAT="."'".$lat."'".",LNG="."'".$lng."'"."where ID_USER ="."'".$userid."'");

}
else {

mysql_query("INSERT INTO location (ID_USER,LAT,LNG) VALUES ("."'".$userid."'" .","."'".$lat."'".","."'".$lng."'".")" );

}





}



}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

function createuser($phone,$UserName,$email,$firstname,$lastname,$Password,$type, $msg){


$checkavi = checkavi($phone, $UserName);

if($checkavi==0){

$result =mysql_query("INSERT INTO user (PHONE_ID,F_NAME,L_NAME,USERNAME,PASSWORD,EMAIL,TYPE,MESS_CHK) VALUES ("."'".$phone."'".","."'".$firstname."'".","."'".$lastname."'".","."'".$UserName."'".","."'".$Password."'".","."'".$email."'".","."'".$type."'".","."'".$msg."'".")");




$valid = mysql_query("select ID_USER from user where PHONE_ID ="."'".$phone."'");

//echo ("select ID_USER from user where PHONE_ID ="."'".$phone."'");


if(mysql_num_rows($valid)==1){


//echo ("select ID_USER from user where PHONE_ID ="."'".$phone."'");


echo("1");




}if(mysql_num_rows($valid)==0){

echo("0");

}

}
else if($checkavi==1){

echo("2");


}



}


function checkavi($phone,$username){

$avaliable;


//$result =mysql_query("select * from user where PHONE_ID ='".$phone."' OR USERNAME ='".$username."'");

//echo("'".$phone."'".","."'".$username."'");


$result =mysql_query("select * from user where PHONE_ID ="."'".$phone."'"." or USERNAME ="."'".$username."'");



if(mysql_num_rows($result)==0){
 



$avaliable = 0;


}else{




$avaliable =1;


}


return $avaliable ;





}
/////////////////////////////////////////////////////////////////////////


function getUserLoc($phoneid){




$q=mysql_query("select user.PHONE_ID, LAT,LNG from user inner join location on user.ID_USER = location.ID_USER where user.PHONE_ID ="."'".$phoneid."'");

      while($e=mysql_fetch_assoc($q)){

              $output[]=$e;
}
      
     print(json_encode($output));





}
/////////////////////////////////////////////////////////////////////////////

function friend_req($req,$user){

$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$req."'");

$req_id;
$user_id=0;


while($row = mysql_fetch_array($query))
  {
$req_id=$row['ID_USER'] ;
 
  }


$query2 =mysql_query("select ID_USER from user where PHONE_ID ="."'".$user."'");
while($row = mysql_fetch_array($query2))
  {
$user_id=$row['ID_USER'] ;

if($user_id==0){

echo("0");
 }


  }
////////// check to see if there is alreayd a request from the user to the same person

$query3 =mysql_query("select * from friend_req where sender_user_ID ="."'".$req_id."'"."and recip_user_ID ="."'".$user_id."'");

if(mysql_num_rows($query3)==1){


while($row = mysql_fetch_array($query3))
  {

if($row['sender_user_ID ']== $req_id){
echo("0");

}
 
  }


}




// cehcking if there user who was reqestd made a reqest erlier


$query4 =mysql_query("select * from friend_req where sender_user_ID ="."'".$user_id."'"."and recip_user_ID ="."'".$req_id."'");

if(mysql_num_rows($query4)==1){


AcceptFriend($req_id,$user_id);


}



/////  check if they are already friends


$query5 =mysql_query("Select * from friends where ID_FriendLink1 ="."'".$user_id."'"." and ID_FriendLink2 ="."'".$req_id."'");

if(mysql_num_rows($query5)==1){

echo("0");


}

// insert details into the friends request

if(mysql_num_rows($query3)==0){

$query5 =mysql_query("INSERT INTO friend_req(sender_user_ID,recip_user_ID,confirm)VALUES("."'".$user_id."'".","."'".$req_id."'".",'p')");



echo ("1");

}


}



//////////////////////////////////////////////////////////////////////////////////////




function getFriendReq($phoneid){


$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$phoneid."'");

while($row = mysql_fetch_array($query))
  {
$user_id=$row['ID_USER'] ;
 
  }


$q=mysql_query("select sender_user_ID,confirm, F_NAME,L_NAME, LAT,LNG from friend_req inner join user on ID_USER = sender_user_id  join location on sender_user_id = location.ID_USER where recip_user_ID ="."'".$user_id."'"."and confirm ='p'");


while($e=mysql_fetch_assoc($q)){

              $output[]=$e;	

 
}

echo(json_encode($output));


 
}


//////////////////////////////////////////////////////////////////

function AcceptFriend($accepter,$requester){


$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$accepter."'");

$req=0;

while($row = mysql_fetch_array($query))
  {
$accpt_id=$row['ID_USER'] ;
$req = $requester;
 
  }



 
$query2 =mysql_query("INSERT INTO friends(ID_FriendLink1,ID_FriendLink2)VALUES("."'".$req."'".","."'".$accpt_id."'".")");




$query3 =mysql_query("INSERT INTO friends(ID_FriendLink1,ID_FriendLink2)VALUES("."'".$accpt_id."'".","."'".$req."'".")");




$query4 = mysql_query("select Fri_Req_ID from Friend_req where sender_user_ID ="."'".$accpt_id."'"." or sender_user_ID ="."'".$req."'"." and recip_user_ID ="."'".$accpt_id."'"." or recip_user_ID ="."'".$req."'");

while($row = mysql_fetch_array($query4))
  {
$req_id=$row['Fri_Req_ID'] ;
 
  }


$query5 = mysql_query("update Friend_req set confirm ='a' where Fri_Req_ID ="."'".$req_id."'");



echo ("1");   




}
//////////////////////////////////////////////////////

function DeclineFriend($accepter,$requester){


$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$accepter."'");

$req=0;

while($row = mysql_fetch_array($query))
  {
$accpt_id=$row['ID_USER'] ;
$req = $requester;
 
  }






$q=mysql_query("select * from Friend_req where sender_user_ID ="."'".$accpt_id."'"." or sender_user_ID ="."'".$req."'"." and recip_user_ID ="."'".$accpt_id."'"." or recip_user_ID ="."'".$req."'");

$req_id=0;
while($row = mysql_fetch_array($q))
  {
$req_id=$row['Fri_Req_ID'] ;
 
  }



$query5 = mysql_query("update Friend_req set confirm ='d' where Fri_Req_ID ="."'".$req_id."'");


echo ("1");   



}

////////////////////////////////////////////////


function getFriends($phoneid){
$user_id=0;

$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$phoneid."'");

while($row = mysql_fetch_array($query))
  {
$user_id=$row['ID_USER'] ;
 
  }

$query2 =mysql_query ("Select friends.ID_FriendLink2, user.F_NAME,user.L_NAME, user.USERNAME, LAT,LNG, PHONE_ID from friends inner join user on ID_USER =ID_FriendLink2 inner join location  on location.ID_USER =ID_FriendLink2  where ID_FriendLink1 = "."'".$user_id."'");

while($e=mysql_fetch_assoc($query2)){

              $output[]=$e;

 
}

echo(json_encode($output));


}


/////////////////////////////////////////////////////////////////

function sendMessage($from, $to, $message ){

$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$from."'");

$sender =0;


while($row = mysql_fetch_array($query))
  {

$sender=$row['ID_USER'] ;

 
  }



 $query1 = "INSERT INTO Messages(from_id, to_id ,mess,stamp)VALUES("."'".$sender."'".","."'".$to."'".","."'".$message."'".", NOW())";

 if(mysql_query($query1)) {
	echo ("0");

} else{


echo ("1");

}


}

/////////////////////////////////////////////////////

function readMessages($phone){

$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$phone."'");

$to_user =0;


while($row = mysql_fetch_array($query))
  {

$to_user=$row['ID_USER'] ;

 }




$query = mysql_query("select from_id,to_id,F_NAME,L_NAME, USERNAME, count(mess_id) as no_mess from messages join user on from_id= ID_USER where  to_id="."'".$to_user."'"."order by stamp asc");

while($e=mysql_fetch_assoc($query)){

              $output[]=$e;

 
}

echo(json_encode($output));





}




////////////////////////////////////////////////////////

function readmessage($reader,$from){



$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$reader."'");

$to_user =0;


while($row = mysql_fetch_array($query))
  {

$to_user=$row['ID_USER'] ;

 
  }

$query2 = mysql_query("select USERNAME,mess from messages join user on from_id = ID_USER where from_id="."'".$to_user."'" ." or from_id = "."'".$from."'"." and to_id="."'".$from."'"." or to_id ="."'".$to_user."'"."order by stamp desc");

while($e=mysql_fetch_assoc($query2)){

              $output[]=$e;

 
}

echo(json_encode($output));






}
///////////////////////////////////////////////
function deleteMessage($from, $to){



$query1 = "delete from messages where from_id ="."'".$from."'"." or  from_id ="."'".$to."'"." and to_id="."'".$to."'"." or to_id ="."'".$from."'";


if(mysql_query($query1)) {
	echo ("1");

} else{


echo ("0");

}




}
///////////////////////////////////////////////////


function getTypesuser(){


$query2 = mysql_query("select Type,COUNT(TYPE) as No from user join location  on user.ID_USER =location.ID_USER group by TYPE");


while($e=mysql_fetch_assoc($query2)){

              $output[]=$e;

 
}

echo(json_encode($output));


}


//////////////////////////////////////////////////////
function getTypeList($type){


$q=mysql_query("select user.PHONE_ID, F_NAME,L_NAME,USERNAME,LAT,LNG from user inner join location on user.ID_USER = location.ID_USER where TYPE ="."'".$type."'");

      while($e=mysql_fetch_assoc($q)){

              $output[]=$e;
}
      
     print(json_encode($output));




}
////////////////////////////////////////////////////////////
function searchUser($user){


$q=mysql_query("select user.ID_USER,MESS_CHK,PHONE_ID, F_NAME,L_NAME,USERNAME,LAT,LNG from user inner join location on user.ID_USER = location.ID_USER where F_NAME like"."'".$user."%'"." or L_NAME like"."'".$user."%'"." or USERNAME like"."'".$user."%'");

      while($e=mysql_fetch_assoc($q)){

              $output[]=$e;
}
      
     print(json_encode($output));




}
///////////////////////////////////////////////////////////////

function readSentMessages($phone){

$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$phone."'");

$to_user =0;


while($row = mysql_fetch_array($query))
  {

$to_user=$row['ID_USER'] ;

 }




$query = mysql_query("select from_id,to_id,F_NAME,L_NAME, USERNAME, count(mess_id) as no_mess from messages join user on from_id= ID_USER where  from_id="."'".$to_user."'"."order by stamp asc");

while($e=mysql_fetch_assoc($query)){

              $output[]=$e;

 
}

echo(json_encode($output));





}
////////////////////////////////////////////////////
function deleteFriend($phone, $to){


$query =mysql_query("select ID_USER from user where PHONE_ID ="."'".$phone."'");

$deleter =0;


while($row = mysql_fetch_array($query))
  {

$deleter=$row['ID_USER'] ;

 }

$query1 = "delete from friends where ID_FriendLink1 ="."'".$deleter."'"." or  ID_FriendLink2 ="."'".$deleter."'"." and ID_FriendLink1="."'".$to."'"." or ID_FriendLink2 ="."'".$to."'";


if(mysql_query($query1)) {
	echo ("1");

} else{


echo ("0");

}




}
/////////////////////////////////////////////////////////
     
    mysql_close();


?>