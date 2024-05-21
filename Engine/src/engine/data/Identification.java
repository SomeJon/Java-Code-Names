package engine.data;

import java.io.Serializable;

public class Identification implements Serializable{
    private final String Identification;
    private final int Related;

    public String getIdentification() {
        return Identification;
    }

    public int getRelated() {
        return Related;
    }

    public Identification(String guess, int related) {
        Identification = guess;
        Related = related;
    }
}
