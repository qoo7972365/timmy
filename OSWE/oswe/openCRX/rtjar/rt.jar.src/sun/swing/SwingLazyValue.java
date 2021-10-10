/*     */ package sun.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javax.swing.UIDefaults;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SwingLazyValue
/*     */   implements UIDefaults.LazyValue
/*     */ {
/*     */   private String className;
/*     */   private String methodName;
/*     */   private Object[] args;
/*     */   
/*     */   public SwingLazyValue(String paramString) {
/*  49 */     this(paramString, (String)null);
/*     */   }
/*     */   public SwingLazyValue(String paramString1, String paramString2) {
/*  52 */     this(paramString1, paramString2, null);
/*     */   }
/*     */   public SwingLazyValue(String paramString, Object[] paramArrayOfObject) {
/*  55 */     this(paramString, null, paramArrayOfObject);
/*     */   }
/*     */   public SwingLazyValue(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/*  58 */     this.className = paramString1;
/*  59 */     this.methodName = paramString2;
/*  60 */     if (paramArrayOfObject != null) {
/*  61 */       this.args = (Object[])paramArrayOfObject.clone();
/*     */     }
/*     */   }
/*     */   
/*     */   public Object createValue(UIDefaults paramUIDefaults) {
/*     */     try {
/*  67 */       ReflectUtil.checkPackageAccess(this.className);
/*  68 */       Class<?> clazz = Class.forName(this.className, true, null);
/*  69 */       if (this.methodName != null) {
/*  70 */         Class[] arrayOfClass1 = getClassArray(this.args);
/*  71 */         Method method = clazz.getMethod(this.methodName, arrayOfClass1);
/*  72 */         makeAccessible(method);
/*  73 */         return method.invoke(clazz, this.args);
/*     */       } 
/*  75 */       Class[] arrayOfClass = getClassArray(this.args);
/*  76 */       Constructor<?> constructor = clazz.getConstructor(arrayOfClass);
/*  77 */       makeAccessible(constructor);
/*  78 */       return constructor.newInstance(this.args);
/*     */     }
/*  80 */     catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  87 */       return null;
/*     */     } 
/*     */   }
/*     */   private void makeAccessible(final AccessibleObject object) {
/*  91 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*  93 */             object.setAccessible(true);
/*  94 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private Class[] getClassArray(Object[] paramArrayOfObject) {
/* 100 */     Class[] arrayOfClass = null;
/* 101 */     if (paramArrayOfObject != null) {
/* 102 */       arrayOfClass = new Class[paramArrayOfObject.length];
/* 103 */       for (byte b = 0; b < paramArrayOfObject.length; b++) {
/*     */ 
/*     */ 
/*     */         
/* 107 */         if (paramArrayOfObject[b] instanceof Integer) {
/* 108 */           arrayOfClass[b] = int.class;
/* 109 */         } else if (paramArrayOfObject[b] instanceof Boolean) {
/* 110 */           arrayOfClass[b] = boolean.class;
/* 111 */         } else if (paramArrayOfObject[b] instanceof javax.swing.plaf.ColorUIResource) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 120 */           arrayOfClass[b] = Color.class;
/*     */         } else {
/* 122 */           arrayOfClass[b] = paramArrayOfObject[b].getClass();
/*     */         } 
/*     */       } 
/*     */     } 
/* 126 */     return arrayOfClass;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/SwingLazyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */