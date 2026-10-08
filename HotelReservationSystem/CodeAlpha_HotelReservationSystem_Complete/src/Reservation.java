public class Reservation {
    private int reservationId;
    private String guestName;
    private Room room;
    private int nights;

    public Reservation(int reservationId, String guestName, Room room, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalAmount() {
        return room.getPricePerNight() * nights;
    }
}
