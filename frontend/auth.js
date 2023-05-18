import createPrompt from "prompt-sync";

const prompt = createPrompt();

let authToken;

async function login() {
    const username = prompt("enter username: ");
    const password = prompt("enter password: ");


    return fetch("http://localhost:8080/authenticate", 
    {method: "POST", 
    headers: {"Content-Type": "application/json", accept: "application/json"}, 
    body: JSON.stringify({username, password})})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    });
}

async function refresh() {

    return fetch("http://localhost:8080/refresh", 
    {method: "POST", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + authToken.jwt_token}, 
    body: JSON.stringify({username, password})})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    });

}

export async function getAuth() {
    if(!authToken) {
        authToken = await login();
        console.log(authToken);
    }
    else {
        authToken = await refresh();
    }

    return authToken;
}
