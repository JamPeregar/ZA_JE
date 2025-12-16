/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import java.util.List;

/**
 *
 * @author Admin
 */
public class InventoryComponent implements Component{
    public List<ItemComponent> items;
    public float inv_weight;
}
