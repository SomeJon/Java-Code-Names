package ui;

import engine.Definition;
import engine.Engine; //to del
import engine.board.Board;
import engine.board.card.Card;
import engine.board.card.CardGroup;
import engine.board.card.GroupNeutral;
import engine.board.card.GroupTeam;
import engine.data.GameStatus;
import engine.data.Team;
import menu.console.ChoiceNotifier;
import menu.console.MainMenu;
import menu.console.Menu;
import engine.Engine.eMenuAction;
import menu.console.MenuItem;

import java.util.*;


public class UiAction implements engine.ui.UiAction, ChoiceNotifier {
    private UiData Data;
    private eMenuAction CurrentChoice;

    public static void main(String[] args) {
        UiAction uiAction = new UiAction();
        Engine engine = new Engine(uiAction);

        engine.startEngine();
        uiAction.buildStartingMenu();
        uiAction.openMenu();
    }

    public UiAction() {
        Data = new UiData();
    }

    @Override
    public void buildStartingMenu() {
        Menu StartMenu = Data.getMainMenu().getStartMenu();
        StartMenu.createMenuOption("Load data from an xml file", eMenuAction.LoadXml, this);
    }

    @Override
    public eMenuAction openMenu() {
        MainMenu MainMenu = Data.getMainMenu();

        MainMenu.play();
        if(MainMenu.isClosing())
            CurrentChoice = eMenuAction.Close;

        return CurrentChoice;
    }

    @Override
    public String getXmlPath() {
        return "";
    }

    @Override
    public void exceptionHandler(Exception ReceivedError) {

    }

    @Override
    public void showGameData(GameStatus ReceivedGameStatus) {

    }

    @Override
    public void showBoard(Board i_ReceivedBoard, boolean i_Visible) {
        Card[][] board = i_ReceivedBoard.getBoard();
        int rows = i_ReceivedBoard.getNumOfRows();
        List<String> secondLines;

        if(Data.isUpdate()){
            updateBuildingData(i_ReceivedBoard);
            Data.setFirstLines(createLines(board, rows, true, i_Visible));
            Data.flipUpdate();
        }

        secondLines = createLines(board, rows, false, i_Visible);
        for(int i = 0; i < rows ; i++){
            System.out.println(Data.getClosingLine());
            System.out.println(Data.getFirstLines().get(i));
            System.out.println(secondLines.get(i));
        }

        System.out.println(Data.getClosingLine());
    }

    private List<String> createLines(Card[][] i_Board, int i_NumOfRows,
                                     boolean i_First, boolean i_Visible){
        List<String> lines = new ArrayList<>();

        for(int i = 0; i < i_NumOfRows; i++){
            String line = "";
            for(Card card : i_Board[i]){
                if(i_First) {
                    line += "|" + firstCardLine(card);
                }
                else{
                    line += "|" + secondCardLine(card, i_Visible);
                }
            }

            line += "|";
            lines.add(line);
        }

        return lines;
    }

    private String firstCardLine(Card i_Card){
        String a = alignString(i_Card.getText(), Data.getCardLineSize());
        return a;
    }

    private String secondCardLine(Card i_Card, boolean i_Visible){
        String endText;
        String text;
        String groupName;

        CardGroup group = i_Card.getGroup();

        if(group instanceof GroupTeam){
            groupName = "(" + ((GroupTeam)group).getName() + ")";
        }
        else if(((GroupNeutral)group).isBlack()){
            groupName = "(" + UiData.BLACK + ")";
        }
        else{
            groupName = "";
        }

        endText = i_Visible?
                " " + (i_Card.isFlipped()? UiData.FLIPPED : UiData.NOT_FLIPPED) + " " + groupName :
                i_Card.isFlipped()? " " + UiData.FLIPPED + " " + groupName : "";

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
    public void showTurn(Team PlayingTeam, Board RecivedBoard) {

    }

    @Override
    public Definition getDefinition() {
        return null;
    }

    @Override
    public void guessResult(String Received) {

    }

    @Override
    public void Notify(Object sender) {
        eMenuAction action = (eMenuAction) ((MenuItem)sender).getItemValue();
        if (action == eMenuAction.LoadXml) {
            CurrentChoice = action;
            Data.getMainMenu().pauseRunning();
        }
    }

    private void updateBuildingData(Board i_RecivedBoard) {
        Card[][] cardMatrix = i_RecivedBoard.getBoard();
        int maxWordSize = getMaxWordLengthInCards(cardMatrix);
        int maxIdDigits = ((Integer)i_RecivedBoard.getBoardSize()).toString().length();
        int maxTeamName = getMaxTeamName(i_RecivedBoard.getCardGroups());
        int maxLineIdentification;
        int maxLineLength;

        maxLineIdentification = Integer.max(maxTeamName, UiData.NEUTRAL_MAX_NAME_LENGTH)
                + UiData.IDENTIFICATION_LINE_ADDONS_SIZE + maxIdDigits;
        maxLineLength = Integer.max(maxLineIdentification, maxWordSize);

        Data.setCardLineSize(maxLineLength, i_RecivedBoard.getNumOfColumns());
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

    private Integer getMaxTeamName(List<CardGroup> i_CardGroup) {
        return i_CardGroup.stream()
                .filter(c -> c instanceof GroupTeam)
                .map(GroupTeam.class::cast)
                .map(GroupTeam::getName)
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(0);
    }
}
