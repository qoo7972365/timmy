/*     */ package jdk.internal.util.xml.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import jdk.internal.org.xml.sax.ContentHandler;
/*     */ import jdk.internal.org.xml.sax.DTDHandler;
/*     */ import jdk.internal.org.xml.sax.EntityResolver;
/*     */ import jdk.internal.org.xml.sax.ErrorHandler;
/*     */ import jdk.internal.org.xml.sax.InputSource;
/*     */ import jdk.internal.org.xml.sax.Locator;
/*     */ import jdk.internal.org.xml.sax.SAXException;
/*     */ import jdk.internal.org.xml.sax.SAXParseException;
/*     */ import jdk.internal.org.xml.sax.XMLReader;
/*     */ import jdk.internal.org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ParserSAX
/*     */   extends Parser
/*     */   implements XMLReader, Locator
/*     */ {
/*     */   public static final String FEATURE_NS = "http://xml.org/sax/features/namespaces";
/*     */   public static final String FEATURE_PREF = "http://xml.org/sax/features/namespace-prefixes";
/*     */   private boolean mFNamespaces = true;
/*     */   private boolean mFPrefixes = false;
/*  85 */   private DefaultHandler mHand = new DefaultHandler();
/*  86 */   private ContentHandler mHandCont = this.mHand;
/*  87 */   private DTDHandler mHandDtd = this.mHand;
/*  88 */   private ErrorHandler mHandErr = this.mHand;
/*  89 */   private EntityResolver mHandEnt = this.mHand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentHandler getContentHandler() {
/*  99 */     return (this.mHandCont != this.mHand) ? this.mHandCont : null;
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
/*     */   public void setContentHandler(ContentHandler paramContentHandler) {
/* 118 */     if (paramContentHandler == null) {
/* 119 */       throw new NullPointerException();
/*     */     }
/* 121 */     this.mHandCont = paramContentHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTDHandler getDTDHandler() {
/* 131 */     return (this.mHandDtd != this.mHand) ? this.mHandDtd : null;
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
/*     */   public void setDTDHandler(DTDHandler paramDTDHandler) {
/* 150 */     if (paramDTDHandler == null) {
/* 151 */       throw new NullPointerException();
/*     */     }
/* 153 */     this.mHandDtd = paramDTDHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ErrorHandler getErrorHandler() {
/* 163 */     return (this.mHandErr != this.mHand) ? this.mHandErr : null;
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
/*     */   
/*     */   public void setErrorHandler(ErrorHandler paramErrorHandler) {
/* 184 */     if (paramErrorHandler == null) {
/* 185 */       throw new NullPointerException();
/*     */     }
/* 187 */     this.mHandErr = paramErrorHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityResolver getEntityResolver() {
/* 197 */     return (this.mHandEnt != this.mHand) ? this.mHandEnt : null;
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
/*     */   public void setEntityResolver(EntityResolver paramEntityResolver) {
/* 216 */     if (paramEntityResolver == null) {
/* 217 */       throw new NullPointerException();
/*     */     }
/* 219 */     this.mHandEnt = paramEntityResolver;
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
/*     */   public String getPublicId() {
/* 235 */     return (this.mInp != null) ? this.mInp.pubid : null;
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
/*     */   public String getSystemId() {
/* 254 */     return (this.mInp != null) ? this.mInp.sysid : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/* 265 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNumber() {
/* 276 */     return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(String paramString) throws IOException, SAXException {
/* 301 */     parse(new InputSource(paramString));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(InputSource paramInputSource) throws IOException, SAXException {
/* 336 */     if (paramInputSource == null) {
/* 337 */       throw new IllegalArgumentException("");
/*     */     }
/*     */     
/* 340 */     this.mInp = new Input(512);
/* 341 */     this.mPh = -1;
/*     */     try {
/* 343 */       setinp(paramInputSource);
/* 344 */     } catch (SAXException sAXException) {
/* 345 */       throw sAXException;
/* 346 */     } catch (IOException iOException) {
/* 347 */       throw iOException;
/* 348 */     } catch (RuntimeException runtimeException) {
/* 349 */       throw runtimeException;
/* 350 */     } catch (Exception exception) {
/* 351 */       panic(exception.toString());
/*     */     } 
/* 353 */     parse();
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
/*     */   public void parse(InputStream paramInputStream, DefaultHandler paramDefaultHandler) throws SAXException, IOException {
/* 371 */     if (paramInputStream == null || paramDefaultHandler == null) {
/* 372 */       throw new IllegalArgumentException("");
/*     */     }
/* 374 */     parse(new InputSource(paramInputStream), paramDefaultHandler);
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
/*     */   public void parse(InputSource paramInputSource, DefaultHandler paramDefaultHandler) throws SAXException, IOException {
/* 393 */     if (paramInputSource == null || paramDefaultHandler == null) {
/* 394 */       throw new IllegalArgumentException("");
/*     */     }
/*     */     
/* 397 */     this.mHandCont = paramDefaultHandler;
/* 398 */     this.mHandDtd = paramDefaultHandler;
/* 399 */     this.mHandErr = paramDefaultHandler;
/* 400 */     this.mHandEnt = paramDefaultHandler;
/*     */     
/* 402 */     this.mInp = new Input(512);
/* 403 */     this.mPh = -1;
/*     */     try {
/* 405 */       setinp(paramInputSource);
/* 406 */     } catch (SAXException|IOException|RuntimeException sAXException) {
/* 407 */       throw sAXException;
/* 408 */     } catch (Exception exception) {
/* 409 */       panic(exception.toString());
/*     */     } 
/* 411 */     parse();
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
/*     */   private void parse() throws SAXException, IOException {
/* 424 */     init();
/*     */     try {
/* 426 */       this.mHandCont.setDocumentLocator(this);
/* 427 */       this.mHandCont.startDocument();
/*     */       
/* 429 */       if (this.mPh != 1) {
/* 430 */         this.mPh = 1;
/*     */       }
/* 432 */       int i = 0;
/*     */       
/*     */       do {
/* 435 */         wsskip();
/* 436 */         switch (i = step()) {
/*     */           case 1:
/*     */           case 2:
/* 439 */             this.mPh = 4;
/*     */             break;
/*     */           
/*     */           case 6:
/*     */           case 8:
/*     */             break;
/*     */           
/*     */           case 9:
/* 447 */             if (this.mPh >= 3) {
/* 448 */               panic("");
/*     */             }
/* 450 */             this.mPh = 3;
/*     */             break;
/*     */           
/*     */           default:
/* 454 */             panic(""); break;
/*     */         } 
/* 456 */       } while (this.mPh < 4);
/*     */       
/*     */       do {
/* 459 */         switch (i) {
/*     */           
/*     */           case 1:
/*     */           case 2:
/* 463 */             if (this.mIsNSAware == true) {
/* 464 */               this.mHandCont.startElement(this.mElm.value, this.mElm.name, "", this.mAttrs);
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */               
/* 470 */               this.mHandCont.startElement("", "", this.mElm.name, this.mAttrs);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 476 */             if (i == 2) {
/* 477 */               i = step();
/*     */               break;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 3:
/* 483 */             if (this.mIsNSAware == true) {
/* 484 */               this.mHandCont.endElement(this.mElm.value, this.mElm.name, "");
/*     */             } else {
/* 486 */               this.mHandCont.endElement("", "", this.mElm.name);
/*     */             } 
/*     */             
/* 489 */             while (this.mPref.list == this.mElm) {
/* 490 */               this.mHandCont.endPrefixMapping(this.mPref.name);
/* 491 */               this.mPref = del(this.mPref);
/*     */             } 
/*     */             
/* 494 */             this.mElm = del(this.mElm);
/* 495 */             if (this.mElm == null) {
/* 496 */               this.mPh = 5; break;
/*     */             } 
/* 498 */             i = step();
/*     */             break;
/*     */ 
/*     */           
/*     */           case 4:
/*     */           case 5:
/*     */           case 6:
/*     */           case 7:
/*     */           case 8:
/*     */           case 10:
/* 508 */             i = step();
/*     */             break;
/*     */           
/*     */           default:
/* 512 */             panic(""); break;
/*     */         } 
/* 514 */       } while (this.mPh == 4);
/*     */ 
/*     */       
/* 517 */       while (wsskip() != Character.MAX_VALUE)
/*     */       
/*     */       { 
/*     */         
/* 521 */         switch (step()) {
/*     */           case 6:
/*     */           case 8:
/*     */             break;
/*     */           
/*     */           default:
/* 527 */             panic(""); break;
/*     */         } 
/* 529 */         if (this.mPh != 5)
/* 530 */           break;  }  this.mPh = 6;
/*     */     }
/* 532 */     catch (SAXException sAXException) {
/* 533 */       throw sAXException;
/* 534 */     } catch (IOException iOException) {
/* 535 */       throw iOException;
/* 536 */     } catch (RuntimeException runtimeException) {
/* 537 */       throw runtimeException;
/* 538 */     } catch (Exception exception) {
/* 539 */       panic(exception.toString());
/*     */     } finally {
/* 541 */       this.mHandCont.endDocument();
/* 542 */       cleanup();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void docType(String paramString1, String paramString2, String paramString3) throws SAXException {
/* 554 */     this.mHandDtd.notationDecl(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void comm(char[] paramArrayOfchar, int paramInt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void pi(String paramString1, String paramString2) throws SAXException {
/* 573 */     this.mHandCont.processingInstruction(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void newPrefix() throws SAXException {
/* 583 */     this.mHandCont.startPrefixMapping(this.mPref.name, this.mPref.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void skippedEnt(String paramString) throws SAXException {
/* 592 */     this.mHandCont.skippedEntity(paramString);
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
/*     */   protected InputSource resolveEnt(String paramString1, String paramString2, String paramString3) throws SAXException, IOException {
/* 607 */     return this.mHandEnt.resolveEntity(paramString2, paramString3);
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
/*     */   protected void notDecl(String paramString1, String paramString2, String paramString3) throws SAXException {
/* 620 */     this.mHandDtd.notationDecl(paramString1, paramString2, paramString3);
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
/*     */   protected void unparsedEntDecl(String paramString1, String paramString2, String paramString3, String paramString4) throws SAXException {
/* 634 */     this.mHandDtd.unparsedEntityDecl(paramString1, paramString2, paramString3, paramString4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void panic(String paramString) throws SAXException {
/* 643 */     SAXParseException sAXParseException = new SAXParseException(paramString, this);
/* 644 */     this.mHandErr.fatalError(sAXParseException);
/* 645 */     throw sAXParseException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bflash() throws SAXException {
/* 655 */     if (this.mBuffIdx >= 0) {
/*     */       
/* 657 */       this.mHandCont.characters(this.mBuff, 0, this.mBuffIdx + 1);
/* 658 */       this.mBuffIdx = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bflash_ws() throws SAXException {
/* 669 */     if (this.mBuffIdx >= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 675 */       this.mHandCont.characters(this.mBuff, 0, this.mBuffIdx + 1);
/* 676 */       this.mBuffIdx = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getFeature(String paramString) {
/* 681 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   
/*     */   public void setFeature(String paramString, boolean paramBoolean) {
/* 685 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   
/*     */   public Object getProperty(String paramString) {
/* 689 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   
/*     */   public void setProperty(String paramString, Object paramObject) {
/* 693 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/impl/ParserSAX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */