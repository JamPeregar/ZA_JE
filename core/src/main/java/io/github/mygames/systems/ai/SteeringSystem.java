/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.systems.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.mygames.Components.AIComponent;
import io.github.mygames.Components.AIComponent.AIState;
import io.github.mygames.Components.AIComponent.SteeringLocation;
import io.github.mygames.Components.B2dBodyComponent;
import io.github.mygames.Components.FactionComponent;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.StatisticsComponent;
import io.github.mygames.Components.TaskComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.TypeComponent;
import io.github.mygames.Components.WeaponComponent;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class SteeringSystem extends IteratingSystem {
    private ComponentMapper<AIComponent> aiMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<TaskComponent> task_map;
    private ComponentMapper<StatisticsComponent> statsMapper;
    private ComponentMapper<FactionComponent> factionMapper;
    private ComponentMapper<WeaponComponent> wpnMapper;
    private ComponentMapper<B2dBodyComponent> bodyMapper;
    private ImmutableArray<Entity> CharsEntities;
    private ArrayList<Entity> nearbyEntitiesCache;
    
    Random randomizer = new Random();
    
    public SteeringSystem() {
        super(Family.all(AIComponent.class, 
                         TransformComponent.class, 
                         TaskComponent.class,
                         StatisticsComponent.class,
                         FactionComponent.class).get());
    }
    
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        aiMapper = ComponentMapper.getFor(AIComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        //movementMapper = ComponentMapper.getFor(TaskComponent.class);
        task_map = ComponentMapper.getFor(TaskComponent.class);
        statsMapper = ComponentMapper.getFor(StatisticsComponent.class);
        factionMapper = ComponentMapper.getFor(FactionComponent.class);
        wpnMapper = ComponentMapper.getFor(WeaponComponent.class);
        bodyMapper = ComponentMapper.getFor(B2dBodyComponent.class);
        // Получаем персонажей для обнаружения (а мы их и так имеем. Тут был игрок зачем-то)
        CharsEntities = engine.getEntitiesFor(
            Family.all(FactionComponent.class, TransformComponent.class).get()
        );
        nearbyEntitiesCache  = new ArrayList<>();
        System.out.println("AI Added");
    }
    
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AIComponent ai = aiMapper.get(entity);
        TransformComponent transform = transformMapper.get(entity);
        StatisticsComponent stats = statsMapper.get(entity);
        //FactionComponent faction = factionMapper.get(entity);
        //WeaponComponent wpn_cmp = wpnMapper.get(entity); //attack range
        // Проверяем, не мертв ли персонаж
        if (stats.is_dead) {
            if (ai.state != AIState.DEAD) {
                ai.state = AIState.DEAD;
                //ai.steeringBehavior = null;
                //transform.vel.set(0, 0);
            }
            return; // Мертвые не двигаются
        }
        
        // Обновляем таймер состояния
        ai.stateTime += deltaTime;
        
        // Проверяем, не пора ли принимать новое решение
        if (shouldMakeDecision(ai, deltaTime)) {
            makeDecision(entity, ai, transform, stats);
        }
        
        // Обновляем поведение на основе текущего состояния
        //updateSteeringBehavior(entity, ai, transform, stats);
        
        // Обрабатываем текущее состояние
        /*processAIState(entity, ai, transform, stats, deltaTime);
        
        // Применяем steering behavior если он есть
        if (ai.steeringBehavior != null && ai.state != AIState.IDLE && ai.state != AIState.DEAD) {
            // Инициализируем steering output если нужно
            if (ai.steeringOutput == null) {
                ai.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
            }
            
            // Рассчитываем steering
            ai.steeringBehavior.calculateSteering(ai.steeringOutput);
            
            // Применяем к движению
            applySteering(ai, transform, stats, deltaTime);
        } else {
            // Останавливаем движение для IDLE и других состояний без поведения
            if (transform.vel.len2() > 0.01f) {
                // Плавная остановка
                transform.vel.scl(0.9f);
                if (transform.vel.len2() < 0.01f) {
                    transform.vel.set(0, 0);
                }
            }
        }*/
    }
    
    private boolean shouldMakeDecision(AIComponent ai, float deltaTime) {
        ai.decisionTimer -= deltaTime;
        
        // Проверяем условия для принятия решения
        if (ai.decisionTimer <= 0) {
            ai.decisionTimer = ai.decisionCooldown;
            return true;
        }
        
        // Специальные случаи для быстрого реагирования
        /*switch (ai.state) {
            case PURSUE:
            case ATTACK:
                // Чаще проверяем при преследовании/атаке
                return ai.stateTime > 0.2f;
            case EVADE:
                // Чаще проверяем при бегстве
                return ai.stateTime > 0.1f;
            default:
                return false;
        }*/
        return false;
    }
    
    private void makeDecision(Entity self, AIComponent ai, TransformComponent transform, 
                             StatisticsComponent stats) {
        
        //System.out.println("MAKE DEC");
        // Проверяем ближайших существ
        ArrayList<Entity> nearbyEntities = getNearbyEntities(self, 
                transform.coords, 
                ai.detectionRadius);
        //System.out.println("entities near: " + nearbyEntities.size());
        
        // Ищем ближайшую враждебную цель
        Entity closestHostile = findClosestHostile(self, nearbyEntities, transform.coords);
        
        // Проверяем наличие опасности (для EVADE)
        Entity closestDanger = findClosestDanger(self, nearbyEntities, transform.coords, stats);
        
        // Логика принятия решений на основе текущего состояния
        switch (ai.state) {
            case IDLE:
                //handleIdleState(self, ai, stats, closestHostile, closestDanger);
                break;
                
            case WANDER:
                handleWanderState(self, ai, stats, closestHostile, closestDanger);
                break;
                
            case PATROL:
                //handlePatrolState(self, ai, stats, closestHostile, closestDanger);
                break;
                
            case INVESTIGATE:
                //handleInvestigateState(self, ai, transform, stats, closestHostile);
                break;
                
            case PURSUE:
                //handlePursueState(self, ai, transform, stats, closestHostile);
                break;
                
            case ATTACK:
                //handleAttackState(self, ai, transform, stats, closestHostile);
                break;
                
            case EVADE:
                //handleEvadeState(self, ai, stats, closestDanger);
                break;
                
            case GOSLING:
                // Ничего не делаем - специальное состояние бездействия
                break;
                
            case DEAD:
                // Ничего не делаем
                break;
        }
        
        // Обновляем таймер для следующего решения
        ai.decisionTimer = ai.decisionCooldown;
        // Сбрасываем таймер состояния
        //ai.stateTime = 0;
    }
    
    private void handleIdleState(Entity self, AIComponent ai, StatisticsComponent stats,
                                Entity closestHostile, Entity closestDanger) {
        // Проверяем опасность
        if (closestDanger != null) {
            //ai.state = AIState.EVADE;
            //ai.targetEntity = closestDanger;
            return;
        }
        
        // Проверяем врагов
        if (closestHostile != null) {
            //ai.state = AIState.PURSUE;
            //ai.targetEntity = closestHostile;
            return;
        }
        
        TransformComponent transform = transformMapper.get(self);
        TaskComponent task_cmp = task_map.get(self);
        if (ai.stateTime > 5f) {
            ai.state = AIState.WANDER;
            ai.stateTime = 0;
            System.out.println("GO WANDER AROUND");
        } else if (task_cmp.the_task == TaskComponent.TaskEnum.MOVE_VEL){
            //move to random point
            task_cmp.the_task = TaskComponent.TaskEnum.STOP_MOVING;
            transform.vel.set(Vector2.Zero);
            
        }
        
    }
    
    private void handleWanderState(Entity self, AIComponent ai, StatisticsComponent stats,
                                  Entity closestHostile, Entity closestDanger) {
        // Проверяем опасность
        if (closestDanger != null) {
            //ai.state = AIState.EVADE;
            //ai.targetEntity = closestDanger;
            return;
        }
        
        // Проверяем врагов
        if (closestHostile != null) {
            //ai.state = AIState.PURSUE;
            //ai.targetEntity = closestHostile;
            
            return;
        }
        
        // Случайный переход к IDLE или идти в случайную точку (потом мб по дорогам)
        TransformComponent transform = transformMapper.get(self);
        TaskComponent task_cmp = task_map.get(self);
        WeaponComponent weapon_cmp = wpnMapper.get(self);
        B2dBodyComponent bod_cmp = bodyMapper.get(self);
        
        /*if (ai.stateTime > 3f) {
            ai.state = AIState.IDLE;
            ai.previous_state = AIState.WANDER;
            ai.stateTime = 0;
            System.out.println("GO STAND");
        } else if (task_cmp.the_task != TaskComponent.TaskEnum.MOVE_VEL) {
            //move to random point
            transform.vel.set(randomizer.nextFloat(-transform.acceleration,transform.acceleration),randomizer.nextFloat(-transform.acceleration,transform.acceleration));
            task_cmp.the_task = TaskComponent.TaskEnum.MOVE_VEL;
        }*/
        
        if (task_cmp.the_task == TaskComponent.TaskEnum.WANDER) {
            
            task_cmp.the_task = TaskComponent.TaskEnum.MOVE_FORWARD;
            //System.out.println("MOVE_FORWARD INIT AI");
                
        } 
        //if (task_cmp.the_task == TaskComponent.TaskEnum.NONE) {return;}
        else if (task_cmp.the_task == TaskComponent.TaskEnum.MOVE_FORWARD) {
            if (ai.stateTime > 2f) {
                task_cmp.the_task = TaskComponent.TaskEnum.STOP_MOVING;
                //System.out.println("STOP_MOVING 1 AI");
            }
                
        } 
        else if (task_cmp.the_task == TaskComponent.TaskEnum.NONE) {
            transform.rotate_to_angle = randomizer.nextFloat(0f,360f);
            task_cmp.the_task = TaskComponent.TaskEnum.ROTATE_TO_VEL_SIMPLE;
           // System.out.println("TURN RANDOM 1 AI");
            
        } else if (task_cmp.the_task == TaskComponent.TaskEnum.ROTATE_TO_VEL_SIMPLE && task_cmp.is_done) {
            if (ai.stateTime > 5f) {
                task_cmp.the_task = TaskComponent.TaskEnum.MOVE_FORWARD;
                //System.out.println("STOP_MOVING 1 AI");
            }
            
            //System.out.println("MOVE_FORWARD 2 AI");
        } else {
            System.out.println("AI TASK - " + task_cmp.the_task);
        }
    }
    
    private void handlePatrolState(Entity self, AIComponent ai, StatisticsComponent stats,
                                  Entity closestHostile, Entity closestDanger) {
        // Проверяем опасность
        if (closestDanger != null) {
            ai.state = AIState.EVADE;
            ai.targetEntity = closestDanger;
            return;
        }
        
        // Проверяем врагов
        if (closestHostile != null) {
            ai.state = AIState.PURSUE;
            ai.targetEntity = closestHostile;
            return;
        }
        
        // Проверяем достижение точки патрулирования
        if (ai.patrolPoints != null && !ai.patrolPoints.isEmpty()) {
            Vector3 targetPoint = ai.patrolPoints.get(ai.currentPatrolIndex);
            TransformComponent transform = transformMapper.get(self);
            
            if (transform != null && transform.coords.dst(targetPoint) < 10f) {
                // Переходим к следующей точке
                ai.currentPatrolIndex = (ai.currentPatrolIndex + 1) % ai.patrolPoints.size();
            }
        }
    }
    
    private void handleInvestigateState(Entity self, AIComponent ai, TransformComponent transform,
                                       StatisticsComponent stats, Entity closestHostile) {
        // Проверяем, не нашли ли мы цель
        if (closestHostile != null) {
            ai.state = AIState.PURSUE;
            ai.targetEntity = closestHostile;
            
            return;
        }
        //Исп Memory
        //... Запомнил последнюю точку контакта - расследует
    }
    
    private void handlePursueState(Entity self, AIComponent ai, TransformComponent transform,
                                  StatisticsComponent stats, Entity closestHostile) {
        // Если цель потеряна
        if (closestHostile == null || closestHostile != ai.targetEntity) {
            // Переходим в режим поиска
            ai.state = AIState.INVESTIGATE;
            ai.previous_state = AIState.PURSUE;
            //ai.targetPosition = memory.lastSeenTarget
            /*TransformComponent hostile_transform = transformMapper.get(closestHostile);
            if (ai.targetEntity != null) {
                TransformComponent targetTransform = transformMapper.get(ai.targetEntity);
                if (targetTransform != null) {
                    hostile_transform.coords = new Vector3(targetTransform.coords);
                }
            }
            ai.targetEntity = null;
            */
            
            return;
        }
        
        // Проверяем расстояние для атаки
        TransformComponent targetTransform = transformMapper.get(closestHostile);
        WeaponComponent wpn_cmp = wpnMapper.get(self); //attack range
        if (targetTransform != null) {
            float distance = transform.coords.dst(targetTransform.coords);
            if (distance <= wpn_cmp.range) {
                ai.state = AIState.ATTACK;
            }
        }
    }
    
    private void handleAttackState(Entity self, AIComponent ai, TransformComponent transform,
                                  StatisticsComponent stats, Entity closestHostile) {
        // Если цель мертва или потеряна
        if (closestHostile == null || closestHostile != ai.targetEntity) {
            ai.state = AIState.INVESTIGATE;
            ai.previous_state = AIState.ATTACK;
            ai.targetEntity = null;
            return;
        }
        
        // Проверяем расстояние
        TransformComponent targetTransform = transformMapper.get(closestHostile);
        if (targetTransform != null) {
            float distance = transform.coords.dst(targetTransform.coords);
            if (distance > ai.attackRange) {
                ai.state = AIState.PURSUE;
            }
        }
    }
    
    private void handleEvadeState(Entity self, AIComponent ai, StatisticsComponent stats,
                                 Entity closestDanger) {
        // Если опасность миновала
        if (closestDanger == null) {
            // Возвращаемся к предыдущему состоянию
            ai.state = ai.previous_state != null ? ai.previous_state : AIState.IDLE;
            ai.targetEntity = null;
            return;
        }
        
        // Проверяем здоровье для принятия решения
        if (stats.health > stats.maxhealth * 0.5f && ai.stateTime > 3f) {
            // Если здоровье восстановилось, можно перестать убегать
            StatisticsComponent dangerStats = statsMapper.get(closestDanger);
            if (dangerStats != null && dangerStats.health < stats.health * 0.7f) {
                ai.state = AIState.PURSUE;
                ai.targetEntity = closestDanger;
            }
        }
    }
    
    private void updateSteeringBehavior(Entity entity, AIComponent ai, 
                                       TransformComponent transform, StatisticsComponent stats) {
        
        // Создаем location для steering
        SteeringLocation location = new SteeringLocation(transform.coords);
        
        // Обновляем поведение в зависимости от состояния
        switch (ai.state) {
            case IDLE:
                ai.steeringBehavior = null;
                break;
                
            case WANDER:
                if (ai.steeringBehavior == null || !(ai.steeringBehavior instanceof Wander)) {
                    Wander<Vector2> wander = new Wander<>((Steerable<Vector2>) location)
                        .setWanderOffset(ai.wanderDistance)
                        .setWanderRadius(ai.wanderRadius)
                        .setWanderRate(ai.wanderJitter)
                        .setFaceEnabled(false);
                    
                    ai.steeringBehavior = wander;
                }
                break;
                
            case PATROL:
                if (ai.patrolPoints != null && ai.patrolPoints.size() > 0) {
                    Vector3 targetPoint = ai.patrolPoints.get(ai.currentPatrolIndex);
                    Seek<Vector2> seek = new Seek<Vector2>((Steerable<Vector2>) location, 
                        new SteeringLocation(targetPoint));
                    
                    ai.steeringBehavior = seek;
                }
                break;
                
            case INVESTIGATE:
                if (ai.targetPosition != null) {
                    Arrive<Vector2> arrive = new Arrive<Vector2>((Steerable<Vector2>) location,
                        new SteeringLocation(ai.targetPosition))
                        .setArrivalTolerance(5f)
                        .setDecelerationRadius(30f)
                        .setTimeToTarget(0.5f);
                    
                    ai.steeringBehavior = arrive;
                }
                break;
                
            case PURSUE:
                /*if (ai.targetEntity != null) {
                    TransformComponent targetTransform = transformMapper.get(ai.targetEntity);
                    if (targetTransform != null) {
                        Pursue<Vector2> pursue = new Pursue<Vector2>(location,
                            new SteeringLocation(targetTransform.coords))
                            .setMaxPredictionTime(0.8f);
                            //.setMaxLinearAcceleration(stats.maxLinearAcceleration);
                        
                        ai.steeringBehavior = pursue;
                    }
                }*/
                break;
                
            case ATTACK:
                /*if (ai.targetEntity != null) {
                    TransformComponent targetTransform = transformMapper.get(ai.targetEntity);
                    if (targetTransform != null) {
                        // Для атаки используем Seek или Pursue на короткой дистанции
                        Seek<Vector2> seek = new Seek<Vector2>(location,
                            new SteeringLocation(targetTransform.coords)); 
                        
                        ai.steeringBehavior = seek;
                    }
                }*/
                break;
                
            case EVADE:
                /*if (ai.targetEntity != null) {
                    TransformComponent targetTransform = transformMapper.get(ai.targetEntity);
                    if (targetTransform != null) {
                        Evade<Vector2> evade = (Evade<Vector2>) new Evade<>(location,
                            new SteeringLocation(targetTransform.coords))
                            .setMaxPredictionTime(0.5f); // Быстрее при бегстве
                        
                        ai.steeringBehavior = evade;
                    }
                }*/
                break;
                
            case GOSLING:
            case DEAD:
                ai.steeringBehavior = null;
                break;
        }
    }
    
    private void processAIState(Entity entity, AIComponent ai, TransformComponent transform,
                               StatisticsComponent stats, float deltaTime) {
        // Обработка специфических действий для каждого состояния
        switch (ai.state) {
            case ATTACK:
                // Здесь можно добавить логику атаки
                // Например, вызов системы боя
                if (ai.targetEntity != null && ai.stateTime >= 1f / stats.attack_rate) {
                    // Сброс таймера для следующей атаки
                    ai.stateTime = 0;
                    // Инициировать атаку через систему событий
                }
                break;
                
            case INVESTIGATE:
                // Медленное движение при расследовании
                if (ai.steeringBehavior != null && transform.acceleration > 50) {
                    // Ограничиваем скорость при расследовании
                    transform.acceleration *= 0.7f;
                }
                break;
        }
    }
    
    private void applySteering(AIComponent ai, TransformComponent transform, 
                              StatisticsComponent stats, float deltaTime) {
        
        if (ai.steeringOutput != null) {
            // Применяем линейное ускорение
            Vector2 linearAccel = ai.steeringOutput.linear;
            
            // Ограничиваем ускорение
            float maxAccel = transform.maxLinearAcceleration;
            if (linearAccel.len2() > maxAccel * maxAccel) {
                linearAccel.nor().scl(maxAccel);
            }
            
            // Обновляем скорость
            transform.vel.x += linearAccel.x * deltaTime;
            transform.vel.y += linearAccel.y * deltaTime;
            
            // Ограничиваем максимальную скорость
            float maxSpeed = transform.acceleration;
            float currentSpeed2 = transform.vel.len2();
            if (currentSpeed2 > maxSpeed * maxSpeed) {
                transform.vel.nor().scl(maxSpeed);
            }
            
            // Минимальная скорость для остановки
            if (currentSpeed2 < 0.1f && linearAccel.len2() < 0.1f) {
                transform.vel.set(0, 0);
            }
        }
    }
    
    private ArrayList<Entity> getNearbyEntities(Entity self, Vector3 position, 
                                           float radius) {
        
        nearbyEntitiesCache.clear();
        float radiusSquared = radius * radius;
        
        for (Entity entity : CharsEntities) {
            if (entity == self) continue;
            
            TransformComponent otherTransform = transformMapper.get(entity);
            if (otherTransform == null) continue;
            
            // Проверяем расстояние
            if (position.dst2(otherTransform.coords) <= radiusSquared) {
                nearbyEntitiesCache.add(entity);
            }
        }
        
        return nearbyEntitiesCache;
    }
    
    private Entity findClosestHostile(Entity self, ArrayList<Entity> entities, Vector3 position) {
        Entity closest = null;
        StatisticsComponent self_stats = statsMapper.get(self);
        if (self_stats == null) return null;
        float closestDistance = self_stats.sense_range;
        
        FactionComponent selfFaction = factionMapper.get(self);
        if (selfFaction == null) return null;
        for (Entity entity : entities) {
            FactionComponent otherFaction = factionMapper.get(entity);
            if (otherFaction == null) continue;
            //System.out.println("TRY HOSTILE - " + selfFaction.self_aware + " vs " + otherFaction.self_aware);
            // Проверяем враждебность
            if (selfFaction.isHostileTo(otherFaction.self_aware)) {
                //System.out.println("IS HOSTILE");
               // System.out.println(selfFaction.self_aware + " IS HOSTILE to " + otherFaction.self_aware);
                TransformComponent transform = transformMapper.get(entity);
                    
                if (transform != null) {
                    float distance = position.dst2(transform.coords);
                    if (distance < closestDistance) {
                        closest = entity;
                        closestDistance = distance;
                        
                    }
                }
            }
        }
        
        return closest;
    }
    
    private Entity findClosestDanger(Entity self, ArrayList<Entity> entities, 
                                    Vector3 position, StatisticsComponent stats) {
        
        // По умолчанию ищем более сильных врагов
        Entity closestDanger = null;
        float closestDistance = stats.sense_range;
        //float dangerThreshold = 1.5f; // Во сколько раз враг должен быть сильнее
        
        FactionComponent selfFaction = factionMapper.get(self);
        if (selfFaction == null) return null;
        
        for (Entity entity : entities) {
            FactionComponent otherFaction = factionMapper.get(entity);
            if (otherFaction == null) continue;
            
            // Проверяем враждебность
            if (selfFaction.isHostileTo(otherFaction.self_aware)) {
                TransformComponent transform = transformMapper.get(entity);
                if (transform != null) {
                    float distance = position.dst2(transform.coords);
                    if (distance < closestDistance) {
                        closestDanger = entity;
                        closestDistance = distance;
                    }
                }
            }
        }
        
        return closestDanger;
    }
}
