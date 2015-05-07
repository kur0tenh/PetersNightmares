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
    // barra de tiempo
    private Sprite barraTiempo;
    private Sprite spriteFondo;
    //private AnimatedSprite vidas;
    private Text t;
    private Text V;
    private Text P;
    private Font fuente;


    @Override
    public void crearEscena() {
        manejadorDeTiempo();
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
    private int dx = 15;
    private void manejadorDeTiempo(){
        barraTiempo = new Sprite(ControlJuego.ANCHO_CAMARA/2,(ControlJuego.ALTO_CAMARA)+20,admRecursos.regionBarraTiempoMini,admRecursos.vbom) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                // Esta barra no tiene animación

                // Mueve las coordenadas restando dx
                float px = barraTiempo.getX()-dx;
                barraTiempo.setPosition(px,this.getY());
                // Prueba los límites de la pantalla
                if (px<=-ControlJuego.ANCHO_CAMARA/2&&ControlJuego.vidas>0) {

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
                    admEscenas.liberarEscenaMiniGameOver();
                    admEscenas.setEscena(m);

                }else{
                    if(ControlJuego.vidas<=0){
                        P.setText("Press back");
                    }
                }

            }
        };
        attachChild(barraTiempo);
    }


    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal

        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaMiniGameOver();
        ControlJuego.score = 0;
        ControlJuego.vidas = 3;
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

        t = new Text(625,450,fuente," " + ControlJuego.score, 100, admRecursos.vbom);
        V = new Text(625,300,fuente,"Lifes: " + ControlJuego.vidas, 100, admRecursos.vbom);
        P = new Text(625,200,fuente," ", 100, admRecursos.vbom);
        attachChild(t);
        attachChild(V);
        attachChild(P);
    }
}
