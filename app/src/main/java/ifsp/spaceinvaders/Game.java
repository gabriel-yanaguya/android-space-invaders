package ifsp.spaceinvaders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ifsp.spaceinvaders.model.Alien;
import ifsp.spaceinvaders.model.AlienSquad;
import ifsp.spaceinvaders.model.Hero;
import ifsp.spaceinvaders.model.Projectile;
import ifsp.spaceinvaders.model.Screen;

/**
 * Created by Chan on 04/05/2017.
 */

public class Game extends SurfaceView implements Runnable, View.OnTouchListener{

    private boolean running;
    private final SurfaceHolder holder = getHolder();
    private Bitmap background;
    private Screen screen;

    private Hero hero;
    private AlienSquad alienSquad;
    private SensorManager sensorManager;
    private SoundPool soundPool;
    private int SHOT_SOUND;

    private int frames = 0;
    private final int FRAMES_TO_ALIEN_SHOOT = 100;

    private ArrayList<Projectile> projectiles;

    private ArrayList<Projectile> alienProjectiles;

    public Game(final Context context){
        super(context);
        init();

        this.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    Projectile projectile = new Projectile(context, (hero.getXPos() + (hero.getWidth()/2)), hero.getYPos(), false);
                   //if (projectiles.size() <= 1)
                    {
                        projectiles.add(projectile);
                        playSound(SHOT_SOUND);
                    }
                }
            return true;
            }
        });
    }

    private void init(){

        this.screen = new Screen(this.getContext());

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        this.background = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), screen.getHeight(), false);

        this.hero = new Hero(getContext());
        this.alienSquad = new AlienSquad(getContext());

        this.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        SHOT_SOUND = this.soundPool.load(getContext(), R.raw.laser_shot, 1);
        projectiles = new ArrayList<>();
        alienProjectiles = new ArrayList<>();
    }

    private void playSound(int code){
        this.soundPool.play(code, 1, 1, 1, 0, 1);
    }

    @Override
    public void run() {

        while(this.running) {
            if (!this.holder.getSurface().isValid()) continue;

            Canvas canvas = this.holder.lockCanvas();

            //Desenhando os elementos do jogo:
            canvas.drawBitmap(this.background, 0, 0, null);

            hero.draw(canvas);
            hero.move();
            alienSquad.move();
            alienSquad.draw(canvas);

            Projectile projectile;
            Iterator<Projectile> iterator = new ArrayList<Projectile>(projectiles).iterator();
            while (iterator.hasNext()) {
                try {
                    projectile = iterator.next();
                    projectile.draw(canvas);
                    projectile.move();

                    if (projectile.getYPos() < 0) {

                        iterator.remove();
                        projectiles.remove(projectile);
                    } else {
                        //Verifica se atingiu algum alien
                        Alien alien;
                        List<Alien> toRemove = new ArrayList<>();
                        Iterator<Alien> alienIterator = alienSquad.getAliens().iterator();
                        while (alienIterator.hasNext()) {
                            alien = alienIterator.next();
                            if (alien.strike(projectile)) {
                                alienIterator.remove();
                                //alienSquad.getAliens().remove(alien);

                                iterator.remove();
                                projectiles.remove(projectile);
                            }
                        }
                    }
                } catch (ConcurrentModificationException e) {
                    System.out.println(e.getMessage());
                }
            }

            Projectile alienProjectile;

            Iterator<Projectile> alienIterator = new ArrayList<Projectile>(alienProjectiles).iterator();

            while (alienIterator.hasNext()) {
                try {
                    alienProjectile = alienIterator.next();
                    alienProjectile.draw(canvas);
                    alienProjectile.move();

                    if (alienProjectile.getYPos() > screen.getHeight()) {

                        alienIterator.remove();
                        alienProjectiles.remove(alienProjectile);
                    } else {
                        //Verifica se atingiu o Hero

                        if (hero.strike(alienProjectile)) {

                            // PERDEU
                            this.running = false;
                            ((Activity) getContext()).runOnUiThread(new Runnable() {
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("GAME OVER, você foi derrotado")
                                            .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    ((Activity) getContext()).recreate();
                                                    running = true;
                                                }
                                            })
                                            .setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    stop();
                                                    ((Activity) getContext()).finish();
                                                }
                                            });
                                    builder.create().show();
                                }
                            });

                            alienIterator.remove();
                            alienProjectiles.remove(alienProjectile);
                        }
                    }
                } catch (ConcurrentModificationException e) {
                    System.out.println(e.getMessage());
                }
            }

            this.holder.unlockCanvasAndPost(canvas);

            // Verifica vitória
            if (win()) {
                this.running = false;
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Parabéns, você derrotou a frota alienígena !!")
                                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ((Activity) getContext()).recreate();
                                        running = true;
                                    }
                                })
                                .setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        stop();
                                        ((Activity) getContext()).finish();
                                    }
                                });
                        builder.create().show();
                    }
                });
            }


            // Alien atira
            frames++;
            if(frames % FRAMES_TO_ALIEN_SHOOT == 0){
                ArrayList<Alien> bottomLine = alienSquad.getAliensBottomLine();
                Alien shooter = bottomLine.get((new Random()).nextInt(bottomLine.size()));
                Projectile newProjectile = new Projectile(getContext(), shooter.getXPos(), shooter.getYPos(), true);
                alienProjectiles.add(newProjectile);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        return false;
    }

    private boolean win(){
        return this.alienSquad.getAliens().size() <= 0;
    }

    public void stop(){

        this.running = false;
    }

    public void start(){

        this.running = true;
    }

    // Controle do Herói
    public void setHeroVelocity(int xVel){
        this.hero.setXVel(xVel);
    }
}
