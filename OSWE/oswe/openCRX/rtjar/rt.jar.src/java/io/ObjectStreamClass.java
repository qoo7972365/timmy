/*      */ package java.io;
/*      */ 
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Member;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.lang.reflect.UndeclaredThrowableException;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.Permissions;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.misc.JavaSecurityAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.ReflectionFactory;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectStreamClass
/*      */   implements Serializable
/*      */ {
/*   82 */   public static final ObjectStreamField[] NO_FIELDS = new ObjectStreamField[0];
/*      */   
/*      */   private static final long serialVersionUID = -6120832682080437368L;
/*      */   
/*   86 */   private static final ObjectStreamField[] serialPersistentFields = NO_FIELDS;
/*      */   
/*      */   private Class<?> cl;
/*      */   private String name;
/*      */   
/*   91 */   private static boolean disableSerialConstructorChecks = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */       {
/*      */         public Boolean run() {
/*   94 */           String str = "jdk.disableSerialConstructorChecks";
/*   95 */           return "true".equals(System.getProperty(str)) ? Boolean.TRUE : Boolean.FALSE;
/*      */         }
/*   99 */       })).booleanValue();
/*      */   private volatile Long suid; private boolean isProxy;
/*      */   private boolean isEnum;
/*      */   private boolean serializable;
/*  103 */   private static final ReflectionFactory reflFactory = AccessController.<ReflectionFactory>doPrivileged((PrivilegedAction<ReflectionFactory>)new ReflectionFactory.GetReflectionFactoryAction());
/*      */   private boolean externalizable;
/*      */   private boolean hasWriteObjectData;
/*      */   
/*      */   private static class Caches {
/*  108 */     static final ConcurrentMap<ObjectStreamClass.WeakClassKey, Reference<?>> localDescs = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */     
/*  112 */     static final ConcurrentMap<ObjectStreamClass.FieldReflectorKey, Reference<?>> reflectors = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */     
/*  116 */     private static final ReferenceQueue<Class<?>> localDescsQueue = new ReferenceQueue<>();
/*      */ 
/*      */     
/*  119 */     private static final ReferenceQueue<Class<?>> reflectorsQueue = new ReferenceQueue<>();
/*      */   }
/*      */   
/*      */   private boolean hasBlockExternalData = true;
/*      */   
/*      */   private ClassNotFoundException resolveEx;
/*      */   
/*      */   private ExceptionInfo deserializeEx;
/*      */   
/*      */   private ExceptionInfo serializeEx;
/*      */   
/*      */   private ExceptionInfo defaultSerializeEx;
/*      */   
/*      */   private ObjectStreamField[] fields;
/*      */   
/*      */   private int primDataSize;
/*      */   
/*      */   private int numObjFields;
/*      */   
/*      */   private FieldReflector fieldRefl;
/*      */   
/*      */   private volatile ClassDataSlot[] dataLayout;
/*      */   
/*      */   private Constructor<?> cons;
/*      */   private ProtectionDomain[] domains;
/*      */   private Method writeObjectMethod;
/*      */   private Method readObjectMethod;
/*      */   private Method readObjectNoDataMethod;
/*      */   private Method writeReplaceMethod;
/*      */   private Method readResolveMethod;
/*      */   private ObjectStreamClass localDesc;
/*      */   private ObjectStreamClass superDesc;
/*      */   private boolean initialized;
/*      */   
/*      */   private static class ExceptionInfo
/*      */   {
/*      */     private final String className;
/*      */     private final String message;
/*      */     
/*      */     ExceptionInfo(String param1String1, String param1String2) {
/*  159 */       this.className = param1String1;
/*  160 */       this.message = param1String2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     InvalidClassException newInvalidClassException() {
/*  169 */       return new InvalidClassException(this.className, this.message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  222 */     initNative();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ObjectStreamClass lookup(Class<?> paramClass) {
/*  235 */     return lookup(paramClass, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ObjectStreamClass lookupAny(Class<?> paramClass) {
/*  247 */     return lookup(paramClass, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  258 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSerialVersionUID() {
/*  271 */     if (this.suid == null) {
/*  272 */       this.suid = AccessController.<Long>doPrivileged(new PrivilegedAction<Long>()
/*      */           {
/*      */             public Long run() {
/*  275 */               return Long.valueOf(ObjectStreamClass.computeDefaultSUID(ObjectStreamClass.this.cl));
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  280 */     return this.suid.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public Class<?> forClass() {
/*  291 */     if (this.cl == null) {
/*  292 */       return null;
/*      */     }
/*  294 */     requireInitialized();
/*  295 */     if (System.getSecurityManager() != null) {
/*  296 */       Class clazz = Reflection.getCallerClass();
/*  297 */       if (ReflectUtil.needsPackageAccessCheck(clazz.getClassLoader(), this.cl.getClassLoader())) {
/*  298 */         ReflectUtil.checkPackageAccess(this.cl);
/*      */       }
/*      */     } 
/*  301 */     return this.cl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectStreamField[] getFields() {
/*  313 */     return getFields(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectStreamField getField(String paramString) {
/*  324 */     return getField(paramString, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  331 */     return this.name + ": static final long serialVersionUID = " + 
/*  332 */       getSerialVersionUID() + "L;";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ObjectStreamClass lookup(Class<?> paramClass, boolean paramBoolean) {
/*  344 */     if (!paramBoolean && !Serializable.class.isAssignableFrom(paramClass)) {
/*  345 */       return null;
/*      */     }
/*  347 */     processQueue(Caches.localDescsQueue, (ConcurrentMap)Caches.localDescs);
/*  348 */     WeakClassKey weakClassKey = new WeakClassKey(paramClass, Caches.localDescsQueue);
/*  349 */     Reference<Object> reference = (Reference)Caches.localDescs.get(weakClassKey);
/*  350 */     Object object = null;
/*  351 */     if (reference != null) {
/*  352 */       object = reference.get();
/*      */     }
/*  354 */     EntryFuture entryFuture = null;
/*  355 */     if (object == null) {
/*  356 */       EntryFuture entryFuture1 = new EntryFuture();
/*  357 */       SoftReference<EntryFuture> softReference = new SoftReference<>(entryFuture1);
/*      */       do {
/*  359 */         if (reference != null) {
/*  360 */           Caches.localDescs.remove(weakClassKey, reference);
/*      */         }
/*  362 */         reference = (Reference<Object>)Caches.localDescs.putIfAbsent(weakClassKey, softReference);
/*  363 */         if (reference == null)
/*  364 */           continue;  object = reference.get();
/*      */       }
/*  366 */       while (reference != null && object == null);
/*  367 */       if (object == null) {
/*  368 */         entryFuture = entryFuture1;
/*      */       }
/*      */     } 
/*      */     
/*  372 */     if (object instanceof ObjectStreamClass) {
/*  373 */       return (ObjectStreamClass)object;
/*      */     }
/*  375 */     if (object instanceof EntryFuture) {
/*  376 */       entryFuture = (EntryFuture)object;
/*  377 */       if (entryFuture.getOwner() == Thread.currentThread()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  384 */         object = null;
/*      */       } else {
/*  386 */         object = entryFuture.get();
/*      */       } 
/*      */     } 
/*  389 */     if (object == null) {
/*      */       try {
/*  391 */         object = new ObjectStreamClass(paramClass);
/*  392 */       } catch (Throwable throwable) {
/*  393 */         object = throwable;
/*      */       } 
/*  395 */       if (entryFuture.set(object)) {
/*  396 */         Caches.localDescs.put(weakClassKey, new SoftReference(object));
/*      */       } else {
/*      */         
/*  399 */         object = entryFuture.get();
/*      */       } 
/*      */     } 
/*      */     
/*  403 */     if (object instanceof ObjectStreamClass)
/*  404 */       return (ObjectStreamClass)object; 
/*  405 */     if (object instanceof RuntimeException)
/*  406 */       throw (RuntimeException)object; 
/*  407 */     if (object instanceof Error) {
/*  408 */       throw (Error)object;
/*      */     }
/*  410 */     throw new InternalError("unexpected entry: " + object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class EntryFuture
/*      */   {
/*  424 */     private static final Object unset = new Object();
/*  425 */     private final Thread owner = Thread.currentThread();
/*  426 */     private Object entry = unset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized boolean set(Object param1Object) {
/*  436 */       if (this.entry != unset) {
/*  437 */         return false;
/*      */       }
/*  439 */       this.entry = param1Object;
/*  440 */       notifyAll();
/*  441 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized Object get() {
/*  449 */       boolean bool = false;
/*  450 */       while (this.entry == unset) {
/*      */         try {
/*  452 */           wait();
/*  453 */         } catch (InterruptedException interruptedException) {
/*  454 */           bool = true;
/*      */         } 
/*      */       } 
/*  457 */       if (bool) {
/*  458 */         AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */             {
/*      */               public Void run() {
/*  461 */                 Thread.currentThread().interrupt();
/*  462 */                 return null;
/*      */               }
/*      */             });
/*      */       }
/*      */       
/*  467 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Thread getOwner() {
/*  474 */       return this.owner;
/*      */     }
/*      */ 
/*      */     
/*      */     private EntryFuture() {}
/*      */   }
/*      */   
/*      */   private ObjectStreamClass(final Class<?> cl) {
/*  482 */     this.cl = cl;
/*  483 */     this.name = cl.getName();
/*  484 */     this.isProxy = Proxy.isProxyClass(cl);
/*  485 */     this.isEnum = Enum.class.isAssignableFrom(cl);
/*  486 */     this.serializable = Serializable.class.isAssignableFrom(cl);
/*  487 */     this.externalizable = Externalizable.class.isAssignableFrom(cl);
/*      */     
/*  489 */     Class<?> clazz = cl.getSuperclass();
/*  490 */     this.superDesc = (clazz != null) ? lookup(clazz, false) : null;
/*  491 */     this.localDesc = this;
/*      */     
/*  493 */     if (this.serializable) {
/*  494 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */             public Void run() {
/*  496 */               if (ObjectStreamClass.this.isEnum) {
/*  497 */                 ObjectStreamClass.this.suid = Long.valueOf(0L);
/*  498 */                 ObjectStreamClass.this.fields = ObjectStreamClass.NO_FIELDS;
/*  499 */                 return null;
/*      */               } 
/*  501 */               if (cl.isArray()) {
/*  502 */                 ObjectStreamClass.this.fields = ObjectStreamClass.NO_FIELDS;
/*  503 */                 return null;
/*      */               } 
/*      */               
/*  506 */               ObjectStreamClass.this.suid = ObjectStreamClass.getDeclaredSUID(cl);
/*      */               try {
/*  508 */                 ObjectStreamClass.this.fields = ObjectStreamClass.getSerialFields(cl);
/*  509 */                 ObjectStreamClass.this.computeFieldOffsets();
/*  510 */               } catch (InvalidClassException invalidClassException) {
/*  511 */                 ObjectStreamClass.this.serializeEx = ObjectStreamClass.this.deserializeEx = new ObjectStreamClass.ExceptionInfo(invalidClassException.classname, invalidClassException
/*  512 */                     .getMessage());
/*  513 */                 ObjectStreamClass.this.fields = ObjectStreamClass.NO_FIELDS;
/*      */               } 
/*      */               
/*  516 */               if (ObjectStreamClass.this.externalizable) {
/*  517 */                 ObjectStreamClass.this.cons = ObjectStreamClass.getExternalizableConstructor(cl);
/*      */               } else {
/*  519 */                 ObjectStreamClass.this.cons = ObjectStreamClass.getSerializableConstructor(cl);
/*  520 */                 ObjectStreamClass.this.writeObjectMethod = ObjectStreamClass.getPrivateMethod(cl, "writeObject", new Class[] { ObjectOutputStream.class }, void.class);
/*      */ 
/*      */                 
/*  523 */                 ObjectStreamClass.this.readObjectMethod = ObjectStreamClass.getPrivateMethod(cl, "readObject", new Class[] { ObjectInputStream.class }, void.class);
/*      */ 
/*      */                 
/*  526 */                 ObjectStreamClass.this.readObjectNoDataMethod = ObjectStreamClass.getPrivateMethod(cl, "readObjectNoData", null, void.class);
/*      */                 
/*  528 */                 ObjectStreamClass.this.hasWriteObjectData = (ObjectStreamClass.this.writeObjectMethod != null);
/*      */               } 
/*  530 */               ObjectStreamClass.this.domains = ObjectStreamClass.this.getProtectionDomains(ObjectStreamClass.this.cons, cl);
/*  531 */               ObjectStreamClass.this.writeReplaceMethod = ObjectStreamClass.getInheritableMethod(cl, "writeReplace", null, Object.class);
/*      */               
/*  533 */               ObjectStreamClass.this.readResolveMethod = ObjectStreamClass.getInheritableMethod(cl, "readResolve", null, Object.class);
/*      */               
/*  535 */               return null;
/*      */             }
/*      */           });
/*      */     } else {
/*  539 */       this.suid = Long.valueOf(0L);
/*  540 */       this.fields = NO_FIELDS;
/*      */     } 
/*      */     
/*      */     try {
/*  544 */       this.fieldRefl = getReflector(this.fields, this);
/*  545 */     } catch (InvalidClassException invalidClassException) {
/*      */       
/*  547 */       throw new InternalError(invalidClassException);
/*      */     } 
/*      */     
/*  550 */     if (this.deserializeEx == null) {
/*  551 */       if (this.isEnum) {
/*  552 */         this.deserializeEx = new ExceptionInfo(this.name, "enum type");
/*  553 */       } else if (this.cons == null) {
/*  554 */         this.deserializeEx = new ExceptionInfo(this.name, "no valid constructor");
/*      */       } 
/*      */     }
/*  557 */     for (byte b = 0; b < this.fields.length; b++) {
/*  558 */       if (this.fields[b].getField() == null) {
/*  559 */         this.defaultSerializeEx = new ExceptionInfo(this.name, "unmatched serializable field(s) declared");
/*      */       }
/*      */     } 
/*      */     
/*  563 */     this.initialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ProtectionDomain noPermissionsDomain() {
/*  577 */     Permissions permissions = new Permissions();
/*  578 */     permissions.setReadOnly();
/*  579 */     return new ProtectionDomain(null, permissions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ProtectionDomain[] getProtectionDomains(Constructor<?> paramConstructor, Class<?> paramClass) {
/*  602 */     ProtectionDomain[] arrayOfProtectionDomain = null;
/*  603 */     if (paramConstructor != null && paramClass.getClassLoader() != null && 
/*  604 */       System.getSecurityManager() != null) {
/*  605 */       Class<?> clazz1 = paramClass;
/*  606 */       Class<?> clazz2 = paramConstructor.getDeclaringClass();
/*  607 */       HashSet<ProtectionDomain> hashSet = null;
/*  608 */       while (clazz1 != clazz2) {
/*  609 */         ProtectionDomain protectionDomain = clazz1.getProtectionDomain();
/*  610 */         if (protectionDomain != null) {
/*  611 */           if (hashSet == null) hashSet = new HashSet(); 
/*  612 */           hashSet.add(protectionDomain);
/*      */         } 
/*  614 */         clazz1 = clazz1.getSuperclass();
/*  615 */         if (clazz1 == null) {
/*      */ 
/*      */ 
/*      */           
/*  619 */           if (hashSet == null) { hashSet = new HashSet<>(); }
/*  620 */           else { hashSet.clear(); }
/*  621 */            hashSet.add(noPermissionsDomain());
/*      */           break;
/*      */         } 
/*      */       } 
/*  625 */       if (hashSet != null) {
/*  626 */         arrayOfProtectionDomain = hashSet.<ProtectionDomain>toArray(new ProtectionDomain[0]);
/*      */       }
/*      */     } 
/*  629 */     return arrayOfProtectionDomain;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void initProxy(Class<?> paramClass, ClassNotFoundException paramClassNotFoundException, ObjectStreamClass paramObjectStreamClass) throws InvalidClassException {
/*  640 */     ObjectStreamClass objectStreamClass = null;
/*  641 */     if (paramClass != null) {
/*  642 */       objectStreamClass = lookup(paramClass, true);
/*  643 */       if (!objectStreamClass.isProxy) {
/*  644 */         throw new InvalidClassException("cannot bind proxy descriptor to a non-proxy class");
/*      */       }
/*      */     } 
/*      */     
/*  648 */     this.cl = paramClass;
/*  649 */     this.resolveEx = paramClassNotFoundException;
/*  650 */     this.superDesc = paramObjectStreamClass;
/*  651 */     this.isProxy = true;
/*  652 */     this.serializable = true;
/*  653 */     this.suid = Long.valueOf(0L);
/*  654 */     this.fields = NO_FIELDS;
/*  655 */     if (objectStreamClass != null) {
/*  656 */       this.localDesc = objectStreamClass;
/*  657 */       this.name = this.localDesc.name;
/*  658 */       this.externalizable = this.localDesc.externalizable;
/*  659 */       this.writeReplaceMethod = this.localDesc.writeReplaceMethod;
/*  660 */       this.readResolveMethod = this.localDesc.readResolveMethod;
/*  661 */       this.deserializeEx = this.localDesc.deserializeEx;
/*  662 */       this.domains = this.localDesc.domains;
/*  663 */       this.cons = this.localDesc.cons;
/*      */     } 
/*  665 */     this.fieldRefl = getReflector(this.fields, this.localDesc);
/*  666 */     this.initialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void initNonProxy(ObjectStreamClass paramObjectStreamClass1, Class<?> paramClass, ClassNotFoundException paramClassNotFoundException, ObjectStreamClass paramObjectStreamClass2) throws InvalidClassException {
/*  678 */     long l = Long.valueOf(paramObjectStreamClass1.getSerialVersionUID()).longValue();
/*  679 */     ObjectStreamClass objectStreamClass = null;
/*  680 */     if (paramClass != null) {
/*  681 */       objectStreamClass = lookup(paramClass, true);
/*  682 */       if (objectStreamClass.isProxy) {
/*  683 */         throw new InvalidClassException("cannot bind non-proxy descriptor to a proxy class");
/*      */       }
/*      */       
/*  686 */       if (paramObjectStreamClass1.isEnum != objectStreamClass.isEnum) {
/*  687 */         throw new InvalidClassException(paramObjectStreamClass1.isEnum ? "cannot bind enum descriptor to a non-enum class" : "cannot bind non-enum descriptor to an enum class");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  692 */       if (paramObjectStreamClass1.serializable == objectStreamClass.serializable && 
/*  693 */         !paramClass.isArray() && l != objectStreamClass
/*  694 */         .getSerialVersionUID()) {
/*  695 */         throw new InvalidClassException(objectStreamClass.name, "local class incompatible: stream classdesc serialVersionUID = " + l + ", local class serialVersionUID = " + objectStreamClass
/*      */ 
/*      */ 
/*      */             
/*  699 */             .getSerialVersionUID());
/*      */       }
/*      */       
/*  702 */       if (!classNamesEqual(paramObjectStreamClass1.name, objectStreamClass.name)) {
/*  703 */         throw new InvalidClassException(objectStreamClass.name, "local class name incompatible with stream class name \"" + paramObjectStreamClass1.name + "\"");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  708 */       if (!paramObjectStreamClass1.isEnum) {
/*  709 */         if (paramObjectStreamClass1.serializable == objectStreamClass.serializable && paramObjectStreamClass1.externalizable != objectStreamClass.externalizable)
/*      */         {
/*  711 */           throw new InvalidClassException(objectStreamClass.name, "Serializable incompatible with Externalizable");
/*      */         }
/*      */ 
/*      */         
/*  715 */         if (paramObjectStreamClass1.serializable != objectStreamClass.serializable || paramObjectStreamClass1.externalizable != objectStreamClass.externalizable || (!paramObjectStreamClass1.serializable && !paramObjectStreamClass1.externalizable))
/*      */         {
/*      */           
/*  718 */           this.deserializeEx = new ExceptionInfo(objectStreamClass.name, "class invalid for deserialization");
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  724 */     this.cl = paramClass;
/*  725 */     this.resolveEx = paramClassNotFoundException;
/*  726 */     this.superDesc = paramObjectStreamClass2;
/*  727 */     this.name = paramObjectStreamClass1.name;
/*  728 */     this.suid = Long.valueOf(l);
/*  729 */     this.isProxy = false;
/*  730 */     this.isEnum = paramObjectStreamClass1.isEnum;
/*  731 */     this.serializable = paramObjectStreamClass1.serializable;
/*  732 */     this.externalizable = paramObjectStreamClass1.externalizable;
/*  733 */     this.hasBlockExternalData = paramObjectStreamClass1.hasBlockExternalData;
/*  734 */     this.hasWriteObjectData = paramObjectStreamClass1.hasWriteObjectData;
/*  735 */     this.fields = paramObjectStreamClass1.fields;
/*  736 */     this.primDataSize = paramObjectStreamClass1.primDataSize;
/*  737 */     this.numObjFields = paramObjectStreamClass1.numObjFields;
/*      */     
/*  739 */     if (objectStreamClass != null) {
/*  740 */       this.localDesc = objectStreamClass;
/*  741 */       this.writeObjectMethod = this.localDesc.writeObjectMethod;
/*  742 */       this.readObjectMethod = this.localDesc.readObjectMethod;
/*  743 */       this.readObjectNoDataMethod = this.localDesc.readObjectNoDataMethod;
/*  744 */       this.writeReplaceMethod = this.localDesc.writeReplaceMethod;
/*  745 */       this.readResolveMethod = this.localDesc.readResolveMethod;
/*  746 */       if (this.deserializeEx == null) {
/*  747 */         this.deserializeEx = this.localDesc.deserializeEx;
/*      */       }
/*  749 */       this.domains = this.localDesc.domains;
/*  750 */       this.cons = this.localDesc.cons;
/*      */     } 
/*      */     
/*  753 */     this.fieldRefl = getReflector(this.fields, this.localDesc);
/*      */     
/*  755 */     this.fields = this.fieldRefl.getFields();
/*  756 */     this.initialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void readNonProxy(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  768 */     this.name = paramObjectInputStream.readUTF();
/*  769 */     this.suid = Long.valueOf(paramObjectInputStream.readLong());
/*  770 */     this.isProxy = false;
/*      */     
/*  772 */     byte b = paramObjectInputStream.readByte();
/*  773 */     this.hasWriteObjectData = ((b & 0x1) != 0);
/*      */     
/*  775 */     this.hasBlockExternalData = ((b & 0x8) != 0);
/*      */     
/*  777 */     this.externalizable = ((b & 0x4) != 0);
/*      */     
/*  779 */     boolean bool = ((b & 0x2) != 0) ? true : false;
/*      */     
/*  781 */     if (this.externalizable && bool) {
/*  782 */       throw new InvalidClassException(this.name, "serializable and externalizable flags conflict");
/*      */     }
/*      */     
/*  785 */     this.serializable = (this.externalizable || bool);
/*  786 */     this.isEnum = ((b & 0x10) != 0);
/*  787 */     if (this.isEnum && this.suid.longValue() != 0L) {
/*  788 */       throw new InvalidClassException(this.name, "enum descriptor has non-zero serialVersionUID: " + this.suid);
/*      */     }
/*      */ 
/*      */     
/*  792 */     short s = paramObjectInputStream.readShort();
/*  793 */     if (this.isEnum && s != 0) {
/*  794 */       throw new InvalidClassException(this.name, "enum descriptor has non-zero field count: " + s);
/*      */     }
/*      */     
/*  797 */     this.fields = (s > 0) ? new ObjectStreamField[s] : NO_FIELDS;
/*      */     
/*  799 */     for (byte b1 = 0; b1 < s; b1++) {
/*  800 */       char c = (char)paramObjectInputStream.readByte();
/*  801 */       String str1 = paramObjectInputStream.readUTF();
/*      */       
/*  803 */       String str2 = (c == 'L' || c == '[') ? paramObjectInputStream.readTypeString() : new String(new char[] { c });
/*      */       try {
/*  805 */         this.fields[b1] = new ObjectStreamField(str1, str2, false);
/*  806 */       } catch (RuntimeException runtimeException) {
/*  807 */         throw (IOException)(new InvalidClassException(this.name, "invalid descriptor for field " + str1))
/*  808 */           .initCause(runtimeException);
/*      */       } 
/*      */     } 
/*  811 */     computeFieldOffsets();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void writeNonProxy(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  818 */     paramObjectOutputStream.writeUTF(this.name);
/*  819 */     paramObjectOutputStream.writeLong(getSerialVersionUID());
/*      */     
/*  821 */     byte b = 0;
/*  822 */     if (this.externalizable) {
/*  823 */       b = (byte)(b | 0x4);
/*  824 */       int i = paramObjectOutputStream.getProtocolVersion();
/*  825 */       if (i != 1) {
/*  826 */         b = (byte)(b | 0x8);
/*      */       }
/*  828 */     } else if (this.serializable) {
/*  829 */       b = (byte)(b | 0x2);
/*      */     } 
/*  831 */     if (this.hasWriteObjectData) {
/*  832 */       b = (byte)(b | 0x1);
/*      */     }
/*  834 */     if (this.isEnum) {
/*  835 */       b = (byte)(b | 0x10);
/*      */     }
/*  837 */     paramObjectOutputStream.writeByte(b);
/*      */     
/*  839 */     paramObjectOutputStream.writeShort(this.fields.length);
/*  840 */     for (byte b1 = 0; b1 < this.fields.length; b1++) {
/*  841 */       ObjectStreamField objectStreamField = this.fields[b1];
/*  842 */       paramObjectOutputStream.writeByte(objectStreamField.getTypeCode());
/*  843 */       paramObjectOutputStream.writeUTF(objectStreamField.getName());
/*  844 */       if (!objectStreamField.isPrimitive()) {
/*  845 */         paramObjectOutputStream.writeTypeString(objectStreamField.getTypeString());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ClassNotFoundException getResolveException() {
/*  855 */     return this.resolveEx;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void requireInitialized() {
/*  862 */     if (!this.initialized) {
/*  863 */       throw new InternalError("Unexpected call when not initialized");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkDeserialize() throws InvalidClassException {
/*  872 */     requireInitialized();
/*  873 */     if (this.deserializeEx != null) {
/*  874 */       throw this.deserializeEx.newInvalidClassException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkSerialize() throws InvalidClassException {
/*  884 */     requireInitialized();
/*  885 */     if (this.serializeEx != null) {
/*  886 */       throw this.serializeEx.newInvalidClassException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkDefaultSerialize() throws InvalidClassException {
/*  898 */     requireInitialized();
/*  899 */     if (this.defaultSerializeEx != null) {
/*  900 */       throw this.defaultSerializeEx.newInvalidClassException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ObjectStreamClass getSuperDesc() {
/*  910 */     requireInitialized();
/*  911 */     return this.superDesc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ObjectStreamClass getLocalDesc() {
/*  921 */     requireInitialized();
/*  922 */     return this.localDesc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ObjectStreamField[] getFields(boolean paramBoolean) {
/*  932 */     return paramBoolean ? (ObjectStreamField[])this.fields.clone() : this.fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ObjectStreamField getField(String paramString, Class<?> paramClass) {
/*  942 */     for (byte b = 0; b < this.fields.length; b++) {
/*  943 */       ObjectStreamField objectStreamField = this.fields[b];
/*  944 */       if (objectStreamField.getName().equals(paramString)) {
/*  945 */         if (paramClass == null || (paramClass == Object.class && 
/*  946 */           !objectStreamField.isPrimitive()))
/*      */         {
/*  948 */           return objectStreamField;
/*      */         }
/*  950 */         Class<?> clazz = objectStreamField.getType();
/*  951 */         if (clazz != null && paramClass.isAssignableFrom(clazz)) {
/*  952 */           return objectStreamField;
/*      */         }
/*      */       } 
/*      */     } 
/*  956 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isProxy() {
/*  964 */     requireInitialized();
/*  965 */     return this.isProxy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isEnum() {
/*  973 */     requireInitialized();
/*  974 */     return this.isEnum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isExternalizable() {
/*  982 */     requireInitialized();
/*  983 */     return this.externalizable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isSerializable() {
/*  991 */     requireInitialized();
/*  992 */     return this.serializable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean hasBlockExternalData() {
/* 1000 */     requireInitialized();
/* 1001 */     return this.hasBlockExternalData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean hasWriteObjectData() {
/* 1010 */     requireInitialized();
/* 1011 */     return this.hasWriteObjectData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isInstantiable() {
/* 1022 */     requireInitialized();
/* 1023 */     return (this.cons != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean hasWriteObjectMethod() {
/* 1032 */     requireInitialized();
/* 1033 */     return (this.writeObjectMethod != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean hasReadObjectMethod() {
/* 1042 */     requireInitialized();
/* 1043 */     return (this.readObjectMethod != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean hasReadObjectNoDataMethod() {
/* 1052 */     requireInitialized();
/* 1053 */     return (this.readObjectNoDataMethod != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean hasWriteReplaceMethod() {
/* 1061 */     requireInitialized();
/* 1062 */     return (this.writeReplaceMethod != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean hasReadResolveMethod() {
/* 1070 */     requireInitialized();
/* 1071 */     return (this.readResolveMethod != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object newInstance() throws InstantiationException, InvocationTargetException, UnsupportedOperationException {
/* 1087 */     requireInitialized();
/* 1088 */     if (this.cons != null) {
/*      */       try {
/* 1090 */         if (this.domains == null || this.domains.length == 0) {
/* 1091 */           return this.cons.newInstance(new Object[0]);
/*      */         }
/* 1093 */         JavaSecurityAccess javaSecurityAccess = SharedSecrets.getJavaSecurityAccess();
/* 1094 */         PrivilegedAction privilegedAction = () -> {
/*      */             try {
/*      */               return this.cons.newInstance(new Object[0]);
/* 1097 */             } catch (InstantiationException|InvocationTargetException|IllegalAccessException instantiationException) {
/*      */               throw new UndeclaredThrowableException(instantiationException);
/*      */             } 
/*      */           };
/*      */ 
/*      */         
/*      */         try {
/* 1104 */           return javaSecurityAccess.doIntersectionPrivilege(privilegedAction, 
/* 1105 */               AccessController.getContext(), new AccessControlContext(this.domains));
/*      */         }
/* 1107 */         catch (UndeclaredThrowableException undeclaredThrowableException) {
/* 1108 */           Throwable throwable = undeclaredThrowableException.getCause();
/* 1109 */           if (throwable instanceof InstantiationException)
/* 1110 */             throw (InstantiationException)throwable; 
/* 1111 */           if (throwable instanceof InvocationTargetException)
/* 1112 */             throw (InvocationTargetException)throwable; 
/* 1113 */           if (throwable instanceof IllegalAccessException) {
/* 1114 */             throw (IllegalAccessException)throwable;
/*      */           }
/* 1116 */           throw undeclaredThrowableException;
/*      */         }
/*      */       
/* 1119 */       } catch (IllegalAccessException illegalAccessException) {
/*      */         
/* 1121 */         throw new InternalError(illegalAccessException);
/*      */       } 
/*      */     }
/* 1124 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void invokeWriteObject(Object paramObject, ObjectOutputStream paramObjectOutputStream) throws IOException, UnsupportedOperationException {
/* 1137 */     requireInitialized();
/* 1138 */     if (this.writeObjectMethod != null) {
/*      */       try {
/* 1140 */         this.writeObjectMethod.invoke(paramObject, new Object[] { paramObjectOutputStream });
/* 1141 */       } catch (InvocationTargetException invocationTargetException) {
/* 1142 */         Throwable throwable = invocationTargetException.getTargetException();
/* 1143 */         if (throwable instanceof IOException) {
/* 1144 */           throw (IOException)throwable;
/*      */         }
/* 1146 */         throwMiscException(throwable);
/*      */       }
/* 1148 */       catch (IllegalAccessException illegalAccessException) {
/*      */         
/* 1150 */         throw new InternalError(illegalAccessException);
/*      */       } 
/*      */     } else {
/* 1153 */       throw new UnsupportedOperationException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void invokeReadObject(Object paramObject, ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, UnsupportedOperationException {
/* 1167 */     requireInitialized();
/* 1168 */     if (this.readObjectMethod != null) {
/*      */       try {
/* 1170 */         this.readObjectMethod.invoke(paramObject, new Object[] { paramObjectInputStream });
/* 1171 */       } catch (InvocationTargetException invocationTargetException) {
/* 1172 */         Throwable throwable = invocationTargetException.getTargetException();
/* 1173 */         if (throwable instanceof ClassNotFoundException)
/* 1174 */           throw (ClassNotFoundException)throwable; 
/* 1175 */         if (throwable instanceof IOException) {
/* 1176 */           throw (IOException)throwable;
/*      */         }
/* 1178 */         throwMiscException(throwable);
/*      */       }
/* 1180 */       catch (IllegalAccessException illegalAccessException) {
/*      */         
/* 1182 */         throw new InternalError(illegalAccessException);
/*      */       } 
/*      */     } else {
/* 1185 */       throw new UnsupportedOperationException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void invokeReadObjectNoData(Object paramObject) throws IOException, UnsupportedOperationException {
/* 1198 */     requireInitialized();
/* 1199 */     if (this.readObjectNoDataMethod != null) {
/*      */       try {
/* 1201 */         this.readObjectNoDataMethod.invoke(paramObject, (Object[])null);
/* 1202 */       } catch (InvocationTargetException invocationTargetException) {
/* 1203 */         Throwable throwable = invocationTargetException.getTargetException();
/* 1204 */         if (throwable instanceof ObjectStreamException) {
/* 1205 */           throw (ObjectStreamException)throwable;
/*      */         }
/* 1207 */         throwMiscException(throwable);
/*      */       }
/* 1209 */       catch (IllegalAccessException illegalAccessException) {
/*      */         
/* 1211 */         throw new InternalError(illegalAccessException);
/*      */       } 
/*      */     } else {
/* 1214 */       throw new UnsupportedOperationException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object invokeWriteReplace(Object paramObject) throws IOException, UnsupportedOperationException {
/* 1227 */     requireInitialized();
/* 1228 */     if (this.writeReplaceMethod != null) {
/*      */       try {
/* 1230 */         return this.writeReplaceMethod.invoke(paramObject, (Object[])null);
/* 1231 */       } catch (InvocationTargetException invocationTargetException) {
/* 1232 */         Throwable throwable = invocationTargetException.getTargetException();
/* 1233 */         if (throwable instanceof ObjectStreamException) {
/* 1234 */           throw (ObjectStreamException)throwable;
/*      */         }
/* 1236 */         throwMiscException(throwable);
/* 1237 */         throw new InternalError(throwable);
/*      */       }
/* 1239 */       catch (IllegalAccessException illegalAccessException) {
/*      */         
/* 1241 */         throw new InternalError(illegalAccessException);
/*      */       } 
/*      */     }
/* 1244 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object invokeReadResolve(Object paramObject) throws IOException, UnsupportedOperationException {
/* 1257 */     requireInitialized();
/* 1258 */     if (this.readResolveMethod != null) {
/*      */       try {
/* 1260 */         return this.readResolveMethod.invoke(paramObject, (Object[])null);
/* 1261 */       } catch (InvocationTargetException invocationTargetException) {
/* 1262 */         Throwable throwable = invocationTargetException.getTargetException();
/* 1263 */         if (throwable instanceof ObjectStreamException) {
/* 1264 */           throw (ObjectStreamException)throwable;
/*      */         }
/* 1266 */         throwMiscException(throwable);
/* 1267 */         throw new InternalError(throwable);
/*      */       }
/* 1269 */       catch (IllegalAccessException illegalAccessException) {
/*      */         
/* 1271 */         throw new InternalError(illegalAccessException);
/*      */       } 
/*      */     }
/* 1274 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ClassDataSlot
/*      */   {
/*      */     final ObjectStreamClass desc;
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean hasData;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ClassDataSlot(ObjectStreamClass param1ObjectStreamClass, boolean param1Boolean) {
/* 1292 */       this.desc = param1ObjectStreamClass;
/* 1293 */       this.hasData = param1Boolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ClassDataSlot[] getClassDataLayout() throws InvalidClassException {
/* 1306 */     if (this.dataLayout == null) {
/* 1307 */       this.dataLayout = getClassDataLayout0();
/*      */     }
/* 1309 */     return this.dataLayout;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassDataSlot[] getClassDataLayout0() throws InvalidClassException {
/* 1315 */     ArrayList<ClassDataSlot> arrayList = new ArrayList();
/* 1316 */     Class<?> clazz1 = this.cl, clazz2 = this.cl;
/*      */ 
/*      */     
/* 1319 */     while (clazz2 != null && Serializable.class.isAssignableFrom(clazz2)) {
/* 1320 */       clazz2 = clazz2.getSuperclass();
/*      */     }
/*      */     
/* 1323 */     HashSet<String> hashSet = new HashSet(3);
/*      */     
/* 1325 */     for (ObjectStreamClass objectStreamClass = this; objectStreamClass != null; objectStreamClass = objectStreamClass.superDesc) {
/* 1326 */       if (hashSet.contains(objectStreamClass.name)) {
/* 1327 */         throw new InvalidClassException("Circular reference.");
/*      */       }
/* 1329 */       hashSet.add(objectStreamClass.name);
/*      */ 
/*      */ 
/*      */       
/* 1333 */       String str = (objectStreamClass.cl != null) ? objectStreamClass.cl.getName() : objectStreamClass.name;
/* 1334 */       Class<?> clazz4 = null; Class<?> clazz5;
/* 1335 */       for (clazz5 = clazz1; clazz5 != clazz2; clazz5 = clazz5.getSuperclass()) {
/* 1336 */         if (str.equals(clazz5.getName())) {
/* 1337 */           clazz4 = clazz5;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/* 1343 */       if (clazz4 != null) {
/* 1344 */         for (clazz5 = clazz1; clazz5 != clazz4; clazz5 = clazz5.getSuperclass()) {
/* 1345 */           arrayList.add(new ClassDataSlot(
/* 1346 */                 lookup(clazz5, true), false));
/*      */         }
/* 1348 */         clazz1 = clazz4.getSuperclass();
/*      */       } 
/*      */ 
/*      */       
/* 1352 */       arrayList.add(new ClassDataSlot(objectStreamClass.getVariantFor(clazz4), true));
/*      */     } 
/*      */ 
/*      */     
/* 1356 */     for (Class<?> clazz3 = clazz1; clazz3 != clazz2; clazz3 = clazz3.getSuperclass()) {
/* 1357 */       arrayList.add(new ClassDataSlot(
/* 1358 */             lookup(clazz3, true), false));
/*      */     }
/*      */ 
/*      */     
/* 1362 */     Collections.reverse(arrayList);
/* 1363 */     return arrayList.<ClassDataSlot>toArray(new ClassDataSlot[arrayList.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getPrimDataSize() {
/* 1371 */     return this.primDataSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getNumObjFields() {
/* 1379 */     return this.numObjFields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void getPrimFieldValues(Object paramObject, byte[] paramArrayOfbyte) {
/* 1389 */     this.fieldRefl.getPrimFieldValues(paramObject, paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setPrimFieldValues(Object paramObject, byte[] paramArrayOfbyte) {
/* 1399 */     this.fieldRefl.setPrimFieldValues(paramObject, paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void getObjFieldValues(Object paramObject, Object[] paramArrayOfObject) {
/* 1408 */     this.fieldRefl.getObjFieldValues(paramObject, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setObjFieldValues(Object paramObject, Object[] paramArrayOfObject) {
/* 1417 */     this.fieldRefl.setObjFieldValues(paramObject, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void computeFieldOffsets() throws InvalidClassException {
/* 1426 */     this.primDataSize = 0;
/* 1427 */     this.numObjFields = 0;
/* 1428 */     byte b = -1;
/*      */     
/* 1430 */     for (byte b1 = 0; b1 < this.fields.length; b1++) {
/* 1431 */       ObjectStreamField objectStreamField = this.fields[b1];
/* 1432 */       switch (objectStreamField.getTypeCode()) {
/*      */         case 'B':
/*      */         case 'Z':
/* 1435 */           objectStreamField.setOffset(this.primDataSize++);
/*      */           break;
/*      */         
/*      */         case 'C':
/*      */         case 'S':
/* 1440 */           objectStreamField.setOffset(this.primDataSize);
/* 1441 */           this.primDataSize += 2;
/*      */           break;
/*      */         
/*      */         case 'F':
/*      */         case 'I':
/* 1446 */           objectStreamField.setOffset(this.primDataSize);
/* 1447 */           this.primDataSize += 4;
/*      */           break;
/*      */         
/*      */         case 'D':
/*      */         case 'J':
/* 1452 */           objectStreamField.setOffset(this.primDataSize);
/* 1453 */           this.primDataSize += 8;
/*      */           break;
/*      */         
/*      */         case 'L':
/*      */         case '[':
/* 1458 */           objectStreamField.setOffset(this.numObjFields++);
/* 1459 */           if (b == -1) {
/* 1460 */             b = b1;
/*      */           }
/*      */           break;
/*      */         
/*      */         default:
/* 1465 */           throw new InternalError();
/*      */       } 
/*      */     } 
/* 1468 */     if (b != -1 && b + this.numObjFields != this.fields.length)
/*      */     {
/*      */       
/* 1471 */       throw new InvalidClassException(this.name, "illegal field order");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ObjectStreamClass getVariantFor(Class<?> paramClass) throws InvalidClassException {
/* 1483 */     if (this.cl == paramClass) {
/* 1484 */       return this;
/*      */     }
/* 1486 */     ObjectStreamClass objectStreamClass = new ObjectStreamClass();
/* 1487 */     if (this.isProxy) {
/* 1488 */       objectStreamClass.initProxy(paramClass, null, this.superDesc);
/*      */     } else {
/* 1490 */       objectStreamClass.initNonProxy(this, paramClass, null, this.superDesc);
/*      */     } 
/* 1492 */     return objectStreamClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Constructor<?> getExternalizableConstructor(Class<?> paramClass) {
/*      */     try {
/* 1502 */       Constructor<?> constructor = paramClass.getDeclaredConstructor((Class[])null);
/* 1503 */       constructor.setAccessible(true);
/* 1504 */       return ((constructor.getModifiers() & 0x1) != 0) ? constructor : null;
/*      */     }
/* 1506 */     catch (NoSuchMethodException noSuchMethodException) {
/* 1507 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean superHasAccessibleConstructor(Class<?> paramClass) {
/* 1525 */     Class<?> clazz = paramClass.getSuperclass();
/* 1526 */     assert Serializable.class.isAssignableFrom(paramClass);
/* 1527 */     assert clazz != null;
/* 1528 */     if (packageEquals(paramClass, clazz)) {
/*      */       
/* 1530 */       for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
/* 1531 */         if ((constructor.getModifiers() & 0x2) == 0) {
/* 1532 */           return true;
/*      */         }
/*      */       } 
/* 1535 */       return false;
/*      */     } 
/*      */     
/* 1538 */     if ((clazz.getModifiers() & 0x5) == 0) {
/* 1539 */       return false;
/*      */     }
/*      */     
/* 1542 */     for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
/* 1543 */       if ((constructor.getModifiers() & 0x5) != 0) {
/* 1544 */         return true;
/*      */       }
/*      */     } 
/* 1547 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Constructor<?> getSerializableConstructor(Class<?> paramClass) {
/* 1557 */     Class<?> clazz = paramClass;
/* 1558 */     while (Serializable.class.isAssignableFrom(clazz)) {
/* 1559 */       Class<?> clazz1 = clazz;
/* 1560 */       if ((clazz = clazz.getSuperclass()) == null || (!disableSerialConstructorChecks && 
/* 1561 */         !superHasAccessibleConstructor(clazz1))) {
/* 1562 */         return null;
/*      */       }
/*      */     } 
/*      */     try {
/* 1566 */       Constructor<?> constructor = clazz.getDeclaredConstructor((Class[])null);
/* 1567 */       int i = constructor.getModifiers();
/* 1568 */       if ((i & 0x2) != 0 || ((i & 0x5) == 0 && 
/*      */         
/* 1570 */         !packageEquals(paramClass, clazz)))
/*      */       {
/* 1572 */         return null;
/*      */       }
/* 1574 */       constructor = reflFactory.newConstructorForSerialization(paramClass, constructor);
/* 1575 */       constructor.setAccessible(true);
/* 1576 */       return constructor;
/* 1577 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 1578 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method getInheritableMethod(Class<?> paramClass1, String paramString, Class<?>[] paramArrayOfClass, Class<?> paramClass2) {
/* 1592 */     Method method = null;
/* 1593 */     Class<?> clazz = paramClass1;
/* 1594 */     while (clazz != null) {
/*      */       try {
/* 1596 */         method = clazz.getDeclaredMethod(paramString, paramArrayOfClass);
/*      */         break;
/* 1598 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 1599 */         clazz = clazz.getSuperclass();
/*      */       } 
/*      */     } 
/*      */     
/* 1603 */     if (method == null || method.getReturnType() != paramClass2) {
/* 1604 */       return null;
/*      */     }
/* 1606 */     method.setAccessible(true);
/* 1607 */     int i = method.getModifiers();
/* 1608 */     if ((i & 0x408) != 0)
/* 1609 */       return null; 
/* 1610 */     if ((i & 0x5) != 0)
/* 1611 */       return method; 
/* 1612 */     if ((i & 0x2) != 0) {
/* 1613 */       return (paramClass1 == clazz) ? method : null;
/*      */     }
/* 1615 */     return packageEquals(paramClass1, clazz) ? method : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method getPrivateMethod(Class<?> paramClass1, String paramString, Class<?>[] paramArrayOfClass, Class<?> paramClass2) {
/*      */     try {
/* 1629 */       Method method = paramClass1.getDeclaredMethod(paramString, paramArrayOfClass);
/* 1630 */       method.setAccessible(true);
/* 1631 */       int i = method.getModifiers();
/* 1632 */       return (method.getReturnType() == paramClass2 && (i & 0x8) == 0 && (i & 0x2) != 0) ? method : null;
/*      */     
/*      */     }
/* 1635 */     catch (NoSuchMethodException noSuchMethodException) {
/* 1636 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean packageEquals(Class<?> paramClass1, Class<?> paramClass2) {
/* 1645 */     return (paramClass1.getClassLoader() == paramClass2.getClassLoader() && 
/* 1646 */       getPackageName(paramClass1).equals(getPackageName(paramClass2)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getPackageName(Class<?> paramClass) {
/* 1653 */     String str = paramClass.getName();
/* 1654 */     int i = str.lastIndexOf('[');
/* 1655 */     if (i >= 0) {
/* 1656 */       str = str.substring(i + 2);
/*      */     }
/* 1658 */     i = str.lastIndexOf('.');
/* 1659 */     return (i >= 0) ? str.substring(0, i) : "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean classNamesEqual(String paramString1, String paramString2) {
/* 1667 */     paramString1 = paramString1.substring(paramString1.lastIndexOf('.') + 1);
/* 1668 */     paramString2 = paramString2.substring(paramString2.lastIndexOf('.') + 1);
/* 1669 */     return paramString1.equals(paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getClassSignature(Class<?> paramClass) {
/* 1676 */     StringBuilder stringBuilder = new StringBuilder();
/* 1677 */     while (paramClass.isArray()) {
/* 1678 */       stringBuilder.append('[');
/* 1679 */       paramClass = paramClass.getComponentType();
/*      */     } 
/* 1681 */     if (paramClass.isPrimitive()) {
/* 1682 */       if (paramClass == int.class) {
/* 1683 */         stringBuilder.append('I');
/* 1684 */       } else if (paramClass == byte.class) {
/* 1685 */         stringBuilder.append('B');
/* 1686 */       } else if (paramClass == long.class) {
/* 1687 */         stringBuilder.append('J');
/* 1688 */       } else if (paramClass == float.class) {
/* 1689 */         stringBuilder.append('F');
/* 1690 */       } else if (paramClass == double.class) {
/* 1691 */         stringBuilder.append('D');
/* 1692 */       } else if (paramClass == short.class) {
/* 1693 */         stringBuilder.append('S');
/* 1694 */       } else if (paramClass == char.class) {
/* 1695 */         stringBuilder.append('C');
/* 1696 */       } else if (paramClass == boolean.class) {
/* 1697 */         stringBuilder.append('Z');
/* 1698 */       } else if (paramClass == void.class) {
/* 1699 */         stringBuilder.append('V');
/*      */       } else {
/* 1701 */         throw new InternalError();
/*      */       } 
/*      */     } else {
/* 1704 */       stringBuilder.append('L' + paramClass.getName().replace('.', '/') + ';');
/*      */     } 
/* 1706 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getMethodSignature(Class<?>[] paramArrayOfClass, Class<?> paramClass) {
/* 1715 */     StringBuilder stringBuilder = new StringBuilder();
/* 1716 */     stringBuilder.append('(');
/* 1717 */     for (byte b = 0; b < paramArrayOfClass.length; b++) {
/* 1718 */       stringBuilder.append(getClassSignature(paramArrayOfClass[b]));
/*      */     }
/* 1720 */     stringBuilder.append(')');
/* 1721 */     stringBuilder.append(getClassSignature(paramClass));
/* 1722 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void throwMiscException(Throwable paramThrowable) throws IOException {
/* 1731 */     if (paramThrowable instanceof RuntimeException)
/* 1732 */       throw (RuntimeException)paramThrowable; 
/* 1733 */     if (paramThrowable instanceof Error) {
/* 1734 */       throw (Error)paramThrowable;
/*      */     }
/* 1736 */     IOException iOException = new IOException("unexpected exception type");
/* 1737 */     iOException.initCause(paramThrowable);
/* 1738 */     throw iOException;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ObjectStreamField[] getSerialFields(Class<?> paramClass) throws InvalidClassException {
/*      */     ObjectStreamField[] arrayOfObjectStreamField;
/* 1753 */     if (Serializable.class.isAssignableFrom(paramClass) && 
/* 1754 */       !Externalizable.class.isAssignableFrom(paramClass) && 
/* 1755 */       !Proxy.isProxyClass(paramClass) && 
/* 1756 */       !paramClass.isInterface()) {
/*      */       
/* 1758 */       if ((arrayOfObjectStreamField = getDeclaredSerialFields(paramClass)) == null) {
/* 1759 */         arrayOfObjectStreamField = getDefaultSerialFields(paramClass);
/*      */       }
/* 1761 */       Arrays.sort((Object[])arrayOfObjectStreamField);
/*      */     } else {
/* 1763 */       arrayOfObjectStreamField = NO_FIELDS;
/*      */     } 
/* 1765 */     return arrayOfObjectStreamField;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ObjectStreamField[] getDeclaredSerialFields(Class<?> paramClass) throws InvalidClassException {
/* 1782 */     ObjectStreamField[] arrayOfObjectStreamField1 = null;
/*      */     try {
/* 1784 */       Field field = paramClass.getDeclaredField("serialPersistentFields");
/* 1785 */       byte b1 = 26;
/* 1786 */       if ((field.getModifiers() & b1) == b1) {
/* 1787 */         field.setAccessible(true);
/* 1788 */         arrayOfObjectStreamField1 = (ObjectStreamField[])field.get(null);
/*      */       } 
/* 1790 */     } catch (Exception exception) {}
/*      */     
/* 1792 */     if (arrayOfObjectStreamField1 == null)
/* 1793 */       return null; 
/* 1794 */     if (arrayOfObjectStreamField1.length == 0) {
/* 1795 */       return NO_FIELDS;
/*      */     }
/*      */     
/* 1798 */     ObjectStreamField[] arrayOfObjectStreamField2 = new ObjectStreamField[arrayOfObjectStreamField1.length];
/*      */     
/* 1800 */     HashSet<String> hashSet = new HashSet(arrayOfObjectStreamField1.length);
/*      */     
/* 1802 */     for (byte b = 0; b < arrayOfObjectStreamField1.length; b++) {
/* 1803 */       ObjectStreamField objectStreamField = arrayOfObjectStreamField1[b];
/*      */       
/* 1805 */       String str = objectStreamField.getName();
/* 1806 */       if (hashSet.contains(str)) {
/* 1807 */         throw new InvalidClassException("multiple serializable fields named " + str);
/*      */       }
/*      */       
/* 1810 */       hashSet.add(str);
/*      */       
/*      */       try {
/* 1813 */         Field field = paramClass.getDeclaredField(str);
/* 1814 */         if (field.getType() == objectStreamField.getType() && (field
/* 1815 */           .getModifiers() & 0x8) == 0)
/*      */         {
/* 1817 */           arrayOfObjectStreamField2[b] = new ObjectStreamField(field, objectStreamField
/* 1818 */               .isUnshared(), true);
/*      */         }
/* 1820 */       } catch (NoSuchFieldException noSuchFieldException) {}
/*      */       
/* 1822 */       if (arrayOfObjectStreamField2[b] == null) {
/* 1823 */         arrayOfObjectStreamField2[b] = new ObjectStreamField(str, objectStreamField
/* 1824 */             .getType(), objectStreamField.isUnshared());
/*      */       }
/*      */     } 
/* 1827 */     return arrayOfObjectStreamField2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ObjectStreamField[] getDefaultSerialFields(Class<?> paramClass) {
/* 1837 */     Field[] arrayOfField = paramClass.getDeclaredFields();
/* 1838 */     ArrayList<ObjectStreamField> arrayList = new ArrayList();
/* 1839 */     char c = '';
/*      */     int i;
/* 1841 */     for (i = 0; i < arrayOfField.length; i++) {
/* 1842 */       if ((arrayOfField[i].getModifiers() & c) == 0) {
/* 1843 */         arrayList.add(new ObjectStreamField(arrayOfField[i], false, true));
/*      */       }
/*      */     } 
/* 1846 */     i = arrayList.size();
/* 1847 */     return (i == 0) ? NO_FIELDS : arrayList
/* 1848 */       .<ObjectStreamField>toArray(new ObjectStreamField[i]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Long getDeclaredSUID(Class<?> paramClass) {
/*      */     try {
/* 1857 */       Field field = paramClass.getDeclaredField("serialVersionUID");
/* 1858 */       byte b = 24;
/* 1859 */       if ((field.getModifiers() & b) == b) {
/* 1860 */         field.setAccessible(true);
/* 1861 */         return Long.valueOf(field.getLong(null));
/*      */       } 
/* 1863 */     } catch (Exception exception) {}
/*      */     
/* 1865 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long computeDefaultSUID(Class<?> paramClass) {
/* 1872 */     if (!Serializable.class.isAssignableFrom(paramClass) || Proxy.isProxyClass(paramClass))
/*      */     {
/* 1874 */       return 0L;
/*      */     }
/*      */     
/*      */     try {
/* 1878 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 1879 */       DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
/*      */       
/* 1881 */       dataOutputStream.writeUTF(paramClass.getName());
/*      */       
/* 1883 */       int i = paramClass.getModifiers() & 0x611;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1891 */       Method[] arrayOfMethod = paramClass.getDeclaredMethods();
/* 1892 */       if ((i & 0x200) != 0) {
/* 1893 */         i = (arrayOfMethod.length > 0) ? (i | 0x400) : (i & 0xFFFFFBFF);
/*      */       }
/*      */ 
/*      */       
/* 1897 */       dataOutputStream.writeInt(i);
/*      */       
/* 1899 */       if (!paramClass.isArray()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1905 */         Class[] arrayOfClass = paramClass.getInterfaces();
/* 1906 */         String[] arrayOfString = new String[arrayOfClass.length]; byte b;
/* 1907 */         for (b = 0; b < arrayOfClass.length; b++) {
/* 1908 */           arrayOfString[b] = arrayOfClass[b].getName();
/*      */         }
/* 1910 */         Arrays.sort((Object[])arrayOfString);
/* 1911 */         for (b = 0; b < arrayOfString.length; b++) {
/* 1912 */           dataOutputStream.writeUTF(arrayOfString[b]);
/*      */         }
/*      */       } 
/*      */       
/* 1916 */       Field[] arrayOfField = paramClass.getDeclaredFields();
/* 1917 */       MemberSignature[] arrayOfMemberSignature1 = new MemberSignature[arrayOfField.length]; byte b1;
/* 1918 */       for (b1 = 0; b1 < arrayOfField.length; b1++) {
/* 1919 */         arrayOfMemberSignature1[b1] = new MemberSignature(arrayOfField[b1]);
/*      */       }
/* 1921 */       Arrays.sort(arrayOfMemberSignature1, new Comparator<MemberSignature>() {
/*      */             public int compare(ObjectStreamClass.MemberSignature param1MemberSignature1, ObjectStreamClass.MemberSignature param1MemberSignature2) {
/* 1923 */               return param1MemberSignature1.name.compareTo(param1MemberSignature2.name);
/*      */             }
/*      */           });
/* 1926 */       for (b1 = 0; b1 < arrayOfMemberSignature1.length; b1++) {
/* 1927 */         MemberSignature memberSignature = arrayOfMemberSignature1[b1];
/* 1928 */         int k = memberSignature.member.getModifiers() & 0xDF;
/*      */ 
/*      */ 
/*      */         
/* 1932 */         if ((k & 0x2) == 0 || (k & 0x88) == 0) {
/*      */ 
/*      */           
/* 1935 */           dataOutputStream.writeUTF(memberSignature.name);
/* 1936 */           dataOutputStream.writeInt(k);
/* 1937 */           dataOutputStream.writeUTF(memberSignature.signature);
/*      */         } 
/*      */       } 
/*      */       
/* 1941 */       if (hasStaticInitializer(paramClass)) {
/* 1942 */         dataOutputStream.writeUTF("<clinit>");
/* 1943 */         dataOutputStream.writeInt(8);
/* 1944 */         dataOutputStream.writeUTF("()V");
/*      */       } 
/*      */       
/* 1947 */       Constructor[] arrayOfConstructor = (Constructor[])paramClass.getDeclaredConstructors();
/* 1948 */       MemberSignature[] arrayOfMemberSignature2 = new MemberSignature[arrayOfConstructor.length]; byte b2;
/* 1949 */       for (b2 = 0; b2 < arrayOfConstructor.length; b2++) {
/* 1950 */         arrayOfMemberSignature2[b2] = new MemberSignature(arrayOfConstructor[b2]);
/*      */       }
/* 1952 */       Arrays.sort(arrayOfMemberSignature2, new Comparator<MemberSignature>() {
/*      */             public int compare(ObjectStreamClass.MemberSignature param1MemberSignature1, ObjectStreamClass.MemberSignature param1MemberSignature2) {
/* 1954 */               return param1MemberSignature1.signature.compareTo(param1MemberSignature2.signature);
/*      */             }
/*      */           });
/* 1957 */       for (b2 = 0; b2 < arrayOfMemberSignature2.length; b2++) {
/* 1958 */         MemberSignature memberSignature = arrayOfMemberSignature2[b2];
/* 1959 */         int k = memberSignature.member.getModifiers() & 0xD3F;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1964 */         if ((k & 0x2) == 0) {
/* 1965 */           dataOutputStream.writeUTF("<init>");
/* 1966 */           dataOutputStream.writeInt(k);
/* 1967 */           dataOutputStream.writeUTF(memberSignature.signature.replace('/', '.'));
/*      */         } 
/*      */       } 
/*      */       
/* 1971 */       MemberSignature[] arrayOfMemberSignature3 = new MemberSignature[arrayOfMethod.length]; byte b3;
/* 1972 */       for (b3 = 0; b3 < arrayOfMethod.length; b3++) {
/* 1973 */         arrayOfMemberSignature3[b3] = new MemberSignature(arrayOfMethod[b3]);
/*      */       }
/* 1975 */       Arrays.sort(arrayOfMemberSignature3, new Comparator<MemberSignature>() {
/*      */             public int compare(ObjectStreamClass.MemberSignature param1MemberSignature1, ObjectStreamClass.MemberSignature param1MemberSignature2) {
/* 1977 */               int i = param1MemberSignature1.name.compareTo(param1MemberSignature2.name);
/* 1978 */               if (i == 0) {
/* 1979 */                 i = param1MemberSignature1.signature.compareTo(param1MemberSignature2.signature);
/*      */               }
/* 1981 */               return i;
/*      */             }
/*      */           });
/* 1984 */       for (b3 = 0; b3 < arrayOfMemberSignature3.length; b3++) {
/* 1985 */         MemberSignature memberSignature = arrayOfMemberSignature3[b3];
/* 1986 */         int k = memberSignature.member.getModifiers() & 0xD3F;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1991 */         if ((k & 0x2) == 0) {
/* 1992 */           dataOutputStream.writeUTF(memberSignature.name);
/* 1993 */           dataOutputStream.writeInt(k);
/* 1994 */           dataOutputStream.writeUTF(memberSignature.signature.replace('/', '.'));
/*      */         } 
/*      */       } 
/*      */       
/* 1998 */       dataOutputStream.flush();
/*      */       
/* 2000 */       MessageDigest messageDigest = MessageDigest.getInstance("SHA");
/* 2001 */       byte[] arrayOfByte = messageDigest.digest(byteArrayOutputStream.toByteArray());
/* 2002 */       long l = 0L;
/* 2003 */       for (int j = Math.min(arrayOfByte.length, 8) - 1; j >= 0; j--) {
/* 2004 */         l = l << 8L | (arrayOfByte[j] & 0xFF);
/*      */       }
/* 2006 */       return l;
/* 2007 */     } catch (IOException iOException) {
/* 2008 */       throw new InternalError(iOException);
/* 2009 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 2010 */       throw new SecurityException(noSuchAlgorithmException.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class MemberSignature
/*      */   {
/*      */     public final Member member;
/*      */ 
/*      */ 
/*      */     
/*      */     public final String name;
/*      */ 
/*      */     
/*      */     public final String signature;
/*      */ 
/*      */ 
/*      */     
/*      */     public MemberSignature(Field param1Field) {
/* 2031 */       this.member = param1Field;
/* 2032 */       this.name = param1Field.getName();
/* 2033 */       this.signature = ObjectStreamClass.getClassSignature(param1Field.getType());
/*      */     }
/*      */     
/*      */     public MemberSignature(Constructor<?> param1Constructor) {
/* 2037 */       this.member = param1Constructor;
/* 2038 */       this.name = param1Constructor.getName();
/* 2039 */       this.signature = ObjectStreamClass.getMethodSignature(param1Constructor
/* 2040 */           .getParameterTypes(), void.class);
/*      */     }
/*      */     
/*      */     public MemberSignature(Method param1Method) {
/* 2044 */       this.member = param1Method;
/* 2045 */       this.name = param1Method.getName();
/* 2046 */       this.signature = ObjectStreamClass.getMethodSignature(param1Method
/* 2047 */           .getParameterTypes(), param1Method.getReturnType());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class FieldReflector
/*      */   {
/* 2058 */     private static final Unsafe unsafe = Unsafe.getUnsafe();
/*      */ 
/*      */     
/*      */     private final ObjectStreamField[] fields;
/*      */ 
/*      */     
/*      */     private final int numPrimFields;
/*      */ 
/*      */     
/*      */     private final long[] readKeys;
/*      */ 
/*      */     
/*      */     private final long[] writeKeys;
/*      */ 
/*      */     
/*      */     private final int[] offsets;
/*      */ 
/*      */     
/*      */     private final char[] typeCodes;
/*      */ 
/*      */     
/*      */     private final Class<?>[] types;
/*      */ 
/*      */     
/*      */     FieldReflector(ObjectStreamField[] param1ArrayOfObjectStreamField) {
/* 2083 */       this.fields = param1ArrayOfObjectStreamField;
/* 2084 */       int i = param1ArrayOfObjectStreamField.length;
/* 2085 */       this.readKeys = new long[i];
/* 2086 */       this.writeKeys = new long[i];
/* 2087 */       this.offsets = new int[i];
/* 2088 */       this.typeCodes = new char[i];
/* 2089 */       ArrayList<Class<?>> arrayList = new ArrayList();
/* 2090 */       HashSet<Long> hashSet = new HashSet();
/*      */ 
/*      */       
/* 2093 */       for (byte b = 0; b < i; b++) {
/* 2094 */         ObjectStreamField objectStreamField = param1ArrayOfObjectStreamField[b];
/* 2095 */         Field field = objectStreamField.getField();
/*      */         
/* 2097 */         long l = (field != null) ? unsafe.objectFieldOffset(field) : -1L;
/* 2098 */         this.readKeys[b] = l;
/* 2099 */         this.writeKeys[b] = hashSet.add(Long.valueOf(l)) ? l : -1L;
/*      */         
/* 2101 */         this.offsets[b] = objectStreamField.getOffset();
/* 2102 */         this.typeCodes[b] = objectStreamField.getTypeCode();
/* 2103 */         if (!objectStreamField.isPrimitive()) {
/* 2104 */           arrayList.add((field != null) ? field.getType() : null);
/*      */         }
/*      */       } 
/*      */       
/* 2108 */       this.types = (Class[])arrayList.<Class<?>[]>toArray((Class<?>[][])new Class[arrayList.size()]);
/* 2109 */       this.numPrimFields = i - this.types.length;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectStreamField[] getFields() {
/* 2119 */       return this.fields;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void getPrimFieldValues(Object param1Object, byte[] param1ArrayOfbyte) {
/* 2128 */       if (param1Object == null) {
/* 2129 */         throw new NullPointerException();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2135 */       for (byte b = 0; b < this.numPrimFields; b++) {
/* 2136 */         long l = this.readKeys[b];
/* 2137 */         int i = this.offsets[b];
/* 2138 */         switch (this.typeCodes[b]) {
/*      */           case 'Z':
/* 2140 */             Bits.putBoolean(param1ArrayOfbyte, i, unsafe.getBoolean(param1Object, l));
/*      */             break;
/*      */           
/*      */           case 'B':
/* 2144 */             param1ArrayOfbyte[i] = unsafe.getByte(param1Object, l);
/*      */             break;
/*      */           
/*      */           case 'C':
/* 2148 */             Bits.putChar(param1ArrayOfbyte, i, unsafe.getChar(param1Object, l));
/*      */             break;
/*      */           
/*      */           case 'S':
/* 2152 */             Bits.putShort(param1ArrayOfbyte, i, unsafe.getShort(param1Object, l));
/*      */             break;
/*      */           
/*      */           case 'I':
/* 2156 */             Bits.putInt(param1ArrayOfbyte, i, unsafe.getInt(param1Object, l));
/*      */             break;
/*      */           
/*      */           case 'F':
/* 2160 */             Bits.putFloat(param1ArrayOfbyte, i, unsafe.getFloat(param1Object, l));
/*      */             break;
/*      */           
/*      */           case 'J':
/* 2164 */             Bits.putLong(param1ArrayOfbyte, i, unsafe.getLong(param1Object, l));
/*      */             break;
/*      */           
/*      */           case 'D':
/* 2168 */             Bits.putDouble(param1ArrayOfbyte, i, unsafe.getDouble(param1Object, l));
/*      */             break;
/*      */           
/*      */           default:
/* 2172 */             throw new InternalError();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setPrimFieldValues(Object param1Object, byte[] param1ArrayOfbyte) {
/* 2183 */       if (param1Object == null) {
/* 2184 */         throw new NullPointerException();
/*      */       }
/* 2186 */       for (byte b = 0; b < this.numPrimFields; b++) {
/* 2187 */         long l = this.writeKeys[b];
/* 2188 */         if (l != -1L) {
/*      */ 
/*      */           
/* 2191 */           int i = this.offsets[b];
/* 2192 */           switch (this.typeCodes[b]) {
/*      */             case 'Z':
/* 2194 */               unsafe.putBoolean(param1Object, l, Bits.getBoolean(param1ArrayOfbyte, i));
/*      */               break;
/*      */             
/*      */             case 'B':
/* 2198 */               unsafe.putByte(param1Object, l, param1ArrayOfbyte[i]);
/*      */               break;
/*      */             
/*      */             case 'C':
/* 2202 */               unsafe.putChar(param1Object, l, Bits.getChar(param1ArrayOfbyte, i));
/*      */               break;
/*      */             
/*      */             case 'S':
/* 2206 */               unsafe.putShort(param1Object, l, Bits.getShort(param1ArrayOfbyte, i));
/*      */               break;
/*      */             
/*      */             case 'I':
/* 2210 */               unsafe.putInt(param1Object, l, Bits.getInt(param1ArrayOfbyte, i));
/*      */               break;
/*      */             
/*      */             case 'F':
/* 2214 */               unsafe.putFloat(param1Object, l, Bits.getFloat(param1ArrayOfbyte, i));
/*      */               break;
/*      */             
/*      */             case 'J':
/* 2218 */               unsafe.putLong(param1Object, l, Bits.getLong(param1ArrayOfbyte, i));
/*      */               break;
/*      */             
/*      */             case 'D':
/* 2222 */               unsafe.putDouble(param1Object, l, Bits.getDouble(param1ArrayOfbyte, i));
/*      */               break;
/*      */             
/*      */             default:
/* 2226 */               throw new InternalError();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void getObjFieldValues(Object param1Object, Object[] param1ArrayOfObject) {
/* 2237 */       if (param1Object == null) {
/* 2238 */         throw new NullPointerException();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2244 */       for (int i = this.numPrimFields; i < this.fields.length; i++) {
/* 2245 */         switch (this.typeCodes[i]) {
/*      */           case 'L':
/*      */           case '[':
/* 2248 */             param1ArrayOfObject[this.offsets[i]] = unsafe.getObject(param1Object, this.readKeys[i]);
/*      */             break;
/*      */           
/*      */           default:
/* 2252 */             throw new InternalError();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setObjFieldValues(Object param1Object, Object[] param1ArrayOfObject) {
/* 2265 */       if (param1Object == null) {
/* 2266 */         throw new NullPointerException();
/*      */       }
/* 2268 */       for (int i = this.numPrimFields; i < this.fields.length; i++) {
/* 2269 */         long l = this.writeKeys[i];
/* 2270 */         if (l != -1L) {
/*      */           Object object;
/*      */           
/* 2273 */           switch (this.typeCodes[i]) {
/*      */             case 'L':
/*      */             case '[':
/* 2276 */               object = param1ArrayOfObject[this.offsets[i]];
/* 2277 */               if (object != null && 
/* 2278 */                 !this.types[i - this.numPrimFields].isInstance(object)) {
/*      */                 
/* 2280 */                 Field field = this.fields[i].getField();
/* 2281 */                 throw new ClassCastException("cannot assign instance of " + object
/*      */                     
/* 2283 */                     .getClass().getName() + " to field " + field
/* 2284 */                     .getDeclaringClass().getName() + "." + field
/* 2285 */                     .getName() + " of type " + field
/* 2286 */                     .getType().getName() + " in instance of " + param1Object
/* 2287 */                     .getClass().getName());
/*      */               } 
/* 2289 */               unsafe.putObject(param1Object, l, object);
/*      */               break;
/*      */             
/*      */             default:
/* 2293 */               throw new InternalError();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FieldReflector getReflector(ObjectStreamField[] paramArrayOfObjectStreamField, ObjectStreamClass paramObjectStreamClass) throws InvalidClassException {
/* 2313 */     Class<?> clazz = (paramObjectStreamClass != null && paramArrayOfObjectStreamField.length > 0) ? paramObjectStreamClass.cl : null;
/*      */     
/* 2315 */     processQueue(Caches.reflectorsQueue, (ConcurrentMap)Caches.reflectors);
/*      */     
/* 2317 */     FieldReflectorKey fieldReflectorKey = new FieldReflectorKey(clazz, paramArrayOfObjectStreamField, Caches.reflectorsQueue);
/* 2318 */     Reference<Object> reference = (Reference)Caches.reflectors.get(fieldReflectorKey);
/* 2319 */     Object object = null;
/* 2320 */     if (reference != null) {
/* 2321 */       object = reference.get();
/*      */     }
/* 2323 */     EntryFuture entryFuture = null;
/* 2324 */     if (object == null) {
/* 2325 */       EntryFuture entryFuture1 = new EntryFuture();
/* 2326 */       SoftReference<EntryFuture> softReference = new SoftReference<>(entryFuture1);
/*      */       do {
/* 2328 */         if (reference != null) {
/* 2329 */           Caches.reflectors.remove(fieldReflectorKey, reference);
/*      */         }
/* 2331 */         reference = (Reference<Object>)Caches.reflectors.putIfAbsent(fieldReflectorKey, softReference);
/* 2332 */         if (reference == null)
/* 2333 */           continue;  object = reference.get();
/*      */       }
/* 2335 */       while (reference != null && object == null);
/* 2336 */       if (object == null) {
/* 2337 */         entryFuture = entryFuture1;
/*      */       }
/*      */     } 
/*      */     
/* 2341 */     if (object instanceof FieldReflector)
/* 2342 */       return (FieldReflector)object; 
/* 2343 */     if (object instanceof EntryFuture) {
/* 2344 */       object = ((EntryFuture)object).get();
/* 2345 */     } else if (object == null) {
/*      */       try {
/* 2347 */         object = new FieldReflector(matchFields(paramArrayOfObjectStreamField, paramObjectStreamClass));
/* 2348 */       } catch (Throwable throwable) {
/* 2349 */         object = throwable;
/*      */       } 
/* 2351 */       entryFuture.set(object);
/* 2352 */       Caches.reflectors.put(fieldReflectorKey, new SoftReference(object));
/*      */     } 
/*      */     
/* 2355 */     if (object instanceof FieldReflector)
/* 2356 */       return (FieldReflector)object; 
/* 2357 */     if (object instanceof InvalidClassException)
/* 2358 */       throw (InvalidClassException)object; 
/* 2359 */     if (object instanceof RuntimeException)
/* 2360 */       throw (RuntimeException)object; 
/* 2361 */     if (object instanceof Error) {
/* 2362 */       throw (Error)object;
/*      */     }
/* 2364 */     throw new InternalError("unexpected entry: " + object);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class FieldReflectorKey
/*      */     extends WeakReference<Class<?>>
/*      */   {
/*      */     private final String[] sigs;
/*      */ 
/*      */     
/*      */     private final int hash;
/*      */     
/*      */     private final boolean nullClass;
/*      */ 
/*      */     
/*      */     FieldReflectorKey(Class<?> param1Class, ObjectStreamField[] param1ArrayOfObjectStreamField, ReferenceQueue<Class<?>> param1ReferenceQueue) {
/* 2381 */       super(param1Class, param1ReferenceQueue);
/* 2382 */       this.nullClass = (param1Class == null);
/* 2383 */       this.sigs = new String[2 * param1ArrayOfObjectStreamField.length];
/* 2384 */       for (byte b1 = 0, b2 = 0; b1 < param1ArrayOfObjectStreamField.length; b1++) {
/* 2385 */         ObjectStreamField objectStreamField = param1ArrayOfObjectStreamField[b1];
/* 2386 */         this.sigs[b2++] = objectStreamField.getName();
/* 2387 */         this.sigs[b2++] = objectStreamField.getSignature();
/*      */       } 
/* 2389 */       this.hash = System.identityHashCode(param1Class) + Arrays.hashCode((Object[])this.sigs);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 2393 */       return this.hash;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2397 */       if (param1Object == this) {
/* 2398 */         return true;
/*      */       }
/*      */       
/* 2401 */       if (param1Object instanceof FieldReflectorKey) {
/* 2402 */         FieldReflectorKey fieldReflectorKey = (FieldReflectorKey)param1Object;
/*      */         Class<?> clazz;
/* 2404 */         if (this.nullClass ? fieldReflectorKey.nullClass : ((
/* 2405 */           clazz = get()) != null && clazz == fieldReflectorKey
/* 2406 */           .get()))
/* 2407 */           if (Arrays.equals((Object[])this.sigs, (Object[])fieldReflectorKey.sigs));  return false;
/*      */       } 
/* 2409 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ObjectStreamField[] matchFields(ObjectStreamField[] paramArrayOfObjectStreamField, ObjectStreamClass paramObjectStreamClass) throws InvalidClassException {
/* 2429 */     ObjectStreamField[] arrayOfObjectStreamField1 = (paramObjectStreamClass != null) ? paramObjectStreamClass.fields : NO_FIELDS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2443 */     ObjectStreamField[] arrayOfObjectStreamField2 = new ObjectStreamField[paramArrayOfObjectStreamField.length];
/* 2444 */     for (byte b = 0; b < paramArrayOfObjectStreamField.length; b++) {
/* 2445 */       ObjectStreamField objectStreamField1 = paramArrayOfObjectStreamField[b], objectStreamField2 = null;
/* 2446 */       for (byte b1 = 0; b1 < arrayOfObjectStreamField1.length; b1++) {
/* 2447 */         ObjectStreamField objectStreamField = arrayOfObjectStreamField1[b1];
/* 2448 */         if (objectStreamField1.getName().equals(objectStreamField.getName())) {
/* 2449 */           if ((objectStreamField1.isPrimitive() || objectStreamField.isPrimitive()) && objectStreamField1
/* 2450 */             .getTypeCode() != objectStreamField.getTypeCode())
/*      */           {
/* 2452 */             throw new InvalidClassException(paramObjectStreamClass.name, "incompatible types for field " + objectStreamField1
/* 2453 */                 .getName());
/*      */           }
/* 2455 */           if (objectStreamField.getField() != null) {
/*      */             
/* 2457 */             objectStreamField2 = new ObjectStreamField(objectStreamField.getField(), objectStreamField.isUnshared(), false);
/*      */           } else {
/*      */             
/* 2460 */             objectStreamField2 = new ObjectStreamField(objectStreamField.getName(), objectStreamField.getSignature(), objectStreamField.isUnshared());
/*      */           } 
/*      */         } 
/*      */       } 
/* 2464 */       if (objectStreamField2 == null)
/*      */       {
/* 2466 */         objectStreamField2 = new ObjectStreamField(objectStreamField1.getName(), objectStreamField1.getSignature(), false);
/*      */       }
/* 2468 */       objectStreamField2.setOffset(objectStreamField1.getOffset());
/* 2469 */       arrayOfObjectStreamField2[b] = objectStreamField2;
/*      */     } 
/* 2471 */     return arrayOfObjectStreamField2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void processQueue(ReferenceQueue<Class<?>> paramReferenceQueue, ConcurrentMap<? extends WeakReference<Class<?>>, ?> paramConcurrentMap) {
/*      */     Reference<? extends Class<?>> reference;
/* 2483 */     while ((reference = paramReferenceQueue.poll()) != null) {
/* 2484 */       paramConcurrentMap.remove(reference);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static native void initNative();
/*      */ 
/*      */   
/*      */   ObjectStreamClass() {}
/*      */ 
/*      */   
/*      */   private static native boolean hasStaticInitializer(Class<?> paramClass);
/*      */ 
/*      */   
/*      */   static class WeakClassKey
/*      */     extends WeakReference<Class<?>>
/*      */   {
/*      */     private final int hash;
/*      */     
/*      */     WeakClassKey(Class<?> param1Class, ReferenceQueue<Class<?>> param1ReferenceQueue) {
/* 2504 */       super(param1Class, param1ReferenceQueue);
/* 2505 */       this.hash = System.identityHashCode(param1Class);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 2512 */       return this.hash;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2522 */       if (param1Object == this) {
/* 2523 */         return true;
/*      */       }
/*      */       
/* 2526 */       if (param1Object instanceof WeakClassKey) {
/* 2527 */         Class<?> clazz = get();
/* 2528 */         return (clazz != null && clazz == ((WeakClassKey)param1Object)
/* 2529 */           .get());
/*      */       } 
/* 2531 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/ObjectStreamClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */