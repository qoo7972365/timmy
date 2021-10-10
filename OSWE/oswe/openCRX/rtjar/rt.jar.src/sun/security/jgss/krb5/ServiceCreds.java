/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.kerberos.KerberosKey;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import javax.security.auth.kerberos.KeyTab;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServiceCreds
/*     */ {
/*     */   private KerberosPrincipal kp;
/*     */   private Set<KerberosPrincipal> allPrincs;
/*     */   private List<KeyTab> ktabs;
/*     */   private List<KerberosKey> kk;
/*     */   private KerberosTicket tgt;
/*     */   private boolean destroyed;
/*     */   
/*     */   public static ServiceCreds getInstance(Subject paramSubject, String paramString) {
/*  90 */     ServiceCreds serviceCreds = new ServiceCreds();
/*     */     
/*  92 */     serviceCreds
/*  93 */       .allPrincs = paramSubject.getPrincipals(KerberosPrincipal.class);
/*     */ 
/*     */     
/*  96 */     for (KerberosKey kerberosKey : SubjectComber.<KerberosKey>findMany(paramSubject, paramString, null, KerberosKey.class))
/*     */     {
/*  98 */       serviceCreds.allPrincs.add(kerberosKey.getPrincipal());
/*     */     }
/*     */     
/* 101 */     if (paramString != null) {
/* 102 */       serviceCreds.kp = new KerberosPrincipal(paramString);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 107 */     else if (serviceCreds.allPrincs.size() == 1) {
/* 108 */       boolean bool = false;
/* 109 */       for (KeyTab keyTab : SubjectComber.<KeyTab>findMany(paramSubject, null, null, KeyTab.class)) {
/*     */         
/* 111 */         if (!keyTab.isBound()) {
/* 112 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 116 */       if (!bool) {
/* 117 */         serviceCreds.kp = serviceCreds.allPrincs.iterator().next();
/* 118 */         paramString = serviceCreds.kp.getName();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 123 */     serviceCreds.ktabs = SubjectComber.findMany(paramSubject, paramString, null, KeyTab.class);
/*     */     
/* 125 */     serviceCreds.kk = SubjectComber.findMany(paramSubject, paramString, null, KerberosKey.class);
/*     */     
/* 127 */     serviceCreds.tgt = SubjectComber.<KerberosTicket>find(paramSubject, null, paramString, KerberosTicket.class);
/*     */     
/* 129 */     if (serviceCreds.ktabs.isEmpty() && serviceCreds.kk.isEmpty() && serviceCreds.tgt == null) {
/* 130 */       return null;
/*     */     }
/*     */     
/* 133 */     serviceCreds.destroyed = false;
/*     */     
/* 135 */     return serviceCreds;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 140 */     if (this.destroyed) {
/* 141 */       throw new IllegalStateException("This object is destroyed");
/*     */     }
/* 143 */     return (this.kp == null) ? null : this.kp.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosKey[] getKKeys() {
/* 153 */     if (this.destroyed) {
/* 154 */       throw new IllegalStateException("This object is destroyed");
/*     */     }
/* 156 */     KerberosPrincipal kerberosPrincipal = this.kp;
/* 157 */     if (kerberosPrincipal == null && !this.allPrincs.isEmpty()) {
/* 158 */       kerberosPrincipal = this.allPrincs.iterator().next();
/*     */     }
/* 160 */     if (kerberosPrincipal == null) {
/* 161 */       for (KeyTab keyTab : this.ktabs) {
/*     */ 
/*     */         
/* 164 */         PrincipalName principalName = Krb5Util.snapshotFromJavaxKeyTab(keyTab).getOneName();
/* 165 */         if (principalName != null) {
/* 166 */           kerberosPrincipal = new KerberosPrincipal(principalName.getName());
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 171 */     if (kerberosPrincipal != null) {
/* 172 */       return getKKeys(kerberosPrincipal);
/*     */     }
/* 174 */     return new KerberosKey[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosKey[] getKKeys(KerberosPrincipal paramKerberosPrincipal) {
/* 184 */     if (this.destroyed) {
/* 185 */       throw new IllegalStateException("This object is destroyed");
/*     */     }
/* 187 */     ArrayList<KerberosKey> arrayList = new ArrayList();
/* 188 */     if (this.kp != null && !paramKerberosPrincipal.equals(this.kp)) {
/* 189 */       return new KerberosKey[0];
/*     */     }
/* 191 */     for (KerberosKey kerberosKey : this.kk) {
/* 192 */       if (kerberosKey.getPrincipal().equals(paramKerberosPrincipal)) {
/* 193 */         arrayList.add(kerberosKey);
/*     */       }
/*     */     } 
/* 196 */     for (KeyTab keyTab : this.ktabs) {
/* 197 */       if (keyTab.getPrincipal() == null && keyTab.isBound())
/*     */       {
/*     */         
/* 200 */         if (!this.allPrincs.contains(paramKerberosPrincipal)) {
/*     */           continue;
/*     */         }
/*     */       }
/* 204 */       for (KerberosKey kerberosKey : keyTab.getKeys(paramKerberosPrincipal)) {
/* 205 */         arrayList.add(kerberosKey);
/*     */       }
/*     */     } 
/* 208 */     return arrayList.<KerberosKey>toArray(new KerberosKey[arrayList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionKey[] getEKeys(PrincipalName paramPrincipalName) {
/* 217 */     if (this.destroyed) {
/* 218 */       throw new IllegalStateException("This object is destroyed");
/*     */     }
/* 220 */     KerberosKey[] arrayOfKerberosKey = getKKeys(new KerberosPrincipal(paramPrincipalName.getName()));
/* 221 */     if (arrayOfKerberosKey.length == 0)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 228 */       arrayOfKerberosKey = getKKeys();
/*     */     }
/* 230 */     EncryptionKey[] arrayOfEncryptionKey = new EncryptionKey[arrayOfKerberosKey.length];
/* 231 */     for (byte b = 0; b < arrayOfEncryptionKey.length; b++) {
/* 232 */       arrayOfEncryptionKey[b] = new EncryptionKey(arrayOfKerberosKey[b]
/* 233 */           .getEncoded(), arrayOfKerberosKey[b].getKeyType(), new Integer(arrayOfKerberosKey[b]
/* 234 */             .getVersionNumber()));
/*     */     }
/* 236 */     return arrayOfEncryptionKey;
/*     */   }
/*     */   
/*     */   public Credentials getInitCred() {
/* 240 */     if (this.destroyed) {
/* 241 */       throw new IllegalStateException("This object is destroyed");
/*     */     }
/* 243 */     if (this.tgt == null) {
/* 244 */       return null;
/*     */     }
/*     */     try {
/* 247 */       return Krb5Util.ticketToCreds(this.tgt);
/* 248 */     } catch (KrbException|java.io.IOException krbException) {
/* 249 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 256 */     this.destroyed = true;
/* 257 */     this.kp = null;
/* 258 */     this.ktabs.clear();
/* 259 */     this.kk.clear();
/* 260 */     this.tgt = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/ServiceCreds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */