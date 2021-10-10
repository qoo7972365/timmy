/*     */ package sun.launcher;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.Normalizer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.TreeSet;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.misc.VM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum LauncherHelper
/*     */ {
/*  71 */   INSTANCE; private static boolean isCharsetSupported; private static String encoding; private static final String encprop = "sun.jnu.encoding"; private static final int LM_JAR = 2; private static final int LM_CLASS = 1; private static final int LM_UNKNOWN = 0; private static Class<?> appClass; private static final ClassLoader scloader; private static PrintStream ostream; private static final String defaultBundleName = "sun.launcher.resources.launcher"; static final boolean trace; private static final String diagprop = "sun.java.launcher.diag"; private static final String LOCALE_SETTINGS = "Locale settings:"; private static final String PROP_SETTINGS = "Property settings:"; private static final String VM_SETTINGS = "VM settings:"; private static final String INDENT = "    "; private static StringBuilder outBuf;
/*     */   private static final String MAIN_CLASS = "Main-Class";
/*     */   
/*  74 */   static { outBuf = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     trace = (VM.getSavedProperty("sun.java.launcher.diag") != null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     scloader = ClassLoader.getSystemClassLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 569 */     encoding = null;
/* 570 */     isCharsetSupported = false; }
/*     */   private static class ResourceBundleHolder {
/*     */     private static final ResourceBundle RB = ResourceBundle.getBundle("sun.launcher.resources.launcher"); }
/*     */   static void showSettings(boolean paramBoolean1, String paramString, long paramLong1, long paramLong2, long paramLong3, boolean paramBoolean2) { initOutput(paramBoolean1); String[] arrayOfString = paramString.split(":"); String str = (arrayOfString.length > 1 && arrayOfString[1] != null) ? arrayOfString[1].trim() : "all"; switch (str) { case "vm": printVmSettings(paramLong1, paramLong2, paramLong3, paramBoolean2); return;case "properties": printProperties(); return;case "locale": printLocale(); return; }  printVmSettings(paramLong1, paramLong2, paramLong3, paramBoolean2); printProperties(); printLocale(); }
/*     */   private static void printVmSettings(long paramLong1, long paramLong2, long paramLong3, boolean paramBoolean) { ostream.println("VM settings:"); if (paramLong3 != 0L) ostream.println("    Stack Size: " + SizePrefix.scaleValue(paramLong3));  if (paramLong1 != 0L) ostream.println("    Min. Heap Size: " + SizePrefix.scaleValue(paramLong1));  if (paramLong2 != 0L) { ostream.println("    Max. Heap Size: " + SizePrefix.scaleValue(paramLong2)); } else { ostream.println("    Max. Heap Size (Estimated): " + SizePrefix.scaleValue(Runtime.getRuntime().maxMemory())); }  ostream.println("    Ergonomics Machine Class: " + (paramBoolean ? "server" : "client")); ostream.println("    Using VM: " + System.getProperty("java.vm.name")); ostream.println(); }
/*     */   private static void printProperties() { Properties properties = System.getProperties(); ostream.println("Property settings:"); ArrayList<String> arrayList = new ArrayList(); arrayList.addAll(properties.stringPropertyNames()); Collections.sort(arrayList); for (String str : arrayList) printPropertyValue(str, properties.getProperty(str));  ostream.println(); }
/*     */   private static boolean isPath(String paramString) { return (paramString.endsWith(".dirs") || paramString.endsWith(".path")); } private static void printPropertyValue(String paramString1, String paramString2) { ostream.print("    " + paramString1 + " = "); if (paramString1.equals("line.separator")) { for (byte b : paramString2.getBytes()) { switch (b) { case 13: ostream.print("\\r "); break;case 10: ostream.print("\\n "); break;default: ostream.printf("0x%02X", new Object[] { Integer.valueOf(b & 0xFF) }); break; }  }  ostream.println(); return; }  if (!isPath(paramString1)) { ostream.println(paramString2); return; }  String[] arrayOfString = paramString2.split(System.getProperty("path.separator")); boolean bool = true; for (String str : arrayOfString) { if (bool) { ostream.println(str); bool = false; } else { ostream.println("        " + str); }  }  } private static void printLocale() { Locale locale = Locale.getDefault(); ostream.println("Locale settings:"); ostream.println("    default locale = " + locale.getDisplayLanguage()); ostream.println("    default display locale = " + Locale.getDefault(Locale.Category.DISPLAY).getDisplayName()); ostream.println("    default format locale = " + Locale.getDefault(Locale.Category.FORMAT).getDisplayName()); printLocales(); ostream.println(); } private static void printLocales() { Locale[] arrayOfLocale = Locale.getAvailableLocales(); byte b = (arrayOfLocale == null) ? 0 : arrayOfLocale.length; if (b < 1) return;  TreeSet<String> treeSet = new TreeSet(); for (Locale locale : arrayOfLocale) treeSet.add(locale.toString());  ostream.print("    available locales = "); Iterator<String> iterator = treeSet.iterator(); int i = b - 1; for (int j = 0; iterator.hasNext(); j++) { String str = iterator.next(); ostream.print(str); if (j != i) ostream.print(", ");  if ((j + 1) % 8 == 0) { ostream.println(); ostream.print("        "); }  }  } private enum SizePrefix {
/* 577 */     KILO(1024L, "K"), MEGA(1048576L, "M"), GIGA(1073741824L, "G"), TERA(1099511627776L, "T"); long size; String abbrev; SizePrefix(long param1Long, String param1String1) { this.size = param1Long; this.abbrev = param1String1; } private static String scale(long param1Long, SizePrefix param1SizePrefix) { return BigDecimal.valueOf(param1Long).divide(BigDecimal.valueOf(param1SizePrefix.size), 2, RoundingMode.HALF_EVEN).toPlainString() + param1SizePrefix.abbrev; } static String scaleValue(long param1Long) { if (param1Long < MEGA.size) return scale(param1Long, KILO);  if (param1Long < GIGA.size) return scale(param1Long, MEGA);  if (param1Long < TERA.size) return scale(param1Long, GIGA);  return scale(param1Long, TERA); } } private static String getLocalizedMessage(String paramString, Object... paramVarArgs) { String str = ResourceBundleHolder.RB.getString(paramString); return (paramVarArgs != null) ? MessageFormat.format(str, paramVarArgs) : str; } static void initHelpMessage(String paramString) { outBuf = outBuf.append(getLocalizedMessage("java.launcher.opt.header", new Object[] { (paramString == null) ? "java" : paramString })); outBuf = outBuf.append(getLocalizedMessage("java.launcher.opt.datamodel", new Object[] { Integer.valueOf(32) })); outBuf = outBuf.append(getLocalizedMessage("java.launcher.opt.datamodel", new Object[] { Integer.valueOf(64) })); } static String makePlatformString(boolean paramBoolean, byte[] paramArrayOfbyte) { initOutput(paramBoolean);
/* 578 */     if (encoding == null) {
/* 579 */       encoding = System.getProperty("sun.jnu.encoding");
/* 580 */       isCharsetSupported = Charset.isSupported(encoding);
/*     */     } 
/*     */     
/* 583 */     try { return isCharsetSupported ? new String(paramArrayOfbyte, encoding) : new String(paramArrayOfbyte);
/*     */       
/*     */        }
/*     */     
/* 587 */     catch (UnsupportedEncodingException unsupportedEncodingException)
/* 588 */     { abort(unsupportedEncodingException, null, new Object[0]);
/*     */       
/* 590 */       return null; }  }
/*     */   static void appendVmSelectMessage(String paramString1, String paramString2) { outBuf = outBuf.append(getLocalizedMessage("java.launcher.opt.vmselect", new Object[] { paramString1, paramString2 })); }
/*     */   static void appendVmSynonymMessage(String paramString1, String paramString2) { outBuf = outBuf.append(getLocalizedMessage("java.launcher.opt.hotspot", new Object[] { paramString1, paramString2 })); }
/*     */   static void appendVmErgoMessage(boolean paramBoolean, String paramString) { outBuf = outBuf.append(getLocalizedMessage("java.launcher.ergo.message1", new Object[] { paramString })); outBuf = paramBoolean ? outBuf.append(",\n" + getLocalizedMessage("java.launcher.ergo.message2", new Object[0]) + "\n\n") : outBuf.append(".\n\n"); }
/* 594 */   static void printHelpMessage(boolean paramBoolean) { initOutput(paramBoolean); outBuf = outBuf.append(getLocalizedMessage("java.launcher.opt.footer", new Object[] { File.pathSeparator })); ostream.println(outBuf.toString()); } static void printXUsageMessage(boolean paramBoolean) { initOutput(paramBoolean); ostream.println(getLocalizedMessage("java.launcher.X.usage", new Object[] { File.pathSeparator })); if (System.getProperty("os.name").contains("OS X")) ostream.println(getLocalizedMessage("java.launcher.X.macosx.usage", new Object[] { File.pathSeparator }));  } static void initOutput(boolean paramBoolean) { ostream = paramBoolean ? System.err : System.out; } static String getMainClassFromJar(String paramString) { String str = null; try (JarFile null = new JarFile(paramString)) { Manifest manifest = jarFile.getManifest(); if (manifest == null) abort(null, "java.launcher.jar.error2", new Object[] { paramString });  Attributes attributes = manifest.getMainAttributes(); if (attributes == null) abort(null, "java.launcher.jar.error3", new Object[] { paramString });  str = attributes.getValue("Main-Class"); if (str == null) abort(null, "java.launcher.jar.error3", new Object[] { paramString });  if (attributes.containsKey(new Attributes.Name("JavaFX-Application-Class"))) return FXHelper.class.getName();  return str.trim(); } catch (IOException iOException) { abort(iOException, "java.launcher.jar.error1", new Object[] { paramString }); return null; }  } static void abort(Throwable paramThrowable, String paramString, Object... paramVarArgs) { if (paramString != null) ostream.println(getLocalizedMessage(paramString, paramVarArgs));  if (trace) if (paramThrowable != null) { paramThrowable.printStackTrace(); } else { Thread.dumpStack(); }   System.exit(1); } public static Class<?> checkAndLoadMain(boolean paramBoolean, int paramInt, String paramString) { initOutput(paramBoolean); String str = null; switch (paramInt) { case 1: str = paramString; break;case 2: str = getMainClassFromJar(paramString); break;default: throw new InternalError("" + paramInt + ": Unknown launch mode"); }  str = str.replace('/', '.'); Class<?> clazz = null; try { clazz = scloader.loadClass(str); } catch (NoClassDefFoundError|ClassNotFoundException noClassDefFoundError) { if (System.getProperty("os.name", "").contains("OS X") && Normalizer.isNormalized(str, Normalizer.Form.NFD)) { try { clazz = scloader.loadClass(Normalizer.normalize(str, Normalizer.Form.NFC)); } catch (NoClassDefFoundError|ClassNotFoundException noClassDefFoundError1) { abort(noClassDefFoundError, "java.launcher.cls.error1", new Object[] { str }); }  } else { abort(noClassDefFoundError, "java.launcher.cls.error1", new Object[] { str }); }  }  appClass = clazz; if (clazz.equals(FXHelper.class) || FXHelper.doesExtendFXApplication(clazz)) { FXHelper.setFXLaunchParameters(paramString, paramInt); return FXHelper.class; }  validateMainClass(clazz); return clazz; } public static Class<?> getApplicationClass() { return appClass; } static void validateMainClass(Class<?> paramClass) { Method method; try { method = paramClass.getMethod("main", new Class[] { String[].class }); } catch (NoSuchMethodException noSuchMethodException) { abort(null, "java.launcher.cls.error4", new Object[] { paramClass.getName(), "javafx.application.Application" }); return; }  int i = method.getModifiers(); if (!Modifier.isStatic(i)) abort(null, "java.launcher.cls.error2", new Object[] { "static", method.getDeclaringClass().getName() });  if (method.getReturnType() != void.class) abort(null, "java.launcher.cls.error3", new Object[] { method.getDeclaringClass().getName() });  } static String[] expandArgs(String[] paramArrayOfString) { ArrayList<StdArg> arrayList = new ArrayList();
/* 595 */     for (String str : paramArrayOfString) {
/* 596 */       arrayList.add(new StdArg(str));
/*     */     }
/* 598 */     return expandArgs(arrayList); }
/*     */ 
/*     */   
/*     */   static String[] expandArgs(List<StdArg> paramList) {
/* 602 */     ArrayList<String> arrayList = new ArrayList();
/* 603 */     if (trace) {
/* 604 */       System.err.println("Incoming arguments:");
/*     */     }
/* 606 */     for (StdArg stdArg : paramList) {
/* 607 */       if (trace) {
/* 608 */         System.err.println(stdArg);
/*     */       }
/* 610 */       if (stdArg.needsExpansion) {
/* 611 */         File file1 = new File(stdArg.arg);
/* 612 */         File file2 = file1.getParentFile();
/* 613 */         String str = file1.getName();
/* 614 */         if (file2 == null) {
/* 615 */           file2 = new File(".");
/*     */         }
/*     */         
/* 618 */         try (DirectoryStream<Path> null = Files.newDirectoryStream(file2.toPath(), str)) {
/* 619 */           byte b = 0;
/* 620 */           for (Path path : directoryStream) {
/* 621 */             arrayList.add(path.normalize().toString());
/* 622 */             b++;
/*     */           } 
/* 624 */           if (b == 0) {
/* 625 */             arrayList.add(stdArg.arg);
/*     */           }
/* 627 */         } catch (Exception exception) {
/* 628 */           arrayList.add(stdArg.arg);
/* 629 */           if (trace) {
/* 630 */             System.err.println("Warning: passing argument as-is " + stdArg);
/* 631 */             System.err.print(exception);
/*     */           } 
/*     */         }  continue;
/*     */       } 
/* 635 */       arrayList.add(stdArg.arg);
/*     */     } 
/*     */     
/* 638 */     String[] arrayOfString = new String[arrayList.size()];
/* 639 */     arrayList.toArray(arrayOfString);
/*     */     
/* 641 */     if (trace) {
/* 642 */       System.err.println("Expanded arguments:");
/* 643 */       for (String str : arrayOfString) {
/* 644 */         System.err.println(str);
/*     */       }
/*     */     } 
/* 647 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   private static class StdArg {
/*     */     final String arg;
/*     */     final boolean needsExpansion;
/*     */     
/*     */     StdArg(String param1String, boolean param1Boolean) {
/* 655 */       this.arg = param1String;
/* 656 */       this.needsExpansion = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     StdArg(String param1String) {
/* 662 */       this.arg = param1String.substring(1);
/* 663 */       this.needsExpansion = (param1String.charAt(0) == 'T');
/*     */     }
/*     */     public String toString() {
/* 666 */       return "StdArg{arg=" + this.arg + ", needsExpansion=" + this.needsExpansion + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class FXHelper
/*     */   {
/*     */     private static final String JAVAFX_APPLICATION_MARKER = "JavaFX-Application-Class";
/*     */ 
/*     */ 
/*     */     
/*     */     private static final String JAVAFX_APPLICATION_CLASS_NAME = "javafx.application.Application";
/*     */ 
/*     */ 
/*     */     
/*     */     private static final String JAVAFX_LAUNCHER_CLASS_NAME = "com.sun.javafx.application.LauncherImpl";
/*     */ 
/*     */ 
/*     */     
/*     */     private static final String JAVAFX_LAUNCH_MODE_CLASS = "LM_CLASS";
/*     */ 
/*     */ 
/*     */     
/*     */     private static final String JAVAFX_LAUNCH_MODE_JAR = "LM_JAR";
/*     */ 
/*     */ 
/*     */     
/* 696 */     private static String fxLaunchName = null;
/* 697 */     private static String fxLaunchMode = null;
/*     */     
/* 699 */     private static Class<?> fxLauncherClass = null;
/* 700 */     private static Method fxLauncherMethod = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void setFXLaunchParameters(String param1String, int param1Int) {
/*     */       try {
/* 710 */         fxLauncherClass = LauncherHelper.scloader.loadClass("com.sun.javafx.application.LauncherImpl");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 716 */         fxLauncherMethod = fxLauncherClass.getMethod("launchApplication", new Class[] { String.class, String.class, String[].class });
/*     */ 
/*     */ 
/*     */         
/* 720 */         int i = fxLauncherMethod.getModifiers();
/* 721 */         if (!Modifier.isStatic(i)) {
/* 722 */           LauncherHelper.abort(null, "java.launcher.javafx.error1", new Object[0]);
/*     */         }
/* 724 */         if (fxLauncherMethod.getReturnType() != void.class) {
/* 725 */           LauncherHelper.abort(null, "java.launcher.javafx.error1", new Object[0]);
/*     */         }
/* 727 */       } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {
/* 728 */         LauncherHelper.abort(classNotFoundException, "java.launcher.cls.error5", new Object[] { classNotFoundException });
/*     */       } 
/*     */       
/* 731 */       fxLaunchName = param1String;
/* 732 */       switch (param1Int) {
/*     */         case 1:
/* 734 */           fxLaunchMode = "LM_CLASS";
/*     */           return;
/*     */         case 2:
/* 737 */           fxLaunchMode = "LM_JAR";
/*     */           return;
/*     */       } 
/*     */       
/* 741 */       throw new InternalError(param1Int + ": Unknown launch mode");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean doesExtendFXApplication(Class<?> param1Class) {
/* 751 */       for (Class<?> clazz = param1Class.getSuperclass(); clazz != null; 
/* 752 */         clazz = clazz.getSuperclass()) {
/* 753 */         if (clazz.getName().equals("javafx.application.Application")) {
/* 754 */           return true;
/*     */         }
/*     */       } 
/* 757 */       return false;
/*     */     }
/*     */     
/*     */     public static void main(String... param1VarArgs) throws Exception {
/* 761 */       if (fxLauncherMethod == null || fxLaunchMode == null || fxLaunchName == null)
/*     */       {
/*     */         
/* 764 */         throw new RuntimeException("Invalid JavaFX launch parameters");
/*     */       }
/*     */       
/* 767 */       fxLauncherMethod.invoke(null, new Object[] { fxLaunchName, fxLaunchMode, param1VarArgs });
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/launcher/LauncherHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */