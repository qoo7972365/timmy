/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.Identifiable;
/*    */ import java.util.Arrays;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
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
/*    */ public abstract class GenericIdentifiable
/*    */   implements Identifiable
/*    */ {
/*    */   private int id;
/*    */   private byte[] data;
/*    */   
/*    */   public GenericIdentifiable(int paramInt, InputStream paramInputStream) {
/* 46 */     this.id = paramInt;
/* 47 */     this.data = EncapsulationUtility.readOctets(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 52 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(OutputStream paramOutputStream) {
/* 57 */     paramOutputStream.write_ulong(this.data.length);
/* 58 */     paramOutputStream.write_octet_array(this.data, 0, this.data.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return "GenericIdentifiable[id=" + getId() + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 68 */     if (paramObject == null) {
/* 69 */       return false;
/*    */     }
/* 71 */     if (!(paramObject instanceof GenericIdentifiable)) {
/* 72 */       return false;
/*    */     }
/* 74 */     GenericIdentifiable genericIdentifiable = (GenericIdentifiable)paramObject;
/*    */     
/* 76 */     return (getId() == genericIdentifiable.getId() && 
/* 77 */       Arrays.equals(getData(), genericIdentifiable.getData()));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 82 */     int i = 17;
/* 83 */     for (byte b = 0; b < this.data.length; b++)
/* 84 */       i = 37 * i + this.data[b]; 
/* 85 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public GenericIdentifiable(int paramInt, byte[] paramArrayOfbyte) {
/* 90 */     this.id = paramInt;
/* 91 */     this.data = (byte[])paramArrayOfbyte.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getData() {
/* 96 */     return this.data;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/GenericIdentifiable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */