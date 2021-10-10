/*     */ package javax.tools;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolProvider
/*     */ {
/*     */   private static final String propertyName = "sun.tools.ToolProvider";
/*     */   private static final String loggerName = "javax.tools";
/*     */   private static final String defaultJavaCompilerName = "com.sun.tools.javac.api.JavacTool";
/*     */   private static final String defaultDocumentationToolName = "com.sun.tools.javadoc.api.JavadocTool";
/*     */   private static ToolProvider instance;
/*     */   
/*     */   static <T> T trace(Level paramLevel, Object paramObject) {
/*     */     try {
/*  63 */       if (System.getProperty("sun.tools.ToolProvider") != null) {
/*  64 */         StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
/*  65 */         String str1 = "???";
/*  66 */         String str2 = ToolProvider.class.getName();
/*  67 */         if (arrayOfStackTraceElement.length > 2) {
/*  68 */           StackTraceElement stackTraceElement = arrayOfStackTraceElement[2];
/*  69 */           str1 = String.format((Locale)null, "%s(%s:%s)", new Object[] { stackTraceElement
/*  70 */                 .getMethodName(), stackTraceElement
/*  71 */                 .getFileName(), 
/*  72 */                 Integer.valueOf(stackTraceElement.getLineNumber()) });
/*  73 */           str2 = stackTraceElement.getClassName();
/*     */         } 
/*  75 */         Logger logger = Logger.getLogger("javax.tools");
/*  76 */         if (paramObject instanceof Throwable) {
/*  77 */           logger.logp(paramLevel, str2, str1, paramObject
/*  78 */               .getClass().getName(), (Throwable)paramObject);
/*     */         } else {
/*  80 */           logger.logp(paramLevel, str2, str1, String.valueOf(paramObject));
/*     */         } 
/*     */       } 
/*  83 */     } catch (SecurityException securityException) {
/*  84 */       System.err.format((Locale)null, "%s: %s; %s%n", new Object[] { ToolProvider.class
/*  85 */             .getName(), paramObject, securityException
/*     */             
/*  87 */             .getLocalizedMessage() });
/*     */     } 
/*  89 */     return null;
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
/*     */   public static JavaCompiler getSystemJavaCompiler() {
/* 102 */     return instance().<JavaCompiler>getSystemTool(JavaCompiler.class, "com.sun.tools.javac.api.JavacTool");
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
/*     */   public static DocumentationTool getSystemDocumentationTool() {
/* 115 */     return instance().<DocumentationTool>getSystemTool(DocumentationTool.class, "com.sun.tools.javadoc.api.JavadocTool");
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
/*     */   public static ClassLoader getSystemToolClassLoader() {
/*     */     try {
/* 130 */       Class<? extends JavaCompiler> clazz = instance().getSystemToolClass(JavaCompiler.class, "com.sun.tools.javac.api.JavacTool");
/* 131 */       return clazz.getClassLoader();
/* 132 */     } catch (Throwable throwable) {
/* 133 */       return trace(Level.WARNING, throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized ToolProvider instance() {
/* 141 */     if (instance == null)
/* 142 */       instance = new ToolProvider(); 
/* 143 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 148 */   private Map<String, Reference<Class<?>>> toolClasses = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/* 152 */   private Reference<ClassLoader> refToolClassLoader = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> T getSystemTool(Class<T> paramClass, String paramString) {
/* 158 */     Class<? extends T> clazz = getSystemToolClass(paramClass, paramString);
/*     */     try {
/* 160 */       return clazz.<T>asSubclass(paramClass).newInstance();
/* 161 */     } catch (Throwable throwable) {
/* 162 */       trace(Level.WARNING, throwable);
/* 163 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> Class<? extends T> getSystemToolClass(Class<T> paramClass, String paramString) {
/* 168 */     Reference<Class<?>> reference = this.toolClasses.get(paramString);
/* 169 */     Class<?> clazz = (reference == null) ? null : reference.get();
/* 170 */     if (clazz == null) {
/*     */       try {
/* 172 */         clazz = findSystemToolClass(paramString);
/* 173 */       } catch (Throwable throwable) {
/* 174 */         return trace(Level.WARNING, throwable);
/*     */       } 
/* 176 */       this.toolClasses.put(paramString, new WeakReference<>(clazz));
/*     */     } 
/* 178 */     return clazz.asSubclass(paramClass);
/*     */   }
/*     */   
/* 181 */   private static final String[] defaultToolsLocation = new String[] { "lib", "tools.jar" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> findSystemToolClass(String paramString) throws MalformedURLException, ClassNotFoundException {
/*     */     try {
/* 188 */       return Class.forName(paramString, false, null);
/* 189 */     } catch (ClassNotFoundException classNotFoundException) {
/* 190 */       trace(Level.FINE, classNotFoundException);
/*     */ 
/*     */       
/* 193 */       ClassLoader classLoader = (this.refToolClassLoader == null) ? null : this.refToolClassLoader.get();
/* 194 */       if (classLoader == null) {
/* 195 */         File file = new File(System.getProperty("java.home"));
/* 196 */         if (file.getName().equalsIgnoreCase("jre"))
/* 197 */           file = file.getParentFile(); 
/* 198 */         for (String str : defaultToolsLocation) {
/* 199 */           file = new File(file, str);
/*     */         }
/*     */ 
/*     */         
/* 203 */         if (!file.exists()) {
/* 204 */           throw classNotFoundException;
/*     */         }
/* 206 */         URL[] arrayOfURL = { file.toURI().toURL() };
/* 207 */         trace(Level.FINE, arrayOfURL[0].toString());
/*     */         
/* 209 */         classLoader = URLClassLoader.newInstance(arrayOfURL);
/* 210 */         this.refToolClassLoader = new WeakReference<>(classLoader);
/*     */       } 
/*     */       
/* 213 */       return Class.forName(paramString, false, classLoader);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/tools/ToolProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */