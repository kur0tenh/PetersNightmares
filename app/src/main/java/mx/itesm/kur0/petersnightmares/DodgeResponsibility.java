package mx.itesm.kur0.petersnightmares;


import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;

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
    //fondo
    private Sprite spriteFondo;

    private SensorManager admSensores; // Administra TODOS los sensores del dispositivo
    private Sensor sensorAceleracion;      // El sensor específico de gravedad


    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
    @Override
    public void crearEscena() {
        spriteFondo = new Sprite(0,0, admRecursos.regionFondoDodge,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();

            }
        };

        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA / 2, (ControlJuego.ALTO_CAMARA / 2) - 40);
        SpriteBackground fondo = new SpriteBackground(0,0,0,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);
        manejadorDeTiempo();
        crearPeter();
        agregarPuntero();
        inicializarSensor();
    }
    // Constante para mover la barra de tiempo
    private int dx = 2;
    // Este metodo controla la barra de tiempo
    private void manejadorDeTiempo(){
        barraTiempo = new Sprite(ControlJuego.ANCHO_CAMARA/2,(ControlJuego.ALTO_CAMARA)-20,admRecursos.regionBarraTiempoDodge,admRecursos.vbom) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                // Esta barra no tiene animación

                // Mueve las coordenadas restando dx
                float px = barraTiempo.getX()-dx;
                barraTiempo.setPosition(px,this.getY());
                // Prueba los límites de la pantalla
                if (px<=-ControlJuego.ANCHO_CAMARA/2) {

                    ControlJuego.score+= (int)Math.abs(Math.random()*100);
                    onFinishedLevel();
                }

            }
        };

        attachChild(barraTiempo);
    }
    private void inicializarSensor() {
        admSensores = (SensorManager)admRecursos.actividadJuego.getSystemService(Context.SENSOR_SERVICE);
        sensorAceleracion = admSensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorAceleracion!=null) {
            admSensores.registerListener(this,sensorAceleracion,SensorManager.SENSOR_DELAY_GAME);
        } else {
            Log.i("SENSOR", "No hay sensor en tu dispositivo");
        }
    }
    // movimiento del puntero
    int dxPuntero = 15;
    //Pone el puntero
    private void agregarPuntero() {

        TiledTextureRegion regionPuntero = admRecursos.regionImagenPuntero;
        puntero = new AnimatedSprite((float)Math.random()*1280, 500, regionPuntero, admRecursos.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }

            @Override

            protected void onManagedUpdate(float pSecondsElapsed) {

                super.onManagedUpdate(pSecondsElapsed);
                // Mueve con dx
                float px =puntero.getX()+dxPuntero;
                puntero.setPosition(px, this.getY());
                time = time+pSecondsElapsed;
                float n = (float) (Math.random());
                if (time>=(1+n)){
                    time = 0;
                    agregarLetra();
                }
                if (px>=1280|| px<=0) {

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
        //selecciona una letra de manera aleatoria
        int h = ((int)(Math.random()*26));
        regionLetra.setCurrentTileIndex(h);

        letra = new Sprite(puntero.getX(),680,regionLetra,admRecursos.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                // Mueve con dy
                float py =letra.getY()-dyLetra;

                letra.setPosition(this.getX(), py);
                if (letra.getY() < 0) {
                    letra.detachSelf();
                    ControlJuego.score+= (int)Math.abs(Math.random() * 10);
                }else {
                    if(letra.collidesWith(peter)){
                        ControlJuego.vidas--;
                        letra.detachSelf();
                        onFinishedLevel();
                    }
                }
            }
        };
        long frames[] = new long[26];
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


    @Override
    public void onBackKeyPressed() {

        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaDodge();
        ControlJuego.score = 0;
        ControlJuego.vidas = 3;
    }
    public void onFinishedLevel(){

        admEscenas.crearEscenaMiniGameOver();

        admEscenas.setEscena(TipoEscena.ESCENA_MINIGAMEOVER);
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
        // cada elemento es la aceleracion medida en x,y,z
        //float pulgadas = (metrics.widthPixels)/(metrics.densityDpi);
        float nuevaX = peter.getX() + event.values[1] * 5;
        float nuevaY = peter.getX() - event.values[0] * 5;
        if (nuevaY < ControlJuego.ANCHO_CAMARA && nuevaY >= 0 && isTablet(admRecursos.actividadJuego)==true) {
            peter.setX(nuevaY);

        } else if (nuevaX < ControlJuego.ANCHO_CAMARA && nuevaX >= 0 && isTablet(admRecursos.actividadJuego) == false)  {
            peter.setX(nuevaX);
        }
    }

}
