/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ClassWriter
/*     */ {
/*     */   int verbose;
/*     */   Package pkg;
/*     */   Package.Class cls;
/*     */   DataOutputStream out;
/*     */   ConstantPool.Index cpIndex;
/*     */   ConstantPool.Index bsmIndex;
/*     */   ByteArrayOutputStream buf;
/*     */   DataOutputStream bufOut;
/*     */   
/*     */   ClassWriter(Package.Class paramClass, OutputStream paramOutputStream) throws IOException {
/* 227 */     this.buf = new ByteArrayOutputStream();
/* 228 */     this.bufOut = new DataOutputStream(this.buf); this.pkg = paramClass.getPackage(); this.cls = paramClass; this.verbose = this.pkg.verbose; this.out = new DataOutputStream(new BufferedOutputStream(paramOutputStream)); this.cpIndex = ConstantPool.makeIndex(paramClass.toString(), paramClass.getCPMap()); this.cpIndex.flattenSigs = true; if (paramClass.hasBootstrapMethods()) this.bsmIndex = ConstantPool.makeIndex(this.cpIndex.debugName + ".BootstrapMethods", (ConstantPool.Entry[])paramClass.getBootstrapMethodMap());  if (this.verbose > 1) Utils.log.fine("local CP=" + ((this.verbose > 2) ? this.cpIndex.dumpString() : this.cpIndex.toString())); 
/*     */   } private void writeShort(int paramInt) throws IOException { this.out.writeShort(paramInt); } private void writeInt(int paramInt) throws IOException { this.out.writeInt(paramInt); } private void writeRef(ConstantPool.Entry paramEntry) throws IOException { writeRef(paramEntry, this.cpIndex); } private void writeRef(ConstantPool.Entry paramEntry, ConstantPool.Index paramIndex) throws IOException { boolean bool = (paramEntry == null) ? false : paramIndex.indexOf(paramEntry); writeShort(bool); }
/*     */   void write() throws IOException { boolean bool = false; try { if (this.verbose > 1) Utils.log.fine("...writing " + this.cls);  writeMagicNumbers(); writeConstantPool(); writeHeader(); writeMembers(false); writeMembers(true); writeAttributes(0, this.cls); this.out.flush(); bool = true; } finally { if (!bool) Utils.log.warning("Error on output of " + this.cls);  }  }
/* 231 */   void writeAttributes(int paramInt, Attribute.Holder paramHolder) throws IOException { if (paramHolder.attributes == null) {
/* 232 */       writeShort(0);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 237 */     if (paramHolder instanceof Package.Class) {
/* 238 */       reorderBSMandICS(paramHolder);
/*     */     }
/* 240 */     writeShort(paramHolder.attributes.size());
/* 241 */     for (Attribute attribute : paramHolder.attributes)
/* 242 */     { attribute.finishRefs(this.cpIndex);
/* 243 */       writeRef(attribute.getNameRef());
/* 244 */       if (attribute.layout() == Package.attrCodeEmpty || attribute
/* 245 */         .layout() == Package.attrBootstrapMethodsEmpty || attribute
/* 246 */         .layout() == Package.attrInnerClassesEmpty) {
/*     */         
/* 248 */         DataOutputStream dataOutputStream = this.out;
/* 249 */         assert this.out != this.bufOut;
/* 250 */         this.buf.reset();
/* 251 */         this.out = this.bufOut;
/* 252 */         if ("Code".equals(attribute.name())) {
/* 253 */           Package.Class.Method method = (Package.Class.Method)paramHolder;
/* 254 */           writeCode(method.code);
/* 255 */         } else if ("BootstrapMethods".equals(attribute.name())) {
/* 256 */           assert paramHolder == this.cls;
/* 257 */           writeBootstrapMethods(this.cls);
/* 258 */         } else if ("InnerClasses".equals(attribute.name())) {
/* 259 */           assert paramHolder == this.cls;
/* 260 */           writeInnerClasses(this.cls);
/*     */         } else {
/* 262 */           throw new AssertionError();
/*     */         } 
/* 264 */         this.out = dataOutputStream;
/* 265 */         if (this.verbose > 2)
/* 266 */           Utils.log.fine("Attribute " + attribute.name() + " [" + this.buf.size() + "]"); 
/* 267 */         writeInt(this.buf.size());
/* 268 */         this.buf.writeTo(this.out); continue;
/*     */       } 
/* 270 */       if (this.verbose > 2)
/* 271 */         Utils.log.fine("Attribute " + attribute.name() + " [" + attribute.size() + "]"); 
/* 272 */       writeInt(attribute.size());
/* 273 */       this.out.write(attribute.bytes()); }  }
/*     */   void writeMagicNumbers() throws IOException { writeInt(this.cls.magic); writeShort(this.cls.version.minor); writeShort(this.cls.version.major); }
/*     */   void writeConstantPool() throws IOException { ConstantPool.Entry[] arrayOfEntry = this.cls.cpMap; writeShort(arrayOfEntry.length); for (byte b = 0; b < arrayOfEntry.length; b++) { ConstantPool.Entry entry = arrayOfEntry[b]; assert false; if (entry != null) { float f; double d; ConstantPool.MethodHandleEntry methodHandleEntry; byte b1 = entry.getTag(); if (this.verbose > 2)
/*     */           Utils.log.fine("   CP[" + b + "] = " + entry);  this.out.write(b1); switch (b1) { case 13: throw new AssertionError("CP should have Signatures remapped to Utf8");case 1: this.out.writeUTF(entry.stringValue()); break;case 3: this.out.writeInt(((ConstantPool.NumberEntry)entry).numberValue().intValue()); break;case 4: f = ((ConstantPool.NumberEntry)entry).numberValue().floatValue(); this.out.writeInt(Float.floatToRawIntBits(f)); break;case 5: this.out.writeLong(((ConstantPool.NumberEntry)entry).numberValue().longValue()); break;case 6: d = ((ConstantPool.NumberEntry)entry).numberValue().doubleValue(); this.out.writeLong(Double.doubleToRawLongBits(d)); break;case 7: case 8: case 16: writeRef(entry.getRef(0)); break;case 15: methodHandleEntry = (ConstantPool.MethodHandleEntry)entry; this.out.writeByte(methodHandleEntry.refKind); writeRef(methodHandleEntry.getRef(0)); break;case 9: case 10: case 11: case 12: writeRef(entry.getRef(0)); writeRef(entry.getRef(1)); break;case 18: writeRef(entry.getRef(0), this.bsmIndex); writeRef(entry.getRef(1)); break;case 17: throw new AssertionError("CP should have BootstrapMethods moved to side-table");
/*     */           default: throw new IOException("Bad constant pool tag " + b1); }  }  }  }
/*     */   void writeHeader() throws IOException { writeShort(this.cls.flags); writeRef(this.cls.thisClass); writeRef(this.cls.superClass); writeShort(this.cls.interfaces.length); for (byte b = 0; b < this.cls.interfaces.length; b++)
/* 279 */       writeRef(this.cls.interfaces[b]);  } void writeCode(Code paramCode) throws IOException { paramCode.finishRefs(this.cpIndex);
/* 280 */     writeShort(paramCode.max_stack);
/* 281 */     writeShort(paramCode.max_locals);
/* 282 */     writeInt(paramCode.bytes.length);
/* 283 */     this.out.write(paramCode.bytes);
/* 284 */     int i = paramCode.getHandlerCount();
/* 285 */     writeShort(i);
/* 286 */     for (byte b = 0; b < i; b++) {
/* 287 */       writeShort(paramCode.handler_start[b]);
/* 288 */       writeShort(paramCode.handler_end[b]);
/* 289 */       writeShort(paramCode.handler_catch[b]);
/* 290 */       writeRef(paramCode.handler_class[b]);
/*     */     } 
/* 292 */     writeAttributes(3, paramCode); }
/*     */   void writeMembers(boolean paramBoolean) throws IOException { List<Package.Class.Method> list; if (!paramBoolean) { List<Package.Class.Field> list1 = this.cls.getFields(); } else { list = this.cls.getMethods(); }  writeShort(list.size()); for (Package.Class.Member member : list) writeMember(member, paramBoolean);  }
/*     */   void writeMember(Package.Class.Member paramMember, boolean paramBoolean) throws IOException { if (this.verbose > 2) Utils.log.fine("writeMember " + paramMember);  writeShort(paramMember.flags); writeRef((paramMember.getDescriptor()).nameRef); writeRef((paramMember.getDescriptor()).typeRef); writeAttributes(!paramBoolean ? 1 : 2, paramMember); }
/*     */   private void reorderBSMandICS(Attribute.Holder paramHolder) { Attribute attribute1 = paramHolder.getAttribute(Package.attrBootstrapMethodsEmpty); if (attribute1 == null) return;  Attribute attribute2 = paramHolder.getAttribute(Package.attrInnerClassesEmpty); if (attribute2 == null)
/* 296 */       return;  int i = paramHolder.attributes.indexOf(attribute1); int j = paramHolder.attributes.indexOf(attribute2); if (i > j) { paramHolder.attributes.remove(attribute1); paramHolder.attributes.add(j, attribute1); }  } void writeBootstrapMethods(Package.Class paramClass) throws IOException { List<ConstantPool.BootstrapMethodEntry> list = paramClass.getBootstrapMethods();
/* 297 */     writeShort(list.size());
/* 298 */     for (ConstantPool.BootstrapMethodEntry bootstrapMethodEntry : list) {
/* 299 */       writeRef(bootstrapMethodEntry.bsmRef);
/* 300 */       writeShort(bootstrapMethodEntry.argRefs.length);
/* 301 */       for (ConstantPool.Entry entry : bootstrapMethodEntry.argRefs) {
/* 302 */         writeRef(entry);
/*     */       }
/*     */     }  }
/*     */ 
/*     */   
/*     */   void writeInnerClasses(Package.Class paramClass) throws IOException {
/* 308 */     List<Package.InnerClass> list = paramClass.getInnerClasses();
/* 309 */     writeShort(list.size());
/* 310 */     for (Package.InnerClass innerClass : list) {
/* 311 */       writeRef(innerClass.thisClass);
/* 312 */       writeRef(innerClass.outerClass);
/* 313 */       writeRef(innerClass.name);
/* 314 */       writeShort(innerClass.flags);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/ClassWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */