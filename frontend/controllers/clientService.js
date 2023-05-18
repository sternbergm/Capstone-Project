import { getAuth } from "../auth.js";
import createPrompt from "prompt-sync"

const prompt = createPrompt();

export async function clientController() {
    let keepRunning = true;
    let response;
    console.log("1. Read all Clients\n2. Read Client By id\n3. Add client\n4. Update client\n5. Delete client");
    let choice = prompt("Enter choice: ");
    try {
        
    
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
} catch (error) {
        console.log(error);
}

}
async function getAllClients() {
    return fetch("http://localhost:8080/client", {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function getClientById() {
    let clientId = prompt("Enter client Id you wish to view ");
    return fetch(`http://localhost:8080/client/${clientId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function addClient() {
    let token = await getAuth();
    const data = {
        "clientName": prompt("Enter Client name "),
        "address": prompt("Enter Client address "),
        "companySize": prompt("Enter Client company size "),
        "email": prompt("Enter Client Email ")
    }

    return fetch("http://localhost:8080/client", 
    {method: "POST", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}, 
    body: JSON.stringify(data)})
    .then(async (response) => {
        if (response.status !== 201) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    });
}

async function updateclient() {
    let token = await getAuth();
    let clientId = prompt("what is the Id of the client you wish to update?");
    const data = {
        "clientId": clientId,
        "clientName": prompt("Enter Client Name "),
        "address": prompt("Enter Client address "),
        "companySize": prompt("Enter Client company size "),
        "email": prompt("Enter Client Email ")
    }
    

    return fetch(`http://localhost:8080/client/${clientId}`, 
    {method: "PUT", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}, 
    body: JSON.stringify(data)})
    .then(async (response) => {
        if (response.status !== 204) {
            return Promise.reject((await response.json())[0]);
        }
        return `client ${clientId} was updated.`;
    });

}

async function deleteClient() {
    let token = await getAuth();
    let clientId = prompt("what is the Id of the client you wish to delete? ");

    return fetch(`http://localhost:8080/client/${clientId}`, 
    {method: "DELETE", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}})
    .then(async (response) => {
        if (response.status !== 204) {
            return Promise.reject((await response.json())[0]);
        }
        return `Client ${clientId} was deleted.`;
    });
}
