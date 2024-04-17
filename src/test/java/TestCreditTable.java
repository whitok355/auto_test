import org.example.Client;
import org.example.Credit;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.AbstractQueue;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TestCreditTable extends AbstractTest{

    @Order(1)
    @Test
    public void checkGetMethod(){
        String sql_select = "SELECT * FROM credit";
        Query query = getOpenSession().createSQLQuery(sql_select).addEntity(Credit.class);

        assertEquals(1, query.list().size());
    }

    @Order(2)
    @Test
    public void checkInsertMethod(){
        Credit credit = new Credit();
        credit.setCredit_id(6);
        credit.setBalance("1000");
        credit.setOpen_date("2024-04-02 00:00:00");
        credit.setClose_date("2025-04-02 00:00:00");
        credit.setSumm(123123);
        credit.setNumber("412");
        credit.setStatus("active");
        credit.setEmployee_id(2);
        credit.setClient_id(1);

        Session session = getOpenSession();
        session.beginTransaction();

        session.persist(credit);
        session.getTransaction().commit();

        Optional<Credit> querySelect  = getOpenSession().createSQLQuery("SELECT * FROM credit WHERE credit_id=4").uniqueResultOptional();

        assumeTrue(querySelect.isPresent());

    }

    @Order(3)
    @Test
    public void checkDeleteMethod(){

        Session session = getOpenSession();

        Query query = getOpenSession().createSQLQuery("SELECT * FROM credit WHERE credit_id=0").addEntity(Credit.class);
        Optional<Credit> optionalQuery = query.uniqueResultOptional();
        assumeTrue(optionalQuery.isPresent());

        session.beginTransaction();
        session.delete(optionalQuery.get());
        session.getTransaction().commit();

        Optional query2 = getOpenSession().createSQLQuery("SELECT * FROM credit WHERE credit_id=0").uniqueResultOptional();
        assumeFalse(query2.isPresent());
    }

    @Order(4)
    @Test
    public void checkUpdateMethod(){
        Session session = getOpenSession();
        Optional<Credit> credit1 = session.createSQLQuery("SELECT * FROM credit WHERE credit_id=2").addEntity(Credit.class).uniqueResultOptional();
        assumeTrue(credit1.isPresent());



        session.beginTransaction();
        Query query = session.createSQLQuery("UPDATE credit set balance=123123123 WHERE credit_id=2");
        query.executeUpdate();
        session.getTransaction().commit();

        Credit findCredit = (Credit) session.createSQLQuery("SELECT * FROM credit WHERE credit_id=2").addEntity(Credit.class).uniqueResult();

        assertEquals("123123123", findCredit.getBalance());

    }
}
