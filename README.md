# Multi-Level Cache System

## Overview
This project implements a **multi-level cache system** with configurable cache levels, capacities, and read/write latencies. The system is designed to optimize data retrieval by leveraging multiple cache levels, each with its own characteristics. The cache follows a **chain of responsibility pattern**, where each cache level delegates requests to the next level if the data is not found.

## Features
- **Multi-level caching** with configurable levels.
- **In-memory caching** with eviction policies.
- Simulated **read/write latencies** for each cache level.
- **Key-value store** with a simple eviction policy (FIFO).
- Extensible design for adding new cache types or policies.

---

## Low-Level Design (LLD)

### 1. **Core Components**
#### a. `CacheClient` (Interface)
- Defines the contract for cache operations:
  - `read(String key)`
  - `write(String key, String value)`

#### b. `MultiLevelCacheClient` (Implementation)
- Implements `CacheClient` and manages the chain of cache levels.
- Delegates `read` and `write` operations to the first cache in the chain.

#### c. `CacheChain` (Abstract Class)
- Represents a single cache level in the chain.
- Defines methods:
  - `get(String key)`
  - `set(String key, String value)`
- Supports chaining via `setNextChain`.

#### d. `InMemoryCacheChain` (Concrete Class)
- Implements `CacheChain` for in-memory caching.
- Simulates read/write latencies.
- Uses a `KVStore` for key-value storage.

#### e. `KVStore`
- A simple key-value store with a FIFO eviction policy.
- Ensures capacity constraints are respected.

---

### 2. **Factory**
#### a. `KVStoreFactory`
- Creates instances of `KVStore` with a specified capacity.

---

### 3. **Options**
#### a. `InMemoryCacheChainOptions`
- Encapsulates configuration for read/write latencies.

---

## Code Flow in `App.main`

1. **Create Cache Chains**:
   - Four cache levels (`c1`, `c2`, `c3`, `c4`) are created using `createCacheChain`.
   - Each cache level is configured with specific capacities and latencies.

2. **Link Cache Chains**:
   - The cache levels are linked in a chain using `setNextChain`.

3. **Initialize Cache Client**:
   - A `MultiLevelCacheClient` is created with the first cache chain (`c1`).

4. **Perform Write Operations**:
   - Data is written to the cache using `cacheClient.write(key, value)`.

5. **Perform Read Operations**:
   - Data is read from the cache using `cacheClient.read(key)`.
   - If a key is not found in the current cache level, the request is delegated to the next level.

6. **Simulated Latencies**:
   - Each read/write operation simulates the configured latency for the respective cache level.

---

## Example Output
```
PUT Shruty = Nice
Time taken: 1138 ms
PUT Shikhar = Good
Time taken: 1134 ms
PUT Harshit = Okay
Time taken: 1155 ms
PUT Adam = Alright
Time taken: 1142 ms
PUT Sam = Theek hai
Time taken: 1153 ms
GET Paul
Time taken: 577 ms
null
GET Shruty
Time taken: 299 ms
Nice
GET Shikhar
Time taken: 292 ms
Good
GET Shruty
Time taken: 85 ms <- This is less than first call to 'Shruty'
Nice
```

---

## How to Run
1. Clone the repository.
2. Build the project using Maven:
   ```
   mvn clean install
   ```
3. Run the `App` class:
   ```
   mvn exec:java -Dexec.mainClass="com.shikhar03stark.App"
   ```

---

## Future Enhancements
- Add support for distributed caching.
- Implement additional eviction policies (e.g., LRU, LFU).
- Add metrics for cache hit/miss rates.
- Extend support for persistent storage backends.