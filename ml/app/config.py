import os

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

MODEL_PATH = os.path.join(BASE_DIR, "models", "quality_model.pkl")
VERSION_FILE = os.path.join(BASE_DIR, "model_version.txt")


def get_model_version():
    try:
        with open(VERSION_FILE, "r") as f:
            return f.read().strip()
    except:
        return "unknown"
