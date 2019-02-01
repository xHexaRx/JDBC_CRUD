package crudjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple CRUD class for JDBC learning purposes.
 * Using DriverManager to get connection to database.
 * Table columns: Programmer_id, Name, Location, Qualification.
 */
public class JDBC_CRUD {
	private Connection connection;
	private String tableName;
	
	/**
	 * Constructor creates connection and statement.
	 * @param adress Adress of database.
	 * @param login Login for database.
	 * @param password Password for database.
	 * @throws SQLException
	 */
	public JDBC_CRUD(String adress, String login, String password, String tableName) throws SQLException {
		connection=DriverManager.getConnection(adress,login,password);
		this.tableName=tableName;
	}
	
	/**
	 * Creates new programmer in database.
	 * @param name Programmer name.
	 * @param location Programmer location.
	 * @param qualification Programmer Qualification.
	 * @throws SQLException
	 */
	public void createProgrammer(int id, String name, String location, String qualification) throws SQLException {
		String query = "INSERT INTO "+tableName+" (Programmer_id, Name, Location, Qualification) VALUES (?,?,?,?);";
		PreparedStatement pstmt = connection.prepareStatement(query);
		pstmt.setInt(1, id);
		pstmt.setString(2, name);
		pstmt.setString(3, location);
		pstmt.setString(4, qualification);
		pstmt.executeUpdate();
	}
	
	/**
	 * Delete programmer from database.
	 * @param name Programmer name.
	 * @throws SQLException
	 */
	public void deleteProgrammer(int id) throws SQLException {
		String query = "DELETE FROM "+tableName+" WHERE Programmer_id=?;";
		PreparedStatement pstmt = connection.prepareStatement(query);
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
	}
	
	/**
	 * Show table and return ResultSet.
	 * @return Returns table contents in ResultSet.
	 * @throws SQLException
	 */
	public ResultSet readTable() throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM "+tableName);
		int count=rs.getMetaData().getColumnCount();
		while(rs.next()) {
			for(int i=0; i<count; i++) {
				System.out.print(rs.getString(i+1)+" ");
			}
			System.out.print("\n");
		}
		return rs;
	}
	
	/**
	 * Updates record based on ID.
	 * @param id Programmer_id.
	 * @param name Programmer Name.
	 * @param location Programmer location
	 * @param qualification Programmer qualification
	 * @throws SQLException
	 */
	public void updateProgrammer(int id, String name, String location, String qualification) throws SQLException {
		String query = "UPDATE "+tableName+" SET Name=?, Location=?, Qualification=? WHERE Programmer_id=?;";
		PreparedStatement pstmt = connection.prepareStatement(query);
		pstmt.setString(1, name);
		pstmt.setString(2, location);
		pstmt.setString(3, qualification);
		pstmt.setInt(4, id);
		pstmt.executeUpdate();
	}
	
	/**
	 * Truncates table.
	 * @throws SQLException
	 */
	public void clear() throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("TRUNCATE TABLE "+tableName);
	}
	
}
