/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ public class GuardedObject
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5240450096227834308L;
/*     */   private Object object;
/*     */   private Guard guard;
/*     */   
/*     */   public GuardedObject(Object paramObject, Guard paramGuard) {
/*  68 */     this.guard = paramGuard;
/*  69 */     this.object = paramObject;
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
/*     */   public Object getObject() throws SecurityException {
/*  84 */     if (this.guard != null) {
/*  85 */       this.guard.checkGuard(this.object);
/*     */     }
/*  87 */     return this.object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  97 */     if (this.guard != null) {
/*  98 */       this.guard.checkGuard(this.object);
/*     */     }
/* 100 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/GuardedObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */