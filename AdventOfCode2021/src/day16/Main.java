package day16;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

	static int index;
	static String bits;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day16/input.txt"));
		index = 0;
		String line = br.readLine();
		bits = line
				.chars()
				.mapToObj(c -> bitString(String.valueOf((char) c)))
				.collect(Collectors.joining());
		Packet root = parsePacket();
		System.out.println(root.versionSum());
		System.out.println(root.value());
	}
	
	static Packet parsePacket() {
		String versionString = take(3), typeIDString = take(3);
		int version = Integer.parseInt(versionString, 2), typeID = Integer.parseInt(typeIDString, 2);
		if(typeID == 4) {
			String block, binStr = "";
			do {
				block = take(5);
				binStr += block.substring(1);
			} while(block.startsWith("1"));
			long value = Long.parseLong(binStr, 2);
			return new LiteralPacket(value, version);
		}
		else {
			String ltString = take(1);
			List<Packet> packets = new ArrayList<>();
			if(ltString.equals("0")) {
				int length = Integer.parseInt(take(15), 2);
				int startIndex = index;
				int destIndex = startIndex + length;
				while(index != destIndex)
					packets.add(parsePacket());
			}
			else {
				int packetCount = Integer.parseInt(take(11), 2);
				for(int i = 0; i < packetCount; i++)
					packets.add(parsePacket());
			}
			return new OperatorPacket(version, typeID, packets);
		}
	}
	
	static String take(int n) {
		String sub = bits.substring(index, index + n);
		index += n;
		return sub;
	}
	
	static String bitString(String singleHexDigit) {
		if(singleHexDigit.length() != 1)
			throw new IllegalArgumentException(String.format("singleHexDigit=%s", singleHexDigit));
		String binary = Integer.toString(Integer.parseInt(singleHexDigit, 16), 2);
		return "0".repeat(4 - binary.length()) + binary; 
	}
	
}
