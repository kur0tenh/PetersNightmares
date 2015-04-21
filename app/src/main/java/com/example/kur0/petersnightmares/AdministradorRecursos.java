package com.example.kur0.petersnightmares;

import android.util.Log;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import java.io.IOException;

/**
 * Carga/Descarga los recurso del juego. Imágenes, Audios
 * @author kur0
 */
public class AdministradorRecursos
{
    // Instancia única de la clase
    private static final AdministradorRecursos INSTANCE = new AdministradorRecursos();

    public Engine engine;
    public ControlJuego actividadJuego;
    public Camera camara;
    public VertexBufferObjectManager vbom;

    //==================================TEXTURAS==========================
    //---------------------------------Escena Splash--------------------------
    private ITexture texturaSplash;
    public ITextureRegion regionSplash;

    //-------------------------Escena Menú-----------------------------------
    // Imagen de fondo del menú
    private ITexture texturaFondoMenu;
    public ITextureRegion regionFondoMenu;
    // Botón jugar del menú (con dos estados, normal y presionado)
    public ITiledTextureRegion regionBtnOpciones;
    private BuildableBitmapTextureAtlas btaBtnOpciones;
    // Botón jugar del menú (para el menu de tipo MenuScene)
    private ITexture texturaBotonJugar;
    public ITextureRegion regionBotonJugar;
    // Botón acerca de del menú (para el menu de tipo MenuScene)
    private ITexture texturaBotonAcercaDe;
    public ITextureRegion regionBotonAcercaDe;
    // Botón Opciones
    private ITexture texturaBotonOpciones;
    public ITextureRegion regionBotonOpciones;

    //----------------------------Escena Acerca de-----------------------------
    private ITexture texturaFondoAcercaDe;
    public ITextureRegion regionFondoAcercaDe;
    // Botón de regreso
    private BitmapTextureAtlas texturaBtnRegresar;
    public ITextureRegion regionBtnRegresar;

    //-------------------------Universal para la barra de tiempo----------------------------
    private AssetBitmapTexture texturaBarraTiempo;
    public TextureRegion regionBarraTiempo;
    //--------------------------------------------------------------------------------------

    //-------------------------Escena juego Run if it's unreal-----------------------------
    // Fondo
    private ITexture texturaFondoLejos;
    public ITextureRegion regionFondoLejos;
    private ITexture texturaFondoMedioMedio;
    public ITextureRegion regionFondoMedioMedio;
    private ITexture texturaFondoMedio;
    public ITextureRegion regionFondoMedio;
    private ITexture texturaFondoCerca;
    public ITextureRegion regionFondoCerca;
    // Enemigo animado sobre la pantalla
    private BuildableBitmapTextureAtlas texturaEnemigoAnimado;
    public TiledTextureRegion regionEnemigoAnimado;
    // Peter animado sobre la pantalla
    private BuildableBitmapTextureAtlas texturaPeterRunningAnimado;
    public TiledTextureRegion regionPeterRunningAnimado;
    // Disparo del enemigo
    private BuildableBitmapTextureAtlas texturaDisparoEnemigo;
    public TiledTextureRegion regionDisparoEnemigo;

    //----------------------Escena Juego Think Happy Thoughts------------


    //----------------------Escena Juego Dodge your responsibility-------


    //----------------------Escena Opciones-------------------------------

    private ITexture texturaFondoOpciones;
    public ITextureRegion regionOpciones;
    // Botón de regreso
    private BitmapTextureAtlas texturaBtnRegresarOpciones;
    public ITextureRegion regionBtnRegresarOpciones;



    // Método accesor de la instancia
    public static AdministradorRecursos getInstance() {
        return INSTANCE;
    }

    // Asigna valores iniciales del administrador
    public static void inicializarAdministrador(Engine engine,ControlJuego control, Camera camara, VertexBufferObjectManager vbom) {

        getInstance().engine = engine;
        getInstance().actividadJuego = control;
        getInstance().camara = camara;
        getInstance().vbom = vbom;
    }

    //*** Recursos de la pantalla de Splash
    public void cargarRecursosSplash() {
        try {
            // Carga la imagen de fondo de la pantalla Splash
            texturaSplash = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "splash/logoTec.png");
            regionSplash = TextureRegionFactory.extractFromTexture(texturaSplash);
            texturaSplash.load();
        } catch (IOException e) {
            Log.d("cargarRecursosSplash", "No se puede cargar el fondo");
        }
    }

    public void liberarRecursosSplash() {
        texturaSplash.unload();
        regionSplash = null;
    }

    //*** Recursos de la pantalla de Menú
    public void cargarRecursosMenu() {
        try {
            // Carga la imagen de fondo de la pantalla del Menú
            texturaFondoMenu = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "menu/FondoMenu.jpg");
            regionFondoMenu = TextureRegionFactory.extractFromTexture(texturaFondoMenu);
            texturaFondoMenu.load();
            // Carga la imagen para el botón jugar (2 estados)
            btaBtnOpciones = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),256, 128);
            regionBtnOpciones = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnOpciones, actividadJuego.getAssets(),"menu/BOpciones.png", 2, 1);
            try {
                btaBtnOpciones.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));

            } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
                Log.d("cargarRecursosMenu", "No se puede cargar la imagen del botón jugar");
            }
            btaBtnOpciones.load();
            // Fin de carga imagen botón opciones
        } catch (IOException e) {
            Log.d("cargarRecursosMenu", "No se puede cargar el fondo");
        }
        // Cargar las imágenes de los botones
        cargarBotonesMenu();
    }

    // Carga las imágenes de los botones del menú principal
    private void cargarBotonesMenu() {
        try {
            // Carga la imagen del botón acercaDe
            texturaBotonAcercaDe = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "menu/Babout.png");
            regionBotonAcercaDe = TextureRegionFactory.extractFromTexture(texturaBotonAcercaDe);
            texturaBotonAcercaDe.load();

            // Carga la imagen para el botón jugar
            texturaBotonJugar = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "menu/Bjuego1.png");
            regionBotonJugar = TextureRegionFactory.extractFromTexture(texturaBotonJugar);
            texturaBotonJugar.load();

            // Carga la imagen para el botón de opciones
            texturaBotonOpciones = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "menu/BOpciones.png");
            regionBotonOpciones = TextureRegionFactory.extractFromTexture(texturaBotonOpciones);
            texturaBotonOpciones.load();

        } catch (IOException e) {
            Log.d("cargarBotonesMenu", "No se pueden cargar las imágenes");
        }
    }

    public void liberarRecursosMenu() {
        // Fondo
        texturaFondoMenu.unload();
        regionFondoMenu = null;
        // botón jugar
        btaBtnOpciones.unload();
        regionBtnOpciones = null;
        // Botones del menú
        texturaBotonAcercaDe.unload();
        texturaBotonJugar.unload();
        texturaBotonOpciones.unload();
        regionBotonAcercaDe=null;
        regionBotonJugar=null;
        regionBotonOpciones=null;
    }

    //*** Recursos de la pantalla de Aerca De
    public void cargarRecursosAcercaDe() {
        try {
            // Carga la imagen de fondo de la pantalla Acerca De
            texturaFondoAcercaDe = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "acercade/PeterAbout.jpg");
            regionFondoAcercaDe = TextureRegionFactory.extractFromTexture(texturaFondoAcercaDe);
            texturaFondoAcercaDe.load();
            // Carga la imagen del botón regresar
            texturaBtnRegresar = new BitmapTextureAtlas(actividadJuego.getTextureManager(),260, 260, TextureOptions.BILINEAR);
            regionBtnRegresar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texturaBtnRegresar,actividadJuego, "acercade/regresar.png", 0, 0);
            texturaBtnRegresar.load();

        } catch (IOException e) {
            Log.d("cargarRecursosAcercaDe", "No se pueden cargar las imágenes");
        }
    }

    public void liberarRecursosAcercaDe() {
        // Fondo
        texturaFondoAcercaDe.unload();
        regionFondoAcercaDe = null;
        // Btn regresar
        texturaBtnRegresar.unload();
        regionBtnRegresar = null;

    }
    //*** Recursos de la pantalla juego
    public void cargarRecursosJuego() {
        try {
            // *** Fondos para la animación en capas
            texturaFondoLejos = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "RunIfUnreal/capa0.png");
            regionFondoLejos = TextureRegionFactory.extractFromTexture(texturaFondoLejos);
            texturaFondoLejos.load();

            texturaFondoMedioMedio = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "RunIfUnreal/capa1.png");
            regionFondoMedioMedio = TextureRegionFactory.extractFromTexture(texturaFondoMedioMedio);
            texturaFondoMedioMedio.load();

            texturaFondoMedio = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "RunIfUnreal/capa2.png");
            regionFondoMedio = TextureRegionFactory.extractFromTexture(texturaFondoMedio);
            texturaFondoMedio.load();

            texturaFondoCerca = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "RunIfUnreal/capa3.png");
            regionFondoCerca = TextureRegionFactory.extractFromTexture(texturaFondoCerca);
            texturaFondoCerca.load();
        } catch (IOException e) {
            Log.d("cargarRecursosAcercaDe", "No se pueden cargar las imágenes");
        }
        // Carga las imágenes para el Enemigo Animado
        texturaEnemigoAnimado = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),1700,720);
        regionEnemigoAnimado = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texturaEnemigoAnimado, actividadJuego, "RunIfUnreal/EnemigoAnimado.png", 5, 2);
        try {
            texturaEnemigoAnimado.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite del Enemigo Animado");
        }
        texturaEnemigoAnimado.load();
        // Carga las imagenes para Peter corriendo Animado
        texturaPeterRunningAnimado = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),480,360);
        regionPeterRunningAnimado = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texturaPeterRunningAnimado, actividadJuego, "RunIfUnreal/PeterRunning.png", 4, 2);
        try {
            texturaPeterRunningAnimado.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite de Peter Corriendo Animado");
        }
        texturaPeterRunningAnimado.load();
        // Carga el disparo del enemigo
        texturaDisparoEnemigo = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),270,90);
        regionDisparoEnemigo = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texturaDisparoEnemigo, actividadJuego, "RunIfUnreal/DisparoEnemigo.png", 3, 2);        // Capas);
        try {
            texturaDisparoEnemigo.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite del disparo del enemigo");
        }
        texturaDisparoEnemigo.load();

        // Carga la barra de tiempo
        try{
            texturaBarraTiempo = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "RunIfUnreal/BarraTiempo.png");
            regionBarraTiempo = TextureRegionFactory.extractFromTexture(texturaBarraTiempo);
            texturaBarraTiempo.load();
        }
        catch (IOException e){
            Log.d("cargarRecursosAcercaDe", "No se puede cargar la barra de tiempo");
        }
    }
    public void liberarRecursosJuego() {
        texturaFondoLejos.unload();
        regionFondoLejos = null;
        texturaFondoMedio.unload();
        regionFondoMedio = null;
        texturaFondoMedioMedio.unload();
        regionFondoMedioMedio = null;
        texturaFondoCerca.unload();
        regionFondoCerca = null;
        // Enemigo
        texturaEnemigoAnimado.unload();
        regionEnemigoAnimado = null;
        // Peter
        texturaPeterRunningAnimado.unload();
        regionPeterRunningAnimado = null;
        //disparo enemigo
        texturaDisparoEnemigo.unload();
        regionDisparoEnemigo = null;
    }
    public void cargarRecursosOpciones() {
        try {
            // Carga la imagen de fondo de la pantalla de opciones
            texturaFondoOpciones = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(), "opciones/FondoOpciones.jpg");
            regionOpciones = TextureRegionFactory.extractFromTexture(texturaFondoOpciones);
            texturaFondoOpciones.load();
            texturaBtnRegresarOpciones = new BitmapTextureAtlas(actividadJuego.getTextureManager(),260, 260, TextureOptions.BILINEAR);
            regionBtnRegresarOpciones = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texturaBtnRegresarOpciones,actividadJuego, "opciones/regresar.png", 0, 0);
            texturaBtnRegresarOpciones.load();
        } catch (IOException e) {
            Log.d("cargarRecursosOpciones", "No se pueden cargar las imágenes");
        }
    }
    public void liberarRecursosOpciones() {
        // Fondo
        texturaFondoOpciones.unload();
        regionOpciones = null;
        // Btn regresar
        texturaBtnRegresarOpciones.unload();
        regionBtnRegresarOpciones = null;
    }
}