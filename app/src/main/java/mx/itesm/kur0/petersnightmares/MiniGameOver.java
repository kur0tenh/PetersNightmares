package mx.itesm.kur0.petersnightmares;

import android.graphics.Color;
import android.graphics.Typeface;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.util.GLState;

import java.util.ArrayList;


/**
 * Created by kur0
 */
public class MiniGameOver extends EscenaBase {
    public static String lastLevel;
    private Sprite spriteFondo;
    private AnimatedSprite vidas;
    private Text t;
    private Font fuente;


    @Override
    public void crearEscena() {
        spriteFondo = new Sprite(0,0, admRecursos.regionMiniGameOver,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                
            }

        };

        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA / 2, (ControlJuego.ALTO_CAMARA / 2));
        SpriteBackground fondo = new SpriteBackground(0,0,0,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);
        cargarFuente();
        cargarTexto();
    }


    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal
        admRecursos.camara.setHUD(null);
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaMiniGameOver();
        ControlJuego.score = 0;
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


    private void cargarFuente(){
        fuente = FontFactory.create(admRecursos.engine.getFontManager(), admRecursos.engine.getTextureManager(), 1024, 1024, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 100, Color.WHITE );
        fuente.load();
    }

    private void cargarTexto(){

        t = new Text(625,450,fuente,"Score: " + ControlJuego.score, 100, admRecursos.vbom);
        attachChild(t);
    }
}
