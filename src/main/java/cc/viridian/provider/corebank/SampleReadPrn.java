package cc.viridian.provider.corebank;

import cc.viridian.provider.model.Statement;
import cc.viridian.provider.model.StatementDetail;
import cc.viridian.provider.model.StatementHeader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SampleReadPrn {
    public static void main(String[] args) throws Exception {
        String filePath ="/home/isvar/Documents/Fix-dummy-bank/old/adaptercorebankprn/src/test/resources/sample.prn";
            parseContent(filePath,3);
    }

    public static Integer parseContent(String archivo, Integer startHeader) throws FileNotFoundException, IOException {
        String line;
        String[] splitLine;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        Integer currentLine = 0;
        Statement statement = new Statement();
        List<StatementDetail> details = new ArrayList<StatementDetail>();
        Integer i=0;
        Boolean readyDetails=false;
        while((line = b.readLine())!=null) {

            currentLine++;
            System.out.println(line);
            splitLine = line.split(" ");
            //splitLine = line.split(",");
            //System.out.println("SPLIT: "+splitLine);
            System.out.println("SPLIT: "+ Arrays.toString(splitLine));

            if(currentLine == startHeader) {
                StatementHeader statementHeader = new StatementHeader();
                statementHeader.setAccountCode(splitLine[0]);
                statementHeader.setAccountType(splitLine[1]);
                statementHeader.setAccountBranch(splitLine[2]);
                statement.setHeader(statementHeader);
                readyDetails=true;
                i=currentLine+1;
            }
            if(readyDetails && i <= currentLine) {
                StatementDetail statementDetail = new StatementDetail();
                statementDetail.setAccountCode(splitLine[0].toString());
                statementDetail.setAccountType(splitLine[1].toString());
                statementDetail.setBranchChannel(splitLine[2].toString());
                details.add(statementDetail);
            }
        }
        statement.setDetails(details);
        b.close();

        return currentLine;
    }
}
