/*
            ////////////////////////////////////
            /// TENANT FULFILLED REQUIREMENTS ///
            ////////////////////////////////////

    1. Login and Register as Potential Tenant
        - New Tenant must wait for approval of the administrator before they can login
    2. Manipulation of profile details
        - View Profile Details
        - Edit Profile Details
    3. View Report + Rent Property
        - List of Property According to Property Type + Rent Property      (Available Property)
        - List According to Rental Rate + Rent Property                    (Available Property)
        - List According to Rental Fee + Rent Property                     (Available Property)
        - List According to Project etc. + Rent Property                   (Available Property)
        - List According to Facilities + Rent Property                     (Available Property)
*/

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// This class(including all the methods and attributes) has been created by Kishen Kumar A/L Sivalingam (SID:1191101423)
public class Tenant extends User {

    // Attributes
    private String tenantName, tenantPhone, tenantUser, tenantPass, tenantPassConfirm, tenantCurrentName, tenantCurrentPhone, filterTerm;
    private ArrayList<User> newTenant = readRegistrationCSV("tenantRegistration.csv"), tenant = readUserCSV("tenant.csv");
    private Boolean tenantStatus = false;
    private ArrayList<Property> property = readPropertyCSV("cyberproperty2.csv"), sortedProperty = new ArrayList<>();
    private final static boolean shouldFill = true, shouldWeightX = true;

    // Tenant Registration Page (Constructor)
    public Tenant() {
        JFrame frame = new JFrame("Tenant Registration Page");
        JPanel panel = new JPanel();
        frame.setSize(400, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 20, 150, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(150, 20, 165, 25);
        panel.add(nameText);

        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setBounds(10, 50, 150, 25);
        panel.add(phoneLabel);

        JTextField phoneText = new JTextField(20);
        phoneText.setBounds(150, 50, 165, 25);
        panel.add(phoneText);

        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(10, 80, 150, 25);
        panel.add(userNameLabel);

        JTextField userNameText = new JTextField(20);
        userNameText.setBounds(150, 80, 165, 25);
        panel.add(userNameText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 110, 150, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 110, 165, 25);
        panel.add(passwordText);

        JLabel conPasswordLabel = new JLabel("Confirm Password");
        conPasswordLabel.setBounds(10, 140, 150, 25);
        panel.add(conPasswordLabel);

        JPasswordField conPasswordText = new JPasswordField(20);
        conPasswordText.setBounds(150, 140, 165, 25);
        panel.add(conPasswordText);

        JButton button = new JButton("Register");
        button.setBounds(220, 170, 90, 25);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantName = nameText.getText();
                tenantPhone = phoneText.getText();
                tenantUser = userNameText.getText();
                tenantPass = passwordText.getText();
                tenantPassConfirm = conPasswordText.getText();

                // Condition: If the name, phone number, username, password or confirmation password text field is empty, Display 'Registration is not fully filled'
                if(nameText.getText().isEmpty() || phoneText.getText().isEmpty() || userNameText.getText().isEmpty() || passwordText.getText().isEmpty() || conPasswordText.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Registration is not fully filled", "Tenant Registration Status", JOptionPane.ERROR_MESSAGE);
                else if(tenantPhone.equals(getTenantPhoneCSV(newTenant, tenantPhone, "tenantRegistration.csv"))){
                        JOptionPane.showMessageDialog(null, "Phone Number is already used", "Tenant Edit Status", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    //Condition: If the password is equals to confirmation password, save the tenant registration details into the csv database and display "Tenant registration complete!"
                    if(tenantPass.equals(tenantPassConfirm)) {
                        newTenant.add(new User(tenantStatus, tenantName, tenantPhone, tenantUser, tenantPass, tenantPassConfirm)); // Add the new tenant registration details under 'newTenant' Arraylist
                        User.saveRegistrationCSV(newTenant, "tenantRegistration.csv"); // Save all the registration details of tenant from 'newTenant' Arraylist to 'tenantRegistration.csv'
                        JOptionPane.showMessageDialog(null, "Tenant registration complete!", "Tenant Registration Status", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        new Tenant();
                    }
                    // Condition: If the password is not equals to confirmation password, Display "Password does not match"
                    else {
                        JOptionPane.showMessageDialog(null, "Password does not match", "Tenant Registration Status", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(button);

        JButton backButton = new JButton("Back");
        backButton.setBounds(120, 170, 90, 25);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MainPage();
            }
        });
        panel.add(backButton);

        JLabel loginLabel = new JLabel("Already have an account? Click the login button below");
        loginLabel.setBounds(10, 210, 350, 25);
        panel.add(loginLabel);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(130, 240, 90, 25);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                loginButton.setVisible(true);
                frame.dispose();
                tenantLogin();
            }
        });
        panel.add(loginButton);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    // Getters and Setters for username and password details
    public void setCurrentUserName(String tenantUser) {
        this.tenantUser = tenantUser;
    }

    public String getCurrentUserName() {
        return tenantUser;
    }

    public void setCurrentPassword(String tenantPass) {
        this.tenantPass = tenantPass;
    }

    public String getCurrentPassword() {
        return tenantPass;
    }

    // Tenant Login Page
    private void tenantLogin() {
        JFrame tenantFrame = new JFrame("Tenant Login Page");
        JPanel tenantPanel = new JPanel();

        tenantPanel.setLayout(null);


        // username
        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(10, 20, 80, 25);

        JTextField userNameText = new JTextField();
        userNameText.setBounds(130, 20, 165, 25);

        //password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 90, 25);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(130, 50, 165, 25);

        // back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(120, 90, 90, 25);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantFrame.dispose();
                new Tenant();
            }
        });

        //login button (to submit)
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(220, 90, 90, 25);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantUser = userNameText.getText();
                tenantPass = passwordText.getText();

                // Condition: If the entered username and entered password are in the csv database, Display 'Tenant Login Successful!' and direct into the tenant menu page UI
                if (tenantUser.equals(getUsernameCSV(tenant, tenantUser, "tenant.csv")) && tenantPass.equals(getPasswordCSV(tenant, tenantPass, "tenant.csv"))) {
                    JOptionPane.showMessageDialog(null, "Tenant Login Successful!", "Tenant Login Status", JOptionPane.INFORMATION_MESSAGE);
                    tenantFrame.dispose();
                    setCurrentUserName(tenantUser); // Set the entered username to current username
                    setCurrentPassword(tenantPass); // Set the entered password to current password
                    tenantMenuPage();
                }
                // Condition: If the username or password text field is empty, Display 'Username or password is not filled'
                else if(userNameText.getText().isEmpty() || passwordText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Username or password is not filled", "Tenant Login Status", JOptionPane.ERROR_MESSAGE);
                }
                // Condition: If the entered username or entered password is not in the csv database, Display 'Invalid username or password'
                else if(!(tenantUser.equals(getUsernameCSV(tenant, tenantUser, "tenant.csv"))) || !(tenantPass.equals(getPasswordCSV(tenant, tenantPass, "tenant.csv")))){
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Tenant Login Status", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        tenantPanel.add(userNameLabel);
        tenantPanel.add(userNameText);
        tenantPanel.add(passwordLabel);
        tenantPanel.add(passwordText);
        tenantPanel.add(backButton);
        tenantPanel.add(loginButton);

        tenantFrame.add(tenantPanel);
        tenantFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tenantFrame.setSize(400, 200);
        tenantFrame.setLocationRelativeTo(null);
        tenantFrame.setVisible(true);
        tenantFrame.setFocusable(false);
    }

    // Tenant Menu Page
    private void tenantMenuPage() {
        JFrame tenantMenuFrame = new JFrame("Tenant Menu Page");
        JPanel tenantMenuPanel = new JPanel();

        tenantMenuPanel.setLayout(new GridLayout(5,1));

        JLabel editLabel = new JLabel("Choose the task menu: ");
        tenantMenuPanel.add(editLabel);

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tenantMenuFrame.dispose();
                tenantViewProfile();
            }
        });
        tenantMenuPanel.add(viewProfileButton);

        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantMenuFrame.dispose();
                tenantEditProfile();
            }
        });
        tenantMenuPanel.add(editProfileButton);

        JButton rentPropertyButton = new JButton("Rent Property");
        rentPropertyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantMenuFrame.setVisible(false);
                rentProperty();
            }
        });
        tenantMenuPanel.add(rentPropertyButton);

        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantMenuFrame.dispose();
                JOptionPane.showMessageDialog(null, "Logged out successfully!", "Tenant Login Status", JOptionPane.INFORMATION_MESSAGE);
                new Tenant();
            }
        });
        tenantMenuPanel.add(logoutButton);

        tenantMenuFrame.add(tenantMenuPanel);
        tenantMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tenantMenuFrame.setSize(400, 300);
        tenantMenuFrame.setLocationRelativeTo(null);
        tenantMenuFrame.setVisible(true);
        tenantMenuFrame.setFocusable(false);
    }

    // Tenant View Profile Page
    private void tenantViewProfile() {
        JFrame tenantViewFrame = new JFrame("Tenant View Profile Page");
        JPanel tenantViewPanel = new JPanel();

        tenantViewPanel.setLayout(null);

        String userName = getCurrentUserName(); // Get the current username
        String password = getCurrentPassword(); // Get the current password

        tenantName = getCurrentName(userName, "tenantRegistration.csv"); // Get the current name
        tenantPhone = getCurrentPhoneNumber(userName, "tenantRegistration.csv"); // Get the current phone number

        JLabel viewLabel = new JLabel("Profile");
        viewLabel.setBounds(180, 10, 300, 25);
        tenantViewPanel.add(viewLabel);

        JLabel nameLabel = new JLabel("Name: " + tenantName);
        nameLabel.setBounds(10, 50, 200, 25);
        tenantViewPanel.add(nameLabel);

        JLabel phoneLabel = new JLabel("Phone Number: " + tenantPhone);
        phoneLabel.setBounds(10, 90, 200, 25);
        tenantViewPanel.add(phoneLabel);

        JLabel userNameLabel = new JLabel("Username: " + userName);
        userNameLabel.setBounds(10, 130, 200, 25);
        tenantViewPanel.add(userNameLabel);

        JLabel passwordLabel = new JLabel("Password: " + password);
        passwordLabel.setBounds(10, 170, 200, 25);
        tenantViewPanel.add(passwordLabel);

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 210, 90, 25);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantViewFrame.dispose();
                tenantMenuPage();
            }
        });
        tenantViewPanel.add(backButton);

        tenantViewFrame.add(tenantViewPanel);
        tenantViewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tenantViewFrame.setSize(400, 300);
        tenantViewFrame.setVisible(true);
        tenantViewFrame.setLocationRelativeTo(null);
        tenantViewFrame.setFocusable(false);
    }

    // Used for checking purpose whether the phone number is already used/registered by others or a new phone number
    private static String getTenantPhoneCSV(ArrayList<User> newTenant, String phoneNumber, String filepath) {
        String tenantName = "", tenantPhone = "", tenantUsername = "", tenantPass = "", tenantPassConfirm = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tenant.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            tenantName = String.valueOf(items[0]);
            tenantPhone = String.valueOf(items[1]);
            tenantUsername = String.valueOf(items[2]);
            tenantPass = String.valueOf(items[3]);
            tenantPassConfirm = String.valueOf(items[4]);

            // If entered phone number is already in 'tenant.csv', return it
            if(tenantPhone.equals(phoneNumber)) {
                return tenantPhone;
            }
        }
        return null;
    }

    // Used for checking purpose whether the username is already used/registered by others or a new username
    private static String getTenantUsernameCSV(ArrayList<User> newTenant, String name, String filepath) {
        String tenantName = "", tenantPhone = "", tenantUsername = "", tenantPass = "", tenantPassConfirm = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tenant.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            tenantName = String.valueOf(items[0]);
            tenantPhone = String.valueOf(items[1]);
            tenantUsername = String.valueOf(items[2]);
            tenantPass = String.valueOf(items[3]);
            tenantPassConfirm = String.valueOf(items[4]);

            // If entered username is already in 'tenant.csv', return it
            if(tenantUsername.equals(name)) {
                return tenantUsername;
            }
        }
        return null;
    }

    // Acquire name of the currently logged in tenant
    private static String getCurrentName(String userName, String filepath) {
        String tenantName = "", tenantPhone = "", tenantUsername = "", tenantPass = "", tenantPassConfirm = "";
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get("tenant.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            tenantName = String.valueOf(items[0]);
            tenantPhone = String.valueOf(items[1]);
            tenantUsername = String.valueOf(items[2]);
            tenantPass = String.valueOf(items[3]);
            tenantPassConfirm = String.valueOf(items[4]);

            // If entered username is same as the username in 'tenant.csv', return the name of the specific tenant where this username is used
            if(tenantUsername.equals(userName)) {
                return tenantName;
            }
        }
        return null;
    }

    // Acquire phone number of the currently logged in tenant
    private static String getCurrentPhoneNumber(String userName, String filepath) {
        String tenantName = "", tenantPhone = "", tenantUsername = "", tenantPass = "", tenantPassConfirm = "";
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get("tenant.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            tenantName = String.valueOf(items[0]);
            tenantPhone = String.valueOf(items[1]);
            tenantUsername = String.valueOf(items[2]);
            tenantPass = String.valueOf(items[3]);
            tenantPassConfirm = String.valueOf(items[4]);

            // If entered username is same as the username in 'tenant.csv', return the phone number of the specific tenant where this username is used
            if(tenantUsername.equals(userName)) {
                return tenantPhone;
            }
        }
        return null;
    }

    // Tenant Edit Profile Page
    private void tenantEditProfile() {
        JFrame tenantEditFrame = new JFrame("Tenant Edit Profile Page");
        JPanel tenantEditPanel = new JPanel();

        tenantEditPanel.setLayout(new GridLayout(6,1));

        JLabel editLabel = new JLabel("Choose the detail that you wanted to edit: ");
        tenantEditPanel.add(editLabel);

        JButton nameButton = new JButton("Name");
        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editName();
                tenantEditFrame.dispose();
            }
        });
        tenantEditPanel.add(nameButton);

        JButton phoneButton = new JButton("Phone Number");
        phoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPhoneNumber();
                tenantEditFrame.dispose();
            }
        });
        tenantEditPanel.add(phoneButton);

        JButton userNameButton = new JButton("Username");
        userNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editUsername();
                tenantEditFrame.dispose();
            }
        });
        tenantEditPanel.add(userNameButton);


        JButton passwordButton = new JButton("Password");
        passwordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPassword();
                tenantEditFrame.dispose();
            }
        });
        tenantEditPanel.add(passwordButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tenantEditFrame.dispose();
                tenantMenuPage();
            }

        });
        tenantEditPanel.add(backButton);


        tenantEditFrame.add(tenantEditPanel);
        tenantEditFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tenantEditFrame.setSize(500, 300);
        tenantEditFrame.setLocationRelativeTo(null);
        tenantEditFrame.setVisible(true);
        tenantEditFrame.setFocusable(false);

    }

    // 'Edit Name' page
    private void editName() {
        JFrame nameFrame = new JFrame("Edit Name");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String userName = getCurrentUserName();

        tenantCurrentName = getCurrentName(userName, "tenantRegistration.csv");
        JLabel oldNameLabel = new JLabel("Current Name: " + tenantCurrentName);
        oldNameLabel.setBounds(0, 20, 250, 25);
        panel.add(oldNameLabel);

        JLabel newNameLabel = new JLabel("New Name");
        newNameLabel.setBounds(0, 60, 150, 25);
        panel.add(newNameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(65, 60, 165, 25);
        panel.add(nameText);


        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(90, 100, 90, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameFrame.dispose();
                tenantEditProfile();
            }
        });
        panel.add(cancelButton);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(200, 100, 90, 25);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantName = nameText.getText();
                // Condition: If the name text field is empty, display 'Name is not filled'
                if(nameText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Name is not filled", "Tenant Edit Status", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    updateNameCSV(tenantName, "tenantRegistration.csv"); // Replace the old name of the specific tenant with the newly entered name in the 'tenant.csv'
                    JOptionPane.showMessageDialog(null, "Name changed successfully!", "Tenant Edit Status", JOptionPane.INFORMATION_MESSAGE);
                    nameFrame.dispose();
                    tenantLogin();
                }
            }
        });
        panel.add(submitButton);

        nameFrame.setSize(400, 200);
        nameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nameFrame.add(panel);
        nameFrame.setVisible(true);
        nameFrame.setLocationRelativeTo(null);
    }

    // Edit name of the currently logged in tenant and update it into the 'tenant.csv'
    protected void updateNameCSV(String name, String filepath) {
        ArrayList<User> newTenant = new ArrayList<>();

        String userName = getCurrentUserName();

        String tenantName = "", tenantPhone = "", tenantUsername = "", tenantPass = "", tenantPassConfirm = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tenant.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            tenantName = String.valueOf(items[0]);
            tenantPhone = String.valueOf(items[1]);
            tenantUsername = String.valueOf(items[2]);
            tenantPass = String.valueOf(items[3]);
            tenantPassConfirm = String.valueOf(items[4]);

            // If entered username is same as the username in 'tenant.csv', Replace the old name of the specific tenant where this username is used with the newly entered name
            if (tenantUsername.equals(userName)) {
                tenantName = name;
            }
            newTenant.add(new User(tenantName, tenantPhone, tenantUsername, tenantPass, tenantPassConfirm));
        }

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < newTenant.size(); j++) {
            sb.append(newTenant.get(j).toStringCSV2() + "\n");
        }
        try {
            Files.write(Paths.get("tenant.csv"), sb.toString().getBytes());
        } catch (IOException e) {
            System.out.println("ERROR in saving to tenant.csv");
        }

    }

    // 'Edit Phone Number' page
    private void editPhoneNumber() {
        JFrame phoneFrame = new JFrame("Edit Phone Number");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String userName = getCurrentUserName();

        tenantCurrentPhone = getCurrentPhoneNumber(userName, "tenantRegistration.csv");
        JLabel oldPhoneLabel = new JLabel("Current Phone Number: " + tenantCurrentPhone);
        oldPhoneLabel.setBounds(0, 20, 250, 25);
        panel.add(oldPhoneLabel);

        JLabel  newPhoneLabel = new JLabel("New Phone Number");
        newPhoneLabel.setBounds(0, 60, 150, 25);
        panel.add(newPhoneLabel);

        JTextField phoneText = new JTextField(20);
        phoneText.setBounds(120, 60, 165, 25);
        panel.add(phoneText);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(90, 100, 90, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                phoneFrame.dispose();
                tenantEditProfile();
            }
        });
        panel.add(cancelButton);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(200, 100, 90, 25);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantPhone = phoneText.getText();
                // Condition: If the phone number text field is empty, display 'Phone Number is not filled'
                if(phoneText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Phone Number is not filled", "Tenant Edit Status", JOptionPane.ERROR_MESSAGE);
                }
                // Condition: If the entered phone number is in the csv database, Display 'Phone Number is already used' and ask for the new phone number
                else if(tenantPhone.equals(getTenantPhoneCSV(newTenant, tenantPhone, "tenantRegistration.csv"))){
                    JOptionPane.showMessageDialog(null, "Phone Number is already used", "Tenant Edit Status", JOptionPane.ERROR_MESSAGE);
                }
                // Condition: If the entered phone number is not in the csv database, update the phone number, display 'Phone Number changed successfully!' and direct into the tenant login page UI
                else if(!(tenantPhone.equals(getTenantPhoneCSV(newTenant, tenantPhone, "tenantRegistration.csv")))) {
                    updatePhoneCSV(tenantPhone, "tenantRegistration.csv");
                    JOptionPane.showMessageDialog(null, "Phone Number changed successfully!", "Tenant Edit Status", JOptionPane.INFORMATION_MESSAGE);
                    phoneFrame.dispose();
                    tenantLogin();
                }
            }
        });
        panel.add(submitButton);

        phoneFrame.setSize(400, 200);
        phoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        phoneFrame.add(panel);
        phoneFrame.setVisible(true);
        phoneFrame.setLocationRelativeTo(null);
    }

    // Edit Phone Number of the currently logged in tenant and update it into the 'tenant.csv'
    protected void updatePhoneCSV(String phoneNumber, String filepath) {
        ArrayList<User> newTenant = new ArrayList<>();

        String userName = getCurrentUserName();

        String tenantName = "", tenantPhone = "", tenantUsername = "", tenantPass = "", tenantPassConfirm = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tenant.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            tenantName = String.valueOf(items[0]);
            tenantPhone = String.valueOf(items[1]);
            tenantUsername = String.valueOf(items[2]);
            tenantPass = String.valueOf(items[3]);
            tenantPassConfirm = String.valueOf(items[4]);

            // If entered username is same as the username in 'tenant.csv', Replace the old phone number of the specific tenant where this username is used with the newly entered phone number
            if (tenantUsername.equals(userName)) {
                tenantPhone = phoneNumber;
            }
            newTenant.add(new User(tenantName, tenantPhone, tenantUsername, tenantPass, tenantPassConfirm));
        }

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < newTenant.size(); j++) {
            sb.append(newTenant.get(j).toStringCSV2() + "\n");
        }
        try {
            Files.write(Paths.get("tenant.csv"), sb.toString().getBytes());
        } catch (IOException e) {
            System.out.println("ERROR in saving to tenant.csv");
        }

    }

    // 'Edit Username' page
    private void editUsername() {
        JFrame userNameFrame = new JFrame("Edit Username");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String userName = getCurrentUserName();

        JLabel oldUserNameLabel = new JLabel("Current Username: " + userName);
        oldUserNameLabel.setBounds(0, 20, 180, 25);
        panel.add(oldUserNameLabel);

        JLabel newUserNameLabel = new JLabel("New Username");
        newUserNameLabel.setBounds(0, 60, 150, 25);
        panel.add(newUserNameLabel);

        JTextField userNameText = new JTextField(20);
        userNameText.setBounds(90, 60, 165, 25);
        panel.add(userNameText);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(90, 100, 90, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userNameFrame.dispose();
                tenantEditProfile();
            }
        });
        panel.add(cancelButton);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(200, 100, 90, 25);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantUser = userNameText.getText();
                // Condition: If the username text field is empty, display 'Username is not filled'
                if(userNameText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Username is not filled", "Tenant Edit Status", JOptionPane.ERROR_MESSAGE);
                }
                // Condition: If the entered username is in the csv database, Display 'Username is already used'
                else if(tenantUser.equals(getTenantUsernameCSV(newTenant, tenantUser, "tenantRegistration.csv"))){
                    JOptionPane.showMessageDialog(null, "Username is already used", "Tenant Edit Status", JOptionPane.ERROR_MESSAGE);
                }
                // Condition: If the entered username is not in the csv database, update the username, and display 'Username changed successfully!'
                else if(!(tenantUser.equals(getTenantUsernameCSV(newTenant, tenantUser, "tenantRegistration.csv")))) {
                    updateUserNameCSV(userName, tenantUser,"tenantRegistration.csv");
                    JOptionPane.showMessageDialog(null, "Username changed successfully!", "Tenant Edit Status", JOptionPane.INFORMATION_MESSAGE);
                    userNameFrame.dispose();
                    tenantLogin();
                }

            }
        });
        panel.add(submitButton);

        userNameFrame.setSize(400, 200);
        userNameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userNameFrame.add(panel);
        userNameFrame.setVisible(true);
        userNameFrame.setLocationRelativeTo(null);
    }

    // Edit username of the currently logged in tenant and update it into the 'tenant.csv'
    protected void updateUserNameCSV(String oldUserName, String newUserName, String filepath) {
        ArrayList<User> newTenant = new ArrayList<>();


        String tenantName = "", tenantPhone = "", tenantUsername = "", tenantPass = "", tenantPassConfirm = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tenant.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            tenantName = String.valueOf(items[0]);
            tenantPhone = String.valueOf(items[1]);
            tenantUsername = String.valueOf(items[2]);
            tenantPass = String.valueOf(items[3]);
            tenantPassConfirm = String.valueOf(items[4]);

            // If entered username is same as the username in 'tenant.csv', Replace the old username of the specific tenant with the newly entered username
            if (tenantUsername.equals(oldUserName)) {
                tenantUsername = newUserName;
            }
            newTenant.add(new User(tenantName, tenantPhone, tenantUsername, tenantPass, tenantPassConfirm));
        }

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < newTenant.size(); j++) {
            sb.append(newTenant.get(j).toStringCSV2() + "\n");
        }
        try {
            Files.write(Paths.get("tenant.csv"), sb.toString().getBytes());
        } catch (IOException e) {
            System.out.println("ERROR in saving to tenant.csv");
        }

    }

    // 'Edit Password' Page
    private void editPassword() {
        JFrame passwordFrame = new JFrame("Edit Password");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String password = getCurrentPassword();

        JLabel oldPasswordLabel = new JLabel("Current Password: " + password);
        oldPasswordLabel.setBounds(0, 20, 180, 25);
        panel.add(oldPasswordLabel);

        JLabel newPasswordLabel = new JLabel("New Password");
        newPasswordLabel.setBounds(0, 60, 150, 25);
        panel.add(newPasswordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(90, 60, 165, 25);
        panel.add(passwordText);

        JLabel conPasswordLabel = new JLabel("Confirm New Password");
        conPasswordLabel.setBounds(0, 100, 150, 25);
        panel.add(conPasswordLabel);

        JPasswordField conPasswordText = new JPasswordField(20);
        conPasswordText.setBounds(140, 100, 165, 25);
        panel.add(conPasswordText);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(90, 140, 90, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passwordFrame.dispose();
                tenantEditProfile();
            }
        });
        panel.add(cancelButton);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(200, 140, 90, 25);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantPass = passwordText.getText();
                tenantPassConfirm = conPasswordText.getText();
                // Condition: If the confirmation password or password text field is empty, Display 'Password or/and Confirmation Password is not filled'
                if(passwordText.getText().isEmpty() || conPasswordText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Password or/and Confirmation Password is not filled", "Tenant Edit Status", JOptionPane.ERROR_MESSAGE);
                }
                // Condition: If the entered password and entered confirmation password are not the same, Display 'Password and Confirmation Password are not same'
                else if(!(tenantPass.equals(tenantPassConfirm))){
                    JOptionPane.showMessageDialog(null, "Password and Confirmation Password are not same", "Tenant Login Status", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    updatePasswordCSV(password, tenantPass, tenantPassConfirm, "tenantRegistration.csv"); // update the password and confirmation password of the specific tenant, display 'Password changed successfully!' and direct into the tenant login page UI
                    JOptionPane.showMessageDialog(null, "Password changed successfully!", "Tenant Edit Status", JOptionPane.INFORMATION_MESSAGE);
                    passwordFrame.dispose();
                    tenantLogin();
                }
            }
        });
        panel.add(submitButton);

        passwordFrame.setSize(400, 250);
        passwordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        passwordFrame.add(panel);
        passwordFrame.setVisible(true);
        passwordFrame.setLocationRelativeTo(null);
    }

    // Edit password of the currently logged in tenant and update it into the 'tenant.csv'
    protected void updatePasswordCSV(String oldPassword, String newPassword, String newPassConfirm, String filepath) {
        ArrayList<User> newTenant = new ArrayList<>();
        String tenantName = "", tenantPhone = "", tenantUsername = "", tenantPass = "", tenantPassConfirm = "";
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get("tenant.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            tenantName = String.valueOf(items[0]);
            tenantPhone = String.valueOf(items[1]);
            tenantUsername = String.valueOf(items[2]);
            tenantPass = String.valueOf(items[3]);
            tenantPassConfirm = String.valueOf(items[4]);

            // If entered password is same as the old password  in 'tenant.csv', Replace the old password and confirmation password of the specific tenant with the newly entered password and confirmation password
            if (tenantPass.equals(oldPassword)) {
                tenantPass = newPassword;
                tenantPassConfirm = newPassConfirm;
            }
            newTenant.add(new User(tenantName, tenantPhone, tenantUsername, tenantPass, tenantPassConfirm));
        }

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < newTenant.size(); j++) {
            sb.append(newTenant.get(j).toStringCSV2() + "\n");
        }
        try {
            Files.write(Paths.get("tenant.csv"), sb.toString().getBytes());
        } catch (IOException e) {
            System.out.println("ERROR in saving to tenant.csv");
        }

    }

    //  Rent Property Page
    private void rentProperty() {
        JPanel panel = new JPanel(new GridLayout(7,1));
        JFrame frame = new JFrame("Rent Property Page");
        JLabel label = new JLabel("Choose your listing:");
        panel.add(label);

        JButton listPropertyTypeButton = new JButton("List of property according to property type");
        listPropertyTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    sortByPropertyType("activeProperty.csv");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(listPropertyTypeButton);


        JButton listProjectButton = new JButton("List According to Project etc.");
        listProjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByProject("activeProperty.csv");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(listProjectButton);

        JButton listFacilityButton = new JButton("List According to Facilities");
        listFacilityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByFacilities("activeProperty.csv");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(listFacilityButton);

        JButton listRentalRateButton = new JButton("List According to Rental Rate");
        listRentalRateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByRentalRate("activeProperty.csv");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        panel.add(listRentalRateButton);

        JButton listRentalFeeButton = new JButton("List According to Rental Fee");
        listRentalFeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByRentalFee("activeProperty.csv");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        panel.add(listRentalFeeButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                tenantMenuPage();
            }
        });
        panel.add(backButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);

    }

    // Filter Property by Property Type + Rent property
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
                rentProperty();
            }
        });

        if (shouldFill)
            c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)
            c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(backButton, c);

        JLabel propertyTypeLabel = new JLabel("Choose Property Type");
        if (shouldFill)
            c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)
            c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
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
                        property = Property.returnProperty("activeProperty.csv", filterTerm); // Get the properties that has been filtered with the property type that user selects
                        Property.saveTempCSV(property, "tempPropertyType.csv"); // save the filtered properties into 'tempPropertyType.csv'
                        try {
                            sortByPropertyType("activeProperty.csv");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        if (shouldFill)
            c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)
            c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(propertyTypeCombo, c);

        JLabel label = new JLabel("Property Type Chosen: " + filterTerm);
        if (shouldFill)
            c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)
            c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(label, c);


        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tempPropertyType.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String id = getIdCSV(i, "tempPropertyType.csv"), name = getNameCSV(i, "tempPropertyType.csv"), address = getAddressCSV(i, "tempPropertyType.csv"),
                    feetSize = getFeetSizeCSV(i, "tempPropertyType.csv"), type = getTypeCSV(i, "tempPropertyType.csv"), room = getRoomCSV(i, "tempPropertyType.csv"),
                    bathroom = getBathroomCSV(i, "tempPropertyType.csv"), facilities = getFacilitiesCSV(i, "tempPropertyType.csv"),
                    rentalPrice = getRentalPriceCSV(i, "tempPropertyType.csv"), contact = getContactCSV(i, "tempPropertyType.csv");

            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));

            panel1.add(new JLabel(("Name                   : " + name)));
            panel1.add(new JLabel(("Address               : " + address)));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + feetSize)));
            panel1.add(new JLabel(("Type                    : " + type)));
            panel1.add(new JLabel(("Room                   : " + room)));
            panel1.add(new JLabel(("Bathroom             : " + bathroom)));
            panel1.add(new JLabel(("Facilities              : " + facilities)));
            panel1.add(new JLabel(("Rental (RM)          : " + rentalPrice)));
            panel1.add(new JLabel(("Contact               : " + contact)));

            JButton rentButton = new JButton("Rent Property");
            rentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.dispose();
                    ArrayList<Property> inactiveProperty = Property.readTempCSV("inactiveProperty.csv");
                    inactiveProperty.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
                    Property.saveTempCSV(inactiveProperty, "inactiveProperty.csv"); // save the rented property into the 'inactiveProperty.csv'

                    // remove rented property in activeProperty.csv & tempPropertyType.csv
                    Property.rentProperty("tempPropertyType.csv", id);
                    Property.rentProperty("activeProperty.csv", id);
                    try {
                        JOptionPane.showMessageDialog(null, "Property successfully rented!", "Property Rent Status", JOptionPane.INFORMATION_MESSAGE);
                        sortByPropertyType("activeProperty.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            rentButton.setBackground(Color.GREEN);
            rentButton.setOpaque(true);
            rentButton.setBorderPainted(false);
            panel1.add(rentButton);
            panel1.setBorder(blackline1);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.weightx = 0.0;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.gridwidth = 3;
            c.gridx = 1;
            c.gridy = i;
            panel1.add(rentButton, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.gridwidth = 3;
            c.gridx = 0;
            c.gridy = i+4;
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

    // Filter Property by Projects + Rent property
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
                rentProperty();
            }
        });

        if (shouldFill)
            c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)
            c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(backButton, c);

        JLabel projectLabel = new JLabel("Choose Project");
        if (shouldFill)
            c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)
            c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(projectLabel, c);

        JComboBox projectCombo = new JComboBox(projectArr.toArray());
        projectCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==projectCombo) {
                    frame.dispose();
                    filterTerm = (String) projectCombo.getSelectedItem();
                    if(filterTerm.equals("Select")) {
                        try {
                            sortByProject("activeProperty.csv");
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else {
                        property = Property.returnProperty("activeProperty.csv", filterTerm); // Get the properties that has been filtered with the project that user selects
                        Property.saveTempCSV(property, "tempProjects.csv"); // save the filtered properties into 'tempProjects.csv'
                        try {
                            sortByProject("activeProperty.csv");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        if (shouldFill)
            c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)
            c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(projectCombo, c);

        JLabel label = new JLabel("Project Chosen: " + filterTerm);
        if (shouldFill)
            c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)
            c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(label, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tempProjects.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String id = getIdCSV(i, "tempProjects.csv"), name = getNameCSV(i, "tempProjects.csv"), address = getAddressCSV(i, "tempProjects.csv"),
                    feetSize = getFeetSizeCSV(i, "tempProjects.csv"), type = getTypeCSV(i, "tempProjects.csv"), room = getRoomCSV(i, "tempProjects.csv"),
                    bathroom = getBathroomCSV(i, "tempProjects.csv"), facilities = getFacilitiesCSV(i, "tempProjects.csv"),
                    rentalPrice = getRentalPriceCSV(i, "tempProjects.csv"), contact = getContactCSV(i, "tempProjects.csv");

            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));

            panel1.add(new JLabel(("Name                   : " + name)));
            panel1.add(new JLabel(("Address               : " + address)));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + feetSize)));
            panel1.add(new JLabel(("Type                    : " + type)));
            panel1.add(new JLabel(("Room                   : " + room)));
            panel1.add(new JLabel(("Bathroom             : " + bathroom)));
            panel1.add(new JLabel(("Facilities              : " + facilities)));
            panel1.add(new JLabel(("Rental (RM)          : " + rentalPrice)));
            panel1.add(new JLabel(("Contact               : " + contact)));

            JButton rentButton = new JButton("Rent Property");
            rentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.dispose();
                    ArrayList<Property> inactiveProperty = Property.readTempCSV("inactiveProperty.csv");
                    inactiveProperty.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
                    Property.saveTempCSV(inactiveProperty, "inactiveProperty.csv"); // save the rented property into the 'inactiveProperty.csv'

                    // remove the rented property in activeProperty.csv & tempProjects.csv
                    Property.rentProperty("tempProjects.csv", id);
                    Property.rentProperty("activeProperty.csv", id);
                    try {
                        JOptionPane.showMessageDialog(null, "Property successfully rented!", "Property Rent Status", JOptionPane.INFORMATION_MESSAGE);
                        sortByProject("activeProperty.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            rentButton.setBackground(Color.GREEN);
            rentButton.setOpaque(true);
            rentButton.setBorderPainted(false);
            panel1.add(rentButton);
            panel1.setBorder(blackline1);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.weightx = 0.0;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.gridwidth = 3;
            c.gridx = 1;
            c.gridy = i;
            panel1.add(rentButton, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.gridwidth = 3;
            c.gridx = 0;
            c.gridy = i+4;
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

    // Filter Property by Facilities + Rent property
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
                rentProperty();
            }
        });

        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);

        JLabel facilityLabel = new JLabel("Choose Facility");
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
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
                        property = Property.returnProperty("activeProperty.csv", filterTerm); // Get the properties that has been filtered with the facility that user selects
                        Property.saveTempCSV(property, "tempFacilities.csv"); // save the filtered properties into 'tempFacilities.csv'
                        try {
                            sortByFacilities("activeProperty.csv");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)  c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 2;
        panel.add(facilityCombo, c);

        JLabel label = new JLabel("Facility chosen: " + filterTerm);
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 3;
        panel.add(label, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tempFacilities.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String id = getIdCSV(i, "tempFacilities.csv"), name = getNameCSV(i, "tempFacilities.csv"), address = getAddressCSV(i, "tempFacilities.csv"),
                    feetSize = getFeetSizeCSV(i, "tempFacilities.csv"), type = getTypeCSV(i, "tempFacilities.csv"), room = getRoomCSV(i, "tempFacilities.csv"),
                    bathroom = getBathroomCSV(i, "tempFacilities.csv"), facilities = getFacilitiesCSV(i, "tempFacilities.csv"),
                    rentalPrice = getRentalPriceCSV(i, "tempFacilities.csv"), contact = getContactCSV(i, "tempFacilities.csv");

            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));

            panel1.add(new JLabel(("Name                   : " + name)));
            panel1.add(new JLabel(("Address               : " + address)));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + feetSize)));
            panel1.add(new JLabel(("Type                    : " + type)));
            panel1.add(new JLabel(("Room                   : " + room)));
            panel1.add(new JLabel(("Bathroom             : " + bathroom)));
            panel1.add(new JLabel(("Facilities              : " + facilities)));
            panel1.add(new JLabel(("Rental (RM)          : " + rentalPrice)));
            panel1.add(new JLabel(("Contact               : " + contact)));

            JButton rentButton = new JButton("Rent Property");
            rentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.dispose();
                    ArrayList<Property> inactiveProperty = Property.readTempCSV("inactiveProperty.csv");
                    inactiveProperty.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
                    Property.saveTempCSV(inactiveProperty, "inactiveProperty.csv"); // save the rented property into the 'inactiveProperty.csv'

                    // remove the rented property in activeProperty.csv & tempFacilities.csv
                    Property.rentProperty("tempFacilities.csv", id);
                    Property.rentProperty("activeProperty.csv", id);
                    try {
                        JOptionPane.showMessageDialog(null, "Property successfully rented!", "Property Rent Status", JOptionPane.INFORMATION_MESSAGE);
                        sortByFacilities("activeProperty.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            rentButton.setBackground(Color.GREEN);
            rentButton.setOpaque(true);
            rentButton.setBorderPainted(false);
            panel1.add(rentButton);
            panel1.setBorder(blackline1);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.weightx = 0.0;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.gridwidth = 3;
            c.gridx = 1;
            c.gridy = i;
            panel1.add(rentButton, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.gridwidth = 3;
            c.gridx = 0;
            c.gridy = i+4;
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

    // Sort Property by Rental Rate + Rent property
    private void sortByRentalRate(String filepath)  throws IOException {
        JFrame frame = new JFrame("Filter by Rental Rate");
        JPanel panel = new JPanel();
        ArrayList<String> RentalRateArr = new ArrayList<String>();

        RentalRateArr.add("Select");
        RentalRateArr.add("Below RM1 per sqft");
        RentalRateArr.add("Between RM1 and RM2 per sqft");
        RentalRateArr.add("Above RM2 per sqft");

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                rentProperty();
            }
        });

        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(backButton, c);

        JLabel RentalRateLabel = new JLabel("Choose Rental Rate");
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(RentalRateLabel, c);

        JComboBox propertyTypeCombo = new JComboBox(RentalRateArr.toArray());
        propertyTypeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == propertyTypeCombo) {
                    frame.dispose();
                    filterTerm = (String) propertyTypeCombo.getSelectedItem();
                    if (filterTerm.equals("Select")) {
                        try {
                            sortByRentalRate("activeProperty.csv");
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        property = Property.returnSortedRentalRateProperty("activeProperty.csv", filterTerm); // Get the properties that has been sorted with the rental rate that user selects
                        Property.saveTempCSV(property, "tempRentalRate.csv"); // save the sorted properties into 'tempRentalRate.csv'
                        try {
                            sortByRentalRate("activeProperty.csv");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(propertyTypeCombo, c);

        JLabel label = new JLabel("Property Type Chosen: " + filterTerm);
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(label, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tempRentalRate.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String id = getIdCSV(i, "tempRentalRate.csv"), name = getNameCSV(i, "tempRentalRate.csv"), address = getAddressCSV(i, "tempRentalRate.csv"),
                    feetSize = getFeetSizeCSV(i, "tempRentalRate.csv"), type = getTypeCSV(i, "tempRentalRate.csv"), room = getRoomCSV(i, "tempRentalRate.csv"),
                    bathroom = getBathroomCSV(i, "tempRentalRate.csv"), facilities = getFacilitiesCSV(i, "tempRentalRate.csv"),
                    rentalPrice = getRentalPriceCSV(i, "tempRentalRate.csv"), contact = getContactCSV(i, "tempRentalRate.csv");
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));

            panel1.add(new JLabel(("Name                   : " + name)));
            panel1.add(new JLabel(("Address               : " + address)));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + feetSize)));
            panel1.add(new JLabel(("Type                    : " + type)));
            panel1.add(new JLabel(("Room                   : " + room)));
            panel1.add(new JLabel(("Bathroom             : " + bathroom)));
            panel1.add(new JLabel(("Facilities              : " + facilities)));
            panel1.add(new JLabel(("Rental (RM)          : " + rentalPrice)));
            panel1.add(new JLabel(("Contact               : " + contact)));

            JButton rentButton = new JButton("Rent Property");
            rentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.dispose();
                    ArrayList<Property> inactiveProperty = Property.readTempCSV("inactiveProperty.csv");
                    inactiveProperty.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
                    Property.saveTempCSV(inactiveProperty, "inactiveProperty.csv"); // save the rented property into the 'inactiveProperty.csv'

                    // remove rented property in activeProperty.csv & tempRentalRate.csv
                    Property.rentProperty("tempRentalRate.csv", id);
                    Property.rentProperty("activeProperty.csv", id);
                    try {
                        JOptionPane.showMessageDialog(null, "Property successfully rented!", "Property Rent Status", JOptionPane.INFORMATION_MESSAGE);
                        sortByRentalRate("activeProperty.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            rentButton.setBackground(Color.GREEN);
            rentButton.setOpaque(true);
            rentButton.setBorderPainted(false);
            panel1.add(rentButton);
            panel1.setBorder(blackline1);

            panel1.setBorder(blackline1);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.weightx = 0.0;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.gridwidth = 3;
            c.gridx = 1;
            c.gridy = i;
            //panel1.add(rentButton, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.gridwidth = 3;
            c.gridx = 0;
            c.gridy = i + 4;
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

    // Sort Property by Rental Fee + Rent property
    private void sortByRentalFee(String filepath) throws IOException {
        JFrame frame = new JFrame("Sort by Rental Fee");
        JPanel panel = new JPanel();
        ArrayList<String> feeArr = new ArrayList<String>();

        feeArr.add("Select");
        feeArr.add("From Lowest");
        feeArr.add("From Highest");

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                rentProperty();
            }
        });

        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);

        JLabel feeLabel = new JLabel("Choose Sorting");
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 1;
        panel.add(feeLabel, c);

        JComboBox feeCombo = new JComboBox(feeArr.toArray());
        feeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==feeCombo) {
                    frame.dispose();
                    filterTerm = (String) feeCombo.getSelectedItem();
                    if(filterTerm.equals("Select")) {
                        try {
                            sortByRentalFee("activeProperty.csv");
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else if(filterTerm.equals("From Lowest")){
                        try {
                            int[] array = returnSortRentalFeeArray("activeProperty.csv", "From Lowest");        // sort ascending Property fee ONLY
                            sortedProperty = returnSortedProperty("activeProperty.csv", array); // Get the properties that has been sorted from the lowest to the highest property fee that user selects
                            saveTempCSV(sortedProperty, "tempRentalFee.csv"); // save the sorted properties into 'tempRentalFee.csv'
                            sortByRentalFee("activeProperty.csv");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    else if(filterTerm.equals("From Highest")){
                        try {
                            int[] array = returnSortRentalFeeArray("activeProperty.csv", "From Highest");        // sorted descending Property fee ONLY
                            sortedProperty = returnSortedProperty("activeProperty.csv", array);  // Get the properties that has been sorted from the highest to the lowest property fee that user selects
                            saveTempCSV(sortedProperty, "tempRentalFee.csv"); // save the sorted properties into 'tempRentalFee.csv'
                            sortByRentalFee("activeProperty.csv");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX)  c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 2;
        panel.add(feeCombo, c);

        JLabel label = new JLabel("Sort chosen: " + filterTerm);
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 3;
        panel.add(label, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("tempRentalFee.csv"));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String id = getIdCSV(i, "tempRentalFee.csv"), name = getNameCSV(i, "tempRentalFee.csv"), address = getAddressCSV(i, "tempRentalFee.csv"),
                    feetSize = getFeetSizeCSV(i, "tempRentalFee.csv"), type = getTypeCSV(i, "tempRentalFee.csv"), room = getRoomCSV(i, "tempRentalFee.csv"),
                    bathroom = getBathroomCSV(i, "tempRentalFee.csv"), facilities = getFacilitiesCSV(i, "tempRentalFee.csv"),
                    rentalPrice = getRentalPriceCSV(i, "tempRentalFee.csv"), contact = getContactCSV(i, "tempRentalFee.csv");
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));

            panel1.add(new JLabel(("Name                   : " + name)));
            panel1.add(new JLabel(("Address               : " + address)));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + feetSize)));
            panel1.add(new JLabel(("Type                    : " + type)));
            panel1.add(new JLabel(("Room                   : " + room)));
            panel1.add(new JLabel(("Bathroom             : " + bathroom)));
            panel1.add(new JLabel(("Facilities              : " + facilities)));
            panel1.add(new JLabel(("Rental (RM)          : " + rentalPrice)));
            panel1.add(new JLabel(("Contact               : " + contact)));

            JButton rentButton = new JButton("Rent Property");
            rentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.dispose();
                    ArrayList<Property> inactiveProperty = Property.readTempCSV("inactiveProperty.csv");
                    inactiveProperty.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
                    Property.saveTempCSV(inactiveProperty, "inactiveProperty.csv"); // save the rented property into the 'inactiveProperty.csv'

                    // remove rented property in activeProperty.csv & tempRentalFee.csv
                    Property.rentProperty("tempRentalFee.csv", id);
                    Property.rentProperty("activeProperty.csv", id);
                    try {
                        JOptionPane.showMessageDialog(null, "Property successfully rented!", "Property Rent Status", JOptionPane.INFORMATION_MESSAGE);
                        sortByRentalFee("activeProperty.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            rentButton.setBackground(Color.GREEN);
            rentButton.setOpaque(true);
            rentButton.setBorderPainted(false);
            panel1.add(rentButton);
            panel1.setBorder(blackline1);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.weightx = 0.0;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.gridwidth = 3;
            c.gridx = 1;
            c.gridy = i;
            panel1.add(rentButton, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.gridwidth = 3;
            c.gridx = 0;
            c.gridy = i+4;
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
}