/*    */ package com.sun.xml.internal.bind.api;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Utils
/*    */ {
/* 48 */   private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
/*    */ 
/*    */   
/*    */   static final Navigator<Type, Class, Field, Method> REFLECTION_NAVIGATOR;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/*    */     try {
/* 57 */       final Class<?> refNav = Class.forName("com.sun.xml.internal.bind.v2.model.nav.ReflectionNavigator");
/*    */ 
/*    */       
/* 60 */       Method getInstance = AccessController.<Method>doPrivileged(new PrivilegedAction<Method>()
/*    */           {
/*    */             public Method run()
/*    */             {
/*    */               try {
/* 65 */                 Method getInstance = refNav.getDeclaredMethod("getInstance", new Class[0]);
/* 66 */                 getInstance.setAccessible(true);
/* 67 */                 return getInstance;
/* 68 */               } catch (NoSuchMethodException e) {
/* 69 */                 throw new IllegalStateException("ReflectionNavigator.getInstance can't be found");
/*    */               } 
/*    */             }
/*    */           });
/*    */ 
/*    */ 
/*    */       
/* 76 */       REFLECTION_NAVIGATOR = (Navigator<Type, Class, Field, Method>)getInstance.invoke(null, new Object[0]);
/* 77 */     } catch (ClassNotFoundException e) {
/* 78 */       throw new IllegalStateException("Can't find ReflectionNavigator class");
/* 79 */     } catch (InvocationTargetException e) {
/* 80 */       throw new IllegalStateException("ReflectionNavigator.getInstance throws the exception");
/* 81 */     } catch (IllegalAccessException e) {
/* 82 */       throw new IllegalStateException("ReflectionNavigator.getInstance method is inaccessible");
/* 83 */     } catch (SecurityException e) {
/* 84 */       LOGGER.log(Level.FINE, "Unable to access ReflectionNavigator.getInstance", e);
/* 85 */       throw e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/api/Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */