const express = require("express");
const batchRoutes = require("./modules/batch/batch.routes");
const crateRoutes = require("./modules/crate/crate.routes");

const router = express.Router();

// Mount Batch Routes -> Handles /api/batches
router.use("/batches", batchRoutes);

// FIX: Mount Crate Routes at "/batches" also
// Since crate.routes.js has "/:batchId/crates" inside it,
// this combines to: /api/batches + /:batchId/crates
router.use("/batches", crateRoutes); 

module.exports = router;