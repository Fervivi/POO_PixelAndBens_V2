package cl.fernandov.pnb.app;

import cl.fernandov.pnb.controller.*;
import cl.fernandov.pnb.service.*;
import cl.fernandov.pnb.mock.repository.*;


public class ApplicationContext {

    private static ApplicationContext instance;


    private final LoginController loginController;
    private final UsuarioController usuarioController;
    private final ProductoController productoController;
    private final VentaController ventaController;


    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final VentaService ventaService;

    private ApplicationContext() {

        UsuarioRepositoryMock usuarioRepository = new UsuarioRepositoryMock();
        ProductoRepositoryMock productoRepository = new ProductoRepositoryMock();
        VentaRepositoryMock ventaRepository = new VentaRepositoryMock();


        this.usuarioService = new UsuarioService(usuarioRepository);
        this.productoService = new ProductoService(productoRepository);
        this.ventaService = new VentaService(ventaRepository);


        this.loginController = new LoginController(usuarioService);
        this.usuarioController = new UsuarioController(usuarioService);
        this.productoController = new ProductoController(productoService);
        this.ventaController = new VentaController(ventaService, productoService);
    }


    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }


    public LoginController getLoginController() { return loginController; }
    public UsuarioController getUsuarioController() { return usuarioController; }
    public ProductoController getProductoController() { return productoController; }
    public VentaController getVentaController() { return ventaController; }
}