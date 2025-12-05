package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.time.LocalDate;

public class MenuAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private modelo.Administrador administrador;
	private ArrayList<JPanel> paneles = new ArrayList<>();
	
	private JTextField txtNombre, txtTipo, txtUbi, txtRestr, txtCap;
	private DefaultTableModel modeloT;
	
	private JPanel panelMenu;
    private JPanel panelRegistroV;
    private JPanel panelAprobarV;
    private JLayeredPane layeredPane;

	
	/**
	 * Create the frame.
	 */
	public MenuAdmin(modelo.Administrador administrador) {
		
		this.administrador = administrador;
		
		configurarVentana();
		inicializarLayeredPanel();
		crearPanelMenu();
		crearPanelRegistroV();
		crearPanelAprobarV();
		
		paneles.add(panelMenu);
		paneles.add(panelRegistroV);
		paneles.add(panelAprobarV);
		
			
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
		layeredPane.add(panelMenu, "name_71840167171800");
		
		JLabel titulo_Menu = new JLabel("BOLETA MASTER");
		titulo_Menu.setBounds(213, 11, 310, 37);
		titulo_Menu.setForeground(Color.LIGHT_GRAY);
		titulo_Menu.setFont(new Font("Elephant", Font.PLAIN, 30));
		panelMenu.add(titulo_Menu);
		
		JLabel lblNewLabel_1_1 = new JLabel("Menu Administrador");
		lblNewLabel_1_1.setBounds(261, 53, 210, 26);
		lblNewLabel_1_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_1_1.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelMenu.add(lblNewLabel_1_1);
		
		
		JButton btnRegistrarV = new JButton("Registrar Venue");
		btnRegistrarV.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnRegistrarV.setBackground(Color.WHITE);
		btnRegistrarV.setBounds(10, 167, 740, 23);
		panelMenu.add(btnRegistrarV);
		
		btnRegistrarV.addActionListener(e -> mostrarPanel(panelRegistroV));
		
		JButton btnAprobarV = new JButton("Aprobar Venue");
		btnAprobarV.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnAprobarV.setBackground(Color.WHITE);
		btnAprobarV.setBounds(10, 201, 740, 23);
		panelMenu.add(btnAprobarV);
		
		btnAprobarV.addActionListener(e -> {cargarVenues(); mostrarPanel(panelAprobarV);});
		
		
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnSalir.setBounds(336, 474, 89, 23);
		panelMenu.add(btnSalir);
		
		btnSalir.addActionListener(e -> { this.dispose();
	        new Login().setVisible(true); });
	}
	
	
	private void crearPanelRegistroV() {
		
		panelRegistroV = new JPanel();
		panelRegistroV.setBackground(new Color(10, 62, 114));
		panelRegistroV.setLayout(null);
		layeredPane.add(panelRegistroV, "name_71840181663400");
		
		JLabel titulo_RegistroV = new JLabel("BOLETA MASTER");
		titulo_RegistroV.setForeground(Color.LIGHT_GRAY);
		titulo_RegistroV.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo_RegistroV.setBounds(213, 11, 310, 37);
		panelRegistroV.add(titulo_RegistroV);
		
		JLabel subtituloRegistroV = new JLabel("Registro de Venue");
		subtituloRegistroV.setForeground(Color.LIGHT_GRAY);
		subtituloRegistroV.setFont(new Font("Elephant", Font.PLAIN, 20));
		subtituloRegistroV.setBounds(275, 53, 182, 26);
		panelRegistroV.add(subtituloRegistroV);
		
		JLabel pedirNombre = new JLabel("Ingrese el nombre del venue");
		pedirNombre.setForeground(new Color(192, 192, 192));
		pedirNombre.setFont(new Font("Elephant", Font.PLAIN, 15));
		pedirNombre.setBounds(10, 104, 210, 26);
		panelRegistroV.add(pedirNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(10, 129, 725, 20);
		panelRegistroV.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel pedirTipo = new JLabel("Ingrese el tipo del venue");
		pedirTipo.setForeground(Color.LIGHT_GRAY);
		pedirTipo.setFont(new Font("Elephant", Font.PLAIN, 15));
		pedirTipo.setBounds(10, 160, 205, 26);
		panelRegistroV.add(pedirTipo);
		
		txtTipo = new JTextField();
		txtTipo.setColumns(10);
		txtTipo.setBounds(10, 185, 725, 20);
		panelRegistroV.add(txtTipo);
		
		JLabel pedirUbi = new JLabel("Ingrese la ubicación del venue");
		pedirUbi.setForeground(Color.LIGHT_GRAY);
		pedirUbi.setFont(new Font("Elephant", Font.PLAIN, 15));
		pedirUbi.setBounds(10, 216, 226, 26);
		panelRegistroV.add(pedirUbi);
		
		txtUbi = new JTextField();
		txtUbi.setColumns(10);
		txtUbi.setBounds(10, 241, 725, 20);
		panelRegistroV.add(txtUbi);
		
		JLabel pedirRestric = new JLabel("Ingrese las restricciones del venue");
		pedirRestric.setForeground(Color.LIGHT_GRAY);
		pedirRestric.setFont(new Font("Elephant", Font.PLAIN, 15));
		pedirRestric.setBounds(10, 272, 262, 26);
		panelRegistroV.add(pedirRestric);
		
		txtRestr = new JTextField();
		txtRestr.setColumns(10);
		txtRestr.setBounds(10, 297, 725, 20);
		panelRegistroV.add(txtRestr);
		
		JLabel pedirCapacidad = new JLabel("Ingrese la capacidad del venue");
		pedirCapacidad.setForeground(Color.LIGHT_GRAY);
		pedirCapacidad.setFont(new Font("Elephant", Font.PLAIN, 15));
		pedirCapacidad.setBounds(10, 328, 228, 31);
		panelRegistroV.add(pedirCapacidad);
		
		txtCap = new JTextField();
		txtCap.setColumns(10);
		txtCap.setBounds(10, 359, 725, 20);
		panelRegistroV.add(txtCap);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnRegistrar.setBounds(328, 455, 89, 23);
		panelRegistroV.add(btnRegistrar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnVolver.setBounds(10, 11, 89, 23);
		panelRegistroV.add(btnVolver);
		
		btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
		
		
		btnRegistrar.addActionListener(e -> {
	        try {
	           
	            String nombre = txtNombre.getText().trim();
	            String tipo = txtTipo.getText().trim();
	            String ubi = txtUbi.getText().trim();
	            String restr = txtRestr.getText().trim();
	            String capStr = txtCap.getText().trim();

	           
	            if (nombre.isEmpty() || tipo.isEmpty() || ubi.isEmpty() || restr.isEmpty() || capStr.isEmpty()) {
	                JOptionPane.showMessageDialog(this,
	                        "Todos los campos son obligatorios.", 
	                        "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	           
	            int capacidad = Integer.parseInt(capStr);

	           
	            modelo.Venue v = new modelo.Venue(nombre, capacidad, tipo, ubi, restr);
	            v.setAprobado(false); 

	            
	            exec.Consola.adminGUI.registrarVenue(v);

	         
	            JOptionPane.showMessageDialog(this,
	                    "Venue registrado correctamente.\nQueda pendiente de aprobación.",
	                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

	            
	            txtNombre.setText("");
	            txtTipo.setText("");
	            txtUbi.setText("");
	            txtRestr.setText("");
	            txtCap.setText("");

	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(this, 
	                    "La capacidad debe ser un número entero.",
	                    "Error", JOptionPane.ERROR_MESSAGE);

	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, 
	                    "Error al registrar Venue: " + ex.getMessage(),
	                    "Error", JOptionPane.ERROR_MESSAGE);
	        }
	        
	        mostrarPanel(panelMenu);
	    });

	}
	
	
	private void crearPanelAprobarV() {
		
		panelAprobarV = new JPanel();
		panelAprobarV.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelAprobarV, "name_74552616160500");
		panelAprobarV.setLayout(null);
		
		JLabel titulo_Menu = new JLabel("BOLETA MASTER");
		titulo_Menu.setBounds(225, 5, 310, 37);
		titulo_Menu.setForeground(Color.LIGHT_GRAY);
		titulo_Menu.setFont(new Font("Elephant", Font.PLAIN, 30));
		panelAprobarV.add(titulo_Menu);
		
		JLabel lblAprobarVenvue = new JLabel("Aprobar Venvue");
		lblAprobarVenvue.setForeground(Color.LIGHT_GRAY);
		lblAprobarVenvue.setFont(new Font("Elephant", Font.PLAIN, 20));
		lblAprobarVenvue.setBounds(292, 53, 160, 26);
		panelAprobarV.add(lblAprobarVenvue);
		
		modeloT = new DefaultTableModel(new Object[]{"Nombre", "Ubicación", "Capacidad", "Aprobado"}, 0);
		JTable tablaV = new JTable(modeloT);
		
		JScrollPane scroll = new JScrollPane(tablaV);
		scroll.setBounds(46, 100, 660, 217);
		panelAprobarV.add(scroll);
		
		JLabel lblFecha = new JLabel("Fecha a aprobar:");
		lblFecha.setForeground(new Color(192, 192, 192));
		lblFecha.setFont(new Font("Elephant", Font.PLAIN, 15));
		lblFecha.setBounds(23, 358, 150, 25);
		panelAprobarV.add(lblFecha);
		
		JComboBox<String> cbDia = new JComboBox<>();
		cbDia.setBounds(76, 386, 60, 25);
		panelAprobarV.add(cbDia);
		JComboBox<String> cbMes = new JComboBox<>();
		cbMes.setBounds(184, 386, 60, 25);
		panelAprobarV.add(cbMes);
		JComboBox<String> cbAno = new JComboBox<>();
		cbAno.setBounds(303, 386, 80, 25);
		panelAprobarV.add(cbAno);
		
		

	   for(int i=1;i<=31;i++) cbDia.addItem(String.valueOf(i));
	   for(int i=1;i<=12;i++) cbMes.addItem(String.valueOf(i));
	   for(int i=2025;i<=2040;i++) cbAno.addItem(String.valueOf(i));
	   
	   
	   JLabel lblNewLabel = new JLabel("Día");
	   lblNewLabel.setForeground(new Color(192, 192, 192));
	   lblNewLabel.setFont(new Font("Elephant", Font.PLAIN, 15));
	   lblNewLabel.setBounds(33, 386, 28, 20);
	   panelAprobarV.add(lblNewLabel);
		
	   JLabel lblMes = new JLabel("Mes");
	   lblMes.setForeground(Color.LIGHT_GRAY);
	   lblMes.setFont(new Font("Elephant", Font.PLAIN, 15));
	   lblMes.setBounds(146, 386, 37, 20);
	   panelAprobarV.add(lblMes);
		
	   JLabel lblAo = new JLabel("Año");
	   lblAo.setForeground(Color.LIGHT_GRAY);
	   lblAo.setFont(new Font("Elephant", Font.PLAIN, 15));
	   lblAo.setBounds(263, 386, 37, 20);
	   panelAprobarV.add(lblAo);
	   
	   JButton btnAprobar = new JButton("Aprobar Venue");
	   btnAprobar.setFont(new Font("Elephant", Font.PLAIN, 11));
	   btnAprobar.setBounds(273, 450, 200, 30);
	   panelAprobarV.add(btnAprobar);
	   
	   btnAprobar.addActionListener(e -> {
	   
	        int fila = tablaV.getSelectedRow();
	        if(fila == -1){
	           JOptionPane.showMessageDialog(this, "Seleccione un venue.");
	            return;
	         }
	   
	         String nombreV = modeloT.getValueAt(fila, 0).toString();
	   
	         int d = Integer.parseInt(cbDia.getSelectedItem().toString());
	         int m = Integer.parseInt(cbMes.getSelectedItem().toString());
	         int a = Integer.parseInt(cbAno.getSelectedItem().toString());
	         LocalDate fecha = LocalDate.of(a, m, d);
	   
	         exec.Consola.adminGUI.aprobarVenue(nombreV, fecha);
	   
	         JOptionPane.showMessageDialog(this, 
	         "Venue aprobado correctamente para la fecha " + fecha);
	   
	         cargarVenues(); 
	         mostrarPanel(panelMenu);
	     });
	   
	   
	  JButton btnVolver = new JButton("Volver");
	  btnVolver.setFont(new Font("Elephant", Font.PLAIN, 11));
	  btnVolver.setBounds(10, 19, 89, 23);
	  panelAprobarV.add(btnVolver);
	  
	  btnVolver.addActionListener(e -> {mostrarPanel(panelMenu); cargarVenues();});
		
	}
	
	
	private void mostrarPanel(JPanel panelMostrado) {
		for(JPanel p : paneles) {
			p.setVisible(false);
		}
		panelMostrado.setVisible(true);
		panelMostrado.repaint();
	}
	
	
	 private void cargarVenues() {
	   modeloT.setRowCount(0);
	   for(modelo.Venue v : exec.Consola.adminGUI.getVenues()){
	       modeloT.addRow(new Object[]{
	                v.getNombreV(),
	                v.getUbicacion(),
	                v.getCapacidad(),
	                v.getAprobado()
	            });
	        }
	    }
}
