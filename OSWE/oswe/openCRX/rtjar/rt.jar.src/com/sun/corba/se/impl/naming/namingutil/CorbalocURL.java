/*     */ package com.sun.corba.se.impl.naming.namingutil;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.NamingSystemException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class CorbalocURL
/*     */   extends INSURLBase
/*     */ {
/*  42 */   static NamingSystemException wrapper = NamingSystemException.get("naming.read");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorbalocURL(String paramString) {
/*  51 */     String str = paramString;
/*     */     
/*  53 */     if (str != null) {
/*     */       
/*     */       try {
/*  56 */         str = Utility.cleanEscapes(str);
/*  57 */       } catch (Exception exception) {
/*     */ 
/*     */         
/*  60 */         badAddress(exception);
/*     */       } 
/*  62 */       int i = str.indexOf('/');
/*  63 */       if (i == -1)
/*     */       {
/*  65 */         i = str.length();
/*     */       }
/*     */       
/*  68 */       if (i == 0)
/*     */       {
/*  70 */         badAddress((Throwable)null);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  75 */       StringTokenizer stringTokenizer = new StringTokenizer(str.substring(0, i), ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  81 */       while (stringTokenizer.hasMoreTokens()) {
/*  82 */         String str1 = stringTokenizer.nextToken();
/*  83 */         IIOPEndpointInfo iIOPEndpointInfo = null;
/*  84 */         if (str1.startsWith("iiop:")) {
/*  85 */           iIOPEndpointInfo = handleIIOPColon(str1);
/*  86 */         } else if (str1.startsWith("rir:")) {
/*  87 */           handleRIRColon(str1);
/*  88 */           this.rirFlag = true;
/*  89 */         } else if (str1.startsWith(":")) {
/*  90 */           iIOPEndpointInfo = handleColon(str1);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/*  95 */           badAddress((Throwable)null);
/*     */         } 
/*  97 */         if (!this.rirFlag) {
/*     */ 
/*     */ 
/*     */           
/* 101 */           if (this.theEndpointInfo == null) {
/* 102 */             this.theEndpointInfo = new ArrayList();
/*     */           }
/* 104 */           this.theEndpointInfo.add(iIOPEndpointInfo);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 109 */       if (str.length() > i + 1) {
/* 110 */         this.theKeyString = str.substring(i + 1);
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
/*     */   private void badAddress(Throwable paramThrowable) {
/* 122 */     throw wrapper.insBadAddress(paramThrowable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IIOPEndpointInfo handleIIOPColon(String paramString) {
/* 132 */     paramString = paramString.substring(4);
/* 133 */     return handleColon(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IIOPEndpointInfo handleColon(String paramString) {
/* 143 */     paramString = paramString.substring(1);
/* 144 */     String str = paramString;
/*     */     
/* 146 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "@");
/* 147 */     IIOPEndpointInfo iIOPEndpointInfo = new IIOPEndpointInfo();
/* 148 */     int i = stringTokenizer.countTokens();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (i == 0 || i > 2)
/*     */     {
/*     */       
/* 158 */       badAddress((Throwable)null);
/*     */     }
/* 160 */     if (i == 2) {
/*     */       
/* 162 */       String str1 = stringTokenizer.nextToken();
/* 163 */       int j = str1.indexOf('.');
/*     */ 
/*     */       
/* 166 */       if (j == -1) {
/* 167 */         badAddress((Throwable)null);
/*     */       }
/*     */       try {
/* 170 */         iIOPEndpointInfo.setVersion(
/* 171 */             Integer.parseInt(str1.substring(0, j)), 
/* 172 */             Integer.parseInt(str1.substring(j + 1)));
/* 173 */         str = stringTokenizer.nextToken();
/* 174 */       } catch (Throwable throwable) {
/* 175 */         badAddress(throwable);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 182 */       int j = str.indexOf('[');
/* 183 */       if (j != -1) {
/*     */ 
/*     */ 
/*     */         
/* 187 */         String str1 = getIPV6Port(str);
/* 188 */         if (str1 != null) {
/* 189 */           iIOPEndpointInfo.setPort(Integer.parseInt(str1));
/*     */         }
/* 191 */         iIOPEndpointInfo.setHost(getIPV6Host(str));
/* 192 */         return iIOPEndpointInfo;
/*     */       } 
/* 194 */       stringTokenizer = new StringTokenizer(str, ":");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 200 */       if (stringTokenizer.countTokens() == 2)
/*     */       {
/* 202 */         iIOPEndpointInfo.setHost(stringTokenizer.nextToken());
/* 203 */         iIOPEndpointInfo.setPort(Integer.parseInt(stringTokenizer
/* 204 */               .nextToken()));
/*     */       }
/* 206 */       else if (str != null && str
/* 207 */         .length() != 0)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 212 */         iIOPEndpointInfo.setHost(str);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 218 */     catch (Throwable throwable) {
/*     */ 
/*     */ 
/*     */       
/* 222 */       badAddress(throwable);
/*     */     } 
/* 224 */     Utility.validateGIOPVersion(iIOPEndpointInfo);
/* 225 */     return iIOPEndpointInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleRIRColon(String paramString) {
/* 233 */     if (paramString.length() != 4) {
/* 234 */       badAddress((Throwable)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getIPV6Port(String paramString) {
/* 246 */     int i = paramString.indexOf(']');
/*     */ 
/*     */ 
/*     */     
/* 250 */     if (i + 1 != paramString.length()) {
/* 251 */       if (paramString.charAt(i + 1) != ':') {
/* 252 */         throw new RuntimeException("Host and Port is not separated by ':'");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       return paramString.substring(i + 2);
/*     */     } 
/* 260 */     return null;
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
/*     */   private String getIPV6Host(String paramString) {
/* 274 */     int i = paramString.indexOf(']');
/*     */     
/* 276 */     return paramString.substring(1, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCorbanameURL() {
/* 284 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/namingutil/CorbalocURL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */