package util;
import java.sql.*;

public class Conexion {
	
	Connection conex = null;

	public Conexion() {
		
		
	}
	
	public Connection dameConexion() {
		
			try {
				conex=DriverManager.getConnection("jdbc:mysql://localhost:3306/vuelos?serverTimezone=UTC", "root", "");
				System.out.println("Conexion correcta");
			}catch(Exception e){
				System.out.println("error de conexion");
				e.printStackTrace();
			}
			
			return conex;
	}
	
	public static void closeConnection(Connection connArg) {
		try {
			if (connArg != null) {
				connArg.close();
				connArg = null;
			}
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
	}


}