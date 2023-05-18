import { getAuth } from "../auth.js";
import createPrompt from "prompt-sync"

const prompt = createPrompt();

export async function contractorController() {
    let keepRunning = true;
    let response;
    console.log("1. Read Contractors\n2. Read Contractor By id\n3. Add Contractor\n4. Update Contractor\n5. Delete Contractor");
    let choice = prompt("Enter choice: ");
        try {
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
        } catch (error) {
            console.log(error);
        }

}
async function getAllContractors() {
    return fetch("http://localhost:8080/contractor", {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function getContractorById() {
    let contractorId = prompt("Enter Contractor Id you wish to view ");
    return fetch(`http://localhost:8080/contractor/${contractorId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function addcontractor() {
    let token = await getAuth();
    const data = {

        "firstName": prompt("Enter constractor first name "),
        "lastName": prompt("Enter constractor last name "),
        "dateOfBirth": prompt("Enter constractor date of birth "),
        "address": prompt("Enter constractor address "),
        "email": prompt("Enter constractor email "),
        "salary": prompt("Enter constractor salary "),
        "isHired": prompt("was the contractor hired? ")

    }

    return fetch("http://localhost:8080/contractor", 
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

async function updatecontractor() {
    let token = await getAuth();
    let contractorId = prompt("what is the Id of the contractor you wish to update? ");
    const data = {

        "contractorId":contractorId,
        "firstName": prompt("Enter constractor first name "),
        "lastName": prompt("Enter constractor last name "),
        "dateOfBirth": prompt("Enter constractor date of birth "),
        "address": prompt("Enter constractor address "),
        "email": prompt("Enter constractor email "),
        "salary": prompt("Enter constractor salary "),
        "isHired": prompt("was the contractor hired? ")

    }
    
    return fetch(`http://localhost:8080/contractor/${contractorId}`, 
    {method: "PUT", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}, 
    body: JSON.stringify(data)})
    .then(async (response) => {
        if (response.status !== 204) {
            return Promise.reject((await response.json())[0]);
        }
        return `Contractor ${contractorId} was updated.`;
    });
}

    async function deletecontractor() {
        let token = await getAuth();
        let contractorId = prompt("what is the Id of the contractor you wish to delete? ");
    
        return fetch(`http://localhost:8080/contractor/${contractorId}`, 
        {method: "DELETE", 
        headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}})
        .then(async (response) => {
            if (response.status !== 204) {
                return Promise.reject(`Contractor ${contractorId} was deleted.`);
            }
            return `Contractor ${contractorId} was deleted.`;
        });
    }

