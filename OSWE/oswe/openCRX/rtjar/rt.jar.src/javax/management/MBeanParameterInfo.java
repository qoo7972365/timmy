/*     */ package javax.management;
/*     */ 
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanParameterInfo
/*     */   extends MBeanFeatureInfo
/*     */   implements Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 7432616882776782338L;
/*  44 */   static final MBeanParameterInfo[] NO_PARAMS = new MBeanParameterInfo[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanParameterInfo(String paramString1, String paramString2, String paramString3) {
/*  62 */     this(paramString1, paramString2, paramString3, (Descriptor)null);
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
/*     */   public MBeanParameterInfo(String paramString1, String paramString2, String paramString3, Descriptor paramDescriptor) {
/*  80 */     super(paramString1, paramString3, paramDescriptor);
/*     */     
/*  82 */     this.type = paramString2;
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
/*     */   public Object clone() {
/*     */     try {
/*  98 */       return super.clone();
/*  99 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 101 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 111 */     return this.type;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 115 */     return 
/* 116 */       getClass().getName() + "[description=" + 
/* 117 */       getDescription() + ", name=" + 
/* 118 */       getName() + ", type=" + 
/* 119 */       getType() + ", descriptor=" + 
/* 120 */       getDescriptor() + "]";
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
/* 136 */     if (paramObject == this)
/* 137 */       return true; 
/* 138 */     if (!(paramObject instanceof MBeanParameterInfo))
/* 139 */       return false; 
/* 140 */     MBeanParameterInfo mBeanParameterInfo = (MBeanParameterInfo)paramObject;
/* 141 */     return (Objects.equals(mBeanParameterInfo.getName(), getName()) && 
/* 142 */       Objects.equals(mBeanParameterInfo.getType(), getType()) && 
/* 143 */       Objects.equals(mBeanParameterInfo.getDescription(), getDescription()) && 
/* 144 */       Objects.equals(mBeanParameterInfo.getDescriptor(), getDescriptor()));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 148 */     return Objects.hash(new Object[] { getName(), getType() });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanParameterInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */