<?php

$md5image1 = md5(file_get_contents("images/1650879751.png"));
$md5image2 = md5(file_get_contents("images/1650879752.png"));
if ($md5image1 == $md5image2) {
    echo 1 . " md5 1: " . $md5image1;
}else{
    echo 0 . " md5 1: " . $md5image1;
}


?>