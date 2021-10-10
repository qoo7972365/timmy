/*     */ package sun.applet;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */ {
/*     */   static File theUserPropertiesFile;
/*  54 */   static final String[][] avDefaultUserProps = new String[][] { { "http.proxyHost", "" }, { "http.proxyPort", "80" }, { "package.restrict.access.sun", "true" } };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AppletMessageHandler amh;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  65 */     File file = new File(System.getProperty("user.home"));
/*     */     
/*  67 */     file.canWrite();
/*     */     
/*  69 */     theUserPropertiesFile = new File(file, ".appletviewer");
/*     */ 
/*     */ 
/*     */     
/*  73 */     amh = new AppletMessageHandler("appletviewer");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     cmdLineTestFlag = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     urlList = new Vector(1);
/*     */ 
/*     */ 
/*     */     
/*  91 */     theVersion = System.getProperty("java.version");
/*     */   }
/*     */   private boolean debugFlag = false; private boolean helpFlag = false;
/*     */   private String encoding = null;
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*  97 */     Main main = new Main();
/*  98 */     int i = main.run(paramArrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     if (i != 0 || cmdLineTestFlag)
/* 104 */       System.exit(i); 
/*     */   }
/*     */   private boolean noSecurityFlag = false; private static boolean cmdLineTestFlag; private static Vector urlList; public static final String theVersion;
/*     */   
/*     */   private int run(String[] paramArrayOfString) {
/*     */     try {
/* 110 */       if (paramArrayOfString.length == 0) {
/* 111 */         usage();
/* 112 */         return 0;
/*     */       } 
/* 114 */       for (int i = 0; i < paramArrayOfString.length; ) {
/* 115 */         int j = decodeArg(paramArrayOfString, i);
/* 116 */         if (j == 0) {
/* 117 */           throw new ParseException(lookup("main.err.unrecognizedarg", paramArrayOfString[i]));
/*     */         }
/*     */         
/* 120 */         i += j;
/*     */       } 
/* 122 */     } catch (ParseException parseException) {
/* 123 */       System.err.println(parseException.getMessage());
/* 124 */       return 1;
/*     */     } 
/*     */ 
/*     */     
/* 128 */     if (this.helpFlag) {
/* 129 */       usage();
/* 130 */       return 0;
/*     */     } 
/*     */     
/* 133 */     if (urlList.size() == 0) {
/* 134 */       System.err.println(lookup("main.err.inputfile"));
/* 135 */       return 1;
/*     */     } 
/*     */     
/* 138 */     if (this.debugFlag)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 143 */       return invokeDebugger(paramArrayOfString);
/*     */     }
/*     */ 
/*     */     
/* 147 */     if (!this.noSecurityFlag && System.getSecurityManager() == null) {
/* 148 */       init();
/*     */     }
/*     */     
/* 151 */     for (byte b = 0; b < urlList.size(); b++) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 156 */         AppletViewer.parse(urlList.elementAt(b), this.encoding);
/* 157 */       } catch (IOException iOException) {
/* 158 */         System.err.println(lookup("main.err.io", iOException.getMessage()));
/* 159 */         return 1;
/*     */       } 
/*     */     } 
/* 162 */     return 0;
/*     */   }
/*     */   
/*     */   private static void usage() {
/* 166 */     System.out.println(lookup("usage"));
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
/*     */   private int decodeArg(String[] paramArrayOfString, int paramInt) throws ParseException {
/* 182 */     String str = paramArrayOfString[paramInt];
/* 183 */     int i = paramArrayOfString.length;
/*     */     
/* 185 */     if ("-help".equalsIgnoreCase(str) || "-?".equals(str)) {
/* 186 */       this.helpFlag = true;
/* 187 */       return 1;
/* 188 */     }  if ("-encoding".equals(str) && paramInt < i - 1) {
/* 189 */       if (this.encoding != null)
/* 190 */         throw new ParseException(lookup("main.err.dupoption", str)); 
/* 191 */       this.encoding = paramArrayOfString[++paramInt];
/* 192 */       return 2;
/* 193 */     }  if ("-debug".equals(str)) {
/* 194 */       this.debugFlag = true;
/* 195 */       return 1;
/* 196 */     }  if ("-Xnosecurity".equals(str)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       System.err.println();
/* 202 */       System.err.println(lookup("main.warn.nosecmgr"));
/* 203 */       System.err.println();
/*     */       
/* 205 */       this.noSecurityFlag = true;
/* 206 */       return 1;
/* 207 */     }  if ("-XcmdLineTest".equals(str)) {
/*     */ 
/*     */ 
/*     */       
/* 211 */       cmdLineTestFlag = true;
/* 212 */       return 1;
/* 213 */     }  if (str.startsWith("-")) {
/* 214 */       throw new ParseException(lookup("main.err.unsupportedopt", str));
/*     */     }
/*     */     
/* 217 */     URL uRL = parseURL(str);
/* 218 */     if (uRL != null) {
/* 219 */       urlList.addElement(uRL);
/* 220 */       return 1;
/*     */     } 
/*     */     
/* 223 */     return 0;
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
/*     */   private URL parseURL(String paramString) throws ParseException {
/* 238 */     URL uRL = null;
/*     */     
/* 240 */     String str = "file:";
/*     */     
/*     */     try {
/* 243 */       if (paramString.indexOf(':') <= 1) {
/*     */ 
/*     */         
/* 246 */         uRL = ParseUtil.fileToEncodedURL(new File(paramString));
/* 247 */       } else if (paramString.startsWith(str) && paramString
/* 248 */         .length() != str.length() && 
/* 249 */         !(new File(paramString.substring(str.length()))).isAbsolute()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 255 */         String str1 = ParseUtil.fileToEncodedURL(new File(System.getProperty("user.dir"))).getPath() + paramString.substring(str.length());
/* 256 */         uRL = new URL("file", "", str1);
/*     */       } else {
/*     */         
/* 259 */         uRL = new URL(paramString);
/*     */       } 
/* 261 */     } catch (MalformedURLException malformedURLException) {
/* 262 */       throw new ParseException(lookup("main.err.badurl", paramString, malformedURLException
/* 263 */             .getMessage()));
/*     */     } 
/*     */     
/* 266 */     return uRL;
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
/*     */   private int invokeDebugger(String[] paramArrayOfString) {
/* 278 */     String[] arrayOfString = new String[paramArrayOfString.length + 1];
/* 279 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     String str = System.getProperty("java.home") + File.separator + "phony";
/*     */     
/* 289 */     arrayOfString[b1++] = "-Djava.class.path=" + str;
/*     */ 
/*     */     
/* 292 */     arrayOfString[b1++] = "sun.applet.Main";
/*     */ 
/*     */ 
/*     */     
/* 296 */     for (byte b2 = 0; b2 < paramArrayOfString.length; b2++) {
/* 297 */       if (!"-debug".equals(paramArrayOfString[b2])) {
/* 298 */         arrayOfString[b1++] = paramArrayOfString[b2];
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 310 */       Class<?> clazz = Class.forName("com.sun.tools.example.debug.tty.TTY", true, 
/* 311 */           ClassLoader.getSystemClassLoader());
/* 312 */       Method method = clazz.getDeclaredMethod("main", new Class[] { String[].class });
/*     */       
/* 314 */       method.invoke(null, new Object[] { arrayOfString });
/* 315 */     } catch (ClassNotFoundException classNotFoundException) {
/* 316 */       System.err.println(lookup("main.debug.cantfinddebug"));
/* 317 */       return 1;
/* 318 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 319 */       System.err.println(lookup("main.debug.cantfindmain"));
/* 320 */       return 1;
/* 321 */     } catch (InvocationTargetException invocationTargetException) {
/* 322 */       System.err.println(lookup("main.debug.exceptionindebug"));
/* 323 */       return 1;
/* 324 */     } catch (IllegalAccessException illegalAccessException) {
/* 325 */       System.err.println(lookup("main.debug.cantaccess"));
/* 326 */       return 1;
/*     */     } 
/* 328 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/* 333 */     Properties properties1 = getAVProps();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 340 */     properties1.put("browser", "sun.applet.AppletViewer");
/* 341 */     properties1.put("browser.version", "1.06");
/* 342 */     properties1.put("browser.vendor", "Oracle Corporation");
/* 343 */     properties1.put("http.agent", "Java(tm) 2 SDK, Standard Edition v" + theVersion);
/*     */ 
/*     */ 
/*     */     
/* 347 */     properties1.put("package.restrict.definition.java", "true");
/* 348 */     properties1.put("package.restrict.definition.sun", "true");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     properties1.put("java.version.applet", "true");
/* 357 */     properties1.put("java.vendor.applet", "true");
/* 358 */     properties1.put("java.vendor.url.applet", "true");
/* 359 */     properties1.put("java.class.version.applet", "true");
/* 360 */     properties1.put("os.name.applet", "true");
/* 361 */     properties1.put("os.version.applet", "true");
/* 362 */     properties1.put("os.arch.applet", "true");
/* 363 */     properties1.put("file.separator.applet", "true");
/* 364 */     properties1.put("path.separator.applet", "true");
/* 365 */     properties1.put("line.separator.applet", "true");
/*     */ 
/*     */ 
/*     */     
/* 369 */     Properties properties2 = System.getProperties();
/* 370 */     for (Enumeration<?> enumeration = properties2.propertyNames(); enumeration.hasMoreElements(); ) {
/* 371 */       String str1 = (String)enumeration.nextElement();
/* 372 */       String str2 = properties2.getProperty(str1);
/*     */       String str3;
/* 374 */       if ((str3 = (String)properties1.setProperty(str1, str2)) != null) {
/* 375 */         System.err.println(lookup("main.warn.prop.overwrite", str1, str3, str2));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 380 */     System.setProperties(properties1);
/*     */ 
/*     */     
/* 383 */     if (!this.noSecurityFlag) {
/* 384 */       System.setSecurityManager((SecurityManager)new AppletSecurity());
/*     */     } else {
/* 386 */       System.err.println(lookup("main.nosecmgr"));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Properties getAVProps() {
/* 403 */     Properties properties = new Properties();
/*     */     
/* 405 */     File file = theUserPropertiesFile;
/* 406 */     if (file.exists()) {
/*     */       
/* 408 */       if (file.canRead()) {
/*     */         
/* 410 */         properties = getAVProps(file);
/*     */       } else {
/*     */         
/* 413 */         System.err.println(lookup("main.warn.cantreadprops", file
/* 414 */               .toString()));
/* 415 */         properties = setDefaultAVProps();
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 421 */       File file1 = new File(System.getProperty("user.home"));
/* 422 */       File file2 = new File(file1, ".hotjava");
/* 423 */       file2 = new File(file2, "properties");
/* 424 */       if (file2.exists()) {
/*     */         
/* 426 */         properties = getAVProps(file2);
/*     */       } else {
/*     */         
/* 429 */         System.err.println(lookup("main.warn.cantreadprops", file2
/* 430 */               .toString()));
/* 431 */         properties = setDefaultAVProps();
/*     */       } 
/*     */ 
/*     */       
/* 435 */       try (FileOutputStream null = new FileOutputStream(file)) {
/* 436 */         properties.store(fileOutputStream, lookup("main.prop.store"));
/* 437 */       } catch (IOException iOException) {
/* 438 */         System.err.println(lookup("main.err.prop.cantsave", file
/* 439 */               .toString()));
/*     */       } 
/*     */     } 
/* 442 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Properties setDefaultAVProps() {
/* 452 */     Properties properties = new Properties();
/* 453 */     for (byte b = 0; b < avDefaultUserProps.length; b++) {
/* 454 */       properties.setProperty(avDefaultUserProps[b][0], avDefaultUserProps[b][1]);
/*     */     }
/*     */     
/* 457 */     return properties;
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
/*     */   private Properties getAVProps(File paramFile) {
/* 469 */     Properties properties1 = new Properties();
/*     */ 
/*     */     
/* 472 */     Properties properties2 = new Properties();
/* 473 */     try (FileInputStream null = new FileInputStream(paramFile)) {
/* 474 */       properties2.load(new BufferedInputStream(fileInputStream));
/* 475 */     } catch (IOException iOException) {
/* 476 */       System.err.println(lookup("main.err.prop.cantread", paramFile.toString()));
/*     */     } 
/*     */ 
/*     */     
/* 480 */     for (byte b = 0; b < avDefaultUserProps.length; b++) {
/* 481 */       String str = properties2.getProperty(avDefaultUserProps[b][0]);
/* 482 */       if (str != null) {
/*     */         
/* 484 */         properties1.setProperty(avDefaultUserProps[b][0], str);
/*     */       } else {
/*     */         
/* 487 */         properties1.setProperty(avDefaultUserProps[b][0], avDefaultUserProps[b][1]);
/*     */       } 
/*     */     } 
/*     */     
/* 491 */     return properties1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String lookup(String paramString) {
/* 499 */     return amh.getMessage(paramString);
/*     */   }
/*     */   
/*     */   private static String lookup(String paramString1, String paramString2) {
/* 503 */     return amh.getMessage(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   private static String lookup(String paramString1, String paramString2, String paramString3) {
/* 507 */     return amh.getMessage(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String lookup(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 512 */     return amh.getMessage(paramString1, paramString2, paramString3, paramString4);
/*     */   }
/*     */   
/*     */   class ParseException extends RuntimeException {
/*     */     Throwable t;
/*     */     
/* 518 */     public ParseException(String param1String) { super(param1String);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 526 */       this.t = null; } public ParseException(Throwable param1Throwable) { super(param1Throwable.getMessage()); this.t = null;
/*     */       this.t = param1Throwable; }
/*     */   
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */