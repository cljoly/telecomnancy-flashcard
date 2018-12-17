package flashcards.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class Deck  {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String nom;
    @DatabaseField
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
