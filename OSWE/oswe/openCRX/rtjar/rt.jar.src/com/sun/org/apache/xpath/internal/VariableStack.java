/*     */ package com.sun.org.apache.xpath.internal;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*     */ import com.sun.org.apache.xml.internal.utils.QName;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import javax.xml.transform.TransformerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VariableStack
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int CLEARLIMITATION = 1024;
/*     */   XObject[] _stackFrames;
/*     */   int _frameTop;
/*     */   private int _currentFrameBottom;
/*     */   int[] _links;
/*     */   int _linksTop;
/*     */   
/*     */   public VariableStack() {
/*  77 */     this._stackFrames = new XObject[8192];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     this._links = new int[4096];
/*     */     reset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Object clone() throws CloneNotSupportedException {
/*     */     VariableStack vs = (VariableStack)super.clone();
/*     */     vs._stackFrames = (XObject[])this._stackFrames.clone();
/*     */     vs._links = (int[])this._links.clone();
/*     */     return vs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XObject elementAt(int i) {
/* 113 */     return this._stackFrames[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 123 */     return this._frameTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 134 */     this._frameTop = 0;
/* 135 */     this._linksTop = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     this._links[this._linksTop++] = 0;
/* 141 */     this._stackFrames = new XObject[this._stackFrames.length];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStackFrame(int sf) {
/* 151 */     this._currentFrameBottom = sf;
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
/*     */   public int getStackFrame() {
/* 163 */     return this._currentFrameBottom;
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
/*     */   public int link(int size) {
/* 184 */     this._currentFrameBottom = this._frameTop;
/* 185 */     this._frameTop += size;
/*     */     
/* 187 */     if (this._frameTop >= this._stackFrames.length) {
/*     */       
/* 189 */       XObject[] newsf = new XObject[this._stackFrames.length + 4096 + size];
/*     */       
/* 191 */       System.arraycopy(this._stackFrames, 0, newsf, 0, this._stackFrames.length);
/*     */       
/* 193 */       this._stackFrames = newsf;
/*     */     } 
/*     */     
/* 196 */     if (this._linksTop + 1 >= this._links.length) {
/*     */       
/* 198 */       int[] newlinks = new int[this._links.length + 2048];
/*     */       
/* 200 */       System.arraycopy(this._links, 0, newlinks, 0, this._links.length);
/*     */       
/* 202 */       this._links = newlinks;
/*     */     } 
/*     */     
/* 205 */     this._links[this._linksTop++] = this._currentFrameBottom;
/*     */     
/* 207 */     return this._currentFrameBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unlink() {
/* 216 */     this._frameTop = this._links[--this._linksTop];
/* 217 */     this._currentFrameBottom = this._links[this._linksTop - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unlink(int currentFrame) {
/* 228 */     this._frameTop = this._links[--this._linksTop];
/* 229 */     this._currentFrameBottom = currentFrame;
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
/*     */   public void setLocalVariable(int index, XObject val) {
/* 243 */     this._stackFrames[index + this._currentFrameBottom] = val;
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
/*     */   public void setLocalVariable(int index, XObject val, int stackFrame) {
/* 258 */     this._stackFrames[index + stackFrame] = val;
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
/*     */   public XObject getLocalVariable(XPathContext xctxt, int index) throws TransformerException {
/* 279 */     index += this._currentFrameBottom;
/*     */     
/* 281 */     XObject val = this._stackFrames[index];
/*     */     
/* 283 */     if (null == val) {
/* 284 */       throw new TransformerException(XSLMessages.createXPATHMessage("ER_VARIABLE_ACCESSED_BEFORE_BIND", null), xctxt
/* 285 */           .getSAXLocator());
/*     */     }
/*     */ 
/*     */     
/* 289 */     if (val.getType() == 600) {
/* 290 */       this._stackFrames[index] = val.execute(xctxt); return val.execute(xctxt);
/*     */     } 
/* 292 */     return val;
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
/*     */   public XObject getLocalVariable(int index, int frame) throws TransformerException {
/* 311 */     index += frame;
/*     */     
/* 313 */     XObject val = this._stackFrames[index];
/*     */     
/* 315 */     return val;
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
/*     */   public XObject getLocalVariable(XPathContext xctxt, int index, boolean destructiveOK) throws TransformerException {
/* 336 */     index += this._currentFrameBottom;
/*     */     
/* 338 */     XObject val = this._stackFrames[index];
/*     */     
/* 340 */     if (null == val) {
/* 341 */       throw new TransformerException(XSLMessages.createXPATHMessage("ER_VARIABLE_ACCESSED_BEFORE_BIND", null), xctxt
/* 342 */           .getSAXLocator());
/*     */     }
/*     */ 
/*     */     
/* 346 */     if (val.getType() == 600) {
/* 347 */       this._stackFrames[index] = val.execute(xctxt); return val.execute(xctxt);
/*     */     } 
/* 349 */     return destructiveOK ? val : val.getFresh();
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
/*     */   public boolean isLocalSet(int index) throws TransformerException {
/* 364 */     return (this._stackFrames[index + this._currentFrameBottom] != null);
/*     */   }
/*     */ 
/*     */   
/* 368 */   private static XObject[] m_nulls = new XObject[1024];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearLocalSlots(int start, int len) {
/* 382 */     start += this._currentFrameBottom;
/*     */     
/* 384 */     System.arraycopy(m_nulls, 0, this._stackFrames, start, len);
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
/*     */   public void setGlobalVariable(int index, XObject val) {
/* 398 */     this._stackFrames[index] = val;
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
/*     */   public XObject getGlobalVariable(XPathContext xctxt, int index) throws TransformerException {
/* 419 */     XObject val = this._stackFrames[index];
/*     */ 
/*     */     
/* 422 */     if (val.getType() == 600) {
/* 423 */       this._stackFrames[index] = val.execute(xctxt); return val.execute(xctxt);
/*     */     } 
/* 425 */     return val;
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
/*     */   public XObject getGlobalVariable(XPathContext xctxt, int index, boolean destructiveOK) throws TransformerException {
/* 446 */     XObject val = this._stackFrames[index];
/*     */ 
/*     */     
/* 449 */     if (val.getType() == 600) {
/* 450 */       this._stackFrames[index] = val.execute(xctxt); return val.execute(xctxt);
/*     */     } 
/* 452 */     return destructiveOK ? val : val.getFresh();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XObject getVariableOrParam(XPathContext xctxt, QName qname) throws TransformerException {
/* 518 */     throw new TransformerException(XSLMessages.createXPATHMessage("ER_VAR_NOT_RESOLVABLE", new Object[] { qname.toString() }));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/VariableStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */