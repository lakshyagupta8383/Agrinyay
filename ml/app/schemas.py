from pydantic import BaseModel, Field
from enum import Enum


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
