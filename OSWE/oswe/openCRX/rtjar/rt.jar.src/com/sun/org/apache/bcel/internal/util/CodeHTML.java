/*     */ package com.sun.org.apache.bcel.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.Constants;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Attribute;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Code;
/*     */ import com.sun.org.apache.bcel.internal.classfile.CodeException;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantFieldref;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantInterfaceMethodref;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantMethodref;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantPool;
/*     */ import com.sun.org.apache.bcel.internal.classfile.LocalVariable;
/*     */ import com.sun.org.apache.bcel.internal.classfile.LocalVariableTable;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Method;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Utility;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.BitSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CodeHTML
/*     */   implements Constants
/*     */ {
/*     */   private String class_name;
/*     */   private Method[] methods;
/*     */   private PrintWriter file;
/*     */   private BitSet goto_set;
/*     */   private ConstantPool constant_pool;
/*     */   private ConstantHTML constant_html;
/*     */   private static boolean wide = false;
/*     */   
/*     */   CodeHTML(String dir, String class_name, Method[] methods, ConstantPool constant_pool, ConstantHTML constant_html) throws IOException {
/*  84 */     this.class_name = class_name;
/*  85 */     this.methods = methods;
/*  86 */     this.constant_pool = constant_pool;
/*  87 */     this.constant_html = constant_html;
/*     */     
/*  89 */     this.file = new PrintWriter(new FileOutputStream(dir + class_name + "_code.html"));
/*  90 */     this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\">");
/*     */     
/*  92 */     for (int i = 0; i < methods.length; i++) {
/*  93 */       writeMethod(methods[i], i);
/*     */     }
/*  95 */     this.file.println("</BODY></HTML>");
/*  96 */     this.file.close();
/*     */   }
/*     */   
/*     */   private final String codeToHTML(ByteSequence bytes, int method_number) throws IOException {
/*     */     String name, signature;
/*     */     int low, high, index, class_index, vindex, constant, jump_table[], offset, i, npairs, j, windex;
/*     */     ConstantFieldref c1;
/*     */     String field_name;
/*     */     int m_index;
/*     */     String str1;
/*     */     ConstantNameAndType c2;
/*     */     String args[], type;
/*     */     int k, dimensions;
/* 109 */     short opcode = (short)bytes.readUnsignedByte();
/*     */ 
/*     */     
/* 112 */     int default_offset = 0;
/*     */ 
/*     */     
/* 115 */     int no_pad_bytes = 0;
/*     */     
/* 117 */     StringBuffer buf = new StringBuffer("<TT>" + OPCODE_NAMES[opcode] + "</TT></TD><TD>");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (opcode == 170 || opcode == 171) {
/* 123 */       int remainder = bytes.getIndex() % 4;
/* 124 */       no_pad_bytes = (remainder == 0) ? 0 : (4 - remainder);
/*     */       
/* 126 */       for (int m = 0; m < no_pad_bytes; m++) {
/* 127 */         bytes.readByte();
/*     */       }
/*     */       
/* 130 */       default_offset = bytes.readInt();
/*     */     } 
/*     */     
/* 133 */     switch (opcode)
/*     */     { case 170:
/* 135 */         low = bytes.readInt();
/* 136 */         high = bytes.readInt();
/*     */         
/* 138 */         offset = bytes.getIndex() - 12 - no_pad_bytes - 1;
/* 139 */         default_offset += offset;
/*     */         
/* 141 */         buf.append("<TABLE BORDER=1><TR>");
/*     */ 
/*     */         
/* 144 */         jump_table = new int[high - low + 1];
/* 145 */         for (i = 0; i < jump_table.length; i++) {
/* 146 */           jump_table[i] = offset + bytes.readInt();
/*     */           
/* 148 */           buf.append("<TH>" + (low + i) + "</TH>");
/*     */         } 
/* 150 */         buf.append("<TH>default</TH></TR>\n<TR>");
/*     */ 
/*     */         
/* 153 */         for (i = 0; i < jump_table.length; i++) {
/* 154 */           buf.append("<TD><A HREF=\"#code" + method_number + "@" + jump_table[i] + "\">" + jump_table[i] + "</A></TD>");
/*     */         }
/* 156 */         buf.append("<TD><A HREF=\"#code" + method_number + "@" + default_offset + "\">" + default_offset + "</A></TD></TR>\n</TABLE>\n");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 399 */         buf.append("</TD>");
/* 400 */         return buf.toString();case 171: npairs = bytes.readInt(); offset = bytes.getIndex() - 8 - no_pad_bytes - 1; jump_table = new int[npairs]; default_offset += offset; buf.append("<TABLE BORDER=1><TR>"); for (j = 0; j < npairs; j++) { int match = bytes.readInt(); jump_table[j] = offset + bytes.readInt(); buf.append("<TH>" + match + "</TH>"); }  buf.append("<TH>default</TH></TR>\n<TR>"); for (j = 0; j < npairs; j++) buf.append("<TD><A HREF=\"#code" + method_number + "@" + jump_table[j] + "\">" + jump_table[j] + "</A></TD>");  buf.append("<TD><A HREF=\"#code" + method_number + "@" + default_offset + "\">" + default_offset + "</A></TD></TR>\n</TABLE>\n"); buf.append("</TD>"); return buf.toString();case 153: case 154: case 155: case 156: case 157: case 158: case 159: case 160: case 161: case 162: case 163: case 164: case 165: case 166: case 167: case 168: case 198: case 199: index = bytes.getIndex() + bytes.readShort() - 1; buf.append("<A HREF=\"#code" + method_number + "@" + index + "\">" + index + "</A>"); buf.append("</TD>"); return buf.toString();case 200: case 201: windex = bytes.getIndex() + bytes.readInt() - 1; buf.append("<A HREF=\"#code" + method_number + "@" + windex + "\">" + windex + "</A>"); buf.append("</TD>"); return buf.toString();case 21: case 22: case 23: case 24: case 25: case 54: case 55: case 56: case 57: case 58: case 169: if (wide) { vindex = bytes.readShort(); wide = false; } else { vindex = bytes.readUnsignedByte(); }  buf.append("%" + vindex); buf.append("</TD>"); return buf.toString();case 196: wide = true; buf.append("(wide)"); buf.append("</TD>"); return buf.toString();case 188: buf.append("<FONT COLOR=\"#00FF00\">" + TYPE_NAMES[bytes.readByte()] + "</FONT>"); buf.append("</TD>"); return buf.toString();case 178: case 179: case 180: case 181: index = bytes.readShort(); c1 = (ConstantFieldref)this.constant_pool.getConstant(index, (byte)9); class_index = c1.getClassIndex(); name = this.constant_pool.getConstantString(class_index, (byte)7); name = Utility.compactClassName(name, false); index = c1.getNameAndTypeIndex(); field_name = this.constant_pool.constantToString(index, (byte)12); if (name.equals(this.class_name)) { buf.append("<A HREF=\"" + this.class_name + "_methods.html#field" + field_name + "\" TARGET=Methods>" + field_name + "</A>\n"); } else { buf.append(this.constant_html.referenceConstant(class_index) + "." + field_name); }  buf.append("</TD>"); return buf.toString();case 187: case 192: case 193: index = bytes.readShort(); buf.append(this.constant_html.referenceConstant(index)); buf.append("</TD>"); return buf.toString();case 182: case 183: case 184: case 185: m_index = bytes.readShort(); if (opcode == 185) { int nargs = bytes.readUnsignedByte(); int reserved = bytes.readUnsignedByte(); ConstantInterfaceMethodref c = (ConstantInterfaceMethodref)this.constant_pool.getConstant(m_index, (byte)11); class_index = c.getClassIndex(); String str = this.constant_pool.constantToString(c); index = c.getNameAndTypeIndex(); } else { ConstantMethodref c = (ConstantMethodref)this.constant_pool.getConstant(m_index, (byte)10); class_index = c.getClassIndex(); String str = this.constant_pool.constantToString(c); index = c.getNameAndTypeIndex(); }  name = Class2HTML.referenceClass(class_index); str1 = Class2HTML.toHTML(this.constant_pool.constantToString(this.constant_pool.getConstant(index, (byte)12))); c2 = (ConstantNameAndType)this.constant_pool.getConstant(index, (byte)12); signature = this.constant_pool.constantToString(c2.getSignatureIndex(), (byte)1); args = Utility.methodSignatureArgumentTypes(signature, false); type = Utility.methodSignatureReturnType(signature, false); buf.append(name + ".<A HREF=\"" + this.class_name + "_cp.html#cp" + m_index + "\" TARGET=ConstantPool>" + str1 + "</A>("); for (k = 0; k < args.length; k++) { buf.append(Class2HTML.referenceType(args[k])); if (k < args.length - 1) buf.append(", ");  }  buf.append("):" + Class2HTML.referenceType(type)); buf.append("</TD>"); return buf.toString();case 19: case 20: index = bytes.readShort(); buf.append("<A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">" + Class2HTML.toHTML(this.constant_pool.constantToString(index, this.constant_pool.getConstant(index).getTag())) + "</a>"); buf.append("</TD>"); return buf.toString();case 18: index = bytes.readUnsignedByte(); buf.append("<A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">" + Class2HTML.toHTML(this.constant_pool.constantToString(index, this.constant_pool.getConstant(index).getTag())) + "</a>"); buf.append("</TD>"); return buf.toString();case 189: index = bytes.readShort(); buf.append(this.constant_html.referenceConstant(index)); buf.append("</TD>"); return buf.toString();case 197: index = bytes.readShort(); dimensions = bytes.readByte(); buf.append(this.constant_html.referenceConstant(index) + ":" + dimensions + "-dimensional"); buf.append("</TD>"); return buf.toString();case 132: if (wide) { vindex = bytes.readShort(); constant = bytes.readShort(); wide = false; } else { vindex = bytes.readUnsignedByte(); constant = bytes.readByte(); }  buf.append("%" + vindex + " " + constant); buf.append("</TD>"); return buf.toString(); }  if (NO_OF_OPERANDS[opcode] > 0) for (int m = 0; m < (TYPE_OF_OPERANDS[opcode]).length; m++) { switch (TYPE_OF_OPERANDS[opcode][m]) { case 8: buf.append(bytes.readUnsignedByte()); break;case 9: buf.append(bytes.readShort()); break;case 10: buf.append(bytes.readInt()); break;default: System.err.println("Unreachable default case reached!"); System.exit(-1); break; }  buf.append("&nbsp;"); }   buf.append("</TD>"); return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void findGotos(ByteSequence bytes, Method method, Code code) throws IOException {
/* 411 */     this.goto_set = new BitSet(bytes.available());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 418 */     if (code != null) {
/* 419 */       CodeException[] ce = code.getExceptionTable();
/* 420 */       int len = ce.length;
/*     */       
/* 422 */       for (int j = 0; j < len; j++) {
/* 423 */         this.goto_set.set(ce[j].getStartPC());
/* 424 */         this.goto_set.set(ce[j].getEndPC());
/* 425 */         this.goto_set.set(ce[j].getHandlerPC());
/*     */       } 
/*     */ 
/*     */       
/* 429 */       Attribute[] attributes = code.getAttributes();
/* 430 */       for (int k = 0; k < attributes.length; k++) {
/* 431 */         if (attributes[k].getTag() == 5) {
/* 432 */           LocalVariable[] vars = ((LocalVariableTable)attributes[k]).getLocalVariableTable();
/*     */           
/* 434 */           for (int m = 0; m < vars.length; m++) {
/* 435 */             int start = vars[m].getStartPC();
/* 436 */             int end = start + vars[m].getLength();
/* 437 */             this.goto_set.set(start);
/* 438 */             this.goto_set.set(end);
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 446 */     for (int i = 0; bytes.available() > 0; i++) {
/* 447 */       int index, remainder, no_pad_bytes, default_offset, offset, j, npairs, k, opcode = bytes.readUnsignedByte();
/*     */       
/* 449 */       switch (opcode) {
/*     */         
/*     */         case 170:
/*     */         case 171:
/* 453 */           remainder = bytes.getIndex() % 4;
/* 454 */           no_pad_bytes = (remainder == 0) ? 0 : (4 - remainder);
/*     */ 
/*     */           
/* 457 */           for (j = 0; j < no_pad_bytes; j++) {
/* 458 */             bytes.readByte();
/*     */           }
/*     */           
/* 461 */           default_offset = bytes.readInt();
/*     */           
/* 463 */           if (opcode == 170) {
/* 464 */             int low = bytes.readInt();
/* 465 */             int high = bytes.readInt();
/*     */             
/* 467 */             int m = bytes.getIndex() - 12 - no_pad_bytes - 1;
/* 468 */             default_offset += m;
/* 469 */             this.goto_set.set(default_offset);
/*     */             
/* 471 */             for (int n = 0; n < high - low + 1; n++) {
/* 472 */               int i1 = m + bytes.readInt();
/* 473 */               this.goto_set.set(i1);
/*     */             } 
/*     */             break;
/*     */           } 
/* 477 */           npairs = bytes.readInt();
/*     */           
/* 479 */           offset = bytes.getIndex() - 8 - no_pad_bytes - 1;
/* 480 */           default_offset += offset;
/* 481 */           this.goto_set.set(default_offset);
/*     */           
/* 483 */           for (k = 0; k < npairs; k++)
/* 484 */           { int match = bytes.readInt();
/*     */             
/* 486 */             int m = offset + bytes.readInt();
/* 487 */             this.goto_set.set(m); }  break;
/*     */         case 153: case 154: case 155: case 156: case 157: case 158: case 159: case 160: case 161:
/*     */         case 162:
/*     */         case 163:
/*     */         case 164:
/*     */         case 165:
/*     */         case 166:
/*     */         case 167:
/*     */         case 168:
/*     */         case 198:
/*     */         case 199:
/* 498 */           index = bytes.getIndex() + bytes.readShort() - 1;
/*     */           
/* 500 */           this.goto_set.set(index);
/*     */           break;
/*     */         
/*     */         case 200:
/*     */         case 201:
/* 505 */           index = bytes.getIndex() + bytes.readInt() - 1;
/* 506 */           this.goto_set.set(index);
/*     */           break;
/*     */         
/*     */         default:
/* 510 */           bytes.unreadByte();
/* 511 */           codeToHTML(bytes, 0);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMethod(Method method, int method_number) throws IOException {
/* 523 */     String signature = method.getSignature();
/*     */     
/* 525 */     String[] args = Utility.methodSignatureArgumentTypes(signature, false);
/*     */     
/* 527 */     String type = Utility.methodSignatureReturnType(signature, false);
/*     */     
/* 529 */     String name = method.getName();
/* 530 */     String html_name = Class2HTML.toHTML(name);
/*     */     
/* 532 */     String access = Utility.accessToString(method.getAccessFlags());
/* 533 */     access = Utility.replace(access, " ", "&nbsp;");
/*     */     
/* 535 */     Attribute[] attributes = method.getAttributes();
/*     */     
/* 537 */     this.file.print("<P><B><FONT COLOR=\"#FF0000\">" + access + "</FONT>&nbsp;<A NAME=method" + method_number + ">" + 
/* 538 */         Class2HTML.referenceType(type) + "</A>&nbsp<A HREF=\"" + this.class_name + "_methods.html#method" + method_number + "\" TARGET=Methods>" + html_name + "</A>(");
/*     */ 
/*     */ 
/*     */     
/* 542 */     for (int i = 0; i < args.length; i++) {
/* 543 */       this.file.print(Class2HTML.referenceType(args[i]));
/* 544 */       if (i < args.length - 1) {
/* 545 */         this.file.print(",&nbsp;");
/*     */       }
/*     */     } 
/* 548 */     this.file.println(")</B></P>");
/*     */     
/* 550 */     Code c = null;
/* 551 */     byte[] code = null;
/*     */     
/* 553 */     if (attributes.length > 0) {
/* 554 */       this.file.print("<H4>Attributes</H4><UL>\n");
/* 555 */       for (int j = 0; j < attributes.length; j++) {
/* 556 */         byte tag = attributes[j].getTag();
/*     */         
/* 558 */         if (tag != -1) {
/* 559 */           this.file.print("<LI><A HREF=\"" + this.class_name + "_attributes.html#method" + method_number + "@" + j + "\" TARGET=Attributes>" + ATTRIBUTE_NAMES[tag] + "</A></LI>\n");
/*     */         } else {
/*     */           
/* 562 */           this.file.print("<LI>" + attributes[j] + "</LI>");
/*     */         } 
/* 564 */         if (tag == 2) {
/* 565 */           c = (Code)attributes[j];
/* 566 */           Attribute[] attributes2 = c.getAttributes();
/* 567 */           code = c.getCode();
/*     */           
/* 569 */           this.file.print("<UL>");
/* 570 */           for (int k = 0; k < attributes2.length; k++) {
/* 571 */             tag = attributes2[k].getTag();
/* 572 */             this.file.print("<LI><A HREF=\"" + this.class_name + "_attributes.html#method" + method_number + "@" + j + "@" + k + "\" TARGET=Attributes>" + ATTRIBUTE_NAMES[tag] + "</A></LI>\n");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 577 */           this.file.print("</UL>");
/*     */         } 
/*     */       } 
/* 580 */       this.file.println("</UL>");
/*     */     } 
/*     */     
/* 583 */     if (code != null) {
/*     */ 
/*     */ 
/*     */       
/* 587 */       ByteSequence stream = new ByteSequence(code);
/* 588 */       stream.mark(stream.available());
/* 589 */       findGotos(stream, method, c);
/* 590 */       stream.reset();
/*     */       
/* 592 */       this.file.println("<TABLE BORDER=0><TR><TH ALIGN=LEFT>Byte<BR>offset</TH><TH ALIGN=LEFT>Instruction</TH><TH ALIGN=LEFT>Argument</TH>");
/*     */ 
/*     */       
/* 595 */       for (int j = 0; stream.available() > 0; j++) {
/* 596 */         String anchor2; int offset = stream.getIndex();
/* 597 */         String str = codeToHTML(stream, method_number);
/* 598 */         String anchor = "";
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 603 */         if (this.goto_set.get(offset)) {
/* 604 */           anchor = "<A NAME=code" + method_number + "@" + offset + "></A>";
/*     */         }
/*     */         
/* 607 */         if (stream.getIndex() == code.length) {
/* 608 */           anchor2 = "<A NAME=code" + method_number + "@" + code.length + ">" + offset + "</A>";
/*     */         } else {
/* 610 */           anchor2 = "" + offset;
/*     */         } 
/* 612 */         this.file.println("<TR VALIGN=TOP><TD>" + anchor2 + "</TD><TD>" + anchor + str + "</TR>");
/*     */       } 
/*     */ 
/*     */       
/* 616 */       this.file.println("<TR><TD> </A></TD></TR>");
/* 617 */       this.file.println("</TABLE>");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/CodeHTML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */