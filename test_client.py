import requests
import time
from datetime import datetime

ML_URL = "http://127.0.0.1:8000/predict"

batch_data = {
    "batch_id": "B101",
    "farmer_id": "F23",
    "crop_type": "Banana",
    "initial_condition": "Raw",
    "avg_temp": 25,
    "humidity_avg": 85,
    "storage_hours": 0,
    "violation_duration": 0,
    "base_price": 50
}

INTERVAL_SECONDS = 5   # simulate every 5 seconds
TOTAL_ITERATIONS = 20  # run 20 cycles


for i in range(TOTAL_ITERATIONS):

    # Simulate storage increasing
    batch_data["storage_hours"] += 1

    # Simulate small temp fluctuation
    batch_data["avg_temp"] += 0.2

    try:
        response = requests.post(ML_URL, json=batch_data, timeout=2)
        result = response.json()

        print(f"\nTime: {datetime.now()}")
        print(f"Storage Hours: {batch_data['storage_hours']}")
        print("Response:", result)

    except Exception as e:
        print("Request failed:", e)

    time.sleep(INTERVAL_SECONDS)
