package javax.sql;

import java.sql.SQLException;
import javax.transaction.xa.XAResource;

public interface XAConnection extends PooledConnection {
  XAResource getXAResource() throws SQLException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/XAConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */