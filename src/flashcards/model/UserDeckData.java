package flashcards.model;

import java.util.List;

public class UserDeckData
{
    private Deck deck;
    private List<Card> cards;

    public UserDeckData(Deck deck, List<Card> cards)
    {
        this.deck = deck;
        this.cards = cards;
    }

    public Deck getDeck()
    {
        return deck;
    }

    public List<Card> getCards()
    {
        return cards;
    }
}
