require 'java'

java_import 'org.nova.net.packet.codec.MessageDecoder'
java_import 'org.nova.net.packet.codec.MessageEncoder'

# Notes:
#
#         - Possibly for message decoder allow user to set the packaging instead of forcing a specific package.

# Convert a lower case underscore delimited string to camel-case.
class String
  def camelize
    gsub(/(?:^|_)(.)/) { $1.upcase }
  end
end

# The class used to help register message decoders to the message handler context.
class MessageDecoderContext

  # Method to initialize the context.
  def initialize(handler)
    @handler = handler
  end

  # Method to register an event decoder to the event handler chain.
  def register(name, &block)

    # Create the message decoder and register it to the handler.
    decoder = ProcMessageDecoder.new block
    @handler.register_decoder name, decoder
  end
end

# The class used to help register message encoders to the message handler context.
class MessageEncoderContext

  # Method to initialize the context.
  def initialize(handler)
    @handler = handler
  end

  # Method to register an event encoder to the event handler chain.
  def register(class_name, &block)

    # Get the message class from the provided class name.
    class_name = class_name.camelize.concat "Message"
    message_class = Java::JavaClass.for_name("org.nova.net.messages.".concat class_name)

    # Create the message encoder and register it to the handler.
    encoder = ProcMessageEncoder.new block
    @handler.register_encoder message_class, encoder
  end
end

# The class used to wrap a proc block for decoding a packet to a message.
class ProcMessageDecoder < MessageDecoder

  # Method to initialize the decoder.
  def initialize(proc)
    super()

    @proc = proc
  end

  # Method to decode the packet to a new message.
  def decode(packet)
    proc.call packet
  end
end

# The class used to wrap a proc block for decoding a message to a packet.
class ProcMessageEncoder < MessageEncoder

  # Method to initialize the decoder.
  def initialize(proc)
    super()

    @proc = proc
  end

  # Method to decode the message to a new packet.
  def encode(msg)
    proc.call msg
  end
end

# Method to register all the message decoders to the message handler.
def register_message_decoders(&block)

  # Create the message decoder context and register all the message decoders to the environment context message handler.
  ctx = MessageDecoderContext.new $ctx.message_handler    
  block.call ctx                      
end

# Method to register all the message encoders to the message handler.
def register_message_encoders(&block)

  # Create the message decoder context and register all the message decoders to the environment context message handler.
  ctx = MessageEncoderContext.new $ctx.message_handler    
  block.call ctx                      
end