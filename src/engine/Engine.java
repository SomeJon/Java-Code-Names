package engine;

import engine.data.GameData;

public class Engine {
    public enum eMenuAction {
        openMenu,
        LoadXml,
        ShowGameData,
        StartGame,

    }
    public enum eGuessResult{
        CorrectTeam,
        WrongTeam,
        BlackWord,
        NeutralWord
    }

    private GameData Data;


    public void startEngine(){

    }
}
