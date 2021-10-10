/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.UIManager;
/*      */ import sun.awt.SunToolkit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultEditorKit
/*      */   extends EditorKit
/*      */ {
/*      */   public static final String EndOfLineStringProperty = "__EndOfLine__";
/*      */   public static final String insertContentAction = "insert-content";
/*      */   public static final String insertBreakAction = "insert-break";
/*      */   public static final String insertTabAction = "insert-tab";
/*      */   public static final String deletePrevCharAction = "delete-previous";
/*      */   public static final String deleteNextCharAction = "delete-next";
/*      */   public static final String deleteNextWordAction = "delete-next-word";
/*      */   public static final String deletePrevWordAction = "delete-previous-word";
/*      */   public static final String readOnlyAction = "set-read-only";
/*      */   public static final String writableAction = "set-writable";
/*      */   public static final String cutAction = "cut-to-clipboard";
/*      */   public static final String copyAction = "copy-to-clipboard";
/*      */   public static final String pasteAction = "paste-from-clipboard";
/*      */   public static final String beepAction = "beep";
/*      */   public static final String pageUpAction = "page-up";
/*      */   public static final String pageDownAction = "page-down";
/*      */   static final String selectionPageUpAction = "selection-page-up";
/*      */   static final String selectionPageDownAction = "selection-page-down";
/*      */   static final String selectionPageLeftAction = "selection-page-left";
/*      */   static final String selectionPageRightAction = "selection-page-right";
/*      */   public static final String forwardAction = "caret-forward";
/*      */   public static final String backwardAction = "caret-backward";
/*      */   public static final String selectionForwardAction = "selection-forward";
/*      */   public static final String selectionBackwardAction = "selection-backward";
/*      */   public static final String upAction = "caret-up";
/*      */   public static final String downAction = "caret-down";
/*      */   public static final String selectionUpAction = "selection-up";
/*      */   public static final String selectionDownAction = "selection-down";
/*      */   public static final String beginWordAction = "caret-begin-word";
/*      */   public static final String endWordAction = "caret-end-word";
/*      */   public static final String selectionBeginWordAction = "selection-begin-word";
/*      */   public static final String selectionEndWordAction = "selection-end-word";
/*      */   public static final String previousWordAction = "caret-previous-word";
/*      */   public static final String nextWordAction = "caret-next-word";
/*      */   public static final String selectionPreviousWordAction = "selection-previous-word";
/*      */   public static final String selectionNextWordAction = "selection-next-word";
/*      */   public static final String beginLineAction = "caret-begin-line";
/*      */   public static final String endLineAction = "caret-end-line";
/*      */   public static final String selectionBeginLineAction = "selection-begin-line";
/*      */   public static final String selectionEndLineAction = "selection-end-line";
/*      */   public static final String beginParagraphAction = "caret-begin-paragraph";
/*      */   public static final String endParagraphAction = "caret-end-paragraph";
/*      */   public static final String selectionBeginParagraphAction = "selection-begin-paragraph";
/*      */   public static final String selectionEndParagraphAction = "selection-end-paragraph";
/*      */   public static final String beginAction = "caret-begin";
/*      */   public static final String endAction = "caret-end";
/*      */   public static final String selectionBeginAction = "selection-begin";
/*      */   public static final String selectionEndAction = "selection-end";
/*      */   public static final String selectWordAction = "select-word";
/*      */   public static final String selectLineAction = "select-line";
/*      */   public static final String selectParagraphAction = "select-paragraph";
/*      */   public static final String selectAllAction = "select-all";
/*      */   static final String unselectAction = "unselect";
/*      */   static final String toggleComponentOrientationAction = "toggle-componentOrientation";
/*      */   public static final String defaultKeyTypedAction = "default-typed";
/*      */   
/*      */   public String getContentType() {
/*   89 */     return "text/plain";
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
/*      */   public ViewFactory getViewFactory() {
/*  101 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Action[] getActions() {
/*  112 */     return (Action[])defaultActions.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Caret createCaret() {
/*  122 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document createDefaultDocument() {
/*  132 */     return new PlainDocument();
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
/*      */   public void read(InputStream paramInputStream, Document paramDocument, int paramInt) throws IOException, BadLocationException {
/*  151 */     read(new InputStreamReader(paramInputStream), paramDocument, paramInt);
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
/*      */   public void write(OutputStream paramOutputStream, Document paramDocument, int paramInt1, int paramInt2) throws IOException, BadLocationException {
/*  169 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(paramOutputStream);
/*      */     
/*  171 */     write(outputStreamWriter, paramDocument, paramInt1, paramInt2);
/*  172 */     outputStreamWriter.flush();
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
/*      */   MutableAttributeSet getInputAttributes() {
/*  184 */     return null;
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
/*      */   public void read(Reader paramReader, Document paramDocument, int paramInt) throws IOException, BadLocationException {
/*  202 */     char[] arrayOfChar = new char[4096];
/*      */     
/*  204 */     boolean bool1 = false;
/*  205 */     boolean bool2 = false;
/*  206 */     boolean bool3 = false;
/*      */     
/*  208 */     boolean bool4 = (paramDocument.getLength() == 0) ? true : false;
/*  209 */     MutableAttributeSet mutableAttributeSet = getInputAttributes();
/*      */ 
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */     
/*  216 */     while ((i = paramReader.read(arrayOfChar, 0, arrayOfChar.length)) != -1) {
/*  217 */       byte b1 = 0;
/*  218 */       for (byte b2 = 0; b2 < i; b2++) {
/*  219 */         switch (arrayOfChar[b2]) {
/*      */           case '\r':
/*  221 */             if (bool1) {
/*  222 */               bool3 = true;
/*  223 */               if (b2 == 0) {
/*  224 */                 paramDocument.insertString(paramInt, "\n", mutableAttributeSet);
/*  225 */                 paramInt++;
/*      */                 break;
/*      */               } 
/*  228 */               arrayOfChar[b2 - 1] = '\n';
/*      */               
/*      */               break;
/*      */             } 
/*  232 */             bool1 = true;
/*      */             break;
/*      */           
/*      */           case '\n':
/*  236 */             if (bool1) {
/*  237 */               if (b2 > b1 + 1) {
/*  238 */                 paramDocument.insertString(paramInt, new String(arrayOfChar, b1, b2 - b1 - 1), mutableAttributeSet);
/*      */                 
/*  240 */                 paramInt += b2 - b1 - 1;
/*      */               } 
/*      */ 
/*      */               
/*  244 */               bool1 = false;
/*  245 */               b1 = b2;
/*  246 */               bool2 = true;
/*      */             } 
/*      */             break;
/*      */           default:
/*  250 */             if (bool1) {
/*  251 */               bool3 = true;
/*  252 */               if (b2 == 0) {
/*  253 */                 paramDocument.insertString(paramInt, "\n", mutableAttributeSet);
/*  254 */                 paramInt++;
/*      */               } else {
/*      */                 
/*  257 */                 arrayOfChar[b2 - 1] = '\n';
/*      */               } 
/*  259 */               bool1 = false;
/*      */             } 
/*      */             break;
/*      */         } 
/*      */       } 
/*  264 */       if (b1 < i) {
/*  265 */         if (bool1) {
/*  266 */           if (b1 < i - 1) {
/*  267 */             paramDocument.insertString(paramInt, new String(arrayOfChar, b1, i - b1 - 1), mutableAttributeSet);
/*      */             
/*  269 */             paramInt += i - b1 - 1;
/*      */           } 
/*      */           continue;
/*      */         } 
/*  273 */         paramDocument.insertString(paramInt, new String(arrayOfChar, b1, i - b1), mutableAttributeSet);
/*      */         
/*  275 */         paramInt += i - b1;
/*      */       } 
/*      */     } 
/*      */     
/*  279 */     if (bool1) {
/*  280 */       paramDocument.insertString(paramInt, "\n", mutableAttributeSet);
/*  281 */       bool3 = true;
/*      */     } 
/*  283 */     if (bool4) {
/*  284 */       if (bool2) {
/*  285 */         paramDocument.putProperty("__EndOfLine__", "\r\n");
/*      */       }
/*  287 */       else if (bool3) {
/*  288 */         paramDocument.putProperty("__EndOfLine__", "\r");
/*      */       } else {
/*      */         
/*  291 */         paramDocument.putProperty("__EndOfLine__", "\n");
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(Writer paramWriter, Document paramDocument, int paramInt1, int paramInt2) throws IOException, BadLocationException {
/*      */     String str;
/*  312 */     if (paramInt1 < 0 || paramInt1 + paramInt2 > paramDocument.getLength()) {
/*  313 */       throw new BadLocationException("DefaultEditorKit.write", paramInt1);
/*      */     }
/*  315 */     Segment segment = new Segment();
/*  316 */     int i = paramInt2;
/*  317 */     int j = paramInt1;
/*  318 */     Object object = paramDocument.getProperty("__EndOfLine__");
/*  319 */     if (object == null) {
/*      */       try {
/*  321 */         object = System.getProperty("line.separator");
/*  322 */       } catch (SecurityException null) {}
/*      */     }
/*      */     
/*  325 */     if (object instanceof String) {
/*  326 */       str = (String)object;
/*      */     } else {
/*      */       
/*  329 */       str = null;
/*      */     } 
/*  331 */     if (object != null && !str.equals("\n")) {
/*      */ 
/*      */       
/*  334 */       while (i > 0) {
/*  335 */         int k = Math.min(i, 4096);
/*  336 */         paramDocument.getText(j, k, segment);
/*  337 */         int m = segment.offset;
/*  338 */         char[] arrayOfChar = segment.array;
/*  339 */         int n = m + segment.count;
/*  340 */         for (int i1 = m; i1 < n; i1++) {
/*  341 */           if (arrayOfChar[i1] == '\n') {
/*  342 */             if (i1 > m) {
/*  343 */               paramWriter.write(arrayOfChar, m, i1 - m);
/*      */             }
/*  345 */             paramWriter.write(str);
/*  346 */             m = i1 + 1;
/*      */           } 
/*      */         } 
/*  349 */         if (n > m) {
/*  350 */           paramWriter.write(arrayOfChar, m, n - m);
/*      */         }
/*  352 */         j += k;
/*  353 */         i -= k;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  359 */       while (i > 0) {
/*  360 */         int k = Math.min(i, 4096);
/*  361 */         paramDocument.getText(j, k, segment);
/*  362 */         paramWriter.write(segment.array, segment.offset, segment.count);
/*  363 */         j += k;
/*  364 */         i -= k;
/*      */       } 
/*      */     } 
/*  367 */     paramWriter.flush();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  763 */   private static final Action[] defaultActions = new Action[] { new InsertContentAction(), new DeletePrevCharAction(), new DeleteNextCharAction(), new ReadOnlyAction(), new DeleteWordAction("delete-previous-word"), new DeleteWordAction("delete-next-word"), new WritableAction(), new CutAction(), new CopyAction(), new PasteAction(), new VerticalPageAction("page-up", -1, false), new VerticalPageAction("page-down", 1, false), new VerticalPageAction("selection-page-up", -1, true), new VerticalPageAction("selection-page-down", 1, true), new PageAction("selection-page-left", true, true), new PageAction("selection-page-right", false, true), new InsertBreakAction(), new BeepAction(), new NextVisualPositionAction("caret-forward", false, 3), new NextVisualPositionAction("caret-backward", false, 7), new NextVisualPositionAction("selection-forward", true, 3), new NextVisualPositionAction("selection-backward", true, 7), new NextVisualPositionAction("caret-up", false, 1), new NextVisualPositionAction("caret-down", false, 5), new NextVisualPositionAction("selection-up", true, 1), new NextVisualPositionAction("selection-down", true, 5), new BeginWordAction("caret-begin-word", false), new EndWordAction("caret-end-word", false), new BeginWordAction("selection-begin-word", true), new EndWordAction("selection-end-word", true), new PreviousWordAction("caret-previous-word", false), new NextWordAction("caret-next-word", false), new PreviousWordAction("selection-previous-word", true), new NextWordAction("selection-next-word", true), new BeginLineAction("caret-begin-line", false), new EndLineAction("caret-end-line", false), new BeginLineAction("selection-begin-line", true), new EndLineAction("selection-end-line", true), new BeginParagraphAction("caret-begin-paragraph", false), new EndParagraphAction("caret-end-paragraph", false), new BeginParagraphAction("selection-begin-paragraph", true), new EndParagraphAction("selection-end-paragraph", true), new BeginAction("caret-begin", false), new EndAction("caret-end", false), new BeginAction("selection-begin", true), new EndAction("selection-end", true), new DefaultKeyTypedAction(), new InsertTabAction(), new SelectWordAction(), new SelectLineAction(), new SelectParagraphAction(), new SelectAllAction(), new UnselectAction(), new ToggleComponentOrientationAction(), new DumpModelAction() };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DefaultKeyTypedAction
/*      */     extends TextAction
/*      */   {
/*      */     public DefaultKeyTypedAction() {
/*  858 */       super("default-typed");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  867 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/*  868 */       if (jTextComponent != null && param1ActionEvent != null) {
/*  869 */         if (!jTextComponent.isEditable() || !jTextComponent.isEnabled()) {
/*      */           return;
/*      */         }
/*  872 */         String str = param1ActionEvent.getActionCommand();
/*  873 */         int i = param1ActionEvent.getModifiers();
/*  874 */         if (str != null && str.length() > 0) {
/*  875 */           boolean bool = true;
/*  876 */           Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  877 */           if (toolkit instanceof SunToolkit) {
/*  878 */             bool = ((SunToolkit)toolkit).isPrintableCharacterModifiersMask(i);
/*      */           }
/*      */           
/*  881 */           char c = str.charAt(0);
/*  882 */           if ((bool && c >= ' ' && c != '') || (!bool && c >= '‌' && c <= '‍'))
/*      */           {
/*  884 */             jTextComponent.replaceSelection(str);
/*      */           }
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class InsertContentAction
/*      */     extends TextAction
/*      */   {
/*      */     public InsertContentAction() {
/*  914 */       super("insert-content");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  923 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/*  924 */       if (jTextComponent != null && param1ActionEvent != null) {
/*  925 */         if (!jTextComponent.isEditable() || !jTextComponent.isEnabled()) {
/*  926 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */           return;
/*      */         } 
/*  929 */         String str = param1ActionEvent.getActionCommand();
/*  930 */         if (str != null) {
/*  931 */           jTextComponent.replaceSelection(str);
/*      */         } else {
/*  933 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class InsertBreakAction
/*      */     extends TextAction
/*      */   {
/*      */     public InsertBreakAction() {
/*  962 */       super("insert-break");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  971 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/*  972 */       if (jTextComponent != null) {
/*  973 */         if (!jTextComponent.isEditable() || !jTextComponent.isEnabled()) {
/*  974 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */           return;
/*      */         } 
/*  977 */         jTextComponent.replaceSelection("\n");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class InsertTabAction
/*      */     extends TextAction
/*      */   {
/*      */     public InsertTabAction() {
/* 1004 */       super("insert-tab");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1013 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1014 */       if (jTextComponent != null) {
/* 1015 */         if (!jTextComponent.isEditable() || !jTextComponent.isEnabled()) {
/* 1016 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */           return;
/*      */         } 
/* 1019 */         jTextComponent.replaceSelection("\t");
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
/*      */   static class DeletePrevCharAction
/*      */     extends TextAction
/*      */   {
/*      */     DeletePrevCharAction() {
/* 1036 */       super("delete-previous");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1045 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1046 */       boolean bool = true;
/* 1047 */       if (jTextComponent != null && jTextComponent.isEditable()) {
/*      */         try {
/* 1049 */           Document document = jTextComponent.getDocument();
/* 1050 */           Caret caret = jTextComponent.getCaret();
/* 1051 */           int i = caret.getDot();
/* 1052 */           int j = caret.getMark();
/* 1053 */           if (i != j) {
/* 1054 */             document.remove(Math.min(i, j), Math.abs(i - j));
/* 1055 */             bool = false;
/* 1056 */           } else if (i > 0) {
/* 1057 */             byte b = 1;
/*      */             
/* 1059 */             if (i > 1) {
/* 1060 */               String str = document.getText(i - 2, 2);
/* 1061 */               char c1 = str.charAt(0);
/* 1062 */               char c2 = str.charAt(1);
/*      */               
/* 1064 */               if (c1 >= '?' && c1 <= '?' && c2 >= '?' && c2 <= '?')
/*      */               {
/* 1066 */                 b = 2;
/*      */               }
/*      */             } 
/*      */             
/* 1070 */             document.remove(i - b, b);
/* 1071 */             bool = false;
/*      */           } 
/* 1073 */         } catch (BadLocationException badLocationException) {}
/*      */       }
/*      */       
/* 1076 */       if (bool) {
/* 1077 */         UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
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
/*      */   static class DeleteNextCharAction
/*      */     extends TextAction
/*      */   {
/*      */     DeleteNextCharAction() {
/* 1092 */       super("delete-next");
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1097 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1098 */       boolean bool = true;
/* 1099 */       if (jTextComponent != null && jTextComponent.isEditable()) {
/*      */         try {
/* 1101 */           Document document = jTextComponent.getDocument();
/* 1102 */           Caret caret = jTextComponent.getCaret();
/* 1103 */           int i = caret.getDot();
/* 1104 */           int j = caret.getMark();
/* 1105 */           if (i != j) {
/* 1106 */             document.remove(Math.min(i, j), Math.abs(i - j));
/* 1107 */             bool = false;
/* 1108 */           } else if (i < document.getLength()) {
/* 1109 */             byte b = 1;
/*      */             
/* 1111 */             if (i < document.getLength() - 1) {
/* 1112 */               String str = document.getText(i, 2);
/* 1113 */               char c1 = str.charAt(0);
/* 1114 */               char c2 = str.charAt(1);
/*      */               
/* 1116 */               if (c1 >= '?' && c1 <= '?' && c2 >= '?' && c2 <= '?')
/*      */               {
/* 1118 */                 b = 2;
/*      */               }
/*      */             } 
/*      */             
/* 1122 */             document.remove(i, b);
/* 1123 */             bool = false;
/*      */           } 
/* 1125 */         } catch (BadLocationException badLocationException) {}
/*      */       }
/*      */       
/* 1128 */       if (bool) {
/* 1129 */         UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class DeleteWordAction
/*      */     extends TextAction
/*      */   {
/*      */     DeleteWordAction(String param1String) {
/* 1141 */       super(param1String);
/* 1142 */       assert param1String == "delete-previous-word" || param1String == "delete-next-word";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1151 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1152 */       if (jTextComponent != null && param1ActionEvent != null) {
/* 1153 */         if (!jTextComponent.isEditable() || !jTextComponent.isEnabled()) {
/* 1154 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */           return;
/*      */         } 
/* 1157 */         boolean bool = true;
/*      */         try {
/* 1159 */           int j, i = jTextComponent.getSelectionStart();
/*      */           
/* 1161 */           Element element = Utilities.getParagraphElement(jTextComponent, i);
/*      */           
/* 1163 */           if ("delete-next-word" == getValue("Name")) {
/*      */             
/* 1165 */             j = Utilities.getNextWordInParagraph(jTextComponent, element, i, false);
/* 1166 */             if (j == -1) {
/*      */               
/* 1168 */               int n = element.getEndOffset();
/* 1169 */               if (i == n - 1) {
/*      */                 
/* 1171 */                 j = n;
/*      */               } else {
/*      */                 
/* 1174 */                 j = n - 1;
/*      */               } 
/*      */             } 
/*      */           } else {
/*      */             
/* 1179 */             j = Utilities.getPrevWordInParagraph(jTextComponent, element, i);
/* 1180 */             if (j == -1) {
/*      */               
/* 1182 */               int n = element.getStartOffset();
/* 1183 */               if (i == n) {
/*      */                 
/* 1185 */                 j = n - 1;
/*      */               } else {
/*      */                 
/* 1188 */                 j = n;
/*      */               } 
/*      */             } 
/*      */           } 
/* 1192 */           int k = Math.min(i, j);
/* 1193 */           int m = Math.abs(j - i);
/* 1194 */           if (k >= 0) {
/* 1195 */             jTextComponent.getDocument().remove(k, m);
/* 1196 */             bool = false;
/*      */           } 
/* 1198 */         } catch (BadLocationException badLocationException) {}
/*      */         
/* 1200 */         if (bool) {
/* 1201 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */         }
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
/*      */   static class ReadOnlyAction
/*      */     extends TextAction
/*      */   {
/*      */     ReadOnlyAction() {
/* 1217 */       super("set-read-only");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1226 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1227 */       if (jTextComponent != null) {
/* 1228 */         jTextComponent.setEditable(false);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class WritableAction
/*      */     extends TextAction
/*      */   {
/*      */     WritableAction() {
/* 1242 */       super("set-writable");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1251 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1252 */       if (jTextComponent != null) {
/* 1253 */         jTextComponent.setEditable(true);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CutAction
/*      */     extends TextAction
/*      */   {
/*      */     public CutAction() {
/* 1278 */       super("cut-to-clipboard");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1287 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1288 */       if (jTextComponent != null) {
/* 1289 */         jTextComponent.cut();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CopyAction
/*      */     extends TextAction
/*      */   {
/*      */     public CopyAction() {
/* 1314 */       super("copy-to-clipboard");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1323 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1324 */       if (jTextComponent != null) {
/* 1325 */         jTextComponent.copy();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PasteAction
/*      */     extends TextAction
/*      */   {
/*      */     public PasteAction() {
/* 1351 */       super("paste-from-clipboard");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1360 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1361 */       if (jTextComponent != null) {
/* 1362 */         jTextComponent.paste();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class BeepAction
/*      */     extends TextAction
/*      */   {
/*      */     public BeepAction() {
/* 1386 */       super("beep");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1395 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1396 */       UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class VerticalPageAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */     
/*      */     private int direction;
/*      */ 
/*      */     
/*      */     public VerticalPageAction(String param1String, int param1Int, boolean param1Boolean) {
/* 1412 */       super(param1String);
/* 1413 */       this.select = param1Boolean;
/* 1414 */       this.direction = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1419 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1420 */       if (jTextComponent != null) {
/* 1421 */         Rectangle rectangle1 = jTextComponent.getVisibleRect();
/* 1422 */         Rectangle rectangle2 = new Rectangle(rectangle1);
/* 1423 */         int i = jTextComponent.getCaretPosition();
/*      */         
/* 1425 */         int j = this.direction * jTextComponent.getScrollableBlockIncrement(rectangle1, 1, this.direction);
/*      */         
/* 1427 */         int k = rectangle1.y;
/* 1428 */         Caret caret = jTextComponent.getCaret();
/* 1429 */         Point point = caret.getMagicCaretPosition();
/*      */         
/* 1431 */         if (i != -1) {
/*      */           try {
/* 1433 */             Rectangle rectangle = jTextComponent.modelToView(i);
/*      */             
/* 1435 */             int m = (point != null) ? point.x : rectangle.x;
/*      */             
/* 1437 */             int n = rectangle.height;
/* 1438 */             if (n > 0)
/*      */             {
/*      */               
/* 1441 */               j = j / n * n;
/*      */             }
/* 1443 */             rectangle2.y = constrainY(jTextComponent, k + j, rectangle1.height);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1448 */             if (rectangle1.contains(rectangle.x, rectangle.y)) {
/*      */ 
/*      */               
/* 1451 */               i1 = jTextComponent.viewToModel(new Point(m, 
/* 1452 */                     constrainY(jTextComponent, rectangle.y + j, 0)));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/* 1458 */             else if (this.direction == -1) {
/* 1459 */               i1 = jTextComponent.viewToModel(new Point(m, rectangle2.y));
/*      */             }
/*      */             else {
/*      */               
/* 1463 */               i1 = jTextComponent.viewToModel(new Point(m, rectangle2.y + rectangle1.height));
/*      */             } 
/*      */ 
/*      */             
/* 1467 */             int i1 = constrainOffset(jTextComponent, i1);
/* 1468 */             if (i1 != i) {
/*      */ 
/*      */ 
/*      */               
/* 1472 */               int i2 = getAdjustedY(jTextComponent, rectangle2, i1);
/*      */               
/* 1474 */               if ((this.direction == -1 && i2 <= k) || (this.direction == 1 && i2 >= k)) {
/*      */                 
/* 1476 */                 rectangle2.y = i2;
/*      */                 
/* 1478 */                 if (this.select) {
/* 1479 */                   jTextComponent.moveCaretPosition(i1);
/*      */                 } else {
/* 1481 */                   jTextComponent.setCaretPosition(i1);
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1485 */           } catch (BadLocationException badLocationException) {}
/*      */         } else {
/* 1487 */           rectangle2.y = constrainY(jTextComponent, k + j, rectangle1.height);
/*      */         } 
/*      */         
/* 1490 */         if (point != null) {
/* 1491 */           caret.setMagicCaretPosition(point);
/*      */         }
/* 1493 */         jTextComponent.scrollRectToVisible(rectangle2);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int constrainY(JTextComponent param1JTextComponent, int param1Int1, int param1Int2) {
/* 1502 */       if (param1Int1 < 0) {
/* 1503 */         param1Int1 = 0;
/*      */       }
/* 1505 */       else if (param1Int1 + param1Int2 > param1JTextComponent.getHeight()) {
/* 1506 */         param1Int1 = Math.max(0, param1JTextComponent.getHeight() - param1Int2);
/*      */       } 
/* 1508 */       return param1Int1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int constrainOffset(JTextComponent param1JTextComponent, int param1Int) {
/* 1516 */       Document document = param1JTextComponent.getDocument();
/*      */       
/* 1518 */       if (param1Int != 0 && param1Int > document.getLength()) {
/* 1519 */         param1Int = document.getLength();
/*      */       }
/* 1521 */       if (param1Int < 0) {
/* 1522 */         param1Int = 0;
/*      */       }
/* 1524 */       return param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getAdjustedY(JTextComponent param1JTextComponent, Rectangle param1Rectangle, int param1Int) {
/* 1532 */       int i = param1Rectangle.y;
/*      */       
/*      */       try {
/* 1535 */         Rectangle rectangle = param1JTextComponent.modelToView(param1Int);
/*      */         
/* 1537 */         if (rectangle.y < param1Rectangle.y) {
/* 1538 */           i = rectangle.y;
/*      */         }
/* 1540 */         else if (rectangle.y > param1Rectangle.y + param1Rectangle.height || rectangle.y + rectangle.height > param1Rectangle.y + param1Rectangle.height) {
/*      */           
/* 1542 */           i = rectangle.y + rectangle.height - param1Rectangle.height;
/*      */         }
/*      */       
/* 1545 */       } catch (BadLocationException badLocationException) {}
/*      */ 
/*      */       
/* 1548 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class PageAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean left;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PageAction(String param1String, boolean param1Boolean1, boolean param1Boolean2) {
/* 1571 */       super(param1String);
/* 1572 */       this.select = param1Boolean2;
/* 1573 */       this.left = param1Boolean1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1578 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1579 */       if (jTextComponent != null) {
/*      */         
/* 1581 */         Rectangle rectangle = new Rectangle();
/* 1582 */         jTextComponent.computeVisibleRect(rectangle);
/* 1583 */         if (this.left) {
/* 1584 */           rectangle.x = Math.max(0, rectangle.x - rectangle.width);
/*      */         } else {
/*      */           
/* 1587 */           rectangle.x += rectangle.width;
/*      */         } 
/*      */         
/* 1590 */         int i = jTextComponent.getCaretPosition();
/* 1591 */         if (i != -1) {
/* 1592 */           if (this.left) {
/*      */             
/* 1594 */             i = jTextComponent.viewToModel(new Point(rectangle.x, rectangle.y));
/*      */           }
/*      */           else {
/*      */             
/* 1598 */             i = jTextComponent.viewToModel(new Point(rectangle.x + rectangle.width - 1, rectangle.y + rectangle.height - 1));
/*      */           } 
/*      */           
/* 1601 */           Document document = jTextComponent.getDocument();
/* 1602 */           if (i != 0 && i > document
/* 1603 */             .getLength() - 1) {
/* 1604 */             i = document.getLength() - 1;
/*      */           }
/* 1606 */           else if (i < 0) {
/* 1607 */             i = 0;
/*      */           } 
/* 1609 */           if (this.select) {
/* 1610 */             jTextComponent.moveCaretPosition(i);
/*      */           } else {
/* 1612 */             jTextComponent.setCaretPosition(i);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class DumpModelAction
/*      */     extends TextAction
/*      */   {
/*      */     DumpModelAction() {
/* 1624 */       super("dump-model");
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1628 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1629 */       if (jTextComponent != null) {
/* 1630 */         Document document = jTextComponent.getDocument();
/* 1631 */         if (document instanceof AbstractDocument) {
/* 1632 */           ((AbstractDocument)document).dump(System.err);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class NextVisualPositionAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */     
/*      */     private int direction;
/*      */ 
/*      */ 
/*      */     
/*      */     NextVisualPositionAction(String param1String, boolean param1Boolean, int param1Int) {
/* 1652 */       super(param1String);
/* 1653 */       this.select = param1Boolean;
/* 1654 */       this.direction = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1659 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1660 */       if (jTextComponent != null) {
/* 1661 */         Caret caret = jTextComponent.getCaret();
/* 1662 */         DefaultCaret defaultCaret = (caret instanceof DefaultCaret) ? (DefaultCaret)caret : null;
/*      */         
/* 1664 */         int i = caret.getDot();
/* 1665 */         Position.Bias[] arrayOfBias = new Position.Bias[1];
/* 1666 */         Point point = caret.getMagicCaretPosition();
/*      */         
/*      */         try {
/* 1669 */           if (point == null && (this.direction == 1 || this.direction == 5)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1675 */             Rectangle rectangle = (defaultCaret != null) ? jTextComponent.getUI().modelToView(jTextComponent, i, defaultCaret.getDotBias()) : jTextComponent.modelToView(i);
/* 1676 */             point = new Point(rectangle.x, rectangle.y);
/*      */           } 
/*      */           
/* 1679 */           NavigationFilter navigationFilter = jTextComponent.getNavigationFilter();
/*      */           
/* 1681 */           if (navigationFilter != null) {
/*      */             
/* 1683 */             i = navigationFilter.getNextVisualPositionFrom(jTextComponent, i, (defaultCaret != null) ? defaultCaret
/* 1684 */                 .getDotBias() : Position.Bias.Forward, this.direction, arrayOfBias);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1689 */             i = jTextComponent.getUI().getNextVisualPositionFrom(jTextComponent, i, (defaultCaret != null) ? defaultCaret
/* 1690 */                 .getDotBias() : Position.Bias.Forward, this.direction, arrayOfBias);
/*      */           } 
/*      */           
/* 1693 */           if (arrayOfBias[0] == null) {
/* 1694 */             arrayOfBias[0] = Position.Bias.Forward;
/*      */           }
/* 1696 */           if (defaultCaret != null) {
/* 1697 */             if (this.select) {
/* 1698 */               defaultCaret.moveDot(i, arrayOfBias[0]);
/*      */             } else {
/* 1700 */               defaultCaret.setDot(i, arrayOfBias[0]);
/*      */             }
/*      */           
/*      */           }
/* 1704 */           else if (this.select) {
/* 1705 */             caret.moveDot(i);
/*      */           } else {
/* 1707 */             caret.setDot(i);
/*      */           } 
/*      */           
/* 1710 */           if (point != null && (this.direction == 1 || this.direction == 5))
/*      */           {
/*      */             
/* 1713 */             jTextComponent.getCaret().setMagicCaretPosition(point);
/*      */           }
/* 1715 */         } catch (BadLocationException badLocationException) {}
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
/*      */   static class BeginWordAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     BeginWordAction(String param1String, boolean param1Boolean) {
/* 1739 */       super(param1String);
/* 1740 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1745 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1746 */       if (jTextComponent != null) {
/*      */         try {
/* 1748 */           int i = jTextComponent.getCaretPosition();
/* 1749 */           int j = Utilities.getWordStart(jTextComponent, i);
/* 1750 */           if (this.select) {
/* 1751 */             jTextComponent.moveCaretPosition(j);
/*      */           } else {
/* 1753 */             jTextComponent.setCaretPosition(j);
/*      */           } 
/* 1755 */         } catch (BadLocationException badLocationException) {
/* 1756 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */         } 
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
/*      */   static class EndWordAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EndWordAction(String param1String, boolean param1Boolean) {
/* 1779 */       super(param1String);
/* 1780 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1785 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1786 */       if (jTextComponent != null) {
/*      */         try {
/* 1788 */           int i = jTextComponent.getCaretPosition();
/* 1789 */           int j = Utilities.getWordEnd(jTextComponent, i);
/* 1790 */           if (this.select) {
/* 1791 */             jTextComponent.moveCaretPosition(j);
/*      */           } else {
/* 1793 */             jTextComponent.setCaretPosition(j);
/*      */           } 
/* 1795 */         } catch (BadLocationException badLocationException) {
/* 1796 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */         } 
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
/*      */   static class PreviousWordAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     PreviousWordAction(String param1String, boolean param1Boolean) {
/* 1819 */       super(param1String);
/* 1820 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1825 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1826 */       if (jTextComponent != null) {
/* 1827 */         int i = jTextComponent.getCaretPosition();
/* 1828 */         boolean bool = false;
/*      */         
/*      */         try {
/* 1831 */           Element element = Utilities.getParagraphElement(jTextComponent, i);
/* 1832 */           i = Utilities.getPreviousWord(jTextComponent, i);
/* 1833 */           if (i < element.getStartOffset())
/*      */           {
/*      */ 
/*      */             
/* 1837 */             i = Utilities.getParagraphElement(jTextComponent, i).getEndOffset() - 1;
/*      */           }
/* 1839 */         } catch (BadLocationException badLocationException) {
/* 1840 */           if (i != 0) {
/* 1841 */             i = 0;
/*      */           } else {
/*      */             
/* 1844 */             bool = true;
/*      */           } 
/*      */         } 
/* 1847 */         if (!bool) {
/* 1848 */           if (this.select) {
/* 1849 */             jTextComponent.moveCaretPosition(i);
/*      */           } else {
/* 1851 */             jTextComponent.setCaretPosition(i);
/*      */           } 
/*      */         } else {
/*      */           
/* 1855 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */         } 
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
/*      */   static class NextWordAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     NextWordAction(String param1String, boolean param1Boolean) {
/* 1878 */       super(param1String);
/* 1879 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1884 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1885 */       if (jTextComponent != null) {
/* 1886 */         int i = jTextComponent.getCaretPosition();
/* 1887 */         boolean bool = false;
/* 1888 */         int j = i;
/*      */         
/* 1890 */         Element element = Utilities.getParagraphElement(jTextComponent, i);
/*      */         try {
/* 1892 */           i = Utilities.getNextWord(jTextComponent, i);
/* 1893 */           if (i >= element.getEndOffset() && j != element
/* 1894 */             .getEndOffset() - 1)
/*      */           {
/*      */             
/* 1897 */             i = element.getEndOffset() - 1;
/*      */           }
/* 1899 */         } catch (BadLocationException badLocationException) {
/* 1900 */           int k = jTextComponent.getDocument().getLength();
/* 1901 */           if (i != k) {
/* 1902 */             if (j != element.getEndOffset() - 1) {
/* 1903 */               i = element.getEndOffset() - 1;
/*      */             } else {
/* 1905 */               i = k;
/*      */             } 
/*      */           } else {
/*      */             
/* 1909 */             bool = true;
/*      */           } 
/*      */         } 
/* 1912 */         if (!bool) {
/* 1913 */           if (this.select) {
/* 1914 */             jTextComponent.moveCaretPosition(i);
/*      */           } else {
/* 1916 */             jTextComponent.setCaretPosition(i);
/*      */           } 
/*      */         } else {
/*      */           
/* 1920 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */         } 
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
/*      */   static class BeginLineAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     BeginLineAction(String param1String, boolean param1Boolean) {
/* 1943 */       super(param1String);
/* 1944 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1949 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1950 */       if (jTextComponent != null) {
/*      */         try {
/* 1952 */           int i = jTextComponent.getCaretPosition();
/* 1953 */           int j = Utilities.getRowStart(jTextComponent, i);
/* 1954 */           if (this.select) {
/* 1955 */             jTextComponent.moveCaretPosition(j);
/*      */           } else {
/* 1957 */             jTextComponent.setCaretPosition(j);
/*      */           } 
/* 1959 */         } catch (BadLocationException badLocationException) {
/* 1960 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */         } 
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
/*      */   static class EndLineAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EndLineAction(String param1String, boolean param1Boolean) {
/* 1983 */       super(param1String);
/* 1984 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1989 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1990 */       if (jTextComponent != null) {
/*      */         try {
/* 1992 */           int i = jTextComponent.getCaretPosition();
/* 1993 */           int j = Utilities.getRowEnd(jTextComponent, i);
/* 1994 */           if (this.select) {
/* 1995 */             jTextComponent.moveCaretPosition(j);
/*      */           } else {
/* 1997 */             jTextComponent.setCaretPosition(j);
/*      */           } 
/* 1999 */         } catch (BadLocationException badLocationException) {
/* 2000 */           UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */         } 
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
/*      */   static class BeginParagraphAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     BeginParagraphAction(String param1String, boolean param1Boolean) {
/* 2023 */       super(param1String);
/* 2024 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2029 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 2030 */       if (jTextComponent != null) {
/* 2031 */         int i = jTextComponent.getCaretPosition();
/* 2032 */         Element element = Utilities.getParagraphElement(jTextComponent, i);
/* 2033 */         i = element.getStartOffset();
/* 2034 */         if (this.select) {
/* 2035 */           jTextComponent.moveCaretPosition(i);
/*      */         } else {
/* 2037 */           jTextComponent.setCaretPosition(i);
/*      */         } 
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
/*      */   static class EndParagraphAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EndParagraphAction(String param1String, boolean param1Boolean) {
/* 2060 */       super(param1String);
/* 2061 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2066 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 2067 */       if (jTextComponent != null) {
/* 2068 */         int i = jTextComponent.getCaretPosition();
/* 2069 */         Element element = Utilities.getParagraphElement(jTextComponent, i);
/* 2070 */         i = Math.min(jTextComponent.getDocument().getLength(), element
/* 2071 */             .getEndOffset());
/* 2072 */         if (this.select) {
/* 2073 */           jTextComponent.moveCaretPosition(i);
/*      */         } else {
/* 2075 */           jTextComponent.setCaretPosition(i);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class BeginAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */     
/*      */     BeginAction(String param1String, boolean param1Boolean) {
/* 2092 */       super(param1String);
/* 2093 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2098 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 2099 */       if (jTextComponent != null) {
/* 2100 */         if (this.select) {
/* 2101 */           jTextComponent.moveCaretPosition(0);
/*      */         } else {
/* 2103 */           jTextComponent.setCaretPosition(0);
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class EndAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */     
/*      */     EndAction(String param1String, boolean param1Boolean) {
/* 2120 */       super(param1String);
/* 2121 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2126 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 2127 */       if (jTextComponent != null) {
/* 2128 */         Document document = jTextComponent.getDocument();
/* 2129 */         int i = document.getLength();
/* 2130 */         if (this.select) {
/* 2131 */           jTextComponent.moveCaretPosition(i);
/*      */         } else {
/* 2133 */           jTextComponent.setCaretPosition(i);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class SelectWordAction
/*      */     extends TextAction
/*      */   {
/*      */     private Action start;
/*      */ 
/*      */ 
/*      */     
/*      */     private Action end;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SelectWordAction() {
/* 2155 */       super("select-word");
/* 2156 */       this.start = new DefaultEditorKit.BeginWordAction("pigdog", false);
/* 2157 */       this.end = new DefaultEditorKit.EndWordAction("pigdog", true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2162 */       this.start.actionPerformed(param1ActionEvent);
/* 2163 */       this.end.actionPerformed(param1ActionEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class SelectLineAction
/*      */     extends TextAction
/*      */   {
/*      */     private Action start;
/*      */ 
/*      */ 
/*      */     
/*      */     private Action end;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SelectLineAction() {
/* 2184 */       super("select-line");
/* 2185 */       this.start = new DefaultEditorKit.BeginLineAction("pigdog", false);
/* 2186 */       this.end = new DefaultEditorKit.EndLineAction("pigdog", true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2191 */       this.start.actionPerformed(param1ActionEvent);
/* 2192 */       this.end.actionPerformed(param1ActionEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class SelectParagraphAction
/*      */     extends TextAction
/*      */   {
/*      */     private Action start;
/*      */ 
/*      */ 
/*      */     
/*      */     private Action end;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SelectParagraphAction() {
/* 2213 */       super("select-paragraph");
/* 2214 */       this.start = new DefaultEditorKit.BeginParagraphAction("pigdog", false);
/* 2215 */       this.end = new DefaultEditorKit.EndParagraphAction("pigdog", true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2220 */       this.start.actionPerformed(param1ActionEvent);
/* 2221 */       this.end.actionPerformed(param1ActionEvent);
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
/*      */   static class SelectAllAction
/*      */     extends TextAction
/*      */   {
/*      */     SelectAllAction() {
/* 2242 */       super("select-all");
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2247 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 2248 */       if (jTextComponent != null) {
/* 2249 */         Document document = jTextComponent.getDocument();
/* 2250 */         jTextComponent.setCaretPosition(0);
/* 2251 */         jTextComponent.moveCaretPosition(document.getLength());
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
/*      */   static class UnselectAction
/*      */     extends TextAction
/*      */   {
/*      */     UnselectAction() {
/* 2268 */       super("unselect");
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2273 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 2274 */       if (jTextComponent != null) {
/* 2275 */         jTextComponent.setCaretPosition(jTextComponent.getCaretPosition());
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
/*      */   static class ToggleComponentOrientationAction
/*      */     extends TextAction
/*      */   {
/*      */     ToggleComponentOrientationAction() {
/* 2292 */       super("toggle-componentOrientation");
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2297 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 2298 */       if (jTextComponent != null) {
/* 2299 */         ComponentOrientation componentOrientation2, componentOrientation1 = jTextComponent.getComponentOrientation();
/*      */         
/* 2301 */         if (componentOrientation1 == ComponentOrientation.RIGHT_TO_LEFT) {
/* 2302 */           componentOrientation2 = ComponentOrientation.LEFT_TO_RIGHT;
/*      */         } else {
/* 2304 */           componentOrientation2 = ComponentOrientation.RIGHT_TO_LEFT;
/* 2305 */         }  jTextComponent.setComponentOrientation(componentOrientation2);
/* 2306 */         jTextComponent.repaint();
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/DefaultEditorKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */