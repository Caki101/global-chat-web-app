if (sessionStorage.getItem("username") != null) window.location.replace(`http://${serverIp}`);
let serverIp = null;

async function login(username, password) {
    let user = await fetch(`http://${serverIp}/user/` + username).then(res => res.json());

    if (user[0].username === username && user[0].password === password) {
        sessionStorage.setItem("username", username);
        window.location.replace(`http://${serverIp}`);
    }
    else if (user[0].username === null) {
        return false;
    }
    //return true;
}

async function loadIp() {
    try {
        const response = await fetch('config.json');
        const config = await response.json();
        serverIp = config.serverIp;
    } catch (error) {
        console.error('Error loading config: ', error);
    }
}

function goToRegistration(){
    window.location.replace(`http://${serverIp}/registration`);
}

document.addEventListener('DOMContentLoaded', (event) => {
    loadIp();

    document.getElementById("login_form").addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(this);

        const formObject = Object.fromEntries(formData.entries());

        let exists = login(formObject.login_username, formObject.login_password);
        exists.then(res => {
            if (!res) document.getElementById("login_err");
        });
    });

    document.getElementById("login_username").addEventListener('input', (event => {
        if (document.getElementById("login_err").checkVisibility()){
            document.getElementById("login_err").setAttribute("visibility","hidden");
        }
    }))
});