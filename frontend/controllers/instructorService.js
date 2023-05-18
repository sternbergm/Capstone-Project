import { getAuth } from "../auth.js";
import createPrompt from "prompt-sync"

const prompt = createPrompt();

export async function instructorController() {
    let keepRunning = true;
    let response;
    console.log("1. Read Instructors\n2. Read Instructor By id\n3. Add Instructor\n4. Update Instructor\n5. Delete Instructor");
    let choice = prompt("Enter choice: ");
        try {
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
    catch (error) {
        console.log(error);
    }

}
async function getAllInstructors() {
    return fetch("http://localhost:8080/instructor", {method: "GET", headers: {"Content-Type": "application/json"}})
    .then(async (response) => {
        if (response.status !== 200) {
            return Promise.reject((await response.json())[0]);
        }
        return response.json();
    }).catch((err) => console.log(err));
}

async function getInstructorById() {
    let instructorId = prompt("Enter instructor Id you wish to view ");
     
}

async function addInstructor() {
    let token = await getAuth();
    const data = {
        "firstName": prompt("Enter Instructor first name "),
        "lastName": prompt("Enter Instructor last name "),
        "yearsOfExperience": prompt("Enter Instructor years of experience "),
        "expertise": prompt("Enter Instructor field of expertise "),
        "salary": prompt("Enter Instructor salary ")
    }

    return fetch("http://localhost:8080/instructor", 
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

async function updateinstructor() {
    let token = await getAuth();
    let instructorId = prompt("what is the Id of the instructor you wish to update? ");
    const data = {
        "instructorId": instructorId,
        "firstName": prompt("Enter Instructor first name "),
        "lastName": prompt("Enter Instructor last name "),
        "yearsOfExperience": prompt("Enter Instructor years of experience "),
        "expertise": prompt("Enter Instructor field of expertise "),
        "salary": prompt("Enter Instructor salary ")
        
    }
    
    return fetch(`http://localhost:8080/instructor/${instructorId}`, 
    {method: "PUT", 
    headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}, 
    body: JSON.stringify(data)})
    .then(async (response) => {
        if (response.status !== 204) {
            return Promise.reject((await response.json())[0]);
        }
        return `Instructor ${instructorId} was updated.`;
    });
}

    async function deleteinstructor() {
        let token = await getAuth();
        let instructorId = prompt("what is the Id of the instructor you wish to delete? ");
    
        return fetch(`http://localhost:8080/instructor/${instructorId}`, 
        {method: "DELETE", 
        headers: {"Content-Type": "application/json", accept: "application/json", authorization: "Bearer " + token.jwt_token}})
        .then(async (response) => {
            if (response.status !== 204) {
                return Promise.reject((await response.json())[0]);
            }
            return `Instructor ${instructorId} was deleted.`;
        });
    }

