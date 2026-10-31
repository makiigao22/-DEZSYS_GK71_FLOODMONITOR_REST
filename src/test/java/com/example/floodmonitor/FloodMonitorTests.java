package com.example.floodmonitor;
import com.example.floodmonitor.model.*;
import com.example.floodmonitor.service.FloodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest(properties="simulation.interval=99999999") @AutoConfigureMockMvc class FloodMonitorTests {
 @Autowired MockMvc mvc; @Autowired FloodService service;
 @Test void allJson() throws Exception {mvc.perform(get("/api/v1/stations").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].id").exists());}
 @Test void allXml() throws Exception {mvc.perform(get("/api/v1/stations").accept(MediaType.APPLICATION_XML)).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML));}
 @Test void existing() throws Exception {mvc.perform(get("/api/v1/stations/ST-001")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value("ST-001"));}
 @Test void unknown() throws Exception {mvc.perform(get("/api/v1/stations/ST-999")).andExpect(status().isNotFound());}
 @Test void latest() throws Exception {mvc.perform(get("/api/v1/stations/ST-001/measurements/latest")).andExpect(status().isOk()).andExpect(jsonPath("$.waterLevel").exists());}
 @Test void timeFilter() throws Exception {mvc.perform(get("/api/v1/stations/ST-001/measurements?from=2020-01-01T00:00:00Z&to=2030-01-01T00:00:00Z")).andExpect(status().isOk());}
 @Test void invalidTime() throws Exception {mvc.perform(get("/api/v1/stations/ST-001/measurements?from=2030-01-01T00:00:00Z&to=2020-01-01T00:00:00Z")).andExpect(status().isBadRequest());}
 @Test void levels(){Station s=service.station("ST-001");assertEquals(WarningLevel.NORMAL,service.level(s,200));assertEquals(WarningLevel.WARNING,service.level(s,310));assertEquals(WarningLevel.CRITICAL,service.level(s,400));}
 @Test
 void unsupported() throws Exception {
  mvc.perform(get("/api/v1/stations")
                  .accept(MediaType.parseMediaType("text/csv")))
          .andExpect(status().isNotAcceptable());
 }
 @Test void maxLimit() throws Exception {mvc.perform(get("/api/v1/stations/ST-001/measurements?limit=1000")).andExpect(status().isOk());mvc.perform(get("/api/v1/stations/ST-001/measurements?limit=1001")).andExpect(status().isBadRequest());}
}
