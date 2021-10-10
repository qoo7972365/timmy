/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
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
/*     */ public class OtherName
/*     */   implements GeneralNameInterface
/*     */ {
/*     */   private String name;
/*     */   private ObjectIdentifier oid;
/*  53 */   private byte[] nameValue = null;
/*  54 */   private GeneralNameInterface gni = null;
/*     */   
/*     */   private static final byte TAG_VALUE = 0;
/*     */   
/*  58 */   private int myhash = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OtherName(ObjectIdentifier paramObjectIdentifier, byte[] paramArrayOfbyte) throws IOException {
/*  69 */     if (paramObjectIdentifier == null || paramArrayOfbyte == null) {
/*  70 */       throw new NullPointerException("parameters may not be null");
/*     */     }
/*  72 */     this.oid = paramObjectIdentifier;
/*  73 */     this.nameValue = paramArrayOfbyte;
/*  74 */     this.gni = getGNI(paramObjectIdentifier, paramArrayOfbyte);
/*  75 */     if (this.gni != null) {
/*  76 */       this.name = this.gni.toString();
/*     */     } else {
/*  78 */       this.name = "Unrecognized ObjectIdentifier: " + paramObjectIdentifier.toString();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OtherName(DerValue paramDerValue) throws IOException {
/*  89 */     DerInputStream derInputStream = paramDerValue.toDerInputStream();
/*     */     
/*  91 */     this.oid = derInputStream.getOID();
/*  92 */     DerValue derValue = derInputStream.getDerValue();
/*  93 */     this.nameValue = derValue.toByteArray();
/*  94 */     this.gni = getGNI(this.oid, this.nameValue);
/*  95 */     if (this.gni != null) {
/*  96 */       this.name = this.gni.toString();
/*     */     } else {
/*  98 */       this.name = "Unrecognized ObjectIdentifier: " + this.oid.toString();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdentifier getOID() {
/* 107 */     return this.oid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getNameValue() {
/* 114 */     return (byte[])this.nameValue.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GeneralNameInterface getGNI(ObjectIdentifier paramObjectIdentifier, byte[] paramArrayOfbyte) throws IOException {
/*     */     try {
/* 123 */       Class<?> clazz = OIDMap.getClass(paramObjectIdentifier);
/* 124 */       if (clazz == null) {
/* 125 */         return null;
/*     */       }
/* 127 */       Class[] arrayOfClass = { Object.class };
/* 128 */       Constructor<?> constructor = clazz.getConstructor(arrayOfClass);
/*     */       
/* 130 */       Object[] arrayOfObject = { paramArrayOfbyte };
/* 131 */       return (GeneralNameInterface)constructor
/* 132 */         .newInstance(arrayOfObject);
/*     */     }
/* 134 */     catch (Exception exception) {
/* 135 */       throw new IOException("Instantiation error: " + exception, exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 143 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 153 */     if (this.gni != null) {
/*     */       
/* 155 */       this.gni.encode(paramDerOutputStream);
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 160 */     derOutputStream.putOID(this.oid);
/* 161 */     derOutputStream.write(DerValue.createTag(-128, true, (byte)0), this.nameValue);
/* 162 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*     */     boolean bool;
/* 172 */     if (this == paramObject) {
/* 173 */       return true;
/*     */     }
/* 175 */     if (!(paramObject instanceof OtherName)) {
/* 176 */       return false;
/*     */     }
/* 178 */     OtherName otherName = (OtherName)paramObject;
/* 179 */     if (!otherName.oid.equals(this.oid)) {
/* 180 */       return false;
/*     */     }
/* 182 */     GeneralNameInterface generalNameInterface = null;
/*     */     try {
/* 184 */       generalNameInterface = getGNI(otherName.oid, otherName.nameValue);
/* 185 */     } catch (IOException iOException) {
/* 186 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 190 */     if (generalNameInterface != null) {
/*     */       try {
/* 192 */         bool = (generalNameInterface.constrains(this) == 0);
/* 193 */       } catch (UnsupportedOperationException unsupportedOperationException) {
/* 194 */         bool = false;
/*     */       } 
/*     */     } else {
/* 197 */       bool = Arrays.equals(this.nameValue, otherName.nameValue);
/*     */     } 
/*     */     
/* 200 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 209 */     if (this.myhash == -1) {
/* 210 */       this.myhash = 37 + this.oid.hashCode();
/* 211 */       for (byte b = 0; b < this.nameValue.length; b++) {
/* 212 */         this.myhash = 37 * this.myhash + this.nameValue[b];
/*     */       }
/*     */     } 
/* 215 */     return this.myhash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 222 */     return "Other-Name: " + this.name;
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
/*     */   public int constrains(GeneralNameInterface paramGeneralNameInterface) {
/*     */     byte b;
/* 246 */     if (paramGeneralNameInterface == null) {
/* 247 */       b = -1;
/* 248 */     } else if (paramGeneralNameInterface.getType() != 0) {
/* 249 */       b = -1;
/*     */     } else {
/* 251 */       throw new UnsupportedOperationException("Narrowing, widening, and matching are not supported for OtherName.");
/*     */     } 
/*     */     
/* 254 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int subtreeDepth() {
/* 265 */     throw new UnsupportedOperationException("subtreeDepth() not supported for generic OtherName");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/OtherName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */