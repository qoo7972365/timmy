/*     */ package sun.java2d.cmm.lcms;
/*     */ 
/*     */ import java.awt.color.CMMException;
/*     */ import java.awt.color.ICC_Profile;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.java2d.cmm.ColorTransform;
/*     */ import sun.java2d.cmm.PCMM;
/*     */ import sun.java2d.cmm.Profile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LCMS
/*     */   implements PCMM
/*     */ {
/*     */   public Profile loadProfile(byte[] paramArrayOfbyte) {
/*  40 */     Object object = new Object();
/*     */     
/*  42 */     long l = loadProfileNative(paramArrayOfbyte, object);
/*     */     
/*  44 */     if (l != 0L) {
/*  45 */       return new LCMSProfile(l, object);
/*     */     }
/*  47 */     return null;
/*     */   }
/*     */   
/*     */   private native long loadProfileNative(byte[] paramArrayOfbyte, Object paramObject);
/*     */   
/*     */   private LCMSProfile getLcmsProfile(Profile paramProfile) {
/*  53 */     if (paramProfile instanceof LCMSProfile) {
/*  54 */       return (LCMSProfile)paramProfile;
/*     */     }
/*  56 */     throw new CMMException("Invalid profile: " + paramProfile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void freeProfile(Profile paramProfile) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProfileSize(Profile paramProfile) {
/*  67 */     synchronized (paramProfile) {
/*  68 */       return getProfileSizeNative(getLcmsProfile(paramProfile).getLcmsPtr());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private native int getProfileSizeNative(long paramLong);
/*     */   
/*     */   public void getProfileData(Profile paramProfile, byte[] paramArrayOfbyte) {
/*  76 */     synchronized (paramProfile) {
/*  77 */       getProfileDataNative(getLcmsProfile(paramProfile).getLcmsPtr(), paramArrayOfbyte);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private native void getProfileDataNative(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   public int getTagSize(Profile paramProfile, int paramInt) {
/*  85 */     LCMSProfile lCMSProfile = getLcmsProfile(paramProfile);
/*     */     
/*  87 */     synchronized (lCMSProfile) {
/*  88 */       LCMSProfile.TagData tagData = lCMSProfile.getTag(paramInt);
/*  89 */       return (tagData == null) ? 0 : tagData.getSize();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static native byte[] getTagNative(long paramLong, int paramInt);
/*     */ 
/*     */   
/*     */   public void getTagData(Profile paramProfile, int paramInt, byte[] paramArrayOfbyte) {
/*  98 */     LCMSProfile lCMSProfile = getLcmsProfile(paramProfile);
/*     */     
/* 100 */     synchronized (lCMSProfile) {
/* 101 */       LCMSProfile.TagData tagData = lCMSProfile.getTag(paramInt);
/* 102 */       if (tagData != null) {
/* 103 */         tagData.copyDataTo(paramArrayOfbyte);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setTagData(Profile paramProfile, int paramInt, byte[] paramArrayOfbyte) {
/* 110 */     LCMSProfile lCMSProfile = getLcmsProfile(paramProfile);
/*     */     
/* 112 */     synchronized (lCMSProfile) {
/* 113 */       lCMSProfile.clearTagCache();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       setTagDataNative(lCMSProfile.getLcmsPtr(), paramInt, paramArrayOfbyte);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native void setTagDataNative(long paramLong, int paramInt, byte[] paramArrayOfbyte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized native LCMSProfile getProfileID(ICC_Profile paramICC_Profile);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long createTransform(LCMSProfile[] paramArrayOfLCMSProfile, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2, Object paramObject) {
/* 148 */     long[] arrayOfLong = new long[paramArrayOfLCMSProfile.length];
/*     */     
/* 150 */     for (byte b = 0; b < paramArrayOfLCMSProfile.length; b++) {
/* 151 */       if (paramArrayOfLCMSProfile[b] == null) throw new CMMException("Unknown profile ID");
/*     */       
/* 153 */       arrayOfLong[b] = paramArrayOfLCMSProfile[b].getLcmsPtr();
/*     */     } 
/*     */     
/* 156 */     return createNativeTransform(arrayOfLong, paramInt1, paramInt2, paramBoolean1, paramInt3, paramBoolean2, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native long createNativeTransform(long[] paramArrayOflong, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2, Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorTransform createTransform(ICC_Profile paramICC_Profile, int paramInt1, int paramInt2) {
/* 173 */     return new LCMSTransform(paramICC_Profile, paramInt1, paramInt1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ColorTransform createTransform(ColorTransform[] paramArrayOfColorTransform) {
/* 183 */     return new LCMSTransform(paramArrayOfColorTransform);
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
/* 196 */   private static LCMS theLcms = null;
/*     */   
/*     */   static synchronized PCMM getModule() {
/* 199 */     if (theLcms != null) {
/* 200 */       return theLcms;
/*     */     }
/*     */     
/* 203 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */ 
/*     */           
/*     */           public Object run()
/*     */           {
/* 209 */             System.loadLibrary("awt");
/* 210 */             System.loadLibrary("javalcms");
/* 211 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 215 */     initLCMS(LCMSTransform.class, LCMSImageLayout.class, ICC_Profile.class);
/*     */     
/* 217 */     theLcms = new LCMS();
/*     */     
/* 219 */     return theLcms;
/*     */   }
/*     */   
/*     */   public static native void colorConvert(LCMSTransform paramLCMSTransform, LCMSImageLayout paramLCMSImageLayout1, LCMSImageLayout paramLCMSImageLayout2);
/*     */   
/*     */   public static native void freeTransform(long paramLong);
/*     */   
/*     */   public static native void initLCMS(Class paramClass1, Class paramClass2, Class paramClass3);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/lcms/LCMS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */