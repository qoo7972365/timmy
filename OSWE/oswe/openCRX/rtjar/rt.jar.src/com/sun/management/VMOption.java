/*     */ package com.sun.management;
/*     */ 
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import jdk.Exported;
/*     */ import sun.management.VMOptionCompositeData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class VMOption
/*     */ {
/*     */   private String name;
/*     */   private String value;
/*     */   private boolean writeable;
/*     */   private Origin origin;
/*     */   
/*     */   @Exported
/*     */   public enum Origin
/*     */   {
/*  74 */     DEFAULT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     VM_CREATION,
/*     */ 
/*     */ 
/*     */     
/*  85 */     ENVIRON_VAR,
/*     */ 
/*     */ 
/*     */     
/*  89 */     CONFIG_FILE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     MANAGEMENT,
/*     */ 
/*     */ 
/*     */     
/*  98 */     ERGONOMIC,
/*     */ 
/*     */ 
/*     */     
/* 102 */     OTHER;
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
/*     */   public VMOption(String paramString1, String paramString2, boolean paramBoolean, Origin paramOrigin) {
/* 117 */     this.name = paramString1;
/* 118 */     this.value = paramString2;
/* 119 */     this.writeable = paramBoolean;
/* 120 */     this.origin = paramOrigin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private VMOption(CompositeData paramCompositeData) {
/* 129 */     VMOptionCompositeData.validateCompositeData(paramCompositeData);
/*     */     
/* 131 */     this.name = VMOptionCompositeData.getName(paramCompositeData);
/* 132 */     this.value = VMOptionCompositeData.getValue(paramCompositeData);
/* 133 */     this.writeable = VMOptionCompositeData.isWriteable(paramCompositeData);
/* 134 */     this.origin = VMOptionCompositeData.getOrigin(paramCompositeData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 143 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 154 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Origin getOrigin() {
/* 164 */     return this.origin;
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
/*     */   public boolean isWriteable() {
/* 176 */     return this.writeable;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 180 */     return "VM option: " + getName() + " value: " + this.value + "  origin: " + this.origin + " " + (this.writeable ? "(read-write)" : "(read-only)");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VMOption from(CompositeData paramCompositeData) {
/* 227 */     if (paramCompositeData == null) {
/* 228 */       return null;
/*     */     }
/*     */     
/* 231 */     if (paramCompositeData instanceof VMOptionCompositeData) {
/* 232 */       return ((VMOptionCompositeData)paramCompositeData).getVMOption();
/*     */     }
/* 234 */     return new VMOption(paramCompositeData);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/management/VMOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */