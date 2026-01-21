/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import java.util.EnumSet;

/**
 *
 * @author Admin
 */
public class StateComponent implements Component{
    public StateEnum the_state = StateEnum.STAYING;
    //public EnumSet<StateEnum> state_flags = EnumSet.noneOf(StateEnum.class);
    
    public enum StateEnum {
    FREEZE,
    STAYING,
    MOVING
    //ROTATE,
    //FLYING
    }
}
