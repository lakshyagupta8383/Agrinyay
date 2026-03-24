from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import JSONResponse

from .schemas import PredictionRequest, PredictionResponse
from .predictor import QualityPredictor
from .config import MODEL_PATH, get_model_version
from .logger import setup_logger


app = FastAPI(title="AGRINYAY ML Service")

logger = setup_logger()
model_version = get_model_version()

predictor = QualityPredictor(MODEL_PATH)



# Startup
@app.on_event("startup")
def startup_event():
    try:
        predictor.load()
        logger.info("Model loaded successfully.")
    except Exception as e:
        logger.critical(f"Model loading failed: {e}")
        raise e



# Global Exception Handler
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



# Health Check
@app.get("/health")
def health_check():
    return {
        "status": "ok",
        "model_version": model_version
    }



# Prediction Endpoint
@app.post("/predict", response_model=PredictionResponse)
async def predict(data: PredictionRequest):

    try:
        quality_score, spoilage_risk, predicted_price = await predictor.predict(
            data.dict()
        )

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