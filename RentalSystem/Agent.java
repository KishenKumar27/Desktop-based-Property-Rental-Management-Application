import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;



public class Agent extends User {

    private static JLabel nameLabel, phoneLabel, userNameLabel, passwordLabel, conPasswordLabel;
    private static JTextField nameText, phoneText, userNameText;
    private static JPasswordField passwordText, conPasswordText;
    private String agentName, agentPhone, agentUser, agentPass, agentPassConfirm,filterTerm, filepath = "agentRegistration.csv", filepath2 = "agent.csv",
    cyberpropertyCSV = "cyberproperty2.csv",tempPropertyTypeCSV = "tempPropertyType.csv",tempFacilitiesCSV = "tempFacilities.csv",
    tempProjectsCSV = "tempProjects.csv", activepropertyCSV = "activeProperty.csv",tempRentalRate = "tempRentalRate.csv",tempRentalFeeCSV = "tempRentalFee.csv";
    private static JButton button,loginButton;
    private ArrayList<User> newAgent = readRegistrationCSV(filepath), agent = readUserCSV(filepath2);
    private ArrayList<Property> property = readPropertyCSV(cyberpropertyCSV), sortedProperty = new ArrayList<>();
    private Boolean agentStatus = false;
    private final static boolean shouldFill = true, shouldWeightX = true;


    public Agent () {
        JPanel agentPanel = new JPanel(new GridLayout(6,1));
        JFrame agentFrame = new JFrame("Agent Profile Page");
    
        JLabel label = new JLabel("Choose to Login or Register ");
    
    
        JButton loginButton = new JButton("Login as an Agent");
        loginButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) {
                loginButton.setVisible(true);
                agentFrame.dispose();
                agentLogin();
            }
        });
    
        JButton registerButton = new JButton("Register as an Agent");
        registerButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) {
                registerButton.setVisible(true);
                agentFrame.dispose();
                agentRegister();
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {
                agentFrame.dispose();
                new MainPage();
            }
        });
    
        agentPanel.add(label);
        agentPanel.add(loginButton);
        agentPanel.add(registerButton);
        agentPanel.add(backButton);
        agentFrame.add(agentPanel);
        agentFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        agentFrame.setSize(500, 400);
        agentFrame.setLocationRelativeTo(null);
        agentFrame.setVisible(true);
        agentFrame.setFocusable(true);
    }
    
    public void agentRegister(){
    
        JPanel agentPanel = new JPanel(new GridLayout(7,1));
        JFrame agentFrame = new JFrame("Agent Registration Page");
        //JLabel registerSuccess = new JLabel ("");
    
        nameText = new JTextField(20);
        phoneText = new JTextField(20);
        userNameText = new JTextField(20);
        passwordText = new JPasswordField(20);
        conPasswordText = new JPasswordField(20);
        
        nameLabel = new JLabel("Name");
        phoneLabel = new JLabel("Phone");
        userNameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        conPasswordLabel = new JLabel("Confirm Password");    

        JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    agentFrame.setVisible(false);
                    new Agent();
                }
            });
    
        button = new JButton("Register");
        button.setSize(100,100);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agentName = nameText.getText();
                agentPhone = phoneText.getText();
                agentUser = userNameText.getText();
                agentPass = passwordText.getText();
                agentPassConfirm = conPasswordText.getText();
    
                    if(nameText.getText().isEmpty() || phoneText.getText().isEmpty() || userNameText.getText().isEmpty() || passwordText.getText().isEmpty() || conPasswordText.getText().isEmpty())
                        JOptionPane.showMessageDialog(null, "Registration is not fully filled", "Agent Registration Status", JOptionPane.ERROR_MESSAGE);
                    else {
                        if(agentPass.equals(agentPassConfirm)) {
                            newAgent.add(new User(agentStatus, agentName, agentPhone, agentUser, agentPass,agentPassConfirm));
                            saveRegistrationCSV(newAgent, filepath);
                            JOptionPane.showMessageDialog(null, "Agent registration complete!", "Agent Registration Status", JOptionPane.INFORMATION_MESSAGE);
                            agentFrame.dispose();
                            new Agent();
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Password does not match", "Agent Registration Status", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            agentPanel.add(nameLabel);
            agentPanel.add(nameText);
            agentPanel.add(phoneLabel);
            agentPanel.add(phoneText);
            agentPanel.add(userNameLabel);
            agentPanel.add(userNameText);
            agentPanel.add(passwordLabel);
            agentPanel.add(passwordText);
            agentPanel.add(conPasswordLabel);
            agentPanel.add(conPasswordText);
            agentPanel.add(backButton);
            agentPanel.add(button);
            agentFrame.add(agentPanel);
            agentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            agentFrame.setSize(500, 400);
            agentFrame.setLocationRelativeTo(null);
            agentFrame.setVisible(true);
            agentFrame.setFocusable(false);
        }
    
    private void agentLogin() {
        JPanel agentPanel = new JPanel(new GridLayout(4,1));
        JFrame agentFrame = new JFrame("Admin Login Page");
        JLabel emptyLabel = new JLabel("");
        // username
        userNameLabel = new JLabel("Username");
        userNameText = new JTextField();
        //password
        passwordLabel = new JLabel("Password");
        passwordText = new JPasswordField();
    
        loginButton = new JButton("Login");
        //gbc.gridx = 10; gbc.gridy = 6; gbc.gridwidth = 5;
        //agentFrame.add(loginButton, gbc);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agentUser = userNameText.getText();
                agentPass = passwordText.getText();
    
                if (agentUser.equals(getUsernameCSV(agent, agentUser, filepath2)) && agentPass.equals(getPasswordCSV(agent, agentPass, filepath2))) {
                    JOptionPane.showMessageDialog(null, "Agent Login Successful!", "Agent Login Status", JOptionPane.INFORMATION_MESSAGE);
                    agentFrame.dispose();
                    agentMenuPage();
                }
                else if(userNameText.getText().isEmpty() || passwordText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Username or password is not filled", "Agent Login Status", JOptionPane.ERROR_MESSAGE);
                }
                else if(!(agentUser.equals(getUsernameCSV(agent, agentUser, filepath2))) || !(agentPass.equals(getPasswordCSV(agent, agentPass, filepath2)))) {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Agent Login Status", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agentFrame.setVisible(false);
                agentMenuPage();
            }
        });


        agentPanel.add(userNameLabel);
        agentPanel.add(userNameText);
        agentPanel.add(passwordLabel);
        agentPanel.add(passwordText);   
        agentPanel.add(loginButton);
        agentPanel.add(backButton);
        agentPanel.add(emptyLabel);
        agentFrame.add(agentPanel);
        agentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        agentFrame.setSize(500, 400);
        agentFrame.setLocationRelativeTo(null);
        agentFrame.setVisible(true);
        agentFrame.setFocusable(false);
    }

    public void agentMenuPage() {

        JPanel agentMenuPanel = new JPanel(new GridLayout(5,1));
        JFrame agentMenuFrame = new JFrame("Agent Menu Page");
        JLabel agentLabel = new JLabel("What Would You Like To Do:");

        JButton propertyButton = new JButton("Add Property");
        propertyButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            agentMenuFrame.setVisible(false);
            AddProperty();

            }
        });

        JButton viewListButton = new JButton("View List");
        viewListButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            agentMenuFrame.setVisible(false);
            viewReport();

            }
        });
        
        JButton exitButton = new JButton("EXIT");
        exitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            agentMenuFrame.setVisible(false);
            new MainPage();
            }
        });

        agentMenuPanel.add(agentLabel);
        agentMenuPanel.add(propertyButton);
        agentMenuPanel.add(viewListButton);
        agentMenuPanel.add(exitButton);
        agentMenuFrame.add(agentMenuPanel);
        agentMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        agentMenuFrame.setSize(500, 400);
        agentMenuFrame.setLocationRelativeTo(null);
        agentMenuFrame.setVisible(true);
        agentMenuFrame.setFocusable(false);        
    }

    private void viewReport() {
        JPanel panel = new JPanel(new GridLayout(8,1));
        JFrame frame = new JFrame("View Report Page");
        JLabel label = new JLabel("Choose your listing:");
    
    
        JButton agentMenuButton1 = new JButton("List of Property According to Property Type");
        agentMenuButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByPropertyType(activepropertyCSV);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }            
            }
        });
    
        JButton agentMenuButton2 = new JButton("List According to Project etc.");
        agentMenuButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByProject(activepropertyCSV);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
         });
        
        JButton agentMenuButton3 = new JButton("List According to Facilities");
        agentMenuButton3.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByFacilities(activepropertyCSV);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
           }
        });
        JButton agentMenuButton4 = new JButton("Sort By Rental Rate");
        agentMenuButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByRentalRate(activepropertyCSV);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    
        JButton agentMenuButton5 = new JButton("Sort By Rental Price");
        agentMenuButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByRentalFee(activepropertyCSV);
                } catch (IOException e1) {
                     e1.printStackTrace();
                }      
            }
        });
    
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                agentMenuPage();
            }
        });
    
        panel.add(label);
        panel.add(agentMenuButton1);
        panel.add(agentMenuButton2);
        panel.add(agentMenuButton3);
        panel.add(agentMenuButton4);
        panel.add(agentMenuButton5);
        panel.add(backButton);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);  
    
    }
    
    private void AddProperty() {
        JPanel agentPanel = new JPanel(new GridLayout(11,1));
        JFrame agentFrame = new JFrame("Add New Property Page");
        JLabel registerSuccess = new JLabel ("");

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
        JLabel feeLabel = new JLabel("Rental Fee");
        JTextField feeText = new JTextField();
        // property owner phone number
        phoneLabel = new JLabel("Phone Number");
        phoneText = new JTextField();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agentFrame.setVisible(false);
                agentMenuPage();
            }
        });

        JButton submitButton = new JButton("Upload Property");
        submitButton.setSize(100, 100);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String propName = propNameText.getText();
                String propAddress = addText.getText();
                String propType = typeText.getText();
                String propSize = sizeText.getText();
                String propRoom = roomText.getText();
                String propBroom = broomText.getText();
                String propFacilities = facilitiesText.getText();
                String propFee = feeText.getText();
                String propContact = phoneText.getText();
        
                if(propNameText.getText().isEmpty() || addText.getText().isEmpty() || typeText.getText().isEmpty() || sizeText.getText().isEmpty()|| roomText.getText().isEmpty() || broomText.getText().isEmpty() || facilitiesText.getText().isEmpty() || feeText.getText().isEmpty() || phoneText.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Property details is incomplete", "Property Status", JOptionPane.ERROR_MESSAGE);
                else {
                        //save at cyberproperty & activeProperty
                        //details sequence according to csv
                        property.add(new Property(propId, propName, propAddress, propSize, propType, propRoom, propBroom, propFacilities, propFee, propContact));

                        saveTempCSV(property, cyberpropertyCSV); 
                        saveTempCSV(property, activepropertyCSV);
                        JOptionPane.showMessageDialog(null, "New property uploaded!", "Property Status", JOptionPane.INFORMATION_MESSAGE);
                        AddProperty();
                }
            }
        });
        agentPanel.add(propNameLabel);
        agentPanel.add(propNameText);
        agentPanel.add(addLabel);
        agentPanel.add(addText);
        agentPanel.add(typeLabel);
        agentPanel.add(typeText);
        agentPanel.add(sizeLabel);
        agentPanel.add(sizeText);
        agentPanel.add(roomLabel);
        agentPanel.add(roomText);
        agentPanel.add(broomLabel);
        agentPanel.add(broomText);
        agentPanel.add(facilitiesLabel);
        agentPanel.add(facilitiesText);
        agentPanel.add(feeLabel);
        agentPanel.add(feeText);
        agentPanel.add(phoneLabel);
        agentPanel.add(phoneText);
        agentPanel.add(backButton);
        agentPanel.add(submitButton);
        agentPanel.add(registerSuccess);
        agentFrame.add(agentPanel);
        agentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        agentFrame.setSize(500, 400);
        agentFrame.setLocationRelativeTo(null);
        agentFrame.setVisible(true);
        agentFrame.setFocusable(false);
    }
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

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
        backButton.setHorizontalAlignment(SwingConstants.LEFT);

        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);

        JLabel projectLabel = new JLabel("Choose Project");
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 1;
        panel.add(projectLabel, c);

        JComboBox projectCombo = new JComboBox(projectArr.toArray());
        projectCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==projectCombo) {                        
                    frame.dispose();
                    filterTerm = (String) projectCombo.getSelectedItem();
                    if(filterTerm.equals("Select")) {
                        try {
                            sortByProject(activepropertyCSV);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else {
                        property = returnProperty(activepropertyCSV, filterTerm);
                        saveTempCSV(property, tempProjectsCSV);
                        try {
                            sortByProject(activepropertyCSV);
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
        panel.add(projectCombo, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(tempProjectsCSV));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {    
            String id = getIdCSV(i, tempProjectsCSV);
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(9, 1));
        
            panel1.add(new JLabel(("Name                   : " + getNameCSV(i, tempProjectsCSV))));
            panel1.add(new JLabel(("Address               : " + getAddressCSV(i, tempProjectsCSV))));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + getFeetSizeCSV(i, tempProjectsCSV))));
            panel1.add(new JLabel(("Type                    : " + getTypeCSV(i, tempProjectsCSV))));
            panel1.add(new JLabel(("Room                   : " + getRoomCSV(i, tempProjectsCSV))));
            panel1.add(new JLabel(("Bathroom             : " + getBathroomCSV(i, tempProjectsCSV))));
            panel1.add(new JLabel(("Facilities              : " + getFacilitiesCSV(i, tempProjectsCSV))));
            panel1.add(new JLabel(("Rental (RM)          : " + getRentalPriceCSV(i, tempProjectsCSV))));
            panel1.add(new JLabel(("Contact               : " + getContactCSV(i, tempProjectsCSV))));
     
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
                            sortByFacilities(activepropertyCSV);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else {
                        property = returnProperty(activepropertyCSV, filterTerm);
                        saveTempCSV(property, tempFacilitiesCSV);
                        try {
                            sortByFacilities(activepropertyCSV);
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
            lines = Files.readAllLines(Paths.get(tempFacilitiesCSV));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {    
            String id = getIdCSV(i, tempFacilitiesCSV);
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(9, 1));
        
            panel1.add(new JLabel(("Name                   : " + getNameCSV(i, tempFacilitiesCSV))));
            panel1.add(new JLabel(("Address               : " + getAddressCSV(i, tempFacilitiesCSV))));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + getFeetSizeCSV(i, tempFacilitiesCSV))));
            panel1.add(new JLabel(("Type                    : " + getTypeCSV(i, tempFacilitiesCSV))));
            panel1.add(new JLabel(("Room                   : " + getRoomCSV(i, tempFacilitiesCSV))));
            panel1.add(new JLabel(("Bathroom             : " + getBathroomCSV(i, tempFacilitiesCSV))));
            panel1.add(new JLabel(("Facilities              : " + getFacilitiesCSV(i, tempFacilitiesCSV))));
            panel1.add(new JLabel(("Rental (RM)          : " + getRentalPriceCSV(i, tempFacilitiesCSV))));
            panel1.add(new JLabel(("Contact               : " + getContactCSV(i, tempFacilitiesCSV))));
     
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

        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);

        JLabel propertyTypeLabel = new JLabel("Choose Property Type");
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
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
                            sortByPropertyType(activepropertyCSV);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else {
                        property = returnProperty(activepropertyCSV, filterTerm);
                        saveTempCSV(property, tempPropertyTypeCSV);
                        try {
                            sortByPropertyType(activepropertyCSV);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 2;
        panel.add(propertyTypeCombo, c);

        JLabel label = new JLabel("Property Type Chosen: " + filterTerm);
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 3;
        panel.add(label, c);

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(tempPropertyTypeCSV));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String id = getIdCSV(i, tempPropertyTypeCSV);
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));

            panel1.add(new JLabel(("Name                   : " + getNameCSV(i, tempPropertyTypeCSV))));
            panel1.add(new JLabel(("Address               : " + getAddressCSV(i, tempPropertyTypeCSV))));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + getFeetSizeCSV(i, tempPropertyTypeCSV))));
            panel1.add(new JLabel(("Type                    : " + getTypeCSV(i, tempPropertyTypeCSV))));
            panel1.add(new JLabel(("Room                   : " + getRoomCSV(i, tempPropertyTypeCSV))));
            panel1.add(new JLabel(("Bathroom             : " + getBathroomCSV(i, tempPropertyTypeCSV))));
            panel1.add(new JLabel(("Facilities              : " + getFacilitiesCSV(i, tempPropertyTypeCSV))));
            panel1.add(new JLabel(("Rental (RM)          : " + getRentalPriceCSV(i, tempPropertyTypeCSV))));
            panel1.add(new JLabel(("Contact               : " + getContactCSV(i, tempPropertyTypeCSV))));
           
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
                viewReport();
            }
        });
    
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
        panel.add(backButton, c);
    
        JLabel RentalRateLabel = new JLabel("Choose Rental Rate");
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 1;
        panel.add(RentalRateLabel, c);
    
        JComboBox rentalRateCombo = new JComboBox(RentalRateArr.toArray());
        rentalRateCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==rentalRateCombo) {
                    frame.dispose();
                    filterTerm = (String) rentalRateCombo.getSelectedItem();
                    if(filterTerm.equals("Select")) {
                        try {
                            sortByRentalRate(activepropertyCSV);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else {
                        property = returnSortedRentalRateProperty(activepropertyCSV, filterTerm);
                        saveTempCSV(property, tempRentalRate);
                        try {
                            sortByRentalRate(activepropertyCSV);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 2;
        panel.add(rentalRateCombo, c);
    
        JLabel label = new JLabel("Property Type Chosen: " + filterTerm);
        if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
        if (shouldWeightX) c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 3;
        panel.add(label, c);
    
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(tempRentalRate));
        } catch (IOException e) {
            lines = null;
        }
    
        for (int i = 0; i < lines.size(); i++) {
            String id = getIdCSV(i, tempRentalRate);
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(10, 1));
    
            panel1.add(new JLabel(("Name                   : " + getNameCSV(i, tempRentalRate))));
            panel1.add(new JLabel(("Address               : " + getAddressCSV(i, tempRentalRate))));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + getFeetSizeCSV(i, tempRentalRate))));
            panel1.add(new JLabel(("Type                    : " + getTypeCSV(i, tempRentalRate))));
            panel1.add(new JLabel(("Room                   : " + getRoomCSV(i, tempRentalRate))));
            panel1.add(new JLabel(("Bathroom             : " + getBathroomCSV(i, tempRentalRate))));
            panel1.add(new JLabel(("Facilities              : " + getFacilitiesCSV(i, tempRentalRate))));
            panel1.add(new JLabel(("Rental (RM)          : " + getRentalPriceCSV(i, tempRentalRate))));
            panel1.add(new JLabel(("Contact               : " + getContactCSV(i, tempRentalRate))));
    
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
                viewReport();
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
                            sortByRentalFee(activepropertyCSV);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else if(filterTerm.equals("From Lowest")){                     
                        try {
                            int[] array = returnSortRentalFeeArray(activepropertyCSV, "From Lowest");        // sort ascending Property fee ONLY
                            sortedProperty = returnSortedProperty(activepropertyCSV, array);
                            saveTempCSV(sortedProperty, tempRentalFeeCSV);
                            sortByRentalFee(activepropertyCSV);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    else if(filterTerm.equals("From Highest")){
                        try {
                            int[] array = returnSortRentalFeeArray(activepropertyCSV, "From Highest");        // sorted descending Property fee ONLY
                            sortedProperty = returnSortedProperty(activepropertyCSV, array);
                            saveTempCSV(sortedProperty, tempRentalFeeCSV);
                            sortByRentalFee(activepropertyCSV);
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
            lines = Files.readAllLines(Paths.get(tempRentalFeeCSV));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {    
            String id = getIdCSV(i, tempRentalFeeCSV);
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(9, 1));
        
            panel1.add(new JLabel(("Name                   : " + getNameCSV(i, tempRentalFeeCSV))));
            panel1.add(new JLabel(("Address               : " + getAddressCSV(i, tempRentalFeeCSV))));
            panel1.add(new JLabel(("Built-up Size (ft)  : " + getFeetSizeCSV(i, tempRentalFeeCSV))));
            panel1.add(new JLabel(("Type                    : " + getTypeCSV(i, tempRentalFeeCSV))));
            panel1.add(new JLabel(("Room                   : " + getRoomCSV(i, tempRentalFeeCSV))));
            panel1.add(new JLabel(("Bathroom             : " + getBathroomCSV(i, tempRentalFeeCSV))));
            panel1.add(new JLabel(("Facilities              : " + getFacilitiesCSV(i, tempRentalFeeCSV))));
            panel1.add(new JLabel(("Rental (RM)          : " + getRentalPriceCSV(i, tempRentalFeeCSV))));
            panel1.add(new JLabel(("Contact               : " + getContactCSV(i, tempRentalFeeCSV))));
     
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
    
}