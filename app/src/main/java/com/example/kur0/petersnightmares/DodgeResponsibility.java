package com.example.kur0.petersnightmares;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import java.io.IOException;
import java.util.ArrayList;
/**
 * Created by Anuar
 */
public class DodgeResponsibility extends EscenaBase{

    private float time = 0;
    // barra de tiempo
    private Sprite barraTiempo;
    // sprite puntero enemigo
    private AnimatedSprite puntero;
    // sprite letra
    private Sprite letra;
    // sprite cabeza de peter
    private AnimatedSprite peter;
    private SensorManager admSensores; // Administra TODOS los sensores del dispositivo
    private Sensor sensorGravedad;

    @Override
    public void crearEscena() {
        manejadorDeTiempo();
        createBackground();
        crearPeter();
        agregarPuntero();
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
    // movimiento del puntero
    int dxPuntero = 10;
    //Pone el puntero
    private void agregarPuntero() {

        TiledTextureRegion regionPuntero = admRecursos.regionImagenPuntero;
        puntero = new AnimatedSprite((float)Math.random()*1280, 570, regionPuntero, admRecursos.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
            @Override

            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                // Mueve con dx
                float px = (float)(puntero.getX()+dxPuntero);
                puntero.setPosition(px,this.getY());
                time = time+pSecondsElapsed;
                if (time>1){
                    time = 0;
                    agregarLetra();
                }
                if (px>=1260|| px<=30) {

                    dxPuntero = -dxPuntero;
                }
            }
        };
        long frames[] = new long[6];
        for(int i=0; i<frames.length; i++) {
            frames[i] = 100;
        }
        puntero.animate(frames,0, frames.length-1, true);
        attachChild(puntero);
    }

    // movimiento del proyectil
    int dyLetra = 20;
    //Pone un proyectil
    private void agregarLetra(){
        TiledTextureRegion regionLetra = admRecursos.regionImagenLetras;
        letra = new Sprite(puntero.getX(),720,regionLetra,admRecursos.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                // Mueve con dy
                float py = (float)(letra.getY()-dyLetra);
                letra.setPosition(this.getX(),py);
                if (letra.getY() < 0) {
                    letra.detachSelf();
                }else {
                    if(letra.collidesWith(peter)){
                        letra.detachSelf();
                        //onBackKeyPressed();
                    }
                }
            }
        };
        long frames[] = new long[24];
        for(int i=0; i<frames.length; i++) {
            frames[i] = 100;
        }
        attachChild(letra);
    }

    private void crearPeter() {
        TiledTextureRegion regionPeter = admRecursos.regionImagenCabezaPeter;
        peter = new AnimatedSprite(640, 30, regionPeter, admRecursos.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();

                /*public void onAccelerometerChanged(final AccelerometerData myAccelerometerData) {
                    peter.setPosition(peter.getX() + myAccelerometerData.getX(), mySprite.getY() + myAccelerometerData.getY());

                }*/
            }
        };
        long frames[] = new long[8];
        for(int i=0; i<frames.length; i++) {
            frames[i] = 100;
        }
        peter.animate(frames, 0, frames.length-1, true);
        attachChild(peter);
    }



    // Pone el Backgroung blanco
    private void createBackground()
    {
        setBackground(new Background(Color.WHITE));
    }

    @Override
    public void onBackKeyPressed() {
        admRecursos.camara.setHUD(null);
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaDodge();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_DODGERESPONSIBILITY;
    }

    @Override
    public void liberarEscena() {
        this.detachSelf();
        this.dispose();
    }
}
