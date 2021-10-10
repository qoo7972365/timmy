/*     */ package javax.management;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanFeatureInfo
/*     */   implements Serializable, DescriptorRead
/*     */ {
/*     */   static final long serialVersionUID = 3952882688968447265L;
/*     */   protected String name;
/*     */   protected String description;
/*     */   private transient Descriptor descriptor;
/*     */   
/*     */   public MBeanFeatureInfo(String paramString1, String paramString2) {
/*  84 */     this(paramString1, paramString2, null);
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
/*     */   public MBeanFeatureInfo(String paramString1, String paramString2, Descriptor paramDescriptor) {
/*  99 */     this.name = paramString1;
/* 100 */     this.description = paramString2;
/* 101 */     this.descriptor = paramDescriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 110 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 119 */     return this.description;
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
/*     */   public Descriptor getDescriptor() {
/* 131 */     return (Descriptor)ImmutableDescriptor.nonNullDescriptor(this.descriptor).clone();
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
/*     */   public boolean equals(Object paramObject) {
/* 146 */     if (paramObject == this)
/* 147 */       return true; 
/* 148 */     if (!(paramObject instanceof MBeanFeatureInfo))
/* 149 */       return false; 
/* 150 */     MBeanFeatureInfo mBeanFeatureInfo = (MBeanFeatureInfo)paramObject;
/* 151 */     return (Objects.equals(mBeanFeatureInfo.getName(), getName()) && 
/* 152 */       Objects.equals(mBeanFeatureInfo.getDescription(), getDescription()) && 
/* 153 */       Objects.equals(mBeanFeatureInfo.getDescriptor(), getDescriptor()));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 157 */     return getName().hashCode() ^ getDescription().hashCode() ^ 
/* 158 */       getDescriptor().hashCode();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 187 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 189 */     if (this.descriptor != null && this.descriptor
/* 190 */       .getClass() == ImmutableDescriptor.class) {
/*     */       
/* 192 */       paramObjectOutputStream.write(1);
/*     */       
/* 194 */       String[] arrayOfString = this.descriptor.getFieldNames();
/*     */       
/* 196 */       paramObjectOutputStream.writeObject(arrayOfString);
/* 197 */       paramObjectOutputStream.writeObject(this.descriptor.getFieldValues(arrayOfString));
/*     */     } else {
/* 199 */       paramObjectOutputStream.write(0);
/*     */       
/* 201 */       paramObjectOutputStream.writeObject(this.descriptor);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     String[] arrayOfString;
/*     */     Object[] arrayOfObject;
/* 237 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 239 */     switch (paramObjectInputStream.read()) {
/*     */       case 1:
/* 241 */         arrayOfString = (String[])paramObjectInputStream.readObject();
/*     */         
/* 243 */         arrayOfObject = (Object[])paramObjectInputStream.readObject();
/* 244 */         this.descriptor = (arrayOfString.length == 0) ? ImmutableDescriptor.EMPTY_DESCRIPTOR : new ImmutableDescriptor(arrayOfString, arrayOfObject);
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/* 250 */         this.descriptor = (Descriptor)paramObjectInputStream.readObject();
/*     */         
/* 252 */         if (this.descriptor == null) {
/* 253 */           this.descriptor = ImmutableDescriptor.EMPTY_DESCRIPTOR;
/*     */         }
/*     */         return;
/*     */       
/*     */       case -1:
/* 258 */         this.descriptor = ImmutableDescriptor.EMPTY_DESCRIPTOR;
/*     */         return;
/*     */     } 
/*     */     
/* 262 */     throw new StreamCorruptedException("Got unexpected byte.");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanFeatureInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */