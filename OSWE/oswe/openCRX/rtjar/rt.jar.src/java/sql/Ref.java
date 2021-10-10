package java.sql;

import java.util.Map;

public interface Ref {
  String getBaseTypeName() throws SQLException;
  
  Object getObject(Map<String, Class<?>> paramMap) throws SQLException;
  
  Object getObject() throws SQLException;
  
  void setObject(Object paramObject) throws SQLException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/Ref.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */