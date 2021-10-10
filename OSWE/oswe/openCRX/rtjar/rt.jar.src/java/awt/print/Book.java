/*     */ package java.awt.print;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Book
/*     */   implements Pageable
/*     */ {
/*  58 */   private Vector mPages = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfPages() {
/*  66 */     return this.mPages.size();
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
/*     */   public PageFormat getPageFormat(int paramInt) throws IndexOutOfBoundsException {
/*  82 */     return getPage(paramInt).getPageFormat();
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
/*     */   public Printable getPrintable(int paramInt) throws IndexOutOfBoundsException {
/*  97 */     return getPage(paramInt).getPrintable();
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
/*     */   public void setPage(int paramInt, Printable paramPrintable, PageFormat paramPageFormat) throws IndexOutOfBoundsException {
/* 116 */     if (paramPrintable == null) {
/* 117 */       throw new NullPointerException("painter is null");
/*     */     }
/*     */     
/* 120 */     if (paramPageFormat == null) {
/* 121 */       throw new NullPointerException("page is null");
/*     */     }
/*     */     
/* 124 */     this.mPages.setElementAt(new BookPage(paramPrintable, paramPageFormat), paramInt);
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
/*     */   public void append(Printable paramPrintable, PageFormat paramPageFormat) {
/* 137 */     this.mPages.addElement(new BookPage(paramPrintable, paramPageFormat));
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
/*     */   public void append(Printable paramPrintable, PageFormat paramPageFormat, int paramInt) {
/* 154 */     BookPage bookPage = new BookPage(paramPrintable, paramPageFormat);
/* 155 */     int i = this.mPages.size();
/* 156 */     int j = i + paramInt;
/*     */     
/* 158 */     this.mPages.setSize(j);
/* 159 */     for (int k = i; k < j; k++) {
/* 160 */       this.mPages.setElementAt(bookPage, k);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BookPage getPage(int paramInt) throws ArrayIndexOutOfBoundsException {
/* 170 */     return this.mPages.elementAt(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class BookPage
/*     */   {
/*     */     private PageFormat mFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Printable mPainter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     BookPage(Printable param1Printable, PageFormat param1PageFormat) {
/* 198 */       if (param1Printable == null || param1PageFormat == null) {
/* 199 */         throw new NullPointerException();
/*     */       }
/*     */       
/* 202 */       this.mFormat = param1PageFormat;
/* 203 */       this.mPainter = param1Printable;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Printable getPrintable() {
/* 211 */       return this.mPainter;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PageFormat getPageFormat() {
/* 218 */       return this.mFormat;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/print/Book.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */