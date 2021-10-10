package javax.xml.stream.events;

public interface EntityReference extends XMLEvent {
  EntityDeclaration getDeclaration();
  
  String getName();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/events/EntityReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */