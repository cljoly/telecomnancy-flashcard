package flashcards.model;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Boolean.FALSE;

public class User {

    private String username;
    private String DATABASE_URL = "jdbc:h2:file:./database/";
    private ConnectionSource connectionSource;
    private Dao<Card, Integer> cardDao;
    private Dao<Deck, Integer> deckDao;
    private Dao<VisitePerDay,Integer> visitePerDayDao;
    private Dao<DeckCard, Integer> deckCardDao;
    private Training currentTraining;

    /**
     * Constructeur d'un utilisateur et de sa base de données associée
     * @param username : nom d'utilisateur de l'utilisateur dans l'application, il doit être unique
     */
    public User(String username){
        this.username = username;
        this.DATABASE_URL = this.DATABASE_URL.concat(this.username);
        System.out.println("Création de la base dans :");
        System.out.println(this.DATABASE_URL);
        this.currentTraining = null;
        try{
            db_init();
        } catch (Exception e){
            System.out.println("Exception levée : création base de donnée");
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

        try {
            System.out.println("Src");
            // create our data-source for the database
            this.connectionSource = new JdbcConnectionSource(this.DATABASE_URL);

            // setup our database and DAOs
            System.out.println("Setup");
            this.cardDao = DaoManager.createDao(connectionSource, Card.class);
            this.deckDao = DaoManager.createDao(connectionSource, Deck.class);
            this.deckCardDao = DaoManager.createDao(connectionSource, DeckCard.class);
            this.visitePerDayDao = DaoManager.createDao(connectionSource, VisitePerDay.class);

            TableUtils.createTableIfNotExists(connectionSource, Deck.class);
            TableUtils.createTableIfNotExists(connectionSource, Card.class);
            TableUtils.createTableIfNotExists(connectionSource, DeckCard.class);
            TableUtils.createTableIfNotExists(connectionSource,VisitePerDay.class);

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
     * Modifier la description d’un paquet de carte
     * @param d Paquet à modifier
     * @param description Nouvelle description
     */
    public void change_description_of_deck(Deck d, String description) throws SQLException {
        d.setDescription(description);
        deckDao.update(d);
    }


    /**
     * Liste l’ensemble des paquets contenus dans la base de donnée
     * @return Liste des paquets
     */
    public List<Deck> get_all_decks() throws SQLException {
        QueryBuilder<Deck, Integer> deckQb = deckDao.queryBuilder();
        return deckDao.query(deckQb.prepare());
    }

    public List<DeckCard> get_all_links() throws SQLException
    {
        QueryBuilder<DeckCard, Integer> deckQb = deckCardDao.queryBuilder();
        return deckCardDao.query(deckQb.prepare());
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
     * A-t-on fini d’apprendre un paquet ?
     * @param d le paquet
     */
    public boolean isLearned(Deck d) throws SQLException {
        int nb_card_learned = this.get_deck_stats_about_cards(d, CardStates.Learned);
        int nb_card_in_deck = this.get_deck_cards_number(d);
        return nb_card_in_deck == nb_card_learned;
    }

    /** Crée une carte, non réversible, telle qu’elle seront conservées dans la base de donnée sous jacente
     * @param recto Recto de la carte
     * @param verso Verso de la carte
     * @return Nouvelle carte créée
     */
    private Card create_one_card(String recto, String verso) throws SQLException {
        Card c = new Card(recto, verso);
        cardDao.create(c);
        return c;
    }

    /**
     * Crée une nouvelle carte dans la base de donnée. Les cartes réversible sont stockées comme deux cartes dans la
     * base en inversant recto et verso
     * @param recto Recto de la carte (doit être unique)
     * @param verso Verso de la carte (doit être unique)
     * @param estReversible La carte est-elle réversible ?
     * @return Nouvelle carte créee
     * @throws SQLException En cas de problème avec la base de donnée (recto/verso non unique ou autre)
     */
    public Card create_card(String recto, String verso, boolean estReversible) throws SQLException {
        if (estReversible) {
            create_one_card(verso, recto);
        }
        return create_one_card(recto, verso);
    }

    /**
     * Modifier le verso d’une carte
     * @param c Carte à modifier
     * @param verso Nouveau verso
     */
    public void change_verso_of_card(Card c, String verso) throws SQLException {
        c.setVerso(verso);
        cardDao.update(c);
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
     * Liste de toutes les cartes
     */
    public List<Card> get_all_cards() throws SQLException {
        QueryBuilder<Card, Integer> cardQb = cardDao.queryBuilder();
        return cardDao.query(cardQb.prepare());
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

    /**
     * retourne le nombre total de carte pour chaque type
     * @return
     */
    public ArrayList<Pair<String, Integer>> get_all_nbcard_type() throws SQLException{
        /////Récupère les infos de la base de donnée
        GenericRawResults<String[]> rawResults = cardDao.queryRaw("SELECT "+Card.STATE_FIELD_NAME+",COUNT(*) FROM Card GROUP BY "+Card.STATE_FIELD_NAME);
        List<String[]> qResults = rawResults.getResults();

        /////Génère le résultat//////
        ArrayList<Pair<String, Integer>> result = new ArrayList<>();
        int pasVus = 0; int enCours = 0; int Aquis = 0;
        int size = qResults.size();
        for (int i=0; i<size; i++){
            //System.out.println("DEEEEEEEEEEEEEE bug : " + qResults.get(i)[0] + " -> " + qResults.get(i)[1]);
            if (qResults.get(i)[0] == CardStates.NotSeen.toString()) { pasVus = Integer.parseInt(qResults.get(i)[1]); }
            if (qResults.get(i)[0] == CardStates.Learning.toString()) { enCours = Integer.parseInt(qResults.get(i)[1]); }
            if (qResults.get(i)[0] == CardStates.Learned.toString()) { Aquis = Integer.parseInt(qResults.get(i)[1]); }
        }
        result.add(new Pair<>("Non vu", pasVus));
        result.add(new Pair<>("En cours d'apprentissage", enCours));
        result.add(new Pair<>("Aquis", Aquis));

        return result;

    }

    public void createNewTraining(Deck d) throws SQLException
    {
        this.currentTraining = new Training(this, d);
    }

    public void createNewTraining(Deck d, int nbCard, int nbRepeat) throws SQLException
    {
        this.currentTraining = new Training(this, d, nbCard, nbRepeat);
    }

    public void finishTraining()
    {
        this.currentTraining = null;
    }

    public Training getCurrentTraining()
    {
        return currentTraining;
    }

    public void add_visit(Date d) throws SQLException
    {
        QueryBuilder<VisitePerDay, Integer> statementBuilder = visitePerDayDao.queryBuilder();
        statementBuilder.selectColumns();
        statementBuilder.where().eq(VisitePerDay.DAY_FIELD_NAME,d);
        PreparedQuery<VisitePerDay> tmp = statementBuilder.prepare();
        VisitePerDay v = visitePerDayDao.queryForFirst(tmp);

        if(v == null)
        {
            v = new VisitePerDay(d,1);
            visitePerDayDao.create(v);
        }
        else
        {
            v.incNbCard();
            visitePerDayDao.update(v);
        }
    }

    public VisitePerDay get_day_entry(Date d) throws SQLException
    {
        QueryBuilder<VisitePerDay, Integer> statementBuilder = visitePerDayDao.queryBuilder();
        statementBuilder.where().eq(VisitePerDay.DAY_FIELD_NAME,d);
        VisitePerDay result = visitePerDayDao.queryForFirst(statementBuilder.prepare());
        return result;
    }

    public ArrayList<Pair<String,Integer>> get_all_nbcard_days() throws SQLException
    {
        ArrayList<Pair<String,Integer>> tmp = new ArrayList<Pair<String,Integer>>();
        QueryBuilder<VisitePerDay, Integer> statementBuilder = visitePerDayDao.queryBuilder();
        statementBuilder.selectColumns();
        List<VisitePerDay> result = visitePerDayDao.query(statementBuilder.prepare());
        Pair<String,Integer> pair = null;
        for(VisitePerDay v : result)
        {
            pair = new Pair<String,Integer>(v.getDay().toString(),v.getNbCard());
            tmp.add(pair);
        }
        return tmp;
    }

    public ArrayList<Pair<String,Integer>> get_all_nbcard_days(int nbDays) throws SQLException
    {
        ArrayList<Pair<String,Integer>> tmp = this.get_all_nbcard_days();
        if(tmp.size() <= nbDays)
        {
            nbDays = tmp.size();
        }
        int lim = tmp.size();
        int deb = lim - nbDays;
        ArrayList<Pair<String,Integer>> tmp2 = new ArrayList<Pair<String,Integer>>();
        for(int i = deb; i < lim; i++)
        {
            tmp2.add(tmp.get(i));
        }
        return tmp2;
    }

    public boolean delete_card_and_its_associations(Card c) throws SQLException
    {
        int id = c.getId();
        deckCardDao.executeRaw("DELETE FROM DeckCard WHERE " + DeckCard.CARD_ID_FIELD_NAME + " = " + id + ";");
        cardDao.executeRaw("DELETE FROM Card WHERE " + Card.ID_FIELD_NAME + "=" + id + ";");
        c = null;
        if(c == null)
        {
            return true;
        }
        return false;
    }

    public boolean delete_deck_and_its_cards(Deck d) throws SQLException
    {
        int id = d.getId();
        cardDao.executeRaw("DELETE FROM Card WHERE " + Card.ID_FIELD_NAME + " IN (SELECT " + DeckCard.CARD_ID_FIELD_NAME + " FROM DeckCard WHERE " + DeckCard.DECK_ID_FIELD_NAME + "=" + id + ");" );
        deckCardDao.executeRaw("DELETE FROM DeckCard WHERE " + DeckCard.DECK_ID_FIELD_NAME + "=" + id + ";");
        deckDao.executeRaw("DELETE FROM Deck WHERE " + Deck.ID_FIELD_NAME + "=" + id + ";");
        d = null;
        if(d == null)
        {
            return true;
        }
        return false;
    }

    /**
     * Nombre de cartes dans l’état state pour le paquet deck
     * @param deck Paquet de cartes
     * @param cardStatType État des cartes
     * @return int Nombre de carte correspondante
     */
    public int get_deck_stats_about_cards(Deck deck, CardStates cardStatType) throws SQLException {
        GenericRawResults<String[]> raw = cardDao.queryRaw(
                "SELECT COUNT(*) FROM Card c, DeckCard dc " +
                        " WHERE dc." + DeckCard.CARD_ID_FIELD_NAME + " = c." +  Card.ID_FIELD_NAME +
                        " AND dc." + DeckCard.DECK_ID_FIELD_NAME + " = " + deck.getId() + " " +
                        " AND c." + Card.STATE_FIELD_NAME + " LIKE '" + cardStatType + "'" +
                        ""
        );
        return listString(raw);
    }

    /**
     * Nombre de cartes total dans le paquet deck
     * @param deck Paquet de cartes
     * @return int Nombre de carte correspondante
     */
    public int get_deck_cards_number(Deck deck) throws SQLException {
        GenericRawResults<String[]> raw = cardDao.queryRaw(
                "SELECT COUNT(*) FROM Card c, DeckCard dc " +
                        " WHERE dc." + DeckCard.CARD_ID_FIELD_NAME + " = c." +  Card.ID_FIELD_NAME +
                        " AND dc." + DeckCard.DECK_ID_FIELD_NAME + " = " + deck.getId() + " " +
                        ""
        );
        return listString(raw);
    }

    private int listString(GenericRawResults<String[]> raw) throws SQLException
    {
        List<String[]> r = raw.getResults();
        int nb_card = -1;
        try {
            nb_card = Integer.parseInt(r.get(0)[0]);
        } catch (IndexOutOfBoundsException e) {
            nb_card = 0;
        }
        return nb_card;
    }

    public void insert_list_cards(List<Card> cards) throws SQLException
    {
        System.out.println("cards~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~cards");
        System.out.println(cards);
        cardDao.create(cards);
    }

    public void insert_list_decks(List<Deck> decks) throws SQLException
    {
        deckDao.create(decks);
    }

    public void insert_list_links(List<DeckCard> links) throws SQLException
    {
        deckCardDao.create(links);
    }

    public void delete_all_data() throws SQLException
    {
        deckCardDao.executeRaw("DELETE FROM DeckCard");
        deckDao.executeRaw("DELETE FROM Deck");
        cardDao.executeRaw("DELETE FROM Card");
        visitePerDayDao.executeRaw("DELETE FROM VisitePerDay");
    }

    public String export_card_in_deck(Deck d) throws SQLException
    {
        UserDeckData udd = new UserDeckData(d, this.get_card_from_deck(d));
        Gson gson = new Gson();

        return gson.toJson(udd);
    }

    public void import_card_in_deck(String s) throws SQLException
    {
        Gson gson = new Gson();
        UserDeckData udd = gson.fromJson(s,UserDeckData.class);
        deckDao.create(udd.getDeck());
        for(Card c : udd.getCards())
        {
            cardDao.create(c);
            this.add_card2deck(c,this.get_deck(udd.getDeck().getNom()));
            System.out.println(c);
        }
    }
}
