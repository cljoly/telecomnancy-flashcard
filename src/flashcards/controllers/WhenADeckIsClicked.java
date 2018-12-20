package flashcards.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class WhenADeckIsClicked implements EventHandler<ActionEvent> {

    private Button b;

    public WhenADeckIsClicked(Button button) {
        this.b = button;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("----------------------");
        System.out.println("le bouton a été cliqué");
        System.out.println(this.b.getText());
        System.out.println("----------------------");

    }
}
