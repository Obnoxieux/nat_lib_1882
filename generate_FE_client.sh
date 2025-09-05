openapi-generator generate \
  -i openapi.yaml \
  -g typescript-fetch \
  -o frontend/src/ts/api/gen \
  --additional-properties supportsES6=true,stringEnums=true,modelPropertyNaming=camelCase,paramNaming=camelCase