/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.Extension;
/*     */ import java.util.Arrays;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ public class Extension
/*     */   implements Extension
/*     */ {
/*  63 */   protected ObjectIdentifier extensionId = null;
/*     */   protected boolean critical = false;
/*  65 */   protected byte[] extensionValue = null;
/*     */ 
/*     */   
/*     */   private static final int hashMagic = 31;
/*     */ 
/*     */ 
/*     */   
/*     */   public Extension() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Extension(DerValue paramDerValue) throws IOException {
/*  77 */     DerInputStream derInputStream = paramDerValue.toDerInputStream();
/*     */ 
/*     */     
/*  80 */     this.extensionId = derInputStream.getOID();
/*     */ 
/*     */     
/*  83 */     DerValue derValue = derInputStream.getDerValue();
/*  84 */     if (derValue.tag == 1) {
/*  85 */       this.critical = derValue.getBoolean();
/*     */ 
/*     */       
/*  88 */       derValue = derInputStream.getDerValue();
/*  89 */       this.extensionValue = derValue.getOctetString();
/*     */     } else {
/*  91 */       this.critical = false;
/*  92 */       this.extensionValue = derValue.getOctetString();
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
/*     */ 
/*     */   
/*     */   public Extension(ObjectIdentifier paramObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfbyte) throws IOException {
/* 106 */     this.extensionId = paramObjectIdentifier;
/* 107 */     this.critical = paramBoolean;
/*     */ 
/*     */     
/* 110 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/* 111 */     this.extensionValue = derValue.getOctetString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Extension(Extension paramExtension) {
/* 121 */     this.extensionId = paramExtension.extensionId;
/* 122 */     this.critical = paramExtension.critical;
/* 123 */     this.extensionValue = paramExtension.extensionValue;
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
/*     */   public static Extension newExtension(ObjectIdentifier paramObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfbyte) throws IOException {
/* 137 */     Extension extension = new Extension();
/* 138 */     extension.extensionId = paramObjectIdentifier;
/* 139 */     extension.critical = paramBoolean;
/* 140 */     extension.extensionValue = paramArrayOfbyte;
/* 141 */     return extension;
/*     */   }
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 145 */     if (paramOutputStream == null) {
/* 146 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 149 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 150 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 152 */     derOutputStream1.putOID(this.extensionId);
/* 153 */     if (this.critical) {
/* 154 */       derOutputStream1.putBoolean(this.critical);
/*     */     }
/* 156 */     derOutputStream1.putOctetString(this.extensionValue);
/*     */     
/* 158 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 159 */     paramOutputStream.write(derOutputStream2.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 170 */     if (this.extensionId == null)
/* 171 */       throw new IOException("Null OID to encode for the extension!"); 
/* 172 */     if (this.extensionValue == null) {
/* 173 */       throw new IOException("No value to encode for the extension!");
/*     */     }
/* 175 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     
/* 177 */     derOutputStream.putOID(this.extensionId);
/* 178 */     if (this.critical)
/* 179 */       derOutputStream.putBoolean(this.critical); 
/* 180 */     derOutputStream.putOctetString(this.extensionValue);
/*     */     
/* 182 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCritical() {
/* 189 */     return this.critical;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdentifier getExtensionId() {
/* 196 */     return this.extensionId;
/*     */   }
/*     */   
/*     */   public byte[] getValue() {
/* 200 */     return (byte[])this.extensionValue.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getExtensionValue() {
/* 211 */     return this.extensionValue;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 215 */     return this.extensionId.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 222 */     String str = "ObjectId: " + this.extensionId.toString();
/* 223 */     if (this.critical) {
/* 224 */       str = str + " Criticality=true\n";
/*     */     } else {
/* 226 */       str = str + " Criticality=false\n";
/*     */     } 
/* 228 */     return str;
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
/*     */   public int hashCode() {
/* 240 */     int i = 0;
/* 241 */     if (this.extensionValue != null) {
/* 242 */       byte[] arrayOfByte = this.extensionValue;
/* 243 */       int j = arrayOfByte.length;
/* 244 */       while (j > 0)
/* 245 */         i += j * arrayOfByte[--j]; 
/*     */     } 
/* 247 */     i = i * 31 + this.extensionId.hashCode();
/* 248 */     i = i * 31 + (this.critical ? 1231 : 1237);
/* 249 */     return i;
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
/*     */   public boolean equals(Object paramObject) {
/* 265 */     if (this == paramObject)
/* 266 */       return true; 
/* 267 */     if (!(paramObject instanceof Extension))
/* 268 */       return false; 
/* 269 */     Extension extension = (Extension)paramObject;
/* 270 */     if (this.critical != extension.critical)
/* 271 */       return false; 
/* 272 */     if (!this.extensionId.equals(extension.extensionId))
/* 273 */       return false; 
/* 274 */     return Arrays.equals(this.extensionValue, extension.extensionValue);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/Extension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */