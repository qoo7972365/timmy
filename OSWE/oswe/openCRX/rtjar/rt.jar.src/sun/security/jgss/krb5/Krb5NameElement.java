/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.Provider;
/*     */ import java.util.Locale;
/*     */ import javax.security.auth.kerberos.ServicePermission;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Krb5NameElement
/*     */   implements GSSNameSpi
/*     */ {
/*     */   private PrincipalName krb5PrincipalName;
/*  51 */   private String gssNameStr = null;
/*  52 */   private Oid gssNameType = null;
/*     */ 
/*     */   
/*  55 */   private static String CHAR_ENCODING = "UTF-8";
/*     */ 
/*     */ 
/*     */   
/*     */   private Krb5NameElement(PrincipalName paramPrincipalName, String paramString, Oid paramOid) {
/*  60 */     this.krb5PrincipalName = paramPrincipalName;
/*  61 */     this.gssNameStr = paramString;
/*  62 */     this.gssNameType = paramOid;
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
/*     */   static Krb5NameElement getInstance(String paramString, Oid paramOid) throws GSSException {
/*     */     PrincipalName principalName;
/*  79 */     if (paramOid == null) {
/*  80 */       paramOid = Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL;
/*     */     }
/*  82 */     else if (!paramOid.equals(GSSName.NT_USER_NAME) && 
/*  83 */       !paramOid.equals(GSSName.NT_HOSTBASED_SERVICE) && 
/*  84 */       !paramOid.equals(Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL) && 
/*  85 */       !paramOid.equals(GSSName.NT_EXPORT_NAME)) {
/*  86 */       throw new GSSException(4, -1, paramOid
/*  87 */           .toString() + " is an unsupported nametype");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  93 */       if (paramOid.equals(GSSName.NT_EXPORT_NAME) || paramOid
/*  94 */         .equals(Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL)) {
/*  95 */         principalName = new PrincipalName(paramString, 1);
/*     */       }
/*     */       else {
/*     */         
/*  99 */         String[] arrayOfString = getComponents(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 113 */         if (paramOid.equals(GSSName.NT_USER_NAME)) {
/* 114 */           principalName = new PrincipalName(paramString, 1);
/*     */         } else {
/*     */           
/* 117 */           String str1 = null;
/* 118 */           String str2 = arrayOfString[0];
/* 119 */           if (arrayOfString.length >= 2) {
/* 120 */             str1 = arrayOfString[1];
/*     */           }
/* 122 */           String str3 = getHostBasedInstance(str2, str1);
/* 123 */           principalName = new PrincipalName(str3, 3);
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 128 */     } catch (KrbException krbException) {
/* 129 */       throw new GSSException(3, -1, krbException.getMessage());
/*     */     } 
/*     */     
/* 132 */     if (principalName.isRealmDeduced() && !Realm.AUTODEDUCEREALM) {
/* 133 */       SecurityManager securityManager = System.getSecurityManager();
/* 134 */       if (securityManager != null) {
/*     */         try {
/* 136 */           securityManager.checkPermission(new ServicePermission("@" + principalName
/* 137 */                 .getRealmAsString(), "-"));
/* 138 */         } catch (SecurityException securityException) {
/*     */           
/* 140 */           throw new GSSException(11);
/*     */         } 
/*     */       }
/*     */     } 
/* 144 */     return new Krb5NameElement(principalName, paramString, paramOid);
/*     */   }
/*     */   
/*     */   public static Krb5NameElement getInstance(PrincipalName paramPrincipalName) {
/* 148 */     return new Krb5NameElement(paramPrincipalName, paramPrincipalName
/* 149 */         .getName(), Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
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
/*     */   private static String[] getComponents(String paramString) throws GSSException {
/*     */     String[] arrayOfString;
/* 162 */     int i = paramString.lastIndexOf('@', paramString.length());
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (i > 0 && paramString
/* 167 */       .charAt(i - 1) == '\\')
/*     */     {
/* 169 */       if (i - 2 < 0 || paramString
/* 170 */         .charAt(i - 2) != '\\') {
/* 171 */         i = -1;
/*     */       }
/*     */     }
/* 174 */     if (i > 0) {
/* 175 */       String str1 = paramString.substring(0, i);
/* 176 */       String str2 = paramString.substring(i + 1);
/* 177 */       arrayOfString = new String[] { str1, str2 };
/*     */     } else {
/* 179 */       arrayOfString = new String[] { paramString };
/*     */     } 
/*     */     
/* 182 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getHostBasedInstance(String paramString1, String paramString2) throws GSSException {
/* 189 */     StringBuffer stringBuffer = new StringBuffer(paramString1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 195 */       if (paramString2 == null) {
/* 196 */         paramString2 = InetAddress.getLocalHost().getHostName();
/*     */       }
/* 198 */     } catch (UnknownHostException unknownHostException) {}
/*     */ 
/*     */     
/* 201 */     paramString2 = paramString2.toLowerCase(Locale.ENGLISH);
/*     */     
/* 203 */     stringBuffer = stringBuffer.append('/').append(paramString2);
/* 204 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public final PrincipalName getKrb5PrincipalName() {
/* 208 */     return this.krb5PrincipalName;
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
/*     */   public boolean equals(GSSNameSpi paramGSSNameSpi) throws GSSException {
/* 223 */     if (paramGSSNameSpi == this) {
/* 224 */       return true;
/*     */     }
/* 226 */     if (paramGSSNameSpi instanceof Krb5NameElement) {
/* 227 */       Krb5NameElement krb5NameElement = (Krb5NameElement)paramGSSNameSpi;
/* 228 */       return this.krb5PrincipalName.getName().equals(krb5NameElement.krb5PrincipalName
/* 229 */           .getName());
/*     */     } 
/* 231 */     return false;
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
/*     */   public boolean equals(Object paramObject) {
/* 246 */     if (this == paramObject) {
/* 247 */       return true;
/*     */     }
/*     */     
/*     */     try {
/* 251 */       if (paramObject instanceof Krb5NameElement)
/* 252 */         return equals((Krb5NameElement)paramObject); 
/* 253 */     } catch (GSSException gSSException) {}
/*     */ 
/*     */     
/* 256 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 265 */     return 629 + this.krb5PrincipalName.getName().hashCode();
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
/*     */   public byte[] export() throws GSSException {
/* 288 */     byte[] arrayOfByte = null;
/*     */     try {
/* 290 */       arrayOfByte = this.krb5PrincipalName.getName().getBytes(CHAR_ENCODING);
/* 291 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */     
/* 294 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid getMechanism() {
/* 303 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
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
/*     */   public String toString() {
/* 315 */     return this.gssNameStr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid getGSSNameType() {
/* 323 */     return this.gssNameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid getStringNameType() {
/* 334 */     return this.gssNameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnonymousName() {
/* 341 */     return this.gssNameType.equals(GSSName.NT_ANONYMOUS);
/*     */   }
/*     */   
/*     */   public Provider getProvider() {
/* 345 */     return Krb5MechFactory.PROVIDER;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/Krb5NameElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */