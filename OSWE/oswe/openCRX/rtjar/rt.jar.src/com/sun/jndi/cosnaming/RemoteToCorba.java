/*    */ package com.sun.jndi.cosnaming;
/*    */ 
/*    */ import com.sun.jndi.toolkit.corba.CorbaUtils;
/*    */ import java.rmi.Remote;
/*    */ import java.util.Hashtable;
/*    */ import javax.naming.ConfigurationException;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.Name;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.spi.StateFactory;
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
/*    */ public class RemoteToCorba
/*    */   implements StateFactory
/*    */ {
/*    */   public Object getStateToBind(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 67 */     if (paramObject instanceof org.omg.CORBA.Object)
/*    */     {
/* 69 */       return null;
/*    */     }
/*    */     
/* 72 */     if (paramObject instanceof Remote) {
/*    */       
/*    */       try {
/*    */ 
/*    */ 
/*    */         
/* 78 */         return 
/* 79 */           CorbaUtils.remoteToCorba((Remote)paramObject, ((CNCtx)paramContext)._orb);
/* 80 */       } catch (ClassNotFoundException classNotFoundException) {
/*    */         
/* 82 */         throw new ConfigurationException("javax.rmi packages not available");
/*    */       } 
/*    */     }
/*    */     
/* 86 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/cosnaming/RemoteToCorba.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */