package flashcards.model;

import com.j256.ormlite.field.DatabaseField;

import java.text.SimpleDateFormat;
import java.util.Date;

/** Table contenant le nombre de carte vue chaque jour
 */
public class VisitePerDay {
    public static final String ID_FIELD_NAME = "id";
    public static final String DAY_FIELD_NAME = "day";
    public static final String NB_CARD_FIELD_NAME = "nb_card";

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    private int id;

    @DatabaseField(columnName = DAY_FIELD_NAME, unique = true)
    private Date day;

    @DatabaseField(columnName = NB_CARD_FIELD_NAME)
    private int nbCard;


    public VisitePerDay() {}

    public VisitePerDay(Date day, int nbCard)
    {
        this.day = day;
        this.nbCard = nbCard;
    }

    public Date getDay()
    {
        return day;
    }

    public void setDay(Date day)
    {
        this.day = day;
    }

    public int getNbCard()
    {
        return nbCard;
    }

    public void setNbCard(int nbCard) { this.nbCard = nbCard; }

    public void incNbCard() { ++this.nbCard;}
}
