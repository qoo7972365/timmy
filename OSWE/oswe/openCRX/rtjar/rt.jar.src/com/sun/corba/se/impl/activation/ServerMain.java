/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import com.sun.corba.se.spi.activation.Activator;
/*     */ import com.sun.corba.se.spi.activation.ActivatorHelper;
/*     */ import com.sun.corba.se.spi.activation.Server;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Date;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerMain
/*     */ {
/*     */   public static final int OK = 0;
/*     */   public static final int MAIN_CLASS_NOT_FOUND = 1;
/*     */   public static final int NO_MAIN_METHOD = 2;
/*     */   public static final int APPLICATION_ERROR = 3;
/*     */   public static final int UNKNOWN_ERROR = 4;
/*     */   public static final int NO_SERVER_ID = 5;
/*     */   public static final int REGISTRATION_FAILED = 6;
/*     */   private static final boolean debug = false;
/*     */   
/*     */   public static String printResult(int paramInt) {
/*  65 */     switch (paramInt) { case 0:
/*  66 */         return "Server terminated normally";
/*  67 */       case 1: return "main class not found";
/*  68 */       case 2: return "no main method";
/*  69 */       case 3: return "application error";
/*  70 */       case 5: return "server ID not defined";
/*  71 */       case 6: return "server registration failed"; }
/*  72 */      return "unknown error";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void redirectIOStreams() {
/*     */     try {
/*  84 */       String str1 = System.getProperty("com.sun.CORBA.activation.DbDir") + System.getProperty("file.separator") + "logs" + System.getProperty("file.separator");
/*     */       
/*  86 */       File file = new File(str1);
/*  87 */       String str2 = System.getProperty("com.sun.CORBA.POA.ORBServerId");
/*     */ 
/*     */       
/*  90 */       FileOutputStream fileOutputStream1 = new FileOutputStream(str1 + str2 + ".out", true);
/*     */       
/*  92 */       FileOutputStream fileOutputStream2 = new FileOutputStream(str1 + str2 + ".err", true);
/*     */ 
/*     */       
/*  95 */       PrintStream printStream1 = new PrintStream(fileOutputStream1, true);
/*  96 */       PrintStream printStream2 = new PrintStream(fileOutputStream2, true);
/*     */       
/*  98 */       System.setOut(printStream1);
/*  99 */       System.setErr(printStream2);
/*     */       
/* 101 */       logInformation("Server started");
/*     */     }
/* 103 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeLogMessage(PrintStream paramPrintStream, String paramString) {
/* 110 */     Date date = new Date();
/* 111 */     paramPrintStream.print("[" + date.toString() + "] " + paramString + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void logInformation(String paramString) {
/* 118 */     writeLogMessage(System.out, "        " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void logError(String paramString) {
/* 125 */     writeLogMessage(System.out, "ERROR:  " + paramString);
/* 126 */     writeLogMessage(System.err, "ERROR:  " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void logTerminal(String paramString, int paramInt) {
/* 136 */     if (paramInt == 0) {
/* 137 */       writeLogMessage(System.out, "        " + paramString);
/*     */     } else {
/* 139 */       writeLogMessage(System.out, "FATAL:  " + 
/* 140 */           printResult(paramInt) + ": " + paramString);
/*     */       
/* 142 */       writeLogMessage(System.err, "FATAL:  " + 
/* 143 */           printResult(paramInt) + ": " + paramString);
/*     */     } 
/*     */     
/* 146 */     System.exit(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private Method getMainMethod(Class paramClass) {
/* 151 */     Class[] arrayOfClass = { String[].class };
/* 152 */     Method method = null;
/*     */     
/*     */     try {
/* 155 */       method = paramClass.getDeclaredMethod("main", arrayOfClass);
/* 156 */     } catch (Exception exception) {
/* 157 */       logTerminal(exception.getMessage(), 2);
/*     */     } 
/*     */     
/* 160 */     if (!isPublicStaticVoid(method)) {
/* 161 */       logTerminal("", 2);
/*     */     }
/* 163 */     return method;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPublicStaticVoid(Method paramMethod) {
/* 169 */     int i = paramMethod.getModifiers();
/* 170 */     if (!Modifier.isPublic(i) || !Modifier.isStatic(i)) {
/* 171 */       logError(paramMethod.getName() + " is not public static");
/* 172 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 176 */     if ((paramMethod.getExceptionTypes()).length != 0) {
/* 177 */       logError(paramMethod.getName() + " declares exceptions");
/* 178 */       return false;
/*     */     } 
/*     */     
/* 181 */     if (!paramMethod.getReturnType().equals(void.class)) {
/* 182 */       logError(paramMethod.getName() + " does not have a void return type");
/* 183 */       return false;
/*     */     } 
/*     */     
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private Method getNamedMethod(Class paramClass, String paramString) {
/* 191 */     Class[] arrayOfClass = { ORB.class };
/* 192 */     Method method = null;
/*     */     
/*     */     try {
/* 195 */       method = paramClass.getDeclaredMethod(paramString, arrayOfClass);
/* 196 */     } catch (Exception exception) {
/* 197 */       return null;
/*     */     } 
/*     */     
/* 200 */     if (!isPublicStaticVoid(method)) {
/* 201 */       return null;
/*     */     }
/* 203 */     return method;
/*     */   }
/*     */ 
/*     */   
/*     */   private void run(String[] paramArrayOfString) {
/*     */     try {
/* 209 */       redirectIOStreams();
/*     */       
/* 211 */       String str = System.getProperty("com.sun.CORBA.POA.ORBServerName");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */       
/* 219 */       if (classLoader == null) {
/* 220 */         classLoader = ClassLoader.getSystemClassLoader();
/*     */       }
/*     */       
/* 223 */       Class<?> clazz = null;
/*     */ 
/*     */       
/*     */       try {
/* 227 */         clazz = Class.forName(str);
/* 228 */       } catch (ClassNotFoundException classNotFoundException) {
/*     */         
/* 230 */         clazz = Class.forName(str, true, classLoader);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 237 */       Method method = getMainMethod(clazz);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 244 */       boolean bool = Boolean.getBoolean("com.sun.CORBA.activation.ORBServerVerify");
/*     */       
/* 246 */       if (bool) {
/* 247 */         if (method == null) {
/* 248 */           logTerminal("", 2);
/*     */         }
/*     */         else {
/*     */           
/* 252 */           logTerminal("", 0);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 257 */       registerCallback(clazz);
/*     */ 
/*     */       
/* 260 */       Object[] arrayOfObject = new Object[1];
/* 261 */       arrayOfObject[0] = paramArrayOfString;
/* 262 */       method.invoke(null, arrayOfObject);
/*     */     }
/* 264 */     catch (ClassNotFoundException classNotFoundException) {
/* 265 */       logTerminal("ClassNotFound exception: " + classNotFoundException.getMessage(), 1);
/*     */     }
/* 267 */     catch (Exception exception) {
/* 268 */       logTerminal("Exception: " + exception.getMessage(), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 274 */     ServerMain serverMain = new ServerMain();
/* 275 */     serverMain.run(paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getServerId() {
/* 282 */     Integer integer = Integer.getInteger("com.sun.CORBA.POA.ORBServerId");
/*     */     
/* 284 */     if (integer == null) {
/* 285 */       logTerminal("", 5);
/*     */     }
/* 287 */     return integer.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerCallback(Class paramClass) {
/* 292 */     Method method1 = getNamedMethod(paramClass, "install");
/* 293 */     Method method2 = getNamedMethod(paramClass, "uninstall");
/* 294 */     Method method3 = getNamedMethod(paramClass, "shutdown");
/*     */     
/* 296 */     Properties properties = new Properties();
/* 297 */     properties.put("org.omg.CORBA.ORBClass", "com.sun.corba.se.impl.orb.ORBImpl");
/*     */ 
/*     */ 
/*     */     
/* 301 */     properties.put("com.sun.CORBA.POA.ORBActivated", "false");
/* 302 */     String[] arrayOfString = null;
/* 303 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     
/* 305 */     ServerCallback serverCallback = new ServerCallback(oRB, method1, method2, method3);
/*     */ 
/*     */     
/* 308 */     int i = getServerId();
/*     */     
/*     */     try {
/* 311 */       Activator activator = ActivatorHelper.narrow(oRB
/* 312 */           .resolve_initial_references("ServerActivator"));
/* 313 */       activator.active(i, (Server)serverCallback);
/* 314 */     } catch (Exception exception) {
/* 315 */       logTerminal("exception " + exception.getMessage(), 6);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/ServerMain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */