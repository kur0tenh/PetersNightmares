package com.example.kur0.petersnightmares;

import org.andengine.entity.sprite.Sprite;

/**
 * Created by kur0.
 */
public class ThinkHappy extends EscenaBase {
    // barra de tiempo
    private Sprite barraTiempo;

    @Override
    public void crearEscena() {

    }
    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal
        admRecursos.camara.setHUD(null);
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaJuegoThink();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_THINKHAPPY;
    }

    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena misma se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}
