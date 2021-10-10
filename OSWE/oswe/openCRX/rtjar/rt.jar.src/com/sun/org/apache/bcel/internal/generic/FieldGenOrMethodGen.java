/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.classfile.AccessFlags;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Attribute;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FieldGenOrMethodGen
/*     */   extends AccessFlags
/*     */   implements NamedAndTyped, Cloneable
/*     */ {
/*     */   protected String name;
/*     */   protected Type type;
/*     */   protected ConstantPoolGen cp;
/*  77 */   private ArrayList attribute_vec = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(Type type) {
/*  82 */     if (type.getType() == 16) {
/*  83 */       throw new IllegalArgumentException("Type can not be " + type);
/*     */     }
/*  85 */     this.type = type;
/*     */   } public Type getType() {
/*  87 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  91 */     return this.name; } public void setName(String name) {
/*  92 */     this.name = name;
/*     */   }
/*  94 */   public ConstantPoolGen getConstantPool() { return this.cp; } public void setConstantPool(ConstantPoolGen cp) {
/*  95 */     this.cp = cp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(Attribute a) {
/* 105 */     this.attribute_vec.add(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttribute(Attribute a) {
/* 110 */     this.attribute_vec.remove(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttributes() {
/* 115 */     this.attribute_vec.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute[] getAttributes() {
/* 121 */     Attribute[] attributes = new Attribute[this.attribute_vec.size()];
/* 122 */     this.attribute_vec.toArray((Object[])attributes);
/* 123 */     return attributes;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getSignature();
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 132 */       return super.clone();
/* 133 */     } catch (CloneNotSupportedException e) {
/* 134 */       System.err.println(e);
/* 135 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/FieldGenOrMethodGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */