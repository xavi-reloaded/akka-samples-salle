import ejercicio.SimpleSampleService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ejercicio.SimpleSampleService;


public class SimpleSampleServiceTest {

    SimpleSampleService sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new SimpleSampleService();
    }




}
