package flashcards.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class Deck  {
    public static final String NOM_FIELD_NAME = "nom";
    public static final String DESCRIPTION_FIELD_NAME = "description";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = NOM_FIELD_NAME, unique = true)
    private String nom;
    @DatabaseField(columnName = DESCRIPTION_FIELD_NAME)
    private String  description;

    public Deck() {}

    public Deck(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
