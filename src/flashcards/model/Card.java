package flashcards.model;

import com.j256.ormlite.field.DatabaseField;

public class Card {
    public static final String RECTO_FIELD_NAME = "recto";
    public static final String VERSO_FIELD_NAME = "verso";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = RECTO_FIELD_NAME)
    private String recto;

    @DatabaseField(columnName = VERSO_FIELD_NAME)
    private String verso;

    // TODO Nommer la colonne commme recto & verso
    @DatabaseField
    private boolean reversible;

    public Card() {
    }

    public Card(String recto, String verso, boolean reversible) {
        this.recto = recto;
        this.verso = verso;
        this.reversible = reversible;
    }

    public int getId() {
        return id;
    }


    public String getRecto() {
        return recto;
    }

    public void setRecto(String recto) {
        this.recto = recto;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }

    public boolean isReversible() {
        return reversible;
    }

    public void setReversible(boolean reversible) {
        this.reversible = reversible;
    }

    public String toString() {
        StringBuffer r = new StringBuffer();
        r.append("Recto : ");
        r.append(this.recto);
        r.append("\nVerso : ");
        r.append(this.verso);
        r.append("\n");
        return r.toString();
    }
}
