package java.awt.datatransfer;

import java.util.List;

public interface FlavorTable extends FlavorMap {
  List<String> getNativesForFlavor(DataFlavor paramDataFlavor);
  
  List<DataFlavor> getFlavorsForNative(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/datatransfer/FlavorTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */