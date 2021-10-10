/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import sun.corba.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StubIORImpl
/*     */ {
/*     */   private int hashCode;
/*     */   private byte[] typeData;
/*     */   private int[] profileTags;
/*     */   private byte[][] profileData;
/*     */   
/*     */   public StubIORImpl() {
/*  67 */     this.hashCode = 0;
/*  68 */     this.typeData = null;
/*  69 */     this.profileTags = null;
/*  70 */     this.profileData = (byte[][])null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRepositoryId() {
/*  75 */     if (this.typeData == null) {
/*  76 */       return null;
/*     */     }
/*  78 */     return new String(this.typeData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StubIORImpl(Object paramObject) {
/*  84 */     OutputStream outputStream = StubAdapter.getORB(paramObject).create_output_stream();
/*  85 */     outputStream.write_Object(paramObject);
/*  86 */     InputStream inputStream = outputStream.create_input_stream();
/*     */ 
/*     */     
/*  89 */     int i = inputStream.read_long();
/*  90 */     this.typeData = new byte[i];
/*  91 */     inputStream.read_octet_array(this.typeData, 0, i);
/*  92 */     int j = inputStream.read_long();
/*  93 */     this.profileTags = new int[j];
/*  94 */     this.profileData = new byte[j][];
/*  95 */     for (byte b = 0; b < j; b++) {
/*  96 */       this.profileTags[b] = inputStream.read_long();
/*  97 */       this.profileData[b] = new byte[inputStream.read_long()];
/*  98 */       inputStream.read_octet_array(this.profileData[b], 0, (this.profileData[b]).length);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Delegate getDelegate(ORB paramORB) {
/* 105 */     OutputStream outputStream = paramORB.create_output_stream();
/* 106 */     outputStream.write_long(this.typeData.length);
/* 107 */     outputStream.write_octet_array(this.typeData, 0, this.typeData.length);
/* 108 */     outputStream.write_long(this.profileTags.length);
/* 109 */     for (byte b = 0; b < this.profileTags.length; b++) {
/* 110 */       outputStream.write_long(this.profileTags[b]);
/* 111 */       outputStream.write_long((this.profileData[b]).length);
/* 112 */       outputStream.write_octet_array(this.profileData[b], 0, (this.profileData[b]).length);
/*     */     } 
/*     */     
/* 115 */     InputStream inputStream = outputStream.create_input_stream();
/*     */ 
/*     */     
/* 118 */     Object object = inputStream.read_Object();
/* 119 */     return StubAdapter.getDelegate(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRead(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 126 */     int i = paramObjectInputStream.readInt();
/* 127 */     SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, byte[].class, i);
/* 128 */     this.typeData = new byte[i];
/* 129 */     paramObjectInputStream.readFully(this.typeData);
/*     */     
/* 131 */     int j = paramObjectInputStream.readInt();
/* 132 */     SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, int[].class, j);
/* 133 */     SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, byte[].class, j);
/* 134 */     this.profileTags = new int[j];
/* 135 */     this.profileData = new byte[j][];
/* 136 */     for (byte b = 0; b < j; b++) {
/* 137 */       this.profileTags[b] = paramObjectInputStream.readInt();
/* 138 */       int k = paramObjectInputStream.readInt();
/* 139 */       SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, byte[].class, k);
/* 140 */       this.profileData[b] = new byte[k];
/* 141 */       paramObjectInputStream.readFully(this.profileData[b]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doWrite(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 149 */     paramObjectOutputStream.writeInt(this.typeData.length);
/* 150 */     paramObjectOutputStream.write(this.typeData);
/* 151 */     paramObjectOutputStream.writeInt(this.profileTags.length);
/* 152 */     for (byte b = 0; b < this.profileTags.length; b++) {
/* 153 */       paramObjectOutputStream.writeInt(this.profileTags[b]);
/* 154 */       paramObjectOutputStream.writeInt((this.profileData[b]).length);
/* 155 */       paramObjectOutputStream.write(this.profileData[b]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int hashCode() {
/* 166 */     if (this.hashCode == 0) {
/*     */       byte b;
/*     */       
/* 169 */       for (b = 0; b < this.typeData.length; b++) {
/* 170 */         this.hashCode = this.hashCode * 37 + this.typeData[b];
/*     */       }
/*     */       
/* 173 */       for (b = 0; b < this.profileTags.length; b++) {
/* 174 */         this.hashCode = this.hashCode * 37 + this.profileTags[b];
/* 175 */         for (byte b1 = 0; b1 < (this.profileData[b]).length; b1++) {
/* 176 */           this.hashCode = this.hashCode * 37 + this.profileData[b][b1];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean equalArrays(int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 186 */     if (paramArrayOfint1.length != paramArrayOfint2.length) {
/* 187 */       return false;
/*     */     }
/* 189 */     for (byte b = 0; b < paramArrayOfint1.length; b++) {
/* 190 */       if (paramArrayOfint1[b] != paramArrayOfint2[b]) {
/* 191 */         return false;
/*     */       }
/*     */     } 
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean equalArrays(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 199 */     if (paramArrayOfbyte1.length != paramArrayOfbyte2.length) {
/* 200 */       return false;
/*     */     }
/* 202 */     for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/* 203 */       if (paramArrayOfbyte1[b] != paramArrayOfbyte2[b]) {
/* 204 */         return false;
/*     */       }
/*     */     } 
/* 207 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean equalArrays(byte[][] paramArrayOfbyte1, byte[][] paramArrayOfbyte2) {
/* 212 */     if (paramArrayOfbyte1.length != paramArrayOfbyte2.length) {
/* 213 */       return false;
/*     */     }
/* 215 */     for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/* 216 */       if (!equalArrays(paramArrayOfbyte1[b], paramArrayOfbyte2[b])) {
/* 217 */         return false;
/*     */       }
/*     */     } 
/* 220 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 225 */     if (this == paramObject) {
/* 226 */       return true;
/*     */     }
/*     */     
/* 229 */     if (!(paramObject instanceof StubIORImpl)) {
/* 230 */       return false;
/*     */     }
/*     */     
/* 233 */     StubIORImpl stubIORImpl = (StubIORImpl)paramObject;
/* 234 */     if (stubIORImpl.hashCode() != hashCode()) {
/* 235 */       return false;
/*     */     }
/*     */     
/* 238 */     return (equalArrays(this.typeData, stubIORImpl.typeData) && 
/* 239 */       equalArrays(this.profileTags, stubIORImpl.profileTags) && 
/* 240 */       equalArrays(this.profileData, stubIORImpl.profileData));
/*     */   }
/*     */ 
/*     */   
/*     */   private void appendByteArray(StringBuffer paramStringBuffer, byte[] paramArrayOfbyte) {
/* 245 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 246 */       paramStringBuffer.append(Integer.toHexString(paramArrayOfbyte[b]));
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
/*     */   public String toString() {
/* 258 */     StringBuffer stringBuffer = new StringBuffer();
/* 259 */     stringBuffer.append("SimpleIORImpl[");
/* 260 */     String str = new String(this.typeData);
/* 261 */     stringBuffer.append(str);
/* 262 */     for (byte b = 0; b < this.profileTags.length; b++) {
/* 263 */       stringBuffer.append(",(");
/* 264 */       stringBuffer.append(this.profileTags[b]);
/* 265 */       stringBuffer.append(")");
/* 266 */       appendByteArray(stringBuffer, this.profileData[b]);
/*     */     } 
/*     */     
/* 269 */     stringBuffer.append("]");
/* 270 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/StubIORImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */