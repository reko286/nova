## Message Flow

### Incoming

```
BYTES -> PACKET -> MESSAGE -> EVENT
```

### Outgoing

```
MESSAGE -> PACKET -> BYTES
```

## TODO List


### Figure out a way to map out client to specific context object

```
Client ->
          Player
          {Update Context}
```

#### Notes:

Possible client session key to retrieve unique hash code for client. Secure random constant in Client.

### Figure out if message -> event decoder step is needed

#### Method One

Use the message -> event decoder by creating a MessageDecodedEventHandler that then turns all the raw references in the message into actual physical object reference. 


##### Benefits

Allows for a way to validate incoming server messages sent from the client.
Allows a focal point for configuring.

##### Costs

Preformance and complicates the design slightly more.

##### Method Implementation 

```
MessageDecodedEventHandler < EventHandler<MessageDecodedEvent> 

* dispatcher
* masteeventencoder

- constructor(dispatcher, eventencoder) : void

- handle(event) : void

handle(event) 
  message = event.get_message
  new_event = eventencoder.encode(message)
  dispatcher.dispatch(new_event)
end

end

MasterEventEncoder < Encoder<Event, Message>

* encoders : map<class, encoder<event, message>

- encode(message) : event

encode(message)
end

end

```
