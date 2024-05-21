package program;

import engine.Engine;
import ui.Controller;
import ui.view.UiView;

public class Program {
    public static void main(String[] args) {
        Run();
    }

    public static void Run(){
        UiView gameUi = new UiView();
        Engine gameEngine = new Engine();
        Controller codeNameController = new Controller(gameUi, gameEngine);

        codeNameController.Start();
    }
}
