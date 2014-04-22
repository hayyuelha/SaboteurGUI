
import java.util.Collections;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author icha
 */
public class Deck {
    private Stack<Card> PileOfCard;
    private int NumberOfAvailableCard;
    
    public Deck(){
        PileOfCard = new Stack<Card>();
    }
    
    public Card popCard(){
        Card result = PileOfCard.pop();
        NumberOfAvailableCard--;
        return result;
    }
    
    public void pushCard(Card C){
        PileOfCard.push(C);
        NumberOfAvailableCard++;
    }
    
    public void shuffleDeck(){
        Collections.shuffle(PileOfCard);
    }
}
