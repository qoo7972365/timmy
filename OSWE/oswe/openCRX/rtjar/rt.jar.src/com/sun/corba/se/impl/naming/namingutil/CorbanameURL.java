/*     */ package com.sun.corba.se.impl.naming.namingutil;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.NamingSystemException;
/*     */ import java.util.ArrayList;
/*     */ import org.omg.CORBA.BAD_PARAM;
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
/*     */ public class CorbanameURL
/*     */   extends INSURLBase
/*     */ {
/*  41 */   private static NamingSystemException wrapper = NamingSystemException.get("naming");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorbanameURL(String paramString) {
/*  49 */     String str1 = paramString;
/*     */ 
/*     */     
/*     */     try {
/*  53 */       str1 = Utility.cleanEscapes(str1);
/*  54 */     } catch (Exception exception) {
/*  55 */       badAddress(exception);
/*     */     } 
/*     */     
/*  58 */     int i = str1.indexOf('#');
/*  59 */     String str2 = null;
/*  60 */     if (i != -1) {
/*     */ 
/*     */ 
/*     */       
/*  64 */       str2 = "corbaloc:" + str1.substring(0, i) + "/";
/*     */     }
/*     */     else {
/*     */       
/*  68 */       str2 = "corbaloc:" + str1.substring(0, str1.length());
/*     */ 
/*     */       
/*  71 */       if (str2.endsWith("/") != true) {
/*  72 */         str2 = str2 + "/";
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  79 */       INSURL iNSURL = INSURLHandler.getINSURLHandler().parseURL(str2);
/*  80 */       copyINSURL(iNSURL);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  85 */       if (i > -1 && i < paramString
/*  86 */         .length() - 1) {
/*     */         
/*  88 */         int j = i + 1;
/*  89 */         String str = str1.substring(j);
/*  90 */         this.theStringifiedName = str;
/*     */       } 
/*  92 */     } catch (Exception exception) {
/*  93 */       badAddress(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void badAddress(Throwable paramThrowable) throws BAD_PARAM {
/* 103 */     throw wrapper.insBadAddress(paramThrowable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyINSURL(INSURL paramINSURL) {
/* 111 */     this.rirFlag = paramINSURL.getRIRFlag();
/* 112 */     this.theEndpointInfo = (ArrayList)paramINSURL.getEndpointInfo();
/* 113 */     this.theKeyString = paramINSURL.getKeyString();
/* 114 */     this.theStringifiedName = paramINSURL.getStringifiedName();
/*     */   }
/*     */   
/*     */   public boolean isCorbanameURL() {
/* 118 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/namingutil/CorbanameURL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */