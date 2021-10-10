package javax.sql.rowset;

import java.sql.SQLException;

public interface RowSetFactory {
  CachedRowSet createCachedRowSet() throws SQLException;
  
  FilteredRowSet createFilteredRowSet() throws SQLException;
  
  JdbcRowSet createJdbcRowSet() throws SQLException;
  
  JoinRowSet createJoinRowSet() throws SQLException;
  
  WebRowSet createWebRowSet() throws SQLException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/RowSetFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */