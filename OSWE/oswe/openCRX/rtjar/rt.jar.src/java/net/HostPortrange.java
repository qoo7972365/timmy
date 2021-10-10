/*     */ package java.net;
/*     */ 
/*     */ import java.util.Formatter;
/*     */ import java.util.Locale;
/*     */ import sun.net.util.IPAddressUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HostPortrange
/*     */ {
/*     */   String hostname;
/*     */   String scheme;
/*     */   int[] portrange;
/*     */   boolean wildcard;
/*     */   boolean literal;
/*     */   boolean ipv6;
/*     */   boolean ipv4;
/*     */   static final int PORT_MIN = 0;
/*     */   static final int PORT_MAX = 65535;
/*     */   static final int CASE_DIFF = -32;
/*     */   
/*     */   boolean equals(HostPortrange paramHostPortrange) {
/*  49 */     return (this.hostname.equals(paramHostPortrange.hostname) && this.portrange[0] == paramHostPortrange.portrange[0] && this.portrange[1] == paramHostPortrange.portrange[1] && this.wildcard == paramHostPortrange.wildcard && this.literal == paramHostPortrange.literal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  57 */     return this.hostname.hashCode() + this.portrange[0] + this.portrange[1];
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
/*     */   HostPortrange(String paramString1, String paramString2) {
/*  71 */     String str = null;
/*  72 */     this.scheme = paramString1;
/*     */ 
/*     */     
/*  75 */     if (paramString2.charAt(0) == '[') {
/*  76 */       String str1; this.ipv6 = this.literal = true;
/*  77 */       int i = paramString2.indexOf(']');
/*  78 */       if (i != -1) {
/*  79 */         str1 = paramString2.substring(1, i);
/*     */       } else {
/*  81 */         throw new IllegalArgumentException("invalid IPv6 address: " + paramString2);
/*     */       } 
/*  83 */       int j = paramString2.indexOf(':', i + 1);
/*  84 */       if (j != -1 && paramString2.length() > j) {
/*  85 */         str = paramString2.substring(j + 1);
/*     */       }
/*     */       
/*  88 */       byte[] arrayOfByte = IPAddressUtil.textToNumericFormatV6(str1);
/*  89 */       if (arrayOfByte == null) {
/*  90 */         throw new IllegalArgumentException("illegal IPv6 address");
/*     */       }
/*  92 */       StringBuilder stringBuilder = new StringBuilder();
/*  93 */       Formatter formatter = new Formatter(stringBuilder, Locale.US);
/*  94 */       formatter.format("%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x", new Object[] {
/*     */             
/*  96 */             Byte.valueOf(arrayOfByte[0]), Byte.valueOf(arrayOfByte[1]), Byte.valueOf(arrayOfByte[2]), Byte.valueOf(arrayOfByte[3]), Byte.valueOf(arrayOfByte[4]), Byte.valueOf(arrayOfByte[5]), Byte.valueOf(arrayOfByte[6]), Byte.valueOf(arrayOfByte[7]), Byte.valueOf(arrayOfByte[8]), 
/*  97 */             Byte.valueOf(arrayOfByte[9]), Byte.valueOf(arrayOfByte[10]), Byte.valueOf(arrayOfByte[11]), Byte.valueOf(arrayOfByte[12]), Byte.valueOf(arrayOfByte[13]), Byte.valueOf(arrayOfByte[14]), Byte.valueOf(arrayOfByte[15]) });
/*  98 */       this.hostname = stringBuilder.toString();
/*     */     } else {
/*     */       String str1;
/*     */       
/* 102 */       int i = paramString2.indexOf(':');
/* 103 */       if (i != -1 && paramString2.length() > i) {
/* 104 */         str1 = paramString2.substring(0, i);
/* 105 */         str = paramString2.substring(i + 1);
/*     */       } else {
/* 107 */         str1 = (i == -1) ? paramString2 : paramString2.substring(0, i);
/*     */       } 
/*     */       
/* 110 */       if (str1.lastIndexOf('*') > 0)
/* 111 */         throw new IllegalArgumentException("invalid host wildcard specification"); 
/* 112 */       if (str1.startsWith("*")) {
/* 113 */         this.wildcard = true;
/* 114 */         if (str1.equals("*")) {
/* 115 */           str1 = "";
/* 116 */         } else if (str1.startsWith("*.")) {
/* 117 */           str1 = toLowerCase(str1.substring(1));
/*     */         } else {
/* 119 */           throw new IllegalArgumentException("invalid host wildcard specification");
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 127 */         int j = str1.lastIndexOf('.');
/* 128 */         if (j != -1 && str1.length() > 1) {
/* 129 */           boolean bool = true;
/*     */           
/* 131 */           for (int k = j + 1, m = str1.length(); k < m; k++) {
/* 132 */             char c = str1.charAt(k);
/* 133 */             if (c < '0' || c > '9') {
/* 134 */               bool = false;
/*     */               break;
/*     */             } 
/*     */           } 
/* 138 */           this.ipv4 = this.literal = bool;
/* 139 */           if (bool) {
/* 140 */             byte[] arrayOfByte = IPAddressUtil.textToNumericFormatV4(str1);
/* 141 */             if (arrayOfByte == null) {
/* 142 */               throw new IllegalArgumentException("illegal IPv4 address");
/*     */             }
/* 144 */             StringBuilder stringBuilder = new StringBuilder();
/* 145 */             Formatter formatter = new Formatter(stringBuilder, Locale.US);
/* 146 */             formatter.format("%d.%d.%d.%d", new Object[] { Byte.valueOf(arrayOfByte[0]), Byte.valueOf(arrayOfByte[1]), Byte.valueOf(arrayOfByte[2]), Byte.valueOf(arrayOfByte[3]) });
/* 147 */             str1 = stringBuilder.toString();
/*     */           } else {
/*     */             
/* 150 */             str1 = toLowerCase(str1);
/*     */           } 
/*     */         } 
/*     */       } 
/* 154 */       this.hostname = str1;
/*     */     } 
/*     */     
/*     */     try {
/* 158 */       this.portrange = parsePort(str);
/* 159 */     } catch (Exception exception) {
/* 160 */       throw new IllegalArgumentException("invalid port range: " + str);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String toLowerCase(String paramString) {
/* 171 */     int i = paramString.length();
/* 172 */     StringBuilder stringBuilder = null;
/*     */     
/* 174 */     for (byte b = 0; b < i; b++) {
/* 175 */       char c = paramString.charAt(b);
/* 176 */       if ((c >= 'a' && c <= 'z') || c == '.') {
/* 177 */         if (stringBuilder != null)
/* 178 */           stringBuilder.append(c); 
/* 179 */       } else if ((c >= '0' && c <= '9') || c == '-') {
/* 180 */         if (stringBuilder != null)
/* 181 */           stringBuilder.append(c); 
/* 182 */       } else if (c >= 'A' && c <= 'Z') {
/* 183 */         if (stringBuilder == null) {
/* 184 */           stringBuilder = new StringBuilder(i);
/* 185 */           stringBuilder.append(paramString, 0, b);
/*     */         } 
/* 187 */         stringBuilder.append((char)(c - -32));
/*     */       } else {
/* 189 */         throw new IllegalArgumentException("Invalid characters in hostname");
/*     */       } 
/*     */     } 
/* 192 */     return (stringBuilder == null) ? paramString : stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean literal() {
/* 197 */     return this.literal;
/*     */   }
/*     */   
/*     */   public boolean ipv4Literal() {
/* 201 */     return this.ipv4;
/*     */   }
/*     */   
/*     */   public boolean ipv6Literal() {
/* 205 */     return this.ipv6;
/*     */   }
/*     */   
/*     */   public String hostname() {
/* 209 */     return this.hostname;
/*     */   }
/*     */   
/*     */   public int[] portrange() {
/* 213 */     return this.portrange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wildcard() {
/* 224 */     return this.wildcard;
/*     */   }
/*     */ 
/*     */   
/* 228 */   static final int[] HTTP_PORT = new int[] { 80, 80 };
/* 229 */   static final int[] HTTPS_PORT = new int[] { 443, 443 };
/* 230 */   static final int[] NO_PORT = new int[] { -1, -1 };
/*     */   
/*     */   int[] defaultPort() {
/* 233 */     if (this.scheme.equals("http"))
/* 234 */       return HTTP_PORT; 
/* 235 */     if (this.scheme.equals("https")) {
/* 236 */       return HTTPS_PORT;
/*     */     }
/* 238 */     return NO_PORT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int[] parsePort(String paramString) {
/* 244 */     if (paramString == null || paramString.equals("")) {
/* 245 */       return defaultPort();
/*     */     }
/*     */     
/* 248 */     if (paramString.equals("*")) {
/* 249 */       return new int[] { 0, 65535 };
/*     */     }
/*     */     
/*     */     try {
/* 253 */       int j, k, i = paramString.indexOf('-');
/*     */       
/* 255 */       if (i == -1) {
/* 256 */         int m = Integer.parseInt(paramString);
/* 257 */         return new int[] { m, m };
/*     */       } 
/* 259 */       String str1 = paramString.substring(0, i);
/* 260 */       String str2 = paramString.substring(i + 1);
/*     */ 
/*     */       
/* 263 */       if (str1.equals("")) {
/* 264 */         j = 0;
/*     */       } else {
/* 266 */         j = Integer.parseInt(str1);
/*     */       } 
/*     */       
/* 269 */       if (str2.equals("")) {
/* 270 */         k = 65535;
/*     */       } else {
/* 272 */         k = Integer.parseInt(str2);
/*     */       } 
/* 274 */       if (j < 0 || k < 0 || k < j) {
/* 275 */         return defaultPort();
/*     */       }
/* 277 */       return new int[] { j, k };
/*     */     }
/* 279 */     catch (IllegalArgumentException illegalArgumentException) {
/* 280 */       return defaultPort();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/HostPortrange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */