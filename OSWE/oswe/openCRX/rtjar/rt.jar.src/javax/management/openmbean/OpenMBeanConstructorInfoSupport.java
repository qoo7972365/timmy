/*     */ package javax.management.openmbean;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.MBeanConstructorInfo;
/*     */ import javax.management.MBeanParameterInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OpenMBeanConstructorInfoSupport
/*     */   extends MBeanConstructorInfo
/*     */   implements OpenMBeanConstructorInfo
/*     */ {
/*     */   static final long serialVersionUID = -4400441579007477003L;
/*  55 */   private transient Integer myHashCode = null;
/*  56 */   private transient String myToString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMBeanConstructorInfoSupport(String paramString1, String paramString2, OpenMBeanParameterInfo[] paramArrayOfOpenMBeanParameterInfo) {
/*  84 */     this(paramString1, paramString2, paramArrayOfOpenMBeanParameterInfo, (Descriptor)null);
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
/*     */   public OpenMBeanConstructorInfoSupport(String paramString1, String paramString2, OpenMBeanParameterInfo[] paramArrayOfOpenMBeanParameterInfo, Descriptor paramDescriptor) {
/* 119 */     super(paramString1, paramString2, 
/*     */         
/* 121 */         arrayCopyCast(paramArrayOfOpenMBeanParameterInfo), paramDescriptor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (paramString1 == null || paramString1.trim().equals("")) {
/* 128 */       throw new IllegalArgumentException("Argument name cannot be null or empty");
/*     */     }
/*     */     
/* 131 */     if (paramString2 == null || paramString2.trim().equals("")) {
/* 132 */       throw new IllegalArgumentException("Argument description cannot be null or empty");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MBeanParameterInfo[] arrayCopyCast(OpenMBeanParameterInfo[] paramArrayOfOpenMBeanParameterInfo) {
/* 140 */     if (paramArrayOfOpenMBeanParameterInfo == null) {
/* 141 */       return null;
/*     */     }
/* 143 */     MBeanParameterInfo[] arrayOfMBeanParameterInfo = new MBeanParameterInfo[paramArrayOfOpenMBeanParameterInfo.length];
/* 144 */     System.arraycopy(paramArrayOfOpenMBeanParameterInfo, 0, arrayOfMBeanParameterInfo, 0, paramArrayOfOpenMBeanParameterInfo.length);
/*     */     
/* 146 */     return arrayOfMBeanParameterInfo;
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
/*     */   public boolean equals(Object paramObject) {
/*     */     OpenMBeanConstructorInfo openMBeanConstructorInfo;
/* 183 */     if (paramObject == null) {
/* 184 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 191 */       openMBeanConstructorInfo = (OpenMBeanConstructorInfo)paramObject;
/* 192 */     } catch (ClassCastException classCastException) {
/* 193 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (!getName().equals(openMBeanConstructorInfo.getName())) {
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 206 */     if (!Arrays.equals((Object[])getSignature(), (Object[])openMBeanConstructorInfo.getSignature())) {
/* 207 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 212 */     return true;
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
/*     */   public int hashCode() {
/* 251 */     if (this.myHashCode == null) {
/* 252 */       int i = 0;
/* 253 */       i += getName().hashCode();
/* 254 */       i += Arrays.<MBeanParameterInfo>asList(getSignature()).hashCode();
/* 255 */       this.myHashCode = Integer.valueOf(i);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 260 */     return this.myHashCode.intValue();
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
/*     */   public String toString() {
/* 286 */     if (this.myToString == null) {
/* 287 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 296 */         .myToString = getClass().getName() + "(name=" + getName() + ",signature=" + Arrays.<MBeanParameterInfo>asList(getSignature()).toString() + ",descriptor=" + getDescriptor() + ")";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     return this.myToString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/OpenMBeanConstructorInfoSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */