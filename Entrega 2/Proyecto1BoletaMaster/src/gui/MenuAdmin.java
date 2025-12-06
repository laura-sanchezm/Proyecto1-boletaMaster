package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import Tiquetes.*;
import Persistencia.*;

public class MenuAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private modelo.Administrador administrador;
	private ArrayList<JPanel> paneles = new ArrayList<>();
	
	private JLayeredPane layeredPane;
	private JPanel panelMenu;
	private JPanel panelRegistroV;
	private JPanel panelAprobarV;
	private JPanel panelFijarCargos;
	private JPanel panelVerDevoluciones;
	private JPanel panelAprobarDevolucion;
	private JPanel panelAprobarCancelacion;
	private JPanel panelConsultasFinancieras;
	
	// Modelos de tablas
	private DefaultTableModel modeloTablaVenues;
	private DefaultTableModel modeloTablaDevoluciones;
	private DefaultTableModel modeloTablaCancelaciones;

	public MenuAdmin(modelo.Administrador administrador) {
		this.administrador = administrador;
		
		configurarVentana();
		inicializarLayeredPanel();
		
		crearPanelMenu();
		crearPanelRegistroV();
		crearPanelAprobarV();
		crearPanelFijarCargos();
		crearPanelVerDevoluciones();
		crearPanelAprobarDevolucion();
		crearPanelAprobarCancelacion();
		crearPanelConsultasFinancieras();
		
		paneles.add(panelMenu);
		paneles.add(panelRegistroV);
		paneles.add(panelAprobarV);
		paneles.add(panelFijarCargos);
		paneles.add(panelVerDevoluciones);
		paneles.add(panelAprobarDevolucion);
		paneles.add(panelAprobarCancelacion);
		paneles.add(panelConsultasFinancieras);
		
		mostrarPanel(panelMenu);
	}

	private void configurarVentana() {
		setTitle("BoletaMaster - Administrador");
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
	}

	private void mostrarPanel(JPanel panelMostrado) {
		for(JPanel p : paneles) {
			p.setVisible(false);
		}
		panelMostrado.setVisible(true);
		panelMostrado.setBounds(0, 0, 884, 611);
		panelMostrado.repaint();
	}

	// ==================== PANEL MENÚ ====================
	private void crearPanelMenu() {
		panelMenu = new JPanel();
		panelMenu.setBackground(new Color(10, 62, 114));
		panelMenu.setLayout(null);
		layeredPane.add(panelMenu, "panelMenu");
		
		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 380, 37);
		titulo.setForeground(Color.LIGHT_GRAY);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		panelMenu.add(titulo);
		
		JLabel subtitulo = new JLabel("Menú Administrador");
		subtitulo.setBounds(290, 53, 300, 26);
		subtitulo.setForeground(Color.LIGHT_GRAY);
		subtitulo.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelMenu.add(subtitulo);

		int y = 120;
		int espaciado = 40;

		JButton btnRegistrarV = crearBotonMenu("1. Registrar Venue", y);
		btnRegistrarV.addActionListener(e -> mostrarPanel(panelRegistroV));
		panelMenu.add(btnRegistrarV);
		y += espaciado;

		JButton btnAprobarV = crearBotonMenu("2. Aprobar Venue", y);
		btnAprobarV.addActionListener(e -> {
			cargarVenues();
			mostrarPanel(panelAprobarV);
		});
		panelMenu.add(btnAprobarV);
		y += espaciado;

		JButton btnFijarCargos = crearBotonMenu("3. Fijar Cargos Adicionales", y);
		btnFijarCargos.addActionListener(e -> mostrarPanel(panelFijarCargos));
		panelMenu.add(btnFijarCargos);
		y += espaciado;

		JButton btnVerDevoluciones = crearBotonMenu("4. Ver Solicitudes de Devolución", y);
		btnVerDevoluciones.addActionListener(e -> {
			cargarDevoluciones();
			mostrarPanel(panelVerDevoluciones);
		});
		panelMenu.add(btnVerDevoluciones);
		y += espaciado;

		JButton btnAprobarDevolucion = crearBotonMenu("5. Aprobar/Rechazar Devolución", y);
		btnAprobarDevolucion.addActionListener(e -> mostrarPanel(panelAprobarDevolucion));
		panelMenu.add(btnAprobarDevolucion);
		y += espaciado;

		JButton btnAprobarCancelacion = crearBotonMenu("6. Aprobar/Rechazar Cancelación de Evento", y);
		btnAprobarCancelacion.addActionListener(e -> {
			cargarCancelaciones();
			mostrarPanel(panelAprobarCancelacion);
		});
		panelMenu.add(btnAprobarCancelacion);
		y += espaciado;

		JButton btnConsultas = crearBotonMenu("7. Consultas Financieras", y);
		btnConsultas.addActionListener(e -> mostrarPanel(panelConsultasFinancieras));
		panelMenu.add(btnConsultas);
		y += espaciado;

		JButton btnGuardar = new JButton("💾 Guardar Datos");
		btnGuardar.setFont(new Font("Elephant", Font.PLAIN, 13));
		btnGuardar.setBackground(new Color(100, 200, 100));
		btnGuardar.setBounds(10, y, 864, 35);
		btnGuardar.addActionListener(e -> guardarDatos());
		panelMenu.add(btnGuardar);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnSalir.setBounds(397, 560, 89, 23);
		btnSalir.addActionListener(e -> salir());
		panelMenu.add(btnSalir);
	}

	private JButton crearBotonMenu(String texto, int y) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("Elephant", Font.PLAIN, 14));
		btn.setBackground(Color.WHITE);
		btn.setBounds(10, y, 864, 35);
		return btn;
	}

	// ==================== PANEL REGISTRAR VENUE ====================
	private void crearPanelRegistroV() {
		panelRegistroV = new JPanel();
		panelRegistroV.setBackground(new Color(10, 62, 114));
		panelRegistroV.setLayout(null);
		layeredPane.add(panelRegistroV, "panelRegistroV");
		
		agregarTituloYSubtitulo(panelRegistroV, "Registro de Venue");
		
		JLabel lblNombre = new JLabel("Nombre del venue:");
		lblNombre.setForeground(Color.LIGHT_GRAY);
		lblNombre.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblNombre.setBounds(10, 120, 200, 25);
		panelRegistroV.add(lblNombre);
		
		JTextField txtNombre = new JTextField();
		txtNombre.setBounds(10, 150, 864, 25);
		panelRegistroV.add(txtNombre);
		
		JLabel lblTipo = new JLabel("Tipo del venue:");
		lblTipo.setForeground(Color.LIGHT_GRAY);
		lblTipo.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblTipo.setBounds(10, 190, 200, 25);
		panelRegistroV.add(lblTipo);
		
		JTextField txtTipo = new JTextField();
		txtTipo.setBounds(10, 220, 864, 25);
		panelRegistroV.add(txtTipo);
		
		JLabel lblUbi = new JLabel("Ubicación:");
		lblUbi.setForeground(Color.LIGHT_GRAY);
		lblUbi.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblUbi.setBounds(10, 260, 200, 25);
		panelRegistroV.add(lblUbi);
		
		JTextField txtUbi = new JTextField();
		txtUbi.setBounds(10, 290, 864, 25);
		panelRegistroV.add(txtUbi);
		
		JLabel lblRestr = new JLabel("Restricciones:");
		lblRestr.setForeground(Color.LIGHT_GRAY);
		lblRestr.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblRestr.setBounds(10, 330, 200, 25);
		panelRegistroV.add(lblRestr);
		
		JTextField txtRestr = new JTextField();
		txtRestr.setBounds(10, 360, 864, 25);
		panelRegistroV.add(txtRestr);
		
		JLabel lblCap = new JLabel("Capacidad:");
		lblCap.setForeground(Color.LIGHT_GRAY);
		lblCap.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblCap.setBounds(10, 400, 200, 25);
		panelRegistroV.add(lblCap);
		
		JTextField txtCap = new JTextField();
		txtCap.setBounds(10, 430, 200, 25);
		panelRegistroV.add(txtCap);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnRegistrar.setBounds(350, 500, 180, 35);
		btnRegistrar.addActionListener(e -> {
			try {
				String nombre = txtNombre.getText().trim();
				String tipo = txtTipo.getText().trim();
				String ubi = txtUbi.getText().trim();
				String restr = txtRestr.getText().trim();
				int capacidad = Integer.parseInt(txtCap.getText().trim());

				if (nombre.isEmpty() || tipo.isEmpty() || ubi.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
					return;
				}

				Venue v = new Venue(nombre, capacidad, tipo, ubi, restr);
				v.setAprobado(false);
				administrador.registrarVenue(v);

				JOptionPane.showMessageDialog(this, 
					"Venue registrado correctamente.\n" +
					"Ahora debe ir a 'Aprobar Venue' para habilitarlo.");

				txtNombre.setText("");
				txtTipo.setText("");
				txtUbi.setText("");
				txtRestr.setText("");
				txtCap.setText("");
				
				mostrarPanel(panelMenu);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "La capacidad debe ser un número válido.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelRegistroV.add(btnRegistrar);
		
		agregarBotonVolver(panelRegistroV);
	}

	// ==================== PANEL APROBAR VENUE ====================
	private void crearPanelAprobarV() {
		panelAprobarV = new JPanel();
		panelAprobarV.setBackground(new Color(10, 62, 114));
		panelAprobarV.setLayout(null);
		layeredPane.add(panelAprobarV, "panelAprobarV");
		
		agregarTituloYSubtitulo(panelAprobarV, "Aprobar Venue");
		
		String[] columnas = {"Nombre", "Tipo", "Ubicación", "Capacidad", "Aprobado"};
		modeloTablaVenues = new DefaultTableModel(columnas, 0);
		JTable tablaVenues = new JTable(modeloTablaVenues);
		
		JScrollPane scroll = new JScrollPane(tablaVenues);
		scroll.setBounds(10, 100, 864, 250);
		panelAprobarV.add(scroll);
		
		JLabel lblFecha = new JLabel("Fecha a aprobar (dd/MM/yyyy):");
		lblFecha.setForeground(Color.LIGHT_GRAY);
		lblFecha.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblFecha.setBounds(10, 370, 300, 25);
		panelAprobarV.add(lblFecha);
		
		JTextField txtFecha = new JTextField();
		txtFecha.setBounds(10, 400, 200, 25);
		txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		panelAprobarV.add(txtFecha);
		
		JLabel lblInfo = new JLabel("(La fecha indica desde cuándo estará disponible el venue)");
		lblInfo.setForeground(new Color(255, 200, 100));
		lblInfo.setFont(new Font("Elephant", Font.PLAIN, 11));
		lblInfo.setBounds(10, 430, 500, 20);
		panelAprobarV.add(lblInfo);
		
		JButton btnAprobar = new JButton("Aprobar Venue");
		btnAprobar.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnAprobar.setBounds(350, 480, 180, 35);
		btnAprobar.addActionListener(e -> {
			int fila = tablaVenues.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Seleccione un venue de la tabla.");
				return;
			}

			try {
				String nombreV = modeloTablaVenues.getValueAt(fila, 0).toString();
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), formato);

				administrador.aprobarVenue(nombreV, fecha);

				JOptionPane.showMessageDialog(this, 
					"✓ Venue '" + nombreV + "' aprobado para la fecha " + fecha);

				cargarVenues();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelAprobarV.add(btnAprobar);
		
		agregarBotonVolver(panelAprobarV);
	}

	// ==================== PANEL FIJAR CARGOS ====================
	private void crearPanelFijarCargos() {
		panelFijarCargos = new JPanel();
		panelFijarCargos.setBackground(new Color(10, 62, 114));
		panelFijarCargos.setLayout(null);
		layeredPane.add(panelFijarCargos, "panelFijarCargos");
		
		agregarTituloYSubtitulo(panelFijarCargos, "Fijar Cargos Adicionales");
		
		JLabel lblActual = new JLabel("Cargos actuales:");
		lblActual.setForeground(new Color(255, 200, 100));
		lblActual.setFont(new Font("Elephant", Font.BOLD, 16));
		lblActual.setBounds(10, 120, 300, 30);
		panelFijarCargos.add(lblActual);
		
		JLabel lblServicioActual = new JLabel("Cargo de servicio: " + 
			String.format("%.2f", administrador.getCargoServicio()) + "%");
		lblServicioActual.setForeground(Color.LIGHT_GRAY);
		lblServicioActual.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblServicioActual.setBounds(10, 160, 400, 25);
		panelFijarCargos.add(lblServicioActual);
		
		JLabel lblImpresionActual = new JLabel("Cargo de impresión: $" + 
			String.format("%.2f", administrador.getCargoImpresion()));
		lblImpresionActual.setForeground(Color.LIGHT_GRAY);
		lblImpresionActual.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblImpresionActual.setBounds(10, 190, 400, 25);
		panelFijarCargos.add(lblImpresionActual);
		
		JSeparator sep = new JSeparator();
		sep.setBounds(10, 230, 864, 2);
		panelFijarCargos.add(sep);
		
		JLabel lblServicio = new JLabel("Nuevo cargo de servicio (%):");
		lblServicio.setForeground(Color.LIGHT_GRAY);
		lblServicio.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblServicio.setBounds(10, 260, 300, 25);
		panelFijarCargos.add(lblServicio);
		
		JTextField txtServicio = new JTextField();
		txtServicio.setBounds(10, 290, 200, 25);
		panelFijarCargos.add(txtServicio);
		
		JButton btnFijarServicio = new JButton("Fijar");
		btnFijarServicio.setFont(new Font("Elephant", Font.PLAIN, 12));
		btnFijarServicio.setBounds(220, 290, 80, 25);
		btnFijarServicio.addActionListener(e -> {
			try {
				double porcentaje = Double.parseDouble(txtServicio.getText().trim());
				administrador.fijarCargoServicio(porcentaje);
				JOptionPane.showMessageDialog(this, 
					"Cargo de servicio actualizado a " + porcentaje + "%");
				lblServicioActual.setText("Cargo de servicio: " + 
					String.format("%.2f", administrador.getCargoServicio()) + "%");
				txtServicio.setText("");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Ingrese un número válido.");
			}
		});
		panelFijarCargos.add(btnFijarServicio);
		
		JLabel lblImpresion = new JLabel("Nuevo cargo de impresión ($):");
		lblImpresion.setForeground(Color.LIGHT_GRAY);
		lblImpresion.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblImpresion.setBounds(10, 340, 300, 25);
		panelFijarCargos.add(lblImpresion);
		
		JTextField txtImpresion = new JTextField();
		txtImpresion.setBounds(10, 370, 200, 25);
		panelFijarCargos.add(txtImpresion);
		
		JButton btnFijarImpresion = new JButton("Fijar");
		btnFijarImpresion.setFont(new Font("Elephant", Font.PLAIN, 12));
		btnFijarImpresion.setBounds(220, 370, 80, 25);
		btnFijarImpresion.addActionListener(e -> {
			try {
				double monto = Double.parseDouble(txtImpresion.getText().trim());
				administrador.fijarCargoImpresion(monto);
				JOptionPane.showMessageDialog(this, 
					"Cargo de impresión actualizado a $" + monto);
				lblImpresionActual.setText("Cargo de impresión: $" + 
					String.format("%.2f", administrador.getCargoImpresion()));
				txtImpresion.setText("");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Ingrese un número válido.");
			}
		});
		panelFijarCargos.add(btnFijarImpresion);
		
		agregarBotonVolver(panelFijarCargos);
	}

	// ==================== PANEL VER DEVOLUCIONES ====================
	private void crearPanelVerDevoluciones() {
		panelVerDevoluciones = new JPanel();
		panelVerDevoluciones.setBackground(new Color(10, 62, 114));
		panelVerDevoluciones.setLayout(null);
		layeredPane.add(panelVerDevoluciones, "panelVerDevoluciones");
		
		agregarTituloYSubtitulo(panelVerDevoluciones, "Solicitudes de Devolución");
		
		String[] columnas = {"ID Tiquete", "Propietario", "Tipo", "Motivo"};
		modeloTablaDevoluciones = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modeloTablaDevoluciones);
		
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(10, 100, 864, 450);
		panelVerDevoluciones.add(scroll);
		
		agregarBotonVolver(panelVerDevoluciones);
	}

	// ==================== PANEL APROBAR DEVOLUCIÓN ====================
	private void crearPanelAprobarDevolucion() {
		panelAprobarDevolucion = new JPanel();
		panelAprobarDevolucion.setBackground(new Color(10, 62, 114));
		panelAprobarDevolucion.setLayout(null);
		layeredPane.add(panelAprobarDevolucion, "panelAprobarDevolucion");
		
		agregarTituloYSubtitulo(panelAprobarDevolucion, "Aprobar/Rechazar Devolución");
		
		JLabel lblId = new JLabel("ID del tiquete:");
		lblId.setForeground(Color.LIGHT_GRAY);
		lblId.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblId.setBounds(10, 150, 200, 25);
		panelAprobarDevolucion.add(lblId);
		
		JTextField txtId = new JTextField();
		txtId.setBounds(10, 180, 200, 25);
		panelAprobarDevolucion.add(txtId);
		
		JLabel lblInfo = new JLabel("<html><b>Criterios de aprobación automática:</b><br>" +
									 "• Motivo contiene 'hospital' → Aprobado<br>" +
									 "• Motivo contiene 'viaje' → Aprobado<br>" +
									 "• Otros motivos → Rechazado</html>");
		lblInfo.setForeground(new Color(255, 200, 100));
		lblInfo.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblInfo.setBounds(10, 230, 500, 100);
		panelAprobarDevolucion.add(lblInfo);
		
		JButton btnProcesar = new JButton("Procesar Devolución");
		btnProcesar.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnProcesar.setBounds(300, 400, 250, 40);
		btnProcesar.addActionListener(e -> {
			try {
				int idT = Integer.parseInt(txtId.getText().trim());
				boolean aprobada = administrador.aprobarDevolucion(idT, exec.Consola.usuariosGUI);
				
				if (aprobada) {
					JOptionPane.showMessageDialog(this, 
						"✓ Devolución APROBADA\n" +
						"El reembolso fue procesado automáticamente.");
				} else {
					JOptionPane.showMessageDialog(this, 
						"✗ Devolución RECHAZADA\n" +
						"El motivo no cumple los criterios de aprobación.");
				}
				
				txtId.setText("");
				
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Ingrese un ID válido.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelAprobarDevolucion.add(btnProcesar);
		
		agregarBotonVolver(panelAprobarDevolucion);
	}

	// ==================== PANEL APROBAR CANCELACIÓN ====================
	private void crearPanelAprobarCancelacion() {
		panelAprobarCancelacion = new JPanel();
		panelAprobarCancelacion.setBackground(new Color(10, 62, 114));
		panelAprobarCancelacion.setLayout(null);
		layeredPane.add(panelAprobarCancelacion, "panelAprobarCancelacion");
		
		agregarTituloYSubtitulo(panelAprobarCancelacion, "Aprobar/Rechazar Cancelación");
		
		JLabel lblPendientes = new JLabel("Solicitudes pendientes:");
		lblPendientes.setForeground(Color.LIGHT_GRAY);
		lblPendientes.setFont(new Font("Elephant", Font.PLAIN, 14));
		lblPendientes.setBounds(10, 100, 300, 25);
		panelAprobarCancelacion.add(lblPendientes);
		
		String[] columnas = {"ID", "Evento", "Fecha", "Organizador", "Motivo"};
		modeloTablaCancelaciones = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modeloTablaCancelaciones);
		
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(10, 130, 864, 300);
		panelAprobarCancelacion.add(scroll);
		
		JLabel lblInfo = new JLabel("<html><b>Al aprobar:</b> Se cancela el evento y se reembolsa a todos los clientes automáticamente.</html>");
		lblInfo.setForeground(new Color(255, 200, 100));
		lblInfo.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblInfo.setBounds(10, 450, 700, 30);
		panelAprobarCancelacion.add(lblInfo);
		
		JButton btnAprobar = new JButton("Aprobar Cancelación");
		btnAprobar.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnAprobar.setBackground(new Color(100, 200, 100));
		btnAprobar.setBounds(250, 500, 200, 40);
		btnAprobar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Seleccione un evento de la tabla.");
				return;
			}
			
			try {
				int idE = (int) modeloTablaCancelaciones.getValueAt(fila, 0);
				String nombreEvento = modeloTablaCancelaciones.getValueAt(fila, 1).toString();
				
				int confirmacion = JOptionPane.showConfirmDialog(this,
					"¿Está seguro de aprobar la cancelación del evento:\n" +
					nombreEvento + "?\n\n" +
					"Esto reembolsará a todos los clientes automáticamente.",
					"Confirmar Cancelación",
					JOptionPane.YES_NO_OPTION);
				
				if (confirmacion == JOptionPane.YES_OPTION) {
					administrador.aprobarCancelacion(idE, exec.Consola.usuariosGUI);
					JOptionPane.showMessageDialog(this, 
						"✓ Cancelación aprobada\n" +
						"Reembolsos procesados automáticamente.");
					cargarCancelaciones();
				}
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelAprobarCancelacion.add(btnAprobar);
		
		JButton btnRechazar = new JButton("Rechazar");
		btnRechazar.setFont(new Font("Elephant", Font.PLAIN, 14));
		btnRechazar.setBackground(new Color(255, 100, 100));
		btnRechazar.setBounds(460, 500, 150, 40);
		btnRechazar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Seleccione un evento de la tabla.");
				return;
			}
			
			try {
				int idE = (int) modeloTablaCancelaciones.getValueAt(fila, 0);
				
				// Buscar el evento y quitar la solicitud
				for (Evento ev : administrador.getEventos()) {
					if (ev.getIdE() == idE) {
						ev.setCancelacionSolicitada(false);
						ev.setMotivoCancelacion(null);
						break;
					}
				}
				
				JOptionPane.showMessageDialog(this, "✗ Cancelación rechazada");
				cargarCancelaciones();
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelAprobarCancelacion.add(btnRechazar);
		
		agregarBotonVolver(panelAprobarCancelacion);
	}

	// ==================== PANEL CONSULTAS FINANCIERAS ====================
	private void crearPanelConsultasFinancieras() {
		panelConsultasFinancieras = new JPanel();
		panelConsultasFinancieras.setBackground(new Color(10, 62, 114));
		panelConsultasFinancieras.setLayout(null);
		layeredPane.add(panelConsultasFinancieras, "panelConsultasFinancieras");
		
		agregarTituloYSubtitulo(panelConsultasFinancieras, "Consultas Financieras");
		
		// GANANCIAS POR FECHA
		JLabel lblFechas = new JLabel("Ganancias por rango de fechas:");
		lblFechas.setForeground(Color.LIGHT_GRAY);
		lblFechas.setFont(new Font("Elephant", Font.BOLD, 14));
		lblFechas.setBounds(10, 120, 300, 25);
		panelConsultasFinancieras.add(lblFechas);
		
		JLabel lblFechaMin = new JLabel("Desde (dd/MM/yyyy):");
		lblFechaMin.setForeground(Color.LIGHT_GRAY);
		lblFechaMin.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblFechaMin.setBounds(10, 150, 150, 20);
		panelConsultasFinancieras.add(lblFechaMin);
		
		JTextField txtFechaMin = new JTextField();
		txtFechaMin.setBounds(160, 150, 150, 25);
		panelConsultasFinancieras.add(txtFechaMin);
		
		JLabel lblFechaMax = new JLabel("Hasta (dd/MM/yyyy):");
		lblFechaMax.setForeground(Color.LIGHT_GRAY);
		lblFechaMax.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblFechaMax.setBounds(320, 150, 150, 20);
		panelConsultasFinancieras.add(lblFechaMax);
		
		JTextField txtFechaMax = new JTextField();
		txtFechaMax.setBounds(470, 150, 150, 25);
		panelConsultasFinancieras.add(txtFechaMax);
		
		JButton btnConsultarFechas = new JButton("Consultar");
		btnConsultarFechas.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnConsultarFechas.setBounds(630, 150, 100, 25);
		btnConsultarFechas.addActionListener(e -> {
			try {
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate min = LocalDate.parse(txtFechaMin.getText().trim(), formato);
				LocalDate max = LocalDate.parse(txtFechaMax.getText().trim(), formato);
				
				double total = administrador.getEstadosFinancieros().totalGananciasPorFecha(min, max);
				JOptionPane.showMessageDialog(this, 
					"Ganancias entre " + min + " y " + max + ":\n$" + 
					String.format("%.2f", total));
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelConsultasFinancieras.add(btnConsultarFechas);
		
		JSeparator sep1 = new JSeparator();
		sep1.setBounds(10, 200, 864, 2);
		panelConsultasFinancieras.add(sep1);
		
		// GANANCIAS POR EVENTO
		JLabel lblEvento = new JLabel("Ganancias por evento:");
		lblEvento.setForeground(Color.LIGHT_GRAY);
		lblEvento.setFont(new Font("Elephant", Font.BOLD, 14));
		lblEvento.setBounds(10, 220, 300, 25);
		panelConsultasFinancieras.add(lblEvento);
		
		JLabel lblIdEvento = new JLabel("ID del evento:");
		lblIdEvento.setForeground(Color.LIGHT_GRAY);
		lblIdEvento.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblIdEvento.setBounds(10, 250, 150, 20);
		panelConsultasFinancieras.add(lblIdEvento);
		
		JTextField txtIdEvento = new JTextField();
		txtIdEvento.setBounds(160, 250, 150, 25);
		panelConsultasFinancieras.add(txtIdEvento);
		
		JButton btnConsultarEvento = new JButton("Consultar");
		btnConsultarEvento.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnConsultarEvento.setBounds(320, 250, 100, 25);
		btnConsultarEvento.addActionListener(e -> {
			try {
				int idE = Integer.parseInt(txtIdEvento.getText().trim());
				double total = administrador.getEstadosFinancieros().totalGananciasPorEvento(idE);
				JOptionPane.showMessageDialog(this, 
					"Ganancias del evento ID " + idE + ":\n$" + 
					String.format("%.2f", total));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelConsultasFinancieras.add(btnConsultarEvento);
		
		JSeparator sep2 = new JSeparator();
		sep2.setBounds(10, 300, 864, 2);
		panelConsultasFinancieras.add(sep2);
		
		// GANANCIAS POR ORGANIZADOR
		JLabel lblOrg = new JLabel("Ganancias por organizador:");
		lblOrg.setForeground(Color.LIGHT_GRAY);
		lblOrg.setFont(new Font("Elephant", Font.BOLD, 14));
		lblOrg.setBounds(10, 320, 300, 25);
		panelConsultasFinancieras.add(lblOrg);
		
		JLabel lblIdOrg = new JLabel("ID del organizador:");
		lblIdOrg.setForeground(Color.LIGHT_GRAY);
		lblIdOrg.setFont(new Font("Elephant", Font.PLAIN, 12));
		lblIdOrg.setBounds(10, 350, 150, 20);
		panelConsultasFinancieras.add(lblIdOrg);
		
		JTextField txtIdOrg = new JTextField();
		txtIdOrg.setBounds(160, 350, 150, 25);
		panelConsultasFinancieras.add(txtIdOrg);
		
		JButton btnConsultarOrg = new JButton("Consultar");
		btnConsultarOrg.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnConsultarOrg.setBounds(320, 350, 100, 25);
		btnConsultarOrg.addActionListener(e -> {
			try {
				int idO = Integer.parseInt(txtIdOrg.getText().trim());
				double total = administrador.getEstadosFinancieros().totalGananciasPorOrganizador(idO);
				JOptionPane.showMessageDialog(this, 
					"Ganancias del organizador ID " + idO + ":\n$" + 
					String.format("%.2f", total));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
		panelConsultasFinancieras.add(btnConsultarOrg);
		
		agregarBotonVolver(panelConsultasFinancieras);
	}

	// ==================== MÉTODOS AUXILIARES ====================
	private void agregarTituloYSubtitulo(JPanel panel, String subtitulo) {
		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(250, 11, 380, 37);
		titulo.setForeground(Color.LIGHT_GRAY);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		panel.add(titulo);
		
		JLabel sub = new JLabel(subtitulo);
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(250, 59, 400, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panel.add(sub);
	}

	private void agregarBotonVolver(JPanel panel) {
		JButton btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnVolver.setBounds(10, 10, 100, 23);
		btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
		panel.add(btnVolver);
	}

	private void cargarVenues() {
		if (modeloTablaVenues == null) return;
		
		modeloTablaVenues.setRowCount(0);
		for (Venue v : administrador.getVenues()) {
			modeloTablaVenues.addRow(new Object[]{
				v.getNombreV(),
				v.getTipoV(),
				v.getUbicacion(),
				v.getCapacidad(),
				v.getAprobado() ? "✓ Sí" : "✗ No"
			});
		}
	}

	private void cargarDevoluciones() {
		if (modeloTablaDevoluciones == null) return;
		
		modeloTablaDevoluciones.setRowCount(0);
		java.util.List<Tiquete> pendientes = administrador.listarDevolucionesPendientes();
		
		for (Tiquete t : pendientes) {
			modeloTablaDevoluciones.addRow(new Object[]{
				t.getIdT(),
				t.getPropietario(),
				t.getTipo(),
				t.getMotivoDevolucion()
			});
		}
	}

	private void cargarCancelaciones() {
		if (modeloTablaCancelaciones == null) return;
		
		modeloTablaCancelaciones.setRowCount(0);
		for (Evento e : administrador.getEventos()) {
			if (e.getCancelacionSolicitada() && !e.getCancelacionAprobada()) {
				modeloTablaCancelaciones.addRow(new Object[]{
					e.getIdE(),
					e.getNombreE(),
					e.getFecha(),
					e.getOrganizador().getLogin(),
					e.getMotivoCancelacion()
				});
			}
		}
	}

	private void guardarDatos() {
		try {
			PerUsuario.guardarUsuarios(new ArrayList<>(exec.Consola.usuariosGUI));
			JOptionPane.showMessageDialog(this, "✓ Datos guardados correctamente.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
		}
	}

	private void salir() {
		guardarDatos();
		this.dispose();
		new Login().setVisible(true);
	}
}
