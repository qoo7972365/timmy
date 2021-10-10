/*     */ package sun.management;
/*     */ 
/*     */ import com.sun.management.VMOption;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Flag
/*     */ {
/*     */   private String name;
/*     */   private Object value;
/*     */   private VMOption.Origin origin;
/*     */   private boolean writeable;
/*     */   private boolean external;
/*     */   
/*     */   Flag(String paramString, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, VMOption.Origin paramOrigin) {
/*  48 */     this.name = paramString;
/*  49 */     this.value = (paramObject == null) ? "" : paramObject;
/*  50 */     this.origin = paramOrigin;
/*  51 */     this.writeable = paramBoolean1;
/*  52 */     this.external = paramBoolean2;
/*     */   }
/*     */   
/*     */   Object getValue() {
/*  56 */     return this.value;
/*     */   }
/*     */   
/*     */   boolean isWriteable() {
/*  60 */     return this.writeable;
/*     */   }
/*     */   
/*     */   boolean isExternal() {
/*  64 */     return this.external;
/*     */   }
/*     */   
/*     */   VMOption getVMOption() {
/*  68 */     return new VMOption(this.name, this.value.toString(), this.writeable, this.origin);
/*     */   }
/*     */   
/*     */   static Flag getFlag(String paramString) {
/*  72 */     String[] arrayOfString = new String[1];
/*  73 */     arrayOfString[0] = paramString;
/*     */     
/*  75 */     List<Flag> list = getFlags(arrayOfString, 1);
/*  76 */     if (list.isEmpty()) {
/*  77 */       return null;
/*     */     }
/*     */     
/*  80 */     return list.get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   static List<Flag> getAllFlags() {
/*  85 */     int i = getInternalFlagCount();
/*     */ 
/*     */     
/*  88 */     return getFlags(null, i);
/*     */   }
/*     */   
/*     */   private static List<Flag> getFlags(String[] paramArrayOfString, int paramInt) {
/*  92 */     Flag[] arrayOfFlag = new Flag[paramInt];
/*  93 */     int i = getFlags(paramArrayOfString, arrayOfFlag, paramInt);
/*     */     
/*  95 */     ArrayList<Flag> arrayList = new ArrayList();
/*  96 */     for (Flag flag : arrayOfFlag) {
/*  97 */       if (flag != null) {
/*  98 */         arrayList.add(flag);
/*     */       }
/*     */     } 
/* 101 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native String[] getAllFlagNames();
/*     */ 
/*     */ 
/*     */   
/*     */   private static native int getFlags(String[] paramArrayOfString, Flag[] paramArrayOfFlag, int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   private static native int getInternalFlagCount();
/*     */ 
/*     */   
/*     */   static {
/* 118 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 121 */             System.loadLibrary("management");
/* 122 */             return null;
/*     */           }
/*     */         });
/* 125 */     initialize();
/*     */   }
/*     */   
/*     */   static synchronized native void setLongValue(String paramString, long paramLong);
/*     */   
/*     */   static synchronized native void setBooleanValue(String paramString, boolean paramBoolean);
/*     */   
/*     */   static synchronized native void setStringValue(String paramString1, String paramString2);
/*     */   
/*     */   private static native void initialize();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/Flag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */