/*      */ package java.net;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.util.Hashtable;
/*      */ import java.util.StringTokenizer;
/*      */ import sun.misc.VM;
/*      */ import sun.net.ApplicationProxy;
/*      */ import sun.net.util.IPAddressUtil;
/*      */ import sun.net.www.protocol.jar.Handler;
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
/*      */ public final class URL
/*      */   implements Serializable
/*      */ {
/*      */   static final String BUILTIN_HANDLERS_PREFIX = "sun.net.www.protocol";
/*      */   static final long serialVersionUID = -7627629688361524110L;
/*      */   private static final String protocolPathProp = "java.protocol.handler.pkgs";
/*      */   private String protocol;
/*      */   private String host;
/*  177 */   private int port = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String file;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String query;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String authority;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String path;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String userInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String ref;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient InetAddress hostAddress;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient URLStreamHandler handler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  227 */   private int hashCode = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient UrlDeserializedState tempState;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static URLStreamHandlerFactory factory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL(String paramString1, String paramString2, int paramInt, String paramString3) throws MalformedURLException {
/*  312 */     this(paramString1, paramString2, paramInt, paramString3, null);
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
/*      */   public URL(String paramString1, String paramString2, String paramString3) throws MalformedURLException {
/*  335 */     this(paramString1, paramString2, -1, paramString3);
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
/*      */   public URL(String paramString1, String paramString2, int paramInt, String paramString3, URLStreamHandler paramURLStreamHandler) throws MalformedURLException {
/*  379 */     if (paramURLStreamHandler != null) {
/*  380 */       SecurityManager securityManager = System.getSecurityManager();
/*  381 */       if (securityManager != null)
/*      */       {
/*  383 */         checkSpecifyHandler(securityManager);
/*      */       }
/*      */     } 
/*      */     
/*  387 */     paramString1 = paramString1.toLowerCase();
/*  388 */     this.protocol = paramString1;
/*  389 */     if (paramString2 != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  395 */       if (paramString2.indexOf(':') >= 0 && !paramString2.startsWith("[")) {
/*  396 */         paramString2 = "[" + paramString2 + "]";
/*      */       }
/*  398 */       this.host = paramString2;
/*      */       
/*  400 */       if (paramInt < -1) {
/*  401 */         throw new MalformedURLException("Invalid port number :" + paramInt);
/*      */       }
/*      */       
/*  404 */       this.port = paramInt;
/*  405 */       this.authority = (paramInt == -1) ? paramString2 : (paramString2 + ":" + paramInt);
/*      */     } 
/*      */     
/*  408 */     Parts parts = new Parts(paramString3);
/*  409 */     this.path = parts.getPath();
/*  410 */     this.query = parts.getQuery();
/*      */     
/*  412 */     if (this.query != null) {
/*  413 */       this.file = this.path + "?" + this.query;
/*      */     } else {
/*  415 */       this.file = this.path;
/*      */     } 
/*  417 */     this.ref = parts.getRef();
/*      */ 
/*      */ 
/*      */     
/*  421 */     if (paramURLStreamHandler == null && (
/*  422 */       paramURLStreamHandler = getURLStreamHandler(paramString1)) == null) {
/*  423 */       throw new MalformedURLException("unknown protocol: " + paramString1);
/*      */     }
/*  425 */     this.handler = paramURLStreamHandler;
/*  426 */     if (paramString2 != null && isBuiltinStreamHandler(paramURLStreamHandler)) {
/*  427 */       String str = IPAddressUtil.checkExternalForm(this);
/*  428 */       if (str != null) {
/*  429 */         throw new MalformedURLException(str);
/*      */       }
/*      */     } 
/*  432 */     if ("jar".equalsIgnoreCase(paramString1) && 
/*  433 */       paramURLStreamHandler instanceof Handler) {
/*      */ 
/*      */       
/*  436 */       String str = ((Handler)paramURLStreamHandler).checkNestedProtocol(paramString3);
/*  437 */       if (str != null) {
/*  438 */         throw new MalformedURLException(str);
/*      */       }
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
/*      */   public URL(String paramString) throws MalformedURLException {
/*  457 */     this(null, paramString);
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
/*      */   
/*      */   public URL(URL paramURL, String paramString) throws MalformedURLException {
/*  508 */     this(paramURL, paramString, (URLStreamHandler)null);
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
/*      */   public URL(URL paramURL, String paramString, URLStreamHandler paramURLStreamHandler) throws MalformedURLException {
/*  534 */     String str1 = paramString;
/*      */     
/*  536 */     int i = 0;
/*  537 */     String str2 = null;
/*  538 */     boolean bool1 = false;
/*  539 */     boolean bool2 = false;
/*      */ 
/*      */     
/*  542 */     if (paramURLStreamHandler != null) {
/*  543 */       SecurityManager securityManager = System.getSecurityManager();
/*  544 */       if (securityManager != null) {
/*  545 */         checkSpecifyHandler(securityManager);
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  550 */       int k = paramString.length();
/*  551 */       while (k > 0 && paramString.charAt(k - 1) <= ' ') {
/*  552 */         k--;
/*      */       }
/*  554 */       while (i < k && paramString.charAt(i) <= ' ') {
/*  555 */         i++;
/*      */       }
/*      */       
/*  558 */       if (paramString.regionMatches(true, i, "url:", 0, 4)) {
/*  559 */         i += 4;
/*      */       }
/*  561 */       if (i < paramString.length() && paramString.charAt(i) == '#')
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  566 */         bool1 = true; }  int j;
/*      */       char c;
/*  568 */       for (j = i; !bool1 && j < k && (
/*  569 */         c = paramString.charAt(j)) != '/'; j++) {
/*  570 */         if (c == ':') {
/*      */           
/*  572 */           String str = paramString.substring(i, j).toLowerCase();
/*  573 */           if (isValidProtocol(str)) {
/*  574 */             str2 = str;
/*  575 */             i = j + 1;
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  582 */       this.protocol = str2;
/*  583 */       if (paramURL != null && (str2 == null || str2
/*  584 */         .equalsIgnoreCase(paramURL.protocol))) {
/*      */ 
/*      */         
/*  587 */         if (paramURLStreamHandler == null) {
/*  588 */           paramURLStreamHandler = paramURL.handler;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  595 */         if (paramURL.path != null && paramURL.path.startsWith("/")) {
/*  596 */           str2 = null;
/*      */         }
/*  598 */         if (str2 == null) {
/*  599 */           this.protocol = paramURL.protocol;
/*  600 */           this.authority = paramURL.authority;
/*  601 */           this.userInfo = paramURL.userInfo;
/*  602 */           this.host = paramURL.host;
/*  603 */           this.port = paramURL.port;
/*  604 */           this.file = paramURL.file;
/*  605 */           this.path = paramURL.path;
/*  606 */           bool2 = true;
/*      */         } 
/*      */       } 
/*      */       
/*  610 */       if (this.protocol == null) {
/*  611 */         throw new MalformedURLException("no protocol: " + str1);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  616 */       if (paramURLStreamHandler == null && (
/*  617 */         paramURLStreamHandler = getURLStreamHandler(this.protocol)) == null) {
/*  618 */         throw new MalformedURLException("unknown protocol: " + this.protocol);
/*      */       }
/*      */       
/*  621 */       this.handler = paramURLStreamHandler;
/*      */       
/*  623 */       j = paramString.indexOf('#', i);
/*  624 */       if (j >= 0) {
/*  625 */         this.ref = paramString.substring(j + 1, k);
/*  626 */         k = j;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  633 */       if (bool2 && i == k) {
/*  634 */         this.query = paramURL.query;
/*  635 */         if (this.ref == null) {
/*  636 */           this.ref = paramURL.ref;
/*      */         }
/*      */       } 
/*      */       
/*  640 */       paramURLStreamHandler.parseURL(this, paramString, i, k);
/*      */     }
/*  642 */     catch (MalformedURLException malformedURLException) {
/*  643 */       throw malformedURLException;
/*  644 */     } catch (Exception exception) {
/*  645 */       MalformedURLException malformedURLException = new MalformedURLException(exception.getMessage());
/*  646 */       malformedURLException.initCause(exception);
/*  647 */       throw malformedURLException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidProtocol(String paramString) {
/*  655 */     int i = paramString.length();
/*  656 */     if (i < 1)
/*  657 */       return false; 
/*  658 */     char c = paramString.charAt(0);
/*  659 */     if (!Character.isLetter(c))
/*  660 */       return false; 
/*  661 */     for (byte b = 1; b < i; b++) {
/*  662 */       c = paramString.charAt(b);
/*  663 */       if (!Character.isLetterOrDigit(c) && c != '.' && c != '+' && c != '-')
/*      */       {
/*  665 */         return false;
/*      */       }
/*      */     } 
/*  668 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSpecifyHandler(SecurityManager paramSecurityManager) {
/*  675 */     paramSecurityManager.checkPermission(SecurityConstants.SPECIFY_HANDLER_PERMISSION);
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
/*      */   void set(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4) {
/*  691 */     synchronized (this) {
/*  692 */       this.protocol = paramString1;
/*  693 */       this.host = paramString2;
/*  694 */       this.authority = (paramInt == -1) ? paramString2 : (paramString2 + ":" + paramInt);
/*  695 */       this.port = paramInt;
/*  696 */       this.file = paramString3;
/*  697 */       this.ref = paramString4;
/*      */ 
/*      */       
/*  700 */       this.hashCode = -1;
/*  701 */       this.hostAddress = null;
/*  702 */       int i = paramString3.lastIndexOf('?');
/*  703 */       if (i != -1) {
/*  704 */         this.query = paramString3.substring(i + 1);
/*  705 */         this.path = paramString3.substring(0, i);
/*      */       } else {
/*  707 */         this.path = paramString3;
/*      */       } 
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
/*      */   void set(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) {
/*  729 */     synchronized (this) {
/*  730 */       this.protocol = paramString1;
/*  731 */       this.host = paramString2;
/*  732 */       this.port = paramInt;
/*  733 */       this.file = (paramString6 == null) ? paramString5 : (paramString5 + "?" + paramString6);
/*  734 */       this.userInfo = paramString4;
/*  735 */       this.path = paramString5;
/*  736 */       this.ref = paramString7;
/*      */ 
/*      */       
/*  739 */       this.hashCode = -1;
/*  740 */       this.hostAddress = null;
/*  741 */       this.query = paramString6;
/*  742 */       this.authority = paramString3;
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
/*      */   public String getQuery() {
/*  754 */     return this.query;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath() {
/*  765 */     return this.path;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserInfo() {
/*  776 */     return this.userInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAuthority() {
/*  786 */     return this.authority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/*  795 */     return this.port;
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
/*      */   public int getDefaultPort() {
/*  808 */     return this.handler.getDefaultPort();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProtocol() {
/*  817 */     return this.protocol;
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
/*      */   public String getHost() {
/*  829 */     return this.host;
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
/*      */   public String getFile() {
/*  844 */     return this.file;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRef() {
/*  855 */     return this.ref;
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
/*      */   public boolean equals(Object paramObject) {
/*  884 */     if (!(paramObject instanceof URL))
/*  885 */       return false; 
/*  886 */     URL uRL = (URL)paramObject;
/*      */     
/*  888 */     return this.handler.equals(this, uRL);
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
/*      */   public synchronized int hashCode() {
/*  900 */     if (this.hashCode != -1) {
/*  901 */       return this.hashCode;
/*      */     }
/*  903 */     this.hashCode = this.handler.hashCode(this);
/*  904 */     return this.hashCode;
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
/*      */   public boolean sameFile(URL paramURL) {
/*  919 */     return this.handler.sameFile(this, paramURL);
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
/*      */   public String toString() {
/*  933 */     return toExternalForm();
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
/*      */   public String toExternalForm() {
/*  947 */     return this.handler.toExternalForm(this);
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
/*      */   public URI toURI() throws URISyntaxException {
/*  964 */     URI uRI = new URI(toString());
/*  965 */     if (this.authority != null && isBuiltinStreamHandler(this.handler)) {
/*  966 */       String str = IPAddressUtil.checkAuthority(this);
/*  967 */       if (str != null) throw new URISyntaxException(this.authority, str); 
/*      */     } 
/*  969 */     return uRI;
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
/*      */   public URLConnection openConnection() throws IOException {
/* 1002 */     return this.handler.openConnection(this);
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
/*      */   public URLConnection openConnection(Proxy paramProxy) throws IOException {
/* 1036 */     if (paramProxy == null) {
/* 1037 */       throw new IllegalArgumentException("proxy can not be null");
/*      */     }
/*      */ 
/*      */     
/* 1041 */     Proxy proxy = (paramProxy == Proxy.NO_PROXY) ? Proxy.NO_PROXY : ApplicationProxy.create(paramProxy);
/* 1042 */     SecurityManager securityManager = System.getSecurityManager();
/* 1043 */     if (proxy.type() != Proxy.Type.DIRECT && securityManager != null) {
/* 1044 */       InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
/* 1045 */       if (inetSocketAddress.isUnresolved()) {
/* 1046 */         securityManager.checkConnect(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
/*      */       } else {
/* 1048 */         securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/* 1049 */             .getPort());
/*      */       } 
/* 1051 */     }  return this.handler.openConnection(this, proxy);
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
/*      */   public final InputStream openStream() throws IOException {
/* 1068 */     return openConnection().getInputStream();
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
/*      */   public final Object getContent() throws IOException {
/* 1082 */     return openConnection().getContent();
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
/*      */   public final Object getContent(Class[] paramArrayOfClass) throws IOException {
/* 1101 */     return openConnection().getContent(paramArrayOfClass);
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
/*      */   public static void setURLStreamHandlerFactory(URLStreamHandlerFactory paramURLStreamHandlerFactory) {
/* 1133 */     synchronized (streamHandlerLock) {
/* 1134 */       if (factory != null) {
/* 1135 */         throw new Error("factory already defined");
/*      */       }
/* 1137 */       SecurityManager securityManager = System.getSecurityManager();
/* 1138 */       if (securityManager != null) {
/* 1139 */         securityManager.checkSetFactory();
/*      */       }
/* 1141 */       handlers.clear();
/* 1142 */       factory = paramURLStreamHandlerFactory;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1149 */   static Hashtable<String, URLStreamHandler> handlers = new Hashtable<>();
/* 1150 */   private static Object streamHandlerLock = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static URLStreamHandler getURLStreamHandler(String paramString) {
/* 1158 */     URLStreamHandler uRLStreamHandler = handlers.get(paramString);
/* 1159 */     if (uRLStreamHandler == null) {
/*      */       
/* 1161 */       boolean bool = false;
/*      */ 
/*      */       
/* 1164 */       if (factory != null) {
/* 1165 */         uRLStreamHandler = factory.createURLStreamHandler(paramString);
/* 1166 */         bool = true;
/*      */       } 
/*      */ 
/*      */       
/* 1170 */       if (uRLStreamHandler == null) {
/* 1171 */         String str = null;
/*      */ 
/*      */         
/* 1174 */         str = AccessController.<String>doPrivileged(new GetPropertyAction("java.protocol.handler.pkgs", ""));
/*      */ 
/*      */         
/* 1177 */         if (str != "") {
/* 1178 */           str = str + "|";
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1183 */         str = str + "sun.net.www.protocol";
/*      */         
/* 1185 */         StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*      */ 
/*      */         
/* 1188 */         while (uRLStreamHandler == null && stringTokenizer
/* 1189 */           .hasMoreTokens()) {
/*      */ 
/*      */           
/* 1192 */           String str1 = stringTokenizer.nextToken().trim();
/*      */           try {
/* 1194 */             String str2 = str1 + "." + paramString + ".Handler";
/*      */             
/* 1196 */             Class<?> clazz = null;
/*      */             try {
/* 1198 */               clazz = Class.forName(str2);
/* 1199 */             } catch (ClassNotFoundException classNotFoundException) {
/* 1200 */               ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 1201 */               if (classLoader != null) {
/* 1202 */                 clazz = classLoader.loadClass(str2);
/*      */               }
/*      */             } 
/* 1205 */             if (clazz != null)
/*      */             {
/* 1207 */               uRLStreamHandler = (URLStreamHandler)clazz.newInstance();
/*      */             }
/* 1209 */           } catch (Exception exception) {}
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1215 */       synchronized (streamHandlerLock) {
/*      */         
/* 1217 */         URLStreamHandler uRLStreamHandler1 = null;
/*      */ 
/*      */ 
/*      */         
/* 1221 */         uRLStreamHandler1 = handlers.get(paramString);
/*      */         
/* 1223 */         if (uRLStreamHandler1 != null) {
/* 1224 */           return uRLStreamHandler1;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1229 */         if (!bool && factory != null) {
/* 1230 */           uRLStreamHandler1 = factory.createURLStreamHandler(paramString);
/*      */         }
/*      */         
/* 1233 */         if (uRLStreamHandler1 != null)
/*      */         {
/*      */ 
/*      */           
/* 1237 */           uRLStreamHandler = uRLStreamHandler1;
/*      */         }
/*      */ 
/*      */         
/* 1241 */         if (uRLStreamHandler != null) {
/* 1242 */           handlers.put(paramString, uRLStreamHandler);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1248 */     return uRLStreamHandler;
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
/* 1268 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("protocol", String.class), new ObjectStreamField("host", String.class), new ObjectStreamField("port", int.class), new ObjectStreamField("authority", String.class), new ObjectStreamField("file", String.class), new ObjectStreamField("ref", String.class), new ObjectStreamField("hashCode", int.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1290 */     paramObjectOutputStream.defaultWriteObject();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1300 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 1301 */     String str1 = (String)getField.get("protocol", (Object)null);
/* 1302 */     if (getURLStreamHandler(str1) == null) {
/* 1303 */       throw new IOException("unknown protocol: " + str1);
/*      */     }
/* 1305 */     String str2 = (String)getField.get("host", (Object)null);
/* 1306 */     int i = getField.get("port", -1);
/* 1307 */     String str3 = (String)getField.get("authority", (Object)null);
/* 1308 */     String str4 = (String)getField.get("file", (Object)null);
/* 1309 */     String str5 = (String)getField.get("ref", (Object)null);
/* 1310 */     int j = getField.get("hashCode", -1);
/* 1311 */     if (str3 == null && ((str2 != null && str2
/* 1312 */       .length() > 0) || i != -1)) {
/* 1313 */       if (str2 == null)
/* 1314 */         str2 = ""; 
/* 1315 */       str3 = (i == -1) ? str2 : (str2 + ":" + i);
/*      */     } 
/* 1317 */     this.tempState = new UrlDeserializedState(str1, str2, i, str3, str4, str5, j);
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
/*      */   private Object readResolve() throws ObjectStreamException {
/* 1332 */     URLStreamHandler uRLStreamHandler = null;
/*      */     
/* 1334 */     uRLStreamHandler = getURLStreamHandler(this.tempState.getProtocol());
/*      */     
/* 1336 */     URL uRL = null;
/* 1337 */     if (isBuiltinStreamHandler(uRLStreamHandler.getClass().getName())) {
/* 1338 */       uRL = fabricateNewURL();
/*      */     } else {
/* 1340 */       uRL = setDeserializedFields(uRLStreamHandler);
/*      */     } 
/* 1342 */     return uRL;
/*      */   }
/*      */ 
/*      */   
/*      */   private URL setDeserializedFields(URLStreamHandler paramURLStreamHandler) {
/* 1347 */     String str1 = null;
/* 1348 */     String str2 = this.tempState.getProtocol();
/* 1349 */     String str3 = this.tempState.getHost();
/* 1350 */     int i = this.tempState.getPort();
/* 1351 */     String str4 = this.tempState.getAuthority();
/* 1352 */     String str5 = this.tempState.getFile();
/* 1353 */     String str6 = this.tempState.getRef();
/* 1354 */     int j = this.tempState.getHashCode();
/*      */ 
/*      */ 
/*      */     
/* 1358 */     if (str4 == null && ((str3 != null && str3
/* 1359 */       .length() > 0) || i != -1)) {
/* 1360 */       if (str3 == null)
/* 1361 */         str3 = ""; 
/* 1362 */       str4 = (i == -1) ? str3 : (str3 + ":" + i);
/*      */ 
/*      */       
/* 1365 */       int k = str3.lastIndexOf('@');
/* 1366 */       if (k != -1) {
/* 1367 */         str1 = str3.substring(0, k);
/* 1368 */         str3 = str3.substring(k + 1);
/*      */       } 
/* 1370 */     } else if (str4 != null) {
/*      */       
/* 1372 */       int k = str4.indexOf('@');
/* 1373 */       if (k != -1) {
/* 1374 */         str1 = str4.substring(0, k);
/*      */       }
/*      */     } 
/*      */     
/* 1378 */     String str7 = null;
/* 1379 */     String str8 = null;
/* 1380 */     if (str5 != null) {
/*      */       
/* 1382 */       int k = str5.lastIndexOf('?');
/* 1383 */       if (k != -1) {
/* 1384 */         str8 = str5.substring(k + 1);
/* 1385 */         str7 = str5.substring(0, k);
/*      */       } else {
/* 1387 */         str7 = str5;
/*      */       } 
/*      */     } 
/*      */     
/* 1391 */     this.protocol = str2;
/* 1392 */     this.host = str3;
/* 1393 */     this.port = i;
/* 1394 */     this.file = str5;
/* 1395 */     this.authority = str4;
/* 1396 */     this.ref = str6;
/* 1397 */     this.hashCode = j;
/* 1398 */     this.handler = paramURLStreamHandler;
/* 1399 */     this.query = str8;
/* 1400 */     this.path = str7;
/* 1401 */     this.userInfo = str1;
/* 1402 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private URL fabricateNewURL() throws InvalidObjectException {
/* 1409 */     URL uRL = null;
/* 1410 */     String str = this.tempState.reconstituteUrlString();
/*      */     
/*      */     try {
/* 1413 */       uRL = new URL(str);
/* 1414 */     } catch (MalformedURLException malformedURLException) {
/* 1415 */       resetState();
/* 1416 */       InvalidObjectException invalidObjectException = new InvalidObjectException("Malformed URL: " + str);
/*      */       
/* 1418 */       invalidObjectException.initCause(malformedURLException);
/* 1419 */       throw invalidObjectException;
/*      */     } 
/* 1421 */     uRL.setSerializedHashCode(this.tempState.getHashCode());
/* 1422 */     resetState();
/* 1423 */     return uRL;
/*      */   }
/*      */   
/*      */   boolean isBuiltinStreamHandler(URLStreamHandler paramURLStreamHandler) {
/* 1427 */     Class<?> clazz = paramURLStreamHandler.getClass();
/* 1428 */     return (isBuiltinStreamHandler(clazz.getName()) || 
/* 1429 */       VM.isSystemDomainLoader(clazz.getClassLoader()));
/*      */   }
/*      */   
/*      */   private boolean isBuiltinStreamHandler(String paramString) {
/* 1433 */     return paramString.startsWith("sun.net.www.protocol");
/*      */   }
/*      */   
/*      */   private void resetState() {
/* 1437 */     this.protocol = null;
/* 1438 */     this.host = null;
/* 1439 */     this.port = -1;
/* 1440 */     this.file = null;
/* 1441 */     this.authority = null;
/* 1442 */     this.ref = null;
/* 1443 */     this.hashCode = -1;
/* 1444 */     this.handler = null;
/* 1445 */     this.query = null;
/* 1446 */     this.path = null;
/* 1447 */     this.userInfo = null;
/* 1448 */     this.tempState = null;
/*      */   }
/*      */   
/*      */   private void setSerializedHashCode(int paramInt) {
/* 1452 */     this.hashCode = paramInt;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/URL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */