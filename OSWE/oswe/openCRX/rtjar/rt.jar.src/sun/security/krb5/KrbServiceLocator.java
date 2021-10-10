/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.net.SocketPermission;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Random;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.spi.NamingManager;
/*     */ import sun.security.krb5.internal.Krb5;
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
/*     */ class KrbServiceLocator
/*     */ {
/*     */   private static final String SRV_RR = "SRV";
/*  53 */   private static final String[] SRV_RR_ATTR = new String[] { "SRV" };
/*     */   
/*     */   private static final String SRV_TXT = "TXT";
/*  56 */   private static final String[] SRV_TXT_ATTR = new String[] { "TXT" };
/*     */   
/*  58 */   private static final Random random = new Random();
/*     */   
/*  60 */   private static final boolean DEBUG = Krb5.DEBUG;
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
/*     */   static String[] getKerberosService(String paramString) {
/*  79 */     String str = "dns:///_kerberos." + paramString;
/*  80 */     String[] arrayOfString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  86 */       Context context = NamingManager.getURLContext("dns", new Hashtable<>(0));
/*  87 */       if (!(context instanceof DirContext)) {
/*  88 */         return null;
/*     */       }
/*  90 */       Attributes attributes = null;
/*     */       
/*     */       try {
/*  93 */         attributes = AccessController.<Attributes>doPrivileged(() -> ((DirContext)paramContext).getAttributes(paramString, SRV_TXT_ATTR), (AccessControlContext)null, new Permission[] { new SocketPermission("*", "connect,accept") });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  99 */       catch (PrivilegedActionException privilegedActionException) {
/* 100 */         throw (NamingException)privilegedActionException.getCause();
/*     */       } 
/*     */       
/*     */       Attribute attribute;
/* 104 */       if (attributes != null && (attribute = attributes.get("TXT")) != null) {
/* 105 */         int i = attribute.size();
/* 106 */         byte b1 = 0;
/* 107 */         String[] arrayOfString1 = new String[i];
/*     */ 
/*     */         
/* 110 */         byte b2 = 0;
/* 111 */         byte b3 = 0;
/* 112 */         while (b2 < i) {
/*     */           try {
/* 114 */             arrayOfString1[b3] = (String)attribute.get(b2);
/* 115 */             b3++;
/* 116 */           } catch (Exception exception) {}
/*     */ 
/*     */           
/* 119 */           b2++;
/*     */         } 
/* 121 */         b1 = b3;
/*     */ 
/*     */         
/* 124 */         if (b1 < i) {
/* 125 */           String[] arrayOfString2 = new String[b1];
/* 126 */           System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, b1);
/* 127 */           arrayOfString = arrayOfString2;
/*     */         } else {
/* 129 */           arrayOfString = arrayOfString1;
/*     */         } 
/*     */       } 
/* 132 */     } catch (NamingException namingException) {}
/*     */ 
/*     */     
/* 135 */     return arrayOfString;
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
/*     */   
/*     */   static String[] getKerberosService(String paramString1, String paramString2) {
/* 150 */     String str = "dns:///_kerberos." + paramString2 + "." + paramString1;
/* 151 */     String[] arrayOfString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 158 */       Context context = NamingManager.getURLContext("dns", new Hashtable<>(0));
/* 159 */       if (!(context instanceof DirContext)) {
/* 160 */         return null;
/*     */       }
/*     */       
/* 163 */       Attributes attributes = null;
/*     */       
/*     */       try {
/* 166 */         attributes = AccessController.<Attributes>doPrivileged(() -> ((DirContext)paramContext).getAttributes(paramString, SRV_RR_ATTR), (AccessControlContext)null, new Permission[] { new SocketPermission("*", "connect,accept") });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 172 */       catch (PrivilegedActionException privilegedActionException) {
/* 173 */         throw (NamingException)privilegedActionException.getCause();
/*     */       } 
/*     */       
/*     */       Attribute attribute;
/*     */       
/* 178 */       if (attributes != null && (attribute = attributes.get("SRV")) != null) {
/* 179 */         int i = attribute.size();
/* 180 */         byte b1 = 0;
/* 181 */         SrvRecord[] arrayOfSrvRecord = new SrvRecord[i];
/*     */ 
/*     */         
/* 184 */         byte b2 = 0;
/* 185 */         byte b3 = 0;
/* 186 */         while (b2 < i) {
/*     */           try {
/* 188 */             arrayOfSrvRecord[b3] = new SrvRecord((String)attribute.get(b2));
/* 189 */             b3++;
/* 190 */           } catch (Exception exception) {}
/*     */ 
/*     */           
/* 193 */           b2++;
/*     */         } 
/* 195 */         b1 = b3;
/*     */ 
/*     */         
/* 198 */         if (b1 < i) {
/* 199 */           SrvRecord[] arrayOfSrvRecord1 = new SrvRecord[b1];
/* 200 */           System.arraycopy(arrayOfSrvRecord, 0, arrayOfSrvRecord1, 0, b1);
/* 201 */           arrayOfSrvRecord = arrayOfSrvRecord1;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 207 */         if (b1 > 1) {
/* 208 */           Arrays.sort((Object[])arrayOfSrvRecord);
/*     */         }
/*     */ 
/*     */         
/* 212 */         arrayOfString = extractHostports(arrayOfSrvRecord);
/*     */       } 
/* 214 */     } catch (NamingException namingException) {}
/*     */ 
/*     */ 
/*     */     
/* 218 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] extractHostports(SrvRecord[] paramArrayOfSrvRecord) {
/* 226 */     String[] arrayOfString = null;
/*     */     
/* 228 */     byte b1 = 0;
/* 229 */     byte b2 = 0;
/* 230 */     int i = 0;
/* 231 */     byte b3 = 0;
/* 232 */     for (byte b4 = 0; b4 < paramArrayOfSrvRecord.length; b4++) {
/* 233 */       if (arrayOfString == null) {
/* 234 */         arrayOfString = new String[paramArrayOfSrvRecord.length];
/*     */       }
/*     */ 
/*     */       
/* 238 */       b1 = b4;
/* 239 */       while (b4 < paramArrayOfSrvRecord.length - 1 && (paramArrayOfSrvRecord[b4]).priority == (paramArrayOfSrvRecord[b4 + 1]).priority)
/*     */       {
/* 241 */         b4++;
/*     */       }
/* 243 */       b2 = b4;
/*     */ 
/*     */       
/* 246 */       i = b2 - b1 + 1;
/* 247 */       for (byte b = 0; b < i; b++) {
/* 248 */         arrayOfString[b3++] = selectHostport(paramArrayOfSrvRecord, b1, b2);
/*     */       }
/*     */     } 
/* 251 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String selectHostport(SrvRecord[] paramArrayOfSrvRecord, int paramInt1, int paramInt2) {
/* 260 */     if (paramInt1 == paramInt2) {
/* 261 */       return (paramArrayOfSrvRecord[paramInt1]).hostport;
/*     */     }
/*     */ 
/*     */     
/* 265 */     int i = 0;
/* 266 */     for (int j = paramInt1; j <= paramInt2; j++) {
/* 267 */       if (paramArrayOfSrvRecord[j] != null) {
/* 268 */         i += (paramArrayOfSrvRecord[j]).weight;
/* 269 */         (paramArrayOfSrvRecord[j]).sum = i;
/*     */       } 
/*     */     } 
/* 272 */     String str = null;
/*     */ 
/*     */ 
/*     */     
/* 276 */     byte b = (i == 0) ? 0 : random.nextInt(i + 1);
/* 277 */     for (int k = paramInt1; k <= paramInt2; k++) {
/* 278 */       if (paramArrayOfSrvRecord[k] != null && (paramArrayOfSrvRecord[k]).sum >= b) {
/* 279 */         str = (paramArrayOfSrvRecord[k]).hostport;
/* 280 */         paramArrayOfSrvRecord[k] = null;
/*     */         break;
/*     */       } 
/*     */     } 
/* 284 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class SrvRecord
/*     */     implements Comparable<SrvRecord>
/*     */   {
/*     */     int priority;
/*     */ 
/*     */     
/*     */     int weight;
/*     */ 
/*     */     
/*     */     int sum;
/*     */ 
/*     */     
/*     */     String hostport;
/*     */ 
/*     */ 
/*     */     
/*     */     SrvRecord(String param1String) throws Exception {
/* 307 */       StringTokenizer stringTokenizer = new StringTokenizer(param1String, " ");
/*     */ 
/*     */       
/* 310 */       if (stringTokenizer.countTokens() == 4) {
/* 311 */         this.priority = Integer.parseInt(stringTokenizer.nextToken());
/* 312 */         this.weight = Integer.parseInt(stringTokenizer.nextToken());
/* 313 */         String str = stringTokenizer.nextToken();
/* 314 */         this.hostport = stringTokenizer.nextToken() + ":" + str;
/*     */       } else {
/* 316 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(SrvRecord param1SrvRecord) {
/* 325 */       if (this.priority > param1SrvRecord.priority)
/* 326 */         return 1; 
/* 327 */       if (this.priority < param1SrvRecord.priority)
/* 328 */         return -1; 
/* 329 */       if (this.weight == 0 && param1SrvRecord.weight != 0)
/* 330 */         return -1; 
/* 331 */       if (this.weight != 0 && param1SrvRecord.weight == 0) {
/* 332 */         return 1;
/*     */       }
/* 334 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbServiceLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */