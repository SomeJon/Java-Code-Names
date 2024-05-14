package menu.console;

import java.util.Scanner;

public class PauseConsole {
    public static void Pause(){
        System.out.print("\nPlease press any button to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println();
        scanner.close();
    }
}
