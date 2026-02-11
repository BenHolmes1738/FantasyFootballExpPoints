import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.exppoints.fantasy.Scraper;



public class ScraperTest {

    @Test
    public void testScrape() {
        Scraper scraper = new Scraper();
        List<String> out = scraper.scrape("ashton-jeanty");
        for (String n:out) {
            System.out.println(n);
        }
        //System.out.println("Scraper output: " + out);
        assertTrue(out != null && !out.isEmpty(), "Scraper should return a non-empty list of WebElements");
    }
}