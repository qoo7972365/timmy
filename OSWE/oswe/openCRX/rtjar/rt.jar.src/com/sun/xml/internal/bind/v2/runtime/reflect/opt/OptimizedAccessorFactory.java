/*     */ package com.sun.xml.internal.bind.v2.runtime.reflect.opt;
/*     */ 
/*     */ import com.sun.xml.internal.bind.Util;
/*     */ import com.sun.xml.internal.bind.v2.bytecode.ClassTailor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ public abstract class OptimizedAccessorFactory
/*     */ {
/*  47 */   private static final Logger logger = Util.getClassLogger();
/*     */   
/*     */   private static final String fieldTemplateName;
/*     */   
/*     */   private static final String methodTemplateName;
/*     */   
/*     */   static {
/*  54 */     String s = FieldAccessor_Byte.class.getName();
/*  55 */     fieldTemplateName = s.substring(0, s.length() - "Byte".length()).replace('.', '/');
/*     */     
/*  57 */     s = MethodAccessor_Byte.class.getName();
/*  58 */     methodTemplateName = s.substring(0, s.length() - "Byte".length()).replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final <B, V> Accessor<B, V> get(Method getter, Method setter) {
/*     */     Class<?> opt;
/*  69 */     if ((getter.getParameterTypes()).length != 0)
/*  70 */       return null; 
/*  71 */     Class<?>[] sparams = setter.getParameterTypes();
/*  72 */     if (sparams.length != 1)
/*  73 */       return null; 
/*  74 */     if (sparams[0] != getter.getReturnType())
/*  75 */       return null; 
/*  76 */     if (setter.getReturnType() != void.class)
/*  77 */       return null; 
/*  78 */     if (getter.getDeclaringClass() != setter.getDeclaringClass())
/*  79 */       return null; 
/*  80 */     if (Modifier.isPrivate(getter.getModifiers()) || Modifier.isPrivate(setter.getModifiers()))
/*     */     {
/*  82 */       return null;
/*     */     }
/*  84 */     Class<?> t = sparams[0];
/*  85 */     String typeName = t.getName().replace('.', '_');
/*  86 */     if (t.isArray()) {
/*  87 */       typeName = "AOf_";
/*  88 */       String compName = t.getComponentType().getName().replace('.', '_');
/*  89 */       while (compName.startsWith("[L")) {
/*  90 */         compName = compName.substring(2);
/*  91 */         typeName = typeName + "AOf_";
/*     */       } 
/*  93 */       typeName = typeName + compName;
/*     */     } 
/*     */     
/*  96 */     String newClassName = ClassTailor.toVMClassName(getter.getDeclaringClass()) + "$JaxbAccessorM_" + getter.getName() + '_' + setter.getName() + '_' + typeName;
/*     */ 
/*     */     
/*  99 */     if (t.isPrimitive()) {
/* 100 */       opt = AccessorInjector.prepare(getter.getDeclaringClass(), methodTemplateName + ((Class)RuntimeUtil.primitiveToBox
/* 101 */           .get(t)).getSimpleName(), newClassName, new String[] {
/*     */             
/* 103 */             ClassTailor.toVMClassName(Bean.class), 
/* 104 */             ClassTailor.toVMClassName(getter.getDeclaringClass()), "get_" + t
/* 105 */             .getName(), getter
/* 106 */             .getName(), "set_" + t
/* 107 */             .getName(), setter
/* 108 */             .getName() });
/*     */     } else {
/* 110 */       opt = AccessorInjector.prepare(getter.getDeclaringClass(), methodTemplateName + "Ref", newClassName, new String[] {
/*     */ 
/*     */             
/* 113 */             ClassTailor.toVMClassName(Bean.class), 
/* 114 */             ClassTailor.toVMClassName(getter.getDeclaringClass()), 
/* 115 */             ClassTailor.toVMClassName(Ref.class), 
/* 116 */             ClassTailor.toVMClassName(t), "()" + 
/* 117 */             ClassTailor.toVMTypeName(Ref.class), "()" + 
/* 118 */             ClassTailor.toVMTypeName(t), '(' + 
/* 119 */             ClassTailor.toVMTypeName(Ref.class) + ")V", '(' + 
/* 120 */             ClassTailor.toVMTypeName(t) + ")V", "get_ref", getter
/*     */             
/* 122 */             .getName(), "set_ref", setter
/*     */             
/* 124 */             .getName() });
/*     */     } 
/* 126 */     if (opt == null) {
/* 127 */       return null;
/*     */     }
/* 129 */     Accessor<B, V> acc = instanciate(opt);
/* 130 */     if (acc != null && 
/* 131 */       logger.isLoggable(Level.FINE)) {
/* 132 */       logger.log(Level.FINE, "Using optimized Accessor for {0} and {1}", new Object[] { getter, setter });
/*     */     }
/*     */     
/* 135 */     return acc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final <B, V> Accessor<B, V> get(Field field) {
/*     */     Class<?> opt;
/* 146 */     int mods = field.getModifiers();
/* 147 */     if (Modifier.isPrivate(mods) || Modifier.isFinal(mods))
/*     */     {
/* 149 */       return null;
/*     */     }
/* 151 */     String newClassName = ClassTailor.toVMClassName(field.getDeclaringClass()) + "$JaxbAccessorF_" + field.getName();
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (field.getType().isPrimitive()) {
/* 156 */       opt = AccessorInjector.prepare(field.getDeclaringClass(), fieldTemplateName + ((Class)RuntimeUtil.primitiveToBox
/* 157 */           .get(field.getType())).getSimpleName(), newClassName, new String[] {
/*     */             
/* 159 */             ClassTailor.toVMClassName(Bean.class), 
/* 160 */             ClassTailor.toVMClassName(field.getDeclaringClass()), "f_" + field
/* 161 */             .getType().getName(), field
/* 162 */             .getName() });
/*     */     } else {
/* 164 */       opt = AccessorInjector.prepare(field.getDeclaringClass(), fieldTemplateName + "Ref", newClassName, new String[] {
/*     */ 
/*     */             
/* 167 */             ClassTailor.toVMClassName(Bean.class), 
/* 168 */             ClassTailor.toVMClassName(field.getDeclaringClass()), 
/* 169 */             ClassTailor.toVMClassName(Ref.class), 
/* 170 */             ClassTailor.toVMClassName(field.getType()), 
/* 171 */             ClassTailor.toVMTypeName(Ref.class), 
/* 172 */             ClassTailor.toVMTypeName(field.getType()), "f_ref", field
/*     */             
/* 174 */             .getName() });
/*     */     } 
/* 176 */     if (opt == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     Accessor<B, V> acc = instanciate(opt);
/* 180 */     if (acc != null && 
/* 181 */       logger.isLoggable(Level.FINE)) {
/* 182 */       logger.log(Level.FINE, "Using optimized Accessor for {0}", field);
/*     */     }
/*     */     
/* 185 */     return acc;
/*     */   }
/*     */   
/*     */   private static <B, V> Accessor<B, V> instanciate(Class<Accessor<B, V>> opt) {
/*     */     try {
/* 190 */       return opt.newInstance();
/* 191 */     } catch (InstantiationException e) {
/* 192 */       logger.log(Level.INFO, "failed to load an optimized Accessor", e);
/* 193 */     } catch (IllegalAccessException e) {
/* 194 */       logger.log(Level.INFO, "failed to load an optimized Accessor", e);
/* 195 */     } catch (SecurityException e) {
/* 196 */       logger.log(Level.INFO, "failed to load an optimized Accessor", e);
/*     */     } 
/* 198 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/opt/OptimizedAccessorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */