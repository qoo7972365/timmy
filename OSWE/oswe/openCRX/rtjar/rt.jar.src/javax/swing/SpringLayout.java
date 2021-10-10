/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager2;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SpringLayout
/*      */   implements LayoutManager2
/*      */ {
/*  188 */   private Map<Component, Constraints> componentConstraints = new HashMap<>();
/*      */   
/*  190 */   private Spring cyclicReference = Spring.constant(-2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<Spring> cyclicSprings;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<Spring> acyclicSprings;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String NORTH = "North";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String SOUTH = "South";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String EAST = "East";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String WEST = "West";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String HORIZONTAL_CENTER = "HorizontalCenter";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String VERTICAL_CENTER = "VerticalCenter";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String BASELINE = "Baseline";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String WIDTH = "Width";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String HEIGHT = "Height";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  250 */   private static String[] ALL_HORIZONTAL = new String[] { "West", "Width", "East", "HorizontalCenter" };
/*      */   
/*  252 */   private static String[] ALL_VERTICAL = new String[] { "North", "Height", "South", "VerticalCenter", "Baseline" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Constraints
/*      */   {
/*      */     private Spring x;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Spring y;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Spring width;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Spring height;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Spring east;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Spring south;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Spring horizontalCenter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Spring verticalCenter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Spring baseline;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  329 */     private List<String> horizontalHistory = new ArrayList<>(2);
/*  330 */     private List<String> verticalHistory = new ArrayList<>(2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Component c;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Constraints(Spring param1Spring1, Spring param1Spring2) {
/*  352 */       setX(param1Spring1);
/*  353 */       setY(param1Spring2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Constraints(Spring param1Spring1, Spring param1Spring2, Spring param1Spring3, Spring param1Spring4) {
/*  372 */       setX(param1Spring1);
/*  373 */       setY(param1Spring2);
/*  374 */       setWidth(param1Spring3);
/*  375 */       setHeight(param1Spring4);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Constraints(Component param1Component) {
/*  395 */       this.c = param1Component;
/*  396 */       setX(Spring.constant(param1Component.getX()));
/*  397 */       setY(Spring.constant(param1Component.getY()));
/*  398 */       setWidth(Spring.width(param1Component));
/*  399 */       setHeight(Spring.height(param1Component));
/*      */     }
/*      */     
/*      */     private void pushConstraint(String param1String, Spring param1Spring, boolean param1Boolean) {
/*  403 */       boolean bool = true;
/*  404 */       List<String> list = param1Boolean ? this.horizontalHistory : this.verticalHistory;
/*      */       
/*  406 */       if (list.contains(param1String)) {
/*  407 */         list.remove(param1String);
/*  408 */         bool = false;
/*  409 */       } else if (list.size() == 2 && param1Spring != null) {
/*  410 */         list.remove(0);
/*  411 */         bool = false;
/*      */       } 
/*  413 */       if (param1Spring != null) {
/*  414 */         list.add(param1String);
/*      */       }
/*  416 */       if (!bool) {
/*  417 */         String[] arrayOfString = param1Boolean ? SpringLayout.ALL_HORIZONTAL : SpringLayout.ALL_VERTICAL;
/*  418 */         for (String str : arrayOfString) {
/*  419 */           if (!list.contains(str)) {
/*  420 */             setConstraint(str, null);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private Spring sum(Spring param1Spring1, Spring param1Spring2) {
/*  427 */       return (param1Spring1 == null || param1Spring2 == null) ? null : Spring.sum(param1Spring1, param1Spring2);
/*      */     }
/*      */     
/*      */     private Spring difference(Spring param1Spring1, Spring param1Spring2) {
/*  431 */       return (param1Spring1 == null || param1Spring2 == null) ? null : Spring.difference(param1Spring1, param1Spring2);
/*      */     }
/*      */     
/*      */     private Spring scale(Spring param1Spring, float param1Float) {
/*  435 */       return (param1Spring == null) ? null : Spring.scale(param1Spring, param1Float);
/*      */     }
/*      */     
/*      */     private int getBaselineFromHeight(int param1Int) {
/*  439 */       if (param1Int < 0)
/*      */       {
/*  441 */         return -this.c.getBaseline((this.c.getPreferredSize()).width, -param1Int);
/*      */       }
/*      */       
/*  444 */       return this.c.getBaseline((this.c.getPreferredSize()).width, param1Int);
/*      */     }
/*      */     
/*      */     private int getHeightFromBaseLine(int param1Int) {
/*  448 */       Dimension dimension = this.c.getPreferredSize();
/*  449 */       int i = dimension.height;
/*  450 */       int j = this.c.getBaseline(dimension.width, i);
/*  451 */       if (j == param1Int)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  456 */         return i;
/*      */       }
/*      */       
/*  459 */       switch (this.c.getBaselineResizeBehavior()) {
/*      */         case CONSTANT_DESCENT:
/*  461 */           return i + param1Int - j;
/*      */         case CENTER_OFFSET:
/*  463 */           return i + 2 * (param1Int - j);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  470 */       return Integer.MIN_VALUE;
/*      */     }
/*      */     
/*      */     private Spring heightToRelativeBaseline(Spring param1Spring) {
/*  474 */       return new Spring.SpringMap(param1Spring) {
/*      */           protected int map(int param2Int) {
/*  476 */             return SpringLayout.Constraints.this.getBaselineFromHeight(param2Int);
/*      */           }
/*      */           
/*      */           protected int inv(int param2Int) {
/*  480 */             return SpringLayout.Constraints.this.getHeightFromBaseLine(param2Int);
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     private Spring relativeBaselineToHeight(Spring param1Spring) {
/*  486 */       return new Spring.SpringMap(param1Spring) {
/*      */           protected int map(int param2Int) {
/*  488 */             return SpringLayout.Constraints.this.getHeightFromBaseLine(param2Int);
/*      */           }
/*      */           
/*      */           protected int inv(int param2Int) {
/*  492 */             return SpringLayout.Constraints.this.getBaselineFromHeight(param2Int);
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     private boolean defined(List param1List, String param1String1, String param1String2) {
/*  498 */       return (param1List.contains(param1String1) && param1List.contains(param1String2));
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
/*      */     public void setX(Spring param1Spring) {
/*  513 */       this.x = param1Spring;
/*  514 */       pushConstraint("West", param1Spring, true);
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
/*      */     public Spring getX() {
/*  527 */       if (this.x == null) {
/*  528 */         if (defined(this.horizontalHistory, "East", "Width")) {
/*  529 */           this.x = difference(this.east, this.width);
/*  530 */         } else if (defined(this.horizontalHistory, "HorizontalCenter", "Width")) {
/*  531 */           this.x = difference(this.horizontalCenter, scale(this.width, 0.5F));
/*  532 */         } else if (defined(this.horizontalHistory, "HorizontalCenter", "East")) {
/*  533 */           this.x = difference(scale(this.horizontalCenter, 2.0F), this.east);
/*      */         } 
/*      */       }
/*  536 */       return this.x;
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
/*      */     public void setY(Spring param1Spring) {
/*  551 */       this.y = param1Spring;
/*  552 */       pushConstraint("North", param1Spring, false);
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
/*      */     public Spring getY() {
/*  565 */       if (this.y == null) {
/*  566 */         if (defined(this.verticalHistory, "South", "Height")) {
/*  567 */           this.y = difference(this.south, this.height);
/*  568 */         } else if (defined(this.verticalHistory, "VerticalCenter", "Height")) {
/*  569 */           this.y = difference(this.verticalCenter, scale(this.height, 0.5F));
/*  570 */         } else if (defined(this.verticalHistory, "VerticalCenter", "South")) {
/*  571 */           this.y = difference(scale(this.verticalCenter, 2.0F), this.south);
/*  572 */         } else if (defined(this.verticalHistory, "Baseline", "Height")) {
/*  573 */           this.y = difference(this.baseline, heightToRelativeBaseline(this.height));
/*  574 */         } else if (defined(this.verticalHistory, "Baseline", "South")) {
/*  575 */           this.y = scale(difference(this.baseline, heightToRelativeBaseline(this.south)), 2.0F);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  582 */       return this.y;
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
/*      */     public void setWidth(Spring param1Spring) {
/*  596 */       this.width = param1Spring;
/*  597 */       pushConstraint("Width", param1Spring, true);
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
/*      */     public Spring getWidth() {
/*  609 */       if (this.width == null) {
/*  610 */         if (this.horizontalHistory.contains("East")) {
/*  611 */           this.width = difference(this.east, getX());
/*  612 */         } else if (this.horizontalHistory.contains("HorizontalCenter")) {
/*  613 */           this.width = scale(difference(this.horizontalCenter, getX()), 2.0F);
/*      */         } 
/*      */       }
/*  616 */       return this.width;
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
/*      */     public void setHeight(Spring param1Spring) {
/*  630 */       this.height = param1Spring;
/*  631 */       pushConstraint("Height", param1Spring, false);
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
/*      */     public Spring getHeight() {
/*  643 */       if (this.height == null) {
/*  644 */         if (this.verticalHistory.contains("South")) {
/*  645 */           this.height = difference(this.south, getY());
/*  646 */         } else if (this.verticalHistory.contains("VerticalCenter")) {
/*  647 */           this.height = scale(difference(this.verticalCenter, getY()), 2.0F);
/*  648 */         } else if (this.verticalHistory.contains("Baseline")) {
/*  649 */           this.height = relativeBaselineToHeight(difference(this.baseline, getY()));
/*      */         } 
/*      */       }
/*  652 */       return this.height;
/*      */     }
/*      */     
/*      */     private void setEast(Spring param1Spring) {
/*  656 */       this.east = param1Spring;
/*  657 */       pushConstraint("East", param1Spring, true);
/*      */     }
/*      */     
/*      */     private Spring getEast() {
/*  661 */       if (this.east == null) {
/*  662 */         this.east = sum(getX(), getWidth());
/*      */       }
/*  664 */       return this.east;
/*      */     }
/*      */     
/*      */     private void setSouth(Spring param1Spring) {
/*  668 */       this.south = param1Spring;
/*  669 */       pushConstraint("South", param1Spring, false);
/*      */     }
/*      */     
/*      */     private Spring getSouth() {
/*  673 */       if (this.south == null) {
/*  674 */         this.south = sum(getY(), getHeight());
/*      */       }
/*  676 */       return this.south;
/*      */     }
/*      */     
/*      */     private Spring getHorizontalCenter() {
/*  680 */       if (this.horizontalCenter == null) {
/*  681 */         this.horizontalCenter = sum(getX(), scale(getWidth(), 0.5F));
/*      */       }
/*  683 */       return this.horizontalCenter;
/*      */     }
/*      */     
/*      */     private void setHorizontalCenter(Spring param1Spring) {
/*  687 */       this.horizontalCenter = param1Spring;
/*  688 */       pushConstraint("HorizontalCenter", param1Spring, true);
/*      */     }
/*      */     
/*      */     private Spring getVerticalCenter() {
/*  692 */       if (this.verticalCenter == null) {
/*  693 */         this.verticalCenter = sum(getY(), scale(getHeight(), 0.5F));
/*      */       }
/*  695 */       return this.verticalCenter;
/*      */     }
/*      */     
/*      */     private void setVerticalCenter(Spring param1Spring) {
/*  699 */       this.verticalCenter = param1Spring;
/*  700 */       pushConstraint("VerticalCenter", param1Spring, false);
/*      */     }
/*      */     
/*      */     private Spring getBaseline() {
/*  704 */       if (this.baseline == null) {
/*  705 */         this.baseline = sum(getY(), heightToRelativeBaseline(getHeight()));
/*      */       }
/*  707 */       return this.baseline;
/*      */     }
/*      */     
/*      */     private void setBaseline(Spring param1Spring) {
/*  711 */       this.baseline = param1Spring;
/*  712 */       pushConstraint("Baseline", param1Spring, false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setConstraint(String param1String, Spring param1Spring) {
/*  752 */       param1String = param1String.intern();
/*  753 */       if (param1String == "West") {
/*  754 */         setX(param1Spring);
/*  755 */       } else if (param1String == "North") {
/*  756 */         setY(param1Spring);
/*  757 */       } else if (param1String == "East") {
/*  758 */         setEast(param1Spring);
/*  759 */       } else if (param1String == "South") {
/*  760 */         setSouth(param1Spring);
/*  761 */       } else if (param1String == "HorizontalCenter") {
/*  762 */         setHorizontalCenter(param1Spring);
/*  763 */       } else if (param1String == "Width") {
/*  764 */         setWidth(param1Spring);
/*  765 */       } else if (param1String == "Height") {
/*  766 */         setHeight(param1Spring);
/*  767 */       } else if (param1String == "VerticalCenter") {
/*  768 */         setVerticalCenter(param1Spring);
/*  769 */       } else if (param1String == "Baseline") {
/*  770 */         setBaseline(param1Spring);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spring getConstraint(String param1String) {
/*  811 */       param1String = param1String.intern();
/*  812 */       return (param1String == "West") ? getX() : ((param1String == "North") ? 
/*  813 */         getY() : ((param1String == "East") ? 
/*  814 */         getEast() : ((param1String == "South") ? 
/*  815 */         getSouth() : ((param1String == "Width") ? 
/*  816 */         getWidth() : ((param1String == "Height") ? 
/*  817 */         getHeight() : ((param1String == "HorizontalCenter") ? 
/*  818 */         getHorizontalCenter() : ((param1String == "VerticalCenter") ? 
/*  819 */         getVerticalCenter() : ((param1String == "Baseline") ? 
/*  820 */         getBaseline() : null))))))));
/*      */     }
/*      */ 
/*      */     
/*      */     void reset() {
/*  825 */       Spring[] arrayOfSpring = { this.x, this.y, this.width, this.height, this.east, this.south, this.horizontalCenter, this.verticalCenter, this.baseline };
/*      */       
/*  827 */       for (Spring spring : arrayOfSpring) {
/*  828 */         if (spring != null)
/*  829 */           spring.setValue(-2147483648); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public Constraints() {} }
/*      */   
/*      */   private static class SpringProxy extends Spring {
/*      */     private String edgeName;
/*      */     private Component c;
/*      */     private SpringLayout l;
/*      */     
/*      */     public SpringProxy(String param1String, Component param1Component, SpringLayout param1SpringLayout) {
/*  841 */       this.edgeName = param1String;
/*  842 */       this.c = param1Component;
/*  843 */       this.l = param1SpringLayout;
/*      */     }
/*      */     
/*      */     private Spring getConstraint() {
/*  847 */       return this.l.getConstraints(this.c).getConstraint(this.edgeName);
/*      */     }
/*      */     
/*      */     public int getMinimumValue() {
/*  851 */       return getConstraint().getMinimumValue();
/*      */     }
/*      */     
/*      */     public int getPreferredValue() {
/*  855 */       return getConstraint().getPreferredValue();
/*      */     }
/*      */     
/*      */     public int getMaximumValue() {
/*  859 */       return getConstraint().getMaximumValue();
/*      */     }
/*      */     
/*      */     public int getValue() {
/*  863 */       return getConstraint().getValue();
/*      */     }
/*      */     
/*      */     public void setValue(int param1Int) {
/*  867 */       getConstraint().setValue(param1Int);
/*      */     }
/*      */     
/*      */     boolean isCyclic(SpringLayout param1SpringLayout) {
/*  871 */       return param1SpringLayout.isCyclic(getConstraint());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  875 */       return "SpringProxy for " + this.edgeName + " edge of " + this.c.getName() + ".";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetCyclicStatuses() {
/*  885 */     this.cyclicSprings = new HashSet<>();
/*  886 */     this.acyclicSprings = new HashSet<>();
/*      */   }
/*      */   
/*      */   private void setParent(Container paramContainer) {
/*  890 */     resetCyclicStatuses();
/*  891 */     Constraints constraints = getConstraints(paramContainer);
/*      */     
/*  893 */     constraints.setX(Spring.constant(0));
/*  894 */     constraints.setY(Spring.constant(0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  904 */     Spring spring1 = constraints.getWidth();
/*  905 */     if (spring1 instanceof Spring.WidthSpring && ((Spring.WidthSpring)spring1).c == paramContainer) {
/*  906 */       constraints.setWidth(Spring.constant(0, 0, 2147483647));
/*      */     }
/*  908 */     Spring spring2 = constraints.getHeight();
/*  909 */     if (spring2 instanceof Spring.HeightSpring && ((Spring.HeightSpring)spring2).c == paramContainer) {
/*  910 */       constraints.setHeight(Spring.constant(0, 0, 2147483647));
/*      */     }
/*      */   }
/*      */   
/*      */   boolean isCyclic(Spring paramSpring) {
/*  915 */     if (paramSpring == null) {
/*  916 */       return false;
/*      */     }
/*  918 */     if (this.cyclicSprings.contains(paramSpring)) {
/*  919 */       return true;
/*      */     }
/*  921 */     if (this.acyclicSprings.contains(paramSpring)) {
/*  922 */       return false;
/*      */     }
/*  924 */     this.cyclicSprings.add(paramSpring);
/*  925 */     boolean bool = paramSpring.isCyclic(this);
/*  926 */     if (!bool) {
/*  927 */       this.acyclicSprings.add(paramSpring);
/*  928 */       this.cyclicSprings.remove(paramSpring);
/*      */     } else {
/*      */       
/*  931 */       System.err.println(paramSpring + " is cyclic. ");
/*      */     } 
/*  933 */     return bool;
/*      */   }
/*      */   
/*      */   private Spring abandonCycles(Spring paramSpring) {
/*  937 */     return isCyclic(paramSpring) ? this.cyclicReference : paramSpring;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeLayoutComponent(Component paramComponent) {
/*  955 */     this.componentConstraints.remove(paramComponent);
/*      */   }
/*      */   
/*      */   private static Dimension addInsets(int paramInt1, int paramInt2, Container paramContainer) {
/*  959 */     Insets insets = paramContainer.getInsets();
/*  960 */     return new Dimension(paramInt1 + insets.left + insets.right, paramInt2 + insets.top + insets.bottom);
/*      */   }
/*      */   
/*      */   public Dimension minimumLayoutSize(Container paramContainer) {
/*  964 */     setParent(paramContainer);
/*  965 */     Constraints constraints = getConstraints(paramContainer);
/*  966 */     return addInsets(abandonCycles(constraints.getWidth()).getMinimumValue(), 
/*  967 */         abandonCycles(constraints.getHeight()).getMinimumValue(), paramContainer);
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension preferredLayoutSize(Container paramContainer) {
/*  972 */     setParent(paramContainer);
/*  973 */     Constraints constraints = getConstraints(paramContainer);
/*  974 */     return addInsets(abandonCycles(constraints.getWidth()).getPreferredValue(), 
/*  975 */         abandonCycles(constraints.getHeight()).getPreferredValue(), paramContainer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension maximumLayoutSize(Container paramContainer) {
/*  982 */     setParent(paramContainer);
/*  983 */     Constraints constraints = getConstraints(paramContainer);
/*  984 */     return addInsets(abandonCycles(constraints.getWidth()).getMaximumValue(), 
/*  985 */         abandonCycles(constraints.getHeight()).getMaximumValue(), paramContainer);
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
/*      */   public void addLayoutComponent(Component paramComponent, Object paramObject) {
/* 1000 */     if (paramObject instanceof Constraints) {
/* 1001 */       putConstraints(paramComponent, (Constraints)paramObject);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getLayoutAlignmentX(Container paramContainer) {
/* 1009 */     return 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getLayoutAlignmentY(Container paramContainer) {
/* 1016 */     return 0.5F;
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
/*      */   public void invalidateLayout(Container paramContainer) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putConstraint(String paramString1, Component paramComponent1, int paramInt, String paramString2, Component paramComponent2) {
/* 1041 */     putConstraint(paramString1, paramComponent1, Spring.constant(paramInt), paramString2, paramComponent2);
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
/*      */   public void putConstraint(String paramString1, Component paramComponent1, Spring paramSpring, String paramString2, Component paramComponent2) {
/* 1075 */     putConstraint(paramString1, paramComponent1, Spring.sum(paramSpring, getConstraint(paramString2, paramComponent2)));
/*      */   }
/*      */   
/*      */   private void putConstraint(String paramString, Component paramComponent, Spring paramSpring) {
/* 1079 */     if (paramSpring != null) {
/* 1080 */       getConstraints(paramComponent).setConstraint(paramString, paramSpring);
/*      */     }
/*      */   }
/*      */   
/*      */   private Constraints applyDefaults(Component paramComponent, Constraints paramConstraints) {
/* 1085 */     if (paramConstraints == null) {
/* 1086 */       paramConstraints = new Constraints();
/*      */     }
/* 1088 */     if (paramConstraints.c == null) {
/* 1089 */       paramConstraints.c = paramComponent;
/*      */     }
/* 1091 */     if (paramConstraints.horizontalHistory.size() < 2) {
/* 1092 */       applyDefaults(paramConstraints, "West", Spring.constant(0), "Width", 
/* 1093 */           Spring.width(paramComponent), paramConstraints.horizontalHistory);
/*      */     }
/* 1095 */     if (paramConstraints.verticalHistory.size() < 2) {
/* 1096 */       applyDefaults(paramConstraints, "North", Spring.constant(0), "Height", 
/* 1097 */           Spring.height(paramComponent), paramConstraints.verticalHistory);
/*      */     }
/* 1099 */     return paramConstraints;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void applyDefaults(Constraints paramConstraints, String paramString1, Spring paramSpring1, String paramString2, Spring paramSpring2, List<String> paramList) {
/* 1105 */     if (paramList.size() == 0) {
/* 1106 */       paramConstraints.setConstraint(paramString1, paramSpring1);
/* 1107 */       paramConstraints.setConstraint(paramString2, paramSpring2);
/*      */     }
/*      */     else {
/*      */       
/* 1111 */       if (paramConstraints.getConstraint(paramString2) == null) {
/* 1112 */         paramConstraints.setConstraint(paramString2, paramSpring2);
/*      */       } else {
/*      */         
/* 1115 */         paramConstraints.setConstraint(paramString1, paramSpring1);
/*      */       } 
/*      */       
/* 1118 */       Collections.rotate(paramList, 1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void putConstraints(Component paramComponent, Constraints paramConstraints) {
/* 1123 */     this.componentConstraints.put(paramComponent, applyDefaults(paramComponent, paramConstraints));
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
/*      */   public Constraints getConstraints(Component paramComponent) {
/* 1149 */     Constraints constraints = this.componentConstraints.get(paramComponent);
/* 1150 */     if (constraints == null) {
/* 1151 */       if (paramComponent instanceof JComponent) {
/* 1152 */         Object object = ((JComponent)paramComponent).getClientProperty(SpringLayout.class);
/* 1153 */         if (object instanceof Constraints) {
/* 1154 */           return applyDefaults(paramComponent, (Constraints)object);
/*      */         }
/*      */       } 
/* 1157 */       constraints = new Constraints();
/* 1158 */       putConstraints(paramComponent, constraints);
/*      */     } 
/* 1160 */     return constraints;
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
/*      */   public Spring getConstraint(String paramString, Component paramComponent) {
/* 1201 */     paramString = paramString.intern();
/* 1202 */     return new SpringProxy(paramString, paramComponent, this);
/*      */   }
/*      */   
/*      */   public void layoutContainer(Container paramContainer) {
/* 1206 */     setParent(paramContainer);
/*      */     
/* 1208 */     int i = paramContainer.getComponentCount();
/* 1209 */     getConstraints(paramContainer).reset();
/* 1210 */     for (byte b1 = 0; b1 < i; b1++) {
/* 1211 */       getConstraints(paramContainer.getComponent(b1)).reset();
/*      */     }
/*      */     
/* 1214 */     Insets insets = paramContainer.getInsets();
/* 1215 */     Constraints constraints = getConstraints(paramContainer);
/* 1216 */     abandonCycles(constraints.getX()).setValue(0);
/* 1217 */     abandonCycles(constraints.getY()).setValue(0);
/* 1218 */     abandonCycles(constraints.getWidth()).setValue(paramContainer.getWidth() - insets.left - insets.right);
/*      */     
/* 1220 */     abandonCycles(constraints.getHeight()).setValue(paramContainer.getHeight() - insets.top - insets.bottom);
/*      */ 
/*      */     
/* 1223 */     for (byte b2 = 0; b2 < i; b2++) {
/* 1224 */       Component component = paramContainer.getComponent(b2);
/* 1225 */       Constraints constraints1 = getConstraints(component);
/* 1226 */       int j = abandonCycles(constraints1.getX()).getValue();
/* 1227 */       int k = abandonCycles(constraints1.getY()).getValue();
/* 1228 */       int m = abandonCycles(constraints1.getWidth()).getValue();
/* 1229 */       int n = abandonCycles(constraints1.getHeight()).getValue();
/* 1230 */       component.setBounds(insets.left + j, insets.top + k, m, n);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/SpringLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */