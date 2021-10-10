/*     */ package javax.swing.text.rtf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.StyledDocument;
/*     */ import javax.swing.text.StyledEditorKit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RTFEditorKit
/*     */   extends StyledEditorKit
/*     */ {
/*     */   public String getContentType() {
/*  60 */     return "text/rtf";
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
/*     */   public void read(InputStream paramInputStream, Document paramDocument, int paramInt) throws IOException, BadLocationException {
/*  78 */     if (paramDocument instanceof StyledDocument) {
/*     */ 
/*     */       
/*  81 */       RTFReader rTFReader = new RTFReader((StyledDocument)paramDocument);
/*  82 */       rTFReader.readFromStream(paramInputStream);
/*  83 */       rTFReader.close();
/*     */     } else {
/*     */       
/*  86 */       super.read(paramInputStream, paramDocument, paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(OutputStream paramOutputStream, Document paramDocument, int paramInt1, int paramInt2) throws IOException, BadLocationException {
/* 108 */     RTFGenerator.writeDocument(paramDocument, paramOutputStream);
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
/*     */   public void read(Reader paramReader, Document paramDocument, int paramInt) throws IOException, BadLocationException {
/* 126 */     if (paramDocument instanceof StyledDocument) {
/* 127 */       RTFReader rTFReader = new RTFReader((StyledDocument)paramDocument);
/* 128 */       rTFReader.readFromReader(paramReader);
/* 129 */       rTFReader.close();
/*     */     } else {
/*     */       
/* 132 */       super.read(paramReader, paramDocument, paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(Writer paramWriter, Document paramDocument, int paramInt1, int paramInt2) throws IOException, BadLocationException {
/* 152 */     throw new IOException("RTF is an 8-bit format");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/rtf/RTFEditorKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */