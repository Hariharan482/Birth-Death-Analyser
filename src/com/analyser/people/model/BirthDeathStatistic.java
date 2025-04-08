package com.analyser.people.model;
import com.analyser.people.model.enums.VitalStatus;
import java.time.Year;

public record BirthDeathStatistic(Year period, VitalStatus status, String region, int count) {}
