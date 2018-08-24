package cc.viridian.provider.corebank;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class SamplePrn {

    private static boolean jobRunning = true;

    public static void main(String[] args) throws Exception {


        // Open the image file
        InputStream is = new BufferedInputStream(new FileInputStream("/home/isvar/Documents/Fix-dummy-bank/old/adaptercorebankprn/src/test/resources/testPdf.pdf"));
        //InputStream is = new BufferedInputStream(new FileInputStream("/home/isvar/Documents/Fix-dummy-bank/old/adaptercorebankprn/src/test/resources/testCsv.csv"));
        //InputStream is = new BufferedInputStream(new FileInputStream("/home/isvar/Documents/Fix-dummy-bank/old/adaptercorebankprn/src/test/resources/testJson.json"));

        // create a PDF doc flavor
        //DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

        // Locate the default print service for this environment.
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();

        // Create and return a PrintJob capable of handling data from
        // any of the supported document flavors.
        DocPrintJob printJob = service.createPrintJob();


        // register a listener to get notified when the job is complete
        printJob.addPrintJobListener(new JobCompleteMonitor());


        // Construct a SimpleDoc with the specified
        // print data, doc flavor and doc attribute set.
        Doc doc = new SimpleDoc(is, flavor, null);

        // set up the attributes
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(new Destination(new java.net.URI("file:///home/isvar/Documents/Fix-dummy-bank/old/adaptercorebankprn/src/test/resources/myfile.ps")));


        // Print a document with the specified job attributes.
        printJob.print(doc, attributes);
        //printJob.print(doc, new HashPrintRequestAttributeSet());

        while (jobRunning) {
            Thread.sleep(1000);
        }

        System.out.println("Exiting app");
        is.close();


    }



    private static class JobCompleteMonitor extends PrintJobAdapter {

        @Override
        public void printJobCompleted(PrintJobEvent jobEvent) {
            System.out.println("Job completed");
            jobRunning = false;
        }

    }
}
