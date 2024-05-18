package program;

import engine.Engine;
import ui.UiAction;

public class Program {
    public static void main(String[] args) {
        Run();
    }

    public static void Run(){
        UiAction gameUi = new UiAction();
        Engine gameEngine = new Engine(gameUi);

        gameEngine.startGame();
    }
}
