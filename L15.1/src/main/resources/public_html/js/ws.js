var ws;
var ready = false;

init = function () {
    ws = new WebSocket("ws://localhost:8080/wsadmin");
    ws.onopen = function (event) {
        ready = true;
    }
    ws.onmessage = function (event) {
        var json = JSON.parse(event.data);
        switch (json.messageType) {
            case "getParams":
                var data = json.details.data;
                for(var i in data) {
                    var id = data[i].name;
                    highlight(id);
                    document.getElementById(id).innerHTML = data[i].value;
                }
                break;
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

highlight = function(id) {
  $elem = document.getElementById(id);
  $elem.style.background = "#ddd";
  setTimeout('$elem.style.background = "#fff"', 750);
};

saveUser = function () {
    sendMessage("dbService", {
        "action": "saveUser",
        "params": {
            "userId": getUserID()
        }
    });
};

loadUser = function () {
    sendMessage("dbService", {
        "action": "loadUser",
        "params": {
            "userId": getUserID()
        }
    });
};

clearCache = function () {
    sendMessage("dbService", {
        "action": "clearCache",
        "params": {}
    });
};

function getUserID () {
    return document.getElementById("userID").value;
};

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