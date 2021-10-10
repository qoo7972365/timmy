/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
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
/*     */ 
/*     */ 
/*     */ public class DNSName
/*     */   implements GeneralNameInterface
/*     */ {
/*     */   private String name;
/*     */   private static final String alphaDigits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
/*     */   
/*     */   public DNSName(DerValue paramDerValue) throws IOException {
/*  65 */     this.name = paramDerValue.getIA5String();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DNSName(String paramString) throws IOException {
/*  75 */     if (paramString == null || paramString.length() == 0)
/*  76 */       throw new IOException("DNSName must not be null or empty"); 
/*  77 */     if (paramString.contains(" "))
/*  78 */       throw new IOException("DNSName with blank components is not permitted"); 
/*  79 */     if (paramString.startsWith(".") || paramString.endsWith(".")) {
/*  80 */       throw new IOException("DNSName may not begin or end with a .");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     for (int i = 0; i < paramString.length(); i = j + 1) {
/*  87 */       int j = paramString.indexOf('.', i);
/*  88 */       if (j < 0) {
/*  89 */         j = paramString.length();
/*     */       }
/*  91 */       if (j - i < 1) {
/*  92 */         throw new IOException("DNSName with empty components are not permitted");
/*     */       }
/*     */       
/*  95 */       if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".indexOf(paramString.charAt(i)) < 0) {
/*  96 */         throw new IOException("DNSName components must begin with a letter or digit");
/*     */       }
/*  98 */       for (int k = i + 1; k < j; k++) {
/*  99 */         char c = paramString.charAt(k);
/* 100 */         if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".indexOf(c) < 0 && c != '-')
/* 101 */           throw new IOException("DNSName components must consist of letters, digits, and hyphens"); 
/*     */       } 
/*     */     } 
/* 104 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 112 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 119 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 129 */     paramDerOutputStream.putIA5String(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 136 */     return "DNSName: " + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 146 */     if (this == paramObject) {
/* 147 */       return true;
/*     */     }
/* 149 */     if (!(paramObject instanceof DNSName)) {
/* 150 */       return false;
/*     */     }
/* 152 */     DNSName dNSName = (DNSName)paramObject;
/*     */ 
/*     */ 
/*     */     
/* 156 */     return this.name.equalsIgnoreCase(dNSName.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 165 */     return this.name.toUpperCase(Locale.ENGLISH).hashCode();
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
/*     */   public int constrains(GeneralNameInterface paramGeneralNameInterface) throws UnsupportedOperationException {
/*     */     byte b;
/* 202 */     if (paramGeneralNameInterface == null) {
/* 203 */       b = -1;
/* 204 */     } else if (paramGeneralNameInterface.getType() != 2) {
/* 205 */       b = -1;
/*     */     } else {
/*     */       
/* 208 */       String str1 = ((DNSName)paramGeneralNameInterface).getName().toLowerCase(Locale.ENGLISH);
/* 209 */       String str2 = this.name.toLowerCase(Locale.ENGLISH);
/* 210 */       if (str1.equals(str2))
/* 211 */       { b = 0; }
/* 212 */       else if (str2.endsWith(str1))
/* 213 */       { int i = str2.lastIndexOf(str1);
/* 214 */         if (str2.charAt(i - 1) == '.')
/* 215 */         { b = 2; }
/*     */         else
/* 217 */         { b = 3; }  }
/* 218 */       else if (str1.endsWith(str2))
/* 219 */       { int i = str1.lastIndexOf(str2);
/* 220 */         if (str1.charAt(i - 1) == '.') {
/* 221 */           b = 1;
/*     */         } else {
/* 223 */           b = 3;
/*     */         }  }
/* 225 */       else { b = 3; }
/*     */     
/*     */     } 
/* 228 */     return b;
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
/*     */   public int subtreeDepth() throws UnsupportedOperationException {
/* 241 */     byte b = 1;
/*     */ 
/*     */     
/* 244 */     for (int i = this.name.indexOf('.'); i >= 0; i = this.name.indexOf('.', i + 1)) {
/* 245 */       b++;
/*     */     }
/*     */     
/* 248 */     return b;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/DNSName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */