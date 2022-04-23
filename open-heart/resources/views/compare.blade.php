<?php

$md5image1 = md5(file_get_contents("images/1650645110.jpg"));
$md5image2 = md5(file_get_contents("images/1650645112.jpg"));
if ($md5image1 == $md5image2) {
    echo 1;
}else{
    echo 0;
}


?>