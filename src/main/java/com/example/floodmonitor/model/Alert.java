package com.example.floodmonitor.model;
import java.time.Instant;
public record Alert(String stationId,String river,Instant timestamp,WarningLevel warningLevel,double waterLevel) {}