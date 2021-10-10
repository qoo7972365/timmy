/*    */ package com.sun.corba.se.impl.orb;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.DataCollector;
/*    */ import java.applet.Applet;
/*    */ import java.net.URL;
/*    */ import java.util.Properties;
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
/*    */ public abstract class DataCollectorFactory
/*    */ {
/*    */   public static DataCollector create(Applet paramApplet, Properties paramProperties, String paramString) {
/* 40 */     String str = paramString;
/*    */     
/* 42 */     if (paramApplet != null) {
/* 43 */       URL uRL = paramApplet.getCodeBase();
/*    */       
/* 45 */       if (uRL != null) {
/* 46 */         str = uRL.getHost();
/*    */       }
/*    */     } 
/* 49 */     return new AppletDataCollector(paramApplet, paramProperties, paramString, str);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataCollector create(String[] paramArrayOfString, Properties paramProperties, String paramString) {
/* 56 */     return new NormalDataCollector(paramArrayOfString, paramProperties, paramString, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataCollector create(Properties paramProperties, String paramString) {
/* 63 */     return new PropertyOnlyDataCollector(paramProperties, paramString, paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/DataCollectorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */