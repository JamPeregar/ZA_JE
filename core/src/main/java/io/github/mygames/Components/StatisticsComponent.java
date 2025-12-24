/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 *
 * @author Admin
 */
public class StatisticsComponent implements Component{
    public String name;
    public int health = 100;
    public int maxhealth = 100;
    public int armor = 0;
    public int accuracy = 60;
    public int attack_rate = 100;
    public int strengh = 0;
    public float sense_range = 100;
    public boolean is_dead = false;
    public ArrayList inventory;
    
    public EnumSet<CHAR_TRAITS> STAT_FLAGS = EnumSet.noneOf(CHAR_TRAITS.class);
    
    public enum CHAR_TRAITS {
        IMMORTAL,
        SUFFER_CRITICAL_HITS,
        SUFFER_HEADSHOTS,
        SUFFER_HUNGER,
        SUFFER_BLEEDING
    }
    
    public void setflag(CHAR_TRAITS strait, boolean is_adds){
        if (is_adds) {
            STAT_FLAGS.add(strait);
        } else {
            STAT_FLAGS.add(strait);
        }
    }
    
    public boolean checkflag(CHAR_TRAITS strait){
        return STAT_FLAGS.contains(strait);
    }
}
