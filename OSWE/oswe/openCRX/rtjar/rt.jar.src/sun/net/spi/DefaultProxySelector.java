/*     */ package sun.net.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.net.ProxySelector;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.URI;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringJoiner;
/*     */ import java.util.regex.Pattern;
/*     */ import sun.net.NetProperties;
/*     */ import sun.net.SocksProxy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultProxySelector
/*     */   extends ProxySelector
/*     */ {
/*  75 */   static final String[][] props = new String[][] { { "http", "http.proxy", "proxy", "socksProxy" }, { "https", "https.proxy", "proxy", "socksProxy" }, { "ftp", "ftp.proxy", "ftpProxy", "proxy", "socksProxy" }, { "gopher", "gopherProxy", "socksProxy" }, { "socket", "socksProxy" } };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String SOCKS_PROXY_VERSION = "socksProxyVersion";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasSystemProxies = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  92 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run() {
/*  95 */             return NetProperties.getBoolean("java.net.useSystemProxies"); }
/*     */         });
/*  97 */     if (bool != null && bool.booleanValue()) {
/*  98 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */           {
/*     */             public Void run() {
/* 101 */               System.loadLibrary("net");
/* 102 */               return null;
/*     */             }
/*     */           });
/* 105 */       hasSystemProxies = init();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class NonProxyInfo
/*     */   {
/*     */     static final String defStringVal = "localhost|127.*|[::1]|0.0.0.0|[::0]";
/*     */ 
/*     */     
/*     */     String hostsSource;
/*     */ 
/*     */     
/*     */     Pattern pattern;
/*     */ 
/*     */     
/*     */     final String property;
/*     */     
/*     */     final String defaultVal;
/*     */     
/* 126 */     static NonProxyInfo ftpNonProxyInfo = new NonProxyInfo("ftp.nonProxyHosts", null, null, "localhost|127.*|[::1]|0.0.0.0|[::0]");
/* 127 */     static NonProxyInfo httpNonProxyInfo = new NonProxyInfo("http.nonProxyHosts", null, null, "localhost|127.*|[::1]|0.0.0.0|[::0]");
/* 128 */     static NonProxyInfo socksNonProxyInfo = new NonProxyInfo("socksNonProxyHosts", null, null, "localhost|127.*|[::1]|0.0.0.0|[::0]");
/*     */     
/*     */     NonProxyInfo(String param1String1, String param1String2, Pattern param1Pattern, String param1String3) {
/* 131 */       this.property = param1String1;
/* 132 */       this.hostsSource = param1String2;
/* 133 */       this.pattern = param1Pattern;
/* 134 */       this.defaultVal = param1String3;
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
/*     */   public List<Proxy> select(URI paramURI) {
/* 147 */     if (paramURI == null) {
/* 148 */       throw new IllegalArgumentException("URI can't be null.");
/*     */     }
/* 150 */     String str1 = paramURI.getScheme();
/* 151 */     String str2 = paramURI.getHost();
/*     */     
/* 153 */     if (str2 == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 161 */       String str = paramURI.getAuthority();
/* 162 */       if (str != null) {
/*     */         
/* 164 */         int i = str.indexOf('@');
/* 165 */         if (i >= 0) {
/* 166 */           str = str.substring(i + 1);
/*     */         }
/* 168 */         i = str.lastIndexOf(':');
/* 169 */         if (i >= 0) {
/* 170 */           str = str.substring(0, i);
/*     */         }
/* 172 */         str2 = str;
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     if (str1 == null || str2 == null) {
/* 177 */       throw new IllegalArgumentException("protocol = " + str1 + " host = " + str2);
/*     */     }
/* 179 */     ArrayList<Proxy> arrayList = new ArrayList(1);
/*     */     
/* 181 */     NonProxyInfo nonProxyInfo1 = null;
/*     */     
/* 183 */     if ("http".equalsIgnoreCase(str1)) {
/* 184 */       nonProxyInfo1 = NonProxyInfo.httpNonProxyInfo;
/* 185 */     } else if ("https".equalsIgnoreCase(str1)) {
/*     */ 
/*     */       
/* 188 */       nonProxyInfo1 = NonProxyInfo.httpNonProxyInfo;
/* 189 */     } else if ("ftp".equalsIgnoreCase(str1)) {
/* 190 */       nonProxyInfo1 = NonProxyInfo.ftpNonProxyInfo;
/* 191 */     } else if ("socket".equalsIgnoreCase(str1)) {
/* 192 */       nonProxyInfo1 = NonProxyInfo.socksNonProxyInfo;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     final String proto = str1;
/* 199 */     final NonProxyInfo nprop = nonProxyInfo1;
/* 200 */     final String urlhost = str2.toLowerCase();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     Proxy proxy = AccessController.<Proxy>doPrivileged(new PrivilegedAction<Proxy>()
/*     */         {
/*     */           public Proxy run()
/*     */           {
/* 212 */             String str1 = null;
/* 213 */             int i = 0;
/* 214 */             String str2 = null;
/* 215 */             InetSocketAddress inetSocketAddress = null;
/*     */ 
/*     */             
/* 218 */             for (byte b = 0; b < DefaultProxySelector.props.length; b++) {
/* 219 */               if (DefaultProxySelector.props[b][0].equalsIgnoreCase(proto)) {
/* 220 */                 byte b1; for (b1 = 1; b1 < (DefaultProxySelector.props[b]).length; b1++) {
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 225 */                   str1 = NetProperties.get(DefaultProxySelector.props[b][b1] + "Host");
/* 226 */                   if (str1 != null && str1.length() != 0)
/*     */                     break; 
/*     */                 } 
/* 229 */                 if (str1 == null || str1.length() == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 236 */                   if (DefaultProxySelector.hasSystemProxies) {
/*     */                     String str;
/* 238 */                     if (proto.equalsIgnoreCase("socket")) {
/* 239 */                       str = "socks";
/*     */                     } else {
/* 241 */                       str = proto;
/* 242 */                     }  Proxy proxy = DefaultProxySelector.this.getSystemProxy(str, urlhost);
/* 243 */                     if (proxy != null) {
/* 244 */                       return proxy;
/*     */                     }
/*     */                   } 
/* 247 */                   return Proxy.NO_PROXY;
/*     */                 } 
/*     */ 
/*     */                 
/* 251 */                 if (nprop != null) {
/* 252 */                   str2 = NetProperties.get(nprop.property);
/* 253 */                   synchronized (nprop) {
/* 254 */                     if (str2 == null) {
/* 255 */                       if (nprop.defaultVal != null) {
/* 256 */                         str2 = nprop.defaultVal;
/*     */                       } else {
/* 258 */                         nprop.hostsSource = null;
/* 259 */                         nprop.pattern = null;
/*     */                       } 
/* 261 */                     } else if (str2.length() != 0) {
/*     */ 
/*     */ 
/*     */                       
/* 265 */                       str2 = str2 + "|localhost|127.*|[::1]|0.0.0.0|[::0]";
/*     */                     } 
/*     */                     
/* 268 */                     if (str2 != null && 
/* 269 */                       !str2.equals(nprop.hostsSource)) {
/* 270 */                       nprop.pattern = DefaultProxySelector.toPattern(str2);
/* 271 */                       nprop.hostsSource = str2;
/*     */                     } 
/*     */                     
/* 274 */                     if (DefaultProxySelector.shouldNotUseProxyFor(nprop.pattern, urlhost)) {
/* 275 */                       return Proxy.NO_PROXY;
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */ 
/*     */                 
/* 281 */                 i = NetProperties.getInteger(DefaultProxySelector.props[b][b1] + "Port", 0).intValue();
/* 282 */                 if (i == 0 && b1 < (DefaultProxySelector.props[b]).length - 1)
/*     */                 {
/*     */ 
/*     */                   
/* 286 */                   for (byte b2 = 1; b2 < (DefaultProxySelector.props[b]).length - 1; b2++) {
/* 287 */                     if (b2 != b1 && i == 0) {
/* 288 */                       i = NetProperties.getInteger(DefaultProxySelector.props[b][b2] + "Port", 0).intValue();
/*     */                     }
/*     */                   } 
/*     */                 }
/*     */                 
/* 293 */                 if (i == 0) {
/* 294 */                   if (b1 == (DefaultProxySelector.props[b]).length - 1) {
/* 295 */                     i = DefaultProxySelector.this.defaultPort("socket");
/*     */                   } else {
/* 297 */                     i = DefaultProxySelector.this.defaultPort(proto);
/*     */                   } 
/*     */                 }
/*     */ 
/*     */                 
/* 302 */                 inetSocketAddress = InetSocketAddress.createUnresolved(str1, i);
/*     */                 
/* 304 */                 if (b1 == (DefaultProxySelector.props[b]).length - 1) {
/* 305 */                   int j = NetProperties.getInteger("socksProxyVersion", 5).intValue();
/* 306 */                   return SocksProxy.create(inetSocketAddress, j);
/*     */                 } 
/* 308 */                 return new Proxy(Proxy.Type.HTTP, inetSocketAddress);
/*     */               } 
/*     */             } 
/*     */             
/* 312 */             return Proxy.NO_PROXY;
/*     */           }
/*     */         });
/* 315 */     arrayList.add(proxy);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 321 */     return arrayList;
/*     */   }
/*     */   
/*     */   public void connectFailed(URI paramURI, SocketAddress paramSocketAddress, IOException paramIOException) {
/* 325 */     if (paramURI == null || paramSocketAddress == null || paramIOException == null) {
/* 326 */       throw new IllegalArgumentException("Arguments can't be null.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int defaultPort(String paramString) {
/* 333 */     if ("http".equalsIgnoreCase(paramString))
/* 334 */       return 80; 
/* 335 */     if ("https".equalsIgnoreCase(paramString))
/* 336 */       return 443; 
/* 337 */     if ("ftp".equalsIgnoreCase(paramString))
/* 338 */       return 80; 
/* 339 */     if ("socket".equalsIgnoreCase(paramString))
/* 340 */       return 1080; 
/* 341 */     if ("gopher".equalsIgnoreCase(paramString)) {
/* 342 */       return 80;
/*     */     }
/* 344 */     return -1;
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
/*     */   static boolean shouldNotUseProxyFor(Pattern paramPattern, String paramString) {
/* 356 */     if (paramPattern == null || paramString.isEmpty())
/* 357 */       return false; 
/* 358 */     return paramPattern.matcher(paramString).matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Pattern toPattern(String paramString) {
/* 368 */     boolean bool = true;
/* 369 */     StringJoiner stringJoiner = new StringJoiner("|");
/* 370 */     for (String str : paramString.split("\\|")) {
/* 371 */       if (!str.isEmpty()) {
/*     */         
/* 373 */         bool = false;
/* 374 */         String str1 = disjunctToRegex(str.toLowerCase());
/* 375 */         stringJoiner.add(str1);
/*     */       } 
/* 377 */     }  return bool ? null : Pattern.compile(stringJoiner.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String disjunctToRegex(String paramString) {
/*     */     String str;
/* 386 */     if (paramString.startsWith("*")) {
/* 387 */       str = ".*" + Pattern.quote(paramString.substring(1));
/* 388 */     } else if (paramString.endsWith("*")) {
/* 389 */       str = Pattern.quote(paramString.substring(0, paramString.length() - 1)) + ".*";
/*     */     } else {
/* 391 */       str = Pattern.quote(paramString);
/*     */     } 
/* 393 */     return str;
/*     */   }
/*     */   
/*     */   private static native boolean init();
/*     */   
/*     */   private synchronized native Proxy getSystemProxy(String paramString1, String paramString2);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/spi/DefaultProxySelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */