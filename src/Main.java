import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        int parkingSpots = 4;
        int gate1 = 0, gate2 = 0, gate3 = 0;

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            ParkingLot parkingLot = new ParkingLot(parkingSpots, writer);
            List<Thread> cars = new ArrayList<>();

            // Reading the input file to create car threads
            try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(", ");
                    int gate = Integer.parseInt(parts[0].split(" ")[1]);
                    int carId = Integer.parseInt(parts[1].split(" ")[1]);
                    int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                    int parkingDuration = Integer.parseInt(parts[3].split(" ")[1]);

                    Car car = new Car(carId, gate, arrivalTime, parkingDuration, parkingLot);
                    gate1 += gate == 1 ? 1 : 0;
                    gate2 += gate == 2 ? 1 : 0;
                    gate3 += gate == 3 ? 1 : 0;

                    Thread carThread = new Thread(car);
                    cars.add(carThread);
                    carThread.start();
                }
            } catch (IOException e) {
                System.err.println("Error reading input file: " + e.getMessage());
            }

            // Wait for all cars to finish
            for (Thread carThread : cars) {
                try {
                    carThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            writer.write("\nTotal Cars Served: " + parkingLot.getTotalCarsServed());
            writer.write("\nCurrent Cars in Parking: " + parkingLot.getOccupiedSpots());
            writer.write("\nDetails:");
            writer.write("\n-Gate 1: " + gate1);
            writer.write("\n-Gate 2: " + gate2);
            writer.write("\n-Gate 3: " + gate3);

        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }
}