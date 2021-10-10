/*     */ package sun.management;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DiagnosticCommandArgumentInfo
/*     */ {
/*     */   private final String name;
/*     */   private final String description;
/*     */   private final String type;
/*     */   private final String defaultValue;
/*     */   private final boolean mandatory;
/*     */   private final boolean option;
/*     */   private final boolean multiple;
/*     */   private final int position;
/*     */   
/*     */   String getName() {
/*  65 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getDescription() {
/*  74 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getType() {
/*  83 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getDefault() {
/*  94 */     return this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isMandatory() {
/* 105 */     return this.mandatory;
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
/*     */   boolean isOption() {
/* 119 */     return this.option;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isMultiple() {
/* 130 */     return this.multiple;
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
/*     */   int getPosition() {
/* 143 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DiagnosticCommandArgumentInfo(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt) {
/* 150 */     this.name = paramString1;
/* 151 */     this.description = paramString2;
/* 152 */     this.type = paramString3;
/* 153 */     this.defaultValue = paramString4;
/* 154 */     this.mandatory = paramBoolean1;
/* 155 */     this.option = paramBoolean2;
/* 156 */     this.multiple = paramBoolean3;
/* 157 */     this.position = paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/DiagnosticCommandArgumentInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */