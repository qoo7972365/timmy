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
/*     */ public class RFC822Name
/*     */   implements GeneralNameInterface
/*     */ {
/*     */   private String name;
/*     */   
/*     */   public RFC822Name(DerValue paramDerValue) throws IOException {
/*  54 */     this.name = paramDerValue.getIA5String();
/*  55 */     parseName(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RFC822Name(String paramString) throws IOException {
/*  65 */     parseName(paramString);
/*  66 */     this.name = paramString;
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
/*     */   public void parseName(String paramString) throws IOException {
/*  82 */     if (paramString == null || paramString.length() == 0) {
/*  83 */       throw new IOException("RFC822Name may not be null or empty");
/*     */     }
/*     */     
/*  86 */     String str = paramString.substring(paramString.indexOf('@') + 1);
/*  87 */     if (str.length() == 0) {
/*  88 */       throw new IOException("RFC822Name may not end with @");
/*     */     }
/*     */ 
/*     */     
/*  92 */     if (str.startsWith(".") && 
/*  93 */       str.length() == 1) {
/*  94 */       throw new IOException("RFC822Name domain may not be just .");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 103 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 110 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 120 */     paramDerOutputStream.putIA5String(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 127 */     return "RFC822Name: " + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 137 */     if (this == paramObject) {
/* 138 */       return true;
/*     */     }
/* 140 */     if (!(paramObject instanceof RFC822Name)) {
/* 141 */       return false;
/*     */     }
/* 143 */     RFC822Name rFC822Name = (RFC822Name)paramObject;
/*     */ 
/*     */ 
/*     */     
/* 147 */     return this.name.equalsIgnoreCase(rFC822Name.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 156 */     return this.name.toUpperCase(Locale.ENGLISH).hashCode();
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
/*     */   public int constrains(GeneralNameInterface paramGeneralNameInterface) throws UnsupportedOperationException {
/*     */     byte b;
/* 185 */     if (paramGeneralNameInterface == null) {
/* 186 */       b = -1;
/* 187 */     } else if (paramGeneralNameInterface.getType() != 1) {
/* 188 */       b = -1;
/*     */     }
/*     */     else {
/*     */       
/* 192 */       String str1 = ((RFC822Name)paramGeneralNameInterface).getName().toLowerCase(Locale.ENGLISH);
/* 193 */       String str2 = this.name.toLowerCase(Locale.ENGLISH);
/* 194 */       if (str1.equals(str2)) {
/* 195 */         b = 0;
/* 196 */       } else if (str2.endsWith(str1)) {
/*     */         
/* 198 */         if (str1.indexOf('@') != -1) {
/* 199 */           b = 3;
/* 200 */         } else if (str1.startsWith(".")) {
/* 201 */           b = 2;
/*     */         } else {
/* 203 */           int i = str2.lastIndexOf(str1);
/* 204 */           if (str2.charAt(i - 1) == '@') {
/* 205 */             b = 2;
/*     */           } else {
/* 207 */             b = 3;
/*     */           } 
/*     */         } 
/* 210 */       } else if (str1.endsWith(str2)) {
/*     */         
/* 212 */         if (str2.indexOf('@') != -1) {
/* 213 */           b = 3;
/* 214 */         } else if (str2.startsWith(".")) {
/* 215 */           b = 1;
/*     */         } else {
/* 217 */           int i = str1.lastIndexOf(str2);
/* 218 */           if (str1.charAt(i - 1) == '@') {
/* 219 */             b = 1;
/*     */           } else {
/* 221 */             b = 3;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 225 */         b = 3;
/*     */       } 
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
/*     */   public int subtreeDepth() throws UnsupportedOperationException {
/* 239 */     String str = this.name;
/* 240 */     byte b = 1;
/*     */ 
/*     */     
/* 243 */     int i = str.lastIndexOf('@');
/* 244 */     if (i >= 0) {
/* 245 */       b++;
/* 246 */       str = str.substring(i + 1);
/*     */     } 
/*     */ 
/*     */     
/* 250 */     for (; str.lastIndexOf('.') >= 0; b++) {
/* 251 */       str = str.substring(0, str.lastIndexOf('.'));
/*     */     }
/*     */     
/* 254 */     return b;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/RFC822Name.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */