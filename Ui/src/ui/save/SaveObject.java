package ui.save;

import engine.EngineInterface;
import ui.interfaces.UiViewInterface;

import java.io.Serializable;

public class SaveObject implements Serializable {
    private UiViewInterface Ui;
    private EngineInterface Engine;

    public UiViewInterface getUi() {
        return Ui;
    }

    public EngineInterface getEngine() {
        return Engine;
    }

    public SaveObject(UiViewInterface ui, EngineInterface engine) {
        Ui = ui;
        Engine = engine;
    }
}
