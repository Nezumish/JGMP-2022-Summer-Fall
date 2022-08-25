package com.rntgroup.code.quality.terrible_domain;

public class horror{

    public horrortype type;

    public void some_horror_stuff() {
        if (type==horrortype.chtonic){
            System.out.println("it's a chtonic horror");
        }
        else{
            switch(type){
                case Lovecraftian:
                    System.out.println("it's a Lovecraftian horror");
                    break;
                case unreasonable:
                    System.out.println("it's an unreasonable horror");
                    break;
                case indescribable:
                    System.out.println("it's an indescribable horror");
                    break;
                default:
                    System.out.println("you've unlocked a new horror!");
            }
        }
    }

}
