/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;

/**
 *
 * @author Admin
 */
public class FactionComponent implements Component{
    /**Relationships matrix for all factions**/
    public static int[][] political_map = new int[FactionEnum.values().length][FactionEnum.values().length]; // relations to other factions
    /**Relationships between character and factions**/
    public int[] relationships = new int[FactionEnum.values().length];
    /**Faction of character**/
    public FactionEnum self_aware = FactionEnum.NONE;
    
    public static enum FactionEnum {
        NONE,
        PLAYER,
        ZOMBIE,
        BANDIT,
        SURVIVOR,
        ARMY,
        FARMER,
        MERCENARY,
        MILITIA,
        ROUGE,
        ASSHOLE,
        SPEC1,
        SPEC2,
        SPEC3
    }
    
    public static int FactionToInteger(FactionEnum faction) {
        for (int i = 0; i < FactionEnum.values().length; i++) {
            if (FactionEnum.values()[i] == faction) {
                return i;
            }
        }
        return 0;
    }
    
    public boolean isHostileTo(FactionEnum faction) {
        final int fac_num = FactionToInteger(faction);
        
        for (int i = 0; i < relationships.length; i++) {
            if (FactionEnum.values()[i] != faction) {
                if (political_map[fac_num][i] < 0) return true; 
            }
        }
        return false;
    }
    
}
