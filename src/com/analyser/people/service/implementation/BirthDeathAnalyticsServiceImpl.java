package com.analyser.people.service.implementation;

import com.analyser.people.model.BirthDeathStatistic;
import com.analyser.people.model.enums.VitalStatus;
import com.analyser.people.service.interfaces.BirthDeathAnalyticsService;
import com.analyser.people.utils.FileUtils;

import java.time.Year;
import java.util.*;

public class BirthDeathAnalyticsServiceImpl implements BirthDeathAnalyticsService {
    private final ArrayList<BirthDeathStatistic> birthDeathStatistics;

    public BirthDeathAnalyticsServiceImpl(){
        String filePath = System.getProperty("user.dir") + "/Files/Birth Death Dataset.csv";
        this.birthDeathStatistics= FileUtils.getData(filePath);
    }

    @Override
    public List<String> getUniqueRegions() {
        TreeSet<String> uniqueRegions=new TreeSet<>();
        for(BirthDeathStatistic birthDeathStatistic:birthDeathStatistics){
            uniqueRegions.add(birthDeathStatistic.region());
        }
        return uniqueRegions.stream().toList();
    }

    @Override
    public void getUniqueYears() {
        TreeSet<String> uniqueYears=new TreeSet<>();
        for(BirthDeathStatistic birthDeathStatistic:birthDeathStatistics){
            uniqueYears.add(birthDeathStatistic.period().toString());
        }
        System.out.println("Period");
        uniqueYears.forEach(System.out::println);
    }

    @Override
    public void getOverallBirthDeathCount() {
        Map<VitalStatus,Integer> overallCount=new HashMap<>();
        for(BirthDeathStatistic birthDeathStatistic:birthDeathStatistics){
           overallCount.put(birthDeathStatistic.status(),overallCount.getOrDefault(birthDeathStatistic.status(),0)+birthDeathStatistic.count());
        }
        System.out.println("Overall Birth Count across years: "+overallCount.get(VitalStatus.BIRTHS));
        System.out.println("Overall Death Count across years: "+overallCount.get(VitalStatus.DEATHS));
    }

    @Override
    public void getYearWiseBirthDeathCount() {
        Map<Year,Map<VitalStatus,Integer>> yearWiseOverallCount=new HashMap<>();
        for(BirthDeathStatistic birthDeathStatistic:birthDeathStatistics) {
            Map<VitalStatus,Integer> yearWiseCount=yearWiseOverallCount.getOrDefault(birthDeathStatistic.period(),new HashMap<>());
            yearWiseCount.put(birthDeathStatistic.status(),yearWiseCount.getOrDefault(birthDeathStatistic.status(),0)+ birthDeathStatistic.count());
            yearWiseOverallCount.put(birthDeathStatistic.period(),yearWiseCount);
        }

        for(Year year:yearWiseOverallCount.keySet()){
            System.out.println("Year : "+year);
            String[] headers = {"Type", "Count"};
            System.out.printf("%-15s %-15s %n", headers[0], headers[1]);
            Map<VitalStatus, Integer> yearWiseCount =yearWiseOverallCount.get(year);
            for (Map.Entry<VitalStatus, Integer> entry : yearWiseCount.entrySet()) {
                System.out.printf("%-15s %-15s %n",entry.getKey(),entry.getValue());
            }
        }
    }

    @Override
    public void getRegionalBirthDeathCount() {
        Map<String,Map<VitalStatus,Integer>> regionalWiseOverallCount =new HashMap<>();
        for(BirthDeathStatistic birthDeathStatistic:birthDeathStatistics) {
            Map<VitalStatus,Integer> regionWiseCount= regionalWiseOverallCount.getOrDefault(birthDeathStatistic.region(),new HashMap<>());
            regionWiseCount.put(birthDeathStatistic.status(),regionWiseCount.getOrDefault(birthDeathStatistic.status(),0)+ birthDeathStatistic.count());
            regionalWiseOverallCount.put(birthDeathStatistic.region(),regionWiseCount);
        }

        for(String region:regionalWiseOverallCount.keySet()){
            System.out.println("Region : "+region);
            String[] headers = {"Type", "Count"};
            System.out.printf("%-15s %-15s %n", headers[0], headers[1]);
            Map<VitalStatus, Integer> regionWiseCount =regionalWiseOverallCount.get(region);
            for (Map.Entry<VitalStatus, Integer> entry : regionWiseCount.entrySet()) {
                System.out.printf("%-15s %-15s %n",entry.getKey(),entry.getValue());
            }
        }
    }

    @Override
    public void getHighestBirthDeathRateYear() {
        int highestBirthRate=-1,highestDeathRate=-1;
        String highestBirthRateYear = null,highestDeathRateYear=null;

        for(BirthDeathStatistic birthDeathStatistic:birthDeathStatistics) {
            if(birthDeathStatistic.status()==VitalStatus.BIRTHS && birthDeathStatistic.count()>highestBirthRate){
                highestBirthRateYear=birthDeathStatistic.period().toString();
                highestBirthRate=birthDeathStatistic.count();
            } else if (birthDeathStatistic.status()==VitalStatus.DEATHS && birthDeathStatistic.count()>highestDeathRate){
                highestDeathRateYear=birthDeathStatistic.period().toString();
                highestDeathRate=birthDeathStatistic.count();
            }
        }

        System.out.println("Highest Birth Rate: "+highestBirthRateYear+"-"+highestBirthRate);
        System.out.println("Highest Death Rate: "+highestDeathRateYear+"-"+highestDeathRate);
    }

    @Override
    public void getHighestRatePerRegion() {
        Map<String,Map<VitalStatus,BirthDeathStatistic>> regionalWiseOverallCount =new HashMap<>();
        for(BirthDeathStatistic birthDeathStatistic:birthDeathStatistics) {
            Map<VitalStatus,BirthDeathStatistic> regionWiseCount= regionalWiseOverallCount.getOrDefault(birthDeathStatistic.region(),new HashMap<>());
            BirthDeathStatistic birthDeathStatisticRecord=regionWiseCount.get(birthDeathStatistic.status());
            if(birthDeathStatisticRecord==null || birthDeathStatistic.count()>birthDeathStatisticRecord.count()) {
                regionWiseCount.put(birthDeathStatistic.status(), birthDeathStatistic);
                regionalWiseOverallCount.put(birthDeathStatistic.region(),regionWiseCount);
            }
        }

        for(String region:regionalWiseOverallCount.keySet()){
            System.out.println("Region : "+region);
            String[] headers = {"Year", "Type", "Highest Count"};
            System.out.printf("%-15s %-15s %-15s %n", headers[0], headers[1],headers[2]);
            Map<VitalStatus, BirthDeathStatistic> regionWiseCount =regionalWiseOverallCount.get(region);
            for (Map.Entry<VitalStatus, BirthDeathStatistic> entry : regionWiseCount.entrySet()) {
                System.out.printf("%-15s %-15s %-15s %n",entry.getValue().period(),entry.getKey(),entry.getValue().count());
            }
        }
    }
}
