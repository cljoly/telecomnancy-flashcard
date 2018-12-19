package flashcards.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import javax.management.Query;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Boolean.FALSE;

public class User {

    private String username;
    private String DATABASE_URL = "jdbc:h2:file:./database/";
    private Dao<Card, Integer> cardDao;
    private Dao<Deck, Integer> deckDao;
    private Dao<DeckCard, Integer> deckCardDao;

    /**
     * Constructeur d'un utilisateur et de sa base de données associée
     * @param username : nom d'utilisateur de l'utilisateur dans l'application, il doit être unique
     */
    public User(String username){
        this.username = username;
        this.DATABASE_URL = this.DATABASE_URL.concat(this.username);
        System.out.println(this.DATABASE_URL);
        try{
            db_init();
        } catch (Exception e){
            System.out.println("Exception levée : constructeur User");
        }

    }

    /**
     * Getter username
     * @return Nom de l’utilisateur courant (supposé unique)
     */
    public String getUsername() {
        return username;
    }

    /**
     * Unitialisation de la base de donnée (création du fichier, des tables, des Dao)
     * @throws Exception
     */
    private void db_init()throws Exception{
        System.out.println("Init db");

        ConnectionSource connectionSource = null;
        try {
            System.out.println("Src");
            // create our data-source for the database
            connectionSource = new JdbcConnectionSource(this.DATABASE_URL);

            // setup our database and DAOs
            System.out.println("Setup");
            this.cardDao = DaoManager.createDao(connectionSource, Card.class);
            this.deckDao = DaoManager.createDao(connectionSource, Deck.class);
            this.deckCardDao = DaoManager.createDao(connectionSource, DeckCard.class);

            TableUtils.createTableIfNotExists(connectionSource, Deck.class);
            TableUtils.createTableIfNotExists(connectionSource, Card.class);
            TableUtils.createTableIfNotExists(connectionSource, DeckCard.class);

            this.create_deck("Par défaut", "Paquet par défaut");

        } catch (Exception e) {
            System.out.println("==========================");
            System.out.println("==========  EXN ==========");
            System.out.println("==========================");
            System.out.println(e);
        } finally {
            System.out.println("Close");
            // destroy the data source which should close underlying connections
            if (connectionSource != null) {
                connectionSource.close();
            }
            System.out.println("Closed");
        }

    }

    /**
     * Ajout d’une carte à un paquet dans la base de donnée
     * @param c Carte à ajouter
     * @param d Paquet cible
     * @throws SQLException
     */
    public void add_card2deck(Card c, Deck d) throws SQLException {
        DeckCard dc = new DeckCard(d, c);
        deckCardDao.create(dc);
    }

    /**
     * Crée un nouveau paquet dans la base de donnée
     * @param nom Nom du paquet (doit être unique)
     * @param description Descrption du paquet
     * @return Nouveau paquet créee
     * @throws SQLException En cas de problème avec la base de donnée (nom non unique ou autre)
     */
    public Deck create_deck(String nom, String description) throws SQLException {
        Deck d = new Deck(nom, description);
        deckDao.create(d);
        return d;
    }

    /**
     * Liste l’ensemble des paquets contenus dans la base de donnée
     * @return Liste des paquets
     */
    public List<Deck> get_all_decks() throws SQLException {
        QueryBuilder<Deck, Integer> deckQb = deckDao.queryBuilder();
        return deckDao.query(deckQb.prepare());
    }

    /**
     * Accéder à un paquet enregistre dans la base de donnée, par son nom
     * @param deck_name Nom du paquet
     * @return Premier (et unique) paquet correspondant
     * @throws SQLException
     */
    public Deck get_deck(String deck_name) throws SQLException{
        QueryBuilder<Deck, Integer> statementBuilder = deckDao.queryBuilder();
        statementBuilder.where().eq(Deck.NOM_FIELD_NAME, deck_name);
        Deck result = deckDao.queryForFirst(statementBuilder.prepare());

        return result;
    }

    /**
     * Crée une nouvelle carte dans la base de donnée
     * @param recto Recto de la carte (doit être unique)
     * @param verso Verso de la carte (doit être unique)
     * @param estReversible La carte est-elle réversible ?
     * @return Nouvelle carte créee
     * @throws SQLException En cas de problème avec la base de donnée (recto/verso non unique ou autre)
     */
    public Card create_card(String recto, String verso, boolean estReversible) throws SQLException {
        Card c = new Card(recto, verso, FALSE);
        cardDao.create(c);
        return c;
    }

    /**
     * Accéder à une carte enregistrée dans la base de donnée, par son recto
     * @param recto_content Recto de la carte
     * @return Première (et unique) carte correspondante
     * @throws SQLException
     */
    public Card get_card_recto(String recto_content) throws SQLException {
        QueryBuilder<Card, Integer> statementBuilder = cardDao.queryBuilder();
        statementBuilder.where().eq(Card.RECTO_FIELD_NAME, recto_content);
        Card result = cardDao.queryForFirst(statementBuilder.prepare());
        return result;
    }

    /**
     * Renvoie la liste (éventuellement vide) des cartes associées à un paquet. On peut récupérer l’objet Deck avec
     * @link get_deck.
     * @param d Paquet dont on cheche les cartes
     * @return Cartes associées
     */
    public List<Card> get_card_from_deck(Deck d) throws SQLException {
        QueryBuilder<DeckCard, Integer> deckCardQb = deckCardDao.queryBuilder();
        deckCardQb.selectColumns(DeckCard.CARD_ID_FIELD_NAME);
        deckCardQb.where().eq(DeckCard.DECK_ID_FIELD_NAME, d.getId());
        deckCardQb.prepare();

        QueryBuilder<Card, Integer> cardQb = cardDao.queryBuilder();
        cardQb.where().in(Card.ID_FIELD_NAME, deckCardQb);
        PreparedQuery<Card> prepare = cardQb.prepare();
        List<Card> q = cardDao.query(prepare);
        return q;
    }

    /**
     * Renvoie la liste (éventuellement vide) des paquets associés à une carte. On peut récupérer l’objet Deck avec
     * @link get_recto.
     * @param c Carte dont on cheche le paquet
     * @return Paquet associés
     */
    public List<Deck> get_deck_from_card(Card c) throws SQLException {
        QueryBuilder<DeckCard, Integer> deckCardQb = deckCardDao.queryBuilder();
        deckCardQb.selectColumns(DeckCard.DECK_ID_FIELD_NAME);
        deckCardQb.where().eq(DeckCard.CARD_ID_FIELD_NAME, c.getId());
        deckCardQb.prepare();

        QueryBuilder<Deck, Integer> deckQb = deckDao.queryBuilder();
        deckQb.where().in(Deck.ID_FIELD_NAME, deckCardQb);
        PreparedQuery<Deck> prepare = deckQb.prepare();
        List<Deck> q = deckDao.query(prepare);
        return q;
    }

    /**
     * Récupération de la note d’une carte
     * @param c Carte dont on veut la note
     * @return Note de la carte
     */
    public int getMark(Card c) {
        return c.getMark();
    }

    /**
     * Modifier la note d’une carte, en répercutant le changement dans la base de donnée, si la note est négative, elle est mise à zéro.
     * @param c La carte dont on modifie la note
     * @param v La nouvelle valeur de note de la carte
     */
    public void setMark(Card c, int v) throws SQLException{
        c.setMark(v);
        //System.out.println("Maj");
        //System.out.println(v);
        //System.out.println(c.getMark());
        cardDao.update(c);
    }

    /**
     * Récupère dans la base de donnée l’état d’une carte
     * @param c Carte dont on récupère l’état
     * @return
     */
    public CardStates getState(Card c) {
        return c.getState();
    }

    /**
     * Modifie l’état d’une carte et enregistre ceci dans la base de donnée
     * @param c Carte à modifier
     * @param cs État de la crate à modifier
     */
    public void setState(Card c, CardStates cs) throws SQLException {
        c.setState(cs);
        cardDao.update(c);
    }
}
