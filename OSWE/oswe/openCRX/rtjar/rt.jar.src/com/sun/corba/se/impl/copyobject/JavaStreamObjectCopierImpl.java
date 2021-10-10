/*    */ package com.sun.corba.se.impl.copyobject;
/*    */ 
/*    */ import com.sun.corba.se.impl.util.Utility;
/*    */ import com.sun.corba.se.spi.copyobject.ObjectCopier;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import org.omg.CORBA.ORB;
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
/*    */ public class JavaStreamObjectCopierImpl
/*    */   implements ObjectCopier
/*    */ {
/*    */   private ORB orb;
/*    */   
/*    */   public JavaStreamObjectCopierImpl(ORB paramORB) {
/* 51 */     this.orb = paramORB;
/*    */   }
/*    */   
/*    */   public Object copy(Object paramObject) {
/* 55 */     if (paramObject instanceof java.rmi.Remote)
/*    */     {
/*    */       
/* 58 */       return Utility.autoConnect(paramObject, this.orb, true);
/*    */     }
/*    */     
/*    */     try {
/* 62 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10000);
/* 63 */       ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
/* 64 */       objectOutputStream.writeObject(paramObject);
/*    */       
/* 66 */       byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
/* 67 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/* 68 */       ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
/*    */       
/* 70 */       return objectInputStream.readObject();
/* 71 */     } catch (Exception exception) {
/* 72 */       System.out.println("Failed with exception:" + exception);
/* 73 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/copyobject/JavaStreamObjectCopierImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */