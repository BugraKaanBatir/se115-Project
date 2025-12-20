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
        if(from<1 || to>DAYS || from>to) {
            return -99999;
        }
        int commodityIndex = -1;
        for(int i = 0; i<COMMS; i++){
            if(commodities[i].equals(commodity)){
                commodityIndex = i;
                break;
            }
        }
        if (commodityIndex == -1){
            return -99999;
        }
        int totalProfit = 0;
        for(int i = 0; i<MONTHS; i++){
            for(int j = from - 1; j<to; j++){
                totalProfit += profitData[j][i][commodityIndex];
            }
        }
        return totalProfit;
    }

    public static int bestDayOfMonth(int month) {
        if(month<0 || month>= MONTHS) {
            return -1;
        }

        int maxProfit = Integer.MIN_VALUE;
        int bestDay= -1;
        for(int i = 0; i<DAYS; i++){
            int dailyTotalProfit = 0;
            for(int j = 0; j<COMMS; j++){
                dailyTotalProfit += profitData[i][month][j];
            }
            if(dailyTotalProfit>maxProfit){
                maxProfit = dailyTotalProfit;
                bestDay = i + 1;
            }
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) {
        int commIndex =  -1;
        for(int i = 0; i<COMMS; i++){
            if (commodities[i].equals(comm)){
                commIndex = i;
                break;
            }
        }
        if(commIndex == -1) return "INVALID_COMMODITY";

        int maxProfit = Integer.MIN_VALUE;
        int bestMonthIndex = -1;
        for(int i = 0; i<MONTHS; i++){
            int monthlyTotalProfit = 0;
            for(int j = 0; j<DAYS; j++){
                monthlyTotalProfit += profitData[j][i][commIndex];
            }
            if(monthlyTotalProfit>maxProfit){
                maxProfit = monthlyTotalProfit;
                bestMonthIndex = i;
            }
        }
        return months[bestMonthIndex];
    }

    public static int consecutiveLossDays(String comm) {
        int commIndex = -1;
        for(int i = 0;i<COMMS; i++){
            if(commodities[i].equals(comm)){
                commIndex = i;
                break;
            }
        }
        if(commIndex == -1) return -1;
        int maxStreak = 0;
        int currentStreak = 0;

        for(int i = 0; i<MONTHS; i++){
            for(int j = 0; j<DAYS; j++){
                int profit = profitData[j][i][commIndex];

                if(profit<0){
                    currentStreak++;
                }else{
                    if(currentStreak>maxStreak){
                        maxStreak = currentStreak;
                    }
                    currentStreak = 0;
                }
            }
        }
        // eğer 1 ürün yıl sonuna kadar zarar ettiyse
        if(currentStreak>maxStreak){
            maxStreak = currentStreak;
        }
        return maxStreak;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
        int commIndex = -1;
        for(int i = 0; i<COMMS; i++){
            if(commodities[i].equals(comm)){
                commIndex =i;
                break;
            }
        }
        if(commIndex == -1) return -1;
        int count = 0;
        for(int i = 0; i<MONTHS; i++){
            for(int j = 0; j<DAYS; j++){
                if(profitData[j][i][commIndex]>threshold){
                    count++;
                }
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) { 
        if(month<0 || month>=MONTHS) return -99999;
        int maxSwing = Integer.MIN_VALUE;
        int prevDayTotalProfit = 0;

        for(int i = 0; i<COMMS; i++){
            prevDayTotalProfit += profitData[0][month][i];
        }

        for(int i = 1; i<DAYS; i++){
            int currentDayTotalProfit = 0;
            for(int j = 0; j<COMMS; j++){
                currentDayTotalProfit += profitData[i][month][j];
            }
            int diff = Math.abs(currentDayTotalProfit - prevDayTotalProfit);
            if(diff>maxSwing){
                maxSwing = diff;
            }
            prevDayTotalProfit = currentDayTotalProfit;
        }
        return maxSwing;
    }
    
    public static String compareTwoCommodities(String c1, String c2) {
        int commIndex1 = -1;
        for(int i = 0; i<COMMS; i++){
            if(commodities[i].equals(c1)){
                commIndex1 = i;
                break;
            }
        }

        int commIndex2 = -1;
        for(int i = 0; i<COMMS; i++){
            if(commodities[i].equals(c2)){
                commIndex2 = i;
                break;
            }
        }

        if (commIndex1 == -1 || commIndex2 == -1){
            return "INVALID_COMMODITY";
        }

        int totalProfit1 = 0;
        for (int i = 0; i<MONTHS; i++){
            for(int j = 0; j<DAYS; j++){
                totalProfit1 += profitData[j][i][commIndex1];
            }
        }

        int totalProfit2 = 0;
        for (int i = 0; i<MONTHS; i++){
            for(int j = 0; j<DAYS; j++){
                totalProfit2 += profitData[j][i][commIndex2];
            }
        }

        if(totalProfit1>totalProfit2){
            int diff = totalProfit1 - totalProfit2;
            return c1 + "is better by " + diff;
        }else if(totalProfit2>totalProfit1){
            int diff = totalProfit2 - totalProfit1;
            return c2 + "is beter by" + diff;
        }else{
            return "Equal";
        }

    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");


    }
}