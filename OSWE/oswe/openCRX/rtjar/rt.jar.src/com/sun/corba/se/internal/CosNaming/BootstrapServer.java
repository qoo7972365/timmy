/*     */ package com.sun.corba.se.internal.CosNaming;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.resolver.LocalResolver;
/*     */ import com.sun.corba.se.spi.resolver.Resolver;
/*     */ import com.sun.corba.se.spi.resolver.ResolverDefault;
/*     */ import java.io.File;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.ORBPackage.InvalidName;
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
/*     */ public class BootstrapServer
/*     */ {
/*     */   private ORB orb;
/*     */   
/*     */   public static final void main(String[] paramArrayOfString) {
/*  63 */     String str = null;
/*  64 */     int i = 900;
/*     */ 
/*     */     
/*  67 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*     */       
/*  69 */       if (paramArrayOfString[b].equals("-InitialServicesFile") && b < paramArrayOfString.length - 1) {
/*  70 */         str = paramArrayOfString[b + 1];
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  78 */       if (paramArrayOfString[b].equals("-ORBInitialPort") && b < paramArrayOfString.length - 1) {
/*  79 */         i = Integer.parseInt(paramArrayOfString[b + 1]);
/*     */       }
/*     */     } 
/*     */     
/*  83 */     if (str == null) {
/*  84 */       System.out.println(CorbaResourceUtil.getText("bootstrap.usage", "BootstrapServer"));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  90 */     File file = new File(str);
/*     */ 
/*     */     
/*  93 */     if (file.exists() == true && !file.canRead()) {
/*  94 */       System.err.println(CorbaResourceUtil.getText("bootstrap.filenotreadable", file
/*  95 */             .getAbsolutePath()));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 100 */     System.out.println(CorbaResourceUtil.getText("bootstrap.success", 
/* 101 */           Integer.toString(i), file
/* 102 */           .getAbsolutePath()));
/*     */     
/* 104 */     Properties properties = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     properties.put("com.sun.CORBA.ORBServerPort", 
/* 111 */         Integer.toString(i));
/*     */     
/* 113 */     ORB oRB = (ORB)ORB.init(paramArrayOfString, properties);
/*     */     
/* 115 */     LocalResolver localResolver1 = oRB.getLocalResolver();
/* 116 */     Resolver resolver1 = ResolverDefault.makeFileResolver(oRB, file);
/* 117 */     Resolver resolver2 = ResolverDefault.makeCompositeResolver(resolver1, (Resolver)localResolver1);
/* 118 */     LocalResolver localResolver2 = ResolverDefault.makeSplitLocalResolver(resolver2, localResolver1);
/*     */     
/* 120 */     oRB.setLocalResolver(localResolver2);
/*     */ 
/*     */     
/*     */     try {
/* 124 */       oRB.resolve_initial_references("RootPOA");
/* 125 */     } catch (InvalidName invalidName) {
/* 126 */       RuntimeException runtimeException = new RuntimeException("This should not happen");
/* 127 */       runtimeException.initCause((Throwable)invalidName);
/* 128 */       throw runtimeException;
/*     */     } 
/*     */     
/* 131 */     oRB.run();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/internal/CosNaming/BootstrapServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */