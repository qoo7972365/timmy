/*      */ package java.net;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permission;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import sun.net.www.MessageHeader;
/*      */ import sun.net.www.MimeTable;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class URLConnection
/*      */ {
/*      */   protected URL url;
/*      */   protected boolean doInput = true;
/*      */   protected boolean doOutput = false;
/*      */   private static boolean defaultAllowUserInteraction = false;
/*  226 */   protected boolean allowUserInteraction = defaultAllowUserInteraction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean defaultUseCaches = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  245 */   protected boolean useCaches = defaultUseCaches;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  265 */   protected long ifModifiedSince = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean connected = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int connectTimeout;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readTimeout;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MessageHeader requests;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FileNameMap fileNameMap;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean fileNameMapLoaded = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ContentHandlerFactory factory;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized FileNameMap getFileNameMap() {
/*  306 */     if (fileNameMap == null && !fileNameMapLoaded) {
/*  307 */       fileNameMap = MimeTable.loadTable();
/*  308 */       fileNameMapLoaded = true;
/*      */     } 
/*      */     
/*  311 */     return new FileNameMap() {
/*  312 */         private FileNameMap map = URLConnection.fileNameMap;
/*      */         public String getContentTypeFor(String param1String) {
/*  314 */           return this.map.getContentTypeFor(param1String);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFileNameMap(FileNameMap paramFileNameMap) {
/*  335 */     SecurityManager securityManager = System.getSecurityManager();
/*  336 */     if (securityManager != null) securityManager.checkSetFactory(); 
/*  337 */     fileNameMap = paramFileNameMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectTimeout(int paramInt) {
/*  387 */     if (paramInt < 0) {
/*  388 */       throw new IllegalArgumentException("timeout can not be negative");
/*      */     }
/*  390 */     this.connectTimeout = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getConnectTimeout() {
/*  406 */     return this.connectTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadTimeout(int paramInt) {
/*  430 */     if (paramInt < 0) {
/*  431 */       throw new IllegalArgumentException("timeout can not be negative");
/*      */     }
/*  433 */     this.readTimeout = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getReadTimeout() {
/*  448 */     return this.readTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected URLConnection(URL paramURL) {
/*  458 */     this.url = paramURL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL() {
/*  470 */     return this.url;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getContentLength() {
/*  485 */     long l = getContentLengthLong();
/*  486 */     if (l > 2147483647L)
/*  487 */       return -1; 
/*  488 */     return (int)l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getContentLengthLong() {
/*  501 */     return getHeaderFieldLong("content-length", -1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getContentType() {
/*  512 */     return getHeaderField("content-type");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getContentEncoding() {
/*  523 */     return getHeaderField("content-encoding");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getExpiration() {
/*  535 */     return getHeaderFieldDate("expires", 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getDate() {
/*  547 */     return getHeaderFieldDate("date", 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLastModified() {
/*  559 */     return getHeaderFieldDate("last-modified", 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHeaderField(String paramString) {
/*  574 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, List<String>> getHeaderFields() {
/*  588 */     return Collections.emptyMap();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeaderFieldInt(String paramString, int paramInt) {
/*  606 */     String str = getHeaderField(paramString);
/*      */     try {
/*  608 */       return Integer.parseInt(str);
/*  609 */     } catch (Exception exception) {
/*  610 */       return paramInt;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getHeaderFieldLong(String paramString, long paramLong) {
/*  629 */     String str = getHeaderField(paramString);
/*      */     try {
/*  631 */       return Long.parseLong(str);
/*  632 */     } catch (Exception exception) {
/*  633 */       return paramLong;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getHeaderFieldDate(String paramString, long paramLong) {
/*  654 */     String str = getHeaderField(paramString);
/*      */     try {
/*  656 */       return Date.parse(str);
/*  657 */     } catch (Exception exception) {
/*  658 */       return paramLong;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHeaderFieldKey(int paramInt) {
/*  671 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHeaderField(int paramInt) {
/*  689 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getContent() throws IOException {
/*  739 */     getInputStream();
/*  740 */     return getContentHandler().getContent(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getContent(Class[] paramArrayOfClass) throws IOException {
/*  767 */     getInputStream();
/*  768 */     return getContentHandler().getContent(this, paramArrayOfClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Permission getPermission() throws IOException {
/*  811 */     return SecurityConstants.ALL_PERMISSION;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getInputStream() throws IOException {
/*  830 */     throw new UnknownServiceException("protocol doesn't support input");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputStream getOutputStream() throws IOException {
/*  843 */     throw new UnknownServiceException("protocol doesn't support output");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  852 */     return getClass().getName() + ":" + this.url;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDoInput(boolean paramBoolean) {
/*  869 */     if (this.connected)
/*  870 */       throw new IllegalStateException("Already connected"); 
/*  871 */     this.doInput = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDoInput() {
/*  883 */     return this.doInput;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDoOutput(boolean paramBoolean) {
/*  899 */     if (this.connected)
/*  900 */       throw new IllegalStateException("Already connected"); 
/*  901 */     this.doOutput = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDoOutput() {
/*  913 */     return this.doOutput;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowUserInteraction(boolean paramBoolean) {
/*  925 */     if (this.connected)
/*  926 */       throw new IllegalStateException("Already connected"); 
/*  927 */     this.allowUserInteraction = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAllowUserInteraction() {
/*  939 */     return this.allowUserInteraction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDefaultAllowUserInteraction(boolean paramBoolean) {
/*  951 */     defaultAllowUserInteraction = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getDefaultAllowUserInteraction() {
/*  967 */     return defaultAllowUserInteraction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseCaches(boolean paramBoolean) {
/*  988 */     if (this.connected)
/*  989 */       throw new IllegalStateException("Already connected"); 
/*  990 */     this.useCaches = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseCaches() {
/* 1002 */     return this.useCaches;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIfModifiedSince(long paramLong) {
/* 1014 */     if (this.connected)
/* 1015 */       throw new IllegalStateException("Already connected"); 
/* 1016 */     this.ifModifiedSince = paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getIfModifiedSince() {
/* 1026 */     return this.ifModifiedSince;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDefaultUseCaches() {
/* 1042 */     return defaultUseCaches;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultUseCaches(boolean paramBoolean) {
/* 1053 */     defaultUseCaches = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRequestProperty(String paramString1, String paramString2) {
/* 1073 */     if (this.connected)
/* 1074 */       throw new IllegalStateException("Already connected"); 
/* 1075 */     if (paramString1 == null) {
/* 1076 */       throw new NullPointerException("key is null");
/*      */     }
/* 1078 */     if (this.requests == null) {
/* 1079 */       this.requests = new MessageHeader();
/*      */     }
/* 1081 */     this.requests.set(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRequestProperty(String paramString1, String paramString2) {
/* 1098 */     if (this.connected)
/* 1099 */       throw new IllegalStateException("Already connected"); 
/* 1100 */     if (paramString1 == null) {
/* 1101 */       throw new NullPointerException("key is null");
/*      */     }
/* 1103 */     if (this.requests == null) {
/* 1104 */       this.requests = new MessageHeader();
/*      */     }
/* 1106 */     this.requests.add(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRequestProperty(String paramString) {
/* 1121 */     if (this.connected) {
/* 1122 */       throw new IllegalStateException("Already connected");
/*      */     }
/* 1124 */     if (this.requests == null) {
/* 1125 */       return null;
/*      */     }
/* 1127 */     return this.requests.findValue(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, List<String>> getRequestProperties() {
/* 1143 */     if (this.connected) {
/* 1144 */       throw new IllegalStateException("Already connected");
/*      */     }
/* 1146 */     if (this.requests == null) {
/* 1147 */       return Collections.emptyMap();
/*      */     }
/* 1149 */     return this.requests.getHeaders(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void setDefaultRequestProperty(String paramString1, String paramString2) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String getDefaultRequestProperty(String paramString) {
/* 1191 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized void setContentHandlerFactory(ContentHandlerFactory paramContentHandlerFactory) {
/* 1220 */     if (factory != null) {
/* 1221 */       throw new Error("factory already defined");
/*      */     }
/* 1223 */     SecurityManager securityManager = System.getSecurityManager();
/* 1224 */     if (securityManager != null) {
/* 1225 */       securityManager.checkSetFactory();
/*      */     }
/* 1227 */     factory = paramContentHandlerFactory;
/*      */   }
/*      */   
/* 1230 */   private static Hashtable<String, ContentHandler> handlers = new Hashtable<>();
/*      */   
/*      */   private static final String contentClassPrefix = "sun.net.www.content";
/*      */   
/*      */   private static final String contentPathProp = "java.content.handler.pkgs";
/*      */ 
/*      */   
/*      */   synchronized ContentHandler getContentHandler() throws UnknownServiceException {
/* 1238 */     String str = stripOffParameters(getContentType());
/* 1239 */     ContentHandler contentHandler = null;
/* 1240 */     if (str == null)
/* 1241 */       throw new UnknownServiceException("no content-type"); 
/*      */     try {
/* 1243 */       contentHandler = handlers.get(str);
/* 1244 */       if (contentHandler != null)
/* 1245 */         return contentHandler; 
/* 1246 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1249 */     if (factory != null)
/* 1250 */       contentHandler = factory.createContentHandler(str); 
/* 1251 */     if (contentHandler == null) {
/*      */       try {
/* 1253 */         contentHandler = lookupContentHandlerClassFor(str);
/* 1254 */       } catch (Exception exception) {
/* 1255 */         exception.printStackTrace();
/* 1256 */         contentHandler = UnknownContentHandler.INSTANCE;
/*      */       } 
/* 1258 */       handlers.put(str, contentHandler);
/*      */     } 
/* 1260 */     return contentHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String stripOffParameters(String paramString) {
/* 1270 */     if (paramString == null)
/* 1271 */       return null; 
/* 1272 */     int i = paramString.indexOf(';');
/*      */     
/* 1274 */     if (i > 0) {
/* 1275 */       return paramString.substring(0, i);
/*      */     }
/* 1277 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ContentHandler lookupContentHandlerClassFor(String paramString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
/* 1297 */     String str1 = typeToPackageName(paramString);
/*      */     
/* 1299 */     String str2 = getContentHandlerPkgPrefixes();
/*      */     
/* 1301 */     StringTokenizer stringTokenizer = new StringTokenizer(str2, "|");
/*      */ 
/*      */     
/* 1304 */     while (stringTokenizer.hasMoreTokens()) {
/* 1305 */       String str = stringTokenizer.nextToken().trim();
/*      */       
/*      */       try {
/* 1308 */         String str3 = str + "." + str1;
/* 1309 */         Class<?> clazz = null;
/*      */         try {
/* 1311 */           clazz = Class.forName(str3);
/* 1312 */         } catch (ClassNotFoundException classNotFoundException) {
/* 1313 */           ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 1314 */           if (classLoader != null) {
/* 1315 */             clazz = classLoader.loadClass(str3);
/*      */           }
/*      */         } 
/* 1318 */         if (clazz != null) {
/* 1319 */           return (ContentHandler)clazz
/* 1320 */             .newInstance();
/*      */         }
/*      */       }
/* 1323 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/* 1327 */     return UnknownContentHandler.INSTANCE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String typeToPackageName(String paramString) {
/* 1337 */     paramString = paramString.toLowerCase();
/* 1338 */     int i = paramString.length();
/* 1339 */     char[] arrayOfChar = new char[i];
/* 1340 */     paramString.getChars(0, i, arrayOfChar, 0);
/* 1341 */     for (byte b = 0; b < i; b++) {
/* 1342 */       char c = arrayOfChar[b];
/* 1343 */       if (c == '/') {
/* 1344 */         arrayOfChar[b] = '.';
/* 1345 */       } else if (('A' > c || c > 'Z') && ('a' > c || c > 'z') && ('0' > c || c > '9')) {
/*      */ 
/*      */         
/* 1348 */         arrayOfChar[b] = '_';
/*      */       } 
/*      */     } 
/* 1351 */     return new String(arrayOfChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getContentHandlerPkgPrefixes() {
/* 1363 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.content.handler.pkgs", ""));
/*      */ 
/*      */     
/* 1366 */     if (str != "") {
/* 1367 */       str = str + "|";
/*      */     }
/*      */     
/* 1370 */     return str + "sun.net.www.content";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String guessContentTypeFromName(String paramString) {
/* 1385 */     return getFileNameMap().getContentTypeFor(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String guessContentTypeFromStream(InputStream paramInputStream) throws IOException {
/* 1412 */     if (!paramInputStream.markSupported()) {
/* 1413 */       return null;
/*      */     }
/* 1415 */     paramInputStream.mark(16);
/* 1416 */     int i = paramInputStream.read();
/* 1417 */     int j = paramInputStream.read();
/* 1418 */     int k = paramInputStream.read();
/* 1419 */     int m = paramInputStream.read();
/* 1420 */     int n = paramInputStream.read();
/* 1421 */     int i1 = paramInputStream.read();
/* 1422 */     int i2 = paramInputStream.read();
/* 1423 */     int i3 = paramInputStream.read();
/* 1424 */     int i4 = paramInputStream.read();
/* 1425 */     int i5 = paramInputStream.read();
/* 1426 */     int i6 = paramInputStream.read();
/* 1427 */     int i7 = paramInputStream.read();
/* 1428 */     int i8 = paramInputStream.read();
/* 1429 */     int i9 = paramInputStream.read();
/* 1430 */     int i10 = paramInputStream.read();
/* 1431 */     int i11 = paramInputStream.read();
/* 1432 */     paramInputStream.reset();
/*      */     
/* 1434 */     if (i == 202 && j == 254 && k == 186 && m == 190) {
/* 1435 */       return "application/java-vm";
/*      */     }
/*      */     
/* 1438 */     if (i == 172 && j == 237)
/*      */     {
/* 1440 */       return "application/x-java-serialized-object";
/*      */     }
/*      */     
/* 1443 */     if (i == 60) {
/* 1444 */       if (j == 33 || (j == 104 && ((k == 116 && m == 109 && n == 108) || (k == 101 && m == 97 && n == 100))) || (j == 98 && k == 111 && m == 100 && n == 121) || (j == 72 && ((k == 84 && m == 77 && n == 76) || (k == 69 && m == 65 && n == 68))) || (j == 66 && k == 79 && m == 68 && n == 89))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1451 */         return "text/html";
/*      */       }
/*      */       
/* 1454 */       if (j == 63 && k == 120 && m == 109 && n == 108 && i1 == 32) {
/* 1455 */         return "application/xml";
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1460 */     if (i == 239 && j == 187 && k == 191 && 
/* 1461 */       m == 60 && n == 63 && i1 == 120) {
/* 1462 */       return "application/xml";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1467 */     if (i == 254 && j == 255 && 
/* 1468 */       k == 0 && m == 60 && n == 0 && i1 == 63 && i2 == 0 && i3 == 120)
/*      */     {
/* 1470 */       return "application/xml";
/*      */     }
/*      */ 
/*      */     
/* 1474 */     if (i == 255 && j == 254 && 
/* 1475 */       k == 60 && m == 0 && n == 63 && i1 == 0 && i2 == 120 && i3 == 0)
/*      */     {
/* 1477 */       return "application/xml";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1482 */     if (i == 0 && j == 0 && k == 254 && m == 255 && 
/* 1483 */       n == 0 && i1 == 0 && i2 == 0 && i3 == 60 && i4 == 0 && i5 == 0 && i6 == 0 && i7 == 63 && i8 == 0 && i9 == 0 && i10 == 0 && i11 == 120)
/*      */     {
/*      */       
/* 1486 */       return "application/xml";
/*      */     }
/*      */ 
/*      */     
/* 1490 */     if (i == 255 && j == 254 && k == 0 && m == 0 && 
/* 1491 */       n == 60 && i1 == 0 && i2 == 0 && i3 == 0 && i4 == 63 && i5 == 0 && i6 == 0 && i7 == 0 && i8 == 120 && i9 == 0 && i10 == 0 && i11 == 0)
/*      */     {
/*      */       
/* 1494 */       return "application/xml";
/*      */     }
/*      */ 
/*      */     
/* 1498 */     if (i == 71 && j == 73 && k == 70 && m == 56) {
/* 1499 */       return "image/gif";
/*      */     }
/*      */     
/* 1502 */     if (i == 35 && j == 100 && k == 101 && m == 102) {
/* 1503 */       return "image/x-bitmap";
/*      */     }
/*      */     
/* 1506 */     if (i == 33 && j == 32 && k == 88 && m == 80 && n == 77 && i1 == 50)
/*      */     {
/* 1508 */       return "image/x-pixmap";
/*      */     }
/*      */     
/* 1511 */     if (i == 137 && j == 80 && k == 78 && m == 71 && n == 13 && i1 == 10 && i2 == 26 && i3 == 10)
/*      */     {
/*      */       
/* 1514 */       return "image/png";
/*      */     }
/*      */     
/* 1517 */     if (i == 255 && j == 216 && k == 255) {
/* 1518 */       if (m == 224 || m == 238) {
/* 1519 */         return "image/jpeg";
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1528 */       if (m == 225 && i2 == 69 && i3 == 120 && i4 == 105 && i5 == 102 && i6 == 0)
/*      */       {
/*      */         
/* 1531 */         return "image/jpeg";
/*      */       }
/*      */     } 
/*      */     
/* 1535 */     if (i == 208 && j == 207 && k == 17 && m == 224 && n == 161 && i1 == 177 && i2 == 26 && i3 == 225)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1542 */       if (checkfpx(paramInputStream)) {
/* 1543 */         return "image/vnd.fpx";
/*      */       }
/*      */     }
/*      */     
/* 1547 */     if (i == 46 && j == 115 && k == 110 && m == 100) {
/* 1548 */       return "audio/basic";
/*      */     }
/*      */     
/* 1551 */     if (i == 100 && j == 110 && k == 115 && m == 46) {
/* 1552 */       return "audio/basic";
/*      */     }
/*      */     
/* 1555 */     if (i == 82 && j == 73 && k == 70 && m == 70)
/*      */     {
/*      */ 
/*      */       
/* 1559 */       return "audio/x-wav";
/*      */     }
/* 1561 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean checkfpx(InputStream paramInputStream) throws IOException {
/*      */     int j, k;
/* 1600 */     paramInputStream.mark(256);
/*      */ 
/*      */ 
/*      */     
/* 1604 */     long l1 = 28L;
/*      */     
/*      */     long l2;
/* 1607 */     if ((l2 = skipForward(paramInputStream, l1)) < l1) {
/* 1608 */       paramInputStream.reset();
/* 1609 */       return false;
/*      */     } 
/*      */     
/* 1612 */     int[] arrayOfInt = new int[16];
/* 1613 */     if (readBytes(arrayOfInt, 2, paramInputStream) < 0) {
/* 1614 */       paramInputStream.reset();
/* 1615 */       return false;
/*      */     } 
/*      */     
/* 1618 */     int i = arrayOfInt[0];
/*      */     
/* 1620 */     l2 += 2L;
/*      */     
/* 1622 */     if (readBytes(arrayOfInt, 2, paramInputStream) < 0) {
/* 1623 */       paramInputStream.reset();
/* 1624 */       return false;
/*      */     } 
/*      */     
/* 1627 */     if (i == 254) {
/* 1628 */       j = arrayOfInt[0];
/* 1629 */       j += arrayOfInt[1] << 8;
/*      */     } else {
/*      */       
/* 1632 */       j = arrayOfInt[0] << 8;
/* 1633 */       j += arrayOfInt[1];
/*      */     } 
/*      */     
/* 1636 */     l2 += 2L;
/* 1637 */     l1 = 48L - l2;
/* 1638 */     long l3 = 0L;
/* 1639 */     if ((l3 = skipForward(paramInputStream, l1)) < l1) {
/* 1640 */       paramInputStream.reset();
/* 1641 */       return false;
/*      */     } 
/* 1643 */     l2 += l3;
/*      */     
/* 1645 */     if (readBytes(arrayOfInt, 4, paramInputStream) < 0) {
/* 1646 */       paramInputStream.reset();
/* 1647 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1651 */     if (i == 254) {
/* 1652 */       k = arrayOfInt[0];
/* 1653 */       k += arrayOfInt[1] << 8;
/* 1654 */       k += arrayOfInt[2] << 16;
/* 1655 */       k += arrayOfInt[3] << 24;
/*      */     } else {
/* 1657 */       k = arrayOfInt[0] << 24;
/* 1658 */       k += arrayOfInt[1] << 16;
/* 1659 */       k += arrayOfInt[2] << 8;
/* 1660 */       k += arrayOfInt[3];
/*      */     } 
/* 1662 */     l2 += 4L;
/* 1663 */     paramInputStream.reset();
/*      */     
/* 1665 */     l1 = 512L + (1 << j) * k + 80L;
/*      */ 
/*      */     
/* 1668 */     if (l1 < 0L) {
/* 1669 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1678 */     paramInputStream.mark((int)l1 + 48);
/*      */     
/* 1680 */     if (skipForward(paramInputStream, l1) < l1) {
/* 1681 */       paramInputStream.reset();
/* 1682 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1697 */     if (readBytes(arrayOfInt, 16, paramInputStream) < 0) {
/* 1698 */       paramInputStream.reset();
/* 1699 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1703 */     if (i == 254 && arrayOfInt[0] == 0 && arrayOfInt[2] == 97 && arrayOfInt[3] == 86 && arrayOfInt[4] == 84 && arrayOfInt[5] == 193 && arrayOfInt[6] == 206 && arrayOfInt[7] == 17 && arrayOfInt[8] == 133 && arrayOfInt[9] == 83 && arrayOfInt[10] == 0 && arrayOfInt[11] == 170 && arrayOfInt[12] == 0 && arrayOfInt[13] == 161 && arrayOfInt[14] == 249 && arrayOfInt[15] == 91) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1709 */       paramInputStream.reset();
/* 1710 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1714 */     if (arrayOfInt[3] == 0 && arrayOfInt[1] == 97 && arrayOfInt[0] == 86 && arrayOfInt[5] == 84 && arrayOfInt[4] == 193 && arrayOfInt[7] == 206 && arrayOfInt[6] == 17 && arrayOfInt[8] == 133 && arrayOfInt[9] == 83 && arrayOfInt[10] == 0 && arrayOfInt[11] == 170 && arrayOfInt[12] == 0 && arrayOfInt[13] == 161 && arrayOfInt[14] == 249 && arrayOfInt[15] == 91) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1719 */       paramInputStream.reset();
/* 1720 */       return true;
/*      */     } 
/* 1722 */     paramInputStream.reset();
/* 1723 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int readBytes(int[] paramArrayOfint, int paramInt, InputStream paramInputStream) throws IOException {
/* 1734 */     byte[] arrayOfByte = new byte[paramInt];
/* 1735 */     if (paramInputStream.read(arrayOfByte, 0, paramInt) < paramInt) {
/* 1736 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1740 */     for (byte b = 0; b < paramInt; b++) {
/* 1741 */       paramArrayOfint[b] = arrayOfByte[b] & 0xFF;
/*      */     }
/* 1743 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long skipForward(InputStream paramInputStream, long paramLong) throws IOException {
/* 1755 */     long l1 = 0L;
/* 1756 */     long l2 = 0L;
/*      */     
/* 1758 */     while (l2 != paramLong) {
/* 1759 */       l1 = paramInputStream.skip(paramLong - l2);
/*      */ 
/*      */       
/* 1762 */       if (l1 <= 0L) {
/* 1763 */         if (paramInputStream.read() == -1) {
/* 1764 */           return l2;
/*      */         }
/* 1766 */         l2++;
/*      */       } 
/*      */       
/* 1769 */       l2 += l1;
/*      */     } 
/* 1771 */     return l2;
/*      */   }
/*      */   
/*      */   public abstract void connect() throws IOException;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/URLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */