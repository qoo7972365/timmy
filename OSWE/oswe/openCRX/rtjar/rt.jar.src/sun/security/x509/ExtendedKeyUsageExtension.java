/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtendedKeyUsageExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.ExtendedKeyUsage";
/*     */   public static final String NAME = "ExtendedKeyUsage";
/*     */   public static final String USAGES = "usages";
/*  99 */   private static final Map<ObjectIdentifier, String> map = new HashMap<>();
/*     */ 
/*     */   
/* 102 */   private static final int[] anyExtendedKeyUsageOidData = new int[] { 2, 5, 29, 37, 0 };
/* 103 */   private static final int[] serverAuthOidData = new int[] { 1, 3, 6, 1, 5, 5, 7, 3, 1 };
/* 104 */   private static final int[] clientAuthOidData = new int[] { 1, 3, 6, 1, 5, 5, 7, 3, 2 };
/* 105 */   private static final int[] codeSigningOidData = new int[] { 1, 3, 6, 1, 5, 5, 7, 3, 3 };
/* 106 */   private static final int[] emailProtectionOidData = new int[] { 1, 3, 6, 1, 5, 5, 7, 3, 4 };
/* 107 */   private static final int[] ipsecEndSystemOidData = new int[] { 1, 3, 6, 1, 5, 5, 7, 3, 5 };
/* 108 */   private static final int[] ipsecTunnelOidData = new int[] { 1, 3, 6, 1, 5, 5, 7, 3, 6 };
/* 109 */   private static final int[] ipsecUserOidData = new int[] { 1, 3, 6, 1, 5, 5, 7, 3, 7 };
/* 110 */   private static final int[] timeStampingOidData = new int[] { 1, 3, 6, 1, 5, 5, 7, 3, 8 };
/* 111 */   private static final int[] OCSPSigningOidData = new int[] { 1, 3, 6, 1, 5, 5, 7, 3, 9 };
/*     */   
/*     */   static {
/* 114 */     map.put(ObjectIdentifier.newInternal(anyExtendedKeyUsageOidData), "anyExtendedKeyUsage");
/* 115 */     map.put(ObjectIdentifier.newInternal(serverAuthOidData), "serverAuth");
/* 116 */     map.put(ObjectIdentifier.newInternal(clientAuthOidData), "clientAuth");
/* 117 */     map.put(ObjectIdentifier.newInternal(codeSigningOidData), "codeSigning");
/* 118 */     map.put(ObjectIdentifier.newInternal(emailProtectionOidData), "emailProtection");
/* 119 */     map.put(ObjectIdentifier.newInternal(ipsecEndSystemOidData), "ipsecEndSystem");
/* 120 */     map.put(ObjectIdentifier.newInternal(ipsecTunnelOidData), "ipsecTunnel");
/* 121 */     map.put(ObjectIdentifier.newInternal(ipsecUserOidData), "ipsecUser");
/* 122 */     map.put(ObjectIdentifier.newInternal(timeStampingOidData), "timeStamping");
/* 123 */     map.put(ObjectIdentifier.newInternal(OCSPSigningOidData), "OCSPSigning");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector<ObjectIdentifier> keyUsages;
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/* 133 */     if (this.keyUsages == null || this.keyUsages.isEmpty()) {
/* 134 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/* 137 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 138 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 140 */     for (byte b = 0; b < this.keyUsages.size(); b++) {
/* 141 */       derOutputStream2.putOID(this.keyUsages.elementAt(b));
/*     */     }
/*     */     
/* 144 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 145 */     this.extensionValue = derOutputStream1.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedKeyUsageExtension(Vector<ObjectIdentifier> paramVector) throws IOException {
/* 156 */     this(Boolean.FALSE, paramVector);
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
/*     */   public ExtendedKeyUsageExtension(Boolean paramBoolean, Vector<ObjectIdentifier> paramVector) throws IOException {
/* 168 */     this.keyUsages = paramVector;
/* 169 */     this.extensionId = PKIXExtensions.ExtendedKeyUsage_Id;
/* 170 */     this.critical = paramBoolean.booleanValue();
/* 171 */     encodeThis();
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
/*     */   public ExtendedKeyUsageExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 184 */     this.extensionId = PKIXExtensions.ExtendedKeyUsage_Id;
/* 185 */     this.critical = paramBoolean.booleanValue();
/* 186 */     this.extensionValue = (byte[])paramObject;
/* 187 */     DerValue derValue = new DerValue(this.extensionValue);
/* 188 */     if (derValue.tag != 48) {
/* 189 */       throw new IOException("Invalid encoding for ExtendedKeyUsageExtension.");
/*     */     }
/*     */     
/* 192 */     this.keyUsages = new Vector<>();
/* 193 */     while (derValue.data.available() != 0) {
/* 194 */       DerValue derValue1 = derValue.data.getDerValue();
/* 195 */       ObjectIdentifier objectIdentifier = derValue1.getOID();
/* 196 */       this.keyUsages.addElement(objectIdentifier);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 204 */     if (this.keyUsages == null) return ""; 
/* 205 */     String str = "  ";
/* 206 */     boolean bool = true;
/* 207 */     for (ObjectIdentifier objectIdentifier : this.keyUsages) {
/* 208 */       if (!bool) {
/* 209 */         str = str + "\n  ";
/*     */       }
/*     */       
/* 212 */       String str1 = map.get(objectIdentifier);
/* 213 */       if (str1 != null) {
/* 214 */         str = str + str1;
/*     */       } else {
/* 216 */         str = str + objectIdentifier.toString();
/*     */       } 
/* 218 */       bool = false;
/*     */     } 
/* 220 */     return super.toString() + "ExtendedKeyUsages [\n" + str + "\n]\n";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 231 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 232 */     if (this.extensionValue == null) {
/* 233 */       this.extensionId = PKIXExtensions.ExtendedKeyUsage_Id;
/* 234 */       this.critical = false;
/* 235 */       encodeThis();
/*     */     } 
/* 237 */     encode(derOutputStream);
/* 238 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 246 */     if (paramString.equalsIgnoreCase("usages")) {
/* 247 */       if (!(paramObject instanceof Vector)) {
/* 248 */         throw new IOException("Attribute value should be of type Vector.");
/*     */       }
/* 250 */       this.keyUsages = (Vector<ObjectIdentifier>)paramObject;
/*     */     } else {
/* 252 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:ExtendedKeyUsageExtension.");
/*     */     } 
/*     */ 
/*     */     
/* 256 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<ObjectIdentifier> get(String paramString) throws IOException {
/* 263 */     if (paramString.equalsIgnoreCase("usages"))
/*     */     {
/* 265 */       return this.keyUsages;
/*     */     }
/* 267 */     throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:ExtendedKeyUsageExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 277 */     if (paramString.equalsIgnoreCase("usages")) {
/* 278 */       this.keyUsages = null;
/*     */     } else {
/* 280 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:ExtendedKeyUsageExtension.");
/*     */     } 
/*     */ 
/*     */     
/* 284 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 292 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 293 */     attributeNameEnumeration.addElement("usages");
/*     */     
/* 295 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 302 */     return "ExtendedKeyUsage";
/*     */   }
/*     */   
/*     */   public List<String> getExtendedKeyUsage() {
/* 306 */     ArrayList<String> arrayList = new ArrayList(this.keyUsages.size());
/* 307 */     for (ObjectIdentifier objectIdentifier : this.keyUsages) {
/* 308 */       arrayList.add(objectIdentifier.toString());
/*     */     }
/* 310 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/ExtendedKeyUsageExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */