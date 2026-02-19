const mongoose = require("mongoose");

const sensorSchema = new mongoose.Schema(
  {
    hardware_id: {
      type: String,
      required: true,
      index: true,
    },

    batch_id: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Batch",
      required: true,
      index: true,
    },

    temperature: {
      type: Number,
      required: true,
    },

    humidity: {
      type: Number,
      required: true,
    },
  },
  { timestamps: true }
);

// compound index for fast time queries
sensorSchema.index({ hardware_id: 1, createdAt: -1 });

module.exports =
  mongoose.models.Sensor || mongoose.model("Sensor", sensorSchema);
