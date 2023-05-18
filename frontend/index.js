import createPrompt from "prompt-sync";

const prompt = createPrompt();
let authToken = "";

async function run() {
    let keepRunning = true;
    
    while (keepRunning) {
        let choice = prompt("1. Clients\n2. Contractors \n3. Cohorts \n4.Modules \n5. Instructors \n 6. Grades \n enter choice ");
        switch (choice) {
                case "1":
                    await clientController();
                case "2":
                    await contractorController(); 
                case "3":
                    await cohortController();
                case "4":
                    await moduleController();
                case "5":
                    await instructorController();
                case "6":
                    await gradeController();    
            default:
                keepRunning = false;
                break;
        }
        prompt("Press enter");
        console.clear();
    }
}