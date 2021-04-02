<?php
  $ACS_CONFIG = array(); //DO NOT EDIT
/* ######################## DATABASE ######################## */

  // Database server
  $ACS_CONFIG["db_server"] = "localhost";
  
  // Database name
  $ACS_CONFIG["db_name"] = "acs";
  
  // Database username
  $ACS_CONFIG["db_user"] = "root";

  // Database password
  $ACS_CONFIG["db_password"] = "aCs2009offsec";
  
  // Database table to store comments
  // (will be created automatically)
  $ACS_CONFIG["db_table"] = "advanced_comment_system";

/* ##################### CONFIGURATION ###################### */

  // Complete URL of the script
  // (begins with http://, ends with slash)
  $ACS_CONFIG["url"] = "http://yourdomain.com/advanced_comment_system/";
  
  // Error message if inserting a new comment failed
  $ACS_CONFIG["error_message"] = "Your comment could not be inserted. Please check your inputs.";
  
  // Success message if inserting a new comment succeeded
  $ACS_CONFIG["success_message"] = "Your comment has been inserted. Thank you.";
  
  // Minimum length for field "name"
  $ACS_CONFIG["min_length_name"] = 3;
  
  // Maximum length for field "name" (maximum 255)
  $ACS_CONFIG["max_length_name"] = 255;
  
  // Minimum length for field "message"
  $ACS_CONFIG["min_length_message"] = 3;
  
  // Maximum length for field "message" (maximum 65000)
  $ACS_CONFIG["max_length_message"] = 5000;
  
  // Enable anti-spam-code?
  $ACS_CONFIG["anti_spam_enabled"] = true;
  
  // Enable slider?
  $ACS_CONFIG["slider_enabled"] = true;
  
  // Enable email notification on new comment?
  $ACS_CONFIG["email_enabled"] = false;
  
  // Enable text counter for message field
  $ACS_CONFIG["textcounter_enabled"] = true;
  
  // Email address to send notification to
  $ACS_CONFIG["notification_email"] = "you@yourdomain.com";
  
  // Subject of notification email
  $ACS_CONFIG["notification_subject"] = "New comment @ Advanced comment system";
  
  // Title of comment section
  $ACS_CONFIG["title"] = "Comments";
  
  // Display comments count
  $ACS_CONFIG["count_enabled"] = true;
  
  // Allow users to hide comments for themselves
  $ACS_CONFIG["allow_hide"] = true;
  
  // Date format (PHP Date)
  $ACS_CONFIG["date_format"] = "m/d/Y";
  
  // Time format (PHP Date)
  $ACS_CONFIG["time_format"] = "h:i:s a";
  
  // Date-Time seperator
  $ACS_CONFIG["date-time_seperator"] = " @ ";
  
  // Message if no comments found
  $ACS_CONFIG["no_comments"] = "No comments found. Be the first to write a comment! Fill in the form below.";
  
  // Text for "Leave a comment"
  $ACS_CONFIG["leave_comment"] = "Leave a comment";
  
  // Text for "Your name"
  $ACS_CONFIG["your_name"] = "Your name";
  
  // Text for "Your message"
  $ACS_CONFIG["your_message"] = "Your message";
  
  // Text for "Remember"
  $ACS_CONFIG["remember"] = "Remember";
  
  // Text for anti-spam-code textfield (only needed if anti-spam-code is enabled)
  $ACS_CONFIG["insert_letters"] = "Insert these letters into the textfield below:";
  
  // Text for slider (only needed if slider is enabled)
  $ACS_CONFIG["drag_slider"] = "Drag the slider to the right";
  
  // Text for "Submit"
  $ACS_CONFIG["submit"] = "Submit";
  
  // Length of anti-spam-code
  $ACS_CONFIG["anti_spam_code_length"] = 4;
  
  // New comments on top or bottom (allowed values: top, bottom);
  $ACS_CONFIG["comments_order"] = "top";
  
  // Password for administration
  $ACS_CONFIG["admin_password"] = "admin";
  
  // Text for "Hide comments"
  $ACS_CONFIG["hide_comments"] = "Hide comments.";
  
  // Text for "Show comments"
  $ACS_CONFIG["show_comments"] = "Show comments.";
  
  // Text for "Comments are hidden"
  $ACS_CONFIG["comments_hidden"] = "Comments are hidden.";
  
  // Text for "Write a comment"
  $ACS_CONFIG["write_comment"] = "Write a comment.";
?>
