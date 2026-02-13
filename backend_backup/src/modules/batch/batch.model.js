const mongoose = require("mongoose");

const batchSchema = new mongoose.Schema({
  farmer_uid: {
    type: String,
    required: true,
  },
  batch_uid: {
    type: String,
    required: true
  },
  crop_type: {
    type: String,
    default: "BANANA",
  },
  crop_quantity: {
    type: Number
  }
}, { timestamps: true });

module.exports = mongoose.model("Batch", batchSchema);
