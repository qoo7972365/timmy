/*      */ package com.sun.org.apache.bcel.internal.classfile;
/*      */ 
/*      */ import com.sun.org.apache.bcel.internal.Constants;
/*      */ import com.sun.org.apache.bcel.internal.util.ByteSequence;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.CharArrayReader;
/*      */ import java.io.CharArrayWriter;
/*      */ import java.io.FilterReader;
/*      */ import java.io.FilterWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.zip.GZIPInputStream;
/*      */ import java.util.zip.GZIPOutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Utility
/*      */ {
/*      */   private static int consumed_chars;
/*      */   private static boolean wide = false;
/*      */   private static final int FREE_CHARS = 48;
/*      */   
/*      */   public static final String accessToString(int access_flags) {
/*   94 */     return accessToString(access_flags, false);
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
/*      */   public static final String accessToString(int access_flags, boolean for_class) {
/*  112 */     StringBuffer buf = new StringBuffer();
/*      */     
/*  114 */     int p = 0;
/*  115 */     for (int i = 0; p < 2048; i++) {
/*  116 */       p = pow2(i);
/*      */       
/*  118 */       if ((access_flags & p) != 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  124 */         if (!for_class || (p != 32 && p != 512))
/*      */         {
/*      */           
/*  127 */           buf.append(Constants.ACCESS_NAMES[i] + " ");
/*      */         }
/*      */       }
/*      */     } 
/*  131 */     return buf.toString().trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String classOrInterface(int access_flags) {
/*  138 */     return ((access_flags & 0x200) != 0) ? "interface" : "class";
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
/*      */   public static final String codeToString(byte[] code, ConstantPool constant_pool, int index, int length, boolean verbose) {
/*  159 */     StringBuffer buf = new StringBuffer(code.length * 20);
/*  160 */     ByteSequence stream = new ByteSequence(code);
/*      */     try {
/*      */       int i;
/*  163 */       for (i = 0; i < index; i++) {
/*  164 */         codeToString(stream, constant_pool, verbose);
/*      */       }
/*  166 */       for (i = 0; stream.available() > 0; i++) {
/*  167 */         if (length < 0 || i < length) {
/*  168 */           String indices = fillup(stream.getIndex() + ":", 6, true, ' ');
/*  169 */           buf.append(indices + codeToString(stream, constant_pool, verbose) + '\n');
/*      */         } 
/*      */       } 
/*  172 */     } catch (IOException e) {
/*  173 */       System.out.println(buf.toString());
/*  174 */       e.printStackTrace();
/*  175 */       throw new ClassFormatException("Byte code error: " + e);
/*      */     } 
/*      */     
/*  178 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String codeToString(byte[] code, ConstantPool constant_pool, int index, int length) {
/*  184 */     return codeToString(code, constant_pool, index, length, true);
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
/*      */   public static final String codeToString(ByteSequence bytes, ConstantPool constant_pool, boolean verbose) throws IOException {
/*      */     int low, high, npairs, index, vindex, constant, match[], jump_table[], offset, i, nargs, dimensions;
/*  200 */     short opcode = (short)bytes.readUnsignedByte();
/*  201 */     int default_offset = 0;
/*      */ 
/*      */     
/*  204 */     int no_pad_bytes = 0;
/*  205 */     StringBuffer buf = new StringBuffer(Constants.OPCODE_NAMES[opcode]);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  210 */     if (opcode == 170 || opcode == 171) {
/*  211 */       int remainder = bytes.getIndex() % 4;
/*  212 */       no_pad_bytes = (remainder == 0) ? 0 : (4 - remainder);
/*      */       
/*  214 */       for (int j = 0; j < no_pad_bytes; j++) {
/*      */         byte b;
/*      */         
/*  217 */         if ((b = bytes.readByte()) != 0) {
/*  218 */           System.err.println("Warning: Padding byte != 0 in " + Constants.OPCODE_NAMES[opcode] + ":" + b);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  223 */       default_offset = bytes.readInt();
/*      */     } 
/*      */     
/*  226 */     switch (opcode)
/*      */     
/*      */     { 
/*      */       case 170:
/*  230 */         low = bytes.readInt();
/*  231 */         high = bytes.readInt();
/*      */         
/*  233 */         offset = bytes.getIndex() - 12 - no_pad_bytes - 1;
/*  234 */         default_offset += offset;
/*      */         
/*  236 */         buf.append("\tdefault = " + default_offset + ", low = " + low + ", high = " + high + "(");
/*      */ 
/*      */         
/*  239 */         jump_table = new int[high - low + 1];
/*  240 */         for (i = 0; i < jump_table.length; i++) {
/*  241 */           jump_table[i] = offset + bytes.readInt();
/*  242 */           buf.append(jump_table[i]);
/*      */           
/*  244 */           if (i < jump_table.length - 1)
/*  245 */             buf.append(", "); 
/*      */         } 
/*  247 */         buf.append(")");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  440 */         return buf.toString();case 171: npairs = bytes.readInt(); offset = bytes.getIndex() - 8 - no_pad_bytes - 1; match = new int[npairs]; jump_table = new int[npairs]; default_offset += offset; buf.append("\tdefault = " + default_offset + ", npairs = " + npairs + " ("); for (i = 0; i < npairs; i++) { match[i] = bytes.readInt(); jump_table[i] = offset + bytes.readInt(); buf.append("(" + match[i] + ", " + jump_table[i] + ")"); if (i < npairs - 1) buf.append(", ");  }  buf.append(")"); return buf.toString();case 153: case 154: case 155: case 156: case 157: case 158: case 159: case 160: case 161: case 162: case 163: case 164: case 165: case 166: case 167: case 168: case 198: case 199: buf.append("\t\t#" + (bytes.getIndex() - 1 + bytes.readShort())); return buf.toString();case 200: case 201: buf.append("\t\t#" + (bytes.getIndex() - 1 + bytes.readInt())); return buf.toString();case 21: case 22: case 23: case 24: case 25: case 54: case 55: case 56: case 57: case 58: case 169: if (wide) { vindex = bytes.readUnsignedShort(); wide = false; } else { vindex = bytes.readUnsignedByte(); }  buf.append("\t\t%" + vindex); return buf.toString();case 196: wide = true; buf.append("\t(wide)"); return buf.toString();case 188: buf.append("\t\t<" + Constants.TYPE_NAMES[bytes.readByte()] + ">"); return buf.toString();case 178: case 179: case 180: case 181: index = bytes.readUnsignedShort(); buf.append("\t\t" + constant_pool.constantToString(index, (byte)9) + (verbose ? (" (" + index + ")") : "")); return buf.toString();case 187: case 192: buf.append("\t");case 193: index = bytes.readUnsignedShort(); buf.append("\t<" + constant_pool.constantToString(index, (byte)7) + ">" + (verbose ? (" (" + index + ")") : "")); return buf.toString();case 182: case 183: case 184: index = bytes.readUnsignedShort(); buf.append("\t" + constant_pool.constantToString(index, (byte)10) + (verbose ? (" (" + index + ")") : "")); return buf.toString();case 185: index = bytes.readUnsignedShort(); nargs = bytes.readUnsignedByte(); buf.append("\t" + constant_pool.constantToString(index, (byte)11) + (verbose ? (" (" + index + ")\t") : "") + nargs + "\t" + bytes.readUnsignedByte()); return buf.toString();case 19: case 20: index = bytes.readUnsignedShort(); buf.append("\t\t" + constant_pool.constantToString(index, constant_pool.getConstant(index).getTag()) + (verbose ? (" (" + index + ")") : "")); return buf.toString();case 18: index = bytes.readUnsignedByte(); buf.append("\t\t" + constant_pool.constantToString(index, constant_pool.getConstant(index).getTag()) + (verbose ? (" (" + index + ")") : "")); return buf.toString();case 189: index = bytes.readUnsignedShort(); buf.append("\t\t<" + compactClassName(constant_pool.getConstantString(index, (byte)7), false) + ">" + (verbose ? (" (" + index + ")") : "")); return buf.toString();case 197: index = bytes.readUnsignedShort(); dimensions = bytes.readUnsignedByte(); buf.append("\t<" + compactClassName(constant_pool.getConstantString(index, (byte)7), false) + ">\t" + dimensions + (verbose ? (" (" + index + ")") : "")); return buf.toString();case 132: if (wide) { vindex = bytes.readUnsignedShort(); constant = bytes.readShort(); wide = false; } else { vindex = bytes.readUnsignedByte(); constant = bytes.readByte(); }  buf.append("\t\t%" + vindex + "\t" + constant); return buf.toString(); }  if (Constants.NO_OF_OPERANDS[opcode] > 0) for (int j = 0; j < (Constants.TYPE_OF_OPERANDS[opcode]).length; j++) { buf.append("\t\t"); switch (Constants.TYPE_OF_OPERANDS[opcode][j]) { case 8: buf.append(bytes.readByte()); break;case 9: buf.append(bytes.readShort()); break;case 10: buf.append(bytes.readInt()); break;default: System.err.println("Unreachable default case reached!"); buf.setLength(0); break; }  }   return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String codeToString(ByteSequence bytes, ConstantPool constant_pool) throws IOException {
/*  446 */     return codeToString(bytes, constant_pool, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String compactClassName(String str) {
/*  457 */     return compactClassName(str, true);
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
/*      */   public static final String compactClassName(String str, String prefix, boolean chopit) {
/*  475 */     int len = prefix.length();
/*      */     
/*  477 */     str = str.replace('/', '.');
/*      */     
/*  479 */     if (chopit)
/*      */     {
/*  481 */       if (str.startsWith(prefix) && str
/*  482 */         .substring(len).indexOf('.') == -1) {
/*  483 */         str = str.substring(len);
/*      */       }
/*      */     }
/*  486 */     return str;
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
/*      */   public static final String compactClassName(String str, boolean chopit) {
/*  500 */     return compactClassName(str, "java.lang.", chopit);
/*      */   }
/*      */   
/*      */   private static final boolean is_digit(char ch) {
/*  504 */     return (ch >= '0' && ch <= '9');
/*      */   }
/*      */   
/*      */   private static final boolean is_space(char ch) {
/*  508 */     return (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int setBit(int flag, int i) {
/*  515 */     return flag | pow2(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int clearBit(int flag, int i) {
/*  522 */     int bit = pow2(i);
/*  523 */     return ((flag & bit) == 0) ? flag : (flag ^ bit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean isSet(int flag, int i) {
/*  530 */     return ((flag & pow2(i)) != 0);
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
/*      */   public static final String methodTypeToSignature(String ret, String[] argv) throws ClassFormatException {
/*  544 */     StringBuffer buf = new StringBuffer("(");
/*      */ 
/*      */     
/*  547 */     if (argv != null) {
/*  548 */       for (int i = 0; i < argv.length; i++) {
/*  549 */         String str1 = getSignature(argv[i]);
/*      */         
/*  551 */         if (str1.endsWith("V")) {
/*  552 */           throw new ClassFormatException("Invalid type: " + argv[i]);
/*      */         }
/*  554 */         buf.append(str1);
/*      */       } 
/*      */     }
/*  557 */     String str = getSignature(ret);
/*      */     
/*  559 */     buf.append(")" + str);
/*      */     
/*  561 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String[] methodSignatureArgumentTypes(String signature) throws ClassFormatException {
/*  572 */     return methodSignatureArgumentTypes(signature, true);
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
/*      */   public static final String[] methodSignatureArgumentTypes(String signature, boolean chopit) throws ClassFormatException {
/*  585 */     ArrayList<String> vec = new ArrayList();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  590 */       if (signature.charAt(0) != '(') {
/*  591 */         throw new ClassFormatException("Invalid method signature: " + signature);
/*      */       }
/*  593 */       int index = 1;
/*      */       
/*  595 */       while (signature.charAt(index) != ')') {
/*  596 */         vec.add(signatureToString(signature.substring(index), chopit));
/*  597 */         index += consumed_chars;
/*      */       } 
/*  599 */     } catch (StringIndexOutOfBoundsException e) {
/*  600 */       throw new ClassFormatException("Invalid method signature: " + signature);
/*      */     } 
/*      */     
/*  603 */     String[] types = new String[vec.size()];
/*  604 */     vec.toArray(types);
/*  605 */     return types;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String methodSignatureReturnType(String signature) throws ClassFormatException {
/*  615 */     return methodSignatureReturnType(signature, true);
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
/*      */   public static final String methodSignatureReturnType(String signature, boolean chopit) throws ClassFormatException {
/*      */     String type;
/*      */     try {
/*  632 */       int index = signature.lastIndexOf(')') + 1;
/*  633 */       type = signatureToString(signature.substring(index), chopit);
/*  634 */     } catch (StringIndexOutOfBoundsException e) {
/*  635 */       throw new ClassFormatException("Invalid method signature: " + signature);
/*      */     } 
/*      */     
/*  638 */     return type;
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
/*      */   public static final String methodSignatureToString(String signature, String name, String access) {
/*  652 */     return methodSignatureToString(signature, name, access, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String methodSignatureToString(String signature, String name, String access, boolean chopit) {
/*  659 */     return methodSignatureToString(signature, name, access, chopit, null);
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
/*      */   public static final String methodSignatureToString(String signature, String name, String access, boolean chopit, LocalVariableTable vars) throws ClassFormatException {
/*      */     String type;
/*  696 */     StringBuffer buf = new StringBuffer("(");
/*      */ 
/*      */     
/*  699 */     int var_index = (access.indexOf("static") >= 0) ? 0 : 1;
/*      */     
/*      */     try {
/*  702 */       if (signature.charAt(0) != '(') {
/*  703 */         throw new ClassFormatException("Invalid method signature: " + signature);
/*      */       }
/*  705 */       int index = 1;
/*      */       
/*  707 */       while (signature.charAt(index) != ')') {
/*  708 */         String param_type = signatureToString(signature.substring(index), chopit);
/*  709 */         buf.append(param_type);
/*      */         
/*  711 */         if (vars != null) {
/*  712 */           LocalVariable l = vars.getLocalVariable(var_index);
/*      */           
/*  714 */           if (l != null)
/*  715 */             buf.append(" " + l.getName()); 
/*      */         } else {
/*  717 */           buf.append(" arg" + var_index);
/*      */         } 
/*  719 */         if ("double".equals(param_type) || "long".equals(param_type)) {
/*  720 */           var_index += 2;
/*      */         } else {
/*  722 */           var_index++;
/*      */         } 
/*  724 */         buf.append(", ");
/*  725 */         index += consumed_chars;
/*      */       } 
/*      */       
/*  728 */       index++;
/*      */ 
/*      */       
/*  731 */       type = signatureToString(signature.substring(index), chopit);
/*      */     }
/*  733 */     catch (StringIndexOutOfBoundsException e) {
/*  734 */       throw new ClassFormatException("Invalid method signature: " + signature);
/*      */     } 
/*      */     
/*  737 */     if (buf.length() > 1) {
/*  738 */       buf.setLength(buf.length() - 2);
/*      */     }
/*  740 */     buf.append(")");
/*      */     
/*  742 */     return access + ((access.length() > 0) ? " " : "") + type + " " + name + buf
/*  743 */       .toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static final int pow2(int n) {
/*  748 */     return 1 << n;
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
/*      */   public static final String replace(String str, String old, String new_) {
/*  761 */     StringBuffer buf = new StringBuffer();
/*      */     try {
/*      */       int index;
/*  764 */       if ((index = str.indexOf(old)) != -1) {
/*  765 */         int old_index = 0;
/*      */ 
/*      */         
/*  768 */         while ((index = str.indexOf(old, old_index)) != -1) {
/*  769 */           buf.append(str.substring(old_index, index));
/*  770 */           buf.append(new_);
/*      */           
/*  772 */           old_index = index + old.length();
/*      */         } 
/*      */         
/*  775 */         buf.append(str.substring(old_index));
/*  776 */         str = buf.toString();
/*      */       } 
/*  778 */     } catch (StringIndexOutOfBoundsException e) {
/*  779 */       System.err.println(e);
/*      */     } 
/*      */     
/*  782 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String signatureToString(String signature) {
/*  792 */     return signatureToString(signature, true);
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
/*      */   public static final String signatureToString(String signature, boolean chopit) {
/*  832 */     Utility.consumed_chars = 1; try {
/*      */       int index; int n; StringBuffer brackets; String type;
/*      */       int consumed_chars;
/*  835 */       switch (signature.charAt(0)) { case 'B':
/*  836 */           return "byte";
/*  837 */         case 'C': return "char";
/*  838 */         case 'D': return "double";
/*  839 */         case 'F': return "float";
/*  840 */         case 'I': return "int";
/*  841 */         case 'J': return "long";
/*      */         
/*      */         case 'L':
/*  844 */           index = signature.indexOf(';');
/*      */           
/*  846 */           if (index < 0) {
/*  847 */             throw new ClassFormatException("Invalid signature: " + signature);
/*      */           }
/*  849 */           Utility.consumed_chars = index + 1;
/*      */           
/*  851 */           return compactClassName(signature.substring(1, index), chopit);
/*      */         
/*      */         case 'S':
/*  854 */           return "short";
/*  855 */         case 'Z': return "boolean";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case '[':
/*  864 */           brackets = new StringBuffer();
/*      */ 
/*      */           
/*  867 */           for (n = 0; signature.charAt(n) == '['; n++) {
/*  868 */             brackets.append("[]");
/*      */           }
/*  870 */           consumed_chars = n;
/*      */ 
/*      */           
/*  873 */           type = signatureToString(signature.substring(n), chopit);
/*      */           
/*  875 */           Utility.consumed_chars += consumed_chars;
/*  876 */           return type + brackets.toString();
/*      */         
/*      */         case 'V':
/*  879 */           return "void"; }
/*      */       
/*  881 */       throw new ClassFormatException("Invalid signature: `" + signature + "'");
/*      */     
/*      */     }
/*  884 */     catch (StringIndexOutOfBoundsException e) {
/*  885 */       throw new ClassFormatException("Invalid signature: " + e + ":" + signature);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSignature(String type) {
/*  896 */     StringBuffer buf = new StringBuffer();
/*  897 */     char[] chars = type.toCharArray();
/*  898 */     boolean char_found = false, delim = false;
/*  899 */     int index = -1;
/*      */ 
/*      */     
/*  902 */     for (int i = 0; i < chars.length; i++) {
/*  903 */       switch (chars[i]) { case '\t': case '\n': case '\f': case '\r':
/*      */         case ' ':
/*  905 */           if (char_found) {
/*  906 */             delim = true;
/*      */           }
/*      */           break;
/*      */         case '[':
/*  910 */           if (!char_found) {
/*  911 */             throw new RuntimeException("Illegal type: " + type);
/*      */           }
/*  913 */           index = i;
/*      */           break;
/*      */         
/*      */         default:
/*  917 */           char_found = true;
/*  918 */           if (!delim)
/*  919 */             buf.append(chars[i]); 
/*      */           break; }
/*      */     
/*      */     } 
/*  923 */     int brackets = 0;
/*      */     
/*  925 */     if (index > 0) {
/*  926 */       brackets = countBrackets(type.substring(index));
/*      */     }
/*  928 */     type = buf.toString();
/*  929 */     buf.setLength(0);
/*      */     
/*  931 */     for (int j = 0; j < brackets; j++) {
/*  932 */       buf.append('[');
/*      */     }
/*  934 */     boolean found = false;
/*      */     
/*  936 */     for (int k = 4; k <= 12 && !found; k++) {
/*  937 */       if (Constants.TYPE_NAMES[k].equals(type)) {
/*  938 */         found = true;
/*  939 */         buf.append(Constants.SHORT_TYPE_NAMES[k]);
/*      */       } 
/*      */     } 
/*      */     
/*  943 */     if (!found) {
/*  944 */       buf.append('L' + type.replace('.', '/') + ';');
/*      */     }
/*  946 */     return buf.toString();
/*      */   }
/*      */   
/*      */   private static int countBrackets(String brackets) {
/*  950 */     char[] chars = brackets.toCharArray();
/*  951 */     int count = 0;
/*  952 */     boolean open = false;
/*      */     
/*  954 */     for (int i = 0; i < chars.length; i++) {
/*  955 */       switch (chars[i]) {
/*      */         case '[':
/*  957 */           if (open)
/*  958 */             throw new RuntimeException("Illegally nested brackets:" + brackets); 
/*  959 */           open = true;
/*      */           break;
/*      */         
/*      */         case ']':
/*  963 */           if (!open)
/*  964 */             throw new RuntimeException("Illegally nested brackets:" + brackets); 
/*  965 */           open = false;
/*  966 */           count++;
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/*  974 */     if (open) {
/*  975 */       throw new RuntimeException("Illegally nested brackets:" + brackets);
/*      */     }
/*  977 */     return count;
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
/*      */   public static final byte typeOfMethodSignature(String signature) throws ClassFormatException {
/*      */     try {
/*  993 */       if (signature.charAt(0) != '(') {
/*  994 */         throw new ClassFormatException("Invalid method signature: " + signature);
/*      */       }
/*  996 */       int index = signature.lastIndexOf(')') + 1;
/*  997 */       return typeOfSignature(signature.substring(index));
/*  998 */     } catch (StringIndexOutOfBoundsException e) {
/*  999 */       throw new ClassFormatException("Invalid method signature: " + signature);
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
/*      */   public static final byte typeOfSignature(String signature) throws ClassFormatException {
/*      */     try {
/* 1014 */       switch (signature.charAt(0)) { case 'B':
/* 1015 */           return 8;
/* 1016 */         case 'C': return 5;
/* 1017 */         case 'D': return 7;
/* 1018 */         case 'F': return 6;
/* 1019 */         case 'I': return 10;
/* 1020 */         case 'J': return 11;
/* 1021 */         case 'L': return 14;
/* 1022 */         case '[': return 13;
/* 1023 */         case 'V': return 12;
/* 1024 */         case 'Z': return 4;
/* 1025 */         case 'S': return 9; }
/*      */       
/* 1027 */       throw new ClassFormatException("Invalid method signature: " + signature);
/*      */     }
/* 1029 */     catch (StringIndexOutOfBoundsException e) {
/* 1030 */       throw new ClassFormatException("Invalid method signature: " + signature);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static short searchOpcode(String name) {
/* 1037 */     name = name.toLowerCase();
/*      */     
/* 1039 */     for (short i = 0; i < Constants.OPCODE_NAMES.length; i = (short)(i + 1)) {
/* 1040 */       if (Constants.OPCODE_NAMES[i].equals(name))
/* 1041 */         return i; 
/*      */     } 
/* 1043 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final short byteToShort(byte b) {
/* 1051 */     return (b < 0) ? (short)(256 + b) : (short)b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String toHexString(byte[] bytes) {
/* 1059 */     StringBuffer buf = new StringBuffer();
/*      */     
/* 1061 */     for (int i = 0; i < bytes.length; i++) {
/* 1062 */       short b = byteToShort(bytes[i]);
/* 1063 */       String hex = Integer.toString(b, 16);
/*      */       
/* 1065 */       if (b < 16) {
/* 1066 */         buf.append('0');
/*      */       }
/* 1068 */       buf.append(hex);
/*      */       
/* 1070 */       if (i < bytes.length - 1) {
/* 1071 */         buf.append(' ');
/*      */       }
/*      */     } 
/* 1074 */     return buf.toString();
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
/*      */   public static final String format(int i, int length, boolean left_justify, char fill) {
/* 1088 */     return fillup(Integer.toString(i), length, left_justify, fill);
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
/*      */   public static final String fillup(String str, int length, boolean left_justify, char fill) {
/* 1101 */     int len = length - str.length();
/* 1102 */     char[] buf = new char[(len < 0) ? 0 : len];
/*      */     
/* 1104 */     for (int j = 0; j < buf.length; j++) {
/* 1105 */       buf[j] = fill;
/*      */     }
/* 1107 */     if (left_justify) {
/* 1108 */       return str + new String(buf);
/*      */     }
/* 1110 */     return new String(buf) + str;
/*      */   }
/*      */ 
/*      */   
/*      */   static final boolean equals(byte[] a, byte[] b) {
/*      */     int size;
/* 1116 */     if ((size = a.length) != b.length) {
/* 1117 */       return false;
/*      */     }
/* 1119 */     for (int i = 0; i < size; i++) {
/* 1120 */       if (a[i] != b[i])
/* 1121 */         return false; 
/*      */     } 
/* 1123 */     return true;
/*      */   }
/*      */   
/*      */   public static final void printArray(PrintStream out, Object[] obj) {
/* 1127 */     out.println(printArray(obj, true));
/*      */   }
/*      */   
/*      */   public static final void printArray(PrintWriter out, Object[] obj) {
/* 1131 */     out.println(printArray(obj, true));
/*      */   }
/*      */   
/*      */   public static final String printArray(Object[] obj) {
/* 1135 */     return printArray(obj, true);
/*      */   }
/*      */   
/*      */   public static final String printArray(Object[] obj, boolean braces) {
/* 1139 */     return printArray(obj, braces, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public static final String printArray(Object[] obj, boolean braces, boolean quote) {
/* 1144 */     if (obj == null) {
/* 1145 */       return null;
/*      */     }
/* 1147 */     StringBuffer buf = new StringBuffer();
/* 1148 */     if (braces) {
/* 1149 */       buf.append('{');
/*      */     }
/* 1151 */     for (int i = 0; i < obj.length; i++) {
/* 1152 */       if (obj[i] != null) {
/* 1153 */         buf.append((quote ? "\"" : "") + obj[i].toString() + (quote ? "\"" : ""));
/*      */       } else {
/* 1155 */         buf.append("null");
/*      */       } 
/*      */       
/* 1158 */       if (i < obj.length - 1) {
/* 1159 */         buf.append(", ");
/*      */       }
/*      */     } 
/*      */     
/* 1163 */     if (braces) {
/* 1164 */       buf.append('}');
/*      */     }
/* 1166 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJavaIdentifierPart(char ch) {
/* 1172 */     return ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9') || ch == '_');
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
/*      */   public static String encode(byte[] bytes, boolean compress) throws IOException {
/* 1195 */     if (compress) {
/* 1196 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1197 */       GZIPOutputStream gos = new GZIPOutputStream(baos);
/*      */       
/* 1199 */       gos.write(bytes, 0, bytes.length);
/* 1200 */       gos.close();
/* 1201 */       baos.close();
/*      */       
/* 1203 */       bytes = baos.toByteArray();
/*      */     } 
/*      */     
/* 1206 */     CharArrayWriter caw = new CharArrayWriter();
/* 1207 */     JavaWriter jw = new JavaWriter(caw);
/*      */     
/* 1209 */     for (int i = 0; i < bytes.length; i++) {
/* 1210 */       int in = bytes[i] & 0xFF;
/* 1211 */       jw.write(in);
/*      */     } 
/*      */     
/* 1214 */     return caw.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] decode(String s, boolean uncompress) throws IOException {
/* 1223 */     char[] chars = s.toCharArray();
/*      */     
/* 1225 */     CharArrayReader car = new CharArrayReader(chars);
/* 1226 */     JavaReader jr = new JavaReader(car);
/*      */     
/* 1228 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*      */     
/*      */     int ch;
/*      */     
/* 1232 */     while ((ch = jr.read()) >= 0) {
/* 1233 */       bos.write(ch);
/*      */     }
/*      */     
/* 1236 */     bos.close();
/* 1237 */     car.close();
/* 1238 */     jr.close();
/*      */     
/* 1240 */     byte[] bytes = bos.toByteArray();
/*      */     
/* 1242 */     if (uncompress) {
/* 1243 */       GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
/*      */       
/* 1245 */       byte[] tmp = new byte[bytes.length * 3];
/* 1246 */       int count = 0;
/*      */       
/*      */       int b;
/* 1249 */       while ((b = gis.read()) >= 0) {
/* 1250 */         tmp[count++] = (byte)b;
/*      */       }
/* 1252 */       bytes = new byte[count];
/* 1253 */       System.arraycopy(tmp, 0, bytes, 0, count);
/*      */     } 
/*      */     
/* 1256 */     return bytes;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1261 */   private static int[] CHAR_MAP = new int[48];
/* 1262 */   private static int[] MAP_CHAR = new int[256];
/*      */   private static final char ESCAPE_CHAR = '$';
/*      */   
/*      */   static {
/* 1266 */     int j = 0, k = 0; int i;
/* 1267 */     for (i = 65; i <= 90; i++) {
/* 1268 */       CHAR_MAP[j] = i;
/* 1269 */       MAP_CHAR[i] = j;
/* 1270 */       j++;
/*      */     } 
/*      */     
/* 1273 */     for (i = 103; i <= 122; i++) {
/* 1274 */       CHAR_MAP[j] = i;
/* 1275 */       MAP_CHAR[i] = j;
/* 1276 */       j++;
/*      */     } 
/*      */     
/* 1279 */     CHAR_MAP[j] = 36;
/* 1280 */     MAP_CHAR[36] = j;
/* 1281 */     j++;
/*      */     
/* 1283 */     CHAR_MAP[j] = 95;
/* 1284 */     MAP_CHAR[95] = j;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class JavaReader
/*      */     extends FilterReader
/*      */   {
/*      */     public JavaReader(Reader in) {
/* 1292 */       super(in);
/*      */     }
/*      */     
/*      */     public int read() throws IOException {
/* 1296 */       int b = this.in.read();
/*      */       
/* 1298 */       if (b != 36) {
/* 1299 */         return b;
/*      */       }
/* 1301 */       int i = this.in.read();
/*      */       
/* 1303 */       if (i < 0) {
/* 1304 */         return -1;
/*      */       }
/* 1306 */       if ((i >= 48 && i <= 57) || (i >= 97 && i <= 102)) {
/* 1307 */         int j = this.in.read();
/*      */         
/* 1309 */         if (j < 0) {
/* 1310 */           return -1;
/*      */         }
/* 1312 */         char[] tmp = { (char)i, (char)j };
/* 1313 */         int s = Integer.parseInt(new String(tmp), 16);
/*      */         
/* 1315 */         return s;
/*      */       } 
/* 1317 */       return Utility.MAP_CHAR[i];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int read(char[] cbuf, int off, int len) throws IOException {
/* 1323 */       for (int i = 0; i < len; i++) {
/* 1324 */         cbuf[off + i] = (char)read();
/*      */       }
/* 1326 */       return len;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class JavaWriter
/*      */     extends FilterWriter
/*      */   {
/*      */     public JavaWriter(Writer out) {
/* 1335 */       super(out);
/*      */     }
/*      */     
/*      */     public void write(int b) throws IOException {
/* 1339 */       if (Utility.isJavaIdentifierPart((char)b) && b != 36) {
/* 1340 */         this.out.write(b);
/*      */       } else {
/* 1342 */         this.out.write(36);
/*      */ 
/*      */         
/* 1345 */         if (b >= 0 && b < 48) {
/* 1346 */           this.out.write(Utility.CHAR_MAP[b]);
/*      */         } else {
/* 1348 */           char[] tmp = Integer.toHexString(b).toCharArray();
/*      */           
/* 1350 */           if (tmp.length == 1) {
/* 1351 */             this.out.write(48);
/* 1352 */             this.out.write(tmp[0]);
/*      */           } else {
/* 1354 */             this.out.write(tmp[0]);
/* 1355 */             this.out.write(tmp[1]);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void write(char[] cbuf, int off, int len) throws IOException {
/* 1362 */       for (int i = 0; i < len; i++)
/* 1363 */         write(cbuf[off + i]); 
/*      */     }
/*      */     
/*      */     public void write(String str, int off, int len) throws IOException {
/* 1367 */       write(str.toCharArray(), off, len);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String convertString(String label) {
/* 1375 */     char[] ch = label.toCharArray();
/* 1376 */     StringBuffer buf = new StringBuffer();
/*      */     
/* 1378 */     for (int i = 0; i < ch.length; i++) {
/* 1379 */       switch (ch[i]) {
/*      */         case '\n':
/* 1381 */           buf.append("\\n"); break;
/*      */         case '\r':
/* 1383 */           buf.append("\\r"); break;
/*      */         case '"':
/* 1385 */           buf.append("\\\""); break;
/*      */         case '\'':
/* 1387 */           buf.append("\\'"); break;
/*      */         case '\\':
/* 1389 */           buf.append("\\\\"); break;
/*      */         default:
/* 1391 */           buf.append(ch[i]);
/*      */           break;
/*      */       } 
/*      */     } 
/* 1395 */     return buf.toString();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/Utility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */