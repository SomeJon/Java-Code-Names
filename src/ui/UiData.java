package ui;

import menu.console.MainMenu;

import java.util.Collections;
import java.util.List;

public class UiData {
    public final static String BLACK = "BLACK";
    public final static String NEUTRAL = "";
    public final static String FLIPPED = "V";
    public final static String NOT_FLIPPED = "X";
    public final static int NEUTRAL_MAX_NAME_LENGTH = 5;
    public final static int IDENTIFICATION_LINE_ADDONS_SIZE = 6; //using -"[]  ()"
    public final static int CARD_LINE_SPACING = 2;
    public final static int SPACE_BETWEEN_CARDS = 1;
    private MainMenu MainMenu;
    private int CardLineSize;
    private int LineSize;
    private List<String> FirstLines;
    private String ClosingLine;
    private boolean Update = true;

    public String getClosingLine() {
        return ClosingLine;
    }

    public void flipUpdate(){
        Update = !Update;
    }

    public boolean isUpdate() {
        return Update;
    }

    public List<String> getFirstLines() {
        return FirstLines;
    }

    public void setFirstLines(List<String> firstLines) {
        FirstLines = firstLines;
    }

    public int getLineSize() {
        return LineSize;
    }

    public int getCardLineSize() {
        return CardLineSize;
    }

    public void setCardLineSize(int i_CardLineSize, int i_NumOfRowCards) {
        CardLineSize = i_CardLineSize + CARD_LINE_SPACING;

        String dashes = " " + String.join("", Collections.nCopies(CardLineSize, "-"));
        StringBuilder closingLine = new StringBuilder();
        LineSize = (CardLineSize + SPACE_BETWEEN_CARDS) * i_NumOfRowCards + SPACE_BETWEEN_CARDS;
        for(int i = 0; i < i_NumOfRowCards; i++){
            closingLine.append(dashes);
        }

        ClosingLine = closingLine.toString();
    }

    public MainMenu getMainMenu() {
        return MainMenu;
    }

    public UiData() {
        MainMenu = new MainMenu("Code Names: Main Menu");
    }
}
