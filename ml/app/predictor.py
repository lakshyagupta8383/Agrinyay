import joblib
import pandas as pd
import asyncio


class QualityPredictor:

    def __init__(self, model_path: str):
        self.model_path = model_path
        self.model = None

    def load(self):
        self.model = joblib.load(self.model_path)

    async def predict(self, data: dict):

        if self.model is None:
            raise RuntimeError("Model not loaded")

        features = {
            "crop_type": data["crop_type"],
            "initial_condition": data["initial_condition"],
            "avg_temp": data["avg_temp"],
            "humidity_avg": data["humidity_avg"],
            "storage_hours": data["storage_hours"],
            "violation_duration": data["violation_duration"],
            "base_price": data["base_price"],
        }

        df = pd.DataFrame([features])

        prediction = await asyncio.to_thread(self.model.predict, df)
        quality_score = float(prediction[0])

        spoilage_risk = 1 - (quality_score / 100)
        predicted_price = data["base_price"] * (quality_score / 100)

        return quality_score, spoilage_risk, predicted_price
