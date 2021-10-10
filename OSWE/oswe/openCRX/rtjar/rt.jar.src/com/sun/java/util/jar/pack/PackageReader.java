/*      */ package com.sun.java.util.jar.pack;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class PackageReader
/*      */   extends BandStructure
/*      */ {
/*      */   Package pkg;
/*      */   byte[] bytes;
/*      */   LimitedBuffer in;
/*      */   Package.Version packageVersion;
/*      */   int[] tagCount;
/*      */   int numFiles;
/*      */   int numAttrDefs;
/*      */   int numInnerClasses;
/*      */   int numClasses;
/*      */   static final int MAGIC_BYTES = 4;
/*      */   Map<ConstantPool.Utf8Entry, ConstantPool.SignatureEntry> utf8Signatures;
/*      */   static final int NO_FLAGS_YET = 0;
/*      */   Comparator<ConstantPool.Entry> entryOutputOrder;
/*      */   Code[] allCodes;
/*      */   List<Code> codesWithFlags;
/*      */   Map<Package.Class, Set<ConstantPool.Entry>> ldcRefMap;
/*      */   
/*      */   PackageReader(Package paramPackage, InputStream paramInputStream) throws IOException {
/*  197 */     this.tagCount = new int[19];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1154 */     this.entryOutputOrder = new Comparator<ConstantPool.Entry>() {
/*      */         public int compare(ConstantPool.Entry param1Entry1, ConstantPool.Entry param1Entry2) {
/* 1156 */           int i = PackageReader.this.getOutputIndex(param1Entry1);
/* 1157 */           int j = PackageReader.this.getOutputIndex(param1Entry2);
/* 1158 */           if (i >= 0 && j >= 0)
/*      */           {
/* 1160 */             return i - j; } 
/* 1161 */           if (i == j)
/*      */           {
/* 1163 */             return param1Entry1.compareTo(param1Entry2);
/*      */           }
/* 1165 */           return (i >= 0) ? -1 : 1;
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1352 */     this.ldcRefMap = new HashMap<>(); this.pkg = paramPackage; this.in = new LimitedBuffer(paramInputStream);
/*      */   } static class LimitedBuffer extends BufferedInputStream {
/*      */     long served; int servedPos; long limit; long buffered; public boolean atLimit() { boolean bool = (getBytesServed() == this.limit) ? true : false; assert !bool || this.limit == this.buffered; return bool; } public long getBytesServed() { return this.served + (this.pos - this.servedPos); } public void setReadLimit(long param1Long) { if (param1Long == -1L) { this.limit = -1L; } else { this.limit = getBytesServed() + param1Long; }  } public long getReadLimit() { if (this.limit == -1L) return this.limit;  return this.limit - getBytesServed(); } public int read() throws IOException { if (this.pos < this.count) return this.buf[this.pos++] & 0xFF;  this.served += (this.pos - this.servedPos); int i = super.read(); this.servedPos = this.pos; if (i >= 0) this.served++;  assert this.served <= this.limit || this.limit == -1L; return i; } public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException { this.served += (this.pos - this.servedPos); int i = super.read(param1ArrayOfbyte, param1Int1, param1Int2); this.servedPos = this.pos; if (i >= 0) this.served += i;  return i; } public long skip(long param1Long) throws IOException { throw new RuntimeException("no skipping"); } LimitedBuffer(InputStream param1InputStream) { super((InputStream)null, 16384); this.servedPos = this.pos; this.in = new FilterInputStream(param1InputStream) { public int read() throws IOException { if (PackageReader.LimitedBuffer.this.buffered == PackageReader.LimitedBuffer.this.limit) return -1;  PackageReader.LimitedBuffer.this.buffered++; return super.read(); } public int read(byte[] param2ArrayOfbyte, int param2Int1, int param2Int2) throws IOException { if (PackageReader.LimitedBuffer.this.buffered == PackageReader.LimitedBuffer.this.limit) return -1;  if (PackageReader.LimitedBuffer.this.limit != -1L) { long l = PackageReader.LimitedBuffer.this.limit - PackageReader.LimitedBuffer.this.buffered; if (param2Int2 > l) param2Int2 = (int)l;  }  int i = super.read(param2ArrayOfbyte, param2Int1, param2Int2); if (i >= 0) PackageReader.LimitedBuffer.this.buffered += i;  return i; } }
/* 1355 */         ; } } void read() throws IOException { boolean bool = false; try { readFileHeader(); readBandHeaders(); readConstantPool(); readAttrDefs(); readInnerClasses(); Package.Class[] arrayOfClass = readClasses(); readByteCodes(); readFiles(); assert this.archiveSize1 == 0L || this.in.atLimit(); assert this.archiveSize1 == 0L || this.in.getBytesServed() == this.archiveSize0 + this.archiveSize1; this.all_bands.doneDisbursing(); for (byte b = 0; b < arrayOfClass.length; b++) reconstructClass(arrayOfClass[b]);  bool = true; } catch (Exception exception) { Utils.log.warning("Error on input: " + exception, exception); if (this.verbose > 0) Utils.log.info("Stream offsets: served=" + this.in.getBytesServed() + " buffered=" + this.in.buffered + " limit=" + this.in.limit);  if (exception instanceof IOException) throw (IOException)exception;  if (exception instanceof RuntimeException) throw (RuntimeException)exception;  throw new Error("error unpacking", exception); }  } void readFileHeader() throws IOException { readArchiveMagic(); readArchiveHeader(); } private int getMagicInt32() throws IOException { int i = 0; for (byte b = 0; b < 4; b++) { i <<= 8; i |= this.archive_magic.getByte() & 0xFF; }  return i; } void readArchiveMagic() throws IOException { this.in.setReadLimit(19L); this.archive_magic.expectLength(4); this.archive_magic.readFrom(this.in); int i = getMagicInt32(); this.pkg.getClass(); if (-889270259 != i) { this.pkg.getClass(); throw new IOException("Unexpected package magic number: got " + i + "; expected " + -889270259); }  this.archive_magic.doneDisbursing(); } void checkArchiveVersion() throws IOException { Package.Version version = null; for (Package.Version version1 : new Package.Version[] { Constants.JAVA8_PACKAGE_VERSION, Constants.JAVA7_PACKAGE_VERSION, Constants.JAVA6_PACKAGE_VERSION, Constants.JAVA5_PACKAGE_VERSION }) { if (this.packageVersion.equals(version1)) { version = version1; break; }  }  if (version == null) { String str = Constants.JAVA8_PACKAGE_VERSION.toString() + "OR" + Constants.JAVA7_PACKAGE_VERSION.toString() + " OR " + Constants.JAVA6_PACKAGE_VERSION.toString() + " OR " + Constants.JAVA5_PACKAGE_VERSION.toString(); throw new IOException("Unexpected package minor version: got " + this.packageVersion.toString() + "; expected " + str); }  } void readArchiveHeader() throws IOException { this.archive_header_0.expectLength(3); this.archive_header_0.readFrom(this.in); int i = this.archive_header_0.getInt(); int j = this.archive_header_0.getInt(); this.packageVersion = Package.Version.of(j, i); checkArchiveVersion(); initHighestClassVersion(Constants.JAVA7_MAX_CLASS_VERSION); this.archiveOptions = this.archive_header_0.getInt(); this.archive_header_0.doneDisbursing(); boolean bool1 = testBit(this.archiveOptions, 1); boolean bool2 = testBit(this.archiveOptions, 16); boolean bool3 = testBit(this.archiveOptions, 2); boolean bool4 = testBit(this.archiveOptions, 8); initAttrIndexLimit(); this.archive_header_S.expectLength(bool2 ? 2 : 0); this.archive_header_S.readFrom(this.in); if (bool2) { long l1 = this.archive_header_S.getInt(); long l2 = this.archive_header_S.getInt(); this.archiveSize1 = (l1 << 32L) + (l2 << 32L >>> 32L); this.in.setReadLimit(this.archiveSize1); } else { this.archiveSize1 = 0L; this.in.setReadLimit(-1L); }  this.archive_header_S.doneDisbursing(); this.archiveSize0 = this.in.getBytesServed(); byte b = 10; if (bool2) b += 5;  if (bool1) b += 2;  if (bool3) b += 4;  if (bool4) b += 4;  this.archive_header_1.expectLength(b); this.archive_header_1.readFrom(this.in); if (bool2) { this.archiveNextCount = this.archive_header_1.getInt(); this.pkg.default_modtime = this.archive_header_1.getInt(); this.numFiles = this.archive_header_1.getInt(); } else { this.archiveNextCount = 0; this.numFiles = 0; }  if (bool1) { this.band_headers.expectLength(this.archive_header_1.getInt()); this.numAttrDefs = this.archive_header_1.getInt(); } else { this.band_headers.expectLength(0); this.numAttrDefs = 0; }  readConstantPoolCounts(bool3, bool4); this.numInnerClasses = this.archive_header_1.getInt(); i = (short)this.archive_header_1.getInt(); j = (short)this.archive_header_1.getInt(); this.pkg.defaultClassVersion = Package.Version.of(j, i); this.numClasses = this.archive_header_1.getInt(); this.archive_header_1.doneDisbursing(); if (testBit(this.archiveOptions, 32)) this.pkg.default_options |= 0x1;  } void readBandHeaders() throws IOException { this.band_headers.readFrom(this.in); this.bandHeaderBytePos = 1; this.bandHeaderBytes = new byte[this.bandHeaderBytePos + this.band_headers.length()]; for (int i = this.bandHeaderBytePos; i < this.bandHeaderBytes.length; i++) this.bandHeaderBytes[i] = (byte)this.band_headers.getByte();  this.band_headers.doneDisbursing(); } void readConstantPoolCounts(boolean paramBoolean1, boolean paramBoolean2) throws IOException { for (byte b = 0; b < ConstantPool.TAGS_IN_ORDER.length; ) { byte b1 = ConstantPool.TAGS_IN_ORDER[b]; if (!paramBoolean1) { switch (b1) { case 3: case 4: case 5: case 6: break;default: if (!paramBoolean2) switch (b1) { case 15: case 16: case 17: case 18: break; }   this.tagCount[b1] = this.archive_header_1.getInt(); break; }  b++; }  }  } protected ConstantPool.Index getCPIndex(byte paramByte) { return this.pkg.cp.getIndexByTag(paramByte); } ConstantPool.Index initCPIndex(byte paramByte, ConstantPool.Entry[] paramArrayOfEntry) { if (this.verbose > 3) for (byte b = 0; b < paramArrayOfEntry.length; b++) Utils.log.fine("cp.add " + paramArrayOfEntry[b]);   ConstantPool.Index index = ConstantPool.makeIndex(ConstantPool.tagName(paramByte), paramArrayOfEntry); if (this.verbose > 1) Utils.log.fine("Read " + index);  this.pkg.cp.initIndexByTag(paramByte, index); return index; } void checkLegacy(String paramString) { if (this.packageVersion.lessThan(Constants.JAVA7_PACKAGE_VERSION)) throw new RuntimeException("unexpected band " + paramString);  } Code[] buildCodeAttrs(List<Package.Class.Method> paramList) { ArrayList<Code> arrayList = new ArrayList(paramList.size());
/* 1356 */     for (Package.Class.Method method : paramList) {
/* 1357 */       if (method.getAttribute(this.attrCodeEmpty) != null) {
/* 1358 */         method.code = new Code(method);
/* 1359 */         arrayList.add(method.code);
/*      */       } 
/*      */     } 
/* 1362 */     Code[] arrayOfCode = new Code[arrayList.size()];
/* 1363 */     arrayList.toArray(arrayOfCode);
/* 1364 */     return arrayOfCode; }
/*      */   void readConstantPool() throws IOException { if (this.verbose > 0) Utils.log.info("Reading CP");  byte b; for (b = 0; b < ConstantPool.TAGS_IN_ORDER.length; b++) { int j; byte b2; byte b1 = ConstantPool.TAGS_IN_ORDER[b]; int i = this.tagCount[b1]; ConstantPool.Entry[] arrayOfEntry = new ConstantPool.Entry[i]; if (this.verbose > 0) Utils.log.info("Reading " + arrayOfEntry.length + " " + ConstantPool.tagName(b1) + " entries...");  switch (b1) { case 1: readUtf8Bands(arrayOfEntry); break;case 3: this.cp_Int.expectLength(arrayOfEntry.length); this.cp_Int.readFrom(this.in); for (j = 0; j < arrayOfEntry.length; j++) { int k = this.cp_Int.getInt(); arrayOfEntry[j] = ConstantPool.getLiteralEntry(Integer.valueOf(k)); }  this.cp_Int.doneDisbursing(); break;case 4: this.cp_Float.expectLength(arrayOfEntry.length); this.cp_Float.readFrom(this.in); for (j = 0; j < arrayOfEntry.length; j++) { int k = this.cp_Float.getInt(); float f = Float.intBitsToFloat(k); arrayOfEntry[j] = ConstantPool.getLiteralEntry(Float.valueOf(f)); }  this.cp_Float.doneDisbursing(); break;case 5: this.cp_Long_hi.expectLength(arrayOfEntry.length); this.cp_Long_hi.readFrom(this.in); this.cp_Long_lo.expectLength(arrayOfEntry.length); this.cp_Long_lo.readFrom(this.in); for (j = 0; j < arrayOfEntry.length; j++) { long l1 = this.cp_Long_hi.getInt(); long l2 = this.cp_Long_lo.getInt(); long l3 = (l1 << 32L) + (l2 << 32L >>> 32L); arrayOfEntry[j] = ConstantPool.getLiteralEntry(Long.valueOf(l3)); }  this.cp_Long_hi.doneDisbursing(); this.cp_Long_lo.doneDisbursing(); break;case 6: this.cp_Double_hi.expectLength(arrayOfEntry.length); this.cp_Double_hi.readFrom(this.in); this.cp_Double_lo.expectLength(arrayOfEntry.length); this.cp_Double_lo.readFrom(this.in); for (j = 0; j < arrayOfEntry.length; j++) { long l1 = this.cp_Double_hi.getInt(); long l2 = this.cp_Double_lo.getInt(); long l3 = (l1 << 32L) + (l2 << 32L >>> 32L); double d = Double.longBitsToDouble(l3); arrayOfEntry[j] = ConstantPool.getLiteralEntry(Double.valueOf(d)); }  this.cp_Double_hi.doneDisbursing(); this.cp_Double_lo.doneDisbursing(); break;case 8: this.cp_String.expectLength(arrayOfEntry.length); this.cp_String.readFrom(this.in); this.cp_String.setIndex(getCPIndex((byte)1)); for (j = 0; j < arrayOfEntry.length; j++) arrayOfEntry[j] = ConstantPool.getLiteralEntry(this.cp_String.getRef().stringValue());  this.cp_String.doneDisbursing(); break;case 7: this.cp_Class.expectLength(arrayOfEntry.length); this.cp_Class.readFrom(this.in); this.cp_Class.setIndex(getCPIndex((byte)1)); for (j = 0; j < arrayOfEntry.length; j++) arrayOfEntry[j] = ConstantPool.getClassEntry(this.cp_Class.getRef().stringValue());  this.cp_Class.doneDisbursing(); break;case 13: readSignatureBands(arrayOfEntry); break;case 12: this.cp_Descr_name.expectLength(arrayOfEntry.length); this.cp_Descr_name.readFrom(this.in); this.cp_Descr_name.setIndex(getCPIndex((byte)1)); this.cp_Descr_type.expectLength(arrayOfEntry.length); this.cp_Descr_type.readFrom(this.in); this.cp_Descr_type.setIndex(getCPIndex((byte)13)); for (j = 0; j < arrayOfEntry.length; j++) { ConstantPool.Entry entry1 = this.cp_Descr_name.getRef(); ConstantPool.Entry entry2 = this.cp_Descr_type.getRef(); arrayOfEntry[j] = ConstantPool.getDescriptorEntry((ConstantPool.Utf8Entry)entry1, (ConstantPool.SignatureEntry)entry2); }  this.cp_Descr_name.doneDisbursing(); this.cp_Descr_type.doneDisbursing(); break;case 9: readMemberRefs(b1, arrayOfEntry, this.cp_Field_class, this.cp_Field_desc); break;case 10: readMemberRefs(b1, arrayOfEntry, this.cp_Method_class, this.cp_Method_desc); break;case 11: readMemberRefs(b1, arrayOfEntry, this.cp_Imethod_class, this.cp_Imethod_desc); break;case 15: if (arrayOfEntry.length > 0) checkLegacy(this.cp_MethodHandle_refkind.name());  this.cp_MethodHandle_refkind.expectLength(arrayOfEntry.length); this.cp_MethodHandle_refkind.readFrom(this.in); this.cp_MethodHandle_member.expectLength(arrayOfEntry.length); this.cp_MethodHandle_member.readFrom(this.in); this.cp_MethodHandle_member.setIndex(getCPIndex((byte)52)); for (j = 0; j < arrayOfEntry.length; j++) { byte b3 = (byte)this.cp_MethodHandle_refkind.getInt(); ConstantPool.MemberEntry memberEntry = (ConstantPool.MemberEntry)this.cp_MethodHandle_member.getRef(); arrayOfEntry[j] = ConstantPool.getMethodHandleEntry(b3, memberEntry); }  this.cp_MethodHandle_refkind.doneDisbursing(); this.cp_MethodHandle_member.doneDisbursing(); break;case 16: if (arrayOfEntry.length > 0) checkLegacy(this.cp_MethodType.name());  this.cp_MethodType.expectLength(arrayOfEntry.length); this.cp_MethodType.readFrom(this.in); this.cp_MethodType.setIndex(getCPIndex((byte)13)); for (j = 0; j < arrayOfEntry.length; j++) { ConstantPool.SignatureEntry signatureEntry = (ConstantPool.SignatureEntry)this.cp_MethodType.getRef(); arrayOfEntry[j] = ConstantPool.getMethodTypeEntry(signatureEntry); }  this.cp_MethodType.doneDisbursing(); break;case 18: if (arrayOfEntry.length > 0) checkLegacy(this.cp_InvokeDynamic_spec.name());  this.cp_InvokeDynamic_spec.expectLength(arrayOfEntry.length); this.cp_InvokeDynamic_spec.readFrom(this.in); this.cp_InvokeDynamic_spec.setIndex(getCPIndex((byte)17)); this.cp_InvokeDynamic_desc.expectLength(arrayOfEntry.length); this.cp_InvokeDynamic_desc.readFrom(this.in); this.cp_InvokeDynamic_desc.setIndex(getCPIndex((byte)12)); for (j = 0; j < arrayOfEntry.length; j++) { ConstantPool.BootstrapMethodEntry bootstrapMethodEntry = (ConstantPool.BootstrapMethodEntry)this.cp_InvokeDynamic_spec.getRef(); ConstantPool.DescriptorEntry descriptorEntry = (ConstantPool.DescriptorEntry)this.cp_InvokeDynamic_desc.getRef(); arrayOfEntry[j] = ConstantPool.getInvokeDynamicEntry(bootstrapMethodEntry, descriptorEntry); }  this.cp_InvokeDynamic_spec.doneDisbursing(); this.cp_InvokeDynamic_desc.doneDisbursing(); break;case 17: if (arrayOfEntry.length > 0) checkLegacy(this.cp_BootstrapMethod_ref.name());  this.cp_BootstrapMethod_ref.expectLength(arrayOfEntry.length); this.cp_BootstrapMethod_ref.readFrom(this.in); this.cp_BootstrapMethod_ref.setIndex(getCPIndex((byte)15)); this.cp_BootstrapMethod_arg_count.expectLength(arrayOfEntry.length); this.cp_BootstrapMethod_arg_count.readFrom(this.in); j = this.cp_BootstrapMethod_arg_count.getIntTotal(); this.cp_BootstrapMethod_arg.expectLength(j); this.cp_BootstrapMethod_arg.readFrom(this.in); this.cp_BootstrapMethod_arg.setIndex(getCPIndex((byte)51)); for (b2 = 0; b2 < arrayOfEntry.length; b2++) { ConstantPool.MethodHandleEntry methodHandleEntry = (ConstantPool.MethodHandleEntry)this.cp_BootstrapMethod_ref.getRef(); int k = this.cp_BootstrapMethod_arg_count.getInt(); ConstantPool.Entry[] arrayOfEntry1 = new ConstantPool.Entry[k]; for (byte b3 = 0; b3 < k; b3++)
/*      */               arrayOfEntry1[b3] = this.cp_BootstrapMethod_arg.getRef();  arrayOfEntry[b2] = ConstantPool.getBootstrapMethodEntry(methodHandleEntry, arrayOfEntry1); }  this.cp_BootstrapMethod_ref.doneDisbursing(); this.cp_BootstrapMethod_arg_count.doneDisbursing(); this.cp_BootstrapMethod_arg.doneDisbursing(); break;default: throw new AssertionError("unexpected CP tag in package"); }  ConstantPool.Index index = initCPIndex(b1, arrayOfEntry); if (this.optDumpBands)
/*      */         try (PrintStream null = new PrintStream(getDumpStream(index, ".idx"))) { printArrayTo(printStream, index.cpMap, 0, index.cpMap.length); }   }  this.cp_bands.doneDisbursing(); if (this.optDumpBands || this.verbose > 1)
/*      */       for (b = 50; b < 54; b = (byte)(b + 1)) { ConstantPool.Index index = this.pkg.cp.getIndexByTag(b); if (index != null && !index.isEmpty()) { ConstantPool.Entry[] arrayOfEntry = index.cpMap; if (this.verbose > 1)
/*      */             Utils.log.info("Index group " + ConstantPool.tagName(b) + " contains " + arrayOfEntry.length + " entries.");  if (this.optDumpBands)
/*      */             try (PrintStream null = new PrintStream(getDumpStream(index.debugName, b, ".gidx", index))) { printArrayTo(printStream, arrayOfEntry, 0, arrayOfEntry.length, true); }   }  }   setBandIndexes(); }
/*      */   void readUtf8Bands(ConstantPool.Entry[] paramArrayOfEntry) throws IOException { int i = paramArrayOfEntry.length; if (i == 0)
/*      */       return;  this.cp_Utf8_prefix.expectLength(Math.max(0, i - 2)); this.cp_Utf8_prefix.readFrom(this.in); this.cp_Utf8_suffix.expectLength(Math.max(0, i - 1)); this.cp_Utf8_suffix.readFrom(this.in); char[][] arrayOfChar = new char[i][]; byte b1 = 0; this.cp_Utf8_chars.expectLength(this.cp_Utf8_suffix.getIntTotal()); this.cp_Utf8_chars.readFrom(this.in); int j; for (j = 0; j < i; j++) { byte b = (j < 1) ? 0 : this.cp_Utf8_suffix.getInt(); if (!b && j >= 1) { b1++; } else { arrayOfChar[j] = new char[b]; for (byte b4 = 0; b4 < b; b4++) { int k = this.cp_Utf8_chars.getInt(); assert k == (char)k; arrayOfChar[j][b4] = (char)k; }  }  }  this.cp_Utf8_chars.doneDisbursing(); j = 0; this.cp_Utf8_big_suffix.expectLength(b1); this.cp_Utf8_big_suffix.readFrom(this.in); this.cp_Utf8_suffix.resetForSecondPass(); for (byte b2 = 0; b2 < i; b2++) { int k = (b2 < 1) ? 0 : this.cp_Utf8_suffix.getInt(); byte b = (b2 < 2) ? 0 : this.cp_Utf8_prefix.getInt(); if (!k && b2 >= 1) { assert arrayOfChar[b2] == null; k = this.cp_Utf8_big_suffix.getInt(); } else { assert arrayOfChar[b2] != null; }  if (j < b + k)
/*      */         j = b + k;  }  char[] arrayOfChar1 = new char[j]; this.cp_Utf8_suffix.resetForSecondPass(); this.cp_Utf8_big_suffix.resetForSecondPass(); byte b3; for (b3 = 0; b3 < i; b3++) { if (b3 >= 1) { int k = this.cp_Utf8_suffix.getInt(); if (k == 0) { k = this.cp_Utf8_big_suffix.getInt(); arrayOfChar[b3] = new char[k]; if (k != 0) { BandStructure.IntBand intBand = this.cp_Utf8_big_chars.newIntBand("(Utf8_big_" + b3 + ")"); intBand.expectLength(k); intBand.readFrom(this.in); for (byte b = 0; b < k; b++) { int m = intBand.getInt(); assert m == (char)m; arrayOfChar[b3][b] = (char)m; }  intBand.doneDisbursing(); }  }  }  }  this.cp_Utf8_big_chars.doneDisbursing(); this.cp_Utf8_prefix.resetForSecondPass(); this.cp_Utf8_suffix.resetForSecondPass(); this.cp_Utf8_big_suffix.resetForSecondPass(); for (b3 = 0; b3 < i; b3++) { byte b = (b3 < 2) ? 0 : this.cp_Utf8_prefix.getInt(); int k = (b3 < 1) ? 0 : this.cp_Utf8_suffix.getInt(); if (!k && b3 >= 1)
/*      */         k = this.cp_Utf8_big_suffix.getInt();  System.arraycopy(arrayOfChar[b3], 0, arrayOfChar1, b, k); paramArrayOfEntry[b3] = ConstantPool.getUtf8Entry(new String(arrayOfChar1, 0, b + k)); }  this.cp_Utf8_prefix.doneDisbursing(); this.cp_Utf8_suffix.doneDisbursing(); this.cp_Utf8_big_suffix.doneDisbursing(); }
/*      */   void readSignatureBands(ConstantPool.Entry[] paramArrayOfEntry) throws IOException { this.cp_Signature_form.expectLength(paramArrayOfEntry.length); this.cp_Signature_form.readFrom(this.in); this.cp_Signature_form.setIndex(getCPIndex((byte)1)); int[] arrayOfInt = new int[paramArrayOfEntry.length]; byte b; for (b = 0; b < paramArrayOfEntry.length; b++) { ConstantPool.Utf8Entry utf8Entry = (ConstantPool.Utf8Entry)this.cp_Signature_form.getRef(); arrayOfInt[b] = ConstantPool.countClassParts(utf8Entry); }  this.cp_Signature_form.resetForSecondPass(); this.cp_Signature_classes.expectLength(getIntTotal(arrayOfInt)); this.cp_Signature_classes.readFrom(this.in); this.cp_Signature_classes.setIndex(getCPIndex((byte)7)); this.utf8Signatures = new HashMap<>(); for (b = 0; b < paramArrayOfEntry.length; b++) { ConstantPool.Utf8Entry utf8Entry = (ConstantPool.Utf8Entry)this.cp_Signature_form.getRef(); ConstantPool.ClassEntry[] arrayOfClassEntry = new ConstantPool.ClassEntry[arrayOfInt[b]]; for (byte b1 = 0; b1 < arrayOfClassEntry.length; b1++)
/* 1376 */         arrayOfClassEntry[b1] = (ConstantPool.ClassEntry)this.cp_Signature_classes.getRef();  ConstantPool.SignatureEntry signatureEntry = ConstantPool.getSignatureEntry(utf8Entry, arrayOfClassEntry); paramArrayOfEntry[b] = signatureEntry; this.utf8Signatures.put(signatureEntry.asUtf8Entry(), signatureEntry); }  this.cp_Signature_form.doneDisbursing(); this.cp_Signature_classes.doneDisbursing(); } void readCodeHeaders() throws IOException { boolean bool = testBit(this.archiveOptions, 4);
/* 1377 */     this.code_headers.expectLength(this.allCodes.length);
/* 1378 */     this.code_headers.readFrom(this.in);
/* 1379 */     ArrayList<Code> arrayList = new ArrayList(this.allCodes.length / 10);
/* 1380 */     for (byte b = 0; b < this.allCodes.length; b++) {
/* 1381 */       Code code = this.allCodes[b];
/* 1382 */       int i = this.code_headers.getByte();
/* 1383 */       assert i == (i & 0xFF);
/* 1384 */       if (this.verbose > 2)
/* 1385 */         Utils.log.fine("codeHeader " + code + " = " + i); 
/* 1386 */       if (i == 0) {
/*      */         
/* 1388 */         arrayList.add(code);
/*      */       }
/*      */       else {
/*      */         
/* 1392 */         code.setMaxStack(shortCodeHeader_max_stack(i));
/* 1393 */         code.setMaxNALocals(shortCodeHeader_max_na_locals(i));
/* 1394 */         code.setHandlerCount(shortCodeHeader_handler_count(i));
/* 1395 */         assert shortCodeHeader(code) == i;
/*      */       } 
/* 1397 */     }  this.code_headers.doneDisbursing();
/* 1398 */     this.code_max_stack.expectLength(arrayList.size());
/* 1399 */     this.code_max_na_locals.expectLength(arrayList.size());
/* 1400 */     this.code_handler_count.expectLength(arrayList.size());
/*      */ 
/*      */     
/* 1403 */     this.code_max_stack.readFrom(this.in);
/* 1404 */     this.code_max_na_locals.readFrom(this.in);
/* 1405 */     this.code_handler_count.readFrom(this.in);
/* 1406 */     for (Code code : arrayList) {
/* 1407 */       code.setMaxStack(this.code_max_stack.getInt());
/* 1408 */       code.setMaxNALocals(this.code_max_na_locals.getInt());
/* 1409 */       code.setHandlerCount(this.code_handler_count.getInt());
/*      */     } 
/* 1411 */     this.code_max_stack.doneDisbursing();
/* 1412 */     this.code_max_na_locals.doneDisbursing();
/* 1413 */     this.code_handler_count.doneDisbursing();
/*      */     
/* 1415 */     readCodeHandlers();
/*      */     
/* 1417 */     if (bool) {
/*      */       
/* 1419 */       this.codesWithFlags = Arrays.asList(this.allCodes);
/*      */     } else {
/*      */       
/* 1422 */       this.codesWithFlags = arrayList;
/*      */     } 
/* 1424 */     countAttrs(3, (Collection)this.codesWithFlags); }
/*      */   void readMemberRefs(byte paramByte, ConstantPool.Entry[] paramArrayOfEntry, BandStructure.CPRefBand paramCPRefBand1, BandStructure.CPRefBand paramCPRefBand2) throws IOException { paramCPRefBand1.expectLength(paramArrayOfEntry.length); paramCPRefBand1.readFrom(this.in); paramCPRefBand1.setIndex(getCPIndex((byte)7)); paramCPRefBand2.expectLength(paramArrayOfEntry.length); paramCPRefBand2.readFrom(this.in); paramCPRefBand2.setIndex(getCPIndex((byte)12)); for (byte b = 0; b < paramArrayOfEntry.length; b++) { ConstantPool.ClassEntry classEntry = (ConstantPool.ClassEntry)paramCPRefBand1.getRef(); ConstantPool.DescriptorEntry descriptorEntry = (ConstantPool.DescriptorEntry)paramCPRefBand2.getRef(); paramArrayOfEntry[b] = ConstantPool.getMemberEntry(paramByte, classEntry, descriptorEntry); }  paramCPRefBand1.doneDisbursing(); paramCPRefBand2.doneDisbursing(); }
/*      */   void readFiles() throws IOException { if (this.verbose > 0) Utils.log.info("  ...building " + this.numFiles + " files...");  this.file_name.expectLength(this.numFiles); this.file_size_lo.expectLength(this.numFiles); int i = this.archiveOptions; boolean bool1 = testBit(i, 256); boolean bool2 = testBit(i, 64); boolean bool3 = testBit(i, 128); if (bool1) this.file_size_hi.expectLength(this.numFiles);  if (bool2) this.file_modtime.expectLength(this.numFiles);  if (bool3) this.file_options.expectLength(this.numFiles);  this.file_name.readFrom(this.in); this.file_size_hi.readFrom(this.in); this.file_size_lo.readFrom(this.in); this.file_modtime.readFrom(this.in); this.file_options.readFrom(this.in); this.file_bits.setInputStreamFrom(this.in); Iterator<Package.Class> iterator = this.pkg.getClasses().iterator(); long l = 0L; long[] arrayOfLong = new long[this.numFiles]; for (byte b1 = 0; b1 < this.numFiles; b1++) { long l1 = this.file_size_lo.getInt() << 32L >>> 32L; if (bool1) l1 += this.file_size_hi.getInt() << 32L;  arrayOfLong[b1] = l1; l += l1; }  assert this.in.getReadLimit() == -1L || this.in.getReadLimit() == l; byte[] arrayOfByte = new byte[65536]; for (byte b2 = 0; b2 < this.numFiles; b2++) { ConstantPool.Utf8Entry utf8Entry = (ConstantPool.Utf8Entry)this.file_name.getRef(); long l1 = arrayOfLong[b2]; this.pkg.getClass(); Package.File file = new Package.File(this.pkg, utf8Entry); file.modtime = this.pkg.default_modtime; file.options = this.pkg.default_options; if (bool2) file.modtime += this.file_modtime.getInt();  if (bool3) file.options |= this.file_options.getInt();  if (this.verbose > 1) Utils.log.fine("Reading " + l1 + " bytes of " + utf8Entry.stringValue());  long l2 = l1; while (l2 > 0L) { int j = arrayOfByte.length; if (j > l2) j = (int)l2;  j = this.file_bits.getInputStream().read(arrayOfByte, 0, j); if (j < 0) throw new EOFException();  file.addBytes(arrayOfByte, 0, j); l2 -= j; }  this.pkg.addFile(file); if (file.isClassStub()) { assert file.getFileLength() == 0L; Package.Class clazz = iterator.next(); clazz.initFile(file); }  }  while (iterator.hasNext()) { Package.Class clazz = iterator.next(); clazz.initFile((Package.File)null); clazz.file.modtime = this.pkg.default_modtime; }  this.file_name.doneDisbursing(); this.file_size_hi.doneDisbursing(); this.file_size_lo.doneDisbursing(); this.file_modtime.doneDisbursing(); this.file_options.doneDisbursing(); this.file_bits.doneDisbursing(); this.file_bands.doneDisbursing(); if (this.archiveSize1 != 0L && !this.in.atLimit()) throw new RuntimeException("Predicted archive_size " + this.archiveSize1 + " != " + (this.in.getBytesServed() - this.archiveSize0));  }
/*      */   void readAttrDefs() throws IOException { this.attr_definition_headers.expectLength(this.numAttrDefs); this.attr_definition_name.expectLength(this.numAttrDefs); this.attr_definition_layout.expectLength(this.numAttrDefs); this.attr_definition_headers.readFrom(this.in); this.attr_definition_name.readFrom(this.in); this.attr_definition_layout.readFrom(this.in); try (PrintStream null = !this.optDumpBands ? null : new PrintStream(getDumpStream(this.attr_definition_headers, ".def"))) { for (byte b = 0; b < this.numAttrDefs; b++) { int i = this.attr_definition_headers.getByte(); ConstantPool.Utf8Entry utf8Entry1 = (ConstantPool.Utf8Entry)this.attr_definition_name.getRef(); ConstantPool.Utf8Entry utf8Entry2 = (ConstantPool.Utf8Entry)this.attr_definition_layout.getRef(); int j = i & 0x3; int k = (i >> 2) - 1; Attribute.Layout layout = new Attribute.Layout(j, utf8Entry1.stringValue(), utf8Entry2.stringValue()); String str = layout.layoutForClassVersion(getHighestClassVersion()); if (!str.equals(layout.layout())) throw new IOException("Bad attribute layout in archive: " + layout.layout());  setAttributeLayoutIndex(layout, k); if (printStream != null) printStream.println(k + " " + layout);  }  }  this.attr_definition_headers.doneDisbursing(); this.attr_definition_name.doneDisbursing(); this.attr_definition_layout.doneDisbursing(); makeNewAttributeBands(); this.attr_definition_bands.doneDisbursing(); }
/*      */   void readInnerClasses() throws IOException { this.ic_this_class.expectLength(this.numInnerClasses); this.ic_this_class.readFrom(this.in); this.ic_flags.expectLength(this.numInnerClasses); this.ic_flags.readFrom(this.in); byte b1 = 0; for (byte b2 = 0; b2 < this.numInnerClasses; b2++) { int i = this.ic_flags.getInt(); boolean bool = ((i & 0x10000) != 0) ? true : false; if (bool) b1++;  }  this.ic_outer_class.expectLength(b1); this.ic_outer_class.readFrom(this.in); this.ic_name.expectLength(b1); this.ic_name.readFrom(this.in); this.ic_flags.resetForSecondPass(); ArrayList<Package.InnerClass> arrayList = new ArrayList(this.numInnerClasses); for (byte b3 = 0; b3 < this.numInnerClasses; b3++) { ConstantPool.ClassEntry classEntry2; ConstantPool.Utf8Entry utf8Entry; int i = this.ic_flags.getInt(); boolean bool = ((i & 0x10000) != 0) ? true : false; i &= 0xFFFEFFFF; ConstantPool.ClassEntry classEntry1 = (ConstantPool.ClassEntry)this.ic_this_class.getRef(); if (bool) { classEntry2 = (ConstantPool.ClassEntry)this.ic_outer_class.getRef(); utf8Entry = (ConstantPool.Utf8Entry)this.ic_name.getRef(); } else { String str1 = classEntry1.stringValue(); String[] arrayOfString = Package.parseInnerClassName(str1); assert arrayOfString != null; String str2 = arrayOfString[0]; String str3 = arrayOfString[2]; if (str2 == null) { classEntry2 = null; } else { classEntry2 = ConstantPool.getClassEntry(str2); }  if (str3 == null) { utf8Entry = null; } else { utf8Entry = ConstantPool.getUtf8Entry(str3); }  }  Package.InnerClass innerClass = new Package.InnerClass(classEntry1, classEntry2, utf8Entry, i); assert bool || innerClass.predictable; arrayList.add(innerClass); }  this.ic_flags.doneDisbursing(); this.ic_this_class.doneDisbursing(); this.ic_outer_class.doneDisbursing(); this.ic_name.doneDisbursing(); this.pkg.setAllInnerClasses(arrayList); this.ic_bands.doneDisbursing(); }
/*      */   void readLocalInnerClasses(Package.Class paramClass) throws IOException { int i = this.class_InnerClasses_N.getInt(); ArrayList<Package.InnerClass> arrayList = new ArrayList(i); for (byte b = 0; b < i; b++) { ConstantPool.ClassEntry classEntry = (ConstantPool.ClassEntry)this.class_InnerClasses_RC.getRef(); int j = this.class_InnerClasses_F.getInt(); if (j == 0) { Package.InnerClass innerClass = this.pkg.getGlobalInnerClass(classEntry); assert innerClass != null; arrayList.add(innerClass); } else { if (j == 65536) j = 0;  ConstantPool.ClassEntry classEntry1 = (ConstantPool.ClassEntry)this.class_InnerClasses_outer_RCN.getRef(); ConstantPool.Utf8Entry utf8Entry = (ConstantPool.Utf8Entry)this.class_InnerClasses_name_RUN.getRef(); arrayList.add(new Package.InnerClass(classEntry, classEntry1, utf8Entry, j)); }  }  paramClass.setInnerClasses(arrayList); }
/*      */   Package.Class[] readClasses() throws IOException { Package.Class[] arrayOfClass = new Package.Class[this.numClasses]; if (this.verbose > 0) Utils.log.info("  ...building " + arrayOfClass.length + " classes...");  this.class_this.expectLength(this.numClasses); this.class_super.expectLength(this.numClasses); this.class_interface_count.expectLength(this.numClasses); this.class_this.readFrom(this.in); this.class_super.readFrom(this.in); this.class_interface_count.readFrom(this.in); this.class_interface.expectLength(this.class_interface_count.getIntTotal()); this.class_interface.readFrom(this.in); for (byte b = 0; b < arrayOfClass.length; b++) { ConstantPool.ClassEntry classEntry1 = (ConstantPool.ClassEntry)this.class_this.getRef(); ConstantPool.ClassEntry classEntry2 = (ConstantPool.ClassEntry)this.class_super.getRef(); ConstantPool.ClassEntry[] arrayOfClassEntry = new ConstantPool.ClassEntry[this.class_interface_count.getInt()]; for (byte b1 = 0; b1 < arrayOfClassEntry.length; b1++) arrayOfClassEntry[b1] = (ConstantPool.ClassEntry)this.class_interface.getRef();  if (classEntry2 == classEntry1) classEntry2 = null;  this.pkg.getClass(); Package.Class clazz = new Package.Class(this.pkg, 0, classEntry1, classEntry2, arrayOfClassEntry); arrayOfClass[b] = clazz; }  this.class_this.doneDisbursing(); this.class_super.doneDisbursing(); this.class_interface_count.doneDisbursing(); this.class_interface.doneDisbursing(); readMembers(arrayOfClass); countAndReadAttrs(0, Arrays.asList((Attribute.Holder[])arrayOfClass)); this.pkg.trimToSize(); readCodeHeaders(); return arrayOfClass; }
/*      */   private int getOutputIndex(ConstantPool.Entry paramEntry) { assert paramEntry.tag != 13; int i = this.pkg.cp.untypedIndexOf(paramEntry); if (i >= 0) return i;  if (paramEntry.tag == 1) { ConstantPool.Entry entry = this.utf8Signatures.get(paramEntry); return this.pkg.cp.untypedIndexOf(entry); }  return -1; }
/*      */   void reconstructClass(Package.Class paramClass) { if (this.verbose > 1) Utils.log.fine("reconstruct " + paramClass);  Attribute attribute = paramClass.getAttribute(this.attrClassFileVersion); if (attribute != null) { paramClass.removeAttribute(attribute); paramClass.version = parseClassFileVersionAttr(attribute); } else { paramClass.version = this.pkg.defaultClassVersion; }  paramClass.expandSourceFile(); paramClass.setCPMap(reconstructLocalCPMap(paramClass)); }
/*      */   ConstantPool.Entry[] reconstructLocalCPMap(Package.Class paramClass) { Set<?> set = this.ldcRefMap.get(paramClass); HashSet<ConstantPool.Entry> hashSet1 = new HashSet(); paramClass.visitRefs(0, hashSet1); ArrayList<ConstantPool.BootstrapMethodEntry> arrayList = new ArrayList(); paramClass.addAttribute(Package.attrBootstrapMethodsEmpty.canonicalInstance()); ConstantPool.completeReferencesIn(hashSet1, true, arrayList); int i = paramClass.expandLocalICs(); if (i != 0) { if (i > 0) { paramClass.visitInnerClassRefs(0, hashSet1); } else { hashSet1.clear(); paramClass.visitRefs(0, hashSet1); }  ConstantPool.completeReferencesIn(hashSet1, true, arrayList); }  if (arrayList.isEmpty()) { paramClass.attributes.remove(Package.attrBootstrapMethodsEmpty.canonicalInstance()); } else { hashSet1.add(Package.getRefString("BootstrapMethods")); Collections.sort(arrayList); paramClass.setBootstrapMethods(arrayList); }  byte b1 = 0; for (ConstantPool.Entry entry : hashSet1) { if (entry.isDoubleWord()) b1++;  }  ConstantPool.Entry[] arrayOfEntry = new ConstantPool.Entry[1 + b1 + hashSet1.size()]; byte b2 = 1; if (set != null) { assert hashSet1.containsAll(set); for (ConstantPool.Entry entry : set) arrayOfEntry[b2++] = entry;  assert b2 == 1 + set.size(); hashSet1.removeAll(set); set = null; }  HashSet<ConstantPool.Entry> hashSet2 = hashSet1; hashSet1 = null; byte b3 = b2; for (ConstantPool.Entry entry : hashSet2) arrayOfEntry[b2++] = entry;  assert b2 == b3 + hashSet2.size(); Arrays.sort(arrayOfEntry, 1, b3, this.entryOutputOrder); Arrays.sort(arrayOfEntry, b3, b2, this.entryOutputOrder); if (this.verbose > 3) { Utils.log.fine("CP of " + this + " {"); for (byte b = 0; b < b2; b++) { ConstantPool.Entry entry = arrayOfEntry[b]; Utils.log.fine("  " + ((entry == null) ? -1 : getOutputIndex(entry)) + " : " + entry); }  Utils.log.fine("}"); }  int j = arrayOfEntry.length; for (byte b4 = b2; --b4 >= 1; ) { ConstantPool.Entry entry = arrayOfEntry[b4]; if (entry.isDoubleWord())
/*      */         arrayOfEntry[--j] = null;  arrayOfEntry[--j] = entry; }  assert j == 1; return arrayOfEntry; }
/*      */   void readMembers(Package.Class[] paramArrayOfClass) throws IOException { assert paramArrayOfClass.length == this.numClasses; this.class_field_count.expectLength(this.numClasses); this.class_method_count.expectLength(this.numClasses); this.class_field_count.readFrom(this.in); this.class_method_count.readFrom(this.in); int i = this.class_field_count.getIntTotal(); int j = this.class_method_count.getIntTotal(); this.field_descr.expectLength(i); this.method_descr.expectLength(j); if (this.verbose > 1)
/* 1436 */       Utils.log.fine("expecting #fields=" + i + " and #methods=" + j + " in #classes=" + this.numClasses);  ArrayList<Package.Class.Field> arrayList = new ArrayList(i); this.field_descr.readFrom(this.in); for (byte b1 = 0; b1 < paramArrayOfClass.length; b1++) { Package.Class clazz = paramArrayOfClass[b1]; int k = this.class_field_count.getInt(); for (byte b = 0; b < k; b++) { clazz.getClass(); Package.Class.Field field = new Package.Class.Field(clazz, 0, (ConstantPool.DescriptorEntry)this.field_descr.getRef()); arrayList.add(field); }  }  this.class_field_count.doneDisbursing(); this.field_descr.doneDisbursing(); countAndReadAttrs(1, (Collection)arrayList); arrayList = null; ArrayList<Package.Class.Method> arrayList1 = new ArrayList(j); this.method_descr.readFrom(this.in); for (byte b2 = 0; b2 < paramArrayOfClass.length; b2++) { Package.Class clazz = paramArrayOfClass[b2]; int k = this.class_method_count.getInt(); for (byte b = 0; b < k; b++) { clazz.getClass(); Package.Class.Method method = new Package.Class.Method(clazz, 0, (ConstantPool.DescriptorEntry)this.method_descr.getRef()); arrayList1.add(method); }  }  this.class_method_count.doneDisbursing(); this.method_descr.doneDisbursing(); countAndReadAttrs(2, (Collection)arrayList1); this.allCodes = buildCodeAttrs(arrayList1); } void readCodeHandlers() throws IOException { int i = 0;
/* 1437 */     for (byte b1 = 0; b1 < this.allCodes.length; b1++) {
/* 1438 */       Code code = this.allCodes[b1];
/* 1439 */       i += code.getHandlerCount();
/*      */     } 
/*      */     
/* 1442 */     BandStructure.ValueBand[] arrayOfValueBand = { this.code_handler_start_P, this.code_handler_end_PO, this.code_handler_catch_PO, this.code_handler_class_RCN };
/*      */ 
/*      */ 
/*      */     
/*      */     byte b2;
/*      */ 
/*      */     
/* 1449 */     for (b2 = 0; b2 < arrayOfValueBand.length; b2++) {
/* 1450 */       arrayOfValueBand[b2].expectLength(i);
/* 1451 */       arrayOfValueBand[b2].readFrom(this.in);
/*      */     } 
/*      */     
/* 1454 */     for (b2 = 0; b2 < this.allCodes.length; b2++) {
/* 1455 */       Code code = this.allCodes[b2]; byte b; int j;
/* 1456 */       for (b = 0, j = code.getHandlerCount(); b < j; b++) {
/* 1457 */         code.handler_class[b] = this.code_handler_class_RCN.getRef();
/*      */ 
/*      */         
/* 1460 */         code.handler_start[b] = this.code_handler_start_P.getInt();
/* 1461 */         code.handler_end[b] = this.code_handler_end_PO.getInt();
/* 1462 */         code.handler_catch[b] = this.code_handler_catch_PO.getInt();
/*      */       } 
/*      */     } 
/* 1465 */     for (b2 = 0; b2 < arrayOfValueBand.length; b2++) {
/* 1466 */       arrayOfValueBand[b2].doneDisbursing();
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   void fixupCodeHandlers() {
/* 1472 */     for (byte b = 0; b < this.allCodes.length; b++) {
/* 1473 */       Code code = this.allCodes[b]; byte b1; int i;
/* 1474 */       for (b1 = 0, i = code.getHandlerCount(); b1 < i; b1++) {
/* 1475 */         int j = code.handler_start[b1];
/* 1476 */         code.handler_start[b1] = code.decodeBCI(j);
/* 1477 */         j += code.handler_end[b1];
/* 1478 */         code.handler_end[b1] = code.decodeBCI(j);
/* 1479 */         j += code.handler_catch[b1];
/* 1480 */         code.handler_catch[b1] = code.decodeBCI(j);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void countAndReadAttrs(int paramInt, Collection<? extends Attribute.Holder> paramCollection) throws IOException {
/* 1545 */     countAttrs(paramInt, paramCollection);
/* 1546 */     readAttrs(paramInt, paramCollection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void countAttrs(int paramInt, Collection<? extends Attribute.Holder> paramCollection) throws IOException {
/* 1554 */     BandStructure.MultiBand multiBand = this.attrBands[paramInt];
/* 1555 */     long l = this.attrFlagMask[paramInt];
/* 1556 */     if (this.verbose > 1) {
/* 1557 */       Utils.log.fine("scanning flags and attrs for " + 
/* 1558 */           Attribute.contextName(paramInt) + "[" + paramCollection.size() + "]");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1563 */     List list = this.attrDefs.get(paramInt);
/* 1564 */     Attribute.Layout[] arrayOfLayout = new Attribute.Layout[list.size()];
/* 1565 */     list.toArray((Object[])arrayOfLayout);
/* 1566 */     BandStructure.IntBand intBand1 = getAttrBand(multiBand, 0);
/* 1567 */     BandStructure.IntBand intBand2 = getAttrBand(multiBand, 1);
/* 1568 */     BandStructure.IntBand intBand3 = getAttrBand(multiBand, 2);
/* 1569 */     BandStructure.IntBand intBand4 = getAttrBand(multiBand, 3);
/* 1570 */     BandStructure.IntBand intBand5 = getAttrBand(multiBand, 4);
/*      */ 
/*      */     
/* 1573 */     int i = this.attrOverflowMask[paramInt];
/* 1574 */     byte b1 = 0;
/* 1575 */     boolean bool1 = haveFlagsHi(paramInt);
/* 1576 */     intBand1.expectLength(bool1 ? paramCollection.size() : 0);
/* 1577 */     intBand1.readFrom(this.in);
/* 1578 */     intBand2.expectLength(paramCollection.size());
/* 1579 */     intBand2.readFrom(this.in);
/* 1580 */     assert (l & i) == i;
/* 1581 */     for (Attribute.Holder holder : paramCollection) {
/* 1582 */       int j = intBand2.getInt();
/* 1583 */       holder.flags = j;
/* 1584 */       if ((j & i) != 0) {
/* 1585 */         b1++;
/*      */       }
/*      */     } 
/*      */     
/* 1589 */     intBand3.expectLength(b1);
/* 1590 */     intBand3.readFrom(this.in);
/* 1591 */     intBand4.expectLength(intBand3.getIntTotal());
/* 1592 */     intBand4.readFrom(this.in);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1598 */     int[] arrayOfInt = new int[arrayOfLayout.length];
/* 1599 */     for (Attribute.Holder holder : paramCollection) {
/* 1600 */       assert holder.attributes == null;
/*      */       
/* 1602 */       long l1 = (holder.flags & l) << 32L >>> 32L;
/*      */       
/* 1604 */       holder.flags -= (int)l1;
/* 1605 */       assert holder.flags == (char)holder.flags;
/* 1606 */       assert paramInt != 3 || holder.flags == 0;
/* 1607 */       if (bool1)
/* 1608 */         l1 += intBand1.getInt() << 32L; 
/* 1609 */       if (l1 == 0L)
/*      */         continue; 
/* 1611 */       int j = 0;
/* 1612 */       long l2 = l1 & i;
/* 1613 */       assert l2 >= 0L;
/* 1614 */       l1 -= l2;
/* 1615 */       if (l2 != 0L) {
/* 1616 */         j = intBand3.getInt();
/*      */       }
/*      */       
/* 1619 */       byte b3 = 0;
/* 1620 */       long l3 = l1;
/* 1621 */       for (byte b4 = 0; l3 != 0L; b4++) {
/* 1622 */         if ((l3 & 1L << b4) != 0L) {
/* 1623 */           l3 -= 1L << b4;
/* 1624 */           b3++;
/*      */         } 
/* 1626 */       }  ArrayList<Attribute> arrayList = new ArrayList(b3 + j);
/* 1627 */       holder.attributes = arrayList;
/* 1628 */       l3 = l1; int k;
/* 1629 */       for (k = 0; l3 != 0L; k++) {
/* 1630 */         if ((l3 & 1L << k) != 0L) {
/* 1631 */           l3 -= 1L << k;
/* 1632 */           arrayOfInt[k] = arrayOfInt[k] + 1;
/*      */           
/* 1634 */           if (arrayOfLayout[k] == null) badAttrIndex(k, paramInt); 
/* 1635 */           Attribute attribute = arrayOfLayout[k].canonicalInstance();
/* 1636 */           arrayList.add(attribute);
/* 1637 */           b3--;
/*      */         } 
/* 1639 */       }  assert b3 == 0;
/* 1640 */       for (; j > 0; j--) {
/* 1641 */         k = intBand4.getInt();
/* 1642 */         arrayOfInt[k] = arrayOfInt[k] + 1;
/*      */         
/* 1644 */         if (arrayOfLayout[k] == null) badAttrIndex(k, paramInt); 
/* 1645 */         Attribute attribute = arrayOfLayout[k].canonicalInstance();
/* 1646 */         arrayList.add(attribute);
/*      */       } 
/*      */     } 
/*      */     
/* 1650 */     intBand1.doneDisbursing();
/* 1651 */     intBand2.doneDisbursing();
/* 1652 */     intBand3.doneDisbursing();
/* 1653 */     intBand4.doneDisbursing();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1662 */     byte b2 = 0; boolean bool2;
/* 1663 */     for (bool2 = true;; bool2 = false) {
/* 1664 */       for (byte b = 0; b < arrayOfLayout.length; b++) {
/* 1665 */         Attribute.Layout layout = arrayOfLayout[b];
/* 1666 */         if (layout != null && 
/* 1667 */           bool2 == isPredefinedAttr(paramInt, b)) {
/*      */           
/* 1669 */           int j = arrayOfInt[b];
/* 1670 */           if (j != 0) {
/*      */             
/* 1672 */             Attribute.Layout.Element[] arrayOfElement = layout.getCallables();
/* 1673 */             for (byte b3 = 0; b3 < arrayOfElement.length; b3++)
/* 1674 */             { assert (arrayOfElement[b3]).kind == 10;
/* 1675 */               if (arrayOfElement[b3].flagTest((byte)8))
/* 1676 */                 b2++;  } 
/*      */           } 
/*      */         } 
/* 1679 */       }  if (!bool2)
/*      */         break; 
/* 1681 */     }  intBand5.expectLength(b2);
/* 1682 */     intBand5.readFrom(this.in);
/*      */ 
/*      */     
/* 1685 */     for (bool2 = true;; bool2 = false) {
/* 1686 */       for (byte b = 0; b < arrayOfLayout.length; b++) {
/* 1687 */         Attribute.Layout layout = arrayOfLayout[b];
/* 1688 */         if (layout != null && 
/* 1689 */           bool2 == isPredefinedAttr(paramInt, b)) {
/*      */           
/* 1691 */           int j = arrayOfInt[b];
/* 1692 */           BandStructure.Band[] arrayOfBand = this.attrBandTable.get(layout);
/* 1693 */           if (layout == this.attrInnerClassesEmpty) {
/*      */ 
/*      */ 
/*      */             
/* 1697 */             this.class_InnerClasses_N.expectLength(j);
/* 1698 */             this.class_InnerClasses_N.readFrom(this.in);
/* 1699 */             int k = this.class_InnerClasses_N.getIntTotal();
/* 1700 */             this.class_InnerClasses_RC.expectLength(k);
/* 1701 */             this.class_InnerClasses_RC.readFrom(this.in);
/* 1702 */             this.class_InnerClasses_F.expectLength(k);
/* 1703 */             this.class_InnerClasses_F.readFrom(this.in);
/*      */             
/* 1705 */             k -= this.class_InnerClasses_F.getIntCount(0);
/* 1706 */             this.class_InnerClasses_outer_RCN.expectLength(k);
/* 1707 */             this.class_InnerClasses_outer_RCN.readFrom(this.in);
/* 1708 */             this.class_InnerClasses_name_RUN.expectLength(k);
/* 1709 */             this.class_InnerClasses_name_RUN.readFrom(this.in);
/* 1710 */           } else if (!this.optDebugBands && j == 0) {
/*      */ 
/*      */             
/* 1713 */             for (byte b3 = 0; b3 < arrayOfBand.length; b3++) {
/* 1714 */               arrayOfBand[b3].doneWithUnusedBand();
/*      */             }
/*      */           } else {
/*      */             
/* 1718 */             boolean bool = layout.hasCallables();
/* 1719 */             if (!bool) {
/* 1720 */               readAttrBands(layout.elems, j, new int[0], arrayOfBand);
/*      */             } else {
/* 1722 */               Attribute.Layout.Element[] arrayOfElement = layout.getCallables();
/*      */ 
/*      */               
/* 1725 */               int[] arrayOfInt1 = new int[arrayOfElement.length];
/* 1726 */               arrayOfInt1[0] = j;
/* 1727 */               for (byte b3 = 0; b3 < arrayOfElement.length; b3++) {
/* 1728 */                 assert (arrayOfElement[b3]).kind == 10;
/* 1729 */                 int k = arrayOfInt1[b3];
/* 1730 */                 arrayOfInt1[b3] = -1;
/* 1731 */                 if (j > 0 && arrayOfElement[b3].flagTest((byte)8))
/* 1732 */                   k += intBand5.getInt(); 
/* 1733 */                 readAttrBands((arrayOfElement[b3]).body, k, arrayOfInt1, arrayOfBand);
/*      */               } 
/*      */             } 
/*      */             
/* 1737 */             if (this.optDebugBands && j == 0)
/* 1738 */               for (byte b3 = 0; b3 < arrayOfBand.length; b3++) {
/* 1739 */                 arrayOfBand[b3].doneDisbursing();
/*      */               } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1744 */       if (!bool2)
/*      */         break; 
/* 1746 */     }  intBand5.doneDisbursing();
/*      */   }
/*      */   
/*      */   void badAttrIndex(int paramInt1, int paramInt2) throws IOException {
/* 1750 */     throw new IOException("Unknown attribute index " + paramInt1 + " for " + Constants.ATTR_CONTEXT_NAME[paramInt2] + " attribute");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void readAttrs(int paramInt, Collection<? extends Attribute.Holder> paramCollection) throws IOException {
/* 1757 */     HashSet<Attribute.Layout> hashSet = new HashSet();
/* 1758 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 1759 */     for (Attribute.Holder holder : paramCollection) {
/* 1760 */       if (holder.attributes == null)
/* 1761 */         continue;  for (ListIterator<Attribute> listIterator = holder.attributes.listIterator(); listIterator.hasNext(); ) {
/* 1762 */         Attribute attribute = listIterator.next();
/* 1763 */         Attribute.Layout layout = attribute.layout();
/* 1764 */         if (layout.bandCount == 0) {
/* 1765 */           if (layout == this.attrInnerClassesEmpty)
/*      */           {
/* 1767 */             readLocalInnerClasses((Package.Class)holder);
/*      */           }
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1773 */         hashSet.add(layout);
/* 1774 */         boolean bool = (paramInt == 1 && layout == this.attrConstantValue) ? true : false;
/* 1775 */         if (bool) setConstantValueIndex((Package.Class.Field)holder); 
/* 1776 */         if (this.verbose > 2)
/* 1777 */           Utils.log.fine("read " + attribute + " in " + holder); 
/* 1778 */         final BandStructure.Band[] ab = this.attrBandTable.get(layout);
/*      */         
/* 1780 */         byteArrayOutputStream.reset();
/* 1781 */         Object object = attribute.unparse(new Attribute.ValueStream() {
/*      */               public int getInt(int param1Int) {
/* 1783 */                 return ((BandStructure.IntBand)ab[param1Int]).getInt();
/*      */               }
/*      */               public ConstantPool.Entry getRef(int param1Int) {
/* 1786 */                 return ((BandStructure.CPRefBand)ab[param1Int]).getRef();
/*      */               }
/*      */               public int decodeBCI(int param1Int) {
/* 1789 */                 Code code = (Code)h;
/* 1790 */                 return code.decodeBCI(param1Int);
/*      */               }
/*      */             },  byteArrayOutputStream);
/*      */         
/* 1794 */         listIterator.set(attribute.addContent(byteArrayOutputStream.toByteArray(), object));
/* 1795 */         if (bool) setConstantValueIndex(null);
/*      */       
/*      */       } 
/*      */     } 
/*      */     
/* 1800 */     for (Attribute.Layout layout : hashSet) {
/* 1801 */       if (layout == null)
/* 1802 */         continue;  final BandStructure.Band[] ab = this.attrBandTable.get(layout);
/* 1803 */       for (byte b1 = 0; b1 < arrayOfBand.length; b1++) {
/* 1804 */         arrayOfBand[b1].doneDisbursing();
/*      */       }
/*      */     } 
/*      */     
/* 1808 */     if (paramInt == 0) {
/* 1809 */       this.class_InnerClasses_N.doneDisbursing();
/* 1810 */       this.class_InnerClasses_RC.doneDisbursing();
/* 1811 */       this.class_InnerClasses_F.doneDisbursing();
/* 1812 */       this.class_InnerClasses_outer_RCN.doneDisbursing();
/* 1813 */       this.class_InnerClasses_name_RUN.doneDisbursing();
/*      */     } 
/*      */     
/* 1816 */     BandStructure.MultiBand multiBand = this.attrBands[paramInt];
/* 1817 */     for (byte b = 0; b < multiBand.size(); b++) {
/* 1818 */       BandStructure.Band band = multiBand.get(b);
/* 1819 */       if (band instanceof BandStructure.MultiBand)
/* 1820 */         band.doneDisbursing(); 
/*      */     } 
/* 1822 */     multiBand.doneDisbursing();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readAttrBands(Attribute.Layout.Element[] paramArrayOfElement, int paramInt, int[] paramArrayOfint, BandStructure.Band[] paramArrayOfBand) throws IOException {
/* 1830 */     for (byte b = 0; b < paramArrayOfElement.length; b++) {
/* 1831 */       int i, j; byte b1; Attribute.Layout.Element element = paramArrayOfElement[b];
/* 1832 */       BandStructure.Band band = null;
/* 1833 */       if (element.hasBand()) {
/* 1834 */         band = paramArrayOfBand[element.bandIndex];
/* 1835 */         band.expectLength(paramInt);
/* 1836 */         band.readFrom(this.in);
/*      */       } 
/* 1838 */       switch (element.kind) {
/*      */         
/*      */         case 5:
/* 1841 */           i = ((BandStructure.IntBand)band).getIntTotal();
/*      */           
/* 1843 */           readAttrBands(element.body, i, paramArrayOfint, paramArrayOfBand);
/*      */           break;
/*      */         case 7:
/* 1846 */           j = paramInt;
/* 1847 */           for (b1 = 0; b1 < element.body.length; b1++) {
/*      */             int k;
/* 1849 */             if (b1 == element.body.length - 1) {
/* 1850 */               k = j;
/*      */             } else {
/* 1852 */               k = 0;
/* 1853 */               byte b2 = b1;
/*      */               
/* 1855 */               for (; b1 == b2 || (b1 < element.body.length && element.body[b1]
/* 1856 */                 .flagTest((byte)8)); 
/* 1857 */                 b1++) {
/* 1858 */                 k += ((BandStructure.IntBand)band).getIntCount((element.body[b1]).value);
/*      */               }
/* 1860 */               b1--;
/*      */             } 
/* 1862 */             j -= k;
/* 1863 */             readAttrBands((element.body[b1]).body, k, paramArrayOfint, paramArrayOfBand);
/*      */           } 
/* 1865 */           assert j == 0;
/*      */           break;
/*      */         case 9:
/* 1868 */           assert element.body.length == 1;
/* 1869 */           assert (element.body[0]).kind == 10;
/* 1870 */           if (!element.flagTest((byte)8)) {
/*      */ 
/*      */             
/* 1873 */             assert paramArrayOfint[element.value] >= 0;
/* 1874 */             paramArrayOfint[element.value] = paramArrayOfint[element.value] + paramInt;
/*      */           } 
/*      */           break;
/*      */         case 10:
/*      */           assert false;
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void readByteCodes() throws IOException {
/* 1911 */     this.bc_codes.elementCountForDebug = this.allCodes.length;
/* 1912 */     this.bc_codes.setInputStreamFrom(this.in);
/* 1913 */     readByteCodeOps();
/* 1914 */     this.bc_codes.doneDisbursing();
/*      */ 
/*      */     
/* 1917 */     BandStructure.Band[] arrayOfBand = { this.bc_case_value, this.bc_byte, this.bc_short, this.bc_local, this.bc_label, this.bc_intref, this.bc_floatref, this.bc_longref, this.bc_doubleref, this.bc_stringref, this.bc_loadablevalueref, this.bc_classref, this.bc_fieldref, this.bc_methodref, this.bc_imethodref, this.bc_indyref, this.bc_thisfield, this.bc_superfield, this.bc_thismethod, this.bc_supermethod, this.bc_initref, this.bc_escref, this.bc_escrefsize, this.bc_escsize };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     byte b;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1932 */     for (b = 0; b < arrayOfBand.length; b++) {
/* 1933 */       arrayOfBand[b].readFrom(this.in);
/*      */     }
/* 1935 */     this.bc_escbyte.expectLength(this.bc_escsize.getIntTotal());
/* 1936 */     this.bc_escbyte.readFrom(this.in);
/*      */     
/* 1938 */     expandByteCodeOps();
/*      */ 
/*      */     
/* 1941 */     this.bc_case_count.doneDisbursing();
/* 1942 */     for (b = 0; b < arrayOfBand.length; b++) {
/* 1943 */       arrayOfBand[b].doneDisbursing();
/*      */     }
/* 1945 */     this.bc_escbyte.doneDisbursing();
/* 1946 */     this.bc_bands.doneDisbursing();
/*      */ 
/*      */ 
/*      */     
/* 1950 */     readAttrs(3, (Collection)this.codesWithFlags);
/*      */     
/* 1952 */     fixupCodeHandlers();
/*      */     
/* 1954 */     this.code_bands.doneDisbursing();
/* 1955 */     this.class_bands.doneDisbursing();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readByteCodeOps() throws IOException {
/* 1960 */     byte[] arrayOfByte = new byte[4096];
/*      */     
/* 1962 */     ArrayList<Integer> arrayList = new ArrayList();
/* 1963 */     for (byte b = 0; b < this.allCodes.length; ) {
/* 1964 */       Code code = this.allCodes[b];
/*      */       
/* 1966 */       for (byte b1 = 0;; b1++) {
/* 1967 */         int i = this.bc_codes.getByte();
/* 1968 */         if (b1 + 10 > arrayOfByte.length) arrayOfByte = realloc(arrayOfByte); 
/* 1969 */         arrayOfByte[b1] = (byte)i;
/* 1970 */         boolean bool = false;
/* 1971 */         if (i == 196) {
/* 1972 */           i = this.bc_codes.getByte();
/* 1973 */           arrayOfByte[++b1] = (byte)i;
/* 1974 */           bool = true;
/*      */         } 
/* 1976 */         assert i == (0xFF & i);
/*      */         
/* 1978 */         switch (i) {
/*      */           case 170:
/*      */           case 171:
/* 1981 */             this.bc_case_count.expectMoreLength(1);
/* 1982 */             arrayList.add(Integer.valueOf(i));
/*      */             break;
/*      */           case 132:
/* 1985 */             this.bc_local.expectMoreLength(1);
/* 1986 */             if (bool) {
/* 1987 */               this.bc_short.expectMoreLength(1); break;
/*      */             } 
/* 1989 */             this.bc_byte.expectMoreLength(1);
/*      */             break;
/*      */           case 17:
/* 1992 */             this.bc_short.expectMoreLength(1);
/*      */             break;
/*      */           case 16:
/* 1995 */             this.bc_byte.expectMoreLength(1);
/*      */             break;
/*      */           case 188:
/* 1998 */             this.bc_byte.expectMoreLength(1);
/*      */             break;
/*      */           case 197:
/* 2001 */             assert getCPRefOpBand(i) == this.bc_classref;
/* 2002 */             this.bc_classref.expectMoreLength(1);
/* 2003 */             this.bc_byte.expectMoreLength(1);
/*      */             break;
/*      */           case 253:
/* 2006 */             this.bc_escrefsize.expectMoreLength(1);
/* 2007 */             this.bc_escref.expectMoreLength(1);
/*      */             break;
/*      */           case 254:
/* 2010 */             this.bc_escsize.expectMoreLength(1);
/*      */             break;
/*      */           
/*      */           default:
/* 2014 */             if (Instruction.isInvokeInitOp(i)) {
/* 2015 */               this.bc_initref.expectMoreLength(1);
/*      */               break;
/*      */             } 
/* 2018 */             if (Instruction.isSelfLinkerOp(i)) {
/* 2019 */               BandStructure.CPRefBand cPRefBand = selfOpRefBand(i);
/* 2020 */               cPRefBand.expectMoreLength(1);
/*      */               break;
/*      */             } 
/* 2023 */             if (Instruction.isBranchOp(i)) {
/* 2024 */               this.bc_label.expectMoreLength(1);
/*      */               break;
/*      */             } 
/* 2027 */             if (Instruction.isCPRefOp(i)) {
/* 2028 */               BandStructure.CPRefBand cPRefBand = getCPRefOpBand(i);
/* 2029 */               cPRefBand.expectMoreLength(1);
/* 2030 */               assert i != 197;
/*      */               break;
/*      */             } 
/* 2033 */             if (Instruction.isLocalSlotOp(i)) {
/* 2034 */               this.bc_local.expectMoreLength(1);
/*      */             }
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 255:
/* 2041 */             code.bytes = realloc(arrayOfByte, b1);
/*      */             b++;
/*      */             continue;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/*      */     } 
/* 2049 */     this.bc_case_count.readFrom(this.in);
/* 2050 */     for (Integer integer : arrayList) {
/* 2051 */       int i = integer.intValue();
/* 2052 */       int j = this.bc_case_count.getInt();
/* 2053 */       this.bc_label.expectMoreLength(1 + j);
/* 2054 */       this.bc_case_value.expectMoreLength((i == 170) ? 1 : j);
/*      */     } 
/* 2056 */     this.bc_case_count.resetForSecondPass();
/*      */   }
/*      */ 
/*      */   
/*      */   private void expandByteCodeOps() throws IOException {
/* 2061 */     byte[] arrayOfByte = new byte[4096];
/*      */     
/* 2063 */     int[] arrayOfInt1 = new int[4096];
/*      */     
/* 2065 */     int[] arrayOfInt2 = new int[1024];
/*      */     
/* 2067 */     Fixups fixups = new Fixups();
/*      */     
/* 2069 */     for (byte b = 0; b < this.allCodes.length; b++) {
/* 2070 */       Code code = this.allCodes[b];
/* 2071 */       byte[] arrayOfByte1 = code.bytes;
/* 2072 */       code.bytes = null;
/*      */       
/* 2074 */       Package.Class clazz = code.thisClass();
/*      */       
/* 2076 */       Set<ConstantPool.Entry> set = this.ldcRefMap.get(clazz);
/* 2077 */       if (set == null) {
/* 2078 */         this.ldcRefMap.put(clazz, set = new HashSet<>());
/*      */       }
/* 2080 */       ConstantPool.ClassEntry classEntry1 = clazz.thisClass;
/* 2081 */       ConstantPool.ClassEntry classEntry2 = clazz.superClass;
/* 2082 */       ConstantPool.ClassEntry classEntry3 = null;
/*      */       
/* 2084 */       int i = 0;
/* 2085 */       byte b1 = 0;
/* 2086 */       byte b2 = 0;
/* 2087 */       boolean bool = false;
/* 2088 */       fixups.clear();
/* 2089 */       for (byte b3 = 0; b3 < arrayOfByte1.length; b3++) {
/* 2090 */         int m; Instruction.Switch switch_; byte b5; ConstantPool.Entry entry; int n, j = Instruction.getByte(arrayOfByte1, b3);
/* 2091 */         int k = i;
/* 2092 */         arrayOfInt1[b1++] = k;
/* 2093 */         if (i + 10 > arrayOfByte.length) arrayOfByte = realloc(arrayOfByte); 
/* 2094 */         if (b1 + 10 > arrayOfInt1.length) arrayOfInt1 = realloc(arrayOfInt1); 
/* 2095 */         if (b2 + 10 > arrayOfInt2.length) arrayOfInt2 = realloc(arrayOfInt2); 
/* 2096 */         boolean bool1 = false;
/* 2097 */         if (j == 196) {
/* 2098 */           arrayOfByte[i++] = (byte)j;
/* 2099 */           j = Instruction.getByte(arrayOfByte1, ++b3);
/* 2100 */           bool1 = true;
/*      */         } 
/* 2102 */         switch (j) {
/*      */           
/*      */           case 170:
/*      */           case 171:
/* 2106 */             m = this.bc_case_count.getInt();
/* 2107 */             while (i + 30 + m * 8 > arrayOfByte.length)
/* 2108 */               arrayOfByte = realloc(arrayOfByte); 
/* 2109 */             arrayOfByte[i++] = (byte)j;
/*      */             
/* 2111 */             Arrays.fill(arrayOfByte, i, i + 30, (byte)0);
/*      */             
/* 2113 */             switch_ = (Instruction.Switch)Instruction.at(arrayOfByte, k);
/*      */             
/* 2115 */             switch_.setCaseCount(m);
/* 2116 */             if (j == 170) {
/* 2117 */               switch_.setCaseValue(0, this.bc_case_value.getInt());
/*      */             } else {
/* 2119 */               for (byte b6 = 0; b6 < m; b6++) {
/* 2120 */                 switch_.setCaseValue(b6, this.bc_case_value.getInt());
/*      */               }
/*      */             } 
/*      */             
/* 2124 */             arrayOfInt2[b2++] = k;
/* 2125 */             i = switch_.getNextPC();
/*      */             break;
/*      */ 
/*      */           
/*      */           case 132:
/* 2130 */             arrayOfByte[i++] = (byte)j;
/* 2131 */             m = this.bc_local.getInt();
/*      */             
/* 2133 */             if (bool1) {
/* 2134 */               int i1 = this.bc_short.getInt();
/* 2135 */               Instruction.setShort(arrayOfByte, i, m); i += 2;
/* 2136 */               Instruction.setShort(arrayOfByte, i, i1); i += 2; break;
/*      */             } 
/* 2138 */             b5 = (byte)this.bc_byte.getByte();
/* 2139 */             arrayOfByte[i++] = (byte)m;
/* 2140 */             arrayOfByte[i++] = (byte)b5;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 17:
/* 2146 */             m = this.bc_short.getInt();
/* 2147 */             arrayOfByte[i++] = (byte)j;
/* 2148 */             Instruction.setShort(arrayOfByte, i, m); i += 2;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 16:
/*      */           case 188:
/* 2154 */             m = this.bc_byte.getByte();
/* 2155 */             arrayOfByte[i++] = (byte)j;
/* 2156 */             arrayOfByte[i++] = (byte)m;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 253:
/* 2162 */             bool = true;
/* 2163 */             m = this.bc_escrefsize.getInt();
/* 2164 */             entry = this.bc_escref.getRef();
/* 2165 */             if (m == 1) set.add(entry);
/*      */             
/* 2167 */             switch (m) { case 1:
/* 2168 */                 fixups.addU1(i, entry); break;
/* 2169 */               case 2: fixups.addU2(i, entry); break;
/* 2170 */               default: assert false; n = 0; break; }
/*      */             
/* 2172 */             arrayOfByte[i + 1] = 0; arrayOfByte[i + 0] = 0;
/* 2173 */             i += m;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 254:
/* 2179 */             bool = true;
/* 2180 */             m = this.bc_escsize.getInt();
/* 2181 */             while (i + m > arrayOfByte.length)
/* 2182 */               arrayOfByte = realloc(arrayOfByte); 
/* 2183 */             while (m-- > 0) {
/* 2184 */               arrayOfByte[i++] = (byte)this.bc_escbyte.getByte();
/*      */             }
/*      */             break;
/*      */           
/*      */           default:
/* 2189 */             if (Instruction.isInvokeInitOp(j)) {
/* 2190 */               ConstantPool.ClassEntry classEntry; m = j - 230;
/* 2191 */               char c = '·';
/*      */               
/* 2193 */               switch (m) {
/*      */                 case 0:
/* 2195 */                   classEntry = classEntry1; break;
/*      */                 case 1:
/* 2197 */                   classEntry = classEntry2; break;
/*      */                 default:
/* 2199 */                   assert m == 2;
/* 2200 */                   classEntry = classEntry3; break;
/*      */               } 
/* 2202 */               arrayOfByte[i++] = (byte)c;
/* 2203 */               int i1 = this.bc_initref.getInt();
/*      */               
/* 2205 */               ConstantPool.MemberEntry memberEntry = this.pkg.cp.getOverloadingForIndex((byte)10, classEntry, "<init>", i1);
/* 2206 */               fixups.addU2(i, memberEntry);
/* 2207 */               arrayOfByte[i + 1] = 0; arrayOfByte[i + 0] = 0;
/* 2208 */               i += 2;
/* 2209 */               assert Instruction.opLength(c) == i - k;
/*      */               break;
/*      */             } 
/* 2212 */             if (Instruction.isSelfLinkerOp(j)) {
/* 2213 */               BandStructure.CPRefBand cPRefBand; ConstantPool.Index index; m = j - 202;
/* 2214 */               boolean bool2 = (m >= 14) ? true : false;
/* 2215 */               if (bool2) m -= 14; 
/* 2216 */               n = (m >= 7) ? 1 : 0;
/* 2217 */               if (n) m -= 7; 
/* 2218 */               int i1 = 178 + m;
/* 2219 */               boolean bool3 = Instruction.isFieldOp(i1);
/*      */               
/* 2221 */               ConstantPool.ClassEntry classEntry = bool2 ? classEntry2 : classEntry1;
/*      */               
/* 2223 */               if (bool3) {
/* 2224 */                 cPRefBand = bool2 ? this.bc_superfield : this.bc_thisfield;
/* 2225 */                 index = this.pkg.cp.getMemberIndex((byte)9, classEntry);
/*      */               } else {
/* 2227 */                 cPRefBand = bool2 ? this.bc_supermethod : this.bc_thismethod;
/* 2228 */                 index = this.pkg.cp.getMemberIndex((byte)10, classEntry);
/*      */               } 
/* 2230 */               assert cPRefBand == selfOpRefBand(j);
/* 2231 */               ConstantPool.MemberEntry memberEntry = (ConstantPool.MemberEntry)cPRefBand.getRef(index);
/* 2232 */               if (n) {
/* 2233 */                 arrayOfByte[i++] = 42;
/* 2234 */                 k = i;
/*      */                 
/* 2236 */                 arrayOfInt1[b1++] = k;
/*      */               } 
/* 2238 */               arrayOfByte[i++] = (byte)i1;
/* 2239 */               fixups.addU2(i, memberEntry);
/* 2240 */               arrayOfByte[i + 1] = 0; arrayOfByte[i + 0] = 0;
/* 2241 */               i += 2;
/* 2242 */               assert Instruction.opLength(i1) == i - k;
/*      */               break;
/*      */             } 
/* 2245 */             if (Instruction.isBranchOp(j)) {
/* 2246 */               arrayOfByte[i++] = (byte)j;
/* 2247 */               assert !bool1;
/* 2248 */               m = k + Instruction.opLength(j);
/*      */               
/* 2250 */               arrayOfInt2[b2++] = k;
/*      */               
/* 2252 */               for (; i < m; arrayOfByte[i++] = 0);
/*      */               break;
/*      */             } 
/* 2255 */             if (Instruction.isCPRefOp(j)) {
/* 2256 */               boolean bool2; BandStructure.CPRefBand cPRefBand = getCPRefOpBand(j);
/* 2257 */               entry = cPRefBand.getRef();
/* 2258 */               if (entry == null) {
/* 2259 */                 if (cPRefBand == this.bc_classref) {
/*      */                   
/* 2261 */                   entry = classEntry1;
/*      */                 } else {
/*      */                   assert false;
/*      */                 } 
/*      */               }
/* 2266 */               n = j;
/* 2267 */               byte b6 = 2;
/* 2268 */               switch (j) {
/*      */                 case 243:
/* 2270 */                   n = 184;
/*      */                   break;
/*      */                 case 242:
/* 2273 */                   n = 183;
/*      */                   break;
/*      */                 case 18:
/*      */                 case 233:
/*      */                 case 234:
/*      */                 case 235:
/*      */                 case 240:
/* 2280 */                   n = 18;
/* 2281 */                   b6 = 1;
/* 2282 */                   set.add(entry);
/*      */                   break;
/*      */                 case 19:
/*      */                 case 236:
/*      */                 case 237:
/*      */                 case 238:
/*      */                 case 241:
/* 2289 */                   n = 19;
/*      */                   break;
/*      */                 case 20:
/*      */                 case 239:
/* 2293 */                   n = 20;
/*      */                   break;
/*      */                 case 187:
/* 2296 */                   classEntry3 = (ConstantPool.ClassEntry)entry;
/*      */                   break;
/*      */               } 
/* 2299 */               arrayOfByte[i++] = (byte)n;
/*      */               
/* 2301 */               switch (b6) { case 1:
/* 2302 */                   fixups.addU1(i, entry); break;
/* 2303 */                 case 2: fixups.addU2(i, entry); break;
/* 2304 */                 default: assert false; bool2 = false; break; }
/*      */               
/* 2306 */               arrayOfByte[i + 1] = 0; arrayOfByte[i + 0] = 0;
/* 2307 */               i += b6;
/* 2308 */               if (n == 197) {
/*      */                 
/* 2310 */                 int i1 = this.bc_byte.getByte();
/* 2311 */                 arrayOfByte[i++] = (byte)i1;
/* 2312 */               } else if (n == 185) {
/* 2313 */                 int i1 = ((ConstantPool.MemberEntry)entry).descRef.typeRef.computeSize(true);
/* 2314 */                 arrayOfByte[i++] = (byte)(1 + i1);
/* 2315 */                 arrayOfByte[i++] = 0;
/* 2316 */               } else if (n == 186) {
/* 2317 */                 arrayOfByte[i++] = 0;
/* 2318 */                 arrayOfByte[i++] = 0;
/*      */               } 
/* 2320 */               assert Instruction.opLength(n) == i - k;
/*      */               break;
/*      */             } 
/* 2323 */             if (Instruction.isLocalSlotOp(j)) {
/* 2324 */               arrayOfByte[i++] = (byte)j;
/* 2325 */               m = this.bc_local.getInt();
/* 2326 */               if (bool1) {
/* 2327 */                 Instruction.setShort(arrayOfByte, i, m);
/* 2328 */                 i += 2;
/* 2329 */                 if (j == 132) {
/* 2330 */                   int i1 = this.bc_short.getInt();
/* 2331 */                   Instruction.setShort(arrayOfByte, i, i1);
/* 2332 */                   i += 2;
/*      */                 } 
/*      */               } else {
/* 2335 */                 Instruction.setByte(arrayOfByte, i, m);
/* 2336 */                 i++;
/* 2337 */                 if (j == 132) {
/* 2338 */                   int i1 = this.bc_byte.getByte();
/* 2339 */                   Instruction.setByte(arrayOfByte, i, i1);
/* 2340 */                   i++;
/*      */                 } 
/*      */               } 
/* 2343 */               assert Instruction.opLength(j) == i - k;
/*      */               
/*      */               break;
/*      */             } 
/* 2347 */             if (j >= 202)
/* 2348 */               Utils.log.warning("unrecognized bytescode " + j + " " + 
/* 2349 */                   Instruction.byteName(j)); 
/* 2350 */             assert j < 202;
/* 2351 */             arrayOfByte[i++] = (byte)j;
/* 2352 */             assert Instruction.opLength(j) == i - k;
/*      */             break;
/*      */         } 
/*      */       
/*      */       } 
/* 2357 */       code.setBytes(realloc(arrayOfByte, i));
/* 2358 */       code.setInstructionMap(arrayOfInt1, b1);
/*      */       
/* 2360 */       Instruction instruction = null;
/* 2361 */       for (byte b4 = 0; b4 < b2; b4++) {
/* 2362 */         int j = arrayOfInt2[b4];
/*      */         
/* 2364 */         instruction = Instruction.at(code.bytes, j, instruction);
/* 2365 */         if (instruction instanceof Instruction.Switch) {
/* 2366 */           Instruction.Switch switch_ = (Instruction.Switch)instruction;
/* 2367 */           switch_.setDefaultLabel(getLabel(this.bc_label, code, j));
/* 2368 */           int k = switch_.getCaseCount();
/* 2369 */           for (byte b5 = 0; b5 < k; b5++) {
/* 2370 */             switch_.setCaseLabel(b5, getLabel(this.bc_label, code, j));
/*      */           }
/*      */         } else {
/* 2373 */           instruction.setBranchLabel(getLabel(this.bc_label, code, j));
/*      */         } 
/*      */       } 
/* 2376 */       if (fixups.size() > 0) {
/* 2377 */         if (this.verbose > 2)
/* 2378 */           Utils.log.fine("Fixups in code: " + fixups); 
/* 2379 */         code.addFixups(fixups);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/PackageReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */