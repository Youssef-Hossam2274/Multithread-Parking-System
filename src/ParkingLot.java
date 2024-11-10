import java.io.PrintWriter;
import java.util.concurrent.Semaphore;


class ParkingLot {
    private final Semaphore parkingSpots;
    private int carsParked = 0;
    private int totalCarsServed = 0;
    public final PrintWriter writer;

    public ParkingLot(int spots, PrintWriter writer) {
        this.parkingSpots = new Semaphore(spots, true);
        this.writer = writer;
    }

    public synchronized boolean tryPark(Car car) throws InterruptedException {
        if (parkingSpots.tryAcquire()) {
            carsParked++;
            totalCarsServed++;
            writer.println("Car " + car.getId() + " from Gate " + car.getGate() +
                    " parked " + (car.getCarIsWaitedBefore() ? ("after waiting for " + car.getWaitingTime() + " units of time") : "") + ". (Parking Status: " + carsParked + " spots occupied)");
            writer.flush();
            return true;
        } else {
            if(!car.getCarIsWaitedBefore()){
                car.setCarIsCarWaitedBefore(true); // mark car that wait for a certain time
                writer.println("Car " + car.getId() + " from Gate " + car.getGate() + " waiting for a spot.");
                writer.flush();
            }
            return false;
        }
    }
    public synchronized void leave(Car car) {
        parkingSpots.release();
        carsParked--;
        writer.println("Car " + car.getId() + " from " + car.getGate() +
                " left after " + car.getParkingDuration() + " units of time. (Parking Status: " + carsParked + " spots occupied)");
        writer.flush();
    }
    public int getTotalCarsServed() {
        return totalCarsServed;
    }
    public int getOccupiedSpots(){
        return carsParked;
    }
}