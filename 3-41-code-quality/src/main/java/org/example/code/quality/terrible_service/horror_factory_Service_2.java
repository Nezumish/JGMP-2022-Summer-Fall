package org.example.code.quality.terrible_service;

import org.example.code.quality.terrible_domain.horror;
import org.example.code.quality.terrible_domain.horror_creature;
import org.example.code.quality.terrible_domain.horrortype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class horror_factory_Service_2 {

    static public int cr_coef = 50;

    int next_creature_hash = 1;

    ArrayList creatures = new ArrayList();

    public class horror_factory_creature extends horror_creature implements Serializable, Cloneable {

        public org.example.code.quality.terrible_domain.horror horror;

        int hash;

        public horror_factory_creature() {
            hash = next_creature_hash++;
            int rand_h_ = new Random().nextInt(horrortype.values().length);
            horror=new horror();
            horror.type=horrortype.values()[rand_h_];
        }

        @Override
        public void make_some_noise() {
            super.make_some_noise();
            horror.some_horror_stuff();
        }

        @Override
        public void self_position() {
            System.out.println("i should never exist, im lame");
            super.self_position();
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return ((horror_factory_service.horror_factory_creature) obj).equals(this);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException{
            horror_factory_creature horror_factory_creature_sibbling=new horror_factory_creature();
            horror_factory_creature_sibbling.horror=this.horror;

            return horror_factory_creature_sibbling;
        }

        @Override
        public String toString(){
            System.out.println("im creature " + hash);
            make_some_noise();
            self_position();
            return "";
        }
    }

    public horror_creature do_creature(){
        int random=new Random(cr_coef).nextInt()>cr_coef?1:2;
        if (random ==1){
            creatures.add(new horror_creature());
            return (horror_creature)creatures.get(creatures.size()-1);}
        else { creatures.add(new horror_factory_creature()); return (horror_creature)creatures.get(creatures.size()-1); }
    }

}
