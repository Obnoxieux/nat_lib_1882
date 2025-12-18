# Stage 1: Build
FROM golang:1.25 AS build
WORKDIR /app
COPY go.mod go.sum ./
RUN go mod download
COPY . .
RUN go build -o natlib

# Stage 2: Run
FROM debian:trixie-slim
WORKDIR /app
COPY --from=build /app/natlib /app/natlib
EXPOSE 8090
CMD ["./natlib"]
