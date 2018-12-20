package flashcards.model;

//Cette classe est un singleton : le constructeur est donc privé, et elle possède une méthode statique instance
//Permet d'être accessible partout sans avoir à passer le game en paramètre partout
//Permet de pas casser tout le code
public class GameUsers {

    private static GameUsers game;
    private User user;


    private GameUsers(){
    }

    /**
     * Récupère l'instance de l'utilisateur
     * @return
     */
    public static GameUsers getInstance(){
        if (GameUsers.game == null){
            GameUsers.game = new GameUsers();
        }
        return GameUsers.game;
    }

    /**
     * Créée un nouvel utilisateur
     * @param username
     */
    public void newUser(String username){
        this.user = new User(username);
    }

    /**
     * Récupère l'utilisateur courant
     * @return
     */
    public User getCurrentUser(){
        return this.user;
    }

}
