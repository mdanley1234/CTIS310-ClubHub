# Supabase Information

The Supabase API implementation is stored in this folder. Each class contains static methods relating to some functionality of the Supabase server. The API key and URL are both stored in the authentication class along with the primary HTTPClient instance. Functionality of the other SB classes depend on an authentication token and user UUID, both of which are automatically stored and referenced via the authentication class upon a successful user login.

## Supabase Static Methods Overview

### SupabaseAuth
Handles all authentication services and manages authentication tokens, UUIDs, and secure Supabase URL & API keys.
- **signUp**: Signs up a new user.
- **login**: Logs in an existing user.

### SupabaseDelete
Handles all delete operations in the Supabase database.
- **deleteById**: Deletes a single record by ID.
- **deleteMany**: Deletes multiple records matching specified filters.

### SupabaseInsert
Handles all insert operations in the Supabase database.
- **insertOne**: Inserts a single record.
- **insertMany**: Inserts multiple records in a single request.
- **upsertOne**: Inserts or updates a record if it already exists.

### SupabaseQuery
Handles all query operations in the Supabase database.
- **queryById**: Retrieves a single record by ID.
- **queryMany**: Executes a SELECT query with optional filters, returning multiple rows.

### SupabaseUpdate
Handles all update operations in the Supabase database.
- **updateById**: Updates a single record by ID.
- **updateMany**: Updates multiple records matching specified filters.
