if (sessionStorage.getItem("username") != null) window.location.replace(`http://${serverIp}`);
let serverIp = null;

async function register(username, password) {
    let user = await fetch(`http://${serverIp}/user/` + username).then(res => res.json());

    if (user[0].username === null){
        let tbs_user = {
            username: username,
            password: password
        };

        await fetch(`http://${serverIp}/add_user`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(tbs_user)
        });

        window.location.replace(`http://${serverIp}/login`);
    }
    else if (user[0].username === username) {
        alert("Account with that username already exists.");
    }
}

function goToLogin() {
    window.location.replace(`http://${serverIp}/login`);
}

async function loadIp() {
    try {
        const response = await fetch('config.json');
        const config = await response.json();
        serverIp = config.serverIp;
        console.log('Server IP:', serverIp);
    } catch (error) {
        console.error('Error loading config: ', error);
    }
}

document.addEventListener('DOMContentLoaded', (event) => {
    loadIp();

    document.getElementById("registration_form").addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(this);

        const formObject = Object.fromEntries(formData.entries());

        if (formObject.registration_password === formObject.confirm_registration_password)
            register(formObject.registration_username, formObject.registration_password);
    });
});