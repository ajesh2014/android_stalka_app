<?php 

      mysql_connect("localhost","root","ajesh");

      mysql_select_db("android");

$method =$_REQUEST['method'];

$method_no = (int) ($method);




switch ($method_no)
{
case 1:

      getlist();

break;



case 2:

      updateLoc($_REQUEST['phone']);

break;


case 3:


createuser($_REQUEST['phone'],$_REQUEST['username'],$_REQUEST['email'],$_REQUEST['firstname'],$_REQUEST['lastname'],$_REQUEST['password'],$_REQUEST['type']);



break;







}







function createuser($phone,$UserName,$email,$firstname,$lastname,$Password,$type){


$checkavi = checkavi($phone, $UserName);

if($checkavi==0){

$result =mysql_query("INSERT INTO user (PHONE_ID,F_NAME,L_NAME,USERNAME,PASSWORD,EMAIL,TYPE) VALUES ("."'".$phone."'".","."'".$firstname."'".","."'".$lastname."'".","."'".$UserName."'".","."'".$Password."'".","."'".$email."'".","."'".$type."'".")");




$valid = mysql_query("select ID_USER from user where PHONE_ID ="."'".$phone."'");

//echo ("select ID_USER from user where PHONE_ID ="."'".$phone."'");


if(mysql_num_rows($valid)==1){

echo (1);

}if(mysql_num_rows($valid)==0){

echo(0);

}

}
else if($checkavi==1){

echo(2);


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


     
    mysql_close();


?>