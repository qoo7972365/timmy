/*     */ package javax.swing.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentFilter
/*     */ {
/*     */   public void remove(FilterBypass paramFilterBypass, int paramInt1, int paramInt2) throws BadLocationException {
/*  79 */     paramFilterBypass.remove(paramInt1, paramInt2);
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
/*     */   public void insertString(FilterBypass paramFilterBypass, int paramInt, String paramString, AttributeSet paramAttributeSet) throws BadLocationException {
/* 100 */     paramFilterBypass.insertString(paramInt, paramString, paramAttributeSet);
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
/*     */   public void replace(FilterBypass paramFilterBypass, int paramInt1, int paramInt2, String paramString, AttributeSet paramAttributeSet) throws BadLocationException {
/* 120 */     paramFilterBypass.replace(paramInt1, paramInt2, paramString, paramAttributeSet);
/*     */   }
/*     */   
/*     */   public static abstract class FilterBypass {
/*     */     public abstract Document getDocument();
/*     */     
/*     */     public abstract void remove(int param1Int1, int param1Int2) throws BadLocationException;
/*     */     
/*     */     public abstract void insertString(int param1Int, String param1String, AttributeSet param1AttributeSet) throws BadLocationException;
/*     */     
/*     */     public abstract void replace(int param1Int1, int param1Int2, String param1String, AttributeSet param1AttributeSet) throws BadLocationException;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/DocumentFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */