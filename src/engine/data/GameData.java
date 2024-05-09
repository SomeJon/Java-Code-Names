package engine.data;

import engine.ui.UiAction;

import java.util.Set;

public class GameData {
    private Set<String> m_Words;
    private Set<String> m_BlackWords;
    private Integer m_NumOfRows;
    private Integer m_NumOfColumns;
    private UiAction m_UserInterface;
    private Active m_ActiveData;
    private GameStatus m_Status;
}
