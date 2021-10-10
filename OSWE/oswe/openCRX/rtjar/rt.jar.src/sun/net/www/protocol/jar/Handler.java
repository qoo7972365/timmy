/*     */ package sun.net.www.protocol.jar;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLStreamHandler;
/*     */ import sun.net.www.ParseUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Handler
/*     */   extends URLStreamHandler
/*     */ {
/*     */   private static final String separator = "!/";
/*     */   
/*     */   protected URLConnection openConnection(URL paramURL) throws IOException {
/*  41 */     return new JarURLConnection(paramURL, this);
/*     */   }
/*     */   
/*     */   private static int indexOfBangSlash(String paramString) {
/*  45 */     int i = paramString.length();
/*  46 */     while ((i = paramString.lastIndexOf('!', i)) != -1) {
/*  47 */       if (i != paramString.length() - 1 && paramString
/*  48 */         .charAt(i + 1) == '/') {
/*  49 */         return i + 1;
/*     */       }
/*  51 */       i--;
/*     */     } 
/*     */     
/*  54 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean sameFile(URL paramURL1, URL paramURL2) {
/*  62 */     if (!paramURL1.getProtocol().equals("jar") || !paramURL2.getProtocol().equals("jar")) {
/*  63 */       return false;
/*     */     }
/*  65 */     String str1 = paramURL1.getFile();
/*  66 */     String str2 = paramURL2.getFile();
/*  67 */     int i = str1.indexOf("!/");
/*  68 */     int j = str2.indexOf("!/");
/*     */     
/*  70 */     if (i == -1 || j == -1) {
/*  71 */       return super.sameFile(paramURL1, paramURL2);
/*     */     }
/*     */     
/*  74 */     String str3 = str1.substring(i + 2);
/*  75 */     String str4 = str2.substring(j + 2);
/*     */     
/*  77 */     if (!str3.equals(str4)) {
/*  78 */       return false;
/*     */     }
/*  80 */     URL uRL1 = null, uRL2 = null;
/*     */     try {
/*  82 */       uRL1 = new URL(str1.substring(0, i));
/*  83 */       uRL2 = new URL(str2.substring(0, j));
/*  84 */     } catch (MalformedURLException malformedURLException) {
/*  85 */       return super.sameFile(paramURL1, paramURL2);
/*     */     } 
/*     */     
/*  88 */     if (!super.sameFile(uRL1, uRL2)) {
/*  89 */       return false;
/*     */     }
/*     */     
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int hashCode(URL paramURL) {
/*  97 */     int i = 0;
/*     */     
/*  99 */     String str1 = paramURL.getProtocol();
/* 100 */     if (str1 != null) {
/* 101 */       i += str1.hashCode();
/*     */     }
/* 103 */     String str2 = paramURL.getFile();
/* 104 */     int j = str2.indexOf("!/");
/*     */     
/* 106 */     if (j == -1) {
/* 107 */       return i + str2.hashCode();
/*     */     }
/* 109 */     URL uRL = null;
/* 110 */     String str3 = str2.substring(0, j);
/*     */     try {
/* 112 */       uRL = new URL(str3);
/* 113 */       i += uRL.hashCode();
/* 114 */     } catch (MalformedURLException malformedURLException) {
/* 115 */       i += str3.hashCode();
/*     */     } 
/*     */     
/* 118 */     String str4 = str2.substring(j + 2);
/* 119 */     i += str4.hashCode();
/*     */     
/* 121 */     return i;
/*     */   }
/*     */   
/*     */   public String checkNestedProtocol(String paramString) {
/* 125 */     if (paramString.regionMatches(true, 0, "jar:", 0, 4)) {
/* 126 */       return "Nested JAR URLs are not supported";
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void parseURL(URL paramURL, String paramString, int paramInt1, int paramInt2) {
/* 136 */     String str1 = null;
/* 137 */     String str2 = null;
/*     */     
/* 139 */     int i = paramString.indexOf('#', paramInt2);
/* 140 */     boolean bool = (i == paramInt1) ? true : false;
/* 141 */     if (i > -1) {
/* 142 */       str2 = paramString.substring(i + 1, paramString.length());
/* 143 */       if (bool) {
/* 144 */         str1 = paramURL.getFile();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     boolean bool1 = false;
/* 152 */     if (paramString.length() >= 4) {
/* 153 */       bool1 = paramString.substring(0, 4).equalsIgnoreCase("jar:");
/*     */     }
/* 155 */     paramString = paramString.substring(paramInt1, paramInt2);
/*     */     
/* 157 */     String str3 = checkNestedProtocol(paramString);
/* 158 */     if (str3 != null)
/*     */     {
/* 160 */       throw new NullPointerException(str3);
/*     */     }
/*     */     
/* 163 */     if (bool1) {
/* 164 */       str1 = parseAbsoluteSpec(paramString);
/* 165 */     } else if (!bool) {
/* 166 */       str1 = parseContextSpec(paramURL, paramString);
/*     */ 
/*     */       
/* 169 */       int j = indexOfBangSlash(str1);
/* 170 */       String str4 = str1.substring(0, j);
/* 171 */       String str5 = str1.substring(j);
/* 172 */       ParseUtil parseUtil = new ParseUtil();
/* 173 */       str5 = parseUtil.canonizeString(str5);
/* 174 */       str1 = str4 + str5;
/*     */     } 
/* 176 */     setURL(paramURL, "jar", "", -1, str1, str2);
/*     */   }
/*     */   
/*     */   private String parseAbsoluteSpec(String paramString) {
/* 180 */     URL uRL = null;
/* 181 */     int i = -1;
/*     */     
/* 183 */     if ((i = indexOfBangSlash(paramString)) == -1) {
/* 184 */       throw new NullPointerException("no !/ in spec");
/*     */     }
/*     */     
/*     */     try {
/* 188 */       String str = paramString.substring(0, i - 1);
/* 189 */       uRL = new URL(str);
/* 190 */     } catch (MalformedURLException malformedURLException) {
/* 191 */       throw new NullPointerException("invalid url: " + paramString + " (" + malformedURLException + ")");
/*     */     } 
/*     */     
/* 194 */     return paramString;
/*     */   }
/*     */   
/*     */   private String parseContextSpec(URL paramURL, String paramString) {
/* 198 */     String str = paramURL.getFile();
/*     */     
/* 200 */     if (paramString.startsWith("/")) {
/* 201 */       int i = indexOfBangSlash(str);
/* 202 */       if (i == -1) {
/* 203 */         throw new NullPointerException("malformed context url:" + paramURL + ": no !/");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 208 */       str = str.substring(0, i);
/*     */     } 
/* 210 */     if (!str.endsWith("/") && !paramString.startsWith("/")) {
/*     */       
/* 212 */       int i = str.lastIndexOf('/');
/* 213 */       if (i == -1) {
/* 214 */         throw new NullPointerException("malformed context url:" + paramURL);
/*     */       }
/*     */ 
/*     */       
/* 218 */       str = str.substring(0, i + 1);
/*     */     } 
/* 220 */     return str + paramString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/jar/Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */