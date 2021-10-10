/*     */ package com.sun.org.apache.bcel.internal.classfile;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AccessFlags
/*     */   implements Serializable
/*     */ {
/*     */   protected int access_flags;
/*     */   
/*     */   public AccessFlags() {}
/*     */   
/*     */   public AccessFlags(int a) {
/*  78 */     this.access_flags = a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getAccessFlags() {
/*  84 */     return this.access_flags;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getModifiers() {
/*  89 */     return this.access_flags;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setAccessFlags(int access_flags) {
/*  95 */     this.access_flags = access_flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setModifiers(int access_flags) {
/* 102 */     setAccessFlags(access_flags);
/*     */   }
/*     */   
/*     */   private final void setFlag(int flag, boolean set) {
/* 106 */     if ((this.access_flags & flag) != 0) {
/* 107 */       if (!set) {
/* 108 */         this.access_flags ^= flag;
/*     */       }
/* 110 */     } else if (set) {
/* 111 */       this.access_flags |= flag;
/*     */     } 
/*     */   }
/*     */   public final void isPublic(boolean flag) {
/* 115 */     setFlag(1, flag);
/*     */   } public final boolean isPublic() {
/* 117 */     return ((this.access_flags & 0x1) != 0);
/*     */   }
/*     */   public final void isPrivate(boolean flag) {
/* 120 */     setFlag(2, flag);
/*     */   } public final boolean isPrivate() {
/* 122 */     return ((this.access_flags & 0x2) != 0);
/*     */   }
/*     */   public final void isProtected(boolean flag) {
/* 125 */     setFlag(4, flag);
/*     */   } public final boolean isProtected() {
/* 127 */     return ((this.access_flags & 0x4) != 0);
/*     */   }
/*     */   public final void isStatic(boolean flag) {
/* 130 */     setFlag(8, flag);
/*     */   } public final boolean isStatic() {
/* 132 */     return ((this.access_flags & 0x8) != 0);
/*     */   }
/*     */   public final void isFinal(boolean flag) {
/* 135 */     setFlag(16, flag);
/*     */   } public final boolean isFinal() {
/* 137 */     return ((this.access_flags & 0x10) != 0);
/*     */   }
/*     */   public final void isSynchronized(boolean flag) {
/* 140 */     setFlag(32, flag);
/*     */   } public final boolean isSynchronized() {
/* 142 */     return ((this.access_flags & 0x20) != 0);
/*     */   }
/*     */   public final void isVolatile(boolean flag) {
/* 145 */     setFlag(64, flag);
/*     */   } public final boolean isVolatile() {
/* 147 */     return ((this.access_flags & 0x40) != 0);
/*     */   }
/*     */   public final void isTransient(boolean flag) {
/* 150 */     setFlag(128, flag);
/*     */   } public final boolean isTransient() {
/* 152 */     return ((this.access_flags & 0x80) != 0);
/*     */   }
/*     */   public final void isNative(boolean flag) {
/* 155 */     setFlag(256, flag);
/*     */   } public final boolean isNative() {
/* 157 */     return ((this.access_flags & 0x100) != 0);
/*     */   }
/*     */   public final void isInterface(boolean flag) {
/* 160 */     setFlag(512, flag);
/*     */   } public final boolean isInterface() {
/* 162 */     return ((this.access_flags & 0x200) != 0);
/*     */   }
/*     */   public final void isAbstract(boolean flag) {
/* 165 */     setFlag(1024, flag);
/*     */   } public final boolean isAbstract() {
/* 167 */     return ((this.access_flags & 0x400) != 0);
/*     */   }
/*     */   public final void isStrictfp(boolean flag) {
/* 170 */     setFlag(2048, flag);
/*     */   } public final boolean isStrictfp() {
/* 172 */     return ((this.access_flags & 0x800) != 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/AccessFlags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */