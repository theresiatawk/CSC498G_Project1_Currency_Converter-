<?php

date_default_timezone_set('Asia/Beirut');

$year = intval(date('Y'));
$month = intval(date('m'));
$day = intval(date('d'));
$hour = intval(date('H'));


// Creating the variant part of the api which is the date and time
$variant_part = strval($year.$month.$day.$hour);

$url = "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t$variant_part";

$ch_session = curl_init();

curl_setopt($ch_session, CURLOPT_URL, $url);
curl_setopt($ch_session, CURLOPT_RETURNTRANSFER, true);

$response = curl_exec($ch_session);

curl_close($ch_session);

$server_response = (array) json_decode($response,true);
$array_length =  sizeof($server_response['buy']);

//echo "<pre>";print_r($server_response);echo"</pre>";
//echo "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t$variant_part";
$json_response = json_encode($server_response['buy'][$array_length - 1][1]);
echo $json_response;

?>