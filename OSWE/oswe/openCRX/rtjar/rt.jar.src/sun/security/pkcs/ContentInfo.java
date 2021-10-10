/*     */ package sun.security.pkcs;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public class ContentInfo
/*     */ {
/*  41 */   private static int[] pkcs7 = new int[] { 1, 2, 840, 113549, 1, 7 };
/*  42 */   private static int[] data = new int[] { 1, 2, 840, 113549, 1, 7, 1 };
/*  43 */   private static int[] sdata = new int[] { 1, 2, 840, 113549, 1, 7, 2 };
/*  44 */   private static int[] edata = new int[] { 1, 2, 840, 113549, 1, 7, 3 };
/*  45 */   private static int[] sedata = new int[] { 1, 2, 840, 113549, 1, 7, 4 };
/*  46 */   private static int[] ddata = new int[] { 1, 2, 840, 113549, 1, 7, 5 };
/*  47 */   private static int[] crdata = new int[] { 1, 2, 840, 113549, 1, 7, 6 };
/*  48 */   private static int[] nsdata = new int[] { 2, 16, 840, 1, 113730, 2, 5 };
/*     */   
/*  50 */   private static int[] tstInfo = new int[] { 1, 2, 840, 113549, 1, 9, 16, 1, 4 };
/*     */   
/*  52 */   private static final int[] OLD_SDATA = new int[] { 1, 2, 840, 1113549, 1, 7, 2 };
/*  53 */   private static final int[] OLD_DATA = new int[] { 1, 2, 840, 1113549, 1, 7, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static ObjectIdentifier PKCS7_OID = ObjectIdentifier.newInternal(pkcs7);
/*  68 */   public static ObjectIdentifier DATA_OID = ObjectIdentifier.newInternal(data);
/*  69 */   public static ObjectIdentifier SIGNED_DATA_OID = ObjectIdentifier.newInternal(sdata);
/*  70 */   public static ObjectIdentifier ENVELOPED_DATA_OID = ObjectIdentifier.newInternal(edata);
/*  71 */   public static ObjectIdentifier SIGNED_AND_ENVELOPED_DATA_OID = ObjectIdentifier.newInternal(sedata);
/*  72 */   public static ObjectIdentifier DIGESTED_DATA_OID = ObjectIdentifier.newInternal(ddata);
/*  73 */   public static ObjectIdentifier ENCRYPTED_DATA_OID = ObjectIdentifier.newInternal(crdata);
/*  74 */   public static ObjectIdentifier OLD_SIGNED_DATA_OID = ObjectIdentifier.newInternal(OLD_SDATA);
/*  75 */   public static ObjectIdentifier OLD_DATA_OID = ObjectIdentifier.newInternal(OLD_DATA);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static ObjectIdentifier NETSCAPE_CERT_SEQUENCE_OID = ObjectIdentifier.newInternal(nsdata);
/*  83 */   public static ObjectIdentifier TIMESTAMP_TOKEN_INFO_OID = ObjectIdentifier.newInternal(tstInfo);
/*     */   
/*     */   ObjectIdentifier contentType;
/*     */   
/*     */   DerValue content;
/*     */   
/*     */   public ContentInfo(ObjectIdentifier paramObjectIdentifier, DerValue paramDerValue) {
/*  90 */     this.contentType = paramObjectIdentifier;
/*  91 */     this.content = paramDerValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentInfo(byte[] paramArrayOfbyte) {
/*  98 */     DerValue derValue = new DerValue((byte)4, paramArrayOfbyte);
/*  99 */     this.contentType = DATA_OID;
/* 100 */     this.content = derValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentInfo(DerInputStream paramDerInputStream) throws IOException, ParsingException {
/* 109 */     this(paramDerInputStream, false);
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
/*     */   public ContentInfo(DerInputStream paramDerInputStream, boolean paramBoolean) throws IOException, ParsingException {
/* 132 */     DerValue[] arrayOfDerValue = paramDerInputStream.getSequence(2);
/*     */ 
/*     */     
/* 135 */     DerValue derValue = arrayOfDerValue[0];
/* 136 */     DerInputStream derInputStream = new DerInputStream(derValue.toByteArray());
/* 137 */     this.contentType = derInputStream.getOID();
/*     */     
/* 139 */     if (paramBoolean) {
/*     */       
/* 141 */       this.content = arrayOfDerValue[1];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 147 */     else if (arrayOfDerValue.length > 1) {
/* 148 */       DerValue derValue1 = arrayOfDerValue[1];
/*     */       
/* 150 */       DerInputStream derInputStream1 = new DerInputStream(derValue1.toByteArray());
/* 151 */       DerValue[] arrayOfDerValue1 = derInputStream1.getSet(1, true);
/* 152 */       this.content = arrayOfDerValue1[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DerValue getContent() {
/* 158 */     return this.content;
/*     */   }
/*     */   
/*     */   public ObjectIdentifier getContentType() {
/* 162 */     return this.contentType;
/*     */   }
/*     */   
/*     */   public byte[] getData() throws IOException {
/* 166 */     if (this.contentType.equals(DATA_OID) || this.contentType
/* 167 */       .equals(OLD_DATA_OID) || this.contentType
/* 168 */       .equals(TIMESTAMP_TOKEN_INFO_OID)) {
/* 169 */       if (this.content == null) {
/* 170 */         return null;
/*     */       }
/* 172 */       return this.content.getOctetString();
/*     */     } 
/* 174 */     throw new IOException("content type is not DATA: " + this.contentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 181 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 182 */     derOutputStream.putOID(this.contentType);
/*     */ 
/*     */     
/* 185 */     if (this.content != null) {
/* 186 */       DerValue derValue = null;
/* 187 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 188 */       this.content.encode(derOutputStream1);
/*     */ 
/*     */ 
/*     */       
/* 192 */       derValue = new DerValue((byte)-96, derOutputStream1.toByteArray());
/* 193 */       derOutputStream.putDerValue(derValue);
/*     */     } 
/*     */     
/* 196 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getContentBytes() throws IOException {
/* 204 */     if (this.content == null) {
/* 205 */       return null;
/*     */     }
/* 207 */     DerInputStream derInputStream = new DerInputStream(this.content.toByteArray());
/* 208 */     return derInputStream.getOctetString();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 212 */     String str = "";
/*     */     
/* 214 */     str = str + "Content Info Sequence\n\tContent type: " + this.contentType + "\n";
/* 215 */     str = str + "\tContent: " + this.content;
/* 216 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs/ContentInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */