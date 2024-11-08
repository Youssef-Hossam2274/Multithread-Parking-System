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
        int NumberOfGates = 3;

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {

            ParkingLot parkingLot = new ParkingLot(NumberOfGates , parkingSpots, writer);
            List<Thread> cars = new Reader().Read(parkingLot);

            for (Thread carThread : cars) {
                carThread.start();
            }

            // Wait for all cars to finish
            for (Thread carThread : cars) {
                try {
                    carThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            var report = new Report();
            report.PrintReport(parkingLot  , writer);

        } catch (IOException e){
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }
}