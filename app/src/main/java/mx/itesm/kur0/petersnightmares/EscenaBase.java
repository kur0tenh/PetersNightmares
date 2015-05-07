package mx.itesm.kur0.petersnightmares;

import org.andengine.entity.scene.Scene;

/**
 * Escena de la que heredan las demás escenas
 *
 * @author kur0
 */
public abstract class EscenaBase extends Scene
{
    // Variable protegida para que se pueda acceder desde las subclases
    protected AdministradorRecursos admRecursos;
    protected AdministradorEscenas admEscenas;

    public EscenaBase() {
        admRecursos = AdministradorRecursos.getInstance();
        admEscenas = AdministradorEscenas.getInstance();
        // Llama al método que crea la escena
        crearEscena();  // Este método debe implementarse en la subclase
    }

    // Métodos abstractos
    public abstract void crearEscena(); // Arma la escena
    public abstract void onBackKeyPressed(); // Atiende el botón de back
    public abstract TipoEscena getTipoEscena(); // Regresa el tipo de escena
    public abstract void liberarEscena();   // Libera los recursos asignados
    //public abstract void onFinishedLevel();
}

