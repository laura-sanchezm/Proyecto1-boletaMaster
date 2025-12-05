package gui;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import java.util.Random;

public class MenuOrganizador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private modelo.Organizador organizador;
	
	
	private ArrayList<JPanel> paneles = new ArrayList<>();
	private JPanel panelMenu;
	private JPanel panelOrganizarE;
	private JPanel panelCreacionE_1;
	private JPanel panelCreacionE_2;
	private JPanel panelAsignarL;
	private JLayeredPane layeredPane;
	
	
	private JTextField txtNombre;
	private JTextField textField_1;
	private JComboBox<String> cbDia, cbMes, cbAno;
	private JComboBox<String> cbHora, cbMinuto;
	private String nombreE_1;
	private LocalDate fechaE_1;
	private LocalTime horaE_1;
	private String tipoE_1;
	private modelo.Venue venueSeleccionado;
	private JComboBox<String> cbEventos; 
	private modelo.Evento eventoSeleccionadoLocalidad;
	private JTextField txtNombreL;
	private JTextField txtPrecioBL;
	private JTextField txtCantidadPaquetes;
	private JTextField txtCapacidadL;
	private JTextField txtNumPaquetes;
	


	/**
	 * Create the frame.
	 */
	public MenuOrganizador(modelo.Organizador organizador) {
		this.organizador = organizador;
		
		configurarVentana();
		inicializarLayeredPanel();
		crearPanelMenu();
		crearPanelOrganizarE();
		crearPanelCreacionE_1();
		crearPanelCreacionE_2();
		crearPanelAsignarL();
		
		
		paneles.add(panelMenu);
		paneles.add(panelOrganizarE);
		paneles.add(panelCreacionE_1);
		paneles.add(panelCreacionE_2);
		paneles.add(panelAsignarL);
		
		
		
		mostrarPanel(panelMenu);
				
		
		

	}
	
	
	private void configurarVentana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 589);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(10, 62, 114));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void inicializarLayeredPanel() {
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 760, 550);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
	}
	
	
	private void crearPanelMenu() {
		panelMenu = new JPanel();
		panelMenu.setBackground(new Color(10, 62, 114));
		panelMenu.setLayout(null);
		layeredPane.add(panelMenu);
		
		JLabel tituloMenu = new JLabel("BOLETA MASTER");
		tituloMenu.setBounds(222, 11, 310, 37);
		tituloMenu.setFont(new Font("Elephant", Font.PLAIN, 30));
		tituloMenu.setForeground(new Color(192, 192, 192));
		panelMenu.add(tituloMenu);
		
		JLabel subtituloMenu = new JLabel("Menu Organizador");
		subtituloMenu.setForeground(new Color(192, 192, 192));
		subtituloMenu.setBounds(285, 57, 191, 26);
		subtituloMenu.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelMenu.add(subtituloMenu);
		
		JButton btnOrganizar = new JButton("Organizar Eventos");
		btnOrganizar.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnOrganizar.setBackground(new Color(255, 255, 255));
		btnOrganizar.setBounds(10, 166, 740, 23);
		panelMenu.add(btnOrganizar);
		
		btnOrganizar.addActionListener(e -> mostrarPanel(panelOrganizarE));
		
		JButton btnSolicitarCancelaci = new JButton("Solicitar Cancelación");
		btnSolicitarCancelaci.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnSolicitarCancelaci.setBackground(Color.WHITE);
		btnSolicitarCancelaci.setBounds(10, 200, 740, 23);
		panelMenu.add(btnSolicitarCancelaci);
		
		JButton btnConsultarEstadoCancelaciones = new JButton("Consultar Estado Cancelaciones");
		btnConsultarEstadoCancelaciones.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnConsultarEstadoCancelaciones.setBackground(Color.WHITE);
		btnConsultarEstadoCancelaciones.setBounds(10, 234, 740, 23);
		panelMenu.add(btnConsultarEstadoCancelaciones);
		
		JButton btnMenuDeIngresos = new JButton("Menu de Ingresos");
		btnMenuDeIngresos.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnMenuDeIngresos.setBackground(Color.WHITE);
		btnMenuDeIngresos.setBounds(10, 267, 740, 23);
		panelMenu.add(btnMenuDeIngresos);
		
		JButton btnSugerirVenue = new JButton("Sugerir Venue");
		btnSugerirVenue.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnSugerirVenue.setBackground(Color.WHITE);
		btnSugerirVenue.setBounds(10, 301, 740, 23);
		panelMenu.add(btnSugerirVenue);
		
		JButton btnAccederAlMenu = new JButton("Acceder al menu de Cliente");
		btnAccederAlMenu.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnAccederAlMenu.setBackground(Color.WHITE);
		btnAccederAlMenu.setBounds(10, 335, 740, 23);
		panelMenu.add(btnAccederAlMenu);
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnSalir.setBounds(326, 426, 89, 23);
		panelMenu.add(btnSalir);
		
		btnSalir.addActionListener(e -> { this.dispose();
        new Login().setVisible(true); });
	
		
	}
	
	private void crearPanelOrganizarE(){
		panelOrganizarE = new JPanel();
		panelOrganizarE.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelOrganizarE, "name_86757222996400");
		panelOrganizarE.setLayout(null);
		
		JLabel tituloMenu = new JLabel("BOLETA MASTER");
		tituloMenu.setBounds(222, 11, 310, 37);
		tituloMenu.setFont(new Font("Elephant", Font.PLAIN, 30));
		tituloMenu.setForeground(new Color(192, 192, 192));
		panelOrganizarE.add(tituloMenu);
		
		JLabel subtituloMenu = new JLabel("Organizar Evento");
		subtituloMenu.setForeground(new Color(192, 192, 192));
		subtituloMenu.setBounds(286, 57, 179, 26);
		subtituloMenu.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelOrganizarE.add(subtituloMenu);
		
		JButton btnCrearE = new JButton("Crear Evento");
		btnCrearE.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnCrearE.setBackground(Color.WHITE);
		btnCrearE.setBounds(10, 177, 740, 23);
		panelOrganizarE.add(btnCrearE);
		
		btnCrearE.addActionListener(e -> mostrarPanel(panelCreacionE_1));
		
		JButton btnAsignarL = new JButton("Asignar Localidad");
		btnAsignarL.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnAsignarL.setBackground(Color.WHITE);
		btnAsignarL.setBounds(10, 245, 740, 23);
		panelOrganizarE.add(btnAsignarL);
		
		btnAsignarL.addActionListener(e -> mostrarPanel(panelAsignarL));
		
		JButton btnAplicarO = new JButton("Aplicar Oferta");
		btnAplicarO.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnAplicarO.setBackground(Color.WHITE);
		btnAplicarO.setBounds(10, 313, 740, 23);
		panelOrganizarE.add(btnAplicarO);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnVolver.setBounds(10, 11, 89, 23);
		panelOrganizarE.add(btnVolver);
		
		btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
		
		
	}
	
	private void crearPanelCreacionE_1() {
		
		panelCreacionE_1 = new JPanel();
		panelCreacionE_1.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelCreacionE_1, "name_87754789921100");
		panelCreacionE_1.setLayout(null);
		
		JLabel tituloMenu = new JLabel("BOLETA MASTER");
		tituloMenu.setForeground(Color.LIGHT_GRAY);
		tituloMenu.setFont(new Font("Elephant", Font.PLAIN, 30));
		tituloMenu.setBounds(222, 11, 310, 37);
		panelCreacionE_1.add(tituloMenu);
		
		JLabel lblCrearEvento = new JLabel("Crear Evento");
		lblCrearEvento.setForeground(Color.LIGHT_GRAY);
		lblCrearEvento.setFont(new Font("Elephant", Font.PLAIN, 20));
		lblCrearEvento.setBounds(308, 57, 134, 26);
		panelCreacionE_1.add(lblCrearEvento);
		
		JLabel pedriNomE = new JLabel("Ingrese el nombre del evento");
		pedriNomE.setForeground(new Color(192, 192, 192));
		pedriNomE.setFont(new Font("Elephant", Font.PLAIN, 15));
		pedriNomE.setBounds(10, 138, 225, 14);
		panelCreacionE_1.add(pedriNomE);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(10, 163, 740, 20);
		panelCreacionE_1.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel pedirFechaE = new JLabel("Ingrese la fecha del evento");
		pedirFechaE.setForeground(Color.LIGHT_GRAY);
		pedirFechaE.setFont(new Font("Elephant", Font.PLAIN, 15));
		pedirFechaE.setBounds(10, 194, 225, 14);
		panelCreacionE_1.add(pedirFechaE);
		
		cbDia = new JComboBox<>();
		cbDia.setBounds(10, 219, 60, 22);
		panelCreacionE_1.add(cbDia);

		cbMes = new JComboBox<>();
		cbMes.setBounds(80, 219, 60, 22);
		panelCreacionE_1.add(cbMes);

		cbAno = new JComboBox<>();
		cbAno.setBounds(150, 219, 80, 22);
		panelCreacionE_1.add(cbAno);
		
		
		for(int i=1;i<=31;i++) cbDia.addItem(String.valueOf(i));
		for(int i=1;i<=12;i++) cbMes.addItem(String.valueOf(i));
		for(int i=2025;i<=2040;i++) cbAno.addItem(String.valueOf(i));
		
		JLabel pedirHoraE = new JLabel("Ingrese la hora del evento");
		pedirHoraE.setForeground(Color.LIGHT_GRAY);
		pedirHoraE.setFont(new Font("Elephant", Font.PLAIN, 15));
		pedirHoraE.setBounds(10, 250, 225, 14);
		panelCreacionE_1.add(pedirHoraE);
		
		
		cbHora = new JComboBox<>();
		cbHora.setBounds(10, 275, 60, 22);
		panelCreacionE_1.add(cbHora);

		cbMinuto = new JComboBox<>();
		cbMinuto.setBounds(80, 275, 60, 22);
		panelCreacionE_1.add(cbMinuto);

		
		for(int i=0;i<=23;i++) cbHora.addItem(String.format("%02d", i));
		for(int i=0;i<=59;i++) cbMinuto.addItem(String.format("%02d", i));
		
		
		JLabel pedirTipo = new JLabel("Ingrese el tipo de evento");
		pedirTipo.setForeground(Color.LIGHT_GRAY);
		pedirTipo.setFont(new Font("Elephant", Font.PLAIN, 15));
		pedirTipo.setBounds(10, 306, 225, 14);
		panelCreacionE_1.add(pedirTipo);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 331, 740, 20);
		panelCreacionE_1.add(textField_1);
		
		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnSiguiente.setBounds(329, 454, 89, 23);
		panelCreacionE_1.add(btnSiguiente);
		
		btnSiguiente.addActionListener(e -> {


		    String nombre = txtNombre.getText().trim();
		    String tipo = textField_1.getText().trim();

		    int dia = Integer.parseInt(cbDia.getSelectedItem().toString());
		    int mes = Integer.parseInt(cbMes.getSelectedItem().toString());
		    int ano = Integer.parseInt(cbAno.getSelectedItem().toString());

		    int hora = Integer.parseInt(cbHora.getSelectedItem().toString());
		    int minuto = Integer.parseInt(cbMinuto.getSelectedItem().toString());

		    LocalDate fecha = LocalDate.of(ano, mes, dia);
		    LocalTime horaEv = LocalTime.of(hora, minuto);

		   
		    nombreE_1 = nombre;
		    fechaE_1 = fecha;
		    horaE_1 = horaEv;
		    tipoE_1 = tipo;

		    mostrarPanel(panelCreacionE_2);
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnVolver.setBounds(10, 11, 89, 23);
		panelCreacionE_1.add(btnVolver);
		
		btnVolver.addActionListener(e -> mostrarPanel(panelOrganizarE));
		
	}
	
	
	private void crearPanelCreacionE_2() {
		panelCreacionE_2 = new JPanel();
		panelCreacionE_2.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelCreacionE_2, "name_89447288242900");
		panelCreacionE_2.setLayout(null);
		
		JLabel tituloMenu = new JLabel("BOLETA MASTER");
		tituloMenu.setForeground(Color.LIGHT_GRAY);
		tituloMenu.setFont(new Font("Elephant", Font.PLAIN, 30));
		tituloMenu.setBounds(222, 11, 310, 37);
		panelCreacionE_2.add(tituloMenu);
		
		JLabel lblCrearEvento = new JLabel("Crear Evento");
		lblCrearEvento.setForeground(Color.LIGHT_GRAY);
		lblCrearEvento.setFont(new Font("Elephant", Font.PLAIN, 20));
		lblCrearEvento.setBounds(308, 57, 134, 26);
		panelCreacionE_2.add(lblCrearEvento);
		
		JLabel selectV = new JLabel("Seleccione un venue");
		selectV.setForeground(new Color(192, 192, 192));
		selectV.setFont(new Font("Elephant", Font.PLAIN, 15));
		selectV.setBounds(10, 135, 152, 19);
		panelCreacionE_2.add(selectV);
		
		JComboBox<String> cbVenue = new JComboBox<>();
		cbVenue.setBounds(13, 165, 733, 22);
		panelCreacionE_2.add(cbVenue);
		
		
		panelCreacionE_2.addComponentListener(new java.awt.event.ComponentAdapter() {
	     
	        public void componentShown(java.awt.event.ComponentEvent evt) {
	            cbVenue.removeAllItems();

	            
	            ArrayList<modelo.Venue> venues = new ArrayList<>(exec.Consola.adminGUI.getVenues());

	            for (modelo.Venue v : venues) {

	               
	                if (v.getAprobado() && fechaE_1 != null && v.consultarDisponibilidad(fechaE_1)) {

	                    String texto = v.getNombreV() +
	                                   " | Capacidad: " + v.getCapacidad() +
	                                   " | Tipo: " + v.getTipoV() +
	                                   " | Ubicación: " + v.getUbicacion();

	                    cbVenue.addItem(texto);
	                }
	            }

	            if (cbVenue.getItemCount() == 0) {
	                cbVenue.addItem("No hay venues disponibles para esta fecha");
	            }
	        }
	    });
		
		JButton btnTerminar = new JButton("Terminar");
		   btnTerminar.setFont(new Font("Elephant", Font.PLAIN, 11));
		   btnTerminar.setBounds(326, 294, 87, 23);
		   panelCreacionE_2.add(btnTerminar);

		   btnTerminar.addActionListener(e -> {
			   if (cbVenue.getSelectedItem() == null ||
				       cbVenue.getSelectedItem().toString().startsWith("No hay")) {

				        JOptionPane.showMessageDialog(this,
				            "No puedes continuar porque no hay venues disponibles.",
				            "Error",
				            JOptionPane.ERROR_MESSAGE);
				        return;
				    }

			
				    String seleccionado = cbVenue.getSelectedItem().toString();
				    venueSeleccionado = null;

				    for (modelo.Venue v : exec.Consola.adminGUI.getVenues()) {
				        if (seleccionado.contains(v.getNombreV())) {
				            venueSeleccionado = v;
				            break;
				        }
				    }

				    if (venueSeleccionado == null) {
				        JOptionPane.showMessageDialog(this,
				            "No se pudo identificar el venue seleccionado.",
				            "Error",
				            JOptionPane.ERROR_MESSAGE);
				        return;
				    }

				    int idE = new Random().nextInt(100000);
				    modelo.Evento nuevoEvento = organizador.crearEvento(idE, nombreE_1, fechaE_1, horaE_1, seleccionado, venueSeleccionado);
				        
				  
				    exec.Consola.eventosGUI.add(nuevoEvento);

				  
				    venueSeleccionado.addFechaOcupada(fechaE_1);

				 
				    JOptionPane.showMessageDialog(this,
				        "El evento se ha creado exitosamente.\n\n" +
				        "Nombre: " + nombreE_1 + "\n" +
				        "Tipo: " + tipoE_1 + "\n" +
				        "Fecha: " + fechaE_1 + " " + horaE_1 + "\n" +
				        "Venue: " + venueSeleccionado.getNombreV() + "\n" +
				        "Organizador: " + organizador.getLogin()
				    );

				    
				    mostrarPanel(panelMenu);
		    });

	
		    JButton btnVolver = new JButton("Volver");
		    btnVolver.setFont(new Font("Elephant", Font.PLAIN, 11));
		    btnVolver.setBounds(10, 11, 69, 23);
		    panelCreacionE_2.add(btnVolver);

		    btnVolver.addActionListener(e -> mostrarPanel(panelCreacionE_1));

	}
	
	
	
	private void crearPanelAsignarL() {
		panelAsignarL = new JPanel();
		panelAsignarL.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelAsignarL, "name_91472770330100");
		panelAsignarL.setLayout(null);
		
		JLabel tituloMenu = new JLabel("BOLETA MASTER");
		tituloMenu.setForeground(Color.LIGHT_GRAY);
		tituloMenu.setFont(new Font("Elephant", Font.PLAIN, 30));
		tituloMenu.setBounds(222, 11, 310, 37);
		panelAsignarL.add(tituloMenu);
		
		JLabel subtituloAsignar = new JLabel("Asignar Localidad");
		subtituloAsignar.setForeground(Color.LIGHT_GRAY);
		subtituloAsignar.setFont(new Font("Elephant", Font.PLAIN, 20));
		subtituloAsignar.setBounds(281, 57, 183, 26);
		panelAsignarL.add(subtituloAsignar);
		
		JLabel selectE = new JLabel("Seleccione un evento");
		selectE.setForeground(new Color(192, 192, 192));
		selectE.setFont(new Font("Elephant", Font.PLAIN, 15));
		selectE.setBounds(10, 121, 157, 14);
		panelAsignarL.add(selectE);
		
		cbEventos = new JComboBox();
		cbEventos.setBounds(10, 146, 740, 22);
		panelAsignarL.add(cbEventos);
		
		 panelAsignarL.addComponentListener(new java.awt.event.ComponentAdapter() {
		        public void componentShown(java.awt.event.ComponentEvent evt) {
		            cbEventos.removeAllItems();

		            ArrayList<modelo.Evento> eventos = new ArrayList<>();

		            for (modelo.Evento e : exec.Consola.eventosGUI) {
		                if (e.getOrganizador().equals(organizador)) {
		                    eventos.add(e);
		                    cbEventos.addItem(
		                        e.getNombreE() + " | Fecha: " + e.getFecha() + " | Venue: " + e.getVenue().getNombreV()
		                    );
		                }
		            }

		            if (cbEventos.getItemCount() == 0) {
		                cbEventos.addItem("No tienes eventos creados");
		            }
		        }
		    });
		 
		 cbEventos.addActionListener(e -> {
		        if (cbEventos.getSelectedItem() == null) return;
		        String seleccionado = cbEventos.getSelectedItem().toString();

		        if (seleccionado.startsWith("No tienes")) {
		            eventoSeleccionadoLocalidad = null;
		            return;
		        }

		        for (modelo.Evento ev : exec.Consola.eventosGUI) {
		            if (ev.getOrganizador().equals(organizador)) {

		                String texto = ev.getNombreE() + " | Fecha: " + ev.getFecha() +
		                               " | Venue: " + ev.getVenue().getNombreV();

		                if (seleccionado.equals(texto)) {
		                    eventoSeleccionadoLocalidad = ev;
		                    break;
		                }
		            }
		        }
		    });
		 
		JLabel nombreL = new JLabel("Ingrese el nombre de la localidad");
		nombreL.setForeground(Color.LIGHT_GRAY);
		nombreL.setFont(new Font("Elephant", Font.PLAIN, 15));
		nombreL.setBounds(10, 179, 247, 26);
		panelAsignarL.add(nombreL);
			
		txtNombreL = new JTextField();
		txtNombreL.setBounds(10, 204, 740, 20);
		panelAsignarL.add(txtNombreL);
		txtNombreL.setColumns(10);
			
		JLabel precioBL = new JLabel("Ingrese el precio base de los tiquetes");
		precioBL.setForeground(Color.LIGHT_GRAY);
		precioBL.setFont(new Font("Elephant", Font.PLAIN, 15));
		precioBL.setBounds(10, 240, 271, 26);
		panelAsignarL.add(precioBL);
			
		txtPrecioBL = new JTextField();
		txtPrecioBL.setColumns(10);
		txtPrecioBL.setBounds(10, 265, 740, 20);
		panelAsignarL.add(txtPrecioBL);
		
		JLabel capacidadL = new JLabel("Ingrese la capacidad de la localidad");
		capacidadL.setForeground(Color.LIGHT_GRAY);
		capacidadL.setFont(new Font("Elephant", Font.PLAIN, 15));
		capacidadL.setBounds(10, 296, 271, 26);
		panelAsignarL.add(capacidadL);
		
		txtCapacidadL = new JTextField();
		txtCapacidadL.setColumns(10);
		txtCapacidadL.setBounds(10, 323, 740, 20);
		panelAsignarL.add(txtCapacidadL);
			
		JLabel lbNumerada = new JLabel("¿Es numerada?");
		lbNumerada.setForeground(Color.LIGHT_GRAY);
		lbNumerada.setFont(new Font("Elephant", Font.PLAIN, 15));
		lbNumerada.setBounds(10, 354, 119, 26);
		panelAsignarL.add(lbNumerada);
			
		JLabel lbPaquetes = new JLabel("¿Crear paquetes?");
		lbPaquetes.setForeground(Color.LIGHT_GRAY);
		lbPaquetes.setFont(new Font("Elephant", Font.PLAIN, 15));
		lbPaquetes.setBounds(165, 354, 133, 26);
		panelAsignarL.add(lbPaquetes);
			
		JComboBox<String> cbNumerada = new JComboBox<>(new String[]{"No", "Sí"});
		cbNumerada.setBounds(10, 383, 119, 22);
		panelAsignarL.add(cbNumerada);
			
		JComboBox<String> cbPaquetes = new JComboBox<>(new String[]{"No", "Sí"});
		cbPaquetes.setBounds(175, 383, 123, 22);
		panelAsignarL.add(cbPaquetes);
		
		txtCantidadPaquetes = new JTextField();
		txtCantidadPaquetes.setBounds(165, 449, 436, 20);
		panelAsignarL.add(txtCantidadPaquetes);
		txtCantidadPaquetes.setColumns(10);
		
		JLabel txtTiquetesPaquete = new JLabel("Ingrese el numero de tiquetes por paquete");
		txtTiquetesPaquete.setForeground(Color.LIGHT_GRAY);
		txtTiquetesPaquete.setFont(new Font("Elephant", Font.PLAIN, 15));
		txtTiquetesPaquete.setBounds(165, 416, 314, 26);
		panelAsignarL.add(txtTiquetesPaquete);
		
		JLabel txtTipoPaquete = new JLabel("Tipo de Paquete");
		txtTipoPaquete.setForeground(Color.LIGHT_GRAY);
		txtTipoPaquete.setFont(new Font("Elephant", Font.PLAIN, 15));
		txtTipoPaquete.setBounds(10, 447, 133, 26);
		panelAsignarL.add(txtTipoPaquete);
		
		JComboBox<String> cbTipoPaquete = new JComboBox<>(new String[]{"Normal", "Deluxe"});
		cbTipoPaquete.setBounds(10, 484, 119, 22);
		panelAsignarL.add(cbTipoPaquete);
		
		JLabel lblIngreseElNumero = new JLabel("Ingrese el numero de paquetes que  desea crear");
		lblIngreseElNumero.setForeground(Color.LIGHT_GRAY);
		lblIngreseElNumero.setFont(new Font("Elephant", Font.PLAIN, 15));
		lblIngreseElNumero.setBounds(165, 480, 360, 26);
		panelAsignarL.add(lblIngreseElNumero);
		
		txtNumPaquetes = new JTextField();
		txtNumPaquetes.setColumns(10);
		txtNumPaquetes.setBounds(165, 517, 436, 20);
		panelAsignarL.add(txtNumPaquetes);
		
		
		JButton btnAsignarL = new JButton("Asignar Localidad");
		btnAsignarL.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnAsignarL.setBounds(565, 383, 142, 23);
		panelAsignarL.add(btnAsignarL);
		
		btnAsignarL.addActionListener(e -> {

	        if (eventoSeleccionadoLocalidad == null) {
	            JOptionPane.showMessageDialog(this, "Error interno: no hay evento seleccionado.");
	            return;
	        }

	        try {
	            String nombre = txtNombreL.getText().trim();
	            double precio = Double.parseDouble(txtPrecioBL.getText().trim());
	            int capacidad = Integer.parseInt(txtCapacidadL.getText().trim());

	            modelo.Venue venue = eventoSeleccionadoLocalidad.getVenue();

	            int capacidadOcupada = eventoSeleccionadoLocalidad.capacidadOcupada();
	            int totalVenue = venue.getCapacidad();

	            if (capacidad <= 0 || capacidad + capacidadOcupada > totalVenue) {
	                JOptionPane.showMessageDialog(this, "La capacidad excede el límite del venue.");
	                return;
	            }

	            boolean numerada = cbNumerada.getSelectedItem().toString().equals("Sí");

	            int idL = new Random().nextInt(10000);
	            modelo.Localidad nueva = new modelo.Localidad(idL, nombre, precio, capacidad);
	            nueva.setNumerada(numerada);

	            for (int i = 0; i < capacidad; i++) {
	                int idT = new Random().nextInt(10000);
	                Tiquetes.TiqueteSimple t = new Tiquetes.TiqueteSimple(
	                    idT, true, "ADMIN", Tiquetes.estadoTiquete.DISPONIBLE,
	                    "SIMPLE", eventoSeleccionadoLocalidad, nueva
	                );
	                nueva.addTiquete(t);
	            }

	            if (cbPaquetes.getSelectedItem().toString().equals("Sí")) {

	                int tipo = cbTipoPaquete.getSelectedItem().toString().equals("Deluxe") ? 2 : 1;
	                int cantidad = Integer.parseInt(txtCantidadPaquetes.getText().trim());
	                int numP = Integer.parseInt(txtNumPaquetes.getText().trim());	

	                for (int j = 0; j < numP; j++) {
	                    int idPack = new Random().nextInt(10000);

	                    Tiquetes.TiqueteMultiple paquete;
	                    if (tipo == 2) {
	                        paquete = new Tiquetes.PaqueteDeluxe(idPack, "ADMIN");
	                    } else {
	                        paquete = new Tiquetes.TiqueteMultiple(idPack, true, "ADMIN",
	                                Tiquetes.estadoTiquete.DISPONIBLE, "MULTIPLE");
	                    }

	                    double precioTotal = 0;
	                    int agregados = 0;

	                    for (Tiquetes.Tiquete t : nueva.getTiquetes()) {
	                        if (t instanceof Tiquetes.TiqueteSimple &&
	                            t.getStatus() == Tiquetes.estadoTiquete.DISPONIBLE &&
	                            agregados < cantidad) {

	                            paquete.addEntrada((Tiquetes.TiqueteSimple) t);
	                            precioTotal += nueva.getPrecioBase();
	                            agregados++;
	                        }
	                    }

	                    paquete.setPrecioPaquete(precioTotal);
	                    nueva.addTiquete(paquete);
	                }
	            }

	            eventoSeleccionadoLocalidad.addLocalidad(nueva);

	            JOptionPane.showMessageDialog(this,
	                "Localidad '" + nombre + "' creada correctamente.");

	            mostrarPanel(panelMenu);

	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Datos inválidos.");
	        }
	    });
		
		
		
	}
	
	
	private void mostrarPanel(JPanel panelMostrado) {
		for(JPanel p : paneles) {
			p.setVisible(false);
		}
		panelMostrado.setVisible(true);
		panelMostrado.repaint();
	}
}
