/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.text.ParseException;
/*     */ import java.util.Locale;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.DefaultFormatterFactory;
/*     */ import javax.swing.text.DocumentFilter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ValueFormatter
/*     */   extends JFormattedTextField.AbstractFormatter
/*     */   implements FocusListener, Runnable
/*     */ {
/*     */   static void init(int paramInt, boolean paramBoolean, JFormattedTextField paramJFormattedTextField) {
/*  44 */     ValueFormatter valueFormatter = new ValueFormatter(paramInt, paramBoolean);
/*  45 */     paramJFormattedTextField.setColumns(paramInt);
/*  46 */     paramJFormattedTextField.setFormatterFactory(new DefaultFormatterFactory(valueFormatter));
/*  47 */     paramJFormattedTextField.setHorizontalAlignment(4);
/*  48 */     paramJFormattedTextField.setMinimumSize(paramJFormattedTextField.getPreferredSize());
/*  49 */     paramJFormattedTextField.addFocusListener(valueFormatter);
/*     */   }
/*     */   
/*  52 */   private final DocumentFilter filter = new DocumentFilter()
/*     */     {
/*     */       public void remove(DocumentFilter.FilterBypass param1FilterBypass, int param1Int1, int param1Int2) throws BadLocationException {
/*  55 */         if (ValueFormatter.this.isValid(param1FilterBypass.getDocument().getLength() - param1Int2)) {
/*  56 */           param1FilterBypass.remove(param1Int1, param1Int2);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public void replace(DocumentFilter.FilterBypass param1FilterBypass, int param1Int1, int param1Int2, String param1String, AttributeSet param1AttributeSet) throws BadLocationException {
/*  62 */         if (ValueFormatter.this.isValid(param1FilterBypass.getDocument().getLength() + param1String.length() - param1Int2) && ValueFormatter.this.isValid(param1String)) {
/*  63 */           param1FilterBypass.replace(param1Int1, param1Int2, param1String.toUpperCase(Locale.ENGLISH), param1AttributeSet);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public void insertString(DocumentFilter.FilterBypass param1FilterBypass, int param1Int, String param1String, AttributeSet param1AttributeSet) throws BadLocationException {
/*  69 */         if (ValueFormatter.this.isValid(param1FilterBypass.getDocument().getLength() + param1String.length()) && ValueFormatter.this.isValid(param1String)) {
/*  70 */           param1FilterBypass.insertString(param1Int, param1String.toUpperCase(Locale.ENGLISH), param1AttributeSet);
/*     */         }
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final int length;
/*     */   private final int radix;
/*     */   private JFormattedTextField text;
/*     */   
/*     */   ValueFormatter(int paramInt, boolean paramBoolean) {
/*  81 */     this.length = paramInt;
/*  82 */     this.radix = paramBoolean ? 16 : 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object stringToValue(String paramString) throws ParseException {
/*     */     try {
/*  88 */       return Integer.valueOf(paramString, this.radix);
/*     */     }
/*  90 */     catch (NumberFormatException numberFormatException) {
/*  91 */       ParseException parseException = new ParseException("illegal format", 0);
/*  92 */       parseException.initCause(numberFormatException);
/*  93 */       throw parseException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String valueToString(Object paramObject) throws ParseException {
/*  99 */     if (paramObject instanceof Integer) {
/* 100 */       if (this.radix == 10) {
/* 101 */         return paramObject.toString();
/*     */       }
/* 103 */       int i = ((Integer)paramObject).intValue();
/* 104 */       int j = this.length;
/* 105 */       char[] arrayOfChar = new char[j];
/* 106 */       while (0 < j--) {
/* 107 */         arrayOfChar[j] = Character.forDigit(i & 0xF, this.radix);
/* 108 */         i >>= 4;
/*     */       } 
/* 110 */       return (new String(arrayOfChar)).toUpperCase(Locale.ENGLISH);
/*     */     } 
/* 112 */     throw new ParseException("illegal object", 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DocumentFilter getDocumentFilter() {
/* 117 */     return this.filter;
/*     */   }
/*     */   
/*     */   public void focusGained(FocusEvent paramFocusEvent) {
/* 121 */     Object object = paramFocusEvent.getSource();
/* 122 */     if (object instanceof JFormattedTextField) {
/* 123 */       this.text = (JFormattedTextField)object;
/* 124 */       SwingUtilities.invokeLater(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent paramFocusEvent) {}
/*     */   
/*     */   public void run() {
/* 132 */     if (this.text != null) {
/* 133 */       this.text.selectAll();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isValid(int paramInt) {
/* 138 */     return (0 <= paramInt && paramInt <= this.length);
/*     */   }
/*     */   
/*     */   private boolean isValid(String paramString) {
/* 142 */     int i = paramString.length();
/* 143 */     for (byte b = 0; b < i; b++) {
/* 144 */       char c = paramString.charAt(b);
/* 145 */       if (Character.digit(c, this.radix) < 0) {
/* 146 */         return false;
/*     */       }
/*     */     } 
/* 149 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/ValueFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */