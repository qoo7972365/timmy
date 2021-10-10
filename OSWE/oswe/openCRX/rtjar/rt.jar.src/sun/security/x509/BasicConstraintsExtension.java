/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Enumeration;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicConstraintsExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.BasicConstraints";
/*     */   public static final String NAME = "BasicConstraints";
/*     */   public static final String IS_CA = "is_ca";
/*     */   public static final String PATH_LEN = "path_len";
/*     */   private boolean ca = false;
/*  69 */   private int pathLen = -1;
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  73 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*  74 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/*  76 */     if (this.ca) {
/*  77 */       derOutputStream2.putBoolean(this.ca);
/*     */       
/*  79 */       if (this.pathLen >= 0) {
/*  80 */         derOutputStream2.putInteger(this.pathLen);
/*     */       }
/*     */     } 
/*  83 */     derOutputStream1.write((byte)48, derOutputStream2);
/*  84 */     this.extensionValue = derOutputStream1.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicConstraintsExtension(boolean paramBoolean, int paramInt) throws IOException {
/*  95 */     this(Boolean.valueOf(paramBoolean), paramBoolean, paramInt);
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
/*     */   public BasicConstraintsExtension(Boolean paramBoolean, boolean paramBoolean1, int paramInt) throws IOException {
/* 107 */     this.ca = paramBoolean1;
/* 108 */     this.pathLen = paramInt;
/* 109 */     this.extensionId = PKIXExtensions.BasicConstraints_Id;
/* 110 */     this.critical = paramBoolean.booleanValue();
/* 111 */     encodeThis();
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
/*     */   public BasicConstraintsExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 125 */     this.extensionId = PKIXExtensions.BasicConstraints_Id;
/* 126 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 128 */     this.extensionValue = (byte[])paramObject;
/* 129 */     DerValue derValue1 = new DerValue(this.extensionValue);
/* 130 */     if (derValue1.tag != 48) {
/* 131 */       throw new IOException("Invalid encoding of BasicConstraints");
/*     */     }
/*     */     
/* 134 */     if (derValue1.data == null || derValue1.data.available() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 138 */     DerValue derValue2 = derValue1.data.getDerValue();
/* 139 */     if (derValue2.tag != 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 144 */     this.ca = derValue2.getBoolean();
/* 145 */     if (derValue1.data.available() == 0) {
/*     */ 
/*     */ 
/*     */       
/* 149 */       this.pathLen = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/* 153 */     derValue2 = derValue1.data.getDerValue();
/* 154 */     if (derValue2.tag != 2) {
/* 155 */       throw new IOException("Invalid encoding of BasicConstraints");
/*     */     }
/* 157 */     this.pathLen = derValue2.getInteger();
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
/*     */   public String toString() {
/* 174 */     String str = super.toString() + "BasicConstraints:[\n";
/*     */     
/* 176 */     str = str + (this.ca ? "  CA:true" : "  CA:false") + "\n";
/* 177 */     if (this.pathLen >= 0) {
/* 178 */       str = str + "  PathLen:" + this.pathLen + "\n";
/*     */     } else {
/* 180 */       str = str + "  PathLen: undefined\n";
/*     */     } 
/* 182 */     return str + "]\n";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 191 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 192 */     if (this.extensionValue == null) {
/* 193 */       this.extensionId = PKIXExtensions.BasicConstraints_Id;
/* 194 */       if (this.ca) {
/* 195 */         this.critical = true;
/*     */       } else {
/* 197 */         this.critical = false;
/*     */       } 
/* 199 */       encodeThis();
/*     */     } 
/* 201 */     encode(derOutputStream);
/*     */     
/* 203 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 210 */     if (paramString.equalsIgnoreCase("is_ca")) {
/* 211 */       if (!(paramObject instanceof Boolean)) {
/* 212 */         throw new IOException("Attribute value should be of type Boolean.");
/*     */       }
/* 214 */       this.ca = ((Boolean)paramObject).booleanValue();
/* 215 */     } else if (paramString.equalsIgnoreCase("path_len")) {
/* 216 */       if (!(paramObject instanceof Integer)) {
/* 217 */         throw new IOException("Attribute value should be of type Integer.");
/*     */       }
/* 219 */       this.pathLen = ((Integer)paramObject).intValue();
/*     */     } else {
/* 221 */       throw new IOException("Attribute name not recognized by CertAttrSet:BasicConstraints.");
/*     */     } 
/*     */     
/* 224 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String paramString) throws IOException {
/* 231 */     if (paramString.equalsIgnoreCase("is_ca"))
/* 232 */       return Boolean.valueOf(this.ca); 
/* 233 */     if (paramString.equalsIgnoreCase("path_len")) {
/* 234 */       return Integer.valueOf(this.pathLen);
/*     */     }
/* 236 */     throw new IOException("Attribute name not recognized by CertAttrSet:BasicConstraints.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 245 */     if (paramString.equalsIgnoreCase("is_ca")) {
/* 246 */       this.ca = false;
/* 247 */     } else if (paramString.equalsIgnoreCase("path_len")) {
/* 248 */       this.pathLen = -1;
/*     */     } else {
/* 250 */       throw new IOException("Attribute name not recognized by CertAttrSet:BasicConstraints.");
/*     */     } 
/*     */     
/* 253 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 261 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 262 */     attributeNameEnumeration.addElement("is_ca");
/* 263 */     attributeNameEnumeration.addElement("path_len");
/*     */     
/* 265 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 272 */     return "BasicConstraints";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/BasicConstraintsExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */