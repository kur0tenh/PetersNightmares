package mx.itesm.kur0.petersnightmares;

import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import java.io.IOException;

/**
 * Escena inicial
 * @author kur0
 */
public class EscenaSplash extends EscenaBase
{
    private Sprite spriteFondo; //el fondo de color de la escena
    public static Music musicaFondo;
    //Musica
    private void cargarSonidos() {
        try {
            musicaFondo = MusicFactory.createMusicFromAsset(admRecursos.engine.getMusicManager(),
                    admRecursos.actividadJuego, "sonido/Trancer.mp3");
        }
        catch (IOException e) {
            Log.i("cargarSonidos", "No se puede cargar la musica");
        }
        // Reproducir
        musicaFondo.setLooping(true);
        musicaFondo.play();

    }
    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, admRecursos.regionSplash,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        spriteFondo.setScale(1.0f);

        // Crea el color de fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(0,0.1f,1,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);

    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_SPLASH;
    }

    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
        cargarSonidos();
    }
    public Music getMusicaFondo() {
        return musicaFondo;
    }
}
