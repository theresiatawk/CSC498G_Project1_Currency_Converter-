<?php

// Getting the current time in Beirut so if the api is updated from the website it will update automatically here
date_default_timezone_set('Asia/Beirut');

$year = intval(date('Y'));
$month = intval(date('m'));
$day = intval(date('d'));
$hour = intval(date('H'));


// Creating the variant part of the api which is the date and time
$variant_part = strval($year.$month.$day.$hour);

$url = "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t$variant_part";


// Getting the json object from the api using curl function
$ch_session = curl_init();

curl_setopt($ch_session, CURLOPT_URL, $url);
curl_setopt($ch_session, CURLOPT_RETURNTRANSFER, true);

$response = curl_exec($ch_session);

curl_close($ch_session);

// Getting this json object and parsing it to an array
$server_response = (array) json_decode($response,true);

// Get the array size
$array_length =  sizeof($server_response['buy']);

// Getting the element on the last index from the specified array
$json ["Rate"] = $server_response['buy'][$array_length - 1][1];

//echo "<pre>";print_r($server_response);echo"</pre>";

//echo "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t$variant_part";
$json_response = json_encode($json);
echo $json_response;

?>