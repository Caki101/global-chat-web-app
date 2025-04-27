if (sessionStorage.getItem("username") != null) window.location.replace("/");

async function login(username, password) {
    const req = await fetch("/api/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN": getCsrfToken()
        },
        credentials: "include",
        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    if (req.status === 200) {
        sessionStorage.setItem("username", username);
        window.location.replace("/");
    }
    else {
        console.log("Wrong credentials.");
    }
}

function goToRegistration(){
    window.location.replace("/registration");
}

document.addEventListener('DOMContentLoaded', _ => {

    document.getElementById("login_form").addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(this);

        const formObject = Object.fromEntries(formData.entries());

        let exists = login(formObject.login_username, formObject.login_password);
        exists.then(res => {
            if (!res) document.getElementById("login_err");
        });
    });

    document.getElementById("login_username").addEventListener('input', (_ => {
        if (document.getElementById("login_err").checkVisibility()){
            document.getElementById("login_err").setAttribute("visibility","hidden");
        }
    }))
});

function getCsrfToken() {
    return document.cookie
        .split('; ')
        .find(row => row.startsWith('XSRF-TOKEN='))?.split('=')[1];
}