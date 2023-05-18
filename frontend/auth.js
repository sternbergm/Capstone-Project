let authToken;

async function login() {
    const username = prompt("enter username: ");
    const password = prompt("enter password: ");


    fetch("http://localhost:8080/authenticate", 
    {method: "POST", 
    headers: {"Content-Type": "application/json", accept: "application/json"}, 
    body: JSON.stringify({username, password})})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            authToken = Promise.reject("The promise was not okay.");
        }
        authToken = response.json();
    });
}

async function refresh() {

    fetch("http://localhost:8080/refresh", 
    {method: "POST", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + authToken.jwt_token}, 
    body: JSON.stringify({username, password})})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            authToken = Promise.reject("The promise was not okay.");
        }
        authToken = response.json();
    });

}

async function getAuth() {
    if(authToken == undefined) {
        await login();
    }
    else {
        await refresh();
    }

    return authToken;
}