const express = require("express");
const router = express.Router();
const { ingestSensorData } = require("./sensor.controller");
const verifyHardware = require("../../middlewares/verifyHardware");

router.post("/", verifyHardware, ingestSensorData);

module.exports = router;
