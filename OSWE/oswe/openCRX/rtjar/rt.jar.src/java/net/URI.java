/*      */ package java.net;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.charset.CharacterCodingException;
/*      */ import java.nio.charset.CharsetDecoder;
/*      */ import java.nio.charset.CoderResult;
/*      */ import java.nio.charset.CodingErrorAction;
/*      */ import java.text.Normalizer;
/*      */ import sun.nio.cs.ThreadLocalCoders;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class URI
/*      */   implements Comparable<URI>, Serializable
/*      */ {
/*      */   static final long serialVersionUID = -6052424284110960213L;
/*      */   private transient String scheme;
/*      */   private transient String fragment;
/*      */   private transient String authority;
/*      */   private transient String userInfo;
/*      */   private transient String host;
/*  487 */   private transient int port = -1;
/*      */ 
/*      */   
/*      */   private transient String path;
/*      */   
/*      */   private transient String query;
/*      */   
/*      */   private volatile transient String schemeSpecificPart;
/*      */   
/*      */   private volatile transient int hash;
/*      */   
/*  498 */   private volatile transient String decodedUserInfo = null;
/*  499 */   private volatile transient String decodedAuthority = null;
/*  500 */   private volatile transient String decodedPath = null;
/*  501 */   private volatile transient String decodedQuery = null;
/*  502 */   private volatile transient String decodedFragment = null;
/*  503 */   private volatile transient String decodedSchemeSpecificPart = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile String string;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URI(String paramString) throws URISyntaxException {
/*  588 */     (new Parser(paramString)).parse(false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URI(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6) throws URISyntaxException {
/*  669 */     String str = toString(paramString1, null, null, paramString2, paramString3, paramInt, paramString4, paramString5, paramString6);
/*      */ 
/*      */     
/*  672 */     checkPath(str, paramString1, paramString4);
/*  673 */     (new Parser(str)).parse(true);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URI(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws URISyntaxException {
/*  742 */     String str = toString(paramString1, null, paramString2, null, null, -1, paramString3, paramString4, paramString5);
/*      */ 
/*      */     
/*  745 */     checkPath(str, paramString1, paramString3);
/*  746 */     (new Parser(str)).parse(false);
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
/*      */   public URI(String paramString1, String paramString2, String paramString3, String paramString4) throws URISyntaxException {
/*  774 */     this(paramString1, null, paramString2, -1, paramString3, null, paramString4);
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
/*      */   public URI(String paramString1, String paramString2, String paramString3) throws URISyntaxException {
/*  817 */     (new Parser(toString(paramString1, paramString2, null, null, null, -1, null, null, paramString3)))
/*      */ 
/*      */       
/*  820 */       .parse(false);
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
/*      */   public static URI create(String paramString) {
/*      */     try {
/*  850 */       return new URI(paramString);
/*  851 */     } catch (URISyntaxException uRISyntaxException) {
/*  852 */       throw new IllegalArgumentException(uRISyntaxException.getMessage(), uRISyntaxException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URI parseServerAuthority() throws URISyntaxException {
/*  912 */     if (this.host != null || this.authority == null)
/*  913 */       return this; 
/*  914 */     defineString();
/*  915 */     (new Parser(this.string)).parse(true);
/*  916 */     return this;
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
/*      */   public URI normalize() {
/*  957 */     return normalize(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URI resolve(URI paramURI) {
/* 1015 */     return resolve(this, paramURI);
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
/*      */   public URI resolve(String paramString) {
/* 1036 */     return resolve(create(paramString));
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
/*      */   public URI relativize(URI paramURI) {
/* 1066 */     return relativize(this, paramURI);
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
/*      */   public URL toURL() throws MalformedURLException {
/* 1087 */     if (!isAbsolute())
/* 1088 */       throw new IllegalArgumentException("URI is not absolute"); 
/* 1089 */     return new URL(toString());
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
/*      */   public String getScheme() {
/* 1108 */     return this.scheme;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAbsolute() {
/* 1119 */     return (this.scheme != null);
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
/*      */   public boolean isOpaque() {
/* 1133 */     return (this.path == null);
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
/*      */   public String getRawSchemeSpecificPart() {
/* 1147 */     defineSchemeSpecificPart();
/* 1148 */     return this.schemeSpecificPart;
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
/*      */   public String getSchemeSpecificPart() {
/* 1163 */     if (this.decodedSchemeSpecificPart == null)
/* 1164 */       this.decodedSchemeSpecificPart = decode(getRawSchemeSpecificPart()); 
/* 1165 */     return this.decodedSchemeSpecificPart;
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
/*      */   public String getRawAuthority() {
/* 1182 */     return this.authority;
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
/*      */   public String getAuthority() {
/* 1196 */     if (this.decodedAuthority == null)
/* 1197 */       this.decodedAuthority = decode(this.authority); 
/* 1198 */     return this.decodedAuthority;
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
/*      */   public String getRawUserInfo() {
/* 1212 */     return this.userInfo;
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
/*      */   public String getUserInfo() {
/* 1226 */     if (this.decodedUserInfo == null && this.userInfo != null)
/* 1227 */       this.decodedUserInfo = decode(this.userInfo); 
/* 1228 */     return this.decodedUserInfo;
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
/*      */   public String getHost() {
/* 1268 */     return this.host;
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
/*      */   public int getPort() {
/* 1281 */     return this.port;
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
/*      */   public String getRawPath() {
/* 1296 */     return this.path;
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
/*      */   public String getPath() {
/* 1310 */     if (this.decodedPath == null && this.path != null)
/* 1311 */       this.decodedPath = decode(this.path); 
/* 1312 */     return this.decodedPath;
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
/*      */   public String getRawQuery() {
/* 1325 */     return this.query;
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
/*      */   public String getQuery() {
/* 1339 */     if (this.decodedQuery == null && this.query != null)
/* 1340 */       this.decodedQuery = decode(this.query); 
/* 1341 */     return this.decodedQuery;
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
/*      */   public String getRawFragment() {
/* 1354 */     return this.fragment;
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
/*      */   public String getFragment() {
/* 1368 */     if (this.decodedFragment == null && this.fragment != null)
/* 1369 */       this.decodedFragment = decode(this.fragment); 
/* 1370 */     return this.decodedFragment;
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
/*      */   public boolean equals(Object paramObject) {
/* 1414 */     if (paramObject == this)
/* 1415 */       return true; 
/* 1416 */     if (!(paramObject instanceof URI))
/* 1417 */       return false; 
/* 1418 */     URI uRI = (URI)paramObject;
/* 1419 */     if (isOpaque() != uRI.isOpaque()) return false; 
/* 1420 */     if (!equalIgnoringCase(this.scheme, uRI.scheme)) return false; 
/* 1421 */     if (!equal(this.fragment, uRI.fragment)) return false;
/*      */ 
/*      */     
/* 1424 */     if (isOpaque()) {
/* 1425 */       return equal(this.schemeSpecificPart, uRI.schemeSpecificPart);
/*      */     }
/*      */     
/* 1428 */     if (!equal(this.path, uRI.path)) return false; 
/* 1429 */     if (!equal(this.query, uRI.query)) return false;
/*      */ 
/*      */     
/* 1432 */     if (this.authority == uRI.authority) return true; 
/* 1433 */     if (this.host != null) {
/*      */       
/* 1435 */       if (!equal(this.userInfo, uRI.userInfo)) return false; 
/* 1436 */       if (!equalIgnoringCase(this.host, uRI.host)) return false; 
/* 1437 */       if (this.port != uRI.port) return false; 
/* 1438 */     } else if (this.authority != null) {
/*      */       
/* 1440 */       if (!equal(this.authority, uRI.authority)) return false; 
/* 1441 */     } else if (this.authority != uRI.authority) {
/* 1442 */       return false;
/*      */     } 
/*      */     
/* 1445 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1456 */     if (this.hash != 0)
/* 1457 */       return this.hash; 
/* 1458 */     int i = hashIgnoringCase(0, this.scheme);
/* 1459 */     i = hash(i, this.fragment);
/* 1460 */     if (isOpaque()) {
/* 1461 */       i = hash(i, this.schemeSpecificPart);
/*      */     } else {
/* 1463 */       i = hash(i, this.path);
/* 1464 */       i = hash(i, this.query);
/* 1465 */       if (this.host != null) {
/* 1466 */         i = hash(i, this.userInfo);
/* 1467 */         i = hashIgnoringCase(i, this.host);
/* 1468 */         i += 1949 * this.port;
/*      */       } else {
/* 1470 */         i = hash(i, this.authority);
/*      */       } 
/*      */     } 
/* 1473 */     this.hash = i;
/* 1474 */     return i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(URI paramURI) {
/*      */     int i;
/* 1548 */     if ((i = compareIgnoringCase(this.scheme, paramURI.scheme)) != 0) {
/* 1549 */       return i;
/*      */     }
/* 1551 */     if (isOpaque()) {
/* 1552 */       if (paramURI.isOpaque()) {
/*      */         
/* 1554 */         if ((i = compare(this.schemeSpecificPart, paramURI.schemeSpecificPart)) != 0)
/*      */         {
/* 1556 */           return i; } 
/* 1557 */         return compare(this.fragment, paramURI.fragment);
/*      */       } 
/* 1559 */       return 1;
/* 1560 */     }  if (paramURI.isOpaque()) {
/* 1561 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1565 */     if (this.host != null && paramURI.host != null)
/*      */     
/* 1567 */     { if ((i = compare(this.userInfo, paramURI.userInfo)) != 0)
/* 1568 */         return i; 
/* 1569 */       if ((i = compareIgnoringCase(this.host, paramURI.host)) != 0)
/* 1570 */         return i; 
/* 1571 */       if ((i = this.port - paramURI.port) != 0) {
/* 1572 */         return i;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       
/*      */        }
/*      */     
/* 1580 */     else if ((i = compare(this.authority, paramURI.authority)) != 0) { return i; }
/*      */ 
/*      */     
/* 1583 */     if ((i = compare(this.path, paramURI.path)) != 0) return i; 
/* 1584 */     if ((i = compare(this.query, paramURI.query)) != 0) return i; 
/* 1585 */     return compare(this.fragment, paramURI.fragment);
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
/*      */   public String toString() {
/* 1603 */     defineString();
/* 1604 */     return this.string;
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
/*      */   public String toASCIIString() {
/* 1621 */     defineString();
/* 1622 */     return encode(this.string);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1642 */     defineString();
/* 1643 */     paramObjectOutputStream.defaultWriteObject();
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 1659 */     this.port = -1;
/* 1660 */     paramObjectInputStream.defaultReadObject();
/*      */     try {
/* 1662 */       (new Parser(this.string)).parse(false);
/* 1663 */     } catch (URISyntaxException uRISyntaxException) {
/* 1664 */       InvalidObjectException invalidObjectException = new InvalidObjectException("Invalid URI");
/* 1665 */       invalidObjectException.initCause(uRISyntaxException);
/* 1666 */       throw invalidObjectException;
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
/*      */   private static int toLower(char paramChar) {
/* 1685 */     if (paramChar >= 'A' && paramChar <= 'Z')
/* 1686 */       return paramChar + 32; 
/* 1687 */     return paramChar;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int toUpper(char paramChar) {
/* 1692 */     if (paramChar >= 'a' && paramChar <= 'z')
/* 1693 */       return paramChar - 32; 
/* 1694 */     return paramChar;
/*      */   }
/*      */   
/*      */   private static boolean equal(String paramString1, String paramString2) {
/* 1698 */     if (paramString1 == paramString2) return true; 
/* 1699 */     if (paramString1 != null && paramString2 != null) {
/* 1700 */       if (paramString1.length() != paramString2.length())
/* 1701 */         return false; 
/* 1702 */       if (paramString1.indexOf('%') < 0)
/* 1703 */         return paramString1.equals(paramString2); 
/* 1704 */       int i = paramString1.length();
/* 1705 */       for (byte b = 0; b < i; ) {
/* 1706 */         char c1 = paramString1.charAt(b);
/* 1707 */         char c2 = paramString2.charAt(b);
/* 1708 */         if (c1 != '%') {
/* 1709 */           if (c1 != c2)
/* 1710 */             return false; 
/* 1711 */           b++;
/*      */           continue;
/*      */         } 
/* 1714 */         if (c2 != '%')
/* 1715 */           return false; 
/* 1716 */         b++;
/* 1717 */         if (toLower(paramString1.charAt(b)) != toLower(paramString2.charAt(b)))
/* 1718 */           return false; 
/* 1719 */         b++;
/* 1720 */         if (toLower(paramString1.charAt(b)) != toLower(paramString2.charAt(b)))
/* 1721 */           return false; 
/* 1722 */         b++;
/*      */       } 
/* 1724 */       return true;
/*      */     } 
/* 1726 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean equalIgnoringCase(String paramString1, String paramString2) {
/* 1731 */     if (paramString1 == paramString2) return true; 
/* 1732 */     if (paramString1 != null && paramString2 != null) {
/* 1733 */       int i = paramString1.length();
/* 1734 */       if (paramString2.length() != i)
/* 1735 */         return false; 
/* 1736 */       for (byte b = 0; b < i; b++) {
/* 1737 */         if (toLower(paramString1.charAt(b)) != toLower(paramString2.charAt(b)))
/* 1738 */           return false; 
/*      */       } 
/* 1740 */       return true;
/*      */     } 
/* 1742 */     return false;
/*      */   }
/*      */   
/*      */   private static int hash(int paramInt, String paramString) {
/* 1746 */     if (paramString == null) return paramInt; 
/* 1747 */     return (paramString.indexOf('%') < 0) ? (paramInt * 127 + paramString.hashCode()) : 
/* 1748 */       normalizedHash(paramInt, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int normalizedHash(int paramInt, String paramString) {
/* 1753 */     int i = 0;
/* 1754 */     for (byte b = 0; b < paramString.length(); b++) {
/* 1755 */       char c = paramString.charAt(b);
/* 1756 */       i = 31 * i + c;
/* 1757 */       if (c == '%') {
/*      */ 
/*      */ 
/*      */         
/* 1761 */         for (int j = b + 1; j < b + 3; j++)
/* 1762 */           i = 31 * i + toUpper(paramString.charAt(j)); 
/* 1763 */         b += 2;
/*      */       } 
/*      */     } 
/* 1766 */     return paramInt * 127 + i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int hashIgnoringCase(int paramInt, String paramString) {
/* 1771 */     if (paramString == null) return paramInt; 
/* 1772 */     int i = paramInt;
/* 1773 */     int j = paramString.length();
/* 1774 */     for (byte b = 0; b < j; b++)
/* 1775 */       i = 31 * i + toLower(paramString.charAt(b)); 
/* 1776 */     return i;
/*      */   }
/*      */   
/*      */   private static int compare(String paramString1, String paramString2) {
/* 1780 */     if (paramString1 == paramString2) return 0; 
/* 1781 */     if (paramString1 != null) {
/* 1782 */       if (paramString2 != null) {
/* 1783 */         return paramString1.compareTo(paramString2);
/*      */       }
/* 1785 */       return 1;
/*      */     } 
/* 1787 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int compareIgnoringCase(String paramString1, String paramString2) {
/* 1793 */     if (paramString1 == paramString2) return 0; 
/* 1794 */     if (paramString1 != null) {
/* 1795 */       if (paramString2 != null) {
/* 1796 */         int i = paramString1.length();
/* 1797 */         int j = paramString2.length();
/* 1798 */         int k = (i < j) ? i : j;
/* 1799 */         for (byte b = 0; b < k; b++) {
/* 1800 */           int m = toLower(paramString1.charAt(b)) - toLower(paramString2.charAt(b));
/* 1801 */           if (m != 0)
/* 1802 */             return m; 
/*      */         } 
/* 1804 */         return i - j;
/*      */       } 
/* 1806 */       return 1;
/*      */     } 
/* 1808 */     return -1;
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
/*      */   private static void checkPath(String paramString1, String paramString2, String paramString3) throws URISyntaxException {
/* 1820 */     if (paramString2 != null && 
/* 1821 */       paramString3 != null && paramString3
/* 1822 */       .length() > 0 && paramString3.charAt(0) != '/') {
/* 1823 */       throw new URISyntaxException(paramString1, "Relative path in absolute URI");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appendAuthority(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, int paramInt) {
/* 1834 */     if (paramString3 != null) {
/* 1835 */       paramStringBuffer.append("//");
/* 1836 */       if (paramString2 != null) {
/* 1837 */         paramStringBuffer.append(quote(paramString2, L_USERINFO, H_USERINFO));
/* 1838 */         paramStringBuffer.append('@');
/*      */       } 
/*      */ 
/*      */       
/* 1842 */       boolean bool = (paramString3.indexOf(':') >= 0 && !paramString3.startsWith("[") && !paramString3.endsWith("]")) ? true : false;
/* 1843 */       if (bool) paramStringBuffer.append('['); 
/* 1844 */       paramStringBuffer.append(paramString3);
/* 1845 */       if (bool) paramStringBuffer.append(']'); 
/* 1846 */       if (paramInt != -1) {
/* 1847 */         paramStringBuffer.append(':');
/* 1848 */         paramStringBuffer.append(paramInt);
/*      */       } 
/* 1850 */     } else if (paramString1 != null) {
/* 1851 */       paramStringBuffer.append("//");
/* 1852 */       if (paramString1.startsWith("[")) {
/*      */         
/* 1854 */         int i = paramString1.indexOf("]");
/* 1855 */         String str1 = paramString1, str2 = "";
/* 1856 */         if (i != -1 && paramString1.indexOf(":") != -1)
/*      */         {
/* 1858 */           if (i == paramString1.length()) {
/* 1859 */             str2 = paramString1;
/* 1860 */             str1 = "";
/*      */           } else {
/* 1862 */             str2 = paramString1.substring(0, i + 1);
/* 1863 */             str1 = paramString1.substring(i + 1);
/*      */           } 
/*      */         }
/* 1866 */         paramStringBuffer.append(str2);
/* 1867 */         paramStringBuffer.append(quote(str1, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
/*      */       }
/*      */       else {
/*      */         
/* 1871 */         paramStringBuffer.append(quote(paramString1, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
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
/*      */   private void appendSchemeSpecificPart(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5, String paramString6) {
/* 1887 */     if (paramString1 != null) {
/*      */ 
/*      */ 
/*      */       
/* 1891 */       if (paramString1.startsWith("//[")) {
/* 1892 */         int i = paramString1.indexOf("]");
/* 1893 */         if (i != -1 && paramString1.indexOf(":") != -1) {
/*      */           String str1, str2;
/* 1895 */           if (i == paramString1.length()) {
/* 1896 */             str2 = paramString1;
/* 1897 */             str1 = "";
/*      */           } else {
/* 1899 */             str2 = paramString1.substring(0, i + 1);
/* 1900 */             str1 = paramString1.substring(i + 1);
/*      */           } 
/* 1902 */           paramStringBuffer.append(str2);
/* 1903 */           paramStringBuffer.append(quote(str1, L_URIC, H_URIC));
/*      */         } 
/*      */       } else {
/* 1906 */         paramStringBuffer.append(quote(paramString1, L_URIC, H_URIC));
/*      */       } 
/*      */     } else {
/* 1909 */       appendAuthority(paramStringBuffer, paramString2, paramString3, paramString4, paramInt);
/* 1910 */       if (paramString5 != null)
/* 1911 */         paramStringBuffer.append(quote(paramString5, L_PATH, H_PATH)); 
/* 1912 */       if (paramString6 != null) {
/* 1913 */         paramStringBuffer.append('?');
/* 1914 */         paramStringBuffer.append(quote(paramString6, L_URIC, H_URIC));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void appendFragment(StringBuffer paramStringBuffer, String paramString) {
/* 1920 */     if (paramString != null) {
/* 1921 */       paramStringBuffer.append('#');
/* 1922 */       paramStringBuffer.append(quote(paramString, L_URIC, H_URIC));
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
/*      */   private String toString(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, String paramString6, String paramString7, String paramString8) {
/* 1936 */     StringBuffer stringBuffer = new StringBuffer();
/* 1937 */     if (paramString1 != null) {
/* 1938 */       stringBuffer.append(paramString1);
/* 1939 */       stringBuffer.append(':');
/*      */     } 
/* 1941 */     appendSchemeSpecificPart(stringBuffer, paramString2, paramString3, paramString4, paramString5, paramInt, paramString6, paramString7);
/*      */ 
/*      */     
/* 1944 */     appendFragment(stringBuffer, paramString8);
/* 1945 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private void defineSchemeSpecificPart() {
/* 1949 */     if (this.schemeSpecificPart != null)
/* 1950 */       return;  StringBuffer stringBuffer = new StringBuffer();
/* 1951 */     appendSchemeSpecificPart(stringBuffer, null, getAuthority(), getUserInfo(), this.host, this.port, 
/* 1952 */         getPath(), getQuery());
/* 1953 */     if (stringBuffer.length() == 0)
/* 1954 */       return;  this.schemeSpecificPart = stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private void defineString() {
/* 1958 */     if (this.string != null)
/*      */       return; 
/* 1960 */     StringBuffer stringBuffer = new StringBuffer();
/* 1961 */     if (this.scheme != null) {
/* 1962 */       stringBuffer.append(this.scheme);
/* 1963 */       stringBuffer.append(':');
/*      */     } 
/* 1965 */     if (isOpaque()) {
/* 1966 */       stringBuffer.append(this.schemeSpecificPart);
/*      */     } else {
/* 1968 */       if (this.host != null) {
/* 1969 */         stringBuffer.append("//");
/* 1970 */         if (this.userInfo != null) {
/* 1971 */           stringBuffer.append(this.userInfo);
/* 1972 */           stringBuffer.append('@');
/*      */         } 
/*      */ 
/*      */         
/* 1976 */         boolean bool = (this.host.indexOf(':') >= 0 && !this.host.startsWith("[") && !this.host.endsWith("]")) ? true : false;
/* 1977 */         if (bool) stringBuffer.append('['); 
/* 1978 */         stringBuffer.append(this.host);
/* 1979 */         if (bool) stringBuffer.append(']'); 
/* 1980 */         if (this.port != -1) {
/* 1981 */           stringBuffer.append(':');
/* 1982 */           stringBuffer.append(this.port);
/*      */         } 
/* 1984 */       } else if (this.authority != null) {
/* 1985 */         stringBuffer.append("//");
/* 1986 */         stringBuffer.append(this.authority);
/*      */       } 
/* 1988 */       if (this.path != null)
/* 1989 */         stringBuffer.append(this.path); 
/* 1990 */       if (this.query != null) {
/* 1991 */         stringBuffer.append('?');
/* 1992 */         stringBuffer.append(this.query);
/*      */       } 
/*      */     } 
/* 1995 */     if (this.fragment != null) {
/* 1996 */       stringBuffer.append('#');
/* 1997 */       stringBuffer.append(this.fragment);
/*      */     } 
/* 1999 */     this.string = stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String resolvePath(String paramString1, String paramString2, boolean paramBoolean) {
/* 2009 */     int i = paramString1.lastIndexOf('/');
/* 2010 */     int j = paramString2.length();
/* 2011 */     String str = "";
/*      */     
/* 2013 */     if (j == 0) {
/*      */       
/* 2015 */       if (i >= 0)
/* 2016 */         str = paramString1.substring(0, i + 1); 
/*      */     } else {
/* 2018 */       StringBuffer stringBuffer = new StringBuffer(paramString1.length() + j);
/*      */       
/* 2020 */       if (i >= 0) {
/* 2021 */         stringBuffer.append(paramString1.substring(0, i + 1));
/*      */       }
/* 2023 */       stringBuffer.append(paramString2);
/* 2024 */       str = stringBuffer.toString();
/*      */     } 
/*      */ 
/*      */     
/* 2028 */     return normalize(str);
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
/*      */   private static URI resolve(URI paramURI1, URI paramURI2) {
/* 2040 */     if (paramURI2.isOpaque() || paramURI1.isOpaque()) {
/* 2041 */       return paramURI2;
/*      */     }
/*      */     
/* 2044 */     if (paramURI2.scheme == null && paramURI2.authority == null && paramURI2.path
/* 2045 */       .equals("") && paramURI2.fragment != null && paramURI2.query == null) {
/*      */       
/* 2047 */       if (paramURI1.fragment != null && paramURI2.fragment
/* 2048 */         .equals(paramURI1.fragment)) {
/* 2049 */         return paramURI1;
/*      */       }
/* 2051 */       URI uRI1 = new URI();
/* 2052 */       uRI1.scheme = paramURI1.scheme;
/* 2053 */       uRI1.authority = paramURI1.authority;
/* 2054 */       uRI1.userInfo = paramURI1.userInfo;
/* 2055 */       uRI1.host = paramURI1.host;
/* 2056 */       uRI1.port = paramURI1.port;
/* 2057 */       uRI1.path = paramURI1.path;
/* 2058 */       uRI1.fragment = paramURI2.fragment;
/* 2059 */       uRI1.query = paramURI1.query;
/* 2060 */       return uRI1;
/*      */     } 
/*      */ 
/*      */     
/* 2064 */     if (paramURI2.scheme != null) {
/* 2065 */       return paramURI2;
/*      */     }
/* 2067 */     URI uRI = new URI();
/* 2068 */     uRI.scheme = paramURI1.scheme;
/* 2069 */     uRI.query = paramURI2.query;
/* 2070 */     uRI.fragment = paramURI2.fragment;
/*      */ 
/*      */     
/* 2073 */     if (paramURI2.authority == null) {
/* 2074 */       uRI.authority = paramURI1.authority;
/* 2075 */       uRI.host = paramURI1.host;
/* 2076 */       uRI.userInfo = paramURI1.userInfo;
/* 2077 */       uRI.port = paramURI1.port;
/*      */       
/* 2079 */       String str = (paramURI2.path == null) ? "" : paramURI2.path;
/* 2080 */       if (str.length() > 0 && str.charAt(0) == '/') {
/*      */         
/* 2082 */         uRI.path = paramURI2.path;
/*      */       } else {
/*      */         
/* 2085 */         uRI.path = resolvePath(paramURI1.path, str, paramURI1.isAbsolute());
/*      */       } 
/*      */     } else {
/* 2088 */       uRI.authority = paramURI2.authority;
/* 2089 */       uRI.host = paramURI2.host;
/* 2090 */       uRI.userInfo = paramURI2.userInfo;
/* 2091 */       uRI.host = paramURI2.host;
/* 2092 */       uRI.port = paramURI2.port;
/* 2093 */       uRI.path = paramURI2.path;
/*      */     } 
/*      */ 
/*      */     
/* 2097 */     return uRI;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static URI normalize(URI paramURI) {
/* 2104 */     if (paramURI.isOpaque() || paramURI.path == null || paramURI.path.length() == 0) {
/* 2105 */       return paramURI;
/*      */     }
/* 2107 */     String str = normalize(paramURI.path);
/* 2108 */     if (str == paramURI.path) {
/* 2109 */       return paramURI;
/*      */     }
/* 2111 */     URI uRI = new URI();
/* 2112 */     uRI.scheme = paramURI.scheme;
/* 2113 */     uRI.fragment = paramURI.fragment;
/* 2114 */     uRI.authority = paramURI.authority;
/* 2115 */     uRI.userInfo = paramURI.userInfo;
/* 2116 */     uRI.host = paramURI.host;
/* 2117 */     uRI.port = paramURI.port;
/* 2118 */     uRI.path = str;
/* 2119 */     uRI.query = paramURI.query;
/* 2120 */     return uRI;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static URI relativize(URI paramURI1, URI paramURI2) {
/* 2131 */     if (paramURI2.isOpaque() || paramURI1.isOpaque())
/* 2132 */       return paramURI2; 
/* 2133 */     if (!equalIgnoringCase(paramURI1.scheme, paramURI2.scheme) || 
/* 2134 */       !equal(paramURI1.authority, paramURI2.authority)) {
/* 2135 */       return paramURI2;
/*      */     }
/* 2137 */     String str1 = normalize(paramURI1.path);
/* 2138 */     String str2 = normalize(paramURI2.path);
/* 2139 */     if (!str1.equals(str2)) {
/* 2140 */       if (!str1.endsWith("/"))
/* 2141 */         str1 = str1 + "/"; 
/* 2142 */       if (!str2.startsWith(str1)) {
/* 2143 */         return paramURI2;
/*      */       }
/*      */     } 
/* 2146 */     URI uRI = new URI();
/* 2147 */     uRI.path = str2.substring(str1.length());
/* 2148 */     uRI.query = paramURI2.query;
/* 2149 */     uRI.fragment = paramURI2.fragment;
/* 2150 */     return uRI;
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
/*      */   private static int needsNormalization(String paramString) {
/* 2179 */     boolean bool = true;
/* 2180 */     byte b1 = 0;
/* 2181 */     int i = paramString.length() - 1;
/* 2182 */     byte b2 = 0;
/*      */ 
/*      */     
/* 2185 */     while (b2 <= i && 
/* 2186 */       paramString.charAt(b2) == '/') {
/* 2187 */       b2++;
/*      */     }
/* 2189 */     if (b2 > 1) bool = false;
/*      */ 
/*      */     
/* 2192 */     label34: while (b2 <= i) {
/*      */ 
/*      */       
/* 2195 */       if (paramString.charAt(b2) == '.' && (b2 == i || paramString
/*      */         
/* 2197 */         .charAt(b2 + 1) == '/' || (paramString
/* 2198 */         .charAt(b2 + 1) == '.' && (b2 + 1 == i || paramString
/*      */         
/* 2200 */         .charAt(b2 + 2) == '/')))) {
/* 2201 */         bool = false;
/*      */       }
/* 2203 */       b1++;
/*      */ 
/*      */       
/* 2206 */       while (b2 <= i) {
/* 2207 */         if (paramString.charAt(b2++) != '/') {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         continue label34;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2221 */     return bool ? -1 : b1;
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
/*      */   private static void split(char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 2236 */     int i = paramArrayOfchar.length - 1;
/* 2237 */     byte b1 = 0;
/* 2238 */     byte b2 = 0;
/*      */ 
/*      */     
/* 2241 */     while (b1 <= i && 
/* 2242 */       paramArrayOfchar[b1] == '/') {
/* 2243 */       paramArrayOfchar[b1] = Character.MIN_VALUE;
/* 2244 */       b1++;
/*      */     } 
/*      */     
/* 2247 */     while (b1 <= i) {
/*      */ 
/*      */       
/* 2250 */       paramArrayOfint[b2++] = b1++;
/*      */ 
/*      */       
/* 2253 */       label22: while (b1 <= i) {
/* 2254 */         if (paramArrayOfchar[b1++] != '/')
/*      */           continue; 
/* 2256 */         paramArrayOfchar[b1 - 1] = Character.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         break label22;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2267 */     if (b2 != paramArrayOfint.length) {
/* 2268 */       throw new InternalError();
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
/*      */   private static int join(char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 2285 */     int i = paramArrayOfint.length;
/* 2286 */     int j = paramArrayOfchar.length - 1;
/* 2287 */     byte b1 = 0;
/*      */     
/* 2289 */     if (paramArrayOfchar[b1] == '\000')
/*      */     {
/* 2291 */       paramArrayOfchar[b1++] = '/';
/*      */     }
/*      */     
/* 2294 */     for (byte b2 = 0; b2 < i; b2++) {
/* 2295 */       int k = paramArrayOfint[b2];
/* 2296 */       if (k != -1)
/*      */       {
/*      */ 
/*      */         
/* 2300 */         if (b1 == k) {
/*      */           
/* 2302 */           while (b1 <= j && paramArrayOfchar[b1] != '\000')
/* 2303 */             b1++; 
/* 2304 */           if (b1 <= j)
/*      */           {
/* 2306 */             paramArrayOfchar[b1++] = '/';
/*      */           }
/* 2308 */         } else if (b1 < k) {
/*      */           
/* 2310 */           while (k <= j && paramArrayOfchar[k] != '\000')
/* 2311 */             paramArrayOfchar[b1++] = paramArrayOfchar[k++]; 
/* 2312 */           if (k <= j)
/*      */           {
/* 2314 */             paramArrayOfchar[b1++] = '/';
/*      */           }
/*      */         } else {
/* 2317 */           throw new InternalError();
/*      */         }  } 
/*      */     } 
/* 2320 */     return b1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void removeDots(char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 2328 */     int i = paramArrayOfint.length;
/* 2329 */     int j = paramArrayOfchar.length - 1;
/*      */     
/* 2331 */     for (byte b = 0; b < i; b++) {
/* 2332 */       byte b1 = 0;
/*      */ 
/*      */       
/*      */       do {
/* 2336 */         int k = paramArrayOfint[b];
/* 2337 */         if (paramArrayOfchar[k] != '.')
/* 2338 */           continue;  if (k == j) {
/* 2339 */           b1 = 1; break;
/*      */         } 
/* 2341 */         if (paramArrayOfchar[k + 1] == '\000') {
/* 2342 */           b1 = 1; break;
/*      */         } 
/* 2344 */         if (paramArrayOfchar[k + 1] == '.' && (k + 1 == j || paramArrayOfchar[k + 2] == '\000')) {
/*      */ 
/*      */           
/* 2347 */           b1 = 2;
/*      */           
/*      */           break;
/*      */         } 
/* 2351 */         ++b;
/* 2352 */       } while (b < i);
/* 2353 */       if (b > i || b1 == 0) {
/*      */         break;
/*      */       }
/* 2356 */       if (b1 == 1) {
/*      */         
/* 2358 */         paramArrayOfint[b] = -1;
/*      */       } else {
/*      */         int k;
/*      */ 
/*      */ 
/*      */         
/* 2364 */         for (k = b - 1; k >= 0 && 
/* 2365 */           paramArrayOfint[k] == -1; k--);
/*      */         
/* 2367 */         if (k >= 0) {
/* 2368 */           int m = paramArrayOfint[k];
/* 2369 */           if (paramArrayOfchar[m] != '.' || paramArrayOfchar[m + 1] != '.' || paramArrayOfchar[m + 2] != '\000') {
/*      */ 
/*      */             
/* 2372 */             paramArrayOfint[b] = -1;
/* 2373 */             paramArrayOfint[k] = -1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void maybeAddLeadingDot(char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 2386 */     if (paramArrayOfchar[0] == '\000') {
/*      */       return;
/*      */     }
/*      */     
/* 2390 */     int i = paramArrayOfint.length;
/* 2391 */     byte b = 0;
/* 2392 */     while (b < i && 
/* 2393 */       paramArrayOfint[b] < 0)
/*      */     {
/* 2395 */       b++;
/*      */     }
/* 2397 */     if (b >= i || b == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2402 */     int j = paramArrayOfint[b];
/* 2403 */     for (; j < paramArrayOfchar.length && paramArrayOfchar[j] != ':' && paramArrayOfchar[j] != '\000'; j++);
/* 2404 */     if (j >= paramArrayOfchar.length || paramArrayOfchar[j] == '\000') {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2410 */     paramArrayOfchar[0] = '.';
/* 2411 */     paramArrayOfchar[1] = Character.MIN_VALUE;
/* 2412 */     paramArrayOfint[0] = 0;
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
/*      */   private static String normalize(String paramString) {
/* 2425 */     int i = needsNormalization(paramString);
/* 2426 */     if (i < 0)
/*      */     {
/* 2428 */       return paramString;
/*      */     }
/* 2430 */     char[] arrayOfChar = paramString.toCharArray();
/*      */ 
/*      */     
/* 2433 */     int[] arrayOfInt = new int[i];
/* 2434 */     split(arrayOfChar, arrayOfInt);
/*      */ 
/*      */     
/* 2437 */     removeDots(arrayOfChar, arrayOfInt);
/*      */ 
/*      */     
/* 2440 */     maybeAddLeadingDot(arrayOfChar, arrayOfInt);
/*      */ 
/*      */     
/* 2443 */     String str = new String(arrayOfChar, 0, join(arrayOfChar, arrayOfInt));
/* 2444 */     if (str.equals(paramString))
/*      */     {
/* 2446 */       return paramString;
/*      */     }
/* 2448 */     return str;
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
/*      */   private static long lowMask(String paramString) {
/* 2469 */     int i = paramString.length();
/* 2470 */     long l = 0L;
/* 2471 */     for (byte b = 0; b < i; b++) {
/* 2472 */       char c = paramString.charAt(b);
/* 2473 */       if (c < '@')
/* 2474 */         l |= 1L << c; 
/*      */     } 
/* 2476 */     return l;
/*      */   }
/*      */ 
/*      */   
/*      */   private static long highMask(String paramString) {
/* 2481 */     int i = paramString.length();
/* 2482 */     long l = 0L;
/* 2483 */     for (byte b = 0; b < i; b++) {
/* 2484 */       char c = paramString.charAt(b);
/* 2485 */       if (c >= '@' && c < '')
/* 2486 */         l |= 1L << c - 64; 
/*      */     } 
/* 2488 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static long lowMask(char paramChar1, char paramChar2) {
/* 2494 */     long l = 0L;
/* 2495 */     int i = Math.max(Math.min(paramChar1, 63), 0);
/* 2496 */     int j = Math.max(Math.min(paramChar2, 63), 0);
/* 2497 */     for (int k = i; k <= j; k++)
/* 2498 */       l |= 1L << k; 
/* 2499 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static long highMask(char paramChar1, char paramChar2) {
/* 2505 */     long l = 0L;
/* 2506 */     int i = Math.max(Math.min(paramChar1, 127), 64) - 64;
/* 2507 */     int j = Math.max(Math.min(paramChar2, 127), 64) - 64;
/* 2508 */     for (int k = i; k <= j; k++)
/* 2509 */       l |= 1L << k; 
/* 2510 */     return l;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean match(char paramChar, long paramLong1, long paramLong2) {
/* 2515 */     if (paramChar == '\000')
/* 2516 */       return false; 
/* 2517 */     if (paramChar < '@')
/* 2518 */       return ((1L << paramChar & paramLong1) != 0L); 
/* 2519 */     if (paramChar < '')
/* 2520 */       return ((1L << paramChar - 64 & paramLong2) != 0L); 
/* 2521 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2529 */   private static final long L_DIGIT = lowMask('0', '9');
/*      */ 
/*      */   
/*      */   private static final long H_DIGIT = 0L;
/*      */   
/*      */   private static final long L_UPALPHA = 0L;
/*      */   
/* 2536 */   private static final long H_UPALPHA = highMask('A', 'Z');
/*      */ 
/*      */   
/*      */   private static final long L_LOWALPHA = 0L;
/*      */ 
/*      */   
/* 2542 */   private static final long H_LOWALPHA = highMask('a', 'z');
/*      */   
/*      */   private static final long L_ALPHA = 0L;
/*      */   
/* 2546 */   private static final long H_ALPHA = H_LOWALPHA | H_UPALPHA;
/*      */ 
/*      */   
/* 2549 */   private static final long L_ALPHANUM = L_DIGIT | 0x0L;
/* 2550 */   private static final long H_ALPHANUM = 0x0L | H_ALPHA;
/*      */ 
/*      */ 
/*      */   
/* 2554 */   private static final long L_HEX = L_DIGIT;
/* 2555 */   private static final long H_HEX = highMask('A', 'F') | highMask('a', 'f');
/*      */ 
/*      */ 
/*      */   
/* 2559 */   private static final long L_MARK = lowMask("-_.!~*'()");
/* 2560 */   private static final long H_MARK = highMask("-_.!~*'()");
/*      */ 
/*      */   
/* 2563 */   private static final long L_UNRESERVED = L_ALPHANUM | L_MARK;
/* 2564 */   private static final long H_UNRESERVED = H_ALPHANUM | H_MARK;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2569 */   private static final long L_RESERVED = lowMask(";/?:@&=+$,[]");
/* 2570 */   private static final long H_RESERVED = highMask(";/?:@&=+$,[]");
/*      */ 
/*      */   
/*      */   private static final long L_ESCAPED = 1L;
/*      */ 
/*      */   
/*      */   private static final long H_ESCAPED = 0L;
/*      */   
/* 2578 */   private static final long L_URIC = L_RESERVED | L_UNRESERVED | 0x1L;
/* 2579 */   private static final long H_URIC = H_RESERVED | H_UNRESERVED | 0x0L;
/*      */ 
/*      */ 
/*      */   
/* 2583 */   private static final long L_PCHAR = L_UNRESERVED | 0x1L | 
/* 2584 */     lowMask(":@&=+$,");
/* 2585 */   private static final long H_PCHAR = H_UNRESERVED | 0x0L | 
/* 2586 */     highMask(":@&=+$,");
/*      */ 
/*      */   
/* 2589 */   private static final long L_PATH = L_PCHAR | lowMask(";/");
/* 2590 */   private static final long H_PATH = H_PCHAR | highMask(";/");
/*      */ 
/*      */   
/* 2593 */   private static final long L_DASH = lowMask("-");
/* 2594 */   private static final long H_DASH = highMask("-");
/*      */ 
/*      */   
/* 2597 */   private static final long L_DOT = lowMask(".");
/* 2598 */   private static final long H_DOT = highMask(".");
/*      */ 
/*      */ 
/*      */   
/* 2602 */   private static final long L_USERINFO = L_UNRESERVED | 0x1L | 
/* 2603 */     lowMask(";:&=+$,");
/* 2604 */   private static final long H_USERINFO = H_UNRESERVED | 0x0L | 
/* 2605 */     highMask(";:&=+$,");
/*      */ 
/*      */ 
/*      */   
/* 2609 */   private static final long L_REG_NAME = L_UNRESERVED | 0x1L | 
/* 2610 */     lowMask("$,;:@&=+");
/* 2611 */   private static final long H_REG_NAME = H_UNRESERVED | 0x0L | 
/* 2612 */     highMask("$,;:@&=+");
/*      */ 
/*      */   
/* 2615 */   private static final long L_SERVER = L_USERINFO | L_ALPHANUM | L_DASH | 
/* 2616 */     lowMask(".:@[]");
/* 2617 */   private static final long H_SERVER = H_USERINFO | H_ALPHANUM | H_DASH | 
/* 2618 */     highMask(".:@[]");
/*      */ 
/*      */ 
/*      */   
/* 2622 */   private static final long L_SERVER_PERCENT = L_SERVER | 
/* 2623 */     lowMask("%");
/* 2624 */   private static final long H_SERVER_PERCENT = H_SERVER | 
/* 2625 */     highMask("%");
/* 2626 */   private static final long L_LEFT_BRACKET = lowMask("[");
/* 2627 */   private static final long H_LEFT_BRACKET = highMask("[");
/*      */ 
/*      */   
/* 2630 */   private static final long L_SCHEME = 0x0L | L_DIGIT | lowMask("+-.");
/* 2631 */   private static final long H_SCHEME = H_ALPHA | 0x0L | highMask("+-.");
/*      */ 
/*      */ 
/*      */   
/* 2635 */   private static final long L_URIC_NO_SLASH = L_UNRESERVED | 0x1L | 
/* 2636 */     lowMask(";?:@&=+$,");
/* 2637 */   private static final long H_URIC_NO_SLASH = H_UNRESERVED | 0x0L | 
/* 2638 */     highMask(";?:@&=+$,");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2643 */   private static final char[] hexDigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void appendEscape(StringBuffer paramStringBuffer, byte paramByte) {
/* 2649 */     paramStringBuffer.append('%');
/* 2650 */     paramStringBuffer.append(hexDigits[paramByte >> 4 & 0xF]);
/* 2651 */     paramStringBuffer.append(hexDigits[paramByte >> 0 & 0xF]);
/*      */   }
/*      */   
/*      */   private static void appendEncoded(StringBuffer paramStringBuffer, char paramChar) {
/* 2655 */     ByteBuffer byteBuffer = null;
/*      */     
/*      */     try {
/* 2658 */       byteBuffer = ThreadLocalCoders.encoderFor("UTF-8").encode(CharBuffer.wrap("" + paramChar));
/* 2659 */     } catch (CharacterCodingException characterCodingException) {
/*      */       assert false;
/*      */     } 
/* 2662 */     while (byteBuffer.hasRemaining()) {
/* 2663 */       int i = byteBuffer.get() & 0xFF;
/* 2664 */       if (i >= 128) {
/* 2665 */         appendEscape(paramStringBuffer, (byte)i); continue;
/*      */       } 
/* 2667 */       paramStringBuffer.append((char)i);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String quote(String paramString, long paramLong1, long paramLong2) {
/* 2675 */     int i = paramString.length();
/* 2676 */     StringBuffer stringBuffer = null;
/* 2677 */     boolean bool = ((paramLong1 & 0x1L) != 0L) ? true : false;
/* 2678 */     for (byte b = 0; b < paramString.length(); b++) {
/* 2679 */       char c = paramString.charAt(b);
/* 2680 */       if (c < '') {
/* 2681 */         if (!match(c, paramLong1, paramLong2)) {
/* 2682 */           if (stringBuffer == null) {
/* 2683 */             stringBuffer = new StringBuffer();
/* 2684 */             stringBuffer.append(paramString.substring(0, b));
/*      */           } 
/* 2686 */           appendEscape(stringBuffer, (byte)c);
/*      */         }
/* 2688 */         else if (stringBuffer != null) {
/* 2689 */           stringBuffer.append(c);
/*      */         } 
/* 2691 */       } else if (bool && (
/* 2692 */         Character.isSpaceChar(c) || 
/* 2693 */         Character.isISOControl(c))) {
/* 2694 */         if (stringBuffer == null) {
/* 2695 */           stringBuffer = new StringBuffer();
/* 2696 */           stringBuffer.append(paramString.substring(0, b));
/*      */         } 
/* 2698 */         appendEncoded(stringBuffer, c);
/*      */       }
/* 2700 */       else if (stringBuffer != null) {
/* 2701 */         stringBuffer.append(c);
/*      */       } 
/*      */     } 
/* 2704 */     return (stringBuffer == null) ? paramString : stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String encode(String paramString) {
/* 2711 */     int i = paramString.length();
/* 2712 */     if (i == 0) {
/* 2713 */       return paramString;
/*      */     }
/*      */     
/* 2716 */     byte b = 0;
/* 2717 */     while (paramString.charAt(b) < '') {
/*      */       
/* 2719 */       if (++b >= i) {
/* 2720 */         return paramString;
/*      */       }
/*      */     } 
/* 2723 */     String str = Normalizer.normalize(paramString, Normalizer.Form.NFC);
/* 2724 */     ByteBuffer byteBuffer = null;
/*      */     
/*      */     try {
/* 2727 */       byteBuffer = ThreadLocalCoders.encoderFor("UTF-8").encode(CharBuffer.wrap(str));
/* 2728 */     } catch (CharacterCodingException characterCodingException) {
/*      */       assert false;
/*      */     } 
/*      */     
/* 2732 */     StringBuffer stringBuffer = new StringBuffer();
/* 2733 */     while (byteBuffer.hasRemaining()) {
/* 2734 */       int j = byteBuffer.get() & 0xFF;
/* 2735 */       if (j >= 128) {
/* 2736 */         appendEscape(stringBuffer, (byte)j); continue;
/*      */       } 
/* 2738 */       stringBuffer.append((char)j);
/*      */     } 
/* 2740 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private static int decode(char paramChar) {
/* 2744 */     if (paramChar >= '0' && paramChar <= '9')
/* 2745 */       return paramChar - 48; 
/* 2746 */     if (paramChar >= 'a' && paramChar <= 'f')
/* 2747 */       return paramChar - 97 + 10; 
/* 2748 */     if (paramChar >= 'A' && paramChar <= 'F')
/* 2749 */       return paramChar - 65 + 10; 
/*      */     assert false;
/* 2751 */     return -1;
/*      */   }
/*      */   
/*      */   private static byte decode(char paramChar1, char paramChar2) {
/* 2755 */     return 
/* 2756 */       (byte)((decode(paramChar1) & 0xF) << 4 | (decode(paramChar2) & 0xF) << 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String decode(String paramString) {
/* 2767 */     if (paramString == null)
/* 2768 */       return paramString; 
/* 2769 */     int i = paramString.length();
/* 2770 */     if (i == 0)
/* 2771 */       return paramString; 
/* 2772 */     if (paramString.indexOf('%') < 0) {
/* 2773 */       return paramString;
/*      */     }
/* 2775 */     StringBuffer stringBuffer = new StringBuffer(i);
/* 2776 */     ByteBuffer byteBuffer = ByteBuffer.allocate(i);
/* 2777 */     CharBuffer charBuffer = CharBuffer.allocate(i);
/*      */ 
/*      */     
/* 2780 */     CharsetDecoder charsetDecoder = ThreadLocalCoders.decoderFor("UTF-8").onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*      */ 
/*      */     
/* 2783 */     char c = paramString.charAt(0);
/* 2784 */     boolean bool = false;
/*      */     
/* 2786 */     for (byte b = 0; b < i; ) {
/* 2787 */       assert c == paramString.charAt(b);
/* 2788 */       if (c == '[') {
/* 2789 */         bool = true;
/* 2790 */       } else if (bool && c == ']') {
/* 2791 */         bool = false;
/*      */       } 
/* 2793 */       if (c != '%' || bool) {
/* 2794 */         stringBuffer.append(c);
/* 2795 */         if (++b >= i)
/*      */           break; 
/* 2797 */         c = paramString.charAt(b);
/*      */         continue;
/*      */       } 
/* 2800 */       byteBuffer.clear();
/* 2801 */       byte b1 = b;
/*      */       do {
/* 2803 */         assert i - b >= 2;
/* 2804 */         byteBuffer.put(decode(paramString.charAt(++b), paramString.charAt(++b)));
/* 2805 */         if (++b >= i)
/*      */           break; 
/* 2807 */         c = paramString.charAt(b);
/* 2808 */       } while (c == '%');
/*      */ 
/*      */       
/* 2811 */       byteBuffer.flip();
/* 2812 */       charBuffer.clear();
/* 2813 */       charsetDecoder.reset();
/* 2814 */       CoderResult coderResult = charsetDecoder.decode(byteBuffer, charBuffer, true);
/* 2815 */       assert coderResult.isUnderflow();
/* 2816 */       coderResult = charsetDecoder.flush(charBuffer);
/* 2817 */       assert coderResult.isUnderflow();
/* 2818 */       stringBuffer.append(charBuffer.flip().toString());
/*      */     } 
/*      */     
/* 2821 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private URI() {}
/*      */ 
/*      */ 
/*      */   
/*      */   private class Parser
/*      */   {
/*      */     private String input;
/*      */ 
/*      */     
/*      */     private boolean requireServerAuthority = false;
/*      */ 
/*      */     
/*      */     private int ipv6byteCount;
/*      */ 
/*      */ 
/*      */     
/*      */     private void fail(String param1String) throws URISyntaxException {
/* 2844 */       throw new URISyntaxException(this.input, param1String);
/*      */     }
/*      */     
/*      */     private void fail(String param1String, int param1Int) throws URISyntaxException {
/* 2848 */       throw new URISyntaxException(this.input, param1String, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void failExpecting(String param1String, int param1Int) throws URISyntaxException {
/* 2854 */       fail("Expected " + param1String, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void failExpecting(String param1String1, String param1String2, int param1Int) throws URISyntaxException {
/* 2860 */       fail("Expected " + param1String1 + " following " + param1String2, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String substring(int param1Int1, int param1Int2) {
/* 2869 */       return this.input.substring(param1Int1, param1Int2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private char charAt(int param1Int) {
/* 2876 */       return this.input.charAt(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean at(int param1Int1, int param1Int2, char param1Char) {
/* 2882 */       return (param1Int1 < param1Int2 && charAt(param1Int1) == param1Char);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean at(int param1Int1, int param1Int2, String param1String) {
/* 2889 */       int i = param1Int1;
/* 2890 */       int j = param1String.length();
/* 2891 */       if (j > param1Int2 - i)
/* 2892 */         return false; 
/* 2893 */       byte b = 0;
/* 2894 */       while (b < j && 
/* 2895 */         charAt(i++) == param1String.charAt(b))
/*      */       {
/*      */         
/* 2898 */         b++;
/*      */       }
/* 2900 */       return (b == j);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int scan(int param1Int1, int param1Int2, char param1Char) {
/* 2935 */       if (param1Int1 < param1Int2 && charAt(param1Int1) == param1Char)
/* 2936 */         return param1Int1 + 1; 
/* 2937 */       return param1Int1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int scan(int param1Int1, int param1Int2, String param1String1, String param1String2) {
/* 2948 */       int i = param1Int1;
/* 2949 */       while (i < param1Int2) {
/* 2950 */         char c = charAt(i);
/* 2951 */         if (param1String1.indexOf(c) >= 0)
/* 2952 */           return -1; 
/* 2953 */         if (param1String2.indexOf(c) >= 0)
/*      */           break; 
/* 2955 */         i++;
/*      */       } 
/* 2957 */       return i;
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
/*      */     private int scanEscape(int param1Int1, int param1Int2, char param1Char) throws URISyntaxException {
/* 2969 */       int i = param1Int1;
/* 2970 */       char c = param1Char;
/* 2971 */       if (c == '%') {
/*      */         
/* 2973 */         if (i + 3 <= param1Int2 && URI
/* 2974 */           .match(charAt(i + 1), URI.L_HEX, URI.H_HEX) && URI
/* 2975 */           .match(charAt(i + 2), URI.L_HEX, URI.H_HEX)) {
/* 2976 */           return i + 3;
/*      */         }
/* 2978 */         fail("Malformed escape pair", i);
/* 2979 */       } else if (c > '' && 
/* 2980 */         !Character.isSpaceChar(c) && 
/* 2981 */         !Character.isISOControl(c)) {
/*      */         
/* 2983 */         return i + 1;
/*      */       } 
/* 2985 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int scan(int param1Int1, int param1Int2, long param1Long1, long param1Long2) throws URISyntaxException {
/* 2993 */       int i = param1Int1;
/* 2994 */       while (i < param1Int2) {
/* 2995 */         char c = charAt(i);
/* 2996 */         if (URI.match(c, param1Long1, param1Long2)) {
/* 2997 */           i++;
/*      */           continue;
/*      */         } 
/* 3000 */         if ((param1Long1 & 0x1L) != 0L) {
/* 3001 */           int j = scanEscape(i, param1Int2, c);
/* 3002 */           if (j > i) {
/* 3003 */             i = j;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3009 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void checkChars(int param1Int1, int param1Int2, long param1Long1, long param1Long2, String param1String) throws URISyntaxException {
/* 3019 */       int i = scan(param1Int1, param1Int2, param1Long1, param1Long2);
/* 3020 */       if (i < param1Int2) {
/* 3021 */         fail("Illegal character in " + param1String, i);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void checkChar(int param1Int, long param1Long1, long param1Long2, String param1String) throws URISyntaxException {
/* 3031 */       checkChars(param1Int, param1Int + 1, param1Long1, param1Long2, param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void parse(boolean param1Boolean) throws URISyntaxException {
/*      */       boolean bool;
/* 3040 */       this.requireServerAuthority = param1Boolean;
/*      */       
/* 3042 */       int i = this.input.length();
/* 3043 */       int j = scan(0, i, "/?#", ":");
/* 3044 */       if (j >= 0 && at(j, i, ':')) {
/* 3045 */         if (j == 0)
/* 3046 */           failExpecting("scheme name", 0); 
/* 3047 */         checkChar(0, 0L, URI.H_ALPHA, "scheme name");
/* 3048 */         checkChars(1, j, URI.L_SCHEME, URI.H_SCHEME, "scheme name");
/* 3049 */         URI.this.scheme = substring(0, j);
/*      */         
/* 3051 */         bool = ++j;
/* 3052 */         if (at(j, i, '/')) {
/* 3053 */           j = parseHierarchical(j, i);
/*      */         } else {
/* 3055 */           int k = scan(j, i, "", "#");
/* 3056 */           if (k <= j)
/* 3057 */             failExpecting("scheme-specific part", j); 
/* 3058 */           checkChars(j, k, URI.L_URIC, URI.H_URIC, "opaque part");
/* 3059 */           j = k;
/*      */         } 
/*      */       } else {
/* 3062 */         bool = false;
/* 3063 */         j = parseHierarchical(0, i);
/*      */       } 
/* 3065 */       URI.this.schemeSpecificPart = substring(bool, j);
/* 3066 */       if (at(j, i, '#')) {
/* 3067 */         checkChars(j + 1, i, URI.L_URIC, URI.H_URIC, "fragment");
/* 3068 */         URI.this.fragment = substring(j + 1, i);
/* 3069 */         j = i;
/*      */       } 
/* 3071 */       if (j < i) {
/* 3072 */         fail("end of URI", j);
/*      */       }
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
/*      */ 
/*      */ 
/*      */     
/*      */     private int parseHierarchical(int param1Int1, int param1Int2) throws URISyntaxException {
/* 3092 */       int i = param1Int1;
/* 3093 */       if (at(i, param1Int2, '/') && at(i + 1, param1Int2, '/')) {
/* 3094 */         i += 2;
/* 3095 */         int k = scan(i, param1Int2, "", "/?#");
/* 3096 */         if (k > i) {
/* 3097 */           i = parseAuthority(i, k);
/* 3098 */         } else if (k >= param1Int2) {
/*      */ 
/*      */ 
/*      */           
/* 3102 */           failExpecting("authority", i);
/*      */         } 
/* 3104 */       }  int j = scan(i, param1Int2, "", "?#");
/* 3105 */       checkChars(i, j, URI.L_PATH, URI.H_PATH, "path");
/* 3106 */       URI.this.path = substring(i, j);
/* 3107 */       i = j;
/* 3108 */       if (at(i, param1Int2, '?')) {
/* 3109 */         i++;
/* 3110 */         j = scan(i, param1Int2, "", "#");
/* 3111 */         checkChars(i, j, URI.L_URIC, URI.H_URIC, "query");
/* 3112 */         URI.this.query = substring(i, j);
/* 3113 */         i = j;
/*      */       } 
/* 3115 */       return i;
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
/*      */     private int parseAuthority(int param1Int1, int param1Int2) throws URISyntaxException {
/*      */       boolean bool1;
/* 3129 */       int i = param1Int1;
/* 3130 */       int j = i;
/* 3131 */       URISyntaxException uRISyntaxException = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3136 */       if (scan(i, param1Int2, "", "]") > i) {
/*      */         
/* 3138 */         bool1 = (scan(i, param1Int2, URI.L_SERVER_PERCENT, URI.H_SERVER_PERCENT) == param1Int2) ? true : false;
/*      */       } else {
/* 3140 */         bool1 = (scan(i, param1Int2, URI.L_SERVER, URI.H_SERVER) == param1Int2) ? true : false;
/*      */       } 
/* 3142 */       boolean bool2 = (scan(i, param1Int2, URI.L_REG_NAME, URI.H_REG_NAME) == param1Int2) ? true : false;
/*      */       
/* 3144 */       if (bool2 && !bool1) {
/*      */         
/* 3146 */         URI.this.authority = substring(i, param1Int2);
/* 3147 */         return param1Int2;
/*      */       } 
/*      */       
/* 3150 */       if (bool1) {
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 3155 */           j = parseServer(i, param1Int2);
/* 3156 */           if (j < param1Int2)
/* 3157 */             failExpecting("end of authority", j); 
/* 3158 */           URI.this.authority = substring(i, param1Int2);
/* 3159 */         } catch (URISyntaxException uRISyntaxException1) {
/*      */           
/* 3161 */           URI.this.userInfo = null;
/* 3162 */           URI.this.host = null;
/* 3163 */           URI.this.port = -1;
/* 3164 */           if (this.requireServerAuthority)
/*      */           {
/*      */             
/* 3167 */             throw uRISyntaxException1;
/*      */           }
/*      */ 
/*      */           
/* 3171 */           uRISyntaxException = uRISyntaxException1;
/* 3172 */           j = i;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 3177 */       if (j < param1Int2) {
/* 3178 */         if (bool2)
/*      */         
/* 3180 */         { URI.this.authority = substring(i, param1Int2); }
/* 3181 */         else { if (uRISyntaxException != null)
/*      */           {
/*      */             
/* 3184 */             throw uRISyntaxException;
/*      */           }
/* 3186 */           fail("Illegal character in authority", j); }
/*      */       
/*      */       }
/*      */       
/* 3190 */       return param1Int2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int parseServer(int param1Int1, int param1Int2) throws URISyntaxException {
/* 3199 */       int i = param1Int1;
/*      */ 
/*      */ 
/*      */       
/* 3203 */       int j = scan(i, param1Int2, "/?#", "@");
/* 3204 */       if (j >= i && at(j, param1Int2, '@')) {
/* 3205 */         checkChars(i, j, URI.L_USERINFO, URI.H_USERINFO, "user info");
/* 3206 */         URI.this.userInfo = substring(i, j);
/* 3207 */         i = j + 1;
/*      */       } 
/*      */ 
/*      */       
/* 3211 */       if (at(i, param1Int2, '[')) {
/*      */         
/* 3213 */         i++;
/* 3214 */         j = scan(i, param1Int2, "/?#", "]");
/* 3215 */         if (j > i && at(j, param1Int2, ']')) {
/*      */           
/* 3217 */           int k = scan(i, j, "", "%");
/* 3218 */           if (k > i) {
/* 3219 */             parseIPv6Reference(i, k);
/* 3220 */             if (k + 1 == j) {
/* 3221 */               fail("scope id expected");
/*      */             }
/* 3223 */             checkChars(k + 1, j, URI.L_ALPHANUM, URI.H_ALPHANUM, "scope id");
/*      */           } else {
/*      */             
/* 3226 */             parseIPv6Reference(i, j);
/*      */           } 
/* 3228 */           URI.this.host = substring(i - 1, j + 1);
/* 3229 */           i = j + 1;
/*      */         } else {
/* 3231 */           failExpecting("closing bracket for IPv6 address", j);
/*      */         } 
/*      */       } else {
/* 3234 */         j = parseIPv4Address(i, param1Int2);
/* 3235 */         if (j <= i)
/* 3236 */           j = parseHostname(i, param1Int2); 
/* 3237 */         i = j;
/*      */       } 
/*      */ 
/*      */       
/* 3241 */       if (at(i, param1Int2, ':')) {
/* 3242 */         i++;
/* 3243 */         j = scan(i, param1Int2, "", "/");
/* 3244 */         if (j > i) {
/* 3245 */           checkChars(i, j, URI.L_DIGIT, 0L, "port number");
/*      */           try {
/* 3247 */             URI.this.port = Integer.parseInt(substring(i, j));
/* 3248 */           } catch (NumberFormatException numberFormatException) {
/* 3249 */             fail("Malformed port number", i);
/*      */           } 
/* 3251 */           i = j;
/*      */         } 
/*      */       } 
/* 3254 */       if (i < param1Int2) {
/* 3255 */         failExpecting("port number", i);
/*      */       }
/* 3257 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int scanByte(int param1Int1, int param1Int2) throws URISyntaxException {
/* 3265 */       int i = param1Int1;
/* 3266 */       int j = scan(i, param1Int2, URI.L_DIGIT, 0L);
/* 3267 */       if (j <= i) return j; 
/* 3268 */       if (Integer.parseInt(substring(i, j)) > 255) return i; 
/* 3269 */       return j;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int scanIPv4Address(int param1Int1, int param1Int2, boolean param1Boolean) throws URISyntaxException {
/* 3290 */       int i = param1Int1;
/*      */       
/* 3292 */       int k = scan(i, param1Int2, URI.L_DIGIT | URI.L_DOT, 0x0L | URI.H_DOT);
/* 3293 */       if (k <= i || (param1Boolean && k != param1Int2)) {
/* 3294 */         return -1;
/*      */       }
/*      */ 
/*      */       
/* 3298 */       i = j;
/* 3299 */       i = j;
/* 3300 */       i = j;
/* 3301 */       i = j;
/* 3302 */       i = j;
/* 3303 */       i = j; int j; if ((j = scanByte(i, k)) > i && (j = scan(i, k, '.')) > i && (j = scanByte(i, k)) > i && (j = scan(i, k, '.')) > i && (j = scanByte(i, k)) > i && (j = scan(i, k, '.')) > i && (
/* 3304 */         j = scanByte(i, k)) > i) { i = j;
/* 3305 */         if (j >= k)
/* 3306 */           return j;  }
/*      */       
/* 3308 */       fail("Malformed IPv4 address", j);
/* 3309 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int takeIPv4Address(int param1Int1, int param1Int2, String param1String) throws URISyntaxException {
/* 3318 */       int i = scanIPv4Address(param1Int1, param1Int2, true);
/* 3319 */       if (i <= param1Int1)
/* 3320 */         failExpecting(param1String, param1Int1); 
/* 3321 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int parseIPv4Address(int param1Int1, int param1Int2) {
/*      */       int i;
/*      */       try {
/* 3332 */         i = scanIPv4Address(param1Int1, param1Int2, false);
/* 3333 */       } catch (URISyntaxException uRISyntaxException) {
/* 3334 */         return -1;
/* 3335 */       } catch (NumberFormatException numberFormatException) {
/* 3336 */         return -1;
/*      */       } 
/*      */       
/* 3339 */       if (i > param1Int1 && i < param1Int2)
/*      */       {
/*      */ 
/*      */         
/* 3343 */         if (charAt(i) != ':') {
/* 3344 */           i = -1;
/*      */         }
/*      */       }
/*      */       
/* 3348 */       if (i > param1Int1) {
/* 3349 */         URI.this.host = substring(param1Int1, i);
/*      */       }
/* 3351 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int parseHostname(int param1Int1, int param1Int2) throws URISyntaxException {
/* 3361 */       int i = param1Int1;
/*      */       
/* 3363 */       int j = -1;
/*      */ 
/*      */       
/*      */       do {
/* 3367 */         int k = scan(i, param1Int2, URI.L_ALPHANUM, URI.H_ALPHANUM);
/* 3368 */         if (k <= i)
/*      */           break; 
/* 3370 */         j = i;
/* 3371 */         if (k > i) {
/* 3372 */           i = k;
/* 3373 */           k = scan(i, param1Int2, URI.L_ALPHANUM | URI.L_DASH, URI.H_ALPHANUM | URI.H_DASH);
/* 3374 */           if (k > i) {
/* 3375 */             if (charAt(k - 1) == '-')
/* 3376 */               fail("Illegal character in hostname", k - 1); 
/* 3377 */             i = k;
/*      */           } 
/*      */         } 
/* 3380 */         k = scan(i, param1Int2, '.');
/* 3381 */         if (k <= i)
/*      */           break; 
/* 3383 */         i = k;
/* 3384 */       } while (i < param1Int2);
/*      */       
/* 3386 */       if (i < param1Int2 && !at(i, param1Int2, ':')) {
/* 3387 */         fail("Illegal character in hostname", i);
/*      */       }
/* 3389 */       if (j < 0) {
/* 3390 */         failExpecting("hostname", param1Int1);
/*      */       }
/*      */ 
/*      */       
/* 3394 */       if (j > param1Int1 && !URI.match(charAt(j), 0L, URI.H_ALPHA)) {
/* 3395 */         fail("Illegal character in hostname", j);
/*      */       }
/*      */       
/* 3398 */       URI.this.host = substring(param1Int1, i);
/* 3399 */       return i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Parser(String param1String) {
/* 3444 */       this.ipv6byteCount = 0;
/*      */       this.input = param1String;
/*      */       URI.this.string = param1String;
/*      */     }
/*      */     private int parseIPv6Reference(int param1Int1, int param1Int2) throws URISyntaxException {
/* 3449 */       int i = param1Int1;
/*      */       
/* 3451 */       boolean bool = false;
/*      */       
/* 3453 */       int j = scanHexSeq(i, param1Int2);
/*      */       
/* 3455 */       if (j > i) {
/* 3456 */         i = j;
/* 3457 */         if (at(i, param1Int2, "::")) {
/* 3458 */           bool = true;
/* 3459 */           i = scanHexPost(i + 2, param1Int2);
/* 3460 */         } else if (at(i, param1Int2, ':')) {
/* 3461 */           i = takeIPv4Address(i + 1, param1Int2, "IPv4 address");
/* 3462 */           this.ipv6byteCount += 4;
/*      */         } 
/* 3464 */       } else if (at(i, param1Int2, "::")) {
/* 3465 */         bool = true;
/* 3466 */         i = scanHexPost(i + 2, param1Int2);
/*      */       } 
/* 3468 */       if (i < param1Int2)
/* 3469 */         fail("Malformed IPv6 address", param1Int1); 
/* 3470 */       if (this.ipv6byteCount > 16)
/* 3471 */         fail("IPv6 address too long", param1Int1); 
/* 3472 */       if (!bool && this.ipv6byteCount < 16)
/* 3473 */         fail("IPv6 address too short", param1Int1); 
/* 3474 */       if (bool && this.ipv6byteCount == 16) {
/* 3475 */         fail("Malformed IPv6 address", param1Int1);
/*      */       }
/* 3477 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private int scanHexPost(int param1Int1, int param1Int2) throws URISyntaxException {
/* 3483 */       int i = param1Int1;
/*      */ 
/*      */       
/* 3486 */       if (i == param1Int2) {
/* 3487 */         return i;
/*      */       }
/* 3489 */       int j = scanHexSeq(i, param1Int2);
/* 3490 */       if (j > i) {
/* 3491 */         i = j;
/* 3492 */         if (at(i, param1Int2, ':')) {
/* 3493 */           i++;
/* 3494 */           i = takeIPv4Address(i, param1Int2, "hex digits or IPv4 address");
/* 3495 */           this.ipv6byteCount += 4;
/*      */         } 
/*      */       } else {
/* 3498 */         i = takeIPv4Address(i, param1Int2, "hex digits or IPv4 address");
/* 3499 */         this.ipv6byteCount += 4;
/*      */       } 
/* 3501 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int scanHexSeq(int param1Int1, int param1Int2) throws URISyntaxException {
/* 3509 */       int i = param1Int1;
/*      */ 
/*      */       
/* 3512 */       int j = scan(i, param1Int2, URI.L_HEX, URI.H_HEX);
/* 3513 */       if (j <= i)
/* 3514 */         return -1; 
/* 3515 */       if (at(j, param1Int2, '.'))
/* 3516 */         return -1; 
/* 3517 */       if (j > i + 4)
/* 3518 */         fail("IPv6 hexadecimal digit sequence too long", i); 
/* 3519 */       this.ipv6byteCount += 2;
/* 3520 */       i = j;
/* 3521 */       while (i < param1Int2 && 
/* 3522 */         at(i, param1Int2, ':')) {
/*      */         
/* 3524 */         if (at(i + 1, param1Int2, ':'))
/*      */           break; 
/* 3526 */         i++;
/* 3527 */         j = scan(i, param1Int2, URI.L_HEX, URI.H_HEX);
/* 3528 */         if (j <= i)
/* 3529 */           failExpecting("digits for an IPv6 address", i); 
/* 3530 */         if (at(j, param1Int2, '.')) {
/* 3531 */           i--;
/*      */           break;
/*      */         } 
/* 3534 */         if (j > i + 4)
/* 3535 */           fail("IPv6 hexadecimal digit sequence too long", i); 
/* 3536 */         this.ipv6byteCount += 2;
/* 3537 */         i = j;
/*      */       } 
/*      */       
/* 3540 */       return i;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/URI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */