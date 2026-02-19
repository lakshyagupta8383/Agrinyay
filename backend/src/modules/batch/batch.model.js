const mongoose = require("mongoose");

const batchSchema = new mongoose.Schema(
  {
    batch_uid: {
      type: String,
      required: true,
      unique: true,
      index: true,
    },

    farmer_uid: {
      type: String,
      required: true,
      index: true,
    },

    crop_type: {
      type: String,
      default: "BANANA",
    },

    crop_quantity: {
      type: Number,
    },

    hardware: {
      type: String,
      unique: true, 
      sparse: true,   
      index: true,
    },

    crate_ids: {
      type: [mongoose.Schema.Types.ObjectId],
      ref: "Crate",
      default: [],
    },

    total_crates: {
      type: Number,
      default: 0,
    },

    status: {
      type: String,
      enum: ["ACTIVE", "CLOSED"],
      default: "ACTIVE",
    },
  },
  { timestamps: true }
);

module.exports =
  mongoose.models.Batch || mongoose.model("Batch", batchSchema);
