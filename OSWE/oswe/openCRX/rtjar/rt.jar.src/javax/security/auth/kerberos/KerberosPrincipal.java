/*     */ package javax.security.auth.kerberos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.Principal;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KerberosPrincipal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7374788026156829911L;
/*     */   public static final int KRB_NT_UNKNOWN = 0;
/*     */   public static final int KRB_NT_PRINCIPAL = 1;
/*     */   public static final int KRB_NT_SRV_INST = 2;
/*     */   public static final int KRB_NT_SRV_HST = 3;
/*     */   public static final int KRB_NT_SRV_XHST = 4;
/*     */   public static final int KRB_NT_UID = 5;
/*     */   static final int KRB_NT_ENTERPRISE = 10;
/*     */   private transient String fullName;
/*     */   private transient String realm;
/*     */   private transient int nameType;
/*     */   
/*     */   public KerberosPrincipal(String paramString) {
/* 120 */     this(paramString, 1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosPrincipal(String paramString, int paramInt) {
/* 153 */     PrincipalName principalName = null;
/*     */ 
/*     */     
/*     */     try {
/* 157 */       principalName = new PrincipalName(paramString, paramInt);
/* 158 */     } catch (KrbException krbException) {
/* 159 */       throw new IllegalArgumentException(krbException.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (principalName.isRealmDeduced() && !Realm.AUTODEDUCEREALM) {
/* 165 */       SecurityManager securityManager = System.getSecurityManager();
/* 166 */       if (securityManager != null) {
/*     */         try {
/* 168 */           securityManager.checkPermission(new ServicePermission("@" + principalName
/* 169 */                 .getRealmAsString(), "-"));
/* 170 */         } catch (SecurityException securityException) {
/*     */           
/* 172 */           throw new SecurityException("Cannot read realm info");
/*     */         } 
/*     */       }
/*     */     } 
/* 176 */     this.nameType = paramInt;
/* 177 */     this.fullName = principalName.toString();
/* 178 */     this.realm = principalName.getRealmString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRealm() {
/* 186 */     return this.realm;
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
/*     */   public int hashCode() {
/* 199 */     return getName().hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 216 */     if (paramObject == this) {
/* 217 */       return true;
/*     */     }
/* 219 */     if (!(paramObject instanceof KerberosPrincipal)) {
/* 220 */       return false;
/*     */     }
/* 222 */     String str1 = getName();
/* 223 */     String str2 = ((KerberosPrincipal)paramObject).getName();
/* 224 */     return str1.equals(str2);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*     */     try {
/* 240 */       PrincipalName principalName = new PrincipalName(this.fullName, this.nameType);
/* 241 */       paramObjectOutputStream.writeObject(principalName.asn1Encode());
/* 242 */       paramObjectOutputStream.writeObject(principalName.getRealm().asn1Encode());
/* 243 */     } catch (Exception exception) {
/* 244 */       throw new IOException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 253 */     byte[] arrayOfByte1 = (byte[])paramObjectInputStream.readObject();
/* 254 */     byte[] arrayOfByte2 = (byte[])paramObjectInputStream.readObject();
/*     */     try {
/* 256 */       Realm realm = new Realm(new DerValue(arrayOfByte2));
/* 257 */       PrincipalName principalName = new PrincipalName(new DerValue(arrayOfByte1), realm);
/*     */       
/* 259 */       this.realm = realm.toString();
/* 260 */       this.fullName = principalName.toString();
/* 261 */       this.nameType = principalName.getNameType();
/* 262 */     } catch (Exception exception) {
/* 263 */       throw new IOException(exception);
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
/*     */   public String getName() {
/* 275 */     return this.fullName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNameType() {
/* 286 */     return this.nameType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 291 */     return getName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/kerberos/KerberosPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */