import { getAuth } from "../auth.js";
import createPrompt from "prompt-sync"

const prompt = createPrompt();

export async function contractController() {
    let keepRunning = true;
    let response;
    console.log("1. Add Contractors\n2. Read Contractor By id\n3.add Contractor\n4. update Contractor\n 5.delete Contractor");
    let choice = prompt("Enter choice: ");
        switch (choice) {
            case "1":
                response=await getAllContractors();
                console.log(response);
                    break;
            case "2":
                response = await getContractorById(); 
                console.log(response);
                break;
            case "3":
                response = await addcontractor();
                console.log(response);
                break;
            case "4":
                response=await updatecontractor();
                console.log(response);
                break;
            case "5": 
                response=await deletecontractor();
                console.log(response);
                break;
    }

}
async function getAllContractors() {
    return fetch("http://localhost:8080/contractor", {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function getContractorById() {
    let contractorId = prompt("Enter Contractor Id you wish to view");
    return fetch(`http://localhost:8080/contractor/$contractorId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function addcontractor() {
    let token = await getAuth();
    const data = {

        "firstName": prompt("Enter constractor FirstName "),
        "lastName": prompt("Enter constractor LastName "),
        "dateOfBirth": prompt("Enter constractor dateOfBirth "),
        "address": prompt("Enter constractor address "),
        "email": prompt("Enter constractor email "),
        "salary": prompt("Enter constractor salary "),
        "isHired": prompt("Enter constractor isHired ")

    }

    return fetch("http://localhost:8080/contractor", 
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

async function updatecontractor() {
    let token = await getAuth();
    let contractorId = prompt("what is the Id of the contractor you wish to update?");
    const data = {

        "contractorId":contractorId,
        "firstName": prompt("Enter constractor FirstName "),
        "lastName": prompt("Enter constractor LastName "),
        "dateOfBirth": prompt("Enter constractor dateOfBirth "),
        "address": prompt("Enter constractor address "),
        "email": prompt("Enter constractor email "),
        "salary": prompt("Enter constractor salary "),
        "isHired": prompt("Enter constractor isHired ")

    }
    
    return fetch(`http://localhost:8080/instructor/${contractorId}`, 
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

    async function deletecontractor() {
        let token = await getAuth();
        let contractorId = prompt("what is the Id of the contractor you wish to delete? ");
    
        return fetch(`http://localhost:8080/contractor/${contractorId}`, 
        {method: "DELETE", 
        headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}})
        .then((response) => {
            if (response.status !== 204) {
                console.log(response);
                return Promise.reject("The promise was not okay.");
            }
            return `Contractor ${contractorId} was deleted.`;
        });
    }

