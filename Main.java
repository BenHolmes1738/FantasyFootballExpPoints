import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    /**
     * main function
     * controls general flow of program
     */
    public static void main(String[] args) {
        String getFile = "Data/linesUVCBEN.txt";
        getFile = "Data/" + args[0];
        String setFile = "Data/ExpPoints.txt";

        ArrayList<Player> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getFile))) {
            String name;
            String position;
            
            do {
                name = br.readLine();
                position = br.readLine();
                Player player = new Player(name, position);
                System.out.println("player: " + name);
                if ("QB".equals(position)) {
                    ReadingWriting.readQB(player, br);
                } else {
                    ReadingWriting.readNonQB(player, br);
                }

                players.add(player);

            } while ((br.readLine()) != null);
            
            try {
                QuickSort.quicksort(players);
                FileWriter writer = new FileWriter(setFile);
                for (Player p : players) {
                    if (p.getPosition().equals("QB")) {
                        ReadingWriting.writeQB(p, writer);
                    } else {
                        ReadingWriting.writeNonQB(p, writer);
                    }
                }
                writer.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

}