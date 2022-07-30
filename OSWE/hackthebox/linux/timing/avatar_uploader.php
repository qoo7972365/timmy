<?php

include_once "header.php";

include_once "admin_auth_check.php";
?>

<script src="js/avatar_uploader.js"></script>

<style>
    .bg {
        padding: 30px;
        /* Full height */
        height: 100%;

        /* Center and scale the image nicely */
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
    }
</style>

<div class="bg" id="main">

    <div class="alert alert-success" id="alert-uploaded-success" style="display: none">

    </div>

    <div class="alert alert-danger" id="alert-uploaded-error" style="display: none">

    </div>

    <div class="container bootstrap snippets bootdey" style="margin-bottom: 150px">
        <h1 class="text-primary"><span class="glyphicon glyphicon-user"></span>Upload avatar</h1>
        <hr>


        <form class="form-inline" action="upload.php" method="post" enctype="multipart/form-data">
            <div class="form-group mb-2">
                <input type="file" name="fileToUpload" class="form-control" id="fileToUpload">
            </div>

            <button type="button" onclick="doUpload()" class="btn btn-primary">
                Upload Image
            </button>

        </form>

    </div>
</div>

<?php
include_once "footer.php";
?>
