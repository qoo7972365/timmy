/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.BitSet;
/*     */ import javax.swing.AbstractListModel;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.ComboBoxModel;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.event.HyperlinkEvent;
/*     */ import javax.swing.event.ListDataListener;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.ComponentView;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.ElementIterator;
/*     */ import javax.swing.text.PlainDocument;
/*     */ import javax.swing.text.StyleConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FormView
/*     */   extends ComponentView
/*     */   implements ActionListener
/*     */ {
/*     */   @Deprecated
/* 117 */   public static final String SUBMIT = new String("Submit Query");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 126 */   public static final String RESET = new String("Reset");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String PostDataProperty = "javax.swing.JEditorPane.postdata";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private short maxIsPreferred;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FormView(Element paramElement) {
/* 148 */     super(paramElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Component createComponent() {
/* 157 */     AttributeSet attributeSet = getElement().getAttributes();
/*     */     
/* 159 */     HTML.Tag tag = (HTML.Tag)attributeSet.getAttribute(StyleConstants.NameAttribute);
/* 160 */     JComponent jComponent = null;
/* 161 */     Object object = attributeSet.getAttribute(StyleConstants.ModelAttribute);
/*     */ 
/*     */ 
/*     */     
/* 165 */     removeStaleListenerForModel(object);
/* 166 */     if (tag == HTML.Tag.INPUT) {
/* 167 */       jComponent = createInputComponent(attributeSet, object);
/* 168 */     } else if (tag == HTML.Tag.SELECT) {
/*     */       
/* 170 */       if (object instanceof OptionListModel) {
/*     */         
/* 172 */         JList jList = new JList((ListModel)object);
/* 173 */         int i = HTML.getIntegerAttributeValue(attributeSet, HTML.Attribute.SIZE, 1);
/*     */ 
/*     */         
/* 176 */         jList.setVisibleRowCount(i);
/* 177 */         jList.setSelectionModel((ListSelectionModel)object);
/* 178 */         jComponent = new JScrollPane(jList);
/*     */       } else {
/* 180 */         jComponent = new JComboBox((ComboBoxModel)object);
/* 181 */         this.maxIsPreferred = 3;
/*     */       } 
/* 183 */     } else if (tag == HTML.Tag.TEXTAREA) {
/* 184 */       JTextArea jTextArea = new JTextArea((Document)object);
/* 185 */       int i = HTML.getIntegerAttributeValue(attributeSet, HTML.Attribute.ROWS, 1);
/*     */ 
/*     */       
/* 188 */       jTextArea.setRows(i);
/* 189 */       int j = HTML.getIntegerAttributeValue(attributeSet, HTML.Attribute.COLS, 20);
/*     */ 
/*     */       
/* 192 */       this.maxIsPreferred = 3;
/* 193 */       jTextArea.setColumns(j);
/* 194 */       jComponent = new JScrollPane(jTextArea, 22, 32);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 199 */     if (jComponent != null) {
/* 200 */       jComponent.setAlignmentY(1.0F);
/*     */     }
/* 202 */     return jComponent;
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
/*     */   private JComponent createInputComponent(AttributeSet paramAttributeSet, Object paramObject) {
/*     */     Box box;
/* 215 */     JButton jButton = null;
/* 216 */     String str = (String)paramAttributeSet.getAttribute(HTML.Attribute.TYPE);
/*     */     
/* 218 */     if (str.equals("submit") || str.equals("reset")) {
/*     */       
/* 220 */       String str1 = (String)paramAttributeSet.getAttribute(HTML.Attribute.VALUE);
/* 221 */       if (str1 == null) {
/* 222 */         if (str.equals("submit")) {
/* 223 */           str1 = UIManager.getString("FormView.submitButtonText");
/*     */         } else {
/* 225 */           str1 = UIManager.getString("FormView.resetButtonText");
/*     */         } 
/*     */       }
/* 228 */       JButton jButton1 = new JButton(str1);
/* 229 */       if (paramObject != null) {
/* 230 */         jButton1.setModel((ButtonModel)paramObject);
/* 231 */         jButton1.addActionListener(this);
/*     */       } 
/* 233 */       jButton = jButton1;
/* 234 */       this.maxIsPreferred = 3;
/* 235 */     } else if (str.equals("image")) {
/* 236 */       JButton jButton1; String str1 = (String)paramAttributeSet.getAttribute(HTML.Attribute.SRC);
/*     */       
/*     */       try {
/* 239 */         URL uRL1 = ((HTMLDocument)getElement().getDocument()).getBase();
/* 240 */         URL uRL2 = new URL(uRL1, str1);
/* 241 */         ImageIcon imageIcon = new ImageIcon(uRL2);
/* 242 */         jButton1 = new JButton(imageIcon);
/* 243 */       } catch (MalformedURLException malformedURLException) {
/* 244 */         jButton1 = new JButton(str1);
/*     */       } 
/* 246 */       if (paramObject != null) {
/* 247 */         jButton1.setModel((ButtonModel)paramObject);
/* 248 */         jButton1.addMouseListener(new MouseEventListener());
/*     */       } 
/* 250 */       jButton = jButton1;
/* 251 */       this.maxIsPreferred = 3;
/* 252 */     } else if (str.equals("checkbox")) {
/* 253 */       JCheckBox jCheckBox = new JCheckBox();
/* 254 */       if (paramObject != null) {
/* 255 */         jCheckBox.setModel((JToggleButton.ToggleButtonModel)paramObject);
/*     */       }
/* 257 */       this.maxIsPreferred = 3;
/* 258 */     } else if (str.equals("radio")) {
/* 259 */       JRadioButton jRadioButton = new JRadioButton();
/* 260 */       if (paramObject != null) {
/* 261 */         jRadioButton.setModel((JToggleButton.ToggleButtonModel)paramObject);
/*     */       }
/* 263 */       this.maxIsPreferred = 3;
/* 264 */     } else if (str.equals("text")) {
/* 265 */       JTextField jTextField2; int i = HTML.getIntegerAttributeValue(paramAttributeSet, HTML.Attribute.SIZE, -1);
/*     */ 
/*     */ 
/*     */       
/* 269 */       if (i > 0) {
/* 270 */         jTextField2 = new JTextField();
/* 271 */         jTextField2.setColumns(i);
/*     */       } else {
/*     */         
/* 274 */         jTextField2 = new JTextField();
/* 275 */         jTextField2.setColumns(20);
/*     */       } 
/* 277 */       JTextField jTextField1 = jTextField2;
/* 278 */       if (paramObject != null) {
/* 279 */         jTextField2.setDocument((Document)paramObject);
/*     */       }
/* 281 */       jTextField2.addActionListener(this);
/* 282 */       this.maxIsPreferred = 3;
/* 283 */     } else if (str.equals("password")) {
/* 284 */       JPasswordField jPasswordField2 = new JPasswordField();
/* 285 */       JPasswordField jPasswordField1 = jPasswordField2;
/* 286 */       if (paramObject != null) {
/* 287 */         jPasswordField2.setDocument((Document)paramObject);
/*     */       }
/* 289 */       int i = HTML.getIntegerAttributeValue(paramAttributeSet, HTML.Attribute.SIZE, -1);
/*     */ 
/*     */       
/* 292 */       jPasswordField2.setColumns((i > 0) ? i : 20);
/* 293 */       jPasswordField2.addActionListener(this);
/* 294 */       this.maxIsPreferred = 3;
/* 295 */     } else if (str.equals("file")) {
/* 296 */       JTextField jTextField = new JTextField();
/* 297 */       if (paramObject != null) {
/* 298 */         jTextField.setDocument((Document)paramObject);
/*     */       }
/* 300 */       int i = HTML.getIntegerAttributeValue(paramAttributeSet, HTML.Attribute.SIZE, -1);
/*     */       
/* 302 */       jTextField.setColumns((i > 0) ? i : 20);
/*     */       
/* 304 */       JButton jButton1 = new JButton(UIManager.getString("FormView.browseFileButtonText"));
/* 305 */       Box box1 = Box.createHorizontalBox();
/* 306 */       box1.add(jTextField);
/* 307 */       box1.add(Box.createHorizontalStrut(5));
/* 308 */       box1.add(jButton1);
/* 309 */       jButton1.addActionListener(new BrowseFileAction(paramAttributeSet, (Document)paramObject));
/*     */       
/* 311 */       box = box1;
/* 312 */       this.maxIsPreferred = 3;
/*     */     } 
/* 314 */     return box;
/*     */   }
/*     */   
/*     */   private void removeStaleListenerForModel(Object paramObject) {
/* 318 */     if (paramObject instanceof DefaultButtonModel) {
/*     */ 
/*     */ 
/*     */       
/* 322 */       DefaultButtonModel defaultButtonModel = (DefaultButtonModel)paramObject;
/* 323 */       String str = "javax.swing.AbstractButton$Handler";
/* 324 */       for (ActionListener actionListener : defaultButtonModel.getActionListeners()) {
/* 325 */         if (str.equals(actionListener.getClass().getName())) {
/* 326 */           defaultButtonModel.removeActionListener(actionListener);
/*     */         }
/*     */       } 
/* 329 */       for (ChangeListener changeListener : defaultButtonModel.getChangeListeners()) {
/* 330 */         if (str.equals(changeListener.getClass().getName())) {
/* 331 */           defaultButtonModel.removeChangeListener(changeListener);
/*     */         }
/*     */       } 
/* 334 */       for (ItemListener itemListener : defaultButtonModel.getItemListeners()) {
/* 335 */         if (str.equals(itemListener.getClass().getName())) {
/* 336 */           defaultButtonModel.removeItemListener(itemListener);
/*     */         }
/*     */       } 
/* 339 */     } else if (paramObject instanceof AbstractListModel) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 345 */       AbstractListModel abstractListModel = (AbstractListModel)paramObject;
/* 346 */       String str1 = "javax.swing.plaf.basic.BasicListUI$Handler";
/*     */       
/* 348 */       String str2 = "javax.swing.plaf.basic.BasicComboBoxUI$Handler";
/*     */       
/* 350 */       for (ListDataListener listDataListener : abstractListModel.getListDataListeners()) {
/* 351 */         if (str1.equals(listDataListener.getClass().getName()) || str2
/* 352 */           .equals(listDataListener.getClass().getName()))
/*     */         {
/* 354 */           abstractListModel.removeListDataListener(listDataListener);
/*     */         }
/*     */       } 
/* 357 */     } else if (paramObject instanceof AbstractDocument) {
/*     */ 
/*     */       
/* 360 */       String str1 = "javax.swing.plaf.basic.BasicTextUI$UpdateHandler";
/*     */       
/* 362 */       String str2 = "javax.swing.text.DefaultCaret$Handler";
/*     */       
/* 364 */       AbstractDocument abstractDocument = (AbstractDocument)paramObject;
/* 365 */       for (DocumentListener documentListener : abstractDocument.getDocumentListeners()) {
/* 366 */         if (str1.equals(documentListener.getClass().getName()) || str2
/* 367 */           .equals(documentListener.getClass().getName()))
/*     */         {
/* 369 */           abstractDocument.removeDocumentListener(documentListener);
/*     */         }
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
/*     */   public float getMaximumSpan(int paramInt) {
/* 390 */     switch (paramInt) {
/*     */       case 0:
/* 392 */         if ((this.maxIsPreferred & 0x1) == 1) {
/* 393 */           super.getMaximumSpan(paramInt);
/* 394 */           return getPreferredSpan(paramInt);
/*     */         } 
/* 396 */         return super.getMaximumSpan(paramInt);
/*     */       case 1:
/* 398 */         if ((this.maxIsPreferred & 0x2) == 2) {
/* 399 */           super.getMaximumSpan(paramInt);
/* 400 */           return getPreferredSpan(paramInt);
/*     */         } 
/* 402 */         return super.getMaximumSpan(paramInt);
/*     */     } 
/*     */ 
/*     */     
/* 406 */     return super.getMaximumSpan(paramInt);
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
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/* 425 */     Element element = getElement();
/* 426 */     StringBuilder stringBuilder = new StringBuilder();
/* 427 */     HTMLDocument hTMLDocument = (HTMLDocument)getDocument();
/* 428 */     AttributeSet attributeSet = element.getAttributes();
/*     */     
/* 430 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.TYPE);
/*     */     
/* 432 */     if (str.equals("submit")) {
/* 433 */       getFormData(stringBuilder);
/* 434 */       submitData(stringBuilder.toString());
/* 435 */     } else if (str.equals("reset")) {
/* 436 */       resetForm();
/* 437 */     } else if (str.equals("text") || str.equals("password")) {
/* 438 */       if (isLastTextOrPasswordField()) {
/* 439 */         getFormData(stringBuilder);
/* 440 */         submitData(stringBuilder.toString());
/*     */       } else {
/* 442 */         getComponent().transferFocus();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void submitData(String paramString) {
/*     */     URL uRL2;
/* 453 */     Element element = getFormElement();
/* 454 */     AttributeSet attributeSet = element.getAttributes();
/* 455 */     HTMLDocument hTMLDocument = (HTMLDocument)element.getDocument();
/* 456 */     URL uRL1 = hTMLDocument.getBase();
/*     */     
/* 458 */     String str1 = (String)attributeSet.getAttribute(HTML.Attribute.TARGET);
/* 459 */     if (str1 == null) {
/* 460 */       str1 = "_self";
/*     */     }
/*     */     
/* 463 */     String str2 = (String)attributeSet.getAttribute(HTML.Attribute.METHOD);
/* 464 */     if (str2 == null) {
/* 465 */       str2 = "GET";
/*     */     }
/* 467 */     str2 = str2.toLowerCase();
/* 468 */     boolean bool = str2.equals("post");
/* 469 */     if (bool) {
/* 470 */       storePostData(hTMLDocument, str1, paramString);
/*     */     }
/*     */     
/* 473 */     String str3 = (String)attributeSet.getAttribute(HTML.Attribute.ACTION);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 478 */       uRL2 = (str3 == null) ? new URL(uRL1.getProtocol(), uRL1.getHost(), uRL1.getPort(), uRL1.getFile()) : new URL(uRL1, str3);
/*     */       
/* 480 */       if (!bool) {
/* 481 */         String str = paramString.toString();
/* 482 */         uRL2 = new URL(uRL2 + "?" + str);
/*     */       } 
/* 484 */     } catch (MalformedURLException malformedURLException) {
/* 485 */       uRL2 = null;
/*     */     } 
/* 487 */     final JEditorPane c = (JEditorPane)getContainer();
/* 488 */     HTMLEditorKit hTMLEditorKit = (HTMLEditorKit)jEditorPane.getEditorKit();
/*     */     
/* 490 */     FormSubmitEvent formSubmitEvent1 = null;
/* 491 */     if (!hTMLEditorKit.isAutoFormSubmission() || hTMLDocument.isFrameDocument()) {
/* 492 */       FormSubmitEvent.MethodType methodType = bool ? FormSubmitEvent.MethodType.POST : FormSubmitEvent.MethodType.GET;
/*     */ 
/*     */       
/* 495 */       formSubmitEvent1 = new FormSubmitEvent(this, HyperlinkEvent.EventType.ACTIVATED, uRL2, element, str1, methodType, paramString);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 501 */     final FormSubmitEvent fse = formSubmitEvent1;
/* 502 */     final URL url = uRL2;
/* 503 */     SwingUtilities.invokeLater(new Runnable() {
/*     */           public void run() {
/* 505 */             if (fse != null) {
/* 506 */               c.fireHyperlinkUpdate(fse);
/*     */             } else {
/*     */               try {
/* 509 */                 c.setPage(url);
/* 510 */               } catch (IOException iOException) {
/* 511 */                 UIManager.getLookAndFeel().provideErrorFeedback(c);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
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
/*     */   private void storePostData(HTMLDocument paramHTMLDocument, String paramString1, String paramString2) {
/* 530 */     Document document = paramHTMLDocument;
/* 531 */     String str = "javax.swing.JEditorPane.postdata";
/*     */     
/* 533 */     if (paramHTMLDocument.isFrameDocument()) {
/*     */ 
/*     */       
/* 536 */       FrameView.FrameEditorPane frameEditorPane = (FrameView.FrameEditorPane)getContainer();
/* 537 */       FrameView frameView = frameEditorPane.getFrameView();
/* 538 */       JEditorPane jEditorPane = frameView.getOutermostJEditorPane();
/* 539 */       if (jEditorPane != null) {
/* 540 */         document = jEditorPane.getDocument();
/* 541 */         str = str + "." + paramString1;
/*     */       } 
/*     */     } 
/*     */     
/* 545 */     document.putProperty(str, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class MouseEventListener
/*     */     extends MouseAdapter
/*     */   {
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 558 */       String str = FormView.this.getImageData(param1MouseEvent.getPoint());
/* 559 */       FormView.this.imageSubmit(str);
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
/*     */   protected void imageSubmit(String paramString) {
/* 572 */     StringBuilder stringBuilder = new StringBuilder();
/* 573 */     Element element = getElement();
/* 574 */     HTMLDocument hTMLDocument = (HTMLDocument)element.getDocument();
/* 575 */     getFormData(stringBuilder);
/* 576 */     if (stringBuilder.length() > 0) {
/* 577 */       stringBuilder.append('&');
/*     */     }
/* 579 */     stringBuilder.append(paramString);
/* 580 */     submitData(stringBuilder.toString());
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
/*     */   private String getImageData(Point paramPoint) {
/* 599 */     String str5, str1 = paramPoint.x + ":" + paramPoint.y;
/* 600 */     int i = str1.indexOf(':');
/* 601 */     String str2 = str1.substring(0, i);
/* 602 */     String str3 = str1.substring(++i);
/* 603 */     String str4 = (String)getElement().getAttributes().getAttribute(HTML.Attribute.NAME);
/*     */ 
/*     */     
/* 606 */     if (str4 == null || str4.equals("")) {
/* 607 */       str5 = "x=" + str2 + "&y=" + str3;
/*     */     } else {
/* 609 */       str4 = URLEncoder.encode(str4);
/* 610 */       str5 = str4 + ".x=" + str2 + "&" + str4 + ".y=" + str3;
/*     */     } 
/* 612 */     return str5;
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
/*     */   private Element getFormElement() {
/* 630 */     Element element = getElement();
/* 631 */     while (element != null) {
/* 632 */       if (element.getAttributes()
/* 633 */         .getAttribute(StyleConstants.NameAttribute) == HTML.Tag.FORM) {
/* 634 */         return element;
/*     */       }
/* 636 */       element = element.getParentElement();
/*     */     } 
/* 638 */     return null;
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
/*     */   private void getFormData(StringBuilder paramStringBuilder) {
/* 654 */     Element element = getFormElement();
/* 655 */     if (element != null) {
/* 656 */       ElementIterator elementIterator = new ElementIterator(element);
/*     */       
/*     */       Element element1;
/* 659 */       while ((element1 = elementIterator.next()) != null) {
/* 660 */         if (isControl(element1)) {
/*     */           
/* 662 */           String str = (String)element1.getAttributes().getAttribute(HTML.Attribute.TYPE);
/*     */           
/* 664 */           if (str != null && str.equals("submit") && element1 != 
/* 665 */             getElement())
/*     */             continue; 
/* 667 */           if (str == null || !str.equals("image"))
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 672 */             loadElementDataIntoBuffer(element1, paramStringBuilder);
/*     */           }
/*     */         } 
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
/*     */   private void loadElementDataIntoBuffer(Element paramElement, StringBuilder paramStringBuilder) {
/* 689 */     AttributeSet attributeSet = paramElement.getAttributes();
/* 690 */     String str1 = (String)attributeSet.getAttribute(HTML.Attribute.NAME);
/* 691 */     if (str1 == null) {
/*     */       return;
/*     */     }
/* 694 */     String str2 = null;
/*     */     
/* 696 */     HTML.Tag tag = (HTML.Tag)paramElement.getAttributes().getAttribute(StyleConstants.NameAttribute);
/*     */     
/* 698 */     if (tag == HTML.Tag.INPUT) {
/* 699 */       str2 = getInputElementData(attributeSet);
/* 700 */     } else if (tag == HTML.Tag.TEXTAREA) {
/* 701 */       str2 = getTextAreaData(attributeSet);
/* 702 */     } else if (tag == HTML.Tag.SELECT) {
/* 703 */       loadSelectData(attributeSet, paramStringBuilder);
/*     */     } 
/*     */     
/* 706 */     if (str1 != null && str2 != null) {
/* 707 */       appendBuffer(paramStringBuilder, str1, str2);
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
/*     */   private String getInputElementData(AttributeSet paramAttributeSet) {
/* 721 */     Object object = paramAttributeSet.getAttribute(StyleConstants.ModelAttribute);
/* 722 */     String str1 = (String)paramAttributeSet.getAttribute(HTML.Attribute.TYPE);
/* 723 */     String str2 = null;
/*     */     
/* 725 */     if (str1.equals("text") || str1.equals("password")) {
/* 726 */       Document document = (Document)object;
/*     */       try {
/* 728 */         str2 = document.getText(0, document.getLength());
/* 729 */       } catch (BadLocationException badLocationException) {
/* 730 */         str2 = null;
/*     */       } 
/* 732 */     } else if (str1.equals("submit") || str1.equals("hidden")) {
/* 733 */       str2 = (String)paramAttributeSet.getAttribute(HTML.Attribute.VALUE);
/* 734 */       if (str2 == null) {
/* 735 */         str2 = "";
/*     */       }
/* 737 */     } else if (str1.equals("radio") || str1.equals("checkbox")) {
/* 738 */       ButtonModel buttonModel = (ButtonModel)object;
/* 739 */       if (buttonModel.isSelected()) {
/* 740 */         str2 = (String)paramAttributeSet.getAttribute(HTML.Attribute.VALUE);
/* 741 */         if (str2 == null) {
/* 742 */           str2 = "on";
/*     */         }
/*     */       } 
/* 745 */     } else if (str1.equals("file")) {
/* 746 */       String str; Document document = (Document)object;
/*     */ 
/*     */       
/*     */       try {
/* 750 */         str = document.getText(0, document.getLength());
/* 751 */       } catch (BadLocationException badLocationException) {
/* 752 */         str = null;
/*     */       } 
/* 754 */       if (str != null && str.length() > 0) {
/* 755 */         str2 = str;
/*     */       }
/*     */     } 
/* 758 */     return str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getTextAreaData(AttributeSet paramAttributeSet) {
/* 767 */     Document document = (Document)paramAttributeSet.getAttribute(StyleConstants.ModelAttribute);
/*     */     try {
/* 769 */       return document.getText(0, document.getLength());
/* 770 */     } catch (BadLocationException badLocationException) {
/* 771 */       return null;
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
/*     */   private void loadSelectData(AttributeSet paramAttributeSet, StringBuilder paramStringBuilder) {
/* 783 */     String str = (String)paramAttributeSet.getAttribute(HTML.Attribute.NAME);
/* 784 */     if (str == null) {
/*     */       return;
/*     */     }
/* 787 */     Object object = paramAttributeSet.getAttribute(StyleConstants.ModelAttribute);
/* 788 */     if (object instanceof OptionListModel) {
/* 789 */       OptionListModel<Option> optionListModel = (OptionListModel)object;
/*     */       
/* 791 */       for (byte b = 0; b < optionListModel.getSize(); b++) {
/* 792 */         if (optionListModel.isSelectedIndex(b)) {
/* 793 */           Option option = optionListModel.getElementAt(b);
/* 794 */           appendBuffer(paramStringBuilder, str, option.getValue());
/*     */         } 
/*     */       } 
/* 797 */     } else if (object instanceof ComboBoxModel) {
/* 798 */       ComboBoxModel comboBoxModel = (ComboBoxModel)object;
/* 799 */       Option option = (Option)comboBoxModel.getSelectedItem();
/* 800 */       if (option != null) {
/* 801 */         appendBuffer(paramStringBuilder, str, option.getValue());
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
/*     */   private void appendBuffer(StringBuilder paramStringBuilder, String paramString1, String paramString2) {
/* 813 */     if (paramStringBuilder.length() > 0) {
/* 814 */       paramStringBuilder.append('&');
/*     */     }
/* 816 */     String str1 = URLEncoder.encode(paramString1);
/* 817 */     paramStringBuilder.append(str1);
/* 818 */     paramStringBuilder.append('=');
/* 819 */     String str2 = URLEncoder.encode(paramString2);
/* 820 */     paramStringBuilder.append(str2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isControl(Element paramElement) {
/* 827 */     return paramElement.isLeaf();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLastTextOrPasswordField() {
/* 837 */     Element element1 = getFormElement();
/* 838 */     Element element2 = getElement();
/*     */     
/* 840 */     if (element1 != null) {
/* 841 */       ElementIterator elementIterator = new ElementIterator(element1);
/*     */       
/* 843 */       boolean bool = false;
/*     */       Element element;
/* 845 */       while ((element = elementIterator.next()) != null) {
/* 846 */         if (element == element2) {
/* 847 */           bool = true; continue;
/*     */         } 
/* 849 */         if (bool && isControl(element)) {
/* 850 */           AttributeSet attributeSet = element.getAttributes();
/*     */ 
/*     */           
/* 853 */           if (HTMLDocument.matchNameAttribute(attributeSet, HTML.Tag.INPUT)) {
/*     */             
/* 855 */             String str = (String)attributeSet.getAttribute(HTML.Attribute.TYPE);
/*     */             
/* 857 */             if ("text".equals(str) || "password".equals(str)) {
/* 858 */               return false;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 864 */     return true;
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
/*     */   void resetForm() {
/* 876 */     Element element = getFormElement();
/*     */     
/* 878 */     if (element != null) {
/* 879 */       ElementIterator elementIterator = new ElementIterator(element);
/*     */       
/*     */       Element element1;
/* 882 */       while ((element1 = elementIterator.next()) != null) {
/* 883 */         if (isControl(element1)) {
/* 884 */           AttributeSet attributeSet = element1.getAttributes();
/* 885 */           Object object = attributeSet.getAttribute(StyleConstants.ModelAttribute);
/*     */           
/* 887 */           if (object instanceof TextAreaDocument) {
/* 888 */             TextAreaDocument textAreaDocument = (TextAreaDocument)object;
/* 889 */             textAreaDocument.reset(); continue;
/* 890 */           }  if (object instanceof PlainDocument) {
/*     */             try {
/* 892 */               PlainDocument plainDocument = (PlainDocument)object;
/* 893 */               plainDocument.remove(0, plainDocument.getLength());
/*     */               
/* 895 */               if (HTMLDocument.matchNameAttribute(attributeSet, HTML.Tag.INPUT)) {
/*     */                 
/* 897 */                 String str = (String)attributeSet.getAttribute(HTML.Attribute.VALUE);
/* 898 */                 if (str != null) {
/* 899 */                   plainDocument.insertString(0, str, (AttributeSet)null);
/*     */                 }
/*     */               } 
/* 902 */             } catch (BadLocationException badLocationException) {} continue;
/*     */           } 
/* 904 */           if (object instanceof OptionListModel) {
/* 905 */             OptionListModel optionListModel = (OptionListModel)object;
/* 906 */             int i = optionListModel.getSize();
/* 907 */             for (byte b1 = 0; b1 < i; b1++) {
/* 908 */               optionListModel.removeIndexInterval(b1, b1);
/*     */             }
/* 910 */             BitSet bitSet = optionListModel.getInitialSelection();
/* 911 */             for (byte b2 = 0; b2 < bitSet.size(); b2++) {
/* 912 */               if (bitSet.get(b2))
/* 913 */                 optionListModel.addSelectionInterval(b2, b2); 
/*     */             }  continue;
/*     */           } 
/* 916 */           if (object instanceof OptionComboBoxModel) {
/* 917 */             OptionComboBoxModel optionComboBoxModel = (OptionComboBoxModel)object;
/* 918 */             Option option = optionComboBoxModel.getInitialSelection();
/* 919 */             if (option != null)
/* 920 */               optionComboBoxModel.setSelectedItem(option);  continue;
/*     */           } 
/* 922 */           if (object instanceof JToggleButton.ToggleButtonModel) {
/*     */             
/* 924 */             boolean bool = ((String)attributeSet.getAttribute(HTML.Attribute.CHECKED) != null) ? true : false;
/* 925 */             JToggleButton.ToggleButtonModel toggleButtonModel = (JToggleButton.ToggleButtonModel)object;
/*     */             
/* 927 */             toggleButtonModel.setSelected(bool);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class BrowseFileAction
/*     */     implements ActionListener
/*     */   {
/*     */     private AttributeSet attrs;
/*     */ 
/*     */     
/*     */     private Document model;
/*     */ 
/*     */     
/*     */     BrowseFileAction(AttributeSet param1AttributeSet, Document param1Document) {
/* 946 */       this.attrs = param1AttributeSet;
/* 947 */       this.model = param1Document;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 953 */       JFileChooser jFileChooser = new JFileChooser();
/* 954 */       jFileChooser.setMultiSelectionEnabled(false);
/* 955 */       if (jFileChooser.showOpenDialog(FormView.this.getContainer()) == 0) {
/*     */         
/* 957 */         File file = jFileChooser.getSelectedFile();
/*     */         
/* 959 */         if (file != null)
/*     */           try {
/* 961 */             if (this.model.getLength() > 0) {
/* 962 */               this.model.remove(0, this.model.getLength());
/*     */             }
/* 964 */             this.model.insertString(0, file.getPath(), null);
/* 965 */           } catch (BadLocationException badLocationException) {} 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/FormView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */