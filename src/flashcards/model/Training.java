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
    private int nbCard;
    private int countTot = 0;
    private int rand;
    private int lowRange = 1;
    private int highRange = 4;
    private ArrayList<Card> bad;
    private ArrayList<Card> good;

    public Training(User user, Deck deck) throws SQLException
    {
        this.user = user;
        this.deck = deck;
        bad = new ArrayList<Card>();
        good = new ArrayList<Card>();
        nbCard = 20;
        countTot = 0;
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

    /**
     * Prends une carte selon les critères de l'algorithme d'apprentissage
     * @return une carte à apprendre
     */
    public Card go_to_next_card()
    {
        ++countTot;
        ++current;
        if((current >= 3 && !good.isEmpty()) || (bad.isEmpty() && !good.isEmpty()))
        {
            current = 0;
            return getCard(good);
        }
        else if(!bad.isEmpty())
        {
            return getCard(bad);
        }
        else
        {
            return null;
        }
    }

    /**
     * Prends une carte au hasard dans une liste de carte
     * @param good liste de cartes
     * @return la carte prise au hasard
     */
    private Card getCard(ArrayList<Card> good)
    {
        if(good.size() != 1)
        {
            rand = (new Random()).nextInt(good.size() - 1);
        }
        else
        {
            rand = 0;
        }
        return good.get(rand);
    }

    public Deck getDeck() {
        return deck;
    }

    /**
     * Mets à jour dans la base de données les notes et les états de cahque carte en fonction de ce que l'utilisateur a donné comme appréciation (Faces)
     * @param c carte tirée
     * @param f appréciation de l'utilisateur
     * @throws SQLException
     */
    public void save_answer(Card c, Faces f) throws SQLException
    {
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
            else if(bad.contains(c))
            {
                bad.remove(c);
                good.add(c);
                user.setMark(c,c.getMark() + 1);
            }
            else if(c.getMark() >= highRange - 1)
            {
                //c.setState(Learned);
                System.out.println(c.getMark());
                user.setMark(c,c.getMark() + 1);
                System.out.println(c.getMark());
                user.setState(c,Learned);
                System.out.println(c.getState());
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
        else if(c == null)
        {
            System.out.println("Stop");
        }
    }

    public boolean isFinished()
    {
        if((good.isEmpty() && bad.isEmpty()) || countTot == nbCard)
        {
            return true;
        }
        return false;
    }
}
