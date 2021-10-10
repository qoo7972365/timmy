/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.ldap.LdapName;
/*     */ import javax.naming.ldap.Rdn;
/*     */ import javax.naming.spi.NamingManager;
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
/*     */ class ServiceLocator
/*     */ {
/*     */   private static final String SRV_RR = "SRV";
/*  49 */   private static final String[] SRV_RR_ATTR = new String[] { "SRV" };
/*     */   
/*  51 */   private static final Random random = new Random();
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
/*     */   static String mapDnToDomainName(String paramString) throws InvalidNameException {
/*  68 */     if (paramString == null) {
/*  69 */       return null;
/*     */     }
/*  71 */     StringBuffer stringBuffer = new StringBuffer();
/*  72 */     LdapName ldapName = new LdapName(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     List<Rdn> list = ldapName.getRdns();
/*  78 */     for (int i = list.size() - 1; i >= 0; i--) {
/*     */       
/*  80 */       Rdn rdn = list.get(i);
/*     */ 
/*     */       
/*  83 */       if (rdn.size() == 1 && "dc"
/*  84 */         .equalsIgnoreCase(rdn.getType())) {
/*  85 */         Object object = rdn.getValue();
/*  86 */         if (object instanceof String) {
/*  87 */           if (object.equals(".") || (stringBuffer
/*  88 */             .length() == 1 && stringBuffer.charAt(0) == '.')) {
/*  89 */             stringBuffer.setLength(0);
/*     */           }
/*     */           
/*  92 */           if (stringBuffer.length() > 0) {
/*  93 */             stringBuffer.append('.');
/*     */           }
/*  95 */           stringBuffer.append(object);
/*     */         } else {
/*  97 */           stringBuffer.setLength(0);
/*     */         } 
/*     */       } else {
/* 100 */         stringBuffer.setLength(0);
/*     */       } 
/*     */     } 
/* 103 */     return (stringBuffer.length() != 0) ? stringBuffer.toString() : null;
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
/*     */   static String[] getLdapService(String paramString, Hashtable<?, ?> paramHashtable) {
/* 118 */     if (paramString == null || paramString.length() == 0) {
/* 119 */       return null;
/*     */     }
/*     */     
/* 122 */     String str = "dns:///_ldap._tcp." + paramString;
/* 123 */     String[] arrayOfString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 130 */       Context context = NamingManager.getURLContext("dns", paramHashtable);
/* 131 */       if (!(context instanceof DirContext)) {
/* 132 */         return null;
/*     */       }
/*     */       
/* 135 */       Attributes attributes = ((DirContext)context).getAttributes(str, SRV_RR_ATTR);
/*     */       
/*     */       Attribute attribute;
/* 138 */       if (attributes != null && (attribute = attributes.get("SRV")) != null) {
/* 139 */         int i = attribute.size();
/* 140 */         byte b1 = 0;
/* 141 */         SrvRecord[] arrayOfSrvRecord = new SrvRecord[i];
/*     */ 
/*     */         
/* 144 */         byte b2 = 0;
/* 145 */         byte b3 = 0;
/* 146 */         while (b2 < i) {
/*     */           try {
/* 148 */             arrayOfSrvRecord[b3] = new SrvRecord((String)attribute.get(b2));
/* 149 */             b3++;
/* 150 */           } catch (Exception exception) {}
/*     */ 
/*     */           
/* 153 */           b2++;
/*     */         } 
/* 155 */         b1 = b3;
/*     */ 
/*     */         
/* 158 */         if (b1 < i) {
/* 159 */           SrvRecord[] arrayOfSrvRecord1 = new SrvRecord[b1];
/* 160 */           System.arraycopy(arrayOfSrvRecord, 0, arrayOfSrvRecord1, 0, b1);
/* 161 */           arrayOfSrvRecord = arrayOfSrvRecord1;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 167 */         if (b1 > 1) {
/* 168 */           Arrays.sort((Object[])arrayOfSrvRecord);
/*     */         }
/*     */ 
/*     */         
/* 172 */         arrayOfString = extractHostports(arrayOfSrvRecord);
/*     */       } 
/* 174 */     } catch (NamingException namingException) {}
/*     */ 
/*     */     
/* 177 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] extractHostports(SrvRecord[] paramArrayOfSrvRecord) {
/* 185 */     String[] arrayOfString = null;
/*     */     
/* 187 */     byte b1 = 0;
/* 188 */     byte b2 = 0;
/* 189 */     int i = 0;
/* 190 */     byte b3 = 0;
/* 191 */     for (byte b4 = 0; b4 < paramArrayOfSrvRecord.length; b4++) {
/* 192 */       if (arrayOfString == null) {
/* 193 */         arrayOfString = new String[paramArrayOfSrvRecord.length];
/*     */       }
/*     */ 
/*     */       
/* 197 */       b1 = b4;
/* 198 */       while (b4 < paramArrayOfSrvRecord.length - 1 && (paramArrayOfSrvRecord[b4]).priority == (paramArrayOfSrvRecord[b4 + 1]).priority)
/*     */       {
/* 200 */         b4++;
/*     */       }
/* 202 */       b2 = b4;
/*     */ 
/*     */       
/* 205 */       i = b2 - b1 + 1;
/* 206 */       for (byte b = 0; b < i; b++) {
/* 207 */         arrayOfString[b3++] = selectHostport(paramArrayOfSrvRecord, b1, b2);
/*     */       }
/*     */     } 
/* 210 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String selectHostport(SrvRecord[] paramArrayOfSrvRecord, int paramInt1, int paramInt2) {
/* 219 */     if (paramInt1 == paramInt2) {
/* 220 */       return (paramArrayOfSrvRecord[paramInt1]).hostport;
/*     */     }
/*     */ 
/*     */     
/* 224 */     int i = 0;
/* 225 */     for (int j = paramInt1; j <= paramInt2; j++) {
/* 226 */       if (paramArrayOfSrvRecord[j] != null) {
/* 227 */         i += (paramArrayOfSrvRecord[j]).weight;
/* 228 */         (paramArrayOfSrvRecord[j]).sum = i;
/*     */       } 
/*     */     } 
/* 231 */     String str = null;
/*     */ 
/*     */ 
/*     */     
/* 235 */     byte b = (i == 0) ? 0 : random.nextInt(i + 1);
/* 236 */     for (int k = paramInt1; k <= paramInt2; k++) {
/* 237 */       if (paramArrayOfSrvRecord[k] != null && (paramArrayOfSrvRecord[k]).sum >= b) {
/* 238 */         str = (paramArrayOfSrvRecord[k]).hostport;
/* 239 */         paramArrayOfSrvRecord[k] = null;
/*     */         break;
/*     */       } 
/*     */     } 
/* 243 */     return str;
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
/* 266 */       StringTokenizer stringTokenizer = new StringTokenizer(param1String, " ");
/*     */ 
/*     */       
/* 269 */       if (stringTokenizer.countTokens() == 4) {
/* 270 */         this.priority = Integer.parseInt(stringTokenizer.nextToken());
/* 271 */         this.weight = Integer.parseInt(stringTokenizer.nextToken());
/* 272 */         String str = stringTokenizer.nextToken();
/* 273 */         this.hostport = stringTokenizer.nextToken() + ":" + str;
/*     */       } else {
/* 275 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(SrvRecord param1SrvRecord) {
/* 284 */       if (this.priority > param1SrvRecord.priority)
/* 285 */         return 1; 
/* 286 */       if (this.priority < param1SrvRecord.priority)
/* 287 */         return -1; 
/* 288 */       if (this.weight == 0 && param1SrvRecord.weight != 0)
/* 289 */         return -1; 
/* 290 */       if (this.weight != 0 && param1SrvRecord.weight == 0) {
/* 291 */         return 1;
/*     */       }
/* 293 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/ServiceLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */