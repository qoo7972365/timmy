/*     */ package java.security.cert;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CollectionCertStoreParameters
/*     */   implements CertStoreParameters
/*     */ {
/*     */   private Collection<?> coll;
/*     */   
/*     */   public CollectionCertStoreParameters(Collection<?> paramCollection) {
/*  87 */     if (paramCollection == null)
/*  88 */       throw new NullPointerException(); 
/*  89 */     this.coll = paramCollection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionCertStoreParameters() {
/*  98 */     this.coll = Collections.EMPTY_SET;
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
/*     */   public Collection<?> getCollection() {
/* 111 */     return this.coll;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 122 */       return super.clone();
/* 123 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 125 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 135 */     StringBuffer stringBuffer = new StringBuffer();
/* 136 */     stringBuffer.append("CollectionCertStoreParameters: [\n");
/* 137 */     stringBuffer.append("  collection: " + this.coll + "\n");
/* 138 */     stringBuffer.append("]");
/* 139 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/CollectionCertStoreParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */