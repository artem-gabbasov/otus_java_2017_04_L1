// вебсокет
var ws;
// флаг, указывающий, открыто ли соединение
var ready = false;
// очередь выделенных элементов (сохраняются для снятия с них выделения)
var queue = [];

init = function () {
    ws = new WebSocket("ws://localhost:8080/wsadmin");
    ws.onopen = function (event) {
        ready = true;
    }
    ws.onmessage = function (event) {
        var json = JSON.parse(event.data);
        switch (json.messageType) {
            // переданы обновления в значениях параметров кэша
            case "getParams":
                var data = json.details.data;
                for(var i in data) {
                    var id = data[i].name;
                    highlight(id);
                    document.getElementById(id).innerHTML = data[i].value;
                }
                break;
            // передано сообщение, что пользователь больше не авторизован
            case "notAuthorized":
                window.location = "/restricted/admin";
                break;
            default:
                sendMessage("error", {
                    "errorMessage": "Unrecognized message type: " + json.messageType
                });
        }
    }
    ws.onclose = function (event) {
        ready = false;
    }
};

// подсвечивает параметр кэша, значение которого изменено последним сообщением с сервера
highlight = function(id) {
  $elem = document.getElementById(id);
  // затемняем фон, а потом осветлим его обратно
  $elem.style.background = "#ddd";
  // складываем выделенный элемент в очередь (просто использовать тот же элемент нельзя, т.к. за время задержки ссылка может измениться)
  queue.push($elem);
  setTimeout('queue.shift().style.background = "#fff"', 750);
};

// отправка сообщения для сохранения пользователя
saveUser = function () {
    sendMessage("dbService", {
        "action": "saveUser",
        "params": {
            "userId": getUserID()
        }
    });
};

// отправка сообщения для загрузки пользователя
loadUser = function () {
    sendMessage("dbService", {
        "action": "loadUser",
        "params": {
            "userId": getUserID()
        }
    });
};

// отправка сообщения для очистки кэша
clearCache = function () {
    sendMessage("dbService", {
        "action": "clearCache",
        "params": {}
    });
};

function getUserID () {
    return document.getElementById("userID").value;
};

// функция, реализующая общие шаги для отправки сообщения на сервер
function sendMessage(messageType, details) {
    if (!ready) {
        init();
    }

    var json = JSON.stringify({
        "messageType": messageType,
        "details": details
    });
    ws.send(json);
}