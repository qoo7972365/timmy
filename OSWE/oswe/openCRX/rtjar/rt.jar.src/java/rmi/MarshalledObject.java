/*     */ package java.rmi;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.misc.ObjectInputFilter;
/*     */ import sun.rmi.server.MarshalInputStream;
/*     */ import sun.rmi.server.MarshalOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MarshalledObject<T>
/*     */   implements Serializable
/*     */ {
/*  82 */   private byte[] objBytes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private byte[] locBytes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private transient ObjectInputFilter objectInputFilter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 8988374069173025854L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MarshalledObject(T paramT) throws IOException {
/* 117 */     if (paramT == null) {
/* 118 */       this.hash = 13;
/*     */       
/*     */       return;
/*     */     } 
/* 122 */     ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
/* 123 */     ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
/* 124 */     MarshalledObjectOutputStream marshalledObjectOutputStream = new MarshalledObjectOutputStream(byteArrayOutputStream1, byteArrayOutputStream2);
/*     */     
/* 126 */     marshalledObjectOutputStream.writeObject(paramT);
/* 127 */     marshalledObjectOutputStream.flush();
/* 128 */     this.objBytes = byteArrayOutputStream1.toByteArray();
/*     */     
/* 130 */     this.locBytes = marshalledObjectOutputStream.hadAnnotations() ? byteArrayOutputStream2.toByteArray() : null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     int i = 0;
/* 137 */     for (byte b = 0; b < this.objBytes.length; b++) {
/* 138 */       i = 31 * i + this.objBytes[b];
/*     */     }
/* 140 */     this.hash = i;
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 153 */     paramObjectInputStream.defaultReadObject();
/* 154 */     this.objectInputFilter = ObjectInputFilter.Config.getObjectInputFilter(paramObjectInputStream);
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
/*     */   public T get() throws IOException, ClassNotFoundException {
/* 172 */     if (this.objBytes == null) {
/* 173 */       return null;
/*     */     }
/* 175 */     ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(this.objBytes);
/*     */     
/* 177 */     ByteArrayInputStream byteArrayInputStream2 = (this.locBytes == null) ? null : new ByteArrayInputStream(this.locBytes);
/*     */     
/* 179 */     MarshalledObjectInputStream marshalledObjectInputStream = new MarshalledObjectInputStream(byteArrayInputStream1, byteArrayInputStream2, this.objectInputFilter);
/*     */ 
/*     */     
/* 182 */     Object object = marshalledObjectInputStream.readObject();
/* 183 */     marshalledObjectInputStream.close();
/* 184 */     return (T)object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 193 */     return this.hash;
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
/*     */   public boolean equals(Object paramObject) {
/* 212 */     if (paramObject == this) {
/* 213 */       return true;
/*     */     }
/* 215 */     if (paramObject != null && paramObject instanceof MarshalledObject) {
/* 216 */       MarshalledObject marshalledObject = (MarshalledObject)paramObject;
/*     */ 
/*     */       
/* 219 */       if (this.objBytes == null || marshalledObject.objBytes == null) {
/* 220 */         return (this.objBytes == marshalledObject.objBytes);
/*     */       }
/*     */       
/* 223 */       if (this.objBytes.length != marshalledObject.objBytes.length) {
/* 224 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 228 */       for (byte b = 0; b < this.objBytes.length; b++) {
/* 229 */         if (this.objBytes[b] != marshalledObject.objBytes[b])
/* 230 */           return false; 
/*     */       } 
/* 232 */       return true;
/*     */     } 
/* 234 */     return false;
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
/*     */   private static class MarshalledObjectOutputStream
/*     */     extends MarshalOutputStream
/*     */   {
/*     */     private ObjectOutputStream locOut;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean hadAnnotations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MarshalledObjectOutputStream(OutputStream param1OutputStream1, OutputStream param1OutputStream2) throws IOException {
/* 269 */       super(param1OutputStream1);
/* 270 */       useProtocolVersion(2);
/* 271 */       this.locOut = new ObjectOutputStream(param1OutputStream2);
/* 272 */       this.hadAnnotations = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean hadAnnotations() {
/* 280 */       return this.hadAnnotations;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void writeLocation(String param1String) throws IOException {
/* 288 */       this.hadAnnotations |= (param1String != null) ? 1 : 0;
/* 289 */       this.locOut.writeObject(param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() throws IOException {
/* 294 */       super.flush();
/* 295 */       this.locOut.flush();
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
/*     */   
/*     */   private static class MarshalledObjectInputStream
/*     */     extends MarshalInputStream
/*     */   {
/*     */     private ObjectInputStream locIn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MarshalledObjectInputStream(InputStream param1InputStream1, InputStream param1InputStream2, final ObjectInputFilter filter) throws IOException {
/* 324 */       super(param1InputStream1);
/* 325 */       this.locIn = (param1InputStream2 == null) ? null : new ObjectInputStream(param1InputStream2);
/* 326 */       if (filter != null) {
/* 327 */         AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */             {
/*     */               public Void run() {
/* 330 */                 ObjectInputFilter.Config.setObjectInputFilter(MarshalledObject.MarshalledObjectInputStream.this, filter);
/* 331 */                 if (MarshalledObject.MarshalledObjectInputStream.this.locIn != null) {
/* 332 */                   ObjectInputFilter.Config.setObjectInputFilter(MarshalledObject.MarshalledObjectInputStream.this.locIn, filter);
/*     */                 }
/* 334 */                 return null;
/*     */               }
/*     */             });
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object readLocation() throws IOException, ClassNotFoundException {
/* 348 */       return (this.locIn == null) ? null : this.locIn.readObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/MarshalledObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */