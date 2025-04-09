package com.analyser.people.service.interfaces;
import java.util.List;

public interface BirthDeathAnalyticsService {
    List<String> getUniqueRegions();
    void getUniqueYears();
    void getOverallBirthDeathCount();
    void getYearWiseBirthDeathCount();
    void getRegionalBirthDeathCount();
    void getHighestBirthDeathRateYear();
    void getHighestRatePerRegion();
}
