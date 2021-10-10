/*     */ package javax.swing.text.html.parser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import javax.swing.text.ChangedCharSetException;
/*     */ import javax.swing.text.SimpleAttributeSet;
/*     */ import javax.swing.text.html.HTML;
/*     */ import javax.swing.text.html.HTMLEditorKit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentParser
/*     */   extends Parser
/*     */ {
/*     */   private int inbody;
/*     */   private int intitle;
/*     */   private int inhead;
/*     */   private int instyle;
/*     */   private int inscript;
/*     */   private boolean seentitle;
/* 111 */   private HTMLEditorKit.ParserCallback callback = null;
/*     */   private boolean ignoreCharSet = false;
/*     */   private static final boolean debugFlag = false;
/*     */   
/*     */   public DocumentParser(DTD paramDTD) {
/* 116 */     super(paramDTD);
/*     */   }
/*     */   
/*     */   public void parse(Reader paramReader, HTMLEditorKit.ParserCallback paramParserCallback, boolean paramBoolean) throws IOException {
/* 120 */     this.ignoreCharSet = paramBoolean;
/* 121 */     this.callback = paramParserCallback;
/* 122 */     parse(paramReader);
/*     */     
/* 124 */     paramParserCallback.handleEndOfLineString(getEndOfLineString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleStartTag(TagElement paramTagElement) {
/* 132 */     Element element = paramTagElement.getElement();
/* 133 */     if (element == this.dtd.body) {
/* 134 */       this.inbody++;
/* 135 */     } else if (element != this.dtd.html) {
/* 136 */       if (element == this.dtd.head) {
/* 137 */         this.inhead++;
/* 138 */       } else if (element == this.dtd.title) {
/* 139 */         this.intitle++;
/* 140 */       } else if (element == this.dtd.style) {
/* 141 */         this.instyle++;
/* 142 */       } else if (element == this.dtd.script) {
/* 143 */         this.inscript++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     if (paramTagElement.fictional()) {
/* 154 */       SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 155 */       simpleAttributeSet.addAttribute(HTMLEditorKit.ParserCallback.IMPLIED, Boolean.TRUE);
/*     */       
/* 157 */       this.callback.handleStartTag(paramTagElement.getHTMLTag(), simpleAttributeSet, 
/* 158 */           getBlockStartPosition());
/*     */     } else {
/* 160 */       this.callback.handleStartTag(paramTagElement.getHTMLTag(), getAttributes(), 
/* 161 */           getBlockStartPosition());
/* 162 */       flushAttributes();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleComment(char[] paramArrayOfchar) {
/* 172 */     this.callback.handleComment(paramArrayOfchar, getBlockStartPosition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleEmptyTag(TagElement paramTagElement) throws ChangedCharSetException {
/* 180 */     Element element = paramTagElement.getElement();
/* 181 */     if (element == this.dtd.meta && !this.ignoreCharSet) {
/* 182 */       SimpleAttributeSet simpleAttributeSet = getAttributes();
/* 183 */       if (simpleAttributeSet != null) {
/* 184 */         String str = (String)simpleAttributeSet.getAttribute(HTML.Attribute.CONTENT);
/* 185 */         if (str != null) {
/* 186 */           if ("content-type".equalsIgnoreCase((String)simpleAttributeSet.getAttribute(HTML.Attribute.HTTPEQUIV))) {
/* 187 */             if (!str.equalsIgnoreCase("text/html") && 
/* 188 */               !str.equalsIgnoreCase("text/plain")) {
/* 189 */               throw new ChangedCharSetException(str, false);
/*     */             }
/* 191 */           } else if ("charset".equalsIgnoreCase((String)simpleAttributeSet.getAttribute(HTML.Attribute.HTTPEQUIV))) {
/* 192 */             throw new ChangedCharSetException(str, true);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 197 */     if (this.inbody != 0 || element == this.dtd.meta || element == this.dtd.base || element == this.dtd.isindex || element == this.dtd.style || element == this.dtd.link)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 206 */       if (paramTagElement.fictional()) {
/* 207 */         SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 208 */         simpleAttributeSet.addAttribute(HTMLEditorKit.ParserCallback.IMPLIED, Boolean.TRUE);
/*     */         
/* 210 */         this.callback.handleSimpleTag(paramTagElement.getHTMLTag(), simpleAttributeSet, 
/* 211 */             getBlockStartPosition());
/*     */       } else {
/* 213 */         this.callback.handleSimpleTag(paramTagElement.getHTMLTag(), getAttributes(), 
/* 214 */             getBlockStartPosition());
/* 215 */         flushAttributes();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleEndTag(TagElement paramTagElement) {
/* 224 */     Element element = paramTagElement.getElement();
/* 225 */     if (element == this.dtd.body) {
/* 226 */       this.inbody--;
/* 227 */     } else if (element == this.dtd.title) {
/* 228 */       this.intitle--;
/* 229 */       this.seentitle = true;
/* 230 */     } else if (element == this.dtd.head) {
/* 231 */       this.inhead--;
/* 232 */     } else if (element == this.dtd.style) {
/* 233 */       this.instyle--;
/* 234 */     } else if (element == this.dtd.script) {
/* 235 */       this.inscript--;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 240 */     this.callback.handleEndTag(paramTagElement.getHTMLTag(), getBlockStartPosition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleText(char[] paramArrayOfchar) {
/* 248 */     if (paramArrayOfchar != null) {
/* 249 */       if (this.inscript != 0) {
/* 250 */         this.callback.handleComment(paramArrayOfchar, getBlockStartPosition());
/*     */         return;
/*     */       } 
/* 253 */       if (this.inbody != 0 || this.instyle != 0 || (this.intitle != 0 && !this.seentitle))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 258 */         this.callback.handleText(paramArrayOfchar, getBlockStartPosition());
/*     */       }
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
/*     */   protected void handleError(int paramInt, String paramString) {
/* 271 */     this.callback.handleError(paramString, getCurrentPos());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void debug(String paramString) {
/* 279 */     System.out.println(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/parser/DocumentParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */