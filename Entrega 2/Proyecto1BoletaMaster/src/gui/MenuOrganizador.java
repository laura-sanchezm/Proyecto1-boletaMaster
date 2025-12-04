package gui;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuOrganizador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private modelo.Organizador organizador;


	/**
	 * Create the frame.
	 */
	public MenuOrganizador(modelo.Organizador organizador) {
		this.organizador = organizador;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 589);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(10, 62, 114));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOLETA MASTER");
		lblNewLabel.setBounds(222, 11, 310, 37);
		lblNewLabel.setFont(new Font("Elephant", Font.PLAIN, 30));
		lblNewLabel.setForeground(new Color(192, 192, 192));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Menu Organizador");
		lblNewLabel_1.setForeground(new Color(192, 192, 192));
		lblNewLabel_1.setBounds(285, 57, 191, 26);
		lblNewLabel_1.setFont(new Font("Elephant", Font.PLAIN, 20));
		contentPane.add(lblNewLabel_1);
		
		JButton btnRecargar = new JButton("Organizar Eventos");
		btnRecargar.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnRecargar.setBackground(new Color(255, 255, 255));
		btnRecargar.setBounds(10, 166, 740, 23);
		contentPane.add(btnRecargar);
		
		JButton btnSolicitarCancelaci = new JButton("Solicitar Cancelación");
		btnSolicitarCancelaci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSolicitarCancelaci.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnSolicitarCancelaci.setBackground(Color.WHITE);
		btnSolicitarCancelaci.setBounds(10, 200, 740, 23);
		contentPane.add(btnSolicitarCancelaci);
		
		JButton btnConsultarEstadoCancelaciones = new JButton("Consultar Estado Cancelaciones");
		btnConsultarEstadoCancelaciones.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnConsultarEstadoCancelaciones.setBackground(Color.WHITE);
		btnConsultarEstadoCancelaciones.setBounds(10, 234, 740, 23);
		contentPane.add(btnConsultarEstadoCancelaciones);
		
		JButton btnMenuDeIngresos = new JButton("Menu de Ingresos");
		btnMenuDeIngresos.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnMenuDeIngresos.setBackground(Color.WHITE);
		btnMenuDeIngresos.setBounds(10, 267, 740, 23);
		contentPane.add(btnMenuDeIngresos);
		
		JButton btnSugerirVenue = new JButton("Sugerir Venue");
		btnSugerirVenue.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnSugerirVenue.setBackground(Color.WHITE);
		btnSugerirVenue.setBounds(10, 301, 740, 23);
		contentPane.add(btnSugerirVenue);
		
		JButton btnAccederAlMenu = new JButton("Acceder al menu de Cliente");
		btnAccederAlMenu.setFont(new Font("Elephant", Font.PLAIN, 15));
		btnAccederAlMenu.setBackground(Color.WHITE);
		btnAccederAlMenu.setBounds(10, 335, 740, 23);
		contentPane.add(btnAccederAlMenu);
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSalir.setBounds(326, 426, 89, 23);
		contentPane.add(btnSalir);

	}
}
