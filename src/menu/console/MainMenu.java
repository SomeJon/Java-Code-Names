package menu.console;

import java.util.Scanner;

public class MainMenu implements ChoiceNotifier{
    private final Menu StartMenu;
    private Menu CurrentMenu;
    private boolean Running = false;

    public Menu getStartMenu() {
        return StartMenu;
    }

    public MainMenu(String i_MainMenuName)
    {
        final boolean MAIN_MENU = true;

        StartMenu = new Menu(i_MainMenuName, MAIN_MENU, this);
        CurrentMenu = StartMenu;
    }

    public void play()
    {
        Running = true;

        while(Running) {
            try {
                CurrentMenu.printMenu(20);
                int userIntInput = receivedUserInput();
                if (userIntInput != 0) {
                    if(userIntInput > CurrentMenu.getMenuItems().size() || userIntInput < 0) {
                        System.out.println("Input out of range! Please Enter an Integer in the correct range!");
                    }
                    else {
                        CurrentMenu.getMenuItems().get(userIntInput - 1).MenuItemChosen();
                    }

                } else {
                    previousMenu();
                }
            }
            catch(NumberFormatException i_ExceptionOccurred) {
                System.out.println("!!!!Incorrect input type! Please enter an int!!!!!\n");
                PauseConsole.Pause();
            }
        }
    }

    public void stopRunning(){
        Running = false;
    }

    public void previousMenu(){
        if(CurrentMenu.getPreviousMenu() != null)
            CurrentMenu = CurrentMenu.getPreviousMenu();
        else
            Running = false;
    }

    public int receivedUserInput() throws NumberFormatException{
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            return Integer.parseInt(userInput);
    }

    public void Notify(Object sender) {
        CurrentMenu = (Menu)((MenuItem)sender).getItemValue();
    }
}
