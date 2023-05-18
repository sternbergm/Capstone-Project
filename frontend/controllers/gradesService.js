import { getAuth } from "../auth.js";
import createPrompt from "prompt-sync";

const prompt = createPrompt();

export async function gradeController() {
    let keepRunning = true;
    let response;
        console.log("1. Read grades \n2. read grades by cohort id\n3. read grades by contractor id\n4. read grades by module id\n5. add grade \n6. update grade \n7. delete grade\n");
        let choice = prompt("Enter choice: ");
        try {
            switch (choice) {
                    case "1":
                        response = await getAllGrades();
                        console.log(response);
                        break;
                    case "2":
                        response = await getGradesByCohortId(); 
                        console.log(response);
                        break;
                    case "3":
                        response = await getGradesByContractorId(); 
                        console.log(response);
                        break;
                    case "4":
                            response = await getGradesByModuleId(); 
                            console.log(response);
                        break;
                    case "5":
                        response = await addGrade();
                        console.log(response);
                        break;
                    case "6":
                        response = await updateGrade();
                        console.log(response);
                        break;
                    case "7": 
                        response = await deleteGrade();
                        console.log(response);
                        break;
            }
        } catch (error) {
            console.log(error);
        }

}

async function getAllGrades() {
    return fetch("http://localhost:8080/grade", {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    });
}

async function getGradesByCohortId() {
    let cohortId = prompt("Enter cohort id you wish to view grades for: ");
    return fetch(`http://localhost:8080/grade/cohort/${cohortId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    });
}

async function getGradesByContractorId() {
    let contractorId = prompt("Enter contractor id you wish to view grades for: ");
    return fetch(`http://localhost:8080/grade/contractor/${contractorId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    });
}

async function getGradesByModuleId() {
    let moduleId = prompt("Enter module id you wish to view grades for: ");
    return fetch(`http://localhost:8080/grade/module/${moduleId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    });
}

async function addGrade() {
    let token = await getAuth();
    const data = {
        "contractorId": prompt("Enter the contractor id who's grade this is: "),
        "cohortId": prompt("Enter the cohort this contractor is participating in: "),
        "moduleId": prompt("Enter the module this grade corresponds to: "),
        "grade": prompt("Enter the grade: ")
    }

    return fetch("http://localhost:8080/grade", 
    {method: "POST", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}, 
    body: JSON.stringify(data)})
    .then(async (response) => {
        if (response.status !== 201) {
            return Promise.reject((await response.json())[0]);
        }
        return "grade was added";
    });
}

async function updateGrade() {
    let token = await getAuth();
    const data = {
        "contractorId": prompt("Enter the contractor id who's grade this is: "),
        "cohortId": prompt("Enter the cohort this contractor is participating in: "),
        "moduleId": prompt("Enter the module this grade corresponds to: "),
        "grade": prompt("Enter the grade: ")
    }

    return fetch(`http://localhost:8080/grade`, 
    {method: "PUT", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}, 
    body: JSON.stringify(data)})
    .then(async (response) => {
        if (response.status !== 204) {
            return Promise.reject((await response.json())[0]);
        }
        return `Grade was updated.`;
    });
}


async function deleteGrade() {
    let token = await getAuth();
    const data = {
        "contractorId": prompt("Enter the contractor id who's grade is being deleted: "),
        "cohortId": prompt("Enter the cohort this contractor is participating in: "),
        "moduleId": prompt("Enter the module this grade corresponds to: "),
        "grade": 0.0
    }


    return fetch(`http://localhost:8080/grade`, 
    {method: "DELETE", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token},
    body: JSON.stringify(data)})
    .then(async (response) => {
        if (response.status !== 204) {
            return Promise.reject((await response.json())[0]);
        }
        return `Grade was deleted.`;
    });
}