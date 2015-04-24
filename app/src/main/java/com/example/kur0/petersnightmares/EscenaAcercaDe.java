package com.example.kur0.petersnightmares;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

/**
 * Acerca del juego
 * @author kur0
 */
public class EscenaAcercaDe extends EscenaBase
{
    private Sprite spriteFondo; //(el fondo de la escena, estático)
    private ButtonSprite spriteBtnRegreso;    // El botón de BACK sin imagen de 'presionado'
    // Un fondo para el botón regresar
    private Rectangle fondoBtn;

    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, admRecursos.regionFondoAcercaDe,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };

        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        SpriteBackground fondo = new SpriteBackground(0,0,0,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);


        agregarBtnRegreso();    // Para encapsular el manejo del Botón regresar
    }

    private void agregarBtnRegreso() {
        // Habilita los eventos de touch en ciertas áreas
        setTouchAreaBindingOnActionDownEnabled(true);

        float posicionX = (admRecursos.regionBtnRegresar.getWidth()/2);
        float posicionY = 720-(admRecursos.regionBtnRegresar.getHeight()/2);
        spriteBtnRegreso = new ButtonSprite(posicionX,posicionY,admRecursos.regionBtnRegresar,admRecursos.vbom) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionUp()) {
                    onBackKeyPressed();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        // Registra el área del botón
        registerTouchArea(spriteBtnRegreso);

        // Crea el cuadro detras del botón de regresar
        /*
        fondoBtn = new Rectangle(posicionX,posicionY,128,128,admRecursos.vbom) {
            // Este método se ejecuta cada vez que se actualiza el juego (fps)
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                // El parámetro nos indica el tiempo entre llamadas
                // Se usa para que la velocidad sea independiente del CPU
                //fondoBtn.setRotation(fondoBtn.getRotation()+100*pSecondsElapsed);
                fondoBtn.setWidth( ((int)fondoBtn.getWidth()+3)%128 ) ;
            }
        };

        fondoBtn.setColor(0,0,0,0.4f);  // Negro con 40% de opacidad
        attachChild(fondoBtn);
        */
        // Agrega el botón de regreso después del fondo
        attachChild(spriteBtnRegreso);
    }

    // El usuario oprime el botón de BACK; regresar al menú principal
    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaAcercaDe();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_ACERCA_DE;
    }

    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena misma se deconecta del engine
        this.dispose();         // Libera la memoria
    }

}
