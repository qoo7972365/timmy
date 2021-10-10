/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.text.ParseException;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultFormatter
/*     */   extends JFormattedTextField.AbstractFormatter
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   transient ReplaceHolder replaceHolder;
/*     */   private DocumentFilter documentFilter;
/*     */   private NavigationFilter navigationFilter;
/*     */   private Class<?> valueClass;
/*     */   private boolean commitOnEdit;
/*     */   private boolean overwriteMode = true;
/*     */   private boolean allowsInvalid = true;
/*     */   
/*     */   public void install(JFormattedTextField paramJFormattedTextField) {
/* 125 */     super.install(paramJFormattedTextField);
/* 126 */     positionCursorAtInitialLocation();
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
/*     */   public void setCommitsOnValidEdit(boolean paramBoolean) {
/* 144 */     this.commitOnEdit = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCommitsOnValidEdit() {
/* 154 */     return this.commitOnEdit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOverwriteMode(boolean paramBoolean) {
/* 165 */     this.overwriteMode = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getOverwriteMode() {
/* 174 */     return this.overwriteMode;
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
/*     */   public void setAllowsInvalid(boolean paramBoolean) {
/* 188 */     this.allowsInvalid = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAllowsInvalid() {
/* 198 */     return this.allowsInvalid;
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
/*     */   public void setValueClass(Class<?> paramClass) {
/* 210 */     this.valueClass = paramClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getValueClass() {
/* 219 */     return this.valueClass;
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
/*     */   public Object stringToValue(String paramString) throws ParseException {
/* 237 */     Class<?> clazz = getValueClass();
/* 238 */     JFormattedTextField jFormattedTextField = getFormattedTextField();
/*     */     
/* 240 */     if (clazz == null && jFormattedTextField != null) {
/* 241 */       Object object = jFormattedTextField.getValue();
/*     */       
/* 243 */       if (object != null) {
/* 244 */         clazz = object.getClass();
/*     */       }
/*     */     } 
/* 247 */     if (clazz != null) {
/*     */       Constructor constructor;
/*     */       
/*     */       try {
/* 251 */         ReflectUtil.checkPackageAccess(clazz);
/* 252 */         SwingUtilities2.checkAccess(clazz.getModifiers());
/* 253 */         constructor = clazz.getConstructor(new Class[] { String.class });
/*     */       }
/* 255 */       catch (NoSuchMethodException noSuchMethodException) {
/* 256 */         constructor = null;
/*     */       } 
/*     */       
/* 259 */       if (constructor != null) {
/*     */         try {
/* 261 */           SwingUtilities2.checkAccess(constructor.getModifiers());
/* 262 */           return constructor.newInstance(new Object[] { paramString });
/* 263 */         } catch (Throwable throwable) {
/* 264 */           throw new ParseException("Error creating instance", 0);
/*     */         } 
/*     */       }
/*     */     } 
/* 268 */     return paramString;
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
/*     */   public String valueToString(Object paramObject) throws ParseException {
/* 280 */     if (paramObject == null) {
/* 281 */       return "";
/*     */     }
/* 283 */     return paramObject.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DocumentFilter getDocumentFilter() {
/* 293 */     if (this.documentFilter == null) {
/* 294 */       this.documentFilter = new DefaultDocumentFilter();
/*     */     }
/* 296 */     return this.documentFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NavigationFilter getNavigationFilter() {
/* 306 */     if (this.navigationFilter == null) {
/* 307 */       this.navigationFilter = new DefaultNavigationFilter();
/*     */     }
/* 309 */     return this.navigationFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 318 */     DefaultFormatter defaultFormatter = (DefaultFormatter)super.clone();
/*     */     
/* 320 */     defaultFormatter.navigationFilter = null;
/* 321 */     defaultFormatter.documentFilter = null;
/* 322 */     defaultFormatter.replaceHolder = null;
/* 323 */     return defaultFormatter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void positionCursorAtInitialLocation() {
/* 331 */     JFormattedTextField jFormattedTextField = getFormattedTextField();
/* 332 */     if (jFormattedTextField != null) {
/* 333 */       jFormattedTextField.setCaretPosition(getInitialVisualPosition());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getInitialVisualPosition() {
/* 342 */     return getNextNavigatableChar(0, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isNavigatable(int paramInt) {
/* 352 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLegalInsertText(String paramString) {
/* 361 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getNextNavigatableChar(int paramInt1, int paramInt2) {
/* 369 */     int i = getFormattedTextField().getDocument().getLength();
/*     */     
/* 371 */     while (paramInt1 >= 0 && paramInt1 < i) {
/* 372 */       if (isNavigatable(paramInt1)) {
/* 373 */         return paramInt1;
/*     */       }
/* 375 */       paramInt1 += paramInt2;
/*     */     } 
/* 377 */     return paramInt1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getReplaceString(int paramInt1, int paramInt2, String paramString) {
/* 388 */     String str1 = getFormattedTextField().getText();
/*     */ 
/*     */     
/* 391 */     String str2 = str1.substring(0, paramInt1);
/* 392 */     if (paramString != null) {
/* 393 */       str2 = str2 + paramString;
/*     */     }
/* 395 */     if (paramInt1 + paramInt2 < str1.length()) {
/* 396 */       str2 = str2 + str1.substring(paramInt1 + paramInt2);
/*     */     }
/* 398 */     return str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isValidEdit(ReplaceHolder paramReplaceHolder) {
/* 407 */     if (!getAllowsInvalid()) {
/* 408 */       String str = getReplaceString(paramReplaceHolder.offset, paramReplaceHolder.length, paramReplaceHolder.text);
/*     */       
/*     */       try {
/* 411 */         paramReplaceHolder.value = stringToValue(str);
/*     */         
/* 413 */         return true;
/* 414 */       } catch (ParseException parseException) {
/* 415 */         return false;
/*     */       } 
/*     */     } 
/* 418 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void commitEdit() throws ParseException {
/* 425 */     JFormattedTextField jFormattedTextField = getFormattedTextField();
/*     */     
/* 427 */     if (jFormattedTextField != null) {
/* 428 */       jFormattedTextField.commitEdit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateValue() {
/* 438 */     updateValue((Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateValue(Object paramObject) {
/*     */     try {
/* 448 */       if (paramObject == null) {
/* 449 */         String str = getFormattedTextField().getText();
/*     */         
/* 451 */         paramObject = stringToValue(str);
/*     */       } 
/*     */       
/* 454 */       if (getCommitsOnValidEdit()) {
/* 455 */         commitEdit();
/*     */       }
/* 457 */       setEditValid(true);
/* 458 */     } catch (ParseException parseException) {
/* 459 */       setEditValid(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getNextCursorPosition(int paramInt1, int paramInt2) {
/* 470 */     int i = getNextNavigatableChar(paramInt1, paramInt2);
/* 471 */     int j = getFormattedTextField().getDocument().getLength();
/*     */     
/* 473 */     if (!getAllowsInvalid()) {
/* 474 */       if (paramInt2 == -1 && paramInt1 == i) {
/*     */ 
/*     */         
/* 477 */         i = getNextNavigatableChar(i, 1);
/* 478 */         if (i >= j) {
/* 479 */           i = paramInt1;
/*     */         }
/*     */       }
/* 482 */       else if (paramInt2 == 1 && i >= j) {
/*     */         
/* 484 */         i = getNextNavigatableChar(j - 1, -1);
/* 485 */         if (i < j) {
/* 486 */           i++;
/*     */         }
/*     */       } 
/*     */     }
/* 490 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void repositionCursor(int paramInt1, int paramInt2) {
/* 497 */     getFormattedTextField().getCaret().setDot(
/* 498 */         getNextCursorPosition(paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getNextVisualPositionFrom(JTextComponent paramJTextComponent, int paramInt1, Position.Bias paramBias, int paramInt2, Position.Bias[] paramArrayOfBias) throws BadLocationException {
/* 509 */     int i = paramJTextComponent.getUI().getNextVisualPositionFrom(paramJTextComponent, paramInt1, paramBias, paramInt2, paramArrayOfBias);
/*     */ 
/*     */     
/* 512 */     if (i == -1) {
/* 513 */       return -1;
/*     */     }
/* 515 */     if (!getAllowsInvalid() && (paramInt2 == 3 || paramInt2 == 7)) {
/*     */       
/* 517 */       int j = -1;
/*     */       
/* 519 */       while (!isNavigatable(i) && i != j) {
/* 520 */         j = i;
/* 521 */         i = paramJTextComponent.getUI().getNextVisualPositionFrom(paramJTextComponent, i, paramBias, paramInt2, paramArrayOfBias);
/*     */       } 
/*     */       
/* 524 */       int k = getFormattedTextField().getDocument().getLength();
/* 525 */       if (j == i || i == k) {
/* 526 */         if (i == 0) {
/* 527 */           paramArrayOfBias[0] = Position.Bias.Forward;
/* 528 */           i = getInitialVisualPosition();
/*     */         } 
/* 530 */         if (i >= k && k > 0) {
/*     */           
/* 532 */           paramArrayOfBias[0] = Position.Bias.Forward;
/* 533 */           i = getNextNavigatableChar(k - 1, -1) + 1;
/*     */         } 
/*     */       } 
/*     */     } 
/* 537 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean canReplace(ReplaceHolder paramReplaceHolder) {
/* 545 */     return isValidEdit(paramReplaceHolder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void replace(DocumentFilter.FilterBypass paramFilterBypass, int paramInt1, int paramInt2, String paramString, AttributeSet paramAttributeSet) throws BadLocationException {
/* 554 */     ReplaceHolder replaceHolder = getReplaceHolder(paramFilterBypass, paramInt1, paramInt2, paramString, paramAttributeSet);
/*     */     
/* 556 */     replace(replaceHolder);
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
/*     */   boolean replace(ReplaceHolder paramReplaceHolder) throws BadLocationException {
/* 570 */     boolean bool = true;
/* 571 */     byte b = 1;
/*     */     
/* 573 */     if (paramReplaceHolder.length > 0 && (paramReplaceHolder.text == null || paramReplaceHolder.text.length() == 0) && (
/* 574 */       getFormattedTextField().getSelectionStart() != paramReplaceHolder.offset || paramReplaceHolder.length > 1))
/*     */     {
/* 576 */       b = -1;
/*     */     }
/*     */     
/* 579 */     if (getOverwriteMode() && paramReplaceHolder.text != null && 
/* 580 */       getFormattedTextField().getSelectedText() == null)
/*     */     {
/* 582 */       paramReplaceHolder.length = Math.min(Math.max(paramReplaceHolder.length, paramReplaceHolder.text.length()), paramReplaceHolder.fb
/* 583 */           .getDocument().getLength() - paramReplaceHolder.offset);
/*     */     }
/* 585 */     if ((paramReplaceHolder.text != null && !isLegalInsertText(paramReplaceHolder.text)) || 
/* 586 */       !canReplace(paramReplaceHolder) || (paramReplaceHolder.length == 0 && (paramReplaceHolder.text == null || paramReplaceHolder.text
/* 587 */       .length() == 0))) {
/* 588 */       bool = false;
/*     */     }
/* 590 */     if (bool) {
/* 591 */       int i = paramReplaceHolder.cursorPosition;
/*     */       
/* 593 */       paramReplaceHolder.fb.replace(paramReplaceHolder.offset, paramReplaceHolder.length, paramReplaceHolder.text, paramReplaceHolder.attrs);
/* 594 */       if (i == -1) {
/* 595 */         i = paramReplaceHolder.offset;
/* 596 */         if (b == 1 && paramReplaceHolder.text != null) {
/* 597 */           i = paramReplaceHolder.offset + paramReplaceHolder.text.length();
/*     */         }
/*     */       } 
/* 600 */       updateValue(paramReplaceHolder.value);
/* 601 */       repositionCursor(i, b);
/* 602 */       return true;
/*     */     } 
/*     */     
/* 605 */     invalidEdit();
/*     */     
/* 607 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setDot(NavigationFilter.FilterBypass paramFilterBypass, int paramInt, Position.Bias paramBias) {
/* 615 */     paramFilterBypass.setDot(paramInt, paramBias);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void moveDot(NavigationFilter.FilterBypass paramFilterBypass, int paramInt, Position.Bias paramBias) {
/* 624 */     paramFilterBypass.moveDot(paramInt, paramBias);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReplaceHolder getReplaceHolder(DocumentFilter.FilterBypass paramFilterBypass, int paramInt1, int paramInt2, String paramString, AttributeSet paramAttributeSet) {
/* 635 */     if (this.replaceHolder == null) {
/* 636 */       this.replaceHolder = new ReplaceHolder();
/*     */     }
/* 638 */     this.replaceHolder.reset(paramFilterBypass, paramInt1, paramInt2, paramString, paramAttributeSet);
/* 639 */     return this.replaceHolder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ReplaceHolder
/*     */   {
/*     */     DocumentFilter.FilterBypass fb;
/*     */ 
/*     */     
/*     */     int offset;
/*     */ 
/*     */     
/*     */     int length;
/*     */ 
/*     */     
/*     */     String text;
/*     */ 
/*     */     
/*     */     AttributeSet attrs;
/*     */ 
/*     */     
/*     */     Object value;
/*     */ 
/*     */     
/*     */     int cursorPosition;
/*     */ 
/*     */ 
/*     */     
/*     */     void reset(DocumentFilter.FilterBypass param1FilterBypass, int param1Int1, int param1Int2, String param1String, AttributeSet param1AttributeSet) {
/* 669 */       this.fb = param1FilterBypass;
/* 670 */       this.offset = param1Int1;
/* 671 */       this.length = param1Int2;
/* 672 */       this.text = param1String;
/* 673 */       this.attrs = param1AttributeSet;
/* 674 */       this.value = null;
/* 675 */       this.cursorPosition = -1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class DefaultNavigationFilter
/*     */     extends NavigationFilter
/*     */     implements Serializable
/*     */   {
/*     */     private DefaultNavigationFilter() {}
/*     */     
/*     */     public void setDot(NavigationFilter.FilterBypass param1FilterBypass, int param1Int, Position.Bias param1Bias) {
/* 687 */       JFormattedTextField jFormattedTextField = DefaultFormatter.this.getFormattedTextField();
/* 688 */       if (jFormattedTextField.composedTextExists()) {
/*     */         
/* 690 */         param1FilterBypass.setDot(param1Int, param1Bias);
/*     */       } else {
/* 692 */         DefaultFormatter.this.setDot(param1FilterBypass, param1Int, param1Bias);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void moveDot(NavigationFilter.FilterBypass param1FilterBypass, int param1Int, Position.Bias param1Bias) {
/* 697 */       JFormattedTextField jFormattedTextField = DefaultFormatter.this.getFormattedTextField();
/* 698 */       if (jFormattedTextField.composedTextExists()) {
/*     */         
/* 700 */         param1FilterBypass.moveDot(param1Int, param1Bias);
/*     */       } else {
/* 702 */         DefaultFormatter.this.moveDot(param1FilterBypass, param1Int, param1Bias);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getNextVisualPositionFrom(JTextComponent param1JTextComponent, int param1Int1, Position.Bias param1Bias, int param1Int2, Position.Bias[] param1ArrayOfBias) throws BadLocationException {
/* 711 */       if (param1JTextComponent.composedTextExists())
/*     */       {
/* 713 */         return param1JTextComponent.getUI().getNextVisualPositionFrom(param1JTextComponent, param1Int1, param1Bias, param1Int2, param1ArrayOfBias);
/*     */       }
/*     */       
/* 716 */       return DefaultFormatter.this.getNextVisualPositionFrom(param1JTextComponent, param1Int1, param1Bias, param1Int2, param1ArrayOfBias);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class DefaultDocumentFilter
/*     */     extends DocumentFilter
/*     */     implements Serializable
/*     */   {
/*     */     private DefaultDocumentFilter() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove(DocumentFilter.FilterBypass param1FilterBypass, int param1Int1, int param1Int2) throws BadLocationException {
/* 731 */       JFormattedTextField jFormattedTextField = DefaultFormatter.this.getFormattedTextField();
/* 732 */       if (jFormattedTextField.composedTextExists()) {
/*     */         
/* 734 */         param1FilterBypass.remove(param1Int1, param1Int2);
/*     */       } else {
/* 736 */         DefaultFormatter.this.replace(param1FilterBypass, param1Int1, param1Int2, (String)null, (AttributeSet)null);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void insertString(DocumentFilter.FilterBypass param1FilterBypass, int param1Int, String param1String, AttributeSet param1AttributeSet) throws BadLocationException {
/* 743 */       JFormattedTextField jFormattedTextField = DefaultFormatter.this.getFormattedTextField();
/* 744 */       if (jFormattedTextField.composedTextExists() || 
/* 745 */         Utilities.isComposedTextAttributeDefined(param1AttributeSet)) {
/*     */         
/* 747 */         param1FilterBypass.insertString(param1Int, param1String, param1AttributeSet);
/*     */       } else {
/* 749 */         DefaultFormatter.this.replace(param1FilterBypass, param1Int, 0, param1String, param1AttributeSet);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void replace(DocumentFilter.FilterBypass param1FilterBypass, int param1Int1, int param1Int2, String param1String, AttributeSet param1AttributeSet) throws BadLocationException {
/* 756 */       JFormattedTextField jFormattedTextField = DefaultFormatter.this.getFormattedTextField();
/* 757 */       if (jFormattedTextField.composedTextExists() || 
/* 758 */         Utilities.isComposedTextAttributeDefined(param1AttributeSet)) {
/*     */         
/* 760 */         param1FilterBypass.replace(param1Int1, param1Int2, param1String, param1AttributeSet);
/*     */       } else {
/* 762 */         DefaultFormatter.this.replace(param1FilterBypass, param1Int1, param1Int2, param1String, param1AttributeSet);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/DefaultFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */