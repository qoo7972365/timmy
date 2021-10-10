/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TLGlobals
/*     */ {
/*  69 */   private final Map<String, ConstantPool.Utf8Entry> utf8Entries = new HashMap<>();
/*  70 */   private final Map<String, ConstantPool.ClassEntry> classEntries = new HashMap<>();
/*  71 */   private final Map<Object, ConstantPool.LiteralEntry> literalEntries = new HashMap<>();
/*  72 */   private final Map<String, ConstantPool.SignatureEntry> signatureEntries = new HashMap<>();
/*  73 */   private final Map<String, ConstantPool.DescriptorEntry> descriptorEntries = new HashMap<>();
/*  74 */   private final Map<String, ConstantPool.MemberEntry> memberEntries = new HashMap<>();
/*  75 */   private final Map<String, ConstantPool.MethodHandleEntry> methodHandleEntries = new HashMap<>();
/*  76 */   private final Map<String, ConstantPool.MethodTypeEntry> methodTypeEntries = new HashMap<>();
/*  77 */   private final Map<String, ConstantPool.InvokeDynamicEntry> invokeDynamicEntries = new HashMap<>();
/*  78 */   private final Map<String, ConstantPool.BootstrapMethodEntry> bootstrapMethodEntries = new HashMap<>();
/*  79 */   final PropMap props = new PropMap();
/*     */ 
/*     */   
/*     */   SortedMap<String, String> getPropMap() {
/*  83 */     return this.props;
/*     */   }
/*     */   
/*     */   Map<String, ConstantPool.Utf8Entry> getUtf8Entries() {
/*  87 */     return this.utf8Entries;
/*     */   }
/*     */   
/*     */   Map<String, ConstantPool.ClassEntry> getClassEntries() {
/*  91 */     return this.classEntries;
/*     */   }
/*     */   
/*     */   Map<Object, ConstantPool.LiteralEntry> getLiteralEntries() {
/*  95 */     return this.literalEntries;
/*     */   }
/*     */   
/*     */   Map<String, ConstantPool.DescriptorEntry> getDescriptorEntries() {
/*  99 */     return this.descriptorEntries;
/*     */   }
/*     */   
/*     */   Map<String, ConstantPool.SignatureEntry> getSignatureEntries() {
/* 103 */     return this.signatureEntries;
/*     */   }
/*     */   
/*     */   Map<String, ConstantPool.MemberEntry> getMemberEntries() {
/* 107 */     return this.memberEntries;
/*     */   }
/*     */   
/*     */   Map<String, ConstantPool.MethodHandleEntry> getMethodHandleEntries() {
/* 111 */     return this.methodHandleEntries;
/*     */   }
/*     */   
/*     */   Map<String, ConstantPool.MethodTypeEntry> getMethodTypeEntries() {
/* 115 */     return this.methodTypeEntries;
/*     */   }
/*     */   
/*     */   Map<String, ConstantPool.InvokeDynamicEntry> getInvokeDynamicEntries() {
/* 119 */     return this.invokeDynamicEntries;
/*     */   }
/*     */   
/*     */   Map<String, ConstantPool.BootstrapMethodEntry> getBootstrapMethodEntries() {
/* 123 */     return this.bootstrapMethodEntries;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/TLGlobals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */