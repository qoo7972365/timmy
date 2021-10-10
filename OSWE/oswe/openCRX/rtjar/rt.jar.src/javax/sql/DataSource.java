package javax.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Wrapper;

public interface DataSource extends CommonDataSource, Wrapper {
  Connection getConnection() throws SQLException;
  
  Connection getConnection(String paramString1, String paramString2) throws SQLException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/DataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */