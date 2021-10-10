/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CRLDistributionPointsExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.CRLDistributionPoints";
/*     */   public static final String NAME = "CRLDistributionPoints";
/*     */   public static final String POINTS = "points";
/*     */   private List<DistributionPoint> distributionPoints;
/*     */   private String extensionName;
/*     */   
/*     */   public CRLDistributionPointsExtension(List<DistributionPoint> paramList) throws IOException {
/* 116 */     this(false, paramList);
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
/*     */   public CRLDistributionPointsExtension(boolean paramBoolean, List<DistributionPoint> paramList) throws IOException {
/* 130 */     this(PKIXExtensions.CRLDistributionPoints_Id, paramBoolean, paramList, "CRLDistributionPoints");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CRLDistributionPointsExtension(ObjectIdentifier paramObjectIdentifier, boolean paramBoolean, List<DistributionPoint> paramList, String paramString) throws IOException {
/* 141 */     this.extensionId = paramObjectIdentifier;
/* 142 */     this.critical = paramBoolean;
/* 143 */     this.distributionPoints = paramList;
/* 144 */     encodeThis();
/* 145 */     this.extensionName = paramString;
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
/*     */   public CRLDistributionPointsExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 157 */     this(PKIXExtensions.CRLDistributionPoints_Id, paramBoolean, paramObject, "CRLDistributionPoints");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CRLDistributionPointsExtension(ObjectIdentifier paramObjectIdentifier, Boolean paramBoolean, Object paramObject, String paramString) throws IOException {
/* 167 */     this.extensionId = paramObjectIdentifier;
/* 168 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 170 */     if (!(paramObject instanceof byte[])) {
/* 171 */       throw new IOException("Illegal argument type");
/*     */     }
/*     */     
/* 174 */     this.extensionValue = (byte[])paramObject;
/* 175 */     DerValue derValue = new DerValue(this.extensionValue);
/* 176 */     if (derValue.tag != 48) {
/* 177 */       throw new IOException("Invalid encoding for " + paramString + " extension.");
/*     */     }
/*     */     
/* 180 */     this.distributionPoints = new ArrayList<>();
/* 181 */     while (derValue.data.available() != 0) {
/* 182 */       DerValue derValue1 = derValue.data.getDerValue();
/* 183 */       DistributionPoint distributionPoint = new DistributionPoint(derValue1);
/* 184 */       this.distributionPoints.add(distributionPoint);
/*     */     } 
/* 186 */     this.extensionName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 193 */     return this.extensionName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 203 */     encode(paramOutputStream, PKIXExtensions.CRLDistributionPoints_Id, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(OutputStream paramOutputStream, ObjectIdentifier paramObjectIdentifier, boolean paramBoolean) throws IOException {
/* 213 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 214 */     if (this.extensionValue == null) {
/* 215 */       this.extensionId = paramObjectIdentifier;
/* 216 */       this.critical = paramBoolean;
/* 217 */       encodeThis();
/*     */     } 
/* 219 */     encode(derOutputStream);
/* 220 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 228 */     if (paramString.equalsIgnoreCase("points")) {
/* 229 */       if (!(paramObject instanceof List)) {
/* 230 */         throw new IOException("Attribute value should be of type List.");
/*     */       }
/* 232 */       this.distributionPoints = (List<DistributionPoint>)paramObject;
/*     */     } else {
/* 234 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:" + this.extensionName + ".");
/*     */     } 
/*     */ 
/*     */     
/* 238 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<DistributionPoint> get(String paramString) throws IOException {
/* 245 */     if (paramString.equalsIgnoreCase("points")) {
/* 246 */       return this.distributionPoints;
/*     */     }
/* 248 */     throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:" + this.extensionName + ".");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 258 */     if (paramString.equalsIgnoreCase("points")) {
/* 259 */       this
/* 260 */         .distributionPoints = Collections.emptyList();
/*     */     } else {
/* 262 */       throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:" + this.extensionName + '.');
/*     */     } 
/*     */ 
/*     */     
/* 266 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 274 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 275 */     attributeNameEnumeration.addElement("points");
/* 276 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/* 281 */     if (this.distributionPoints.isEmpty()) {
/* 282 */       this.extensionValue = null;
/*     */     } else {
/* 284 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 285 */       for (DistributionPoint distributionPoint : this.distributionPoints) {
/* 286 */         distributionPoint.encode(derOutputStream1);
/*     */       }
/* 288 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/* 289 */       derOutputStream2.write((byte)48, derOutputStream1);
/* 290 */       this.extensionValue = derOutputStream2.toByteArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 298 */     return super.toString() + this.extensionName + " [\n  " + this.distributionPoints + "]\n";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/CRLDistributionPointsExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */