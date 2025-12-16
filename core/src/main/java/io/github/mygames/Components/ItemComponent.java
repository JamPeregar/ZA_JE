/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import java.util.EnumSet;

/**
 *
 * @author Admin
 */
public class ItemComponent {
    public String name;
    public String description;
    public byte item_weight;
    
    public ItemType itype;
    
    public enum ItemType {
        GARBAGE,
        EATABLE,
        WEAPON,
        ACCESSORIZE
    }
}
