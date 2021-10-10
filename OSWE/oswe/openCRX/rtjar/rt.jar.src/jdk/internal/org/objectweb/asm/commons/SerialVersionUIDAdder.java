/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import jdk.internal.org.objectweb.asm.ClassVisitor;
/*     */ import jdk.internal.org.objectweb.asm.FieldVisitor;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerialVersionUIDAdder
/*     */   extends ClassVisitor
/*     */ {
/*     */   private boolean computeSVUID;
/*     */   private boolean hasSVUID;
/*     */   private int access;
/*     */   private String name;
/*     */   private String[] interfaces;
/*     */   private Collection<Item> svuidFields;
/*     */   private boolean hasStaticInitializer;
/*     */   private Collection<Item> svuidConstructors;
/*     */   private Collection<Item> svuidMethods;
/*     */   
/*     */   public SerialVersionUIDAdder(ClassVisitor paramClassVisitor) {
/* 202 */     this(327680, paramClassVisitor);
/* 203 */     if (getClass() != SerialVersionUIDAdder.class) {
/* 204 */       throw new IllegalStateException();
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
/*     */   protected SerialVersionUIDAdder(int paramInt, ClassVisitor paramClassVisitor) {
/* 219 */     super(paramInt, paramClassVisitor);
/* 220 */     this.svuidFields = new ArrayList<>();
/* 221 */     this.svuidConstructors = new ArrayList<>();
/* 222 */     this.svuidMethods = new ArrayList<>();
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
/*     */   public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 237 */     this.computeSVUID = ((paramInt2 & 0x200) == 0);
/*     */     
/* 239 */     if (this.computeSVUID) {
/* 240 */       this.name = paramString1;
/* 241 */       this.access = paramInt2;
/* 242 */       this.interfaces = new String[paramArrayOfString.length];
/* 243 */       System.arraycopy(paramArrayOfString, 0, this.interfaces, 0, paramArrayOfString.length);
/*     */     } 
/*     */ 
/*     */     
/* 247 */     super.visit(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 257 */     if (this.computeSVUID) {
/* 258 */       if ("<clinit>".equals(paramString1)) {
/* 259 */         this.hasStaticInitializer = true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 268 */       int i = paramInt & 0xD3F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 275 */       if ((paramInt & 0x2) == 0) {
/* 276 */         if ("<init>".equals(paramString1)) {
/* 277 */           this.svuidConstructors.add(new Item(paramString1, i, paramString2));
/* 278 */         } else if (!"<clinit>".equals(paramString1)) {
/* 279 */           this.svuidMethods.add(new Item(paramString1, i, paramString2));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 284 */     return super.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/* 294 */     if (this.computeSVUID) {
/* 295 */       if ("serialVersionUID".equals(paramString1)) {
/*     */         
/* 297 */         this.computeSVUID = false;
/* 298 */         this.hasSVUID = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       if ((paramInt & 0x2) == 0 || (paramInt & 0x88) == 0) {
/*     */         
/* 308 */         int i = paramInt & 0xDF;
/*     */ 
/*     */ 
/*     */         
/* 312 */         this.svuidFields.add(new Item(paramString1, i, paramString2));
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     return super.visitField(paramInt, paramString1, paramString2, paramString3, paramObject);
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
/*     */   public void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt) {
/* 329 */     if (this.name != null && this.name.equals(paramString1)) {
/* 330 */       this.access = paramInt;
/*     */     }
/* 332 */     super.visitInnerClass(paramString1, paramString2, paramString3, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 341 */     if (this.computeSVUID && !this.hasSVUID) {
/*     */       try {
/* 343 */         addSVUID(computeSVUID());
/* 344 */       } catch (Throwable throwable) {
/* 345 */         throw new RuntimeException("Error while computing SVUID for " + this.name, throwable);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 350 */     super.visitEnd();
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
/*     */   public boolean hasSVUID() {
/* 364 */     return this.hasSVUID;
/*     */   }
/*     */   
/*     */   protected void addSVUID(long paramLong) {
/* 368 */     FieldVisitor fieldVisitor = super.visitField(24, "serialVersionUID", "J", null, 
/* 369 */         Long.valueOf(paramLong));
/* 370 */     if (fieldVisitor != null) {
/* 371 */       fieldVisitor.visitEnd();
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
/*     */   protected long computeSVUID() throws IOException {
/* 384 */     DataOutputStream dataOutputStream = null;
/* 385 */     long l = 0L;
/*     */     
/*     */     try {
/* 388 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 389 */       dataOutputStream = new DataOutputStream(byteArrayOutputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 394 */       dataOutputStream.writeUTF(this.name.replace('/', '.'));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 399 */       dataOutputStream.writeInt(this.access & 0x611);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 407 */       Arrays.sort((Object[])this.interfaces);
/* 408 */       for (byte b = 0; b < this.interfaces.length; b++) {
/* 409 */         dataOutputStream.writeUTF(this.interfaces[b].replace('/', '.'));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 423 */       writeItems(this.svuidFields, dataOutputStream, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 432 */       if (this.hasStaticInitializer) {
/* 433 */         dataOutputStream.writeUTF("<clinit>");
/* 434 */         dataOutputStream.writeInt(8);
/* 435 */         dataOutputStream.writeUTF("()V");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 444 */       writeItems(this.svuidConstructors, dataOutputStream, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 452 */       writeItems(this.svuidMethods, dataOutputStream, true);
/*     */       
/* 454 */       dataOutputStream.flush();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 461 */       byte[] arrayOfByte = computeSHAdigest(byteArrayOutputStream.toByteArray());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 476 */       for (int i = Math.min(arrayOfByte.length, 8) - 1; i >= 0; i--) {
/* 477 */         l = l << 8L | (arrayOfByte[i] & 0xFF);
/*     */       }
/*     */     } finally {
/*     */       
/* 481 */       if (dataOutputStream != null) {
/* 482 */         dataOutputStream.close();
/*     */       }
/*     */     } 
/*     */     
/* 486 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] computeSHAdigest(byte[] paramArrayOfbyte) {
/*     */     try {
/* 498 */       return MessageDigest.getInstance("SHA").digest(paramArrayOfbyte);
/* 499 */     } catch (Exception exception) {
/* 500 */       throw new UnsupportedOperationException(exception.toString());
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeItems(Collection<Item> paramCollection, DataOutput paramDataOutput, boolean paramBoolean) throws IOException {
/* 518 */     int i = paramCollection.size();
/* 519 */     Item[] arrayOfItem = paramCollection.<Item>toArray(new Item[i]);
/* 520 */     Arrays.sort((Object[])arrayOfItem);
/* 521 */     for (byte b = 0; b < i; b++) {
/* 522 */       paramDataOutput.writeUTF((arrayOfItem[b]).name);
/* 523 */       paramDataOutput.writeInt((arrayOfItem[b]).access);
/* 524 */       paramDataOutput.writeUTF(paramBoolean ? (arrayOfItem[b]).desc.replace('/', '.') : (arrayOfItem[b]).desc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Item
/*     */     implements Comparable<Item>
/*     */   {
/*     */     final String name;
/*     */ 
/*     */     
/*     */     final int access;
/*     */     
/*     */     final String desc;
/*     */ 
/*     */     
/*     */     Item(String param1String1, int param1Int, String param1String2) {
/* 542 */       this.name = param1String1;
/* 543 */       this.access = param1Int;
/* 544 */       this.desc = param1String2;
/*     */     }
/*     */     
/*     */     public int compareTo(Item param1Item) {
/* 548 */       int i = this.name.compareTo(param1Item.name);
/* 549 */       if (i == 0) {
/* 550 */         i = this.desc.compareTo(param1Item.desc);
/*     */       }
/* 552 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 557 */       if (param1Object instanceof Item) {
/* 558 */         return (compareTo((Item)param1Object) == 0);
/*     */       }
/* 560 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 565 */       return (this.name + this.desc).hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/SerialVersionUIDAdder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */