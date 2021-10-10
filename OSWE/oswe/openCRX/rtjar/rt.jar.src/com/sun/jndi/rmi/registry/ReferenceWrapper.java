/*    */ package com.sun.jndi.rmi.registry;
/*    */ 
/*    */ import java.rmi.RemoteException;
/*    */ import java.rmi.server.UnicastRemoteObject;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.Reference;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReferenceWrapper
/*    */   extends UnicastRemoteObject
/*    */   implements RemoteReference
/*    */ {
/*    */   protected Reference wrappee;
/*    */   private static final long serialVersionUID = 6078186197417641456L;
/*    */   
/*    */   public ReferenceWrapper(Reference paramReference) throws NamingException, RemoteException {
/* 53 */     this.wrappee = paramReference;
/*    */   }
/*    */   
/*    */   public Reference getReference() throws RemoteException {
/* 57 */     return this.wrappee;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/rmi/registry/ReferenceWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */