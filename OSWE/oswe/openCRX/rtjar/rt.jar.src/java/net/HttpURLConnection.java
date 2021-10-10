/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.Permission;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HttpURLConnection
/*     */   extends URLConnection
/*     */ {
/*  75 */   protected String method = "GET";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected int chunkLength = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   protected int fixedContentLength = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   protected long fixedContentLengthLong = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_CHUNK_SIZE = 4096;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderFieldKey(int paramInt) {
/* 117 */     return null;
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
/*     */   public void setFixedLengthStreamingMode(int paramInt) {
/* 154 */     if (this.connected) {
/* 155 */       throw new IllegalStateException("Already connected");
/*     */     }
/* 157 */     if (this.chunkLength != -1) {
/* 158 */       throw new IllegalStateException("Chunked encoding streaming mode set");
/*     */     }
/* 160 */     if (paramInt < 0) {
/* 161 */       throw new IllegalArgumentException("invalid content length");
/*     */     }
/* 163 */     this.fixedContentLength = paramInt;
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
/*     */   public void setFixedLengthStreamingMode(long paramLong) {
/* 199 */     if (this.connected) {
/* 200 */       throw new IllegalStateException("Already connected");
/*     */     }
/* 202 */     if (this.chunkLength != -1) {
/* 203 */       throw new IllegalStateException("Chunked encoding streaming mode set");
/*     */     }
/*     */     
/* 206 */     if (paramLong < 0L) {
/* 207 */       throw new IllegalArgumentException("invalid content length");
/*     */     }
/* 209 */     this.fixedContentLengthLong = paramLong;
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
/*     */   public void setChunkedStreamingMode(int paramInt) {
/* 244 */     if (this.connected) {
/* 245 */       throw new IllegalStateException("Can't set streaming mode: already connected");
/*     */     }
/* 247 */     if (this.fixedContentLength != -1 || this.fixedContentLengthLong != -1L) {
/* 248 */       throw new IllegalStateException("Fixed length streaming mode set");
/*     */     }
/* 250 */     this.chunkLength = (paramInt <= 0) ? 4096 : paramInt;
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
/*     */   public String getHeaderField(int paramInt) {
/* 269 */     return null;
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
/* 282 */   protected int responseCode = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 287 */   protected String responseMessage = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean followRedirects = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int HTTP_OK = 200;
/*     */ 
/*     */   
/*     */   public static final int HTTP_CREATED = 201;
/*     */ 
/*     */   
/*     */   public static final int HTTP_ACCEPTED = 202;
/*     */ 
/*     */   
/*     */   public static final int HTTP_NOT_AUTHORITATIVE = 203;
/*     */ 
/*     */   
/*     */   public static final int HTTP_NO_CONTENT = 204;
/*     */ 
/*     */   
/* 310 */   protected boolean instanceFollowRedirects = followRedirects; public static final int HTTP_RESET = 205; public static final int HTTP_PARTIAL = 206;
/*     */   public static final int HTTP_MULT_CHOICE = 300;
/*     */   public static final int HTTP_MOVED_PERM = 301;
/* 313 */   private static final String[] methods = new String[] { "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE" };
/*     */   
/*     */   public static final int HTTP_MOVED_TEMP = 302;
/*     */   
/*     */   public static final int HTTP_SEE_OTHER = 303;
/*     */   public static final int HTTP_NOT_MODIFIED = 304;
/*     */   public static final int HTTP_USE_PROXY = 305;
/*     */   
/*     */   protected HttpURLConnection(URL paramURL) {
/* 322 */     super(paramURL);
/*     */   }
/*     */   public static final int HTTP_BAD_REQUEST = 400; public static final int HTTP_UNAUTHORIZED = 401; public static final int HTTP_PAYMENT_REQUIRED = 402; public static final int HTTP_FORBIDDEN = 403; public static final int HTTP_NOT_FOUND = 404; public static final int HTTP_BAD_METHOD = 405; public static final int HTTP_NOT_ACCEPTABLE = 406;
/*     */   public static final int HTTP_PROXY_AUTH = 407;
/*     */   public static final int HTTP_CLIENT_TIMEOUT = 408;
/*     */   public static final int HTTP_CONFLICT = 409;
/*     */   public static final int HTTP_GONE = 410;
/*     */   public static final int HTTP_LENGTH_REQUIRED = 411;
/*     */   public static final int HTTP_PRECON_FAILED = 412;
/*     */   public static final int HTTP_ENTITY_TOO_LARGE = 413;
/*     */   public static final int HTTP_REQ_TOO_LONG = 414;
/*     */   public static final int HTTP_UNSUPPORTED_TYPE = 415;
/*     */   @Deprecated
/*     */   public static final int HTTP_SERVER_ERROR = 500;
/*     */   public static final int HTTP_INTERNAL_ERROR = 500;
/*     */   public static final int HTTP_NOT_IMPLEMENTED = 501;
/*     */   public static final int HTTP_BAD_GATEWAY = 502;
/*     */   public static final int HTTP_UNAVAILABLE = 503;
/*     */   public static final int HTTP_GATEWAY_TIMEOUT = 504;
/*     */   public static final int HTTP_VERSION = 505;
/*     */   
/*     */   public static void setFollowRedirects(boolean paramBoolean) {
/* 344 */     SecurityManager securityManager = System.getSecurityManager();
/* 345 */     if (securityManager != null)
/*     */     {
/* 347 */       securityManager.checkSetFactory();
/*     */     }
/* 349 */     followRedirects = paramBoolean;
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
/*     */   public static boolean getFollowRedirects() {
/* 362 */     return followRedirects;
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
/*     */   public void setInstanceFollowRedirects(boolean paramBoolean) {
/* 381 */     this.instanceFollowRedirects = paramBoolean;
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
/*     */   public boolean getInstanceFollowRedirects() {
/* 395 */     return this.instanceFollowRedirects;
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
/*     */   public void setRequestMethod(String paramString) throws ProtocolException {
/* 420 */     if (this.connected) {
/* 421 */       throw new ProtocolException("Can't reset method: already connected");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 428 */     for (byte b = 0; b < methods.length; b++) {
/* 429 */       if (methods[b].equals(paramString)) {
/* 430 */         if (paramString.equals("TRACE")) {
/* 431 */           SecurityManager securityManager = System.getSecurityManager();
/* 432 */           if (securityManager != null) {
/* 433 */             securityManager.checkPermission(new NetPermission("allowHttpTrace"));
/*     */           }
/*     */         } 
/* 436 */         this.method = paramString;
/*     */         return;
/*     */       } 
/*     */     } 
/* 440 */     throw new ProtocolException("Invalid HTTP method: " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRequestMethod() {
/* 449 */     return this.method;
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
/*     */   public int getResponseCode() throws IOException {
/* 469 */     if (this.responseCode != -1) {
/* 470 */       return this.responseCode;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 478 */     Exception exception = null;
/*     */     try {
/* 480 */       getInputStream();
/* 481 */     } catch (Exception exception1) {
/* 482 */       exception = exception1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     String str = getHeaderField(0);
/* 490 */     if (str == null) {
/* 491 */       if (exception != null) {
/* 492 */         if (exception instanceof RuntimeException) {
/* 493 */           throw (RuntimeException)exception;
/*     */         }
/* 495 */         throw (IOException)exception;
/*     */       } 
/* 497 */       return -1;
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
/* 508 */     if (str.startsWith("HTTP/1.")) {
/* 509 */       int i = str.indexOf(' ');
/* 510 */       if (i > 0) {
/*     */         
/* 512 */         int j = str.indexOf(' ', i + 1);
/* 513 */         if (j > 0 && j < str.length()) {
/* 514 */           this.responseMessage = str.substring(j + 1);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 519 */         if (j < 0) {
/* 520 */           j = str.length();
/*     */         }
/*     */         try {
/* 523 */           this
/* 524 */             .responseCode = Integer.parseInt(str.substring(i + 1, j));
/* 525 */           return this.responseCode;
/* 526 */         } catch (NumberFormatException numberFormatException) {}
/*     */       } 
/*     */     } 
/* 529 */     return -1;
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
/*     */   public String getResponseMessage() throws IOException {
/* 546 */     getResponseCode();
/* 547 */     return this.responseMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getHeaderFieldDate(String paramString, long paramLong) {
/* 552 */     String str = getHeaderField(paramString);
/*     */     try {
/* 554 */       if (str.indexOf("GMT") == -1) {
/* 555 */         str = str + " GMT";
/*     */       }
/* 557 */       return Date.parse(str);
/* 558 */     } catch (Exception exception) {
/*     */       
/* 560 */       return paramLong;
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
/*     */   
/*     */   public abstract void disconnect();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean usingProxy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Permission getPermission() throws IOException {
/* 591 */     int i = this.url.getPort();
/* 592 */     i = (i < 0) ? 80 : i;
/* 593 */     String str = this.url.getHost() + ":" + i;
/* 594 */     return new SocketPermission(str, "connect");
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
/*     */   public InputStream getErrorStream() {
/* 617 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/HttpURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */