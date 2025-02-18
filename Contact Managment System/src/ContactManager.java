
import java.io.*;
import java.util.*;

public class ContactManager {
    private List<Contact> contacts = new ArrayList<>();
    private final String FILE_NAME = "contacts.txt";

    public ContactManager() {
        loadContacts();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
    }

    public void deleteContact(String name) {
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
        saveContacts();
    }

    public Contact searchContact(String name) {
        return contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void listContacts() {
        contacts.forEach(System.out::println);
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading contacts: " + e.getMessage());
        }
    }

    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving contacts: " + e.getMessage());
        }
    }
}
