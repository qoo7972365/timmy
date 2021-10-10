/*     */ package javax.management;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanNotificationInfo
/*     */   extends MBeanFeatureInfo
/*     */   implements Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -3888371564530107064L;
/*  66 */   private static final String[] NO_TYPES = new String[0];
/*     */   
/*  68 */   static final MBeanNotificationInfo[] NO_NOTIFICATIONS = new MBeanNotificationInfo[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] types;
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
/*     */   
/*     */   public MBeanNotificationInfo(String[] paramArrayOfString, String paramString1, String paramString2) {
/*  92 */     this(paramArrayOfString, paramString1, paramString2, (Descriptor)null);
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
/*     */   public MBeanNotificationInfo(String[] paramArrayOfString, String paramString1, String paramString2, Descriptor paramDescriptor) {
/* 113 */     super(paramString1, paramString2, paramDescriptor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     this
/* 122 */       .types = (paramArrayOfString != null && paramArrayOfString.length > 0) ? (String[])paramArrayOfString.clone() : NO_TYPES;
/* 123 */     this
/* 124 */       .arrayGettersSafe = MBeanInfo.arrayGettersSafe(getClass(), MBeanNotificationInfo.class);
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
/*     */   public Object clone() {
/*     */     try {
/* 138 */       return super.clone();
/* 139 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 141 */       return null;
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
/*     */   public String[] getNotifTypes() {
/* 154 */     if (this.types.length == 0) {
/* 155 */       return NO_TYPES;
/*     */     }
/* 157 */     return (String[])this.types.clone();
/*     */   }
/*     */   
/*     */   private String[] fastGetNotifTypes() {
/* 161 */     if (this.arrayGettersSafe) {
/* 162 */       return this.types;
/*     */     }
/* 164 */     return getNotifTypes();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 168 */     return 
/* 169 */       getClass().getName() + "[description=" + 
/* 170 */       getDescription() + ", name=" + 
/* 171 */       getName() + ", notifTypes=" + 
/* 172 */       Arrays.<String>asList(fastGetNotifTypes()) + ", descriptor=" + 
/* 173 */       getDescriptor() + "]";
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
/*     */   public boolean equals(Object paramObject) {
/* 192 */     if (paramObject == this)
/* 193 */       return true; 
/* 194 */     if (!(paramObject instanceof MBeanNotificationInfo))
/* 195 */       return false; 
/* 196 */     MBeanNotificationInfo mBeanNotificationInfo = (MBeanNotificationInfo)paramObject;
/* 197 */     return (Objects.equals(mBeanNotificationInfo.getName(), getName()) && 
/* 198 */       Objects.equals(mBeanNotificationInfo.getDescription(), getDescription()) && 
/* 199 */       Objects.equals(mBeanNotificationInfo.getDescriptor(), getDescriptor()) && 
/* 200 */       Arrays.equals((Object[])mBeanNotificationInfo.fastGetNotifTypes(), (Object[])fastGetNotifTypes()));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 204 */     int i = getName().hashCode();
/* 205 */     for (byte b = 0; b < this.types.length; b++)
/* 206 */       i ^= this.types[b].hashCode(); 
/* 207 */     return i;
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 211 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 212 */     String[] arrayOfString = (String[])getField.get("types", (Object)null);
/*     */     
/* 214 */     this.types = (arrayOfString != null && arrayOfString.length != 0) ? (String[])arrayOfString.clone() : NO_TYPES;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanNotificationInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */