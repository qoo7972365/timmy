/*     */ package sun.net.www.protocol.file;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.FileNameMap;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.Permission;
/*     */ import java.text.Collator;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import sun.net.ProgressMonitor;
/*     */ import sun.net.ProgressSource;
/*     */ import sun.net.www.MessageHeader;
/*     */ import sun.net.www.MeteredStream;
/*     */ import sun.net.www.ParseUtil;
/*     */ import sun.net.www.URLConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileURLConnection
/*     */   extends URLConnection
/*     */ {
/*  50 */   static String CONTENT_LENGTH = "content-length";
/*  51 */   static String CONTENT_TYPE = "content-type";
/*  52 */   static String TEXT_PLAIN = "text/plain";
/*  53 */   static String LAST_MODIFIED = "last-modified";
/*     */   
/*     */   String contentType;
/*     */   
/*     */   InputStream is;
/*     */   
/*     */   File file;
/*     */   String filename;
/*     */   boolean isDirectory = false;
/*     */   boolean exists = false;
/*     */   List<String> files;
/*  64 */   long length = -1L;
/*  65 */   long lastModified = 0L; private boolean initializedHeaders;
/*     */   Permission permission;
/*     */   
/*  68 */   protected FileURLConnection(URL paramURL, File paramFile) { super(paramURL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.initializedHeaders = false;
/*     */     this.file = paramFile; }
/*     */   
/*     */   private void initializeHeaders() { try {
/* 110 */       connect();
/* 111 */       this.exists = this.file.exists();
/* 112 */     } catch (IOException iOException) {}
/*     */     
/* 114 */     if (!this.initializedHeaders || !this.exists)
/* 115 */     { this.length = this.file.length();
/* 116 */       this.lastModified = this.file.lastModified();
/*     */       
/* 118 */       if (!this.isDirectory) {
/* 119 */         FileNameMap fileNameMap = URLConnection.getFileNameMap();
/* 120 */         this.contentType = fileNameMap.getContentTypeFor(this.filename);
/* 121 */         if (this.contentType != null) {
/* 122 */           this.properties.add(CONTENT_TYPE, this.contentType);
/*     */         }
/* 124 */         this.properties.add(CONTENT_LENGTH, String.valueOf(this.length));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 131 */         if (this.lastModified != 0L) {
/* 132 */           Date date = new Date(this.lastModified);
/* 133 */           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
/*     */           
/* 135 */           simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
/* 136 */           this.properties.add(LAST_MODIFIED, simpleDateFormat.format(date));
/*     */         } 
/*     */       } else {
/* 139 */         this.properties.add(CONTENT_TYPE, TEXT_PLAIN);
/*     */       } 
/* 141 */       this.initializedHeaders = true; }  }
/*     */   public void connect() throws IOException { if (!this.connected) { try { this.filename = this.file.toString(); this.isDirectory = this.file.isDirectory(); if (this.isDirectory) { String[] arrayOfString = this.file.list(); if (arrayOfString == null)
/*     */             throw new FileNotFoundException(this.filename + " exists, but is not accessible");  this.files = Arrays.asList(arrayOfString); } else { this.is = new BufferedInputStream(new FileInputStream(this.filename)); boolean bool = ProgressMonitor.getDefault().shouldMeterInput(this.url, "GET"); if (bool) { ProgressSource progressSource = new ProgressSource(this.url, "GET", this.file.length()); this.is = new MeteredStream(this.is, progressSource, this.file.length()); }  }  }
/*     */       catch (IOException iOException) { throw iOException; }
/*     */        this.connected = true; }
/* 146 */      } public String getHeaderField(String paramString) { initializeHeaders();
/* 147 */     return super.getHeaderField(paramString); }
/*     */ 
/*     */   
/*     */   public String getHeaderField(int paramInt) {
/* 151 */     initializeHeaders();
/* 152 */     return super.getHeaderField(paramInt);
/*     */   }
/*     */   
/*     */   public int getContentLength() {
/* 156 */     initializeHeaders();
/* 157 */     if (this.length > 2147483647L)
/* 158 */       return -1; 
/* 159 */     return (int)this.length;
/*     */   }
/*     */   
/*     */   public long getContentLengthLong() {
/* 163 */     initializeHeaders();
/* 164 */     return this.length;
/*     */   }
/*     */   
/*     */   public String getHeaderFieldKey(int paramInt) {
/* 168 */     initializeHeaders();
/* 169 */     return super.getHeaderFieldKey(paramInt);
/*     */   }
/*     */   
/*     */   public MessageHeader getProperties() {
/* 173 */     initializeHeaders();
/* 174 */     return super.getProperties();
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 178 */     initializeHeaders();
/* 179 */     return this.lastModified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized InputStream getInputStream() throws IOException {
/* 188 */     connect();
/*     */     
/* 190 */     if (this.is == null) {
/* 191 */       if (this.isDirectory) {
/* 192 */         FileNameMap fileNameMap = URLConnection.getFileNameMap();
/*     */         
/* 194 */         StringBuffer stringBuffer = new StringBuffer();
/*     */         
/* 196 */         if (this.files == null) {
/* 197 */           throw new FileNotFoundException(this.filename);
/*     */         }
/*     */         
/* 200 */         Collections.sort(this.files, Collator.getInstance());
/*     */         
/* 202 */         for (byte b = 0; b < this.files.size(); b++) {
/* 203 */           String str = this.files.get(b);
/* 204 */           stringBuffer.append(str);
/* 205 */           stringBuffer.append("\n");
/*     */         } 
/*     */         
/* 208 */         this.is = new ByteArrayInputStream(stringBuffer.toString().getBytes());
/*     */       } else {
/* 210 */         throw new FileNotFoundException(this.filename);
/*     */       } 
/*     */     }
/* 213 */     return this.is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Permission getPermission() throws IOException {
/* 222 */     if (this.permission == null) {
/* 223 */       String str = ParseUtil.decode(this.url.getPath());
/* 224 */       if (File.separatorChar == '/') {
/* 225 */         this.permission = new FilePermission(str, "read");
/*     */       } else {
/* 227 */         this
/* 228 */           .permission = new FilePermission(str.replace('/', File.separatorChar), "read");
/*     */       } 
/*     */     } 
/* 231 */     return this.permission;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/file/FileURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */