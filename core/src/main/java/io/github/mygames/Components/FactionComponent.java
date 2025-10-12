/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import io.github.mygames.Components.enums.Faction;

/**
 *
 * @author Admin
 */
public class FactionComponent implements Component{
    /**Relationships matrix for all factions**/
    public static int[][] political_map = new int[Faction.values().length][Faction.values().length]; // relations to other factions
    /**Relationships between character and factions**/
    public int[] relationships = new int[Faction.values().length];
    /**Faction of character**/
    public Faction self_aware = Faction.NONE;
    
}
