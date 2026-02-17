const express = require("express");
const router = express.Router();
const verifyFirebase = require("../../middlewares/verifyFirebase");

// Import BOTH functions
const { createBatch, getBatches } = require("./batch.controller"); 

// POST = Create
router.post("/", verifyFirebase, createBatch);

// GET = Fetch (This line was missing!)
router.get("/", verifyFirebase, getBatches);

module.exports = router;