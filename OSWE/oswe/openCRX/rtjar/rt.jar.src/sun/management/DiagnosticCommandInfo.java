/*     */ package sun.management;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DiagnosticCommandInfo
/*     */ {
/*     */   private final String name;
/*     */   private final String description;
/*     */   private final String impact;
/*     */   private final String permissionClass;
/*     */   private final String permissionName;
/*     */   private final String permissionAction;
/*     */   private final boolean enabled;
/*     */   private final List<DiagnosticCommandArgumentInfo> arguments;
/*     */   
/*     */   String getName() {
/*  53 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getDescription() {
/*  62 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getImpact() {
/*  73 */     return this.impact;
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
/*     */   String getPermissionClass() {
/*  86 */     return this.permissionClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getPermissionName() {
/*  97 */     return this.permissionName;
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
/*     */   String getPermissionAction() {
/* 110 */     return this.permissionAction;
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
/*     */   boolean isEnabled() {
/* 123 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<DiagnosticCommandArgumentInfo> getArgumentsInfo() {
/* 133 */     return this.arguments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DiagnosticCommandInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean, List<DiagnosticCommandArgumentInfo> paramList) {
/* 142 */     this.name = paramString1;
/* 143 */     this.description = paramString2;
/* 144 */     this.impact = paramString3;
/* 145 */     this.permissionClass = paramString4;
/* 146 */     this.permissionName = paramString5;
/* 147 */     this.permissionAction = paramString6;
/* 148 */     this.enabled = paramBoolean;
/* 149 */     this.arguments = paramList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/DiagnosticCommandInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */