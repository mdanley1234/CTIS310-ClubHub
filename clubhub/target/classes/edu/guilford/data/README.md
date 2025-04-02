# Data Objects

## Packets
The packet objects are used to store and process individual rows of information between SB and the UI. 

### Profile Packet
The Profile packet contains all information about the current user except the UUID (SupabaseAuth) and password (Hash in SB).

- **Email**: The email address associated with the user.
- **First Name**: The user's first name.
- **Last Name**: The user's last name.
- **Date of Birth**: The user's date of birth.
- **Profile Picture URL**: A link to the user's profile picture.
- **Account Creation Date**: The date the account was created.
- **Last Login**: The timestamp of the user's last login.
- **Roles**: A list of roles or permissions assigned to the user.

### Club Packet
The Club packet contains all information about a club.

### Content Packet
The Content packet contains information relevant to a user's membership in a club. Content packets can contain a wide range of information and across multiple tables in SB. Each content packet is marked via a bundle id.

## Bundles
The Content bundle contains a list of Content packets. The bundle table contains a bundle id for each club that a user is part of. It also contains additional membership information, most importantly their role in the club which indicates which content packets to fetch. 