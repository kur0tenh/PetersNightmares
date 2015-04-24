package com.example.kur0.petersnightmares;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
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
public class DodgeResponsibility extends EscenaBase implements SensorEventListener{

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
    private Sensor sensorGravedad;      // El sensor específico de gravedad


    @Override
    public void crearEscena() {
        manejadorDeTiempo();
        createBackground();
        crearPeter();
        agregarPuntero();
        inicializarSensor();
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
    private void inicializarSensor() {
        admSensores = (SensorManager)admRecursos.actividadJuego.getSystemService(Context.SENSOR_SERVICE);
        sensorGravedad = admSensores.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if (sensorGravedad!=null) {
            admSensores.registerListener(this,sensorGravedad,SensorManager.SENSOR_DELAY_GAME);
        } else {
            Log.i("SENSOR", "No hay sensor en tu dispositivo");
        }
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
        long frames[] = new long[16];
        for(int i=0; i<frames.length; i++) {
            frames[i] = 60;
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
                        onBackKeyPressed();
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
        peter = new AnimatedSprite(640, 70, regionPeter, admRecursos.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        long frames[] = new long[6];
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
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        // Leer los valores del sensor y en base a ellos, mover el matamoscas

        // event.values es un arreglo de tipo float con 3 datos
        // cada elemento es la gravedad medida en x,y,z
        float nuevaX = peter.getX() + event.values[1] * 7;
        if (nuevaX < ControlJuego.ANCHO_CAMARA && nuevaX >= 0) {
            peter.setX(nuevaX);
            //peter.setX(nuevaX);
        }
    }
}
