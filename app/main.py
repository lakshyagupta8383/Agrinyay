from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import JSONResponse
from pydantic import BaseModel, Field
from enum import Enum
import logging
import joblib
import pandas as pd
import os
import asyncio


# CONFIGURATION
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
MODEL_PATH = os.path.join(BASE_DIR, "models", "quality_model.pkl")
VERSION_FILE = os.path.join(BASE_DIR, "model_version.txt")


def get_model_version():
    try:
        with open(VERSION_FILE, "r") as f:
            return f.read().strip()
    except:
        return "unknown"


model_version = get_model_version()



# LOGGING SETUP
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s | %(levelname)s | %(message)s"
)

logger = logging.getLogger("AGRINYAY_ML")



# ENUM DEFINITIONS (DOMAIN RESTRICTION)
class CropType(str, Enum):
    Banana = "Banana"
    Apple = "Apple"
    Mango = "Mango"
    Orange = "Orange"
    Grape = "Grape"
    Strawberry = "Strawberry"


class InitialCondition(str, Enum):
    Raw = "Raw"
    SemiRipe = "Semi-Ripe"
    Ripe = "Ripe"



# REQUEST / RESPONSE SCHEMAS
class PredictionRequest(BaseModel):
    batch_id: str
    farmer_id: str
    crop_type: CropType
    initial_condition: InitialCondition
    avg_temp: float = Field(..., ge=-10, le=60)
    humidity_avg: float = Field(..., ge=0, le=100)
    storage_hours: float = Field(..., ge=0)
    violation_duration: float = Field(..., ge=0)
    base_price: float = Field(..., gt=0)


class PredictionResponse(BaseModel):
    batch_id: str
    farmer_id: str
    quality_score: float
    spoilage_risk: float
    predicted_price: float
    model_version: str



# FASTAPI INITIALIZATION
app = FastAPI(
    title="AGRINYAY ML Service",
    version=model_version
)



# STARTUP MODEL LOADING
@app.on_event("startup")
def load_model():
    try:
        app.state.model = joblib.load(MODEL_PATH)
        logger.info("Model loaded successfully.")
    except Exception as e:
        logger.critical(f"Model loading failed: {e}")
        raise e



# GLOBAL EXCEPTION HANDLER
@app.exception_handler(Exception)
async def global_exception_handler(request: Request, exc: Exception):
    logger.error(f"Unhandled error: {exc}")
    return JSONResponse(
        status_code=500,
        content={
            "error": "Internal Server Error",
            "message": "Something went wrong in ML service."
        }
    )



# HEALTH CHECK
@app.get("/health")
def health_check():
    return {
        "status": "ok",
        "model_version": model_version
    }



# PREDICTION ENDPOINT
@app.post("/predict", response_model=PredictionResponse)
async def predict(data: PredictionRequest):

    try:
        features = {
            "crop_type": data.crop_type.value,
            "initial_condition": data.initial_condition.value,
            "avg_temp": data.avg_temp,
            "humidity_avg": data.humidity_avg,
            "storage_hours": data.storage_hours,
            "violation_duration": data.violation_duration,
            "base_price": data.base_price,
        }

        df = pd.DataFrame([features])


        prediction = await asyncio.to_thread(app.state.model.predict, df)
        quality_score = float(prediction[0])


        spoilage_risk = 1 - (quality_score / 100)
        predicted_price = data.base_price * (quality_score / 100)

        logger.info(f"Prediction success | batch_id={data.batch_id}")

        return PredictionResponse(
            batch_id=data.batch_id,
            farmer_id=data.farmer_id,
            quality_score=round(quality_score, 4),
            spoilage_risk=round(spoilage_risk, 4),
            predicted_price=round(predicted_price, 4),
            model_version=model_version,
        )

    except Exception as e:
        logger.error(f"Prediction failed | batch_id={data.batch_id} | error={e}")
        raise HTTPException(status_code=500, detail="Prediction failed")
