import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import flashcards.model.User;
import flashcards.model.Card;
import flashcards.model.Deck;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTest {

    private User user;

    @Before
    public void init() throws SQLException {
            this.user = new User("test_junit-"+System.currentTimeMillis());

            Deck d1 = this.user.create_deck("Capitales eu", "Espace Économique Européen");
            Deck d2 = this.user.create_deck("Capitales monde", "Reste du monde");

            Card c1 = this.user.create_card("France", "Paris", false);
            Card c2 = this.user.create_card("Islande", "Reykjavic", false);
            Card c3 = this.user.create_card("Australie", "Canberra", false);
            Card c4 = this.user.create_card("Russie", "Moscou", false);
            Card c5 = this.user.create_card("Thailande", "Bangkoq", false);

            this.user.add_card2deck(c1, d1);
            this.user.add_card2deck(c2, d1);

            this.user.add_card2deck(c3, d2);
            this.user.add_card2deck(c4, d2);
            this.user.add_card2deck(c5, d2);
            this.user.add_card2deck(c2, d2);
    }

    @Test
    public void testCardCreation() throws SQLException {
        Card c_fr = this.user.get_card_recto("France");
        assertEquals(c_fr.getVerso(), "Paris");
        assertEquals(this.user.get_card_recto("Islande").getVerso(), "Reykjavic");
        assertEquals(this.user.get_card_recto("Australie").getVerso(), "Canberra");
        assertEquals(this.user.get_card_recto("Russie").getVerso(), "Moscou");
        assertEquals(this.user.get_card_recto("Thailande").getVerso(), "Bangkoq");
    }

    @Test
    public void testDeckCreation() throws SQLException {
            Deck d_eu = this.user.get_deck("Capitales eu");
            Deck d_monde = this.user.get_deck("Capitales monde");
            assertEquals(d_eu.getDescription(), "Espace Économique Européen");
            assertEquals(d_monde.getDescription(), "Reste du monde");
    }

    @Test
    public void testUpdateCard() throws SQLException {
        Card c = this.user.get_card_recto("France");
        this.user.setMark(c, 3);
        assertEquals(this.user.get_card_recto("France").getMark(), 3);
        this.user.setMark(this.user.get_card_recto("Russie"), 30);
        assertEquals(this.user.get_card_recto("Russie").getMark(), 30);
    }

    @Test
    public void testReadAssociation() throws SQLException {
        ArrayList<String> rectosExpected = new ArrayList();
        rectosExpected.add("France");
        rectosExpected.add("Islande");

        Deck d = this.user.get_deck("Capitales eu");
        List<Card> cards = this.user.get_card_from_deck(d);
        ArrayList<String> rectos = new ArrayList();
        for (Card c : cards) {
            rectos.add(c.getRecto());
        }

        Collections.sort(rectosExpected);
        Collections.sort(rectos);
        assertEquals(rectosExpected,rectos);
    }

}