import org.example.Client;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Optional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TestClientTable extends AbstractTest{
    @Order(1)
    @Test
    public void checkQuantityElements(){
        String sql_command = "SELECT * FROM client";
        int quantity = 0;
        try{
            Statement stm = getConnection().createStatement();
            ResultSet res = stm.executeQuery(sql_command);
            while(res.next()){
                quantity++;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        Query query = getOpenSession().createSQLQuery(sql_command).addEntity(Client.class);

        assertEquals(3, quantity);
        assertEquals(3, query.list().size());
    }
    @Order(2)
    @ParameterizedTest
    @CsvSource({"1, Иван", "2, Петр", "3, Сидр"})
    public void checkGetMethod(int id, String firstName) throws SQLException{
        String sql_command = "SELECT client_id,first_name FROM client WHERE client_id="+id;
        Statement stm = getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql_command);

        assertEquals(firstName, res.getString("first_name"));

    }
    @Order(3)
    @Test
    public void checkInsertMethod(){
        Client client = new Client();
        client.setClientId((short) 4);
        client.setFirstName("Test");
        client.setLastName("Test");
        client.setPhoneNumber("Test");
        client.setDistrict("Test");
        client.setStreet("Test");
        client.setHouse("Test");
        client.setApartment("Test");

        Session session = getOpenSession();
        session.beginTransaction();

        session.persist(client);
        session.getTransaction().commit();

        Query query = session.createSQLQuery("SELECT * FROM client WHERE client_id=" +4).addEntity(Client.class);

        Client cl = (Client) query.uniqueResult();
        assertEquals(4, cl.getClientId());
        assertEquals("Test", cl.getFirstName());
        assertEquals("Test", cl.getLastName());
        assertEquals("Test", cl.getPhoneNumber());
        assertEquals("Test", cl.getDistrict());
        assertEquals("Test", cl.getStreet());
        assertEquals("Test", cl.getHouse());
        assertEquals("Test", cl.getApartment());
    }
    @Order(4)
    @Test
    public void checkUpdate(){
        Session session = getOpenSession();
        String sql_select = "SELECT * FROM client WHERE client_id="+ 3;
        String sql_update = "UPDATE client SET first_name='123' WHERE client_id=2";

        Optional<Client> querySelect = session.createSQLQuery(sql_select).addEntity(Client.class).uniqueResultOptional();
        assumeTrue(querySelect.isPresent());

        session.beginTransaction();
        Query queryUpdate = session.createSQLQuery(sql_update);
        queryUpdate.executeUpdate();
        session.getTransaction().commit();

        querySelect = session.createSQLQuery(sql_select).addEntity(Client.class).uniqueResultOptional();
        assumeTrue(querySelect.isPresent());
    }
    @Order(5)
    @Test
    public void checkDeleteMethod(){
        String sql_command = "SELECT * FROM client WHERE client_id=" + 4;

        Query query = getOpenSession().createSQLQuery(sql_command).addEntity(Client.class);

        Optional<Client> cl = query.uniqueResultOptional();

        assumeTrue(cl.isPresent());

        Session session = getOpenSession();
        session.beginTransaction();
        session.delete(cl.get());

        session.getTransaction().commit();

        Query query1 = getOpenSession().createSQLQuery(sql_command).addEntity(Client.class);

        Optional<Client> cl1 = query1.uniqueResultOptional();
        assertFalse(cl1.isPresent());
    }
}
