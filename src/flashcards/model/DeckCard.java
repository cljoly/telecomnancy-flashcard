package flashcards.model;

import com.j256.ormlite.field.DatabaseField;

public class DeckCard {
    public final static String DECK_ID_FIELD_NAME = "deck_id";
    public final static String CARD_ID_FIELD_NAME = "card_id";

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(foreign = true, columnName = DECK_ID_FIELD_NAME)
    Deck d;

    @DatabaseField(foreign = true, columnName = CARD_ID_FIELD_NAME)
    Card c;

    public DeckCard() {}

    public DeckCard(Deck d, Card c) {
        this.d = d;
        this.c = c;
    }
}
