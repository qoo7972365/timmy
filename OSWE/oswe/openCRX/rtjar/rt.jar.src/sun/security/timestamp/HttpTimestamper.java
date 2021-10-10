/*     */ package sun.security.timestamp;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpTimestamper
/*     */   implements Timestamper
/*     */ {
/*     */   private static final int CONNECT_TIMEOUT = 15000;
/*     */   private static final String TS_QUERY_MIME_TYPE = "application/timestamp-query";
/*     */   private static final String TS_REPLY_MIME_TYPE = "application/timestamp-reply";
/*  62 */   private static final Debug debug = Debug.getInstance("ts");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private URI tsaURI = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpTimestamper(URI paramURI) {
/*  76 */     if (!paramURI.getScheme().equalsIgnoreCase("http") && 
/*  77 */       !paramURI.getScheme().equalsIgnoreCase("https")) {
/*  78 */       throw new IllegalArgumentException("TSA must be an HTTP or HTTPS URI");
/*     */     }
/*     */     
/*  81 */     this.tsaURI = paramURI;
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
/*     */   public TSResponse generateTimestamp(TSRequest paramTSRequest) throws IOException {
/*  95 */     HttpURLConnection httpURLConnection = (HttpURLConnection)this.tsaURI.toURL().openConnection();
/*  96 */     httpURLConnection.setDoOutput(true);
/*  97 */     httpURLConnection.setUseCaches(false);
/*  98 */     httpURLConnection.setRequestProperty("Content-Type", "application/timestamp-query");
/*  99 */     httpURLConnection.setRequestMethod("POST");
/*     */     
/* 101 */     httpURLConnection.setConnectTimeout(15000);
/*     */     
/* 103 */     if (debug != null) {
/*     */       
/* 105 */       Set<Map.Entry<String, List<String>>> set = httpURLConnection.getRequestProperties().entrySet();
/* 106 */       debug.println(httpURLConnection.getRequestMethod() + " " + this.tsaURI + " HTTP/1.1");
/*     */       
/* 108 */       for (Map.Entry<String, List<String>> entry : set) {
/* 109 */         debug.println("  " + entry);
/*     */       }
/* 111 */       debug.println();
/*     */     } 
/* 113 */     httpURLConnection.connect();
/*     */ 
/*     */     
/* 116 */     DataOutputStream dataOutputStream = null;
/*     */     try {
/* 118 */       dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
/* 119 */       byte[] arrayOfByte1 = paramTSRequest.encode();
/* 120 */       dataOutputStream.write(arrayOfByte1, 0, arrayOfByte1.length);
/* 121 */       dataOutputStream.flush();
/* 122 */       if (debug != null) {
/* 123 */         debug.println("sent timestamp query (length=" + arrayOfByte1.length + ")");
/*     */       }
/*     */     } finally {
/*     */       
/* 127 */       if (dataOutputStream != null) {
/* 128 */         dataOutputStream.close();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 133 */     BufferedInputStream bufferedInputStream = null;
/* 134 */     byte[] arrayOfByte = null;
/*     */     try {
/* 136 */       bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
/* 137 */       if (debug != null) {
/* 138 */         String str = httpURLConnection.getHeaderField(0);
/* 139 */         debug.println(str);
/* 140 */         byte b = 1;
/* 141 */         while ((str = httpURLConnection.getHeaderField(b)) != null) {
/* 142 */           String str1 = httpURLConnection.getHeaderFieldKey(b);
/* 143 */           debug.println("  " + ((str1 == null) ? "" : (str1 + ": ")) + str);
/*     */           
/* 145 */           b++;
/*     */         } 
/* 147 */         debug.println();
/*     */       } 
/* 149 */       verifyMimeType(httpURLConnection.getContentType());
/*     */       
/* 151 */       int i = httpURLConnection.getContentLength();
/* 152 */       arrayOfByte = IOUtils.readAllBytes(bufferedInputStream);
/* 153 */       if (i != -1 && arrayOfByte.length != i) {
/* 154 */         throw new EOFException("Expected:" + i + ", read:" + arrayOfByte.length);
/*     */       }
/*     */       
/* 157 */       if (debug != null) {
/* 158 */         debug.println("received timestamp response (length=" + arrayOfByte.length + ")");
/*     */       }
/*     */     } finally {
/*     */       
/* 162 */       if (bufferedInputStream != null) {
/* 163 */         bufferedInputStream.close();
/*     */       }
/*     */     } 
/* 166 */     return new TSResponse(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void verifyMimeType(String paramString) throws IOException {
/* 176 */     if (!"application/timestamp-reply".equalsIgnoreCase(paramString))
/* 177 */       throw new IOException("MIME Content-Type is not application/timestamp-reply"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/timestamp/HttpTimestamper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */