const Hardware = require("../modules/hardware/hardware.model");

const verifyHardware = async (req, res, next) => {
  try {
    const { hardware_id } = req.body;
    const deviceKey = req.headers["x-device-key"];

    if (!hardware_id || !deviceKey) {
      return res.status(401).json({ message: "Missing hardware credentials" });
    }

    const hardware = await Hardware.findOne({ hardware_id });

    if (!hardware) {
      return res.status(403).json({ message: "Unknown hardware device" });
    }

    if (hardware.status !== "ACTIVE") {
      return res.status(403).json({ message: "Hardware inactive" });
    }

    if (hardware.device_key !== deviceKey) {
      return res.status(403).json({ message: "Invalid device key" });
    }

    next();

  } catch (err) {
    res.status(500).json({ message: "Hardware authentication failed" });
  }
};

module.exports = verifyHardware;
