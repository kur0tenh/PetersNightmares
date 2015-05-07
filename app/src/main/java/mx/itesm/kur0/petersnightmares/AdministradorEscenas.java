package mx.itesm.kur0.petersnightmares;

import mx.itesm.kur0.petersnightmares.EscenaBase;
import mx.itesm.kur0.petersnightmares.ThinkHappy;
import mx.itesm.kur0.petersnightmares.TipoEscena;

import org.andengine.engine.Engine;

/**
 * Administra la escena que se verá en la pantalla
 */
public class AdministradorEscenas
{
    private TipoEscena lastLevel;
    // Instancia única
    private static final AdministradorEscenas INSTANCE =
            new AdministradorEscenas();

    // Declara las distintas escenas que forman elm juego
    private EscenaBase escenaSplash;
    private EscenaBase escenaMenu;
    private EscenaBase escenaAcercaDe;
    private EscenaBase escenaJuego;
    private EscenaBase escenaJuegoThink;
    private EscenaBase escenaJuegoDodge;
    private EscenaBase escenaOpciones;
    private EscenaBase escenaMiniGameOver;



    // El tipo de escena que se está mostrando
    private TipoEscena tipoEscenaActual = TipoEscena.ESCENA_SPLASH;
    // La escena que se está mostrando
    private EscenaBase escenaActual;
    // El engine para hacer el cambio de escenas
    private Engine engine = AdministradorRecursos.getInstance().engine;
    // El administrados de recursos
    private AdministradorRecursos admRecursos = AdministradorRecursos.getInstance();

    // Regresa la instancia del administrador de escenas
    public static AdministradorEscenas getInstance() {
        return INSTANCE;
    }

    // Regresa el tipo de la escena actual
    public TipoEscena getTipoEscenaActual() {
        return tipoEscenaActual;
    }

    // Regresa la escena actual
    public EscenaBase getEscenaActual() {
        return escenaActual;
    }

    /*
     * Pone en la pantalla la escena que llega como parámetro y guarda el nuevo estado
     */
    private void setEscenaBase(EscenaBase nueva) {
        engine.setScene(nueva);
        escenaActual = nueva;
        tipoEscenaActual = nueva.getTipoEscena();
    }

    /**
     * Cambia a la escena especificada en el parámetro
     *
     *
     */

    public void setEscena(TipoEscena nuevoTipo) {
        switch (nuevoTipo) {
            case ESCENA_SPLASH:
                setEscenaBase(escenaSplash);
                break;
            case ESCENA_MENU:
                setEscenaBase(escenaMenu);
                break;
            case ESCENA_ACERCA_DE:
                setEscenaBase(escenaAcercaDe);
                break;
            case ESCENA_RUNIFUNREAL:
                setEscenaBase(escenaJuego);
                break;
            case ESCENA_THINKHAPPY:
                setEscenaBase(escenaJuegoThink);
                break;
            case ESCENA_DODGERESPONSIBILITY:
                setEscenaBase(escenaJuegoDodge);
                break;
            case ESCENA_OPCIONES:
                setEscenaBase(escenaOpciones);
                break;
            case ESCENA_MINIGAMEOVER:
                setEscenaBase(escenaMiniGameOver);
                break;

        }
    }

    //*** Crea la escena de Splash
    public void crearEscenaSplash() {
        // Carga los recursos
        admRecursos.cargarRecursosSplash();
        escenaSplash = new EscenaSplash();
    }

    //*** Libera la escena de Splash
    public void liberarEscenaSplash() {
        admRecursos.liberarRecursosSplash();
        escenaSplash.liberarEscena();
        escenaSplash = null;
    }

    // ** MENU
    //*** Crea la escena de MENU
    public void crearEscenaMenu() {
        // Carga los recursos
        admRecursos.cargarRecursosMenu();
        escenaMenu = new EscenaMenu();
    }

    //*** Libera la escena de MENU
    public void liberarEscenaMenu() {
        admRecursos.liberarRecursosMenu();
        escenaMenu.liberarEscena();
        escenaMenu = null;
    }

    //*** Crea la escena de Acerca De
    public void crearEscenaAcercaDe() {
        // Carga los recursos
        admRecursos.cargarRecursosAcercaDe();
        escenaAcercaDe = new EscenaAcercaDe();
    }

    //*** Libera la escena de AcercDe
    public void liberarEscenaAcercaDe() {
        admRecursos.liberarRecursosAcercaDe();
        escenaAcercaDe.liberarEscena();
        escenaAcercaDe = null;
    }

    //*** Crea la escena de JUEGO
    public void crearEscenaJuego() {
        // Carga los recursos
        admRecursos.cargarRecursosJuego();
        escenaJuego = new RunIfUnreal();
    }

    //*** Libera la escena de JUEGO
    public void liberarEscenaJuego() {
        admRecursos.liberarRecursosJuego();
        escenaJuego.liberarEscena();
        escenaJuego = null;
    }
    // Crea la escena de JUEGO Think Happy Thoughts
    public void crearEscenaJuegoThink() {
        // Carga los recursos
        admRecursos.cargarRecursosJuegoThink();
        escenaJuegoThink = new ThinkHappy();
    }

    //*** Libera la escena de JUEGO Think Happy Thoughts
    public void liberarEscenaJuegoThink() {
        admRecursos.liberarRecursosJuegoThink();
        escenaJuegoThink.liberarEscena();
        escenaJuegoThink = null;
    }

    // Crea la escena de JUEGO Dodge Responsability
    public void crearEscenaDodge(){
        //Carga los Recursos
        admRecursos.cargarRecursosDodge();
        escenaJuegoDodge = new DodgeResponsibility();
    }

    public void liberarEscenaDodge() {
        admRecursos.liberarRecursosDodge();
        escenaJuegoDodge.liberarEscena();
        escenaJuegoDodge = null;
    }

    //*** Crea la escena de Juego Dos
    public void crearEscenaOpciones() {
        // Carga los recursos
        admRecursos.cargarRecursosOpciones();
        escenaOpciones = new EscenaOpciones();
    }

    //*** Libera la escena de Juego Dos
    public void liberarEscenaOpciones() {
        admRecursos.liberarRecursosOpciones();
        escenaOpciones.liberarEscena();
        escenaOpciones = null;
    }
    public void crearEscenaMiniGameOver(){
        admRecursos.cargarRecursosMiniGameOver();
        escenaMiniGameOver = new MiniGameOver();
    }
    public void liberarEscenaMiniGameOver(){
        admRecursos.liberarRecursosMiniGameOver();
        escenaMiniGameOver.liberarEscena();
        escenaMiniGameOver = null;
    }

}
