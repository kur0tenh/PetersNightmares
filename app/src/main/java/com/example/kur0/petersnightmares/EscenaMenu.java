package com.example.kur0.petersnightmares;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Esto es el menu principal
 * @author kur0
 */
public class EscenaMenu extends EscenaBase
{
    // *** Fondo
    private Sprite spriteFondo; //(el fondo de la escena, estático)

    // *** Botones del menú
    private ButtonSprite btnAcercaDe;
    private ButtonSprite btnJugar;
    private Music musicaFondo;

    // *** Un menú de tipo SceneMenu
    private MenuScene menu;     // Reemplaza a los botones individuales
    private final int OPCION_ACERCA_DE = 0;
    private final int OPCION_JUGAR = 1;
    private final int OPCION_OPCIONES = 2;



    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, admRecursos.regionFondoMenu,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(0,0,0,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);

        // *** Otra forma de mostrar opciones de menú
        agregarMenu();
        cargarSonidos();
    }

    //Musica
    private void cargarSonidos() {
        try {
            musicaFondo = MusicFactory.createMusicFromAsset(admRecursos.engine.getMusicManager(),
                    admRecursos.actividadJuego, "sonido/demo.ogg");
        }
        catch (IOException e) {
            Log.i("cargarSonidos","No se puede cargar la musica");
        }
        // Reproducir
        musicaFondo.setLooping(true);
        musicaFondo.play();

    }

    private void agregarMenu() {
        // Crea el objeto que representa el menú
        menu = new MenuScene(admRecursos.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        // Crea las opciones (por ahora, acerca de y jugar)
        final IMenuItem opcionAcercaDe = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_ACERCA_DE,
                admRecursos.regionBotonAcercaDe,admRecursos.vbom), 1.5f, 1);
        final IMenuItem opcionJugar = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_JUGAR,
                admRecursos.regionBotonJugar,admRecursos.vbom),1.5f,1);
        // Otra opción, jugar Dos
        final IMenuItem opcionJugarDos = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_OPCIONES,
                admRecursos.regionBotonOpciones,admRecursos.vbom),1.5f,1);
        // Otra opción, escena con física


        // Agrega las opciones al menú
        menu.addMenuItem(opcionAcercaDe);
        menu.addMenuItem(opcionJugar);
        menu.addMenuItem(opcionJugarDos);

        // Termina la configuración
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);
        // Ubicar las opciones DENTRO del menú. El centro del menú es (0,0)
        opcionAcercaDe.setPosition(520, -230);
        opcionJugar.setPosition(300, -120);
        opcionJugarDos.setPosition(80,-230);

        // Registra el Listener para atender las opciones
        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opcion oprimida
                switch(pMenuItem.getID()) {
                    case OPCION_ACERCA_DE:
                        // Mostrar la escena de AcercaDe
                        admEscenas.crearEscenaAcercaDe();
                        admEscenas.setEscena(TipoEscena.ESCENA_ACERCA_DE);
                        admEscenas.liberarEscenaMenu();
                        break;
                    case OPCION_OPCIONES:
                        // Mostrar la escena de opciones
                        admEscenas.crearEscenaOpciones();
                        admEscenas.setEscena(TipoEscena.ESCENA_OPCIONES);
                        admEscenas.liberarEscenaMenu();
                        break;
                    case OPCION_JUGAR:
                        ArrayList<TipoEscena> foo = new ArrayList<TipoEscena>();
                        //TipoEscena[] foo = [TipoEscena.ESCENA_RUNIFUNREAL,TipoEscena.ESCENA_THINKHAPPY,TipoEscena.ESCENA_DODGERESPONSIBILITY];
                        foo.add(TipoEscena.ESCENA_RUNIFUNREAL);
                        foo.add(TipoEscena.ESCENA_THINKHAPPY);
                        foo.add(TipoEscena.ESCENA_DODGERESPONSIBILITY);
                        int var = ((int)(Math.random()*3))*1;
                        //Log.d("EscenaMenu,"")
                        TipoEscena m = foo.get(var);

                        if(var == 0){
                            admEscenas.crearEscenaJuego();
                        }else{
                            if(var == 1){
                                admEscenas.crearEscenaJuegoThink();
                            }
                            else{
                                if(var==2){
                                    admEscenas.crearEscenaDodge();
                                }
                            }
                        }
                        admEscenas.liberarEscenaMenu();
                        admEscenas.setEscena(m);
                        break;
                    default:
                        return false;
                        // Mostrar la pantalla de juego
                        /*
                        admEscenas.crearEscenaJuego();
                        admEscenas.setEscena(TipoEscena.ESCENA_RUNIFUNREAL);
                        admEscenas.liberarEscenaMenu();
                        break;
                    default:
                        return false;
                        ---------------------------------------------------
                        admEscenas.crearEscenaJuegoThink();
                        admEscenas.setEscena(TipoEscena.ESCENA_THINKHAPPY);
                        admEscenas.liberarEscenaMenu();
                        break;
                    default:
                        return false;
                    */
                }
                return true;
            }
        });

        // Asigna este menú a la escena
        setChildScene(menu);
    }

    @Override
    public void onBackKeyPressed() {
        // Salir del juego
        musicaFondo.stop();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_MENU;
    }

    @Override
    public void liberarEscena() {
        musicaFondo.release();
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }

    public Music getMusicaFondo() {
        return musicaFondo;
    }
}

