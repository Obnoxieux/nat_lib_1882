#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"

SCHEMA_FILE="openapi.yaml"
REDOCLY_CONFIG="redocly.yaml"
OUTPUT_FILE="redoc-static.html"
DEST_DIR="frontend/openapi"
DEST_FILE="$DEST_DIR/index.html"

abort() {
  echo "Error: $*" >&2
  exit 1
}

# Preconditions
command -v redocly >/dev/null 2>&1 || abort "redocly CLI not found. Install it (e.g., npm i -g @redocly/cli) and retry."

[[ -f "$SCHEMA_FILE" ]] || abort "Schema file not found: $SCHEMA_FILE"
[[ -f "$REDOCLY_CONFIG" ]] || abort "Redocly config not found: $REDOCLY_CONFIG"

echo "Building OpenAPI docs with Redocly..."
redocly build-docs "$SCHEMA_FILE" --config "$REDOCLY_CONFIG" --output "$OUTPUT_FILE" --disableGoogleFont

[[ -f "$OUTPUT_FILE" ]] || abort "Expected output not found after build: $OUTPUT_FILE"

mkdir -p "$DEST_DIR"
mv -f "$OUTPUT_FILE" "$DEST_FILE"

echo "OpenAPI docs generated and placed at: $DEST_FILE"
