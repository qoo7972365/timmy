/*    */ package com.sun.corba.se.impl.naming.pcosnaming;
/*    */ 
/*    */ import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
/*    */ import com.sun.corba.se.spi.activation.InitialNameService;
/*    */ import com.sun.corba.se.spi.activation.InitialNameServiceHelper;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import java.io.File;
/*    */ import java.util.Properties;
/*    */ import org.omg.CORBA.ORB;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NameServer
/*    */ {
/*    */   private ORB orb;
/*    */   private File dbDir;
/*    */   private static final String dbName = "names.db";
/*    */   
/*    */   public static void main(String[] paramArrayOfString) {
/* 57 */     NameServer nameServer = new NameServer(paramArrayOfString);
/* 58 */     nameServer.run();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected NameServer(String[] paramArrayOfString) {
/* 64 */     Properties properties = System.getProperties();
/* 65 */     properties.put("com.sun.CORBA.POA.ORBServerId", "1000");
/* 66 */     properties.put("org.omg.CORBA.ORBClass", "com.sun.corba.se.impl.orb.ORBImpl");
/*    */     
/* 68 */     this.orb = (ORB)ORB.init(paramArrayOfString, properties);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 73 */     String str = properties.getProperty("com.sun.CORBA.activation.DbDir") + properties.getProperty("file.separator") + "names.db" + properties.getProperty("file.separator");
/*    */     
/* 75 */     this.dbDir = new File(str);
/* 76 */     if (!this.dbDir.exists()) this.dbDir.mkdir();
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void run() {
/*    */     try {
/* 84 */       NameService nameService = new NameService(this.orb, this.dbDir);
/*    */ 
/*    */       
/* 87 */       NamingContext namingContext = nameService.initialNamingContext();
/* 88 */       InitialNameService initialNameService = InitialNameServiceHelper.narrow(this.orb
/* 89 */           .resolve_initial_references("InitialNameService"));
/*    */       
/* 91 */       initialNameService.bind("NameService", (Object)namingContext, true);
/* 92 */       System.out.println(CorbaResourceUtil.getText("pnameserv.success"));
/*    */ 
/*    */       
/* 95 */       this.orb.run();
/*    */     }
/* 97 */     catch (Exception exception) {
/*    */       
/* 99 */       exception.printStackTrace(System.err);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/pcosnaming/NameServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */