/*    */ package com.sun.org.apache.bcel.internal.util;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.classfile.JavaClass;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
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
/*    */ public class ClassSet
/*    */   implements Serializable
/*    */ {
/* 73 */   private HashMap _map = new HashMap<>();
/*    */   
/*    */   public boolean add(JavaClass clazz) {
/* 76 */     boolean result = false;
/*    */     
/* 78 */     if (!this._map.containsKey(clazz.getClassName())) {
/* 79 */       result = true;
/* 80 */       this._map.put(clazz.getClassName(), clazz);
/*    */     } 
/*    */     
/* 83 */     return result;
/*    */   }
/*    */   
/* 86 */   public void remove(JavaClass clazz) { this._map.remove(clazz.getClassName()); } public boolean empty() {
/* 87 */     return this._map.isEmpty();
/*    */   }
/*    */   public JavaClass[] toArray() {
/* 90 */     Collection values = this._map.values();
/* 91 */     JavaClass[] classes = new JavaClass[values.size()];
/* 92 */     values.toArray((Object[])classes);
/* 93 */     return classes;
/*    */   }
/*    */   
/*    */   public String[] getClassNames() {
/* 97 */     return (String[])this._map.keySet().toArray((Object[])new String[this._map.keySet().size()]);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/ClassSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */