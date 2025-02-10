import model.Client;
import model.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Reservation> reservationsList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("""
                    \nMenu Prenotazioni
                    
                    1)Creare una prenotazione.
                    2)Visualizzare tutte le prenotazioni
                    3)Disdire prenotazione.
                    4)Esci.
                    """);

            int repsonse = input.nextInt();

            switch (repsonse) {
                case 1 -> reservationsList.add(performReservation(input));
                case 2 -> getAllReservations();
                case 3 -> removeReservation(input);
                case 4 -> {
                    System.out.println("Chiusura menu prenotazioni");
                    isRunning = false;
                }
            }
        }
    }

    private static LocalDateTime getFormattedDate(String chosenTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalTime time = LocalTime.parse(chosenTime, formatter);
            return LocalDateTime.of(LocalDate.now(), time);
        } catch (DateTimeParseException e) {
            System.out.println("\nOrario non valido. Assicurati di usare il formato HH:mm.");
            return null;
        }
    }

    private static Reservation performReservation(Scanner input) {
        input.nextLine();
        System.out.println("\nInserire nome cliente");
        Client client = new Client(input.nextLine());

        System.out.println("\nInserire un'orario ( es. 18:30 )");

        String chosenTime = input.nextLine();
        LocalDateTime date = getFormattedDate(chosenTime);

        if(date == null) {
            return null;
        }

        long count = reservationsList.stream()
                .filter(r -> r != null && r.getAvailableReservationTime() != null && r.getAvailableReservationTime().equals(date))
                .count();

        if (count >= 3) {
            System.out.println("\nMi dispiace, tutte le stanze sono già prenotate per l'orario scelto.");
            return null;
        }

        System.out.println("\nInserire il numero di persone escludendo il cliente");
        int peopleAmount = input.nextInt();

        if (!isValidPeopleAmount(peopleAmount)) {
            return null;
        }

        Reservation newReservation = new Reservation(client.getName(), date, peopleAmount + 1);
        System.out.println("\nPrenotazione effettuata per il cliente:\n" + client.getName() +
                "\nnumero persone: " + (peopleAmount + 1) +
                "\nper le ore: " + newReservation.getAvailableReservationTime().format(DateTimeFormatter.ofPattern("HH:mm")));

        return newReservation;
    }

    private static boolean isValidPeopleAmount(int peopleAmount) {
        if (peopleAmount > 5) {
            System.out.println("\nMi dispiace, ma è possibile accettare un massimo di 6 persone per prenotazione.");
            return false;
        }
        return true;
    }

    private static void removeReservation(Scanner input) {
        input.nextLine();

        System.out.println("\nInserire il nome del cliente");
        String clientName = input.nextLine();

        List<Reservation> reservations = reservationsList.stream()
                .filter(c -> c != null && c.getClient() != null && c.getClient().equals(clientName))
                .toList();

        if (reservations.isEmpty()) {
            System.out.println("\nNon sono state trovate prenotazioni per il cliente: " + clientName);
            return;
        }

        System.out.println("\nQuale prenotazione vuoi disdire ?");
        int counter = 1;

        for (Reservation reservation : reservations) {
            System.out.println(counter + ")" + reservation.getReservationInfo());
            counter++;
        }

        int chosenReservation = input.nextInt() - 1;

        reservationsList.remove(reservations.get(chosenReservation));

        System.out.println("\nPrenotazione disdetta!");
    }

    private static void getAllReservations() {
        if (reservationsList.isEmpty()) {
            System.out.println("\nNessuna prenotazione al momento.");
            return;
        }

        for (Reservation reservation : reservationsList) {
            System.out.println(reservation.getFullReservationInfo());
        }
    }
}