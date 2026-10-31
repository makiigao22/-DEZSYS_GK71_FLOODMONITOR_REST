package com.example.floodmonitor.controller;
import com.example.floodmonitor.model.*;
import com.example.floodmonitor.service.FloodService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.*;
@RestController @RequestMapping(value="/api/v1",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
public class StationController {
 private final FloodService service;
 public StationController(FloodService service){this.service=service;}
 @GetMapping("/stations") public List<Station> stations(@RequestParam(required=false) String river,@RequestParam(required=false) Boolean isActive,@RequestParam(required=false) WarningLevel warningLevel){return service.stations(river,isActive,warningLevel);}
 @GetMapping("/stations/{stationId}") public Station station(@PathVariable String stationId){return service.station(stationId);}
 @GetMapping("/stations/{stationId}/measurements/latest") public Measurement latest(@PathVariable String stationId){return service.latest(stationId);}
 @GetMapping("/stations/{stationId}/measurements") public List<Measurement> measurements(@PathVariable String stationId,@RequestParam(required=false) Instant from,@RequestParam(required=false) Instant to,@RequestParam(required=false) Integer limit,@RequestParam(required=false) WarningLevel warningLevel){return service.history(stationId,from,to,limit,warningLevel);}
 @GetMapping("/stations/{stationId}/statistics") public Statistics stats(@PathVariable String stationId,@RequestParam(required=false) Instant from,@RequestParam(required=false) Instant to){return service.statistics(stationId,from,to);}
 @GetMapping("/alerts") public List<Alert> alerts(@RequestParam(required=false) WarningLevel warningLevel,@RequestParam(required=false) String river){return service.alerts(warningLevel,river);}
 @PostMapping(value="/stations",consumes={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}) public ResponseEntity<Station> create(@RequestBody Station station){Station s=service.create(station);return ResponseEntity.created(java.net.URI.create("/api/v1/stations/"+s.id())).body(s);}
 @PatchMapping(value="/stations/{stationId}",consumes=MediaType.APPLICATION_JSON_VALUE) public Station update(@PathVariable String stationId,@RequestBody Map<String,Object> changes){return service.update(stationId,changes);}
 @DeleteMapping("/stations/{stationId}") public ResponseEntity<Void> delete(@PathVariable String stationId){service.delete(stationId);return ResponseEntity.noContent().build();}
}