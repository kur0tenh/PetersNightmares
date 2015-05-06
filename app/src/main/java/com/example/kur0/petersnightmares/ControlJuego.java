package com.example.kur0.petersnightmares;


import android.graphics.Point;
import android.view.KeyEvent;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSCounter;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

/**
 *Clase que controla el juego completo
 * @author kur0
 */
public class ControlJuego extends SimpleBaseGameActivity
{
    // Dimensiones de la cámara

    public static final int ANCHO_CAMARA = 1280;
    public static final int ALTO_CAMARA = 720;
    public static int vidas = 3;
    // La cámara
    private Camera camara;
    // El administrador de escenas
    private AdministradorEscenas admEscenas;

    @Override
    public EngineOptions onCreateEngineOptions() {


        camara = new Camera(0,0,ANCHO_CAMARA,ALTO_CAMARA);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, /*new RatioResolutionPolicy(ANCHO_CAMARA,ALTO_CAMARA),camara)*/ new FillResolutionPolicy(), camara);
    }

    @Override
    protected void onCreateResources() throws IOException {
        // Pasamos toda la información al administrador de recursos
        AdministradorRecursos.inicializarAdministrador(mEngine,this,
                camara,getVertexBufferObjectManager());
        admEscenas = AdministradorEscenas.getInstance();
    }

    @Override
    protected Scene onCreateScene() {
        // FPS
        mEngine.registerUpdateHandler(new FPSCounter());
        // Crea la primer escena que se quiere mostrar
        admEscenas.crearEscenaSplash();
        admEscenas.setEscena(TipoEscena.ESCENA_SPLASH);

        // Programa la carga de la segunda escena, después de cierto tiempo
        mEngine.registerUpdateHandler(new TimerHandler(2,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        mEngine.unregisterUpdateHandler(pTimerHandler); // Invalida el timer
                        // Cambia a la escena del MENU
                        //** 1. Crea la escena del menú
                        //** 2. Pone la escena del menú
                        //** 3. LIBERA la escena de Splash
                        admEscenas.crearEscenaMenu();
                        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
                        admEscenas.liberarEscenaSplash();
                    }
                }));

        return admEscenas.getEscenaActual();
    }

    // Atiende la tecla de BACK
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_MENU) {
                // Si está en el menú, termina
                finish();
            } else {
                // La escena que esté en pantalla maneja el evento
                admEscenas.getEscenaActual().onBackKeyPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // La aplicación sale de memoria
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (admEscenas!=null) {
            System.exit(0);
        }
    }
}
