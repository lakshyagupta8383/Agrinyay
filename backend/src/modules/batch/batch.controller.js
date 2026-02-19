const Batch = require("./batch.model");
const Hardware = require("../hardware/hardware.model");

exports.createBatch = async (req, res) => {
  try {
    const batch_uid = `BATCH_${Date.now()}`;
    console.log("Creating Batch:", req.body);
    const hardwareDoc = await Hardware.findOne({ hardware_id: hardware });
    if (!hardwareDoc) {
      return res.status(404).json({
        success: false,
        message: "Hardware not registered",
      });
    }
    if (hardwareDoc.status !== "ACTIVE") {
      return res.status(403).json({
        success: false,
        message: "Hardware is inactive",
    });
}
    const batch = await Batch.create({
      farmer_uid: req.user.uid,  
      batch_uid,
      crop_type: req.body.crop_type,
      crop_quantity: req.body.crop_quantity,
      hardware: req.body.hardware
    });

    res.status(201).json({
      success: true,
      data: batch
    });
    
  } catch (err) {
    console.error("Create Error:", err);
    res.status(500).json({ message: err.message });
  }
};

exports.getBatches = async (req, res) => {
  try {
    const batches = await Batch.find({ farmer_uid: req.user.uid });

    res.status(200).json({
      success: true,
      data: batches 
    });

  } catch (err) {
    console.error("Fetch Error:", err);
    res.status(500).json({ 
      success: false, 
      message: "Server Error: Could not fetch batches" 
    });
  }
};