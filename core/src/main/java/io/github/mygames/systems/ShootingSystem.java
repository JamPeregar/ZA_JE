/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import io.github.mygames.Components.BulletComponent;
import io.github.mygames.Components.DamageComponent;
import io.github.mygames.Components.StatisticsComponent;
//import io.github.mygames.Components.StatisticsComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.WeaponComponent;
import io.github.mygames.Components.DamageComponent.DamageType;

/**
 *
 * @author Admin
 */
public class ShootingSystem extends IteratingSystem {

    private ComponentMapper<WeaponComponent> wm;
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<StatisticsComponent> stat_m;
    private final ComponentMapper<BulletComponent> bullet_mapper = ComponentMapper.getFor(BulletComponent.class);
    private World box2dWorld;
    private Engine ashleyEngine;
    
    public ShootingSystem(World box2dWorld, Engine ashleyEngine) {
        super(Family.all(WeaponComponent.class, TransformComponent.class).get());
        
        this.box2dWorld = box2dWorld;
        this.ashleyEngine = ashleyEngine;
        wm = ComponentMapper.getFor(WeaponComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        stat_m = ComponentMapper.getFor(StatisticsComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WeaponComponent weapon = wm.get(entity);
        TransformComponent transform = tm.get(entity);
        if (weapon.make_shoot) {
            shoot(entity, weapon.aimPoint);
        }
        weapon.lastShotTime += deltaTime;
    }
    
    public void shoot(Entity shooter, Vector2 targetPosition) {
        WeaponComponent weapon = shooter.getComponent(WeaponComponent.class);
        TransformComponent transform = shooter.getComponent(TransformComponent.class);
        
        if (weapon == null || transform == null) return;
        
        // Проверка кулдауна
        if (weapon.lastShotTime < 1f / weapon.fireRate) return;
        
        weapon.lastShotTime = 0;
        //System.out.println("some shoot!");
        switch (weapon.projectileType) {
            case BULLET:
                performHitscanShot(shooter, targetPosition, weapon);
                break;
            case PROJECTILE:
                launchProjectile(shooter, targetPosition, weapon);
                break;
            case MELEE:
                performMeleeAttack(shooter, targetPosition, weapon);
                break;
            default:
                System.out.println("Unhandled weapontype");
        }
        weapon.make_shoot = false;
    }
    //-------FUNCTIONS----------//
    private void performHitscanShot(Entity shooter, Vector2 target, WeaponComponent weapon) {
        // Вычисляем точку выстрела в мировых координатах
        //something wrong here...
        Vector2 firePoint = new Vector2(shooter.getComponent(TransformComponent.class).coords.x, shooter.getComponent(TransformComponent.class).coords.y);
        //Vector2 firePoint =  coords_2d;   //new Vector2(weapon.firePoint).add(coords_2d);
        Vector2 direction = new Vector2(target).sub(firePoint).nor();
        Vector2 rayEnd = new Vector2(firePoint).add(direction.scl(weapon.range));
        
        //create visual shoot line
       //Entity hit_vis = ashleyEngine.createEntity();
       //shooter.add(new BulletComponent(firePoint, rayEnd));
        //ashleyEngine.addEntity(hit_vis);
        BulletComponent bullet_cmp = bullet_mapper.get(shooter);
        
        // RayCastCallback создаётся при пересечении фикстур (они часть body)
        RayCastCallback callback = (Fixture fixture, Vector2 point, Vector2 normal, float fraction) -> {
            // Игнорируем сенсоры и триггеры
            if (fixture.isSensor()) return -1;
            // Получаем сущность из фикстуры
            Entity hitEntity = (Entity) fixture.getBody().getUserData();
            if (hitEntity != null && hitEntity != shooter) {
                // Создаем компонент урона
                Entity damageEvent = ashleyEngine.createEntity();
                damageEvent.add(new DamageComponent(hitEntity, shooter, weapon.damage, DamageType.BULLET));
                //new DamageComponent(hitEntity, shooter, weapon.damage, DamageType.BULLET)
                
                /*damageEvent.add(new DamageComponent(
                        hitEntity, shooter, weapon.damage, DamageType.BULLET
                ));*/
                ashleyEngine.addEntity(damageEvent);
                
                System.out.printf("%s has hited %s\n",stat_m.get(shooter).name,stat_m.get(hitEntity).name);
                //bullet_cmp.set(firePoint, rayEnd); //override that bullet hit entity
                
                // Эффекты: кровь, искры и т.д.
                //createHitEffect(point, normal);
                
                return fraction; // Останавливаем raycast
            } else {
                
            }
            return -1; // Продолжаем raycast
        };
        bullet_cmp.set(firePoint, rayEnd);
        
        box2dWorld.rayCast(callback, firePoint, rayEnd);
        //System.out.println("shoot ended");
        
        // Визуальные эффекты выстрела
        //createMuzzleFlash(firePoint, direction);
    }

    private void launchProjectile(Entity shooter, Vector2 targetPosition, WeaponComponent weapon) {
        
    }

    private void performMeleeAttack(Entity shooter, Vector2 targetPosition, WeaponComponent weapon) {
        
    }
    
}
