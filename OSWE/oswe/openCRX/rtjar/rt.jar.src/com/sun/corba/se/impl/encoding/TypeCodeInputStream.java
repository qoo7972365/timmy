/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.TypeCodeImpl;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import sun.corba.EncapsInputStreamFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeCodeInputStream
/*     */   extends EncapsInputStream
/*     */   implements TypeCodeReader
/*     */ {
/*  71 */   private Map typeMap = null;
/*  72 */   private InputStream enclosure = null;
/*     */   private boolean isEncapsulation = false;
/*     */   
/*     */   public TypeCodeInputStream(ORB paramORB, byte[] paramArrayOfbyte, int paramInt) {
/*  76 */     super(paramORB, paramArrayOfbyte, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCodeInputStream(ORB paramORB, byte[] paramArrayOfbyte, int paramInt, boolean paramBoolean, GIOPVersion paramGIOPVersion) {
/*  84 */     super(paramORB, paramArrayOfbyte, paramInt, paramBoolean, paramGIOPVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCodeInputStream(ORB paramORB, ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean, GIOPVersion paramGIOPVersion) {
/*  92 */     super(paramORB, paramByteBuffer, paramInt, paramBoolean, paramGIOPVersion);
/*     */   }
/*     */   
/*     */   public void addTypeCodeAtPosition(TypeCodeImpl paramTypeCodeImpl, int paramInt) {
/*  96 */     if (this.typeMap == null)
/*     */     {
/*  98 */       this.typeMap = new HashMap<>(16);
/*     */     }
/*     */     
/* 101 */     this.typeMap.put(new Integer(paramInt), paramTypeCodeImpl);
/*     */   }
/*     */   
/*     */   public TypeCodeImpl getTypeCodeAtPosition(int paramInt) {
/* 105 */     if (this.typeMap == null) {
/* 106 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 111 */     return (TypeCodeImpl)this.typeMap.get(new Integer(paramInt));
/*     */   }
/*     */   
/*     */   public void setEnclosingInputStream(InputStream paramInputStream) {
/* 115 */     this.enclosure = paramInputStream;
/*     */   }
/*     */   
/*     */   public TypeCodeReader getTopLevelStream() {
/* 119 */     if (this.enclosure == null)
/* 120 */       return this; 
/* 121 */     if (this.enclosure instanceof TypeCodeReader)
/* 122 */       return ((TypeCodeReader)this.enclosure).getTopLevelStream(); 
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   public int getTopLevelPosition() {
/* 127 */     if (this.enclosure != null && this.enclosure instanceof TypeCodeReader) {
/*     */ 
/*     */ 
/*     */       
/* 131 */       int i = ((TypeCodeReader)this.enclosure).getTopLevelPosition();
/*     */ 
/*     */       
/* 134 */       return i - getBufferLength() + getPosition();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     return getPosition();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeCodeInputStream readEncapsulation(InputStream paramInputStream, ORB paramORB) {
/*     */     TypeCodeInputStream typeCodeInputStream;
/* 154 */     int i = paramInputStream.read_long();
/*     */ 
/*     */     
/* 157 */     byte[] arrayOfByte = new byte[i];
/* 158 */     paramInputStream.read_octet_array(arrayOfByte, 0, arrayOfByte.length);
/*     */ 
/*     */     
/* 161 */     if (paramInputStream instanceof CDRInputStream) {
/* 162 */       typeCodeInputStream = EncapsInputStreamFactory.newTypeCodeInputStream(paramORB, arrayOfByte, arrayOfByte.length, ((CDRInputStream)paramInputStream)
/*     */           
/* 164 */           .isLittleEndian(), ((CDRInputStream)paramInputStream)
/* 165 */           .getGIOPVersion());
/*     */     } else {
/* 167 */       typeCodeInputStream = EncapsInputStreamFactory.newTypeCodeInputStream(paramORB, arrayOfByte, arrayOfByte.length);
/*     */     } 
/*     */     
/* 170 */     typeCodeInputStream.setEnclosingInputStream(paramInputStream);
/* 171 */     typeCodeInputStream.makeEncapsulation();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     return typeCodeInputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void makeEncapsulation() {
/* 181 */     consumeEndian();
/* 182 */     this.isEncapsulation = true;
/*     */   }
/*     */   
/*     */   public void printTypeMap() {
/* 186 */     System.out.println("typeMap = {");
/* 187 */     Iterator<Integer> iterator = this.typeMap.keySet().iterator();
/* 188 */     while (iterator.hasNext()) {
/* 189 */       Integer integer = iterator.next();
/* 190 */       TypeCodeImpl typeCodeImpl = (TypeCodeImpl)this.typeMap.get(integer);
/* 191 */       System.out.println("  key = " + integer.intValue() + ", value = " + typeCodeImpl.description());
/*     */     } 
/* 193 */     System.out.println("}");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/TypeCodeInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */