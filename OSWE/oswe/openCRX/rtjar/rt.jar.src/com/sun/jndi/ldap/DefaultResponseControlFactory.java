/*    */ package com.sun.jndi.ldap;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.ldap.Control;
/*    */ import javax.naming.ldap.ControlFactory;
/*    */ import javax.naming.ldap.PagedResultsResponseControl;
/*    */ import javax.naming.ldap.SortResponseControl;
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
/*    */ public class DefaultResponseControlFactory
/*    */   extends ControlFactory
/*    */ {
/*    */   public Control getControlInstance(Control paramControl) throws NamingException {
/* 76 */     String str = paramControl.getID();
/*    */ 
/*    */     
/*    */     try {
/* 80 */       if (str.equals("1.2.840.113556.1.4.474")) {
/* 81 */         return new SortResponseControl(str, paramControl.isCritical(), paramControl
/* 82 */             .getEncodedValue());
/*    */       }
/* 84 */       if (str.equals("1.2.840.113556.1.4.319")) {
/* 85 */         return new PagedResultsResponseControl(str, paramControl.isCritical(), paramControl
/* 86 */             .getEncodedValue());
/*    */       }
/* 88 */       if (str.equals("2.16.840.1.113730.3.4.7")) {
/* 89 */         return new EntryChangeResponseControl(str, paramControl.isCritical(), paramControl
/* 90 */             .getEncodedValue());
/*    */       }
/*    */     }
/* 93 */     catch (IOException iOException) {
/* 94 */       NamingException namingException = new NamingException();
/* 95 */       namingException.setRootCause(iOException);
/* 96 */       throw namingException;
/*    */     } 
/* 98 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/DefaultResponseControlFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */