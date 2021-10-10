package java.util.spi;

import java.util.ResourceBundle;

public interface ResourceBundleControlProvider {
  ResourceBundle.Control getControl(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/spi/ResourceBundleControlProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */