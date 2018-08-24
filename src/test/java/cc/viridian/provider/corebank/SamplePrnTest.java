package cc.viridian.provider.corebank;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.logging.Logger;

public class SamplePrnTest {
    URL sampleFileUrl;

    private final static Logger log = Logger.getLogger(SamplePrnTest.class.getName());

    private final String ADAPTER_NAME = "SAMPLEPRN";

    @Before
    public void setUp() throws Exception {
        log.info("setup test");

        sampleFileUrl = this.getClass().getResource("/sample.pdf");
        log.info(sampleFileUrl.getFile().toString());
    }

    @Test
    public void checkStatementSenderProvider() {

    }
}
