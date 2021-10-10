/*     */ package javax.management;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectInstance
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4099952623687795850L;
/*     */   private ObjectName name;
/*     */   private String className;
/*     */   
/*     */   public ObjectInstance(String paramString1, String paramString2) throws MalformedObjectNameException {
/*  75 */     this(new ObjectName(paramString1), paramString2);
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
/*     */   public ObjectInstance(ObjectName paramObjectName, String paramString) {
/*  93 */     if (paramObjectName.isPattern()) {
/*     */ 
/*     */       
/*  96 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Invalid name->" + paramObjectName.toString());
/*  97 */       throw new RuntimeOperationsException(illegalArgumentException);
/*     */     } 
/*  99 */     this.name = paramObjectName;
/* 100 */     this.className = paramString;
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
/* 113 */     if (!(paramObject instanceof ObjectInstance)) {
/* 114 */       return false;
/*     */     }
/* 116 */     ObjectInstance objectInstance = (ObjectInstance)paramObject;
/* 117 */     if (!this.name.equals(objectInstance.getObjectName())) return false; 
/* 118 */     if (this.className == null)
/* 119 */       return (objectInstance.getClassName() == null); 
/* 120 */     return this.className.equals(objectInstance.getClassName());
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 124 */     boolean bool = (this.className == null) ? false : this.className.hashCode();
/* 125 */     return this.name.hashCode() ^ bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName() {
/* 134 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/* 143 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 152 */     return getClassName() + "[" + getObjectName() + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/ObjectInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */