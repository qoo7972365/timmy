/*     */ package java.rmi.activation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.rmi.server.UID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivationGroupID
/*     */   implements Serializable
/*     */ {
/*     */   private ActivationSystem system;
/*  57 */   private UID uid = new UID();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -1648432278909740833L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActivationGroupID(ActivationSystem paramActivationSystem) {
/*  71 */     this.system = paramActivationSystem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActivationSystem getSystem() {
/*  80 */     return this.system;
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
/*     */   public int hashCode() {
/*  92 */     return this.uid.hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 108 */     if (this == paramObject)
/* 109 */       return true; 
/* 110 */     if (paramObject instanceof ActivationGroupID) {
/* 111 */       ActivationGroupID activationGroupID = (ActivationGroupID)paramObject;
/* 112 */       return (this.uid.equals(activationGroupID.uid) && this.system.equals(activationGroupID.system));
/*     */     } 
/* 114 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/activation/ActivationGroupID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */