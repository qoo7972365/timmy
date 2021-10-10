/*     */ package sun.security.jgss.spnego;
/*     */ 
/*     */ import java.security.Provider;
/*     */ import java.util.Vector;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSCaller;
/*     */ import sun.security.jgss.GSSManagerImpl;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.jgss.ProviderList;
/*     */ import sun.security.jgss.SunProvider;
/*     */ import sun.security.jgss.krb5.Krb5AcceptCredential;
/*     */ import sun.security.jgss.krb5.Krb5InitCredential;
/*     */ import sun.security.jgss.krb5.Krb5MechFactory;
/*     */ import sun.security.jgss.krb5.Krb5NameElement;
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
/*     */ public final class SpNegoMechFactory
/*     */   implements MechanismFactory
/*     */ {
/*  49 */   static final Provider PROVIDER = new SunProvider();
/*     */ 
/*     */ 
/*     */   
/*  53 */   static final Oid GSS_SPNEGO_MECH_OID = GSSUtil.createOid("1.3.6.1.5.5.2");
/*     */   
/*  55 */   private static Oid[] nameTypes = new Oid[] { GSSName.NT_USER_NAME, GSSName.NT_HOSTBASED_SERVICE, GSSName.NT_EXPORT_NAME };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final Oid DEFAULT_SPNEGO_MECH_OID = ProviderList.DEFAULT_MECH_OID.equals(GSS_SPNEGO_MECH_OID) ? GSSUtil.GSS_KRB5_MECH_OID : ProviderList.DEFAULT_MECH_OID;
/*     */ 
/*     */ 
/*     */   
/*     */   final GSSManagerImpl manager;
/*     */ 
/*     */ 
/*     */   
/*     */   final Oid[] availableMechs;
/*     */ 
/*     */ 
/*     */   
/*     */   private static SpNegoCredElement getCredFromSubject(GSSNameSpi paramGSSNameSpi, boolean paramBoolean) throws GSSException {
/*  75 */     Vector<SpNegoCredElement> vector = GSSUtil.searchSubject(paramGSSNameSpi, GSS_SPNEGO_MECH_OID, paramBoolean, SpNegoCredElement.class);
/*     */ 
/*     */ 
/*     */     
/*  79 */     SpNegoCredElement spNegoCredElement = (vector == null || vector.isEmpty()) ? null : vector.firstElement();
/*     */ 
/*     */     
/*  82 */     if (spNegoCredElement != null) {
/*  83 */       GSSCredentialSpi gSSCredentialSpi = spNegoCredElement.getInternalCred();
/*  84 */       if (GSSUtil.isKerberosMech(gSSCredentialSpi.getMechanism())) {
/*  85 */         if (paramBoolean) {
/*  86 */           Krb5InitCredential krb5InitCredential = (Krb5InitCredential)gSSCredentialSpi;
/*     */           
/*  88 */           Krb5MechFactory.checkInitCredPermission((Krb5NameElement)krb5InitCredential.getName());
/*     */         } else {
/*  90 */           Krb5AcceptCredential krb5AcceptCredential = (Krb5AcceptCredential)gSSCredentialSpi;
/*     */           
/*  92 */           Krb5MechFactory.checkAcceptCredPermission((Krb5NameElement)krb5AcceptCredential.getName(), paramGSSNameSpi);
/*     */         } 
/*     */       }
/*     */     } 
/*  96 */     return spNegoCredElement;
/*     */   }
/*     */   
/*     */   public SpNegoMechFactory(GSSCaller paramGSSCaller) {
/* 100 */     this.manager = new GSSManagerImpl(paramGSSCaller, false);
/* 101 */     Oid[] arrayOfOid = this.manager.getMechs();
/* 102 */     this.availableMechs = new Oid[arrayOfOid.length - 1]; byte b1, b2;
/* 103 */     for (b1 = 0, b2 = 0; b1 < arrayOfOid.length; b1++) {
/*     */       
/* 105 */       if (!arrayOfOid[b1].equals(GSS_SPNEGO_MECH_OID)) {
/* 106 */         this.availableMechs[b2++] = arrayOfOid[b1];
/*     */       }
/*     */     } 
/*     */     
/* 110 */     for (b1 = 0; b1 < this.availableMechs.length; b1++) {
/* 111 */       if (this.availableMechs[b1].equals(DEFAULT_SPNEGO_MECH_OID)) {
/* 112 */         if (b1 != 0) {
/* 113 */           this.availableMechs[b1] = this.availableMechs[0];
/* 114 */           this.availableMechs[0] = DEFAULT_SPNEGO_MECH_OID;
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSNameSpi getNameElement(String paramString, Oid paramOid) throws GSSException {
/* 123 */     return this.manager.getNameElement(paramString, paramOid, DEFAULT_SPNEGO_MECH_OID);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSNameSpi getNameElement(byte[] paramArrayOfbyte, Oid paramOid) throws GSSException {
/* 129 */     return this.manager.getNameElement(paramArrayOfbyte, paramOid, DEFAULT_SPNEGO_MECH_OID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSCredentialSpi getCredentialElement(GSSNameSpi paramGSSNameSpi, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/* 137 */     SpNegoCredElement spNegoCredElement = getCredFromSubject(paramGSSNameSpi, (paramInt3 != 2));
/*     */     
/* 139 */     if (spNegoCredElement == null)
/*     */     {
/*     */       
/* 142 */       spNegoCredElement = new SpNegoCredElement(this.manager.getCredentialElement(paramGSSNameSpi, paramInt1, paramInt2, null, paramInt3));
/*     */     }
/*     */     
/* 145 */     return spNegoCredElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContextSpi getMechanismContext(GSSNameSpi paramGSSNameSpi, GSSCredentialSpi paramGSSCredentialSpi, int paramInt) throws GSSException {
/* 152 */     if (paramGSSCredentialSpi == null) {
/* 153 */       paramGSSCredentialSpi = getCredFromSubject(null, true);
/* 154 */     } else if (!(paramGSSCredentialSpi instanceof SpNegoCredElement)) {
/*     */       
/* 156 */       SpNegoCredElement spNegoCredElement = new SpNegoCredElement(paramGSSCredentialSpi);
/* 157 */       return new SpNegoContext(this, paramGSSNameSpi, spNegoCredElement, paramInt);
/*     */     } 
/* 159 */     return new SpNegoContext(this, paramGSSNameSpi, paramGSSCredentialSpi, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContextSpi getMechanismContext(GSSCredentialSpi paramGSSCredentialSpi) throws GSSException {
/* 165 */     if (paramGSSCredentialSpi == null) {
/* 166 */       paramGSSCredentialSpi = getCredFromSubject(null, false);
/* 167 */     } else if (!(paramGSSCredentialSpi instanceof SpNegoCredElement)) {
/*     */       
/* 169 */       SpNegoCredElement spNegoCredElement = new SpNegoCredElement(paramGSSCredentialSpi);
/* 170 */       return new SpNegoContext(this, spNegoCredElement);
/*     */     } 
/* 172 */     return new SpNegoContext(this, paramGSSCredentialSpi);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContextSpi getMechanismContext(byte[] paramArrayOfbyte) throws GSSException {
/* 178 */     return new SpNegoContext(this, paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   public final Oid getMechanismOid() {
/* 182 */     return GSS_SPNEGO_MECH_OID;
/*     */   }
/*     */   
/*     */   public Provider getProvider() {
/* 186 */     return PROVIDER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Oid[] getNameTypes() {
/* 191 */     return nameTypes;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/spnego/SpNegoMechFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */