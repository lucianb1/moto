var ws;
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}

var i = 0;
function connect() {
        //connect to stomp where stomp endpoint is exposed
        var socket = new SockJS("/motorzz/greeting?tokenValue=XXXXX");
        ws = Stomp.over(socket);

        ws.connect({}, function (frame) {
            setConnected(true);
            ws.subscribe("/user/queue/errors", function (message) {
                alert("Error " + message.body);
            });

            ws.subscribe("/user/queue/reply", function (message) {

                showGreeting(message.body);
            });
        }, function (error) {
            setConnected(false);
            alert("STOMP error " + error);
        });
}

function disconnect() {
	if (ws != null) {
		ws.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendName() {
	console.log('i value: ',i);
	i = 0;
	var data = JSON.stringify({
		'name' : $("#name").val()
	})
	ws.send("/app/message", {}, data);
}

function showGreeting(message) {
	i++;
	$("#greetings").append("<tr><td> " + message + "</td></tr>");
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendName();
	});
});
