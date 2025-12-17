// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};

    static int[][][] profitData = new int[DAYS][MONTHS][COMMS];

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int i = 0; i < MONTHS; i++) {
            File f = new File("Data_Files/" + months[i] + ".txt");
            if (!f.exists()) continue;

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        try {
                            int day = Integer.parseInt(parts[0].trim());
                            String commName = parts[1].trim();
                            int profit = Integer.parseInt(parts[2].trim());

                            int dayIndex = day - 1;
                            int commIndex = -1;

                            for (int j = 0; j < COMMS; j++) {
                                if (commodities[j].equals(commName)) {
                                    commIndex = j;
                                    break;
                                }
                            }

                            if (dayIndex >= 0 && dayIndex < DAYS && commIndex != -1) {
                                profitData[dayIndex][i][commIndex] = profit;
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            } catch (IOException e) {
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month<0 || month>= MONTHS) {
            return "INVALID_MONTH";
        }

        int maxProfit= Integer.MIN_VALUE;
        String bestCommName = "";
        for(int i = 0; i<COMMS; i++){
            int currentCommTotalProfit = 0;
            for(int j = 0; j<DAYS; j++){
                currentCommTotalProfit += profitData[j][month][i];
            }
            if(currentCommTotalProfit>maxProfit){
                maxProfit = currentCommTotalProfit;
                bestCommName = commodities[i];
            }
        }
        return bestCommName + "  "+ maxProfit;

    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS){
            return -99999;
        }
        int dayIndex = day - 1;
        int total = 0;
        for(int i = 0; i< COMMS; i++){
            total += profitData[dayIndex][month][i];
        }
        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");


    }
}