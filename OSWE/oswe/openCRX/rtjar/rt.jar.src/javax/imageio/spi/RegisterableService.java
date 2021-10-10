package javax.imageio.spi;

public interface RegisterableService {
  void onRegistration(ServiceRegistry paramServiceRegistry, Class<?> paramClass);
  
  void onDeregistration(ServiceRegistry paramServiceRegistry, Class<?> paramClass);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/spi/RegisterableService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */