/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nim_13512080
 */
class Path_Card extends Card {

    private char top;
    private char right;
    private char bottom;
    private char left;
    private char center;
    private int id;
    public Path_Card(char c, char c0, char c1, char c2, char c3, int i) {
        right = c;
        left = c0;
        bottom = c1;
        top = c2;
        center = c3;
        id = i;
    }
    
    @Override
    public int getType(){
        return 1;
    }
    
    @Override
    public int getID(){
        return id;
    }
    
    @Override
    public void displayCard(){
        System.out.println("Ini pathcard");
    }
    
    public char getRight(){
        return right;
    }
    
    public char getLeft(){
        return left;
    }
    
    public char getTop(){
        return top;
    }
    
    public char getBottom(){
        return bottom;
    }
    
    public char getCenter(){
        return center;
    }
    
    public boolean canBePlacedRightOf(Path_Card C){
        if((this.left=='1' && C.getRight()=='1')||(this.left=='0' && C.getRight()=='0') ){
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean canBePlacedLeftOf(Path_Card C){
        if((this.right=='1' && C.getLeft()=='1')||(this.right=='0' && C.getLeft()=='0') ){
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean canBePlacedAboveOf(Path_Card C){
        if((this.bottom=='1' && C.getTop()=='1')||(this.bottom=='0' && C.getTop()=='0') ){
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean canBePlacedBelowOf(Path_Card C){
        if((this.top=='1' && C.getBottom()=='1')||(this.top=='0' && C.getBottom()=='0') ){
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean CompareCard(Path_Card C){
        return ((this.top==C.getTop())&&(this.bottom==C.getBottom())&&(this.right==C.getRight())&&(this.left==C.getLeft())&&(this.center==C.getCenter())&&(this.id==C.getID()));
    }
}

