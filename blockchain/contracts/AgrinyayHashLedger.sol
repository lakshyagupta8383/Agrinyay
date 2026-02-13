// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

/**
 * AGRINYAY Hash Ledger
 *
 * Stores tamper-proof hashes for:
 * 1. Batch origin certification
 * 2. Final quality certification
 *
 * Blockchain stores only hashes — not raw data.
 * Backend holds JSON.
 * Chain holds proof.
 */

contract AgrinyayHashLedger {

    struct Record {
        bytes32 originHash;
        bytes32 qualityHash;
        uint256 createdAt;
        bool exists;
    }

    mapping(string => Record) private records;

    event OriginRecorded(string batchId, bytes32 hash);
    event QualityRecorded(string batchId, bytes32 hash);

    /**
     * Record initial batch origin
     */
    function recordOrigin(string memory batchId, bytes32 hash) public {
        require(!records[batchId].exists, "Batch already exists");

        records[batchId] = Record({
            originHash: hash,
            qualityHash: bytes32(0),
            createdAt: block.timestamp,
            exists: true
        });

        emit OriginRecorded(batchId, hash);
    }

    /**
     * Record final quality certification
     */
    function recordQuality(string memory batchId, bytes32 hash) public {
        require(records[batchId].exists, "Batch does not exist");

        records[batchId].qualityHash = hash;

        emit QualityRecorded(batchId, hash);
    }

    /**
     * Verify stored blockchain data
     */
    function verify(string memory batchId)
        public view
        returns (
            bytes32 originHash,
            bytes32 qualityHash,
            uint256 timestamp
        )
    {
        require(records[batchId].exists, "Batch does not exist");

        Record memory r = records[batchId];
        return (r.originHash, r.qualityHash, r.createdAt);
    }
}
