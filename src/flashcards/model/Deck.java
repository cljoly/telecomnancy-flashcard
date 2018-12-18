package flashcards.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class Deck  {
    public static final String NOM_FIELD_NAME = "nom";
    public static final String DESCRIPTION_FIELD_NAME = "description";
    public final static String ID_FIELD_NAME = "deck_id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    private int id;
    @DatabaseField(columnName = NOM_FIELD_NAME, unique = true)
    private String nom;
    @DatabaseField(columnName = DESCRIPTION_FIELD_NAME)
    private String  description;

    /**
     * Contrôleur sans argument pour ormilte
     */
    public Deck() {}

    public Deck(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    /**
     * Getter id
     * @return Id du packet, l’id est utilisé dans la base de donnée
     */
    public int getId() {
        return id;
    }

    /**
     * Getter nom
     * @return Nom du paquet
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter nom
     * @param nom Nouveau nom du paquet
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter description
     * @return Description du paquet
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter description
     * @param description Nouvelle description du paquet
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Représentation textuelle de l’objet
     * @return Contenu de l’objet sur plusieurs lines
     */
    public String toString(){
        StringBuffer r = new StringBuffer();
        r.append("Nom : ");
        r.append(this.nom);
        r.append("\nDescription : ");
        r.append(this.description);
        r.append("\n");
        return r.toString();
    }

}
