package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JComboBox<String> tipoUsuarios;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registro frame = new Registro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Registro() {
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
		
		JLabel lblNewLabel_1 = new JLabel("Registro de Usuario");
		lblNewLabel_1.setForeground(new Color(192, 192, 192));
		lblNewLabel_1.setBounds(259, 59, 210, 26);
		lblNewLabel_1.setFont(new Font("Elephant", Font.PLAIN, 20));
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setForeground(new Color(192, 192, 192));
		lblNewLabel_2.setFont(new Font("Elephant", Font.PLAIN, 20));
		lblNewLabel_2.setBackground(new Color(192, 192, 192));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setBounds(27, 146, 67, 26);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(28, 179, 672, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("Contraseña");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_2_1.setFont(new Font("Elephant", Font.PLAIN, 20));
		lblNewLabel_2_1.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_2_1.setBounds(27, 224, 116, 26);
		contentPane.add(lblNewLabel_2_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(27, 261, 673, 20);
		contentPane.add(passwordField);
		
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Tipo de Usuario");
		lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2_1_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_2_1_1.setFont(new Font("Elephant", Font.PLAIN, 20));
		lblNewLabel_2_1_1.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_2_1_1.setBounds(27, 303, 165, 26);
		contentPane.add(lblNewLabel_2_1_1);
		
		tipoUsuarios = new JComboBox<>();
		tipoUsuarios.setMaximumRowCount(2);
		tipoUsuarios.setModel(new DefaultComboBoxModel<String>(new String[] {"Cliente", "Organizador"}));
		tipoUsuarios.setFont(new Font("Elephant", Font.PLAIN, 11));
		tipoUsuarios.setToolTipText("");
		tipoUsuarios.setBounds(28, 336, 164, 22);
		contentPane.add(tipoUsuarios);
		
		
		
		JButton btnRegistro = new JButton("Registrar Usuario");
		btnRegistro.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnRegistro.setBackground(new Color(240, 240, 240));
		btnRegistro.setBounds(295, 424, 150, 23);
		contentPane.add(btnRegistro);
		
		btnRegistro.addActionListener(e -> registrarUsuario());
		
		
		

	}
	
	private void registrarUsuario() {

			String login = textField.getText().trim();
		    String password = new String(passwordField.getPassword()).trim();
		    String tipo = ((String) tipoUsuarios.getSelectedItem()).trim();

		 
		    if (login.isEmpty() || password.isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.");
		        return;
		    }

		    
		    if (exec.Consola.loginYaExiste(exec.Consola.usuariosGUI, login)) {
		        JOptionPane.showMessageDialog(this, "Ese login ya está registrado.");
		        return;
		    }

		    modelo.Usuario nuevo = null;

		   
		    if (tipo.equals("Cliente")) {
		        nuevo = new modelo.Cliente(login, password);
		    } 
		    else if (tipo.equals("Organizador")) {
		       
		    	 java.util.Random random = new java.util.Random();
		    	 int idO = random.nextInt(100000);

		    	    
		    	 while (exec.Consola.buscarOrganizadorPorId(exec.Consola.usuariosGUI, idO) != null) {
		    	    idO = random.nextInt(100000);
		    	    }

		    	 nuevo = new modelo.Organizador(login, password, idO);

		    }

		 
		    exec.Consola.usuariosGUI.add(nuevo);

		 
		    try {
		        ArrayList<modelo.Usuario> lista = new ArrayList<>(exec.Consola.usuariosGUI);
		        Persistencia.PerUsuario.guardarUsuarios(lista);
		    } 
		    catch (Exception ex) {
		        JOptionPane.showMessageDialog(this, "Error guardando usuario.");
		        ex.printStackTrace();
		        return;
		    }

		    JOptionPane.showMessageDialog(this, 
		        "Usuario registrado exitosamente como: " + tipo);

		
		    this.dispose();
		    new Login().setVisible(true);
	}
}
