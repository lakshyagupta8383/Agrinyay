const express = require("express");
const batchRoutes = require("./modules/batch/batch.routes");
const crateRoutes = require("./modules/crate/crate.routes");

const router = express.Router();

router.use("/batches", batchRoutes);
router.use("/batches", crateRoutes);

module.exports = router;
