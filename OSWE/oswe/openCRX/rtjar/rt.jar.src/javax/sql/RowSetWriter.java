package javax.sql;

import java.sql.SQLException;

public interface RowSetWriter {
  boolean writeData(RowSetInternal paramRowSetInternal) throws SQLException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/RowSetWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */