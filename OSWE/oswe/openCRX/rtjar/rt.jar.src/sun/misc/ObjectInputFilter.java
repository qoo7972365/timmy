/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.SerializablePermission;
/*     */ import java.security.AccessController;
/*     */ import java.security.Security;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import jdk.internal.util.StaticProperty;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface ObjectInputFilter
/*     */ {
/*     */   Status checkInput(FilterInfo paramFilterInfo);
/*     */   
/*     */   public static interface FilterInfo
/*     */   {
/*     */     Class<?> serialClass();
/*     */     
/*     */     long arrayLength();
/*     */     
/*     */     long depth();
/*     */     
/*     */     long references();
/*     */     
/*     */     long streamBytes();
/*     */   }
/*     */   
/*     */   public enum Status
/*     */   {
/* 180 */     UNDECIDED,
/*     */ 
/*     */ 
/*     */     
/* 184 */     ALLOWED,
/*     */ 
/*     */ 
/*     */     
/* 188 */     REJECTED;
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
/*     */   public static final class Config
/*     */   {
/* 214 */     private static final Object serialFilterLock = new Object();
/*     */     
/*     */     private static final PlatformLogger configLog;
/*     */     
/*     */     private static final String SERIAL_FILTER_PROPNAME = "jdk.serialFilter";
/*     */     
/*     */     private static final ObjectInputFilter configuredFilter;
/*     */     
/*     */     private static ObjectInputFilter serialFilter;
/*     */     
/*     */     static void filterLog(PlatformLogger.Level param1Level, String param1String, Object... param1VarArgs) {
/* 225 */       if (configLog != null) {
/* 226 */         if (PlatformLogger.Level.INFO.equals(param1Level)) {
/* 227 */           configLog.info(param1String, param1VarArgs);
/* 228 */         } else if (PlatformLogger.Level.WARNING.equals(param1Level)) {
/* 229 */           configLog.warning(param1String, param1VarArgs);
/*     */         } else {
/* 231 */           configLog.severe(param1String, param1VarArgs);
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 251 */       configuredFilter = AccessController.<ObjectInputFilter>doPrivileged(() -> {
/*     */             String str = StaticProperty.jdkSerialFilter();
/*     */             if (str == null) {
/*     */               str = Security.getProperty("jdk.serialFilter");
/*     */             }
/*     */             if (str != null) {
/*     */               PlatformLogger platformLogger = PlatformLogger.getLogger("java.io.serialization");
/*     */               platformLogger.info("Creating serialization filter from {0}", new Object[] { str });
/*     */               try {
/*     */                 return createFilter(str);
/* 261 */               } catch (RuntimeException runtimeException) {
/*     */                 platformLogger.warning("Error configuring filter: {0}", runtimeException);
/*     */               } 
/*     */             } 
/*     */             return null;
/*     */           });
/* 267 */       configLog = (configuredFilter != null) ? PlatformLogger.getLogger("java.io.serialization") : null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 273 */       serialFilter = configuredFilter;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ObjectInputFilter getObjectInputFilter(ObjectInputStream param1ObjectInputStream) {
/* 282 */       Objects.requireNonNull(param1ObjectInputStream, "inputStream");
/* 283 */       return SharedSecrets.getJavaOISAccess().getObjectInputFilter(param1ObjectInputStream);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static void setObjectInputFilter(ObjectInputStream param1ObjectInputStream, ObjectInputFilter param1ObjectInputFilter) {
/* 297 */       Objects.requireNonNull(param1ObjectInputStream, "inputStream");
/* 298 */       SharedSecrets.getJavaOISAccess().setObjectInputFilter(param1ObjectInputStream, param1ObjectInputFilter);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ObjectInputFilter getSerialFilter() {
/* 307 */       synchronized (serialFilterLock) {
/* 308 */         return serialFilter;
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
/*     */     
/*     */     public static void setSerialFilter(ObjectInputFilter param1ObjectInputFilter) {
/* 321 */       Objects.requireNonNull(param1ObjectInputFilter, "filter");
/* 322 */       SecurityManager securityManager = System.getSecurityManager();
/* 323 */       if (securityManager != null) {
/* 324 */         securityManager.checkPermission(new SerializablePermission("serialFilter"));
/*     */       }
/* 326 */       synchronized (serialFilterLock) {
/* 327 */         if (serialFilter != null) {
/* 328 */           throw new IllegalStateException("Serial filter can only be set once");
/*     */         }
/* 330 */         serialFilter = param1ObjectInputFilter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ObjectInputFilter createFilter(String param1String) {
/* 383 */       Objects.requireNonNull(param1String, "pattern");
/* 384 */       return Global.createFilter(param1String, true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ObjectInputFilter createFilter2(String param1String) {
/* 396 */       Objects.requireNonNull(param1String, "pattern");
/* 397 */       return Global.createFilter(param1String, false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static final class Global
/*     */       implements ObjectInputFilter
/*     */     {
/*     */       private final String pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private final List<Function<Class<?>, ObjectInputFilter.Status>> filters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private long maxStreamBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private long maxDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private long maxReferences;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private long maxArrayLength;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private final boolean checkComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       static ObjectInputFilter createFilter(String param2String, boolean param2Boolean) {
/* 448 */         Global global = new Global(param2String, param2Boolean);
/* 449 */         return global.isEmpty() ? null : global;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private Global(String param2String, boolean param2Boolean) {
/* 461 */         this.pattern = param2String;
/* 462 */         this.checkComponentType = param2Boolean;
/*     */         
/* 464 */         this.maxArrayLength = Long.MAX_VALUE;
/* 465 */         this.maxDepth = Long.MAX_VALUE;
/* 466 */         this.maxReferences = Long.MAX_VALUE;
/* 467 */         this.maxStreamBytes = Long.MAX_VALUE;
/*     */         
/* 469 */         String[] arrayOfString = param2String.split(";");
/* 470 */         this.filters = new ArrayList<>(arrayOfString.length);
/* 471 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 472 */           String str = arrayOfString[b];
/* 473 */           int i = str.length();
/* 474 */           if (i != 0)
/*     */           {
/*     */             
/* 477 */             if (!parseLimit(str)) {
/*     */ 
/*     */ 
/*     */               
/* 481 */               boolean bool = (str.charAt(0) == '!') ? true : false;
/*     */               
/* 483 */               if (str.indexOf('/') >= 0) {
/* 484 */                 throw new IllegalArgumentException("invalid character \"/\" in: \"" + param2String + "\"");
/*     */               }
/*     */               
/* 487 */               if (str.endsWith("*")) {
/*     */                 
/* 489 */                 if (str.endsWith(".*")) {
/*     */                   
/* 491 */                   String str1 = str.substring(bool ? 1 : 0, i - 1);
/* 492 */                   if (str1.length() < 2) {
/* 493 */                     throw new IllegalArgumentException("package missing in: \"" + param2String + "\"");
/*     */                   }
/* 495 */                   if (bool) {
/*     */                     
/* 497 */                     this.filters.add(param2Class -> matchesPackage(param2Class, param2String) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } else {
/*     */                     
/* 500 */                     this.filters.add(param2Class -> matchesPackage(param2Class, param2String) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } 
/* 502 */                 } else if (str.endsWith(".**")) {
/*     */                   
/* 504 */                   String str1 = str.substring(bool ? 1 : 0, i - 2);
/* 505 */                   if (str1.length() < 2) {
/* 506 */                     throw new IllegalArgumentException("package missing in: \"" + param2String + "\"");
/*     */                   }
/* 508 */                   if (bool) {
/*     */                     
/* 510 */                     this.filters.add(param2Class -> param2Class.getName().startsWith(param2String) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } else {
/*     */                     
/* 513 */                     this.filters.add(param2Class -> param2Class.getName().startsWith(param2String) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } 
/*     */                 } else {
/*     */                   
/* 517 */                   String str1 = str.substring(bool ? 1 : 0, i - 1);
/* 518 */                   if (bool) {
/*     */                     
/* 520 */                     this.filters.add(param2Class -> param2Class.getName().startsWith(param2String) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } else {
/*     */                     
/* 523 */                     this.filters.add(param2Class -> param2Class.getName().startsWith(param2String) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } 
/*     */                 } 
/*     */               } else {
/* 527 */                 String str1 = str.substring(bool ? 1 : 0);
/* 528 */                 if (str1.isEmpty()) {
/* 529 */                   throw new IllegalArgumentException("class or package missing in: \"" + param2String + "\"");
/*     */                 }
/*     */                 
/* 532 */                 if (bool) {
/*     */                   
/* 534 */                   this.filters.add(param2Class -> param2Class.getName().equals(param2String) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED);
/*     */                 } else {
/*     */                   
/* 537 */                   this.filters.add(param2Class -> param2Class.getName().equals(param2String) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.UNDECIDED);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private boolean isEmpty() {
/* 549 */         return (this.filters.isEmpty() && this.maxArrayLength == Long.MAX_VALUE && this.maxDepth == Long.MAX_VALUE && this.maxReferences == Long.MAX_VALUE && this.maxStreamBytes == Long.MAX_VALUE);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private boolean parseLimit(String param2String) {
/* 565 */         int i = param2String.indexOf('=');
/* 566 */         if (i < 0)
/*     */         {
/* 568 */           return false;
/*     */         }
/* 570 */         String str = param2String.substring(i + 1);
/* 571 */         if (param2String.startsWith("maxdepth=")) {
/* 572 */           this.maxDepth = parseValue(str);
/* 573 */         } else if (param2String.startsWith("maxarray=")) {
/* 574 */           this.maxArrayLength = parseValue(str);
/* 575 */         } else if (param2String.startsWith("maxrefs=")) {
/* 576 */           this.maxReferences = parseValue(str);
/* 577 */         } else if (param2String.startsWith("maxbytes=")) {
/* 578 */           this.maxStreamBytes = parseValue(str);
/*     */         } else {
/* 580 */           throw new IllegalArgumentException("unknown limit: " + param2String.substring(0, i));
/*     */         } 
/* 582 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private static long parseValue(String param2String) throws IllegalArgumentException {
/* 593 */         long l = Long.parseLong(param2String);
/* 594 */         if (l < 0L) {
/* 595 */           throw new IllegalArgumentException("negative limit: " + param2String);
/*     */         }
/* 597 */         return l;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public ObjectInputFilter.Status checkInput(ObjectInputFilter.FilterInfo param2FilterInfo) {
/* 605 */         if (param2FilterInfo.references() < 0L || param2FilterInfo
/* 606 */           .depth() < 0L || param2FilterInfo
/* 607 */           .streamBytes() < 0L || param2FilterInfo
/* 608 */           .references() > this.maxReferences || param2FilterInfo
/* 609 */           .depth() > this.maxDepth || param2FilterInfo
/* 610 */           .streamBytes() > this.maxStreamBytes) {
/* 611 */           return ObjectInputFilter.Status.REJECTED;
/*     */         }
/*     */         
/* 614 */         Class<?> clazz = param2FilterInfo.serialClass();
/* 615 */         if (clazz != null) {
/* 616 */           if (clazz.isArray()) {
/* 617 */             if (param2FilterInfo.arrayLength() >= 0L && param2FilterInfo.arrayLength() > this.maxArrayLength)
/*     */             {
/* 619 */               return ObjectInputFilter.Status.REJECTED;
/*     */             }
/* 621 */             if (!this.checkComponentType)
/*     */             {
/* 623 */               return ObjectInputFilter.Status.UNDECIDED;
/*     */             }
/*     */             
/*     */             do {
/* 627 */               clazz = clazz.getComponentType();
/* 628 */             } while (clazz.isArray());
/*     */           } 
/*     */           
/* 631 */           if (clazz.isPrimitive())
/*     */           {
/* 633 */             return ObjectInputFilter.Status.UNDECIDED;
/*     */           }
/*     */           
/* 636 */           Class<?> clazz1 = clazz;
/*     */ 
/*     */ 
/*     */           
/* 640 */           Optional<ObjectInputFilter.Status> optional = this.filters.stream().map(param2Function -> (ObjectInputFilter.Status)param2Function.apply(param2Class)).filter(param2Status -> (param2Status != ObjectInputFilter.Status.UNDECIDED)).findFirst();
/* 641 */           return optional.orElse(ObjectInputFilter.Status.UNDECIDED);
/*     */         } 
/*     */         
/* 644 */         return ObjectInputFilter.Status.UNDECIDED;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private static boolean matchesPackage(Class<?> param2Class, String param2String) {
/* 656 */         String str = param2Class.getName();
/* 657 */         return (str.startsWith(param2String) && str.lastIndexOf('.') == param2String.length() - 1);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public String toString() {
/* 666 */         return this.pattern;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/ObjectInputFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */