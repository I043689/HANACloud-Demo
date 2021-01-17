import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeployToHANA {
	private static final String HANA_USER = "HANA_CLOUD_DEMO_HDI_DB_1_24KLO6J9ZGL6MMGE6RAI33F63_DT";
	private static final String HANA_PASSWORD = "Bc7BQZK2gRGsXFVnsIX4EGUdTUAHG5f33qubNNZqjHct1j9omNMdQXtqjDvpsvFhZCDpM42KfVAUzMBX.x6y8nwu6PGXMOmHpQD0nlqL9ApCPPWpQSmf.Uu_hmQTmhoo";
	private static final String HANA_URL = "jdbc:sap://1bdebe0e-8369-49be-a96a-c7b32fb3312d.hana.trial-eu10.hanacloud.ondemand.com:443?encrypt=true&validateCertificate=true";

	private static final String CAL_CULATION_VIEW_NAME = "src/model/performanceAnalysisByJava";
	private static final String CONTAINER_NAME = "HANA_CLOUD_DEMO_HDI_DB_1";

	private static final String SQL_PRE_1 = "CREATE LOCAL TEMPORARY COLUMN TABLE #FILESFOLDERS_WRITE LIKE _SYS_DI.TT_FILESFOLDERS_CONTENT";
	private static final String SQL_PRE_2 = "CREATE LOCAL TEMPORARY COLUMN TABLE #FILESFOLDERS_PARAMETERS LIKE _SYS_DI.TT_FILESFOLDERS_PARAMETERS";
	private static final String SQL_PRE_3 = "CREATE LOCAL TEMPORARY COLUMN TABLE #FILESFOLDERS LIKE _SYS_DI.TT_FILESFOLDERS";
	private static final String SQL_PRE_4 = "CREATE LOCAL TEMPORARY COLUMN TABLE #FILESFOLDERS_MAKE LIKE _SYS_DI.TT_FILESFOLDERS";

	private static final String SQL_EXE_1 = "INSERT INTO #FILESFOLDERS_WRITE (PATH, CONTENT) VALUES ('"
			+ CAL_CULATION_VIEW_NAME + ".hdbcalculationview','" + "<xml>')";
	private static final String SQL_EXE_2 = "CALL " + CONTAINER_NAME
			+ "#DI.WRITE(#FILESFOLDERS_WRITE, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?)";
	private static final String SQL_EXE_3 = "INSERT INTO #FILESFOLDERS_MAKE (PATH) VALUES ('" + CAL_CULATION_VIEW_NAME
			+ ".hdbcalculationview')";
	private static final String SQL_EXE_4 = "CALL " + CONTAINER_NAME
			+ "#DI.MAKE(#FILESFOLDERS_MAKE, #FILESFOLDERS, #FILESFOLDERS_PARAMETERS, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?)";

	private static final String SQL_POST_1 = "DROP TABLE #FILESFOLDERS_WRITE";
	private static final String SQL_POST_2 = "DROP TABLE #FILESFOLDERS_PARAMETERS";
	private static final String SQL_POST_3 = "DROP TABLE #FILESFOLDERS";
	private static final String SQL_POST_4 = "DROP TABLE #FILESFOLDERS_MAKE";

	public static void main(String[] args) {

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(HANA_URL, HANA_USER, HANA_PASSWORD);
		} catch (SQLException e) {
			System.err.println("Connection Failed:");
			System.err.println(e);
		}
		if (connection != null) {
			try {
				System.out.println("Connection to HANA successful!");
				Statement stmt_pre_1 = connection.createStatement();
				stmt_pre_1.execute(SQL_PRE_1);
				System.out.println(SQL_PRE_1);
				
				Statement stmt_pre_2 = connection.createStatement();
				stmt_pre_2.execute(SQL_PRE_2);
				System.out.println(SQL_PRE_2);
				
				Statement stmt_pre_3 = connection.createStatement();
				stmt_pre_3.execute(SQL_PRE_3);
				System.out.println(SQL_PRE_3);
				
				Statement stmt_pre_4 = connection.createStatement();
				stmt_pre_4.execute(SQL_PRE_4);
				System.out.println(SQL_PRE_4);

				String xml = CreateCaculationView.createRoot().toXMLWithXSDValidation();
				String SQL_EXE_1_WITH_XML = SQL_EXE_1.replace("<xml>", xml);
				
				Statement stmt_exe_1 = connection.createStatement();
				stmt_exe_1.execute(SQL_EXE_1_WITH_XML);
				System.out.println(SQL_EXE_1_WITH_XML);
				
				Statement stmt_exe_2 = connection.createStatement();
				stmt_exe_2.execute(SQL_EXE_2);
				System.out.println(SQL_EXE_2);
				
				Statement stmt_exe_3 = connection.createStatement();
				stmt_exe_3.execute(SQL_EXE_3);
				System.out.println(SQL_EXE_3);
				
				Statement stmt_exe_4 = connection.createStatement();
				stmt_exe_4.execute(SQL_EXE_4);
				System.out.println(SQL_EXE_4);

				Statement stmt_post_1 = connection.createStatement();
				stmt_post_1.execute(SQL_POST_1);
				System.out.println(SQL_POST_1);
				
				Statement stmt_post_2 = connection.createStatement();
				stmt_post_2.execute(SQL_POST_2);
				System.out.println(SQL_POST_2);
				
				Statement stmt_post_3 = connection.createStatement();
				stmt_post_3.execute(SQL_POST_3);
				System.out.println(SQL_POST_3);
				
				Statement stmt_post_4 = connection.createStatement();
				stmt_post_4.execute(SQL_POST_4);
				System.out.println(SQL_POST_4);

				connection.close();
			} catch (SQLException e) {
				System.err.println(e);
				System.err.println("Execution failed!");
			}
		}
	}

}