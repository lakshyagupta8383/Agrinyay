require("dotenv").config();

const { hashJSON } = require("./service/hashService");
const chain = require("./service/blockchainService");

async function run() {
  const originData = {
    farmer: "F123",
    crop: "Rice",
    time: Date.now()
  };

  const qualityData = {
    score: 87
  };

  const originHash = hashJSON(originData);
  const qualityHash = hashJSON(qualityData);

  console.log("Origin hash:", originHash);
  console.log("Quality hash:", qualityHash);

  await chain.recordOrigin("B001", originHash);
  await chain.recordQuality("B001", qualityHash);

  const result = await chain.verify("B001");
  console.log("Blockchain record:", result);
}

run();
