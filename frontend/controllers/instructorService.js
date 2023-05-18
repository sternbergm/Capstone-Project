import { getAuth } from "../auth.js";
import createPrompt from "prompt-sync"

const prompt = createPrompt();

export async function instructorController() {
    let keepRunning = true;
    let response;
    console.log("1. Add Clients\n2. Read Client By id\n3.add client\n4. update client\n 5.delete client");
    let choice = prompt("Enter choice: ");
        switch (choice) {
            case "1":
                response=await getAllClients();
                console.log(response);
                    break;
            case "2":
                response = await getClientById(); 
                console.log(response);
                break;
            case "3":
                response = await addClient();
                console.log(response);
                break;
            case "4":
                response=await updateclient();
                console.log(response);
                break;
            case "5": 
                response=await deleteClient();
                console.log(response);
                break;
    }

}
async function getAllClients() {
    return fetch("http://localhost:8080/client", {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function getClientById() {
    let clientId = prompt("Enter client Id you wish to view");
    return fetch(`http://localhost:8080/client/${clientId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function addClient() {
    let token = await getAuth();
    const data = {
        "ClientName": prompt("Enter ClientName "),
        "Address": prompt("Enter Client Address topic"),
        "CompanySize": prompt("Enter Client CompanySize "),
        "Email": prompt("Enter Client Email ")
    }

    return fetch("http://localhost:8080/client", 
    {method: "POST", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}, 
    body: JSON.stringify(data)})
    .then((response) => {
        if (response.status !== 201) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    });
}

async function updateclient() {
    let token = await getAuth();
    let clientId = prompt("what is the Id of the client you wish to update?");
    const data = {
        "clientId": clientId,
        "ClientName": prompt("Enter ClientName "),
        "Address": prompt("Enter Client Address topic"),
        "CompanySize": prompt("Enter Client CompanySize "),
        "Email": prompt("Enter Client Email ")
    }
    

    return fetch(`http://localhost:8080/client/${clientId}`, 
    {method: "PUT", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}, 
    body: JSON.stringify(data)})
    .then((response) => {
        if (response.status !== 201) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    });

}
