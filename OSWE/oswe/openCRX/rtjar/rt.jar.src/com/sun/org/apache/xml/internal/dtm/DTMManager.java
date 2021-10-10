/*     */ package com.sun.org.apache.xml.internal.dtm;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault;
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DTMManager
/*     */ {
/*  52 */   protected XMLStringFactory m_xsf = null;
/*     */   
/*     */   private boolean _overrideDefaultParser;
/*     */   
/*     */   public boolean m_incremental;
/*     */   
/*     */   public boolean m_source_location;
/*     */   
/*     */   public static final int IDENT_DTM_NODE_BITS = 16;
/*     */   
/*     */   public static final int IDENT_NODE_DEFAULT = 65535;
/*     */   
/*     */   public static final int IDENT_DTM_DEFAULT = -65536;
/*     */   public static final int IDENT_MAX_DTMS = 65536;
/*     */   
/*     */   public XMLStringFactory getXMLStringFactory() {
/*  68 */     return this.m_xsf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStringFactory(XMLStringFactory xsf) {
/*  79 */     this.m_xsf = xsf;
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
/*     */   public static DTMManager newInstance(XMLStringFactory xsf) throws DTMException {
/* 102 */     DTMManager factoryImpl = new DTMManagerDefault();
/* 103 */     factoryImpl.setXMLStringFactory(xsf);
/*     */     
/* 105 */     return factoryImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DTMManager() {
/* 238 */     this.m_incremental = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     this.m_source_location = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIncremental() {
/* 255 */     return this.m_incremental;
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
/*     */   public void setIncremental(boolean incremental) {
/* 268 */     this.m_incremental = incremental;
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
/*     */   public boolean getSource_location() {
/* 280 */     return this.m_source_location;
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
/*     */   public void setSource_location(boolean sourceLocation) {
/* 293 */     this.m_source_location = sourceLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overrideDefaultParser() {
/* 300 */     return this._overrideDefaultParser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOverrideDefaultParser(boolean flag) {
/* 307 */     this._overrideDefaultParser = flag;
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
/*     */   public int getDTMIdentityMask() {
/* 365 */     return -65536;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNodeIdentityMask() {
/* 375 */     return 65535;
/*     */   }
/*     */   
/*     */   public abstract DTM getDTM(Source paramSource, boolean paramBoolean1, DTMWSFilter paramDTMWSFilter, boolean paramBoolean2, boolean paramBoolean3);
/*     */   
/*     */   public abstract DTM getDTM(int paramInt);
/*     */   
/*     */   public abstract int getDTMHandleFromNode(Node paramNode);
/*     */   
/*     */   public abstract DTM createDocumentFragment();
/*     */   
/*     */   public abstract boolean release(DTM paramDTM, boolean paramBoolean);
/*     */   
/*     */   public abstract DTMIterator createDTMIterator(Object paramObject, int paramInt);
/*     */   
/*     */   public abstract DTMIterator createDTMIterator(String paramString, PrefixResolver paramPrefixResolver);
/*     */   
/*     */   public abstract DTMIterator createDTMIterator(int paramInt, DTMFilter paramDTMFilter, boolean paramBoolean);
/*     */   
/*     */   public abstract DTMIterator createDTMIterator(int paramInt);
/*     */   
/*     */   public abstract int getDTMIdentity(DTM paramDTM);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/DTMManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */