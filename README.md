## TODO List

### Update the PacketFactory class

- Add in constructor that takes in 'PacketDescriptor' parameter.
- Make local 'PacketDescriptor' field protected and update accessor.

### Decide how to handle packets after they are parsed from the client

- Possibly queue packets on the client for future use.
- Possibly handle packets immeidiately. 

### Figure out how to differentiate service reactors or even if service reactors are differentiated

- Possibly register selection key to new selector. 
- Possibly loosely couple the parsing of packets by queuing packets on a client.

#### Notes:

I like idea number two, it allows for external explicit control of how a client functions without manipulating a client.

### Define registration of alternate service and what service domain a client is registered under

- Possibly add 'register' method to each service or even add a 'ClientDomain' where clients are allowed to be registered.

Notes:

I like the idea of adding in a 'ClientDomain' class considering I do not want to add in extra functionality to the 'Service'
class. It seems like the 'Service' class will be saturated more and more by features that may not even be used.

### Add in Disconnect listener

- Allow addition/removal of listeners for when a client disconnects.

