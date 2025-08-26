package main

import (
	"encoding/json"
	"log"
	"net"
	"net/http"
	"os"

	"github.com/Obnoxieux/nat_lib_1882/api"
	"github.com/gorilla/mux"
	_ "github.com/lib/pq"
	"github.com/subosito/gotenv"
)

// / Loads environment.
//   - Locally: from .env file
//   - Production: from container env
func init() {
	_ = gotenv.Load()
	if os.Getenv("PG_DSN") == "" {
		log.Fatal("PG_DSN not set")
	}
}

func main() {
	server := api.NewServer()

	strictHandler := api.NewStrictHandler(server, nil)

	r := mux.NewRouter()

	r.Use(
		api.AddServerHeader(),
		api.CORSHandler(),
	)

	r.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "application/json")
		response := struct {
			Message string `json:"message"`
		}{
			"Go API Service available",
		}
		err := json.NewEncoder(w).Encode(response)
		if err != nil {
			w.WriteHeader(http.StatusInternalServerError)
		}
	})

	h := api.HandlerFromMux(strictHandler, r)

	s := &http.Server{
		Handler: h,
		Addr:    net.JoinHostPort("0.0.0.0", "8090"),
	}
	log.Printf("Listening on %s", s.Addr)

	log.Fatal(s.ListenAndServe())
}
