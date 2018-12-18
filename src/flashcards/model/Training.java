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
    private int rand;
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
                //System.out.println(c);
                bad.add(c);
            }
            else if(c.getMark() < highRange && c.getMark() >= lowRange)
            {
                //System.out.println(c);
                good.add(c);
            }
        }
    }

    public Card go_to_next_card()
    {
        ++current;
        if((current >= 3 && !good.isEmpty()) || bad.isEmpty())
        {
            current = 0;
            rand = (new Random()).nextInt(good.size() - 1);
            return good.get(rand);
        }
        else
        {
            int rand = (new Random()).nextInt(bad.size() - 1);
            return bad.get(rand);
        }
    }

    public void save_answer(Card c, Faces f) throws SQLException
    {
        if(good.isEmpty() && bad.isEmpty())
        {
            System.out.println("Stop");
        }
        if(Faces.FaceFrown.equals(f) && c != null)
        {
            if(c.getMark() >= lowRange)
            {
                //c.setMark(c.getMark() - 1);
                user.setMark(c,c.getMark() - 1);
                if(c.getMark() == 0)
                {
                    if(good.contains(c))
                    {
                        good.remove(c);
                        bad.add(c);
                    }
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
        else if(Faces.FaceSmile.equals(f) && c != null)
        {
            if(CardStates.NotSeen.equals(c.getState()))
            {
                //c.setState(Learning);
                user.setState(c,Learning);
                c.setMark(c.getMark() + 1);
                bad.remove(c);
                good.add(c);
            }
            else if(c.getMark() >= highRange - 1)
            {
                //c.setState(Learned);
                user.setMark(c,c.getMark() + 1);
                user.setState(c,Learned);
                if(good.contains(c))
                {
                    good.remove(c);
                }
            }
            else
            {
                user.setMark(c,c.getMark() + 1);
            }
        }
    }

}
