import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_PATH = "data/reservations.txt";

    public static void saveReservations(ArrayList<Reservation> reservations) {
        File file = new File(FILE_PATH);

        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Reservation reservation : reservations) {
                    writer.write(
                            reservation.getReservationId() + "|" +
                            reservation.getGuestName().replace("|", " ") + "|" +
                            reservation.getRoom().getRoomNumber() + "|" +
                            reservation.getNights()
                    );
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to save reservations: " + e.getMessage());
        }
    }

    public static ArrayList<String[]> loadReservations() {
        ArrayList<String[]> records = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return records;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|", -1);

                    if (parts.length == 4) {
                        records.add(parts);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to load reservations: " + e.getMessage());
        }

        return records;
    }
}
