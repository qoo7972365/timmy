/*     */ package com.sun.corba.se.impl.naming.cosnaming;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import org.omg.CosNaming.NameComponent;
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
/*     */ public class NamingUtils
/*     */ {
/*     */   public static boolean debug = false;
/*     */   public static PrintStream debugStream;
/*     */   public static PrintStream errStream;
/*     */   
/*     */   public static void dprint(String paramString) {
/*  47 */     if (debug && debugStream != null) {
/*  48 */       debugStream.println(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void errprint(String paramString) {
/*  56 */     if (errStream != null) {
/*  57 */       errStream.println(paramString);
/*     */     } else {
/*  59 */       System.err.println(paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void printException(Exception paramException) {
/*  67 */     if (errStream != null) {
/*  68 */       paramException.printStackTrace(errStream);
/*     */     } else {
/*  70 */       paramException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void makeDebugStream(File paramFile) throws IOException {
/*  81 */     FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
/*     */     
/*  83 */     DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
/*     */     
/*  85 */     debugStream = new PrintStream(dataOutputStream);
/*     */ 
/*     */     
/*  88 */     debugStream.println("Debug Stream Enabled.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void makeErrStream(File paramFile) throws IOException {
/*  98 */     if (debug) {
/*     */       
/* 100 */       FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
/*     */       
/* 102 */       DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
/*     */       
/* 104 */       errStream = new PrintStream(dataOutputStream);
/* 105 */       dprint("Error stream setup completed.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getDirectoryStructuredName(NameComponent[] paramArrayOfNameComponent) {
/* 116 */     StringBuffer stringBuffer = new StringBuffer("/");
/* 117 */     for (byte b = 0; b < paramArrayOfNameComponent.length; b++) {
/* 118 */       stringBuffer.append((paramArrayOfNameComponent[b]).id + "." + (paramArrayOfNameComponent[b]).kind);
/*     */     }
/* 120 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/cosnaming/NamingUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */