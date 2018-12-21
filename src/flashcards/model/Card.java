package flashcards.model;

import com.j256.ormlite.field.DatabaseField;

public class Card {
    public static final String RECTO_FIELD_NAME = "recto";
    public static final String VERSO_FIELD_NAME = "verso";
    public final static String ID_FIELD_NAME = "card_id";
    public final static String MARK_FIELD_NAME = "mark";
    public final static String STATE_FIELD_NAME = "state";

    @DatabaseField(generatedId = true, unique = true, columnName = ID_FIELD_NAME)
    private int id;

    @DatabaseField(columnName = RECTO_FIELD_NAME, unique = true)
    private String recto;

    @DatabaseField(columnName = VERSO_FIELD_NAME)
    private String verso;

    @DatabaseField(columnName = MARK_FIELD_NAME)
    private int mark;

    @DatabaseField(columnName = STATE_FIELD_NAME)
    private CardStates state;

    /**
     * Contrôleur sans argument pour ormilte
     */
    public Card() {
    }

    /**
     * Renvoie l'état de la carte
     * @return
     */
    public CardStates getState()
    {
        return state;
    }

    /**
     * Paramètre l'état d'une carte
     * @param state
     */
    public void setState(CardStates state)
    {
        this.state = state;
    }

    /**
     * Constructeur d’une flashcard
     * @param recto Recto de la carte (supposé unique parmis toutes les cartes)
     * @param verso Verso de la carte (supposé unique parmis toutes les cartes)
     */
    public Card(String recto, String verso) {
        this.recto = recto;
        this.verso = verso;
        this.mark = 0;
        this.state = CardStates.NotSeen;
    }

    /**
     * Getter id
     * @return Id de la carte, l’id est utilisé dans la base de donnée
     */
    public int getId() {
        return id;
    }

    /**
     * Getter recto
     * @return Recto de la carte
     */
    public String getRecto() {
        return recto;
    }

    /**
     * Setter recto
     * @param recto Nouveau recto de la carte
     */
    public void setRecto(String recto) {
        this.recto = recto;
    }

    /**
     * Getter verso
     * @return Verso de la carte
     */
    public String getVerso() {
        return verso;
    }

    /**
     * Setter verso
     * @param verso Nouveau verso de la carte
     */
    public void setVerso(String verso) {
        this.verso = verso;
    }

    /**
     * Représentation textuelle de l’objet
     * @return Contenu de l’objet sur plusieurs lines
     */
    public String toString() {
        StringBuffer r = new StringBuffer();
        r.append("Recto : ");
        r.append(this.recto);
        r.append("\nVerso : ");
        r.append(this.verso);
        r.append("\n");
        r.append(this.mark);
        r.append("\n");
        r.append(this.state);
        return r.toString();
    }

    /**
     * Getter de note
     * @return La note actuelle
     */
    public int getMark() {
        return mark;
    }

    /**
     * Setter de note, si la note est négative, elle est mise à zéro.
     * @param mark Nouvelle valeur de la note
     */
    public void setMark(int mark) {
        if (mark >= 0)
          this.mark = mark;
        else
            this.mark = 0;
    }
}
