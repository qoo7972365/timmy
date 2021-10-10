package javax.sql.rowset;

import java.sql.SQLException;
import java.sql.Savepoint;
import javax.sql.RowSet;

public interface JdbcRowSet extends RowSet, Joinable {
  boolean getShowDeleted() throws SQLException;
  
  void setShowDeleted(boolean paramBoolean) throws SQLException;
  
  RowSetWarning getRowSetWarnings() throws SQLException;
  
  void commit() throws SQLException;
  
  boolean getAutoCommit() throws SQLException;
  
  void setAutoCommit(boolean paramBoolean) throws SQLException;
  
  void rollback() throws SQLException;
  
  void rollback(Savepoint paramSavepoint) throws SQLException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/JdbcRowSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */