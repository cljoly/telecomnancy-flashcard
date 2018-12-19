package flashcards.model;

import com.j256.ormlite.field.DatabaseField;

/** Table contenant le nombre de carte vue chaque jour
 */
public class VisitePerDay {
    public static final String ID_FIELD_NAME = "id";
    public static final String DAY_FIELD_NAME = "day";
    public static final String NB_CARD_FIELD_NAME = "nb_card";
    public static final String NB_LEARNING_FIELD_NAME = "learning";
    public static final String NB_NOTSEEN_FIELD_NAME = "not_seen";
    public static final String NB_LEARNED_FIELD_NAME = "learned";

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    private int id;

    @DatabaseField(columnName = DAY_FIELD_NAME, unique = true)
    private String day;

    @DatabaseField(columnName = NB_LEARNING_FIELD_NAME)
    private int learning;

    @DatabaseField(columnName = NB_NOTSEEN_FIELD_NAME)
    private int notseen;

    @DatabaseField(columnName = NB_LEARNED_FIELD_NAME)
    private int learned;


    public VisitePerDay() {}

}
