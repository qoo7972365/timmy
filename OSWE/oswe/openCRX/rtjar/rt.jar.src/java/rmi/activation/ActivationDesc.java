/*     */ package java.rmi.activation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.rmi.MarshalledObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ActivationDesc
/*     */   implements Serializable
/*     */ {
/*     */   private ActivationGroupID groupID;
/*     */   private String className;
/*     */   private String location;
/*     */   private MarshalledObject<?> data;
/*     */   private boolean restart;
/*     */   private static final long serialVersionUID = 7455834104417690957L;
/*     */   
/*     */   public ActivationDesc(String paramString1, String paramString2, MarshalledObject<?> paramMarshalledObject) throws ActivationException {
/* 117 */     this(ActivationGroup.internalCurrentGroupID(), paramString1, paramString2, paramMarshalledObject, false);
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
/*     */   public ActivationDesc(String paramString1, String paramString2, MarshalledObject<?> paramMarshalledObject, boolean paramBoolean) throws ActivationException {
/* 157 */     this(ActivationGroup.internalCurrentGroupID(), paramString1, paramString2, paramMarshalledObject, paramBoolean);
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
/*     */   public ActivationDesc(ActivationGroupID paramActivationGroupID, String paramString1, String paramString2, MarshalledObject<?> paramMarshalledObject) {
/* 192 */     this(paramActivationGroupID, paramString1, paramString2, paramMarshalledObject, false);
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
/*     */   public ActivationDesc(ActivationGroupID paramActivationGroupID, String paramString1, String paramString2, MarshalledObject<?> paramMarshalledObject, boolean paramBoolean) {
/* 227 */     if (paramActivationGroupID == null)
/* 228 */       throw new IllegalArgumentException("groupID can't be null"); 
/* 229 */     this.groupID = paramActivationGroupID;
/* 230 */     this.className = paramString1;
/* 231 */     this.location = paramString2;
/* 232 */     this.data = paramMarshalledObject;
/* 233 */     this.restart = paramBoolean;
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
/*     */   public ActivationGroupID getGroupID() {
/* 246 */     return this.groupID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/* 256 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocation() {
/* 266 */     return this.location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MarshalledObject<?> getData() {
/* 276 */     return this.data;
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
/*     */   public boolean getRestartMode() {
/* 294 */     return this.restart;
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
/*     */   public boolean equals(Object paramObject) {
/* 307 */     if (paramObject instanceof ActivationDesc) {
/* 308 */       ActivationDesc activationDesc = (ActivationDesc)paramObject;
/* 309 */       if ((this.groupID == null) ? (activationDesc.groupID == null) : this.groupID
/*     */         
/* 311 */         .equals(activationDesc.groupID)) if ((this.className == null) ? (activationDesc.className == null) : this.className
/*     */           
/* 313 */           .equals(activationDesc.className)) if ((this.location == null) ? (activationDesc.location == null) : this.location
/*     */             
/* 315 */             .equals(activationDesc.location)) if ((this.data == null) ? (activationDesc.data == null) : this.data
/*     */               
/* 317 */               .equals(activationDesc.data)) if (this.restart == activationDesc.restart);
/*     */                
/*     */       return false;
/*     */     } 
/* 321 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 331 */     return ((this.location == null) ? 0 : (this.location
/*     */       
/* 333 */       .hashCode() << 24)) ^ ((this.groupID == null) ? 0 : (this.groupID
/*     */ 
/*     */       
/* 336 */       .hashCode() << 16)) ^ ((this.className == null) ? 0 : (this.className
/*     */ 
/*     */       
/* 339 */       .hashCode() << 9)) ^ ((this.data == null) ? 0 : (this.data
/*     */ 
/*     */       
/* 342 */       .hashCode() << 1)) ^ (this.restart ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/activation/ActivationDesc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */