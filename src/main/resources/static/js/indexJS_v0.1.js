let session_username = sessionStorage.getItem("username");
let stompClient = null;

document.addEventListener('DOMContentLoaded', _ => {
    if (session_username == null) window.location.replace("/login");
    loadChatRoom().finally();

    document.getElementById("sending_form").addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(this);

        const formObject = Object.fromEntries(formData.entries());

        if (formObject.message_ts !== "") sendMessage(formObject.message_ts);

        document.getElementById("message_ts").value = "";
    });
});

function scrollDown() {
    let div = document.getElementById("messages_div");
    div.scrollTop = div.scrollHeight;
}

function connect() {
    let socket = new SockJS("/ws/global_chat_endpoint");
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/topic/messages", function (message) {
            addMessageToDiv(JSON.parse(message.body)[0]);
        });
    });
}

async function loadChatRoom() {
    let messages = await fetch("/api/messages").then(response => response.json());

    for (let msg of messages) {
        addMessageToDiv(msg);
    }

    connect();
    scrollDown();
}

function addMessageToDiv(message) {
    let new_msg_div = document.createElement("div");
    if (message.from === session_username) new_msg_div.setAttribute("class", "u_messages msgs");
    else new_msg_div.setAttribute("class", "nu_messages msgs");

    let time = new Date(message.timestamp);
    let time_normalized = new Date(time.getFullYear(),time.getMonth(),time.getDate());
    let today = new Date();
    let today_normalized = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    const oneDayInMilliseconds = 24 * 60 * 60 * 1000;

    let date = null;

    if (today.getDay() === time.getDay()) date = "Today";
    else if (today_normalized - time_normalized === oneDayInMilliseconds) date = "Yesterday";
    else date = time.getFullYear()+"/"+time.getMonth()+"/"+time.getDay();


    new_msg_div.innerHTML = "<div class='from_div'>" + message.from + "</div>"
        + "<div class='msg_div'>" + message.message + "</div>" + "<div class='time_div'>" + date + " " + time.getHours() + ":" + time.getMinutes() + "</div>";

    document.getElementById("messages_div").append(new_msg_div);
    scrollDown();
}

function logout() {
    sessionStorage.clear();
    window.location.replace("/login");
}

function sendMessage(msg) {
    let message = {
        from: session_username,
        message: msg
    }

    if (stompClient !== null) {
        stompClient.send("/ws/send_message", {}, JSON.stringify(message));
    }
}

window.addEventListener('beforeunload', function (e) {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
});