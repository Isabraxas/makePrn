package cc.viridian.provider.corebank;

import cc.viridian.provider.model.Statement;
import cc.viridian.provider.model.StatementDetail;
import cc.viridian.provider.model.StatementHeader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class DummyStatement extends Statement {

    public static Statement createRandom (String account, String type, String currency, LocalDate dateFrom, LocalDate dateTo) {
        Statement statement = new Statement();
        StatementHeader header = new StatementHeader();
        currency = currency.toUpperCase(); //todo: validate currency
        Random random = new Random();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterSQL = DateTimeFormatter.ofPattern("yyyyMMdd");

        ArrayList<StatementDetail> details = new ArrayList<>();
        BigDecimal balanceInitial = new BigDecimal(random.nextInt(100));
        BigDecimal balance = balanceInitial;

        for (int i=0; i<random.nextInt(10); i++ ) {
            BigDecimal amount = new BigDecimal(random.nextInt(100));
            String debitCredit;
            String annotation;

            if (balance.compareTo(amount) < 0) {
                debitCredit = "CREDIT";
                annotation = "Credit of " + amount.toString() + " " + currency;
                balance = balance.add(amount);
            } else {
                balance = balance.subtract(amount);
                debitCredit = "DEBIT";
                annotation = "Debit of " + amount.toString() + " " + currency;
            }

            StatementDetail detail  = new StatementDetail();
            detail.setAccountCode(account);
            detail.setAccountCurrency(currency.toUpperCase());
            detail.setAccountType(type.toUpperCase());
            detail.setAmount(amount);
            detail.setDebitCredit(debitCredit);
            detail.setAnnotation(annotation);
            detail.setBalance(balance);
            detail.setBranchChannel("web");
            detail.setDate(dateFrom.format(formatterSQL));
            detail.setLocalDateTime(dateFrom.atTime(random.nextInt(18), random.nextInt(59)));
            detail.setReferenceNumber("" + random.nextLong());
            detail.setSecondaryInfo("info: " + annotation);
            detail.setTransactionCode("" + random.nextLong());
            detail.setTransactionDesc("description: " + annotation);
            detail.setTrnId("" + random.nextLong());
            details.add(detail);
        }
        statement.setDetails(details);

        header.setAccountCode(account);
        header.setAccountType(type.toUpperCase());
        header.setAccountCurrency(currency.toUpperCase());
        header.setDateFrom(dateFrom);
        header.setDateTo(dateTo);
        header.setAccountAddress("Av. Mariscal Santa Cruz #5432");
        header.setAccountBranch("La Paz");
        header.setAccountName("John Doe");
        header.setBalanceInitial(balanceInitial);
        header.setBalanceEnd(balance);
        header.setCustomerCode(account.substring(0,6) + "00000");
        header.setMessage("dummy message");
        header.setStatementTitle("Statement from " + dateFrom.format(formatter) + " to " + dateTo.format(formatter));
        statement.setHeader(header);

        return statement;
    }
}
