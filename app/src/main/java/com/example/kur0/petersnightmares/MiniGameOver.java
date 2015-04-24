package com.example.kur0.petersnightmares;

import java.util.ArrayList;

/**
 * Created by kur0
 */
public class MiniGameOver extends EscenaBase {
    @Override
    public void crearEscena() {

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
