# Assignment Deliverables

This project implements the **single-agent** paradigm for Assignment 2 and is prepared to generate four ADD 3.0 iterations for the Hotel Pricing System.

## Files
- `report-template.md`: final report skeleton to complete in English.
- `../logs/`: generated conversation logs with timestamps and metadata.
- `../src/main/resources/prompts/`: system prompt and iteration prompts.

## Prerequisites
- Java 17+
- Maven 3.9+
- DashScope API key provided either by environment variable or a local profile file

## Quick Start
1. Export your own DashScope API key and model in the current terminal:
```bash
export DASHSCOPE_API_KEY="your-own-key"
export DASHSCOPE_CHAT_MODEL="qwen3-max"
```

2. Start the project:
```bash
mvn spring-boot:run
```

3. Generate all four ADD iterations in one run:
```bash
curl -X POST http://localhost:8080/api/assignment/runs \
  -H 'Content-Type: application/json' \
  -d '{
    "userPrompt": "Keep the outputs concise, assignment-compliant, and suitable for direct inclusion in the course report."
  }'
```
