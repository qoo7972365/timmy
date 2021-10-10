package com.sun.org.apache.xml.internal.security.keys.storage;

import java.security.cert.Certificate;
import java.util.Iterator;

public abstract class StorageResolverSpi {
  public abstract Iterator<Certificate> getIterator();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/storage/StorageResolverSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */