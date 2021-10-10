/*     */ package java.awt.font;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamException;
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
/*     */ public final class TransformAttribute
/*     */   implements Serializable
/*     */ {
/*     */   private AffineTransform transform;
/*     */   
/*     */   public TransformAttribute(AffineTransform paramAffineTransform) {
/*  70 */     if (paramAffineTransform != null && !paramAffineTransform.isIdentity()) {
/*  71 */       this.transform = new AffineTransform(paramAffineTransform);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AffineTransform getTransform() {
/*  81 */     AffineTransform affineTransform = this.transform;
/*  82 */     return (affineTransform == null) ? new AffineTransform() : new AffineTransform(affineTransform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIdentity() {
/*  93 */     return (this.transform == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static final TransformAttribute IDENTITY = new TransformAttribute(null);
/*     */ 
/*     */   
/*     */   static final long serialVersionUID = 3356247357827709530L;
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws ClassNotFoundException, IOException {
/* 107 */     if (this.transform == null) {
/* 108 */       this.transform = new AffineTransform();
/*     */     }
/* 110 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws ObjectStreamException {
/* 117 */     if (this.transform == null || this.transform.isIdentity()) {
/* 118 */       return IDENTITY;
/*     */     }
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 130 */     return (this.transform == null) ? 0 : this.transform.hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 144 */     if (paramObject != null) {
/*     */       try {
/* 146 */         TransformAttribute transformAttribute = (TransformAttribute)paramObject;
/* 147 */         if (this.transform == null) {
/* 148 */           return (transformAttribute.transform == null);
/*     */         }
/* 150 */         return this.transform.equals(transformAttribute.transform);
/*     */       }
/* 152 */       catch (ClassCastException classCastException) {}
/*     */     }
/*     */     
/* 155 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/font/TransformAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */