/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import sun.corba.EncapsInputStreamFactory;
/*     */ import sun.corba.OutputStreamFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TypeCodeOutputStream
/*     */   extends EncapsOutputStream
/*     */ {
/*  68 */   private OutputStream enclosure = null;
/*  69 */   private Map typeMap = null;
/*     */   private boolean isEncapsulation = false;
/*     */   
/*     */   public TypeCodeOutputStream(ORB paramORB) {
/*  73 */     super(paramORB, false);
/*     */   }
/*     */   
/*     */   public TypeCodeOutputStream(ORB paramORB, boolean paramBoolean) {
/*  77 */     super(paramORB, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream create_input_stream() {
/*  83 */     return (InputStream)EncapsInputStreamFactory.newTypeCodeInputStream(orb(), getByteBuffer(), 
/*  84 */         getIndex(), isLittleEndian(), getGIOPVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnclosingOutputStream(OutputStream paramOutputStream) {
/*  93 */     this.enclosure = paramOutputStream;
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
/*     */   public TypeCodeOutputStream getTopLevelStream() {
/* 110 */     if (this.enclosure == null)
/* 111 */       return this; 
/* 112 */     if (this.enclosure instanceof TypeCodeOutputStream)
/* 113 */       return ((TypeCodeOutputStream)this.enclosure).getTopLevelStream(); 
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public int getTopLevelPosition() {
/* 118 */     if (this.enclosure != null && this.enclosure instanceof TypeCodeOutputStream) {
/* 119 */       int i = ((TypeCodeOutputStream)this.enclosure).getTopLevelPosition() + getPosition();
/*     */ 
/*     */       
/* 122 */       if (this.isEncapsulation) i += 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 130 */       return i;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     return getPosition();
/*     */   }
/*     */   
/*     */   public void addIDAtPosition(String paramString, int paramInt) {
/* 140 */     if (this.typeMap == null) {
/* 141 */       this.typeMap = new HashMap<>(16);
/*     */     }
/* 143 */     this.typeMap.put(paramString, new Integer(paramInt));
/*     */   }
/*     */   
/*     */   public int getPositionForID(String paramString) {
/* 147 */     if (this.typeMap == null) {
/* 148 */       throw this.wrapper.refTypeIndirType(CompletionStatus.COMPLETED_NO);
/*     */     }
/*     */     
/* 151 */     return ((Integer)this.typeMap.get(paramString)).intValue();
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
/*     */   public void writeRawBuffer(OutputStream paramOutputStream, int paramInt) {
/* 171 */     paramOutputStream.write_long(paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     ByteBuffer byteBuffer = getByteBuffer();
/* 178 */     if (byteBuffer.hasArray()) {
/*     */       
/* 180 */       paramOutputStream.write_octet_array(byteBuffer.array(), 4, getIndex() - 4);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 188 */       byte[] arrayOfByte = new byte[byteBuffer.limit()];
/* 189 */       for (byte b = 0; b < arrayOfByte.length; b++)
/* 190 */         arrayOfByte[b] = byteBuffer.get(b); 
/* 191 */       paramOutputStream.write_octet_array(arrayOfByte, 4, getIndex() - 4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCodeOutputStream createEncapsulation(ORB paramORB) {
/* 202 */     TypeCodeOutputStream typeCodeOutputStream = OutputStreamFactory.newTypeCodeOutputStream((ORB)paramORB, isLittleEndian());
/* 203 */     typeCodeOutputStream.setEnclosingOutputStream(this);
/* 204 */     typeCodeOutputStream.makeEncapsulation();
/*     */     
/* 206 */     return typeCodeOutputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void makeEncapsulation() {
/* 211 */     putEndian();
/* 212 */     this.isEncapsulation = true;
/*     */   }
/*     */   
/*     */   public static TypeCodeOutputStream wrapOutputStream(OutputStream paramOutputStream) {
/* 216 */     boolean bool = (paramOutputStream instanceof CDROutputStream) ? ((CDROutputStream)paramOutputStream).isLittleEndian() : false;
/*     */     
/* 218 */     TypeCodeOutputStream typeCodeOutputStream = OutputStreamFactory.newTypeCodeOutputStream((ORB)paramOutputStream.orb(), bool);
/* 219 */     typeCodeOutputStream.setEnclosingOutputStream(paramOutputStream);
/*     */     
/* 221 */     return typeCodeOutputStream;
/*     */   }
/*     */   
/*     */   public int getPosition() {
/* 225 */     return getIndex();
/*     */   }
/*     */   
/*     */   public int getRealIndex(int paramInt) {
/* 229 */     return getTopLevelPosition();
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
/*     */   public byte[] getTypeCodeBuffer() {
/* 242 */     ByteBuffer byteBuffer = getByteBuffer();
/*     */     
/* 244 */     byte[] arrayOfByte = new byte[getIndex() - 4];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     for (byte b = 0; b < arrayOfByte.length; b++)
/* 250 */       arrayOfByte[b] = byteBuffer.get(b + 4); 
/* 251 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public void printTypeMap() {
/* 255 */     System.out.println("typeMap = {");
/* 256 */     Iterator<String> iterator = this.typeMap.keySet().iterator();
/* 257 */     while (iterator.hasNext()) {
/* 258 */       String str = iterator.next();
/* 259 */       Integer integer = (Integer)this.typeMap.get(str);
/* 260 */       System.out.println("  key = " + str + ", value = " + integer);
/*     */     } 
/* 262 */     System.out.println("}");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/TypeCodeOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */