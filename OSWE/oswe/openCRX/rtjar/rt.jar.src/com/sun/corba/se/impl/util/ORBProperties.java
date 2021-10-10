/*    */ package com.sun.corba.se.impl.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.PrintWriter;
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
/*    */ public class ORBProperties
/*    */ {
/*    */   public static final String ORB_CLASS = "org.omg.CORBA.ORBClass=com.sun.corba.se.impl.orb.ORBImpl";
/*    */   public static final String ORB_SINGLETON_CLASS = "org.omg.CORBA.ORBSingletonClass=com.sun.corba.se.impl.orb.ORBSingleton";
/*    */   
/*    */   public static void main(String[] paramArrayOfString) {
/*    */     try {
/* 49 */       String str = System.getProperty("java.home");
/* 50 */       File file = new File(str + File.separator + "lib" + File.separator + "orb.properties");
/*    */ 
/*    */ 
/*    */       
/* 54 */       if (file.exists()) {
/*    */         return;
/*    */       }
/*    */       
/* 58 */       FileOutputStream fileOutputStream = new FileOutputStream(file);
/* 59 */       PrintWriter printWriter = new PrintWriter(fileOutputStream);
/*    */       
/*    */       try {
/* 62 */         printWriter.println("org.omg.CORBA.ORBClass=com.sun.corba.se.impl.orb.ORBImpl");
/* 63 */         printWriter.println("org.omg.CORBA.ORBSingletonClass=com.sun.corba.se.impl.orb.ORBSingleton");
/*    */       } finally {
/* 65 */         printWriter.close();
/* 66 */         fileOutputStream.close();
/*    */       }
/*    */     
/* 69 */     } catch (Exception exception) {}
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/util/ORBProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */