package com.example.kur0.petersnightmares;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import java.util.ArrayList;

/**
 * Created by kur0
 */
public class MiniGameOver extends EscenaBase {
    private Sprite spriteFondo;
    private AnimatedSprite vidas;

    @Override
    public void crearEscena() {
        spriteFondo = new Sprite(0, 0, admRecursos.regionMiniGameOver, admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
    }
    @Override
    public void onBackKeyPressed() {

    }
    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
    @Override
    public TipoEscena getTipoEscena(){
        return TipoEscena.ESCENA_MINIGAMEOVER;
    }

}
