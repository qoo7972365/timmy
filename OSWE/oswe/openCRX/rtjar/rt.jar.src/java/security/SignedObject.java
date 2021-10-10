/*     */ package java.security;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SignedObject
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 720502720485447167L;
/*     */   private byte[] content;
/*     */   private byte[] signature;
/*     */   private String thealgorithm;
/*     */   
/*     */   public SignedObject(Serializable paramSerializable, PrivateKey paramPrivateKey, Signature paramSignature) throws IOException, InvalidKeyException, SignatureException {
/* 149 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 150 */     ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
/*     */ 
/*     */     
/* 153 */     objectOutputStream.writeObject(paramSerializable);
/* 154 */     objectOutputStream.flush();
/* 155 */     objectOutputStream.close();
/* 156 */     this.content = byteArrayOutputStream.toByteArray();
/* 157 */     byteArrayOutputStream.close();
/*     */ 
/*     */     
/* 160 */     sign(paramPrivateKey, paramSignature);
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
/*     */   public Object getObject() throws IOException, ClassNotFoundException {
/* 177 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.content);
/* 178 */     ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
/* 179 */     Object object = objectInputStream.readObject();
/* 180 */     byteArrayInputStream.close();
/* 181 */     objectInputStream.close();
/* 182 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getSignature() {
/* 193 */     return (byte[])this.signature.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlgorithm() {
/* 202 */     return this.thealgorithm;
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
/*     */   public boolean verify(PublicKey paramPublicKey, Signature paramSignature) throws InvalidKeyException, SignatureException {
/* 222 */     paramSignature.initVerify(paramPublicKey);
/* 223 */     paramSignature.update((byte[])this.content.clone());
/* 224 */     return paramSignature.verify((byte[])this.signature.clone());
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
/*     */   private void sign(PrivateKey paramPrivateKey, Signature paramSignature) throws InvalidKeyException, SignatureException {
/* 240 */     paramSignature.initSign(paramPrivateKey);
/* 241 */     paramSignature.update((byte[])this.content.clone());
/* 242 */     this.signature = (byte[])paramSignature.sign().clone();
/* 243 */     this.thealgorithm = paramSignature.getAlgorithm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 252 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 253 */     this.content = (byte[])((byte[])getField.get("content", (Object)null)).clone();
/* 254 */     this.signature = (byte[])((byte[])getField.get("signature", (Object)null)).clone();
/* 255 */     this.thealgorithm = (String)getField.get("thealgorithm", (Object)null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/SignedObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */