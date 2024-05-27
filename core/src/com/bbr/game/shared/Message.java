package com.bbr.game.shared;

import java.io.Serializable;

public class Message implements Serializable {
    public Message(){
        text = "Default Message!";
    }
    public Message(String text){this.text = text;}
    String text;
}
