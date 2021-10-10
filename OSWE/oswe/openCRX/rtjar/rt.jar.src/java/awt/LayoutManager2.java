package java.awt;

public interface LayoutManager2 extends LayoutManager {
  void addLayoutComponent(Component paramComponent, Object paramObject);
  
  Dimension maximumLayoutSize(Container paramContainer);
  
  float getLayoutAlignmentX(Container paramContainer);
  
  float getLayoutAlignmentY(Container paramContainer);
  
  void invalidateLayout(Container paramContainer);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/LayoutManager2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */