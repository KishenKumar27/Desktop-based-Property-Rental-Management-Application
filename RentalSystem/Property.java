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

public class Property {
    private String id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact;
    private static Scanner x;

    public Property() {}

    public Property(String id, String name, String address, String feetSize, String type, String room, String bathroom, String facilities, String rentalPrice, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.feetSize = feetSize;
        this.type = type;
        this.room = room;
        this.bathroom = bathroom;
        this.facilities = facilities;
        this.rentalPrice = rentalPrice;
        this.contact = contact;
    }

    public String toString() {
        return id + " " + name + " " + address + " " + feetSize + " " + type + " " + room + " " + bathroom + " " + facilities + " " + rentalPrice + " " + contact;
    }
    public String toStringCSV() {
        return id + "," + name + "," + address + "," + feetSize + "," + type + "," + room + "," + bathroom + "," + facilities + "," + rentalPrice + "," + contact;
    }

    protected static ArrayList<Property> readPropertyCSV(String filepath) {
        ArrayList<Property> property = new ArrayList<>();
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";

        try{
            x = new Scanner(new File(filepath));
            x.useDelimiter("[,\"\"\n]");

            while(x.hasNext()) {
                id = x.next();
                name = x.next();
                address = x.next();
                feetSize = x.next();
                type = x.next();
                room = x.next();
                bathroom = x.next();
                facilities = x.next();
                rentalPrice = x.next();
                contact = x.next();

                property.add(new Property(id, name, address, feetSize, type, room, bathroom,facilities, rentalPrice, contact));
            }
            x.close();
            return property;

        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error Reading cyberproperty2.csv rows", "Reading CSV Status", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    protected static String getIdCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return id;
            }
        }
        return null;
    }

    protected static String getNameCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return name;
            }
        }
        return null;
    }

    protected static String getAddressCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return address;
            }
        }
        return null;
    }

    protected static String getFeetSizeCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return feetSize;
            }
        }
        return null;
    }

    protected static String getTypeCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return type;
            }
        }
        return null;
    }

    protected static String getRoomCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return room;
            }
        }
        return null;
    }

    protected static String getBathroomCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return bathroom;
            }
        }
        return null;
    }

    protected static String getFacilitiesCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return facilities;
            }
        }
        return null;
    }

    protected static String getRentalPriceCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return rentalPrice;
            }
        }
        return null;
    }

    protected static String getContactCSV(int index, String filepath) {
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(i == index) {
                return contact;
            }
        }
        return null;
    }

    protected static void removePropertyRecord(String filepath, String removeID) {
        String tempFile = "temp.csv";
        File oldFile = new File(filepath);
        File newFile = new File (tempFile);
        Boolean status = false;
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";

        try{
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while(x.hasNext()) {
                id = x.next();
                name = x.next();
                address = x.next();
                feetSize = x.next();
                type = x.next();
                room = x.next();
                bathroom = x.next();
                facilities = x.next();
                rentalPrice = x.next();
                contact = x.next();

                if(!(id.equals(removeID)))
                    pw.println(id + "," + name + "," + address + "," + feetSize + "," + type + "," + room + "," + bathroom + "," + facilities + "," + rentalPrice + "," + contact);
            }
            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File (filepath);
            newFile.renameTo(dump);
            if (filepath.equals("inactiveProperty.csv"))
                JOptionPane.showMessageDialog(null, "Property are available to rent!", "Property Status", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Property successfully deleted!", "Property Deletion Status", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error removing CSV rows", "Remove CSV Status", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected static ArrayList<Property> returnProperty(String filepath, String filterTerm) {
        ArrayList<Property> property = new ArrayList<>();
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 1; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            if(address.contains(filterTerm)) {
                property.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
            }
            else if(facilities.contains(filterTerm)) {
                property.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
            }
            else if(type.contains(filterTerm)) {
                property.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
            }
            else if(id.equals(filterTerm)) {
                property.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
            }
            else if(rentalPrice.equals(filterTerm)) {
                property.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
            }
        }
        return property;
    }

    protected static ArrayList<Property> returnSortedProperty(String filepath, int[] array) {
        ArrayList<Property> sortedProperty = new ArrayList<>();
        String id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact;

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int x = 0; x < array.length; x++) {                                            
            for (int i = 1; i < lines.size(); i++) {
                String[] items = lines.get(i).split(",");
                id = String.valueOf(items[0]);
                name = String.valueOf(items[1]);
                address = String.valueOf(items[2]);
                feetSize = String.valueOf(items[3]);
                type = String.valueOf(items[4]);
                room = String.valueOf(items[5]);
                bathroom = String.valueOf(items[6]);
                facilities = String.valueOf(items[7]);
                rentalPrice = String.valueOf(items[8]);
                contact = String.valueOf(items[9]);
    
                if(rentalPrice.equals(String.valueOf(array[x]))) 
                    sortedProperty.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
            }
        }
        return sortedProperty;
    }

    protected static void saveTempCSV(ArrayList<Property> property, String filepath) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < property.size(); i++) {
            sb.append(property.get(i).toStringCSV() + "\n");
        }
        try {
            Files.write(Paths.get(filepath), sb.toString().getBytes());
        } catch (IOException e) {
            System.out.println("ERROR in saving to " + filepath);
        }
    }

    protected static ArrayList<Property> readTempCSV(String filepath) {
        ArrayList<Property> property = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            String id = String.valueOf(items[0]);
            String name = String.valueOf(items[1]);
            String address = String.valueOf(items[2]);
            String feetSize = String.valueOf(items[3]);
            String type = String.valueOf(items[4]);
            String room = String.valueOf(items[5]);
            String bathroom = String.valueOf(items[6]);
            String facilities = String.valueOf(items[7]);
            String rentalPrice = String.valueOf(items[8]);
            String contact = String.valueOf(items[9]);
            property.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));
        }
        return property;
    }

    protected static ArrayList<Property> returnSortedRentalRateProperty(String filepath, String filterTerm) {
        ArrayList<Property> property = new ArrayList<>();
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }

        for (int i = 1; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            double rentalRate = Double.parseDouble(rentalPrice) / Double.parseDouble(feetSize);

            if (filterTerm.equals("Below RM1 per sqft") && rentalRate < 1) {

                property.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));

            } else if (filterTerm.equals("Between RM1 and RM2 per sqft") && rentalRate >= 1 && rentalRate<=2) {

                property.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));

            } else if (filterTerm.equals("Above RM2 per sqft") && rentalRate > 2) {

                property.add(new Property(id, name, address, feetSize, type, room, bathroom, facilities, rentalPrice, contact));

            }

        }
        return property;
    }

    protected static int[] returnSortRentalFeeArray(String filepath, String filterTerm) {
        int rentalfee;
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            lines = null;
        }
        int[] array = new int[lines.size()-1];

        for (int i = 1; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            id = String.valueOf(items[0]);
            name = String.valueOf(items[1]);
            address = String.valueOf(items[2]);
            feetSize = String.valueOf(items[3]);
            type = String.valueOf(items[4]);
            room = String.valueOf(items[5]);
            bathroom = String.valueOf(items[6]);
            facilities = String.valueOf(items[7]);
            rentalPrice = String.valueOf(items[8]);
            contact = String.valueOf(items[9]);

            // change from String to int
            rentalfee = Integer.parseInt(rentalPrice);  
            array[i - 1] = rentalfee;

        }

        if (filterTerm.equals("From Lowest")) {
            for (int y = 0; y < (array.length - 1); y++) {
                for (int z = 0; z < array.length - y - 1; z++) {
                    if (array[z] > array[z+1]) 
                    {
                        int temp = array[z];
                        array[z] = array[z+1];
                        array[z+1] = temp;
                    }
                }
            }
            return array;
        } 
        else if (filterTerm.equals("From Highest")) {
            for (int y = 0; y < (array.length - 1); y++) {
                for (int z = 0; z < array.length - y - 1; z++) {
                    if (array[z] < array[z+1]) 
                    {
                        int temp = array[z];
                        array[z] = array[z+1];
                        array[z+1] = temp;
                    }
                }
            }
            return array;
        } 
        return null;
    }

    protected static void rentProperty(String filepath, String rentID) {
        String tempFile = "temp2.csv";
        File oldFile = new File(filepath);
        File newFile = new File (tempFile);
        Boolean status = false;
        String id = "", name = "", address = "", feetSize = "", type = "", room = "", bathroom = "", facilities = "", rentalPrice = "", contact = "";

        try{
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while(x.hasNext()) {
                id = x.next();
                name = x.next();
                address = x.next();
                feetSize = x.next();
                type = x.next();
                room = x.next();
                bathroom = x.next();
                facilities = x.next();
                rentalPrice = x.next();
                contact = x.next();

                if(!(id.equals(rentID)))
                    pw.println(id + "," + name + "," + address + "," + feetSize + "," + type + "," + room + "," + bathroom + "," + facilities + "," + rentalPrice + "," + contact);
            }
            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File (filepath);
            newFile.renameTo(dump);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error renting Property", "Property Rent Status", JOptionPane.ERROR_MESSAGE);
        }
    }
}
