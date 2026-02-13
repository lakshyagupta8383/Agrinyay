const Crate = require("./crate.model");
const Batch = require("../batch/batch.model");

exports.addCrate = async (req, res) => {
  const { farmer_id, crop_type, qr_code } = req.body;
  const { batchId } = req.params;

  if (!farmer_id || !crop_type || !qr_code) {
    return res.status(400).json({ message: "Missing required fields" });
  }

  if (req.user.uid !== farmer_id) {
    return res.status(403).json({ message: "Unauthorized farmer" });
  }

  const batch = await Batch.findById(batchId);

  if (!batch) {
    return res.status(404).json({ message: "Batch not found" });
  }

  if (batch.farmer_uid !== farmer_id) {
    return res.status(403).json({ message: "Not your batch" });
  }

  const crate = await Crate.create({
    batch_id: batchId,
    crop_type,
    qr_code,
  });

  batch.crate_ids.push(crate._id);
  await batch.save();

  res.status(201).json({
    message: "Crate added to batch",
    crate_id: crate._id,
  });
};
