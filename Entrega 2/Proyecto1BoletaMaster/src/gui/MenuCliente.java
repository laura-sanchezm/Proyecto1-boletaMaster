package gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.BorderLayout;


public class MenuCliente extends JFrame {

	private modelo.Cliente cliente;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel cards;
	private CardLayout cardLayout;
	private JLabel lblSaldo;




	/**
	 * Create the frame.
	 */
	public MenuCliente(modelo.Cliente cliente) {
		this.cliente = cliente;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 589);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(10, 62, 114));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);
		
		JPanel panelMenu = crearPanelMenuPrincipal();
		
		
		 JPanel panelRecargar = crearPanelRecargarSaldo();
	        JPanel panelComprar = crearPanelComprarTiquete();

	   
	        cards.add(panelMenu, "MenuPrincipal");
	        cards.add(panelRecargar, "RecargarSaldo");
	        cards.add(panelComprar, "ComprarTiquete");

	        contentPane.add(cards, BorderLayout.CENTER);
	    }


	    private JPanel crearPanelMenuPrincipal() {
	        JPanel p = new JPanel();
	        p.setLayout(null);
	        p.setBackground(new Color(10, 62, 114));

	        JLabel titulo = new JLabel("BOLETA MASTER");
	        titulo.setBounds(209, 11, 310, 37);
	        titulo.setFont(new Font("Elephant", Font.PLAIN, 30));
	        titulo.setForeground(Color.LIGHT_GRAY);
	        p.add(titulo);

	        JLabel sub = new JLabel("Menu Cliente");
	        sub.setForeground(Color.LIGHT_GRAY);
	        sub.setBounds(300, 59, 142, 26);
	        sub.setFont(new Font("Elephant", Font.PLAIN, 20));
	        p.add(sub);

	        JLabel labelSaldoTxt = new JLabel("Saldo:");
	        labelSaldoTxt.setFont(new Font("Elephant", Font.BOLD, 15));
	        labelSaldoTxt.setForeground(Color.LIGHT_GRAY);
	        labelSaldoTxt.setBounds(10, 114, 61, 14);
	        p.add(labelSaldoTxt);

	        lblSaldo = new JLabel(String.valueOf(cliente.consultarSaldo()));
	        lblSaldo.setFont(new Font("Elephant", Font.PLAIN, 15));
	        lblSaldo.setForeground(Color.LIGHT_GRAY);
	        lblSaldo.setBounds(70, 114, 300, 14);
	        p.add(lblSaldo);

	        JButton btnRecargar = new JButton("Recargar Saldo");
	        btnRecargar.setBounds(10, 166, 740, 23);
	        btnRecargar.addActionListener(e -> cardLayout.show(cards, "RecargarSaldo"));
	        p.add(btnRecargar);

	        JButton btnComprar = new JButton("Comprar Tiquete");
	        btnComprar.setBounds(10, 200, 740, 23);
	        btnComprar.addActionListener(e -> cardLayout.show(cards, "ComprarTiquete"));
	        p.add(btnComprar);

	        JButton btnSalir = new JButton("Salir");
	        btnSalir.setBounds(328, 440, 89, 23);
	        btnSalir.addActionListener(e -> salir());
	        p.add(btnSalir);

	        return p;
	    }

	
	    private JPanel crearPanelRecargarSaldo() {
	        JPanel p = new JPanel();
	        p.setLayout(null);
	        p.setBackground(new Color(10, 62, 114));

	        JLabel titulo = new JLabel("Recargar Saldo");
	        titulo.setBounds(250, 40, 300, 40);
	        titulo.setFont(new Font("Elephant", Font.PLAIN, 25));
	        titulo.setForeground(Color.LIGHT_GRAY);
	        p.add(titulo);

	        JButton btnRecargar = new JButton("Ingresar monto");
	        btnRecargar.setBounds(173, 172, 350, 30);
	        btnRecargar.addActionListener(e -> recargarSaldo());
	        p.add(btnRecargar);

	        JButton btnVolver = new JButton("Volver");
	        btnVolver.setBounds(10, 10, 100, 23);
	        btnVolver.addActionListener(e -> cardLayout.show(cards, "MenuPrincipal"));
	        p.add(btnVolver);

	        return p;
	    }

	 
	    private JPanel crearPanelComprarTiquete() {
	        JPanel p = new JPanel();
	        p.setLayout(null);
	        p.setBackground(new Color(10, 62, 114));

	        JLabel titulo = new JLabel("Comprar Tiquete");
	        titulo.setBounds(250, 40, 300, 40);
	        titulo.setFont(new Font("Elephant", Font.PLAIN, 25));
	        titulo.setForeground(Color.LIGHT_GRAY);
	        p.add(titulo);

	        JButton btnVolver = new JButton("Volver");
	        btnVolver.setBounds(10, 10, 100, 23);
	        btnVolver.addActionListener(e -> cardLayout.show(cards, "MenuPrincipal"));
	        p.add(btnVolver);

	        return p;
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
}
