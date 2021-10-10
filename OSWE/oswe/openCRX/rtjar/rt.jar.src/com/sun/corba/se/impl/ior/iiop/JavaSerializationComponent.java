/*    */ package com.sun.corba.se.impl.ior.iiop;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.TaggedComponentBase;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JavaSerializationComponent
/*    */   extends TaggedComponentBase
/*    */ {
/*    */   private byte version;
/*    */   private static JavaSerializationComponent singleton;
/*    */   
/*    */   public static JavaSerializationComponent singleton() {
/* 52 */     if (singleton == null) {
/* 53 */       synchronized (JavaSerializationComponent.class) {
/* 54 */         singleton = new JavaSerializationComponent((byte)1);
/*    */       } 
/*    */     }
/*    */     
/* 58 */     return singleton;
/*    */   }
/*    */   
/*    */   public JavaSerializationComponent(byte paramByte) {
/* 62 */     this.version = paramByte;
/*    */   }
/*    */   
/*    */   public byte javaSerializationVersion() {
/* 66 */     return this.version;
/*    */   }
/*    */   
/*    */   public void writeContents(OutputStream paramOutputStream) {
/* 70 */     paramOutputStream.write_octet(this.version);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 74 */     return 1398099458;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 78 */     if (!(paramObject instanceof JavaSerializationComponent)) {
/* 79 */       return false;
/*    */     }
/* 81 */     JavaSerializationComponent javaSerializationComponent = (JavaSerializationComponent)paramObject;
/* 82 */     return (this.version == javaSerializationComponent.version);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 86 */     return this.version;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/iiop/JavaSerializationComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */