const admin = require("../config/firebase");

module.exports = async (req, res, next) => {
  try {
    const token = req.headers.authorization?.split(" ")[1];

    if (!token) {
      return res.status(401).json({ message: "No token provided" });
    }

    const decoded = await admin.auth().verifyIdToken(token);

    req.user = decoded;
    next();
  } catch (err) {
    console.log("TOKEN ERROR:", err.message);
    console.log("PROJECT ID:", process.env.FIREBASE_PROJECT_ID);
    return res.status(401).json({ message: "Invalid Firebase token" });
  }

};
