/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.mygames.Components.enums.Faction;
import io.github.mygames.entity.Human;
import io.github.mygames.entity.NpcGenericEntity;
import io.github.mygames.systems.CollisionSystem;
import io.github.mygames.systems.DamageBrokerSystem;
import io.github.mygames.systems.MovementSystem;
import io.github.mygames.systems.PhysicsSystem;
import io.github.mygames.systems.ai.NavigationSystem;
import io.github.mygames.systems.RenderSystem;
import io.github.mygames.systems.ShootingSystem;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 *
 * @author Admin
 */
public class Ashley_test implements Screen{
    ZAFW dropGame;
    Engine engine;
    World world;
    //SpriteBatch batch;
    //Texture image;
    ShapeDrawer shaper;

    //MovementSystem mv_sys;
    RenderSystem r_sys;
    NavigationSystem nav_sys;
    PhysicsSystem phys_sys;
    DamageBrokerSystem dmg_sys;
    ShootingSystem shoot_sys;
    
    CollisionSystem col_sys;

    NpcGenericEntity test_actor;
    NpcGenericEntity test_marker;
    NpcGenericEntity test_enemy;
    Vector3 touchPos;
    OrthographicCamera camera;

    public Ashley_test(final ZAFW dropGame) {
        this.dropGame = dropGame;
        world = dropGame.world;
        engine = new Engine();
        //batch = new SpriteBatch();
        //mv_sys = new MovementSystem(world);
        r_sys = new RenderSystem(dropGame.batch, dropGame.shaper);
        nav_sys = new NavigationSystem();
        col_sys = new CollisionSystem();
        phys_sys = new PhysicsSystem(world);
        dmg_sys = new DamageBrokerSystem();
        shoot_sys = new ShootingSystem(world, engine);


        test_actor = new Human(engine,world);
        test_actor.setFaction(Faction.PLAYER);
        test_actor.setTexture(new Texture("models/ally.png"));
        test_actor.setName("ally");
        
        test_enemy = new Human(engine,world);
        test_enemy.setFaction(Faction.ZOMBIE);
        test_enemy.setCoords(200, 200, 200);
        test_enemy.setName("enemy");
        
        //test_actor.setFreeze(true);
        test_marker = new Human(engine,world);
        test_marker.setTexture(new Texture("models/target.png"));
        test_marker.setHidden(true);
        test_marker.setFreeze(true);
        test_marker.toggleCollisions(false);
        test_marker.setName("marker");

        //engine.addSystem(mv_sys);
        engine.addSystem(r_sys);
        engine.addSystem(nav_sys);
        engine.addSystem(col_sys);
        engine.addSystem(phys_sys);
        engine.addSystem(dmg_sys);
        engine.addSystem(shoot_sys);
        System.out.printf("Systems:\n"
        //+ "mov: %d\n"
        + "render: %d\n"
        + "nav: %d\n"
        + "col: %d\n"
        + "phys: %d\n"
        + "dmg: %d\n"
        + "shoot: %d\n"
        ,r_sys.entities.size()
        ,nav_sys.entities.size()
        ,col_sys.getEntities().size()
        ,phys_sys.getEntities().size()
        ,dmg_sys.getEntities().size()
        ,shoot_sys.getEntities().size()
        );
        
        camera = new OrthographicCamera();
        //camera.setToOrtho(false);
        camera.setToOrtho(false, ZAFW.MAIN_WIDH, ZAFW.MAIN_HEIGHT);
        touchPos = new Vector3();
        dropGame.batch.setProjectionMatrix(camera.combined);

        //image = new Texture("target.png");
        //dropGame.batch.disableBlending();
        
    }



    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);
        //dropGame.batch.setProjectionMatrix(camera.combined);
        //test_actor.setVelocity(100, 0);
        if(Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            
            test_actor.setMoveTo(touchPos);
            test_marker.setCoords(touchPos.x, touchPos.y, 0);
            test_marker.setHidden(false);
            //System.out.printf("\n nav from %s to %s", test_actor.getCoords().toString(),test_marker.getCoords().toString());
            if (test_actor.isCollidedWithEntity(test_enemy.getBase_entity())) {
                //System.out.println("COLLIDED");
            }
            
            //test_enemy.makeSimpleShoot(new Vector2(touchPos.x, touchPos.y), 500, 0);
            test_enemy.aimAtPoint(new Vector2(touchPos.x, touchPos.y));
            test_enemy.makeshoot();
            //shoot_sys.shoot(test_enemy.getBase_entity(), new Vector2(touchPos.x, touchPos.y));
            //touchPos.set(Gdx.input.getX(), 0, 0);
        }
        //test_marker.setCoords(100, 0, 0);
	
        engine.update(delta);
        world.step(delta, 0, 0);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    
    

}
