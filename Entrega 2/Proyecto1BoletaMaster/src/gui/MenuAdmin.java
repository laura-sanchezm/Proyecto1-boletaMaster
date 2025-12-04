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

public class MenuAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private modelo.Administrador administrador;

	
	/**
	 * Create the frame.
	 */
	public MenuAdmin(modelo.Administrador administrador) {
		this.administrador = administrador;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 589);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(10, 62, 114));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOLETA MASTER");
		lblNewLabel.setBounds(209, 11, 310, 37);
		lblNewLabel.setFont(new Font("Elephant", Font.PLAIN, 30));
		lblNewLabel.setForeground(new Color(192, 192, 192));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Menu Administrador");
		lblNewLabel_1.setForeground(new Color(192, 192, 192));
		lblNewLabel_1.setBounds(266, 54, 210, 26);
		lblNewLabel_1.setFont(new Font("Elephant", Font.PLAIN, 20));
		contentPane.add(lblNewLabel_1);
		
		JButton btnRecargar = new JButton("Registrar Venue");
		btnRecargar.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnRecargar.setBackground(new Color(255, 255, 255));
		btnRecargar.setBounds(10, 166, 740, 23);
		contentPane.add(btnRecargar);
		
		JPanel panelOpt = new JPanel();
		panelOpt.setBackground(new Color(10, 62, 114));
		panelOpt.setBounds(0, 81, 750, 469);
		contentPane.add(panelOpt);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 10, 10);
		contentPane.add(panel);

	}
}
