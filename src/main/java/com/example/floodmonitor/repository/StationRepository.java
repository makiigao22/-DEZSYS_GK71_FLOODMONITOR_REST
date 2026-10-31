package com.example.floodmonitor.repository;
import com.example.floodmonitor.model.*;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.*;
@Repository public class StationRepository {
 public final Map<String,Station> stations=new ConcurrentHashMap<>();
 public final Map<String,List<Measurement>> measurements=new ConcurrentHashMap<>();
 public StationRepository(){
  add(new Station("ST-001","Wien Donau","Donau",new Location(48.22,16.41),230,300,370,true));
  add(new Station("ST-002","Linz Donau","Donau",new Location(48.31,14.29),190,260,340,true));
  add(new Station("ST-003","Salzburg Salzach","Salzach",new Location(47.80,13.04),170,240,320,true));
  add(new Station("ST-004","Graz Mur","Mur",new Location(47.07,15.44),140,210,290,true));
 }
 public void add(Station s){stations.put(s.id(),s);measurements.putIfAbsent(s.id(),new CopyOnWriteArrayList<>());}
}