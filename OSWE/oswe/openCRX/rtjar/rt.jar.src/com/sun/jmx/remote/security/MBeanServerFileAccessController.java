/*     */ package com.sun.jmx.remote.security;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ import javax.security.auth.Subject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanServerFileAccessController
/*     */   extends MBeanServerAccessController
/*     */ {
/*     */   static final String READONLY = "readonly";
/*     */   static final String READWRITE = "readwrite";
/*     */   static final String CREATE = "create";
/*     */   static final String UNREGISTER = "unregister";
/*     */   private Map<String, Access> accessMap;
/*     */   private Properties originalProps;
/*     */   private String accessFileName;
/*     */   
/*     */   private enum AccessType
/*     */   {
/*  91 */     READ, WRITE, CREATE, UNREGISTER;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Access
/*     */   {
/*     */     final boolean write;
/*     */     
/*     */     final String[] createPatterns;
/*     */     
/*     */     private boolean unregister;
/*     */     
/*     */     private final String[] NO_STRINGS;
/*     */ 
/*     */     
/*     */     Access(boolean param1Boolean1, boolean param1Boolean2, List<String> param1List) {
/* 108 */       this.NO_STRINGS = new String[0];
/*     */       this.write = param1Boolean1;
/*     */       boolean bool = (param1List == null) ? false : param1List.size();
/*     */       if (!bool) {
/*     */         this.createPatterns = this.NO_STRINGS;
/*     */       } else {
/*     */         this.createPatterns = param1List.<String>toArray(new String[bool]);
/*     */       } 
/*     */       this.unregister = param1Boolean2;
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
/*     */   public MBeanServerFileAccessController(String paramString) throws IOException {
/* 132 */     this.accessFileName = paramString;
/* 133 */     Properties properties = propertiesFromFile(paramString);
/* 134 */     parseProperties(properties);
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
/*     */   public MBeanServerFileAccessController(String paramString, MBeanServer paramMBeanServer) throws IOException {
/* 159 */     this(paramString);
/* 160 */     setMBeanServer(paramMBeanServer);
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
/*     */   public MBeanServerFileAccessController(Properties paramProperties) throws IOException {
/* 188 */     if (paramProperties == null)
/* 189 */       throw new IllegalArgumentException("Null properties"); 
/* 190 */     this.originalProps = paramProperties;
/* 191 */     parseProperties(paramProperties);
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
/*     */   public MBeanServerFileAccessController(Properties paramProperties, MBeanServer paramMBeanServer) throws IOException {
/* 221 */     this(paramProperties);
/* 222 */     setMBeanServer(paramMBeanServer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkRead() {
/* 231 */     checkAccess(AccessType.READ, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkWrite() {
/* 240 */     checkAccess(AccessType.WRITE, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkCreate(String paramString) {
/* 249 */     checkAccess(AccessType.CREATE, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkUnregister(ObjectName paramObjectName) {
/* 258 */     checkAccess(AccessType.UNREGISTER, (String)null);
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
/*     */   public synchronized void refresh() throws IOException {
/*     */     Properties properties;
/* 285 */     if (this.accessFileName == null) {
/* 286 */       properties = this.originalProps;
/*     */     } else {
/* 288 */       properties = propertiesFromFile(this.accessFileName);
/* 289 */     }  parseProperties(properties);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Properties propertiesFromFile(String paramString) throws IOException {
/* 294 */     FileInputStream fileInputStream = new FileInputStream(paramString);
/*     */     try {
/* 296 */       Properties properties = new Properties();
/* 297 */       properties.load(fileInputStream);
/* 298 */       return properties;
/*     */     } finally {
/* 300 */       fileInputStream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void checkAccess(AccessType paramAccessType, String paramString) {
/* 305 */     final AccessControlContext acc = AccessController.getContext();
/*     */     
/* 307 */     Subject subject = AccessController.<Subject>doPrivileged(new PrivilegedAction<Subject>() {
/*     */           public Subject run() {
/* 309 */             return Subject.getSubject(acc);
/*     */           }
/*     */         });
/* 312 */     if (subject == null)
/* 313 */       return;  Set<Principal> set = subject.getPrincipals();
/* 314 */     String str = null;
/* 315 */     for (Principal principal : set) {
/*     */       
/* 317 */       Access access = this.accessMap.get(principal.getName());
/* 318 */       if (access != null) {
/*     */         boolean bool;
/* 320 */         switch (paramAccessType) {
/*     */           case READ:
/* 322 */             bool = true;
/*     */             break;
/*     */           case WRITE:
/* 325 */             bool = access.write;
/*     */             break;
/*     */           case UNREGISTER:
/* 328 */             bool = access.unregister;
/* 329 */             if (!bool && access.write)
/* 330 */               str = "unregister"; 
/*     */             break;
/*     */           case CREATE:
/* 333 */             bool = checkCreateAccess(access, paramString);
/* 334 */             if (!bool && access.write)
/* 335 */               str = "create " + paramString; 
/*     */             break;
/*     */           default:
/* 338 */             throw new AssertionError();
/*     */         } 
/* 340 */         if (bool)
/*     */           return; 
/*     */       } 
/*     */     } 
/* 344 */     SecurityException securityException = new SecurityException("Access denied! Invalid access level for requested MBeanServer operation.");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 351 */     if (str != null) {
/* 352 */       SecurityException securityException1 = new SecurityException("Access property for this identity should be similar to: readwrite " + str);
/*     */ 
/*     */       
/* 355 */       securityException.initCause(securityException1);
/*     */     } 
/* 357 */     throw securityException;
/*     */   }
/*     */   
/*     */   private static boolean checkCreateAccess(Access paramAccess, String paramString) {
/* 361 */     for (String str : paramAccess.createPatterns) {
/* 362 */       if (classNameMatch(str, paramString))
/* 363 */         return true; 
/*     */     } 
/* 365 */     return false;
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
/*     */   private static boolean classNameMatch(String paramString1, String paramString2) {
/* 378 */     StringBuilder stringBuilder = new StringBuilder();
/* 379 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, "*", true);
/* 380 */     while (stringTokenizer.hasMoreTokens()) {
/* 381 */       String str = stringTokenizer.nextToken();
/* 382 */       if (str.equals("*")) {
/* 383 */         stringBuilder.append("[^.]*"); continue;
/*     */       } 
/* 385 */       stringBuilder.append(Pattern.quote(str));
/*     */     } 
/* 387 */     return paramString2.matches(stringBuilder.toString());
/*     */   }
/*     */   
/*     */   private void parseProperties(Properties paramProperties) {
/* 391 */     this.accessMap = new HashMap<>();
/* 392 */     for (Map.Entry<Object, Object> entry : paramProperties.entrySet()) {
/* 393 */       String str1 = (String)entry.getKey();
/* 394 */       String str2 = (String)entry.getValue();
/* 395 */       Access access = Parser.parseAccess(str1, str2);
/* 396 */       this.accessMap.put(str1, access);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Parser { private static final int EOS = -1;
/*     */     
/*     */     static {
/* 403 */       assert !Character.isWhitespace(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     private final String identity;
/*     */     
/*     */     private final String s;
/*     */     
/*     */     private final int len;
/*     */     
/*     */     private int i;
/*     */     
/*     */     private int c;
/*     */     
/*     */     private Parser(String param1String1, String param1String2) {
/* 418 */       this.identity = param1String1;
/* 419 */       this.s = param1String2;
/* 420 */       this.len = param1String2.length();
/* 421 */       this.i = 0;
/* 422 */       if (this.i < this.len) {
/* 423 */         this.c = param1String2.codePointAt(this.i);
/*     */       } else {
/* 425 */         this.c = -1;
/*     */       } 
/*     */     }
/*     */     static MBeanServerFileAccessController.Access parseAccess(String param1String1, String param1String2) {
/* 429 */       return (new Parser(param1String1, param1String2)).parseAccess();
/*     */     }
/*     */     private MBeanServerFileAccessController.Access parseAccess() {
/*     */       MBeanServerFileAccessController.Access access;
/* 433 */       skipSpace();
/* 434 */       String str = parseWord();
/*     */       
/* 436 */       if (str.equals("readonly")) {
/* 437 */         access = new MBeanServerFileAccessController.Access(false, false, null);
/* 438 */       } else if (str.equals("readwrite")) {
/* 439 */         access = parseReadWrite();
/*     */       } else {
/* 441 */         throw syntax("Expected readonly or readwrite: " + str);
/*     */       } 
/*     */       
/* 444 */       if (this.c != -1)
/* 445 */         throw syntax("Extra text at end of line"); 
/* 446 */       return access;
/*     */     }
/*     */     
/*     */     private MBeanServerFileAccessController.Access parseReadWrite() {
/* 450 */       ArrayList<String> arrayList = new ArrayList();
/* 451 */       boolean bool = false;
/*     */       while (true) {
/* 453 */         skipSpace();
/* 454 */         if (this.c == -1)
/*     */           break; 
/* 456 */         String str = parseWord();
/* 457 */         if (str.equals("unregister")) {
/* 458 */           bool = true; continue;
/* 459 */         }  if (str.equals("create")) {
/* 460 */           parseCreate(arrayList); continue;
/*     */         } 
/* 462 */         throw syntax("Unrecognized keyword " + str);
/*     */       } 
/* 464 */       return new MBeanServerFileAccessController.Access(true, bool, arrayList);
/*     */     }
/*     */     
/*     */     private void parseCreate(List<String> param1List) {
/*     */       while (true) {
/* 469 */         skipSpace();
/* 470 */         param1List.add(parseClassName());
/* 471 */         skipSpace();
/* 472 */         if (this.c == 44) {
/* 473 */           next();
/*     */           continue;
/*     */         } 
/*     */         break;
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
/*     */     private String parseClassName() {
/* 488 */       int i = this.i;
/* 489 */       boolean bool = false;
/*     */       while (true) {
/* 491 */         if (this.c == 46) {
/* 492 */           if (!bool)
/* 493 */             throw syntax("Bad . in class name"); 
/* 494 */           bool = false;
/* 495 */         } else if (this.c == 42 || Character.isJavaIdentifierPart(this.c)) {
/* 496 */           bool = true;
/*     */         } else {
/*     */           break;
/* 499 */         }  next();
/*     */       } 
/* 501 */       String str = this.s.substring(i, this.i);
/* 502 */       if (!bool)
/* 503 */         throw syntax("Bad class name " + str); 
/* 504 */       return str;
/*     */     }
/*     */ 
/*     */     
/*     */     private void next() {
/* 509 */       if (this.c != -1) {
/* 510 */         this.i += Character.charCount(this.c);
/* 511 */         if (this.i < this.len) {
/* 512 */           this.c = this.s.codePointAt(this.i);
/*     */         } else {
/* 514 */           this.c = -1;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     private void skipSpace() {
/* 519 */       while (Character.isWhitespace(this.c))
/* 520 */         next(); 
/*     */     }
/*     */     
/*     */     private String parseWord() {
/* 524 */       skipSpace();
/* 525 */       if (this.c == -1)
/* 526 */         throw syntax("Expected word at end of line"); 
/* 527 */       int i = this.i;
/* 528 */       while (this.c != -1 && !Character.isWhitespace(this.c))
/* 529 */         next(); 
/* 530 */       String str = this.s.substring(i, this.i);
/* 531 */       skipSpace();
/* 532 */       return str;
/*     */     }
/*     */     
/*     */     private IllegalArgumentException syntax(String param1String) {
/* 536 */       return new IllegalArgumentException(param1String + " [" + this.identity + " " + this.s + "]");
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/security/MBeanServerFileAccessController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */