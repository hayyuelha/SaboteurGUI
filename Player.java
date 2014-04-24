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
    private Character_Card Role;
    private String Status;
    private Vector<Card> CardsOnHand;
    private int Turn;
    private int Score;
    
    public Player(int i, String S)
    {
        Role = new Character_Card(i);
        Status = S;
        CardsOnHand = new Vector<>();
    }
    
    public void setStatus(String S)
    {
        Status = S;
    }
    
    public void setTurn(int t){
        Turn = t;
    }
    
    public void setScore(int s){
        Score = s;
    }
    
    public void drawCard(Card c)
    {
        CardsOnHand.add(c);
    }
    
    public Card disCard(int index){
        return CardsOnHand.remove(index);
    }
}
