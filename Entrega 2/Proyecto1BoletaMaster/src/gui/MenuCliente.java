package gui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;



public class MenuCliente extends JFrame {

	private modelo.Cliente cliente;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<JPanel> paneles = new ArrayList<>();
	
	
	private JLayeredPane layeredPane;
	private JPanel panelMenu;
	private JPanel panelRecargarSaldo;
	private JPanel panelCompraTiquete;
	private JLabel lblSaldo;




	/**
	 * Create the frame.
	 */
	public MenuCliente(modelo.Cliente cliente) {
		this.cliente = cliente;
		
		configurarVentana();
		inicializarLayeredPanel();
		crearPanelMenu();
		crearPanelRecargarSaldo();
		crearPanelComprarTiquete();
		
		paneles.add(panelMenu);
		paneles.add(panelRecargarSaldo);
		paneles.add(panelCompraTiquete);
		
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

	    JLabel titulo = new JLabel("BOLETA MASTER");
	    titulo.setBounds(209, 11, 310, 37);
	    titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
	    titulo.setForeground(Color.LIGHT_GRAY);
	    panelMenu.add(titulo);

	    JLabel sub = new JLabel("Menu Cliente");
	    sub.setForeground(Color.LIGHT_GRAY);
	    sub.setBounds(300, 59, 142, 26);
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
	     btnRecargar.setBounds(10, 166, 740, 23);
	     btnRecargar.addActionListener(e -> mostrarPanel(panelRecargarSaldo));
	     panelMenu.add(btnRecargar);

	     JButton btnComprar = new JButton("Comprar Tiquete");
	     btnComprar.setFont(new Font("Elephant", Font.PLAIN, 11));
	     btnComprar.setBounds(10, 200, 740, 23);
	     btnComprar.addActionListener(e -> mostrarPanel(panelCompraTiquete));
	     panelMenu.add(btnComprar);

	     JButton btnSalir = new JButton("Salir");
	     btnSalir.setFont(new Font("Elephant", Font.PLAIN, 11));
	     btnSalir.setBounds(328, 440, 89, 23);
	     btnSalir.addActionListener(e -> salir());
	     panelMenu.add(btnSalir);
	        
	     JLayeredPane layeredPane_1 = new JLayeredPane();
	     layeredPane_1.setBounds(0, 0, 1, 1);
	     panelMenu.add(layeredPane_1);

	    }

	
	private void crearPanelRecargarSaldo() {
	    panelRecargarSaldo = new JPanel();
	    panelRecargarSaldo.setLayout(null);
	    panelRecargarSaldo.setBackground(new Color(10, 62, 114));
	    layeredPane.add(panelRecargarSaldo, "panelRecargarSaldo");

	    JLabel titulo = new JLabel("BOLETA MASTER");
	    titulo.setBounds(209, 11, 310, 37);
	    titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
	    titulo.setForeground(Color.LIGHT_GRAY);
	    panelRecargarSaldo.add(titulo);
	    
	    JLabel sub = new JLabel("Recargar Saldo");
	    sub.setForeground(Color.LIGHT_GRAY);
	    sub.setBounds(271, 59, 156, 26);
	     sub.setFont(new Font("Elephant", Font.PLAIN, 20));
	     panelRecargarSaldo.add(sub);

	    JButton btnRecargar = new JButton("Ingresar monto");
	    btnRecargar.setFont(new Font("Elephant", Font.PLAIN, 15));
	    btnRecargar.setBounds(139, 237, 435, 79);
	    btnRecargar.addActionListener(e -> recargarSaldo());
	    panelRecargarSaldo.add(btnRecargar);

	    JButton btnVolver = new JButton("Volver");
	    btnVolver.setBounds(10, 10, 100, 23);
	    btnVolver.addActionListener(e -> mostrarPanel(panelMenu));
	    panelRecargarSaldo.add(btnVolver);

	     
	    }

	 
	private void crearPanelComprarTiquete() {
		panelCompraTiquete = new JPanel();
		panelCompraTiquete.setLayout(null);
		panelCompraTiquete.setBackground(new Color(10, 62, 114));
		layeredPane.add(panelCompraTiquete, "panelCompraTiquete");
		
		JLabel titulo = new JLabel("BOLETA MASTER");
		titulo.setBounds(209, 11, 310, 37);
		titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
		titulo.setForeground(Color.LIGHT_GRAY);
		panelCompraTiquete.add(titulo);
		    
		JLabel sub = new JLabel("Comprar Tiquetes");
		sub.setForeground(Color.LIGHT_GRAY);
		sub.setBounds(261, 59, 188, 26);
		sub.setFont(new Font("Elephant", Font.PLAIN, 20));
		panelCompraTiquete.add(sub);
		
		JLabel metodoP = new JLabel("Metodo de pago");
		metodoP.setForeground(new Color(192, 192, 192));
		metodoP.setFont(new Font("Elephant", Font.PLAIN, 15));
		metodoP.setBounds(10, 133, 117, 19);
		panelCompraTiquete.add(metodoP);
		
		JComboBox<String> cbMetodoP = new JComboBox<>();
		cbMetodoP.addItem("Saldo");
		cbMetodoP.addItem("Pasarela Externa");
		cbMetodoP.setBounds(10, 156, 201, 22);
		panelCompraTiquete.add(cbMetodoP);
		
		JLabel lbEvento = new JLabel("Seleccione el evento al que quiere asistir");
		lbEvento.setForeground(Color.LIGHT_GRAY);
		lbEvento.setFont(new Font("Elephant", Font.PLAIN, 15));
		lbEvento.setBounds(10, 189, 302, 19);
		panelCompraTiquete.add(lbEvento);
		
		JComboBox<String> cbEventos = new JComboBox<String>();
		cbEventos.setBounds(10, 212, 425, 22);
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
		lbLocalidad.setBounds(10, 245, 180, 19);
		panelCompraTiquete.add(lbLocalidad);
		
		JComboBox<String> cbLocalidades = new JComboBox<String>();
		cbLocalidades.setBounds(10, 267, 425, 22);
		panelCompraTiquete.add(cbLocalidades);
		
		
		cbEventos.addActionListener(ev -> {
	       cbLocalidades.removeAllItems();
	       if (cbEventos.getSelectedItem() == null) return;

	       int idE = Integer.parseInt(cbEventos.getSelectedItem().toString().split(" - ")[0]);
	       modelo.Evento eventoSel = null;

	       for (modelo.Evento e : exec.Consola.eventosGUI)
	           if (e.getIdE() == idE) eventoSel = e;

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
	    cbPaquetes.setBounds(10, 370, 720, 25);
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
	    btnComprar.setBounds(300, 440, 120, 30);
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
