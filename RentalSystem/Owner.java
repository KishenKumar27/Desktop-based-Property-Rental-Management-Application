//Owner
//- login & register
//- can upload the content of a property for rent to the system, and publish it to the registered potential tenant (add random id)
    // - name           - type/category
    // - address        - room
    // - phone no       - bathrooms
    // - size           - rental fee
//- search property by
    //- type of property with special ID
    //- projects
    //- facilities
    //- sorted according to rental fee
    //- sorted according to rental rate
//- can also deactivate their property to be hidden from users’ view board (delete)
//- view board

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Owner extends User {
    private static JLabel userLabel, nameLabel, passLabel, registerSuccess, propNameLabel, phoneLabel, passConfirmLabel, addLabel, typeLabel, sizeLabel, roomLabel, broomLabel, feeLabel, facilitiesLabel;
    private static JTextField userText, nameText, phoneText, propNameText, addText, typeText, sizeText, roomText, broomText, facilitiesText, feeText;
    private static JPasswordField passText, passConfirmText;
    private Boolean ownerStatus = false;
    private String ownerName, ownerPhone, ownerUser, ownerPass, ownerPassConfirm, filepath = "ownerRegistration.csv", filepath2 = "owner.csv", propId, propName, propAddress, propType, propSize, propRoom, propBroom, propFacilities, propFee, propContact, cyberpropertyCSV = "cyberproperty2.csv", tempPropertyTypeCSV = "tempPropertyType.csv", activepropertyCSV = "activeProperty.csv", tempFacilitiesCSV = "tempFacilities.csv", tempProjectsCSV = "tempProjects.csv", filterTerm, keySearch;;
    private int id;
    private ArrayList<User> newOwner = readRegistrationCSV(filepath), owner = readUserCSV(filepath2);
    private ArrayList<Property> property = readPropertyCSV(cyberpropertyCSV), sortedProperty = new ArrayList<>();
    ;
    private final static boolean shouldFill = true, shouldWeightX = true;

    //Main Page
    public Owner () {
        JPanel ownerPanel = new JPanel(new GridLayout(6,1));
        JFrame ownerFrame = new JFrame("Owner Profile Page");

        JLabel label = new JLabel("Choose to Login or Register Owner Account:");
        ownerPanel.add(label);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) {
                loginButton.setVisible(true);
                ownerFrame.dispose();
                OwnerLogin();
            }
        });

        JButton registerButton = new JButton("Register as a Property Owner");
        registerButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) {
                registerButton.setVisible(true);
                ownerFrame.dispose();
                OwnerRegister();
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {
                ownerFrame.dispose();
                new MainPage();
            }
        });

        ownerPanel.add(loginButton);
        ownerPanel.add(registerButton);
        ownerPanel.add(backButton);

        ownerFrame.add(ownerPanel);
        ownerFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ownerFrame.setSize(500, 400);
        ownerFrame.setLocationRelativeTo(null);
        ownerFrame.setVisible(true);
        ownerFrame.setFocusable(true);
    }

    //Login Page
    public void OwnerLogin() {
        JPanel ownerPanel = new JPanel(new GridLayout(6,1));
        JFrame ownerFrame = new JFrame("Property Owner Login Page");
        JLabel loginSuccess = new JLabel("");

        // username
        userLabel = new JLabel("Username");
        userText = new JTextField();

        //password
        passLabel = new JLabel("Password");
        passText = new JPasswordField();

        //login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ownerUser = userText.getText();
                ownerPass = passText.getText(); 
            
                if(ownerUser.equals(getUsernameCSV(owner, ownerUser, filepath2)) && ownerPass.equals(getPasswordCSV(owner, ownerPass, filepath2))) {
                    JOptionPane.showMessageDialog(null, "Owner Login Successful!", "Owner Login Status", JOptionPane.INFORMATION_MESSAGE);
                    ownerFrame.dispose();
                    ownerMenuPage(); 
                }
                else if(userText.getText().isEmpty() || passText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Username or password is not filled", "Owner Login Status", JOptionPane.ERROR_MESSAGE);
                }
                else if(!(ownerUser.equals(getUsernameCSV(owner, ownerUser, filepath2))) || !(ownerPass.equals(getPasswordCSV(owner, ownerPass, filepath2)))){
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Owner Login Status", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ownerMenuFrame.dispose();
                JOptionPane.showMessageDialog(null, "Logged out successfully!", "Owner Login Status", JOptionPane.INFORMATION_MESSAGE);
                new Owner();
            }
        });
        
        //back or return button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ownerFrame.dispose();
                new Owner();
            }
        });
        
        ownerPanel.add(userLabel);
        ownerPanel.add(userText);
        ownerPanel.add(passLabel);
        ownerPanel.add(passText);
        ownerPanel.add(backButton);
        ownerPanel.add(loginButton);
        ownerPanel.add(loginSuccess);
        ownerPanel.add(logoutButton);

        ownerFrame.add(ownerPanel);
        ownerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ownerFrame.setSize(500, 400);
        ownerFrame.setLocationRelativeTo(null);
        ownerFrame.setVisible(true);
        ownerFrame.setFocusable(false);
    }

    //Registration Page
    public void OwnerRegister() {
        JPanel ownerPanel = new JPanel(new GridLayout(7,1));
        JFrame ownerFrame = new JFrame("Property Owner Registration Page");
        registerSuccess = new JLabel ("");

        // name
        nameLabel = new JLabel("Name"); nameText = new JTextField();

        // phone number
        phoneLabel = new JLabel("Phone Number"); phoneText = new JTextField();

        // username
        userLabel = new JLabel("Username"); userText = new JTextField();

        //password
        passLabel = new JLabel("Password"); passText = new JPasswordField();

        //password confirm
        passConfirmLabel = new JLabel("Confirm Password"); passConfirmText = new JPasswordField();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ownerFrame.setVisible(false);
                new Owner();
            }
        });

        //Registration submission
        JButton submitButton = new JButton("Submit Registration");
        submitButton.setSize(100, 100);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ownerName = nameText.getText();
                ownerPhone = phoneText.getText();
                ownerUser = userText.getText();
                ownerPass = passText.getText();
                ownerPassConfirm = passConfirmText.getText();
        
                if(nameText.getText().isEmpty() || phoneText.getText().isEmpty() || userText.getText().isEmpty() || passText.getText().isEmpty() || passConfirmText.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Registration is not fully filled", "Owner Registration Status", JOptionPane.ERROR_MESSAGE);
                else {
                    if(ownerPass.equals(ownerPassConfirm)) {
                        newOwner.add(new User(ownerStatus, ownerName, ownerPhone, ownerUser, ownerPass, ownerPassConfirm));
                        saveRegistrationCSV(newOwner, filepath);
                        JOptionPane.showMessageDialog(null, "Owner resgistration completed!\nPlease wait for approval. ", "Owner Registration Status", JOptionPane.INFORMATION_MESSAGE);
                        ownerFrame.dispose();
                        new Owner();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Password does not match", "Owner Registration Status", JOptionPane.ERROR_MESSAGE);
                }
    
            }
        });

        ownerPanel.add(nameLabel);
        ownerPanel.add(nameText);
        ownerPanel.add(phoneLabel);
        ownerPanel.add(phoneText);
        ownerPanel.add(userLabel);
        ownerPanel.add(userText);
        ownerPanel.add(passLabel);
        ownerPanel.add(passText);
        ownerPanel.add(passConfirmLabel);
        ownerPanel.add(passConfirmText);
        ownerPanel.add(backButton);
        ownerPanel.add(submitButton);
        ownerPanel.add(registerSuccess);

        ownerFrame.add(ownerPanel);
        ownerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ownerFrame.setSize(500, 400);
        ownerFrame.setLocationRelativeTo(null);
        ownerFrame.setVisible(true);
        ownerFrame.setFocusable(false);
    }

    //MENU PAGE
    private void ownerMenuPage() {
        JPanel ownerMenuPanel = new JPanel(new GridLayout(6,1));
        JFrame ownerMenuFrame = new JFrame("Property Owner Menu Page");
        JLabel ownerLabel = new JLabel("Choose task menu:");
        
        JButton addPropertyButton = new JButton("Upload New Property");
        addPropertyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 ownerMenuFrame.setVisible(false);
                 AddProperty();
            }
        });
        JButton searchPropertyButton = new JButton("Search Property");
        searchPropertyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ownerMenuFrame.setVisible(false);
                new MainPage();
            }
        });
        JButton viewReportButton = new JButton("View Board");
        viewReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ownerMenuFrame.setVisible(false);
                viewReport();
            }
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ownerMenuFrame.setVisible(false);
                new MainPage();
            }
        });

        ownerMenuPanel.add(ownerLabel);
        ownerMenuPanel.add(viewReportButton);
        ownerMenuPanel.add(addPropertyButton);
        ownerMenuPanel.add(backButton);
        ownerMenuFrame.add(ownerMenuPanel);
        ownerMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ownerMenuFrame.setSize(500, 400);
        ownerMenuFrame.setLocationRelativeTo(null);
        ownerMenuFrame.setVisible(true);
        ownerMenuFrame.setFocusable(false);    
    }

    //UPLOAD NEW PROPERTY 
    private void AddProperty() {
        JPanel ownerPanel = new JPanel(new GridLayout(11,1));
        JFrame ownerFrame = new JFrame("Add New Property Page");
        registerSuccess = new JLabel ("");

        // property id (6 digits random)
        id = getRandomNumber(100000, 999999);
        propId = Integer.toString(id);
        // property name
        propNameLabel = new JLabel("Property Name");
        propNameText = new JTextField();
        // property address
        addLabel = new JLabel("Address (without comma)");
        addText = new JTextField();
        // property type/category
        typeLabel = new JLabel("Property category or type");
        typeText = new JTextField();
        // property size
        sizeLabel = new JLabel("Property Size");
        sizeText = new JTextField();
        // rooms
        roomLabel = new JLabel("Number of rooms");
        roomText = new JTextField();
        // bathrooms
        broomLabel = new JLabel("Number of bathrooms");
        broomText = new JTextField();
        // facilities
        facilitiesLabel = new JLabel("Facilities provided");
        facilitiesText = new JTextField();
        // rental fee
        feeLabel = new JLabel("Rental Fee");
        feeText = new JTextField();
        // property owner phone number
        phoneLabel = new JLabel("Phone Number");
        phoneText = new JTextField();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ownerFrame.setVisible(false);
                ownerMenuPage();
            }
        });

        JButton submitButton = new JButton("Upload Property");
        submitButton.setSize(100, 100);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                propName = propNameText.getText();
                propAddress = addText.getText();
                propType = typeText.getText();
                propSize = sizeText.getText();
                propRoom = roomText.getText();
                propBroom = broomText.getText();
                propFacilities = facilitiesText.getText();
                propFee = feeText.getText();
                propContact = phoneText.getText();
        
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
        ownerPanel.add(propNameLabel);
        ownerPanel.add(propNameText);
        ownerPanel.add(addLabel);
        ownerPanel.add(addText);
        ownerPanel.add(typeLabel);
        ownerPanel.add(typeText);
        ownerPanel.add(sizeLabel);
        ownerPanel.add(sizeText);
        ownerPanel.add(roomLabel);
        ownerPanel.add(roomText);
        ownerPanel.add(broomLabel);
        ownerPanel.add(broomText);
        ownerPanel.add(facilitiesLabel);
        ownerPanel.add(facilitiesText);
        ownerPanel.add(feeLabel);
        ownerPanel.add(feeText);
        ownerPanel.add(phoneLabel);
        ownerPanel.add(phoneText);
        ownerPanel.add(backButton);
        ownerPanel.add(submitButton);
        ownerPanel.add(registerSuccess);
        ownerFrame.add(ownerPanel);
        ownerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ownerFrame.setSize(500, 400);
        ownerFrame.setLocationRelativeTo(null);
        ownerFrame.setVisible(true);
        ownerFrame.setFocusable(false);
    }

    //Search Property based on property type 
    //SORT BY PROPERTY TYPE
    private void sortByPropertyType(String filepath)  throws IOException {
        JFrame frame = new JFrame("Search by property type");
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
                ownerMenuPage();
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
            lines = Files.readAllLines(Paths.get(tempPropertyTypeCSV));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String id = getIdCSV(i, tempPropertyTypeCSV), name = getNameCSV(i, tempPropertyTypeCSV), address = getAddressCSV(i, tempPropertyTypeCSV),
                    feetSize = getFeetSizeCSV(i, tempPropertyTypeCSV), type = getTypeCSV(i, tempPropertyTypeCSV), room = getRoomCSV(i, tempPropertyTypeCSV),
                    bathroom = getBathroomCSV(i, tempPropertyTypeCSV), facilities = getFacilitiesCSV(i, tempPropertyTypeCSV),
                    rentalPrice = getRentalPriceCSV(i, tempPropertyTypeCSV), contact = getContactCSV(i, tempPropertyTypeCSV);

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
        }

        JScrollPane sp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(sp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);
    }

    //Deactivate Property (delete/deactivate button)
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
    
            if (shouldFill) c.fill = GridBagConstraints.HORIZONTAL;
            if (shouldWeightX) c.weightx = 0.5;
            c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0;
            panel.add(backButton, c);
    
            List<String> lines;
            try {
                lines = Files.readAllLines(Paths.get(filepath));
            } catch (IOException e) {
                lines = null;
            }
    
            for (int i = 1; i < lines.size();i++) {     // skip first row (column header)
                String id = getIdCSV(i, filepath);
                Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
                JPanel panel1 = new JPanel(new GridLayout(10, 1));
    
                panel1.add(new JLabel(("Name                   : " + getNameCSV(i, filepath))));
                panel1.add(new JLabel(("Address               : " + getAddressCSV(i, filepath))));
                panel1.add(new JLabel(("Built-up Size (ft)  : " + getFeetSizeCSV(i, filepath))));
                panel1.add(new JLabel(("Type                    : " + getTypeCSV(i, filepath))));
                panel1.add(new JLabel(("Room                   : " + getRoomCSV(i, filepath))));
                panel1.add(new JLabel(("Bathroom             : " + getBathroomCSV(i, filepath))));
                panel1.add(new JLabel(("Facilities              : " + getFacilitiesCSV(i, filepath))));
                panel1.add(new JLabel(("Rental (RM)          : " + getRentalPriceCSV(i, filepath))));
                panel1.add(new JLabel(("Contact               : " + getContactCSV(i, filepath))));
                JButton deleteButton = new JButton("Deactivate Property");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        frame.dispose();
                        removePropertyRecord(filepath, id);  
                        showAllListing(filepath);
                    }
                });
                deleteButton.setBackground(Color.RED);
                panel1.add(deleteButton);        
                panel1.setBorder(blackline1);   
                
                c.fill = GridBagConstraints.HORIZONTAL; c.ipady = 40; c.weightx = 0.0; c.anchor = GridBagConstraints.FIRST_LINE_START; c.gridwidth = 3; c.gridx = 1; c.gridy = i;
                panel1.add(deleteButton, c);
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

    //View Report/Board (similar as admin)
    private void viewReport() {
        JPanel panel = new JPanel(new GridLayout(8,1));
        JFrame frame = new JFrame("View Report Page");
        JLabel label = new JLabel("Choose your listing:");

        JButton ownerMenuButton1 = new JButton("List of Properties");
        ownerMenuButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                showAllListing(activepropertyCSV);
            }
        });
        JButton ownerMenuButton2 = new JButton("List of Property According to Property Type");
        ownerMenuButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByPropertyType(activepropertyCSV);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }            
            }
        });
        JButton ownerMenuButton3 = new JButton("List According to Project etc.");
        ownerMenuButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByProject(activepropertyCSV);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JButton ownerMenuButton4 = new JButton("List According to Facilities");
        ownerMenuButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByFacilities(activepropertyCSV);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JButton ownerMenuButton5 = new JButton("Sort By Rental Rate");
        ownerMenuButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    sortByRentalRate(activepropertyCSV);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JButton ownerMenuButton6 = new JButton("List According to Rental Fee");
        ownerMenuButton6.addActionListener(new ActionListener() {
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
                ownerMenuPage();
            }
        });

        panel.add(label);
        panel.add(ownerMenuButton1);
        panel.add(ownerMenuButton2);
        panel.add(ownerMenuButton3);
        panel.add(ownerMenuButton4);
        panel.add(ownerMenuButton5);
        panel.add(ownerMenuButton6);
        panel.add(backButton);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(false);  
    }

    //SORT BY PROJECTS
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

    //SORT BY FACILITY
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

    //SORT BY RENTAL RATE
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

        JComboBox propertyTypeCombo = new JComboBox(RentalRateArr.toArray());
        propertyTypeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==propertyTypeCombo) {
                    frame.dispose();
                    filterTerm = (String) propertyTypeCombo.getSelectedItem();
                    if(filterTerm.equals("Select")) {
                        try {
                            sortByRentalRate(activepropertyCSV);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    else {
                        property = returnSortedRentalRateProperty(activepropertyCSV, filterTerm);
                        saveTempCSV(property, tempPropertyTypeCSV);
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
            //panel1.add(rentButton, c);
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

    //SORT BY RENTAL FEE
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
                            saveTempCSV(sortedProperty, tempPropertyTypeCSV);
                            sortByRentalFee(activepropertyCSV);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    else if(filterTerm.equals("From Highest")){
                        try {
                            int[] array = returnSortRentalFeeArray(activepropertyCSV, "From Highest");        // sorted descending Property fee ONLY
                            sortedProperty = returnSortedProperty(activepropertyCSV, array);
                            saveTempCSV(sortedProperty, tempPropertyTypeCSV);
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
            lines = Files.readAllLines(Paths.get(tempPropertyTypeCSV));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {    
            String id = getIdCSV(i, tempPropertyTypeCSV);
            Border blackline1 = BorderFactory.createTitledBorder("Property ID: " + id);
            JPanel panel1 = new JPanel(new GridLayout(9, 1));
        
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

    //GENERATE RANDOM ID FOR PROPERTY
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}