import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Registration extends JDialog {
    private JTextField tfName;
    private JTextField tfSID;
    private JTextField tfEmail;
    private JPasswordField tfPass;
    private JPasswordField tfConPass;
    private JButton btnReg;
    private JButton btnCancel;
    private JPanel regPanel;
    private JButton logInButton;

    public Student student;
    public Registration(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(regPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        btnReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registerrUser();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        setVisible(true);
    }

    private void registerrUser() {
        String name = tfName.getText();
        int SID = (Integer.parseInt(tfSID.getText()));
        String email = tfEmail.getText();
        String pass = String.valueOf(tfPass.getPassword());
        String conPass = String.valueOf(tfConPass.getPassword());

        if(name.isEmpty() || email.isEmpty() || SID==0 || email.isEmpty() || pass.isEmpty() || conPass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pass.equals(conPass)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        student=addUserToDatabase(name,SID,email,pass);
        if(student!=null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,"Failed to register new user","Try Again",JOptionPane.ERROR_MESSAGE);
        }
    }

    private Student addUserToDatabase(String name,int SID, String email,String pass){
        Student student = null;
        final String DB_URL = "jdbc:mysql://localhost/jdbc_gui?serverTimezone=UTC";
        final String USERNAME = "ashik";
        final String PASSWORD = "";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO students (studentID, Name, Email, Password) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, SID);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, pass);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                student = new Student();
                student.studentId= SID;
                student.name = name;
                student.email = email;
                student.password = pass;
            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return student;
    }
}
