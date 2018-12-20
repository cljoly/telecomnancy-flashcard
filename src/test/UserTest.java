import flashcards.model.*;
import javafx.util.Pair;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;

import java.sql.SQLException;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collections;
import java.util.List;

import static flashcards.model.CardStates.Learned;
import static flashcards.model.CardStates.NotSeen;
import static flashcards.model.CardStates.Learning;

public class UserTest {

    private User user;

    @Before
    public void init() throws SQLException, ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("test_junit-");
        sb.append(System.currentTimeMillis());
        sb.append("_");
        sb.append(Math.random()*10000);
        this.user = new User(sb.toString());

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

        Long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        String str = formater.format(date);
        date = formater.parse(str);
        this.user.add_visit(date);
        this.user.add_visit(date);
        this.user.add_visit(date);

        this.user.setState(c1, Learned);
        this.user.setState(c2, Learned);
        this.user.setState(c3, NotSeen);
        this.user.setState(c4, Learning);
        this.user.setState(c5, NotSeen);

        System.out.println("End @Before");
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
    public void testCardReversible() throws SQLException {
        Card c = this.user.create_card("Luxembourg", "Luxembourg Ville", true);
        assertEquals(c.getId(), this.user.get_card_recto("Luxembourg").getId());
        assertNotEquals(c.getId(), this.user.get_card_recto("Luxembourg Ville").getId());
    }

    @Test
    public void testChangeVerso() throws SQLException {
        Card c = this.user.get_card_recto("France");
        this.user.change_verso_of_card(c, "Versailles");
        assertEquals("Versailles", this.user.get_card_recto("France").getVerso());
    }

    @Test
    public void testChangeDescription() throws SQLException {
        Deck d = this.user.get_deck("Capitales eu");
        this.user.change_description_of_deck(d, "EEE");
        assertEquals("EEE", this.user.get_deck("Capitales eu").getDescription());
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
        ArrayList<Pair<String, Integer>> nbcard_type = this.user.get_all_nbcard_type();

        for (Pair<String, Integer> p : nbcard_type){
            if (p.getKey() == "Aquis") { assertEquals((int) p.getValue(),2); }
        }

        this.user.setState(this.user.get_card_recto("Islande"), CardStates.Learned);
        nbcard_type = this.user.get_all_nbcard_type();
        for (Pair<String, Integer> p : nbcard_type){
            if (p.getKey() == "Aquis") { assertEquals((int) p.getValue(), 2); }
        }


    }

    @Test
    public void testUpdateDate() throws SQLException,ParseException
    {
        Long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        String str = formater.format(date);
        date = formater.parse(str);
        assertEquals(user.get_day_entry(date).getDay(),date);
        str = "18-12-2018";
        //String strTrue = "19-12-2018";
        Date date1 = formater.parse(str);
        //Date date2 = formater.parse(strTrue);
        assertNotEquals(user.get_day_entry(date).getDay(),date1);
        //assertEquals(user.get_day_entry(date).getDay(),date2);
        assertEquals(user.get_day_entry(date).getNbCard(),3);
    }
    @Test
    public void testGetAllNbCards() throws SQLException,ParseException
    {
        String strTrue = "25-11-2019";
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = formater.parse(strTrue);
        user.add_visit(date1);
        Long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        String str = formater.format(date);
        date = formater.parse(str);
        ArrayList<Pair<String,Integer>> test1 = new ArrayList<Pair<String,Integer>>();
        ArrayList<Pair<String,Integer>> test2 = new ArrayList<Pair<String,Integer>>();
        Pair<String,Integer> p1 = new Pair<String,Integer>(date.toString(),3);
        Pair<String,Integer> p2 = new Pair<String,Integer>(date1.toString(),1);
        test1.add(p1);
        test1.add(p2);
        test2.add(p2);
        assertEquals(user.get_all_nbcard_days(),test1);
        assertEquals(user.get_all_nbcard_days(1),test2);
    }

    @Test
    public void testIsLearned() throws SQLException {
        Deck d_eu = this.user.get_deck("Capitales eu");
        Deck d_monde = this.user.get_deck("Capitales monde");

        assertEquals(true, this.user.isLearned(d_eu));
        assertEquals(false, this.user.isLearned(d_monde));
    }

    @Test
    public void testGetAllCards() throws SQLException {
        ArrayList<Integer> id_expected = new ArrayList();
        id_expected.add(this.user.get_card_recto("France").getId());
        id_expected.add(this.user.get_card_recto("Islande").getId());
        id_expected.add(this.user.get_card_recto("Australie").getId());
        id_expected.add(this.user.get_card_recto("Russie").getId());
        id_expected.add(this.user.get_card_recto("Thailande").getId());

        ArrayList<Integer> id = new ArrayList();
        for (Card c : this.user.get_all_cards()) {
            id.add(c.getId());
        }

        Collections.sort(id_expected);
        Collections.sort(id);
        assertEquals(id_expected, id);
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

    @Test
    public void testDelete() throws SQLException
    {
        Deck d1 = this.user.create_deck("Capitales eue", "Espace Économique Européene");
        Deck d2 = this.user.create_deck("Capitales mondee", "Reste du mondee");

        Card c1 = this.user.create_card("Francee", "Parise", false);
        Card c2 = this.user.create_card("Islandee", "Reykjavice", false);
        Card c3 = this.user.create_card("Australiee", "Canberrae", false);

        this.user.add_card2deck(c1, d1);
        this.user.add_card2deck(c2, d2);
        this.user.add_card2deck(c3, d2);
        user.delete_card_and_its_associations(c1);
        user.delete_deck_and_its_cards(d2);
        Deck d22 = user.get_deck("Capitales mondee");
        assertEquals(d22,null);
        Card c12 = user.get_card_recto("Francee");
        assertEquals(c12,null);
        Card c22 = user.get_card_recto("Islandee");
        assertEquals(c22,null);
        Card c32 = user.get_card_recto("Australiee");
        assertEquals(c32,null);
    }

    @Test
    public void testDeckStatCardNotSeen() throws SQLException {
        Deck d_eu = this.user.get_deck("Capitales eu");
        Deck d_monde = this.user.get_deck("Capitales monde");

        assertEquals(0, this.user.get_deck_stats_about_cards(d_eu, NotSeen));
        assertEquals(2, this.user.get_deck_stats_about_cards(d_monde, NotSeen));
    }

    @Test
    public void testDeckStatCardLearning() throws SQLException {
        Deck d_eu = this.user.get_deck("Capitales eu");
        Deck d_monde = this.user.get_deck("Capitales monde");

        assertEquals(0, this.user.get_deck_stats_about_cards(d_eu, Learning));
        assertEquals(1, this.user.get_deck_stats_about_cards(d_monde, Learning));
    }

    @Test
    public void testDeckStatCardLearned() throws SQLException {
        Deck d_eu = this.user.get_deck("Capitales eu");
        Deck d_monde = this.user.get_deck("Capitales monde");

        assertEquals(2, this.user.get_deck_stats_about_cards(d_eu, Learned));
        assertEquals(1, this.user.get_deck_stats_about_cards(d_monde, Learned));
    }

    @Test
    public void testDeckCardsNumber() throws SQLException {
        Deck d_eu = this.user.get_deck("Capitales eu");
        Deck d_monde = this.user.get_deck("Capitales monde");

        assertEquals(2, this.user.get_deck_cards_number(d_eu));
        assertEquals(4, this.user.get_deck_cards_number(d_monde));
    }
    /*
    @Test
    public void testUserData() throws SQLException
    {
        UserData ud = new UserData(this.user);
        String json = ud.toJson();
        System.out.println("json======================================json");
        System.out.println(json);
        UserData ud2 = new UserData(json);
        user.delete_all_data();
        ud2.fill_data_base(user);
        String json2 = ud2.toJson();
        System.out.println("json2======================================json2");
        System.out.println(json2);
        assertEquals(json,json2);
    }
    */

    @Test
    public void testDeckExportImport() throws SQLException
    {
        Deck d1 = user.get_deck("Capitales eu");
        String json = user.export_card_in_deck(d1);
        System.out.println("json======================================json");
        System.out.println(json);
        user.delete_deck_and_its_cards(d1);
        user.import_card_in_deck(json);

        assertNotEquals(user.get_deck("Capitales eu"),null);
        System.out.println("json======================================json");
        System.out.println(user.get_deck("Capitales eu"));
        for (Card c: user.get_card_from_deck(user.get_deck("Capitales eu")))
        {
            System.out.println(c);
        }
        System.out.println("fin");
    }
}