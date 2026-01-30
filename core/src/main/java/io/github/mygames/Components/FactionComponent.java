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
    public static final int[][] political_map = new int[FactionEnum.values().length][FactionEnum.values().length]; // relations to other factions
    
    /**Relationships between character and factions**/
    public int[] relationships = new int[FactionEnum.values().length];
    /**Faction of character**/
    public FactionEnum self_aware = FactionEnum.NONE;
    
    public static enum FactionEnum {
        NONE,   //special
        PLAYER,
        ZOMBIE, //playable factions 
        BANDIT,
        ASSHOLE,
        SURVIVOR,
        FARMER,
        MILITIA,
        ARMY,
        MERCENARY,
        ROUGE,
        CULTIST, //special
        SPEC1,
        SPEC2,
        SPEC3
    }
    
    static {
        for (int i = 0; i < political_map[0].length; i++) {
            if (i < FactionEnum.ZOMBIE.ordinal() || i > FactionEnum.CULTIST.ordinal()){
                    continue;   //skip non-plaable factions or self faction / 0 dispos
                }
            
            for (int j = 0; j < political_map.length; j++) {
                if (i == j || j < FactionEnum.ZOMBIE.ordinal() || j > FactionEnum.CULTIST.ordinal()){
                    //political_map[i][j] = 404;
                    continue;   //skip non-plaable factions or self faction
                }
                
                switch (FactionEnum.values()[i]) {
                    case ZOMBIE:
                        political_map[i][j] = -1; //zombie hates everyone?
                        break;
                    case FARMER:
                        if (   
                                (j >= FactionEnum.SURVIVOR.ordinal()
                                && j <= FactionEnum.MILITIA.ordinal())
                            ){
                            political_map[i][j] = 1;
                        } else {political_map[i][j] = -1;}
                        //political_map[j][i] = political_map[i][j];
                        break;
                    case BANDIT:
                        if (   
                                (j >= FactionEnum.BANDIT.ordinal()
                                && j <= FactionEnum.ASSHOLE.ordinal())
                            ){
                            political_map[i][j] = 1;
                        } else {political_map[i][j] = -1;}
                        //political_map[j][i] = political_map[i][j];
                        break;
                    case ARMY:
                        political_map[i][j] = -1;
                        //political_map[j][i] = political_map[i][j];
                        break;
                    default:
                        if (political_map[j][i] != 0) {
                            political_map[i][j] = political_map[j][i];
                        }
                }
                
            }
        }
        
    }
    
    public static void show_polmap() {
        System.out.println("FACTION MAP: \n" + political_map.toString());
        //System.out.print("  ");
        for (int i = 0; i < political_map[0].length; i++) {
            System.out.printf("\t%s%s", FactionEnum.values()[i].name().charAt(0),FactionEnum.values()[i].name().charAt(1));
        }
        System.out.println();
        for (int i = 0; i < political_map.length; i++) {
            //System.out.println();
            System.out.print(FactionEnum.values()[i].name().charAt(0));
            for (int j = 0; j < political_map.length; j++) {
                
                //System.out.print("\t \t" + political_map[i][j]);
                System.out.printf("\t%2d", political_map[i][j]);
            }
            System.out.println();
        }
        
    }
    
    public static int FactionToInteger(FactionEnum faction) {
        for (int i = 0; i < FactionEnum.values().length; i++) {
            if (FactionEnum.values()[i] == faction) {
                return i;
            }
        }
        return 0;
    }
    
    public void default_relations() {
        
        
        for (int i = 0; i < political_map[0].length; i++) {
            if (FactionEnum.values()[i] != self_aware) {
                if (political_map[self_aware.ordinal()][i] < 0) {
                    relationships[i] = political_map[self_aware.ordinal()][i];
                }
            }
        }
        /*System.out.println("Relations for " + self_aware.toString());
        System.out.println();
        for (FactionEnum f : FactionEnum.values()) {
            System.out.println(f.toString() + " - " +relationships[f.ordinal()]);
        }*/
    }
    
    public boolean isHostileTo(FactionEnum faction) {
        final int fac_num = faction.ordinal();
        
        for (int i = 0; i < political_map[0].length; i++) {
            if (FactionEnum.values()[i] != faction) {
                if (political_map[fac_num][i] < 0) return true; 
            }
        }
        return false;
    }
    
}
