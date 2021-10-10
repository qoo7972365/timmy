/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.util.KerberosString;
/*     */ import sun.security.util.DerOutputStream;
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
/*     */ public class PAForUserEnc
/*     */ {
/*     */   public final PrincipalName name;
/*     */   private final EncryptionKey key;
/*     */   public static final String AUTH_PACKAGE = "Kerberos";
/*     */   
/*     */   public PAForUserEnc(PrincipalName paramPrincipalName, EncryptionKey paramEncryptionKey) {
/*  62 */     this.name = paramPrincipalName;
/*  63 */     this.key = paramEncryptionKey;
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
/*     */   public PAForUserEnc(DerValue paramDerValue, EncryptionKey paramEncryptionKey) throws Asn1Exception, KrbException, IOException {
/*  76 */     DerValue derValue = null;
/*  77 */     this.key = paramEncryptionKey;
/*     */     
/*  79 */     if (paramDerValue.getTag() != 48) {
/*  80 */       throw new Asn1Exception(906);
/*     */     }
/*     */ 
/*     */     
/*  84 */     PrincipalName principalName = null;
/*  85 */     derValue = paramDerValue.getData().getDerValue();
/*  86 */     if ((derValue.getTag() & 0x1F) == 0) {
/*     */       try {
/*  88 */         principalName = new PrincipalName(derValue.getData().getDerValue(), new Realm("PLACEHOLDER"));
/*     */       }
/*  90 */       catch (RealmException realmException) {}
/*     */     }
/*     */     else {
/*     */       
/*  94 */       throw new Asn1Exception(906);
/*     */     } 
/*     */     
/*  97 */     derValue = paramDerValue.getData().getDerValue();
/*  98 */     if ((derValue.getTag() & 0x1F) == 1) {
/*     */       try {
/* 100 */         Realm realm = new Realm(derValue.getData().getDerValue());
/* 101 */         this
/* 102 */           .name = new PrincipalName(principalName.getNameType(), principalName.getNameStrings(), realm);
/* 103 */       } catch (RealmException realmException) {
/* 104 */         throw new IOException(realmException);
/*     */       } 
/*     */     } else {
/* 107 */       throw new Asn1Exception(906);
/*     */     } 
/*     */     
/* 110 */     derValue = paramDerValue.getData().getDerValue();
/* 111 */     if ((derValue.getTag() & 0x1F) == 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       derValue = paramDerValue.getData().getDerValue();
/* 118 */       if ((derValue.getTag() & 0x1F) == 3) {
/* 119 */         String str = (new KerberosString(derValue.getData().getDerValue())).toString();
/* 120 */         if (!str.equalsIgnoreCase("Kerberos")) {
/* 121 */           throw new IOException("Incorrect auth-package");
/*     */         }
/*     */       } else {
/* 124 */         throw new Asn1Exception(906);
/*     */       } 
/* 126 */       if (paramDerValue.getData().available() > 0)
/* 127 */         throw new Asn1Exception(906); 
/*     */       return;
/*     */     } 
/*     */     throw new Asn1Exception(906); } public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 131 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 132 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), this.name.asn1Encode());
/* 133 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), this.name.getRealm().asn1Encode());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 139 */       Checksum checksum = new Checksum(-138, getS4UByteArray(), this.key, 17);
/*     */ 
/*     */       
/* 142 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), checksum.asn1Encode());
/* 143 */     } catch (KrbException krbException) {
/* 144 */       throw new IOException(krbException);
/*     */     } 
/*     */     
/* 147 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 148 */     derOutputStream2.putDerValue((new KerberosString("Kerberos")).toDerValue());
/* 149 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)3), derOutputStream2);
/*     */     
/* 151 */     derOutputStream2 = new DerOutputStream();
/* 152 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 153 */     return derOutputStream2.toByteArray();
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
/*     */   public byte[] getS4UByteArray() {
/*     */     try {
/* 168 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 169 */       byteArrayOutputStream.write(new byte[4]);
/* 170 */       for (String str : this.name.getNameStrings()) {
/* 171 */         byteArrayOutputStream.write(str.getBytes("UTF-8"));
/*     */       }
/* 173 */       byteArrayOutputStream.write(this.name.getRealm().toString().getBytes("UTF-8"));
/* 174 */       byteArrayOutputStream.write("Kerberos".getBytes("UTF-8"));
/* 175 */       byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
/* 176 */       int i = this.name.getNameType();
/* 177 */       arrayOfByte[0] = (byte)(i & 0xFF);
/* 178 */       arrayOfByte[1] = (byte)(i >> 8 & 0xFF);
/* 179 */       arrayOfByte[2] = (byte)(i >> 16 & 0xFF);
/* 180 */       arrayOfByte[3] = (byte)(i >> 24 & 0xFF);
/* 181 */       return arrayOfByte;
/* 182 */     } catch (IOException iOException) {
/*     */       
/* 184 */       throw new AssertionError("Cannot write ByteArrayOutputStream", iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 189 */     return "PA-FOR-USER: " + this.name;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/PAForUserEnc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */