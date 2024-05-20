package ui.input;

import engine.response.GuesserResponse;
import engine.response.IdentificationResponse;
import engine.response.LoadXmlResponse;
import engine.response.Response;
import ui.veiw.UiAction;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public enum InputHandling {
    FILE_PATH{
        @Override
        public void getInput(Response o_Response) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter a full xml file path: ");
            String path = scanner.nextLine();
            path = path.replaceAll("\\s", "");
            File file = new File(path);

            if (!file.isFile()) {
                UiAction.errorPrint("A file was not found at the given path!");
            } else if (!path.endsWith(".xml")) {
                UiAction.errorPrint("File path does not lead to an xml file!");
            } else {
                o_Response.loadResponse(new LoadXmlResponse(file));
            }
        }
    },
    IDENTIFICATION{
        @Override
        public void getInput(Response o_Response) {
            Scanner scanner = new Scanner(System.in);
            boolean continueLoop;

            System.out.println("Please enter an identification word and number of related words after");
            System.out.print("Identification word: ");
            String identificationWord = scanner.nextLine();
            int numberOfRelatedWords = 0;

            do {
                System.out.print("Number of related words: ");
                try {
                    numberOfRelatedWords = scanner.nextInt();
                    continueLoop = false;
                } catch (InputMismatchException e) {
                    continueLoop = true;
                    UiAction.errorPrint("Non Number entered! please try again");
                }
            } while (continueLoop);

            o_Response.loadResponse(new IdentificationResponse(identificationWord, numberOfRelatedWords));
        }
    },
    GUESSER{
        @Override
        public void getInput(Response o_Response) {
            Scanner scanner = new Scanner(System.in);
            boolean continueLoop;
            int CardId = 0;
            
            do {
                System.out.print("Please enter the number of the guessed card, or enter 0 to end guessing: ");
                try {
                    CardId = scanner.nextInt();
                    continueLoop = false;
                } catch (InputMismatchException e) {
                    continueLoop = true;
                    UiAction.errorPrint("Non Number entered! please try again");
                }
            } while (continueLoop);
            
            o_Response.loadResponse(new GuesserResponse(CardId));
        }
    };


    public abstract void getInput(Response o_Response);
}
