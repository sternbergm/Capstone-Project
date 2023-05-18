import { getAuth } from "../auth.js";
import createPrompt from "prompt-sync"

const prompt = createPrompt();

export async function instructorController() {
    let keepRunning = true;
    let response;
    console.log("1. Add Instructors\n2. Read Instructor By id\n3.add Instructor\n4. update Instructor\n 5.delete Instructor");
    let choice = prompt("Enter choice: ");
        switch (choice) {
            case "1":
                response=await getAllInstructors();
                console.log(response);
                    break;
            case "2":
                response = await getInstructorById(); 
                console.log(response);
                break;
            case "3":
                response = await addInstructor();
                console.log(response);
                break;
            case "4":
                response=await updateinstructor();
                console.log(response);
                break;
            case "5": 
                response=await deleteinstructor();
                console.log(response);
                break;
    }

}
async function getAllInstructors() {
    return fetch("http://localhost:8080/instructor", {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function getInstructorById() {
    let clientId = prompt("Enter client Id you wish to view");
    return fetch(`http://localhost:8080/instructor/$instructorId}`, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => {
        if (response.status !== 200) {
            console.log(response);
            return Promise.reject("The promise was not okay.");
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function addInstructor() {
    let token = await getAuth();
    const data = {
        "firstName": prompt("Enter Instructor FirstName "),
        "lastName": prompt("Enter Instructor LastName "),
        "yearsOfExperience":("Enter Instructor Years of Experience"),
        "expertise": prompt("Enter Instructor FirstName "),
        "salary": prompt("Enter Instructor salary ")
    }

    return fetch("http://localhost:8080/instructor", 
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

async function updateinstructor() {
    let token = await getAuth();
    let instructorId = prompt("what is the Id of the instructor you wish to update?");
    const data = {
        "instructorId": instructorId,
        "firstName": prompt("Enter Instructor FirstName "),
        "lastName": prompt("Enter Instructor LastName "),
        "yearsOfExperience":("Enter Instructor Years of Experience"),
        "expertise": prompt("Enter Instructor FirstName "),
        "salary": prompt("Enter Instructor salary ")
        
    }
    
    return fetch(`http://localhost:8080/instructor/${instructorId}`, 
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

    async function deleteinstructor() {
        let token = await getAuth();
        let instructorId = prompt("what is the Id of the instructor you wish to delete? ");
    
        return fetch(`http://localhost:8080/module/${instructorId}`, 
        {method: "DELETE", 
        headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}})
        .then((response) => {
            if (response.status !== 204) {
                console.log(response);
                return Promise.reject("The promise was not okay.");
            }
            return `Instructor ${instructorId} was deleted.`;
        });
    }

