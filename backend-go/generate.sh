go tool oapi-codegen -config cfg.yaml ../openapi.yaml
mv -f gen.go ./api/gen.go