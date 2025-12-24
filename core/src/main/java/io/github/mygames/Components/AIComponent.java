/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AIComponent implements Component {
    // Steering behavior
    public SteeringBehavior<Vector2> steeringBehavior;
    public SteeringAcceleration<Vector2> steeringOutput;
    public Vector3 targetPosition;
    public AIState state = AIState.IDLE;
    public AIState previous_state = AIState.GOSLING;
    public AIState default_state = AIState.GOSLING;
    //public float maxLinearSpeed = 100f;
    //public float maxLinearAcceleration = 200f;
    //public float maxAngularSpeed = 30f; //max turn spee
    //public float maxAngularAcceleration = 45f;
    public float zeroLinearSpeedThreshold = 0.001f;
    
    // Для патрулирования
    public ArrayList<Vector3> patrolPoints = new ArrayList<>();
    public int currentPatrolIndex;
    public boolean patrolLoop = true;
    
    // Состояние ИИ (некоторое есть в других компонентах)
    public Entity targetEntity;
    public float wanderRadius = 3f;
    public float wanderDistance = 7f;
    public float wanderJitter = 20f;
    public float detectionRadius = 200f;
    public float attackRange = 50f;
    /*public AIState state = AIState.IDLE;
    public Entity targetEntity;
    public float detectionRadius = 200f;
    public float attackRadius = 50f;
    public float wanderRadius = 3f;
    public float wanderDistance = 7f;
    public float wanderJitter = 20f;
    */
    // Временные переменные
    public float stateTime;
    public float decisionCooldown;
    public float decisionTimer;
    
    //память
    public static class MemoryEntry {
        public Entity entity;
        public Vector3 lastKnownPosition;
        public float lastSeenTime;
        //public float threatLevel;
    }
    
    public float memoryDuration = 30f; // 30 секунд памяти
    
    public enum AIState {
        IDLE,           // Стоит на месте
        WANDER,         // Блуждает случайно
        PATROL,         // Патрулирует по точкам
        INVESTIGATE,    // Ищет врагов
        PURSUE,         // Преследует цель
        ATTACK,         // Атакует
        EVADE,          // Убегает
        GOSLING,        // Да пофиг ему
        DEAD            // Мертв
    }
    
    public static class SteeringLocation implements Location<Vector2>{
    private Vector3 position;
    private float orientation;
    
    public SteeringLocation(Vector3 position) {
        this.position = position;
        this.orientation = 0;
    }
    
    @Override
    public Vector2 getPosition() {
        return new Vector2(position.x, position.y);
    }
    
    @Override
    public float getOrientation() {
        return orientation;
    }
    
    @Override
    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }
    
    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }
    
    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }
    
    @Override
    public Location<Vector2> newLocation() {
        return new SteeringLocation(new Vector3());
    }

        /*@Override
        public Vector2 getLinearVelocity() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public float getAngularVelocity() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public float getBoundingRadius() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean isTagged() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setTagged(boolean tagged) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public float getZeroLinearSpeedThreshold() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setZeroLinearSpeedThreshold(float value) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public float getMaxLinearSpeed() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setMaxLinearSpeed(float maxLinearSpeed) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public float getMaxLinearAcceleration() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setMaxLinearAcceleration(float maxLinearAcceleration) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public float getMaxAngularSpeed() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setMaxAngularSpeed(float maxAngularSpeed) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public float getMaxAngularAcceleration() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setMaxAngularAcceleration(float maxAngularAcceleration) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }*/
    }
}

// AIState.java

