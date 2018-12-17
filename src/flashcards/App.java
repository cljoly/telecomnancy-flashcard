package flashcards;

import flashcards.model.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class App extends Application {

    public final static String DATABASE_URL = "jdbc:h2:file:./database/sample";
    public static Dao<Account, Integer> accountDao;

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL path = getClass().getClassLoader().getResource("AppView.fxml");
        Parent root = FXMLLoader.load(path);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try{
            init_database();
        } catch (Exception e){}

        launch(args);
    }




    public static void init_database() throws Exception{
        System.out.println("Init db");

        ConnectionSource connectionSource = null;
        try {
            System.out.println("Src");
            // create our data-source for the database
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            // setup our database and DAOs
            System.out.println("Setup");
            setupDatabase(connectionSource);
            // read and write some data
            System.out.println("ReadWriteMain");
            readWriteData();
            System.out.println("\n\nIt seems to have worked\n\n");
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

    private static void setupDatabase(ConnectionSource connectionSource) throws Exception {

        accountDao = DaoManager.createDao(connectionSource, Account.class);

        // if you need to create the table
        TableUtils.createTable(connectionSource, Account.class);
    }

    private static void readWriteData() throws Exception {
        System.out.println("Begin read write");
        // create an instance of Account
        String name = "Jim Coakley";
        Account account = new Account(name);

        // persist the account object to the database
        accountDao.create(account);
        int id = account.getId();
        verifyDb(id, account);

        // assign a password
        account.setPassword("_secret");
        // update the database after changing the object
        accountDao.update(account);
        verifyDb(id, account);

        // query for all items in the database
        List<Account> accounts = accountDao.queryForAll();
        assertEquals("Should have found 1 account matching our query", 1, accounts.size());
        verifyAccount(account, accounts.get(0));

        // loop through items in the database
        int accountC = 0;
        for (Account account2 : accountDao) {
            verifyAccount(account, account2);
            accountC++;
        }
        assertEquals("Should have found 1 account in for loop", 1, accountC);

        // construct a query using the QueryBuilder
        QueryBuilder<Account, Integer> statementBuilder = accountDao.queryBuilder();
        // shouldn't find anything: name LIKE 'hello" does not match our account
        statementBuilder.where().like(Account.NAME_FIELD_NAME, "hello");
        accounts = accountDao.query(statementBuilder.prepare());
        assertEquals("Should not have found any accounts matching our query", 0, accounts.size());

        // should find our account: name LIKE 'Jim%' should match our account
        statementBuilder.where().like(Account.NAME_FIELD_NAME, name.substring(0, 3) + "%");
        accounts = accountDao.query(statementBuilder.prepare());
        assertEquals("Should have found 1 account matching our query", 1, accounts.size());
        verifyAccount(account, accounts.get(0));

        // delete the account since we are done with it
        accountDao.delete(account);
        // we shouldn't find it now
        assertNull("account was deleted, shouldn't find any", accountDao.queryForId(id));
    }

    private static void verifyDb(int id, Account expected) throws SQLException, Exception {
        // make sure we can read it back
        Account account2 = accountDao.queryForId(id);
        if (account2 == null) {
            throw new Exception("Should have found id '" + id + "' in the database");
        }
        verifyAccount(expected, account2);
    }

    private static void verifyAccount(Account expected, Account account2) {
        assertEquals("expected name does not equal account name", expected, account2);
        assertEquals("expected password does not equal account name", expected.getPassword(), account2.getPassword());
    }



}
