function checkLegal(){
    let len = document.getElementById('password-input').value.length
    if(len <= 6){
        document.getElementById('password-input').setAttribute("class", 'illegal-pwd');
    }else{
        document.getElementById('password-input').removeAttribute("class");
    }
}



function updateTime(){
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function (){
        if (xhr.readyState === 4 && xhr.status === 200){
            document.getElementById('time').innerText = xhr.responseText
        }
    };
    xhr.open('get', 'time', true);
    xhr.send();
}