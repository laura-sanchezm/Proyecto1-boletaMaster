package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import modelo.*;
import Tiquetes.*;

public class MenuOrganizador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private modelo.Organizador organizador;
	private ArrayList<JPanel> paneles;
	
	private JLayeredPane layeredPane;
	private JPanel panelMenu;
	private JPanel panelCrearEvento;
	private JPanel panelAsignarLocalidad;
	private JPanel panelAplicarOferta;
	private JPanel panelSolicitarCancelacion;
	private JPanel panelConsultarCancelaciones;
	private JPanel panelVerIngresos;
	private JPanel panelPorcentajeVentaTotal;
	private JPanel panelPorcentajeVentaEvento;
	private JPanel panelPorcentajeVentaLocalidad;
	private JPanel panelSugerirVenue;
	private JPanel panelModoCliente;
	
	// Modelos de tablas
	private DefaultTableModel modeloTablaEventos;
	private DefaultTableModel modeloTablaLocalidades;
	private DefaultTableModel modeloTablaCancelaciones;
	
	private JComboBox<String> comboEventosAplicarOferta;
	private JComboBox<String> comboLocalidadesAplicarOferta;

	public MenuOrganizador(modelo.Organizador organizador) {
		this.organizador = organizador;
		this.paneles = new ArrayList<>();
		
		configurarVentana();
		inicializarLayeredPanel();
		
		crearPanelMenu();
		crearPanelCrearEvento();
		crearPanelAsignarLocalidad();
		crearPanelAplicarOferta();
		crearPanelSolicitarCancelacion();
		crearPanelConsultarCancelaciones();
		crearPanelVerIngresos();
		crearPanelPorcentajeVentaTotal();
		crearPanelPorcentajeVentaEvento();
		crearPanelPorcentajeVentaLocalidad();
		crearPanelSugerirVenue();
		
		paneles.add(panelMenu);
		paneles.add(panelCrearEvento);
		paneles.add(panelAsignarLocalidad);
		paneles.add(panelAplicarOferta);
		paneles.add(panelSolicitarCancelacion);
		paneles.add(panelConsultarCancelaciones);
		paneles.add(panelVerIngresos);
		paneles.add(panelPorcentajeVentaTotal);
		paneles.add(panelPorcentajeVentaEvento);
		paneles.add(panelPorcentajeVentaLocalidad);
		paneles.add(panelSugerirVenue);
		
		mostrarPanel(panelMenu);
	}

	private void configurarVentana() {
		setTitle("BoletaMaster - Organizador: " + organizador.getLogin());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	private void inicializarLayeredPanel() {
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 884, 611);
		contentPane.add(layeredPane);
	}

	private void mostrarPanel(JPanel panel) {
		for (JPanel p : paneles) {
			p.setVisible(false);
		}
		panel.setVisible(true);
		panel.setBounds(0, 0, 884, 611);
	}

	// ==================== PANEL MENÚ ====================
	private void crearPanelMenu() {
		layeredPane.setLayout(new CardLayout(0, 0));
		panelMenu = new JPanel();
		panelMenu.setLayout(null);
		panelMenu.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelMenu, "name_242939829196200");

		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 380, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panelMenu.add(titulo);

		JLabel lblBienvenida = new JLabel("Bienvenido Organizador: " + organizador.getLogin());
		lblBienvenida.setForeground(Color.LIGHT_GRAY);
		lblBienvenida.setBounds(250, 59, 400, 26);
		lblBienvenida.setFont(new Font("Elephant", Font.PLAIN, 16));
		panelMenu.add(lblBienvenida);

		JLabel lblID = new JLabel("ID: " + organizador.getIdO());
		lblID.setForeground(new Color(255, 200, 100));
		lblID.setBounds(10, 90, 200, 20);
		lblID.setFont(new Font("Elephant", Font.PLAIN, 12));
		panelMenu.add(lblID);

		// Botones del menú
		int y = 130;
		int espaciado = 35;

		JButton btnCrearEvento = crearBotonMenu("Crear Evento", y);
		btnCrearEvento.addActionListener(e -> mostrarPanel(panelCrearEvento));
		panelMenu.add(btnCrearEvento);
		y += espaciado;

		JButton btnAsignarLocalidad = crearBotonMenu("Asignar Localidad a Evento", y);
		btnAsignarLocalidad.addActionListener(e -> {
			actualizarTablaEventos();
			mostrarPanel(panelAsignarLocalidad);
		});
		panelMenu.add(btnAsignarLocalidad);
		y += espaciado;

		JButton btnAplicarOferta = crearBotonMenu("Aplicar Oferta", y);
		btnAplicarOferta.addActionListener(e -> mostrarPanel(panelAplicarOferta));
		panelMenu.add(btnAplicarOferta);
		y += espaciado;

		JButton btnSolicitarCancelacion = crearBotonMenu("Solicitar Cancelación de Evento", y);
		btnSolicitarCancelacion.addActionListener(e -> mostrarPanel(panelSolicitarCancelacion));
		panelMenu.add(btnSolicitarCancelacion);
		y += espaciado;

		JButton btnConsultarCancelaciones = crearBotonMenu("Consultar Estado de Cancelaciones", y);
		btnConsultarCancelaciones.addActionListener(e -> {
			actualizarTablaCancelaciones();
			mostrarPanel(panelConsultarCancelaciones);
		});
		panelMenu.add(btnConsultarCancelaciones);
		y += espaciado;

		JButton btnVerIngresos = crearBotonMenu("Ver Ingresos Totales", y);
		btnVerIngresos.addActionListener(e -> mostrarPanel(panelVerIngresos));
		panelMenu.add(btnVerIngresos);
		y += espaciado;

		JButton btnPorcentajeTotal = crearBotonMenu("Porcentaje de Venta Total", y);
		btnPorcentajeTotal.addActionListener(e -> mostrarPanel(panelPorcentajeVentaTotal));
		panelMenu.add(btnPorcentajeTotal);
		y += espaciado;

		JButton btnPorcentajeEvento = crearBotonMenu("Porcentaje de Venta por Evento", y);
		btnPorcentajeEvento.addActionListener(e -> mostrarPanel(panelPorcentajeVentaEvento));
		panelMenu.add(btnPorcentajeEvento);
		y += espaciado;

		JButton btnPorcentajeLocalidad = crearBotonMenu("Porcentaje de Venta por Localidad", y);
		btnPorcentajeLocalidad.addActionListener(e -> mostrarPanel(panelPorcentajeVentaLocalidad));
		panelMenu.add(btnPorcentajeLocalidad);
		y += espaciado;

		JButton btnSugerirVenue = crearBotonMenu("Sugerir Venue", y);
		btnSugerirVenue.addActionListener(e -> mostrarPanel(panelSugerirVenue));
		panelMenu.add(btnSugerirVenue);
		y += espaciado;

		JButton btnModoCliente = crearBotonMenu("Acceder como Cliente", y);
		btnModoCliente.addActionListener(e -> abrirModoCliente());
		panelMenu.add(btnModoCliente);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnSalir.setBounds(397, 550, 89, 23);
		btnSalir.addActionListener(e -> salir());
		panelMenu.add(btnSalir);
	}

	private JButton crearBotonMenu(String texto, int y) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("Elephant", Font.PLAIN, 11));
		btn.setBounds(10, y, 864, 30);
		return btn;
	}

	// ==================== PANEL CREAR EVENTO ====================
	private void crearPanelCrearEvento() {
		panelCrearEvento = new JPanel();
		panelCrearEvento.setLayout(null);
		panelCrearEvento.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelCrearEvento, "name_242939846201200");

		agregarTituloYSubtitulo(panelCrearEvento, "Crear Evento");

		JLabel lblNombre = new JLabel("Nombre del evento:");
		lblNombre.setForeground(Color.LIGHT_GRAY);
		lblNombre.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblNombre.setBounds(10, 120, 200, 25);
		panelCrearEvento.add(lblNombre);

		JTextField txtNombre = new JTextField();
		txtNombre.setBounds(10, 150, 400, 25);
		panelCrearEvento.add(txtNombre);

		JLabel lblFecha = new JLabel("Fecha (dd/MM/yyyy):");
		lblFecha.setForeground(Color.LIGHT_GRAY);
		lblFecha.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblFecha.setBounds(450, 120, 200, 25);
		panelCrearEvento.add(lblFecha);

		JTextField txtFecha = new JTextField();
		txtFecha.setBounds(450, 150, 200, 25);
		panelCrearEvento.add(txtFecha);

		JLabel lblHora = new JLabel("Hora (HH:mm):");
		lblHora.setForeground(Color.LIGHT_GRAY);
		lblHora.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblHora.setBounds(10, 190, 200, 25);
		panelCrearEvento.add(lblHora);

		JTextField txtHora = new JTextField();
		txtHora.setBounds(10, 220, 200, 25);
		panelCrearEvento.add(txtHora);

		JLabel lblTipo = new JLabel("Tipo de evento:");
		lblTipo.setForeground(Color.LIGHT_GRAY);
		lblTipo.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblTipo.setBounds(250, 190, 200, 25);
		panelCrearEvento.add(lblTipo);

		JTextField txtTipo = new JTextField();
		txtTipo.setBounds(250, 220, 300, 25);
		panelCrearEvento.add(txtTipo);

		JLabel lblVenue = new JLabel("Venues Aprobados:");
		lblVenue.setForeground(Color.LIGHT_GRAY);
		lblVenue.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblVenue.setBounds(10, 260, 200, 25);
		panelCrearEvento.add(lblVenue);

		JComboBox<String> comboVenues = new JComboBox<>();
		comboVenues.setBounds(10, 290, 650, 25);
		panelCrearEvento.add(comboVenues);

		// Cargar venues aprobados
		cargarVenuesAprobados(comboVenues);

		JButton btnRefreshVenues = new JButton("⟳");
		btnRefreshVenues.setBounds(670, 290, 50, 25);
		btnRefreshVenues.addActionListener(e -> cargarVenuesAprobados(comboVenues));
		panelCrearEvento.add(btnRefreshVenues);

		JButton btnCrear = new JButton("Crear Evento");
		btnCrear.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnCrear.setBounds(350, 400, 180, 35);
		btnCrear.addActionListener(e -> {
			try {
				String nombre = txtNombre.getText().trim();
				String fechaStr = txtFecha.getText().trim();
				String horaStr = txtHora.getText().trim();
				String tipo = txtTipo.getText().trim();
				String venueStr = (String) comboVenues.getSelectedItem();

				if (nombre.isEmpty() || fechaStr.isEmpty() || horaStr.isEmpty() || tipo.isEmpty() || venueStr == null) {
					JOptionPane.showMessageDialog(this, "Complete todos los campos.");
					return;
				}

				DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

				LocalDate fecha = LocalDate.parse(fechaStr, formatoFecha);
				LocalTime hora = LocalTime.parse(horaStr, formatoHora);

				// Obtener el venue seleccionado
				String nombreVenue = venueStr.split(" - ")[0];
				modelo.Venue venue = buscarVenuePorNombre(nombreVenue);

				if (venue == null || !venue.getAprobado()) {
					JOptionPane.showMessageDialog(this, "El venue seleccionado no está disponible.");
					return;
				}

				if (!venue.consultarDisponibilidad(fecha)) {
					JOptionPane.showMessageDialog(this, "El venue no está disponible para esa fecha.");
					return;
				}

				int idE = new Random().nextInt(100000);
				Evento evento = organizador.crearEvento(idE, nombre, fecha, hora, tipo, venue);
				
				// Registrar en admin y en la lista global
				exec.Consola.adminGUI.registrarEvento(evento);
				exec.Consola.eventosGUI.add(evento);  // ← AGREGADO: Para que MenuCliente lo vea
				venue.addFechaOcupada(fecha);

				JOptionPane.showMessageDialog(this, "Evento creado exitosamente!\nID: " + idE);
				
				txtNombre.setText("");
				txtFecha.setText("");
				txtHora.setText("");
				txtTipo.setText("");
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
				ex.printStackTrace();
			}
		});
		panelCrearEvento.add(btnCrear);

		agregarBotonVolver(panelCrearEvento);
	}

	
	private void crearPanelAsignarLocalidad() {
		panelAsignarLocalidad = new JPanel();
		panelAsignarLocalidad.setLayout(null);
		panelAsignarLocalidad.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelAsignarLocalidad, "name_242939864081800");

		agregarTituloYSubtitulo(panelAsignarLocalidad, "Asignar Localidad");

		JLabel lblEventos = new JLabel("Seleccione un evento:");
		lblEventos.setForeground(Color.LIGHT_GRAY);
		lblEventos.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblEventos.setBounds(10, 100, 250, 25);
		panelAsignarLocalidad.add(lblEventos);

		String[] columnasEventos = {"ID", "Nombre", "Fecha", "Venue", "Capacidad Usada/Total"};
		modeloTablaEventos = new DefaultTableModel(columnasEventos, 0);
		JTable tablaEventos = new JTable(modeloTablaEventos);
		JScrollPane scrollEventos = new JScrollPane(tablaEventos);
		scrollEventos.setBounds(10, 130, 864, 150);
		panelAsignarLocalidad.add(scrollEventos);

		JLabel lblDatos = new JLabel("Datos de la localidad:");
		lblDatos.setForeground(Color.LIGHT_GRAY);
		lblDatos.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblDatos.setBounds(10, 290, 250, 25);
		panelAsignarLocalidad.add(lblDatos);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setForeground(Color.LIGHT_GRAY);
		lblNombre.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblNombre.setBounds(10, 320, 100, 20);
		panelAsignarLocalidad.add(lblNombre);

		JTextField txtNombre = new JTextField();
		txtNombre.setBounds(100, 320, 200, 25);
		panelAsignarLocalidad.add(txtNombre);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setForeground(Color.LIGHT_GRAY);
		lblPrecio.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblPrecio.setBounds(320, 320, 80, 20);
		panelAsignarLocalidad.add(lblPrecio);

		JTextField txtPrecio = new JTextField();
		txtPrecio.setBounds(400, 320, 150, 25);
		panelAsignarLocalidad.add(txtPrecio);

		JLabel lblCapacidad = new JLabel("Capacidad:");
		lblCapacidad.setForeground(Color.LIGHT_GRAY);
		lblCapacidad.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblCapacidad.setBounds(10, 350, 100, 20);
		panelAsignarLocalidad.add(lblCapacidad);

		JTextField txtCapacidad = new JTextField();
		txtCapacidad.setBounds(100, 350, 200, 25);
		panelAsignarLocalidad.add(txtCapacidad);

		JCheckBox chkNumerada = new JCheckBox("¿Localidad numerada?");
		chkNumerada.setForeground(Color.LIGHT_GRAY);
		chkNumerada.setBackground(new Color(10, 62, 114));
		chkNumerada.setFont(new Font("Elephant", Font.PLAIN, 12));
		chkNumerada.setBounds(320, 350, 250, 25);
		panelAsignarLocalidad.add(chkNumerada);
		
		
	

	    JLabel lblTipoCreacion = new JLabel("Tipo de creación:");
	    lblTipoCreacion.setForeground(Color.LIGHT_GRAY);
	    lblTipoCreacion.setFont(new Font("Elephant", Font.PLAIN, 12));
	    lblTipoCreacion.setBounds(10, 409, 110, 20);
	    panelAsignarLocalidad.add(lblTipoCreacion);

	    JComboBox<String> cbTipoCreacion = new JComboBox<>();
	    cbTipoCreacion.addItem("Tiquetes Simples");
	    cbTipoCreacion.addItem("Paquetes (TiqueteMultiple)");
	    cbTipoCreacion.setBounds(149, 405, 200, 25);
	    panelAsignarLocalidad.add(cbTipoCreacion);


	 

	    JLabel lblEntradasPack = new JLabel("Entradas por paquete:");
	    lblEntradasPack.setForeground(Color.LIGHT_GRAY);
	    lblEntradasPack.setFont(new Font("Elephant", Font.PLAIN, 12));
	    lblEntradasPack.setBounds(15, 478, 143, 20);
	    lblEntradasPack.setVisible(false);
	    panelAsignarLocalidad.add(lblEntradasPack);

	    JTextField txtEntradasPack = new JTextField();
	    txtEntradasPack.setBounds(173, 473, 200, 25);
	    txtEntradasPack.setVisible(false);
	    panelAsignarLocalidad.add(txtEntradasPack);

	    JLabel lblCantidadPack = new JLabel("Número de paquetes:");
	    lblCantidadPack.setForeground(Color.LIGHT_GRAY);
	    lblCantidadPack.setFont(new Font("Elephant", Font.PLAIN, 12));
	    lblCantidadPack.setBounds(400, 478, 200, 20);
	    lblCantidadPack.setVisible(false);
	    panelAsignarLocalidad.add(lblCantidadPack);

	    JTextField txtCantidadPack = new JTextField();
	    txtCantidadPack.setBounds(566, 474, 200, 25);
	    txtCantidadPack.setVisible(false);
	    panelAsignarLocalidad.add(txtCantidadPack);


	  

	    cbTipoCreacion.addActionListener(e -> {
	        boolean esPaquete = cbTipoCreacion.getSelectedIndex() == 1;

	        lblEntradasPack.setVisible(esPaquete);
	        txtEntradasPack.setVisible(esPaquete);

	        lblCantidadPack.setVisible(esPaquete);
	        txtCantidadPack.setVisible(esPaquete);

	        chkNumerada.setEnabled(!esPaquete);
	        if (esPaquete) chkNumerada.setSelected(false);
	    });
		
		

		JButton btnAsignar = new JButton("Crear y Asignar Localidad");
		btnAsignar.setFont(new Font("Elephant", Font.PLAIN, 13));
		btnAsignar.setBounds(305, 540, 280, 35);
		btnAsignar.addActionListener(e -> {
			try {
				int filaSeleccionada = tablaEventos.getSelectedRow();
				if (filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(this, "Seleccione un evento de la tabla.");
					return;
				}

				int idE = (int) modeloTablaEventos.getValueAt(filaSeleccionada, 0);
				String nombre = txtNombre.getText().trim();
				double precio = Double.parseDouble(txtPrecio.getText().trim());
				int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
				boolean numerada = chkNumerada.isSelected();
				
				
				
				

				Evento evento = buscarEventoPorId(idE);
				if (evento == null) {
					JOptionPane.showMessageDialog(this, "Evento no encontrado.");
					return;
				}
				

				int capacidadRestante = evento.getVenue().getCapacidad() - evento.capacidadOcupada();
				if (capacidad > capacidadRestante) {
					JOptionPane.showMessageDialog(this, 
						"Capacidad excede límite del venue.\n" +
						"Disponible: " + capacidadRestante);
					return;
				}

				// Crear localidad
				int idL = new Random().nextInt(10000);
				Localidad localidad = new Localidad(idL, nombre, precio, capacidad);
				localidad.setNumerada(numerada);
				
				 boolean esPaquete = cbTipoCreacion.getSelectedIndex() == 1;

				// Crear tiquetes
				if (!esPaquete) {

		              for (int i = 0; i < capacidad; i++) {
		                  int idT = new Random().nextInt(99999);
		                  TiqueteSimple t = new TiqueteSimple(idT, true, "ADMIN", estadoTiquete.DISPONIBLE,
		                          "SIMPLE", evento, localidad);
		                  localidad.addTiquete(t);
		             }

		          } else {


		        	    int entradas = Integer.parseInt(txtEntradasPack.getText().trim());
		        	    int cantPaquetes = Integer.parseInt(txtCantidadPack.getText().trim());

		        	    
		        	    double precioBase = precio;

		        	    for (int i = 0; i < cantPaquetes; i++) {

		        	        int idPack = new Random().nextInt(99999);
		        	        TiqueteMultiple pack = new TiqueteMultiple(
		        	                idPack, true, "ADMIN",
		        	                Tiquetes.estadoTiquete.DISPONIBLE, "MULTIPLE"
		        	        );

		        	        double precioTotal = 0;
		        	        int agregados = 0;

		        	        
		        	        while (agregados < entradas) {

		        	            int idT = new Random().nextInt(99999);

		        	            TiqueteSimple ts = new TiqueteSimple(
		        	                    idT,
		        	                    true,
		        	                    "ADMIN",
		        	                    Tiquetes.estadoTiquete.DISPONIBLE,
		        	                    "SIMPLE",
		        	                    evento,
		        	                    localidad
		        	            );

		        	            pack.addEntrada(ts);
		        	            precioTotal += precioBase;
		        	            agregados++;
		        	        }

		        	    
		        	        pack.setPrecioPaquete(precioTotal);

		        	        localidad.addTiquete(pack);
		                }
				}

				evento.addLocalidad(localidad);
				refrescarPanelAplicarOferta();

				JOptionPane.showMessageDialog(this, 
					"Localidad creada y asignada exitosamente!\n" +
					"ID Localidad: " + idL + "\n" +
					"Capacidad: " + capacidad + " tiquetes creados");

				txtNombre.setText("");
				txtPrecio.setText("");
				txtCapacidad.setText("");
				chkNumerada.setSelected(false);
				actualizarTablaEventos();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		
		
		panelAsignarLocalidad.add(btnAsignar);

		agregarBotonVolver(panelAsignarLocalidad);
	}

	// ==================== PANEL APLICAR OFERTA ====================
	private void crearPanelAplicarOferta() {
		panelAplicarOferta = new JPanel();
		panelAplicarOferta.setLayout(null);
		panelAplicarOferta.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelAplicarOferta, "name_242939883791100");

		agregarTituloYSubtitulo(panelAplicarOferta, "Aplicar Oferta");

		JLabel lblEvento = new JLabel("Seleccione evento:");
		lblEvento.setForeground(Color.LIGHT_GRAY);
		lblEvento.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblEvento.setBounds(10, 100, 200, 25);
		panelAplicarOferta.add(lblEvento);

		comboEventosAplicarOferta = new JComboBox<>();
		comboEventosAplicarOferta.setBounds(10, 130, 400, 25);
		panelAplicarOferta.add(comboEventosAplicarOferta);

		JLabel lblLocalidad = new JLabel("Seleccione localidad:");
		lblLocalidad.setForeground(Color.LIGHT_GRAY);
		lblLocalidad.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblLocalidad.setBounds(10, 170, 200, 25);
		panelAplicarOferta.add(lblLocalidad);

		comboLocalidadesAplicarOferta = new JComboBox<>();
		comboLocalidadesAplicarOferta.setBounds(10, 200, 400, 25);
		panelAplicarOferta.add(comboLocalidadesAplicarOferta);

		// Cargar eventos
		cargarEventosOrganizador(comboEventosAplicarOferta);
		comboEventosAplicarOferta.addActionListener(e -> cargarLocalidadesEvento(comboEventosAplicarOferta, comboLocalidadesAplicarOferta));

		JLabel lblPorcentaje = new JLabel("Porcentaje de descuento:");
		lblPorcentaje.setForeground(Color.LIGHT_GRAY);
		lblPorcentaje.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblPorcentaje.setBounds(10, 240, 250, 25);
		panelAplicarOferta.add(lblPorcentaje);

		JTextField txtPorcentaje = new JTextField();
		txtPorcentaje.setBounds(10, 270, 150, 25);
		panelAplicarOferta.add(txtPorcentaje);

		JLabel lblFechaInicio = new JLabel("Fecha inicio (dd/MM/yyyy):");
		lblFechaInicio.setForeground(Color.LIGHT_GRAY);
		lblFechaInicio.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblFechaInicio.setBounds(10, 310, 250, 25);
		panelAplicarOferta.add(lblFechaInicio);

		JTextField txtFechaInicio = new JTextField();
		txtFechaInicio.setBounds(10, 340, 200, 25);
		panelAplicarOferta.add(txtFechaInicio);

		JLabel lblFechaFin = new JLabel("Fecha fin (dd/MM/yyyy):");
		lblFechaFin.setForeground(Color.LIGHT_GRAY);
		lblFechaFin.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblFechaFin.setBounds(250, 310, 250, 25);
		panelAplicarOferta.add(lblFechaFin);

		JTextField txtFechaFin = new JTextField();
		txtFechaFin.setBounds(250, 340, 200, 25);
		panelAplicarOferta.add(txtFechaFin);

		JButton btnAplicar = new JButton("Aplicar Oferta");
		btnAplicar.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnAplicar.setBounds(350, 450, 180, 35);
		btnAplicar.addActionListener(e -> {
			try {
				String localidadStr = (String) comboLocalidadesAplicarOferta.getSelectedItem();
				if (localidadStr == null) {
					JOptionPane.showMessageDialog(this, "Seleccione una localidad.");
					return;
				}

				int idL = Integer.parseInt(localidadStr.split(" - ID:")[1].split(" ")[0]);
				double porcentaje = Double.parseDouble(txtPorcentaje.getText().trim());
				
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText().trim(), formato);
				LocalDate fechaFin = LocalDate.parse(txtFechaFin.getText().trim(), formato);

				organizador.aplicarOferta(idL, porcentaje, fechaInicio, fechaFin);

				JOptionPane.showMessageDialog(this, 
					"Oferta aplicada exitosamente!\n" +
					"Descuento: " + porcentaje + "%\n" +
					"Vigencia: " + fechaInicio + " a " + fechaFin);

				txtPorcentaje.setText("");
				txtFechaInicio.setText("");
				txtFechaFin.setText("");

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelAplicarOferta.add(btnAplicar);

		agregarBotonVolver(panelAplicarOferta);
	}

	// ==================== PANEL SOLICITAR CANCELACIÓN ====================
	private void crearPanelSolicitarCancelacion() {
		panelSolicitarCancelacion = new JPanel();
		panelSolicitarCancelacion.setLayout(null);
		panelSolicitarCancelacion.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelSolicitarCancelacion, "name_242939900541000");

		agregarTituloYSubtitulo(panelSolicitarCancelacion, "Solicitar Cancelación");

		JLabel lblEvento = new JLabel("Seleccione evento a cancelar:");
		lblEvento.setForeground(Color.LIGHT_GRAY);
		lblEvento.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblEvento.setBounds(10, 120, 300, 25);
		panelSolicitarCancelacion.add(lblEvento);

		JComboBox<String> comboEventos = new JComboBox<>();
		comboEventos.setBounds(10, 150, 650, 25);
		panelSolicitarCancelacion.add(comboEventos);
		cargarEventosOrganizador(comboEventos);

		JLabel lblMotivo = new JLabel("Motivo de la cancelación:");
		lblMotivo.setForeground(Color.LIGHT_GRAY);
		lblMotivo.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblMotivo.setBounds(10, 200, 300, 25);
		panelSolicitarCancelacion.add(lblMotivo);

		JTextArea txtMotivo = new JTextArea();
		txtMotivo.setLineWrap(true);
		txtMotivo.setWrapStyleWord(true);
		JScrollPane scrollMotivo = new JScrollPane(txtMotivo);
		scrollMotivo.setBounds(10, 230, 864, 120);
		panelSolicitarCancelacion.add(scrollMotivo);

		JLabel lblInfo = new JLabel("<html>Nota: La solicitud será revisada por el administrador.<br>" +
									 "El reembolso será procesado automáticamente si se aprueba.</html>");
		lblInfo.setForeground(new Color(255, 200, 100));
		lblInfo.setFont(new Font("Elephant", Font.PLAIN, 11));
		lblInfo.setBounds(10, 360, 600, 40);
		panelSolicitarCancelacion.add(lblInfo);

		JButton btnSolicitar = new JButton("Enviar Solicitud");
		btnSolicitar.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnSolicitar.setBounds(350, 450, 200, 35);
		btnSolicitar.addActionListener(e -> {
			try {
				String eventoStr = (String) comboEventos.getSelectedItem();
				if (eventoStr == null) {
					JOptionPane.showMessageDialog(this, "Seleccione un evento.");
					return;
				}

				int idE = Integer.parseInt(eventoStr.split(" - ID:")[1].split(" ")[0]);
				String motivo = txtMotivo.getText().trim();

				if (motivo.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Debe ingresar un motivo.");
					return;
				}

				organizador.solicitarCancelacion(idE, motivo);

				JOptionPane.showMessageDialog(this, 
					"Solicitud enviada al administrador.\n" +
					"Será notificado cuando se revise.");

				txtMotivo.setText("");
				comboEventos.setSelectedIndex(0);

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelSolicitarCancelacion.add(btnSolicitar);

		agregarBotonVolver(panelSolicitarCancelacion);
	}

	// ==================== PANEL CONSULTAR CANCELACIONES ====================
	private void crearPanelConsultarCancelaciones() {
		panelConsultarCancelaciones = new JPanel();
		panelConsultarCancelaciones.setLayout(null);
		panelConsultarCancelaciones.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelConsultarCancelaciones, "name_242939917728200");

		agregarTituloYSubtitulo(panelConsultarCancelaciones, "Estado de Cancelaciones");

		String[] columnas = {"ID", "Evento", "Fecha", "Estado", "Motivo"};
		modeloTablaCancelaciones = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modeloTablaCancelaciones);
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(10, 100, 864, 450);
		panelConsultarCancelaciones.add(scroll);

		agregarBotonVolver(panelConsultarCancelaciones);
	}

	// ==================== PANEL VER INGRESOS ====================
	private void crearPanelVerIngresos() {
		panelVerIngresos = new JPanel();
		panelVerIngresos.setLayout(null);
		panelVerIngresos.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelVerIngresos, "name_242939939284900");

		agregarTituloYSubtitulo(panelVerIngresos, "Ingresos Totales");

		JLabel lblIngresos = new JLabel("Calculando...");
		lblIngresos.setForeground(new Color(0, 255, 100));
		lblIngresos.setFont(new Font("Elephant", Font.BOLD, 40));
		lblIngresos.setBounds(100, 200, 700, 80);
		lblIngresos.setHorizontalAlignment(SwingConstants.CENTER);
		panelVerIngresos.add(lblIngresos);

		JLabel lblInfo = new JLabel("Total de ingresos generados por todos tus eventos activos");
		lblInfo.setForeground(Color.LIGHT_GRAY);
		lblInfo.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblInfo.setBounds(100, 300, 700, 30);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		panelVerIngresos.add(lblInfo);

		JButton btnCalcular = new JButton("Actualizar");
		btnCalcular.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnCalcular.setBounds(370, 370, 150, 35);
		btnCalcular.addActionListener(e -> {
			double ingresos = organizador.verIngresos();
			lblIngresos.setText("$" + String.format("%.2f", ingresos));
		});
		panelVerIngresos.add(btnCalcular);

		// Calcular al abrir
		double ingresos = organizador.verIngresos();
		lblIngresos.setText("$" + String.format("%.2f", ingresos));

		agregarBotonVolver(panelVerIngresos);
	}

	// ==================== PANEL PORCENTAJE VENTA TOTAL ====================
	private void crearPanelPorcentajeVentaTotal() {
		panelPorcentajeVentaTotal = new JPanel();
		panelPorcentajeVentaTotal.setLayout(null);
		panelPorcentajeVentaTotal.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelPorcentajeVentaTotal, "name_242939958711800");

		agregarTituloYSubtitulo(panelPorcentajeVentaTotal, "% Venta Total");

		JLabel lblPorcentaje = new JLabel("Calculando...");
		lblPorcentaje.setForeground(new Color(255, 200, 0));
		lblPorcentaje.setFont(new Font("Elephant", Font.BOLD, 50));
		lblPorcentaje.setBounds(100, 200, 700, 80);
		lblPorcentaje.setHorizontalAlignment(SwingConstants.CENTER);
		panelPorcentajeVentaTotal.add(lblPorcentaje);

		JLabel lblInfo = new JLabel("Porcentaje de ocupación de todos tus eventos");
		lblInfo.setForeground(Color.LIGHT_GRAY);
		lblInfo.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblInfo.setBounds(100, 300, 700, 30);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		panelPorcentajeVentaTotal.add(lblInfo);

		JButton btnCalcular = new JButton("Actualizar");
		btnCalcular.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnCalcular.setBounds(370, 370, 150, 35);
		btnCalcular.addActionListener(e -> {
			double porcentaje = organizador.porcentajeVentaTotal();
			lblPorcentaje.setText(String.format("%.2f", porcentaje) + "%");
		});
		panelPorcentajeVentaTotal.add(btnCalcular);

		double porcentaje = organizador.porcentajeVentaTotal();
		lblPorcentaje.setText(String.format("%.2f", porcentaje) + "%");

		agregarBotonVolver(panelPorcentajeVentaTotal);
	}

	// ==================== PANEL PORCENTAJE VENTA POR EVENTO ====================
	private void crearPanelPorcentajeVentaEvento() {
		panelPorcentajeVentaEvento = new JPanel();
		panelPorcentajeVentaEvento.setLayout(null);
		panelPorcentajeVentaEvento.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelPorcentajeVentaEvento, "name_242939977157100");

		agregarTituloYSubtitulo(panelPorcentajeVentaEvento, "% Venta por Evento");

		JLabel lblEvento = new JLabel("Seleccione evento:");
		lblEvento.setForeground(Color.LIGHT_GRAY);
		lblEvento.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblEvento.setBounds(10, 120, 200, 25);
		panelPorcentajeVentaEvento.add(lblEvento);

		JComboBox<String> comboEventos = new JComboBox<>();
		comboEventos.setBounds(10, 150, 650, 25);
		panelPorcentajeVentaEvento.add(comboEventos);
		cargarEventosOrganizador(comboEventos);

		JLabel lblResultado = new JLabel("--");
		lblResultado.setForeground(new Color(255, 200, 0));
		lblResultado.setFont(new Font("Elephant", Font.BOLD, 50));
		lblResultado.setBounds(100, 250, 700, 80);
		lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
		panelPorcentajeVentaEvento.add(lblResultado);

		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnCalcular.setBounds(370, 400, 150, 35);
		btnCalcular.addActionListener(e -> {
			try {
				String eventoStr = (String) comboEventos.getSelectedItem();
				if (eventoStr == null) {
					JOptionPane.showMessageDialog(this, "Seleccione un evento.");
					return;
				}

				int idE = Integer.parseInt(eventoStr.split(" - ID:")[1].split(" ")[0]);
				double porcentaje = organizador.porcentajeVentaPorEvento(idE);
				lblResultado.setText(String.format("%.2f", porcentaje) + "%");

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelPorcentajeVentaEvento.add(btnCalcular);

		agregarBotonVolver(panelPorcentajeVentaEvento);
	}

	// ==================== PANEL PORCENTAJE VENTA POR LOCALIDAD ====================
	private void crearPanelPorcentajeVentaLocalidad() {
		panelPorcentajeVentaLocalidad = new JPanel();
		panelPorcentajeVentaLocalidad.setLayout(null);
		panelPorcentajeVentaLocalidad.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelPorcentajeVentaLocalidad, "name_242939994485100");

		agregarTituloYSubtitulo(panelPorcentajeVentaLocalidad, "% Venta por Localidad");

		JLabel lblEvento = new JLabel("Seleccione evento:");
		lblEvento.setForeground(Color.LIGHT_GRAY);
		lblEvento.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblEvento.setBounds(10, 100, 200, 25);
		panelPorcentajeVentaLocalidad.add(lblEvento);

		JComboBox<String> comboEventos = new JComboBox<>();
		comboEventos.setBounds(10, 130, 400, 25);
		panelPorcentajeVentaLocalidad.add(comboEventos);

		JLabel lblLocalidad = new JLabel("Seleccione localidad:");
		lblLocalidad.setForeground(Color.LIGHT_GRAY);
		lblLocalidad.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblLocalidad.setBounds(10, 170, 200, 25);
		panelPorcentajeVentaLocalidad.add(lblLocalidad);

		JComboBox<String> comboLocalidades = new JComboBox<>();
		comboLocalidades.setBounds(10, 200, 400, 25);
		panelPorcentajeVentaLocalidad.add(comboLocalidades);

		cargarEventosOrganizador(comboEventos);
		comboEventos.addActionListener(e -> cargarLocalidadesEvento(comboEventos, comboLocalidades));

		JLabel lblResultado = new JLabel("--");
		lblResultado.setForeground(new Color(255, 200, 0));
		lblResultado.setFont(new Font("Elephant", Font.BOLD, 50));
		lblResultado.setBounds(100, 280, 700, 80);
		lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
		panelPorcentajeVentaLocalidad.add(lblResultado);

		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnCalcular.setBounds(370, 420, 150, 35);
		btnCalcular.addActionListener(e -> {
			try {
				String localidadStr = (String) comboLocalidades.getSelectedItem();
				if (localidadStr == null) {
					JOptionPane.showMessageDialog(this, "Seleccione una localidad.");
					return;
				}

				int idL = Integer.parseInt(localidadStr.split(" - ID:")[1].split(" ")[0]);
				double porcentaje = organizador.porcentajeVentaPorLocalidad(idL);
				lblResultado.setText(String.format("%.2f", porcentaje) + "%");

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelPorcentajeVentaLocalidad.add(btnCalcular);

		agregarBotonVolver(panelPorcentajeVentaLocalidad);
	}

	// ==================== PANEL SUGERIR VENUE ====================
	private void crearPanelSugerirVenue() {
		panelSugerirVenue = new JPanel();
		panelSugerirVenue.setLayout(null);
		panelSugerirVenue.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelSugerirVenue, "name_242940010700600");

		agregarTituloYSubtitulo(panelSugerirVenue, "Sugerir Venue");

		JLabel lblNombre = new JLabel("Nombre del venue:");
		lblNombre.setForeground(Color.LIGHT_GRAY);
		lblNombre.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblNombre.setBounds(10, 120, 200, 25);
		panelSugerirVenue.add(lblNombre);

		JTextField txtNombre = new JTextField();
		txtNombre.setBounds(10, 150, 400, 25);
		panelSugerirVenue.add(txtNombre);

		JLabel lblTipo = new JLabel("Tipo de venue:");
		lblTipo.setForeground(Color.LIGHT_GRAY);
		lblTipo.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblTipo.setBounds(450, 120, 200, 25);
		panelSugerirVenue.add(lblTipo);

		JTextField txtTipo = new JTextField();
		txtTipo.setBounds(450, 150, 400, 25);
		panelSugerirVenue.add(txtTipo);

		JLabel lblUbicacion = new JLabel("Ubicación:");
		lblUbicacion.setForeground(Color.LIGHT_GRAY);
		lblUbicacion.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblUbicacion.setBounds(10, 190, 200, 25);
		panelSugerirVenue.add(lblUbicacion);

		JTextField txtUbicacion = new JTextField();
		txtUbicacion.setBounds(10, 220, 840, 25);
		panelSugerirVenue.add(txtUbicacion);

		JLabel lblCapacidad = new JLabel("Capacidad:");
		lblCapacidad.setForeground(Color.LIGHT_GRAY);
		lblCapacidad.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblCapacidad.setBounds(10, 260, 200, 25);
		panelSugerirVenue.add(lblCapacidad);

		JTextField txtCapacidad = new JTextField();
		txtCapacidad.setBounds(10, 290, 200, 25);
		panelSugerirVenue.add(txtCapacidad);

		JLabel lblRestricciones = new JLabel("Restricciones:");
		lblRestricciones.setForeground(Color.LIGHT_GRAY);
		lblRestricciones.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblRestricciones.setBounds(10, 330, 200, 25);
		panelSugerirVenue.add(lblRestricciones);

		JTextArea txtRestricciones = new JTextArea();
		txtRestricciones.setLineWrap(true);
		txtRestricciones.setWrapStyleWord(true);
		JScrollPane scrollRestricciones = new JScrollPane(txtRestricciones);
		scrollRestricciones.setBounds(10, 360, 864, 80);
		panelSugerirVenue.add(scrollRestricciones);

		JButton btnSugerir = new JButton("Enviar Sugerencia");
		btnSugerir.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnSugerir.setBounds(340, 480, 200, 35);
		btnSugerir.addActionListener(e -> {
			try {
				String nombre = txtNombre.getText().trim();
				String tipo = txtTipo.getText().trim();
				String ubicacion = txtUbicacion.getText().trim();
				int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
				String restricciones = txtRestricciones.getText().trim();

				if (nombre.isEmpty() || tipo.isEmpty() || ubicacion.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
					return;
				}

				Venue venue = new Venue(nombre, capacidad, tipo, ubicacion, restricciones);
				venue.setAprobado(false);
				exec.Consola.adminGUI.registrarVenue(venue);

				JOptionPane.showMessageDialog(this, 
					"Sugerencia enviada al administrador.\n" +
					"El venue quedará pendiente de aprobación.");

				txtNombre.setText("");
				txtTipo.setText("");
				txtUbicacion.setText("");
				txtCapacidad.setText("");
				txtRestricciones.setText("");

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelSugerirVenue.add(btnSugerir);

		agregarBotonVolver(panelSugerirVenue);
	}

	// ==================== MÉTODOS AUXILIARES ====================
	private void agregarTituloYSubtitulo(JPanel panel, String subtitulo) {
		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 380, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panel.add(titulo);

		JLabel sub = new JLabel(subtitulo);
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(350, 51, 134, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panel.add(sub);
	}

	private void agregarBotonVolver(JPanel panel) {
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(10, 10, 100, 23);
		btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
		panel.add(btnVolver);
	}

	private void cargarVenuesAprobados(JComboBox<String> combo) {
		combo.removeAllItems();
		for (Venue v : exec.Consola.adminGUI.getVenues()) {
			if (v.getAprobado()) {
				combo.addItem(v.getNombreV() + " - Cap: " + v.getCapacidad() + " - " + v.getTipoV());
			}
		}
	}

	private Venue buscarVenuePorNombre(String nombre) {
		for (Venue v : exec.Consola.adminGUI.getVenues()) {
			if (v.getNombreV().equals(nombre)) {
				return v;
			}
		}
		return null;
	}

	private void cargarEventosOrganizador(JComboBox<String> combo) {
		combo.removeAllItems();
		for (Evento e : organizador.getEventos()) {
			combo.addItem(e.getNombreE() + " - ID:" + e.getIdE() + " (" + e.getFecha() + ")");
		}
	}

	private void cargarLocalidadesEvento(JComboBox<String> comboEventos, JComboBox<String> comboLocalidades) {
		comboLocalidades.removeAllItems();
		try {
			String eventoStr = (String) comboEventos.getSelectedItem();
			if (eventoStr != null) {
				int idE = Integer.parseInt(eventoStr.split(" - ID:")[1].split(" ")[0]);
				Evento evento = buscarEventoPorId(idE);
				if (evento != null) {
					for (Localidad l : evento.getLocalidades()) {
						comboLocalidades.addItem(l.getNombreL() + " - ID:" + l.getIdL() + " - $" + l.getPrecioBase());
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Evento buscarEventoPorId(int idE) {
		for (Evento e : organizador.getEventos()) {
			if (e.getIdE() == idE) {
				return e;
			}
		}
		return null;
	}

	private void actualizarTablaEventos() {
		if (modeloTablaEventos == null) return;
		
		modeloTablaEventos.setRowCount(0);
		for (Evento e : organizador.getEventos()) {
			if (e.getEstado() != estadoEvento.CANCELADO) {
				int capacidadUsada = e.capacidadOcupada();
				int capacidadTotal = e.getVenue().getCapacidad();
				modeloTablaEventos.addRow(new Object[]{
					e.getIdE(),
					e.getNombreE(),
					e.getFecha(),
					e.getVenue().getNombreV(),
					capacidadUsada + "/" + capacidadTotal
				});
			}
		}
	}

	private void actualizarTablaCancelaciones() {
		if (modeloTablaCancelaciones == null) return;
		
		modeloTablaCancelaciones.setRowCount(0);
		for (Evento e : organizador.getEventos()) {
			String estado;
			if (e.getCancelacionAprobada()) {
				estado = "✓ Aprobada";
			} else if (e.getCancelacionSolicitada()) {
				estado = "⏳ En espera";
			} else {
				estado = "✓ Activo";
			}

			modeloTablaCancelaciones.addRow(new Object[]{
				e.getIdE(),
				e.getNombreE(),
				e.getFecha(),
				estado,
				e.getMotivoCancelacion() != null ? e.getMotivoCancelacion() : "-"
			});
		}
	}

	private void abrirModoCliente() {
		MenuCliente menuCliente = new MenuCliente(organizador);
		menuCliente.setVisible(true);
		this.dispose();
	}

	private void salir() {
		try {
			Persistencia.PerUsuario.guardarUsuarios(new ArrayList<>(exec.Consola.usuariosGUI));
			JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
		}
		
		Login login = new Login();
		login.setVisible(true);
		this.dispose();
	}
	
	private void refrescarPanelAplicarOferta() {
	    cargarEventosOrganizador(comboEventosAplicarOferta);

	    if (comboEventosAplicarOferta.getItemCount() > 0) {
	        comboEventosAplicarOferta.setSelectedIndex(0);
	        cargarLocalidadesEvento(comboEventosAplicarOferta, comboLocalidadesAplicarOferta);
	    }
	}
}
