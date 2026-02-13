import pandas as pd
import numpy as np
import joblib
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
from sklearn.metrics import mean_absolute_error, r2_score
from xgboost import XGBRegressor


# 1. Load Dataset
data_path = "../data/crop_sensitive_dataset.csv"
df = pd.read_csv(data_path)


# 2. Define Features & Target
features = [
    "crop_type",
    "avg_temp",
    "humidity_avg",
    "storage_hours",
    "violation_duration",
    "base_price"
]

target = "quality_score"

X = df[features]
y = df[target]


# 3. Preprocessing
categorical_features = ["crop_type"]
numerical_features = [
    "avg_temp",
    "humidity_avg",
    "storage_hours",
    "violation_duration",
    "base_price"
]

preprocessor = ColumnTransformer(
    transformers=[
        ("cat", OneHotEncoder(handle_unknown="ignore"), categorical_features),
        ("num", "passthrough", numerical_features)
    ]
)


# 4. XGBoost Model (Optimized)
model = XGBRegressor(
    n_estimators=1500,
    learning_rate=0.02,
    max_depth=10,
    min_child_weight=1,
    subsample=1.0,
    colsample_bytree=1.0,
    reg_lambda=0,
    gamma=0,
    random_state=42,
    objective="reg:squarederror"
)


pipeline = Pipeline(steps=[
    ("preprocessor", preprocessor),
    ("model", model)
])


# 5. Train/Test Split
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42
)

pipeline.fit(X_train, y_train)


# 6. Evaluation
predictions = pipeline.predict(X_test)

mae = mean_absolute_error(y_test, predictions)
r2 = r2_score(y_test, predictions)

print("\nModel Performance:")
print("MAE:", round(mae, 4))
print("R2 Score:", round(r2, 4))


# 7. Save Model
joblib.dump(pipeline, "../models/quality_model.pkl")

print("\nModel saved successfully.")
