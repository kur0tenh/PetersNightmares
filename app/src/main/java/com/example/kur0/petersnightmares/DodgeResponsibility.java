package com.example.kur0.petersnightmares;

/**
 * Created by Anuar
 */
public class DodgeResponsibility extends EscenaBase{


    @Override
    public void crearEscena() {

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
