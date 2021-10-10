/*     */ package javax.swing.text;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlainDocument
/*     */   extends AbstractDocument
/*     */ {
/*     */   public static final String tabSizeAttribute = "tabSize";
/*     */   public static final String lineLimitAttribute = "lineLimit";
/*     */   private AbstractDocument.AbstractElement defaultRoot;
/*     */   private Vector<Element> added;
/*     */   private Vector<Element> removed;
/*     */   private transient Segment s;
/*     */   
/*     */   public PlainDocument() {
/*  80 */     this(new GapContent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlainDocument(AbstractDocument.Content paramContent) {
/*  90 */     super(paramContent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 318 */     this.added = new Vector<>();
/* 319 */     this.removed = new Vector<>();
/*     */     putProperty("tabSize", Integer.valueOf(8));
/*     */     this.defaultRoot = createDefaultRoot();
/*     */   }
/*     */   
/*     */   public void insertString(int paramInt, String paramString, AttributeSet paramAttributeSet) throws BadLocationException {
/*     */     Object object = getProperty("filterNewlines");
/*     */     if (object instanceof Boolean && object.equals(Boolean.TRUE) && paramString != null && paramString.indexOf('\n') >= 0) {
/*     */       StringBuilder stringBuilder = new StringBuilder(paramString);
/*     */       int i = stringBuilder.length();
/*     */       for (byte b = 0; b < i; b++) {
/*     */         if (stringBuilder.charAt(b) == '\n')
/*     */           stringBuilder.setCharAt(b, ' '); 
/*     */       } 
/*     */       paramString = stringBuilder.toString();
/*     */     } 
/*     */     super.insertString(paramInt, paramString, paramAttributeSet);
/*     */   }
/*     */   
/*     */   public Element getDefaultRootElement() {
/*     */     return this.defaultRoot;
/*     */   }
/*     */   
/*     */   protected AbstractDocument.AbstractElement createDefaultRoot() {
/*     */     AbstractDocument.BranchElement branchElement = (AbstractDocument.BranchElement)createBranchElement(null, null);
/*     */     Element element = createLeafElement(branchElement, null, 0, 1);
/*     */     Element[] arrayOfElement = new Element[1];
/*     */     arrayOfElement[0] = element;
/*     */     branchElement.replace(0, 0, arrayOfElement);
/*     */     return branchElement;
/*     */   }
/*     */   
/*     */   public Element getParagraphElement(int paramInt) {
/*     */     Element element = getDefaultRootElement();
/*     */     return element.getElement(element.getElementIndex(paramInt));
/*     */   }
/*     */   
/*     */   protected void insertUpdate(AbstractDocument.DefaultDocumentEvent paramDefaultDocumentEvent, AttributeSet paramAttributeSet) {
/*     */     this.removed.removeAllElements();
/*     */     this.added.removeAllElements();
/*     */     AbstractDocument.BranchElement branchElement = (AbstractDocument.BranchElement)getDefaultRootElement();
/*     */     int i = paramDefaultDocumentEvent.getOffset();
/*     */     int j = paramDefaultDocumentEvent.getLength();
/*     */     if (i > 0) {
/*     */       i--;
/*     */       j++;
/*     */     } 
/*     */     int k = branchElement.getElementIndex(i);
/*     */     Element element = branchElement.getElement(k);
/*     */     int m = element.getStartOffset();
/*     */     int n = element.getEndOffset();
/*     */     int i1 = m;
/*     */     try {
/*     */       if (this.s == null)
/*     */         this.s = new Segment(); 
/*     */       getContent().getChars(i, j, this.s);
/*     */       boolean bool = false;
/*     */       for (byte b = 0; b < j; b++) {
/*     */         char c = this.s.array[this.s.offset + b];
/*     */         if (c == '\n') {
/*     */           int i2 = i + b + 1;
/*     */           this.added.addElement(createLeafElement(branchElement, null, i1, i2));
/*     */           i1 = i2;
/*     */           bool = true;
/*     */         } 
/*     */       } 
/*     */       if (bool) {
/*     */         this.removed.addElement(element);
/*     */         if (i + j == n && i1 != n && k + 1 < branchElement.getElementCount()) {
/*     */           Element element1 = branchElement.getElement(k + 1);
/*     */           this.removed.addElement(element1);
/*     */           n = element1.getEndOffset();
/*     */         } 
/*     */         if (i1 < n)
/*     */           this.added.addElement(createLeafElement(branchElement, null, i1, n)); 
/*     */         Element[] arrayOfElement1 = new Element[this.added.size()];
/*     */         this.added.copyInto((Object[])arrayOfElement1);
/*     */         Element[] arrayOfElement2 = new Element[this.removed.size()];
/*     */         this.removed.copyInto((Object[])arrayOfElement2);
/*     */         AbstractDocument.ElementEdit elementEdit = new AbstractDocument.ElementEdit(branchElement, k, arrayOfElement2, arrayOfElement1);
/*     */         paramDefaultDocumentEvent.addEdit(elementEdit);
/*     */         branchElement.replace(k, arrayOfElement2.length, arrayOfElement1);
/*     */       } 
/*     */       if (Utilities.isComposedTextAttributeDefined(paramAttributeSet))
/*     */         insertComposedTextUpdate(paramDefaultDocumentEvent, paramAttributeSet); 
/*     */     } catch (BadLocationException badLocationException) {
/*     */       throw new Error("Internal error: " + badLocationException.toString());
/*     */     } 
/*     */     super.insertUpdate(paramDefaultDocumentEvent, paramAttributeSet);
/*     */   }
/*     */   
/*     */   protected void removeUpdate(AbstractDocument.DefaultDocumentEvent paramDefaultDocumentEvent) {
/*     */     this.removed.removeAllElements();
/*     */     AbstractDocument.BranchElement branchElement = (AbstractDocument.BranchElement)getDefaultRootElement();
/*     */     int i = paramDefaultDocumentEvent.getOffset();
/*     */     int j = paramDefaultDocumentEvent.getLength();
/*     */     int k = branchElement.getElementIndex(i);
/*     */     int m = branchElement.getElementIndex(i + j);
/*     */     if (k != m) {
/*     */       int n;
/*     */       for (n = k; n <= m; n++)
/*     */         this.removed.addElement(branchElement.getElement(n)); 
/*     */       n = branchElement.getElement(k).getStartOffset();
/*     */       int i1 = branchElement.getElement(m).getEndOffset();
/*     */       Element[] arrayOfElement1 = new Element[1];
/*     */       arrayOfElement1[0] = createLeafElement(branchElement, null, n, i1);
/*     */       Element[] arrayOfElement2 = new Element[this.removed.size()];
/*     */       this.removed.copyInto((Object[])arrayOfElement2);
/*     */       AbstractDocument.ElementEdit elementEdit = new AbstractDocument.ElementEdit(branchElement, k, arrayOfElement2, arrayOfElement1);
/*     */       paramDefaultDocumentEvent.addEdit(elementEdit);
/*     */       branchElement.replace(k, arrayOfElement2.length, arrayOfElement1);
/*     */     } else {
/*     */       Element element = branchElement.getElement(k);
/*     */       if (!element.isLeaf()) {
/*     */         Element element1 = element.getElement(element.getElementIndex(i));
/*     */         if (Utilities.isComposedTextElement(element1)) {
/*     */           Element[] arrayOfElement1 = new Element[1];
/*     */           arrayOfElement1[0] = createLeafElement(branchElement, null, element.getStartOffset(), element.getEndOffset());
/*     */           Element[] arrayOfElement2 = new Element[1];
/*     */           arrayOfElement2[0] = element;
/*     */           AbstractDocument.ElementEdit elementEdit = new AbstractDocument.ElementEdit(branchElement, k, arrayOfElement2, arrayOfElement1);
/*     */           paramDefaultDocumentEvent.addEdit(elementEdit);
/*     */           branchElement.replace(k, 1, arrayOfElement1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     super.removeUpdate(paramDefaultDocumentEvent);
/*     */   }
/*     */   
/*     */   private void insertComposedTextUpdate(AbstractDocument.DefaultDocumentEvent paramDefaultDocumentEvent, AttributeSet paramAttributeSet) {
/*     */     this.added.removeAllElements();
/*     */     AbstractDocument.BranchElement branchElement = (AbstractDocument.BranchElement)getDefaultRootElement();
/*     */     int i = paramDefaultDocumentEvent.getOffset();
/*     */     int j = paramDefaultDocumentEvent.getLength();
/*     */     int k = branchElement.getElementIndex(i);
/*     */     Element element = branchElement.getElement(k);
/*     */     int m = element.getStartOffset();
/*     */     int n = element.getEndOffset();
/*     */     AbstractDocument.BranchElement[] arrayOfBranchElement = new AbstractDocument.BranchElement[1];
/*     */     arrayOfBranchElement[0] = (AbstractDocument.BranchElement)createBranchElement(branchElement, null);
/*     */     Element[] arrayOfElement1 = new Element[1];
/*     */     arrayOfElement1[0] = element;
/*     */     if (m != i)
/*     */       this.added.addElement(createLeafElement(arrayOfBranchElement[0], null, m, i)); 
/*     */     this.added.addElement(createLeafElement(arrayOfBranchElement[0], paramAttributeSet, i, i + j));
/*     */     if (n != i + j)
/*     */       this.added.addElement(createLeafElement(arrayOfBranchElement[0], null, i + j, n)); 
/*     */     Element[] arrayOfElement2 = new Element[this.added.size()];
/*     */     this.added.copyInto((Object[])arrayOfElement2);
/*     */     AbstractDocument.ElementEdit elementEdit = new AbstractDocument.ElementEdit(branchElement, k, arrayOfElement1, (Element[])arrayOfBranchElement);
/*     */     paramDefaultDocumentEvent.addEdit(elementEdit);
/*     */     arrayOfBranchElement[0].replace(0, 0, arrayOfElement2);
/*     */     branchElement.replace(k, 1, (Element[])arrayOfBranchElement);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/PlainDocument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */