const express = require("express");
const router = express.Router({ mergeParams: true });

const verifyFirebase = require("../../middlewares/verifyFirebase");
const { addCrate, getCratesByBatch } = require("./crate.controller");

router.post("/:batchId/crates", verifyFirebase, addCrate);
router.get("/:batchId/crates", verifyFirebase, getCratesByBatch);

module.exports = router;
