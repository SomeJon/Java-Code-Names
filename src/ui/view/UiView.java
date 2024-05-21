package ui.view;

import dto.type.board.DtoBoard;
import dto.type.data.DtoActiveGameStatus;
import dto.type.data.DtoGameDetails;
import dto.type.data.DtoGuessResult;
import engine.board.card.Card;
import engine.board.card.GroupCard;
import engine.board.card.GroupNeutral;
import engine.board.card.GroupTeam;
import engine.data.Identification;
import engine.data.Team;
import engine.exception.CodeNameExceptions;
import engine.exception.OutOfBoundException;
import engine.exception.loadxml.OutOfBoundLoad;
import engine.exception.loadxml.TeamNamesNotUnique;
import engine.response.Response;
import ui.MenuAction;
import ui.view.input.InputHandling;
import ui.interfaces.UiActionConst;
import ui.interfaces.UiViewInterface;
import ui.menu.console.*;

import java.util.*;


public class UiView implements UiViewInterface, ChoiceNotifier, UiActionConst {
    private UiData Data;
    private MenuAction CurrentChoice;

    public UiView() {
        Data = new UiData();
    }

    @Override
    public void buildStartingMenu() {
        Menu StartMenu = Data.getMainMenu().getStartMenu();
        StartMenu.createMenuOption("Load data from an xml file.", MenuAction.LOAD_XML, this);
    }

    @Override
    public MenuAction openMenu() {
        MainMenu MainMenu = Data.getMainMenu();

        System.out.println();
        MainMenu.play();
        if(MainMenu.isClosing())
            CurrentChoice = MenuAction.CLOSE;
        System.out.println();

        return CurrentChoice;

    }

    @Override
    public void showBoard(DtoBoard i_ReceivedBoard, boolean i_Visible) {
        Card[][] board = i_ReceivedBoard.getBoard();
        int rows = i_ReceivedBoard.getNumOfRows();
        List<String> secondLines;

        secondLines = createLines(board, rows, false, i_Visible);
        for(int i = 0; i < rows ; i++){
            System.out.println(Data.getClosingLine());
            System.out.println(Data.getFirstLines().get(i));
            System.out.println(secondLines.get(i));
        }

        System.out.println(Data.getClosingLine());
    }

    @Override
    public void getResponse(Response o_Response) {
        try {
            Data.getNextInput().getInput(o_Response);
        } catch (CodeNameExceptions e) {
            this.exceptionHandler(e, false);
        }
    }

    @Override
    public void askForXml() {
        System.out.println("Please enter a full path to a fitting xml file");
        System.out.print("Path: ");
    }

    @Override
    public void addFileData() {
        if(!Data.hasAFile()){
            Menu menu = Data.getMainMenu().getStartMenu();
            menu.createMenuOption("Show game details.", MenuAction.SHOW_GAME_DATA, this);
            menu.createMenuOption("Start a new game.",
                    MenuAction.START_GAME, this);

            Data.fileLoaded();
        }
    }

    @Override
    public void updateBoard(DtoBoard i_ReceivedBoard) {
        Card[][] board = i_ReceivedBoard.getBoard();
        int rows = i_ReceivedBoard.getNumOfRows();

        if(!Data.isActiveGame()) {
            Data.flipActiveGame();
            Menu menu = Data.getMainMenu().getStartMenu();
            menu.createMenuOption("Play Turn.", MenuAction.PLAYER_TURN, this);
            menu.createMenuOption("Active Game Status.", MenuAction.GAME_STATUS, this);
        }
        updateBuildingData(i_ReceivedBoard);
        Data.setFirstLines(createLines(board, rows, true, false));
    }

    @Override
    public void exceptionHandler(CodeNameExceptions i_ReceivedError, boolean i_TryAgain) {
        System.out.println("\n!!!An error occurred!!!");

        switch(i_ReceivedError.getType()){
            case LOAD_FILE:
                System.out.println("The error occurred while loading the file,");
                break;
            case CHECK_PATH:
                System.out.println("The error occurred while checking the path,");
        }

        if(i_ReceivedError instanceof OutOfBoundException){
            OutOfBoundException Received = (OutOfBoundException)i_ReceivedError;
            System.out.println("Error is the result of ");
            if(Received instanceof OutOfBoundLoad){
                System.out.print("the logic in file, ");
            }

            System.out.println("Entered " + Received.getParameterName() + " with value " +
                    Received.getParameterValue() + " while expected value to be in range: (" +
                    Received.getMin() + " - " + Received.getMax() + ")");
        }

        if(i_ReceivedError instanceof TeamNamesNotUnique){
            TeamNamesNotUnique Received = (TeamNamesNotUnique)i_ReceivedError;
            System.out.print("Error is the result of non unique team names, " +
                    "entered team name were:");
            Received.getNonUniqueNames()
            .forEach(s -> System.out.print(" \"" + s + "\","));
        }

        if(i_TryAgain){
            System.out.println("Please try again.");
        }

        System.out.println("!!!!!!\n");
    }

    @Override
    public void showGameDetails(DtoGameDetails i_ReceivedGameStatus) {
        List<Team> teams = i_ReceivedGameStatus.getTeams();

        System.out.println("\n*******Current game details*******");
        System.out.println("Current word bank size: " +
                i_ReceivedGameStatus.getNumOfWords());
        System.out.println("Current black word bank size: " +
                i_ReceivedGameStatus.getNumOfBlackWords());
        System.out.println("Participating Words: (Normal:" +
                i_ReceivedGameStatus.getNumOfCards() +
                ") (Black:" + i_ReceivedGameStatus.getNumOfBlackCards()
                +")");

        teams.forEach(t -> System.out.println("-------------------\nTeam Name: " + t.getName() +
                "\nWord goal: " + t.getPointGoal()));
        System.out.println("---------------------");

        PauseConsole.pause();
    }

    @Override
    public void showTeam(GroupTeam i_PlayingTeam) {
        System.out.println("--------------------------" +
                "\n" + i_PlayingTeam.getName() + " Current score " +
                i_PlayingTeam.getCardsFlipped() + "/" + i_PlayingTeam.getCards() +
                "\n--------------------------");
    }

    @Override
    public void showIdentification(Identification i_CurrentIdentification) {
        if(Data.getNextInput() != InputHandling.GUESSER)
            Data.setNextInput(InputHandling.GUESSER);

        System.out.println("Identification: " + i_CurrentIdentification.getIdentification() +
                "\nNumber of related words: " + i_CurrentIdentification.getRelated());
    }

    @Override
    public void guessResult(DtoGuessResult i_ReceivedGuessResult, int i_GuessLeft, GroupTeam i_CurrentTeam) {
        System.out.println("You flipped a Card!");
        switch(i_ReceivedGuessResult){
            case SUCCESSFUL_GUESS:
                System.out.println("The card belonged to your team, and received a point!");
                i_CurrentTeam.cardDown();
                if(i_GuessLeft > 0){
                    System.out.println("You can guess " + i_GuessLeft + " more times!");
                }
                else{
                    System.out.println("No guesses left! Turn Ends");
                }
                break;
            case ENEMY_TEAM_HIT:
                GroupTeam enemyTeam = i_ReceivedGuessResult.getGroupTeam().getPlayingTeam();
                System.out.println("The card belonged to an enemy Team!" +
                        "\n" + enemyTeam.getName() + " Received a point!" +
                        "\n\nTurn Ends");
                break;
            case BLACK_HIT:
                GroupTeam teamLost = i_ReceivedGuessResult.getGroupTeam().getPlayingTeam();
                System.out.println("Black card was flipped!" +
                        "\n" + teamLost + " Lost the game!");
            case NEUTRAL_HIT:
                System.out.println("Card flipped was neutral!" +
                        "\n\nTurn Ends");
        }

        showTeam(i_CurrentTeam);
        System.out.println();
        PauseConsole.pause();
    }

    @Override
    public void victoryHandler(GroupTeam i_WinnerTeam) {
        Menu menu = Data.getMainMenu().getStartMenu();
        int sizeOfMenu = menu.getMenuItems().size();

        menu.getMenuItems().remove(sizeOfMenu - 1);
        menu.getMenuItems().remove(sizeOfMenu - 2);
        Data.flipActiveGame();
        System.out.println("Game Ended!" +
                "\nThe winning team is - " + i_WinnerTeam.getName());
    }

    @Override
    public void showActiveGameStatus(DtoActiveGameStatus i_Data) {
        DtoBoard board = i_Data.getBoard();
        List<GroupTeam> groupTeams = board.getGroupTeams();
        GroupTeam currentGroupTeam = i_Data.getNextPlayingTeam();

        System.out.println("Board:");
        showBoard(board, true);
        System.out.println("Teams in game:");
        groupTeams.forEach(this::showTeam);
        System.out.println("Team playing next turn: " + currentGroupTeam.getName());
    }

    private List<String> createLines(Card[][] i_Board, int i_NumOfRows,
                                     boolean i_First, boolean i_Visible){
        List<String> lines = new ArrayList<>();

        for(int i = 0; i < i_NumOfRows; i++){
            StringBuilder line = new StringBuilder();
            for(Card card : i_Board[i]){
                if(card != null) {
                    if (i_First) {
                        line.append("|").append(firstCardLine(card));
                    } else {
                        line.append("|").append(secondCardLine(card, i_Visible));
                    }
                }
                else{
                    line.append("|").append(alignString("", Data.getCardLineSize()));
                }
            }

            line.append("|");
            lines.add(line.toString());
        }

        return lines;
    }

    private String firstCardLine(Card i_Card){
        return alignString(i_Card.getText(), Data.getCardLineSize());
    }

    private String secondCardLine(Card i_Card, boolean i_Visible){
        String endText;
        String text;
        String groupName;

        GroupCard group = i_Card.getGroup();

        if(group instanceof GroupTeam){
            groupName = "(" + ((GroupTeam)group).getName() + ")";
        }
        else if(((GroupNeutral)group).isBlack()){
            groupName = "(" + BLACK + ")";
        }
        else{
            groupName = "";
        }

        endText = i_Visible?
                " " + (i_Card.isFlipped()? FLIPPED : NOT_FLIPPED) + " " + groupName :
                i_Card.isFlipped()? " " + FLIPPED + " " + groupName : "";

        text = "[" + i_Card.getID() + "]" + endText;

        return alignString(text, Data.getCardLineSize());
    }

    private static String alignString(String i_String, int i_LineSize){
        int neededSpaces = i_LineSize - i_String.length();
        StringBuilder returnString = new StringBuilder();


        for(int i = 0; i < neededSpaces; i++){
            if(i == neededSpaces/2)
                returnString.append(i_String);
            returnString.append(" ");
        }

        return returnString.toString();
    }

    @Override
    public void Notify(Object sender) {
        MenuAction action = (MenuAction) ((MenuItem)sender).getItemValue();

        CurrentChoice = action;
        Data.getMainMenu().pauseRunning();

        switch (action){
            case LOAD_XML:
                Data.setNextInput(InputHandling.FILE_PATH);
                break;
            case PLAYER_TURN:
                Data.setNextInput(InputHandling.IDENTIFICATION);
                break;
        }
    }

    private void updateBuildingData(DtoBoard i_ReceivedBoard) {
        Card[][] cardMatrix = i_ReceivedBoard.getBoard();
        int maxWordSize = getMaxWordLengthInCards(cardMatrix);
        int maxIdDigits = ((Integer)i_ReceivedBoard.getBoardSize()).toString().length();
        int maxTeamName = getMaxTeamName(i_ReceivedBoard.getCardGroups());
        int maxLineIdentification;
        int maxLineLength;

        maxLineIdentification = Integer.max(maxTeamName, NEUTRAL_MAX_NAME_LENGTH)
                + IDENTIFICATION_LINE_ADDONS_SIZE + maxIdDigits;
        maxLineLength = Integer.max(maxLineIdentification, maxWordSize);

        Data.setCardLineSize(maxLineLength, i_ReceivedBoard.getNumOfColumns());
    }

    private int getMaxWordLengthInCards(Card[][] i_CardMatrix) {
        int maxWordSize = 0;

        for(Card[] cardsRow : i_CardMatrix){
            Optional<Integer> maxInRow = Arrays.stream(cardsRow)
                    .filter(Objects::nonNull)
                    .map(Card::getText)
                    .map(String::length)
                    .max(Integer::compareTo);

            if(maxInRow.isPresent() && maxInRow.get() > maxWordSize){
                maxWordSize = maxInRow.get();
            }
        }

        return maxWordSize;
    }

    private Integer getMaxTeamName(List<GroupCard> i_CardGroup) {
        return i_CardGroup.stream()
                .filter(c -> c instanceof GroupTeam)
                .map(GroupTeam.class::cast)
                .map(GroupTeam::getName)
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(0);
    }

    public static void errorPrint(String errorMessage){
        System.out.println("\n!!!An error occurred!!!");
        System.out.println(errorMessage);
        System.out.println("!!!!!!\n");
    }
}
