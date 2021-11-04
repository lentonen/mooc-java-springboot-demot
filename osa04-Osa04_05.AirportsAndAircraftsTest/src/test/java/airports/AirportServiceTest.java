package airports;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AirportServiceTest {

    @Autowired
    AirportService airportService;
    
    @Autowired
    AirportRepository airportRepository ;
    
    @Test
    public void testCreate() {
        airportService.create("identifier", "name");
        
        List<Airport> airports = airportRepository.findAll();
        String test = "";
        
        for (Airport airport: airports){
            if (airport.getIdentifier().equals("identifier") && airport.getName().equals("name")){
                test = "identifier";
            }
        }
        assertEquals(test,"identifier");
    }
    
    @Test
    public void testList() {
        airportService.create("HEL", "Helsinki");
        airportService.create("STO", "Stockholm");
        airportService.create("ZUR", "Zurich");
        List<Airport> list = airportService.list();
        
        assertEquals("HEL",list.get(0).getIdentifier());
        assertEquals("STO",list.get(1).getIdentifier());
        assertEquals("ZUR",list.get(2).getIdentifier());
    }
    
    
    @Test
    public void testCreateDuplicate() {
        airportService.create("TES", "Testi");    
        long countFirst = airportRepository.count();
        airportService.create("TES", "Testi");
        long countSecond = airportRepository.count();
        assertEquals(countFirst, countSecond);
    }
}

