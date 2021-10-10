/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import com.sun.corba.se.impl.legacy.connection.SocketFactoryAcceptorImpl;
/*     */ import com.sun.corba.se.impl.naming.cosnaming.TransientNameService;
/*     */ import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
/*     */ import com.sun.corba.se.impl.transport.SocketOrChannelAcceptorImpl;
/*     */ import com.sun.corba.se.pept.transport.Acceptor;
/*     */ import com.sun.corba.se.spi.activation.Activator;
/*     */ import com.sun.corba.se.spi.activation.ActivatorHelper;
/*     */ import com.sun.corba.se.spi.activation.Locator;
/*     */ import com.sun.corba.se.spi.activation.LocatorHelper;
/*     */ import com.sun.corba.se.spi.activation.Repository;
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDef;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.File;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.COMM_FAILURE;
/*     */ import org.omg.CORBA.INTERNAL;
/*     */ import org.omg.CORBA.Object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ORBD
/*     */ {
/*     */   private int initSvcPort;
/*     */   protected File dbDir;
/*     */   private String dbDirName;
/*     */   protected Locator locator;
/*     */   protected Activator activator;
/*     */   protected RepositoryImpl repository;
/*     */   
/*     */   protected void initializeBootNaming(ORB paramORB) {
/*     */     SocketFactoryAcceptorImpl socketFactoryAcceptorImpl;
/*  71 */     this.initSvcPort = paramORB.getORBData().getORBInitialPort();
/*     */ 
/*     */ 
/*     */     
/*  75 */     if (paramORB.getORBData().getLegacySocketFactory() == null) {
/*  76 */       SocketOrChannelAcceptorImpl socketOrChannelAcceptorImpl = new SocketOrChannelAcceptorImpl(paramORB, this.initSvcPort, "BOOT_NAMING", "IIOP_CLEAR_TEXT");
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  83 */       socketFactoryAcceptorImpl = new SocketFactoryAcceptorImpl(paramORB, this.initSvcPort, "BOOT_NAMING", "IIOP_CLEAR_TEXT");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     paramORB.getCorbaTransportManager().registerAcceptor((Acceptor)socketFactoryAcceptorImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ORB createORB(String[] paramArrayOfString) {
/*  95 */     Properties properties = System.getProperties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     properties.put("com.sun.CORBA.POA.ORBServerId", "1000");
/* 102 */     properties.put("com.sun.CORBA.POA.ORBPersistentServerPort", properties
/* 103 */         .getProperty("com.sun.CORBA.activation.Port", 
/* 104 */           Integer.toString(1049)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     properties.put("org.omg.CORBA.ORBClass", "com.sun.corba.se.impl.orb.ORBImpl");
/*     */ 
/*     */     
/* 112 */     return (ORB)ORB.init(paramArrayOfString, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void run(String[] paramArrayOfString) {
/*     */     try {
/* 120 */       processArgs(paramArrayOfString);
/*     */       
/* 122 */       ORB oRB = createORB(paramArrayOfString);
/*     */       
/* 124 */       if (oRB.orbdDebugFlag) {
/* 125 */         System.out.println("ORBD begins initialization.");
/*     */       }
/* 127 */       boolean bool = createSystemDirs("orb.db");
/*     */       
/* 129 */       startActivationObjects(oRB);
/*     */       
/* 131 */       if (bool) {
/* 132 */         installOrbServers(getRepository(), getActivator());
/*     */       }
/* 134 */       if (oRB.orbdDebugFlag) {
/* 135 */         System.out.println("ORBD is ready.");
/* 136 */         System.out.println("ORBD serverid: " + 
/* 137 */             System.getProperty("com.sun.CORBA.POA.ORBServerId"));
/* 138 */         System.out.println("activation dbdir: " + 
/* 139 */             System.getProperty("com.sun.CORBA.activation.DbDir"));
/* 140 */         System.out.println("activation port: " + 
/* 141 */             System.getProperty("com.sun.CORBA.activation.Port"));
/*     */         
/* 143 */         String str1 = System.getProperty("com.sun.CORBA.activation.ServerPollingTime");
/*     */         
/* 145 */         if (str1 == null) {
/* 146 */           str1 = Integer.toString(1000);
/*     */         }
/*     */         
/* 149 */         System.out.println("activation Server Polling Time: " + str1 + " milli-seconds ");
/*     */ 
/*     */         
/* 152 */         String str2 = System.getProperty("com.sun.CORBA.activation.ServerStartupDelay");
/*     */         
/* 154 */         if (str2 == null) {
/* 155 */           str2 = Integer.toString(1000);
/*     */         }
/*     */         
/* 158 */         System.out.println("activation Server Startup Delay: " + str2 + " milli-seconds ");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 163 */       NameServiceStartThread nameServiceStartThread = new NameServiceStartThread(oRB, this.dbDir);
/*     */       
/* 165 */       nameServiceStartThread.start();
/*     */       
/* 167 */       oRB.run();
/* 168 */     } catch (COMM_FAILURE cOMM_FAILURE) {
/* 169 */       System.out.println(CorbaResourceUtil.getText("orbd.commfailure"));
/* 170 */       System.out.println(cOMM_FAILURE);
/* 171 */       cOMM_FAILURE.printStackTrace();
/* 172 */     } catch (INTERNAL iNTERNAL) {
/* 173 */       System.out.println(CorbaResourceUtil.getText("orbd.internalexception"));
/*     */       
/* 175 */       System.out.println(iNTERNAL);
/* 176 */       iNTERNAL.printStackTrace();
/* 177 */     } catch (Exception exception) {
/* 178 */       System.out.println(CorbaResourceUtil.getText("orbd.usage", "orbd"));
/*     */       
/* 180 */       System.out.println(exception);
/* 181 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processArgs(String[] paramArrayOfString) {
/* 187 */     Properties properties = System.getProperties();
/* 188 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 189 */       if (paramArrayOfString[b].equals("-port")) {
/* 190 */         if (b + 1 < paramArrayOfString.length) {
/* 191 */           properties.put("com.sun.CORBA.activation.Port", paramArrayOfString[++b]);
/*     */         } else {
/* 193 */           System.out.println(CorbaResourceUtil.getText("orbd.usage", "orbd"));
/*     */         }
/*     */       
/* 196 */       } else if (paramArrayOfString[b].equals("-defaultdb")) {
/* 197 */         if (b + 1 < paramArrayOfString.length) {
/* 198 */           properties.put("com.sun.CORBA.activation.DbDir", paramArrayOfString[++b]);
/*     */         } else {
/* 200 */           System.out.println(CorbaResourceUtil.getText("orbd.usage", "orbd"));
/*     */         }
/*     */       
/* 203 */       } else if (paramArrayOfString[b].equals("-serverid")) {
/* 204 */         if (b + 1 < paramArrayOfString.length) {
/* 205 */           properties.put("com.sun.CORBA.POA.ORBServerId", paramArrayOfString[++b]);
/*     */         } else {
/* 207 */           System.out.println(CorbaResourceUtil.getText("orbd.usage", "orbd"));
/*     */         }
/*     */       
/* 210 */       } else if (paramArrayOfString[b].equals("-serverPollingTime")) {
/* 211 */         if (b + 1 < paramArrayOfString.length) {
/* 212 */           properties.put("com.sun.CORBA.activation.ServerPollingTime", paramArrayOfString[++b]);
/*     */         } else {
/* 214 */           System.out.println(CorbaResourceUtil.getText("orbd.usage", "orbd"));
/*     */         }
/*     */       
/* 217 */       } else if (paramArrayOfString[b].equals("-serverStartupDelay")) {
/* 218 */         if (b + 1 < paramArrayOfString.length) {
/* 219 */           properties.put("com.sun.CORBA.activation.ServerStartupDelay", paramArrayOfString[++b]);
/*     */         } else {
/* 221 */           System.out.println(CorbaResourceUtil.getText("orbd.usage", "orbd"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean createSystemDirs(String paramString) {
/* 234 */     boolean bool = false;
/* 235 */     Properties properties = System.getProperties();
/* 236 */     String str = properties.getProperty("file.separator");
/*     */ 
/*     */     
/* 239 */     this.dbDir = new File(properties.getProperty("com.sun.CORBA.activation.DbDir", properties
/* 240 */           .getProperty("user.dir") + str + paramString));
/*     */ 
/*     */     
/* 243 */     this.dbDirName = this.dbDir.getAbsolutePath();
/* 244 */     properties.put("com.sun.CORBA.activation.DbDir", this.dbDirName);
/* 245 */     if (!this.dbDir.exists()) {
/* 246 */       this.dbDir.mkdir();
/* 247 */       bool = true;
/*     */     } 
/*     */     
/* 250 */     File file = new File(this.dbDir, "logs");
/* 251 */     if (!file.exists()) file.mkdir();
/*     */     
/* 253 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected File getDbDir() {
/* 259 */     return this.dbDir;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDbDirName() {
/* 265 */     return this.dbDirName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void startActivationObjects(ORB paramORB) throws Exception {
/* 271 */     initializeBootNaming(paramORB);
/*     */ 
/*     */     
/* 274 */     this.repository = new RepositoryImpl(paramORB, this.dbDir, paramORB.orbdDebugFlag);
/* 275 */     paramORB.register_initial_reference("ServerRepository", (Object)this.repository);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     ServerManagerImpl serverManagerImpl = new ServerManagerImpl(paramORB, paramORB.getCorbaTransportManager(), (Repository)this.repository, getDbDirName(), paramORB.orbdDebugFlag);
/*     */ 
/*     */     
/* 285 */     this.locator = LocatorHelper.narrow((Object)serverManagerImpl);
/* 286 */     paramORB.register_initial_reference("ServerLocator", (Object)this.locator);
/*     */     
/* 288 */     this.activator = ActivatorHelper.narrow((Object)serverManagerImpl);
/* 289 */     paramORB.register_initial_reference("ServerActivator", (Object)this.activator);
/*     */ 
/*     */     
/* 292 */     TransientNameService transientNameService = new TransientNameService(paramORB, "TNameService");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Locator getLocator() {
/* 299 */     return this.locator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Activator getActivator() {
/* 305 */     return this.activator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected RepositoryImpl getRepository() {
/* 311 */     return this.repository;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installOrbServers(RepositoryImpl paramRepositoryImpl, Activator paramActivator) {
/* 325 */     for (byte b = 0; b < orbServers.length; b++) {
/*     */       try {
/* 327 */         String[] arrayOfString = orbServers[b];
/* 328 */         ServerDef serverDef = new ServerDef(arrayOfString[1], arrayOfString[2], arrayOfString[3], arrayOfString[4], arrayOfString[5]);
/*     */ 
/*     */         
/* 331 */         int i = Integer.valueOf(orbServers[b][0]).intValue();
/*     */         
/* 333 */         paramRepositoryImpl.registerServer(serverDef, i);
/*     */         
/* 335 */         paramActivator.activate(i);
/*     */       }
/* 337 */       catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 342 */     ORBD oRBD = new ORBD();
/* 343 */     oRBD.run(paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 351 */   private static String[][] orbServers = new String[][] { { "" } };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/ORBD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */