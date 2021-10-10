package javax.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface PooledConnection {
  Connection getConnection() throws SQLException;
  
  void close() throws SQLException;
  
  void addConnectionEventListener(ConnectionEventListener paramConnectionEventListener);
  
  void removeConnectionEventListener(ConnectionEventListener paramConnectionEventListener);
  
  void addStatementEventListener(StatementEventListener paramStatementEventListener);
  
  void removeStatementEventListener(StatementEventListener paramStatementEventListener);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/PooledConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */