package java.sql;

import java.util.Map;

public interface Struct {
  String getSQLTypeName() throws SQLException;
  
  Object[] getAttributes() throws SQLException;
  
  Object[] getAttributes(Map<String, Class<?>> paramMap) throws SQLException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/Struct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */