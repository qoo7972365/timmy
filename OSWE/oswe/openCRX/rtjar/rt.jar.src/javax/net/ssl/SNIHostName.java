/*     */ package javax.net.ssl;
/*     */ 
/*     */ import java.net.IDN;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SNIHostName
/*     */   extends SNIServerName
/*     */ {
/*     */   private final String hostname;
/*     */   
/*     */   public SNIHostName(String paramString) {
/*  98 */     super(0, (
/*  99 */         paramString = IDN.toASCII(
/* 100 */           Objects.requireNonNull(paramString, "Server name value of host_name cannot be null"), 2))
/*     */ 
/*     */         
/* 103 */         .getBytes(StandardCharsets.US_ASCII));
/*     */     
/* 105 */     this.hostname = paramString;
/*     */ 
/*     */     
/* 108 */     checkHostName();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SNIHostName(byte[] paramArrayOfbyte) {
/* 164 */     super(0, paramArrayOfbyte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 173 */       CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
/*     */       
/* 175 */       this.hostname = IDN.toASCII(charsetDecoder
/* 176 */           .decode(ByteBuffer.wrap(paramArrayOfbyte)).toString());
/* 177 */     } catch (RuntimeException|java.nio.charset.CharacterCodingException runtimeException) {
/* 178 */       throw new IllegalArgumentException("The encoded server name value is invalid", runtimeException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 183 */     checkHostName();
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
/*     */   public String getAsciiName() {
/* 201 */     return this.hostname;
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
/*     */   public boolean equals(Object paramObject) {
/* 219 */     if (this == paramObject) {
/* 220 */       return true;
/*     */     }
/*     */     
/* 223 */     if (paramObject instanceof SNIHostName) {
/* 224 */       return this.hostname.equalsIgnoreCase(((SNIHostName)paramObject).hostname);
/*     */     }
/*     */     
/* 227 */     return false;
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
/*     */   public int hashCode() {
/* 240 */     int i = 17;
/* 241 */     i = 31 * i + this.hostname.toUpperCase(Locale.ENGLISH).hashCode();
/*     */     
/* 243 */     return i;
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
/*     */   public String toString() {
/* 273 */     return "type=host_name (0), value=" + this.hostname;
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
/*     */   public static SNIMatcher createSNIMatcher(String paramString) {
/* 303 */     if (paramString == null) {
/* 304 */       throw new NullPointerException("The regular expression cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 308 */     return new SNIHostNameMatcher(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkHostName() {
/* 313 */     if (this.hostname.isEmpty()) {
/* 314 */       throw new IllegalArgumentException("Server name value of host_name cannot be empty");
/*     */     }
/*     */ 
/*     */     
/* 318 */     if (this.hostname.endsWith(".")) {
/* 319 */       throw new IllegalArgumentException("Server name value of host_name cannot have the trailing dot");
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
/*     */   private static final class SNIHostNameMatcher
/*     */     extends SNIMatcher
/*     */   {
/*     */     private final Pattern pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SNIHostNameMatcher(String param1String) {
/* 342 */       super(0);
/* 343 */       this.pattern = Pattern.compile(param1String, 2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matches(SNIServerName param1SNIServerName) {
/*     */       SNIHostName sNIHostName;
/* 364 */       if (param1SNIServerName == null) {
/* 365 */         throw new NullPointerException("The SNIServerName argument cannot be null");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 370 */       if (!(param1SNIServerName instanceof SNIHostName)) {
/* 371 */         if (param1SNIServerName.getType() != 0) {
/* 372 */           throw new IllegalArgumentException("The server name type is not host_name");
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 377 */           sNIHostName = new SNIHostName(param1SNIServerName.getEncoded());
/* 378 */         } catch (NullPointerException|IllegalArgumentException nullPointerException) {
/* 379 */           return false;
/*     */         } 
/*     */       } else {
/* 382 */         sNIHostName = (SNIHostName)param1SNIServerName;
/*     */       } 
/*     */ 
/*     */       
/* 386 */       String str = sNIHostName.getAsciiName();
/* 387 */       if (this.pattern.matcher(str).matches()) {
/* 388 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 393 */       return this.pattern.matcher(IDN.toUnicode(str)).matches();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/SNIHostName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */