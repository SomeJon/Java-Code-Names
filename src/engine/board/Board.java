package engine.board;

import engine.board.card.Card;
import engine.board.card.CardGroup;
import engine.board.card.GroupNeutral;
import engine.board.card.GroupTeam;
import engine.data.GameStatus;
import engine.data.Team;
import javafx.geometry.Pos;
import sun.invoke.empty.Empty;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {
    private boolean Visible;
    private final int NumOfColumns;
    private final int NumOfRows;
    private Card[][] Board;
    private final List<CardGroup> CardGroups;
    private List<Card> test;


    private Board(Integer numOfRows, Integer numOfColumns) {
        NumOfRows = numOfRows;
        NumOfColumns = numOfColumns;
        CardGroups = new ArrayList<>();
    }

    public Card[][] getBoard() {
        return Board;
    }

    public static Board buildBoard(List<String> NormalWords, List<String> BlackWords,
                                   GameStatus BuildData, int NumOfRows, int NumOfColumns) {
        int normalWordCards = BuildData.getNumOfCards();
        int blackWordCards = BuildData.getNumOfBlackCards();

        List<String> ChosenWords = selectDistinctWords(NormalWords, new ArrayList<>(), normalWordCards);
        List<String> ChosenBlackWords = selectDistinctWords(BlackWords, ChosenWords, blackWordCards);

        Board returnedBoard = new Board(NumOfRows, NumOfColumns);
        List<Card> cardsList = new ArrayList<>();
        int index = 0;
        for (Team team : BuildData.getTeams()) {
            int cards = team.getBoardCards();
            CardGroup groupTeam = new GroupTeam(team, cards);
            returnedBoard.CardGroups.add(groupTeam);
            for (int i = 0; i < cards; i++) {
                cardsList.add(new Card(ChosenWords.get(index), groupTeam));
                index++;
            }
        }

        CardGroup groupNeutral = new GroupNeutral(false, (normalWordCards - index), "Neutral");
        returnedBoard.CardGroups.add(groupNeutral);
        int normalWords = normalWordCards - index;
        for (int i = 0; i < normalWords; i++) {
            cardsList.add(new Card(ChosenWords.get(index), groupNeutral));
            index++;
        }

        CardGroup groupBlack = new GroupNeutral(false, (normalWordCards - index), "Black");
        returnedBoard.CardGroups.add(groupBlack);
        ChosenBlackWords
                .forEach(s -> cardsList.add(new Card(s, groupBlack)));

        returnedBoard.buildMatrix(cardsList);
        return returnedBoard;
    }

    private static List<String> selectDistinctWords(List<String> i_Words,
                                                    List<String> i_SelectedWords, int i_Size) {
        Random randomGenerator = new Random();

        return Stream.generate(() -> randomGenerator.nextInt(i_Words.size()))
                .distinct()
                .map(i_Words::get)
                .filter(s -> !i_SelectedWords.contains(s))
                .limit(i_Size)
                .collect(Collectors.toList());
    }

    private void buildMatrix(List<Card> cards) {
        int numOfCards = cards.size();
        List<Integer> indexes = IntStream.rangeClosed(1, numOfCards)
                .boxed()
                .collect(Collectors.toList());

        Collections.shuffle(indexes);
        Board = new Card[NumOfRows][NumOfColumns];

        for(int i = 0 ; i < numOfCards ; i++) {
            cards.get(i).setID(indexes.get(i));
            Postion pos = Postion.getPostion(indexes.get(i), NumOfColumns);
            Board[pos.getRow()][pos.getCol()] = cards.get(i);
        }
    }
}