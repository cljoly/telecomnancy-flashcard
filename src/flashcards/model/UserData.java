package flashcards.model;

import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Classe qui regroupe les données avant export.
 */
public class UserData
{
    private List<Card> cards;
    private List<Deck> decks;
    private List<DeckCard> links;

    /**
     * Récupère les données de l’itilisateur, sauf les statistiques.
     * @param u Utilisateur dont on veut récupérer les données
     * @throws SQLException
     */
    public UserData(User u) throws SQLException
    {
        this.cards = u.get_all_cards();
        this.decks = u.get_all_decks();
        this.links = u.get_all_links();
    }

    public UserData(String json)
    {
        Gson gson = new Gson();
        UserData ud = gson.fromJson(json,UserData.class);
        this.cards = ud.cards;
        this.decks = ud.decks;
        this.links = ud.links;
    }

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void fill_data_base(User u) throws SQLException
    {
        //Collections.sort(cards);
        u.insert_list_cards(this.cards);
        u.insert_list_decks(this.decks);
        u.insert_list_links(this.links);
    }

}
