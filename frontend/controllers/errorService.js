import { getAuth } from "../auth.js";


export async function viewErrors() {
    let token  = await getAuth();

    try {
        let errors = await getErrors(token);
        console.log(errors);
    } catch (error) {
        console.log(error);
    }
}

async function getErrors(token) {

    return fetch("http://localhost:8080/errors", {method: "GET", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    });
}