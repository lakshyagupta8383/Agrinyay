const mongoose = require("mongoose");

const crateSchema = new mongoose.Schema(
  {
    batch_id: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Batch",
      required: true,
      index: true,
    },

    qr_code: {
      type: String,
      required: true,
      unique: true,
      index: true,
    },

    condition: {
      type: String,
      enum: ["GOOD", "DAMAGED", "SPOILED"],
      required: true,
    },
  },
  { timestamps: true }
);

module.exports =
  mongoose.models.Crate || mongoose.model("Crate", crateSchema);
