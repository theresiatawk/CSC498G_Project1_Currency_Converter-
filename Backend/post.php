<?php
include("db_connection.php");

$currency_rate = $_POST["currency_rate"];
$amount_to_be_converted = $_POST["amount_to_be_converted"];
$currency = $_POST["currency"];


$query = $mysqli->prepare("INSERT INTO conversions(currency_rate, amount_to_be_converted, currency) VALUES (?, ?, ?);");
$query->bind_param("iis", $currency_rate, $amount_to_be_converted, $semster);
$query->execute();

$response = [];
$response["status"] = "Mabrouk!";

$json_respnse = json_encode($response);
echo $json_respnse;

?> 
