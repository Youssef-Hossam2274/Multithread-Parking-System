import java.util.concurrent.TimeUnit;

class Car implements Runnable {
    private final int id;
    private final int gate;
    private final int arrivalTime;
    private final int parkingDuration;
    private final ParkingLot parkingLot;

    public Car(int id, int gate, int arrivalTime, int parkingDuration, ParkingLot parkingLot) {
        this.id = id;
        this.gate = gate;
        this.arrivalTime = arrivalTime;
        this.parkingDuration = parkingDuration;
        this.parkingLot = parkingLot;
    }

    public int getId() {
        return id;
    }

    public int getGate() {
        return gate;
    }

    public int getParkingDuration() {
        return parkingDuration;
    }

    @Override
    public void run() {
        try {
            // Simulate car arrival
            TimeUnit.SECONDS.sleep(arrivalTime);
            parkingLot.writer.println("Car " + id + " from " + gate + " arrived at time " + arrivalTime);
            parkingLot.writer.flush();

            // Try to park the car
            while (!parkingLot.tryPark(this)) {
                TimeUnit.SECONDS.sleep(1); // Retry after 1 second
            }

            // Simulate parking duration
            TimeUnit.SECONDS.sleep(parkingDuration);
            parkingLot.leave(this);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}