import java.io.PrintWriter;
import java.util.concurrent.Semaphore;


class ParkingLot {
    private final Semaphore _parkingSpots;
    private int carsParked = 0;
    private int totalCarsServed = 0;
    private int[] _GateCounter;
    private int _spots;
    public final PrintWriter _writer;

    public ParkingLot(int NumberOfGates , int spots, PrintWriter writer) {
        _parkingSpots = new Semaphore(spots, true);
        _spots = spots;
        _writer = writer;
        _GateCounter = new int[NumberOfGates + 1];
    }

    public void newVisitor(int gateNumber){
        _GateCounter[gateNumber]++;
    }

    public synchronized boolean tryPark(Car car) throws InterruptedException {
        if (_parkingSpots.tryAcquire()) {
            carsParked++;
            totalCarsServed++;
            _writer.println("Car " + car.getId() + " from " + car.getGate() +
                    " parked. (Parking Status: " + carsParked + " spots occupied)");
            _writer.flush();
            return true;
        } else {
            _writer.println("Car " + car.getId() + " from " + car.getGate() + " waiting for a spot.");
            _writer.flush();
            return false;
        }
    }

    public synchronized void leave(Car car) {
        _parkingSpots.release();
        carsParked--;
        _writer.println("Car " + car.getId() + " from " + car.getGate() +
                " left after " + car.getParkingDuration() + " units of time. (Parking Status: " + carsParked + " spots occupied)");
        _writer.flush();
    }

    public int getTotalCarsServed() {
        return totalCarsServed;
    }

    public int getOccupiedSpots(){
        return carsParked;
    }
    public int[] GetGateStats(){
        return _GateCounter;
    }
}