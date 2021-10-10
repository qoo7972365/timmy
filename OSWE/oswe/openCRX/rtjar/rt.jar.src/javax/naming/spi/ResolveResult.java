/*     */ package javax.naming.spi;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolveResult
/*     */   implements Serializable
/*     */ {
/*     */   protected Object resolvedObj;
/*     */   protected Name remainingName;
/*     */   private static final long serialVersionUID = -4552108072002407559L;
/*     */   
/*     */   protected ResolveResult() {
/*  67 */     this.resolvedObj = null;
/*  68 */     this.remainingName = null;
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
/*     */   public ResolveResult(Object paramObject, String paramString) {
/*  80 */     this.resolvedObj = paramObject;
/*     */     try {
/*  82 */       this.remainingName = new CompositeName(paramString);
/*     */     }
/*  84 */     catch (InvalidNameException invalidNameException) {}
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
/*     */   public ResolveResult(Object paramObject, Name paramName) {
/*  97 */     this.resolvedObj = paramObject;
/*  98 */     setRemainingName(paramName);
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
/*     */   public Name getRemainingName() {
/* 111 */     return this.remainingName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getResolvedObj() {
/* 121 */     return this.resolvedObj;
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
/*     */   public void setRemainingName(Name paramName) {
/* 136 */     if (paramName != null) {
/* 137 */       this.remainingName = (Name)paramName.clone();
/*     */     } else {
/*     */       
/* 140 */       this.remainingName = null;
/*     */     } 
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
/*     */   public void appendRemainingName(Name paramName) {
/* 156 */     if (paramName != null) {
/* 157 */       if (this.remainingName != null) {
/*     */         try {
/* 159 */           this.remainingName.addAll(paramName);
/* 160 */         } catch (InvalidNameException invalidNameException) {}
/*     */       }
/*     */       else {
/*     */         
/* 164 */         this.remainingName = (Name)paramName.clone();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendRemainingComponent(String paramString) {
/* 177 */     if (paramString != null) {
/* 178 */       CompositeName compositeName = new CompositeName();
/*     */       try {
/* 180 */         compositeName.add(paramString);
/* 181 */       } catch (InvalidNameException invalidNameException) {}
/*     */ 
/*     */       
/* 184 */       appendRemainingName(compositeName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResolvedObj(Object paramObject) {
/* 196 */     this.resolvedObj = paramObject;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/spi/ResolveResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */