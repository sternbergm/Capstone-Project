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
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject(await response.text());
        }
        return response.json();
    });
}

async function refresh() {

    return fetch("http://localhost:8080/refresh_token", 
    {method: "POST", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + authToken.jwt_token}})
    .then(async (response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject(await response.text());
        }
        return response.json();
    });

}

export async function getAuth() {

       try {
         if(!authToken) {
             authToken = await login();
         }
         else {
             authToken = await refresh();
         }
       } catch (error) {
            authToken = undefined;
            throw error;
       }


    return authToken;
}
