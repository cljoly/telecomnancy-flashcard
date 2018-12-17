package flashcards.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

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

    public String getUsername() {
        return username;
    }


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

        } catch (Exception e) {
            System.out.println("EXN");
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

    public void add_card2deck(Card c, Deck d) throws SQLException {
        DeckCard dc = new DeckCard(d, c);
        deckCardDao.create(dc);
    }

    public Deck create_deck(String nom, String description) throws SQLException {
        Deck d = new Deck(nom, description);
        deckDao.create(d);
        return d;
    }

    public Deck get_deck(String deck_name) throws SQLException{
        QueryBuilder<Deck, Integer> statementBuilder = deckDao.queryBuilder();
        statementBuilder.where().eq(Deck.NOM_FIELD_NAME, deck_name);
        Deck result = deckDao.queryForFirst(statementBuilder.prepare());

        return result;
    }

}
