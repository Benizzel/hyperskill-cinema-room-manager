import java.util.Scanner;

public class Cinema {

    private static char[][] cinemaPlan;
    private static int rows;
    private static int purchasedTickets = 0;
    private static int totalSeats;
    final private static int TICKET_PRICE_FRONT_ROW = 10;
    final private static int TICKET_PRICE_BACK_ROW = 8;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        System.out.println();

        totalSeats = rows * seatsPerRow;

        cinemaPlan = createCinema(rows, seatsPerRow);

        while (true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit program");

            int choice = scanner.nextInt();

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                printCinema();
            } else if (choice == 2) {
                reserveSeat();
            } else if (choice == 3) {
                printStatistic();
            }
        }
    }

    private static char[][] createCinema(int rows, int seatsPerRow) {
        char[][] plan = new char[rows][seatsPerRow];

        for (int i = 0; i < plan.length; i++) {
            for (int j = 0; j < plan[i].length; j++) {
                plan[i][j] = 'S';
            }
        }

        return plan;
    }

    private static void printCinema() {
        System.out.println("Cinema:");

        for (int i = 0; i <= cinemaPlan[0].length; i++) {
            System.out.print(i == 0 ? " " : " " + i);
        }
        System.out.println();

        for (int i = 0; i < cinemaPlan.length; i++) {
            System.out.print(i+1);
            for (int j = 0; j < cinemaPlan[i].length; j++) {
                System.out.print(" " + cinemaPlan[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void reserveSeat() {
        Scanner scanner = new Scanner(System.in);
        int selectedRow;
        int selectedSeat;
        int frontHalfLimit = rows / 2;
        int ticketPrice;

        while(true) {
            System.out.println("Enter a row number:");
            selectedRow = scanner.nextInt();

            System.out.println("Enter a seat number in that row:");
            selectedSeat = scanner.nextInt();

            try {
                if (cinemaPlan[selectedRow - 1][selectedSeat - 1] == 'B') {
                    System.out.println("That ticket has already been purchased!");
                    System.out.println();

                } else {
                    cinemaPlan[selectedRow - 1][selectedSeat - 1] = 'B';
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong input!");
                System.out.println();
            }
        }

        ticketPrice = totalSeats <= 60 || selectedRow <= frontHalfLimit ? TICKET_PRICE_FRONT_ROW : TICKET_PRICE_BACK_ROW;

        purchasedTickets++;

        System.out.printf("Ticket Price: $%d\n", ticketPrice);

    }

    private static void printStatistic() {
        System.out.printf("Number of purchased tickets: %d\n", purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", getOccupationRate());
        System.out.printf("Current income: $%d\n", getCurrentIncome());
        System.out.printf("Total income: $%d\n", getTotalIncome());
        System.out.println();

    }

    private static int getTotalIncome() {
        int frontHalfLimit = rows / 2;
        int totalIncome = 0;

        if (totalSeats <= 60) {
            totalIncome = totalSeats * TICKET_PRICE_FRONT_ROW;
        } else {
            for (int i = 0; i < cinemaPlan.length; i++) {
                for (int j = 0; j < cinemaPlan[i].length; j++) {
                    //be careful: Index starts with zero, so with have zu add 1 to check frontHalfLimit
                    totalIncome = totalIncome + (i + 1 <= frontHalfLimit ? TICKET_PRICE_FRONT_ROW : TICKET_PRICE_BACK_ROW);
                }
            }
        }
        return totalIncome;
    }

    private static int getCurrentIncome() {
        int frontHalfLimit = rows / 2;
        int currentIncome = 0;

        for (int i = 0; i < cinemaPlan.length; i++) {
            for (int j = 0; j < cinemaPlan[i].length; j++) {
                if (cinemaPlan[i][j] == 'B') {
                    //be careful: Index starts with zero, so with have zu add 1 to check frontHalfLimit
                    currentIncome = currentIncome + (i + 1 <= frontHalfLimit ? TICKET_PRICE_FRONT_ROW : TICKET_PRICE_BACK_ROW);
                }
            }
        }

        return currentIncome;
    }

    private static float getOccupationRate() {
        return (float) purchasedTickets / totalSeats * 100;
    }
}