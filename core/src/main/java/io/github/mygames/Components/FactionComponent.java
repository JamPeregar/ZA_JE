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
    
    public boolean isHostileTo(FactionEnum faction) {
        
        return true;
    }
    
}
