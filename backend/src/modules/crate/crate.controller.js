const Crate = require("./crate.model");
const Batch = require("../batch/batch.model");
exports.addCrate = async (req, res) => {
  try {
    const { qr_code, condition } = req.body;
    const { batchId } = req.params;
    const farmer_uid = req.user.uid;

    if (!qr_code || !condition) {
      return res.status(400).json({
        message: "qr_code and condition are required",
      });
    }

    const batch = await Batch.findOne({ batch_uid: batchId });

    if (!batch) {
      return res.status(404).json({ message: "Batch not found" });
    }

    if (batch.farmer_uid !== farmer_uid) {
      return res.status(403).json({
        message: "Not authorized",
      });
    }

    const existingCrate = await Crate.findOne({ qr_code });
    if (existingCrate) {
      return res.status(400).json({
        message: "QR already exists",
      });
    }

    const crate = await Crate.create({
      batch_id: batch._id,
      qr_code,
      condition,
    });

    res.status(201).json({
      message: "Crate added",
      crate,
    });

  } catch (err) {
    res.status(500).json({
      message: "Internal error",
      error: err.message,
    });
  }
};
exports.getCratesByBatch = async (req, res) => {
  try {
    const { batchId } = req.params;
    const farmer_uid = req.user.uid;

    // Find batch first (ownership validation)
    const batch = await Batch.findOne({ batch_uid: batchId });

    if (!batch) {
      return res.status(404).json({
        message: "Batch not found",
      });
    }

    if (batch.farmer_uid !== farmer_uid) {
      return res.status(403).json({
        message: "Not authorized",
      });
    }

    const crates = await Crate.find({ batch_id: batch._id });

    res.status(200).json({
      batch_uid: batch.batch_uid,
      total: crates.length,
      crates,
    });

  } catch (err) {
    res.status(500).json({
      message: "Internal error",
      error: err.message,
    });
  }
};
