package com.sun.corba.se.spi.activation;

import com.sun.corba.se.spi.activation.InitialNameServicePackage.NameAlreadyBound;
import org.omg.CORBA.Object;

public interface InitialNameServiceOperations {
  void bind(String paramString, Object paramObject, boolean paramBoolean) throws NameAlreadyBound;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/InitialNameServiceOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */