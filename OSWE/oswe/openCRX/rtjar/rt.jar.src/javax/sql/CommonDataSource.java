package javax.sql;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public interface CommonDataSource {
  PrintWriter getLogWriter() throws SQLException;
  
  void setLogWriter(PrintWriter paramPrintWriter) throws SQLException;
  
  void setLoginTimeout(int paramInt) throws SQLException;
  
  int getLoginTimeout() throws SQLException;
  
  Logger getParentLogger() throws SQLFeatureNotSupportedException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/CommonDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */