package java.sql;

public interface SQLData {
  String getSQLTypeName() throws SQLException;
  
  void readSQL(SQLInput paramSQLInput, String paramString) throws SQLException;
  
  void writeSQL(SQLOutput paramSQLOutput) throws SQLException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/SQLData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */