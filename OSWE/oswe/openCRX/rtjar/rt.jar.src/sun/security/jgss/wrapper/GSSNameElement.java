/*     */ package sun.security.jgss.wrapper;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.Provider;
/*     */ import javax.security.auth.kerberos.ServicePermission;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSExceptionImpl;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.krb5.Realm;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GSSNameElement
/*     */   implements GSSNameSpi
/*     */ {
/*  53 */   long pName = 0L;
/*     */   
/*     */   private String printableName;
/*     */   private Oid printableType;
/*     */   private GSSLibStub cStub;
/*  58 */   static final GSSNameElement DEF_ACCEPTOR = new GSSNameElement();
/*     */   
/*     */   private static Oid getNativeNameType(Oid paramOid, GSSLibStub paramGSSLibStub) {
/*  61 */     if (GSSUtil.NT_GSS_KRB5_PRINCIPAL.equals(paramOid)) {
/*  62 */       Oid[] arrayOfOid = null;
/*     */       try {
/*  64 */         arrayOfOid = paramGSSLibStub.inquireNamesForMech();
/*  65 */       } catch (GSSException gSSException) {
/*  66 */         if (gSSException.getMajor() == 2 && 
/*  67 */           GSSUtil.isSpNegoMech(paramGSSLibStub.getMech())) {
/*     */ 
/*     */           
/*     */           try {
/*  71 */             paramGSSLibStub = GSSLibStub.getInstance(GSSUtil.GSS_KRB5_MECH_OID);
/*  72 */             arrayOfOid = paramGSSLibStub.inquireNamesForMech();
/*  73 */           } catch (GSSException gSSException1) {
/*     */             
/*  75 */             SunNativeProvider.debug("Name type list unavailable: " + gSSException1
/*  76 */                 .getMajorString());
/*     */           } 
/*     */         } else {
/*  79 */           SunNativeProvider.debug("Name type list unavailable: " + gSSException
/*  80 */               .getMajorString());
/*     */         } 
/*     */       } 
/*  83 */       if (arrayOfOid != null) {
/*  84 */         for (byte b = 0; b < arrayOfOid.length; b++) {
/*  85 */           if (arrayOfOid[b].equals(paramOid)) return paramOid;
/*     */         
/*     */         } 
/*  88 */         SunNativeProvider.debug("Override " + paramOid + " with mechanism default(null)");
/*     */         
/*  90 */         return null;
/*     */       } 
/*     */     } 
/*  93 */     return paramOid;
/*     */   }
/*     */   
/*     */   private GSSNameElement() {
/*  97 */     this.printableName = "<DEFAULT ACCEPTOR>";
/*     */   }
/*     */   
/*     */   GSSNameElement(long paramLong, GSSLibStub paramGSSLibStub) throws GSSException {
/* 101 */     assert paramGSSLibStub != null;
/* 102 */     if (paramLong == 0L) {
/* 103 */       throw new GSSException(3);
/*     */     }
/*     */     
/* 106 */     this.pName = paramLong;
/* 107 */     this.cStub = paramGSSLibStub;
/* 108 */     setPrintables();
/*     */   }
/*     */ 
/*     */   
/*     */   GSSNameElement(byte[] paramArrayOfbyte, Oid paramOid, GSSLibStub paramGSSLibStub) throws GSSException {
/* 113 */     assert paramGSSLibStub != null;
/* 114 */     if (paramArrayOfbyte == null) {
/* 115 */       throw new GSSException(3);
/*     */     }
/* 117 */     this.cStub = paramGSSLibStub;
/* 118 */     byte[] arrayOfByte = paramArrayOfbyte;
/*     */     
/* 120 */     if (paramOid != null) {
/*     */ 
/*     */       
/* 123 */       paramOid = getNativeNameType(paramOid, paramGSSLibStub);
/*     */       
/* 125 */       if (GSSName.NT_EXPORT_NAME.equals(paramOid)) {
/*     */ 
/*     */ 
/*     */         
/* 129 */         byte[] arrayOfByte1 = null;
/* 130 */         DerOutputStream derOutputStream = new DerOutputStream();
/* 131 */         Oid oid = this.cStub.getMech();
/*     */         try {
/* 133 */           derOutputStream.putOID(new ObjectIdentifier(oid.toString()));
/* 134 */         } catch (IOException iOException) {
/* 135 */           throw new GSSExceptionImpl(11, iOException);
/*     */         } 
/* 137 */         arrayOfByte1 = derOutputStream.toByteArray();
/* 138 */         arrayOfByte = new byte[4 + arrayOfByte1.length + 4 + paramArrayOfbyte.length];
/* 139 */         int i = 0;
/* 140 */         arrayOfByte[i++] = 4;
/* 141 */         arrayOfByte[i++] = 1;
/* 142 */         arrayOfByte[i++] = (byte)(arrayOfByte1.length >>> 8);
/* 143 */         arrayOfByte[i++] = (byte)arrayOfByte1.length;
/* 144 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte, i, arrayOfByte1.length);
/* 145 */         i += arrayOfByte1.length;
/* 146 */         arrayOfByte[i++] = (byte)(paramArrayOfbyte.length >>> 24);
/* 147 */         arrayOfByte[i++] = (byte)(paramArrayOfbyte.length >>> 16);
/* 148 */         arrayOfByte[i++] = (byte)(paramArrayOfbyte.length >>> 8);
/* 149 */         arrayOfByte[i++] = (byte)paramArrayOfbyte.length;
/* 150 */         System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, i, paramArrayOfbyte.length);
/*     */       } 
/*     */     } 
/* 153 */     this.pName = this.cStub.importName(arrayOfByte, paramOid);
/* 154 */     setPrintables();
/*     */     
/* 156 */     SecurityManager securityManager = System.getSecurityManager();
/* 157 */     if (securityManager != null && !Realm.AUTODEDUCEREALM) {
/* 158 */       String str = getKrbName();
/* 159 */       int i = str.lastIndexOf('@');
/* 160 */       if (i != -1) {
/* 161 */         String str1 = str.substring(i);
/*     */         
/* 163 */         if ((paramOid != null && 
/* 164 */           !paramOid.equals(GSSUtil.NT_GSS_KRB5_PRINCIPAL)) || 
/* 165 */           !(new String(paramArrayOfbyte)).endsWith(str1)) {
/*     */           
/*     */           try {
/*     */             
/* 169 */             securityManager.checkPermission(new ServicePermission(str1, "-"));
/* 170 */           } catch (SecurityException securityException) {
/*     */             
/* 172 */             throw new GSSException(11);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     SunNativeProvider.debug("Imported " + this.printableName + " w/ type " + this.printableType);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPrintables() throws GSSException {
/* 183 */     Object[] arrayOfObject = null;
/* 184 */     arrayOfObject = this.cStub.displayName(this.pName);
/* 185 */     assert arrayOfObject != null && arrayOfObject.length == 2;
/* 186 */     this.printableName = (String)arrayOfObject[0];
/* 187 */     assert this.printableName != null;
/* 188 */     this.printableType = (Oid)arrayOfObject[1];
/* 189 */     if (this.printableType == null) {
/* 190 */       this.printableType = GSSName.NT_USER_NAME;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKrbName() throws GSSException {
/* 196 */     long l = 0L;
/* 197 */     GSSLibStub gSSLibStub = this.cStub;
/* 198 */     if (!GSSUtil.isKerberosMech(this.cStub.getMech())) {
/* 199 */       gSSLibStub = GSSLibStub.getInstance(GSSUtil.GSS_KRB5_MECH_OID);
/*     */     }
/* 201 */     l = gSSLibStub.canonicalizeName(this.pName);
/* 202 */     Object[] arrayOfObject = gSSLibStub.displayName(l);
/* 203 */     gSSLibStub.releaseName(l);
/* 204 */     SunNativeProvider.debug("Got kerberized name: " + arrayOfObject[0]);
/* 205 */     return (String)arrayOfObject[0];
/*     */   }
/*     */   
/*     */   public Provider getProvider() {
/* 209 */     return SunNativeProvider.INSTANCE;
/*     */   }
/*     */   
/*     */   public boolean equals(GSSNameSpi paramGSSNameSpi) throws GSSException {
/* 213 */     if (!(paramGSSNameSpi instanceof GSSNameElement)) {
/* 214 */       return false;
/*     */     }
/* 216 */     return this.cStub.compareName(this.pName, ((GSSNameElement)paramGSSNameSpi).pName);
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 220 */     if (!(paramObject instanceof GSSNameElement)) {
/* 221 */       return false;
/*     */     }
/*     */     try {
/* 224 */       return equals((GSSNameElement)paramObject);
/* 225 */     } catch (GSSException gSSException) {
/* 226 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 231 */     return (new Long(this.pName)).hashCode();
/*     */   }
/*     */   
/*     */   public byte[] export() throws GSSException {
/* 235 */     byte[] arrayOfByte1 = this.cStub.exportName(this.pName);
/*     */ 
/*     */ 
/*     */     
/* 239 */     int i = 0;
/* 240 */     if (arrayOfByte1[i++] != 4 || arrayOfByte1[i++] != 1)
/*     */     {
/* 242 */       throw new GSSException(3);
/*     */     }
/* 244 */     int j = (0xFF & arrayOfByte1[i++]) << 8 | 0xFF & arrayOfByte1[i++];
/*     */     
/* 246 */     ObjectIdentifier objectIdentifier = null;
/*     */     try {
/* 248 */       DerInputStream derInputStream = new DerInputStream(arrayOfByte1, i, j);
/*     */       
/* 250 */       objectIdentifier = new ObjectIdentifier(derInputStream);
/* 251 */     } catch (IOException iOException) {
/* 252 */       throw new GSSExceptionImpl(3, iOException);
/*     */     } 
/* 254 */     Oid oid = new Oid(objectIdentifier.toString());
/* 255 */     assert oid.equals(getMechanism());
/* 256 */     i += j;
/* 257 */     int k = (0xFF & arrayOfByte1[i++]) << 24 | (0xFF & arrayOfByte1[i++]) << 16 | (0xFF & arrayOfByte1[i++]) << 8 | 0xFF & arrayOfByte1[i++];
/*     */ 
/*     */ 
/*     */     
/* 261 */     if (k < 0) {
/* 262 */       throw new GSSException(3);
/*     */     }
/* 264 */     byte[] arrayOfByte2 = new byte[k];
/* 265 */     System.arraycopy(arrayOfByte1, i, arrayOfByte2, 0, k);
/* 266 */     return arrayOfByte2;
/*     */   }
/*     */   
/*     */   public Oid getMechanism() {
/* 270 */     return this.cStub.getMech();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 274 */     return this.printableName;
/*     */   }
/*     */   
/*     */   public Oid getStringNameType() {
/* 278 */     return this.printableType;
/*     */   }
/*     */   
/*     */   public boolean isAnonymousName() {
/* 282 */     return GSSName.NT_ANONYMOUS.equals(this.printableType);
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 286 */     if (this.pName != 0L) {
/* 287 */       this.cStub.releaseName(this.pName);
/* 288 */       this.pName = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 293 */     dispose();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/wrapper/GSSNameElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */