const { ethers } = require("ethers");
const fs = require("fs");
const path = require("path");

const artifact = JSON.parse(
  fs.readFileSync(
    path.join(__dirname, "../artifacts/contracts/AgrinyayHashLedger.sol/AgrinyayHashLedger.json")
  )
);

const provider = new ethers.providers.JsonRpcProvider("http://127.0.0.1:8545");
const wallet = provider.getSigner(0);

const contractAddress = process.env.CONTRACT_ADDRESS;
const contract = new ethers.Contract(contractAddress, artifact.abi, wallet);

async function recordOrigin(batchId, hash) {
  const tx = await contract.recordOrigin(batchId, hash);
  await tx.wait();
  return tx.hash;
}

async function recordQuality(batchId, hash) {
  const tx = await contract.recordQuality(batchId, hash);
  await tx.wait();
  return tx.hash;
}

async function verify(batchId) {
  return await contract.verify(batchId);
}

module.exports = {
  recordOrigin,
  recordQuality,
  verify,
};
