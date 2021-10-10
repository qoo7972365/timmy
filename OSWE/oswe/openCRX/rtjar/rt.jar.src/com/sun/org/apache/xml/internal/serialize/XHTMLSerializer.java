/*     */ package com.sun.org.apache.xml.internal.serialize;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XHTMLSerializer
/*     */   extends HTMLSerializer
/*     */ {
/*     */   public XHTMLSerializer() {
/*  53 */     super(true, new OutputFormat("xhtml", null, false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XHTMLSerializer(OutputFormat format) {
/*  64 */     super(true, (format != null) ? format : new OutputFormat("xhtml", null, false));
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
/*     */   public XHTMLSerializer(Writer writer, OutputFormat format) {
/*  78 */     super(true, (format != null) ? format : new OutputFormat("xhtml", null, false));
/*  79 */     setOutputCharStream(writer);
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
/*     */   public XHTMLSerializer(OutputStream output, OutputFormat format) {
/*  93 */     super(true, (format != null) ? format : new OutputFormat("xhtml", null, false));
/*  94 */     setOutputByteStream(output);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputFormat(OutputFormat format) {
/* 100 */     super.setOutputFormat((format != null) ? format : new OutputFormat("xhtml", null, false));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/XHTMLSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */