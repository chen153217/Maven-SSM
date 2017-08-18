/**
 * Created by chenhansen on 2017/8/1.
 */
var span=document.getElementById("fileName");
function getFile(){
    var file=document.getElementById("file");
    var files=file.files;
    span.innerText=files[0].name;
}

function isAvailable(){
    var file=document.getElementById("file");
    if(file.value==""){
        alert("请选择要提交的文件");
        return false;
    }
    else return  true;
}