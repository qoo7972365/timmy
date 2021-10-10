/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import sun.security.util.BitArray;
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
/*     */ public class NetscapeCertTypeExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.NetscapeCertType";
/*     */   public static final String NAME = "NetscapeCertType";
/*     */   public static final String SSL_CLIENT = "ssl_client";
/*     */   public static final String SSL_SERVER = "ssl_server";
/*     */   public static final String S_MIME = "s_mime";
/*     */   public static final String OBJECT_SIGNING = "object_signing";
/*     */   public static final String SSL_CA = "ssl_ca";
/*     */   public static final String S_MIME_CA = "s_mime_ca";
/*     */   public static final String OBJECT_SIGNING_CA = "object_signing_ca";
/*  72 */   private static final int[] CertType_data = new int[] { 2, 16, 840, 1, 113730, 1, 1 };
/*     */   
/*     */   public static ObjectIdentifier NetscapeCertType_Id;
/*     */   
/*     */   private boolean[] bitString;
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  81 */       NetscapeCertType_Id = new ObjectIdentifier(CertType_data);
/*  82 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MapEntry
/*     */   {
/*     */     String mName;
/*     */     
/*     */     int mPosition;
/*     */ 
/*     */     
/*     */     MapEntry(String param1String, int param1Int) {
/*  94 */       this.mName = param1String;
/*  95 */       this.mPosition = param1Int;
/*     */     }
/*     */   }
/*     */   
/*  99 */   private static MapEntry[] mMapData = new MapEntry[] { new MapEntry("ssl_client", 0), new MapEntry("ssl_server", 1), new MapEntry("s_mime", 2), new MapEntry("object_signing", 3), new MapEntry("ssl_ca", 5), new MapEntry("s_mime_ca", 6), new MapEntry("object_signing_ca", 7) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   private static final Vector<String> mAttributeNames = new Vector<>();
/*     */   static {
/* 112 */     for (MapEntry mapEntry : mMapData) {
/* 113 */       mAttributeNames.add(mapEntry.mName);
/*     */     }
/*     */   }
/*     */   
/*     */   private static int getPosition(String paramString) throws IOException {
/* 118 */     for (byte b = 0; b < mMapData.length; b++) {
/* 119 */       if (paramString.equalsIgnoreCase((mMapData[b]).mName))
/* 120 */         return (mMapData[b]).mPosition; 
/*     */     } 
/* 122 */     throw new IOException("Attribute name [" + paramString + "] not recognized by CertAttrSet:NetscapeCertType.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/* 128 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 129 */     derOutputStream.putTruncatedUnalignedBitString(new BitArray(this.bitString));
/* 130 */     this.extensionValue = derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSet(int paramInt) {
/* 139 */     return (paramInt < this.bitString.length && this.bitString[paramInt]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void set(int paramInt, boolean paramBoolean) {
/* 148 */     if (paramInt >= this.bitString.length) {
/* 149 */       boolean[] arrayOfBoolean = new boolean[paramInt + 1];
/* 150 */       System.arraycopy(this.bitString, 0, arrayOfBoolean, 0, this.bitString.length);
/* 151 */       this.bitString = arrayOfBoolean;
/*     */     } 
/* 153 */     this.bitString[paramInt] = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NetscapeCertTypeExtension(byte[] paramArrayOfbyte) throws IOException {
/* 163 */     this
/* 164 */       .bitString = (new BitArray(paramArrayOfbyte.length * 8, paramArrayOfbyte)).toBooleanArray();
/* 165 */     this.extensionId = NetscapeCertType_Id;
/* 166 */     this.critical = true;
/* 167 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NetscapeCertTypeExtension(boolean[] paramArrayOfboolean) throws IOException {
/* 177 */     this.bitString = paramArrayOfboolean;
/* 178 */     this.extensionId = NetscapeCertType_Id;
/* 179 */     this.critical = true;
/* 180 */     encodeThis();
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
/*     */   public NetscapeCertTypeExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 193 */     this.extensionId = NetscapeCertType_Id;
/* 194 */     this.critical = paramBoolean.booleanValue();
/* 195 */     this.extensionValue = (byte[])paramObject;
/* 196 */     DerValue derValue = new DerValue(this.extensionValue);
/* 197 */     this.bitString = derValue.getUnalignedBitString().toBooleanArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NetscapeCertTypeExtension() {
/* 204 */     this.extensionId = NetscapeCertType_Id;
/* 205 */     this.critical = true;
/* 206 */     this.bitString = new boolean[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 213 */     if (!(paramObject instanceof Boolean)) {
/* 214 */       throw new IOException("Attribute must be of type Boolean.");
/*     */     }
/* 216 */     boolean bool = ((Boolean)paramObject).booleanValue();
/* 217 */     set(getPosition(paramString), bool);
/* 218 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean get(String paramString) throws IOException {
/* 225 */     return Boolean.valueOf(isSet(getPosition(paramString)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 232 */     set(getPosition(paramString), false);
/* 233 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 240 */     StringBuilder stringBuilder = new StringBuilder();
/* 241 */     stringBuilder.append(super.toString());
/* 242 */     stringBuilder.append("NetscapeCertType [\n");
/*     */     
/* 244 */     if (isSet(0)) {
/* 245 */       stringBuilder.append("   SSL client\n");
/*     */     }
/* 247 */     if (isSet(1)) {
/* 248 */       stringBuilder.append("   SSL server\n");
/*     */     }
/* 250 */     if (isSet(2)) {
/* 251 */       stringBuilder.append("   S/MIME\n");
/*     */     }
/* 253 */     if (isSet(3)) {
/* 254 */       stringBuilder.append("   Object Signing\n");
/*     */     }
/* 256 */     if (isSet(5)) {
/* 257 */       stringBuilder.append("   SSL CA\n");
/*     */     }
/* 259 */     if (isSet(6)) {
/* 260 */       stringBuilder.append("   S/MIME CA\n");
/*     */     }
/* 262 */     if (isSet(7)) {
/* 263 */       stringBuilder.append("   Object Signing CA");
/*     */     }
/*     */     
/* 266 */     stringBuilder.append("]\n");
/* 267 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 277 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     
/* 279 */     if (this.extensionValue == null) {
/* 280 */       this.extensionId = NetscapeCertType_Id;
/* 281 */       this.critical = true;
/* 282 */       encodeThis();
/*     */     } 
/* 284 */     encode(derOutputStream);
/* 285 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 293 */     return mAttributeNames.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 300 */     return "NetscapeCertType";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] getKeyUsageMappedBits() {
/* 310 */     KeyUsageExtension keyUsageExtension = new KeyUsageExtension();
/* 311 */     Boolean bool = Boolean.TRUE;
/*     */     
/*     */     try {
/* 314 */       if (isSet(getPosition("ssl_client")) || 
/* 315 */         isSet(getPosition("s_mime")) || 
/* 316 */         isSet(getPosition("object_signing"))) {
/* 317 */         keyUsageExtension.set("digital_signature", bool);
/*     */       }
/* 319 */       if (isSet(getPosition("ssl_server"))) {
/* 320 */         keyUsageExtension.set("key_encipherment", bool);
/*     */       }
/* 322 */       if (isSet(getPosition("ssl_ca")) || 
/* 323 */         isSet(getPosition("s_mime_ca")) || 
/* 324 */         isSet(getPosition("object_signing_ca")))
/* 325 */         keyUsageExtension.set("key_certsign", bool); 
/* 326 */     } catch (IOException iOException) {}
/* 327 */     return keyUsageExtension.getBits();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/NetscapeCertTypeExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */