/*     */ package javax.management;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanInfo
/*     */   implements Cloneable, Serializable, DescriptorRead
/*     */ {
/*     */   static final long serialVersionUID = -6451021435135161911L;
/*     */   private transient Descriptor descriptor;
/*     */   private final String description;
/*     */   private final String className;
/*     */   private final MBeanAttributeInfo[] attributes;
/*     */   private final MBeanOperationInfo[] operations;
/*     */   private final MBeanConstructorInfo[] constructors;
/*     */   private final MBeanNotificationInfo[] notifications;
/*     */   private transient int hashCode;
/*     */   private final transient boolean arrayGettersSafe;
/*     */   
/*     */   public MBeanInfo(String paramString1, String paramString2, MBeanAttributeInfo[] paramArrayOfMBeanAttributeInfo, MBeanConstructorInfo[] paramArrayOfMBeanConstructorInfo, MBeanOperationInfo[] paramArrayOfMBeanOperationInfo, MBeanNotificationInfo[] paramArrayOfMBeanNotificationInfo) throws IllegalArgumentException {
/* 193 */     this(paramString1, paramString2, paramArrayOfMBeanAttributeInfo, paramArrayOfMBeanConstructorInfo, paramArrayOfMBeanOperationInfo, paramArrayOfMBeanNotificationInfo, null);
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
/*     */   public MBeanInfo(String paramString1, String paramString2, MBeanAttributeInfo[] paramArrayOfMBeanAttributeInfo, MBeanConstructorInfo[] paramArrayOfMBeanConstructorInfo, MBeanOperationInfo[] paramArrayOfMBeanOperationInfo, MBeanNotificationInfo[] paramArrayOfMBeanNotificationInfo, Descriptor paramDescriptor) throws IllegalArgumentException {
/* 233 */     this.className = paramString1;
/*     */     
/* 235 */     this.description = paramString2;
/*     */     
/* 237 */     if (paramArrayOfMBeanAttributeInfo == null)
/* 238 */       paramArrayOfMBeanAttributeInfo = MBeanAttributeInfo.NO_ATTRIBUTES; 
/* 239 */     this.attributes = paramArrayOfMBeanAttributeInfo;
/*     */     
/* 241 */     if (paramArrayOfMBeanOperationInfo == null)
/* 242 */       paramArrayOfMBeanOperationInfo = MBeanOperationInfo.NO_OPERATIONS; 
/* 243 */     this.operations = paramArrayOfMBeanOperationInfo;
/*     */     
/* 245 */     if (paramArrayOfMBeanConstructorInfo == null)
/* 246 */       paramArrayOfMBeanConstructorInfo = MBeanConstructorInfo.NO_CONSTRUCTORS; 
/* 247 */     this.constructors = paramArrayOfMBeanConstructorInfo;
/*     */     
/* 249 */     if (paramArrayOfMBeanNotificationInfo == null)
/* 250 */       paramArrayOfMBeanNotificationInfo = MBeanNotificationInfo.NO_NOTIFICATIONS; 
/* 251 */     this.notifications = paramArrayOfMBeanNotificationInfo;
/*     */     
/* 253 */     if (paramDescriptor == null)
/* 254 */       paramDescriptor = ImmutableDescriptor.EMPTY_DESCRIPTOR; 
/* 255 */     this.descriptor = paramDescriptor;
/*     */     
/* 257 */     this
/* 258 */       .arrayGettersSafe = arrayGettersSafe(getClass(), MBeanInfo.class);
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
/*     */   public Object clone() {
/*     */     try {
/* 274 */       return super.clone();
/* 275 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 277 */       return null;
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
/*     */   public String getClassName() {
/* 289 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 298 */     return this.description;
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
/*     */   public MBeanAttributeInfo[] getAttributes() {
/* 313 */     MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = nonNullAttributes();
/* 314 */     if (arrayOfMBeanAttributeInfo.length == 0) {
/* 315 */       return arrayOfMBeanAttributeInfo;
/*     */     }
/* 317 */     return (MBeanAttributeInfo[])arrayOfMBeanAttributeInfo.clone();
/*     */   }
/*     */   
/*     */   private MBeanAttributeInfo[] fastGetAttributes() {
/* 321 */     if (this.arrayGettersSafe) {
/* 322 */       return nonNullAttributes();
/*     */     }
/* 324 */     return getAttributes();
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
/*     */   private MBeanAttributeInfo[] nonNullAttributes() {
/* 339 */     return (this.attributes == null) ? MBeanAttributeInfo.NO_ATTRIBUTES : this.attributes;
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
/*     */   public MBeanOperationInfo[] getOperations() {
/* 355 */     MBeanOperationInfo[] arrayOfMBeanOperationInfo = nonNullOperations();
/* 356 */     if (arrayOfMBeanOperationInfo.length == 0) {
/* 357 */       return arrayOfMBeanOperationInfo;
/*     */     }
/* 359 */     return (MBeanOperationInfo[])arrayOfMBeanOperationInfo.clone();
/*     */   }
/*     */   
/*     */   private MBeanOperationInfo[] fastGetOperations() {
/* 363 */     if (this.arrayGettersSafe) {
/* 364 */       return nonNullOperations();
/*     */     }
/* 366 */     return getOperations();
/*     */   }
/*     */   
/*     */   private MBeanOperationInfo[] nonNullOperations() {
/* 370 */     return (this.operations == null) ? MBeanOperationInfo.NO_OPERATIONS : this.operations;
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
/*     */   public MBeanConstructorInfo[] getConstructors() {
/* 394 */     MBeanConstructorInfo[] arrayOfMBeanConstructorInfo = nonNullConstructors();
/* 395 */     if (arrayOfMBeanConstructorInfo.length == 0) {
/* 396 */       return arrayOfMBeanConstructorInfo;
/*     */     }
/* 398 */     return (MBeanConstructorInfo[])arrayOfMBeanConstructorInfo.clone();
/*     */   }
/*     */   
/*     */   private MBeanConstructorInfo[] fastGetConstructors() {
/* 402 */     if (this.arrayGettersSafe) {
/* 403 */       return nonNullConstructors();
/*     */     }
/* 405 */     return getConstructors();
/*     */   }
/*     */   
/*     */   private MBeanConstructorInfo[] nonNullConstructors() {
/* 409 */     return (this.constructors == null) ? MBeanConstructorInfo.NO_CONSTRUCTORS : this.constructors;
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
/*     */   public MBeanNotificationInfo[] getNotifications() {
/* 425 */     MBeanNotificationInfo[] arrayOfMBeanNotificationInfo = nonNullNotifications();
/* 426 */     if (arrayOfMBeanNotificationInfo.length == 0) {
/* 427 */       return arrayOfMBeanNotificationInfo;
/*     */     }
/* 429 */     return (MBeanNotificationInfo[])arrayOfMBeanNotificationInfo.clone();
/*     */   }
/*     */   
/*     */   private MBeanNotificationInfo[] fastGetNotifications() {
/* 433 */     if (this.arrayGettersSafe) {
/* 434 */       return nonNullNotifications();
/*     */     }
/* 436 */     return getNotifications();
/*     */   }
/*     */   
/*     */   private MBeanNotificationInfo[] nonNullNotifications() {
/* 440 */     return (this.notifications == null) ? MBeanNotificationInfo.NO_NOTIFICATIONS : this.notifications;
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
/*     */   public Descriptor getDescriptor() {
/* 453 */     return (Descriptor)ImmutableDescriptor.nonNullDescriptor(this.descriptor).clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 458 */     return 
/* 459 */       getClass().getName() + "[description=" + 
/* 460 */       getDescription() + ", attributes=" + 
/* 461 */       Arrays.<MBeanAttributeInfo>asList(fastGetAttributes()) + ", constructors=" + 
/* 462 */       Arrays.<MBeanConstructorInfo>asList(fastGetConstructors()) + ", operations=" + 
/* 463 */       Arrays.<MBeanOperationInfo>asList(fastGetOperations()) + ", notifications=" + 
/* 464 */       Arrays.<MBeanNotificationInfo>asList(fastGetNotifications()) + ", descriptor=" + 
/* 465 */       getDescriptor() + "]";
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
/*     */   public boolean equals(Object paramObject) {
/* 490 */     if (paramObject == this)
/* 491 */       return true; 
/* 492 */     if (!(paramObject instanceof MBeanInfo))
/* 493 */       return false; 
/* 494 */     MBeanInfo mBeanInfo = (MBeanInfo)paramObject;
/* 495 */     if (!isEqual(getClassName(), mBeanInfo.getClassName()) || 
/* 496 */       !isEqual(getDescription(), mBeanInfo.getDescription()) || 
/* 497 */       !getDescriptor().equals(mBeanInfo.getDescriptor())) {
/* 498 */       return false;
/*     */     }
/*     */     
/* 501 */     return (
/* 502 */       Arrays.equals((Object[])mBeanInfo.fastGetAttributes(), (Object[])fastGetAttributes()) && 
/* 503 */       Arrays.equals((Object[])mBeanInfo.fastGetOperations(), (Object[])fastGetOperations()) && 
/* 504 */       Arrays.equals((Object[])mBeanInfo.fastGetConstructors(), (Object[])fastGetConstructors()) && 
/* 505 */       Arrays.equals((Object[])mBeanInfo.fastGetNotifications(), (Object[])fastGetNotifications()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 516 */     if (this.hashCode != 0) {
/* 517 */       return this.hashCode;
/*     */     }
/* 519 */     this
/*     */ 
/*     */ 
/*     */       
/* 523 */       .hashCode = Objects.hash(new Object[] { getClassName(), getDescriptor() }) ^ Arrays.hashCode((Object[])fastGetAttributes()) ^ Arrays.hashCode((Object[])fastGetOperations()) ^ Arrays.hashCode((Object[])fastGetConstructors()) ^ Arrays.hashCode((Object[])fastGetNotifications());
/*     */     
/* 525 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 533 */   private static final Map<Class<?>, Boolean> arrayGettersSafeMap = new WeakHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean arrayGettersSafe(Class<?> paramClass1, Class<?> paramClass2) {
/* 547 */     if (paramClass1 == paramClass2)
/* 548 */       return true; 
/* 549 */     synchronized (arrayGettersSafeMap) {
/* 550 */       Boolean bool = arrayGettersSafeMap.get(paramClass1);
/* 551 */       if (bool == null) {
/*     */         try {
/* 553 */           ArrayGettersSafeAction arrayGettersSafeAction = new ArrayGettersSafeAction(paramClass1, paramClass2);
/*     */           
/* 555 */           bool = AccessController.<Boolean>doPrivileged(arrayGettersSafeAction);
/* 556 */         } catch (Exception exception) {
/*     */           
/* 558 */           bool = Boolean.valueOf(false);
/*     */         } 
/* 560 */         arrayGettersSafeMap.put(paramClass1, bool);
/*     */       } 
/* 562 */       return bool.booleanValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ArrayGettersSafeAction
/*     */     implements PrivilegedAction<Boolean>
/*     */   {
/*     */     private final Class<?> subclass;
/*     */ 
/*     */     
/*     */     private final Class<?> immutableClass;
/*     */ 
/*     */ 
/*     */     
/*     */     ArrayGettersSafeAction(Class<?> param1Class1, Class<?> param1Class2) {
/* 580 */       this.subclass = param1Class1;
/* 581 */       this.immutableClass = param1Class2;
/*     */     }
/*     */     
/*     */     public Boolean run() {
/* 585 */       Method[] arrayOfMethod = this.immutableClass.getMethods();
/* 586 */       for (byte b = 0; b < arrayOfMethod.length; b++) {
/* 587 */         Method method = arrayOfMethod[b];
/* 588 */         String str = method.getName();
/* 589 */         if (str.startsWith("get") && (method
/* 590 */           .getParameterTypes()).length == 0 && method
/* 591 */           .getReturnType().isArray()) {
/*     */           
/*     */           try {
/* 594 */             Method method1 = this.subclass.getMethod(str, new Class[0]);
/* 595 */             if (!method1.equals(method))
/* 596 */               return Boolean.valueOf(false); 
/* 597 */           } catch (NoSuchMethodException noSuchMethodException) {
/* 598 */             return Boolean.valueOf(false);
/*     */           } 
/*     */         }
/*     */       } 
/* 602 */       return Boolean.valueOf(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isEqual(String paramString1, String paramString2) {
/*     */     boolean bool;
/* 609 */     if (paramString1 == null) {
/* 610 */       bool = (paramString2 == null);
/*     */     } else {
/* 612 */       bool = paramString1.equals(paramString2);
/*     */     } 
/*     */     
/* 615 */     return bool;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 644 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 646 */     if (this.descriptor.getClass() == ImmutableDescriptor.class) {
/* 647 */       paramObjectOutputStream.write(1);
/*     */       
/* 649 */       String[] arrayOfString = this.descriptor.getFieldNames();
/*     */       
/* 651 */       paramObjectOutputStream.writeObject(arrayOfString);
/* 652 */       paramObjectOutputStream.writeObject(this.descriptor.getFieldValues(arrayOfString));
/*     */     } else {
/* 654 */       paramObjectOutputStream.write(0);
/*     */       
/* 656 */       paramObjectOutputStream.writeObject(this.descriptor);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*     */     String[] arrayOfString;
/*     */     Object[] arrayOfObject;
/* 693 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 695 */     switch (paramObjectInputStream.read()) {
/*     */       case 1:
/* 697 */         arrayOfString = (String[])paramObjectInputStream.readObject();
/*     */         
/* 699 */         arrayOfObject = (Object[])paramObjectInputStream.readObject();
/* 700 */         this.descriptor = (arrayOfString.length == 0) ? ImmutableDescriptor.EMPTY_DESCRIPTOR : new ImmutableDescriptor(arrayOfString, arrayOfObject);
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/* 706 */         this.descriptor = (Descriptor)paramObjectInputStream.readObject();
/*     */         
/* 708 */         if (this.descriptor == null) {
/* 709 */           this.descriptor = ImmutableDescriptor.EMPTY_DESCRIPTOR;
/*     */         }
/*     */         return;
/*     */       
/*     */       case -1:
/* 714 */         this.descriptor = ImmutableDescriptor.EMPTY_DESCRIPTOR;
/*     */         return;
/*     */     } 
/*     */     
/* 718 */     throw new StreamCorruptedException("Got unexpected byte.");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */