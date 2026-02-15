const Crate = require("./crate.model");
const Batch = require("../batch/batch.model");

exports.addCrate = async (req, res) => {
  try {
    const { qr_code, condition } = req.body;
    const { batchId } = req.params;

    const farmer_uid = req.user.uid; // always trust token, not frontend

    // Validate required fields
    if (!qr_code || !condition) {
      return res.status(400).json({
        message: "qr_code and condition are required",
      });
    }

    //Check if batch exists
    const batch = await Batch.findById(batchId);
    if (!batch) {
      return res.status(404).json({
        message: "Batch not found",
      });
    }

    //Verify ownership (Farmer can only add crate to their batch)
    if (batch.farmer_uid !== farmer_uid) {
      return res.status(403).json({
        message: "You are not authorized to add crates to this batch",
      });
    }

    //Check QR uniqueness manually (better error message)
    const existingCrate = await Crate.findOne({ qr_code });
    if (existingCrate) {
      return res.status(400).json({
        message: "QR code already exists",
      });
    }
    //Create crate
    const crate = await Crate.create({
      batch_id: batchId,
      qr_code,
      condition,
    });

    //Push crate reference into batch
    batch.crate_ids.push(crate._id);
    await batch.save();

    res.status(201).json({
      message: "Crate added successfully",
      crate_id: crate._id,
    });

  } catch (error) {
    res.status(500).json({
      message: "Internal server error",
      error: error.message,
    });
  }
};
