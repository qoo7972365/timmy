/*     */ package sun.java2d.cmm.lcms;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
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
/*     */ final class LCMSProfile
/*     */   extends Profile
/*     */ {
/*     */   private final TagCache tagCache;
/*     */   private final Object disposerReferent;
/*     */   
/*     */   LCMSProfile(long paramLong, Object paramObject) {
/*  39 */     super(paramLong);
/*     */     
/*  41 */     this.disposerReferent = paramObject;
/*     */     
/*  43 */     this.tagCache = new TagCache(this);
/*     */   }
/*     */   
/*     */   final long getLcmsPtr() {
/*  47 */     return getNativePtr();
/*     */   }
/*     */   
/*     */   TagData getTag(int paramInt) {
/*  51 */     return this.tagCache.getTag(paramInt);
/*     */   }
/*     */   
/*     */   void clearTagCache() {
/*  55 */     this.tagCache.clear();
/*     */   }
/*     */   
/*     */   static class TagCache {
/*     */     final LCMSProfile profile;
/*     */     private HashMap<Integer, LCMSProfile.TagData> tags;
/*     */     
/*     */     TagCache(LCMSProfile param1LCMSProfile) {
/*  63 */       this.profile = param1LCMSProfile;
/*  64 */       this.tags = new HashMap<>();
/*     */     }
/*     */     
/*     */     LCMSProfile.TagData getTag(int param1Int) {
/*  68 */       LCMSProfile.TagData tagData = this.tags.get(Integer.valueOf(param1Int));
/*  69 */       if (tagData == null) {
/*  70 */         byte[] arrayOfByte = LCMS.getTagNative(this.profile.getNativePtr(), param1Int);
/*  71 */         if (arrayOfByte != null) {
/*  72 */           tagData = new LCMSProfile.TagData(param1Int, arrayOfByte);
/*  73 */           this.tags.put(Integer.valueOf(param1Int), tagData);
/*     */         } 
/*     */       } 
/*  76 */       return tagData;
/*     */     }
/*     */     
/*     */     void clear() {
/*  80 */       this.tags.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   static class TagData {
/*     */     private int signature;
/*     */     private byte[] data;
/*     */     
/*     */     TagData(int param1Int, byte[] param1ArrayOfbyte) {
/*  89 */       this.signature = param1Int;
/*  90 */       this.data = param1ArrayOfbyte;
/*     */     }
/*     */     
/*     */     int getSize() {
/*  94 */       return this.data.length;
/*     */     }
/*     */     
/*     */     byte[] getData() {
/*  98 */       return Arrays.copyOf(this.data, this.data.length);
/*     */     }
/*     */     
/*     */     void copyDataTo(byte[] param1ArrayOfbyte) {
/* 102 */       System.arraycopy(this.data, 0, param1ArrayOfbyte, 0, this.data.length);
/*     */     }
/*     */     
/*     */     int getSignature() {
/* 106 */       return this.signature;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/lcms/LCMSProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */