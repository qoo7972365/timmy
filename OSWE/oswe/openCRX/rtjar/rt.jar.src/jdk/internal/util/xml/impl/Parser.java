/*      */ package jdk.internal.util.xml.impl;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import jdk.internal.org.xml.sax.InputSource;
/*      */ import jdk.internal.org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Parser
/*      */ {
/*      */   public static final String FAULT = "";
/*      */   protected static final int BUFFSIZE_READER = 512;
/*      */   protected static final int BUFFSIZE_PARSER = 128;
/*      */   public static final char EOS = '￿';
/*      */   private Pair mNoNS;
/*      */   private Pair mXml;
/*      */   private Map<String, Input> mEnt;
/*      */   private Map<String, Input> mPEnt;
/*      */   protected boolean mIsSAlone;
/*      */   protected boolean mIsSAloneSet;
/*      */   protected boolean mIsNSAware;
/*      */   protected int mPh;
/*      */   protected static final int PH_BEFORE_DOC = -1;
/*      */   protected static final int PH_DOC_START = 0;
/*      */   protected static final int PH_MISC_DTD = 1;
/*      */   protected static final int PH_DTD = 2;
/*      */   protected static final int PH_DTD_MISC = 3;
/*      */   protected static final int PH_DOCELM = 4;
/*      */   protected static final int PH_DOCELM_MISC = 5;
/*      */   protected static final int PH_AFTER_DOC = 6;
/*      */   protected int mEvt;
/*      */   protected static final int EV_NULL = 0;
/*      */   protected static final int EV_ELM = 1;
/*      */   protected static final int EV_ELMS = 2;
/*      */   protected static final int EV_ELME = 3;
/*      */   protected static final int EV_TEXT = 4;
/*      */   protected static final int EV_WSPC = 5;
/*      */   protected static final int EV_PI = 6;
/*      */   protected static final int EV_CDAT = 7;
/*      */   protected static final int EV_COMM = 8;
/*      */   protected static final int EV_DTD = 9;
/*      */   protected static final int EV_ENT = 10;
/*      */   private char mESt;
/*      */   protected char[] mBuff;
/*      */   protected int mBuffIdx;
/*      */   protected Pair mPref;
/*      */   protected Pair mElm;
/*      */   protected Pair mAttL;
/*      */   protected Input mDoc;
/*      */   protected Input mInp;
/*      */   private char[] mChars;
/*      */   private int mChLen;
/*      */   private int mChIdx;
/*      */   protected Attrs mAttrs;
/*      */   private String[] mItems;
/*      */   private char mAttrIdx;
/*      */   private String mUnent;
/*      */   private Pair mDltd;
/*  128 */   private static final char[] NONS = new char[1]; static {
/*  129 */     NONS[0] = Character.MIN_VALUE;
/*      */   }
/*  131 */   private static final char[] XML = new char[4]; static {
/*  132 */     XML[0] = '\004';
/*  133 */     XML[1] = 'x';
/*  134 */     XML[2] = 'm';
/*  135 */     XML[3] = 'l';
/*      */   }
/*  137 */   private static final char[] XMLNS = new char[6]; static {
/*  138 */     XMLNS[0] = '\006';
/*  139 */     XMLNS[1] = 'x';
/*  140 */     XMLNS[2] = 'm';
/*  141 */     XMLNS[3] = 'l';
/*  142 */     XMLNS[4] = 'n';
/*  143 */     XMLNS[5] = 's';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  178 */     short s = 0;
/*      */   }
/*  180 */   private static final byte[] asctyp = new byte[128]; static {
/*  181 */     while (s < 32) {
/*  182 */       s = (short)(s + 1); asctyp[s] = 122;
/*      */     } 
/*  184 */     asctyp[9] = 32;
/*  185 */     asctyp[13] = 32;
/*  186 */     asctyp[10] = 32;
/*  187 */     while (s < 48) {
/*  188 */       s = (short)(s + 1); asctyp[s] = (byte)s;
/*      */     } 
/*  190 */     while (s <= 57) {
/*  191 */       s = (short)(s + 1); asctyp[s] = 100;
/*      */     } 
/*  193 */     while (s < 65) {
/*  194 */       s = (short)(s + 1); asctyp[s] = (byte)s;
/*      */     } 
/*  196 */     while (s <= 90) {
/*  197 */       s = (short)(s + 1); asctyp[s] = 65;
/*      */     } 
/*  199 */     while (s < 97) {
/*  200 */       s = (short)(s + 1); asctyp[s] = (byte)s;
/*      */     } 
/*  202 */     while (s <= 122) {
/*  203 */       s = (short)(s + 1); asctyp[s] = 97;
/*      */     } 
/*  205 */     while (s < 128) {
/*  206 */       s = (short)(s + 1); asctyp[s] = (byte)s;
/*      */     } 
/*      */   }
/*  209 */   private static final byte[] nmttyp = new byte[128]; static {
/*  210 */     for (s = 0; s < 48; s = (short)(s + 1)) {
/*  211 */       nmttyp[s] = -1;
/*      */     }
/*  213 */     while (s <= 57) {
/*  214 */       s = (short)(s + 1); nmttyp[s] = 2;
/*      */     } 
/*  216 */     while (s < 65) {
/*  217 */       s = (short)(s + 1); nmttyp[s] = -1;
/*      */     } 
/*      */     
/*  220 */     for (s = 91; s < 97; s = (short)(s + 1)) {
/*  221 */       nmttyp[s] = -1;
/*      */     }
/*      */     
/*  224 */     for (s = 123; s < 128; s = (short)(s + 1)) {
/*  225 */       nmttyp[s] = -1;
/*      */     }
/*  227 */     nmttyp[95] = 0;
/*  228 */     nmttyp[58] = 1;
/*  229 */     nmttyp[46] = 2;
/*  230 */     nmttyp[45] = 2;
/*  231 */     nmttyp[32] = 3;
/*  232 */     nmttyp[9] = 3;
/*  233 */     nmttyp[13] = 3;
/*  234 */     nmttyp[10] = 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Parser() {
/*  241 */     this.mPh = -1;
/*      */ 
/*      */     
/*  244 */     this.mBuff = new char[128];
/*  245 */     this.mAttrs = new Attrs();
/*      */ 
/*      */     
/*  248 */     this.mPref = pair(this.mPref);
/*  249 */     this.mPref.name = "";
/*  250 */     this.mPref.value = "";
/*  251 */     this.mPref.chars = NONS;
/*  252 */     this.mNoNS = this.mPref;
/*      */     
/*  254 */     this.mPref = pair(this.mPref);
/*  255 */     this.mPref.name = "xml";
/*  256 */     this.mPref.value = "http://www.w3.org/XML/1998/namespace";
/*  257 */     this.mPref.chars = XML;
/*  258 */     this.mXml = this.mPref;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void init() {
/*  266 */     this.mUnent = null;
/*  267 */     this.mElm = null;
/*  268 */     this.mPref = this.mXml;
/*  269 */     this.mAttL = null;
/*  270 */     this.mPEnt = new HashMap<>();
/*  271 */     this.mEnt = new HashMap<>();
/*  272 */     this.mDoc = this.mInp;
/*  273 */     this.mChars = this.mInp.chars;
/*  274 */     this.mPh = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void cleanup() {
/*  282 */     while (this.mAttL != null) {
/*  283 */       while (this.mAttL.list != null) {
/*  284 */         if (this.mAttL.list.list != null) {
/*  285 */           del(this.mAttL.list.list);
/*      */         }
/*  287 */         this.mAttL.list = del(this.mAttL.list);
/*      */       } 
/*  289 */       this.mAttL = del(this.mAttL);
/*      */     } 
/*      */     
/*  292 */     while (this.mElm != null) {
/*  293 */       this.mElm = del(this.mElm);
/*      */     }
/*      */     
/*  296 */     while (this.mPref != this.mXml) {
/*  297 */       this.mPref = del(this.mPref);
/*      */     }
/*      */     
/*  300 */     while (this.mInp != null) {
/*  301 */       pop();
/*      */     }
/*      */     
/*  304 */     if (this.mDoc != null && this.mDoc.src != null) {
/*      */       try {
/*  306 */         this.mDoc.src.close();
/*  307 */       } catch (IOException iOException) {}
/*      */     }
/*      */     
/*  310 */     this.mPEnt = null;
/*  311 */     this.mEnt = null;
/*  312 */     this.mDoc = null;
/*  313 */     this.mPh = 6;
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
/*      */   protected int step() throws Exception {
/*  326 */     this.mEvt = 0;
/*  327 */     byte b = 0;
/*  328 */     while (this.mEvt == 0) {
/*  329 */       char[] arrayOfChar; Pair pair1, pair2; char c = (this.mChIdx < this.mChLen) ? this.mChars[this.mChIdx++] : getch();
/*  330 */       switch (b) {
/*      */         case false:
/*  332 */           if (c != '<') {
/*  333 */             bkch();
/*  334 */             this.mBuffIdx = -1;
/*  335 */             b = 1;
/*      */             continue;
/*      */           } 
/*  338 */           switch (getch()) {
/*      */             case '/':
/*  340 */               this.mEvt = 3;
/*  341 */               if (this.mElm == null) {
/*  342 */                 panic("");
/*      */               }
/*      */               
/*  345 */               this.mBuffIdx = -1;
/*  346 */               bname(this.mIsNSAware);
/*  347 */               arrayOfChar = this.mElm.chars;
/*  348 */               if (arrayOfChar.length == this.mBuffIdx + 1) {
/*  349 */                 char c1; for (c1 = '\001'; c1 <= this.mBuffIdx; c1 = (char)(c1 + 1)) {
/*  350 */                   if (arrayOfChar[c1] != this.mBuff[c1]) {
/*  351 */                     panic("");
/*      */                   }
/*      */                 } 
/*      */               } else {
/*  355 */                 panic("");
/*      */               } 
/*      */               
/*  358 */               if (wsskip() != '>') {
/*  359 */                 panic("");
/*      */               }
/*  361 */               getch();
/*      */               continue;
/*      */             
/*      */             case '!':
/*  365 */               c = getch();
/*  366 */               bkch();
/*  367 */               switch (c) {
/*      */                 case '-':
/*  369 */                   this.mEvt = 8;
/*  370 */                   comm();
/*      */                   continue;
/*      */                 
/*      */                 case '[':
/*  374 */                   this.mEvt = 7;
/*  375 */                   cdat();
/*      */                   continue;
/*      */               } 
/*      */               
/*  379 */               this.mEvt = 9;
/*  380 */               dtd();
/*      */               continue;
/*      */ 
/*      */ 
/*      */             
/*      */             case '?':
/*  386 */               this.mEvt = 6;
/*  387 */               pi();
/*      */               continue;
/*      */           } 
/*      */           
/*  391 */           bkch();
/*      */ 
/*      */           
/*  394 */           this.mElm = pair(this.mElm);
/*  395 */           this.mElm.chars = qname(this.mIsNSAware);
/*  396 */           this.mElm.name = this.mElm.local();
/*  397 */           this.mElm.id = (this.mElm.next != null) ? this.mElm.next.id : 0;
/*  398 */           this.mElm.num = 0;
/*      */ 
/*      */           
/*  401 */           pair1 = find(this.mAttL, this.mElm.chars);
/*  402 */           this.mElm.list = (pair1 != null) ? pair1.list : null;
/*      */           
/*  404 */           this.mAttrIdx = Character.MIN_VALUE;
/*  405 */           pair2 = pair(null);
/*  406 */           pair2.num = 0;
/*  407 */           attr(pair2);
/*  408 */           del(pair2);
/*  409 */           this.mElm.value = this.mIsNSAware ? rslv(this.mElm.chars) : null;
/*      */           
/*  411 */           switch (wsskip()) {
/*      */             case '>':
/*  413 */               getch();
/*  414 */               this.mEvt = 2;
/*      */               continue;
/*      */             
/*      */             case '/':
/*  418 */               getch();
/*  419 */               if (getch() != '>')
/*      */               {
/*  421 */                 panic("");
/*      */               }
/*  423 */               this.mEvt = 1;
/*      */               continue;
/*      */           } 
/*      */           
/*  427 */           panic("");
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/*  434 */           switch (c) {
/*      */             case '\t':
/*      */             case '\n':
/*      */             case ' ':
/*  438 */               bappend(c);
/*      */               continue;
/*      */             
/*      */             case '\r':
/*  442 */               if (getch() != '\n') {
/*  443 */                 bkch();
/*      */               }
/*  445 */               bappend('\n');
/*      */               continue;
/*      */             
/*      */             case '<':
/*  449 */               this.mEvt = 5;
/*  450 */               bkch();
/*  451 */               bflash_ws();
/*      */               continue;
/*      */           } 
/*      */           
/*  455 */           bkch();
/*  456 */           b = 2;
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/*  462 */           switch (c) {
/*      */             case '&':
/*  464 */               if (this.mUnent == null) {
/*      */                 
/*  466 */                 if ((this.mUnent = ent('x')) != null) {
/*  467 */                   this.mEvt = 4;
/*  468 */                   bkch();
/*  469 */                   setch('&');
/*  470 */                   bflash();
/*      */                 } 
/*      */                 continue;
/*      */               } 
/*  474 */               this.mEvt = 10;
/*  475 */               skippedEnt(this.mUnent);
/*  476 */               this.mUnent = null;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case '<':
/*  481 */               this.mEvt = 4;
/*  482 */               bkch();
/*  483 */               bflash();
/*      */               continue;
/*      */             
/*      */             case '\r':
/*  487 */               if (getch() != '\n') {
/*  488 */                 bkch();
/*      */               }
/*  490 */               bappend('\n');
/*      */               continue;
/*      */             
/*      */             case '￿':
/*  494 */               panic("");
/*      */               break;
/*      */           } 
/*  497 */           bappend(c);
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  503 */       panic("");
/*      */     } 
/*      */ 
/*      */     
/*  507 */     return this.mEvt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dtd() throws Exception {
/*  518 */     Object object = null;
/*  519 */     String str = null;
/*  520 */     Pair pair = null;
/*      */     
/*  522 */     if ("DOCTYPE".equals(name(false)) != true) {
/*  523 */       panic("");
/*      */     }
/*  525 */     this.mPh = 2;
/*  526 */     for (byte b = 0; b; ) {
/*  527 */       char c = getch();
/*  528 */       switch (b) {
/*      */         case false:
/*  530 */           if (chtyp(c) != ' ') {
/*  531 */             bkch();
/*  532 */             str = name(this.mIsNSAware);
/*  533 */             wsskip();
/*  534 */             b = 1;
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case true:
/*  539 */           switch (chtyp(c)) {
/*      */             case 'A':
/*  541 */               bkch();
/*  542 */               pair = pubsys(' ');
/*  543 */               b = 2;
/*  544 */               docType(str, pair.name, pair.value);
/*      */               continue;
/*      */             
/*      */             case '[':
/*  548 */               bkch();
/*  549 */               b = 2;
/*  550 */               docType(str, null, null);
/*      */               continue;
/*      */             
/*      */             case '>':
/*  554 */               bkch();
/*  555 */               b = 3;
/*  556 */               docType(str, null, null);
/*      */               continue;
/*      */           } 
/*      */           
/*  560 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/*  565 */           switch (chtyp(c)) {
/*      */             
/*      */             case '[':
/*  568 */               dtdsub();
/*  569 */               b = 3;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case '>':
/*  574 */               bkch();
/*  575 */               b = 3;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */           } 
/*      */           
/*  583 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/*  588 */           switch (chtyp(c)) {
/*      */             case '>':
/*  590 */               if (pair != null) {
/*      */                 
/*  592 */                 InputSource inputSource = resolveEnt(str, pair.name, pair.value);
/*  593 */                 if (inputSource != null) {
/*  594 */                   if (!this.mIsSAlone) {
/*      */                     
/*  596 */                     bkch();
/*  597 */                     setch(']');
/*      */                     
/*  599 */                     push(new Input(512));
/*  600 */                     setinp(inputSource);
/*  601 */                     this.mInp.pubid = pair.name;
/*  602 */                     this.mInp.sysid = pair.value;
/*      */                     
/*  604 */                     dtdsub();
/*      */                   } else {
/*      */                     
/*  607 */                     skippedEnt("[dtd]");
/*      */                     
/*  609 */                     if (inputSource.getCharacterStream() != null) {
/*      */                       try {
/*  611 */                         inputSource.getCharacterStream().close();
/*  612 */                       } catch (IOException iOException) {}
/*      */                     }
/*      */                     
/*  615 */                     if (inputSource.getByteStream() != null) {
/*      */                       try {
/*  617 */                         inputSource.getByteStream().close();
/*  618 */                       } catch (IOException iOException) {}
/*      */                     }
/*      */                   }
/*      */                 
/*      */                 } else {
/*      */                   
/*  624 */                   skippedEnt("[dtd]");
/*      */                 } 
/*  626 */                 del(pair);
/*      */               } 
/*  628 */               b = -1;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */           } 
/*      */           
/*  636 */           panic("");
/*      */           continue;
/*      */       } 
/*      */ 
/*      */       
/*  641 */       panic("");
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
/*      */   private void dtdsub() throws Exception {
/*  654 */     for (byte b = 0; b; ) {
/*  655 */       char c = getch();
/*  656 */       switch (b) {
/*      */         case false:
/*  658 */           switch (chtyp(c)) {
/*      */             case '<':
/*  660 */               c = getch();
/*  661 */               switch (c) {
/*      */                 case '?':
/*  663 */                   pi();
/*      */                   continue;
/*      */                 
/*      */                 case '!':
/*  667 */                   c = getch();
/*  668 */                   bkch();
/*  669 */                   if (c == '-') {
/*  670 */                     comm();
/*      */                     
/*      */                     continue;
/*      */                   } 
/*  674 */                   bntok();
/*  675 */                   switch (bkeyword()) {
/*      */                     case 'n':
/*  677 */                       dtdent();
/*      */                       break;
/*      */                     
/*      */                     case 'a':
/*  681 */                       dtdattl();
/*      */                       break;
/*      */                     
/*      */                     case 'e':
/*  685 */                       dtdelm();
/*      */                       break;
/*      */                     
/*      */                     case 'o':
/*  689 */                       dtdnot();
/*      */                       break;
/*      */                     
/*      */                     default:
/*  693 */                       panic("");
/*      */                       break;
/*      */                   } 
/*  696 */                   b = 1;
/*      */                   continue;
/*      */               } 
/*      */               
/*  700 */               panic("");
/*      */               continue;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case '%':
/*  707 */               pent(' ');
/*      */               continue;
/*      */ 
/*      */             
/*      */             case ']':
/*  712 */               b = -1;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 'Z':
/*  721 */               if (getch() != ']') {
/*  722 */                 panic("");
/*      */               }
/*  724 */               b = -1;
/*      */               continue;
/*      */           } 
/*      */           
/*  728 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/*  733 */           switch (c) {
/*      */             case '>':
/*  735 */               b = 0;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case '\t':
/*      */             case '\n':
/*      */             case '\r':
/*      */             case ' ':
/*      */               continue;
/*      */           } 
/*      */           
/*  746 */           panic("");
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  752 */       panic("");
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
/*      */   private void dtdent() throws Exception {
/*  768 */     String str = null;
/*  769 */     char[] arrayOfChar = null;
/*  770 */     Input input = null;
/*  771 */     Pair pair = null;
/*      */     
/*  773 */     for (byte b = 0; b; ) {
/*  774 */       char c = getch();
/*  775 */       switch (b) {
/*      */         case false:
/*  777 */           switch (chtyp(c)) {
/*      */             case ' ':
/*      */               continue;
/*      */ 
/*      */ 
/*      */             
/*      */             case '%':
/*  784 */               c = getch();
/*  785 */               bkch();
/*  786 */               if (chtyp(c) == ' ') {
/*      */                 
/*  788 */                 wsskip();
/*  789 */                 str = name(false);
/*  790 */                 switch (chtyp(wsskip())) {
/*      */                   
/*      */                   case 'A':
/*  793 */                     pair = pubsys(' ');
/*  794 */                     if (wsskip() == '>') {
/*      */                       
/*  796 */                       if (!this.mPEnt.containsKey(str)) {
/*  797 */                         input = new Input();
/*  798 */                         input.pubid = pair.name;
/*  799 */                         input.sysid = pair.value;
/*  800 */                         this.mPEnt.put(str, input);
/*      */                       } 
/*      */                     } else {
/*  803 */                       panic("");
/*      */                     } 
/*  805 */                     del(pair);
/*  806 */                     b = -1;
/*      */                     continue;
/*      */ 
/*      */                   
/*      */                   case '"':
/*      */                   case '\'':
/*  812 */                     bqstr('d');
/*      */                     
/*  814 */                     arrayOfChar = new char[this.mBuffIdx + 1];
/*  815 */                     System.arraycopy(this.mBuff, 1, arrayOfChar, 1, arrayOfChar.length - 1);
/*      */                     
/*  817 */                     arrayOfChar[0] = ' ';
/*      */                     
/*  819 */                     if (!this.mPEnt.containsKey(str)) {
/*  820 */                       input = new Input(arrayOfChar);
/*  821 */                       input.pubid = this.mInp.pubid;
/*  822 */                       input.sysid = this.mInp.sysid;
/*  823 */                       input.xmlenc = this.mInp.xmlenc;
/*  824 */                       input.xmlver = this.mInp.xmlver;
/*  825 */                       this.mPEnt.put(str, input);
/*      */                     } 
/*  827 */                     b = -1;
/*      */                     continue;
/*      */                 } 
/*      */                 
/*  831 */                 panic("");
/*      */                 
/*      */                 continue;
/*      */               } 
/*      */               
/*  836 */               pent(' ');
/*      */               continue;
/*      */           } 
/*      */ 
/*      */           
/*  841 */           bkch();
/*  842 */           str = name(false);
/*  843 */           b = 1;
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/*  849 */           switch (chtyp(c)) {
/*      */             case '"':
/*      */             case '\'':
/*  852 */               bkch();
/*  853 */               bqstr('d');
/*  854 */               if (this.mEnt.get(str) == null) {
/*      */                 
/*  856 */                 arrayOfChar = new char[this.mBuffIdx];
/*  857 */                 System.arraycopy(this.mBuff, 1, arrayOfChar, 0, arrayOfChar.length);
/*      */                 
/*  859 */                 if (!this.mEnt.containsKey(str)) {
/*  860 */                   input = new Input(arrayOfChar);
/*  861 */                   input.pubid = this.mInp.pubid;
/*  862 */                   input.sysid = this.mInp.sysid;
/*  863 */                   input.xmlenc = this.mInp.xmlenc;
/*  864 */                   input.xmlver = this.mInp.xmlver;
/*  865 */                   this.mEnt.put(str, input);
/*      */                 } 
/*      */               } 
/*  868 */               b = -1;
/*      */               continue;
/*      */             
/*      */             case 'A':
/*  872 */               bkch();
/*  873 */               pair = pubsys(' ');
/*  874 */               switch (wsskip()) {
/*      */                 case '>':
/*  876 */                   if (!this.mEnt.containsKey(str)) {
/*  877 */                     input = new Input();
/*  878 */                     input.pubid = pair.name;
/*  879 */                     input.sysid = pair.value;
/*  880 */                     this.mEnt.put(str, input);
/*      */                   } 
/*      */                   break;
/*      */                 
/*      */                 case 'N':
/*  885 */                   if ("NDATA".equals(name(false)) == true) {
/*  886 */                     wsskip();
/*  887 */                     unparsedEntDecl(str, pair.name, pair.value, name(false));
/*      */                     break;
/*      */                   } 
/*      */                 default:
/*  891 */                   panic("");
/*      */                   break;
/*      */               } 
/*  894 */               del(pair);
/*  895 */               b = -1;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */           } 
/*      */           
/*  903 */           panic("");
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  909 */       panic("");
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
/*      */   private void dtdelm() throws Exception {
/*  926 */     wsskip();
/*  927 */     name(this.mIsNSAware);
/*      */ 
/*      */     
/*      */     while (true) {
/*  931 */       char c = getch();
/*  932 */       switch (c) {
/*      */         case '>':
/*  934 */           bkch();
/*      */           return;
/*      */         
/*      */         case '￿':
/*  938 */           panic("");
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
/*      */   private void dtdattl() throws Exception {
/*  955 */     char[] arrayOfChar = null;
/*  956 */     Pair pair = null;
/*      */     
/*  958 */     for (boolean bool = false; bool; ) {
/*  959 */       char c = getch();
/*  960 */       switch (bool) {
/*      */         case false:
/*  962 */           switch (chtyp(c)) {
/*      */             case ':':
/*      */             case 'A':
/*      */             case 'X':
/*      */             case '_':
/*      */             case 'a':
/*  968 */               bkch();
/*      */               
/*  970 */               arrayOfChar = qname(this.mIsNSAware);
/*  971 */               pair = find(this.mAttL, arrayOfChar);
/*  972 */               if (pair == null) {
/*  973 */                 pair = pair(this.mAttL);
/*  974 */                 pair.chars = arrayOfChar;
/*  975 */                 this.mAttL = pair;
/*      */               } 
/*  977 */               bool = true;
/*      */               continue;
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */             
/*      */             case '%':
/*  984 */               pent(' ');
/*      */               continue;
/*      */           } 
/*      */           
/*  988 */           panic("");
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/*  994 */           switch (chtyp(c)) {
/*      */             case ':':
/*      */             case 'A':
/*      */             case 'X':
/*      */             case '_':
/*      */             case 'a':
/* 1000 */               bkch();
/* 1001 */               dtdatt(pair);
/* 1002 */               if (wsskip() == '>') {
/*      */                 return;
/*      */               }
/*      */               continue;
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */             
/*      */             case '%':
/* 1011 */               pent(' ');
/*      */               continue;
/*      */           } 
/*      */           
/* 1015 */           panic("");
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1021 */       panic("");
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
/*      */   private void dtdatt(Pair paramPair) throws Exception {
/* 1041 */     char[] arrayOfChar = null;
/* 1042 */     Pair pair = null;
/*      */     
/* 1044 */     for (byte b = 0; b; ) {
/* 1045 */       char c = getch();
/* 1046 */       switch (b) {
/*      */         case false:
/* 1048 */           switch (chtyp(c)) {
/*      */             case ':':
/*      */             case 'A':
/*      */             case 'X':
/*      */             case '_':
/*      */             case 'a':
/* 1054 */               bkch();
/*      */               
/* 1056 */               arrayOfChar = qname(this.mIsNSAware);
/* 1057 */               pair = find(paramPair.list, arrayOfChar);
/* 1058 */               if (pair == null) {
/*      */                 
/* 1060 */                 pair = pair(paramPair.list);
/* 1061 */                 pair.chars = arrayOfChar;
/* 1062 */                 paramPair.list = pair;
/*      */               } else {
/*      */                 
/* 1065 */                 pair = pair(null);
/* 1066 */                 pair.chars = arrayOfChar;
/* 1067 */                 pair.id = 99;
/*      */               } 
/* 1069 */               wsskip();
/* 1070 */               b = 1;
/*      */               continue;
/*      */             
/*      */             case '%':
/* 1074 */               pent(' ');
/*      */               continue;
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */           } 
/*      */           
/* 1081 */           panic("");
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 1087 */           switch (chtyp(c)) {
/*      */             case '(':
/* 1089 */               pair.id = 117;
/* 1090 */               b = 2;
/*      */               continue;
/*      */             
/*      */             case '%':
/* 1094 */               pent(' ');
/*      */               continue;
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */           } 
/*      */           
/* 1101 */           bkch();
/* 1102 */           bntok();
/* 1103 */           pair.id = bkeyword();
/* 1104 */           switch (pair.id) {
/*      */             case 111:
/* 1106 */               if (wsskip() != '(') {
/* 1107 */                 panic("");
/*      */               }
/* 1109 */               c = getch();
/* 1110 */               b = 2;
/*      */               continue;
/*      */             
/*      */             case 78:
/*      */             case 82:
/*      */             case 84:
/*      */             case 99:
/*      */             case 105:
/*      */             case 110:
/*      */             case 114:
/*      */             case 116:
/* 1121 */               wsskip();
/* 1122 */               b = 4;
/*      */               continue;
/*      */           } 
/*      */           
/* 1126 */           panic("");
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 1134 */           switch (chtyp(c)) {
/*      */             case '-':
/*      */             case '.':
/*      */             case ':':
/*      */             case 'A':
/*      */             case 'X':
/*      */             case '_':
/*      */             case 'a':
/*      */             case 'd':
/* 1143 */               bkch();
/* 1144 */               switch (pair.id) {
/*      */                 case 117:
/* 1146 */                   bntok();
/*      */                   break;
/*      */                 
/*      */                 case 111:
/* 1150 */                   this.mBuffIdx = -1;
/* 1151 */                   bname(false);
/*      */                   break;
/*      */                 
/*      */                 default:
/* 1155 */                   panic("");
/*      */                   break;
/*      */               } 
/* 1158 */               wsskip();
/* 1159 */               b = 3;
/*      */               continue;
/*      */             
/*      */             case '%':
/* 1163 */               pent(' ');
/*      */               continue;
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */           } 
/*      */           
/* 1170 */           panic("");
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 1176 */           switch (c) {
/*      */             case ')':
/* 1178 */               wsskip();
/* 1179 */               b = 4;
/*      */               continue;
/*      */             
/*      */             case '|':
/* 1183 */               wsskip();
/* 1184 */               switch (pair.id) {
/*      */                 case 117:
/* 1186 */                   bntok();
/*      */                   break;
/*      */                 
/*      */                 case 111:
/* 1190 */                   this.mBuffIdx = -1;
/* 1191 */                   bname(false);
/*      */                   break;
/*      */                 
/*      */                 default:
/* 1195 */                   panic("");
/*      */                   break;
/*      */               } 
/* 1198 */               wsskip();
/*      */               continue;
/*      */             
/*      */             case '%':
/* 1202 */               pent(' ');
/*      */               continue;
/*      */           } 
/*      */           
/* 1206 */           panic("");
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 1212 */           switch (c) {
/*      */             case '#':
/* 1214 */               bntok();
/* 1215 */               switch (bkeyword()) {
/*      */                 case 'F':
/* 1217 */                   switch (wsskip()) {
/*      */                     case '"':
/*      */                     case '\'':
/* 1220 */                       b = 5;
/*      */                       continue;
/*      */                     
/*      */                     case '￿':
/* 1224 */                       panic("");
/*      */                       break;
/*      */                   } 
/* 1227 */                   b = -1;
/*      */                   continue;
/*      */ 
/*      */ 
/*      */                 
/*      */                 case 'I':
/*      */                 case 'Q':
/* 1234 */                   b = -1;
/*      */                   continue;
/*      */               } 
/*      */               
/* 1238 */               panic("");
/*      */               continue;
/*      */ 
/*      */ 
/*      */             
/*      */             case '"':
/*      */             case '\'':
/* 1245 */               bkch();
/* 1246 */               b = 5;
/*      */               continue;
/*      */             
/*      */             case '\t':
/*      */             case '\n':
/*      */             case '\r':
/*      */             case ' ':
/*      */               continue;
/*      */             
/*      */             case '%':
/* 1256 */               pent(' ');
/*      */               continue;
/*      */           } 
/*      */           
/* 1260 */           bkch();
/* 1261 */           b = -1;
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 1267 */           switch (c) {
/*      */             case '"':
/*      */             case '\'':
/* 1270 */               bkch();
/* 1271 */               bqstr('d');
/* 1272 */               pair.list = pair(null);
/*      */               
/* 1274 */               pair.list.chars = new char[pair.chars.length + this.mBuffIdx + 3];
/* 1275 */               System.arraycopy(pair.chars, 1, pair.list.chars, 0, pair.chars.length - 1);
/*      */               
/* 1277 */               pair.list.chars[pair.chars.length - 1] = '=';
/* 1278 */               pair.list.chars[pair.chars.length] = c;
/* 1279 */               System.arraycopy(this.mBuff, 1, pair.list.chars, pair.chars.length + 1, this.mBuffIdx);
/*      */               
/* 1281 */               pair.list.chars[pair.chars.length + this.mBuffIdx + 1] = c;
/* 1282 */               pair.list.chars[pair.chars.length + this.mBuffIdx + 2] = ' ';
/* 1283 */               b = -1;
/*      */               continue;
/*      */           } 
/*      */           
/* 1287 */           panic("");
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1293 */       panic("");
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
/*      */   private void dtdnot() throws Exception {
/* 1308 */     wsskip();
/* 1309 */     String str = name(false);
/* 1310 */     wsskip();
/* 1311 */     Pair pair = pubsys('N');
/* 1312 */     notDecl(str, pair.name, pair.value);
/* 1313 */     del(pair);
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
/*      */   private void attr(Pair paramPair) throws Exception {
/* 1335 */     switch (wsskip()) {
/*      */       case '/':
/*      */       case '>':
/* 1338 */         if ((paramPair.num & 0x2) == 0) {
/* 1339 */           paramPair.num |= 0x2;
/* 1340 */           Input input = this.mInp;
/*      */           
/* 1342 */           for (Pair pair1 = this.mElm.list; pair1 != null; pair1 = pair1.next) {
/* 1343 */             if (pair1.list != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1349 */               Pair pair2 = find(paramPair.next, pair1.chars);
/* 1350 */               if (pair2 == null)
/* 1351 */                 push(new Input(pair1.list.chars)); 
/*      */             } 
/*      */           } 
/* 1354 */           if (this.mInp != input) {
/* 1355 */             attr(paramPair);
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/* 1360 */         this.mAttrs.setLength(this.mAttrIdx);
/* 1361 */         this.mItems = this.mAttrs.mItems;
/*      */         return;
/*      */       
/*      */       case '￿':
/* 1365 */         panic("");
/*      */         break;
/*      */     } 
/*      */     
/* 1369 */     paramPair.chars = qname(this.mIsNSAware);
/* 1370 */     paramPair.name = paramPair.local();
/* 1371 */     String str1 = atype(paramPair);
/* 1372 */     wsskip();
/* 1373 */     if (getch() != '=') {
/* 1374 */       panic("");
/*      */     }
/* 1376 */     bqstr((char)paramPair.id);
/* 1377 */     String str2 = new String(this.mBuff, 1, this.mBuffIdx);
/* 1378 */     Pair pair = pair(paramPair);
/* 1379 */     paramPair.num &= 0xFFFFFFFE;
/*      */     
/* 1381 */     if (!this.mIsNSAware || !isdecl(paramPair, str2)) {
/*      */       
/* 1383 */       this.mAttrIdx = (char)(this.mAttrIdx + 1);
/* 1384 */       attr(pair);
/* 1385 */       this.mAttrIdx = (char)(this.mAttrIdx - 1);
/*      */       
/* 1387 */       char c = (char)(this.mAttrIdx << 3);
/* 1388 */       this.mItems[c + 1] = paramPair.qname();
/* 1389 */       this.mItems[c + 2] = this.mIsNSAware ? paramPair.name : "";
/* 1390 */       this.mItems[c + 3] = str2;
/* 1391 */       this.mItems[c + 4] = str1;
/* 1392 */       switch (paramPair.num & 0x3) {
/*      */         case 0:
/* 1394 */           this.mItems[c + 5] = null;
/*      */           break;
/*      */         
/*      */         case 1:
/* 1398 */           this.mItems[c + 5] = "d";
/*      */           break;
/*      */         
/*      */         default:
/* 1402 */           this.mItems[c + 5] = "D";
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1407 */       this.mItems[c + 0] = (paramPair.chars[0] != '\000') ? rslv(paramPair.chars) : "";
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1412 */       newPrefix();
/*      */       
/* 1414 */       attr(pair);
/*      */     } 
/*      */     
/* 1417 */     del(pair);
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
/*      */   private String atype(Pair paramPair) throws Exception {
/* 1437 */     paramPair.id = 99; Pair pair;
/* 1438 */     if (this.mElm.list == null || (pair = find(this.mElm.list, paramPair.chars)) == null) {
/* 1439 */       return "CDATA";
/*      */     }
/*      */     
/* 1442 */     paramPair.num |= 0x1;
/*      */ 
/*      */     
/* 1445 */     paramPair.id = 105;
/* 1446 */     switch (pair.id) {
/*      */       case 105:
/* 1448 */         return "ID";
/*      */       
/*      */       case 114:
/* 1451 */         return "IDREF";
/*      */       
/*      */       case 82:
/* 1454 */         return "IDREFS";
/*      */       
/*      */       case 110:
/* 1457 */         return "ENTITY";
/*      */       
/*      */       case 78:
/* 1460 */         return "ENTITIES";
/*      */       
/*      */       case 116:
/* 1463 */         return "NMTOKEN";
/*      */       
/*      */       case 84:
/* 1466 */         return "NMTOKENS";
/*      */       
/*      */       case 117:
/* 1469 */         return "NMTOKEN";
/*      */       
/*      */       case 111:
/* 1472 */         return "NOTATION";
/*      */       
/*      */       case 99:
/* 1475 */         paramPair.id = 99;
/* 1476 */         return "CDATA";
/*      */     } 
/*      */     
/* 1479 */     panic("");
/*      */     
/* 1481 */     return null;
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
/*      */   private void comm() throws Exception {
/* 1494 */     if (this.mPh == 0) {
/* 1495 */       this.mPh = 1;
/*      */     }
/*      */     
/* 1498 */     this.mBuffIdx = -1;
/* 1499 */     for (byte b = 0; b; ) {
/* 1500 */       char c = (this.mChIdx < this.mChLen) ? this.mChars[this.mChIdx++] : getch();
/* 1501 */       if (c == Character.MAX_VALUE) {
/* 1502 */         panic("");
/*      */       }
/* 1504 */       switch (b) {
/*      */         case false:
/* 1506 */           if (c == '-') {
/* 1507 */             b = 1; continue;
/*      */           } 
/* 1509 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 1514 */           if (c == '-') {
/* 1515 */             b = 2; continue;
/*      */           } 
/* 1517 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 1522 */           switch (c) {
/*      */             case '-':
/* 1524 */               b = 3;
/*      */               continue;
/*      */           } 
/*      */           
/* 1528 */           bappend(c);
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 1534 */           switch (c) {
/*      */             case '-':
/* 1536 */               b = 4;
/*      */               continue;
/*      */           } 
/*      */           
/* 1540 */           bappend('-');
/* 1541 */           bappend(c);
/* 1542 */           b = 2;
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 1548 */           if (c == '>') {
/* 1549 */             comm(this.mBuff, this.mBuffIdx + 1);
/* 1550 */             b = -1;
/*      */             continue;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */       
/* 1556 */       panic("");
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
/*      */   private void pi() throws Exception {
/* 1573 */     String str = null;
/* 1574 */     this.mBuffIdx = -1;
/* 1575 */     for (byte b = 0; b; ) {
/* 1576 */       char c = getch();
/* 1577 */       if (c == Character.MAX_VALUE) {
/* 1578 */         panic("");
/*      */       }
/* 1580 */       switch (b) {
/*      */         case false:
/* 1582 */           switch (chtyp(c)) {
/*      */             case ':':
/*      */             case 'A':
/*      */             case 'X':
/*      */             case '_':
/*      */             case 'a':
/* 1588 */               bkch();
/* 1589 */               str = name(false);
/*      */ 
/*      */               
/* 1592 */               if (str.length() == 0 || this.mXml.name
/* 1593 */                 .equals(str.toLowerCase()) == true) {
/* 1594 */                 panic("");
/*      */               }
/*      */               
/* 1597 */               if (this.mPh == 0)
/*      */               {
/* 1599 */                 this.mPh = 1;
/*      */               }
/* 1601 */               wsskip();
/* 1602 */               b = 1;
/* 1603 */               this.mBuffIdx = -1;
/*      */               continue;
/*      */           } 
/*      */           
/* 1607 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 1612 */           switch (c) {
/*      */             case '?':
/* 1614 */               b = 2;
/*      */               continue;
/*      */           } 
/*      */           
/* 1618 */           bappend(c);
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 1624 */           switch (c) {
/*      */             
/*      */             case '>':
/* 1627 */               pi(str, new String(this.mBuff, 0, this.mBuffIdx + 1));
/* 1628 */               b = -1;
/*      */               continue;
/*      */             
/*      */             case '?':
/* 1632 */               bappend('?');
/*      */               continue;
/*      */           } 
/*      */           
/* 1636 */           bappend('?');
/* 1637 */           bappend(c);
/* 1638 */           b = 1;
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1644 */       panic("");
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
/*      */   private void cdat() throws Exception {
/* 1662 */     this.mBuffIdx = -1;
/* 1663 */     for (byte b = 0; b; ) {
/* 1664 */       char c = getch();
/* 1665 */       switch (b) {
/*      */         case false:
/* 1667 */           if (c == '[') {
/* 1668 */             b = 1; continue;
/*      */           } 
/* 1670 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 1675 */           if (chtyp(c) == 'A') {
/* 1676 */             bappend(c); continue;
/*      */           } 
/* 1678 */           if ("CDATA".equals(new String(this.mBuff, 0, this.mBuffIdx + 1)) != true)
/*      */           {
/* 1680 */             panic("");
/*      */           }
/* 1682 */           bkch();
/* 1683 */           b = 2;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 1688 */           if (c != '[') {
/* 1689 */             panic("");
/*      */           }
/* 1691 */           this.mBuffIdx = -1;
/* 1692 */           b = 3;
/*      */           continue;
/*      */         
/*      */         case true:
/* 1696 */           if (c != ']') {
/* 1697 */             bappend(c); continue;
/*      */           } 
/* 1699 */           b = 4;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 1704 */           if (c != ']') {
/* 1705 */             bappend(']');
/* 1706 */             bappend(c);
/* 1707 */             b = 3; continue;
/*      */           } 
/* 1709 */           b = 5;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 1714 */           switch (c) {
/*      */             case ']':
/* 1716 */               bappend(']');
/*      */               continue;
/*      */             
/*      */             case '>':
/* 1720 */               bflash();
/* 1721 */               b = -1;
/*      */               continue;
/*      */           } 
/*      */           
/* 1725 */           bappend(']');
/* 1726 */           bappend(']');
/* 1727 */           bappend(c);
/* 1728 */           b = 3;
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1734 */       panic("");
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
/*      */   protected String name(boolean paramBoolean) throws Exception {
/* 1754 */     this.mBuffIdx = -1;
/* 1755 */     bname(paramBoolean);
/* 1756 */     return new String(this.mBuff, 1, this.mBuffIdx);
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
/*      */   protected char[] qname(boolean paramBoolean) throws Exception {
/* 1775 */     this.mBuffIdx = -1;
/* 1776 */     bname(paramBoolean);
/* 1777 */     char[] arrayOfChar = new char[this.mBuffIdx + 1];
/* 1778 */     System.arraycopy(this.mBuff, 0, arrayOfChar, 0, this.mBuffIdx + 1);
/* 1779 */     return arrayOfChar;
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
/*      */   private void pubsys(Input paramInput) throws Exception {
/* 1791 */     Pair pair = pubsys(' ');
/* 1792 */     paramInput.pubid = pair.name;
/* 1793 */     paramInput.sysid = pair.value;
/* 1794 */     del(pair);
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
/*      */   private Pair pubsys(char paramChar) throws Exception {
/* 1807 */     Pair pair = pair(null);
/* 1808 */     String str = name(false);
/* 1809 */     if ("PUBLIC".equals(str) == true) {
/* 1810 */       bqstr('i');
/* 1811 */       pair.name = new String(this.mBuff, 1, this.mBuffIdx);
/* 1812 */       switch (wsskip())
/*      */       { case '"':
/*      */         case '\'':
/* 1815 */           bqstr(' ');
/* 1816 */           pair.value = new String(this.mBuff, 1, this.mBuffIdx);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1830 */           return pair;case '￿': panic(""); break; }  if (paramChar != 'N') panic("");  pair.value = null; return pair;
/* 1831 */     }  if ("SYSTEM".equals(str) == true) {
/* 1832 */       pair.name = null;
/* 1833 */       bqstr(' ');
/* 1834 */       pair.value = new String(this.mBuff, 1, this.mBuffIdx);
/* 1835 */       return pair;
/*      */     } 
/* 1837 */     panic("");
/* 1838 */     return null;
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
/*      */   protected String eqstr(char paramChar) throws Exception {
/* 1859 */     if (paramChar == '=') {
/* 1860 */       wsskip();
/* 1861 */       if (getch() != '=') {
/* 1862 */         panic("");
/*      */       }
/*      */     } 
/* 1865 */     bqstr((paramChar == '=') ? 45 : paramChar);
/* 1866 */     return new String(this.mBuff, 1, this.mBuffIdx);
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
/*      */   private String ent(char paramChar) throws Exception {
/* 1885 */     int i = this.mBuffIdx + 1;
/* 1886 */     Input input = null;
/* 1887 */     String str = null;
/* 1888 */     this.mESt = 'Ā';
/* 1889 */     bappend('&');
/* 1890 */     for (byte b = 0; b; ) {
/* 1891 */       char c = (this.mChIdx < this.mChLen) ? this.mChars[this.mChIdx++] : getch();
/* 1892 */       switch (b) {
/*      */         case false:
/*      */         case true:
/* 1895 */           switch (chtyp(c)) {
/*      */             case '-':
/*      */             case '.':
/*      */             case 'd':
/* 1899 */               if (b != 1) {
/* 1900 */                 panic("");
/*      */               }
/*      */             case 'A':
/*      */             case 'X':
/*      */             case '_':
/*      */             case 'a':
/* 1906 */               bappend(c);
/* 1907 */               eappend(c);
/* 1908 */               b = 1;
/*      */               continue;
/*      */             
/*      */             case ':':
/* 1912 */               if (this.mIsNSAware) {
/* 1913 */                 panic("");
/*      */               }
/* 1915 */               bappend(c);
/* 1916 */               eappend(c);
/* 1917 */               b = 1;
/*      */               continue;
/*      */             
/*      */             case ';':
/* 1921 */               if (this.mESt < 'Ā') {
/*      */                 
/* 1923 */                 this.mBuffIdx = i - 1;
/* 1924 */                 bappend(this.mESt);
/* 1925 */                 b = -1; continue;
/*      */               } 
/* 1927 */               if (this.mPh == 2) {
/*      */ 
/*      */                 
/* 1930 */                 bappend(';');
/* 1931 */                 b = -1;
/*      */                 
/*      */                 continue;
/*      */               } 
/* 1935 */               str = new String(this.mBuff, i + 1, this.mBuffIdx - i);
/* 1936 */               input = this.mEnt.get(str);
/*      */               
/* 1938 */               this.mBuffIdx = i - 1;
/* 1939 */               if (input != null) {
/* 1940 */                 if (input.chars == null) {
/*      */                   
/* 1942 */                   InputSource inputSource = resolveEnt(str, input.pubid, input.sysid);
/* 1943 */                   if (inputSource != null) {
/* 1944 */                     push(new Input(512));
/* 1945 */                     setinp(inputSource);
/* 1946 */                     this.mInp.pubid = input.pubid;
/* 1947 */                     this.mInp.sysid = input.sysid;
/* 1948 */                     str = null;
/*      */                   
/*      */                   }
/* 1951 */                   else if (paramChar != 'x') {
/* 1952 */                     panic("");
/*      */                   }
/*      */                 
/*      */                 } else {
/*      */                   
/* 1957 */                   push(input);
/* 1958 */                   str = null;
/*      */                 }
/*      */               
/*      */               }
/* 1962 */               else if (paramChar != 'x') {
/* 1963 */                 panic("");
/*      */               } 
/*      */               
/* 1966 */               b = -1;
/*      */               continue;
/*      */             
/*      */             case '#':
/* 1970 */               if (b != 0) {
/* 1971 */                 panic("");
/*      */               }
/* 1973 */               b = 2;
/*      */               continue;
/*      */           } 
/*      */           
/* 1977 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 1982 */           switch (chtyp(c)) {
/*      */             case 'd':
/* 1984 */               bappend(c);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case ';':
/*      */               try {
/* 1990 */                 int j = Integer.parseInt(new String(this.mBuff, i + 1, this.mBuffIdx - i), 10);
/*      */                 
/* 1992 */                 if (j >= 65535) {
/* 1993 */                   panic("");
/*      */                 }
/* 1995 */                 c = (char)j;
/* 1996 */               } catch (NumberFormatException numberFormatException) {
/* 1997 */                 panic("");
/*      */               } 
/*      */               
/* 2000 */               this.mBuffIdx = i - 1;
/* 2001 */               if (c == ' ' || this.mInp.next != null) {
/* 2002 */                 bappend(c, paramChar);
/*      */               } else {
/* 2004 */                 bappend(c);
/*      */               } 
/* 2006 */               b = -1;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 'a':
/* 2011 */               if (this.mBuffIdx == i && c == 'x') {
/* 2012 */                 b = 3; continue;
/*      */               } 
/*      */               break;
/*      */           } 
/* 2016 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 2021 */           switch (chtyp(c)) {
/*      */             case 'A':
/*      */             case 'a':
/*      */             case 'd':
/* 2025 */               bappend(c);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case ';':
/*      */               try {
/* 2031 */                 int j = Integer.parseInt(new String(this.mBuff, i + 1, this.mBuffIdx - i), 16);
/*      */                 
/* 2033 */                 if (j >= 65535) {
/* 2034 */                   panic("");
/*      */                 }
/* 2036 */                 c = (char)j;
/* 2037 */               } catch (NumberFormatException numberFormatException) {
/* 2038 */                 panic("");
/*      */               } 
/*      */               
/* 2041 */               this.mBuffIdx = i - 1;
/* 2042 */               if (c == ' ' || this.mInp.next != null) {
/* 2043 */                 bappend(c, paramChar);
/*      */               } else {
/* 2045 */                 bappend(c);
/*      */               } 
/* 2047 */               b = -1;
/*      */               continue;
/*      */           } 
/*      */           
/* 2051 */           panic("");
/*      */           continue;
/*      */       } 
/*      */ 
/*      */       
/* 2056 */       panic("");
/*      */     } 
/*      */ 
/*      */     
/* 2060 */     return str;
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
/*      */   private void pent(char paramChar) throws Exception {
/* 2077 */     int i = this.mBuffIdx + 1;
/* 2078 */     Input input = null;
/* 2079 */     String str = null;
/* 2080 */     bappend('%');
/* 2081 */     if (this.mPh != 2) {
/*      */       return;
/*      */     }
/*      */     
/* 2085 */     bname(false);
/* 2086 */     str = new String(this.mBuff, i + 2, this.mBuffIdx - i - 1);
/* 2087 */     if (getch() != ';') {
/* 2088 */       panic("");
/*      */     }
/* 2090 */     input = this.mPEnt.get(str);
/*      */     
/* 2092 */     this.mBuffIdx = i - 1;
/* 2093 */     if (input != null) {
/* 2094 */       if (input.chars == null) {
/*      */         
/* 2096 */         InputSource inputSource = resolveEnt(str, input.pubid, input.sysid);
/* 2097 */         if (inputSource != null) {
/* 2098 */           if (paramChar != '-') {
/* 2099 */             bappend(' ');
/*      */           }
/* 2101 */           push(new Input(512));
/*      */           
/* 2103 */           setinp(inputSource);
/* 2104 */           this.mInp.pubid = input.pubid;
/* 2105 */           this.mInp.sysid = input.sysid;
/*      */         } else {
/*      */           
/* 2108 */           skippedEnt("%" + str);
/*      */         } 
/*      */       } else {
/*      */         
/* 2112 */         if (paramChar == '-') {
/*      */           
/* 2114 */           input.chIdx = 1;
/*      */         } else {
/*      */           
/* 2117 */           bappend(' ');
/* 2118 */           input.chIdx = 0;
/*      */         } 
/* 2120 */         push(input);
/*      */       } 
/*      */     } else {
/*      */       
/* 2124 */       skippedEnt("%" + str);
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
/*      */   private boolean isdecl(Pair paramPair, String paramString) {
/* 2140 */     if (paramPair.chars[0] == '\000') {
/* 2141 */       if ("xmlns".equals(paramPair.name) == true)
/*      */       {
/* 2143 */         this.mPref = pair(this.mPref);
/* 2144 */         this.mPref.list = this.mElm;
/* 2145 */         this.mPref.value = paramString;
/* 2146 */         this.mPref.name = "";
/* 2147 */         this.mPref.chars = NONS;
/* 2148 */         this.mElm.num++;
/* 2149 */         return true;
/*      */       }
/*      */     
/* 2152 */     } else if (paramPair.eqpref(XMLNS) == true) {
/*      */       
/* 2154 */       int i = paramPair.name.length();
/* 2155 */       this.mPref = pair(this.mPref);
/* 2156 */       this.mPref.list = this.mElm;
/* 2157 */       this.mPref.value = paramString;
/* 2158 */       this.mPref.name = paramPair.name;
/* 2159 */       this.mPref.chars = new char[i + 1];
/* 2160 */       this.mPref.chars[0] = (char)(i + 1);
/* 2161 */       paramPair.name.getChars(0, i, this.mPref.chars, 1);
/* 2162 */       this.mElm.num++;
/* 2163 */       return true;
/*      */     } 
/*      */     
/* 2166 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String rslv(char[] paramArrayOfchar) throws Exception {
/*      */     Pair pair;
/* 2177 */     for (pair = this.mPref; pair != null; pair = pair.next) {
/* 2178 */       if (pair.eqpref(paramArrayOfchar) == true) {
/* 2179 */         return pair.value;
/*      */       }
/*      */     } 
/* 2182 */     if (paramArrayOfchar[0] == '\001') {
/* 2183 */       for (pair = this.mPref; pair != null; pair = pair.next) {
/* 2184 */         if (pair.chars[0] == '\000') {
/* 2185 */           return pair.value;
/*      */         }
/*      */       } 
/*      */     }
/* 2189 */     panic("");
/* 2190 */     return null;
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
/*      */   protected char wsskip() throws IOException {
/*      */     char c;
/*      */     do {
/* 2207 */       c = (this.mChIdx < this.mChLen) ? this.mChars[this.mChIdx++] : getch();
/* 2208 */     } while (c < '' && 
/* 2209 */       nmttyp[c] == 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2217 */     this.mChIdx--;
/* 2218 */     return c;
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
/*      */   private void bname(boolean paramBoolean) throws Exception {
/* 2327 */     int i = ++this.mBuffIdx;
/* 2328 */     int j = i;
/* 2329 */     int k = i + 1;
/* 2330 */     int m = k;
/* 2331 */     int n = this.mChIdx;
/* 2332 */     short s = (short)((paramBoolean == true) ? 0 : 2);
/*      */     
/*      */     while (true) {
/* 2335 */       if (this.mChIdx >= this.mChLen) {
/* 2336 */         bcopy(n, m);
/* 2337 */         getch();
/*      */         
/* 2339 */         n = --this.mChIdx;
/* 2340 */         m = k;
/*      */       } 
/* 2342 */       char c = this.mChars[this.mChIdx++];
/* 2343 */       char c1 = Character.MIN_VALUE;
/* 2344 */       if (c < '') {
/* 2345 */         c1 = (char)nmttyp[c];
/* 2346 */       } else if (c == Character.MAX_VALUE) {
/* 2347 */         panic("");
/*      */       } 
/*      */       
/* 2350 */       switch (s) {
/*      */         case 0:
/*      */         case 2:
/* 2353 */           switch (c1) {
/*      */             case '\000':
/* 2355 */               k++;
/* 2356 */               s = (short)(s + 1);
/*      */               continue;
/*      */             
/*      */             case '\001':
/* 2360 */               this.mChIdx--;
/* 2361 */               s = (short)(s + 1);
/*      */               continue;
/*      */           } 
/*      */           
/* 2365 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 1:
/*      */         case 3:
/* 2371 */           switch (c1) {
/*      */             case '\000':
/*      */             case '\002':
/* 2374 */               k++;
/*      */               continue;
/*      */             
/*      */             case '\001':
/* 2378 */               k++;
/* 2379 */               if (paramBoolean == true) {
/* 2380 */                 if (j != i) {
/* 2381 */                   panic("");
/*      */                 }
/* 2383 */                 j = k - 1;
/* 2384 */                 if (s == 1) {
/* 2385 */                   s = 2;
/*      */                 }
/*      */               } 
/*      */               continue;
/*      */           } 
/*      */           
/* 2391 */           this.mChIdx--;
/* 2392 */           bcopy(n, m);
/* 2393 */           this.mBuff[i] = (char)(j - i);
/*      */           return;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2399 */       panic("");
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
/*      */   private void bntok() throws Exception {
/* 2415 */     this.mBuffIdx = -1;
/* 2416 */     bappend(false);
/*      */     while (true)
/* 2418 */     { char c = getch();
/* 2419 */       switch (chtyp(c)) {
/*      */         case '-':
/*      */         case '.':
/*      */         case ':':
/*      */         case 'A':
/*      */         case 'X':
/*      */         case '_':
/*      */         case 'a':
/*      */         case 'd':
/* 2428 */           bappend(c);
/*      */           continue;
/*      */         
/*      */         case 'Z':
/* 2432 */           panic(""); break;
/*      */         default:
/*      */           break;
/* 2435 */       }  bkch(); return; }  bkch();
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
/*      */   private char bkeyword() throws Exception {
/* 2455 */     String str = new String(this.mBuff, 1, this.mBuffIdx);
/* 2456 */     switch (str.length()) {
/*      */       case 2:
/* 2458 */         return ("ID".equals(str) == true) ? 'i' : '?';
/*      */       
/*      */       case 5:
/* 2461 */         switch (this.mBuff[1]) {
/*      */           case 'I':
/* 2463 */             return ("IDREF".equals(str) == true) ? 'r' : '?';
/*      */           case 'C':
/* 2465 */             return ("CDATA".equals(str) == true) ? 'c' : '?';
/*      */           case 'F':
/* 2467 */             return ("FIXED".equals(str) == true) ? 'F' : '?';
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 6:
/* 2474 */         switch (this.mBuff[1]) {
/*      */           case 'I':
/* 2476 */             return ("IDREFS".equals(str) == true) ? 'R' : '?';
/*      */           case 'E':
/* 2478 */             return ("ENTITY".equals(str) == true) ? 'n' : '?';
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 7:
/* 2485 */         switch (this.mBuff[1]) {
/*      */           case 'I':
/* 2487 */             return ("IMPLIED".equals(str) == true) ? 'I' : '?';
/*      */           case 'N':
/* 2489 */             return ("NMTOKEN".equals(str) == true) ? 't' : '?';
/*      */           case 'A':
/* 2491 */             return ("ATTLIST".equals(str) == true) ? 'a' : '?';
/*      */           case 'E':
/* 2493 */             return ("ELEMENT".equals(str) == true) ? 'e' : '?';
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 8:
/* 2500 */         switch (this.mBuff[2]) {
/*      */           case 'N':
/* 2502 */             return ("ENTITIES".equals(str) == true) ? 'N' : '?';
/*      */           case 'M':
/* 2504 */             return ("NMTOKENS".equals(str) == true) ? 'T' : '?';
/*      */           case 'O':
/* 2506 */             return ("NOTATION".equals(str) == true) ? 'o' : '?';
/*      */           case 'E':
/* 2508 */             return ("REQUIRED".equals(str) == true) ? 'Q' : '?';
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 2517 */     return '?';
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
/*      */   private void bqstr(char paramChar) throws Exception {
/* 2533 */     Input input = this.mInp;
/* 2534 */     this.mBuffIdx = -1;
/* 2535 */     bappend(false);
/*      */     
/* 2537 */     for (byte b = 0; b; ) {
/* 2538 */       char c = (this.mChIdx < this.mChLen) ? this.mChars[this.mChIdx++] : getch();
/* 2539 */       switch (b) {
/*      */         case false:
/* 2541 */           switch (c) {
/*      */             case '\t':
/*      */             case '\n':
/*      */             case '\r':
/*      */             case ' ':
/*      */               continue;
/*      */             
/*      */             case '\'':
/* 2549 */               b = 2;
/*      */               continue;
/*      */             
/*      */             case '"':
/* 2553 */               b = 3;
/*      */               continue;
/*      */           } 
/*      */           
/* 2557 */           panic("");
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/*      */         case true:
/* 2564 */           switch (c) {
/*      */             case '\'':
/* 2566 */               if (b == 2 && this.mInp == input) {
/* 2567 */                 b = -1; continue;
/*      */               } 
/* 2569 */               bappend(c);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case '"':
/* 2574 */               if (b == 3 && this.mInp == input) {
/* 2575 */                 b = -1; continue;
/*      */               } 
/* 2577 */               bappend(c);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case '&':
/* 2582 */               if (paramChar != 'd') {
/* 2583 */                 ent(paramChar); continue;
/*      */               } 
/* 2585 */               bappend(c);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case '%':
/* 2590 */               if (paramChar == 'd') {
/* 2591 */                 pent('-'); continue;
/*      */               } 
/* 2593 */               bappend(c);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case '<':
/* 2598 */               if (paramChar == '-' || paramChar == 'd') {
/* 2599 */                 bappend(c); continue;
/*      */               } 
/* 2601 */               panic("");
/*      */               continue;
/*      */ 
/*      */             
/*      */             case '￿':
/* 2606 */               panic("");
/*      */             
/*      */             case '\r':
/* 2609 */               if (paramChar != ' ' && this.mInp.next == null) {
/* 2610 */                 if (getch() != '\n') {
/* 2611 */                   bkch();
/*      */                 }
/* 2613 */                 c = '\n';
/*      */               }  break;
/*      */           } 
/* 2616 */           bappend(c, paramChar);
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2622 */       panic("");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2627 */     if (paramChar == 'i' && this.mBuff[this.mBuffIdx] == ' ') {
/* 2628 */       this.mBuffIdx--;
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
/*      */   private void bappend(char paramChar1, char paramChar2) {
/* 2659 */     switch (paramChar2) {
/*      */       case 'i':
/* 2661 */         switch (paramChar1) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 2666 */             if (this.mBuffIdx > 0 && this.mBuff[this.mBuffIdx] != ' ') {
/* 2667 */               bappend(' ');
/*      */             }
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */ 
/*      */       
/*      */       case 'c':
/* 2677 */         switch (paramChar1) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\r':
/* 2681 */             paramChar1 = ' ';
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2692 */     this.mBuffIdx++;
/* 2693 */     if (this.mBuffIdx < this.mBuff.length) {
/* 2694 */       this.mBuff[this.mBuffIdx] = paramChar1;
/*      */     } else {
/* 2696 */       this.mBuffIdx--;
/* 2697 */       bappend(paramChar1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void bappend(char paramChar) {
/*      */     try {
/* 2708 */       this.mBuff[++this.mBuffIdx] = paramChar;
/* 2709 */     } catch (Exception exception) {
/*      */       
/* 2711 */       char[] arrayOfChar = new char[this.mBuff.length << 1];
/* 2712 */       System.arraycopy(this.mBuff, 0, arrayOfChar, 0, this.mBuff.length);
/* 2713 */       this.mBuff = arrayOfChar;
/* 2714 */       this.mBuff[this.mBuffIdx] = paramChar;
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
/*      */   private void bcopy(int paramInt1, int paramInt2) {
/* 2726 */     int i = this.mChIdx - paramInt1;
/* 2727 */     if (paramInt2 + i + 1 >= this.mBuff.length) {
/*      */       
/* 2729 */       char[] arrayOfChar = new char[this.mBuff.length + i];
/* 2730 */       System.arraycopy(this.mBuff, 0, arrayOfChar, 0, this.mBuff.length);
/* 2731 */       this.mBuff = arrayOfChar;
/*      */     } 
/* 2733 */     System.arraycopy(this.mChars, paramInt1, this.mBuff, paramInt2, i);
/* 2734 */     this.mBuffIdx += i;
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
/*      */   private void eappend(char paramChar) {
/* 2746 */     switch (this.mESt) {
/*      */       case 'Ā':
/* 2748 */         switch (paramChar) {
/*      */           case 'l':
/* 2750 */             this.mESt = 'ā';
/*      */             break;
/*      */           case 'g':
/* 2753 */             this.mESt = 'Ă';
/*      */             break;
/*      */           case 'a':
/* 2756 */             this.mESt = 'ă';
/*      */             break;
/*      */           case 'q':
/* 2759 */             this.mESt = 'ć';
/*      */             break;
/*      */         } 
/* 2762 */         this.mESt = 'Ȁ';
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 'ā':
/* 2768 */         this.mESt = (paramChar == 't') ? '<' : 'Ȁ';
/*      */         break;
/*      */       
/*      */       case 'Ă':
/* 2772 */         this.mESt = (paramChar == 't') ? '>' : 'Ȁ';
/*      */         break;
/*      */       
/*      */       case 'ă':
/* 2776 */         switch (paramChar) {
/*      */           case 'm':
/* 2778 */             this.mESt = 'Ą';
/*      */             break;
/*      */           case 'p':
/* 2781 */             this.mESt = 'ą';
/*      */             break;
/*      */         } 
/* 2784 */         this.mESt = 'Ȁ';
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 'Ą':
/* 2790 */         this.mESt = (paramChar == 'p') ? '&' : 'Ȁ';
/*      */         break;
/*      */       
/*      */       case 'ą':
/* 2794 */         this.mESt = (paramChar == 'o') ? 'Ć' : 'Ȁ';
/*      */         break;
/*      */       
/*      */       case 'Ć':
/* 2798 */         this.mESt = (paramChar == 's') ? '\'' : 'Ȁ';
/*      */         break;
/*      */       
/*      */       case 'ć':
/* 2802 */         this.mESt = (paramChar == 'u') ? 'Ĉ' : 'Ȁ';
/*      */         break;
/*      */       
/*      */       case 'Ĉ':
/* 2806 */         this.mESt = (paramChar == 'o') ? 'ĉ' : 'Ȁ';
/*      */         break;
/*      */       
/*      */       case 'ĉ':
/* 2810 */         this.mESt = (paramChar == 't') ? '"' : 'Ȁ';
/*      */         break;
/*      */       
/*      */       case '"':
/*      */       case '&':
/*      */       case '\'':
/*      */       case '<':
/*      */       case '>':
/* 2818 */         this.mESt = 'Ȁ';
/*      */         break;
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
/*      */   protected void setinp(InputSource paramInputSource) throws Exception {
/* 2836 */     Reader reader = null;
/* 2837 */     this.mChIdx = 0;
/* 2838 */     this.mChLen = 0;
/* 2839 */     this.mChars = this.mInp.chars;
/* 2840 */     this.mInp.src = null;
/* 2841 */     if (this.mPh < 0) {
/* 2842 */       this.mIsSAlone = false;
/*      */     }
/* 2844 */     this.mIsSAloneSet = false;
/* 2845 */     if (paramInputSource.getCharacterStream() != null) {
/*      */       
/* 2847 */       reader = paramInputSource.getCharacterStream();
/* 2848 */       xml(reader);
/* 2849 */     } else if (paramInputSource.getByteStream() != null) {
/*      */       
/* 2851 */       if (paramInputSource.getEncoding() != null) {
/*      */         
/* 2853 */         String str = paramInputSource.getEncoding().toUpperCase();
/* 2854 */         if (str.equals("UTF-16")) {
/* 2855 */           reader = bom(paramInputSource.getByteStream(), 'U');
/*      */         } else {
/* 2857 */           reader = enc(str, paramInputSource.getByteStream());
/*      */         } 
/* 2859 */         xml(reader);
/*      */       } else {
/*      */         
/* 2862 */         reader = bom(paramInputSource.getByteStream(), ' ');
/* 2863 */         if (reader == null) {
/*      */           
/* 2865 */           reader = enc("UTF-8", paramInputSource.getByteStream());
/* 2866 */           String str = xml(reader);
/* 2867 */           if (str.startsWith("UTF-16")) {
/* 2868 */             panic("");
/*      */           }
/* 2870 */           reader = enc(str, paramInputSource.getByteStream());
/*      */         } else {
/*      */           
/* 2873 */           xml(reader);
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 2878 */       panic("");
/*      */     } 
/* 2880 */     this.mInp.src = reader;
/* 2881 */     this.mInp.pubid = paramInputSource.getPublicId();
/* 2882 */     this.mInp.sysid = paramInputSource.getSystemId();
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
/*      */   private Reader bom(InputStream paramInputStream, char paramChar) throws Exception {
/* 2900 */     int i = paramInputStream.read();
/* 2901 */     switch (i) {
/*      */       case 239:
/* 2903 */         if (paramChar == 'U')
/*      */         {
/* 2905 */           panic("");
/*      */         }
/* 2907 */         if (paramInputStream.read() != 187) {
/* 2908 */           panic("");
/*      */         }
/* 2910 */         if (paramInputStream.read() != 191) {
/* 2911 */           panic("");
/*      */         }
/* 2913 */         return new ReaderUTF8(paramInputStream);
/*      */       
/*      */       case 254:
/* 2916 */         if (paramInputStream.read() != 255) {
/* 2917 */           panic("");
/*      */         }
/* 2919 */         return new ReaderUTF16(paramInputStream, 'b');
/*      */       
/*      */       case 255:
/* 2922 */         if (paramInputStream.read() != 254) {
/* 2923 */           panic("");
/*      */         }
/* 2925 */         return new ReaderUTF16(paramInputStream, 'l');
/*      */       
/*      */       case -1:
/* 2928 */         this.mChars[this.mChIdx++] = Character.MAX_VALUE;
/* 2929 */         return new ReaderUTF8(paramInputStream);
/*      */     } 
/*      */     
/* 2932 */     if (paramChar == 'U')
/*      */     {
/* 2934 */       panic("");
/*      */     }
/*      */     
/* 2937 */     switch (i & 0xF0)
/*      */     { case 192:
/*      */       case 208:
/* 2940 */         this.mChars[this.mChIdx++] = (char)((i & 0x1F) << 6 | paramInputStream.read() & 0x3F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2955 */         return null;case 224: this.mChars[this.mChIdx++] = (char)((i & 0xF) << 12 | (paramInputStream.read() & 0x3F) << 6 | paramInputStream.read() & 0x3F); return null;case 240: throw new UnsupportedEncodingException(); }  this.mChars[this.mChIdx++] = (char)i; return null;
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
/*      */   private String xml(Reader paramReader) throws Exception {
/* 2973 */     String str1 = null;
/* 2974 */     String str2 = "UTF-8";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2979 */     if (this.mChIdx != 0) {
/*      */       
/* 2981 */       s = (short)((this.mChars[0] == '<') ? 1 : -1);
/*      */     } else {
/* 2983 */       s = 0;
/*      */     } 
/* 2985 */     while (s && this.mChIdx < this.mChars.length) {
/* 2986 */       int i; boolean bool = ((i = paramReader.read()) >= 0) ? (char)i : true;
/* 2987 */       this.mChars[this.mChIdx++] = bool;
/* 2988 */       switch (s) {
/*      */         case false:
/* 2990 */           switch (bool) {
/*      */             case true:
/* 2992 */               s = 1;
/*      */               continue;
/*      */             
/*      */             case true:
/* 2996 */               bool = ((i = paramReader.read()) >= 0) ? (char)i : true;
/* 2997 */               this.mChars[this.mChIdx - 1] = bool;
/* 2998 */               s = (short)((bool == 60) ? 1 : -1);
/*      */               continue;
/*      */           } 
/*      */           
/* 3002 */           s = -1;
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 3008 */           s = (short)((bool == 63) ? 2 : -1);
/*      */           continue;
/*      */         
/*      */         case true:
/* 3012 */           s = (short)((bool == 120) ? 3 : -1);
/*      */           continue;
/*      */         
/*      */         case true:
/* 3016 */           s = (short)((bool == 109) ? 4 : -1);
/*      */           continue;
/*      */         
/*      */         case true:
/* 3020 */           s = (short)((bool == 108) ? 5 : -1);
/*      */           continue;
/*      */         
/*      */         case true:
/* 3024 */           switch (bool) {
/*      */             case true:
/*      */             case true:
/*      */             case true:
/*      */             case true:
/* 3029 */               s = 6;
/*      */               continue;
/*      */           } 
/*      */           
/* 3033 */           s = -1;
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 3039 */           switch (bool) {
/*      */             case true:
/* 3041 */               s = 7;
/*      */               continue;
/*      */             
/*      */             case true:
/* 3045 */               s = -2;
/*      */               continue;
/*      */           } 
/*      */ 
/*      */           
/*      */           continue;
/*      */ 
/*      */         
/*      */         case true:
/* 3054 */           switch (bool) {
/*      */             case true:
/*      */             case true:
/* 3057 */               s = -2;
/*      */               continue;
/*      */           } 
/*      */           
/* 3061 */           s = 6;
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3067 */       panic("");
/*      */     } 
/*      */ 
/*      */     
/* 3071 */     this.mChLen = this.mChIdx;
/* 3072 */     this.mChIdx = 0;
/*      */     
/* 3074 */     if (s == -1) {
/* 3075 */       return str2;
/*      */     }
/* 3077 */     this.mChIdx = 5;
/*      */     
/* 3079 */     for (short s = 0; s >= 0; ) {
/* 3080 */       char c = getch();
/* 3081 */       switch (s) {
/*      */         case 0:
/* 3083 */           if (chtyp(c) != ' ') {
/* 3084 */             bkch();
/* 3085 */             s = 1;
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/* 3092 */           switch (chtyp(c)) {
/*      */             case 'A':
/*      */             case '_':
/*      */             case 'a':
/* 3096 */               bkch();
/* 3097 */               str1 = name(false).toLowerCase();
/* 3098 */               if ("version".equals(str1) == true) {
/* 3099 */                 if (s != 1) {
/* 3100 */                   panic("");
/*      */                 }
/* 3102 */                 if ("1.0".equals(eqstr('=')) != true) {
/* 3103 */                   panic("");
/*      */                 }
/* 3105 */                 this.mInp.xmlver = 'Ā';
/* 3106 */                 s = 2; continue;
/* 3107 */               }  if ("encoding".equals(str1) == true) {
/* 3108 */                 if (s != 2) {
/* 3109 */                   panic("");
/*      */                 }
/* 3111 */                 this.mInp.xmlenc = eqstr('=').toUpperCase();
/* 3112 */                 str2 = this.mInp.xmlenc;
/* 3113 */                 s = 3; continue;
/* 3114 */               }  if ("standalone".equals(str1) == true) {
/* 3115 */                 if (s == 1 || this.mPh >= 0)
/*      */                 {
/* 3117 */                   panic("");
/*      */                 }
/* 3119 */                 str1 = eqstr('=').toLowerCase();
/*      */                 
/* 3121 */                 if (str1.equals("yes") == true) {
/* 3122 */                   this.mIsSAlone = true;
/* 3123 */                 } else if (str1.equals("no") == true) {
/* 3124 */                   this.mIsSAlone = false;
/*      */                 } else {
/* 3126 */                   panic("");
/*      */                 } 
/* 3128 */                 this.mIsSAloneSet = true;
/* 3129 */                 s = 4; continue;
/*      */               } 
/* 3131 */               panic("");
/*      */               continue;
/*      */ 
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */             
/*      */             case '?':
/* 3139 */               if (s == 1) {
/* 3140 */                 panic("");
/*      */               }
/* 3142 */               bkch();
/* 3143 */               s = 4;
/*      */               continue;
/*      */           } 
/*      */           
/* 3147 */           panic("");
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 4:
/* 3152 */           switch (chtyp(c)) {
/*      */             case '?':
/* 3154 */               if (getch() != '>') {
/* 3155 */                 panic("");
/*      */               }
/* 3157 */               if (this.mPh <= 0) {
/* 3158 */                 this.mPh = 1;
/*      */               }
/* 3160 */               s = -1;
/*      */               continue;
/*      */             
/*      */             case ' ':
/*      */               continue;
/*      */           } 
/*      */           
/* 3167 */           panic("");
/*      */           continue;
/*      */       } 
/*      */ 
/*      */       
/* 3172 */       panic("");
/*      */     } 
/*      */     
/* 3175 */     return str2;
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
/*      */   private Reader enc(String paramString, InputStream paramInputStream) throws UnsupportedEncodingException {
/* 3189 */     if (paramString.equals("UTF-8"))
/* 3190 */       return new ReaderUTF8(paramInputStream); 
/* 3191 */     if (paramString.equals("UTF-16LE"))
/* 3192 */       return new ReaderUTF16(paramInputStream, 'l'); 
/* 3193 */     if (paramString.equals("UTF-16BE")) {
/* 3194 */       return new ReaderUTF16(paramInputStream, 'b');
/*      */     }
/* 3196 */     return new InputStreamReader(paramInputStream, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void push(Input paramInput) {
/* 3206 */     this.mInp.chLen = this.mChLen;
/* 3207 */     this.mInp.chIdx = this.mChIdx;
/* 3208 */     paramInput.next = this.mInp;
/* 3209 */     this.mInp = paramInput;
/* 3210 */     this.mChars = paramInput.chars;
/* 3211 */     this.mChLen = paramInput.chLen;
/* 3212 */     this.mChIdx = paramInput.chIdx;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void pop() {
/* 3219 */     if (this.mInp.src != null) {
/*      */       try {
/* 3221 */         this.mInp.src.close();
/* 3222 */       } catch (IOException iOException) {}
/*      */       
/* 3224 */       this.mInp.src = null;
/*      */     } 
/* 3226 */     this.mInp = this.mInp.next;
/* 3227 */     if (this.mInp != null) {
/* 3228 */       this.mChars = this.mInp.chars;
/* 3229 */       this.mChLen = this.mInp.chLen;
/* 3230 */       this.mChIdx = this.mInp.chIdx;
/*      */     } else {
/* 3232 */       this.mChars = null;
/* 3233 */       this.mChLen = 0;
/* 3234 */       this.mChIdx = 0;
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
/*      */   protected char chtyp(char paramChar) {
/* 3254 */     if (paramChar < '') {
/* 3255 */       return (char)asctyp[paramChar];
/*      */     }
/* 3257 */     return (paramChar != Character.MAX_VALUE) ? 'X' : 'Z';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char getch() throws IOException {
/* 3267 */     if (this.mChIdx >= this.mChLen) {
/* 3268 */       if (this.mInp.src == null) {
/* 3269 */         pop();
/* 3270 */         return getch();
/*      */       } 
/*      */       
/* 3273 */       int i = this.mInp.src.read(this.mChars, 0, this.mChars.length);
/* 3274 */       if (i < 0) {
/* 3275 */         if (this.mInp != this.mDoc) {
/* 3276 */           pop();
/* 3277 */           return getch();
/*      */         } 
/* 3279 */         this.mChars[0] = Character.MAX_VALUE;
/* 3280 */         this.mChLen = 1;
/*      */       } else {
/*      */         
/* 3283 */         this.mChLen = i;
/*      */       } 
/* 3285 */       this.mChIdx = 0;
/*      */     } 
/* 3287 */     return this.mChars[this.mChIdx++];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void bkch() throws Exception {
/* 3298 */     if (this.mChIdx <= 0) {
/* 3299 */       panic("");
/*      */     }
/* 3301 */     this.mChIdx--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setch(char paramChar) {
/* 3310 */     this.mChars[this.mChIdx] = paramChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Pair find(Pair paramPair, char[] paramArrayOfchar) {
/* 3321 */     for (Pair pair = paramPair; pair != null; pair = pair.next) {
/* 3322 */       if (pair.eqname(paramArrayOfchar) == true) {
/* 3323 */         return pair;
/*      */       }
/*      */     } 
/* 3326 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Pair pair(Pair paramPair) {
/*      */     Pair pair;
/* 3338 */     if (this.mDltd != null) {
/* 3339 */       pair = this.mDltd;
/* 3340 */       this.mDltd = pair.next;
/*      */     } else {
/* 3342 */       pair = new Pair();
/*      */     } 
/* 3344 */     pair.next = paramPair;
/*      */     
/* 3346 */     return pair;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Pair del(Pair paramPair) {
/* 3356 */     Pair pair = paramPair.next;
/*      */     
/* 3358 */     paramPair.name = null;
/* 3359 */     paramPair.value = null;
/* 3360 */     paramPair.chars = null;
/* 3361 */     paramPair.list = null;
/* 3362 */     paramPair.next = this.mDltd;
/* 3363 */     this.mDltd = paramPair;
/*      */     
/* 3365 */     return pair;
/*      */   }
/*      */   
/*      */   protected abstract void docType(String paramString1, String paramString2, String paramString3) throws SAXException;
/*      */   
/*      */   protected abstract void comm(char[] paramArrayOfchar, int paramInt);
/*      */   
/*      */   protected abstract void pi(String paramString1, String paramString2) throws Exception;
/*      */   
/*      */   protected abstract void newPrefix() throws Exception;
/*      */   
/*      */   protected abstract void skippedEnt(String paramString) throws Exception;
/*      */   
/*      */   protected abstract InputSource resolveEnt(String paramString1, String paramString2, String paramString3) throws Exception;
/*      */   
/*      */   protected abstract void notDecl(String paramString1, String paramString2, String paramString3) throws Exception;
/*      */   
/*      */   protected abstract void unparsedEntDecl(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
/*      */   
/*      */   protected abstract void panic(String paramString) throws Exception;
/*      */   
/*      */   protected abstract void bflash() throws Exception;
/*      */   
/*      */   protected abstract void bflash_ws() throws Exception;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/impl/Parser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */