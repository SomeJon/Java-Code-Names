package ui.view.input;

import engine.response.GuesserResponse;
import engine.response.IdentificationResponse;
import engine.response.LoadXmlResponse;
import engine.response.Response;
import ui.Controller;
import ui.save.FileLocationResponse;
import ui.view.UiView;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public enum InputHandling {
    FILE_PATH_XML {
        @Override
        public void getInput(Response o_Response) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter a full xml file path: ");
            String path = scanner.nextLine();
            File file = new File(path);

            if (!file.isFile()) {
                UiView.errorPrint("A file was not found at the given path!");
            } else if (!path.endsWith(".xml")) {
                UiView.errorPrint("File path does not lead to an xml file!");
            } else {
                o_Response.loadResponse(new LoadXmlResponse(file));
            }
        }
    },
    FILE_PATH_SAVE{
        @Override
        public void getInput(Response o_Response) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter a full file path, include the name of the file: ");
            String path = scanner.nextLine();
            path = path + ".cn";

            o_Response.loadResponse(new FileLocationResponse(path));
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
                    scanner.nextLine();
                    UiView.errorPrint("Non Number entered! please try again");
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
                System.out.print("Please enter the number of the guessed card, or enter " +
                        Controller.EndGuessId + " to end guessing: ");
                try {
                    CardId = scanner.nextInt();
                    continueLoop = false;
                } catch (InputMismatchException e) {
                    continueLoop = true;
                    scanner.nextLine();
                    UiView.errorPrint("Non Number entered! please try again");
                }
            } while (continueLoop);

            System.out.println();
            o_Response.loadResponse(new GuesserResponse(CardId));
        }
    };


    public abstract void getInput(Response o_Response);
}
