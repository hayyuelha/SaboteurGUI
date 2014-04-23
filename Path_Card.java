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
}

