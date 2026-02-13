const express = require("express");
const router = express.Router();
const verifyFirebase = require("../../middlewares/verifyFirebase");
const { createBatch } = require("./batch.controller");

router.post("/", verifyFirebase, createBatch);

module.exports = router;
