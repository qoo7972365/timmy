/*     */ package sun.swing.text;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.CellRendererPane;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JTextPane;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.EditorKit;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.html.HTML;
/*     */ import javax.swing.text.html.HTMLDocument;
/*     */ import sun.font.FontDesignMetrics;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextComponentPrintable
/*     */   implements CountingPrintable
/*     */ {
/*     */   private static final int LIST_SIZE = 1000;
/*     */   private boolean isLayouted = false;
/*     */   private final JTextComponent textComponentToPrint;
/* 105 */   private final AtomicReference<FontRenderContext> frc = new AtomicReference<>(null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JTextComponent printShell;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MessageFormat headerFormat;
/*     */ 
/*     */ 
/*     */   
/*     */   private final MessageFormat footerFormat;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float HEADER_FONT_SIZE = 18.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float FOOTER_FONT_SIZE = 12.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Font headerFont;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Font footerFont;
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<IntegerSegment> rowsMetrics;
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<IntegerSegment> pagesMetrics;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean needReadLock;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Printable getPrintable(JTextComponent paramJTextComponent, MessageFormat paramMessageFormat1, MessageFormat paramMessageFormat2) {
/* 153 */     if (paramJTextComponent instanceof JEditorPane && 
/* 154 */       isFrameSetDocument(paramJTextComponent.getDocument())) {
/*     */ 
/*     */       
/* 157 */       List<JEditorPane> list = getFrames((JEditorPane)paramJTextComponent);
/* 158 */       ArrayList<CountingPrintable> arrayList = new ArrayList();
/*     */       
/* 160 */       for (JEditorPane jEditorPane : list) {
/* 161 */         arrayList.add(
/* 162 */             (CountingPrintable)getPrintable(jEditorPane, paramMessageFormat1, paramMessageFormat2));
/*     */       }
/* 164 */       return new CompoundPrintable(arrayList);
/*     */     } 
/* 166 */     return new TextComponentPrintable(paramJTextComponent, paramMessageFormat1, paramMessageFormat2);
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
/*     */   private static boolean isFrameSetDocument(Document paramDocument) {
/* 179 */     boolean bool = false;
/* 180 */     if (paramDocument instanceof HTMLDocument) {
/* 181 */       HTMLDocument hTMLDocument = (HTMLDocument)paramDocument;
/* 182 */       if (hTMLDocument.getIterator(HTML.Tag.FRAME).isValid()) {
/* 183 */         bool = true;
/*     */       }
/*     */     } 
/* 186 */     return bool;
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
/*     */   private static List<JEditorPane> getFrames(JEditorPane paramJEditorPane) {
/* 198 */     ArrayList<JEditorPane> arrayList = new ArrayList();
/* 199 */     getFrames(paramJEditorPane, arrayList);
/* 200 */     if (arrayList.size() == 0) {
/*     */ 
/*     */       
/* 203 */       createFrames(paramJEditorPane);
/* 204 */       getFrames(paramJEditorPane, arrayList);
/*     */     } 
/* 206 */     return arrayList;
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
/*     */   private static void getFrames(Container paramContainer, List<JEditorPane> paramList) {
/* 219 */     for (Component component : paramContainer.getComponents()) {
/* 220 */       if (component instanceof sun.swing.text.html.FrameEditorPaneTag && component instanceof JEditorPane) {
/*     */         
/* 222 */         paramList.add((JEditorPane)component);
/*     */       }
/* 224 */       else if (component instanceof Container) {
/* 225 */         getFrames((Container)component, paramList);
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
/*     */   private static void createFrames(final JEditorPane editor) {
/* 237 */     Runnable runnable = new Runnable()
/*     */       {
/*     */         
/*     */         public void run()
/*     */         {
/* 242 */           CellRendererPane cellRendererPane = new CellRendererPane();
/* 243 */           cellRendererPane.add(editor);
/*     */ 
/*     */           
/* 246 */           cellRendererPane.setSize(500, 500);
/*     */         }
/*     */       };
/* 249 */     if (SwingUtilities.isEventDispatchThread()) {
/* 250 */       runnable.run();
/*     */     } else {
/*     */       try {
/* 253 */         SwingUtilities.invokeAndWait(runnable);
/* 254 */       } catch (Exception exception) {
/* 255 */         if (exception instanceof RuntimeException) {
/* 256 */           throw (RuntimeException)exception;
/*     */         }
/* 258 */         throw new RuntimeException(exception);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTextComponent createPrintShell(final JTextComponent textComponent) {
/* 300 */     if (SwingUtilities.isEventDispatchThread()) {
/* 301 */       return createPrintShellOnEDT(textComponent);
/*     */     }
/* 303 */     FutureTask<JTextComponent> futureTask = new FutureTask(new Callable<JTextComponent>()
/*     */         {
/*     */           public JTextComponent call() throws Exception
/*     */           {
/* 307 */             return TextComponentPrintable.this.createPrintShellOnEDT(textComponent);
/*     */           }
/*     */         });
/* 310 */     SwingUtilities.invokeLater(futureTask);
/*     */     try {
/* 312 */       return futureTask.get();
/* 313 */     } catch (InterruptedException interruptedException) {
/* 314 */       throw new RuntimeException(interruptedException);
/* 315 */     } catch (ExecutionException executionException) {
/* 316 */       Throwable throwable = executionException.getCause();
/* 317 */       if (throwable instanceof Error) {
/* 318 */         throw (Error)throwable;
/*     */       }
/* 320 */       if (throwable instanceof RuntimeException) {
/* 321 */         throw (RuntimeException)throwable;
/*     */       }
/* 323 */       throw new AssertionError(throwable);
/*     */     } 
/*     */   }
/*     */   private JTextComponent createPrintShellOnEDT(final JTextComponent textComponent) {
/*     */     JEditorPane jEditorPane;
/* 328 */     assert SwingUtilities.isEventDispatchThread();
/*     */     
/* 330 */     JPasswordField jPasswordField = null;
/* 331 */     if (textComponent instanceof JPasswordField) {
/* 332 */       jPasswordField = new JPasswordField()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public FontMetrics getFontMetrics(Font param1Font)
/*     */           {
/* 341 */             return (TextComponentPrintable.this.frc.get() == null) ? super
/* 342 */               .getFontMetrics(param1Font) : 
/* 343 */               FontDesignMetrics.getMetrics(param1Font, TextComponentPrintable.this.frc.get());
/*     */           }
/*     */         };
/* 346 */     } else if (textComponent instanceof JTextField) {
/* 347 */       JTextField jTextField = new JTextField()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public FontMetrics getFontMetrics(Font param1Font)
/*     */           {
/* 355 */             return (TextComponentPrintable.this.frc.get() == null) ? super
/* 356 */               .getFontMetrics(param1Font) : 
/* 357 */               FontDesignMetrics.getMetrics(param1Font, TextComponentPrintable.this.frc.get());
/*     */           }
/*     */         };
/* 360 */     } else if (textComponent instanceof JTextArea) {
/* 361 */       JTextArea jTextArea = new JTextArea()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public FontMetrics getFontMetrics(Font param1Font)
/*     */           {
/* 371 */             return (TextComponentPrintable.this.frc.get() == null) ? super
/* 372 */               .getFontMetrics(param1Font) : 
/* 373 */               FontDesignMetrics.getMetrics(param1Font, TextComponentPrintable.this.frc.get());
/*     */           }
/*     */         };
/* 376 */     } else if (textComponent instanceof JTextPane) {
/* 377 */       jEditorPane = new JTextPane()
/*     */         {
/*     */           public FontMetrics getFontMetrics(Font param1Font)
/*     */           {
/* 381 */             return (TextComponentPrintable.this.frc.get() == null) ? super
/* 382 */               .getFontMetrics(param1Font) : 
/* 383 */               FontDesignMetrics.getMetrics(param1Font, TextComponentPrintable.this.frc.get());
/*     */           }
/*     */           
/*     */           public EditorKit getEditorKit() {
/* 387 */             if (getDocument() == textComponent.getDocument()) {
/* 388 */               return ((JTextPane)textComponent).getEditorKit();
/*     */             }
/* 390 */             return super.getEditorKit();
/*     */           }
/*     */         };
/*     */     }
/* 394 */     else if (textComponent instanceof JEditorPane) {
/* 395 */       jEditorPane = new JEditorPane()
/*     */         {
/*     */           public FontMetrics getFontMetrics(Font param1Font)
/*     */           {
/* 399 */             return (TextComponentPrintable.this.frc.get() == null) ? super
/* 400 */               .getFontMetrics(param1Font) : 
/* 401 */               FontDesignMetrics.getMetrics(param1Font, TextComponentPrintable.this.frc.get());
/*     */           }
/*     */           
/*     */           public EditorKit getEditorKit() {
/* 405 */             if (getDocument() == textComponent.getDocument()) {
/* 406 */               return ((JEditorPane)textComponent).getEditorKit();
/*     */             }
/* 408 */             return super.getEditorKit();
/*     */           }
/*     */         };
/*     */     } 
/*     */ 
/*     */     
/* 414 */     jEditorPane.setBorder((Border)null);
/*     */ 
/*     */     
/* 417 */     jEditorPane.setOpaque(textComponent.isOpaque());
/* 418 */     jEditorPane.setEditable(textComponent.isEditable());
/* 419 */     jEditorPane.setEnabled(textComponent.isEnabled());
/* 420 */     jEditorPane.setFont(textComponent.getFont());
/* 421 */     jEditorPane.setBackground(textComponent.getBackground());
/* 422 */     jEditorPane.setForeground(textComponent.getForeground());
/* 423 */     jEditorPane.setComponentOrientation(textComponent
/* 424 */         .getComponentOrientation());
/*     */     
/* 426 */     if (jEditorPane instanceof JEditorPane) {
/* 427 */       jEditorPane.putClientProperty("JEditorPane.honorDisplayProperties", textComponent
/* 428 */           .getClientProperty("JEditorPane.honorDisplayProperties"));
/*     */       
/* 430 */       jEditorPane.putClientProperty("JEditorPane.w3cLengthUnits", textComponent
/* 431 */           .getClientProperty("JEditorPane.w3cLengthUnits"));
/* 432 */       jEditorPane.putClientProperty("charset", textComponent
/* 433 */           .getClientProperty("charset"));
/*     */     } 
/* 435 */     jEditorPane.setDocument(textComponent.getDocument());
/* 436 */     return jEditorPane;
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
/*     */   public int getNumberOfPages() {
/* 450 */     return this.pagesMetrics.size();
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
/*     */   public int print(final Graphics graphics, final PageFormat pf, final int pageIndex) throws PrinterException {
/*     */     int i;
/* 465 */     if (!this.isLayouted) {
/* 466 */       if (graphics instanceof Graphics2D) {
/* 467 */         this.frc.set(((Graphics2D)graphics).getFontRenderContext());
/*     */       }
/* 469 */       layout((int)Math.floor(pf.getImageableWidth()));
/* 470 */       calculateRowsMetrics();
/*     */     } 
/*     */     
/* 473 */     if (!SwingUtilities.isEventDispatchThread()) {
/* 474 */       Callable<Integer> callable = new Callable<Integer>() {
/*     */           public Integer call() throws Exception {
/* 476 */             return Integer.valueOf(TextComponentPrintable.this.printOnEDT(graphics, pf, pageIndex));
/*     */           }
/*     */         };
/* 479 */       FutureTask<Integer> futureTask = new FutureTask<>(callable);
/*     */       
/* 481 */       SwingUtilities.invokeLater(futureTask);
/*     */       try {
/* 483 */         i = ((Integer)futureTask.get()).intValue();
/* 484 */       } catch (InterruptedException interruptedException) {
/* 485 */         throw new RuntimeException(interruptedException);
/* 486 */       } catch (ExecutionException executionException) {
/* 487 */         Throwable throwable = executionException.getCause();
/* 488 */         if (throwable instanceof PrinterException)
/* 489 */           throw (PrinterException)throwable; 
/* 490 */         if (throwable instanceof RuntimeException)
/* 491 */           throw (RuntimeException)throwable; 
/* 492 */         if (throwable instanceof Error) {
/* 493 */           throw (Error)throwable;
/*     */         }
/* 495 */         throw new RuntimeException(throwable);
/*     */       } 
/*     */     } else {
/*     */       
/* 499 */       i = printOnEDT(graphics, pf, pageIndex);
/*     */     } 
/* 501 */     return i;
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
/*     */   private int printOnEDT(Graphics paramGraphics, PageFormat paramPageFormat, int paramInt) throws PrinterException {
/* 514 */     assert SwingUtilities.isEventDispatchThread();
/* 515 */     Border border = BorderFactory.createEmptyBorder();
/*     */     
/* 517 */     if (this.headerFormat != null || this.footerFormat != null) {
/*     */       
/* 519 */       Object[] arrayOfObject = { Integer.valueOf(paramInt + 1) };
/* 520 */       if (this.headerFormat != null)
/*     */       {
/*     */ 
/*     */         
/* 524 */         border = new TitledBorder(border, this.headerFormat.format(arrayOfObject), 2, 1, this.headerFont, this.printShell.getForeground());
/*     */       }
/* 526 */       if (this.footerFormat != null)
/*     */       {
/*     */ 
/*     */         
/* 530 */         border = new TitledBorder(border, this.footerFormat.format(arrayOfObject), 2, 6, this.footerFont, this.printShell.getForeground());
/*     */       }
/*     */     } 
/* 533 */     Insets insets = border.getBorderInsets(this.printShell);
/* 534 */     updatePagesMetrics(paramInt, 
/* 535 */         (int)Math.floor(paramPageFormat.getImageableHeight()) - insets.top - insets.bottom);
/*     */ 
/*     */     
/* 538 */     if (this.pagesMetrics.size() <= paramInt) {
/* 539 */       return 1;
/*     */     }
/*     */     
/* 542 */     Graphics2D graphics2D = (Graphics2D)paramGraphics.create();
/*     */     
/* 544 */     graphics2D.translate(paramPageFormat.getImageableX(), paramPageFormat.getImageableY());
/* 545 */     border.paintBorder(this.printShell, graphics2D, 0, 0, 
/* 546 */         (int)Math.floor(paramPageFormat.getImageableWidth()), 
/* 547 */         (int)Math.floor(paramPageFormat.getImageableHeight()));
/*     */     
/* 549 */     graphics2D.translate(0, insets.top);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 554 */     Rectangle rectangle = new Rectangle(0, 0, (int)paramPageFormat.getWidth(), ((IntegerSegment)this.pagesMetrics.get(paramInt)).end - ((IntegerSegment)this.pagesMetrics.get(paramInt)).start + 1);
/*     */     
/* 556 */     graphics2D.clip(rectangle);
/* 557 */     int i = 0;
/* 558 */     if (ComponentOrientation.RIGHT_TO_LEFT == this.printShell
/* 559 */       .getComponentOrientation()) {
/* 560 */       i = (int)paramPageFormat.getImageableWidth() - this.printShell.getWidth();
/*     */     }
/* 562 */     graphics2D.translate(i, -((IntegerSegment)this.pagesMetrics.get(paramInt)).start);
/* 563 */     this.printShell.print(graphics2D);
/*     */     
/* 565 */     graphics2D.dispose();
/*     */     
/* 567 */     return 0;
/*     */   }
/*     */   
/*     */   private TextComponentPrintable(JTextComponent paramJTextComponent, MessageFormat paramMessageFormat1, MessageFormat paramMessageFormat2) {
/* 571 */     this.needReadLock = false;
/*     */     this.textComponentToPrint = paramJTextComponent;
/*     */     this.headerFormat = paramMessageFormat1;
/*     */     this.footerFormat = paramMessageFormat2;
/*     */     this.headerFont = paramJTextComponent.getFont().deriveFont(1, 18.0F);
/*     */     this.footerFont = paramJTextComponent.getFont().deriveFont(0, 12.0F);
/*     */     this.pagesMetrics = Collections.synchronizedList(new ArrayList<>());
/*     */     this.rowsMetrics = new ArrayList<>(1000);
/* 579 */     this.printShell = createPrintShell(paramJTextComponent); } private void releaseReadLock() { assert !SwingUtilities.isEventDispatchThread();
/* 580 */     Document document = this.textComponentToPrint.getDocument();
/* 581 */     if (document instanceof AbstractDocument) {
/*     */       try {
/* 583 */         ((AbstractDocument)document).readUnlock();
/* 584 */         this.needReadLock = true;
/* 585 */       } catch (Error error) {}
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void acquireReadLock() {
/* 599 */     assert !SwingUtilities.isEventDispatchThread();
/* 600 */     if (this.needReadLock) {
/*     */ 
/*     */ 
/*     */       
/*     */       try { 
/*     */ 
/*     */         
/* 607 */         SwingUtilities.invokeAndWait(new Runnable()
/*     */             {
/*     */               
/*     */               public void run() {}
/*     */             }); }
/* 612 */       catch (InterruptedException interruptedException) {  }
/* 613 */       catch (InvocationTargetException invocationTargetException) {}
/*     */       
/* 615 */       Document document = this.textComponentToPrint.getDocument();
/* 616 */       ((AbstractDocument)document).readLock();
/* 617 */       this.needReadLock = false;
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
/*     */   private void layout(final int width) {
/* 638 */     if (!SwingUtilities.isEventDispatchThread()) {
/* 639 */       Callable<Object> callable = new Callable() {
/*     */           public Object call() throws Exception {
/* 641 */             TextComponentPrintable.this.layoutOnEDT(width);
/* 642 */             return null;
/*     */           }
/*     */         };
/* 645 */       FutureTask futureTask = new FutureTask(callable);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 652 */       releaseReadLock();
/* 653 */       SwingUtilities.invokeLater(futureTask);
/*     */       try {
/* 655 */         futureTask.get();
/* 656 */       } catch (InterruptedException interruptedException) {
/* 657 */         throw new RuntimeException(interruptedException);
/* 658 */       } catch (ExecutionException executionException) {
/* 659 */         Throwable throwable = executionException.getCause();
/* 660 */         if (throwable instanceof RuntimeException)
/* 661 */           throw (RuntimeException)throwable; 
/* 662 */         if (throwable instanceof Error) {
/* 663 */           throw (Error)throwable;
/*     */         }
/* 665 */         throw new RuntimeException(throwable);
/*     */       } finally {
/*     */         
/* 668 */         acquireReadLock();
/*     */       } 
/*     */     } else {
/* 671 */       layoutOnEDT(width);
/*     */     } 
/*     */     
/* 674 */     this.isLayouted = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void layoutOnEDT(int paramInt) {
/* 683 */     assert SwingUtilities.isEventDispatchThread();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 688 */     CellRendererPane cellRendererPane = new CellRendererPane();
/*     */ 
/*     */ 
/*     */     
/* 692 */     JViewport jViewport = new JViewport();
/* 693 */     jViewport.setBorder((Border)null);
/* 694 */     Dimension dimension = new Dimension(paramInt, 2147482647);
/*     */ 
/*     */ 
/*     */     
/* 698 */     if (this.printShell instanceof JTextField)
/*     */     {
/* 700 */       dimension = new Dimension(dimension.width, (this.printShell.getPreferredSize()).height);
/*     */     }
/* 702 */     this.printShell.setSize(dimension);
/* 703 */     jViewport.setComponentOrientation(this.printShell.getComponentOrientation());
/* 704 */     jViewport.setSize(dimension);
/* 705 */     jViewport.add(this.printShell);
/* 706 */     cellRendererPane.add(jViewport);
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
/*     */   private void updatePagesMetrics(int paramInt1, int paramInt2) {
/* 718 */     while (paramInt1 >= this.pagesMetrics.size() && !this.rowsMetrics.isEmpty()) {
/*     */       
/* 720 */       int i = this.pagesMetrics.size() - 1;
/*     */       
/* 722 */       byte b1 = (i >= 0) ? (((IntegerSegment)this.pagesMetrics.get(i)).end + 1) : 0;
/*     */ 
/*     */       
/* 725 */       byte b2 = 0;
/*     */       
/* 727 */       while (b2 < this.rowsMetrics.size() && ((IntegerSegment)this.rowsMetrics.get(b2)).end - b1 + 1 <= paramInt2)
/*     */       {
/* 729 */         b2++;
/*     */       }
/* 731 */       if (b2 == 0) {
/*     */ 
/*     */         
/* 734 */         this.pagesMetrics.add(new IntegerSegment(b1, b1 + paramInt2 - 1));
/*     */         continue;
/*     */       } 
/* 737 */       b2--;
/* 738 */       this.pagesMetrics.add(new IntegerSegment(b1, ((IntegerSegment)this.rowsMetrics
/* 739 */             .get(b2)).end));
/* 740 */       for (byte b3 = 0; b3 <= b2; b3++) {
/* 741 */         this.rowsMetrics.remove(0);
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
/*     */ 
/*     */   
/*     */   private void calculateRowsMetrics() {
/* 756 */     int i = this.printShell.getDocument().getLength();
/* 757 */     ArrayList<IntegerSegment> arrayList = new ArrayList(1000);
/*     */     int j, k, m;
/* 759 */     for (j = 0, k = -1, m = -1; j < i; 
/* 760 */       j++) {
/*     */       try {
/* 762 */         Rectangle rectangle = this.printShell.modelToView(j);
/* 763 */         if (rectangle != null) {
/* 764 */           int n = (int)rectangle.getY();
/* 765 */           int i1 = (int)rectangle.getHeight();
/* 766 */           if (i1 != 0 && (n != k || i1 != m)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 773 */             k = n;
/* 774 */             m = i1;
/* 775 */             arrayList.add(new IntegerSegment(n, n + i1 - 1));
/*     */           } 
/*     */         } 
/* 778 */       } catch (BadLocationException badLocationException) {
/*     */         assert false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 785 */     Collections.sort(arrayList);
/* 786 */     j = Integer.MIN_VALUE;
/* 787 */     k = Integer.MIN_VALUE;
/* 788 */     for (IntegerSegment integerSegment : arrayList) {
/* 789 */       if (k < integerSegment.start) {
/* 790 */         if (k != Integer.MIN_VALUE) {
/* 791 */           this.rowsMetrics.add(new IntegerSegment(j, k));
/*     */         }
/* 793 */         j = integerSegment.start;
/* 794 */         k = integerSegment.end; continue;
/*     */       } 
/* 796 */       k = integerSegment.end;
/*     */     } 
/*     */     
/* 799 */     if (k != Integer.MIN_VALUE) {
/* 800 */       this.rowsMetrics.add(new IntegerSegment(j, k));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class IntegerSegment
/*     */     implements Comparable<IntegerSegment>
/*     */   {
/*     */     final int start;
/*     */     
/*     */     final int end;
/*     */ 
/*     */     
/*     */     IntegerSegment(int param1Int1, int param1Int2) {
/* 814 */       this.start = param1Int1;
/* 815 */       this.end = param1Int2;
/*     */     }
/*     */     
/*     */     public int compareTo(IntegerSegment param1IntegerSegment) {
/* 819 */       int i = this.start - param1IntegerSegment.start;
/* 820 */       return (i != 0) ? i : (this.end - param1IntegerSegment.end);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 825 */       if (param1Object instanceof IntegerSegment) {
/* 826 */         return (compareTo((IntegerSegment)param1Object) == 0);
/*     */       }
/* 828 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 835 */       int i = 17;
/* 836 */       i = 37 * i + this.start;
/* 837 */       i = 37 * i + this.end;
/* 838 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 843 */       return "IntegerSegment [" + this.start + ", " + this.end + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/text/TextComponentPrintable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */