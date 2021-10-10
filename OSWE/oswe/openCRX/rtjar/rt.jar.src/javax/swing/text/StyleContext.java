/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Toolkit;
/*      */ import java.io.IOException;
/*      */ import java.io.NotSerializableException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Vector;
/*      */ import java.util.WeakHashMap;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import sun.font.FontUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StyleContext
/*      */   implements Serializable, AbstractDocument.AttributeContext
/*      */ {
/*      */   private static StyleContext defaultContext;
/*      */   public static final String DEFAULT_STYLE = "default";
/*      */   private static Hashtable<Object, String> freezeKeyMap;
/*      */   private static Hashtable<String, Object> thawKeyMap;
/*      */   private Style styles;
/*      */   private transient FontKey fontSearch;
/*      */   private transient Hashtable<FontKey, Font> fontTable;
/*      */   private transient Map<SmallAttributeSet, WeakReference<SmallAttributeSet>> attributesPool;
/*      */   private transient MutableAttributeSet search;
/*      */   private int unusedSets;
/*      */   static final int THRESHOLD = 9;
/*      */   
/*      */   public static final StyleContext getDefaultStyleContext() {
/*   74 */     if (defaultContext == null) {
/*   75 */       defaultContext = new StyleContext();
/*      */     }
/*   77 */     return defaultContext;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StyleContext() {
/*  735 */     this.fontSearch = new FontKey(null, 0, 0);
/*  736 */     this.fontTable = new Hashtable<>();
/*      */     
/*  738 */     this
/*  739 */       .attributesPool = Collections.synchronizedMap(new WeakHashMap<>());
/*  740 */     this.search = new SimpleAttributeSet(); this.styles = new NamedStyle(null); addStyle("default", null);
/*      */   }
/*      */   public Style addStyle(String paramString, Style paramStyle) { NamedStyle namedStyle = new NamedStyle(paramString, paramStyle); if (paramString != null) this.styles.addAttribute(paramString, namedStyle);  return namedStyle; }
/*      */   public void removeStyle(String paramString) { this.styles.removeAttribute(paramString); }
/*      */   public Style getStyle(String paramString) { return (Style)this.styles.getAttribute(paramString); }
/*      */   public Enumeration<?> getStyleNames() { return this.styles.getAttributeNames(); }
/*      */   public void addChangeListener(ChangeListener paramChangeListener) { this.styles.addChangeListener(paramChangeListener); }
/*      */   public void removeChangeListener(ChangeListener paramChangeListener) { this.styles.removeChangeListener(paramChangeListener); }
/*      */   public ChangeListener[] getChangeListeners() { return ((NamedStyle)this.styles).getChangeListeners(); }
/*      */   public Font getFont(AttributeSet paramAttributeSet) { int i = 0; if (StyleConstants.isBold(paramAttributeSet)) i |= 0x1;  if (StyleConstants.isItalic(paramAttributeSet)) i |= 0x2;  String str = StyleConstants.getFontFamily(paramAttributeSet); int j = StyleConstants.getFontSize(paramAttributeSet); if (StyleConstants.isSuperscript(paramAttributeSet) || StyleConstants.isSubscript(paramAttributeSet)) j -= 2;  return getFont(str, i, j); }
/*      */   public Color getForeground(AttributeSet paramAttributeSet) { return StyleConstants.getForeground(paramAttributeSet); }
/*      */   public Color getBackground(AttributeSet paramAttributeSet) { return StyleConstants.getBackground(paramAttributeSet); }
/*      */   public Font getFont(String paramString, int paramInt1, int paramInt2) { this.fontSearch.setValue(paramString, paramInt1, paramInt2); Font font = this.fontTable.get(this.fontSearch); if (font == null) { Style style = getStyle("default"); if (style != null) { Font font1 = (Font)style.getAttribute("FONT_ATTRIBUTE_KEY"); if (font1 != null && font1.getFamily().equalsIgnoreCase(paramString)) font = font1.deriveFont(paramInt1, paramInt2);  }  if (font == null) font = new Font(paramString, paramInt1, paramInt2);  if (!FontUtilities.fontSupportsDefaultEncoding(font)) font = FontUtilities.getCompositeFontUIResource(font);  FontKey fontKey = new FontKey(paramString, paramInt1, paramInt2); this.fontTable.put(fontKey, font); }  return font; }
/*      */   public FontMetrics getFontMetrics(Font paramFont) { return Toolkit.getDefaultToolkit().getFontMetrics(paramFont); }
/*      */   public synchronized AttributeSet addAttribute(AttributeSet paramAttributeSet, Object paramObject1, Object paramObject2) { if (paramAttributeSet.getAttributeCount() + 1 <= getCompressionThreshold()) { this.search.removeAttributes(this.search); this.search.addAttributes(paramAttributeSet); this.search.addAttribute(paramObject1, paramObject2); reclaim(paramAttributeSet); return getImmutableUniqueSet(); }  MutableAttributeSet mutableAttributeSet = getMutableAttributeSet(paramAttributeSet); mutableAttributeSet.addAttribute(paramObject1, paramObject2); return mutableAttributeSet; }
/*      */   public synchronized AttributeSet addAttributes(AttributeSet paramAttributeSet1, AttributeSet paramAttributeSet2) { if (paramAttributeSet1.getAttributeCount() + paramAttributeSet2.getAttributeCount() <= getCompressionThreshold()) { this.search.removeAttributes(this.search); this.search.addAttributes(paramAttributeSet1); this.search.addAttributes(paramAttributeSet2); reclaim(paramAttributeSet1); return getImmutableUniqueSet(); }  MutableAttributeSet mutableAttributeSet = getMutableAttributeSet(paramAttributeSet1); mutableAttributeSet.addAttributes(paramAttributeSet2); return mutableAttributeSet; }
/*      */   public synchronized AttributeSet removeAttribute(AttributeSet paramAttributeSet, Object paramObject) { if (paramAttributeSet.getAttributeCount() - 1 <= getCompressionThreshold()) { this.search.removeAttributes(this.search); this.search.addAttributes(paramAttributeSet); this.search.removeAttribute(paramObject); reclaim(paramAttributeSet); return getImmutableUniqueSet(); }  MutableAttributeSet mutableAttributeSet = getMutableAttributeSet(paramAttributeSet); mutableAttributeSet.removeAttribute(paramObject); return mutableAttributeSet; }
/*      */   public synchronized AttributeSet removeAttributes(AttributeSet paramAttributeSet, Enumeration<?> paramEnumeration) { if (paramAttributeSet.getAttributeCount() <= getCompressionThreshold()) { this.search.removeAttributes(this.search); this.search.addAttributes(paramAttributeSet); this.search.removeAttributes(paramEnumeration); reclaim(paramAttributeSet); return getImmutableUniqueSet(); }  MutableAttributeSet mutableAttributeSet = getMutableAttributeSet(paramAttributeSet); mutableAttributeSet.removeAttributes(paramEnumeration); return mutableAttributeSet; }
/*      */   public synchronized AttributeSet removeAttributes(AttributeSet paramAttributeSet1, AttributeSet paramAttributeSet2) { if (paramAttributeSet1.getAttributeCount() <= getCompressionThreshold()) { this.search.removeAttributes(this.search); this.search.addAttributes(paramAttributeSet1); this.search.removeAttributes(paramAttributeSet2); reclaim(paramAttributeSet1); return getImmutableUniqueSet(); }  MutableAttributeSet mutableAttributeSet = getMutableAttributeSet(paramAttributeSet1); mutableAttributeSet.removeAttributes(paramAttributeSet2); return mutableAttributeSet; }
/*      */   public AttributeSet getEmptySet() { return SimpleAttributeSet.EMPTY; }
/*      */   public void reclaim(AttributeSet paramAttributeSet) { if (SwingUtilities.isEventDispatchThread()) this.attributesPool.size();  }
/*      */   protected int getCompressionThreshold() { return 9; }
/*      */   protected SmallAttributeSet createSmallAttributeSet(AttributeSet paramAttributeSet) { return new SmallAttributeSet(paramAttributeSet); }
/*      */   protected MutableAttributeSet createLargeAttributeSet(AttributeSet paramAttributeSet) { return new SimpleAttributeSet(paramAttributeSet); }
/*      */   synchronized void removeUnusedSets() { this.attributesPool.size(); } AttributeSet getImmutableUniqueSet() { SmallAttributeSet smallAttributeSet1 = createSmallAttributeSet(this.search); WeakReference<SmallAttributeSet> weakReference = this.attributesPool.get(smallAttributeSet1); SmallAttributeSet smallAttributeSet2; if (weakReference == null || (smallAttributeSet2 = weakReference.get()) == null) { smallAttributeSet2 = smallAttributeSet1; this.attributesPool.put(smallAttributeSet2, new WeakReference<>(smallAttributeSet2)); }  return smallAttributeSet2; } MutableAttributeSet getMutableAttributeSet(AttributeSet paramAttributeSet) { if (paramAttributeSet instanceof MutableAttributeSet && paramAttributeSet != SimpleAttributeSet.EMPTY) return (MutableAttributeSet)paramAttributeSet;  return createLargeAttributeSet(paramAttributeSet); } public String toString() { removeUnusedSets(); String str = ""; for (SmallAttributeSet smallAttributeSet : this.attributesPool.keySet()) str = str + smallAttributeSet + "\n";  return str; } public void writeAttributes(ObjectOutputStream paramObjectOutputStream, AttributeSet paramAttributeSet) throws IOException { writeAttributeSet(paramObjectOutputStream, paramAttributeSet); } public void readAttributes(ObjectInputStream paramObjectInputStream, MutableAttributeSet paramMutableAttributeSet) throws ClassNotFoundException, IOException { readAttributeSet(paramObjectInputStream, paramMutableAttributeSet); } public static void writeAttributeSet(ObjectOutputStream paramObjectOutputStream, AttributeSet paramAttributeSet) throws IOException { int i = paramAttributeSet.getAttributeCount(); paramObjectOutputStream.writeInt(i); Enumeration<?> enumeration = paramAttributeSet.getAttributeNames(); while (enumeration.hasMoreElements()) { Object object1 = enumeration.nextElement(); if (object1 instanceof Serializable) { paramObjectOutputStream.writeObject(object1); } else { Object object3 = freezeKeyMap.get(object1); if (object3 == null) throw new NotSerializableException(object1.getClass().getName() + " is not serializable as a key in an AttributeSet");  paramObjectOutputStream.writeObject(object3); }  Object object2 = paramAttributeSet.getAttribute(object1); Object object = freezeKeyMap.get(object2); if (object2 instanceof Serializable) { paramObjectOutputStream.writeObject((object != null) ? object : object2); continue; }  if (object == null) throw new NotSerializableException(object2.getClass().getName() + " is not serializable as a value in an AttributeSet");  paramObjectOutputStream.writeObject(object); }  } public static void readAttributeSet(ObjectInputStream paramObjectInputStream, MutableAttributeSet paramMutableAttributeSet) throws ClassNotFoundException, IOException { int i = paramObjectInputStream.readInt(); for (byte b = 0; b < i; b++) { Object object1 = paramObjectInputStream.readObject(); Object object2 = paramObjectInputStream.readObject(); if (thawKeyMap != null) { Object object3 = thawKeyMap.get(object1); if (object3 != null) object1 = object3;  Object object4 = thawKeyMap.get(object2); if (object4 != null) object2 = object4;  }  paramMutableAttributeSet.addAttribute(object1, object2); }  } public static void registerStaticAttributeKey(Object paramObject) { String str = paramObject.getClass().getName() + "." + paramObject.toString(); if (freezeKeyMap == null) { freezeKeyMap = new Hashtable<>(); thawKeyMap = new Hashtable<>(); }  freezeKeyMap.put(paramObject, str); thawKeyMap.put(str, paramObject); } public static Object getStaticAttribute(Object paramObject) { if (thawKeyMap == null || paramObject == null) return null;  return thawKeyMap.get(paramObject); } public static Object getStaticAttributeKey(Object paramObject) { return paramObject.getClass().getName() + "." + paramObject.toString(); } private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException { removeUnusedSets(); paramObjectOutputStream.defaultWriteObject(); } private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException { this.fontSearch = new FontKey(null, 0, 0); this.fontTable = new Hashtable<>(); this.search = new SimpleAttributeSet(); this.attributesPool = Collections.synchronizedMap(new WeakHashMap<>()); paramObjectInputStream.defaultReadObject(); } public class SmallAttributeSet implements AttributeSet
/*      */   {
/*  766 */     Object[] attributes; public SmallAttributeSet(Object[] param1ArrayOfObject) { this.attributes = param1ArrayOfObject;
/*  767 */       updateResolveParent(); }
/*      */     
/*      */     AttributeSet resolveParent;
/*      */     public SmallAttributeSet(AttributeSet param1AttributeSet) {
/*  771 */       int i = param1AttributeSet.getAttributeCount();
/*  772 */       Object[] arrayOfObject = new Object[2 * i];
/*  773 */       Enumeration<?> enumeration = param1AttributeSet.getAttributeNames();
/*  774 */       byte b = 0;
/*  775 */       while (enumeration.hasMoreElements()) {
/*  776 */         arrayOfObject[b] = enumeration.nextElement();
/*  777 */         arrayOfObject[b + 1] = param1AttributeSet.getAttribute(arrayOfObject[b]);
/*  778 */         b += 2;
/*      */       } 
/*  780 */       this.attributes = arrayOfObject;
/*  781 */       updateResolveParent();
/*      */     }
/*      */     
/*      */     private void updateResolveParent() {
/*  785 */       this.resolveParent = null;
/*  786 */       Object[] arrayOfObject = this.attributes;
/*  787 */       for (byte b = 0; b < arrayOfObject.length; b += 2) {
/*  788 */         if (arrayOfObject[b] == StyleConstants.ResolveAttribute) {
/*  789 */           this.resolveParent = (AttributeSet)arrayOfObject[b + 1];
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     Object getLocalAttribute(Object param1Object) {
/*  796 */       if (param1Object == StyleConstants.ResolveAttribute) {
/*  797 */         return this.resolveParent;
/*      */       }
/*  799 */       Object[] arrayOfObject = this.attributes;
/*  800 */       for (byte b = 0; b < arrayOfObject.length; b += 2) {
/*  801 */         if (param1Object.equals(arrayOfObject[b])) {
/*  802 */           return arrayOfObject[b + 1];
/*      */         }
/*      */       } 
/*  805 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  814 */       String str = "{";
/*  815 */       Object[] arrayOfObject = this.attributes;
/*  816 */       for (byte b = 0; b < arrayOfObject.length; b += 2) {
/*  817 */         if (arrayOfObject[b + 1] instanceof AttributeSet) {
/*      */           
/*  819 */           str = str + arrayOfObject[b] + "=AttributeSet,";
/*      */         } else {
/*  821 */           str = str + arrayOfObject[b] + "=" + arrayOfObject[b + 1] + ",";
/*      */         } 
/*      */       } 
/*  824 */       str = str + "}";
/*  825 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  833 */       int i = 0;
/*  834 */       Object[] arrayOfObject = this.attributes;
/*  835 */       for (byte b = 1; b < arrayOfObject.length; b += 2) {
/*  836 */         i ^= arrayOfObject[b].hashCode();
/*      */       }
/*  838 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  850 */       if (param1Object instanceof AttributeSet) {
/*  851 */         AttributeSet attributeSet = (AttributeSet)param1Object;
/*  852 */         return (getAttributeCount() == attributeSet.getAttributeCount() && 
/*  853 */           containsAttributes(attributeSet));
/*      */       } 
/*  855 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  865 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAttributeCount() {
/*  877 */       return this.attributes.length / 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isDefined(Object param1Object) {
/*  888 */       Object[] arrayOfObject = this.attributes;
/*  889 */       int i = arrayOfObject.length;
/*  890 */       for (byte b = 0; b < i; b += 2) {
/*  891 */         if (param1Object.equals(arrayOfObject[b])) {
/*  892 */           return true;
/*      */         }
/*      */       } 
/*  895 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEqual(AttributeSet param1AttributeSet) {
/*  906 */       if (param1AttributeSet instanceof SmallAttributeSet) {
/*  907 */         return (param1AttributeSet == this);
/*      */       }
/*  909 */       return (getAttributeCount() == param1AttributeSet.getAttributeCount() && 
/*  910 */         containsAttributes(param1AttributeSet));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet copyAttributes() {
/*  920 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getAttribute(Object param1Object) {
/*  931 */       Object object = getLocalAttribute(param1Object);
/*  932 */       if (object == null) {
/*  933 */         AttributeSet attributeSet = getResolveParent();
/*  934 */         if (attributeSet != null)
/*  935 */           object = attributeSet.getAttribute(param1Object); 
/*      */       } 
/*  937 */       return object;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Enumeration<?> getAttributeNames() {
/*  947 */       return new StyleContext.KeyEnumeration(this.attributes);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAttribute(Object param1Object1, Object param1Object2) {
/*  959 */       return param1Object2.equals(getAttribute(param1Object1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAttributes(AttributeSet param1AttributeSet) {
/*  971 */       boolean bool = true;
/*      */       
/*  973 */       Enumeration<?> enumeration = param1AttributeSet.getAttributeNames();
/*  974 */       while (bool && enumeration.hasMoreElements()) {
/*  975 */         Object object = enumeration.nextElement();
/*  976 */         bool = param1AttributeSet.getAttribute(object).equals(getAttribute(object));
/*      */       } 
/*      */       
/*  979 */       return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getResolveParent() {
/*  990 */       return this.resolveParent;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class KeyEnumeration
/*      */     implements Enumeration<Object>
/*      */   {
/*      */     Object[] attr;
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */     
/*      */     KeyEnumeration(Object[] param1ArrayOfObject) {
/* 1006 */       this.attr = param1ArrayOfObject;
/* 1007 */       this.i = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasMoreElements() {
/* 1018 */       return (this.i < this.attr.length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object nextElement() {
/* 1029 */       if (this.i < this.attr.length) {
/* 1030 */         Object object = this.attr[this.i];
/* 1031 */         this.i += 2;
/* 1032 */         return object;
/*      */       } 
/* 1034 */       throw new NoSuchElementException();
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
/*      */   class KeyBuilder
/*      */   {
/*      */     public void initialize(AttributeSet param1AttributeSet) {
/* 1048 */       if (param1AttributeSet instanceof StyleContext.SmallAttributeSet) {
/* 1049 */         initialize(((StyleContext.SmallAttributeSet)param1AttributeSet).attributes);
/*      */       } else {
/* 1051 */         this.keys.removeAllElements();
/* 1052 */         this.data.removeAllElements();
/* 1053 */         Enumeration<?> enumeration = param1AttributeSet.getAttributeNames();
/* 1054 */         while (enumeration.hasMoreElements()) {
/* 1055 */           Object object = enumeration.nextElement();
/* 1056 */           addAttribute(object, param1AttributeSet.getAttribute(object));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void initialize(Object[] param1ArrayOfObject) {
/* 1066 */       this.keys.removeAllElements();
/* 1067 */       this.data.removeAllElements();
/* 1068 */       int i = param1ArrayOfObject.length;
/* 1069 */       for (byte b = 0; b < i; b += 2) {
/* 1070 */         this.keys.addElement(param1ArrayOfObject[b]);
/* 1071 */         this.data.addElement(param1ArrayOfObject[b + 1]);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object[] createTable() {
/* 1081 */       int i = this.keys.size();
/* 1082 */       Object[] arrayOfObject = new Object[2 * i];
/* 1083 */       for (byte b = 0; b < i; b++) {
/* 1084 */         int j = 2 * b;
/* 1085 */         arrayOfObject[j] = this.keys.elementAt(b);
/* 1086 */         arrayOfObject[j + 1] = this.data.elementAt(b);
/*      */       } 
/* 1088 */       return arrayOfObject;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getCount() {
/* 1096 */       return this.keys.size();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addAttribute(Object param1Object1, Object param1Object2) {
/* 1103 */       this.keys.addElement(param1Object1);
/* 1104 */       this.data.addElement(param1Object2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addAttributes(AttributeSet param1AttributeSet) {
/* 1111 */       if (param1AttributeSet instanceof StyleContext.SmallAttributeSet) {
/*      */         
/* 1113 */         Object[] arrayOfObject = ((StyleContext.SmallAttributeSet)param1AttributeSet).attributes;
/* 1114 */         int i = arrayOfObject.length;
/* 1115 */         for (byte b = 0; b < i; b += 2) {
/* 1116 */           addAttribute(arrayOfObject[b], arrayOfObject[b + 1]);
/*      */         }
/*      */       } else {
/* 1119 */         Enumeration<?> enumeration = param1AttributeSet.getAttributeNames();
/* 1120 */         while (enumeration.hasMoreElements()) {
/* 1121 */           Object object = enumeration.nextElement();
/* 1122 */           addAttribute(object, param1AttributeSet.getAttribute(object));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttribute(Object param1Object) {
/* 1131 */       int i = this.keys.size();
/* 1132 */       for (byte b = 0; b < i; b++) {
/* 1133 */         if (this.keys.elementAt(b).equals(param1Object)) {
/* 1134 */           this.keys.removeElementAt(b);
/* 1135 */           this.data.removeElementAt(b);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttributes(Enumeration<Object> param1Enumeration) {
/* 1145 */       while (param1Enumeration.hasMoreElements()) {
/* 1146 */         Object object = param1Enumeration.nextElement();
/* 1147 */         removeAttribute(object);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttributes(AttributeSet param1AttributeSet) {
/* 1155 */       Enumeration<?> enumeration = param1AttributeSet.getAttributeNames();
/* 1156 */       while (enumeration.hasMoreElements()) {
/* 1157 */         Object object1 = enumeration.nextElement();
/* 1158 */         Object object2 = param1AttributeSet.getAttribute(object1);
/* 1159 */         removeSearchAttribute(object1, object2);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void removeSearchAttribute(Object param1Object1, Object param1Object2) {
/* 1164 */       int i = this.keys.size();
/* 1165 */       for (byte b = 0; b < i; b++) {
/* 1166 */         if (this.keys.elementAt(b).equals(param1Object1)) {
/* 1167 */           if (this.data.elementAt(b).equals(param1Object2)) {
/* 1168 */             this.keys.removeElementAt(b);
/* 1169 */             this.data.removeElementAt(b);
/*      */           } 
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1176 */     private Vector<Object> keys = new Vector();
/* 1177 */     private Vector<Object> data = new Vector();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class FontKey
/*      */   {
/*      */     private String family;
/*      */ 
/*      */     
/*      */     private int style;
/*      */     
/*      */     private int size;
/*      */ 
/*      */     
/*      */     public FontKey(String param1String, int param1Int1, int param1Int2) {
/* 1193 */       setValue(param1String, param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     public void setValue(String param1String, int param1Int1, int param1Int2) {
/* 1197 */       this.family = (param1String != null) ? param1String.intern() : null;
/* 1198 */       this.style = param1Int1;
/* 1199 */       this.size = param1Int2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1207 */       boolean bool = (this.family != null) ? this.family.hashCode() : false;
/* 1208 */       return bool ^ this.style ^ this.size;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1221 */       if (param1Object instanceof FontKey) {
/* 1222 */         FontKey fontKey = (FontKey)param1Object;
/* 1223 */         return (this.size == fontKey.size && this.style == fontKey.style && this.family == fontKey.family);
/*      */       } 
/* 1225 */       return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class NamedStyle
/*      */     implements Style, Serializable
/*      */   {
/*      */     public NamedStyle(String param1String, Style param1Style) {
/* 1257 */       this.attributes = StyleContext.this.getEmptySet();
/* 1258 */       if (param1String != null) {
/* 1259 */         setName(param1String);
/*      */       }
/* 1261 */       if (param1Style != null) {
/* 1262 */         setResolveParent(param1Style);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NamedStyle(Style param1Style) {
/* 1273 */       this(null, param1Style);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NamedStyle() {
/* 1280 */       this.attributes = StyleContext.this.getEmptySet();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1289 */       return "NamedStyle:" + getName() + " " + this.attributes;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 1299 */       if (isDefined(StyleConstants.NameAttribute)) {
/* 1300 */         return getAttribute(StyleConstants.NameAttribute).toString();
/*      */       }
/* 1302 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setName(String param1String) {
/* 1311 */       if (param1String != null) {
/* 1312 */         addAttribute(StyleConstants.NameAttribute, param1String);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addChangeListener(ChangeListener param1ChangeListener) {
/* 1322 */       this.listenerList.add(ChangeListener.class, param1ChangeListener);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeChangeListener(ChangeListener param1ChangeListener) {
/* 1331 */       this.listenerList.remove(ChangeListener.class, param1ChangeListener);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ChangeListener[] getChangeListeners() {
/* 1344 */       return this.listenerList.<ChangeListener>getListeners(ChangeListener.class);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void fireStateChanged() {
/* 1358 */       Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */       
/* 1361 */       for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1362 */         if (arrayOfObject[i] == ChangeListener.class) {
/*      */           
/* 1364 */           if (this.changeEvent == null)
/* 1365 */             this.changeEvent = new ChangeEvent(this); 
/* 1366 */           ((ChangeListener)arrayOfObject[i + 1]).stateChanged(this.changeEvent);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <T extends java.util.EventListener> T[] getListeners(Class<T> param1Class) {
/* 1381 */       return this.listenerList.getListeners(param1Class);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAttributeCount() {
/* 1394 */       return this.attributes.getAttributeCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isDefined(Object param1Object) {
/* 1405 */       return this.attributes.isDefined(param1Object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEqual(AttributeSet param1AttributeSet) {
/* 1416 */       return this.attributes.isEqual(param1AttributeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet copyAttributes() {
/* 1426 */       NamedStyle namedStyle = new NamedStyle();
/* 1427 */       namedStyle.attributes = this.attributes.copyAttributes();
/* 1428 */       return namedStyle;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getAttribute(Object param1Object) {
/* 1439 */       return this.attributes.getAttribute(param1Object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Enumeration<?> getAttributeNames() {
/* 1449 */       return this.attributes.getAttributeNames();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAttribute(Object param1Object1, Object param1Object2) {
/* 1461 */       return this.attributes.containsAttribute(param1Object1, param1Object2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAttributes(AttributeSet param1AttributeSet) {
/* 1473 */       return this.attributes.containsAttributes(param1AttributeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getResolveParent() {
/* 1485 */       return this.attributes.getResolveParent();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addAttribute(Object param1Object1, Object param1Object2) {
/* 1500 */       StyleContext styleContext = StyleContext.this;
/* 1501 */       this.attributes = styleContext.addAttribute(this.attributes, param1Object1, param1Object2);
/* 1502 */       fireStateChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addAttributes(AttributeSet param1AttributeSet) {
/* 1512 */       StyleContext styleContext = StyleContext.this;
/* 1513 */       this.attributes = styleContext.addAttributes(this.attributes, param1AttributeSet);
/* 1514 */       fireStateChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttribute(Object param1Object) {
/* 1524 */       StyleContext styleContext = StyleContext.this;
/* 1525 */       this.attributes = styleContext.removeAttribute(this.attributes, param1Object);
/* 1526 */       fireStateChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttributes(Enumeration<?> param1Enumeration) {
/* 1536 */       StyleContext styleContext = StyleContext.this;
/* 1537 */       this.attributes = styleContext.removeAttributes(this.attributes, param1Enumeration);
/* 1538 */       fireStateChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttributes(AttributeSet param1AttributeSet) {
/* 1548 */       StyleContext styleContext = StyleContext.this;
/* 1549 */       if (param1AttributeSet == this) {
/* 1550 */         this.attributes = styleContext.getEmptySet();
/*      */       } else {
/* 1552 */         this.attributes = styleContext.removeAttributes(this.attributes, param1AttributeSet);
/*      */       } 
/* 1554 */       fireStateChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setResolveParent(AttributeSet param1AttributeSet) {
/* 1564 */       if (param1AttributeSet != null) {
/* 1565 */         addAttribute(StyleConstants.ResolveAttribute, param1AttributeSet);
/*      */       } else {
/* 1567 */         removeAttribute(StyleConstants.ResolveAttribute);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 1574 */       param1ObjectOutputStream.defaultWriteObject();
/* 1575 */       StyleContext.writeAttributeSet(param1ObjectOutputStream, this.attributes);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws ClassNotFoundException, IOException {
/* 1581 */       param1ObjectInputStream.defaultReadObject();
/* 1582 */       this.attributes = SimpleAttributeSet.EMPTY;
/* 1583 */       StyleContext.readAttributeSet(param1ObjectInputStream, this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1591 */     protected EventListenerList listenerList = new EventListenerList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1598 */     protected transient ChangeEvent changeEvent = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private transient AttributeSet attributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 1611 */       int i = StyleConstants.keys.length;
/* 1612 */       for (byte b = 0; b < i; b++) {
/* 1613 */         registerStaticAttributeKey(StyleConstants.keys[b]);
/*      */       }
/* 1615 */     } catch (Throwable throwable) {
/* 1616 */       throwable.printStackTrace();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/StyleContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */