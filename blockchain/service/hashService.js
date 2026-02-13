const crypto = require("crypto");

function hashJSON(obj) {
  const json = JSON.stringify(obj);
  return "0x" + crypto.createHash("sha256").update(json).digest("hex");
}

module.exports = { hashJSON };
