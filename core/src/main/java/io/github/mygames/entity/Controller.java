/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TaskComponent;

/**
 *
 * @author Admin
 */
public class Controller extends InputAdapter{
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    
    NpcGenericEntity puppet;

    public Controller(NpcGenericEntity character) {
        puppet = character;
    }
    
    public void update(float deltatime) {
        upPressed = Gdx.input.isKeyPressed(Input.Keys.W) || 
                    Gdx.input.isKeyPressed(Input.Keys.UP);
        downPressed = Gdx.input.isKeyPressed(Input.Keys.S) || 
                      Gdx.input.isKeyPressed(Input.Keys.DOWN);
        leftPressed = Gdx.input.isKeyPressed(Input.Keys.A) || 
                      Gdx.input.isKeyPressed(Input.Keys.LEFT);
        rightPressed = Gdx.input.isKeyPressed(Input.Keys.D) || 
                       Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        
        if (upPressed || downPressed || leftPressed || rightPressed) {
            domove();
            //puppet.performTask(TaskComponent.TaskEnum.MOVE_FORWARD);
            //System.out.println("GO");
        } else {
            puppet.performTask(TaskComponent.TaskEnum.STOP_MOVING);
            //puppet.setVelocity(Vector2.Zero);
            //System.out.println("STOP");
        }
        
    }
    
    public void domove() {
        Vector2 vel = new Vector2();
        float speed = puppet.position_cmp.acceleration;
        if (rightPressed) {
            //vel.add(speed,0f);
            puppet.performTask(TaskComponent.TaskEnum.ROTATE_RIGHT);
        }
        if (leftPressed) {
            //vel.add(-1*speed,0f);
            //puppet.position_cmp.rotate_speed
            puppet.performTask(TaskComponent.TaskEnum.ROTATE_LEFT);
            //puppet.bod_cmp.body.setAngularVelocity(speed);
        }
        if (!upPressed && !downPressed) {
            return;
        }
        if (upPressed) {
            //vel.add(0f,speed);
            puppet.performTask(TaskComponent.TaskEnum.MOVE_FORWARD);
        }
        if (downPressed) {
            //vel.add(0f,-1*speed);
            puppet.performTask(TaskComponent.TaskEnum.MOVE_BACKWARD);
        }
        //puppet.setVelocity(vel);
        //puppet.performTask(TaskComponent.TaskEnum.MOVE_VEL);
    }

    //@Override
    //public boolean keyDown(int keycode) ;
    
}
