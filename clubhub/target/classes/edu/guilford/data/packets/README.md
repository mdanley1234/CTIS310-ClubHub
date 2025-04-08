# Packets

Packets facilitate data handling between the application and SB. All packets extend the base DataPacket class which contains minimum functionality for effective use with SB.

## Packet Class Requirements

- **Constructors**:

    - Build Constructor: The build constructor is required to build a full instance of the packet for data upstream methods

    - Retrieve Constructor: The retrieve constructor is required to fetch a full instance of the packet via data downstream methods

    - Additional Constructors: Additional constructors may be added for additional functionality

- **Upstream Methods**: Most upstream functionality is built inherently into the DataPacket base class with updatePacket, upsertPacket (TBD), and insertPacket (TBD)

## Special Child DataPackets

- **ProfilePacket**: Specialized for SB profile information with self-contained Signup and Login methods

- **ClubPacket**: Specialized for SB club information

- **BundlePacket**: Specialized for SB bundle information