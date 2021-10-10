/*     */ package sun.instrument;
/*     */ 
/*     */ import java.lang.instrument.ClassFileTransformer;
/*     */ import java.security.ProtectionDomain;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformerManager
/*     */ {
/*     */   private TransformerInfo[] mTransformerList;
/*     */   private boolean mIsRetransformable;
/*     */   
/*     */   private class TransformerInfo
/*     */   {
/*     */     final ClassFileTransformer mTransformer;
/*     */     String mPrefix;
/*     */     
/*     */     TransformerInfo(ClassFileTransformer param1ClassFileTransformer) {
/*  49 */       this.mTransformer = param1ClassFileTransformer;
/*  50 */       this.mPrefix = null;
/*     */     }
/*     */     
/*     */     ClassFileTransformer transformer() {
/*  54 */       return this.mTransformer;
/*     */     }
/*     */     
/*     */     String getPrefix() {
/*  58 */       return this.mPrefix;
/*     */     }
/*     */     
/*     */     void setPrefix(String param1String) {
/*  62 */       this.mPrefix = param1String;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TransformerManager(boolean paramBoolean) {
/*  85 */     this.mTransformerList = new TransformerInfo[0];
/*  86 */     this.mIsRetransformable = paramBoolean;
/*     */   }
/*     */   
/*     */   boolean isRetransformable() {
/*  90 */     return this.mIsRetransformable;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void addTransformer(ClassFileTransformer paramClassFileTransformer) {
/*  95 */     TransformerInfo[] arrayOfTransformerInfo1 = this.mTransformerList;
/*  96 */     TransformerInfo[] arrayOfTransformerInfo2 = new TransformerInfo[arrayOfTransformerInfo1.length + 1];
/*  97 */     System.arraycopy(arrayOfTransformerInfo1, 0, arrayOfTransformerInfo2, 0, arrayOfTransformerInfo1.length);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     arrayOfTransformerInfo2[arrayOfTransformerInfo1.length] = new TransformerInfo(paramClassFileTransformer);
/* 103 */     this.mTransformerList = arrayOfTransformerInfo2;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean removeTransformer(ClassFileTransformer paramClassFileTransformer) {
/* 108 */     boolean bool = false;
/* 109 */     TransformerInfo[] arrayOfTransformerInfo = this.mTransformerList;
/* 110 */     int i = arrayOfTransformerInfo.length;
/* 111 */     int j = i - 1;
/*     */ 
/*     */ 
/*     */     
/* 115 */     int k = 0;
/* 116 */     for (int m = i - 1; m >= 0; m--) {
/* 117 */       if (arrayOfTransformerInfo[m].transformer() == paramClassFileTransformer) {
/* 118 */         bool = true;
/* 119 */         k = m;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     if (bool) {
/* 126 */       TransformerInfo[] arrayOfTransformerInfo1 = new TransformerInfo[j];
/*     */ 
/*     */       
/* 129 */       if (k > 0) {
/* 130 */         System.arraycopy(arrayOfTransformerInfo, 0, arrayOfTransformerInfo1, 0, k);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 138 */       if (k < j) {
/* 139 */         System.arraycopy(arrayOfTransformerInfo, k + 1, arrayOfTransformerInfo1, k, j - k);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       this.mTransformerList = arrayOfTransformerInfo1;
/*     */     } 
/* 147 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized boolean includesTransformer(ClassFileTransformer paramClassFileTransformer) {
/* 152 */     for (TransformerInfo transformerInfo : this.mTransformerList) {
/* 153 */       if (transformerInfo.transformer() == paramClassFileTransformer) {
/* 154 */         return true;
/*     */       }
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TransformerInfo[] getSnapshotTransformerList() {
/* 166 */     return this.mTransformerList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] transform(ClassLoader paramClassLoader, String paramString, Class<?> paramClass, ProtectionDomain paramProtectionDomain, byte[] paramArrayOfbyte) {
/*     */     byte[] arrayOfByte2;
/* 175 */     boolean bool = false;
/*     */     
/* 177 */     TransformerInfo[] arrayOfTransformerInfo = getSnapshotTransformerList();
/*     */     
/* 179 */     byte[] arrayOfByte1 = paramArrayOfbyte;
/*     */ 
/*     */     
/* 182 */     for (byte b = 0; b < arrayOfTransformerInfo.length; b++) {
/* 183 */       TransformerInfo transformerInfo = arrayOfTransformerInfo[b];
/* 184 */       ClassFileTransformer classFileTransformer = transformerInfo.transformer();
/* 185 */       byte[] arrayOfByte = null;
/*     */       
/*     */       try {
/* 188 */         arrayOfByte = classFileTransformer.transform(paramClassLoader, paramString, paramClass, paramProtectionDomain, arrayOfByte1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 194 */       catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       if (arrayOfByte != null) {
/* 200 */         bool = true;
/* 201 */         arrayOfByte1 = arrayOfByte;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     if (bool) {
/* 209 */       arrayOfByte2 = arrayOfByte1;
/*     */     } else {
/*     */       
/* 212 */       arrayOfByte2 = null;
/*     */     } 
/*     */     
/* 215 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int getTransformerCount() {
/* 221 */     TransformerInfo[] arrayOfTransformerInfo = getSnapshotTransformerList();
/* 222 */     return arrayOfTransformerInfo.length;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean setNativeMethodPrefix(ClassFileTransformer paramClassFileTransformer, String paramString) {
/* 227 */     TransformerInfo[] arrayOfTransformerInfo = getSnapshotTransformerList();
/*     */     
/* 229 */     for (byte b = 0; b < arrayOfTransformerInfo.length; b++) {
/* 230 */       TransformerInfo transformerInfo = arrayOfTransformerInfo[b];
/* 231 */       ClassFileTransformer classFileTransformer = transformerInfo.transformer();
/*     */       
/* 233 */       if (classFileTransformer == paramClassFileTransformer) {
/* 234 */         transformerInfo.setPrefix(paramString);
/* 235 */         return true;
/*     */       } 
/*     */     } 
/* 238 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   String[] getNativeMethodPrefixes() {
/* 244 */     TransformerInfo[] arrayOfTransformerInfo = getSnapshotTransformerList();
/* 245 */     String[] arrayOfString = new String[arrayOfTransformerInfo.length];
/*     */     
/* 247 */     for (byte b = 0; b < arrayOfTransformerInfo.length; b++) {
/* 248 */       TransformerInfo transformerInfo = arrayOfTransformerInfo[b];
/* 249 */       arrayOfString[b] = transformerInfo.getPrefix();
/*     */     } 
/* 251 */     return arrayOfString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/instrument/TransformerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */