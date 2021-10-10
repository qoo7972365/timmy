/*     */ package sun.net;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URLCanonicalizer
/*     */ {
/*     */   public String canonicalize(String paramString) {
/*  58 */     String str = paramString;
/*  59 */     if (paramString.startsWith("ftp.")) {
/*  60 */       str = "ftp://" + paramString;
/*  61 */     } else if (paramString.startsWith("gopher.")) {
/*  62 */       str = "gopher://" + paramString;
/*  63 */     } else if (paramString.startsWith("/")) {
/*  64 */       str = "file:" + paramString;
/*  65 */     } else if (!hasProtocolName(paramString)) {
/*  66 */       if (isSimpleHostName(paramString)) {
/*  67 */         paramString = "www." + paramString + ".com";
/*     */       }
/*  69 */       str = "http://" + paramString;
/*     */     } 
/*     */     
/*  72 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasProtocolName(String paramString) {
/*  80 */     int i = paramString.indexOf(':');
/*  81 */     if (i <= 0) {
/*  82 */       return false;
/*     */     }
/*     */     
/*  85 */     for (byte b = 0; b < i; ) {
/*  86 */       char c = paramString.charAt(b);
/*     */ 
/*     */ 
/*     */       
/*  90 */       if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '-') {
/*     */         b++;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  97 */       return false;
/*     */     } 
/*     */     
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSimpleHostName(String paramString) {
/* 109 */     for (byte b = 0; b < paramString.length(); ) {
/* 110 */       char c = paramString.charAt(b);
/*     */ 
/*     */ 
/*     */       
/* 114 */       if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '-') {
/*     */         b++;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 122 */       return false;
/*     */     } 
/*     */     
/* 125 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/URLCanonicalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */