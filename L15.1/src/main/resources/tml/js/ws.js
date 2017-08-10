var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8080/wsadmin");
    ws.onopen = function (event) {
    }
    ws.onmessage = function (event) {
        var json = JSON.parse(event.data);
        switch (json.messageType) {
            case "getParams":
                var data = json.details.data;
                for(var i in data) {
                    var id = data[i].name;
                    highlight(name);
                    document.getElementById(name).value = data[i].value;
                }
                break;
            default:
                sendMessage("error", {
                    "errorMessage": "Unrecognized message type: " + json.messageType
                });
        }
    }
    ws.onclose = function (event) {
    }
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
    var json = JSON.stringify({
        "messageType": messageType,
        "details": details
    });
    ws.send(json);
}