const axios = require("axios");

const API_URL = "http://localhost:5000/api/sensor";

const hardware_id = "HW_001";
const device_key = "key_abc123";

// Generate realistic truck values
function generateSensorData() {
    const temperature = (20 + Math.random() * 15).toFixed(2); // 20–35°C
    const humidity = (50 + Math.random() * 30).toFixed(2);    // 50–80%

    return {
        hardware_id,
        temperature: Number(temperature),
        humidity: Number(humidity),
    };
}

async function sendSensorData() {
    const data = generateSensorData();

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "x-device-key": device_key,
            },
            body: JSON.stringify(data),
        });

        const text = await response.text();
        console.log("Status:", response.status);
        console.log("Response:", text);


        console.log("Sent:", data);
        console.log("Response:", response.data);
    } catch (error) {
        console.error("Error:", error.response?.data || error.message);
    }
}

// Run every 20 seconds
setInterval(sendSensorData, 20000);

console.log("Sensor simulation started...");
sendSensorData();
