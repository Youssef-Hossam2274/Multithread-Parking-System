import java.util.concurrent.TimeUnit;

class Car implements Runnable {
    private final int _id;
    private final int _gate;
    private final int _arrivalTime;
    private final int _parkingDuration;
    private final ParkingLot _parkingLot;

    public Car(int id, int gate, int arrivalTime, int parkingDuration, ParkingLot parkingLot) {
        _id = id;
        _gate = gate;
        _arrivalTime = arrivalTime;
        _parkingDuration = parkingDuration;
        _parkingLot = parkingLot;
    }

    public int getId() {
        return _id;
    }

    public int getGate() {
        return _gate;
    }

    public int getParkingDuration() {
        return _parkingDuration;
    }

    @Override
    public void run() {
        try {
            // Simulate car arrival
            TimeUnit.SECONDS.sleep(_arrivalTime);
            _parkingLot._writer.println("Car " + _id + " from " + _gate + " arrived at time " + _arrivalTime);
            _parkingLot._writer.flush();

            // Try to park the car
            while (!_parkingLot.tryPark(this)) {
                TimeUnit.SECONDS.sleep(1); // Retry after 1 second
            }

            // Simulate parking duration
            TimeUnit.SECONDS.sleep(_parkingDuration);
            _parkingLot.leave(this);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}