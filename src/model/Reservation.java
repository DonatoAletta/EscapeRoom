package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {

    String clientName;

    LocalDateTime availableReservationTime;

    int clientAmount;

    int maximumPeopleAmount;

    public Reservation(String clientName, LocalDateTime availableReservationTime, int clientAmount) {
        this.clientName = clientName;
        this.availableReservationTime = availableReservationTime;
        this.clientAmount = clientAmount;
        this.maximumPeopleAmount = 6;
    }

    public String getClient() {
        return clientName;
    }

    public void setClient(String client) {
        this.clientName = client;
    }

    public LocalDateTime getAvailableReservationTime() {
        return availableReservationTime;
    }

    public void setAvailableReservationTime(LocalDateTime availableReservationTime) {
        this.availableReservationTime = availableReservationTime;
    }

    public int getClientAmount() {
        return clientAmount;
    }

    public void setClientAmount(int clientAmount) {
        this.clientAmount = clientAmount;
    }

    public int getMaximumPeopleAmount() {
        return maximumPeopleAmount;
    }

    public void setMaximumPeopleAmount(int maximumPeopleAmount) {
        this.maximumPeopleAmount = maximumPeopleAmount;
    }

    public String getReservationInfo(){
        String reservationTime = getAvailableReservationTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        return "Ore: " + reservationTime + " Persone:" + getClientAmount();
    }

    public String getFullReservationInfo(){
        String reservationTime = getAvailableReservationTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        return "Cliente: " + clientName + " Ore: " + reservationTime + " Persone:" + getClientAmount();
    }
}
