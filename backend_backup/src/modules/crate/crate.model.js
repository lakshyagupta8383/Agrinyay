const mongoose = require("mongoose");

const crateSchema = new mongoose.Schema({
  batch_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Batch",
    required: true,
  },
  condition: {
    type: String,
    enum: ['RAW','RIPPED','SEMI-RIPPED']
  },
  qr_code: {
    type: String,
    required: true,
    unique: true,
  },
  quality_score: Number,
  predicted_price: Number,
  status: {
    type: String,
    enum: ["UNSOLD", "SOLD"],
    default: "UNSOLD",
  },
}, { timestamps: true });

module.exports = mongoose.model("Crate", crateSchema);
