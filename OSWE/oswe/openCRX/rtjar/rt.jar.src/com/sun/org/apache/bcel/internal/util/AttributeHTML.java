/*     */ package com.sun.org.apache.bcel.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.Constants;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Attribute;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Code;
/*     */ import com.sun.org.apache.bcel.internal.classfile.CodeException;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantPool;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantUtf8;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantValue;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ExceptionTable;
/*     */ import com.sun.org.apache.bcel.internal.classfile.InnerClass;
/*     */ import com.sun.org.apache.bcel.internal.classfile.InnerClasses;
/*     */ import com.sun.org.apache.bcel.internal.classfile.LineNumber;
/*     */ import com.sun.org.apache.bcel.internal.classfile.LineNumberTable;
/*     */ import com.sun.org.apache.bcel.internal.classfile.LocalVariable;
/*     */ import com.sun.org.apache.bcel.internal.classfile.LocalVariableTable;
/*     */ import com.sun.org.apache.bcel.internal.classfile.SourceFile;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Utility;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AttributeHTML
/*     */   implements Constants
/*     */ {
/*     */   private String class_name;
/*     */   private PrintWriter file;
/*  73 */   private int attr_count = 0;
/*     */   
/*     */   private ConstantHTML constant_html;
/*     */   
/*     */   private ConstantPool constant_pool;
/*     */   
/*     */   AttributeHTML(String dir, String class_name, ConstantPool constant_pool, ConstantHTML constant_html) throws IOException {
/*  80 */     this.class_name = class_name;
/*  81 */     this.constant_pool = constant_pool;
/*  82 */     this.constant_html = constant_html;
/*     */     
/*  84 */     this.file = new PrintWriter(new FileOutputStream(dir + class_name + "_attributes.html"));
/*  85 */     this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
/*     */   }
/*     */   
/*     */   private final String codeLink(int link, int method_number) {
/*  89 */     return "<A HREF=\"" + this.class_name + "_code.html#code" + method_number + "@" + link + "\" TARGET=Code>" + link + "</A>";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final void close() {
/*  95 */     this.file.println("</TABLE></BODY></HTML>");
/*  96 */     this.file.close();
/*     */   }
/*     */   
/*     */   final void writeAttribute(Attribute attribute, String anchor) throws IOException {
/* 100 */     writeAttribute(attribute, anchor, 0); } final void writeAttribute(Attribute attribute, String anchor, int method_number) throws IOException { int index; Code c; CodeException[] ce; int len, indices[], i; LineNumber[] line_numbers; int j; LocalVariable[] vars;
/*     */     int k;
/*     */     InnerClass[] classes;
/*     */     int m;
/* 104 */     byte tag = attribute.getTag();
/*     */ 
/*     */     
/* 107 */     if (tag == -1) {
/*     */       return;
/*     */     }
/* 110 */     this.attr_count++;
/*     */     
/* 112 */     if (this.attr_count % 2 == 0) {
/* 113 */       this.file.print("<TR BGCOLOR=\"#C0C0C0\"><TD>");
/*     */     } else {
/* 115 */       this.file.print("<TR BGCOLOR=\"#A0A0A0\"><TD>");
/*     */     } 
/* 117 */     this.file.println("<H4><A NAME=\"" + anchor + "\">" + this.attr_count + " " + ATTRIBUTE_NAMES[tag] + "</A></H4>");
/*     */ 
/*     */ 
/*     */     
/* 121 */     switch (tag) {
/*     */       case 2:
/* 123 */         c = (Code)attribute;
/*     */ 
/*     */         
/* 126 */         this.file.print("<UL><LI>Maximum stack size = " + c.getMaxStack() + "</LI>\n<LI>Number of local variables = " + c
/*     */             
/* 128 */             .getMaxLocals() + "</LI>\n<LI><A HREF=\"" + this.class_name + "_code.html#method" + method_number + "\" TARGET=Code>Byte code</A></LI></UL>\n");
/*     */ 
/*     */ 
/*     */         
/* 132 */         ce = c.getExceptionTable();
/* 133 */         len = ce.length;
/*     */         
/* 135 */         if (len > 0) {
/* 136 */           this.file.print("<P><B>Exceptions handled</B><UL>");
/*     */           
/* 138 */           for (int n = 0; n < len; n++) {
/* 139 */             int catch_type = ce[n].getCatchType();
/*     */             
/* 141 */             this.file.print("<LI>");
/*     */             
/* 143 */             if (catch_type != 0) {
/* 144 */               this.file.print(this.constant_html.referenceConstant(catch_type));
/*     */             } else {
/* 146 */               this.file.print("Any Exception");
/*     */             } 
/* 148 */             this.file.print("<BR>(Ranging from lines " + codeLink(ce[n].getStartPC(), method_number) + " to " + 
/* 149 */                 codeLink(ce[n].getEndPC(), method_number) + ", handled at line " + 
/* 150 */                 codeLink(ce[n].getHandlerPC(), method_number) + ")</LI>");
/*     */           } 
/* 152 */           this.file.print("</UL>");
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 1:
/* 157 */         index = ((ConstantValue)attribute).getConstantValueIndex();
/*     */ 
/*     */         
/* 160 */         this.file.print("<UL><LI><A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">Constant value index(" + index + ")</A></UL>\n");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 0:
/* 165 */         index = ((SourceFile)attribute).getSourceFileIndex();
/*     */ 
/*     */         
/* 168 */         this.file.print("<UL><LI><A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">Source file index(" + index + ")</A></UL>\n");
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 174 */         indices = ((ExceptionTable)attribute).getExceptionIndexTable();
/*     */         
/* 176 */         this.file.print("<UL>");
/*     */         
/* 178 */         for (i = 0; i < indices.length; i++) {
/* 179 */           this.file.print("<LI><A HREF=\"" + this.class_name + "_cp.html#cp" + indices[i] + "\" TARGET=\"ConstantPool\">Exception class index(" + indices[i] + ")</A>\n");
/*     */         }
/*     */         
/* 182 */         this.file.print("</UL>\n");
/*     */         break;
/*     */       
/*     */       case 4:
/* 186 */         line_numbers = ((LineNumberTable)attribute).getLineNumberTable();
/*     */ 
/*     */         
/* 189 */         this.file.print("<P>");
/*     */         
/* 191 */         for (j = 0; j < line_numbers.length; j++) {
/* 192 */           this.file.print("(" + line_numbers[j].getStartPC() + ",&nbsp;" + line_numbers[j].getLineNumber() + ")");
/*     */           
/* 194 */           if (j < line_numbers.length - 1) {
/* 195 */             this.file.print(", ");
/*     */           }
/*     */         } 
/*     */         break;
/*     */       case 5:
/* 200 */         vars = ((LocalVariableTable)attribute).getLocalVariableTable();
/*     */ 
/*     */         
/* 203 */         this.file.print("<UL>");
/*     */         
/* 205 */         for (k = 0; k < vars.length; k++) {
/* 206 */           index = vars[k].getSignatureIndex();
/* 207 */           String signature = ((ConstantUtf8)this.constant_pool.getConstant(index, (byte)1)).getBytes();
/* 208 */           signature = Utility.signatureToString(signature, false);
/* 209 */           int start = vars[k].getStartPC();
/* 210 */           int end = start + vars[k].getLength();
/*     */           
/* 212 */           this.file.println("<LI>" + Class2HTML.referenceType(signature) + "&nbsp;<B>" + vars[k]
/* 213 */               .getName() + "</B> in slot %" + vars[k].getIndex() + "<BR>Valid from lines <A HREF=\"" + this.class_name + "_code.html#code" + method_number + "@" + start + "\" TARGET=Code>" + start + "</A> to <A HREF=\"" + this.class_name + "_code.html#code" + method_number + "@" + end + "\" TARGET=Code>" + end + "</A></LI>");
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 220 */         this.file.print("</UL>\n");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 225 */         classes = ((InnerClasses)attribute).getInnerClasses();
/*     */ 
/*     */         
/* 228 */         this.file.print("<UL>");
/*     */         
/* 230 */         for (m = 0; m < classes.length; m++) {
/*     */           String name;
/*     */           
/* 233 */           index = classes[m].getInnerNameIndex();
/* 234 */           if (index > 0) {
/* 235 */             name = ((ConstantUtf8)this.constant_pool.getConstant(index, (byte)1)).getBytes();
/*     */           } else {
/* 237 */             name = "&lt;anonymous&gt;";
/*     */           } 
/* 239 */           String access = Utility.accessToString(classes[m].getInnerAccessFlags());
/*     */           
/* 241 */           this.file.print("<LI><FONT COLOR=\"#FF0000\">" + access + "</FONT> " + this.constant_html
/* 242 */               .referenceConstant(classes[m].getInnerClassIndex()) + " in&nbsp;class " + this.constant_html
/*     */               
/* 244 */               .referenceConstant(classes[m].getOuterClassIndex()) + " named " + name + "</LI>\n");
/*     */         } 
/*     */ 
/*     */         
/* 248 */         this.file.print("</UL>\n");
/*     */         break;
/*     */       
/*     */       default:
/* 252 */         this.file.print("<P>" + attribute.toString());
/*     */         break;
/*     */     } 
/* 255 */     this.file.println("</TD></TR>");
/* 256 */     this.file.flush(); }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/AttributeHTML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */