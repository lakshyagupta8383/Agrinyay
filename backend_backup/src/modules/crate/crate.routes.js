const express = require("express");
const router = express.Router({ mergeParams: true });
const verifyFirebase = require("../../middlewares/verifyFirebase");
const { addCrate } = require("./crate.controller");

router.post("/:batchId/crates", verifyFirebase, addCrate);

module.exports = router;
