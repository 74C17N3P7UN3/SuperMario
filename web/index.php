<?php

error_reporting(0);

if ($_SERVER["REQUEST_METHOD"] === "GET") {
   $connection = mysqli_connect("localhost", "root", "", "SuperMario");

   // Set the response Content-Type header as application/xml
   header("Content-Type: application/xml; charset=utf-8");

   $response = "<?xml version='1.0' encoding='UTF-8'?>";
   $response .= "<leaderboard>";

   try {
      $scores = $connection -> query("SELECT * FROM leaderboard ORDER BY points DESC LIMIT 20");
      foreach ($scores as $score) {
         $response .= "<score>";
         $response .= "<username>" . $score['username'] . "</username>";
         $response .= "<points>" . $score['points'] . "</points>";
         $response .= "<timestamp>" . $score['timestamp'] . "</timestamp>";
         $response .= "</score>";
      };
   } catch (\Exception $e) {}

   $response .= "</leaderboard>";
   echo $response;

   $connection -> close();
}

if ($_SERVER["REQUEST_METHOD"] === "POST") {
   $connection = mysqli_connect("localhost", "root", "", "SuperMario");

   // Get the raw application/xml request data
   $raw_request = file_get_contents("php://input");

   // Load the raw application/xml data as a Document to validate it
   $xml = new DOMDocument();
   $xml -> loadXML($raw_request);

   try {
      if ($xml -> schemaValidate("schemas/score.xsd")) {
         // Convert the raw application/xml data into an object
         $score = simplexml_load_string($raw_request);

         $username = $score -> username;
         $points = $score -> points;
         $timestamp = $score -> timestamp;

         $response = $connection -> query("INSERT INTO leaderboard VALUES ('$username', $points, '$timestamp')");
      }
   } catch (\Exception $e) {}

   // If the score was successfully inserted, return 'Success' and 'Error' otherwise
   echo isset($response) && $response ? "Success" : "Error";

   $connection -> close();
}
