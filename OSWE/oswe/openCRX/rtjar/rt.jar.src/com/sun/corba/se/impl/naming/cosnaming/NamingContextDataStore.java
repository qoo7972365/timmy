package com.sun.corba.se.impl.naming.cosnaming;

import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.BindingTypeHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.PortableServer.POA;

public interface NamingContextDataStore {
  void Bind(NameComponent paramNameComponent, Object paramObject, BindingType paramBindingType) throws SystemException;
  
  Object Resolve(NameComponent paramNameComponent, BindingTypeHolder paramBindingTypeHolder) throws SystemException;
  
  Object Unbind(NameComponent paramNameComponent) throws SystemException;
  
  void List(int paramInt, BindingListHolder paramBindingListHolder, BindingIteratorHolder paramBindingIteratorHolder) throws SystemException;
  
  NamingContext NewContext() throws SystemException;
  
  void Destroy() throws SystemException;
  
  boolean IsEmpty();
  
  POA getNSPOA();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/cosnaming/NamingContextDataStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */