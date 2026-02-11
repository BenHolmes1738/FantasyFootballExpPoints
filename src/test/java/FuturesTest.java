import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.exppoints.fantasy.FuturePlayer;
import com.exppoints.fantasy.Futures;



public class FuturesTest {

    @Test
    public void testRetrieval() {
        List<String> input = new java.util.ArrayList<>();
        input.add("cam-ward");
        Futures futures = new Futures();
        List<FuturePlayer> out = futures.getOdds(input);
        for (FuturePlayer n:out) {
            System.out.println(n.getName() 
            + "\n" + n.getRushTds() + "\n" + n.getRecTds() 
            + "\n" + n.getRushYds() + "\n" + n.getRecYds() 
            + "\n" + n.getRec() + "\n" + n.getPassYds() 
            + "\n" + n.getPassTds() + "\n" + n.getInts());
        }

        futures.write(out);
        assertTrue(out != null && !out.isEmpty(), "Futures should return a non-empty list of FuturePlayers");
    }
}