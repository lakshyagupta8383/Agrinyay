# AGRINYAY – Complete System Overview

AGRINYAY is a traceable agri-supply chain platform integrating:

- Mobile Application
- Backend Services
- IoT Monitoring
- Machine Learning
- Blockchain Verification

The system ensures transparent crop quality tracking, AI-driven pricing, and tamper-proof traceability.

---

# 1. Mobile Layer

## A. Farmer Application

Functions:

- Login & Authentication
- Create Batch
- Scan QR/Barcode per crate
- Assign crop type
- View live sensor data
- View threshold violations
- Request quality prediction
- Receive AI price estimation
- Accept/Decline customer offers

Flow:

Farmer → Create Batch → Map Crates → Monitor Conditions → Get AI Prediction → Accept Offer

---

## B. Customer Application

Functions:

- Login
- Scan crate QR/Barcode
- View:
  - Crop type
  - Farmer details
  - Harvest date
  - Sensor history
  - Quality score
  - AI predicted price
- Make price offer

Flow:

Customer → Scan QR → Verify Data → Make Offer → Await Farmer Decision

---

# 2. Backend Layer

Core responsibility: Central coordination system.

Main Components:

## Authentication Service
- JWT based login
- Role-based access control

## Batch Service
- Create batch
- Map crates
- Maintain batch state

## Sensor Service
- Receive IoT data
- Store logs
- Monitor threshold violations
- Track violation duration

## Offer Service
- Store customer offers
- Update status (pending, accepted, declined)

## ML Integration Service
- Send processed sensor data to ML model
- Receive quality score & AI price
- Attach result to batch

Database Entities:

- Users
- Crates
- Batches
- Sensor Logs
- Offers

---

# 3. IoT Layer

Device: ESP32 based sensor unit

Tracks:
- Temperature
- Humidity
- Timestamp

Behavior:

- Sends data at fixed interval
- Calls backend API
- Backend stores data
- Backend checks thresholds

No heavy processing on device.
All intelligence handled server-side.

---

# 4. Machine Learning Layer

Purpose:

Predict:

- Quality Score (0–100)
- AI Suggested Market Price

Inputs:

- Average temperature
- Average humidity
- Violation duration
- Crop type
- Storage duration

Output:

{
  "quality_score": 87,
  "predicted_price": 24.5
}

ML runs as independent service.
Backend communicates via API.

---

# 5. Blockchain Layer

Purpose: Tamper-proof traceability.

Stores only:

- Batch ID
- Farmer ID
- Creation timestamp
- Final quality score
- Ownership transfer record

Does NOT store:

- Sensor logs
- Heavy datasets

Workflow:

Batch Created → Metadata Hash Stored On-Chain  
Batch Sold → Ownership Transfer Recorded  

Customer can verify authenticity via blockchain hash.

---

# 6. End-to-End Flow

1. Farmer creates batch
2. Crates mapped
3. IoT sends sensor data
4. Backend logs data
5. ML predicts quality & price
6. Customer scans crate
7. Views verified data
8. Makes offer
9. Farmer accepts
10. Blockchain records final transaction

---

# System Architecture Pattern

IoT → Backend → ML → Blockchain → Mobile

Microservice-based scalable system.

---

AGRINYAY is designed as a modular, scalable agri-traceability ecosystem suitable for real-world deployment.
