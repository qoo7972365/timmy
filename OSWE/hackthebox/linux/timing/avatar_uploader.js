$(document).ready(function () {
    document.getElementById("main").style.backgroundImage = "url('/image.php?img=images/background.jpg'"
});

function doUpload() {

    if (document.getElementById("fileToUpload").files.length == 0) {
        document.getElementById("alert-uploaded-error").style.display = "block"
        document.getElementById("alert-uploaded-success").style.display = "none"
        document.getElementById("alert-uploaded-error").textContent = "No file selected!"
    } else {

        let file = document.getElementById("fileToUpload").files[0];  // file from input
        let xmlHttpRequest = new XMLHttpRequest();
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {


                if (xmlHttpRequest.responseText.includes("Error:")) {
                    document.getElementById("alert-uploaded-error").style.display = "block"
                    document.getElementById("alert-uploaded-success").style.display = "none"
                    document.getElementById("alert-uploaded-error").textContent = xmlHttpRequest.responseText;
                } else {
                    document.getElementById("alert-uploaded-error").style.display = "none"
                    document.getElementById("alert-uploaded-success").textContent = xmlHttpRequest.responseText;
                    document.getElementById("alert-uploaded-success").style.display = "block"
                }

            }
        };
        let formData = new FormData();

        formData.append("fileToUpload", file);
        xmlHttpRequest.open("POST", 'upload.php');
        xmlHttpRequest.send(formData);
    }
}

