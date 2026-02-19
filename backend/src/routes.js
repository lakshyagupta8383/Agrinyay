const express = require("express");
const batchRoutes = require("./modules/batch/batch.routes");
const crateRoutes = require("./modules/crate/crate.routes");
const sensorRoutes = require("./modules/sensor/sensor.routes");

const router = express.Router();

router.use("/batches", batchRoutes);
router.use("/batches", crateRoutes);

// New sensor endpoint
router.use("/sensor", sensorRoutes);

module.exports = router;
