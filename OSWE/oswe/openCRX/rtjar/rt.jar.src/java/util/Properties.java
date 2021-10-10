/*      */ package java.util;
/*      */ 
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import jdk.internal.util.xml.BasicXmlPropertiesProvider;
/*      */ import sun.util.spi.XmlPropertiesProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Properties
/*      */   extends Hashtable<Object, Object>
/*      */ {
/*      */   private static final long serialVersionUID = 4112578634029874840L;
/*      */   protected Properties defaults;
/*      */   
/*      */   public Properties() {
/*  140 */     this((Properties)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Properties(Properties paramProperties) {
/*  149 */     this.defaults = paramProperties;
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
/*      */   public synchronized Object setProperty(String paramString1, String paramString2) {
/*  166 */     return put(paramString1, paramString2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void load(Reader paramReader) throws IOException {
/*  317 */     load0(new LineReader(paramReader));
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
/*      */   public synchronized void load(InputStream paramInputStream) throws IOException {
/*  341 */     load0(new LineReader(paramInputStream));
/*      */   }
/*      */   
/*      */   private void load0(LineReader paramLineReader) throws IOException {
/*  345 */     char[] arrayOfChar = new char[1024];
/*      */ 
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */ 
/*      */     
/*  353 */     while ((i = paramLineReader.readLine()) >= 0) {
/*  354 */       char c = Character.MIN_VALUE;
/*  355 */       byte b = 0;
/*  356 */       int j = i;
/*  357 */       boolean bool1 = false;
/*      */ 
/*      */       
/*  360 */       boolean bool2 = false;
/*  361 */       while (b < i) {
/*  362 */         c = paramLineReader.lineBuf[b];
/*      */         
/*  364 */         if ((c == '=' || c == ':') && !bool2) {
/*  365 */           j = b + 1;
/*  366 */           bool1 = true; break;
/*      */         } 
/*  368 */         if ((c == ' ' || c == '\t' || c == '\f') && !bool2) {
/*  369 */           j = b + 1;
/*      */           break;
/*      */         } 
/*  372 */         if (c == '\\') {
/*  373 */           bool2 = !bool2 ? true : false;
/*      */         } else {
/*  375 */           bool2 = false;
/*      */         } 
/*  377 */         b++;
/*      */       } 
/*  379 */       while (j < i) {
/*  380 */         c = paramLineReader.lineBuf[j];
/*  381 */         if (c != ' ' && c != '\t' && c != '\f') {
/*  382 */           if (!bool1 && (c == '=' || c == ':')) {
/*  383 */             bool1 = true;
/*      */           } else {
/*      */             break;
/*      */           } 
/*      */         }
/*  388 */         j++;
/*      */       } 
/*  390 */       String str1 = loadConvert(paramLineReader.lineBuf, 0, b, arrayOfChar);
/*  391 */       String str2 = loadConvert(paramLineReader.lineBuf, j, i - j, arrayOfChar);
/*  392 */       put(str1, str2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   class LineReader
/*      */   {
/*      */     byte[] inByteBuf;
/*      */     
/*      */     char[] inCharBuf;
/*      */     
/*      */     char[] lineBuf;
/*      */     
/*      */     int inLimit;
/*      */     
/*      */     int inOff;
/*      */     
/*      */     InputStream inStream;
/*      */     
/*      */     Reader reader;
/*      */ 
/*      */     
/*      */     public LineReader(InputStream param1InputStream) {
/*  415 */       this.lineBuf = new char[1024];
/*  416 */       this.inLimit = 0;
/*  417 */       this.inOff = 0; this.inStream = param1InputStream; this.inByteBuf = new byte[8192]; } public LineReader(Reader param1Reader) { this.lineBuf = new char[1024]; this.inLimit = 0; this.inOff = 0;
/*      */       this.reader = param1Reader;
/*      */       this.inCharBuf = new char[8192]; }
/*      */     
/*      */     int readLine() throws IOException {
/*  422 */       byte b = 0;
/*  423 */       char c = Character.MIN_VALUE;
/*      */       
/*  425 */       boolean bool1 = true;
/*  426 */       boolean bool2 = false;
/*  427 */       boolean bool3 = true;
/*  428 */       boolean bool4 = false;
/*  429 */       boolean bool5 = false;
/*  430 */       boolean bool6 = false;
/*      */       
/*      */       while (true) {
/*  433 */         if (this.inOff >= this.inLimit) {
/*  434 */           this
/*  435 */             .inLimit = (this.inStream == null) ? this.reader.read(this.inCharBuf) : this.inStream.read(this.inByteBuf);
/*  436 */           this.inOff = 0;
/*  437 */           if (this.inLimit <= 0) {
/*  438 */             if (!b || bool2) {
/*  439 */               return -1;
/*      */             }
/*  441 */             if (bool5) {
/*  442 */               b--;
/*      */             }
/*  444 */             return b;
/*      */           } 
/*      */         } 
/*  447 */         if (this.inStream != null) {
/*      */ 
/*      */           
/*  450 */           c = (char)(0xFF & this.inByteBuf[this.inOff++]);
/*      */         } else {
/*  452 */           c = this.inCharBuf[this.inOff++];
/*      */         } 
/*  454 */         if (bool6) {
/*  455 */           bool6 = false;
/*  456 */           if (c == '\n') {
/*      */             continue;
/*      */           }
/*      */         } 
/*  460 */         if (bool1) {
/*  461 */           if (c == ' ' || c == '\t' || c == '\f') {
/*      */             continue;
/*      */           }
/*  464 */           if (!bool4 && (c == '\r' || c == '\n')) {
/*      */             continue;
/*      */           }
/*  467 */           bool1 = false;
/*  468 */           bool4 = false;
/*      */         } 
/*  470 */         if (bool3) {
/*  471 */           bool3 = false;
/*  472 */           if (c == '#' || c == '!') {
/*  473 */             bool2 = true;
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*  478 */         if (c != '\n' && c != '\r') {
/*  479 */           this.lineBuf[b++] = c;
/*  480 */           if (b == this.lineBuf.length) {
/*  481 */             int i = this.lineBuf.length * 2;
/*  482 */             if (i < 0) {
/*  483 */               i = Integer.MAX_VALUE;
/*      */             }
/*  485 */             char[] arrayOfChar = new char[i];
/*  486 */             System.arraycopy(this.lineBuf, 0, arrayOfChar, 0, this.lineBuf.length);
/*  487 */             this.lineBuf = arrayOfChar;
/*      */           } 
/*      */           
/*  490 */           if (c == '\\') {
/*  491 */             bool5 = !bool5 ? true : false; continue;
/*      */           } 
/*  493 */           bool5 = false;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  498 */         if (bool2 || b == 0) {
/*  499 */           bool2 = false;
/*  500 */           bool3 = true;
/*  501 */           bool1 = true;
/*  502 */           b = 0;
/*      */           continue;
/*      */         } 
/*  505 */         if (this.inOff >= this.inLimit) {
/*  506 */           this
/*      */             
/*  508 */             .inLimit = (this.inStream == null) ? this.reader.read(this.inCharBuf) : this.inStream.read(this.inByteBuf);
/*  509 */           this.inOff = 0;
/*  510 */           if (this.inLimit <= 0) {
/*  511 */             if (bool5) {
/*  512 */               b--;
/*      */             }
/*  514 */             return b;
/*      */           } 
/*      */         } 
/*  517 */         if (bool5) {
/*  518 */           b--;
/*      */           
/*  520 */           bool1 = true;
/*  521 */           bool4 = true;
/*  522 */           bool5 = false;
/*  523 */           if (c == '\r')
/*  524 */             bool6 = true;  continue;
/*      */         }  break;
/*      */       } 
/*  527 */       return b;
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
/*      */   private String loadConvert(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2) {
/*  539 */     if (paramArrayOfchar2.length < paramInt2) {
/*  540 */       int j = paramInt2 * 2;
/*  541 */       if (j < 0) {
/*  542 */         j = Integer.MAX_VALUE;
/*      */       }
/*  544 */       paramArrayOfchar2 = new char[j];
/*      */     } 
/*      */     
/*  547 */     char[] arrayOfChar = paramArrayOfchar2;
/*  548 */     byte b = 0;
/*  549 */     int i = paramInt1 + paramInt2;
/*      */     
/*  551 */     while (paramInt1 < i) {
/*  552 */       char c = paramArrayOfchar1[paramInt1++];
/*  553 */       if (c == '\\') {
/*  554 */         c = paramArrayOfchar1[paramInt1++];
/*  555 */         if (c == 'u') {
/*      */           
/*  557 */           int j = 0;
/*  558 */           for (byte b1 = 0; b1 < 4; b1++) {
/*  559 */             c = paramArrayOfchar1[paramInt1++];
/*  560 */             switch (c) { case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7':
/*      */               case '8':
/*      */               case '9':
/*  563 */                 j = (j << 4) + c - 48; break;
/*      */               case 'a': case 'b': case 'c': case 'd':
/*      */               case 'e':
/*      */               case 'f':
/*  567 */                 j = (j << 4) + 10 + c - 97; break;
/*      */               case 'A': case 'B': case 'C': case 'D':
/*      */               case 'E':
/*      */               case 'F':
/*  571 */                 j = (j << 4) + 10 + c - 65;
/*      */                 break;
/*      */               default:
/*  574 */                 throw new IllegalArgumentException("Malformed \\uxxxx encoding."); }
/*      */ 
/*      */           
/*      */           } 
/*  578 */           arrayOfChar[b++] = (char)j; continue;
/*      */         } 
/*  580 */         if (c == 't') { c = '\t'; }
/*  581 */         else if (c == 'r') { c = '\r'; }
/*  582 */         else if (c == 'n') { c = '\n'; }
/*  583 */         else if (c == 'f') { c = '\f'; }
/*  584 */          arrayOfChar[b++] = c;
/*      */         continue;
/*      */       } 
/*  587 */       arrayOfChar[b++] = c;
/*      */     } 
/*      */     
/*  590 */     return new String(arrayOfChar, 0, b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String saveConvert(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/*  600 */     int i = paramString.length();
/*  601 */     int j = i * 2;
/*  602 */     if (j < 0) {
/*  603 */       j = Integer.MAX_VALUE;
/*      */     }
/*  605 */     StringBuffer stringBuffer = new StringBuffer(j);
/*      */     
/*  607 */     for (byte b = 0; b < i; b++) {
/*  608 */       char c = paramString.charAt(b);
/*      */ 
/*      */       
/*  611 */       if (c > '=' && c < '') {
/*  612 */         if (c == '\\') {
/*  613 */           stringBuffer.append('\\'); stringBuffer.append('\\');
/*      */         } else {
/*      */           
/*  616 */           stringBuffer.append(c);
/*      */         } 
/*      */       } else {
/*  619 */         switch (c) {
/*      */           case ' ':
/*  621 */             if (b == 0 || paramBoolean1)
/*  622 */               stringBuffer.append('\\'); 
/*  623 */             stringBuffer.append(' '); break;
/*      */           case '\t':
/*  625 */             stringBuffer.append('\\'); stringBuffer.append('t'); break;
/*      */           case '\n':
/*  627 */             stringBuffer.append('\\'); stringBuffer.append('n'); break;
/*      */           case '\r':
/*  629 */             stringBuffer.append('\\'); stringBuffer.append('r'); break;
/*      */           case '\f':
/*  631 */             stringBuffer.append('\\'); stringBuffer.append('f');
/*      */             break;
/*      */           case '!':
/*      */           case '#':
/*      */           case ':':
/*      */           case '=':
/*  637 */             stringBuffer.append('\\'); stringBuffer.append(c);
/*      */             break;
/*      */           default:
/*  640 */             if ((((c < ' ' || c > '~') ? 1 : 0) & paramBoolean2) != 0) {
/*  641 */               stringBuffer.append('\\');
/*  642 */               stringBuffer.append('u');
/*  643 */               stringBuffer.append(toHex(c >> 12 & 0xF));
/*  644 */               stringBuffer.append(toHex(c >> 8 & 0xF));
/*  645 */               stringBuffer.append(toHex(c >> 4 & 0xF));
/*  646 */               stringBuffer.append(toHex(c & 0xF)); break;
/*      */             } 
/*  648 */             stringBuffer.append(c); break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  652 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void writeComments(BufferedWriter paramBufferedWriter, String paramString) throws IOException {
/*  657 */     paramBufferedWriter.write("#");
/*  658 */     int i = paramString.length();
/*  659 */     byte b = 0;
/*  660 */     int j = 0;
/*  661 */     char[] arrayOfChar = new char[6];
/*  662 */     arrayOfChar[0] = '\\';
/*  663 */     arrayOfChar[1] = 'u';
/*  664 */     while (b < i) {
/*  665 */       char c = paramString.charAt(b);
/*  666 */       if (c > 'ÿ' || c == '\n' || c == '\r') {
/*  667 */         if (j != b)
/*  668 */           paramBufferedWriter.write(paramString.substring(j, b)); 
/*  669 */         if (c > 'ÿ') {
/*  670 */           arrayOfChar[2] = toHex(c >> 12 & 0xF);
/*  671 */           arrayOfChar[3] = toHex(c >> 8 & 0xF);
/*  672 */           arrayOfChar[4] = toHex(c >> 4 & 0xF);
/*  673 */           arrayOfChar[5] = toHex(c & 0xF);
/*  674 */           paramBufferedWriter.write(new String(arrayOfChar));
/*      */         } else {
/*  676 */           paramBufferedWriter.newLine();
/*  677 */           if (c == '\r' && b != i - 1 && paramString
/*      */             
/*  679 */             .charAt(b + 1) == '\n') {
/*  680 */             b++;
/*      */           }
/*  682 */           if (b == i - 1 || (paramString
/*  683 */             .charAt(b + 1) != '#' && paramString
/*  684 */             .charAt(b + 1) != '!'))
/*  685 */             paramBufferedWriter.write("#"); 
/*      */         } 
/*  687 */         j = b + 1;
/*      */       } 
/*  689 */       b++;
/*      */     } 
/*  691 */     if (j != b)
/*  692 */       paramBufferedWriter.write(paramString.substring(j, b)); 
/*  693 */     paramBufferedWriter.newLine();
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
/*      */   @Deprecated
/*      */   public void save(OutputStream paramOutputStream, String paramString) {
/*      */     try {
/*  715 */       store(paramOutputStream, paramString);
/*  716 */     } catch (IOException iOException) {}
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
/*      */   public void store(Writer paramWriter, String paramString) throws IOException {
/*  771 */     store0((paramWriter instanceof BufferedWriter) ? (BufferedWriter)paramWriter : new BufferedWriter(paramWriter), paramString, false);
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
/*      */   public void store(OutputStream paramOutputStream, String paramString) throws IOException {
/*  818 */     store0(new BufferedWriter(new OutputStreamWriter(paramOutputStream, "8859_1")), paramString, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void store0(BufferedWriter paramBufferedWriter, String paramString, boolean paramBoolean) throws IOException {
/*  826 */     if (paramString != null) {
/*  827 */       writeComments(paramBufferedWriter, paramString);
/*      */     }
/*  829 */     paramBufferedWriter.write("#" + (new Date()).toString());
/*  830 */     paramBufferedWriter.newLine();
/*  831 */     synchronized (this) {
/*  832 */       for (Enumeration<String> enumeration = keys(); enumeration.hasMoreElements(); ) {
/*  833 */         String str1 = enumeration.nextElement();
/*  834 */         String str2 = (String)get(str1);
/*  835 */         str1 = saveConvert(str1, true, paramBoolean);
/*      */ 
/*      */ 
/*      */         
/*  839 */         str2 = saveConvert(str2, false, paramBoolean);
/*  840 */         paramBufferedWriter.write(str1 + "=" + str2);
/*  841 */         paramBufferedWriter.newLine();
/*      */       } 
/*      */     } 
/*  844 */     paramBufferedWriter.flush();
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
/*      */   public synchronized void loadFromXML(InputStream paramInputStream) throws IOException, InvalidPropertiesFormatException {
/*  881 */     XmlSupport.load(this, Objects.<InputStream>requireNonNull(paramInputStream));
/*  882 */     paramInputStream.close();
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
/*      */   public void storeToXML(OutputStream paramOutputStream, String paramString) throws IOException {
/*  908 */     storeToXML(paramOutputStream, paramString, "UTF-8");
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
/*      */   public void storeToXML(OutputStream paramOutputStream, String paramString1, String paramString2) throws IOException {
/*  953 */     XmlSupport.save(this, Objects.<OutputStream>requireNonNull(paramOutputStream), paramString1, 
/*  954 */         Objects.<String>requireNonNull(paramString2));
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
/*      */   public String getProperty(String paramString) {
/*  969 */     V v = get(paramString);
/*  970 */     String str = (v instanceof String) ? (String)v : null;
/*  971 */     return (str == null && this.defaults != null) ? this.defaults.getProperty(paramString) : str;
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
/*      */   public String getProperty(String paramString1, String paramString2) {
/*  988 */     String str = getProperty(paramString1);
/*  989 */     return (str == null) ? paramString2 : str;
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
/*      */   public Enumeration<?> propertyNames() {
/* 1007 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1008 */     enumerate((Hashtable)hashtable);
/* 1009 */     return hashtable.keys();
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
/*      */   public Set<String> stringPropertyNames() {
/* 1031 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1032 */     enumerateStringProperties((Hashtable)hashtable);
/* 1033 */     return hashtable.keySet();
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
/*      */   public void list(PrintStream paramPrintStream) {
/* 1045 */     paramPrintStream.println("-- listing properties --");
/* 1046 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1047 */     enumerate((Hashtable)hashtable);
/* 1048 */     for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/* 1049 */       String str1 = enumeration.nextElement();
/* 1050 */       String str2 = (String)hashtable.get(str1);
/* 1051 */       if (str2.length() > 40) {
/* 1052 */         str2 = str2.substring(0, 37) + "...";
/*      */       }
/* 1054 */       paramPrintStream.println(str1 + "=" + str2);
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
/*      */   public void list(PrintWriter paramPrintWriter) {
/* 1073 */     paramPrintWriter.println("-- listing properties --");
/* 1074 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1075 */     enumerate((Hashtable)hashtable);
/* 1076 */     for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/* 1077 */       String str1 = enumeration.nextElement();
/* 1078 */       String str2 = (String)hashtable.get(str1);
/* 1079 */       if (str2.length() > 40) {
/* 1080 */         str2 = str2.substring(0, 37) + "...";
/*      */       }
/* 1082 */       paramPrintWriter.println(str1 + "=" + str2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void enumerate(Hashtable<String, Object> paramHashtable) {
/* 1093 */     if (this.defaults != null) {
/* 1094 */       this.defaults.enumerate(paramHashtable);
/*      */     }
/* 1096 */     for (Enumeration<String> enumeration = keys(); enumeration.hasMoreElements(); ) {
/* 1097 */       String str = enumeration.nextElement();
/* 1098 */       paramHashtable.put(str, get(str));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void enumerateStringProperties(Hashtable<String, String> paramHashtable) {
/* 1108 */     if (this.defaults != null) {
/* 1109 */       this.defaults.enumerateStringProperties(paramHashtable);
/*      */     }
/* 1111 */     for (Enumeration<Object> enumeration = keys(); enumeration.hasMoreElements(); ) {
/* 1112 */       String str = (String)enumeration.nextElement();
/* 1113 */       Object object = get(str);
/* 1114 */       if (str instanceof String && object instanceof String) {
/* 1115 */         paramHashtable.put(str, (String)object);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static char toHex(int paramInt) {
/* 1125 */     return hexDigit[paramInt & 0xF];
/*      */   }
/*      */ 
/*      */   
/* 1129 */   private static final char[] hexDigit = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class XmlSupport
/*      */   {
/*      */     private static XmlPropertiesProvider loadProviderFromProperty(ClassLoader param1ClassLoader) {
/* 1162 */       String str = System.getProperty("sun.util.spi.XmlPropertiesProvider");
/* 1163 */       if (str == null)
/* 1164 */         return null; 
/*      */       try {
/* 1166 */         Class<?> clazz = Class.forName(str, true, param1ClassLoader);
/* 1167 */         return (XmlPropertiesProvider)clazz.newInstance();
/* 1168 */       } catch (ClassNotFoundException|IllegalAccessException|InstantiationException classNotFoundException) {
/*      */ 
/*      */         
/* 1171 */         throw new ServiceConfigurationError(null, classNotFoundException);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private static XmlPropertiesProvider loadProviderAsService(ClassLoader param1ClassLoader) {
/* 1177 */       Iterator<XmlPropertiesProvider> iterator = ServiceLoader.<XmlPropertiesProvider>load(XmlPropertiesProvider.class, param1ClassLoader).iterator();
/* 1178 */       return iterator.hasNext() ? iterator.next() : null;
/*      */     }
/*      */     
/*      */     private static XmlPropertiesProvider loadProvider() {
/* 1182 */       return AccessController.<XmlPropertiesProvider>doPrivileged(new PrivilegedAction<XmlPropertiesProvider>()
/*      */           {
/*      */             public XmlPropertiesProvider run() {
/* 1185 */               ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 1186 */               XmlPropertiesProvider xmlPropertiesProvider = Properties.XmlSupport.loadProviderFromProperty(classLoader);
/* 1187 */               if (xmlPropertiesProvider != null)
/* 1188 */                 return xmlPropertiesProvider; 
/* 1189 */               xmlPropertiesProvider = Properties.XmlSupport.loadProviderAsService(classLoader);
/* 1190 */               if (xmlPropertiesProvider != null)
/* 1191 */                 return xmlPropertiesProvider; 
/* 1192 */               return (XmlPropertiesProvider)new BasicXmlPropertiesProvider();
/*      */             }
/*      */           });
/*      */     }
/* 1196 */     private static final XmlPropertiesProvider PROVIDER = loadProvider();
/*      */ 
/*      */ 
/*      */     
/*      */     static void load(Properties param1Properties, InputStream param1InputStream) throws IOException, InvalidPropertiesFormatException {
/* 1201 */       PROVIDER.load(param1Properties, param1InputStream);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static void save(Properties param1Properties, OutputStream param1OutputStream, String param1String1, String param1String2) throws IOException {
/* 1208 */       PROVIDER.store(param1Properties, param1OutputStream, param1String1, param1String2);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Properties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */