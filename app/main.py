from fastapi import FastAPI
import joblib
import pandas as pd
import os
from pydantic import BaseModel, Field

app = FastAPI()

# Load model directly (no abstraction yet)
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
MODEL_PATH = os.path.join(BASE_DIR, "models", "quality_model.pkl")

model = joblib.load(MODEL_PATH)


@app.get("/")
def home():
    return {"message": "AGRINYAY ML API Running"}


class PredictionRequest(BaseModel):
    batch_id: str
    farmer_id: str
    crop_type: str
    initial_condition: str
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

@app.post("/predict", response_model=PredictionResponse)
def predict(data: PredictionRequest):

    features = {
        "crop_type": data.crop_type,
        "initial_condition": data.initial_condition,
        "avg_temp": data.avg_temp,
        "humidity_avg": data.humidity_avg,
        "storage_hours": data.storage_hours,
        "violation_duration": data.violation_duration,
        "base_price": data.base_price,
    }

    df = pd.DataFrame([features])
    quality_score = float(model.predict(df)[0])

    spoilage_risk = 1 - (quality_score / 100)
    predicted_price = data.base_price * (quality_score / 100)

    return PredictionResponse(
        batch_id=data.batch_id,
        farmer_id=data.farmer_id,
        quality_score=round(quality_score, 4),
        spoilage_risk=round(spoilage_risk, 4),
        predicted_price=round(predicted_price, 4)
    )
