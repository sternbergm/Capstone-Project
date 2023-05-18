import { getAuth } from "../auth.js";
import createPrompt from "prompt-sync";

const prompt = createPrompt();

export async function cohortController() {
    let keepRunning = true;
    let response;
        console.log("1. Read cohorts \n2. read cohort by id\n3. add cohort \n4. update cohort \n5. delete cohort\n");
        let choice = prompt("Enter choice: ");
        try {
            switch (choice) {
                    case "1":
                        response = await getAllCohorts();
                        console.log(response);
                        break;
                    case "2":
                        response = await getCohortsById(); 
                        console.log(response);
                        break;
                    case "3":
                        response = await addCohort();
                        console.log(response);
                        break;
                    case "4":
                        response = await updateCohort();
                        console.log(response);
                        break;
                    case "5": 
                        response = await deleteCohort();
                        console.log(response);
                        break;
            }
        } catch (error) {
            console.log(error);
        }

}

async function getAllCohorts() {
    return fetch("http://localhost:8080/cohort", {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    });
}

async function getCohortsById() {
    let moduleId = prompt("Enter cohort Id you wish to view");
    return fetch(`http://localhost:8080/cohort/${moduleId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    });
}

async function addCohort() {
    let token = await getAuth();
    const data = {
        "startDate": prompt("Enter cohort start date (yyyy-mm-dd) "),
        "endDate": prompt("Enter cohort end date (yyyy-mm-dd) "),
        "client": {
            "clientId": prompt("Enter the client id for which this cohort is being prepared for ")
        },
        "instructor": {
            "instructorId": prompt("Enter the instructor id that will be in charge of this cohort ")
        },
        contractors: [],
        modules: []
    }

    return fetch("http://localhost:8080/cohort", 
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

async function updateCohort() {
    let token = await getAuth();
    let cohortId = prompt("what is the Id of the cohort you wish to update? ");
    const data = {
        "cohortId": cohortId,
        "startDate": prompt("Enter cohort start date (yyyy-mm-dd) "),
        "endDate": prompt("Enter cohort end date (yyyy-mm-dd) "),
        "client": {
            "clientId": prompt("Enter the client id for which this cohort is being prepared for ")
        },
        "instructor": {
            "instructorId": prompt("Enter the instructor id that will be in charge of this cohort ")
        },
        contractors: [],
        modules: []
    }

    return fetch(`http://localhost:8080/cohort/${cohortId}`, 
    {method: "PUT", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}, 
    body: JSON.stringify(data)})
    .then(async (response) => {
        if (response.status !== 204) {
            return Promise.reject((await response.json())[0]);
        }
        return `Cohort ${cohortId} was updated.`;
    });
}


async function deleteCohort() {
    let token = await getAuth();
    let cohortId = prompt("what is the Id of the cohort you wish to delete? ");

    return fetch(`http://localhost:8080/cohort/${cohortId}`, 
    {method: "DELETE", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}})
    .then(async (response) => {
        if (response.status !== 204) {
            return Promise.reject((await response.json())[0]);
        }
        return `Cohort ${cohortId} was deleted.`;
    });
}