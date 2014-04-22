
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author icha
 */
public class Player {
    private String Role;
    private String Status;
    private Vector CardsOnHand;
    
    public Player()
    {
        Role = new String();
        Status = new String();
        CardsOnHand = new Vector<Card>();
    }
    
    public Player(String R, String S)
    {
        Role = R;
        Status = S;
        CardsOnHand = new Vector<Card>();
    }
    
    public void setStatus(String S)
    {
        Status = S;
    }
    
    public void drawCard(Card c)
    {
        CardsOnHand.add(c);
    }
    
}
