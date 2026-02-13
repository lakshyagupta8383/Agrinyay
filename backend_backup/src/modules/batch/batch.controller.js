const Batch = require("./batch.model");

exports.createBatch = async (req, res) => {
  const { farmer_id } = req.body;

  if (!farmer_id) {
    return res.status(400).json({ message: "farmer_id is required" });
  }

  if (req.user.uid !== farmer_id) {
    return res.status(403).json({ message: "Unauthorized farmer" });
  }

  const batch = await Batch.create({
    farmer_uid: farmer_id,
  });

  res.status(201).json({
    message: "Batch created",
    batch_id: batch._id,
  });
};
