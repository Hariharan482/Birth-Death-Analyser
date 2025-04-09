package com.analyser.people.utils;

import com.analyser.people.model.BirthDeathStatistic;
import com.analyser.people.model.enums.VitalStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;

public class FileUtils {

    private FileUtils(){}

    public static ArrayList<BirthDeathStatistic> getData(String filePath){
        ArrayList<BirthDeathStatistic> birthDeathStatisticsList=new ArrayList<>();
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] birthDeathStatisticsData= line.split(",");
                birthDeathStatisticsList.add(new BirthDeathStatistic(Year.of(Integer.parseInt(birthDeathStatisticsData[0])), VitalStatus.valueOf(birthDeathStatisticsData[1].toUpperCase()),birthDeathStatisticsData[2],Integer.parseInt(birthDeathStatisticsData[3])));
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return birthDeathStatisticsList;
    }
}
