import com.analyser.people.service.implementation.BirthDeathAnalyticsServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BirthDeathAnalyticsServiceImpl birthDeathAnalyticsService = new BirthDeathAnalyticsServiceImpl();

        boolean isExit = false;
        Scanner scanner=new Scanner(System.in);
        while (!isExit) {
            System.out.println();
            System.out.println("Select the operation to perform");
            System.out.println("1-> Display all the Regions ");
            System.out.println("2-> Display all the year mentioned under Period");
            System.out.println("3-> Overall Birth and Death count for all the years");
            System.out.println("4-> Year wise Birth and Death");
            System.out.println("5-> Display the number of Birth and Death for all the years for a specific Region ");
            System.out.println("6-> The year that has highest Birth and Death Rate");
            System.out.println("7-> Display the highest Birth and Death Rate for each region along with the year");
            System.out.println("0-> Exit");
            int operation = scanner.nextInt();
            switch (operation) {
                case 1:
                    List<String> uniqueRegions=birthDeathAnalyticsService.getUniqueRegions();
                    System.out.println("Regions : ");
                    uniqueRegions.forEach(System.out::println);
                    break;
                case 2:
                    birthDeathAnalyticsService.getUniqueYears();
                    break;
                case 3:
                    birthDeathAnalyticsService.getOverallBirthDeathCount();
                    break;
                case 4:
                    birthDeathAnalyticsService.getYearWiseBirthDeathCount();
                    break;
                case 5:
                    birthDeathAnalyticsService.getRegionalBirthDeathCount();
                    break;
                case 6:
                    birthDeathAnalyticsService.getHighestBirthDeathRateYear();
                    break;
                case 7:
                    birthDeathAnalyticsService.getHighestRatePerRegion();
                    break;
                case 0:
                    isExit = true;
                    System.out.println("Thanks for using! Have a great day!!");
                    break;
                default:
                    System.out.println("Enter a valid operation need to be done");
                    break;
            }
        }
        scanner.close();
    }
}