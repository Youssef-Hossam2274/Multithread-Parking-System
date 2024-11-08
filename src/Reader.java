import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public static List<Thread> Read(ParkingLot parkingLot){

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
                parkingLot.newVisitor(gate);


                Thread carThread = new Thread(car);
                cars.add(carThread);
//                carThread.start();
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }

        return cars;
    }
}
