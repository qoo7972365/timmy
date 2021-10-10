/*     */ package java.io;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnixFileSystem
/*     */   extends FileSystem
/*     */ {
/*  39 */   private final char slash = ((String)AccessController.<String>doPrivileged(new GetPropertyAction("file.separator")))
/*  40 */     .charAt(0);
/*  41 */   private final char colon = ((String)AccessController.<String>doPrivileged(new GetPropertyAction("path.separator")))
/*  42 */     .charAt(0);
/*  43 */   private final String javaHome = AccessController.<String>doPrivileged(new GetPropertyAction("java.home"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getSeparator() {
/*  51 */     return this.slash;
/*     */   }
/*     */   
/*     */   public char getPathSeparator() {
/*  55 */     return this.colon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String normalize(String paramString, int paramInt1, int paramInt2) {
/*  64 */     if (paramInt1 == 0) return paramString; 
/*  65 */     int i = paramInt1;
/*  66 */     for (; i > 0 && paramString.charAt(i - 1) == '/'; i--);
/*  67 */     if (i == 0) return "/"; 
/*  68 */     StringBuffer stringBuffer = new StringBuffer(paramString.length());
/*  69 */     if (paramInt2 > 0) stringBuffer.append(paramString.substring(0, paramInt2)); 
/*  70 */     char c = Character.MIN_VALUE;
/*  71 */     for (int j = paramInt2; j < i; j++) {
/*  72 */       char c1 = paramString.charAt(j);
/*  73 */       if (c != '/' || c1 != '/') {
/*  74 */         stringBuffer.append(c1);
/*  75 */         c = c1;
/*     */       } 
/*  77 */     }  return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String normalize(String paramString) {
/*  84 */     int i = paramString.length();
/*  85 */     char c = Character.MIN_VALUE;
/*  86 */     for (byte b = 0; b < i; b++) {
/*  87 */       char c1 = paramString.charAt(b);
/*  88 */       if (c == '/' && c1 == '/')
/*  89 */         return normalize(paramString, i, b - 1); 
/*  90 */       c = c1;
/*     */     } 
/*  92 */     if (c == '/') return normalize(paramString, i, i - 1); 
/*  93 */     return paramString;
/*     */   }
/*     */   
/*     */   public int prefixLength(String paramString) {
/*  97 */     if (paramString.length() == 0) return 0; 
/*  98 */     return (paramString.charAt(0) == '/') ? 1 : 0;
/*     */   }
/*     */   
/*     */   public String resolve(String paramString1, String paramString2) {
/* 102 */     if (paramString2.equals("")) return paramString1; 
/* 103 */     if (paramString2.charAt(0) == '/') {
/* 104 */       if (paramString1.equals("/")) return paramString2; 
/* 105 */       return paramString1 + paramString2;
/*     */     } 
/* 107 */     if (paramString1.equals("/")) return paramString1 + paramString2; 
/* 108 */     return paramString1 + '/' + paramString2;
/*     */   }
/*     */   
/*     */   public String getDefaultParent() {
/* 112 */     return "/";
/*     */   }
/*     */   
/*     */   public String fromURIPath(String paramString) {
/* 116 */     String str = paramString;
/* 117 */     if (str.endsWith("/") && str.length() > 1)
/*     */     {
/* 119 */       str = str.substring(0, str.length() - 1);
/*     */     }
/* 121 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbsolute(File paramFile) {
/* 128 */     return (paramFile.getPrefixLength() != 0);
/*     */   }
/*     */   
/*     */   public String resolve(File paramFile) {
/* 132 */     if (isAbsolute(paramFile)) return paramFile.getPath(); 
/* 133 */     return resolve(System.getProperty("user.dir"), paramFile.getPath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   private ExpiringCache cache = new ExpiringCache();
/*     */ 
/*     */ 
/*     */   
/* 146 */   private ExpiringCache javaHomePrefixCache = new ExpiringCache();
/*     */   
/*     */   public String canonicalize(String paramString) throws IOException {
/* 149 */     if (!useCanonCaches) {
/* 150 */       return canonicalize0(paramString);
/*     */     }
/* 152 */     String str = this.cache.get(paramString);
/* 153 */     if (str == null) {
/* 154 */       String str1 = null;
/* 155 */       String str2 = null;
/* 156 */       if (useCanonPrefixCache) {
/*     */ 
/*     */ 
/*     */         
/* 160 */         str1 = parentOrNull(paramString);
/* 161 */         if (str1 != null) {
/* 162 */           str2 = this.javaHomePrefixCache.get(str1);
/* 163 */           if (str2 != null) {
/*     */             
/* 165 */             String str3 = paramString.substring(1 + str1.length());
/* 166 */             str = str2 + this.slash + str3;
/* 167 */             this.cache.put(str1 + this.slash + str3, str);
/*     */           } 
/*     */         } 
/*     */       } 
/* 171 */       if (str == null) {
/* 172 */         str = canonicalize0(paramString);
/* 173 */         this.cache.put(paramString, str);
/* 174 */         if (useCanonPrefixCache && str1 != null && str1
/* 175 */           .startsWith(this.javaHome)) {
/* 176 */           str2 = parentOrNull(str);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 181 */           if (str2 != null && str2.equals(str1)) {
/* 182 */             File file = new File(str);
/* 183 */             if (file.exists() && !file.isDirectory()) {
/* 184 */               this.javaHomePrefixCache.put(str1, str2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 190 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native String canonicalize0(String paramString) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String parentOrNull(String paramString) {
/* 202 */     if (paramString == null) return null; 
/* 203 */     char c = File.separatorChar;
/* 204 */     int i = paramString.length() - 1;
/* 205 */     int j = i;
/* 206 */     byte b1 = 0;
/* 207 */     byte b2 = 0;
/* 208 */     while (j > 0) {
/* 209 */       char c1 = paramString.charAt(j);
/* 210 */       if (c1 == '.') {
/* 211 */         if (++b1 >= 2)
/*     */         {
/* 213 */           return null; } 
/*     */       } else {
/* 215 */         if (c1 == c) {
/* 216 */           if (b1 == 1 && !b2)
/*     */           {
/* 218 */             return null;
/*     */           }
/* 220 */           if (j == 0 || j >= i - 1 || paramString
/*     */             
/* 222 */             .charAt(j - 1) == c)
/*     */           {
/*     */             
/* 225 */             return null;
/*     */           }
/* 227 */           return paramString.substring(0, j);
/*     */         } 
/* 229 */         b2++;
/* 230 */         b1 = 0;
/*     */       } 
/* 232 */       j--;
/*     */     } 
/* 234 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public native int getBooleanAttributes0(File paramFile);
/*     */ 
/*     */   
/*     */   public int getBooleanAttributes(File paramFile) {
/* 242 */     int i = getBooleanAttributes0(paramFile);
/* 243 */     String str = paramFile.getName();
/* 244 */     boolean bool = (str.length() > 0 && str.charAt(0) == '.') ? true : false;
/* 245 */     return i | (bool ? 8 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public native boolean checkAccess(File paramFile, int paramInt);
/*     */ 
/*     */   
/*     */   public native long getLastModifiedTime(File paramFile);
/*     */ 
/*     */   
/*     */   public native long getLength(File paramFile);
/*     */ 
/*     */   
/*     */   public native boolean setPermission(File paramFile, int paramInt, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public native boolean createFileExclusively(String paramString) throws IOException;
/*     */   
/*     */   public boolean delete(File paramFile) {
/* 263 */     this.cache.clear();
/* 264 */     this.javaHomePrefixCache.clear();
/* 265 */     return delete0(paramFile);
/*     */   }
/*     */ 
/*     */   
/*     */   private native boolean delete0(File paramFile);
/*     */   
/*     */   public native String[] list(File paramFile);
/*     */   
/*     */   public native boolean createDirectory(File paramFile);
/*     */   
/*     */   public boolean rename(File paramFile1, File paramFile2) {
/* 276 */     this.cache.clear();
/* 277 */     this.javaHomePrefixCache.clear();
/* 278 */     return rename0(paramFile1, paramFile2);
/*     */   }
/*     */   
/*     */   private native boolean rename0(File paramFile1, File paramFile2);
/*     */   
/*     */   public native boolean setLastModifiedTime(File paramFile, long paramLong);
/*     */   
/*     */   public native boolean setReadOnly(File paramFile);
/*     */   
/*     */   public File[] listRoots() {
/*     */     try {
/* 289 */       SecurityManager securityManager = System.getSecurityManager();
/* 290 */       if (securityManager != null) {
/* 291 */         securityManager.checkRead("/");
/*     */       }
/* 293 */       return new File[] { new File("/") };
/* 294 */     } catch (SecurityException securityException) {
/* 295 */       return new File[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public native long getSpace(File paramFile, int paramInt);
/*     */ 
/*     */   
/*     */   public int compare(File paramFile1, File paramFile2) {
/* 305 */     return paramFile1.getPath().compareTo(paramFile2.getPath());
/*     */   }
/*     */   
/*     */   public int hashCode(File paramFile) {
/* 309 */     return paramFile.getPath().hashCode() ^ 0x12D591;
/*     */   }
/*     */ 
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   static {
/* 316 */     initIDs();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/UnixFileSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */