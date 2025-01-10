import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registro extends JFrame {
    private JPanel registro; // Panel principal
    private JTextField textField1; // Campo de texto para el nombre
    private JTextField textField2; // Campo de texto para la edad
    private JButton registrarButton; // Botón para registrar

    private Connection con; // Conexión a la base de datos

    public Registro() {
        // Configuración de la ventana principal
        setContentPane(registro);
        setTitle("Registro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Acción al presionar el botón "Registrar"
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDatos();
            }
        });
    }

    private void conectar() {
        try {

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taller", // URL de la base de datos
                    "root", // Usuario
                    "Leito_2015" // Contraseña
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + e.getMessage());
        }
    }

    private void registrarDatos() {
        try {
            int edad = Integer.parseInt(textField2.getText());

            // Validar que la edad esté entre 18 y 40 años
            if (edad < 18 || edad > 40) {
                JOptionPane.showMessageDialog(this, "La edad debe estar entre 18 y 40 años.");
                return; // No continuar si la edad no es válida
            }

            conectar(); // Conectar a la base de datos

            // Consulta SQL para insertar los datos
            String sql = "INSERT INTO usuariosnatacion (nombre, edad) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            // Pasar los valores de los campos de texto al SQL
            ps.setString(1, textField1.getText());
            ps.setInt(2, edad);

            // Ejecutar la consulta
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Registro guardado exitosamente");
                limpiarCampos(); // Limpiar los campos de texto
            }

            ps.close(); // Cerrar el PreparedStatement
            con.close(); // Cerrar la conexión
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + e.getMessage());
        }
    }


    private void limpiarCampos() {
        textField1.setText(""); // Limpiar el campo "Nombre"
        textField2.setText(""); // Limpiar el campo "Edad"
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Registro registro = new Registro();
                registro.setVisible(true);
            }
        });
    }
}
