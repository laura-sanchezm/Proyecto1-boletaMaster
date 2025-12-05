package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		   try {
		        
		        exec.Consola.usuariosGUI = new java.util.HashSet<>();
		        exec.Consola.eventosGUI = new java.util.ArrayList<>();

		       
		        try {
		            java.util.List<modelo.Usuario> cargados =
		                    Persistencia.PerUsuario.cargarUsuario();

		            exec.Consola.usuariosGUI.addAll(cargados);
		            System.out.println("Usuarios cargados (GUI): " +
		                               exec.Consola.usuariosGUI.size());

		        } catch (Exception e) {
		            System.out.println("No se pudieron cargar usuarios. Lista vacía.");
		        }

		       
		        exec.Consola.adminGUI = null;
		        for (modelo.Usuario u : exec.Consola.usuariosGUI) {
		            if (u instanceof modelo.Administrador a) {
		                exec.Consola.adminGUI = a;
		                break;
		            }
		        }

		      
		        if (exec.Consola.adminGUI == null) {
		            exec.Consola.adminGUI = new modelo.Administrador("ADMIN", "DPOO");
		            exec.Consola.usuariosGUI.add(exec.Consola.adminGUI);
		        }

		      
		        exec.Consola.eventosGUI = new java.util.ArrayList<>();

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
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
		
		JLabel lblNewLabel_1 = new JLabel("Inicio de Sesión");
		lblNewLabel_1.setForeground(new Color(192, 192, 192));
		lblNewLabel_1.setFont(new Font("Elephant", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(288, 59, 159, 64);
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
		lblNewLabel_2_1.setBounds(27, 291, 116, 26);
		contentPane.add(lblNewLabel_2_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(27, 328, 673, 20);
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setFont(new Font("Elephant", Font.PLAIN, 11));
		btnLogin.setBackground(new Color(240, 240, 240));
		btnLogin.setBounds(309, 401, 126, 23);
		contentPane.add(btnLogin);
		
		btnLogin.addActionListener(e -> validarLogin());
		
		
		

	}
	
	private void validarLogin() {
		String login = textField.getText().trim();
		String password = new String(passwordField.getPassword()).trim();
		
		if(login.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
			return;
		}
		
		 modelo.Usuario usuario =
	                exec.Consola.buscarUsuario(exec.Consola.usuariosGUI, login, password);

	        if (usuario == null) {
	            JOptionPane.showMessageDialog(this, "Usuario no encontrado. Redirigiendo al registro...");
	            this.dispose();
	            new Registro().setVisible(true);	
	            return;
	        }

	        if (usuario instanceof modelo.Organizador o) {
	            JOptionPane.showMessageDialog(this, "Bienvenido Organizador!");
	            this.dispose();
	            new MenuOrganizador(o).setVisible(true);
	            
	        } else if (usuario instanceof modelo.Cliente c) {
	            JOptionPane.showMessageDialog(this, "Bienvenido Cliente!");
	            this.dispose();
	            new MenuCliente(c).setVisible(true);
	        } else if (usuario instanceof modelo.Administrador a) {
	            JOptionPane.showMessageDialog(this, "Bienvenido Administrador!");
	            this.dispose();
	            new MenuAdmin(a).setVisible(true);
	        }

	}
}
