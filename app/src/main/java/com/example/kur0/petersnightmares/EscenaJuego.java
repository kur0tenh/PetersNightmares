package com.example.kur0.petersnightmares;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;


/**
 * Minigame 1
 * @author kur0
 */

public class EscenaJuego extends EscenaBase
{

    // Fondos para la animación de capas
    private Sprite fondoLejos;
    private Sprite fondoMedio;
    private Sprite fondoMedioMedio;
    private Sprite fondoCerca;
    // Barra de tiempo
    private Sprite barraTiempo;
    // Sprite Enemigo
    private AnimatedSprite enemigo;
    // Sprite Peter
    private AnimatedSprite peter;
    // Booleano para saber si peter esta saltando
    private boolean peterSaltando;
    // Sprite disparo enemigo
    private  AnimatedSprite disparoEnemigo;
    // Score
    private  int score = 0;

    @Override
    public void crearEscena() {
        agregarFondoAnimado();
        agregarEnemigo();
        agregarPeterRunning();
        manejadorDeTiempo();
    }
    //modificador de movimiento del enemigo en y
    int dyEnemigo = 7;
    // Agrega a la escena el enemigo animado
    private void agregarEnemigo() {

        TiledTextureRegion regionEnemigo = admRecursos.regionEnemigoAnimado;
        enemigo = new AnimatedSprite(ControlJuego.ANCHO_CAMARA/4,regionEnemigo.getHeight()-100,regionEnemigo,admRecursos.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                double dispara = Math.random()*100;
                // Mueve con dy
                float py = (float) (enemigo.getY()+dyEnemigo);
                enemigo.setPosition(this.getX(),py);
                if (py>=600|| py<=100) {

                    dyEnemigo = -dyEnemigo;
                }
                if(py<=100&&dispara>=50){
                    agregarDisparoEnemigo();
                }
            }
        };
        long tiempos[] = new long[10];
        for(int i=0; i<tiempos.length; i++) {
            tiempos[i] = 40;
        }
        enemigo.animate(tiempos,0,tiempos.length-1,true);
        attachChild(enemigo);
    }
    // modificador de movimiento del disparo del enemigo en x
    int dxDisparoEnemigo = 15;
    private void agregarDisparoEnemigo(){

        TiledTextureRegion regionDisparoEnemigo = admRecursos.regionDisparoEnemigo;
        disparoEnemigo = new AnimatedSprite(enemigo.getX(),regionDisparoEnemigo.getHeight()+125,regionDisparoEnemigo,admRecursos.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }

            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);

                // Mueve con dx
                float px = (float) (disparoEnemigo.getX()+dxDisparoEnemigo);
                disparoEnemigo.setPosition(px,this.getY());
                if(disparoEnemigo.getX()>= 1200){

                    disparoEnemigo.detachSelf();
                    score+=10;
                }
                else{
                    if (disparoEnemigo.collidesWith(peter) ) {

                        disparoEnemigo.detachSelf();
                        onBackKeyPressed();

                    }
                }
            }
        };
        long tiempos[] = new long[6];
        for(int i=0; i<tiempos.length; i++) {
            tiempos[i] = 40;
        }
        disparoEnemigo.animate(tiempos,0,tiempos.length-1,true);
        attachChild(disparoEnemigo);

    }
    int dxNotRun = 5;
    private void agregarPeterRunning() {
        TiledTextureRegion regionPeterRunning = admRecursos.regionPeterRunningAnimado;
        peter = new AnimatedSprite((ControlJuego.ANCHO_CAMARA/4)*3,regionPeterRunning.getHeight()-20,regionPeterRunning,admRecursos.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);

                // Mueve con dx
                float px = (float) (peter.getX()-dxNotRun);
                peter.setPosition(px,this.getY());


                if (peter.collidesWith(enemigo) ) {
                    onBackKeyPressed();
                }
            }
        };
        // Arreglo de tiempos para los frames
        long tiempos[] = new long[16];
        for(int i=0; i<tiempos.length; i++) {
            tiempos[i] = 32;
        }
        peter.animate(tiempos,0,tiempos.length-1,true);
        attachChild(peter);
    }
    // Background ANIMADO con varias CAPAS
    private void agregarFondoAnimado() {
        float xLejos = admRecursos.regionFondoLejos.getWidth()/2;
        float xMedioMedio = admRecursos.regionFondoMedioMedio.getWidth()/2;
        float xMedio = admRecursos.regionFondoMedio.getWidth()/2;
        float xCerca = admRecursos.regionFondoCerca.getWidth()/2;
        float y = ControlJuego.ALTO_CAMARA/2;
        float yCerca = admRecursos.regionFondoCerca.getHeight()/2;
        fondoLejos = new Sprite(xLejos,y,admRecursos.regionFondoLejos,admRecursos.vbom);
        fondoMedioMedio = new Sprite(xMedioMedio,y,admRecursos.regionFondoMedioMedio,admRecursos.vbom);
        fondoMedio = new Sprite(xMedio,y,admRecursos.regionFondoMedio,admRecursos.vbom);
        fondoCerca = new Sprite(xCerca,yCerca,admRecursos.regionFondoCerca,admRecursos.vbom);
        // Se agregan las capas individuales al autoParallax
        AutoParallaxBackground fondo = new AutoParallaxBackground(0,0,0,10);
        fondo.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-8,fondoLejos));
        fondo.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-2,fondoMedioMedio));
        fondo.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-16,fondoMedio));
        fondo.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-24,fondoCerca));
        setBackground(fondo);
        setBackgroundEnabled(true);
    }
    // El ciclo principal de la escena
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
    }
    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
        float x = pSceneTouchEvent.getX();

        if (pSceneTouchEvent.isActionDown() && x>ControlJuego.ANCHO_CAMARA/2 && !peterSaltando) {
            peterSaltando=true;
            // Saltar
            float xa = peter.getX();
            float ya = peter.getY();
            float xn = xa;
            float yn = ya;
            JumpModifier salto = new JumpModifier(1,xa,xn,ya,yn,-300);

            //Frame para salto
            long tiempos[] = new long[16];
            for(int i=0; i<tiempos.length; i++) {
                tiempos[i] = 0;
            }
            peter.animate(tiempos[11],1);
            ParallelEntityModifier paralelo = new ParallelEntityModifier(salto) {
                @Override

                protected void onModifierFinished(IEntity pItem) {


                    long tiempos[] = new long[16];
                    for(int i=0; i<tiempos.length; i++) {
                        tiempos[i] = 32;

                    }
                    peter.animate(tiempos,0,tiempos.length-1,true);
                    super.onModifierFinished(pItem);
                    peterSaltando=false;
                }
            };
            peter.registerEntityModifier(paralelo);
        }else {
            if (pSceneTouchEvent.isActionDown() && x < ControlJuego.ANCHO_CAMARA / 2&& !peterSaltando && peter.getX()<=1100) {
                float xa = peter.getX();
                float ya = peter.getY();
                float xn = xa;
                float yn = ya;
                //JumpModifier corre = new JumpModifier(1,xa,xa+10,ya,ya,-100);
                JumpModifier corre = new JumpModifier(0.06f,xa,xn+50,ya,ya,0);
                ParallelEntityModifier paralelo = new ParallelEntityModifier(corre) {
                    @Override

                    protected void onModifierFinished(IEntity pItem) {

                        super.onModifierFinished(pItem);

                    }
                };
                peter.registerEntityModifier(paralelo);
            }
            else{

            }
        }

        return super.onSceneTouchEvent(pSceneTouchEvent);
    }
    // Constante para mover la barra de tiempo
    private int dx = 1;
    // Este metodo controla la barra de tiempo
    private void manejadorDeTiempo(){
        barraTiempo = new Sprite(ControlJuego.ANCHO_CAMARA/2,(ControlJuego.ALTO_CAMARA)-20,admRecursos.regionBarraTiempo,admRecursos.vbom) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                // Esta barra no tiene animación

                // Mueve las coordenadas restando dx
                float px = barraTiempo.getX()-dx;
                barraTiempo.setPosition(px,this.getY());
                // Prueba los límites de la pantalla
                if (px<=-ControlJuego.ANCHO_CAMARA/2) {
                    //Cambiara a otra escena cuando existan los demas minigames
                    onBackKeyPressed();
                }

            }
        };
        attachChild(barraTiempo);
    }

    // El usuario oprime el botón de BACK; regresar al menú principal
    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal
        admRecursos.camara.setHUD(null);
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaJuego();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_JUEGO;
    }

    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena misma se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}
