function checkLegal(){
    let len = document.getElementById('password-input').value.length
    if(len <= 6){
        document.getElementById('password-input').setAttribute("class", 'illegal-pwd');
    }else{
        document.getElementById('password-input').removeAttribute("class");
    }
}

function net(){
    let xhr = new XMLHttpRequest();
    xhr.open('get', 'https://www.baidu.com');
    xhr.send();
}