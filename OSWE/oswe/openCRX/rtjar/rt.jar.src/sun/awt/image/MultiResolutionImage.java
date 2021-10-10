package sun.awt.image;

import java.awt.Image;
import java.util.List;

public interface MultiResolutionImage {
  Image getResolutionVariant(int paramInt1, int paramInt2);
  
  List<Image> getResolutionVariants();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/MultiResolutionImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */