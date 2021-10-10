/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.classfile.Constant;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantCP;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantClass;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantDouble;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantFieldref;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantFloat;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantInteger;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantInterfaceMethodref;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantLong;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantMethodref;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantPool;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantString;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantUtf8;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConstantPoolGen
/*     */   implements Serializable
/*     */ {
/*  79 */   protected int size = 1024;
/*  80 */   protected Constant[] constants = new Constant[this.size];
/*  81 */   protected int index = 1; private static final String METHODREF_DELIM = ":"; private static final String IMETHODREF_DELIM = "#"; private static final String FIELDREF_DELIM = "&"; private static final String NAT_DELIM = "%"; private HashMap string_table;
/*     */   private HashMap class_table;
/*     */   private HashMap utf8_table;
/*     */   private HashMap n_a_t_table;
/*     */   private HashMap cp_table;
/*     */   
/*     */   private static class Index implements Serializable { int index;
/*     */     
/*     */     Index(int i) {
/*  90 */       this.index = i;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConstantPoolGen(ConstantPool cp) {
/* 162 */     this(cp.getConstantPool());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void adjustSize() {
/* 173 */     if (this.index + 3 >= this.size) {
/* 174 */       Constant[] cs = this.constants;
/*     */       
/* 176 */       this.size *= 2;
/* 177 */       this.constants = new Constant[this.size];
/* 178 */       System.arraycopy(cs, 0, this.constants, 0, this.index);
/*     */     } 
/*     */   }
/*     */   
/* 182 */   public ConstantPoolGen(Constant[] cs) { this.string_table = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     this.class_table = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 363 */     this.utf8_table = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 481 */     this.n_a_t_table = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 520 */     this.cp_table = new HashMap<>(); if (cs.length > this.size) { this.size = cs.length; this.constants = new Constant[this.size]; }  System.arraycopy(cs, 0, this.constants, 0, cs.length); if (cs.length > 0) this.index = cs.length;  for (int i = 1; i < this.index; i++) { Constant c = this.constants[i]; if (c instanceof ConstantString) { ConstantString s = (ConstantString)c; ConstantUtf8 u8 = (ConstantUtf8)this.constants[s.getStringIndex()]; this.string_table.put(u8.getBytes(), new Index(i)); } else if (c instanceof ConstantClass) { ConstantClass s = (ConstantClass)c; ConstantUtf8 u8 = (ConstantUtf8)this.constants[s.getNameIndex()]; this.class_table.put(u8.getBytes(), new Index(i)); } else if (c instanceof ConstantNameAndType) { ConstantNameAndType n = (ConstantNameAndType)c; ConstantUtf8 u8 = (ConstantUtf8)this.constants[n.getNameIndex()]; ConstantUtf8 u8_2 = (ConstantUtf8)this.constants[n.getSignatureIndex()]; this.n_a_t_table.put(u8.getBytes() + "%" + u8_2.getBytes(), new Index(i)); } else if (c instanceof ConstantUtf8) { ConstantUtf8 u = (ConstantUtf8)c; this.utf8_table.put(u.getBytes(), new Index(i)); } else if (c instanceof ConstantCP) { ConstantCP m = (ConstantCP)c; ConstantClass clazz = (ConstantClass)this.constants[m.getClassIndex()]; ConstantNameAndType n = (ConstantNameAndType)this.constants[m.getNameAndTypeIndex()]; ConstantUtf8 u8 = (ConstantUtf8)this.constants[clazz.getNameIndex()]; String class_name = u8.getBytes().replace('/', '.'); u8 = (ConstantUtf8)this.constants[n.getNameIndex()]; String method_name = u8.getBytes(); u8 = (ConstantUtf8)this.constants[n.getSignatureIndex()]; String signature = u8.getBytes(); String delim = ":"; if (c instanceof ConstantInterfaceMethodref) { delim = "#"; } else if (c instanceof ConstantFieldref) { delim = "&"; }  this.cp_table.put(class_name + delim + method_name + delim + signature, new Index(i)); }  }  } public ConstantPoolGen() { this.string_table = new HashMap<>(); this.class_table = new HashMap<>(); this.utf8_table = new HashMap<>(); this.n_a_t_table = new HashMap<>(); this.cp_table = new HashMap<>(); }
/*     */   public int lookupString(String str) { Index index = (Index)this.string_table.get(str); return (index != null) ? index.index : -1; }
/*     */   public int addString(String str) { int ret; if ((ret = lookupString(str)) != -1) return ret;  int utf8 = addUtf8(str); adjustSize(); ConstantString s = new ConstantString(utf8); ret = this.index; this.constants[this.index++] = s; this.string_table.put(str, new Index(ret)); return ret; }
/*     */   public int lookupClass(String str) { Index index = (Index)this.class_table.get(str.replace('.', '/')); return (index != null) ? index.index : -1; }
/*     */   private int addClass_(String clazz) { int ret; if ((ret = lookupClass(clazz)) != -1) return ret;  adjustSize(); ConstantClass c = new ConstantClass(addUtf8(clazz)); ret = this.index; this.constants[this.index++] = c; this.class_table.put(clazz, new Index(ret)); return ret; }
/*     */   public int addClass(String str) { return addClass_(str.replace('.', '/')); }
/*     */   public int addClass(ObjectType type) { return addClass(type.getClassName()); }
/*     */   public int addArrayClass(ArrayType type) { return addClass_(type.getSignature()); }
/*     */   public int lookupInteger(int n) { for (int i = 1; i < this.index; i++) { if (this.constants[i] instanceof ConstantInteger) { ConstantInteger c = (ConstantInteger)this.constants[i]; if (c.getBytes() == n)
/*     */           return i;  }  }  return -1; }
/*     */   public int addInteger(int n) { int ret; if ((ret = lookupInteger(n)) != -1)
/* 531 */       return ret;  adjustSize(); ret = this.index; this.constants[this.index++] = new ConstantInteger(n); return ret; } public int lookupMethodref(String class_name, String method_name, String signature) { Index index = (Index)this.cp_table.get(class_name + ":" + method_name + ":" + signature);
/*     */     
/* 533 */     return (index != null) ? index.index : -1; }
/*     */   public int lookupFloat(float n) { int bits = Float.floatToIntBits(n); for (int i = 1; i < this.index; i++) { if (this.constants[i] instanceof ConstantFloat) { ConstantFloat c = (ConstantFloat)this.constants[i]; if (Float.floatToIntBits(c.getBytes()) == bits) return i;  }  }  return -1; }
/*     */   public int addFloat(float n) { int ret; if ((ret = lookupFloat(n)) != -1) return ret;  adjustSize(); ret = this.index; this.constants[this.index++] = new ConstantFloat(n); return ret; }
/*     */   public int lookupUtf8(String n) { Index index = (Index)this.utf8_table.get(n); return (index != null) ? index.index : -1; }
/* 537 */   public int addUtf8(String n) { int ret; if ((ret = lookupUtf8(n)) != -1) return ret;  adjustSize(); ret = this.index; this.constants[this.index++] = new ConstantUtf8(n); this.utf8_table.put(n, new Index(ret)); return ret; } public int lookupLong(long n) { for (int i = 1; i < this.index; i++) { if (this.constants[i] instanceof ConstantLong) { ConstantLong c = (ConstantLong)this.constants[i]; if (c.getBytes() == n) return i;  }  }  return -1; } public int addLong(long n) { int ret; if ((ret = lookupLong(n)) != -1) return ret;  adjustSize(); ret = this.index; this.constants[this.index] = new ConstantLong(n); this.index += 2; return ret; } public int lookupDouble(double n) { long bits = Double.doubleToLongBits(n); for (int i = 1; i < this.index; i++) { if (this.constants[i] instanceof ConstantDouble) { ConstantDouble c = (ConstantDouble)this.constants[i]; if (Double.doubleToLongBits(c.getBytes()) == bits) return i;  }  }  return -1; } public int addDouble(double n) { int ret; if ((ret = lookupDouble(n)) != -1) return ret;  adjustSize(); ret = this.index; this.constants[this.index] = new ConstantDouble(n); this.index += 2; return ret; } public int lookupNameAndType(String name, String signature) { Index index = (Index)this.n_a_t_table.get(name + "%" + signature); return (index != null) ? index.index : -1; } public int addNameAndType(String name, String signature) { int ret; if ((ret = lookupNameAndType(name, signature)) != -1) return ret;  adjustSize(); int name_index = addUtf8(name); int signature_index = addUtf8(signature); ret = this.index; this.constants[this.index++] = new ConstantNameAndType(name_index, signature_index); this.n_a_t_table.put(name + "%" + signature, new Index(ret)); return ret; } public int lookupMethodref(MethodGen method) { return lookupMethodref(method.getClassName(), method.getName(), method
/* 538 */         .getSignature()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addMethodref(String class_name, String method_name, String signature) {
/*     */     int ret;
/* 551 */     if ((ret = lookupMethodref(class_name, method_name, signature)) != -1) {
/* 552 */       return ret;
/*     */     }
/* 554 */     adjustSize();
/*     */     
/* 556 */     int name_and_type_index = addNameAndType(method_name, signature);
/* 557 */     int class_index = addClass(class_name);
/* 558 */     ret = this.index;
/* 559 */     this.constants[this.index++] = new ConstantMethodref(class_index, name_and_type_index);
/*     */     
/* 561 */     this.cp_table.put(class_name + ":" + method_name + ":" + signature, new Index(ret));
/*     */ 
/*     */     
/* 564 */     return ret;
/*     */   }
/*     */   
/*     */   public int addMethodref(MethodGen method) {
/* 568 */     return addMethodref(method.getClassName(), method.getName(), method
/* 569 */         .getSignature());
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
/*     */   public int lookupInterfaceMethodref(String class_name, String method_name, String signature) {
/* 581 */     Index index = (Index)this.cp_table.get(class_name + "#" + method_name + "#" + signature);
/*     */     
/* 583 */     return (index != null) ? index.index : -1;
/*     */   }
/*     */   
/*     */   public int lookupInterfaceMethodref(MethodGen method) {
/* 587 */     return lookupInterfaceMethodref(method.getClassName(), method.getName(), method
/* 588 */         .getSignature());
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
/*     */   public int addInterfaceMethodref(String class_name, String method_name, String signature) {
/*     */     int ret;
/* 601 */     if ((ret = lookupInterfaceMethodref(class_name, method_name, signature)) != -1) {
/* 602 */       return ret;
/*     */     }
/* 604 */     adjustSize();
/*     */     
/* 606 */     int class_index = addClass(class_name);
/* 607 */     int name_and_type_index = addNameAndType(method_name, signature);
/* 608 */     ret = this.index;
/* 609 */     this.constants[this.index++] = new ConstantInterfaceMethodref(class_index, name_and_type_index);
/*     */     
/* 611 */     this.cp_table.put(class_name + "#" + method_name + "#" + signature, new Index(ret));
/*     */ 
/*     */     
/* 614 */     return ret;
/*     */   }
/*     */   
/*     */   public int addInterfaceMethodref(MethodGen method) {
/* 618 */     return addInterfaceMethodref(method.getClassName(), method.getName(), method
/* 619 */         .getSignature());
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
/*     */   public int lookupFieldref(String class_name, String field_name, String signature) {
/* 631 */     Index index = (Index)this.cp_table.get(class_name + "&" + field_name + "&" + signature);
/*     */     
/* 633 */     return (index != null) ? index.index : -1;
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
/*     */   public int addFieldref(String class_name, String field_name, String signature) {
/*     */     int ret;
/* 647 */     if ((ret = lookupFieldref(class_name, field_name, signature)) != -1) {
/* 648 */       return ret;
/*     */     }
/* 650 */     adjustSize();
/*     */     
/* 652 */     int class_index = addClass(class_name);
/* 653 */     int name_and_type_index = addNameAndType(field_name, signature);
/* 654 */     ret = this.index;
/* 655 */     this.constants[this.index++] = new ConstantFieldref(class_index, name_and_type_index);
/*     */     
/* 657 */     this.cp_table.put(class_name + "&" + field_name + "&" + signature, new Index(ret));
/*     */     
/* 659 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Constant getConstant(int i) {
/* 666 */     return this.constants[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConstant(int i, Constant c) {
/* 674 */     this.constants[i] = c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ConstantPool getConstantPool() {
/* 680 */     return new ConstantPool(this.constants);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 687 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConstantPool getFinalConstantPool() {
/* 694 */     Constant[] cs = new Constant[this.index];
/*     */     
/* 696 */     System.arraycopy(this.constants, 0, cs, 0, this.index);
/*     */     
/* 698 */     return new ConstantPool(cs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString()
/*     */   {
/* 705 */     StringBuffer buf = new StringBuffer();
/*     */     
/* 707 */     for (int i = 1; i < this.index; i++) {
/* 708 */       buf.append(i + ")" + this.constants[i] + "\n");
/*     */     }
/* 710 */     return buf.toString(); } public int addConstant(Constant c, ConstantPoolGen cp) { ConstantString constantString; ConstantClass s; ConstantNameAndType n; ConstantCP m; ConstantUtf8 u8;
/*     */     ConstantClass clazz;
/*     */     ConstantUtf8 u8_2;
/*     */     ConstantNameAndType constantNameAndType1;
/*     */     ConstantUtf8 constantUtf81;
/*     */     String class_name, name, signature;
/* 716 */     Constant[] constants = cp.getConstantPool().getConstantPool();
/*     */     
/* 718 */     switch (c.getTag()) {
/*     */       case 8:
/* 720 */         constantString = (ConstantString)c;
/* 721 */         u8 = (ConstantUtf8)constants[constantString.getStringIndex()];
/*     */         
/* 723 */         return addString(u8.getBytes());
/*     */ 
/*     */       
/*     */       case 7:
/* 727 */         s = (ConstantClass)c;
/* 728 */         u8 = (ConstantUtf8)constants[s.getNameIndex()];
/*     */         
/* 730 */         return addClass(u8.getBytes());
/*     */ 
/*     */       
/*     */       case 12:
/* 734 */         n = (ConstantNameAndType)c;
/* 735 */         u8 = (ConstantUtf8)constants[n.getNameIndex()];
/* 736 */         u8_2 = (ConstantUtf8)constants[n.getSignatureIndex()];
/*     */         
/* 738 */         return addNameAndType(u8.getBytes(), u8_2.getBytes());
/*     */ 
/*     */       
/*     */       case 1:
/* 742 */         return addUtf8(((ConstantUtf8)c).getBytes());
/*     */       
/*     */       case 6:
/* 745 */         return addDouble(((ConstantDouble)c).getBytes());
/*     */       
/*     */       case 4:
/* 748 */         return addFloat(((ConstantFloat)c).getBytes());
/*     */       
/*     */       case 5:
/* 751 */         return addLong(((ConstantLong)c).getBytes());
/*     */       
/*     */       case 3:
/* 754 */         return addInteger(((ConstantInteger)c).getBytes());
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/* 758 */         m = (ConstantCP)c;
/* 759 */         clazz = (ConstantClass)constants[m.getClassIndex()];
/* 760 */         constantNameAndType1 = (ConstantNameAndType)constants[m.getNameAndTypeIndex()];
/* 761 */         constantUtf81 = (ConstantUtf8)constants[clazz.getNameIndex()];
/* 762 */         class_name = constantUtf81.getBytes().replace('/', '.');
/*     */         
/* 764 */         constantUtf81 = (ConstantUtf8)constants[constantNameAndType1.getNameIndex()];
/* 765 */         name = constantUtf81.getBytes();
/*     */         
/* 767 */         constantUtf81 = (ConstantUtf8)constants[constantNameAndType1.getSignatureIndex()];
/* 768 */         signature = constantUtf81.getBytes();
/*     */         
/* 770 */         switch (c.getTag()) {
/*     */           case 11:
/* 772 */             return addInterfaceMethodref(class_name, name, signature);
/*     */           
/*     */           case 10:
/* 775 */             return addMethodref(class_name, name, signature);
/*     */           
/*     */           case 9:
/* 778 */             return addFieldref(class_name, name, signature);
/*     */         } 
/*     */         
/* 781 */         throw new RuntimeException("Unknown constant type " + c);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 786 */     throw new RuntimeException("Unknown constant type " + c); }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/ConstantPoolGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */