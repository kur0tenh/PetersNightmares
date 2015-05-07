package mx.itesm.kur0.petersnightmares;



import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import java.util.ArrayList;

/**
 * Created by kur0.
 */
public class ThinkHappy extends EscenaBase {
    // barra de tiempo
    private Sprite barraTiempo;
    // Puzzle
    private Sprite puzzleWall;


    @Override
    public void crearEscena() {


        ordenarPuzzle();
        manejadorDeTiempo();


    }

    // En esta lista se almacenan las diferentes secciones del puzzle
    ArrayList<Sprite> lista;
    //Esta funcion añade y posiciona las secciones del puzzle con su
    // respectivo touch listener
    private void ordenarPuzzle(){
        TiledTextureRegion sc = admRecursos.regionImagenPuzzle;
        lista = new ArrayList<Sprite>();
        for(int i=0; i<=7; i++) {
            sc.setCurrentTileIndex(i);
            float xa;
            float ya;
            if(i>3) {
                // El valor por defecto es 340 pero tiene una tolerancia
                // para que haya una separación sutil entre las secciones
                ya = (float) ((339 - (sc.getHeight()/2)));
                // El valor es 320... se incrementa para tener una separación
                // sutil entre las secciones
                xa = (float)((321*(i-4))+160);
            }else{
                ya = (float) 680 - sc.getHeight() / 2;
                xa = (float)((321*(i))+160);
            }
            Sprite unaSeccion = new Sprite(xa, ya,admRecursos.regionImagenPuzzle, admRecursos.vbom){
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                    if (pSceneTouchEvent.isActionDown()) {
                        this.registerEntityModifier(new RotationModifier(0.05f,this.getRotation(),this.getRotation()+90));

                    }


                    return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                }

            };

            attachChild(unaSeccion); // AGREGA a la ESCENA
            lista.add(unaSeccion);
            unaSeccion.setRotation(90f*((int)(Math.random()*4)));//Asigna la rotacion aleatoriamente
            registerTouchArea(unaSeccion);

        }

    }
    private boolean finalizar(){
        boolean fin = false;
        ArrayList<Sprite> c = new ArrayList<Sprite>();
        for(int i = 0; i<lista.size(); i++){
            if(((lista.get(i).getRotation()%360)==0)){
                c.add(lista.get(i));
            }else{

            }
        }
        if(c.size() == lista.size()){
            ControlJuego.score = (int)Math.abs(Math.random() * 100);
            fin = true;
        }
        return fin;
    }



    // Constante para mover la barra de tiempo
    private int dx = 1;
    // Este metodo controla la barra de tiempo

    private void manejadorDeTiempo(){
        barraTiempo = new Sprite(ControlJuego.ANCHO_CAMARA/2,(ControlJuego.ALTO_CAMARA)-20,admRecursos.regionBarraTiempo,admRecursos.vbom) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {

                super.onManagedUpdate(pSecondsElapsed);
                // Esta barra no tiene animación
                // Mueve las coordenadas restando dx
                float px = barraTiempo.getX()-dx;
                barraTiempo.setPosition(px,this.getY());
                //Aqui reimplementamos una forma de comprobar que todas
                //las secciones esten en el angulo correcto

                // Prueba los límites de la pantalla
                if (px<=-ControlJuego.ANCHO_CAMARA/2||finalizar()) {

                    onFinishedLevel();
                }

            }
        };
        attachChild(barraTiempo);
    }
    public void onFinishedLevel(){

        admEscenas.crearEscenaMiniGameOver();
        admEscenas.setEscena(TipoEscena.ESCENA_MINIGAMEOVER);
        admEscenas.liberarEscenaJuegoThink();


    }
    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal

        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaJuegoThink();
        ControlJuego.score = 0;

    }

    @Override
    public TipoEscena getTipoEscena() {return TipoEscena.ESCENA_THINKHAPPY;}

    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena misma se deconecta del engine
        this.dispose();         // Libera la memoria
    }



    }


