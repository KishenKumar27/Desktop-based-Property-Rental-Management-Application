
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class User extends Property{
    private String name, phone, username, password, passwordConfirm;
    private Boolean status;
    private static Scanner x;

    public User() {}

    public User(Boolean status, String name, String phone, String username, String password, String passwordConfirm) {
        this.status = status;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public User(String name, String phone, String username, String password, String passwordConfirm) {
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public Boolean getStatus() {
        return status;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public String toString() {
        return name + " " + phone + " " + username + " " + password + " " + passwordConfirm;
    }
    public String toStringCSV() {
        return status + "," + name + "," + phone + "," + username + "," + password + "," + passwordConfirm;
    }
    public String toStringCSV2() {
        return name + "," + phone + "," + username + "," + password + "," + passwordConfirm;
    }
    
    protected static ArrayList<User> readRegistrationCSV(String filepath) {
        ArrayList<User> newUser = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(","); 
            Boolean status = Boolean.valueOf(items[0]); 
            String name = String.valueOf(items[1]); 
            String phone = String.valueOf(items[2]); 
            String username = String.valueOf(items[3]);
            String password = String.valueOf(items[4]);
            String passwordConfirm = String.valueOf(items[5]);
            newUser.add(new User(status, name, phone, username, password, passwordConfirm));
        }
        return newUser;
    }
    
    protected static ArrayList<User> readUserCSV(String filepath) {
        ArrayList<User> user = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(","); 
            String name = String.valueOf(items[0]); 
            String phone = String.valueOf(items[1]); 
            String username = String.valueOf(items[2]);
            String password = String.valueOf(items[3]);
            String passwordConfirm = String.valueOf(items[4]);
            user.add(new User(name, phone, username, password, passwordConfirm));
        }
        return user;
    }
    
    protected static void saveRegistrationCSV(ArrayList<User> newUser, String filepath) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < newUser.size(); i++) {
            sb.append(newUser.get(i).toStringCSV() + "\n");
        }
        try {
            Files.write(Paths.get(filepath), sb.toString().getBytes());
        } catch (IOException e) {
            System.out.println("ERROR in saving to " + filepath);
        }
    }
    
    protected static void saveUserCSV(ArrayList<User> user, String filepath) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < user.size(); i++) {
            sb.append(user.get(i).toStringCSV2() + "\n");
        }
        try {
            Files.write(Paths.get(filepath), sb.toString().getBytes());
        } catch (IOException e) {
            System.out.println("ERROR saving to " + filepath);
        }
    }
    
    protected static String getUsernameCSV(ArrayList<User> newUser, String newUsername, String filepath) {
        String name = "", phone = "", username = "", password = "", passwordConfirm = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            name = String.valueOf(items[0]);
            phone = String.valueOf(items[1]);
            username = String.valueOf(items[2]);
            password = String.valueOf(items[3]);
            passwordConfirm = String.valueOf(items[4]);

            if(username.equals(newUsername)) {
                return username;
            }
        }
        return null;
    }
    
    protected static String getPasswordCSV(ArrayList<User> newUser, String newPassword, String filepath) {
        String name = "", phone = "", username = "", password = "", passwordConfirm = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            name = String.valueOf(items[0]);
            phone = String.valueOf(items[1]);
            username = String.valueOf(items[2]);
            password = String.valueOf(items[3]);
            passwordConfirm = String.valueOf(items[4]);

            if(password.equals(newPassword)) {
                return password;
            }
        }
        return null;
    }
    
    protected static void removeAdminRecord(String filepath, String removeUsername) {
        String tempFile = "temp.csv";
        File oldFile = new File(filepath);
        File newFile = new File (tempFile);
        Boolean status = false;
        String name = "", phone = "", username = "", password = "", passwordConfirm = "";
        
        try{
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while(x.hasNextBoolean() && x.hasNext()) {
                status = x.nextBoolean();
                name = x.next();
                phone = x.next();
                username = x.next();
                password = x.next();
                passwordConfirm = x.next();

                if(!username.equals(removeUsername)) 
                    pw.println(status + "," + name + "," + phone + "," + username + "," + password + "," + passwordConfirm);
            }
            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File (filepath);
            newFile.renameTo(dump);

        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error removing CSV rows", "Remove CSV Status", JOptionPane.ERROR_MESSAGE);
        }
    }
}