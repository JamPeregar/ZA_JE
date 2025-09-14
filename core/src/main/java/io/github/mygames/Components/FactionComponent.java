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
    // May be add some dynamic
    public static int[][] relationships = new int[Faction.values().length][Faction.values().length]; // relations to other factions
    //public Factions[] relationships = new Factions[11]; // relations to other factions
    public Faction self_aware = Faction.NONE;
    
}
