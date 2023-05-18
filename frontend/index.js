import createPrompt from "prompt-sync";
import {moduleController} from "./controllers/moduleService.js";
import { clientController } from "./controllers/clientService.js";
import { instructorController } from "./controllers/instructorService.js";
import { cohortController } from "./controllers/cohortService.js";
import { contractorController } from "./controllers/contractorService.js";
import { viewErrors } from "./controllers/errorService.js";
const prompt = createPrompt();


async function run() {
    let keepRunning = true;
    console.log("Welcome to the HTD database");
    while (keepRunning) {
        console.clear();
        console.log("1. Clients\n2. Contractors \n3. Cohorts \n4. Modules \n5. Instructors \n6. Grades \n7. view errors\n");
        let choice = prompt("enter choice: ");
        switch (choice) {
                case "1":
                    await clientController();
                    break;
                case "2":
                    await contractorController(); 
                    break;
                case "3":
                    await cohortController();
                    break;
                case "4":
                    await moduleController();
                    break;
                case "5": 
                    await instructorController();
                    break;
                case "6":
                    await gradeController();  
                    break;  
                case "7":
                    await viewErrors();
                    break;
            default:
                keepRunning = false;
                break;
        }
        prompt("Press enter");
        console.clear();
    }
}

run();