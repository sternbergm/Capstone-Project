import { getAuth } from "../auth.js";
import createPrompt from "prompt-sync";

const prompt = createPrompt();

export async function moduleController() {
    let keepRunning = true;
    let response;
        let choice = prompt("1. Read Modules \n2. read module by id\n 3. add module \n 4. update module \n 5. delete module");
        switch (choice) {
                case "1":
                    response = await getAllModules();
                    console.log(response);
                    break;
                case "2":
                    response = await getModuleById(); 
                    console.log(response);
                    break;
                case "3":
                    response = await addModule();
                    console.log(response);
                    break;
                case "4":
                    await updateModule();
                    break;
                case "5": 
                    await deleteModule();
                    break;
        }

}

async function getAllModules() {
    return fetch("http://localhost:8080/module", {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function getModuleById() {
    let moduleId = prompt("Enter module Id you wish to view");
    return fetch(`http://localhost:8080/module/${moduleId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function addModule() {
    let token = getAuth();
    const data = {
        "topic": prompt("Enter module topic"),
        "startDate": prompt("Enter module startDate (yyyy-mm-dd)"),
        "endDate": prompt("Enter module end date (yyyy-mm-dd)"),
        "exerciseAmount": prompt("Enter number of exercises in the module"),
        "lessonAmount": prompt("Enter number of lessons in the module")
    }

    return fetch("http://localhost:8080/module", 
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

async function updateModule() {
    let token = getAuth();
    let moduleId = prompt("what is the Id of the module you wish to update?");
    const data = {
        "moduleId": moduleId,
        "topic": prompt("Enter module topic"),
        "startDate": prompt("Enter module startDate (yyyy-mm-dd)"),
        "endDate": prompt("Enter module end date (yyyy-mm-dd)"),
        "exerciseAmount": prompt("Enter number of exercises in the module"),
        "lessonAmount": prompt("Enter number of lessons in the module")
    }

    return fetch(`http://localhost:8080/module/${moduleId}`, 
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
