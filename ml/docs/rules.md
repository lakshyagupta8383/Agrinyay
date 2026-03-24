# AGRINYAY – Engineering Rules & Workflow

This document defines the official engineering standards for the AGRINYAY project.

Team:
- Ripun → ML + IoT
- Amlan → ML
- Ved → Mobile App
- Lakshya → Backend (Repo Lead)
- Drishita → Blockchain

---

# 1. Repository Structure

Permanent Branches:

- main → Stable production branch
- backend → Backend services
- ml → Machine Learning services
- iot → IoT firmware & sensor logic
- mobile-app → Farmer & Customer mobile application
- blockchain → Smart contracts & on-chain logic

No direct commits to `main`.

---

# 2. Branch Discipline

Each member works only in their module branch.

Examples:

Backend:
git checkout backend


No cross-branch direct merges.

All integration happens in controlled environment.

---

# 3. Commit Standards (Mandatory)

We follow Conventional Commits.

Format:

<type>: <clear description>


Allowed Types:

- feat → New feature
- fix → Bug fix
- refactor → Code restructuring
- docs → Documentation update
- test → Add/update tests
- chore → Maintenance
- perf → Performance improvement

Examples:



feat: implement batch creation API
feat: integrate ML prediction endpoint
fix: resolve sensor threshold bug
refactor: modularize offer service
docs: update system architecture


Forbidden commit messages:

- update
- done
- final
- changes
- misc

Commits must describe technical change clearly.

---

# 4. Folder Structure

Top-level structure:



/backend
/ml
/iot
/mobile-app
/blockchain
/docs


Each module must contain:

- README.md
- Environment configuration
- Dependency file
- Clear folder hierarchy

---

# 5. Environment Rules

- No secrets committed
- Use `.env`
- `.env` must be in `.gitignore`
- Provide `.env.example`

---

# 6. Backend Rules

- Follow REST API conventions
- Proper status codes
- Input validation mandatory
- Structured error responses

Example error format:

{
  "status": "error",
  "message": "Invalid batch ID"
}

- No business logic inside controllers
- Use service layer separation

---

# 7. ML Rules

- ML exposed as internal API
- Endpoint: /predict
- Model versioning required
- Containerized service preferred

---

# 8. IoT Rules

- Fixed sensor interval
- Threshold handling must be consistent
- No hardcoded IPs
- Communication via backend API only

---

# 9. Blockchain Rules

- Smart contracts versioned
- Contract ABI stored in /blockchain
- Critical events emit logs
- No heavy data stored on-chain
- Only store hashes and essential metadata

---

# 10. Code Quality

- Clear variable naming
- No commented dead code
- Remove console logs before merging
- Maintain readability

---

# 11. Professional Conduct

- Respect domain ownership
- Keep discussions technical
- Document architectural decisions
- No rushed unstable pushes

---

# Final Note

AGRINYAY is built as a real-world traceability system.

We code with production standards.
No shortcuts.
No messy commits.
Professional engineering only.
