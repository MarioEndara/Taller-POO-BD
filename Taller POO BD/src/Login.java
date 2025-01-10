import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    PreparedStatement ps;
    DefaultListModel mod = new DefaultListModel();
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel panel;
    private JTextField textField2;
    private JLabel idjlabel;
    Connection con;

    public Login() {
        // Constructor
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener datos de los campos
                    String usuario = textField1.getText();
                    String contraseña = new String(passwordField1.getPassword());
                    validarUsuario(usuario, contraseña);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });
    }

    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/taller", "root", "Leito_2015");
            System.out.println("Conectado a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos");
        }
    }

    public void usuario() {
        try {
            conectar();
            ps = con.prepareStatement("INSERT INTO usuariosnatacion VALUES(?,?,?)");
            ps.setInt(1, Integer.parseInt(textField2.getText()));
            ps.setString(2, textField1.getText());
            ps.setString(3, new String(passwordField1.getPassword()));

            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
            }

            ps.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
        }
    }

    public void validarUsuario(String usuarioInput, String contraseñaInput) {
        try {
            conectar();
            String query = "SELECT * FROM usuariosnatacion WHERE usuario = ? AND contraseña = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, usuarioInput);
            ps.setString(2, contraseñaInput);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "¡Bienvenido al sistema!");
                // Abrir ventana de registro
                Registro registro = new Registro();
                registro.setVisible(true);
                this.dispose(); // Cierra la ventana de login
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al validar usuario: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setContentPane(frame.panel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setTitle("Login");
                    frame.setSize(300, 200);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // Para componentes personalizados
    }
}