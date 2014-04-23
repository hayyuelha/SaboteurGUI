/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nim_13512080
 */
class Action_Card extends Card {
    private int id; //1=ViewMap 2=break 3=repair
    public Action_Card(int i) {
        id = i;
    }
    
    @Override
    public int getType(){
        return 2;
    }
    
    @Override
    public int getID(){
        return id;
    }
    
    @Override
    public void displayCard(){
        System.out.println("ini actioncard");
    }
}
