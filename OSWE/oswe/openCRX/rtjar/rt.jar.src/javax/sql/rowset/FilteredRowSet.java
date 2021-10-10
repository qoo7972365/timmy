package javax.sql.rowset;

import java.sql.SQLException;

public interface FilteredRowSet extends WebRowSet {
  void setFilter(Predicate paramPredicate) throws SQLException;
  
  Predicate getFilter();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/FilteredRowSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */