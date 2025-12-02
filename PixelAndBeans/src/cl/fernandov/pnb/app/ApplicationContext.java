package cl.fernandov.pnb.app;

import cl.fernandov.pnb.controller.*;
import cl.fernandov.pnb.service.*;
import cl.fernandov.pnb.mock.repository.*;

/**
 * Contenedor de Inversión de Control (IoC) Singleton.
 * Responsable de inicializar y proporcionar todas las dependencias.
 */
public class ApplicationContext {

    private static ApplicationContext instance;

    // Controladores
    private final LoginController loginController;
    private final UsuarioController usuarioController;
    private final ProductoController productoController;
    private final VentaController ventaController;

    // Servicios
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final VentaService ventaService;

    private ApplicationContext() {

        // --- 1. Inicializar Repositorios MOCK (Capas más bajas) ---
        UsuarioRepositoryMock usuarioRepository = new UsuarioRepositoryMock();
        ProductoRepositoryMock productoRepository = new ProductoRepositoryMock();
        VentaRepositoryMock ventaRepository = new VentaRepositoryMock();

        // --- 2. Inicializar Servicios (Inyección de Repositorios) ---
        this.usuarioService = new UsuarioService(usuarioRepository);
        this.productoService = new ProductoService(productoRepository);
        this.ventaService = new VentaService(ventaRepository);

        // --- 3. Inicializar Controladores (Inyección de Servicios) ---
        this.loginController = new LoginController(usuarioService);
        this.usuarioController = new UsuarioController(usuarioService);
        this.productoController = new ProductoController(productoService);
        this.ventaController = new VentaController(ventaService, productoService);
    }

    /**
     * Obtiene la instancia Singleton del ApplicationContext.
     */
    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    // --- Métodos de Acceso (Getters) ---
    public LoginController getLoginController() { return loginController; }
    public UsuarioController getUsuarioController() { return usuarioController; }
    public ProductoController getProductoController() { return productoController; }
    public VentaController getVentaController() { return ventaController; }
}