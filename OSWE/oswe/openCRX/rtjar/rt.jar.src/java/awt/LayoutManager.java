package java.awt;

public interface LayoutManager {
  void addLayoutComponent(String paramString, Component paramComponent);
  
  void removeLayoutComponent(Component paramComponent);
  
  Dimension preferredLayoutSize(Container paramContainer);
  
  Dimension minimumLayoutSize(Container paramContainer);
  
  void layoutContainer(Container paramContainer);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/LayoutManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */