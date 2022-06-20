package apitests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class OwnerControllerTest {

	private static Connection connection;

	@BeforeAll
	public static void connect() throws SQLException {
		connection = DriverManager.getConnection(
			"jdbc:postgresql://localhost:5432/petclinic",
			"petclinic",
			"petclinic"
		);
	}

	@AfterAll
	public static void disconnect() throws SQLException {
		connection.close();
	}

	///owners

	@Test
	public void shouldReturnPetsWhenGet() {
		when()
			.get("/owners")
			.then()
			.statusCode(200);
	}

	@Test
	public void shouldReturnPetsWhenGetWithLastName() throws SQLException {
		PreparedStatement sql = connection.prepareStatement(
			"INSERT INTO owners(id, first_name, last_name, address, city, telephone) VALUES(?,?,?,?,?,?)"
		);
		sql.setInt(1, 555);
		sql.setString(2, "Testname");
		sql.setString(3, "Testlastname");
		sql.setString(4, "2698 Helloworld St.");
		sql.setString(5, "Garden");
		sql.setString(6, "6085539554");
		sql.executeUpdate();

		when()
			.get("/owners/Testlastname")
			.then()
			.statusCode(200);

		PreparedStatement sqlDelete = connection.prepareStatement(
			"DELETE FROM country WHERE id = 555"
		);
		sqlDelete.executeUpdate();
	}

	//showOwner

	@Test
	public void shouldReturnPetsWhenGetShowOwnerWithId() throws SQLException {
		PreparedStatement sql = connection.prepareStatement(
			"INSERT INTO owners(id, first_name, last_name, address, city, telephone) VALUES(?,?,?,?,?,?)"
		);
		sql.setInt(1, 666);
		sql.setString(2, "Testname");
		sql.setString(3, "Testlastname");
		sql.setString(4, "2698 Helloworld St.");
		sql.setString(5, "Garden");
		sql.setString(6, "7485539554");
		sql.executeUpdate();

		when()
			.get("/owners/666")
			.then()
			.statusCode(200);

		PreparedStatement sqlDelete = connection.prepareStatement(
			"DELETE FROM country WHERE id = 666"
		);
		sqlDelete.executeUpdate();
	}



	@Test
	public void shouldReturnPetsWhenGetShowOwnerWithoutId() {
		when()
			.get("/owners/")
			.then()
			.statusCode(500);
	}

	//processUpdateOwnerForm
	@Test
	public void shouldChangeDataWhenUpdate() throws SQLException {
		PreparedStatement sql = connection.prepareStatement(
			"INSERT INTO owners(id, first_name, last_name, address, city, telephone) VALUES(?,?,?,?,?,?)"
		);
		sql.setInt(1, 777);
		sql.setString(2, "Testname");
		sql.setString(3, "Testlastname");
		sql.setString(4, "2698 Helloworld St.");
		sql.setString(5, "Garden");
		sql.setString(6, "7485539554");
		sql.executeUpdate();

		given()
			.contentType("application/json")
			.body("{\n"
				+ "  \"address\": \"test\"\n"
				+ "}")
			.when()
			.patch("/api/countries/777")
			.then()
			.statusCode(200)
			.body("countryName", equalTo("test"));

		PreparedStatement sqlDelete = connection.prepareStatement(
			"DELETE FROM country WHERE id = 777"
		);
		sqlDelete.executeUpdate();
	}





}
