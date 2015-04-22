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
 * Created by Anuar
 */
public class DodgeResponsibility extends EscenaBase{

    // barra de tiempo
    private Sprite barraTiempo;


    @Override
    public void crearEscena() {
        manejadorDeTiempo();
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
