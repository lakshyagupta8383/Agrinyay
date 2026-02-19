const Sensor = require("./sensor.model");
const Batch = require("../batch/batch.model");

exports.ingestSensorData = async (req, res) => {
  try {
    const { hardware_id, temperature, humidity } = req.body;

    if (!hardware_id || temperature == null || humidity == null) {
      return res.status(400).json({
        message: "hardware_id, temperature and humidity are required",
      });
    }

    // Find active batch mapped to this hardware
    const batch = await Batch.findOne({
      hardware: hardware_id,
      status: "ACTIVE",
    });

    if (!batch) {
      return res.status(404).json({
        message: "No active batch mapped to this hardware",
      });
    }

    await Sensor.create({
      hardware_id,
      batch_id: batch._id,
      temperature,
      humidity,
    });

    res.status(201).json({ message: "Sensor data stored" });

  } catch (err) {
    res.status(500).json({
      message: "Internal error",
      error: err.message,
    });
  }
};
