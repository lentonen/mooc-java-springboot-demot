var url = contextRoot + "tasks"
var http = new XMLHttpRequest()
var laskuriMista = 0

function addTask(){
    var task = {
        name: document.getElementById("task").value
    }
    http.open("POST", url)
    http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    http.send(JSON.stringify(task))
    paivitaLista()
    document.getElementById("task").value = ""  
}


http.onreadystatechange = function() {
    if (this.readyState != 4 || this.status != 200) {
        return
    }
    var ul = document.getElementById("list")
    var task = JSON.parse(this.responseText)
    for (var i = laskuriMista; i < task.length; i++) {
        var li = document.createElement("li");
        li.appendChild(document.createTextNode(task[i].name));
        ul.appendChild(li);
    }
    laskuriMista = task.length
}

function paivitaLista(){
    http.open("GET", url)
    http.send()
}