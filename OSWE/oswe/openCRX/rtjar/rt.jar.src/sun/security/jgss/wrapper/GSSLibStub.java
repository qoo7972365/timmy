/*     */ package sun.security.jgss.wrapper;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import org.ietf.jgss.ChannelBinding;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ import org.ietf.jgss.Oid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GSSLibStub
/*     */ {
/*     */   private Oid mech;
/*     */   private long pMech;
/*     */   
/*     */   static native boolean init(String paramString, boolean paramBoolean);
/*     */   
/*     */   private static native long getMechPtr(byte[] paramArrayOfbyte);
/*     */   
/*     */   static native Oid[] indicateMechs();
/*     */   
/*     */   native Oid[] inquireNamesForMech() throws GSSException;
/*     */   
/*     */   native void releaseName(long paramLong);
/*     */   
/*     */   native long importName(byte[] paramArrayOfbyte, Oid paramOid);
/*     */   
/*     */   native boolean compareName(long paramLong1, long paramLong2);
/*     */   
/*     */   native long canonicalizeName(long paramLong);
/*     */   
/*     */   native byte[] exportName(long paramLong) throws GSSException;
/*     */   
/*     */   native Object[] displayName(long paramLong) throws GSSException;
/*     */   
/*     */   native long acquireCred(long paramLong, int paramInt1, int paramInt2) throws GSSException;
/*     */   
/*     */   native long releaseCred(long paramLong);
/*     */   
/*     */   native long getCredName(long paramLong);
/*     */   
/*     */   native int getCredTime(long paramLong);
/*     */   
/*     */   native int getCredUsage(long paramLong);
/*     */   
/*     */   native NativeGSSContext importContext(byte[] paramArrayOfbyte);
/*     */   
/*     */   native byte[] initContext(long paramLong1, long paramLong2, ChannelBinding paramChannelBinding, byte[] paramArrayOfbyte, NativeGSSContext paramNativeGSSContext);
/*     */   
/*     */   native byte[] acceptContext(long paramLong, ChannelBinding paramChannelBinding, byte[] paramArrayOfbyte, NativeGSSContext paramNativeGSSContext);
/*     */   
/*     */   native long[] inquireContext(long paramLong);
/*     */   
/*     */   native Oid getContextMech(long paramLong);
/*     */   
/*     */   native long getContextName(long paramLong, boolean paramBoolean);
/*     */   
/*     */   native int getContextTime(long paramLong);
/*     */   
/*     */   native long deleteContext(long paramLong);
/*     */   
/*     */   native int wrapSizeLimit(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   native byte[] exportContext(long paramLong);
/*     */   
/*     */   native byte[] getMic(long paramLong, int paramInt, byte[] paramArrayOfbyte);
/*     */   
/*     */   native void verifyMic(long paramLong, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, MessageProp paramMessageProp);
/*     */   
/*     */   native byte[] wrap(long paramLong, byte[] paramArrayOfbyte, MessageProp paramMessageProp);
/*     */   
/*     */   native byte[] unwrap(long paramLong, byte[] paramArrayOfbyte, MessageProp paramMessageProp);
/*     */   
/*  98 */   private static Hashtable<Oid, GSSLibStub> table = new Hashtable<>(5);
/*     */   
/*     */   static GSSLibStub getInstance(Oid paramOid) throws GSSException {
/* 101 */     GSSLibStub gSSLibStub = table.get(paramOid);
/* 102 */     if (gSSLibStub == null) {
/* 103 */       gSSLibStub = new GSSLibStub(paramOid);
/* 104 */       table.put(paramOid, gSSLibStub);
/*     */     } 
/* 106 */     return gSSLibStub;
/*     */   }
/*     */   private GSSLibStub(Oid paramOid) throws GSSException {
/* 109 */     SunNativeProvider.debug("Created GSSLibStub for mech " + paramOid);
/* 110 */     this.mech = paramOid;
/* 111 */     this.pMech = getMechPtr(paramOid.getDER());
/*     */   }
/*     */   public boolean equals(Object paramObject) {
/* 114 */     if (paramObject == this) return true; 
/* 115 */     if (!(paramObject instanceof GSSLibStub)) {
/* 116 */       return false;
/*     */     }
/* 118 */     return this.mech.equals(((GSSLibStub)paramObject).getMech());
/*     */   }
/*     */   public int hashCode() {
/* 121 */     return this.mech.hashCode();
/*     */   }
/*     */   Oid getMech() {
/* 124 */     return this.mech;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/wrapper/GSSLibStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */