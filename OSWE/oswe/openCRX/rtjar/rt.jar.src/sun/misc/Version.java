/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Version
/*     */ {
/*     */   private static final String launcher_name = "openjdk";
/*     */   private static final String java_version = "1.8.0_242";
/*     */   private static final String java_runtime_name = "OpenJDK Runtime Environment";
/*     */   private static final String java_profile_name = "";
/*     */   private static final String java_runtime_version = "1.8.0_242-8u242-b08-0ubuntu3~16.04-b08";
/*     */   
/*     */   static {
/*  48 */     init();
/*     */   }
/*     */   
/*     */   public static void init() {
/*  52 */     System.setProperty("java.version", "1.8.0_242");
/*  53 */     System.setProperty("java.runtime.version", "1.8.0_242-8u242-b08-0ubuntu3~16.04-b08");
/*  54 */     System.setProperty("java.runtime.name", "OpenJDK Runtime Environment");
/*     */   }
/*     */   
/*     */   private static boolean versionsInitialized = false;
/*  58 */   private static int jvm_major_version = 0;
/*  59 */   private static int jvm_minor_version = 0;
/*  60 */   private static int jvm_micro_version = 0;
/*  61 */   private static int jvm_update_version = 0;
/*  62 */   private static int jvm_build_number = 0;
/*  63 */   private static String jvm_special_version = null;
/*  64 */   private static int jdk_major_version = 0;
/*  65 */   private static int jdk_minor_version = 0;
/*  66 */   private static int jdk_micro_version = 0;
/*  67 */   private static int jdk_update_version = 0;
/*  68 */   private static int jdk_build_number = 0;
/*  69 */   private static String jdk_special_version = null;
/*     */ 
/*     */   
/*     */   private static boolean jvmVersionInfoAvailable;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void print() {
/*  77 */     print(System.err);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void println() {
/*  85 */     print(System.err);
/*  86 */     System.err.println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void print(PrintStream paramPrintStream) {
/*  93 */     boolean bool = false;
/*     */ 
/*     */     
/*  96 */     String str1 = System.getProperty("java.awt.headless");
/*  97 */     if (str1 != null && str1.equalsIgnoreCase("true")) {
/*  98 */       bool = true;
/*     */     }
/*     */ 
/*     */     
/* 102 */     paramPrintStream.println("openjdk version \"1.8.0_242\"");
/*     */ 
/*     */ 
/*     */     
/* 106 */     paramPrintStream.print("OpenJDK Runtime Environment (build 1.8.0_242-8u242-b08-0ubuntu3~16.04-b08");
/*     */     
/* 108 */     if ("".length() > 0)
/*     */     {
/* 110 */       paramPrintStream.print(", profile ");
/*     */     }
/*     */     
/* 113 */     if ("OpenJDK Runtime Environment".indexOf("Embedded") != -1 && bool)
/*     */     {
/* 115 */       paramPrintStream.print(", headless");
/*     */     }
/* 117 */     paramPrintStream.println(')');
/*     */ 
/*     */     
/* 120 */     String str2 = System.getProperty("java.vm.name");
/* 121 */     String str3 = System.getProperty("java.vm.version");
/* 122 */     String str4 = System.getProperty("java.vm.info");
/* 123 */     paramPrintStream.println(str2 + " (build " + str3 + ", " + str4 + ")");
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
/*     */   public static synchronized int jvmMajorVersion() {
/* 136 */     if (!versionsInitialized) {
/* 137 */       initVersions();
/*     */     }
/* 139 */     return jvm_major_version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int jvmMinorVersion() {
/* 149 */     if (!versionsInitialized) {
/* 150 */       initVersions();
/*     */     }
/* 152 */     return jvm_minor_version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int jvmMicroVersion() {
/* 163 */     if (!versionsInitialized) {
/* 164 */       initVersions();
/*     */     }
/* 166 */     return jvm_micro_version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int jvmUpdateVersion() {
/* 175 */     if (!versionsInitialized) {
/* 176 */       initVersions();
/*     */     }
/* 178 */     return jvm_update_version;
/*     */   }
/*     */   
/*     */   public static synchronized String jvmSpecialVersion() {
/* 182 */     if (!versionsInitialized) {
/* 183 */       initVersions();
/*     */     }
/* 185 */     if (jvm_special_version == null) {
/* 186 */       jvm_special_version = getJvmSpecialVersion();
/*     */     }
/* 188 */     return jvm_special_version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int jvmBuildNumber() {
/* 198 */     if (!versionsInitialized) {
/* 199 */       initVersions();
/*     */     }
/* 201 */     return jvm_build_number;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int jdkMajorVersion() {
/* 210 */     if (!versionsInitialized) {
/* 211 */       initVersions();
/*     */     }
/* 213 */     return jdk_major_version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int jdkMinorVersion() {
/* 221 */     if (!versionsInitialized) {
/* 222 */       initVersions();
/*     */     }
/* 224 */     return jdk_minor_version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int jdkMicroVersion() {
/* 232 */     if (!versionsInitialized) {
/* 233 */       initVersions();
/*     */     }
/* 235 */     return jdk_micro_version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int jdkUpdateVersion() {
/* 244 */     if (!versionsInitialized) {
/* 245 */       initVersions();
/*     */     }
/* 247 */     return jdk_update_version;
/*     */   }
/*     */   
/*     */   public static synchronized String jdkSpecialVersion() {
/* 251 */     if (!versionsInitialized) {
/* 252 */       initVersions();
/*     */     }
/* 254 */     if (jdk_special_version == null) {
/* 255 */       jdk_special_version = getJdkSpecialVersion();
/*     */     }
/* 257 */     return jdk_special_version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int jdkBuildNumber() {
/* 267 */     if (!versionsInitialized) {
/* 268 */       initVersions();
/*     */     }
/* 270 */     return jdk_build_number;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void initVersions() {
/* 276 */     if (versionsInitialized) {
/*     */       return;
/*     */     }
/* 279 */     jvmVersionInfoAvailable = getJvmVersionInfo();
/* 280 */     if (!jvmVersionInfoAvailable) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 285 */       String str = System.getProperty("java.vm.version");
/* 286 */       if (str.length() >= 5 && 
/* 287 */         Character.isDigit(str.charAt(0)) && str.charAt(1) == '.' && 
/* 288 */         Character.isDigit(str.charAt(2)) && str.charAt(3) == '.' && 
/* 289 */         Character.isDigit(str.charAt(4))) {
/* 290 */         jvm_major_version = Character.digit(str.charAt(0), 10);
/* 291 */         jvm_minor_version = Character.digit(str.charAt(2), 10);
/* 292 */         jvm_micro_version = Character.digit(str.charAt(4), 10);
/* 293 */         CharSequence charSequence = str.subSequence(5, str.length());
/* 294 */         if (charSequence.charAt(0) == '_' && charSequence.length() >= 3) {
/* 295 */           byte b = 0;
/* 296 */           if (Character.isDigit(charSequence.charAt(1)) && 
/* 297 */             Character.isDigit(charSequence.charAt(2)) && 
/* 298 */             Character.isDigit(charSequence.charAt(3))) {
/*     */             
/* 300 */             b = 4;
/* 301 */           } else if (Character.isDigit(charSequence.charAt(1)) && 
/* 302 */             Character.isDigit(charSequence.charAt(2))) {
/*     */             
/* 304 */             b = 3;
/*     */           } 
/*     */           
/*     */           try {
/* 308 */             String str1 = charSequence.subSequence(1, b).toString();
/* 309 */             jvm_update_version = Integer.valueOf(str1).intValue();
/* 310 */             if (charSequence.length() >= b + 1) {
/* 311 */               char c = charSequence.charAt(b);
/* 312 */               if (c >= 'a' && c <= 'z') {
/* 313 */                 jvm_special_version = Character.toString(c);
/* 314 */                 b++;
/*     */               } 
/*     */             } 
/* 317 */           } catch (NumberFormatException numberFormatException) {
/*     */             return;
/*     */           } 
/*     */           
/* 321 */           charSequence = charSequence.subSequence(b, charSequence.length());
/*     */         } 
/* 323 */         if (charSequence.charAt(0) == '-') {
/*     */ 
/*     */ 
/*     */           
/* 327 */           charSequence = charSequence.subSequence(1, charSequence.length());
/* 328 */           String[] arrayOfString = charSequence.toString().split("-");
/* 329 */           for (String str1 : arrayOfString) {
/* 330 */             if (str1.charAt(0) == 'b' && str1.length() == 3 && 
/* 331 */               Character.isDigit(str1.charAt(1)) && 
/* 332 */               Character.isDigit(str1.charAt(2))) {
/*     */               
/* 334 */               jvm_build_number = Integer.valueOf(str1.substring(1, 3)).intValue();
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 341 */     getJdkVersionInfo();
/* 342 */     versionsInitialized = true;
/*     */   }
/*     */   
/*     */   public static native String getJvmSpecialVersion();
/*     */   
/*     */   public static native String getJdkSpecialVersion();
/*     */   
/*     */   private static native boolean getJvmVersionInfo();
/*     */   
/*     */   private static native void getJdkVersionInfo();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Version.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */