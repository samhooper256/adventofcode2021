package day16;

import java.util.List;

public final class OperatorPacket extends Packet {

	private final List<Packet> packets;
	
	public OperatorPacket(int version, int typeID, List<Packet> packets) {
		super(version, typeID);
		this.packets = packets;
	}

	@Override
	public int versionSum() {
		return version() + packets.stream().mapToInt(Packet::versionSum).sum();
	}

	
	@Override
	public long value() {
		return switch(typeID()) {
			case 0 -> packets.stream().mapToLong(Packet::value).sum();
			case 1 -> packets.stream().mapToLong(Packet::value).reduce(1, (a, b) -> a * b);
			case 2 -> packets.stream().mapToLong(Packet::value).min().getAsLong();
			case 3 -> packets.stream().mapToLong(Packet::value).max().getAsLong();
			case 5 -> packets.get(0).value() > packets.get(1).value() ? 1 : 0;
			case 6 -> packets.get(0).value() < packets.get(1).value() ? 1 : 0;
			case 7 -> packets.get(0).value() == packets.get(1).value() ? 1 : 0;
			default -> throw new UnsupportedOperationException(String.format("typeID=%d", typeID()));
		};
	}

	@Override
	public String toString() {
		return String.format("[ver=%d, ID=%d, packets=%s]", version(), typeID(), packets);
	}
	
}
