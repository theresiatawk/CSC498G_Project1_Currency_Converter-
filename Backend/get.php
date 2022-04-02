<?php

$ch_session = curl_init();

curl_setopt($ch_session, CURLOPT_URL, 'https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t20224216');
curl_setopt($ch_session, CURLOPT_RETURNTRANSFER, true);

$response = curl_exec($ch_session);

curl_close($ch_session);

$server_response = json_decode($response);

echo "<pre>";print_r($server_response);echo"</pre>";


?>