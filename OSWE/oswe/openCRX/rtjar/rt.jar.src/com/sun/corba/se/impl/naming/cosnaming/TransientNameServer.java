/*     */ package com.sun.corba.se.impl.naming.cosnaming;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.NamingSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
/*     */ import com.sun.corba.se.org.omg.CORBA.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.SystemException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransientNameServer
/*     */ {
/*     */   private static boolean debug = false;
/*  63 */   static NamingSystemException wrapper = NamingSystemException.get("naming");
/*     */ 
/*     */   
/*     */   public static void trace(String paramString) {
/*  67 */     if (debug) {
/*  68 */       System.out.println(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initDebug(String[] paramArrayOfString) {
/*  74 */     if (debug) {
/*     */       return;
/*     */     }
/*  77 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  78 */       if (paramArrayOfString[b].equalsIgnoreCase("-debug")) {
/*  79 */         debug = true; return;
/*     */       } 
/*     */     } 
/*  82 */     debug = false;
/*     */   }
/*     */   
/*     */   private static Object initializeRootNamingContext(ORB paramORB) {
/*  86 */     Object object = null;
/*     */     try {
/*  88 */       ORB oRB = (ORB)paramORB;
/*     */ 
/*     */       
/*  91 */       TransientNameService transientNameService = new TransientNameService(oRB);
/*  92 */       return transientNameService.initialNamingContext();
/*  93 */     } catch (SystemException systemException) {
/*  94 */       throw wrapper.transNsCannotCreateInitialNcSys(systemException);
/*  95 */     } catch (Exception exception) {
/*  96 */       throw wrapper.transNsCannotCreateInitialNc(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 107 */     initDebug(paramArrayOfString);
/*     */     
/* 109 */     boolean bool1 = false;
/* 110 */     boolean bool2 = false;
/*     */ 
/*     */     
/* 113 */     int i = 0;
/*     */     try {
/* 115 */       trace("Transient name server started with args " + paramArrayOfString);
/*     */ 
/*     */       
/* 118 */       Properties properties = System.getProperties();
/*     */       
/* 120 */       properties.put("com.sun.CORBA.POA.ORBServerId", "1000000");
/* 121 */       properties.put("org.omg.CORBA.ORBClass", "com.sun.corba.se.impl.orb.ORBImpl");
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 126 */         String str1 = System.getProperty("org.omg.CORBA.ORBInitialPort");
/* 127 */         if (str1 != null && str1.length() > 0) {
/* 128 */           i = Integer.parseInt(str1);
/*     */           
/* 130 */           if (i == 0) {
/* 131 */             bool2 = true;
/* 132 */             throw wrapper.transientNameServerBadPort();
/*     */           } 
/*     */         } 
/*     */         
/* 136 */         String str2 = System.getProperty("org.omg.CORBA.ORBInitialHost");
/* 137 */         if (str2 != null) {
/* 138 */           bool1 = true;
/* 139 */           throw wrapper.transientNameServerBadHost();
/*     */         } 
/* 141 */       } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*     */         
/* 148 */         if (paramArrayOfString[b].equals("-ORBInitialPort") && b < paramArrayOfString.length - 1) {
/*     */           
/* 150 */           i = Integer.parseInt(paramArrayOfString[b + 1]);
/*     */           
/* 152 */           if (i == 0) {
/* 153 */             bool2 = true;
/* 154 */             throw wrapper.transientNameServerBadPort();
/*     */           } 
/*     */         } 
/* 157 */         if (paramArrayOfString[b].equals("-ORBInitialHost")) {
/* 158 */           bool1 = true;
/* 159 */           throw wrapper.transientNameServerBadHost();
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 165 */       if (i == 0) {
/* 166 */         i = 900;
/* 167 */         properties.put("org.omg.CORBA.ORBInitialPort", 
/* 168 */             Integer.toString(i));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 173 */       properties.put("com.sun.CORBA.POA.ORBPersistentServerPort", 
/* 174 */           Integer.toString(i));
/*     */       
/* 176 */       ORB oRB = ORB.init(paramArrayOfString, properties);
/* 177 */       trace("ORB object returned from init: " + oRB);
/*     */       
/* 179 */       Object object = initializeRootNamingContext(oRB);
/* 180 */       ((ORB)oRB).register_initial_reference("NamingService", object);
/*     */ 
/*     */       
/* 183 */       String str = null;
/*     */       
/* 185 */       if (object != null) {
/* 186 */         str = oRB.object_to_string(object);
/*     */       } else {
/* 188 */         NamingUtils.errprint(CorbaResourceUtil.getText("tnameserv.exception", i));
/*     */         
/* 190 */         NamingUtils.errprint(CorbaResourceUtil.getText("tnameserv.usage"));
/*     */         
/* 192 */         System.exit(1);
/*     */       } 
/*     */       
/* 195 */       trace("name service created");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       System.out.println(CorbaResourceUtil.getText("tnameserv.hs1", str));
/*     */       
/* 203 */       System.out.println(CorbaResourceUtil.getText("tnameserv.hs2", i));
/*     */       
/* 205 */       System.out.println(CorbaResourceUtil.getText("tnameserv.hs3"));
/*     */ 
/*     */       
/* 208 */       Object object1 = new Object();
/* 209 */       synchronized (object1) { object1.wait(); } 
/* 210 */     } catch (Exception exception) {
/* 211 */       if (bool1) {
/*     */ 
/*     */         
/* 214 */         NamingUtils.errprint(CorbaResourceUtil.getText("tnameserv.invalidhostoption"));
/*     */       }
/* 216 */       else if (bool2) {
/*     */ 
/*     */         
/* 219 */         NamingUtils.errprint(CorbaResourceUtil.getText("tnameserv.orbinitialport0"));
/*     */       } else {
/*     */         
/* 222 */         NamingUtils.errprint(CorbaResourceUtil.getText("tnameserv.exception", i));
/*     */         
/* 224 */         NamingUtils.errprint(CorbaResourceUtil.getText("tnameserv.usage"));
/*     */       } 
/*     */ 
/*     */       
/* 228 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/cosnaming/TransientNameServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */