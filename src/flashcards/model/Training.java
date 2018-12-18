package flashcards.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static flashcards.model.CardStates.Learned;
import static flashcards.model.CardStates.Learning;

public class Training
{
    private User user;
    private Deck deck;
    private int current = 0;
    int lowRange = 1;
    int highRange = 4;
    private ArrayList<Card> bad;
    private ArrayList<Card> good;

    public Training(User user, Deck deck) throws SQLException
    {
        this.user = user;
        this.deck = deck;
        bad = new ArrayList<Card>();
        good = new ArrayList<Card>();
        for(Card c : user.get_card_from_deck(deck))
        {
            if(c.getMark() == 0)
            {
                bad.add(c);
            }
            else if(c.getMark() < highRange && c.getMark() >= lowRange)
            {
                good.add(c);
            }
        }
    }

    public Card go_to_next_card()
    {
        ++current;
        if(bad.isEmpty())
        {
            current = 3;
        }
        if(current == 3)
        {
            int i = (new Random()).nextInt(good.size());
            current = 0;
            return good.get(i);
        }
        int i = (new Random()).nextInt(bad.size());
        return bad.get(i);
    }

    public void save_answer(Card c, Faces f)
    {
        if(Faces.FaceFrown.equals(f))
        {
            if(c.getMark() >= lowRange)
            {
                //c.setMark(c.getMark() - 1);
                user.setMark(c,c.getMark() - 1);
                if(c.getMark() == 0)
                {
                    good.remove(c);
                    bad.add(c);
                }
            }
            else
            {
                if(CardStates.NotSeen.equals(c.getState()))
                {
                    //c.setState(Learning);
                    user.setState(c,Learning);
                }
            }
        }
        else if(Faces.FaceSmile.equals(f))
        {
            if(CardStates.NotSeen.equals(c.getState()))
            {
                //c.setState(Learning);
                user.setState(c,Learning);
            }
            c.setMark(c.getMark() + 1);
            if(c.getMark() >= highRange)
            {
                //c.setState(Learned);
                user.setState(c,Learned);
            }
        }
    }

}
