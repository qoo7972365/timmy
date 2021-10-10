/*      */ package java.util.prefs;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.EventObject;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractPreferences
/*      */   extends Preferences
/*      */ {
/*      */   private final String name;
/*      */   private final String absolutePath;
/*      */   final AbstractPreferences parent;
/*      */   private final AbstractPreferences root;
/*      */   protected boolean newNode = false;
/*  158 */   private Map<String, AbstractPreferences> kidCache = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean removed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   private PreferenceChangeListener[] prefListeners = new PreferenceChangeListener[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  175 */   private NodeChangeListener[] nodeListeners = new NodeChangeListener[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  184 */   protected final Object lock = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractPreferences(AbstractPreferences paramAbstractPreferences, String paramString) {
/*  199 */     if (paramAbstractPreferences == null) {
/*  200 */       if (!paramString.equals("")) {
/*  201 */         throw new IllegalArgumentException("Root name '" + paramString + "' must be \"\"");
/*      */       }
/*  203 */       this.absolutePath = "/";
/*  204 */       this.root = this;
/*      */     } else {
/*  206 */       if (paramString.indexOf('/') != -1) {
/*  207 */         throw new IllegalArgumentException("Name '" + paramString + "' contains '/'");
/*      */       }
/*  209 */       if (paramString.equals("")) {
/*  210 */         throw new IllegalArgumentException("Illegal name: empty string");
/*      */       }
/*  212 */       this.root = paramAbstractPreferences.root;
/*  213 */       this
/*  214 */         .absolutePath = (paramAbstractPreferences == this.root) ? ("/" + paramString) : (paramAbstractPreferences.absolutePath() + "/" + paramString);
/*      */     } 
/*  216 */     this.name = paramString;
/*  217 */     this.parent = paramAbstractPreferences;
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
/*      */   public void put(String paramString1, String paramString2) {
/*  240 */     if (paramString1 == null || paramString2 == null)
/*  241 */       throw new NullPointerException(); 
/*  242 */     if (paramString1.length() > 80)
/*  243 */       throw new IllegalArgumentException("Key too long: " + paramString1); 
/*  244 */     if (paramString2.length() > 8192) {
/*  245 */       throw new IllegalArgumentException("Value too long: " + paramString2);
/*      */     }
/*  247 */     synchronized (this.lock) {
/*  248 */       if (this.removed) {
/*  249 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  251 */       putSpi(paramString1, paramString2);
/*  252 */       enqueuePreferenceChangeEvent(paramString1, paramString2);
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
/*      */   public String get(String paramString1, String paramString2) {
/*  279 */     if (paramString1 == null)
/*  280 */       throw new NullPointerException("Null key"); 
/*  281 */     synchronized (this.lock) {
/*  282 */       if (this.removed) {
/*  283 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  285 */       String str = null;
/*      */       try {
/*  287 */         str = getSpi(paramString1);
/*  288 */       } catch (Exception exception) {}
/*      */ 
/*      */       
/*  291 */       return (str == null) ? paramString2 : str;
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
/*      */   public void remove(String paramString) {
/*  311 */     Objects.requireNonNull(paramString, "Specified key cannot be null");
/*  312 */     synchronized (this.lock) {
/*  313 */       if (this.removed) {
/*  314 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  316 */       removeSpi(paramString);
/*  317 */       enqueuePreferenceChangeEvent(paramString, null);
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
/*      */   public void clear() throws BackingStoreException {
/*  336 */     synchronized (this.lock) {
/*  337 */       String[] arrayOfString = keys();
/*  338 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  339 */         remove(arrayOfString[b]);
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
/*      */   public void putInt(String paramString, int paramInt) {
/*  360 */     put(paramString, Integer.toString(paramInt));
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
/*      */   public int getInt(String paramString, int paramInt) {
/*  386 */     int i = paramInt;
/*      */     try {
/*  388 */       String str = get(paramString, null);
/*  389 */       if (str != null)
/*  390 */         i = Integer.parseInt(str); 
/*  391 */     } catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */     
/*  395 */     return i;
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
/*      */   public void putLong(String paramString, long paramLong) {
/*  415 */     put(paramString, Long.toString(paramLong));
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
/*      */   public long getLong(String paramString, long paramLong) {
/*  441 */     long l = paramLong;
/*      */     try {
/*  443 */       String str = get(paramString, null);
/*  444 */       if (str != null)
/*  445 */         l = Long.parseLong(str); 
/*  446 */     } catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */     
/*  450 */     return l;
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
/*      */   public void putBoolean(String paramString, boolean paramBoolean) {
/*  470 */     put(paramString, String.valueOf(paramBoolean));
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
/*      */   public boolean getBoolean(String paramString, boolean paramBoolean) {
/*  499 */     boolean bool = paramBoolean;
/*  500 */     String str = get(paramString, null);
/*  501 */     if (str != null) {
/*  502 */       if (str.equalsIgnoreCase("true")) {
/*  503 */         bool = true;
/*  504 */       } else if (str.equalsIgnoreCase("false")) {
/*  505 */         bool = false;
/*      */       } 
/*      */     }
/*  508 */     return bool;
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
/*      */   public void putFloat(String paramString, float paramFloat) {
/*  528 */     put(paramString, Float.toString(paramFloat));
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
/*      */   public float getFloat(String paramString, float paramFloat) {
/*  554 */     float f = paramFloat;
/*      */     try {
/*  556 */       String str = get(paramString, null);
/*  557 */       if (str != null)
/*  558 */         f = Float.parseFloat(str); 
/*  559 */     } catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */     
/*  563 */     return f;
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
/*      */   public void putDouble(String paramString, double paramDouble) {
/*  583 */     put(paramString, Double.toString(paramDouble));
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
/*      */   public double getDouble(String paramString, double paramDouble) {
/*  609 */     double d = paramDouble;
/*      */     try {
/*  611 */       String str = get(paramString, null);
/*  612 */       if (str != null)
/*  613 */         d = Double.parseDouble(str); 
/*  614 */     } catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */     
/*  618 */     return d;
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
/*      */   public void putByteArray(String paramString, byte[] paramArrayOfbyte) {
/*  634 */     put(paramString, Base64.byteArrayToBase64(paramArrayOfbyte));
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
/*      */   public byte[] getByteArray(String paramString, byte[] paramArrayOfbyte) {
/*  655 */     byte[] arrayOfByte = paramArrayOfbyte;
/*  656 */     String str = get(paramString, null);
/*      */     try {
/*  658 */       if (str != null) {
/*  659 */         arrayOfByte = Base64.base64ToByteArray(str);
/*      */       }
/*  661 */     } catch (RuntimeException runtimeException) {}
/*      */ 
/*      */ 
/*      */     
/*  665 */     return arrayOfByte;
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
/*      */   public String[] keys() throws BackingStoreException {
/*  684 */     synchronized (this.lock) {
/*  685 */       if (this.removed) {
/*  686 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  688 */       return keysSpi();
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
/*      */   public String[] childrenNames() throws BackingStoreException {
/*  713 */     synchronized (this.lock) {
/*  714 */       if (this.removed) {
/*  715 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  717 */       TreeSet<String> treeSet = new TreeSet(this.kidCache.keySet());
/*  718 */       for (String str : childrenNamesSpi())
/*  719 */         treeSet.add(str); 
/*  720 */       return treeSet.<String>toArray(EMPTY_STRING_ARRAY);
/*      */     } 
/*      */   }
/*      */   
/*  724 */   private static final String[] EMPTY_STRING_ARRAY = new String[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final AbstractPreferences[] cachedChildren() {
/*  732 */     return (AbstractPreferences[])this.kidCache.values().toArray((Object[])EMPTY_ABSTRACT_PREFS_ARRAY);
/*      */   }
/*      */   
/*  735 */   private static final AbstractPreferences[] EMPTY_ABSTRACT_PREFS_ARRAY = new AbstractPreferences[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Preferences parent() {
/*  751 */     synchronized (this.lock) {
/*  752 */       if (this.removed) {
/*  753 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  755 */       return this.parent;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Preferences node(String paramString) {
/*  805 */     synchronized (this.lock) {
/*  806 */       if (this.removed)
/*  807 */         throw new IllegalStateException("Node has been removed."); 
/*  808 */       if (paramString.equals(""))
/*  809 */         return this; 
/*  810 */       if (paramString.equals("/"))
/*  811 */         return this.root; 
/*  812 */       if (paramString.charAt(0) != '/') {
/*  813 */         return node(new StringTokenizer(paramString, "/", true));
/*      */       }
/*      */     } 
/*      */     
/*  817 */     return this.root.node(new StringTokenizer(paramString.substring(1), "/", true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Preferences node(StringTokenizer paramStringTokenizer) {
/*  824 */     String str = paramStringTokenizer.nextToken();
/*  825 */     if (str.equals("/"))
/*  826 */       throw new IllegalArgumentException("Consecutive slashes in path"); 
/*  827 */     synchronized (this.lock) {
/*  828 */       AbstractPreferences abstractPreferences = this.kidCache.get(str);
/*  829 */       if (abstractPreferences == null) {
/*  830 */         if (str.length() > 80) {
/*  831 */           throw new IllegalArgumentException("Node name " + str + " too long");
/*      */         }
/*  833 */         abstractPreferences = childSpi(str);
/*  834 */         if (abstractPreferences.newNode)
/*  835 */           enqueueNodeAddedEvent(abstractPreferences); 
/*  836 */         this.kidCache.put(str, abstractPreferences);
/*      */       } 
/*  838 */       if (!paramStringTokenizer.hasMoreTokens())
/*  839 */         return abstractPreferences; 
/*  840 */       paramStringTokenizer.nextToken();
/*  841 */       if (!paramStringTokenizer.hasMoreTokens())
/*  842 */         throw new IllegalArgumentException("Path ends with slash"); 
/*  843 */       return abstractPreferences.node(paramStringTokenizer);
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
/*      */   public boolean nodeExists(String paramString) throws BackingStoreException {
/*  870 */     synchronized (this.lock) {
/*  871 */       if (paramString.equals(""))
/*  872 */         return !this.removed; 
/*  873 */       if (this.removed)
/*  874 */         throw new IllegalStateException("Node has been removed."); 
/*  875 */       if (paramString.equals("/"))
/*  876 */         return true; 
/*  877 */       if (paramString.charAt(0) != '/') {
/*  878 */         return nodeExists(new StringTokenizer(paramString, "/", true));
/*      */       }
/*      */     } 
/*      */     
/*  882 */     return this.root.nodeExists(new StringTokenizer(paramString.substring(1), "/", true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean nodeExists(StringTokenizer paramStringTokenizer) throws BackingStoreException {
/*  892 */     String str = paramStringTokenizer.nextToken();
/*  893 */     if (str.equals("/"))
/*  894 */       throw new IllegalArgumentException("Consecutive slashes in path"); 
/*  895 */     synchronized (this.lock) {
/*  896 */       AbstractPreferences abstractPreferences = this.kidCache.get(str);
/*  897 */       if (abstractPreferences == null)
/*  898 */         abstractPreferences = getChild(str); 
/*  899 */       if (abstractPreferences == null)
/*  900 */         return false; 
/*  901 */       if (!paramStringTokenizer.hasMoreTokens())
/*  902 */         return true; 
/*  903 */       paramStringTokenizer.nextToken();
/*  904 */       if (!paramStringTokenizer.hasMoreTokens())
/*  905 */         throw new IllegalArgumentException("Path ends with slash"); 
/*  906 */       return abstractPreferences.nodeExists(paramStringTokenizer);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNode() throws BackingStoreException {
/*  943 */     if (this == this.root)
/*  944 */       throw new UnsupportedOperationException("Can't remove the root!"); 
/*  945 */     synchronized (this.parent.lock) {
/*  946 */       removeNode2();
/*  947 */       this.parent.kidCache.remove(this.name);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeNode2() throws BackingStoreException {
/*  956 */     synchronized (this.lock) {
/*  957 */       if (this.removed) {
/*  958 */         throw new IllegalStateException("Node already removed.");
/*      */       }
/*      */       
/*  961 */       String[] arrayOfString = childrenNamesSpi();
/*  962 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  963 */         if (!this.kidCache.containsKey(arrayOfString[b])) {
/*  964 */           this.kidCache.put(arrayOfString[b], childSpi(arrayOfString[b]));
/*      */         }
/*      */       } 
/*  967 */       Iterator<AbstractPreferences> iterator = this.kidCache.values().iterator();
/*  968 */       while (iterator.hasNext()) {
/*      */         try {
/*  970 */           ((AbstractPreferences)iterator.next()).removeNode2();
/*  971 */           iterator.remove();
/*  972 */         } catch (BackingStoreException backingStoreException) {}
/*      */       } 
/*      */ 
/*      */       
/*  976 */       removeNodeSpi();
/*  977 */       this.removed = true;
/*  978 */       this.parent.enqueueNodeRemovedEvent(this);
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
/*      */   public String name() {
/*  992 */     return this.name;
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
/*      */   public String absolutePath() {
/* 1007 */     return this.absolutePath;
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
/*      */   public boolean isUserNode() {
/* 1024 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */         {
/*      */           public Boolean run() {
/* 1027 */             return Boolean.valueOf((AbstractPreferences.this.root == Preferences.userRoot()));
/*      */           }
/* 1029 */         })).booleanValue();
/*      */   }
/*      */   
/*      */   public void addPreferenceChangeListener(PreferenceChangeListener paramPreferenceChangeListener) {
/* 1033 */     if (paramPreferenceChangeListener == null)
/* 1034 */       throw new NullPointerException("Change listener is null."); 
/* 1035 */     synchronized (this.lock) {
/* 1036 */       if (this.removed) {
/* 1037 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*      */       
/* 1040 */       PreferenceChangeListener[] arrayOfPreferenceChangeListener = this.prefListeners;
/* 1041 */       this.prefListeners = new PreferenceChangeListener[arrayOfPreferenceChangeListener.length + 1];
/* 1042 */       System.arraycopy(arrayOfPreferenceChangeListener, 0, this.prefListeners, 0, arrayOfPreferenceChangeListener.length);
/* 1043 */       this.prefListeners[arrayOfPreferenceChangeListener.length] = paramPreferenceChangeListener;
/*      */     } 
/* 1045 */     startEventDispatchThreadIfNecessary();
/*      */   }
/*      */   
/*      */   public void removePreferenceChangeListener(PreferenceChangeListener paramPreferenceChangeListener) {
/* 1049 */     synchronized (this.lock) {
/* 1050 */       if (this.removed)
/* 1051 */         throw new IllegalStateException("Node has been removed."); 
/* 1052 */       if (this.prefListeners == null || this.prefListeners.length == 0) {
/* 1053 */         throw new IllegalArgumentException("Listener not registered.");
/*      */       }
/*      */       
/* 1056 */       PreferenceChangeListener[] arrayOfPreferenceChangeListener = new PreferenceChangeListener[this.prefListeners.length - 1];
/*      */       
/* 1058 */       byte b = 0;
/* 1059 */       while (b < arrayOfPreferenceChangeListener.length && this.prefListeners[b] != paramPreferenceChangeListener) {
/* 1060 */         arrayOfPreferenceChangeListener[b] = this.prefListeners[b++];
/*      */       }
/* 1062 */       if (b == arrayOfPreferenceChangeListener.length && this.prefListeners[b] != paramPreferenceChangeListener)
/* 1063 */         throw new IllegalArgumentException("Listener not registered."); 
/* 1064 */       while (b < arrayOfPreferenceChangeListener.length)
/* 1065 */         arrayOfPreferenceChangeListener[b] = this.prefListeners[++b]; 
/* 1066 */       this.prefListeners = arrayOfPreferenceChangeListener;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addNodeChangeListener(NodeChangeListener paramNodeChangeListener) {
/* 1071 */     if (paramNodeChangeListener == null)
/* 1072 */       throw new NullPointerException("Change listener is null."); 
/* 1073 */     synchronized (this.lock) {
/* 1074 */       if (this.removed) {
/* 1075 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*      */       
/* 1078 */       if (this.nodeListeners == null) {
/* 1079 */         this.nodeListeners = new NodeChangeListener[1];
/* 1080 */         this.nodeListeners[0] = paramNodeChangeListener;
/*      */       } else {
/* 1082 */         NodeChangeListener[] arrayOfNodeChangeListener = this.nodeListeners;
/* 1083 */         this.nodeListeners = new NodeChangeListener[arrayOfNodeChangeListener.length + 1];
/* 1084 */         System.arraycopy(arrayOfNodeChangeListener, 0, this.nodeListeners, 0, arrayOfNodeChangeListener.length);
/* 1085 */         this.nodeListeners[arrayOfNodeChangeListener.length] = paramNodeChangeListener;
/*      */       } 
/*      */     } 
/* 1088 */     startEventDispatchThreadIfNecessary();
/*      */   }
/*      */   
/*      */   public void removeNodeChangeListener(NodeChangeListener paramNodeChangeListener) {
/* 1092 */     synchronized (this.lock) {
/* 1093 */       if (this.removed)
/* 1094 */         throw new IllegalStateException("Node has been removed."); 
/* 1095 */       if (this.nodeListeners == null || this.nodeListeners.length == 0) {
/* 1096 */         throw new IllegalArgumentException("Listener not registered.");
/*      */       }
/*      */       
/* 1099 */       byte b = 0;
/* 1100 */       while (b < this.nodeListeners.length && this.nodeListeners[b] != paramNodeChangeListener)
/* 1101 */         b++; 
/* 1102 */       if (b == this.nodeListeners.length)
/* 1103 */         throw new IllegalArgumentException("Listener not registered."); 
/* 1104 */       NodeChangeListener[] arrayOfNodeChangeListener = new NodeChangeListener[this.nodeListeners.length - 1];
/*      */       
/* 1106 */       if (b != 0)
/* 1107 */         System.arraycopy(this.nodeListeners, 0, arrayOfNodeChangeListener, 0, b); 
/* 1108 */       if (b != arrayOfNodeChangeListener.length) {
/* 1109 */         System.arraycopy(this.nodeListeners, b + 1, arrayOfNodeChangeListener, b, arrayOfNodeChangeListener.length - b);
/*      */       }
/* 1111 */       this.nodeListeners = arrayOfNodeChangeListener;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractPreferences getChild(String paramString) throws BackingStoreException {
/* 1257 */     synchronized (this.lock) {
/*      */       
/* 1259 */       String[] arrayOfString = childrenNames();
/* 1260 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1261 */         if (arrayOfString[b].equals(paramString))
/* 1262 */           return childSpi(arrayOfString[b]); 
/*      */       } 
/* 1264 */     }  return null;
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
/*      */   public String toString() {
/* 1304 */     return (isUserNode() ? "User" : "System") + " Preference Node: " + 
/* 1305 */       absolutePath();
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
/*      */   public void sync() throws BackingStoreException {
/* 1329 */     sync2();
/*      */   }
/*      */ 
/*      */   
/*      */   private void sync2() throws BackingStoreException {
/*      */     AbstractPreferences[] arrayOfAbstractPreferences;
/* 1335 */     synchronized (this.lock) {
/* 1336 */       if (this.removed)
/* 1337 */         throw new IllegalStateException("Node has been removed"); 
/* 1338 */       syncSpi();
/* 1339 */       arrayOfAbstractPreferences = cachedChildren();
/*      */     } 
/*      */     
/* 1342 */     for (byte b = 0; b < arrayOfAbstractPreferences.length; b++) {
/* 1343 */       arrayOfAbstractPreferences[b].sync2();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() throws BackingStoreException {
/* 1389 */     flush2();
/*      */   }
/*      */ 
/*      */   
/*      */   private void flush2() throws BackingStoreException {
/*      */     AbstractPreferences[] arrayOfAbstractPreferences;
/* 1395 */     synchronized (this.lock) {
/* 1396 */       flushSpi();
/* 1397 */       if (this.removed)
/*      */         return; 
/* 1399 */       arrayOfAbstractPreferences = cachedChildren();
/*      */     } 
/*      */     
/* 1402 */     for (byte b = 0; b < arrayOfAbstractPreferences.length; b++) {
/* 1403 */       arrayOfAbstractPreferences[b].flush2();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isRemoved() {
/* 1437 */     synchronized (this.lock) {
/* 1438 */       return this.removed;
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
/* 1450 */   private static final List<EventObject> eventQueue = new LinkedList<>();
/*      */ 
/*      */   
/*      */   private class NodeAddedEvent
/*      */     extends NodeChangeEvent
/*      */   {
/*      */     private static final long serialVersionUID = -6743557530157328528L;
/*      */ 
/*      */     
/*      */     NodeAddedEvent(Preferences param1Preferences1, Preferences param1Preferences2) {
/* 1460 */       super(param1Preferences1, param1Preferences2);
/*      */     }
/*      */   }
/*      */   
/*      */   private class NodeRemovedEvent extends NodeChangeEvent {
/*      */     NodeRemovedEvent(Preferences param1Preferences1, Preferences param1Preferences2) {
/* 1466 */       super(param1Preferences1, param1Preferences2);
/*      */     }
/*      */     
/*      */     private static final long serialVersionUID = 8735497392918824837L;
/*      */   }
/*      */   
/*      */   private static class EventDispatchThread
/*      */     extends Thread {
/*      */     private EventDispatchThread() {}
/*      */     
/*      */     public void run() {
/*      */       while (true) {
/* 1478 */         EventObject eventObject = null;
/* 1479 */         synchronized (AbstractPreferences.eventQueue) { while (true) {
/*      */             try {
/* 1481 */               if (AbstractPreferences.eventQueue.isEmpty()) {
/* 1482 */                 AbstractPreferences.eventQueue.wait(); continue;
/* 1483 */               }  eventObject = AbstractPreferences.eventQueue.remove(0);
/* 1484 */             } catch (InterruptedException interruptedException) {
/*      */               return;
/*      */             } 
/*      */             
/*      */             break;
/*      */           }  }
/*      */         
/* 1491 */         AbstractPreferences abstractPreferences = (AbstractPreferences)eventObject.getSource();
/* 1492 */         if (eventObject instanceof PreferenceChangeEvent) {
/* 1493 */           PreferenceChangeEvent preferenceChangeEvent = (PreferenceChangeEvent)eventObject;
/* 1494 */           PreferenceChangeListener[] arrayOfPreferenceChangeListener = abstractPreferences.prefListeners();
/* 1495 */           for (byte b1 = 0; b1 < arrayOfPreferenceChangeListener.length; b1++)
/* 1496 */             arrayOfPreferenceChangeListener[b1].preferenceChange(preferenceChangeEvent);  continue;
/*      */         } 
/* 1498 */         NodeChangeEvent nodeChangeEvent = (NodeChangeEvent)eventObject;
/* 1499 */         NodeChangeListener[] arrayOfNodeChangeListener = abstractPreferences.nodeListeners();
/* 1500 */         if (nodeChangeEvent instanceof AbstractPreferences.NodeAddedEvent) {
/* 1501 */           for (byte b1 = 0; b1 < arrayOfNodeChangeListener.length; b1++)
/* 1502 */             arrayOfNodeChangeListener[b1].childAdded(nodeChangeEvent); 
/*      */           continue;
/*      */         } 
/* 1505 */         for (byte b = 0; b < arrayOfNodeChangeListener.length; b++) {
/* 1506 */           arrayOfNodeChangeListener[b].childRemoved(nodeChangeEvent);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 1513 */   private static Thread eventDispatchThread = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized void startEventDispatchThreadIfNecessary() {
/* 1521 */     if (eventDispatchThread == null) {
/*      */       
/* 1523 */       eventDispatchThread = new EventDispatchThread();
/* 1524 */       eventDispatchThread.setDaemon(true);
/* 1525 */       eventDispatchThread.start();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PreferenceChangeListener[] prefListeners() {
/* 1536 */     synchronized (this.lock) {
/* 1537 */       return this.prefListeners;
/*      */     } 
/*      */   }
/*      */   NodeChangeListener[] nodeListeners() {
/* 1541 */     synchronized (this.lock) {
/* 1542 */       return this.nodeListeners;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void enqueuePreferenceChangeEvent(String paramString1, String paramString2) {
/* 1552 */     if (this.prefListeners.length != 0) {
/* 1553 */       synchronized (eventQueue) {
/* 1554 */         eventQueue.add(new PreferenceChangeEvent(this, paramString1, paramString2));
/* 1555 */         eventQueue.notify();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void enqueueNodeAddedEvent(Preferences paramPreferences) {
/* 1566 */     if (this.nodeListeners.length != 0) {
/* 1567 */       synchronized (eventQueue) {
/* 1568 */         eventQueue.add(new NodeAddedEvent(this, paramPreferences));
/* 1569 */         eventQueue.notify();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void enqueueNodeRemovedEvent(Preferences paramPreferences) {
/* 1580 */     if (this.nodeListeners.length != 0) {
/* 1581 */       synchronized (eventQueue) {
/* 1582 */         eventQueue.add(new NodeRemovedEvent(this, paramPreferences));
/* 1583 */         eventQueue.notify();
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
/*      */   public void exportNode(OutputStream paramOutputStream) throws IOException, BackingStoreException {
/* 1601 */     XmlSupport.export(paramOutputStream, this, false);
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
/*      */   public void exportSubtree(OutputStream paramOutputStream) throws IOException, BackingStoreException {
/* 1617 */     XmlSupport.export(paramOutputStream, this, true);
/*      */   }
/*      */   
/*      */   protected abstract void putSpi(String paramString1, String paramString2);
/*      */   
/*      */   protected abstract String getSpi(String paramString);
/*      */   
/*      */   protected abstract void removeSpi(String paramString);
/*      */   
/*      */   protected abstract void removeNodeSpi() throws BackingStoreException;
/*      */   
/*      */   protected abstract String[] keysSpi() throws BackingStoreException;
/*      */   
/*      */   protected abstract String[] childrenNamesSpi() throws BackingStoreException;
/*      */   
/*      */   protected abstract AbstractPreferences childSpi(String paramString);
/*      */   
/*      */   protected abstract void syncSpi() throws BackingStoreException;
/*      */   
/*      */   protected abstract void flushSpi() throws BackingStoreException;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/prefs/AbstractPreferences.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */