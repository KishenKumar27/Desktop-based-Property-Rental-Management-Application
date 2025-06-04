/*
            ////////////////////////////////////
            /// ADMIN FULFILLED REQUIREMENTS ///
            ////////////////////////////////////

    1. Login and Register as Administrator
        - New Admin must wait for approval before they can login
    2. New User Registration Request
        - Approve (Unauthorized -> Authorized User)
        - Reject  (Unauthorized -> Rejected User)
        - Revoke  (Rejected -> Authorized User)
        - Delete  (Unauthorized/Rejected User Information Destroyed)
    3. View Report + Delete Property
        - List of Property Entered into the system                          (Available + Rented Out Property)
        - List of Property According to Property Type + Delete Property     (Available Property)
        - List of Inactive Property + Set Property to be Available          (Rented Out Property)
        - List of Active Property + Delete Property                         (Available Property)
        - List According to Project etc. + Delete Property                  (Available Property)
        - List According to Facilities + Delete Property                    (Available Property)
    4. Add New Property into the System
*/

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Admin{
    private String filterTerm;
    private ArrayList<User> newAdmin = User.readRegistrationCSV("adminRegistration.csv"), admin = User.readUserCSV("admin.csv"), tenant = User.readUserCSV("tenant.csv"), owner = User.readUserCSV("owner.csv"), agent = User.readUserCSV("agent.csv"),
                            rejectedAdmin = User.readRegistrationCSV("rejectedAdmin.csv"), rejectedTenant = User.readRegistrationCSV("rejectedTenant.csv"), rejectedOwner = User.readRegistrationCSV("rejectedOwner.csv"), rejectedAgent = User.readRegistrationCSV("rejectedAgent.csv");
    private ArrayList<Property> property = Property.readPropertyCSV("cyberproperty2.csv");
    private final static boolean SHOULD_FILL = true, SHOULD = true;

    public Admin () {
        JPanel adminPanel = new JPanel(new GridLayout(4,1));
        JFrame adminFrame = new JFrame("Admin Profile Page");
        JLabel label = new JLabel("Choose to Login or Register Admin Account:");
        adminPanel.add(label);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                loginButton.setVisible(true);
                adminFrame.dispose();
                AdminLogin();
                
            }
        });
        JButton registerButton = new JButton("Register as Administrator");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                registerButton.setVisible(true);
                adminFrame.dispose();
                AdminRegister();
            }
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
                new MainPage();
            }
        });

        adminPanel.add(loginButton);
        adminPanel.add(registerButton);
        adminPanel.add(backButton);
        adminFrame.add(adminPanel);
        adminFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        adminFrame.setSize(500, 400);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
        adminFrame.setFocusable(true);
    }

    // Admin Login Page
    private void AdminLogin() {
        JPanel adminPanel = new JPanel(new GridLayout(3,1));
        JFrame adminFrame = new JFrame("Admin Login Page");
        // username
        JLabel userLabel = new JLabel("Registered Username");
        JTextField userText = new JTextField();
        //password
        JLabel passLabel = new JLabel("Registered Password");
        JPasswordField passText = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String adminUser = userText.getText();
                String adminPass = passText.getText();
            
                if(adminUser.equals(User.getUsernameCSV(admin, adminUser, "admin.csv")) && adminPass.equals(User.getPasswordCSV(admin, adminPass, "admin.csv"))) {
                    JOptionPane.showMessageDialog(null, "Admin Login Successful!", "Admin Login Status", JOptionPane.INFORMATION_MESSAGE);
                    adminFrame.dispose();
                    adminMenuPage(); 
                }
                else if(userText.getText().isEmpty() || passText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Username or password is not filled", "Admin Login Status", JOptionPane.ERROR_MESSAGE);
                }
                else if(!(adminUser.equals(User.getUsernameCSV(admin, adminUser, "admin.csv"))) || !(adminPass.equals(User.getPasswordCSV(admin, adminPass, "adminRegistration.csv")))){
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Admin Login Status", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
                new Admin();
            }
        });
        
        adminPanel.add(userLabel);
        adminPanel.add(userText);
        adminPanel.add(passLabel);
        adminPanel.add(passText);
        adminPanel.add(backButton);
        adminPanel.add(loginButton);
        adminFrame.add(adminPanel);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(500, 400);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
        adminFrame.setFocusable(false);
    }

    // Admin Register Page
    private void AdminRegister() {
        JPanel adminPanel = new JPanel(new GridLayout(6,1));
        JFrame adminFrame = new JFrame("Admin Registration Page");

        // name
        JLabel nameLabel = new JLabel("Name");
        JTextField nameText = new JTextField();
        // phone
        JLabel phoneLabel = new JLabel("Phone Number");
        JTextField phoneText = new JTextField();
        // username
        JLabel userLabel = new JLabel("Username");
        JTextField userText = new JTextField();
        //password
        JLabel passLabel = new JLabel("Password");
        JPasswordField passText = new JPasswordField();
        //password confirm
        JLabel passConfirmLabel = new JLabel("Confirm Password");
        JPasswordField passConfirmText = new JPasswordField();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminFrame.setVisible(false);
                new Admin();
            }
        });

        JButton submitButton = new JButton("Submit Registration");
        submitButton.setSize(100, 100);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String adminName = nameText.getText();
                String adminPhone = phoneText.getText();
                String adminUser = userText.getText();
                String adminPass = passText.getText();
                String adminPassConfirm = passConfirmText.getText();
        
                if(nameText.getText().isEmpty() || phoneText.getText().isEmpty() || userText.getText().isEmpty() || passText.getText().isEmpty() || passConfirmText.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Registration is not fully filled", "Admin Registration Status", JOptionPane.ERROR_MESSAGE);
                else {
                    if(adminPass.equals(adminPassConfirm)) {
                        newAdmin.add(new User(false, adminName, adminPhone, adminUser, adminPass, adminPassConfirm));
                        User.saveRegistrationCSV(newAdmin, "adminRegistration.csv");
                        JOptionPane.showMessageDialog(null, "Admin resgistration complete!\nPlease wait for approval. ", "Admin Registration Status", JOptionPane.INFORMATION_MESSAGE);
                        adminFrame.dispose();
                        new Admin();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Password does not match", "Admin Registration Status", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        adminPanel.add(nameLabel);
        adminPanel.add(nameText);
        adminPanel.add(phoneLabel);
        adminPanel.add(phoneText);
        adminPanel.add(userLabel);
        adminPanel.add(userText);
        adminPanel.add(passLabel);
        adminPanel.add(passText);
        adminPanel.add(passConfirmLabel);
        adminPanel.add(passConfirmText);
        adminPanel.add(backButton);
        adminPanel.add(submitButton);
        adminFrame.add(adminPanel);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(500, 400);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
        adminFrame.setFocusable(false);
    }

    // Admin Menu Page
    private void adminMenuPage() {
        JPanel adminMenuPanel = new JPanel(new GridLayout(5,1));
        JFrame adminMenuFrame = new JFrame("Admin Menu Page");
        JLabel adminLabel = new JLabel("Choose task menu:");
        
        JButton userApprovalButton = new JButton("User Registration Request");
        userApprovalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminMenuFrame.setVisible(false);
                userRequestPage();
            }
        });
        JButton viewReportButton = new JButton("View Report + Delete Property");
        viewReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminMenuFrame.setVisible(false);
                viewReport();
            }
        });
        JButton addPropertyButton = new JButton("Add Property");
        addPropertyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminMenuFrame.setVisible(false);
                addProperty();
            }
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminMenuFrame.setVisible(false);
                new MainPage();
            }
        });

        adminMenuPanel.add(adminLabel);
        adminMenuPanel.add(userApprovalButton);
        adminMenuPanel.add(viewReportButton);
        adminMenuPanel.add(addPropertyButton);
        adminMenuPanel.add(backButton);
        adminMenuFrame.add(adminMenuPanel);
        adminMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminMenuFrame.setSize(500, 400);
        adminMenuFrame.setLocationRelativeTo(null);
        adminMenuFrame.setVisible(true);
        adminMenuFrame.setFocusable(false);        
    }

    // View Report Page
    private void viewReport() {
        JPanel panel = new JPanel(new GridLayout(8,1));
        JFrame frame = new JFrame("View Report Page");
        JLabel label = new JLabel("Choose your listing:");

        JButton adminMenuButton1 = new JButton("List of Properties Entered into The System");
        adminMenuButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                showAllListing("cyberproperty2.csv");
            }
        });
        JButton adminMenuButton2 = new JButton("List of Properties According to Property Type + Delete Property");
        adminMenuButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByPropertyType("cyberproperty2.csv");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }            
            }
        });
        JButton adminMenuButton3 = new JButton("List of Inactive Property + Set Property to be Available");
        adminMenuButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                showActiveORInactiveListing("inactiveProperty.csv", "Inactive");
            }
        });
        JButton adminMenuButton4 = new JButton("List of Active Property + Delete Property");
        adminMenuButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                showActiveORInactiveListing("activeProperty.csv", "Active");
            }
        });
        JButton adminMenuButton5 = new JButton("List According to Project etc. + Delete Property");
        adminMenuButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByProject("cyberproperty2.csv");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JButton adminMenuButton6 = new JButton("List According to Facilities + Delete Property");
        adminMenuButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByFacilities("cyberproperty2.csv");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                adminMenuPage();
            }
        });

        panel.add(label);
        panel.add(adminMenuButton1);
        panel.add(adminMenuButton2);
        panel.add(adminMenuButton3);
        panel.add(adminMenuButton4);
        panel.add(adminMenuButton5);
        panel.add(adminMenuButton6);
        panel.add(backButton);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);  

    }

    // User Request Request Page
    private void userRequestPage() {
        JPanel panel = new JPanel(new GridLayout(6,1));
        JFrame frame = new JFrame("User Registration Request Page");
        JLabel label = new JLabel("Choose User Registration:");

        JButton adminButton = new JButton("Admin Registration Request");
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                userRequest(admin, rejectedAdmin, "Admin", "adminRegistration.csv", "admin.csv", "rejectedAdmin.csv");
            }
        });
        JButton tenantButton = new JButton("Tenant Registration Request");
        tenantButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                userRequest(tenant, rejectedTenant, "Tenant", "tenantRegistration.csv", "tenant.csv", "rejectedTenant.csv");
            }
        });
        JButton ownerButton = new JButton("Owner Registration Request");
        ownerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                userRequest(owner, rejectedOwner, "Owner", "ownerRegistration.csv", "owner.csv", "rejectedOwner.csv");
            }
        });
        JButton agentButton = new JButton("Agent Registration Request");
        agentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                userRequest(agent, rejectedAgent, "Agent", "agentRegistration.csv", "agent.csv", "rejectedAgent.csv");
            }
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                adminMenuPage();
            }
        });

        panel.add(label);
        panel.add(adminButton);
        panel.add(tenantButton);
        panel.add(ownerButton);
        panel.add(agentButton);
        panel.add(backButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false); 
    }

    // Current and New User Registration Page
    private void userRequest(ArrayList<User> user, ArrayList<User> rejectedUser, String role, String filepath, String filepath2, String rejectedCSV) {
        JFrame tableFrame = new JFrame("Current and New " + role + " Registration");
        tableFrame.setSize(700, 500);

        JPanel tablePanel = new JPanel();
        BoxLayout verticLayout = new BoxLayout(tablePanel, BoxLayout.Y_AXIS);
        tablePanel.setLayout((verticLayout));
        tableFrame.add(tablePanel);
        
        // Showing (role).csv data in table
        JLabel userCsvLabel = new JLabel("Authorized " + role + " List");
        userCsvLabel.setAlignmentX(JLabel.CENTER);
        userCsvLabel.setBackground(Color.BLACK);
        userCsvLabel.setForeground(Color.WHITE);
        userCsvLabel.setOpaque(true);
        EmptyBorder emptyBorder1 = new EmptyBorder(10,10,10,10);
        userCsvLabel.setBorder(emptyBorder1);
        tablePanel.add(userCsvLabel);

        // Parsing csv data
        DefaultTableModel userCsv_data = new DefaultTableModel();

        // Column
        userCsv_data.addColumn("Name");
        userCsv_data.addColumn("Phone Number");
        userCsv_data.addColumn("Username");
        userCsv_data.addColumn("Password");
        userCsv_data.addColumn("Confirm Password");

        try{
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filepath2));
            CSVParser csvParser= CSVFormat.DEFAULT.parse(inputStreamReader);
            for(CSVRecord csvRecord:csvParser){
                Vector row=new Vector();
                row.add(csvRecord.get(0));
                row.add(csvRecord.get(1));
                row.add(csvRecord.get(2));
                row.add(csvRecord.get(3));
                row.add(csvRecord.get(4));
                userCsv_data.addRow(row);
            }
        }
        catch (Exception e){
            System.out.println("Error in Parsing CSV File");
        }

        JTable jTable1 = new JTable();
        jTable1.setModel(userCsv_data);
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.getViewport().add(jTable1);
        tablePanel.add(jScrollPane1);

        // Showing (role)Registration.csv data in table
        JLabel userRegistrationCsvLabel = new JLabel("Unauthorized " + role + " List");
        userRegistrationCsvLabel.setAlignmentX(JLabel.CENTER);
        userRegistrationCsvLabel.setBackground(Color.RED);
        userRegistrationCsvLabel.setForeground(Color.WHITE);
        userRegistrationCsvLabel.setOpaque(true);
        EmptyBorder emptyBorder2 = new EmptyBorder(10,10,10,10);
        userRegistrationCsvLabel.setBorder(emptyBorder2);
        tablePanel.add(userRegistrationCsvLabel);

        // Parsing csv data        
        JTable jTable2 = new JTable();
        DefaultTableModel userRegistrationCsv_data = (DefaultTableModel)jTable2.getModel();

        // OBTAIN SELECTED COLUMN
        userRegistrationCsv_data.addColumn("Select");
        userRegistrationCsv_data.addColumn("Name");
        userRegistrationCsv_data.addColumn("Phone Number");
        userRegistrationCsv_data.addColumn("Username");
        userRegistrationCsv_data.addColumn("Password");
        userRegistrationCsv_data.addColumn("Confirm Password"); 

        try{ 
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filepath));
            CSVParser csvParser= CSVFormat.DEFAULT.parse(inputStreamReader);
            for(CSVRecord csvRecord:csvParser){
                Vector row = new Vector();
                row.add(csvRecord.get(0));
                row.add(csvRecord.get(1));
                row.add(csvRecord.get(2));
                row.add(csvRecord.get(3));
                row.add(csvRecord.get(4));
                row.add(csvRecord.get(5));
                userRegistrationCsv_data.addRow(row);
            }
        }
        catch (Exception e){
            System.out.println("Error in Parsing CSV File");
        }

        JScrollPane jScrollPane2 = new JScrollPane();
        jScrollPane2.getViewport().add(jTable2);
        tablePanel.add(jScrollPane2);

        // OBTAIN SELECTED ROW
        JButton approveUserButton = new JButton("Approve New " + role);
        approveUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //GET SELECTED ROW
                for(int i = 0; i < jTable2.getRowCount(); i++) {
                    Boolean checked = Boolean.valueOf(jTable2.getValueAt(i, 0).toString());
                    String name = jTable2.getValueAt(i, 1).toString();
                    String phone = jTable2.getValueAt(i, 2).toString();
                    String username = jTable2.getValueAt(i, 3).toString();
                    String password = jTable2.getValueAt(i, 4).toString();
                    String passwordConfirm = jTable2.getValueAt(i, 5).toString();

                    //DISPLAY
                    if(checked) {
                        tableFrame.dispose();
                        user.add(new User(name, phone, username, password, passwordConfirm));
                        User.saveUserCSV(user, filepath2);              // transfer information to (role).csv
                        User.removeAdminRecord(filepath, username);     // code to remove row in (role)Registration.csv
                        JOptionPane.showMessageDialog(null, "New " + role + " request successfully approved!", role + " Approval Status", JOptionPane.INFORMATION_MESSAGE);
                        userRequestPage();
                    }
                }
            }
        });
        approveUserButton.setBackground(Color.GREEN);
        approveUserButton.setOpaque(true);
        approveUserButton.setBorderPainted(false);
        tablePanel.add(approveUserButton);

        JButton deleteButton = new JButton("Delete New " + role);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //GET SELECTED ROW
                for(int i = 0; i < jTable2.getRowCount(); i++) {
                    Boolean checked = Boolean.valueOf(jTable2.getValueAt(i, 0).toString());
                    String username = jTable2.getValueAt(i, 3).toString();

                    //DISPLAY
                    if(checked) {
                        tableFrame.dispose();
                        User.removeAdminRecord(filepath, username);             // code to remove row in (role)Registration.csv
                        JOptionPane.showMessageDialog(null, "New " + role + " request deleted!", role + " Approval Status", JOptionPane.INFORMATION_MESSAGE);
                        userRequestPage();
                    }
                }
            }
        });
        tablePanel.add(deleteButton);

        JButton rejectButton = new JButton("Reject New " + role);
        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //GET SELECTED ROW
                for(int i = 0; i < jTable2.getRowCount(); i++) {
                    Boolean checked = Boolean.valueOf(jTable2.getValueAt(i, 0).toString());
                    String name = jTable2.getValueAt(i, 1).toString();
                    String phone = jTable2.getValueAt(i, 2).toString();
                    String username = jTable2.getValueAt(i, 3).toString();
                    String password = jTable2.getValueAt(i, 4).toString();
                    String passwordConfirm = jTable2.getValueAt(i, 5).toString();

                    //DISPLAY
                    if(checked) {
                        tableFrame.dispose();
                        rejectedUser.add(new User(false, name, phone, username, password, passwordConfirm));
                        User.saveRegistrationCSV(rejectedUser, rejectedCSV);    // transfer information to rejected(role).csv
                        User.removeAdminRecord(filepath, username);             // code to remove row in (role)Registration.csv
                        JOptionPane.showMessageDialog(null, "New " + role + " request rejected!", role + " Approval Status", JOptionPane.INFORMATION_MESSAGE);
                        userRequestPage();
                    }
                }
            }
        });
        tablePanel.add(rejectButton);

        // Showing (role)Registration.csv data in table
        JLabel userRejectCsvLabel = new JLabel("Rejected " + role + " List");
        userRejectCsvLabel.setAlignmentX(JLabel.CENTER);
        userRejectCsvLabel.setBackground(Color.RED);
        userRejectCsvLabel.setForeground(Color.WHITE);
        userRejectCsvLabel.setOpaque(true);
        EmptyBorder emptyBorder3 = new EmptyBorder(10,10,10,10);
        userRejectCsvLabel.setBorder(emptyBorder3);
        tablePanel.add(userRejectCsvLabel);

        // Parsing csv data        
        JTable jTable3 = new JTable();
        DefaultTableModel userRejectedCsv_data = (DefaultTableModel)jTable3.getModel();

        // OBTAIN SELECTED COLUMN
        userRejectedCsv_data.addColumn("Select");
        userRejectedCsv_data.addColumn("Name");
        userRejectedCsv_data.addColumn("Phone Number");
        userRejectedCsv_data.addColumn("Username");
        userRejectedCsv_data.addColumn("Password");
        userRejectedCsv_data.addColumn("Confirm Password"); 

        try{ 
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(rejectedCSV));
            CSVParser csvParser= CSVFormat.DEFAULT.parse(inputStreamReader);
            for(CSVRecord csvRecord:csvParser){
                Vector row = new Vector();
                row.add(csvRecord.get(0));
                row.add(csvRecord.get(1));
                row.add(csvRecord.get(2));
                row.add(csvRecord.get(3));
                row.add(csvRecord.get(4));
                row.add(csvRecord.get(5));
                userRejectedCsv_data.addRow(row);
            }
        }
        catch (Exception e){
            System.out.println("Error in Parsing CSV File");
        }

        JScrollPane jScrollPane3 = new JScrollPane();
        jScrollPane3.getViewport().add(jTable3);
        tablePanel.add(jScrollPane3);

        // OBTAIN SELECTED ROW
        JButton revokeButton = new JButton("Revoke New " + role);
        revokeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //GET SELECTED ROW
                for(int i = 0; i < jTable3.getRowCount(); i++) {
                    Boolean checked = Boolean.valueOf(jTable3.getValueAt(i, 0).toString());
                    String name = jTable3.getValueAt(i, 1).toString();
                    String phone = jTable3.getValueAt(i, 2).toString();
                    String username = jTable3.getValueAt(i, 3).toString();
                    String password = jTable3.getValueAt(i, 4).toString();
                    String passwordConfirm = jTable3.getValueAt(i, 5).toString();

                    //DISPLAY
                    if(checked) {
                        tableFrame.dispose();
                        user.add(new User(name, phone, username, password, passwordConfirm));
                        User.saveUserCSV(user, filepath2);                 // transfer information to (user).csv
                        User.removeAdminRecord(rejectedCSV, username);     // code to remove row in rejected(user).csv
                        JOptionPane.showMessageDialog(null, "New " + role + " request successfully revoked!", role + " Approval Status", JOptionPane.INFORMATION_MESSAGE);
                        userRequestPage();
                    }
                }
            }
        });
        revokeButton.setBackground(Color.GREEN);
        revokeButton.setOpaque(true);
        revokeButton.setBorderPainted(false);
        tablePanel.add(revokeButton);

        JButton deleteButton2 = new JButton("Delete New " + role);
        deleteButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //GET SELECTED ROW
                for(int i = 0; i < jTable3.getRowCount(); i++) {
                    Boolean checked = Boolean.valueOf(jTable3.getValueAt(i, 0).toString());
                    String username = jTable3.getValueAt(i, 3).toString();

                    //DISPLAY
                    if(checked) {
                        tableFrame.dispose();
                        User.removeAdminRecord("rejectedAdmin.csv", username);             // code to remove row in (role)Registration.csv
                        JOptionPane.showMessageDialog(null, "New " + role + " request deleted!", role + " Approval Status", JOptionPane.INFORMATION_MESSAGE);
                        userRequestPage();
                    }
                }
            }
        });
        tablePanel.add(deleteButton2);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tableFrame.dispose();
                userRequestPage();
            }
        });

        jTable2.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                System.out.println("Row : "+jTable2.getSelectedRow()+" | Column : "+jTable2.getSelectedColumn()+" | Current Value : "+jTable2.getValueAt(jTable2.getSelectedRow(),jTable2.getSelectedColumn()).toString());
            }
        });
        
        tablePanel.add(backButton);
        tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
        tableFrame.setFocusable(false);
    } 

    // Show all property Listing entered into the system 
    private void showAllListing(String filepath) {
        JFrame frame = new JFrame("Show All Property Listing");
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                viewReport();
            }
        });

        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 1; i < lines.size();i++) {     
            String id = Property.getIdCSV(i, "cyberproperty2.csv");
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(9, 1));

            panel1.add(new JLabel(("Name                   : " + Property.getNameCSV(i, "cyberproperty2.csv"))));
            panel1.add(new JLabel(("Address               : " + Property.getAddressCSV(i, "cyberproperty2.csv"))));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + Property.getFeetSizeCSV(i, "cyberproperty2.csv"))));
            panel1.add(new JLabel(("Type                    : " + Property.getTypeCSV(i, "cyberproperty2.csv"))));
            panel1.add(new JLabel(("Room                   : " + Property.getRoomCSV(i, "cyberproperty2.csv"))));
            panel1.add(new JLabel(("Bathroom             : " + Property.getBathroomCSV(i, "cyberproperty2.csv"))));
            panel1.add(new JLabel(("Facilities              : " + Property.getFacilitiesCSV(i, "cyberproperty2.csv"))));
            panel1.add(new JLabel(("Rental (RM)          : " + Property.getRentalPriceCSV(i, "cyberproperty2.csv"))));
            panel1.add(new JLabel(("Contact               : " + Property.getContactCSV(i, "cyberproperty2.csv"))));

            panel1.setBorder(blackline1);               
            c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.gridwidth = 3; c.gridx = 0; c.gridy = i;
            panel.add(panel1, c);
        }

        JScrollPane sp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(sp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);
    }

    // Filter Property by Projects + Delete property
    private void sortByProject(String filepath) throws IOException {
        JFrame frame = new JFrame("Filter by Projects");
        JPanel panel = new JPanel();
        ArrayList<String> projectArr = new ArrayList<String>();
        projectArr.add("Select");
        projectArr.add("Casa View Cybersouth");
        projectArr.add("Garden Residence");
        projectArr.add("Jalan Ceria");
        projectArr.add("Jalan Cyber Heights");
        projectArr.add("Jalan Fauna");
        projectArr.add("Jalan Impact");
        projectArr.add("Jalan Mirage");
        projectArr.add("Jalan Pulau Meranti");
        projectArr.add("Jalan Teknokrat");
        projectArr.add("Lakefront Villa");
        projectArr.add("Laman View");
        projectArr.add("Lingkaran Cyber Point");
        projectArr.add("Persiaran");
        projectArr.add("Setia Eco Glades");
 
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                viewReport();
            }
        });

        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);

        JLabel projectLabel = new JLabel("Choose Project");
        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 1;
        panel.add(projectLabel, c);

        JComboBox projectCombo = new JComboBox(projectArr.toArray());
        projectCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==projectCombo) {                        
                    frame.dispose();
                    String filterTerm = (String) projectCombo.getSelectedItem();
                    if(filterTerm.equals("Select")) {
                        try {
                            sortByProject("activeProperty.csv");
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else {
                        property = Property.returnProperty("activeProperty.csv", filterTerm);
                        Property.saveTempCSV(property, "tempProjects.csv");
                        try {
                            sortByProject("activeProperty.csv");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD)  c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 2;
        panel.add(projectCombo, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tempProjects.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {    
            String id = Property.getIdCSV(i, "tempProjects.csv");
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));
        
            panel1.add(new JLabel(("Name                   : " + Property.getNameCSV(i, "tempProjects.csv"))));
            panel1.add(new JLabel(("Address               : " + Property.getAddressCSV(i, "tempProjects.csv"))));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + Property.getFeetSizeCSV(i, "tempProjects.csv"))));
            panel1.add(new JLabel(("Type                    : " + Property.getTypeCSV(i, "tempProjects.csv"))));
            panel1.add(new JLabel(("Room                   : " + Property.getRoomCSV(i, "tempProjects.csv"))));
            panel1.add(new JLabel(("Bathroom             : " + Property.getBathroomCSV(i, "tempProjects.csv"))));
            panel1.add(new JLabel(("Facilities              : " + Property.getFacilitiesCSV(i, "tempProjects.csv"))));
            panel1.add(new JLabel(("Rental (RM)          : " + Property.getRentalPriceCSV(i, "tempProjects.csv"))));
            panel1.add(new JLabel(("Contact               : " + Property.getContactCSV(i, "tempProjects.csv"))));
     
            JButton deleteButton = new JButton("Delete Property");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.dispose();
                    Property.removePropertyRecord("activeProperty.csv", id);     // function call to delete property record from cyberproperty2.csv
                    try {
                        sortByProject("activeProperty.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            deleteButton.setBackground(Color.RED);
            deleteButton.setOpaque(true);
            deleteButton.setBorderPainted(false);
            panel1.add(deleteButton);     
            c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.weightx = 0.0; c.anchor = GridBagConstraints.FIRST_LINE_START; c.gridwidth = 3; c.gridx = 1; c.gridy = i;
            panel1.add(deleteButton, c);   
            panel1.setBorder(blackline1);   
            c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.gridwidth = 3; c.gridx = 0;c.gridy = i+3 ;
            panel.add(panel1, c);
        }

        JScrollPane sp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(sp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);
    }

    // Filter Property by facilities + Delete property
    private void sortByFacilities(String filepath) throws IOException {
        JFrame frame = new JFrame("Filter by Facility");
        JPanel panel = new JPanel();
        ArrayList<String> facilitesArr = new ArrayList<String>();
        
        facilitesArr.add("Select");
        facilitesArr.add("24-Hours Security");
        facilitesArr.add("Parking");
        facilitesArr.add("Shuttle Bus");
        facilitesArr.add("BBQ");
        facilitesArr.add("Mini Market");
        facilitesArr.add("Gymnasium");
        facilitesArr.add("Jogging Track");
        facilitesArr.add("Playground");
        facilitesArr.add("Court");
        facilitesArr.add("Business Centre");
        facilitesArr.add("Multipurpose Hall");
        facilitesArr.add("Library");
        facilitesArr.add("Nursery");
        facilitesArr.add("Cafeteria");
        facilitesArr.add("Club House");
        facilitesArr.add("Pool");
        facilitesArr.add("Jacuzzi");
        facilitesArr.add("Sauna");
        facilitesArr.add("Salon");
 
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                viewReport();
            }
        });

        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);

        JLabel facilityLabel = new JLabel("Choose Facility");
        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 1;
        panel.add(facilityLabel, c);

        JComboBox facilityCombo = new JComboBox(facilitesArr.toArray());
        facilityCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==facilityCombo) {                        
                    frame.dispose();
                    filterTerm = (String) facilityCombo.getSelectedItem();
                    if(filterTerm.equals("Select")) {
                        try {
                            sortByFacilities("activeProperty.csv");
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else {
                        property = Property.returnProperty("activeProperty.csv", filterTerm);
                        Property.saveTempCSV(property, "tempFacilities.csv");
                        try {
                            sortByFacilities("activeProperty.csv");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD)  c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 2;
        panel.add(facilityCombo, c);

        JLabel label = new JLabel("Facility chosen: " + filterTerm);
        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 3;
        panel.add(label, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tempFacilities.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {    
            String id = Property.getIdCSV(i, "tempFacilities.csv");
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));
        
            panel1.add(new JLabel(("Name                   : " + Property.getNameCSV(i, "tempFacilities.csv"))));
            panel1.add(new JLabel(("Address               : " + Property.getAddressCSV(i, "tempFacilities.csv"))));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + Property.getFeetSizeCSV(i, "tempFacilities.csv"))));
            panel1.add(new JLabel(("Type                    : " + Property.getTypeCSV(i, "tempFacilities.csv"))));
            panel1.add(new JLabel(("Room                   : " + Property.getRoomCSV(i, "tempFacilities.csv"))));
            panel1.add(new JLabel(("Bathroom             : " + Property.getBathroomCSV(i, "tempFacilities.csv"))));
            panel1.add(new JLabel(("Facilities              : " + Property.getFacilitiesCSV(i, "tempFacilities.csv"))));
            panel1.add(new JLabel(("Rental (RM)          : " + Property.getRentalPriceCSV(i, "tempFacilities.csv"))));
            panel1.add(new JLabel(("Contact               : " + Property.getContactCSV(i, "tempFacilities.csv"))));
     
            JButton deleteButton = new JButton("Delete Property");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.dispose();
                    Property.removePropertyRecord("activeProperty.csv", id);     // function call to delete property record from cyberproperty2.csv
                    try {
                        sortByFacilities("activeProperty.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            deleteButton.setBackground(Color.RED);
            deleteButton.setOpaque(true);
            deleteButton.setBorderPainted(false);
            panel1.add(deleteButton);     
            c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.weightx = 0.0; c.anchor = GridBagConstraints.FIRST_LINE_START; c.gridwidth = 3; c.gridx = 1; c.gridy = i;
            panel1.add(deleteButton, c);   
            panel1.setBorder(blackline1);   
            c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.gridwidth = 3; c.gridx = 0; c.gridy = i+4;
            panel.add(panel1, c);
        }

        JScrollPane sp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(sp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);
    }

    // Filter Property by property type + Delete property
    private void sortByPropertyType(String filepath)  throws IOException {
        JFrame frame = new JFrame("Filter by property type");
        JPanel panel = new JPanel();
        ArrayList<String> propertyTypeArr = new ArrayList<String>();

        propertyTypeArr.add("Select");
        propertyTypeArr.add("Condominium");
        propertyTypeArr.add("Double Storey");
        propertyTypeArr.add("3 Storey Terrace");
        propertyTypeArr.add("Serviced Residence");
        propertyTypeArr.add("Semi-detached House");
        propertyTypeArr.add("Double storey terrace");
        propertyTypeArr.add("Bungalow");

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                viewReport();
            }
        });

        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);

        JLabel propertyTypeLabel = new JLabel("Choose Property Type");
        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 1;
        panel.add(propertyTypeLabel, c);

        JComboBox propertyTypeCombo = new JComboBox(propertyTypeArr.toArray());
        propertyTypeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==propertyTypeCombo) {
                    frame.dispose();
                    filterTerm = (String) propertyTypeCombo.getSelectedItem();
                    if(filterTerm.equals("Select")) {
                        try {
                            sortByPropertyType("activeProperty.csv");
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else {
                        property = Property.returnProperty("activeProperty.csv", filterTerm);
                        Property.saveTempCSV(property, "tempPropertyType.csv");
                        try {
                            sortByPropertyType("activeProperty.csv");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 2;
        panel.add(propertyTypeCombo, c);

        JLabel label = new JLabel("Property Type Chosen: " + filterTerm);
        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 3;
        panel.add(label, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tempPropertyType.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String id = Property.getIdCSV(i, "tempPropertyType.csv");
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));

            panel1.add(new JLabel(("Name                   : " + Property.getNameCSV(i, "tempPropertyType.csv"))));
            panel1.add(new JLabel(("Address               : " + Property.getAddressCSV(i, "tempPropertyType.csv"))));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + Property.getFeetSizeCSV(i, "tempPropertyType.csv"))));
            panel1.add(new JLabel(("Type                    : " + Property.getTypeCSV(i, "tempPropertyType.csv"))));
            panel1.add(new JLabel(("Room                   : " + Property.getRoomCSV(i, "tempPropertyType.csv"))));
            panel1.add(new JLabel(("Bathroom             : " + Property.getBathroomCSV(i, "tempPropertyType.csv"))));
            panel1.add(new JLabel(("Facilities              : " + Property.getFacilitiesCSV(i, "tempPropertyType.csv"))));
            panel1.add(new JLabel(("Rental (RM)          : " + Property.getRentalPriceCSV(i, "tempPropertyType.csv"))));
            panel1.add(new JLabel(("Contact               : " + Property.getContactCSV(i, "tempPropertyType.csv"))));

            JButton deleteButton = new JButton("Delete Property");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.dispose();
                    Property.removePropertyRecord("activeProperty.csv", id);     // function call to delete property record from cyberproperty2.csv
                    try {
                        sortByPropertyType("activeProperty.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            deleteButton.setBackground(Color.RED);
            deleteButton.setOpaque(true);
            deleteButton.setBorderPainted(false);
            panel1.add(deleteButton);     
            c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.weightx = 0.0; c.anchor = GridBagConstraints.FIRST_LINE_START; c.gridwidth = 3; c.gridx = 1; c.gridy = i;
            panel1.add(deleteButton, c);   
            panel1.setBorder(blackline1);
            c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.weightx = 0.0; c.anchor = GridBagConstraints.FIRST_LINE_START; c.gridwidth = 3; c.gridx = 1; c.gridy = i;
            c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.gridwidth = 3; c.gridx = 0; c.gridy = i+4;
            panel.add(panel1, c);
        }

        JScrollPane sp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(sp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);
    }

    // Show all active property listing + Delete property
    // Show all inactive property listing + Set Property to be Available
    private void showActiveORInactiveListing (String filepath, String activeOrInactive) {
        JFrame frame = new JFrame("Show All " + activeOrInactive + " Listing");
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                viewReport();
            }
        });

        if (SHOULD_FILL) c.fill = GridBagConstraints.HORIZONTAL;
        if (SHOULD) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 1; i < lines.size();i++) {  
            String id = Property.getIdCSV(i, filepath), propName = Property.getNameCSV(i, filepath), propAdd = Property.getAddressCSV(i, filepath), propFeetSize = Property.getFeetSizeCSV(i, filepath), propType = Property.getTypeCSV(i, filepath), propRoom = Property.getRoomCSV(i, filepath), propBathroom = Property.getBathroomCSV(i, filepath), propFacilities = Property.getFacilitiesCSV(i, filepath), propPrice = Property.getRentalPriceCSV(i, filepath), propContact = Property.getContactCSV(i, filepath);
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));

            panel1.add(new JLabel(("Name                   : " + propName)));
            panel1.add(new JLabel(("Address               : " + propAdd)));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + propFeetSize)));
            panel1.add(new JLabel(("Type                    : " + propType)));
            panel1.add(new JLabel(("Room                   : " + propRoom)));
            panel1.add(new JLabel(("Bathroom             : " + propBathroom)));
            panel1.add(new JLabel(("Facilities              : " + propFacilities)));
            panel1.add(new JLabel(("Rental (RM)          : " + propPrice)));
            panel1.add(new JLabel(("Contact               : " + propContact)));    

            if(filepath.equals("activeProperty.csv")) {
                JButton deleteButton = new JButton("Delete Property");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        frame.dispose();
                        Property.removePropertyRecord("activeProperty.csv", id);     // function call to delete property record from activeProperty.csv
                        showActiveORInactiveListing("activeProperty.csv", "Active");
                    }
                });
                deleteButton.setBackground(Color.RED);
                deleteButton.setOpaque(true);
                deleteButton.setBorderPainted(false);
                panel1.add(deleteButton);     
                c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.weightx = 0.0; c.anchor = GridBagConstraints.FIRST_LINE_START; c.gridwidth = 3; c.gridx = 1; c.gridy = i;
                panel1.add(deleteButton, c);   
            }
            else{
                JButton setAvailabilityButton = new JButton("Set Property to be Available");
                setAvailabilityButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        ArrayList<Property> activeProperty = Property.readTempCSV("activeProperty.csv");
                        activeProperty.add(new Property(id, propName, propAdd, propFeetSize, propType, propRoom, propBathroom, propFacilities, propPrice, propContact));
                        Property.saveTempCSV(activeProperty, "activeProperty.csv");
                        Property.removePropertyRecord("inactiveProperty.csv", id);     // function call to delete property record from inactiveProperty.csv
                        frame.dispose();
                        showActiveORInactiveListing("inactiveProperty.csv", "Inactive");
                    }
                });
                setAvailabilityButton.setBackground(Color.GREEN);
                setAvailabilityButton.setOpaque(true);
                setAvailabilityButton.setBorderPainted(false);
                panel1.add(setAvailabilityButton);     
                c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.weightx = 0.0; c.anchor = GridBagConstraints.FIRST_LINE_START; c.gridwidth = 3; c.gridx = 1; c.gridy = i;
                panel1.add(setAvailabilityButton, c);   
            }

            panel1.setBorder(blackline1);   
            c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.gridwidth = 3; c.gridx = 0; c.gridy = i;
            panel.add(panel1, c);
        }

        JScrollPane sp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(sp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);
    }

    // Add new property into the system for tenant to rent
    private void addProperty() {
        JPanel panel = new JPanel(new GridLayout(11,1));
        JFrame frame = new JFrame("Add New Property Page");
        JLabel note = new JLabel("NOTE: Do not include comma ','");

        // property id (6 digits random)
        int id = getRandomNumber(100000, 999999);
        String propId = Integer.toString(id);
        // property name
        JLabel propNameLabel = new JLabel("Property Name");
        JTextField propNameText = new JTextField(); 
        // property address
        JLabel addLabel = new JLabel("Address (without comma)");
        JTextField addText = new JTextField();
        // property type/category
        JLabel typeLabel = new JLabel("Property category or type");
        JTextField typeText = new JTextField();
        // property size
        JLabel sizeLabel = new JLabel("Property Size");
        JTextField sizeText = new JTextField();
        // rooms
        JLabel roomLabel = new JLabel("Number of rooms");
        JTextField roomText = new JTextField();
        // bathrooms
        JLabel broomLabel = new JLabel("Number of bathrooms");
        JTextField broomText = new JTextField();
        // facilities
        JLabel facilitiesLabel = new JLabel("Facilities provided");
        JTextField facilitiesText = new JTextField();
        // rental fee
        JLabel feeLabel = new JLabel("Rental Fee (RM)");
        JTextField feeText = new JTextField();
        // property owner phone number
        JLabel contactLabel = new JLabel("Phone Number (601)");
        JTextField contactText = new JTextField();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                adminMenuPage();
            }
        });
        JButton uploadButton = new JButton("Upload Property");
        uploadButton.setSize(100, 100);
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String propName = propNameText.getText();
                String propAddress = addText.getText();
                String propType = typeText.getText();
                String propSize = sizeText.getText();
                String propRoom = roomText.getText();
                String propBroom = broomText.getText();
                String propFacilities = facilitiesText.getText();
                String propFee = feeText.getText();
                String propContact = contactText.getText();
        
                if(propNameText.getText().isEmpty() || addText.getText().isEmpty() || typeText.getText().isEmpty() || sizeText.getText().isEmpty()|| roomText.getText().isEmpty() || broomText.getText().isEmpty() || facilitiesText.getText().isEmpty() || feeText.getText().isEmpty() || contactText.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Property details is incomplete", "Property Status", JOptionPane.ERROR_MESSAGE);
                else {
                        //save at cyberproperty & activeProperty
                        property.add(new Property(propId, propName, propAddress, propSize, propType, propRoom, propBroom, propFacilities, propFee, propContact));
                        Property.saveTempCSV(property, "cyberproperty2.csv"); 
                        Property.saveTempCSV(property, "activeProperty.csv");
                        JOptionPane.showMessageDialog(null, "New property uploaded!", "Property Status", JOptionPane.INFORMATION_MESSAGE);
                        frame.setVisible(false);
                        addProperty();
                }
            }
        });
        panel.add(propNameLabel);
        panel.add(propNameText);
        panel.add(addLabel);
        panel.add(addText);
        panel.add(typeLabel);
        panel.add(typeText);
        panel.add(sizeLabel);
        panel.add(sizeText);
        panel.add(roomLabel);
        panel.add(roomText);
        panel.add(broomLabel);
        panel.add(broomText);
        panel.add(facilitiesLabel);
        panel.add(facilitiesText);
        panel.add(feeLabel);
        panel.add(feeText);
        panel.add(contactLabel);
        panel.add(contactText);
        panel.add(backButton);
        panel.add(uploadButton);
        panel.add(note);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);
    }

    // generate 6 digit random number for new property ID
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}