/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayoutFocusTraversalPolicy
/*     */   extends SortingFocusTraversalPolicy
/*     */   implements Serializable
/*     */ {
/*  55 */   private static final SwingDefaultFocusTraversalPolicy fitnessTestPolicy = new SwingDefaultFocusTraversalPolicy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutFocusTraversalPolicy() {
/*  62 */     super(new LayoutComparator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LayoutFocusTraversalPolicy(Comparator<? super Component> paramComparator) {
/*  70 */     super(paramComparator);
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
/*     */   public Component getComponentAfter(Container paramContainer, Component paramComponent) {
/*  97 */     if (paramContainer == null || paramComponent == null) {
/*  98 */       throw new IllegalArgumentException("aContainer and aComponent cannot be null");
/*     */     }
/* 100 */     Comparator<? super Component> comparator = getComparator();
/* 101 */     if (comparator instanceof LayoutComparator) {
/* 102 */       ((LayoutComparator)comparator)
/* 103 */         .setComponentOrientation(paramContainer
/* 104 */           .getComponentOrientation());
/*     */     }
/* 106 */     return super.getComponentAfter(paramContainer, paramComponent);
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
/*     */   public Component getComponentBefore(Container paramContainer, Component paramComponent) {
/* 133 */     if (paramContainer == null || paramComponent == null) {
/* 134 */       throw new IllegalArgumentException("aContainer and aComponent cannot be null");
/*     */     }
/* 136 */     Comparator<? super Component> comparator = getComparator();
/* 137 */     if (comparator instanceof LayoutComparator) {
/* 138 */       ((LayoutComparator)comparator)
/* 139 */         .setComponentOrientation(paramContainer
/* 140 */           .getComponentOrientation());
/*     */     }
/* 142 */     return super.getComponentBefore(paramContainer, paramComponent);
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
/*     */   public Component getFirstComponent(Container paramContainer) {
/* 157 */     if (paramContainer == null) {
/* 158 */       throw new IllegalArgumentException("aContainer cannot be null");
/*     */     }
/* 160 */     Comparator<? super Component> comparator = getComparator();
/* 161 */     if (comparator instanceof LayoutComparator) {
/* 162 */       ((LayoutComparator)comparator)
/* 163 */         .setComponentOrientation(paramContainer
/* 164 */           .getComponentOrientation());
/*     */     }
/* 166 */     return super.getFirstComponent(paramContainer);
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
/*     */   public Component getLastComponent(Container paramContainer) {
/* 181 */     if (paramContainer == null) {
/* 182 */       throw new IllegalArgumentException("aContainer cannot be null");
/*     */     }
/* 184 */     Comparator<? super Component> comparator = getComparator();
/* 185 */     if (comparator instanceof LayoutComparator) {
/* 186 */       ((LayoutComparator)comparator)
/* 187 */         .setComponentOrientation(paramContainer
/* 188 */           .getComponentOrientation());
/*     */     }
/* 190 */     return super.getLastComponent(paramContainer);
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
/*     */   protected boolean accept(Component paramComponent) {
/* 228 */     if (!super.accept(paramComponent))
/* 229 */       return false; 
/* 230 */     if (SunToolkit.isInstanceOf(paramComponent, "javax.swing.JTable"))
/*     */     {
/*     */       
/* 233 */       return true; } 
/* 234 */     if (SunToolkit.isInstanceOf(paramComponent, "javax.swing.JComboBox")) {
/* 235 */       JComboBox<?> jComboBox = (JComboBox)paramComponent;
/* 236 */       return jComboBox.getUI().isFocusTraversable(jComboBox);
/* 237 */     }  if (paramComponent instanceof JComponent) {
/* 238 */       JComponent jComponent = (JComponent)paramComponent;
/* 239 */       InputMap inputMap = jComponent.getInputMap(0, false);
/*     */       
/* 241 */       while (inputMap != null && inputMap.size() == 0) {
/* 242 */         inputMap = inputMap.getParent();
/*     */       }
/* 244 */       if (inputMap != null) {
/* 245 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 251 */     return fitnessTestPolicy.accept(paramComponent);
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 255 */     paramObjectOutputStream.writeObject(getComparator());
/* 256 */     paramObjectOutputStream.writeBoolean(getImplicitDownCycleTraversal());
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 261 */     setComparator((Comparator<? super Component>)paramObjectInputStream.readObject());
/* 262 */     setImplicitDownCycleTraversal(paramObjectInputStream.readBoolean());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/LayoutFocusTraversalPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */