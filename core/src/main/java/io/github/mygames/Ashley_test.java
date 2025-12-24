/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.mygames.Components.FactionComponent.FactionEnum;
import io.github.mygames.Components.TaskComponent;
import io.github.mygames.Components.WeaponComponent.WeaponType;
import io.github.mygames.entity.Controller;
import io.github.mygames.entity.Human;
import io.github.mygames.entity.ModelLoader;
import io.github.mygames.entity.NpcFactory;
import io.github.mygames.entity.NpcGenericEntity;
import io.github.mygames.systems.CollisionSystem;
import io.github.mygames.systems.DamageBrokerSystem;
import io.github.mygames.systems.MovementSystem;
import io.github.mygames.systems.PhysicsSystem;
import io.github.mygames.systems.ai.NavigationSystem;
import io.github.mygames.systems.RenderSystem;
import io.github.mygames.systems.ShootingSystem;
import io.github.mygames.systems.ai.SteeringSystem;
import java.util.ArrayList;
import java.util.Random;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 *
 * @author Admin
 */
public class Ashley_test implements Screen{
    ZAFW dropGame;
    Engine engine;
    World world;
    Controller player_ctrl;
    //SpriteBatch batch;
    //Texture image;
    ShapeDrawer shaper;

    MovementSystem mv_sys;
    RenderSystem r_sys;
    NavigationSystem nav_sys;
    PhysicsSystem phys_sys;
    DamageBrokerSystem dmg_sys;
    ShootingSystem shoot_sys;
    CollisionSystem col_sys;
    SteeringSystem ai_sys;

    Human test_actor;
    NpcGenericEntity test_marker;
    NpcGenericEntity test_enemy;
    NpcFactory npc_gen;
    
    ArrayList<Human> npcpool = new ArrayList<>();
    ModelLoader mloader = new ModelLoader();
    Random rand = new Random();
    
    Vector3 touchPos;
    OrthographicCamera camera;

    public Ashley_test(final ZAFW dropGame) {
        this.dropGame = dropGame;
        world = dropGame.world;
        engine = dropGame.engine;
        npc_gen = dropGame.npc_generator;
        //batch = new SpriteBatch();
        mv_sys = new MovementSystem(world);
        r_sys = new RenderSystem(dropGame.batch, dropGame.shaper);
        nav_sys = new NavigationSystem();
        col_sys = new CollisionSystem();
        phys_sys = new PhysicsSystem(world);
        dmg_sys = new DamageBrokerSystem();
        shoot_sys = new ShootingSystem(world, engine);
        ai_sys = new SteeringSystem();

        npcpool.add(npc_gen.createNPCFaction(500f, 500f, 0f, FactionEnum.FARMER, mloader.getTextureRegionFromFileName("ally.png")));
        npcpool.add(npc_gen.createNPCFaction(650f, 150f, 0f, FactionEnum.FARMER, mloader.getTextureRegionFromFileName("ally.png")));
        npcpool.add(npc_gen.createNPCFaction(500f, 500f, 0f, FactionEnum.ZOMBIE, mloader.getTextureRegionFromFileName("ally.png")));
        npcpool.add(npc_gen.createNPCFaction(500f, 500f, 0f, FactionEnum.ZOMBIE, mloader.getTextureRegionFromFileName("ally.png")));
        //npcpool.add(npc_gen.createNPCFaction(500f, 500f, 0f, FactionEnum.ZOMBIE, new TextureRegion(new Texture(Gdx.files.internal("models/enemy.png")))));
        
        
        test_actor = new Human(engine,world);
        test_actor.setFaction(FactionEnum.PLAYER);
        test_actor.setTexture(new Texture("models/player.png"));
        test_actor.setName("player");
        test_actor.giveWeapon(WeaponType.ASSAULT_RIFLE);
        
        //test_enemy = new Human(engine,world);
        /*test_enemy = npc_gen.createNPCFaction(500f, 500f, 0f, FactionEnum.BANDIT, new TextureRegion(new Texture(Gdx.files.internal("models/enemy.png"))));
        test_enemy.setFaction(FactionEnum.ZOMBIE);
        test_enemy.setCoords(200, 200, 200);
        test_enemy.setName("enemy");*/
        
        //test_actor.setFreeze(true);
        test_marker = new Human(engine,world);
        test_marker.setTexture(new Texture("models/target.png"));
        test_marker.setHidden(true);
        test_marker.setFreeze(true);
        test_marker.toggleCollisions(false);
        test_marker.setName("marker");

        engine.addSystem(mv_sys);
        engine.addSystem(r_sys);
        engine.addSystem(nav_sys);
        engine.addSystem(col_sys);
        engine.addSystem(phys_sys);
        engine.addSystem(dmg_sys);
        engine.addSystem(shoot_sys);
        engine.addSystem(ai_sys);
        
        camera = new OrthographicCamera();
        //camera.setToOrtho(false);
        camera.setToOrtho(false, ZAFW.MAIN_WIDH, ZAFW.MAIN_HEIGHT);
        touchPos = new Vector3();
        dropGame.batch.setProjectionMatrix(camera.combined);
        player_ctrl = new Controller(test_actor);
        
        //npcpool.get(1).performTask(TaskComponent.TaskEnum.WANDER);
        
        System.out.printf("Systems:\n"
        //+ "mov: %d\n"
        + "render: %d\n"
        + "nav: %d\n"
        + "col: %d\n"
        + "phys: %d\n"
        + "dmg: %d\n"
        + "shoot: %d\n"
        //+ "ai: %d\n"
        ,r_sys.entities.size()
        ,nav_sys.entities.size()
        ,col_sys.getEntities().size()
        ,phys_sys.getEntities().size()
        ,dmg_sys.getEntities().size()
        ,shoot_sys.getEntities().size()
        //,ai_sys.getEntities().size()
        );
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
            
            
            /*test_enemy.setMoveTo(touchPos);
            test_marker.setCoords(touchPos.x, touchPos.y, 0);
            test_marker.setHidden(false);
            //System.out.printf("\n nav from %s to %s", test_actor.getCoords().toString(),test_marker.getCoords().toString());
            if (test_actor.isCollidedWithEntity(test_enemy.getBase_entity())) {
                System.out.println("COLLIDED");
            }*/
            
            //test_enemy.makeSimpleShoot(new Vector2(touchPos.x, touchPos.y), 500, 0);
            test_actor.aimAtPoint(new Vector2(touchPos.x, touchPos.y));
            test_actor.makeshoot(true);
            //System.out.println("ANGLE = " + test_actor.getAngle());
            
            //shoot_sys.shoot(test_enemy.getBase_entity(), new Vector2(touchPos.x, touchPos.y));
            //touchPos.set(Gdx.input.getX(), 0, 0);
            for (Human h: npcpool) {
                if (h.getStats_cmp().is_dead) { continue; }
                //float rf = ;
                h.setMoveTo(new Vector3(touchPos.x+rand.nextFloat(100f), touchPos.y+rand.nextFloat(100f), 0));
                if (h.isCollidedWithEntity(test_actor.getBase_entity())) {
                    System.out.println("COLLIDED");
                    
                }
                //h.performTask(TaskComponent.TaskEnum.WANDER);
                npcpool.get(1).setCoords(touchPos.x,touchPos.y,touchPos.z);
            }
        } else {
            test_actor.makeshoot(false);
        }
        //InputAdapter
        //test_marker.setCoords(100, 0, 0);
        npcpool.get(1).performTask(TaskComponent.TaskEnum.WANDER);
        System.out.println(test_actor.getState_cmp().the_state);
	player_ctrl.update(delta);
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
        npcpool.clear();
    }
    
    

}
