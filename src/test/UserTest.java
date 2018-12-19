import flashcards.model.CardStates;
import javafx.util.Pair;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import flashcards.model.User;
import flashcards.model.Card;
import flashcards.model.Deck;

import java.sql.SQLException;
import java.util.ArrayList;

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

            this.user.add_card2deck(c3, d1);
            this.user.add_card2deck(c4, d1);
            this.user.add_card2deck(c5, d1);
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
    public void testGetNbOfCardsPerType() throws SQLException {
        this.user.setState(this.user.get_card_recto("France"), CardStates.Learned);
        ArrayList<Pair<String, Integer>> nbcard_type = this.user.get_all_nbcard_type();

        for (Pair<String, Integer> p : nbcard_type){
            if (p.getKey() == "Aquis") { assertEquals((int) p.getValue(), 1); }
        }
    }

    // TODO Test association

}