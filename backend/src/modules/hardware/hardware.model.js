const mongoose = require("mongoose");

const hardwareSchema = new mongoose.Schema({
  hardware_id: {
    type: String,
    required: true,
    unique: true,
    index: true,
  },
  device_key: {
    type: String,
    required: true,
  },
  status: {
    type: String,
    enum: ["ACTIVE", "INACTIVE"],
    default: "ACTIVE",
  },
});

module.exports =
  mongoose.models.Hardware || mongoose.model("Hardware", hardwareSchema);
