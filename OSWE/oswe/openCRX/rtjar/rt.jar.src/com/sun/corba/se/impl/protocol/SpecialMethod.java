/*    */ package com.sun.corba.se.impl.protocol;
/*    */ 
/*    */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*    */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
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
/*    */ public abstract class SpecialMethod
/*    */ {
/*    */   public abstract boolean isNonExistentMethod();
/*    */   
/*    */   public abstract String getName();
/*    */   
/*    */   public abstract CorbaMessageMediator invoke(Object paramObject, CorbaMessageMediator paramCorbaMessageMediator, byte[] paramArrayOfbyte, ObjectAdapter paramObjectAdapter);
/*    */   
/*    */   public static final SpecialMethod getSpecialMethod(String paramString) {
/* 57 */     for (byte b = 0; b < methods.length; b++) {
/* 58 */       if (methods[b].getName().equals(paramString))
/* 59 */         return methods[b]; 
/* 60 */     }  return null;
/*    */   }
/*    */   
/* 63 */   static SpecialMethod[] methods = new SpecialMethod[] { new IsA(), new GetInterface(), new NonExistent(), new NotExistent() };
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/SpecialMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */