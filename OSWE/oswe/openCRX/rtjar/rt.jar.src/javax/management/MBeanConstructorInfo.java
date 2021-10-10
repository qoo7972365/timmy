/*     */ package javax.management;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Introspector;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Arrays;
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
/*     */ public class MBeanConstructorInfo
/*     */   extends MBeanFeatureInfo
/*     */   implements Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 4433990064191844427L;
/*  46 */   static final MBeanConstructorInfo[] NO_CONSTRUCTORS = new MBeanConstructorInfo[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient boolean arrayGettersSafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MBeanParameterInfo[] signature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanConstructorInfo(String paramString, Constructor<?> paramConstructor) {
/*  69 */     this(paramConstructor.getName(), paramString, 
/*  70 */         constructorSignature(paramConstructor), 
/*  71 */         Introspector.descriptorForElement(paramConstructor));
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
/*     */   public MBeanConstructorInfo(String paramString1, String paramString2, MBeanParameterInfo[] paramArrayOfMBeanParameterInfo) {
/*  86 */     this(paramString1, paramString2, paramArrayOfMBeanParameterInfo, (Descriptor)null);
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
/*     */   public MBeanConstructorInfo(String paramString1, String paramString2, MBeanParameterInfo[] paramArrayOfMBeanParameterInfo, Descriptor paramDescriptor) {
/* 106 */     super(paramString1, paramString2, paramDescriptor);
/*     */     
/* 108 */     if (paramArrayOfMBeanParameterInfo == null || paramArrayOfMBeanParameterInfo.length == 0) {
/* 109 */       paramArrayOfMBeanParameterInfo = MBeanParameterInfo.NO_PARAMS;
/*     */     } else {
/* 111 */       paramArrayOfMBeanParameterInfo = (MBeanParameterInfo[])paramArrayOfMBeanParameterInfo.clone();
/* 112 */     }  this.signature = paramArrayOfMBeanParameterInfo;
/* 113 */     this
/* 114 */       .arrayGettersSafe = MBeanInfo.arrayGettersSafe(getClass(), MBeanConstructorInfo.class);
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
/*     */   public Object clone() {
/*     */     try {
/* 131 */       return super.clone();
/* 132 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 134 */       return null;
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
/*     */ 
/*     */   
/*     */   public MBeanParameterInfo[] getSignature() {
/* 152 */     if (this.signature.length == 0) {
/* 153 */       return this.signature;
/*     */     }
/* 155 */     return (MBeanParameterInfo[])this.signature.clone();
/*     */   }
/*     */   
/*     */   private MBeanParameterInfo[] fastGetSignature() {
/* 159 */     if (this.arrayGettersSafe) {
/* 160 */       return this.signature;
/*     */     }
/* 162 */     return getSignature();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 166 */     return 
/* 167 */       getClass().getName() + "[description=" + 
/* 168 */       getDescription() + ", name=" + 
/* 169 */       getName() + ", signature=" + 
/* 170 */       Arrays.<MBeanParameterInfo>asList(fastGetSignature()) + ", descriptor=" + 
/* 171 */       getDescriptor() + "]";
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
/*     */   public boolean equals(Object paramObject) {
/* 189 */     if (paramObject == this)
/* 190 */       return true; 
/* 191 */     if (!(paramObject instanceof MBeanConstructorInfo))
/* 192 */       return false; 
/* 193 */     MBeanConstructorInfo mBeanConstructorInfo = (MBeanConstructorInfo)paramObject;
/* 194 */     return (Objects.equals(mBeanConstructorInfo.getName(), getName()) && 
/* 195 */       Objects.equals(mBeanConstructorInfo.getDescription(), getDescription()) && 
/* 196 */       Arrays.equals((Object[])mBeanConstructorInfo.fastGetSignature(), (Object[])fastGetSignature()) && 
/* 197 */       Objects.equals(mBeanConstructorInfo.getDescriptor(), getDescriptor()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 207 */     return Objects.hash(new Object[] { getName() }) ^ Arrays.hashCode((Object[])fastGetSignature());
/*     */   }
/*     */   
/*     */   private static MBeanParameterInfo[] constructorSignature(Constructor<?> paramConstructor) {
/* 211 */     Class[] arrayOfClass = paramConstructor.getParameterTypes();
/* 212 */     Annotation[][] arrayOfAnnotation = paramConstructor.getParameterAnnotations();
/* 213 */     return MBeanOperationInfo.parameters(arrayOfClass, arrayOfAnnotation);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanConstructorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */