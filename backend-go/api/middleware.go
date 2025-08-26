package api

import (
	"net/http"

	"github.com/gorilla/mux"
)

func AddServerHeader() mux.MiddlewareFunc {
	return func(next http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			w.Header().Add("Server", "Gorilla/Mux (Go)")

			next.ServeHTTP(w, r)
		})
	}
}

func CORSHandler() mux.MiddlewareFunc {
	return func(next http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			w.Header().Set("Access-Control-Allow-Origin", "*")

			next.ServeHTTP(w, r)
		})
	}
}
