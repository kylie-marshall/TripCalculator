import Models.Stop;
import org.junit.Assert;
import org.junit.Test;

public class StopComparisonTests {
    @Test
    public void stops_are_equal()
    {
        Stop stop1 = new Stop("Stop1");
        Stop stop1Again = new Stop("Stop1");

        Assert.assertTrue(stop1.equals(stop1Again));
        Assert.assertTrue(stop1Again.equals(stop1));
        Assert.assertTrue(stop1.equals(stop1));
        Assert.assertTrue(stop1.equals("Stop1"));
    }

    @Test
    public void stops_are_not_equal()
    {
        Stop stop1 = new Stop("Stop1");
        Stop stop1Again = new Stop("Stop2");

        Assert.assertFalse(stop1.equals(stop1Again));
        Assert.assertFalse(stop1Again.equals(stop1));
        Assert.assertFalse(stop1.equals("Stop2"));
    }
}
