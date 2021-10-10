/*    */ package com.sun.corba.se.impl.activation;
/*    */ 
/*    */ import com.sun.corba.se.impl.naming.pcosnaming.NameService;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import java.io.File;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CosNaming.NamingContext;
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
/*    */ public class NameServiceStartThread
/*    */   extends Thread
/*    */ {
/*    */   private ORB orb;
/*    */   private File dbDir;
/*    */   
/*    */   public NameServiceStartThread(ORB paramORB, File paramFile) {
/* 45 */     this.orb = paramORB;
/* 46 */     this.dbDir = paramFile;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 53 */       NameService nameService = new NameService(this.orb, this.dbDir);
/* 54 */       NamingContext namingContext = nameService.initialNamingContext();
/* 55 */       this.orb.register_initial_reference("NameService", (Object)namingContext);
/*    */     }
/* 57 */     catch (Exception exception) {
/* 58 */       System.err.println("NameService did not start successfully");
/*    */       
/* 60 */       exception.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/NameServiceStartThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */