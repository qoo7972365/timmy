/*     */ package com.sun.xml.internal.bind.v2.runtime.reflect.opt;
/*     */ 
/*     */ import com.sun.xml.internal.bind.Util;
/*     */ import com.sun.xml.internal.bind.v2.bytecode.ClassTailor;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeClassInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
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
/*     */ public abstract class OptimizedTransducedAccessorFactory
/*     */ {
/*  56 */   private static final Logger logger = Util.getClassLogger();
/*     */   
/*     */   private static final String fieldTemplateName;
/*     */   private static final String methodTemplateName;
/*     */   
/*     */   static {
/*  62 */     String s = TransducedAccessor_field_Byte.class.getName();
/*  63 */     fieldTemplateName = s.substring(0, s.length() - "Byte".length()).replace('.', '/');
/*     */     
/*  65 */     s = TransducedAccessor_method_Byte.class.getName();
/*  66 */     methodTemplateName = s.substring(0, s.length() - "Byte".length()).replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final TransducedAccessor get(RuntimePropertyInfo prop) {
/*  76 */     Accessor acc = prop.getAccessor();
/*     */ 
/*     */     
/*  79 */     Class<?> opt = null;
/*     */     
/*  81 */     TypeInfo<Type, Class<?>> parent = prop.parent();
/*  82 */     if (!(parent instanceof RuntimeClassInfo)) {
/*  83 */       return null;
/*     */     }
/*  85 */     Class dc = (Class)((RuntimeClassInfo)parent).getClazz();
/*  86 */     String newClassName = ClassTailor.toVMClassName(dc) + "_JaxbXducedAccessor_" + prop.getName();
/*     */ 
/*     */     
/*  89 */     if (acc instanceof Accessor.FieldReflection) {
/*     */       
/*  91 */       Accessor.FieldReflection racc = (Accessor.FieldReflection)acc;
/*  92 */       Field field = racc.f;
/*     */       
/*  94 */       int mods = field.getModifiers();
/*  95 */       if (Modifier.isPrivate(mods) || Modifier.isFinal(mods))
/*     */       {
/*     */         
/*  98 */         return null;
/*     */       }
/* 100 */       Class<?> t = field.getType();
/* 101 */       if (t.isPrimitive())
/* 102 */         opt = AccessorInjector.prepare(dc, fieldTemplateName + (String)suffixMap
/* 103 */             .get(t), newClassName, new String[] {
/*     */               
/* 105 */               ClassTailor.toVMClassName(Bean.class), 
/* 106 */               ClassTailor.toVMClassName(dc), "f_" + t
/* 107 */               .getName(), field
/* 108 */               .getName()
/*     */             }); 
/*     */     } 
/* 111 */     if (acc.getClass() == Accessor.GetterSetterReflection.class) {
/* 112 */       Accessor.GetterSetterReflection gacc = (Accessor.GetterSetterReflection)acc;
/*     */       
/* 114 */       if (gacc.getter == null || gacc.setter == null) {
/* 115 */         return null;
/*     */       }
/* 117 */       Class<?> t = gacc.getter.getReturnType();
/*     */       
/* 119 */       if (Modifier.isPrivate(gacc.getter.getModifiers()) || 
/* 120 */         Modifier.isPrivate(gacc.setter.getModifiers()))
/*     */       {
/* 122 */         return null;
/*     */       }
/*     */       
/* 125 */       if (t.isPrimitive())
/* 126 */         opt = AccessorInjector.prepare(dc, methodTemplateName + (String)suffixMap
/* 127 */             .get(t), newClassName, new String[] {
/*     */               
/* 129 */               ClassTailor.toVMClassName(Bean.class), 
/* 130 */               ClassTailor.toVMClassName(dc), "get_" + t
/* 131 */               .getName(), gacc.getter
/* 132 */               .getName(), "set_" + t
/* 133 */               .getName(), gacc.setter
/* 134 */               .getName()
/*     */             }); 
/*     */     } 
/* 137 */     if (opt == null) {
/* 138 */       return null;
/*     */     }
/* 140 */     logger.log(Level.FINE, "Using optimized TransducedAccessor for " + prop.displayName());
/*     */ 
/*     */     
/*     */     try {
/* 144 */       return (TransducedAccessor)opt.newInstance();
/* 145 */     } catch (InstantiationException e) {
/* 146 */       logger.log(Level.INFO, "failed to load an optimized TransducedAccessor", e);
/* 147 */     } catch (IllegalAccessException e) {
/* 148 */       logger.log(Level.INFO, "failed to load an optimized TransducedAccessor", e);
/* 149 */     } catch (SecurityException e) {
/* 150 */       logger.log(Level.INFO, "failed to load an optimized TransducedAccessor", e);
/*     */     } 
/* 152 */     return null;
/*     */   }
/*     */   
/* 155 */   private static final Map<Class, String> suffixMap = (Map)new HashMap<>();
/*     */   
/*     */   static {
/* 158 */     suffixMap.put(byte.class, "Byte");
/* 159 */     suffixMap.put(short.class, "Short");
/* 160 */     suffixMap.put(int.class, "Integer");
/* 161 */     suffixMap.put(long.class, "Long");
/* 162 */     suffixMap.put(boolean.class, "Boolean");
/* 163 */     suffixMap.put(float.class, "Float");
/* 164 */     suffixMap.put(double.class, "Double");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/opt/OptimizedTransducedAccessorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */