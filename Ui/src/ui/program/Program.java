package ui.program;

import engine.Engine;
import ui.Controller;
import ui.view.UiView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Program {
    public final static String SAVE_NAME = "save/save.cd";
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Run();
    }

    public static void Run() throws IOException{
        boolean saveOnExit;
        Controller codeNameController;

        Path path = Paths.get(SAVE_NAME);
        if(Files.exists(path)) {
            try (ObjectInputStream in =
                         new ObjectInputStream(
                                 Files.newInputStream(path))) {
                codeNameController = (Controller) in.readObject();
                if (!codeNameController.isSaveGameExit()) {
                    UiView gameUi = new UiView();
                    Engine gameEngine = new Engine();
                    codeNameController = new Controller(gameUi, gameEngine);
                }
            }
            catch (IOException | ClassNotFoundException e) {
                UiView gameUi = new UiView();
                Engine gameEngine = new Engine();
                codeNameController = new Controller(gameUi, gameEngine);
            }
        }
        else{
            UiView gameUi = new UiView();
            Engine gameEngine = new Engine();
            codeNameController = new Controller(gameUi, gameEngine);
        }

        saveOnExit = codeNameController.Start();
        if(saveOnExit) {
            try (ObjectOutputStream out =
                         new ObjectOutputStream(
                                 Files.newOutputStream(path))) {
                out.writeObject(codeNameController);
                out.flush();
            }
        }
        else if(Files.exists(path)){
            Files.delete(path);
        }
    }
}
