/*     */ package sun.net.www;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.FileNameMap;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MimeTable
/*     */   implements FileNameMap
/*     */ {
/*  36 */   private Hashtable<String, MimeEntry> entries = new Hashtable<>();
/*     */ 
/*     */ 
/*     */   
/*  40 */   private Hashtable<String, MimeEntry> extensionMap = new Hashtable<>();
/*     */   private static String tempFileTemplate;
/*     */   private static final String filePreamble = "sun.net.www MIME content-types table";
/*     */   private static final String fileMagic = "#sun.net.www MIME content-types table";
/*     */   protected static String[] mailcapLocations;
/*     */   
/*     */   static {
/*  47 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run()
/*     */           {
/*  51 */             MimeTable.tempFileTemplate = System.getProperty("content.types.temp.file.template", "/tmp/%s");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  60 */             MimeTable.mailcapLocations = new String[] { System.getProperty("user.mailcap"), System.getProperty("user.home") + "/.mailcap", "/etc/mailcap", "/usr/etc/mailcap", "/usr/local/etc/mailcap", System.getProperty("hotjava.home", "/usr/local/hotjava") + "/lib/mailcap" };
/*     */ 
/*     */ 
/*     */             
/*  64 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MimeTable() {
/*  74 */     load();
/*     */   }
/*     */   
/*     */   private static class DefaultInstanceHolder {
/*  78 */     static final MimeTable defaultInstance = getDefaultInstance();
/*     */     
/*     */     static MimeTable getDefaultInstance() {
/*  81 */       return AccessController.<MimeTable>doPrivileged(new PrivilegedAction<MimeTable>()
/*     */           {
/*     */             public MimeTable run() {
/*  84 */               MimeTable mimeTable = new MimeTable();
/*  85 */               URLConnection.setFileNameMap(mimeTable);
/*  86 */               return mimeTable;
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MimeTable getDefaultTable() {
/*  97 */     return DefaultInstanceHolder.defaultInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileNameMap loadTable() {
/* 104 */     return getDefaultTable();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int getSize() {
/* 109 */     return this.entries.size();
/*     */   }
/*     */   
/*     */   public synchronized String getContentTypeFor(String paramString) {
/* 113 */     MimeEntry mimeEntry = findByFileName(paramString);
/* 114 */     if (mimeEntry != null) {
/* 115 */       return mimeEntry.getType();
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void add(MimeEntry paramMimeEntry) {
/* 122 */     this.entries.put(paramMimeEntry.getType(), paramMimeEntry);
/*     */     
/* 124 */     String[] arrayOfString = paramMimeEntry.getExtensions();
/* 125 */     if (arrayOfString == null) {
/*     */       return;
/*     */     }
/*     */     
/* 129 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 130 */       this.extensionMap.put(arrayOfString[b], paramMimeEntry);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized MimeEntry remove(String paramString) {
/* 135 */     MimeEntry mimeEntry = this.entries.get(paramString);
/* 136 */     return remove(mimeEntry);
/*     */   }
/*     */   
/*     */   public synchronized MimeEntry remove(MimeEntry paramMimeEntry) {
/* 140 */     String[] arrayOfString = paramMimeEntry.getExtensions();
/* 141 */     if (arrayOfString != null) {
/* 142 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 143 */         this.extensionMap.remove(arrayOfString[b]);
/*     */       }
/*     */     }
/*     */     
/* 147 */     return this.entries.remove(paramMimeEntry.getType());
/*     */   }
/*     */   
/*     */   public synchronized MimeEntry find(String paramString) {
/* 151 */     MimeEntry mimeEntry = this.entries.get(paramString);
/* 152 */     if (mimeEntry == null) {
/*     */       
/* 154 */       Enumeration<MimeEntry> enumeration = this.entries.elements();
/* 155 */       while (enumeration.hasMoreElements()) {
/* 156 */         MimeEntry mimeEntry1 = enumeration.nextElement();
/* 157 */         if (mimeEntry1.matches(paramString)) {
/* 158 */           return mimeEntry1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     return mimeEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MimeEntry findByFileName(String paramString) {
/* 171 */     String str = "";
/*     */     
/* 173 */     int i = paramString.lastIndexOf('#');
/*     */     
/* 175 */     if (i > 0) {
/* 176 */       paramString = paramString.substring(0, i - 1);
/*     */     }
/*     */     
/* 179 */     i = paramString.lastIndexOf('.');
/*     */     
/* 181 */     i = Math.max(i, paramString.lastIndexOf('/'));
/* 182 */     i = Math.max(i, paramString.lastIndexOf('?'));
/*     */     
/* 184 */     if (i != -1 && paramString.charAt(i) == '.') {
/* 185 */       str = paramString.substring(i).toLowerCase();
/*     */     }
/*     */     
/* 188 */     return findByExt(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized MimeEntry findByExt(String paramString) {
/* 196 */     return this.extensionMap.get(paramString);
/*     */   }
/*     */   
/*     */   public synchronized MimeEntry findByDescription(String paramString) {
/* 200 */     Enumeration<MimeEntry> enumeration = elements();
/* 201 */     while (enumeration.hasMoreElements()) {
/* 202 */       MimeEntry mimeEntry = enumeration.nextElement();
/* 203 */       if (paramString.equals(mimeEntry.getDescription())) {
/* 204 */         return mimeEntry;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 209 */     return find(paramString);
/*     */   }
/*     */   
/*     */   String getTempFileTemplate() {
/* 213 */     return tempFileTemplate;
/*     */   }
/*     */   
/*     */   public synchronized Enumeration<MimeEntry> elements() {
/* 217 */     return this.entries.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void load() {
/* 226 */     Properties properties = new Properties();
/* 227 */     File file = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 232 */       String str = System.getProperty("content.types.user.table");
/* 233 */       if (str != null) {
/* 234 */         file = new File(str);
/* 235 */         if (!file.exists())
/*     */         {
/* 237 */           file = new File(System.getProperty("java.home") + File.separator + "lib" + File.separator + "content-types.properties");
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 246 */         file = new File(System.getProperty("java.home") + File.separator + "lib" + File.separator + "content-types.properties");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 253 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
/* 254 */       properties.load(bufferedInputStream);
/* 255 */       bufferedInputStream.close();
/*     */     }
/* 257 */     catch (IOException iOException) {
/* 258 */       System.err.println("Warning: default mime table not found: " + file
/* 259 */           .getPath());
/*     */       return;
/*     */     } 
/* 262 */     parse(properties);
/*     */   }
/*     */ 
/*     */   
/*     */   void parse(Properties paramProperties) {
/* 267 */     String str = (String)paramProperties.get("temp.file.template");
/* 268 */     if (str != null) {
/* 269 */       paramProperties.remove("temp.file.template");
/* 270 */       tempFileTemplate = str;
/*     */     } 
/*     */ 
/*     */     
/* 274 */     Enumeration<?> enumeration = paramProperties.propertyNames();
/* 275 */     while (enumeration.hasMoreElements()) {
/* 276 */       String str1 = (String)enumeration.nextElement();
/* 277 */       String str2 = paramProperties.getProperty(str1);
/* 278 */       parse(str1, str2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void parse(String paramString1, String paramString2) {
/* 310 */     MimeEntry mimeEntry = new MimeEntry(paramString1);
/*     */ 
/*     */     
/* 313 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString2, ";");
/* 314 */     while (stringTokenizer.hasMoreTokens()) {
/* 315 */       String str = stringTokenizer.nextToken();
/* 316 */       parse(str, mimeEntry);
/*     */     } 
/*     */     
/* 319 */     add(mimeEntry);
/*     */   }
/*     */ 
/*     */   
/*     */   void parse(String paramString, MimeEntry paramMimeEntry) {
/* 324 */     String str1 = null;
/* 325 */     String str2 = null;
/*     */     
/* 327 */     boolean bool = false;
/* 328 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "=");
/* 329 */     while (stringTokenizer.hasMoreTokens()) {
/* 330 */       if (bool) {
/* 331 */         str2 = stringTokenizer.nextToken().trim();
/*     */         continue;
/*     */       } 
/* 334 */       str1 = stringTokenizer.nextToken().trim();
/* 335 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 339 */     fill(paramMimeEntry, str1, str2);
/*     */   }
/*     */   
/*     */   void fill(MimeEntry paramMimeEntry, String paramString1, String paramString2) {
/* 343 */     if ("description".equalsIgnoreCase(paramString1)) {
/* 344 */       paramMimeEntry.setDescription(paramString2);
/*     */     }
/* 346 */     else if ("action".equalsIgnoreCase(paramString1)) {
/* 347 */       paramMimeEntry.setAction(getActionCode(paramString2));
/*     */     }
/* 349 */     else if ("application".equalsIgnoreCase(paramString1)) {
/* 350 */       paramMimeEntry.setCommand(paramString2);
/*     */     }
/* 352 */     else if ("icon".equalsIgnoreCase(paramString1)) {
/* 353 */       paramMimeEntry.setImageFileName(paramString2);
/*     */     }
/* 355 */     else if ("file_extensions".equalsIgnoreCase(paramString1)) {
/* 356 */       paramMimeEntry.setExtensions(paramString2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   String[] getExtensions(String paramString) {
/* 363 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/* 364 */     int i = stringTokenizer.countTokens();
/* 365 */     String[] arrayOfString = new String[i];
/* 366 */     for (byte b = 0; b < i; b++) {
/* 367 */       arrayOfString[b] = stringTokenizer.nextToken();
/*     */     }
/*     */     
/* 370 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   int getActionCode(String paramString) {
/* 374 */     for (byte b = 0; b < MimeEntry.actionKeywords.length; b++) {
/* 375 */       if (paramString.equalsIgnoreCase(MimeEntry.actionKeywords[b])) {
/* 376 */         return b;
/*     */       }
/*     */     } 
/*     */     
/* 380 */     return 0;
/*     */   }
/*     */   
/*     */   public synchronized boolean save(String paramString) {
/* 384 */     if (paramString == null) {
/* 385 */       paramString = System.getProperty("user.home" + File.separator + "lib" + File.separator + "content-types.properties");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 392 */     return saveAsProperties(new File(paramString));
/*     */   }
/*     */   
/*     */   public Properties getAsProperties() {
/* 396 */     Properties properties = new Properties();
/* 397 */     Enumeration<MimeEntry> enumeration = elements();
/* 398 */     while (enumeration.hasMoreElements()) {
/* 399 */       MimeEntry mimeEntry = enumeration.nextElement();
/* 400 */       properties.put(mimeEntry.getType(), mimeEntry.toProperty());
/*     */     } 
/*     */     
/* 403 */     return properties;
/*     */   }
/*     */   
/*     */   protected boolean saveAsProperties(File paramFile) {
/* 407 */     FileOutputStream fileOutputStream = null;
/*     */     try {
/* 409 */       fileOutputStream = new FileOutputStream(paramFile);
/* 410 */       Properties properties = getAsProperties();
/* 411 */       properties.put("temp.file.template", tempFileTemplate);
/*     */       
/* 413 */       String str = System.getProperty("user.name");
/* 414 */       if (str != null) {
/* 415 */         String str1 = "; customized for " + str;
/* 416 */         properties.store(fileOutputStream, "sun.net.www MIME content-types table" + str1);
/*     */       } else {
/*     */         
/* 419 */         properties.store(fileOutputStream, "sun.net.www MIME content-types table");
/*     */       }
/*     */     
/* 422 */     } catch (IOException iOException) {
/* 423 */       iOException.printStackTrace();
/* 424 */       return false;
/*     */     } finally {
/*     */       
/* 427 */       if (fileOutputStream != null) {
/* 428 */         try { fileOutputStream.close(); } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */     
/* 432 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/MimeTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */