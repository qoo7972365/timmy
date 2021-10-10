/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.io.Serializable;
/*     */ import java.util.EventObject;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.tree.TreeCellEditor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultCellEditor
/*     */   extends AbstractCellEditor
/*     */   implements TableCellEditor, TreeCellEditor
/*     */ {
/*     */   protected JComponent editorComponent;
/*     */   protected EditorDelegate delegate;
/*  73 */   protected int clickCountToStart = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"component"})
/*     */   public DefaultCellEditor(final JTextField textField) {
/*  86 */     this.editorComponent = textField;
/*  87 */     this.clickCountToStart = 2;
/*  88 */     this.delegate = new EditorDelegate() {
/*     */         public void setValue(Object param1Object) {
/*  90 */           textField.setText((param1Object != null) ? param1Object.toString() : "");
/*     */         }
/*     */         
/*     */         public Object getCellEditorValue() {
/*  94 */           return textField.getText();
/*     */         }
/*     */       };
/*  97 */     textField.addActionListener(this.delegate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultCellEditor(final JCheckBox checkBox) {
/* 106 */     this.editorComponent = checkBox;
/* 107 */     this.delegate = new EditorDelegate() {
/*     */         public void setValue(Object param1Object) {
/* 109 */           boolean bool = false;
/* 110 */           if (param1Object instanceof Boolean) {
/* 111 */             bool = ((Boolean)param1Object).booleanValue();
/*     */           }
/* 113 */           else if (param1Object instanceof String) {
/* 114 */             bool = param1Object.equals("true");
/*     */           } 
/* 116 */           checkBox.setSelected(bool);
/*     */         }
/*     */         
/*     */         public Object getCellEditorValue() {
/* 120 */           return Boolean.valueOf(checkBox.isSelected());
/*     */         }
/*     */       };
/* 123 */     checkBox.addActionListener(this.delegate);
/* 124 */     checkBox.setRequestFocusEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultCellEditor(final JComboBox comboBox) {
/* 134 */     this.editorComponent = comboBox;
/* 135 */     comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
/* 136 */     this.delegate = new EditorDelegate() {
/*     */         public void setValue(Object param1Object) {
/* 138 */           comboBox.setSelectedItem(param1Object);
/*     */         }
/*     */         
/*     */         public Object getCellEditorValue() {
/* 142 */           return comboBox.getSelectedItem();
/*     */         }
/*     */         
/*     */         public boolean shouldSelectCell(EventObject param1EventObject) {
/* 146 */           if (param1EventObject instanceof MouseEvent) {
/* 147 */             MouseEvent mouseEvent = (MouseEvent)param1EventObject;
/* 148 */             return (mouseEvent.getID() != 506);
/*     */           } 
/* 150 */           return true;
/*     */         }
/*     */         public boolean stopCellEditing() {
/* 153 */           if (comboBox.isEditable())
/*     */           {
/* 155 */             comboBox.actionPerformed(new ActionEvent(DefaultCellEditor.this, 0, ""));
/*     */           }
/*     */           
/* 158 */           return super.stopCellEditing();
/*     */         }
/*     */       };
/* 161 */     comboBox.addActionListener(this.delegate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getComponent() {
/* 170 */     return this.editorComponent;
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
/*     */   public void setClickCountToStart(int paramInt) {
/* 184 */     this.clickCountToStart = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getClickCountToStart() {
/* 192 */     return this.clickCountToStart;
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
/*     */   public Object getCellEditorValue() {
/* 206 */     return this.delegate.getCellEditorValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(EventObject paramEventObject) {
/* 215 */     return this.delegate.isCellEditable(paramEventObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldSelectCell(EventObject paramEventObject) {
/* 224 */     return this.delegate.shouldSelectCell(paramEventObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean stopCellEditing() {
/* 233 */     return this.delegate.stopCellEditing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelCellEditing() {
/* 242 */     this.delegate.cancelCellEditing();
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
/*     */   public Component getTreeCellEditorComponent(JTree paramJTree, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt) {
/* 254 */     String str = paramJTree.convertValueToText(paramObject, paramBoolean1, paramBoolean2, paramBoolean3, paramInt, false);
/*     */ 
/*     */     
/* 257 */     this.delegate.setValue(str);
/* 258 */     return this.editorComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getTableCellEditorComponent(JTable paramJTable, Object paramObject, boolean paramBoolean, int paramInt1, int paramInt2) {
/* 268 */     this.delegate.setValue(paramObject);
/* 269 */     if (this.editorComponent instanceof JCheckBox) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 276 */       TableCellRenderer tableCellRenderer = paramJTable.getCellRenderer(paramInt1, paramInt2);
/* 277 */       Component component = tableCellRenderer.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean, true, paramInt1, paramInt2);
/*     */       
/* 279 */       if (component != null) {
/* 280 */         this.editorComponent.setOpaque(true);
/* 281 */         this.editorComponent.setBackground(component.getBackground());
/* 282 */         if (component instanceof JComponent) {
/* 283 */           this.editorComponent.setBorder(((JComponent)component).getBorder());
/*     */         }
/*     */       } else {
/* 286 */         this.editorComponent.setOpaque(false);
/*     */       } 
/*     */     } 
/* 289 */     return this.editorComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class EditorDelegate
/*     */     implements ActionListener, ItemListener, Serializable
/*     */   {
/*     */     protected Object value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getCellEditorValue() {
/* 310 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setValue(Object param1Object) {
/* 318 */       this.value = param1Object;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isCellEditable(EventObject param1EventObject) {
/* 333 */       if (param1EventObject instanceof MouseEvent) {
/* 334 */         return (((MouseEvent)param1EventObject).getClickCount() >= DefaultCellEditor.this.clickCountToStart);
/*     */       }
/* 336 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean shouldSelectCell(EventObject param1EventObject) {
/* 348 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean startCellEditing(EventObject param1EventObject) {
/* 357 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean stopCellEditing() {
/* 368 */       DefaultCellEditor.this.fireEditingStopped();
/* 369 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void cancelCellEditing() {
/* 376 */       DefaultCellEditor.this.fireEditingCanceled();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 385 */       DefaultCellEditor.this.stopCellEditing();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void itemStateChanged(ItemEvent param1ItemEvent) {
/* 394 */       DefaultCellEditor.this.stopCellEditing();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/DefaultCellEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */