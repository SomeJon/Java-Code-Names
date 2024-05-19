package ui.input;

import engine.response.LoadXmlResponse;
import engine.response.Response;
import ui.UiAction;

import java.io.File;
import java.util.Scanner;

public enum InputHandling {
    FILE_PATH{
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
                public void getInput(Response o_Response){

                }
    };

    public abstract void getInput(Response o_Response);
}
