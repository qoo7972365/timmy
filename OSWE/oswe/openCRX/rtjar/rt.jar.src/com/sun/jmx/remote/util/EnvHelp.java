/*     */ package com.sun.jmx.remote.util;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import com.sun.jmx.remote.security.NotificationAccessController;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnvHelp
/*     */ {
/*     */   public static final String CREDENTIAL_TYPES = "jmx.remote.rmi.server.credential.types";
/*     */   private static final String DEFAULT_CLASS_LOADER = "jmx.remote.default.class.loader";
/*     */   private static final String DEFAULT_CLASS_LOADER_NAME = "jmx.remote.default.class.loader.name";
/*     */   public static final String BUFFER_SIZE_PROPERTY = "jmx.remote.x.notification.buffer.size";
/*     */   public static final String MAX_FETCH_NOTIFS = "jmx.remote.x.notification.fetch.max";
/*     */   public static final String FETCH_TIMEOUT = "jmx.remote.x.notification.fetch.timeout";
/*     */   public static final String NOTIF_ACCESS_CONTROLLER = "com.sun.jmx.remote.notification.access.controller";
/*     */   public static final String DEFAULT_ORB = "java.naming.corba.orb";
/*     */   public static final String HIDDEN_ATTRIBUTES = "jmx.remote.x.hidden.attributes";
/*     */   public static final String DEFAULT_HIDDEN_ATTRIBUTES = "java.naming.security.* jmx.remote.authenticator jmx.remote.context jmx.remote.default.class.loader jmx.remote.message.connection.server jmx.remote.object.wrapping jmx.remote.rmi.client.socket.factory jmx.remote.rmi.server.socket.factory jmx.remote.sasl.callback.handler jmx.remote.tls.socket.factory jmx.remote.x.access.file jmx.remote.x.password.file ";
/*     */   
/*     */   public static ClassLoader resolveServerClassLoader(Map<String, ?> paramMap, MBeanServer paramMBeanServer) throws InstanceNotFoundException {
/*     */     ObjectName objectName;
/* 140 */     if (paramMap == null) {
/* 141 */       return Thread.currentThread().getContextClassLoader();
/*     */     }
/* 143 */     Object object1 = paramMap.get("jmx.remote.default.class.loader");
/* 144 */     Object object2 = paramMap.get("jmx.remote.default.class.loader.name");
/*     */     
/* 146 */     if (object1 != null && object2 != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 151 */       throw new IllegalArgumentException("Only one of jmx.remote.default.class.loader or jmx.remote.default.class.loader.name should be specified.");
/*     */     }
/*     */     
/* 154 */     if (object1 == null && object2 == null) {
/* 155 */       return Thread.currentThread().getContextClassLoader();
/*     */     }
/* 157 */     if (object1 != null) {
/* 158 */       if (object1 instanceof ClassLoader) {
/* 159 */         return (ClassLoader)object1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 164 */       String str = "ClassLoader object is not an instance of " + ClassLoader.class.getName() + " : " + object1.getClass().getName();
/* 165 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 170 */     if (object2 instanceof ObjectName) {
/* 171 */       objectName = (ObjectName)object2;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 176 */       String str = "ClassLoader name is not an instance of " + ObjectName.class.getName() + " : " + object2.getClass().getName();
/* 177 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 180 */     if (paramMBeanServer == null) {
/* 181 */       throw new IllegalArgumentException("Null MBeanServer object");
/*     */     }
/* 183 */     return paramMBeanServer.getClassLoader(objectName);
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
/*     */   
/*     */   public static ClassLoader resolveClientClassLoader(Map<String, ?> paramMap) {
/* 215 */     if (paramMap == null) {
/* 216 */       return Thread.currentThread().getContextClassLoader();
/*     */     }
/* 218 */     Object object = paramMap.get("jmx.remote.default.class.loader");
/*     */     
/* 220 */     if (object == null) {
/* 221 */       return Thread.currentThread().getContextClassLoader();
/*     */     }
/* 223 */     if (object instanceof ClassLoader) {
/* 224 */       return (ClassLoader)object;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 229 */     String str = "ClassLoader object is not an instance of " + ClassLoader.class.getName() + " : " + object.getClass().getName();
/* 230 */     throw new IllegalArgumentException(str);
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
/*     */   public static <T extends Throwable> T initCause(T paramT, Throwable paramThrowable) {
/* 243 */     paramT.initCause(paramThrowable);
/* 244 */     return paramT;
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
/*     */   public static Throwable getCause(Throwable paramThrowable) {
/* 256 */     Throwable throwable = paramThrowable;
/*     */ 
/*     */     
/*     */     try {
/* 260 */       Method method = paramThrowable.getClass().getMethod("getCause", (Class[])null);
/* 261 */       throwable = (Throwable)method.invoke(paramThrowable, (Object[])null);
/*     */     }
/* 263 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 267 */     return (throwable != null) ? throwable : paramThrowable;
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
/*     */   public static int getNotifBufferSize(Map<String, ?> paramMap) {
/* 284 */     int i = 1000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 293 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.remote.x.notification.buffer.size");
/* 294 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 295 */       if (str != null) {
/* 296 */         i = Integer.parseInt(str);
/*     */       } else {
/* 298 */         getPropertyAction = new GetPropertyAction("jmx.remote.x.buffer.size");
/* 299 */         str = AccessController.<String>doPrivileged(getPropertyAction);
/* 300 */         if (str != null) {
/* 301 */           i = Integer.parseInt(str);
/*     */         }
/*     */       } 
/* 304 */     } catch (RuntimeException runtimeException) {
/* 305 */       logger.warning("getNotifBufferSize", "Can't use System property jmx.remote.x.notification.buffer.size: " + runtimeException);
/*     */ 
/*     */       
/* 308 */       logger.debug("getNotifBufferSize", runtimeException);
/*     */     } 
/*     */     
/* 311 */     int j = i;
/*     */     
/*     */     try {
/* 314 */       if (paramMap.containsKey("jmx.remote.x.notification.buffer.size")) {
/* 315 */         j = (int)getIntegerAttribute(paramMap, "jmx.remote.x.notification.buffer.size", i, 0L, 2147483647L);
/*     */       }
/*     */       else {
/*     */         
/* 319 */         j = (int)getIntegerAttribute(paramMap, "jmx.remote.x.buffer.size", i, 0L, 2147483647L);
/*     */       }
/*     */     
/*     */     }
/* 323 */     catch (RuntimeException runtimeException) {
/* 324 */       logger.warning("getNotifBufferSize", "Can't determine queuesize (using default): " + runtimeException);
/*     */ 
/*     */       
/* 327 */       logger.debug("getNotifBufferSize", runtimeException);
/*     */     } 
/*     */     
/* 330 */     return j;
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
/*     */   public static int getMaxFetchNotifNumber(Map<String, ?> paramMap) {
/* 347 */     return (int)getIntegerAttribute(paramMap, "jmx.remote.x.notification.fetch.max", 1000L, 1L, 2147483647L);
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
/*     */   public static long getFetchTimeout(Map<String, ?> paramMap) {
/* 364 */     return getIntegerAttribute(paramMap, "jmx.remote.x.notification.fetch.timeout", 60000L, 0L, Long.MAX_VALUE);
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
/*     */   public static NotificationAccessController getNotificationAccessController(Map<String, ?> paramMap) {
/* 381 */     return (paramMap == null) ? null : (NotificationAccessController)paramMap
/* 382 */       .get("com.sun.jmx.remote.notification.access.controller");
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
/*     */   public static long getIntegerAttribute(Map<String, ?> paramMap, String paramString, long paramLong1, long paramLong2, long paramLong3) {
/*     */     long l;
/*     */     Object object;
/* 402 */     if (paramMap == null || (object = paramMap.get(paramString)) == null) {
/* 403 */       return paramLong1;
/*     */     }
/*     */ 
/*     */     
/* 407 */     if (object instanceof Number) {
/* 408 */       l = ((Number)object).longValue();
/* 409 */     } else if (object instanceof String) {
/* 410 */       l = Long.parseLong((String)object);
/*     */     }
/*     */     else {
/*     */       
/* 414 */       String str = "Attribute " + paramString + " value must be Integer or String: " + object;
/*     */       
/* 416 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 419 */     if (l < paramLong2) {
/* 420 */       String str = "Attribute " + paramString + " value must be at least " + paramLong2 + ": " + l;
/*     */ 
/*     */       
/* 423 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 426 */     if (l > paramLong3) {
/* 427 */       String str = "Attribute " + paramString + " value must be at most " + paramLong3 + ": " + l;
/*     */ 
/*     */       
/* 430 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 433 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkAttributes(Map<?, ?> paramMap) {
/* 441 */     for (String str : paramMap.keySet()) {
/* 442 */       if (!(str instanceof String)) {
/* 443 */         String str1 = "Attributes contain key that is not a string: " + str;
/*     */         
/* 445 */         throw new IllegalArgumentException(str1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Map<String, V> filterAttributes(Map<String, V> paramMap) {
/* 455 */     if (logger.traceOn()) {
/* 456 */       logger.trace("filterAttributes", "starts");
/*     */     }
/*     */     
/* 459 */     TreeMap<String, V> treeMap = new TreeMap<>(paramMap);
/* 460 */     purgeUnserializable(treeMap.values());
/* 461 */     hideAttributes(treeMap);
/* 462 */     return treeMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void purgeUnserializable(Collection<?> paramCollection) {
/* 470 */     logger.trace("purgeUnserializable", "starts");
/* 471 */     ObjectOutputStream objectOutputStream = null;
/* 472 */     byte b = 0;
/* 473 */     for (Iterator<?> iterator = paramCollection.iterator(); iterator.hasNext(); b++) {
/* 474 */       Object object = iterator.next();
/*     */       
/* 476 */       if (object == null || object instanceof String) {
/* 477 */         if (logger.traceOn()) {
/* 478 */           logger.trace("purgeUnserializable", "Value trivially serializable: " + object);
/*     */         }
/*     */       } else {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 485 */           if (objectOutputStream == null)
/* 486 */             objectOutputStream = new ObjectOutputStream(new SinkOutputStream()); 
/* 487 */           objectOutputStream.writeObject(object);
/* 488 */           if (logger.traceOn()) {
/* 489 */             logger.trace("purgeUnserializable", "Value serializable: " + object);
/*     */           }
/*     */         }
/* 492 */         catch (IOException iOException) {
/* 493 */           if (logger.traceOn()) {
/* 494 */             logger.trace("purgeUnserializable", "Value not serializable: " + object + ": " + iOException);
/*     */           }
/*     */ 
/*     */           
/* 498 */           iterator.remove();
/* 499 */           objectOutputStream = null;
/*     */         } 
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 543 */   private static final SortedSet<String> defaultHiddenStrings = new TreeSet<>();
/*     */   public static final String SERVER_CONNECTION_TIMEOUT = "jmx.remote.x.server.connection.timeout";
/* 545 */   private static final SortedSet<String> defaultHiddenPrefixes = new TreeSet<>();
/*     */   private static void hideAttributes(SortedMap<String, ?> paramSortedMap) {
/*     */     SortedSet<String> sortedSet1, sortedSet2;
/*     */     String str3, str4;
/* 549 */     if (paramSortedMap.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 555 */     String str1 = (String)paramSortedMap.get("jmx.remote.x.hidden.attributes");
/* 556 */     if (str1 != null) {
/* 557 */       if (str1.startsWith("=")) {
/* 558 */         str1 = str1.substring(1);
/*     */       } else {
/* 560 */         str1 = str1 + " java.naming.security.* jmx.remote.authenticator jmx.remote.context jmx.remote.default.class.loader jmx.remote.message.connection.server jmx.remote.object.wrapping jmx.remote.rmi.client.socket.factory jmx.remote.rmi.server.socket.factory jmx.remote.sasl.callback.handler jmx.remote.tls.socket.factory jmx.remote.x.access.file jmx.remote.x.password.file ";
/* 561 */       }  sortedSet1 = new TreeSet();
/* 562 */       sortedSet2 = new TreeSet();
/* 563 */       parseHiddenAttributes(str1, sortedSet1, sortedSet2);
/*     */     } else {
/* 565 */       str1 = "java.naming.security.* jmx.remote.authenticator jmx.remote.context jmx.remote.default.class.loader jmx.remote.message.connection.server jmx.remote.object.wrapping jmx.remote.rmi.client.socket.factory jmx.remote.rmi.server.socket.factory jmx.remote.sasl.callback.handler jmx.remote.tls.socket.factory jmx.remote.x.access.file jmx.remote.x.password.file ";
/* 566 */       synchronized (defaultHiddenStrings) {
/* 567 */         if (defaultHiddenStrings.isEmpty()) {
/* 568 */           parseHiddenAttributes(str1, defaultHiddenStrings, defaultHiddenPrefixes);
/*     */         }
/*     */ 
/*     */         
/* 572 */         sortedSet1 = defaultHiddenStrings;
/* 573 */         sortedSet2 = defaultHiddenPrefixes;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 581 */     String str2 = (String)paramSortedMap.lastKey() + "X";
/* 582 */     Iterator<String> iterator1 = paramSortedMap.keySet().iterator();
/* 583 */     Iterator<String> iterator2 = sortedSet1.iterator();
/* 584 */     Iterator<String> iterator3 = sortedSet2.iterator();
/*     */ 
/*     */     
/* 587 */     if (iterator2.hasNext()) {
/* 588 */       str3 = iterator2.next();
/*     */     } else {
/* 590 */       str3 = str2;
/*     */     } 
/* 592 */     if (iterator3.hasNext()) {
/* 593 */       str4 = iterator3.next();
/*     */     } else {
/* 595 */       str4 = str2;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 600 */     while (iterator1.hasNext()) {
/* 601 */       String str = iterator1.next();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 606 */       int i = 1;
/* 607 */       while ((i = str3.compareTo(str)) < 0) {
/* 608 */         if (iterator2.hasNext()) {
/* 609 */           str3 = iterator2.next(); continue;
/*     */         } 
/* 611 */         str3 = str2;
/*     */       } 
/* 613 */       if (i == 0) {
/* 614 */         iterator1.remove();
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 621 */       while (str4.compareTo(str) <= 0) {
/* 622 */         if (str.startsWith(str4)) {
/* 623 */           iterator1.remove();
/*     */           break;
/*     */         } 
/* 626 */         if (iterator3.hasNext()) {
/* 627 */           str4 = iterator3.next(); continue;
/*     */         } 
/* 629 */         str4 = str2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static final String CLIENT_CONNECTION_CHECK_PERIOD = "jmx.remote.x.client.connection.check.period";
/*     */   public static final String JMX_SERVER_DAEMON = "jmx.remote.x.daemon";
/*     */   
/*     */   private static void parseHiddenAttributes(String paramString, SortedSet<String> paramSortedSet1, SortedSet<String> paramSortedSet2) {
/* 637 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString);
/* 638 */     while (stringTokenizer.hasMoreTokens()) {
/* 639 */       String str = stringTokenizer.nextToken();
/* 640 */       if (str.endsWith("*")) {
/* 641 */         paramSortedSet2.add(str.substring(0, str.length() - 1)); continue;
/*     */       } 
/* 643 */       paramSortedSet1.add(str);
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
/*     */   public static long getServerConnectionTimeout(Map<String, ?> paramMap) {
/* 659 */     return getIntegerAttribute(paramMap, "jmx.remote.x.server.connection.timeout", 120000L, 0L, Long.MAX_VALUE);
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
/*     */   public static long getConnectionCheckPeriod(Map<String, ?> paramMap) {
/* 675 */     return getIntegerAttribute(paramMap, "jmx.remote.x.client.connection.check.period", 60000L, 0L, Long.MAX_VALUE);
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
/*     */   public static boolean computeBooleanFromString(String paramString) {
/* 704 */     return computeBooleanFromString(paramString, false);
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
/*     */   public static boolean computeBooleanFromString(String paramString, boolean paramBoolean) {
/* 734 */     if (paramString == null)
/* 735 */       return paramBoolean; 
/* 736 */     if (paramString.equalsIgnoreCase("true"))
/* 737 */       return true; 
/* 738 */     if (paramString.equalsIgnoreCase("false")) {
/* 739 */       return false;
/*     */     }
/* 741 */     throw new IllegalArgumentException("Property value must be \"true\" or \"false\" instead of \"" + paramString + "\"");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Hashtable<K, V> mapToHashtable(Map<K, V> paramMap) {
/* 751 */     HashMap<K, V> hashMap = new HashMap<>(paramMap);
/* 752 */     if (hashMap.containsKey(null)) hashMap.remove(null); 
/* 753 */     for (Iterator iterator = hashMap.values().iterator(); iterator.hasNext();) {
/* 754 */       if (iterator.next() == null) iterator.remove(); 
/* 755 */     }  return new Hashtable<>(hashMap);
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
/*     */   public static boolean isServerDaemon(Map<String, ?> paramMap) {
/* 772 */     return (paramMap != null && "true"
/* 773 */       .equalsIgnoreCase((String)paramMap.get("jmx.remote.x.daemon")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 781 */   private static final ClassLogger logger = new ClassLogger("javax.management.remote.misc", "EnvHelp");
/*     */   
/*     */   private static final class SinkOutputStream extends OutputStream {
/*     */     private SinkOutputStream() {}
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {}
/*     */     
/*     */     public void write(int param1Int) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/util/EnvHelp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */