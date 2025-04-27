if (sessionStorage.getItem("username") != null) window.location.replace("/");

async function register(username, password) {
    if (username === null || password === null) {
        console.log("Fill all required fields.");
        return false;
    }

    const req = await fetch("/api/user/" + username);

    if (req.status === 200) {
        console.log("User already exists");
        return false;
    }

    let tbs_user = {
        username: username,
        password: password
    };

    const req2 = await fetch("/api/add-user", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN": getCsrfToken()
        },
        credentials: "include",
        body: JSON.stringify(tbs_user)
    });

    if (req2.status === 200) window.location.replace("/login");
    else console.log("Server error.");
}

function goToLogin() {
    window.location.replace("/login");
}

document.addEventListener('DOMContentLoaded', _ => {

    document.getElementById("registration_form").addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(this);

        const formObject = Object.fromEntries(formData.entries());

        if (formObject.registration_password === formObject.confirm_registration_password)
            register(formObject.registration_username, formObject.registration_password).finally();
    });
});