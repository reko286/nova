require 'java'

java_import 'org.nova.event.EventHandlerChainDecorator'
java_import 'org.nova.net.event.handler.PacketParsedEventHandler'
java_import 'org.nova.net.packet.codec.PacketDecoder'

# The class used to decorate the packet parsed event handler chain.
class PacketParsedEventHandlerChainDecorator < EventHandlerChainDecorator

	# Method to decorate the chain.
	def decorate(chain)

		# Create the event decoder context and call the packet decoders proc.
		context = PacketDecoderChainContext.new chain
		packet_decoders_proc.call context
	end
end

# The class used to help register event decoders in context to the register event decoders method.
class PacketDecoderChainContext

	# Method to initialize the context.
	def initialize(chain)
		@chain = chain
	end

	# Method to register an event decoder to the event handler chain.
	def register(name, &block)

		# Create the packet parsed event handler and add it to the back of the chain.
		handler = PacketParsedEventHandler.new name, ProcPacketDecoder.new(block)
		@chain.add_to_back handler
	end
end

# The class used to wrap a proc block for decoding a packet to an event.
class ProcPacketDecoder < PacketDecoder

	# Method to initialize the decoder.
	def initialize(proc)
		@proc = proc
	end

	# Method to decode the packet parsed event to a new event.
	def decode(event)
		proc.call event.client, event.packet
	end
end

# Method to register all the event decoders to the event handler chain.
def register_packet_decoders(&block)

	# Set the event decoders proc
	packet_decoders_proc = block														
end

# Set the packet decoder chain decorator
$ctx.setPacketDecoderChainDecorator PacketParsedEventHandlerChainDecorator.new