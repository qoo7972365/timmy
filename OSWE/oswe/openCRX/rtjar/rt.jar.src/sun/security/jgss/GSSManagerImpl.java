/*     */ package sun.security.jgss;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import org.ietf.jgss.GSSContext;
/*     */ import org.ietf.jgss.GSSCredential;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSManager;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.spi.GSSContextSpi;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.jgss.spi.MechanismFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GSSManagerImpl
/*     */   extends GSSManager
/*     */ {
/*     */   private static final String USE_NATIVE_PROP = "sun.security.jgss.native";
/*     */   
/*  47 */   private static final Boolean USE_NATIVE = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */         public Boolean run() {
/*  49 */           String str = System.getProperty("os.name");
/*  50 */           if (str.startsWith("SunOS") || str
/*  51 */             .contains("OS X") || str
/*  52 */             .startsWith("Linux")) {
/*  53 */             return new Boolean(
/*  54 */                 System.getProperty("sun.security.jgss.native"));
/*     */           }
/*  56 */           return Boolean.FALSE;
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */   
/*     */   private ProviderList list;
/*     */ 
/*     */   
/*     */   public GSSManagerImpl(GSSCaller paramGSSCaller, boolean paramBoolean) {
/*  66 */     this.list = new ProviderList(paramGSSCaller, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSManagerImpl(GSSCaller paramGSSCaller) {
/*  71 */     this.list = new ProviderList(paramGSSCaller, USE_NATIVE.booleanValue());
/*     */   }
/*     */   
/*     */   public GSSManagerImpl() {
/*  75 */     this.list = new ProviderList(GSSCaller.CALLER_UNKNOWN, USE_NATIVE.booleanValue());
/*     */   }
/*     */   
/*     */   public Oid[] getMechs() {
/*  79 */     return this.list.getMechs();
/*     */   }
/*     */ 
/*     */   
/*     */   public Oid[] getNamesForMech(Oid paramOid) throws GSSException {
/*  84 */     MechanismFactory mechanismFactory = this.list.getMechFactory(paramOid);
/*  85 */     return (Oid[])mechanismFactory.getNameTypes().clone();
/*     */   }
/*     */   
/*     */   public Oid[] getMechsForName(Oid paramOid) {
/*  89 */     Oid[] arrayOfOid1 = this.list.getMechs();
/*  90 */     Oid[] arrayOfOid2 = new Oid[arrayOfOid1.length];
/*  91 */     byte b1 = 0;
/*     */ 
/*     */     
/*  94 */     if (paramOid.equals(GSSNameImpl.oldHostbasedServiceName)) {
/*  95 */       paramOid = GSSName.NT_HOSTBASED_SERVICE;
/*     */     }
/*     */ 
/*     */     
/*  99 */     for (byte b2 = 0; b2 < arrayOfOid1.length; b2++) {
/*     */       
/* 101 */       Oid oid = arrayOfOid1[b2];
/*     */       try {
/* 103 */         Oid[] arrayOfOid = getNamesForMech(oid);
/*     */         
/* 105 */         if (paramOid.containedIn(arrayOfOid)) {
/* 106 */           arrayOfOid2[b1++] = oid;
/*     */         }
/* 108 */       } catch (GSSException gSSException) {
/*     */         
/* 110 */         GSSUtil.debug("Skip " + oid + ": error retrieving supported name types");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     if (b1 < arrayOfOid2.length) {
/* 117 */       Oid[] arrayOfOid = new Oid[b1];
/* 118 */       for (byte b = 0; b < b1; b++)
/* 119 */         arrayOfOid[b] = arrayOfOid2[b]; 
/* 120 */       arrayOfOid2 = arrayOfOid;
/*     */     } 
/*     */     
/* 123 */     return arrayOfOid2;
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSName createName(String paramString, Oid paramOid) throws GSSException {
/* 128 */     return new GSSNameImpl(this, paramString, paramOid);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSName createName(byte[] paramArrayOfbyte, Oid paramOid) throws GSSException {
/* 133 */     return new GSSNameImpl(this, paramArrayOfbyte, paramOid);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSName createName(String paramString, Oid paramOid1, Oid paramOid2) throws GSSException {
/* 138 */     return new GSSNameImpl(this, paramString, paramOid1, paramOid2);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSName createName(byte[] paramArrayOfbyte, Oid paramOid1, Oid paramOid2) throws GSSException {
/* 143 */     return new GSSNameImpl(this, paramArrayOfbyte, paramOid1, paramOid2);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSCredential createCredential(int paramInt) throws GSSException {
/* 148 */     return new GSSCredentialImpl(this, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSCredential createCredential(GSSName paramGSSName, int paramInt1, Oid paramOid, int paramInt2) throws GSSException {
/* 154 */     return new GSSCredentialImpl(this, paramGSSName, paramInt1, paramOid, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSCredential createCredential(GSSName paramGSSName, int paramInt1, Oid[] paramArrayOfOid, int paramInt2) throws GSSException {
/* 160 */     return new GSSCredentialImpl(this, paramGSSName, paramInt1, paramArrayOfOid, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContext createContext(GSSName paramGSSName, Oid paramOid, GSSCredential paramGSSCredential, int paramInt) throws GSSException {
/* 166 */     return new GSSContextImpl(this, paramGSSName, paramOid, paramGSSCredential, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSContext createContext(GSSCredential paramGSSCredential) throws GSSException {
/* 171 */     return new GSSContextImpl(this, paramGSSCredential);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSContext createContext(byte[] paramArrayOfbyte) throws GSSException {
/* 176 */     return new GSSContextImpl(this, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addProviderAtFront(Provider paramProvider, Oid paramOid) throws GSSException {
/* 181 */     this.list.addProviderAtFront(paramProvider, paramOid);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addProviderAtEnd(Provider paramProvider, Oid paramOid) throws GSSException {
/* 186 */     this.list.addProviderAtEnd(paramProvider, paramOid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSCredentialSpi getCredentialElement(GSSNameSpi paramGSSNameSpi, int paramInt1, int paramInt2, Oid paramOid, int paramInt3) throws GSSException {
/* 192 */     MechanismFactory mechanismFactory = this.list.getMechFactory(paramOid);
/* 193 */     return mechanismFactory.getCredentialElement(paramGSSNameSpi, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSNameSpi getNameElement(String paramString, Oid paramOid1, Oid paramOid2) throws GSSException {
/* 202 */     MechanismFactory mechanismFactory = this.list.getMechFactory(paramOid2);
/* 203 */     return mechanismFactory.getNameElement(paramString, paramOid1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSNameSpi getNameElement(byte[] paramArrayOfbyte, Oid paramOid1, Oid paramOid2) throws GSSException {
/* 211 */     MechanismFactory mechanismFactory = this.list.getMechFactory(paramOid2);
/* 212 */     return mechanismFactory.getNameElement(paramArrayOfbyte, paramOid1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GSSContextSpi getMechanismContext(GSSNameSpi paramGSSNameSpi, GSSCredentialSpi paramGSSCredentialSpi, int paramInt, Oid paramOid) throws GSSException {
/* 219 */     Provider provider = null;
/* 220 */     if (paramGSSCredentialSpi != null) {
/* 221 */       provider = paramGSSCredentialSpi.getProvider();
/*     */     }
/* 223 */     MechanismFactory mechanismFactory = this.list.getMechFactory(paramOid, provider);
/* 224 */     return mechanismFactory.getMechanismContext(paramGSSNameSpi, paramGSSCredentialSpi, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   GSSContextSpi getMechanismContext(GSSCredentialSpi paramGSSCredentialSpi, Oid paramOid) throws GSSException {
/* 230 */     Provider provider = null;
/* 231 */     if (paramGSSCredentialSpi != null) {
/* 232 */       provider = paramGSSCredentialSpi.getProvider();
/*     */     }
/* 234 */     MechanismFactory mechanismFactory = this.list.getMechFactory(paramOid, provider);
/* 235 */     return mechanismFactory.getMechanismContext(paramGSSCredentialSpi);
/*     */   }
/*     */ 
/*     */   
/*     */   GSSContextSpi getMechanismContext(byte[] paramArrayOfbyte) throws GSSException {
/* 240 */     if (paramArrayOfbyte == null || paramArrayOfbyte.length == 0) {
/* 241 */       throw new GSSException(12);
/*     */     }
/* 243 */     GSSContextSpi gSSContextSpi = null;
/*     */ 
/*     */ 
/*     */     
/* 247 */     Oid[] arrayOfOid = this.list.getMechs();
/* 248 */     for (byte b = 0; b < arrayOfOid.length; b++) {
/* 249 */       MechanismFactory mechanismFactory = this.list.getMechFactory(arrayOfOid[b]);
/* 250 */       if (mechanismFactory.getProvider().getName().equals("SunNativeGSS")) {
/* 251 */         gSSContextSpi = mechanismFactory.getMechanismContext(paramArrayOfbyte);
/* 252 */         if (gSSContextSpi != null)
/*     */           break; 
/*     */       } 
/* 255 */     }  if (gSSContextSpi == null) {
/* 256 */       throw new GSSException(16);
/*     */     }
/* 258 */     return gSSContextSpi;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/GSSManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */