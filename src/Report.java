import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Report {
    public static void PrintReport(ParkingLot parkingLot , PrintWriter writer){
            int[] Gatecounter = parkingLot.GetGateStats();

            writer.write("\nTotal Cars Served: " + parkingLot.getTotalCarsServed());
            writer.write("\nCurrent Cars in Parking: " + parkingLot.getOccupiedSpots());
            writer.write("\nDetails:");
            for(int i = 1; i < Gatecounter.length; i++){
                writer.write("\n-Gate" + i + ": " + Gatecounter[i]);
            }
    }
}
