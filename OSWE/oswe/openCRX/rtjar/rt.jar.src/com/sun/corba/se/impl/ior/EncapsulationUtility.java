/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CDROutputStream;
/*     */ import com.sun.corba.se.impl.encoding.EncapsInputStream;
/*     */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*     */ import com.sun.corba.se.spi.ior.Identifiable;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.WriteContents;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
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
/*     */ 
/*     */ public class EncapsulationUtility
/*     */ {
/*     */   public static void readIdentifiableSequence(List<Identifiable> paramList, IdentifiableFactoryFinder paramIdentifiableFactoryFinder, InputStream paramInputStream) {
/*  70 */     int i = paramInputStream.read_long();
/*  71 */     for (byte b = 0; b < i; b++) {
/*  72 */       int j = paramInputStream.read_long();
/*  73 */       Identifiable identifiable = paramIdentifiableFactoryFinder.create(j, paramInputStream);
/*  74 */       paramList.add(identifiable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeIdentifiableSequence(List paramList, OutputStream paramOutputStream) {
/*  83 */     paramOutputStream.write_long(paramList.size());
/*  84 */     Iterator<Identifiable> iterator = paramList.iterator();
/*  85 */     while (iterator.hasNext()) {
/*  86 */       Identifiable identifiable = iterator.next();
/*  87 */       paramOutputStream.write_long(identifiable.getId());
/*  88 */       identifiable.write(paramOutputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeOutputStream(OutputStream paramOutputStream1, OutputStream paramOutputStream2) {
/*  99 */     byte[] arrayOfByte = ((CDROutputStream)paramOutputStream1).toByteArray();
/* 100 */     paramOutputStream2.write_long(arrayOfByte.length);
/* 101 */     paramOutputStream2.write_octet_array(arrayOfByte, 0, arrayOfByte.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getEncapsulationStream(InputStream paramInputStream) {
/* 112 */     byte[] arrayOfByte = readOctets(paramInputStream);
/* 113 */     EncapsInputStream encapsInputStream = EncapsInputStreamFactory.newEncapsInputStream(paramInputStream.orb(), arrayOfByte, arrayOfByte.length);
/*     */     
/* 115 */     encapsInputStream.consumeEndian();
/* 116 */     return (InputStream)encapsInputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] readOctets(InputStream paramInputStream) {
/* 124 */     int i = paramInputStream.read_ulong();
/* 125 */     byte[] arrayOfByte = new byte[i];
/* 126 */     paramInputStream.read_octet_array(arrayOfByte, 0, i);
/* 127 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeEncapsulation(WriteContents paramWriteContents, OutputStream paramOutputStream) {
/* 134 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream((ORB)paramOutputStream.orb());
/*     */     
/* 136 */     encapsOutputStream.putEndian();
/*     */     
/* 138 */     paramWriteContents.writeContents((OutputStream)encapsOutputStream);
/*     */     
/* 140 */     writeOutputStream((OutputStream)encapsOutputStream, paramOutputStream);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/EncapsulationUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */