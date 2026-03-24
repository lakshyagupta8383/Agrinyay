const express = require("express");
const router = express.Router();
const verifyFirebase = require("../../middlewares/verifyFirebase");

const { createBatch, getBatches } = require("./batch.controller"); 

router.post("/", verifyFirebase, createBatch);

router.get("/", verifyFirebase, getBatches);

module.exports = router;