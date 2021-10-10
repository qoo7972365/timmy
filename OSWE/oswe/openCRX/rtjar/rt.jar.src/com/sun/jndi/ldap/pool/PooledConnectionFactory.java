package com.sun.jndi.ldap.pool;

import javax.naming.NamingException;

public interface PooledConnectionFactory {
  PooledConnection createPooledConnection(PoolCallback paramPoolCallback) throws NamingException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/pool/PooledConnectionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */