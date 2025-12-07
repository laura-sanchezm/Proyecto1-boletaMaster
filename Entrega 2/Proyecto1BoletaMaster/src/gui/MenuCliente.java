package gui;


import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.CardLayout;

public class MenuCliente extends JFrame {

	private modelo.Cliente cliente;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<JPanel> paneles = new ArrayList<>();
	
	private JLayeredPane layeredPane;
	private JPanel panelMenu;
	private JPanel panelRecargarSaldo;
	private JPanel panelCompraTiquete;
	private JPanel panelTiquetesComprados;
	private JPanel panelSolicitarDevolucion;
	private JPanel panelConsultarDevoluciones;
	private JPanel panelImprimirTiquete;
	private JLabel lblSaldo;
	
	// Modelo de tabla como variable de instancia
	private DefaultTableModel modeloTablaTiquetes;
	private JTable tablaDevoluciones;
	private DefaultTableModel modeloDevoluciones;


	public MenuCliente(modelo.Cliente cliente) {
		this.cliente = cliente;
		
		configurarVentana();
		inicializarLayeredPanel();
		crearPanelMenu();
		crearPanelRecargarSaldo();
		crearPanelComprarTiquete();
		crearPanelTiquetesComprados();
		crearPanelSolicitarDevolucion();
		crearPanelConsultarDevoluciones();
		crearPanelImprimirTiquete();
		
		paneles.add(panelMenu);
		paneles.add(panelRecargarSaldo);
		paneles.add(panelCompraTiquete);
		paneles.add(panelTiquetesComprados);
		paneles.add(panelSolicitarDevolucion);
		paneles.add(panelConsultarDevoluciones);		
		paneles.add(panelImprimirTiquete);
		
		mostrarPanel(panelMenu);
		
	}
	
	private void configurarVentana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(10, 62, 114));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void inicializarLayeredPanel() {
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 884, 611);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
	}

	private void crearPanelMenu() {
		panelMenu = new JPanel();
		panelMenu.setBackground(new Color(10, 62, 114));
		panelMenu.setLayout(null);
		layeredPane.add(panelMenu, "name_71840167171800");

		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(273, 6, 310, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panelMenu.add(titulo);

		JLabel sub = new JLabel("Menu Cliente");
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(360, 59, 180, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelMenu.add(sub);

		JLabel labelSaldoTxt = new JLabel("Saldo:");
		labelSaldoTxt.setFont(new Font("Elephant", Font.BOLD, 15));
		labelSaldoTxt.setForeground(Color.LIGHT_GRAY);
		labelSaldoTxt.setBounds(10, 114, 61, 14);
		panelMenu.add(labelSaldoTxt);

		lblSaldo = new JLabel(String.valueOf(cliente.consultarSaldo()));
		lblSaldo.setFont(new Font("Elephant", Font.PLAIN, 15));
		lblSaldo.setForeground(Color.LIGHT_GRAY);
		lblSaldo.setBounds(70, 114, 300, 14);
		panelMenu.add(lblSaldo);

		JButton btnRecargar = new JButton("Recargar Saldo");
		btnRecargar.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnRecargar.setBounds(10, 166, 864, 30);
		btnRecargar.addActionListener(e -> mostrarPanel(panelRecargarSaldo));
		panelMenu.add(btnRecargar);

		JButton btnComprar = new JButton("Comprar Tiquete");
		btnComprar.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnComprar.setBounds(10, 207, 864, 30);
		btnComprar.addActionListener(e -> mostrarPanel(panelCompraTiquete));
		panelMenu.add(btnComprar);

		// NUEVO: Botón para consultar tiquetes
		JButton btnTiquetes = new JButton("Consultar Tiquetes Comprados");
		btnTiquetes.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnTiquetes.setBounds(10, 248, 864, 30);
		btnTiquetes.addActionListener(e -> {
			actualizarTablaTiquetes();
			mostrarPanel(panelTiquetesComprados);
		});
		panelMenu.add(btnTiquetes);

		// NUEVO: Botón para solicitar devolución
		JButton btnSolicitarDev = new JButton("Solicitar Devolución");
		btnSolicitarDev.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnSolicitarDev.setBounds(10, 289, 864, 30);
		btnSolicitarDev.addActionListener(e -> mostrarPanel(panelSolicitarDevolucion));
		panelMenu.add(btnSolicitarDev);
		
		JButton btnConsultarSolDev = new JButton("Consultar Solicitudes de Devolución");
		btnConsultarSolDev.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnConsultarSolDev.setBounds(10, 335, 864, 30);
		panelMenu.add(btnConsultarSolDev);
		
		btnConsultarSolDev.addActionListener(e -> mostrarPanel(panelConsultarDevoluciones));

		// NUEVO: Botón para imprimir tiquete
		JButton btnImprimir = new JButton("Imprimir Tiquete");
		btnImprimir.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnImprimir.setBounds(10, 381, 864, 30);
		btnImprimir.addActionListener(e -> mostrarPanel(panelImprimirTiquete));
		panelMenu.add(btnImprimir);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnSalir.setBounds(397, 540, 89, 23);
		btnSalir.addActionListener(e -> salir());
		panelMenu.add(btnSalir);
	}

	private void crearPanelRecargarSaldo() {
		panelRecargarSaldo = new JPanel();
		panelRecargarSaldo.setLayout(null);
		panelRecargarSaldo.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelRecargarSaldo, "panelRecargarSaldo");

		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 380, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panelRecargarSaldo.add(titulo);
		
		JLabel sub = new JLabel("Recargar Saldo");
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(340, 59, 200, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelRecargarSaldo.add(sub);

		JButton btnRecargar = new JButton("Ingresar monto");
		btnRecargar.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnRecargar.setBounds(200, 250, 484, 79);
		btnRecargar.addActionListener(e -> recargarSaldo());
		panelRecargarSaldo.add(btnRecargar);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(10, 10, 100, 23);
		btnVolver.addActionListener(e -> {
			lblSaldo.setText(String.valueOf(cliente.consultarSaldo()));
			mostrarPanel(panelMenu);
		});
		panelRecargarSaldo.add(btnVolver);
	}

	private void crearPanelComprarTiquete() {
		panelCompraTiquete = new JPanel();
		panelCompraTiquete.setLayout(null);
		panelCompraTiquete.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelCompraTiquete, "panelCompraTiquete");
		
		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 380, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panelCompraTiquete.add(titulo);
		    
		JLabel sub = new JLabel("Comprar Tiquetes");
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(320, 59, 240, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelCompraTiquete.add(sub);
		
		JLabel metodoP = new JLabel("Método de pago");
		metodoP.setForeground(new Color(192, 192, 192));
		metodoP.setFont(new Font("Elephant", Font.PLAIN, 15));
		metodoP.setBounds(10, 133, 200, 19);
		panelCompraTiquete.add(metodoP);
		
		JComboBox<String> cbMetodoP = new JComboBox<>();
		cbMetodoP.addItem("Saldo");
		cbMetodoP.addItem("Pasarela Externa");
		cbMetodoP.setBounds(10, 156, 201, 22);
		panelCompraTiquete.add(cbMetodoP);
		
		JLabel lbEvento = new JLabel("Seleccione el evento");
		lbEvento.setForeground(Color.LIGHT_GRAY);
		lbEvento.setFont(new Font("Elephant", Font.PLAIN, 15));
		lbEvento.setBounds(10, 189, 302, 19);
		panelCompraTiquete.add(lbEvento);
		
		JComboBox<String> cbEventos = new JComboBox<String>();
		cbEventos.setBounds(10, 212, 864, 22);
		panelCompraTiquete.add(cbEventos);
		
		for (modelo.Evento ev : exec.Consola.eventosGUI) {
			String str = ev.getIdE() + " - " + ev.getNombreE() +
						 " | " + ev.getFecha() + " " + ev.getHora() +
						 " | " + ev.getVenue().getNombreV();
			cbEventos.addItem(str);
		}
		
		JLabel lbLocalidad = new JLabel("Seleccione la localidad");
		lbLocalidad.setForeground(Color.LIGHT_GRAY);
		lbLocalidad.setFont(new Font("Elephant", Font.PLAIN, 15));
		lbLocalidad.setBounds(10, 245, 220, 19);
		panelCompraTiquete.add(lbLocalidad);
		
		JComboBox<String> cbLocalidades = new JComboBox<String>();
		cbLocalidades.setBounds(10, 267, 864, 22);
		panelCompraTiquete.add(cbLocalidades);
		
		cbEventos.addActionListener(ev -> {
			cbLocalidades.removeAllItems();
			if (cbEventos.getSelectedItem() == null) return;

			int idE = Integer.parseInt(cbEventos.getSelectedItem().toString().split(" - ")[0]);
			modelo.Evento eventoSel = null;

			for (modelo.Evento e2 : exec.Consola.eventosGUI)
				if (e2.getIdE() == idE) eventoSel = e2;

			if (eventoSel == null) return;

			for (modelo.Localidad l : eventoSel.getLocalidades()) {
				if (l.puedeVender()) {
					cbLocalidades.addItem(l.getIdL() + " - " + l.getNombreL() +
						" | Precio base: " + l.getPrecioBase());
				}
			}
		});
		
		JLabel lbTipoC = new JLabel("Tipo de compra");
		lbTipoC.setForeground(Color.LIGHT_GRAY);
		lbTipoC.setFont(new Font("Elephant", Font.PLAIN, 15));
		lbTipoC.setBounds(10, 300, 180, 19);
		panelCompraTiquete.add(lbTipoC);
		
		JComboBox<String> cbTipoC = new JComboBox<String>();
		cbTipoC.setBounds(10, 321, 425, 22);
		cbTipoC.addItem("Seleccione...");
		cbTipoC.addItem("Individual");
		cbTipoC.addItem("Paquete");
		panelCompraTiquete.add(cbTipoC);
		
		JLabel lblPaquetes = new JLabel("Paquetes disponibles:");
		lblPaquetes.setForeground(Color.LIGHT_GRAY);
		lblPaquetes.setFont(new Font("Elephant", Font.PLAIN, 15));
		lblPaquetes.setBounds(10, 345, 400, 20);
		lblPaquetes.setVisible(false);
		panelCompraTiquete.add(lblPaquetes);

		JComboBox<String> cbPaquetes = new JComboBox<>();
		cbPaquetes.setBounds(10, 370, 864, 25);
		cbPaquetes.setVisible(false);
		panelCompraTiquete.add(cbPaquetes);

		JLabel lblCantidad = new JLabel("Cantidad de tiquetes:");
		lblCantidad.setForeground(Color.LIGHT_GRAY);
		lblCantidad.setBounds(10, 345, 400, 20);
		lblCantidad.setFont(new Font("Elephant", Font.PLAIN, 15));
		lblCantidad.setVisible(false);
		panelCompraTiquete.add(lblCantidad);

		JTextField txtCantidad = new JTextField();
		txtCantidad.setBounds(10, 370, 425, 25);
		txtCantidad.setVisible(false);
		panelCompraTiquete.add(txtCantidad);

		cbTipoC.addActionListener(ev -> {
			lblPaquetes.setVisible(false);
			cbPaquetes.setVisible(false);
			lblCantidad.setVisible(false);
			txtCantidad.setVisible(false);

			if (cbTipoC.getSelectedItem() == null) return;
			String opcion = cbTipoC.getSelectedItem().toString();

			if (opcion.equals("Paquete")) {
				lblPaquetes.setVisible(true);
				cbPaquetes.setVisible(true);

				cbPaquetes.removeAllItems();

				if (cbLocalidades.getSelectedItem() == null) return;

				int idE = Integer.parseInt(cbEventos.getSelectedItem().toString().split(" - ")[0]);
				int idL = Integer.parseInt(cbLocalidades.getSelectedItem().toString().split(" - ")[0]);

				modelo.Evento eventoSel = null;
				modelo.Localidad locSel = null;

				for (modelo.Evento e : exec.Consola.eventosGUI)
					if (e.getIdE() == idE) eventoSel = e;

				for (modelo.Localidad l : eventoSel.getLocalidades())
					if (l.getIdL() == idL) locSel = l;

				for (Tiquetes.Tiquete t : locSel.getTiquetes()) {
					if (t instanceof Tiquetes.TiqueteMultiple tm &&
						t.getStatus() == Tiquetes.estadoTiquete.DISPONIBLE) {
						cbPaquetes.addItem("Paquete " + tm.getIdT() +
							" | Entradas: " + tm.getEntradas().size() +
							" | Precio: " + tm.getPrecioPaquete());
					}
				}

			} else if (opcion.equals("Individual")) {
				lblCantidad.setVisible(true);
				txtCantidad.setVisible(true);
			}
		});

		JButton btnComprar = new JButton("Comprar");
		btnComprar.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnComprar.setBounds(370, 540, 120, 30);
		panelCompraTiquete.add(btnComprar);

		btnComprar.addActionListener(e -> {

			if (cbEventos.getSelectedItem() == null ||
				cbLocalidades.getSelectedItem() == null ||
				cbTipoC.getSelectedIndex() == 0) {

				JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
				return;
			}

			pagos.metodoPago metodo = (cbMetodoP.getSelectedIndex() == 0)
					? pagos.metodoPago.SALDO
					: pagos.metodoPago.PASARELA_EXTERNA;

			int idE = Integer.parseInt(cbEventos.getSelectedItem().toString().split(" - ")[0]);
			modelo.Evento eventoSel = null;
			for (modelo.Evento e2 : exec.Consola.eventosGUI)
				if (e2.getIdE() == idE) eventoSel = e2;

			int idL = Integer.parseInt(cbLocalidades.getSelectedItem().toString().split(" - ")[0]);
			modelo.Localidad locSel = null;
			for (modelo.Localidad l : eventoSel.getLocalidades())
				if (l.getIdL() == idL) locSel = l;

			String tipo = cbTipoC.getSelectedItem().toString();

			try {
				if (tipo.equals("Paquete")) {

					if (cbPaquetes.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(this, "Debe seleccionar un paquete.");
						return;
					}

					int idPack = Integer.parseInt(cbPaquetes.getSelectedItem().toString().split(" ")[1]);

					Tiquetes.TiqueteMultiple pack = null;
					for (Tiquetes.Tiquete t : locSel.getTiquetes())
						if (t instanceof Tiquetes.TiqueteMultiple tm && tm.getIdT() == idPack)
							pack = tm;

					cliente.comprarTiqueteMultiple(eventoSel, pack, metodo, exec.Consola.adminGUI);
					JOptionPane.showMessageDialog(this, "Paquete comprado exitosamente.");

				} else {
					int cant = Integer.parseInt(txtCantidad.getText());
					cliente.comprarTiqueteSimple(eventoSel, locSel, cant, metodo, exec.Consola.adminGUI);
					JOptionPane.showMessageDialog(this, "Tiquetes comprados.");
				}

				lblSaldo.setText(String.valueOf(cliente.consultarSaldo()));
				mostrarPanel(panelMenu);

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error en la compra: " + ex.getMessage());
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(10, 10, 100, 23);
		btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
		panelCompraTiquete.add(btnVolver);
	}

	// NUEVO: Panel para consultar tiquetes comprados
	private void crearPanelTiquetesComprados() {
		panelTiquetesComprados = new JPanel();
		panelTiquetesComprados.setLayout(null);
		panelTiquetesComprados.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelTiquetesComprados, "panelTiquetesComprados");

		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 380, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panelTiquetesComprados.add(titulo);
		
		JLabel sub = new JLabel("Tiquetes Comprados");
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(310, 59, 270, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelTiquetesComprados.add(sub);

		String[] columnas = {"ID", "Tipo", "Evento", "Localidad", "Estado", "Asiento"};
		modeloTablaTiquetes = new DefaultTableModel(columnas, 0);
		JTable tablaTiquetes = new JTable(modeloTablaTiquetes);
		
		JScrollPane scroll = new JScrollPane(tablaTiquetes);
		scroll.setBounds(10, 100, 864, 400);
		panelTiquetesComprados.add(scroll);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(10, 10, 100, 23);
		btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
		panelTiquetesComprados.add(btnVolver);
	}

	// NUEVO: Panel para solicitar devolución
	private void crearPanelSolicitarDevolucion() {
		panelSolicitarDevolucion = new JPanel();
		panelSolicitarDevolucion.setLayout(null);
		panelSolicitarDevolucion.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelSolicitarDevolucion, "panelSolicitarDevolucion");

		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 380, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panelSolicitarDevolucion.add(titulo);
		
		JLabel sub = new JLabel("Solicitar Devolución");
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(300, 59, 290, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelSolicitarDevolucion.add(sub);

		JLabel lblIdTiquete = new JLabel("ID del tiquete:");
		lblIdTiquete.setForeground(Color.LIGHT_GRAY);
		lblIdTiquete.setFont(new Font("Elephant", Font.PLAIN, 15));
		lblIdTiquete.setBounds(10, 150, 200, 25);
		panelSolicitarDevolucion.add(lblIdTiquete);

		JTextField txtIdTiquete = new JTextField();
		txtIdTiquete.setBounds(10, 180, 400, 25);
		panelSolicitarDevolucion.add(txtIdTiquete);

		JLabel lblMotivo = new JLabel("Motivo de la devolución:");
		lblMotivo.setForeground(Color.LIGHT_GRAY);
		lblMotivo.setFont(new Font("Elephant", Font.PLAIN, 15));
		lblMotivo.setBounds(10, 220, 300, 25);
		panelSolicitarDevolucion.add(lblMotivo);

		JTextArea txtMotivo = new JTextArea();
		txtMotivo.setLineWrap(true);
		txtMotivo.setWrapStyleWord(true);
		JScrollPane scrollMotivo = new JScrollPane(txtMotivo);
		scrollMotivo.setBounds(10, 250, 864, 100);
		panelSolicitarDevolucion.add(scrollMotivo);

		JLabel lblInfo = new JLabel("Nota: Las devoluciones con motivo 'hospital' o 'viaje' tienen mayor probabilidad de aprobación");
		lblInfo.setForeground(new Color(255, 200, 100));
		lblInfo.setFont(new Font("Elephant", Font.PLAIN, 11));
		lblInfo.setBounds(10, 360, 800, 20);
		panelSolicitarDevolucion.add(lblInfo);

		JButton btnSolicitar = new JButton("Solicitar");
		btnSolicitar.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnSolicitar.setBounds(377, 450, 120, 30);
		btnSolicitar.addActionListener(e -> {
			try {
				int idT = Integer.parseInt(txtIdTiquete.getText().trim());
				String motivo = txtMotivo.getText().trim();

				if (motivo.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Debe ingresar un motivo.");
					return;
				}

				cliente.solicitarDevolucion(idT, cliente.getLogin(), motivo);
				JOptionPane.showMessageDialog(this, "Solicitud registrada correctamente.\nEl administrador la revisará pronto.");
				txtIdTiquete.setText("");
				txtMotivo.setText("");
				mostrarPanel(panelMenu);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelSolicitarDevolucion.add(btnSolicitar);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(10, 10, 100, 23);
		btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
		panelSolicitarDevolucion.add(btnVolver);
	}
	
	
	
	
	private void crearPanelConsultarDevoluciones() {
		panelConsultarDevoluciones = new JPanel();
		panelConsultarDevoluciones.setLayout(null);
		panelConsultarDevoluciones.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelConsultarDevoluciones, "panelConsultarDevoluciones");
		
		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 310, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panelConsultarDevoluciones.add(titulo);
		
		JLabel sub = new JLabel("Consultar Estado Devoluciones");
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(250, 49, 314, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelConsultarDevoluciones.add(sub);
		
		String[] columnas = {"ID Tiquete", "Evento", "Localidad", "Estado", "Motivo"};
	    modeloDevoluciones = new DefaultTableModel(columnas, 0);
	    tablaDevoluciones = new JTable(modeloDevoluciones);
	    tablaDevoluciones.setRowHeight(25);

	    JScrollPane scroll = new JScrollPane(tablaDevoluciones);
	    scroll.setBounds(10, 100, 859, 495);
	    panelConsultarDevoluciones.add(scroll);

	    JButton btnVolver = new JButton("Volver");
	    btnVolver.setFont(new Font("Elephant", Font.PLAIN, 11));
	    btnVolver.setBounds(10, 10, 100, 23);
	    btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
	    panelConsultarDevoluciones.add(btnVolver);
		
		
	}

	// NUEVO: Panel para imprimir tiquete
	private void crearPanelImprimirTiquete() {
		panelImprimirTiquete = new JPanel();
		panelImprimirTiquete.setLayout(null);
		panelImprimirTiquete.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelImprimirTiquete, "panelImprimirTiquete");

		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 380, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panelImprimirTiquete.add(titulo);
		
		JLabel sub = new JLabel("Imprimir Tiquete");
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(330, 59, 240, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelImprimirTiquete.add(sub);

		JLabel lblIdTiquete = new JLabel("ID del tiquete a imprimir:");
		lblIdTiquete.setForeground(Color.LIGHT_GRAY);
		lblIdTiquete.setFont(new Font("Elephant", Font.PLAIN, 15));
		lblIdTiquete.setBounds(10, 150, 300, 25);
		panelImprimirTiquete.add(lblIdTiquete);

		JTextField txtIdTiquete = new JTextField();
		txtIdTiquete.setBounds(10, 180, 400, 25);
		panelImprimirTiquete.add(txtIdTiquete);

		JLabel lblInfo = new JLabel("<html>• El tiquete se generará con un código QR único<br>" +
									 "• Una vez impreso, NO podrá volver a imprimirse<br>" +
									 "• Tiquetes impresos NO pueden transferirse ni venderse</html>");
		lblInfo.setForeground(new Color(255, 200, 100));
		lblInfo.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblInfo.setBounds(10, 220, 600, 80);
		panelImprimirTiquete.add(lblInfo);

		JButton btnImprimir = new JButton("Generar e Imprimir");
		btnImprimir.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnImprimir.setBounds(320, 400, 250, 40);
		btnImprimir.addActionListener(e -> imprimirTiquete(txtIdTiquete.getText().trim()));
		panelImprimirTiquete.add(btnImprimir);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(10, 10, 100, 23);
		btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
		panelImprimirTiquete.add(btnVolver);
	}

	// NUEVO: Método para actualizar la tabla de tiquetes
	private void actualizarTablaTiquetes() {
		if (modeloTablaTiquetes == null) return;
		
		modeloTablaTiquetes.setRowCount(0);

		for (Tiquetes.Tiquete t : cliente.getTiquetes()) {
			if (t instanceof Tiquetes.TiqueteSimple simple) {
				String asiento = simple.isEnumerado() ? String.valueOf(simple.getNumAsiento()) : "N/A";
				modeloTablaTiquetes.addRow(new Object[]{
					simple.getIdT(),
					"Simple",
					simple.getEvento().getNombreE(),
					simple.getLocalidad().getNombreL(),
					simple.getStatus(),
					asiento
				});
			} else if (t instanceof Tiquetes.TiqueteMultiple multiple) {
				modeloTablaTiquetes.addRow(new Object[]{
					multiple.getIdT(),
					"Multiple (" + multiple.getEntradas().size() + " entradas)",
					"Varios eventos",
					"-",
					multiple.getStatus(),
					"-"
				});
			}
		}
	}

	// NUEVO: Método para imprimir tiquete con código QR
	private void imprimirTiquete(String idStr) {
		try {
			int idT = Integer.parseInt(idStr);
			
			// Buscar el tiquete
			Tiquetes.Tiquete tiquete = cliente.buscarTiquetePorId(idT);
			
			if (tiquete == null) {
				JOptionPane.showMessageDialog(this, "No se encontró un tiquete con ese ID.");
				return;
			}

			// Verificar que sea un tiquete simple
			if (!(tiquete instanceof Tiquetes.TiqueteSimple)) {
				JOptionPane.showMessageDialog(this, "Solo se pueden imprimir tiquetes simples individualmente.");
				return;
			}

			Tiquetes.TiqueteSimple simple = (Tiquetes.TiqueteSimple) tiquete;

			// Verificar que el tiquete esté comprado
			if (simple.getStatus() != Tiquetes.estadoTiquete.COMPRADO) {
				JOptionPane.showMessageDialog(this, "El tiquete debe estar en estado COMPRADO para imprimirse.");
				return;
			}

			// Crear ventana de impresión
			crearVentanaImpresion(simple);
			
			JOptionPane.showMessageDialog(this, "Tiquete generado exitosamente!");
			mostrarPanel(panelMenu);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al imprimir: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// NUEVO: Crear ventana de impresión del tiquete
	private void crearVentanaImpresion(Tiquetes.TiqueteSimple tiquete) {
		JFrame ventanaImpresion = new JFrame("Tiquete - " + tiquete.getIdT());
		ventanaImpresion.setSize(800, 600);
		ventanaImpresion.setLocationRelativeTo(this);
		
		JPanel panelTiquete = new JPanel();
		panelTiquete.setLayout(null);
		panelTiquete.setBackground(Color.WHITE);
		
		// Header con logo
		JPanel header = new JPanel();
		header.setBounds(0, 0, 800, 80);
		header.setBackground(new Color(10, 62, 114));
		header.setLayout(null);
		
		JLabel lblLogo = new JLabel("BM");
		lblLogo.setFont(new Font("Elephant", Font.BOLD, 40));
		lblLogo.setForeground(new Color(255, 200, 0));
		lblLogo.setBounds(20, 10, 100, 60);
		header.add(lblLogo);
		
		JLabel lblBoletaMaster = new JLabel("BoletaMaster");
		lblBoletaMaster.setFont(new Font("Elephant", Font.PLAIN, 24));
		lblBoletaMaster.setForeground(Color.WHITE);
		lblBoletaMaster.setBounds(120, 20, 250, 40);
		header.add(lblBoletaMaster);
		
		panelTiquete.add(header);
		
		// Información del evento
		JLabel lblEvento = new JLabel(tiquete.getEvento().getNombreE());
		lblEvento.setFont(new Font("Arial", Font.BOLD, 28));
		lblEvento.setForeground(new Color(10, 62, 114));
		lblEvento.setBounds(20, 100, 550, 40);
		panelTiquete.add(lblEvento);
		
		// Información del tiquete
		JLabel lblNo = new JLabel("No. " + tiquete.getIdT());
		lblNo.setFont(new Font("Arial", Font.BOLD, 20));
		lblNo.setBounds(20, 150, 200, 30);
		panelTiquete.add(lblNo);
		
		JLabel lblVenue = new JLabel("Venue: " + tiquete.getEvento().getVenue().getNombreV());
		lblVenue.setFont(new Font("Arial", Font.PLAIN, 16));
		lblVenue.setBounds(20, 190, 400, 25);
		panelTiquete.add(lblVenue);
		
		JLabel lblLocalidad = new JLabel("Localidad: " + tiquete.getLocalidad().getNombreL());
		lblLocalidad.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLocalidad.setBounds(20, 220, 400, 25);
		panelTiquete.add(lblLocalidad);
		
		JLabel lblFecha = new JLabel("Fecha Expedición: " + tiquete.getEvento().getFecha());
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFecha.setBounds(20, 250, 400, 25);
		panelTiquete.add(lblFecha);
		
		JLabel lblFechaImpresion = new JLabel("Fecha Impresión: " + java.time.LocalDate.now());
		lblFechaImpresion.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFechaImpresion.setBounds(20, 280, 400, 25);
		panelTiquete.add(lblFechaImpresion);
		
		JLabel lblValor = new JLabel("Valor: $" + tiquete.getLocalidad().getPrecioBase());
		lblValor.setFont(new Font("Arial", Font.BOLD, 18));
		lblValor.setForeground(new Color(0, 150, 0));
		lblValor.setBounds(20, 320, 400, 30);
		panelTiquete.add(lblValor);
		
		if (tiquete.isEnumerado()) {
			JLabel lblAsiento = new JLabel("Asiento: " + tiquete.getNumAsiento());
			lblAsiento.setFont(new Font("Arial", Font.BOLD, 20));
			lblAsiento.setForeground(new Color(200, 0, 0));
			lblAsiento.setBounds(20, 360, 400, 30);
			panelTiquete.add(lblAsiento);
		}
		
		// Generar código QR
		String datosQR = "Evento:" + tiquete.getEvento().getNombreE() + 
						 "|ID:" + tiquete.getIdT() +
						 "|F.Evento:" + tiquete.getEvento().getFecha() +
						 "|F.Impresion:" + java.time.LocalDate.now();
		
		JLabel lblQR = new JLabel("QR");
		lblQR.setBounds(550, 150, 200, 200);
		lblQR.setHorizontalAlignment(SwingConstants.CENTER);
		lblQR.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		// Aquí se generaría el QR real con una librería
		// Por ahora mostramos un placeholder
		lblQR.setText("<html><div style='text-align:center'>" +
					  "CÓDIGO QR<br><br>" +
					  "<small>" + datosQR + "</small>" +
					  "</div></html>");
		lblQR.setVerticalAlignment(SwingConstants.CENTER);
		panelTiquete.add(lblQR);
		
		// Footer
		JLabel lblFooter = new JLabel("Este tiquete es válido solo para una entrada. No transferible una vez impreso.");
		lblFooter.setFont(new Font("Arial", Font.ITALIC, 12));
		lblFooter.setForeground(Color.GRAY);
		lblFooter.setBounds(20, 500, 700, 20);
		panelTiquete.add(lblFooter);
		
		ventanaImpresion.getContentPane().add(panelTiquete);
		ventanaImpresion.setVisible(true);
	}

	private void recargarSaldo() {
		String montoStr = JOptionPane.showInputDialog(this, "Monto a recargar:");
		if (montoStr == null) return;

		try {
			double monto = Double.parseDouble(montoStr);
			cliente.recargarSaldo(monto);
			lblSaldo.setText(String.valueOf(cliente.consultarSaldo()));
			JOptionPane.showMessageDialog(this, "Saldo recargado.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Monto inválido.");
		}
	}

	private void salir() {
		this.dispose();
		new Login().setVisible(true);
	}
	    
	private void mostrarPanel(JPanel panelMostrado) {
		for(JPanel p : paneles) {
			p.setVisible(false);
		}
		panelMostrado.setVisible(true);
		panelMostrado.repaint();
	}
}