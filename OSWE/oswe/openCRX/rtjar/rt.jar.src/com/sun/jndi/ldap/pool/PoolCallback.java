package com.sun.jndi.ldap.pool;

public interface PoolCallback {
  boolean releasePooledConnection(PooledConnection paramPooledConnection);
  
  boolean removePooledConnection(PooledConnection paramPooledConnection);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/pool/PoolCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */